package com.digitalwordcards.controllers;

import com.digitalwordcards.data.Role;
import com.digitalwordcards.data.User;
import com.digitalwordcards.data.repositories.UserRepository;
import com.digitalwordcards.data.requests.UserCreationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/")
public class UserController implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @PostMapping("/create")
    public User createUser(@RequestBody UserCreationRequest request) {
        final User user = new User();
        repository.findById(request.getEmail()).ifPresentOrElse(user1 -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exist");
        }, () -> {
            try {
                final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                try {
                    final Role role = Role.valueOf(request.getRole());
                    if (authentication.getAuthorities().stream().anyMatch(role::canBeGrantedBy)) {
                        user.setEmail(request.getEmail());
                        user.setRole(role);

                        user.setClazz(request.getClazz());
                        user.setName(request.getName());
                        user.setPassword(encoder.encode(request.getPassword()));

                        repository.saveAndFlush(user);
                    } else
                        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "You cannot create an user with " + role.getAuthority() + " authority!");
                } catch (IllegalArgumentException e) {
                    throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Unknown user role!");
                }
            } catch (NullPointerException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not authenticated");
            }
        });
        return user;
    }

    @DeleteMapping
    public void deleteUser(@RequestBody String email) {
        repository.deleteById(email);
    }

    @GetMapping
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repository.findById(s).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}

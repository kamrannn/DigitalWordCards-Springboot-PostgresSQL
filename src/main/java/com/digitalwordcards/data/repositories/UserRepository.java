package com.digitalwordcards.data.repositories;

import com.digitalwordcards.data.Card;
import com.digitalwordcards.data.CardAssociation;
import com.digitalwordcards.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByClazz(String clazz);
//    List<CardAssociation> findByViewedCards(Card card);
}

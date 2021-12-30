package com.digitalwordcards.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class CardAssociation {
    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Card card;
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

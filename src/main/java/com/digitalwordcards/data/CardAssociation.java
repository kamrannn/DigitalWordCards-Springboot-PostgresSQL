package com.digitalwordcards.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.IOException;
import java.util.UUID;

@Entity
//@Getter
//@Setter
/*
@JsonSerialize(using = CardAssociation.Serializer.class)
*/
public class CardAssociation {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Card card;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

/*    static class Serializer extends JsonSerializer<CardAssociation> {
        @Override
        public void serialize(CardAssociation value, JsonGenerator gen, SerializerProvider serializers) {
            try {
                gen.writeStartObject();
                gen.writeStringField("association_id", value.id.toString());
                gen.writeStringField("card", value.card.getId().toString());
                gen.writeStringField("user", value.user.getEmail());
                gen.writeEndObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

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

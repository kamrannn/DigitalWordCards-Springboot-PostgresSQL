package com.digitalwordcards.data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.IOException;
import java.util.UUID;

@Entity
@Getter
@Setter
@JsonSerialize(using = CardAssociation.Serializer.class)
public class CardAssociation {
    @Id
    private UUID id;

    @OneToOne
    private Card card;

    @OneToOne
    private User user;

    static class Serializer extends JsonSerializer<CardAssociation> {
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
    }
}

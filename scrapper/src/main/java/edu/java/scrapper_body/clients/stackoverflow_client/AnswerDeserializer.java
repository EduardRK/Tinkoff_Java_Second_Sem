package edu.java.scrapper_body.clients.stackoverflow_client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public final class AnswerDeserializer extends JsonDeserializer<Answer> {
    public AnswerDeserializer() {
    }

    @Override
    public Answer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        return new Answer(
            node.get("owner").get("display_name").asText(),
            node.get("body").asText(),
            OffsetDateTime.ofInstant(
                Instant.ofEpochSecond(
                    node.get("creation_date").asInt()
                ),
                ZoneOffset.UTC
            )
        );
    }
}

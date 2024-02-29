package edu.java.scrapper_body.clients.github_client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.time.OffsetDateTime;

public final class CommitDeserializer extends JsonDeserializer<Commit> {
    private static final String COMMIT = "commit";
    private static final String COMMITTER = "committer";

    public CommitDeserializer() {

    }

    @Override
    public Commit deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return new Commit(
            node.get(COMMIT).get(COMMITTER).get("name").asText(),
            node.get(COMMIT).get("message").asText(),
            OffsetDateTime.parse(node.get(COMMIT).get(COMMITTER).get("date").asText())
        );
    }
}

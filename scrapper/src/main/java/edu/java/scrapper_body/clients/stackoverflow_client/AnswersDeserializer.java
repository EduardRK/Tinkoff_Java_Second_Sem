package edu.java.scrapper_body.clients.stackoverflow_client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

public final class AnswersDeserializer extends JsonDeserializer<Answers> {
    public AnswersDeserializer() {

    }

    @Override
    public Answers deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode root = node.get("items");

        List<Answer> answerList = new ObjectMapper()
            .readerForListOf(Answer.class)
            .readValue(root);

        return new Answers(answerList);
    }
}

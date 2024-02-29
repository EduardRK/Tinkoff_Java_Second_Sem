package edu.java.scrapper_body.clients.stackoverflow_client;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;

@JsonDeserialize(using = AnswersDeserializer.class)
public record Answers(
    List<Answer> answers
) {
}

package edu.java.requests;

import java.util.List;

public record LinkUpdateRequest(
    long id,
    String uri,
    String description,
    List<Long> tgChatIds
) {
}

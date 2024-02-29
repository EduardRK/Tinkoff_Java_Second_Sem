package edu.java.bot.api.requests;

import java.util.List;

public record LinkUpdateRequest(
    int id,
    String url,
    String description,
    List<Integer> tgChatIds
) {
}

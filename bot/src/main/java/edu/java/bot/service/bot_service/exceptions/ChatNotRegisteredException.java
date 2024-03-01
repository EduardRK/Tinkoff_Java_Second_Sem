package edu.java.bot.service.bot_service.exceptions;

import java.util.List;

public class ChatNotRegisteredException extends RuntimeException {
    private final List<Integer> chatIds;

    public ChatNotRegisteredException(List<Integer> chatIds) {
        this.chatIds = chatIds;
    }

    public List<Integer> chatIds() {
        return chatIds;
    }
}

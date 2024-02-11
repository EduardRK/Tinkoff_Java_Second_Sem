package edu.java.bot.service.BotBody.Messages;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.response.SendResponse;
import org.jetbrains.annotations.NotNull;

public record MessageResponse(Message message) {
    public MessageResponse(@NotNull SendResponse sendResponse) {
        this(sendResponse.message());
    }
}

package edu.java.bot.service.bot_body.generators;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import org.jetbrains.annotations.NotNull;

public final class SendMessageFromCommandCompleteGenerator implements Generator<SendMessage, CommandComplete> {
    public SendMessageFromCommandCompleteGenerator() {

    }

    @Override
    public SendMessage nextObject(@NotNull CommandComplete object) {
        return new SendMessage(object.id(), object.message());
    }
}

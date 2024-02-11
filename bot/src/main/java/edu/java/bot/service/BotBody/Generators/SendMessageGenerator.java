package edu.java.bot.service.BotBody.Generators;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import org.jetbrains.annotations.NotNull;

public final class SendMessageGenerator implements Generator<CommandComplete, SendMessage> {
    private static final ParseMode PARSE_MODE = ParseMode.Markdown;

    public SendMessageGenerator() {

    }

    @Override
    public SendMessage next(@NotNull CommandComplete object) {
        return new SendMessage(object.id(), object.message()).parseMode(PARSE_MODE);
    }
}

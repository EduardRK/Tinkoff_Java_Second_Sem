package edu.java.bot.service.botBody.generators;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.botBody.commands.CommandComplete;

public class SendMessageGenerator implements Generator<SendMessage, CommandComplete> {
    private static final ParseMode PARSE_MODE = ParseMode.Markdown;

    public SendMessageGenerator() {

    }

    @Override
    public SendMessage nextObject(CommandComplete object) {
        return new SendMessage(object.id(), object.message())
            .parseMode(PARSE_MODE);
    }
}

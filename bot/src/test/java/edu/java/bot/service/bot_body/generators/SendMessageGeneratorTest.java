package edu.java.bot.service.bot_body.generators;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SendMessageGeneratorTest {

    @Test
    void nextObject() {
        Generator<SendMessage, CommandComplete> generator = new SendMessageGenerator();
        CommandComplete commandComplete = new CommandComplete(
            "Some text",
            123L
        );

        Assertions.assertNotNull(
            generator.nextObject(commandComplete)
        );
    }
}

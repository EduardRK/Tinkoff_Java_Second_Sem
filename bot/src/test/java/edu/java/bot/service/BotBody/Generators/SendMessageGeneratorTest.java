package edu.java.bot.service.BotBody.Generators;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SendMessageGeneratorTest {

    @Test
    void next() {
        Generator<CommandComplete, SendMessage> generator = new SendMessageGenerator();
        CommandComplete commandComplete = new CommandComplete("Some message", 123L);

        Assertions.assertNotNull(generator.next(commandComplete));
    }
}

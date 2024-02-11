package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class HelpCommandTest {

    @ParameterizedTest
    @CsvSource(value = {
        "/help, true",
        "/other, false"
    })
    void valid(String command, boolean result) {
        Message message = Mockito.mock(Message.class);

        Mockito.when(message.text()).thenReturn(command);

        HelpCommand helpCommand = new HelpCommand(message);

        Assertions.assertEquals(helpCommand.valid(), result);
    }

    @Test
    void applyCommand() {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.text()).thenReturn("/help");
        Mockito.when(chat.id()).thenReturn(123L);

        HelpCommand helpCommand = new HelpCommand(message);

        Assertions.assertEquals(
            new CommandComplete("""
                /help - all commands
                /start - user registration
                /list - list of tracked links
                /track - start tracking link
                /untrack - stop tracking link
                """, 123L),
            helpCommand.applyCommand()
        );
    }
}

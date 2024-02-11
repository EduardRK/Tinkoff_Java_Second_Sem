package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class ListCommandTest {

    @ParameterizedTest
    @CsvSource(value = {
        "/list, true",
        "/other, false"
    })
    void valid(String command, boolean result) {
        Message message = Mockito.mock(Message.class);

        Mockito.when(message.text()).thenReturn(command);

        ListCommand listCommand = new ListCommand(message);

        Assertions.assertEquals(result, listCommand.valid());
    }

    @Test
    void applyCommand() {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.text()).thenReturn("/list");
        Mockito.when(chat.id()).thenReturn(123L);

        ListCommand listCommand = new ListCommand(message);

        Assertions.assertEquals(
            new CommandComplete("Nothing to track.", 123L),
            listCommand.applyCommand()
        );
    }
}

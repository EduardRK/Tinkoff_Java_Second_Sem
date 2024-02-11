package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class TrackCommandTest {

    @ParameterizedTest
    @CsvSource(value = {
        "/track, true",
        "/other, false"
    })
    void valid(String command, boolean result) {
        Message message = Mockito.mock(Message.class);

        Mockito.when(message.text()).thenReturn(command);

        TrackCommand trackCommand = new TrackCommand(message);

        Assertions.assertEquals(result, trackCommand.valid());
    }

    @Test
    void applyCommand() {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.text()).thenReturn("/track");
        Mockito.when(chat.id()).thenReturn(123L);

        TrackCommand trackCommand = new TrackCommand(message);

        Assertions.assertEquals(
            new CommandComplete("Which link should I track?", 123L, true),
            trackCommand.applyCommand()
        );
    }
}

package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class EmptyCommandTest {

    @Test
    void valid() {
        Message message = new Message();
        EmptyCommand command = new EmptyCommand(message);

        Assertions.assertFalse(command.valid());
    }

    @Test
    void applyCommand() {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.text()).thenReturn("/someWrongCommand");
        Mockito.when(chat.id()).thenReturn(123L);

        EmptyCommand emptyCommand = new EmptyCommand(message);

        Assertions.assertEquals(
            new CommandComplete("Wrong command.", 123L),
            emptyCommand.applyCommand()
        );
    }
}

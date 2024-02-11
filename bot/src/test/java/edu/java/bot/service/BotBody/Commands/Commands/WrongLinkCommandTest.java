package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WrongLinkCommandTest {

    @Test
    void valid() {
        Message message = new Message();
        WrongLinkCommand wrongLinkCommand = new WrongLinkCommand(message);

        Assertions.assertFalse(wrongLinkCommand.valid());
    }

    @Test
    void applyCommand() {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.text()).thenReturn("Not link");
        Mockito.when(chat.id()).thenReturn(123L);

        WrongLinkCommand wrongLinkCommand = new WrongLinkCommand(message);

        Assertions.assertEquals(
            new CommandComplete("Wrong link.", 123L),
            wrongLinkCommand.applyCommand()
        );
    }
}

package edu.java.bot.service.bot_service.bot_body.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WrongLinkCommandTest {

    @Test
    void applyCommand() {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(123L);

        WrongLinkCommand wrongLinkCommand = new WrongLinkCommand(message);

        CommandComplete commandComplete = new CommandComplete(
            "Wrong link. Try again.",
            123L
        );

        Assertions.assertEquals(
            commandComplete,
            wrongLinkCommand.applyCommand()
        );
    }
}

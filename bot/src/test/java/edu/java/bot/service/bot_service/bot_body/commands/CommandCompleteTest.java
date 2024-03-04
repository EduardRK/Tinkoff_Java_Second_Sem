package edu.java.bot.service.bot_service.bot_body.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CommandCompleteTest {

    @Test
    void message() {
        long id = 123L;
        String string = "Some text for user";
        CommandComplete commandComplete = new CommandComplete(string, id);

        Assertions.assertEquals(
                string,
                commandComplete.message()
        );
    }

    @Test
    void id() {
        long id = 123L;
        String string = "Some text for user";
        CommandComplete commandComplete = new CommandComplete(string, id);

        Assertions.assertEquals(
                id,
                commandComplete.id()
        );
    }

    @Test
    void constructors() {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(123L);
        Mockito.when(message.text()).thenReturn("Some text");

        CommandComplete commandComplete = new CommandComplete(message);

        Assertions.assertEquals(
                "Some text",
                commandComplete.message()
        );
        Assertions.assertEquals(
                123L,
                commandComplete.id()
        );
    }
}

package edu.java.bot.service.botBody.commands.commandChain;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.botBody.commands.CommandComplete;
import edu.java.bot.service.botBody.commands.EmptyCommand;
import edu.java.bot.service.botBody.dataClasses.Link;
import edu.java.bot.service.dataBase.InMemoryDataBase;
import edu.java.bot.service.dataBase.InMemoryIdLinkDataBase;
import java.util.HashSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserRegistrationCheckCommandTest {
    @Test
    void applyCommand() {
        InMemoryDataBase<Long, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(123L);
        Mockito.when(message.text()).thenReturn("/track");

        UserRegistrationCheckCommand userRegistrationCheckCommand = new UserRegistrationCheckCommand(
            inMemoryDataBase,
            message,
            new EmptyCommand(message)
        );

        CommandComplete commandComplete = new CommandComplete(
            "You are not registered. Use the command /start.",
            123L
        );

        Assertions.assertEquals(
            commandComplete,
            userRegistrationCheckCommand.applyCommand()
        );
    }

    @Test
    void notValid() {
        InMemoryDataBase<Long, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(123L);

        UserRegistrationCheckCommand userRegistrationCheckCommand = new UserRegistrationCheckCommand(
            inMemoryDataBase,
            message,
            new EmptyCommand(message)
        );

        Assertions.assertTrue(userRegistrationCheckCommand.notValid());

        inMemoryDataBase.dataBase().put(123L, new HashSet<>());

        Assertions.assertFalse(userRegistrationCheckCommand.notValid());
    }
}

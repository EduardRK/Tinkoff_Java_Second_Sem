package edu.java.bot.service.botBody.commands.commandChain;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.botBody.commands.CommandComplete;
import edu.java.bot.service.botBody.dataClasses.Link;
import edu.java.bot.service.dataBase.InMemoryDataBase;
import edu.java.bot.service.dataBase.InMemoryIdLinkDataBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class StartCommandTest {

    @ParameterizedTest
    @CsvSource(value = {
        "/start, false",
        "/otherCommand, true"
    })
    void notValid(String command, boolean notValid) {
        InMemoryDataBase<Long, Link> inMemoryDataBase = Mockito.mock(InMemoryIdLinkDataBase.class);
        Message message = Mockito.mock(Message.class);

        Mockito.when(message.text()).thenReturn(command);

        StartCommand startCommand = new StartCommand(inMemoryDataBase, message);

        Assertions.assertEquals(
            notValid,
            startCommand.notValid()
        );
    }

    @Test
    void applyCommand() {
        InMemoryDataBase<Long, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();

        Message messageFirst = Mockito.mock(Message.class);
        Message messageSecond = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(messageFirst.text()).thenReturn("/start");
        Mockito.when(messageFirst.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(123L);

        Mockito.when(messageSecond.text()).thenReturn("/start");
        Mockito.when(messageSecond.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(123L);

        StartCommand startCommandFirst = new StartCommand(inMemoryDataBase, messageFirst);
        StartCommand startCommandSecond = new StartCommand(inMemoryDataBase, messageSecond);

        CommandComplete commandCompleteFirst = new CommandComplete(
            "The user has been successfully registered.",
            123L
        );
        CommandComplete commandCompleteSecond = new CommandComplete(
            "The user is already registered.",
            123L
        );

        Assertions.assertEquals(
            commandCompleteFirst,
            startCommandFirst.applyCommand()
        );
        Assertions.assertEquals(
            commandCompleteSecond,
            startCommandSecond.applyCommand()
        );
    }

    @Test
    void testToString() {
        StartCommand startCommand = new StartCommand();

        Assertions.assertEquals(
            "/start - user registration",
            startCommand.toString()
        );
    }
}

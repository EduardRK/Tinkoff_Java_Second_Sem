package edu.java.bot.service.botBody.commands.commandChain;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.botBody.commands.CommandComplete;
import edu.java.bot.service.botBody.dataClasses.Link;
import edu.java.bot.service.dataBase.InMemoryDataBase;
import edu.java.bot.service.dataBase.InMemoryIdLinkDataBase;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class UntrackCommandTest {

    @Test
    void applyCommand() throws URISyntaxException {
        InMemoryDataBase<Long, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
        Message messageFirst = Mockito.mock(Message.class);
        Message messageSecond = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(messageFirst.chat()).thenReturn(chat);
        Mockito.when(messageFirst.text()).thenReturn("/untrack");
        Mockito.when(messageSecond.chat()).thenReturn(chat);
        Mockito.when(messageSecond.text()).thenReturn("https://github.com/");
        Mockito.when(chat.id()).thenReturn(123L);

        inMemoryDataBase.dataBase().put(123L, new HashSet<>());

        UntrackCommand untrackCommandFirst = new UntrackCommand(inMemoryDataBase, messageFirst);
        UntrackCommand untrackCommandSecond = new UntrackCommand(inMemoryDataBase, messageSecond);

        CommandComplete commandCompleteFirst = new CommandComplete(
            "Which link should I untrack?",
            123L
        );
        CommandComplete commandCompleteSecond = new CommandComplete(
            "Link is not tracked.",
            123L
        );

        Assertions.assertEquals(
            commandCompleteFirst,
            untrackCommandFirst.applyCommand()
        );
        Assertions.assertEquals(
            commandCompleteSecond,
            untrackCommandSecond.applyCommand()
        );

        inMemoryDataBase.dataBase().put(123L, new HashSet<>(Set.of(new Link("https://github.com/"))));

        commandCompleteSecond = new CommandComplete(
            "The link is no longer being tracked.",
            123L
        );

        Assertions.assertEquals(
            commandCompleteFirst,
            untrackCommandFirst.applyCommand()
        );
        Assertions.assertEquals(
            commandCompleteSecond,
            untrackCommandSecond.applyCommand()
        );
        Assertions.assertFalse(
            inMemoryDataBase.dataBase()
                .get(123L)
                .contains(new Link("https://github.com/"))
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
        "/untrack, false",
        "/otherCommand, true"
    })
    void notValid(String command, boolean notValid) {
        InMemoryDataBase<Long, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
        Message message = Mockito.mock(Message.class);

        Mockito.when(message.text()).thenReturn(command);

        UntrackCommand untrackCommand = new UntrackCommand(inMemoryDataBase, message);

        Assertions.assertEquals(
            notValid,
            untrackCommand.notValid()
        );
    }

    @Test
    void testToString() {
        UntrackCommand untrackCommand = new UntrackCommand();

        Assertions.assertEquals(
            "/untrack - stop tracking link",
            untrackCommand.toString()
        );
    }
}

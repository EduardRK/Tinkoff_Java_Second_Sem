package edu.java.bot.service.bot_service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_service.bot_body.data_base.InMemoryDataBase;
import edu.java.bot.service.bot_service.bot_body.data_base.InMemoryIdLinkDataBase;
import edu.java.bot.service.bot_service.bot_body.data_classes.Link;
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
        InMemoryDataBase<Integer, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
        Message messageFirst = Mockito.mock(Message.class);
        Message messageSecond = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(messageFirst.chat()).thenReturn(chat);
        Mockito.when(messageFirst.text()).thenReturn("/untrack");
        Mockito.when(messageSecond.chat()).thenReturn(chat);
        Mockito.when(messageSecond.text()).thenReturn("https://github.com/");
        Mockito.when(chat.id()).thenReturn(123L);

        inMemoryDataBase.dataBase().put(123, new HashSet<>());

        UntrackCommand untrackCommandFirst = new UntrackCommand(inMemoryDataBase, messageFirst);
        UntrackCommand untrackCommandSecond = new UntrackCommand(inMemoryDataBase, messageSecond);

        CommandComplete commandCompleteFirst = new CommandComplete(
            "Which link should I untrack?",
            123
        );
        CommandComplete commandCompleteSecond = new CommandComplete(
            "Link is not tracked.",
            123
        );

        Assertions.assertEquals(
            commandCompleteFirst,
            untrackCommandFirst.applyCommand()
        );
        Assertions.assertEquals(
            commandCompleteSecond,
            untrackCommandSecond.applyCommand()
        );

        inMemoryDataBase.dataBase().put(123, new HashSet<>(Set.of(new Link("https://github.com/"))));

        commandCompleteSecond = new CommandComplete(
            "The link is no longer being tracked.",
            123
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
                .get(123)
                .contains(new Link("https://github.com/"))
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
        "/untrack, false",
        "/otherCommand, true"
    })
    void notValid(String command, boolean notValid) {
        InMemoryDataBase<Integer, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
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

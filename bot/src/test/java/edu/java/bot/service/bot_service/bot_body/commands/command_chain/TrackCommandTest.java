package edu.java.bot.service.bot_service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_service.bot_body.data_base.InMemoryDataBase;
import edu.java.bot.service.bot_service.bot_body.data_base.InMemoryIdLinkDataBase;
import edu.java.bot.service.bot_service.bot_body.data_classes.Link;
import java.net.URISyntaxException;
import java.util.HashSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class TrackCommandTest {

    @Test
    void applyCommand() throws URISyntaxException {
        InMemoryDataBase<Integer, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
        Message messageFirst = Mockito.mock(Message.class);
        Message messageSecond = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(messageFirst.chat()).thenReturn(chat);
        Mockito.when(messageFirst.text()).thenReturn("/track");
        Mockito.when(messageSecond.chat()).thenReturn(chat);
        Mockito.when(messageSecond.text()).thenReturn("https://github.com/");
        Mockito.when(chat.id()).thenReturn(123L);

        inMemoryDataBase.dataBase().put(123, new HashSet<>());

        TrackCommand trackCommandFirst = new TrackCommand(inMemoryDataBase, messageFirst);
        TrackCommand trackCommandSecond = new TrackCommand(inMemoryDataBase, messageSecond);

        CommandComplete commandCompleteFirst = new CommandComplete(
            "Which link should I track?",
            123
        );
        CommandComplete commandCompleteSecond = new CommandComplete(
            "The link is being tracked.",
            123
        );

        Assertions.assertEquals(
            commandCompleteFirst,
            trackCommandFirst.applyCommand()
        );
        Assertions.assertEquals(
            commandCompleteSecond,
            trackCommandSecond.applyCommand()
        );

        Assertions.assertTrue(
            inMemoryDataBase.dataBase()
                .get(123)
                .contains(new Link("https://github.com/"))
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
        "/track, false",
        "/otherCommand, true"
    })
    void notValid(String command, boolean notValid) {
        InMemoryDataBase<Integer, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
        Message message = Mockito.mock(Message.class);

        Mockito.when(message.text()).thenReturn(command);

        TrackCommand trackCommand = new TrackCommand(inMemoryDataBase, message);

        Assertions.assertEquals(
            notValid,
            trackCommand.notValid()
        );
    }

    @Test
    void testToString() {
        TrackCommand trackCommand = new TrackCommand();

        Assertions.assertEquals(
            "/track - start tracking link",
            trackCommand.toString()
        );
    }
}

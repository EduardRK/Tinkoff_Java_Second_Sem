package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.domain.InMemoryDataBase;
import edu.java.bot.domain.InMemoryIdLinkDataBase;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.data_classes.Link;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class ListCommandTest {

    @Test
    void applyCommand() throws URISyntaxException {
        InMemoryDataBase<Long, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.text()).thenReturn("/list");
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(123L);

        ListCommand listCommand = new ListCommand(inMemoryDataBase, message);
        CommandComplete commandComplete = new CommandComplete(
            "No links are tracked.",
            123L
        );

        Assertions.assertEquals(
            commandComplete,
            listCommand.applyCommand()
        );

        inMemoryDataBase.dataBase().put(
            123L,
            new HashSet<>(Set.of(new Link("https://github.com/")))
        );

        commandComplete = new CommandComplete(
            "https://github.com/" + System.lineSeparator(),
            123L
        );

        Assertions.assertEquals(
            commandComplete,
            listCommand.applyCommand()
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
        "/list, false",
        "/otherCommand, true"
    })
    void notValid(String command, boolean notValid) {
        InMemoryDataBase<Long, Link> inMemoryDataBase = Mockito.mock(InMemoryIdLinkDataBase.class);
        Message message = Mockito.mock(Message.class);

        Mockito.when(message.text()).thenReturn(command);

        ListCommand listCommand = new ListCommand(inMemoryDataBase, message);

        Assertions.assertEquals(
            notValid,
            listCommand.notValid()
        );
    }

    @Test
    void testToString() {
        ListCommand listCommand = new ListCommand();

        Assertions.assertEquals(
            "/list - list of tracked links",
            listCommand.toString()
        );
    }
}

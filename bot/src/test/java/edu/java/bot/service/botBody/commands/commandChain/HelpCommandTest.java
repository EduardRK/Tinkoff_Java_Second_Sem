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

class HelpCommandTest {

    @Test
    void applyCommand() {
        InMemoryDataBase<Long, Link> inMemoryDataBase = Mockito.mock(InMemoryIdLinkDataBase.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(123L);
        Mockito.when(message.text()).thenReturn("/help");

        HelpCommand helpCommand = new HelpCommand(inMemoryDataBase, message);
        CommandComplete commandComplete = new CommandComplete(
            "/start - user registration" + System.lineSeparator() +
                "/help - print all commands" + System.lineSeparator() +
                "/list - list of tracked links" + System.lineSeparator() +
                "/track - start tracking link" + System.lineSeparator() +
                "/untrack - stop tracking link" + System.lineSeparator(),
            123L
        );

        Assertions.assertEquals(
            commandComplete,
            helpCommand.applyCommand()
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
        "/help, false",
        "/otherCommand, true"
    })
    void notValid(String command, boolean notValid) {
        InMemoryDataBase<Long, Link> inMemoryDataBase = Mockito.mock(InMemoryIdLinkDataBase.class);
        Message message = Mockito.mock(Message.class);

        Mockito.when(message.text()).thenReturn(command);

        HelpCommand helpCommand = new HelpCommand(inMemoryDataBase, message);

        Assertions.assertEquals(
            notValid,
            helpCommand.notValid()
        );
    }

    @Test
    void testToString() {
        HelpCommand helpCommand = new HelpCommand();

        Assertions.assertEquals(
            "/help - print all commands",
            helpCommand.toString()
        );
    }
}

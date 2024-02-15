package edu.java.bot.service.botBody.commands.commandChain;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.botBody.commands.CommandComplete;
import edu.java.bot.service.botBody.dataClasses.Link;
import edu.java.bot.service.dataBase.InMemoryDataBase;
import edu.java.bot.service.dataBase.InMemoryIdLinkDataBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class CommandChainTest {

    @ParameterizedTest
    @CsvSource(value = {
        "/start, The user has been successfully registered.",
        "/list, No links are tracked.",
        "/track, Which link should I track?",
        "/untrack, Which link should I untrack?"
    })
    void applyCommand(String command, String response) {
        InMemoryDataBase<Long, Link> inMemoryDataBase = new InMemoryIdLinkDataBase();
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.text()).thenReturn(command);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(123L);
        Mockito.when(update.message()).thenReturn(message);

        CommandChain commandChain = new CommandChain(inMemoryDataBase, message);

        CommandComplete commandComplete = new CommandComplete(
            response,
            123L
        );

        Assertions.assertEquals(
            commandComplete,
            commandChain.applyCommand()
        );
    }
}

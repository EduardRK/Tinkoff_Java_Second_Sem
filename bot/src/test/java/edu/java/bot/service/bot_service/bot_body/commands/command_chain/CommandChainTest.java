package edu.java.bot.service.bot_service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.bot_service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_service.bot_body.data_base.InMemoryDataBase;
import edu.java.bot.service.bot_service.bot_body.data_base.InMemoryIdLinkDataBase;
import edu.java.bot.service.bot_service.bot_body.data_classes.Link;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

class CommandChainTest {
    private static final InMemoryDataBase<Integer, Link> IN_MEMORY_DATA_BASE = new InMemoryIdLinkDataBase();

    private static Arguments[] helpData() {
        return new Arguments[] {
            Arguments.of(
                "/help",
                "/start - user registration" + System.lineSeparator() +
                    "/help - print all commands" + System.lineSeparator() +
                    "/list - list of tracked links" + System.lineSeparator() +
                    "/track - start tracking link" + System.lineSeparator() +
                    "/untrack - stop tracking link" + System.lineSeparator()
            )
        };
    }

    @ParameterizedTest
    @CsvSource(value = {
        "/start, The user has been successfully registered.",
        "/list, No links are tracked.",
        "/track, Which link should I track?",
        "Wrong link, Wrong link. Try again.",
        "/untrack, Which link should I untrack?",
        "Wrong link, Wrong link. Try again."
    })
    @MethodSource(value = "helpData")
    void applyCommand(String command, String response) {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.text()).thenReturn(command);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(123L);
        Mockito.when(update.message()).thenReturn(message);

        CommandChain commandChain = CommandChain.defaultChain(IN_MEMORY_DATA_BASE, message);

        CommandComplete commandComplete = new CommandComplete(
            response,
            123
        );

        Assertions.assertEquals(
            commandComplete,
            commandChain.applyCommand()
        );
    }
}

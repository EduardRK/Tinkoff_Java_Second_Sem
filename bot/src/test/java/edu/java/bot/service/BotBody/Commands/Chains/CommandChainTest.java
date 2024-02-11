package edu.java.bot.service.BotBody.Commands.Chains;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

class CommandChainTest {
    private static Arguments @NotNull [] helpData() {
        return new Arguments[] {
            Arguments.of("/help", """
                /help - all commands
                /start - user registration
                /list - list of tracked links
                /track - start tracking link
                /untrack - stop tracking link
                """, false),
        };
    }

    @ParameterizedTest
    @CsvSource(value = {
        "/start, User registered., false",
        "/list, Nothing to track., false",
        "/track, Which link should I track?, true",
        "/untrack, Which link should I untrack?, true",
        "/otherCommand, Wrong command., false"
    })
    @MethodSource(value = "helpData")
    void testStart(String command, String response, boolean nextExpected) {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.text()).thenReturn(command);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(123L);

        CommandChain chain = new CommandChain(message);

        Assertions.assertEquals(
            new CommandComplete(response, 123L, nextExpected),
            chain.start()
        );
    }
}

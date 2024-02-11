package edu.java.bot.service.BotBody.Commands.Chains;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class LinkTrackCommandChainTest {

    @ParameterizedTest
    @CsvSource(value = {
        "https://stackoverflow.com/questions/40471/what-are-the-differences-between-a-hashmap-and-a-hashtable-in-java?rq=1, Link is being tracked, false",
        "Other link, Wrong link., false",
    })
    void start(String userMessage, String response, boolean nextExpected) {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.text()).thenReturn(userMessage);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(123L);

        LinkTrackCommandChain chain = new LinkTrackCommandChain(message);

        Assertions.assertEquals(
            new CommandComplete(response, 123L, nextExpected),
            chain.start()
        );
    }
}

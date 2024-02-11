package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class LinkTrackCommandTest {

    @ParameterizedTest
    @CsvSource(value = {
        "Some wrong link, false",
        "https://stackoverflow.com/questions/40471/what-are-the-differences-between-a-hashmap-and-a-hashtable-in-java?rq=1, true"
    })
    void valid(String command, boolean result) {
        Message message = Mockito.mock(Message.class);

        Mockito.when(message.text()).thenReturn(command);

        LinkTrackCommand linkTrackCommand = new LinkTrackCommand(message);
        Assertions.assertEquals(result, linkTrackCommand.valid());
    }

    @Test
    void applyCommand() {
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.text()).thenReturn("https://stackoverflow.com");
        Mockito.when(chat.id()).thenReturn(123L);

        LinkTrackCommand linkTrackCommand = new LinkTrackCommand(message);

        Assertions.assertEquals(
            new CommandComplete("Link is being tracked", 123L),
            linkTrackCommand.applyCommand()
        );
    }
}

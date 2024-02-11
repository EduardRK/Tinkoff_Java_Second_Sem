package edu.java.bot.service.BotBody.Messages;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MessageResponseTest {

    @Test
    void message() {
        Message message = Mockito.mock(Message.class);
        SendResponse sendResponse = Mockito.mock(SendResponse.class);

        Mockito.when(sendResponse.message()).thenReturn(message);

        MessageResponse messageResponse = new MessageResponse(message);
        MessageResponse messageResponse1 = new MessageResponse(sendResponse);

        Assertions.assertEquals(messageResponse1, messageResponse);
    }
}

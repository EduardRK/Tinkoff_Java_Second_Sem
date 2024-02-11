package edu.java.bot.service.BotBody.Handlers;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.mockito.Mockito;

class UpdateHandlerTest {

    @Test
    void put() {
        Handler<Update, Message> handler = new UpdateHandler();
        Update update = Mockito.mock(Update.class);

        Mockito.when(update.message()).thenReturn(new Message());

        handler.put(update);

        Assertions.assertEquals(update.message(), handler.get());
    }

    @Test
    void get() {
        Handler<Update, Message> handler = new UpdateHandler();
        Update update = Mockito.mock(Update.class);

        Mockito.when(update.message()).thenReturn(new Message());

        handler.put(update);

        Assertions.assertDoesNotThrow((ThrowingSupplier<Object>) handler::get);
    }
}

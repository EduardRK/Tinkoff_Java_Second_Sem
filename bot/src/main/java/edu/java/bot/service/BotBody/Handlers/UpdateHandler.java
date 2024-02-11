package edu.java.bot.service.BotBody.Handlers;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public final class UpdateHandler implements Handler<Update, Message> {
    private final BlockingQueue<Update> updateQueue;

    public UpdateHandler() {
        this.updateQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void put(Update value) {
        updateQueue.offer(value);
    }

    @Override
    public Message get() {
        try {
            return updateQueue.take().message();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

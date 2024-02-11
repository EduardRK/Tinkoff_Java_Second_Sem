package edu.java.bot.service.BotBody.Handlers;

public interface Handler<T, R> {
    void put(T value);

    R get();
}

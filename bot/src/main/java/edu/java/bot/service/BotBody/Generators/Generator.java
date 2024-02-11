package edu.java.bot.service.BotBody.Generators;

public interface Generator<T, R> {
    R next(T object);
}

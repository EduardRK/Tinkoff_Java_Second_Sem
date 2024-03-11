package edu.java.bot.service.bot_body.generators;

public interface Generator<R, T> {
    R nextObject(T object);
}

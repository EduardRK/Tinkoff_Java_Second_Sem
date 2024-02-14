package edu.java.bot.service.botBody.generators;

public interface Generator<R, T> {
    R nextObject(T object);
}

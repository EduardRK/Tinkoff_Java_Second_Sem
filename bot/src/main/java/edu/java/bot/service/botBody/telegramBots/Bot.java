package edu.java.bot.service.botBody.telegramBots;

import com.pengrad.telegrambot.UpdatesListener;

public interface Bot extends AutoCloseable, UpdatesListener {
    void start();
}

package edu.java.bot.service.bot_body.telegram_bots;

import com.pengrad.telegrambot.UpdatesListener;

public interface Bot extends AutoCloseable, UpdatesListener {
    void start();
}

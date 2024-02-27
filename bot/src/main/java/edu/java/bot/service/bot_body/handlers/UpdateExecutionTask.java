package edu.java.bot.service.bot_body.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.command_chain.CommandChain;
import edu.java.bot.service.bot_body.data_classes.Link;
import edu.java.bot.service.bot_body.generators.Generator;
import edu.java.bot.service.bot_body.generators.SendMessageGenerator;
import edu.java.bot.service.data_base.InMemoryDataBase;

public final class UpdateExecutionTask implements Runnable {
    private static final Generator<SendMessage, CommandComplete> GENERATOR = new SendMessageGenerator();
    private final TelegramBot telegramBot;
    private final InMemoryDataBase<Long, Link> dataBase;
    private final Update update;

    public UpdateExecutionTask(TelegramBot telegramBot, InMemoryDataBase<Long, Link> dataBase, Update update) {
        this.telegramBot = telegramBot;
        this.dataBase = dataBase;
        this.update = update;
    }

    @Override
    public void run() {
        CommandChain commandChain = CommandChain.defaultChain(dataBase, update);
        CommandComplete commandComplete = commandChain.applyCommand();
        telegramBot.execute(GENERATOR.nextObject(commandComplete));
    }
}

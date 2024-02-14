package edu.java.bot.service.botBody.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.botBody.commands.Command;
import edu.java.bot.service.botBody.commands.CommandComplete;
import edu.java.bot.service.botBody.commands.commandChain.CommandChain;
import edu.java.bot.service.botBody.dataClasses.Link;
import edu.java.bot.service.botBody.generators.Generator;
import edu.java.bot.service.botBody.generators.SendMessageGenerator;
import edu.java.bot.service.dataBase.InMemoryDataBase;

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
        Command command = new CommandChain(dataBase, update);
        CommandComplete commandComplete = command.applyCommand();
        telegramBot.execute(GENERATOR.nextObject(commandComplete));
    }
}

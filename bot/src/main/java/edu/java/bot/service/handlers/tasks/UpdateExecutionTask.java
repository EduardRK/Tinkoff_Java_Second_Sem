package edu.java.bot.service.handlers.tasks;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.command_chain.CommandChain;
import edu.java.bot.service.bot_body.generators.Generator;
import edu.java.bot.service.bot_body.generators.SendMessageFromCommandCompleteGenerator;
import edu.java.bot.service.scrapper_client.ScrapperClient;

public final class UpdateExecutionTask implements Runnable {
    private static final Generator<SendMessage, CommandComplete> GENERATOR =
        new SendMessageFromCommandCompleteGenerator();
    private final TelegramBot telegramBot;
    private final ScrapperClient scrapperClient;
    private final Message message;

    public UpdateExecutionTask(
        TelegramBot telegramBot,
        ScrapperClient scrapperClient,
        Message message
    ) {
        this.telegramBot = telegramBot;
        this.scrapperClient = scrapperClient;
        this.message = message;
    }

    public UpdateExecutionTask(
        TelegramBot telegramBot,
        ScrapperClient scrapperClient,
        Update update
    ) {
        this(telegramBot, scrapperClient, update.message());
    }

    @Override
    public void run() {
        CommandChain commandChain = CommandChain.defaultChain(scrapperClient);
        CommandComplete commandComplete = commandChain.applyCommand(message);
        telegramBot.execute(GENERATOR.nextObject(commandComplete));
    }
}

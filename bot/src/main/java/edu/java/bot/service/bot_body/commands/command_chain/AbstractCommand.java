package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.scrapper_client.ScrapperClient;

public abstract class AbstractCommand implements Command {
    protected final ScrapperClient scrapperClient;
    protected final Command nextCommand;

    protected AbstractCommand(ScrapperClient scrapperClient, Command command) {
        this.scrapperClient = scrapperClient;
        this.nextCommand = command;
    }

    protected abstract boolean notValid(Message message);

    protected boolean messageTextNull(Message message) {
        return message.text() == null;
    }
}

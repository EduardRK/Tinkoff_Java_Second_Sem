package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.scrapper_client.ScrapperClient;

public final class DeleteCommand extends AbstractCommand {
    public DeleteCommand(ScrapperClient scrapperClient, Command command) {
        super(scrapperClient, command);
    }

    public DeleteCommand(ScrapperClient scrapperClient) {
        super(scrapperClient, new EmptyCommand());
    }

    public DeleteCommand() {
        super(null, null);
    }

    @Override
    public CommandComplete applyCommand(Message message) {
        return null;
    }

    @Override
    protected boolean notValid(Message message) {
        return messageTextNull(message) || !message.text().equals("/delete");
    }

    @Override
    public String toString() {
        return "/delete - user unregister";
    }
}

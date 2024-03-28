package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.scrapper_client.ScrapperClient;
import org.jetbrains.annotations.NotNull;

public final class CommandChain implements Command {
    private final Command chain;

    public CommandChain(Command chain) {
        this.chain = chain;
    }

    public static @NotNull CommandChain defaultChain(ScrapperClient scrapperClient) {
        UntrackCommand untrackCommand = new UntrackCommand(scrapperClient);
        TrackCommand trackCommand = new TrackCommand(scrapperClient, untrackCommand);
        ListCommand listCommand = new ListCommand(scrapperClient, trackCommand);
        HelpCommand helpCommand = new HelpCommand(scrapperClient, listCommand);
        StartCommand startCommand = new StartCommand(scrapperClient, helpCommand);

        return new CommandChain(startCommand);
    }

    @Override
    public CommandComplete applyCommand(Message message) {
        return chain.applyCommand(message);
    }
}

package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.scrapper_client.ScrapperClient;
import edu.java.responses.LinkResponse;
import edu.java.responses.ListLinksResponse;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class ListCommand extends AbstractCommand {
    private static final String NOTHING_TRACK = "No links are tracked.";

    public ListCommand(ScrapperClient scrapperClient, Command next) {
        super(scrapperClient, next);
    }

    public ListCommand(ScrapperClient scrapperClient) {
        super(scrapperClient, new EmptyCommand());
    }

    public ListCommand() {
        this(null, null);
    }

    @Override
    public CommandComplete applyCommand(Message message) {
        if (notValid(message)) {
            return nextCommand.applyCommand(message);
        }

        long id = message.chat().id();

        if (nothingTrack(message)) {
            return new CommandComplete(NOTHING_TRACK, id);
        }

        StringBuilder stringBuilder = new StringBuilder();
        ListLinksResponse listLinksResponse = scrapperClient.allTrackedLinks(id);
        for (LinkResponse linkResponse : listLinksResponse.links()) {
            stringBuilder.append(linkResponse.url()).append(System.lineSeparator());
        }

        return new CommandComplete(stringBuilder.toString(), id);
    }

    @Override
    protected boolean notValid(Message message) {
        return messageTextNull(message) || !message.text().equals("/list");
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "/list - list of tracked links";
    }

    private boolean nothingTrack(Message message) {
        long id = message.chat().id();

        ListLinksResponse listLinksResponse = scrapperClient.allTrackedLinks(id);
        return listLinksResponse.links().isEmpty();
    }
}

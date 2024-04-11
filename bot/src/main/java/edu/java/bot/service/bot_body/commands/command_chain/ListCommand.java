package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.scrapper_client.ApiErrorException;
import edu.java.bot.service.scrapper_client.ScrapperClient;
import edu.java.responses.ListLinksResponse;
import java.util.ArrayList;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public final class ListCommand extends AbstractCommand {
    private static final String NOTHING_TRACK = "No links are tracked.";

    public ListCommand(ScrapperClient scrapperClient, Command next) {
        super(scrapperClient, next);
    }

    public ListCommand(ScrapperClient scrapperClient) {
        super(scrapperClient, new EmptyCommand());
    }

    public ListCommand() {
        super(null, null);
    }

    @Override
    public CommandComplete applyCommand(Message message) {
        if (notValid(message)) {
            return nextCommand.applyCommand(message);
        }

        long id = message.chat().id();

        Optional<ListLinksResponse> linksResponse = scrapperClient.allTrackedLinks(id)
            .onErrorResume(ApiErrorException.class, e -> Mono.just(new ListLinksResponse(new ArrayList<>(), 0)))
            .blockOptional();

        if (linksResponse.isEmpty() || linksResponse.get().size() == 0) {
            return new CommandComplete(NOTHING_TRACK, id);
        }

        ListLinksResponse listLinksResponse = linksResponse.get();
        StringBuilder stringBuilder = new StringBuilder();

        listLinksResponse.links().forEach(
            linkResponse -> stringBuilder
                .append(linkResponse.url())
                .append(System.lineSeparator())
                .append(System.lineSeparator())
        );

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

}

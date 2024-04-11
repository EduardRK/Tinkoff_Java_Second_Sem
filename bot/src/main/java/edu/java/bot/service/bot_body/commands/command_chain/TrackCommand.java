package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.scrapper_client.ApiErrorException;
import edu.java.bot.service.scrapper_client.ScrapperClient;
import edu.java.requests.AddLinkRequest;
import edu.java.responses.ApiErrorResponse;
import edu.java.responses.LinkResponse;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public final class TrackCommand extends AbstractCommand {
    private static final String REGEX = "/track\\s+(http.+)";
    private static final String LINK_START_TRACKED = "Link start tracked";
    private static final String SOMETHING_WRONG = "Something went wrong, please wait";
    private static final String INCORRECT_LINK = "Incorrect link. Try again";

    public TrackCommand(ScrapperClient scrapperClient, Command next) {
        super(scrapperClient, next);
    }

    public TrackCommand(ScrapperClient scrapperClient) {
        super(scrapperClient, new EmptyCommand());
    }

    public TrackCommand() {
        super(null, null);
    }

    @Override
    public CommandComplete applyCommand(Message message) {
        if (notValid(message)) {
            return nextCommand.applyCommand(message);
        }

        long id = message.chat().id();

        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(message.text());

        if (matcher.find()) {
            String link = matcher.group(1);
            AddLinkRequest addLinkRequest = new AddLinkRequest(link);
            Optional<LinkResponse> linkResponse = scrapperClient.startTrackLink(id, addLinkRequest)
                .onErrorResume(ApiErrorException.class, e -> {
                    ApiErrorResponse apiErrorResponse = e.apiErrorResponse();
                    return Mono.just(new LinkResponse(-1, apiErrorResponse.description()));
                })
                .blockOptional();

            return linkResponse.map(response -> response.id() == -1
                    ? new CommandComplete(response.url(), id)
                    : new CommandComplete(LINK_START_TRACKED, id))
                .orElseGet(() -> new CommandComplete(SOMETHING_WRONG, id));

        }

        return new CommandComplete(INCORRECT_LINK, id);
    }

    @Override
    protected boolean notValid(Message message) {
        return messageTextNull(message) || !isCorrectLink(message);
    }

    private boolean isCorrectLink(Message message) {
        return Pattern
            .compile(REGEX)
            .matcher(message.text())
            .matches();
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "/track - start tracking link";
    }
}

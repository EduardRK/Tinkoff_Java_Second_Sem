package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.bot_body.commands.WrongLinkCommand;
import edu.java.bot.service.scrapper_client.ScrapperClient;
import edu.java.requests.AddLinkRequest;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class TrackCommand extends AbstractCommand {
    private static final String TRACK = "track";
    private static final String LINK_TO_TRACK = "Which link should I track?";

    public TrackCommand(ScrapperClient scrapperClient, Command next) {
        super(scrapperClient, next);
    }

    public TrackCommand(ScrapperClient scrapperClient) {
        this(scrapperClient, new EmptyCommand());
    }

    public TrackCommand() {
        this(null, null);
    }

    @Override
    public CommandComplete applyCommand(Message message) {
        long id = message.chat().id();

        if (WAITING_NEXT_COMMAND.getOrDefault(id, "").equals(TRACK)) {
            WAITING_NEXT_COMMAND.remove(id);
            return new LinkTrackCommand(scrapperClient).applyCommand(message);
        }

        if (notValid(message)) {
            return nextCommand.applyCommand(message);
        }

        WAITING_NEXT_COMMAND.put(id, TRACK);
        return new CommandComplete(LINK_TO_TRACK, message.chat().id());
    }

    @Override
    protected boolean notValid(Message message) {
        return messageTextNull(message) || !message.text().equals("/track");
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "/track - start tracking link";
    }

    private static final class LinkTrackCommand extends AbstractCommand {
        private static final String LINK_START_TRACKED = "The link is being tracked.";

        LinkTrackCommand(ScrapperClient scrapperClient) {
            super(scrapperClient, new WrongLinkCommand());
        }

        @SneakyThrows
        @Override
        public CommandComplete applyCommand(Message message) {
            if (notValid(message)) {
                return nextCommand.applyCommand(message);
            }

            long id = message.chat().id();

            AddLinkRequest addLinkRequest = new AddLinkRequest(message.text());
            scrapperClient.startTrackLink(id, addLinkRequest);

            return new CommandComplete(LINK_START_TRACKED, id);
        }

        @Override
        protected boolean notValid(Message message) {
            return false;
        }
    }
}

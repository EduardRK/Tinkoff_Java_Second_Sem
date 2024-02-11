package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import java.net.URI;
import java.net.URISyntaxException;

public final class LinkTrackCommand extends AbstractCommand {
    private static final String LINK_START_TRACK = "Link is being tracked";
    private static final String TRACK_ERROR = "Tracking error.";
    private static final String LINK_ALREADY_TRACK = "Link is already being tracked.";

    public LinkTrackCommand(Message message, Command nextCommand) {
        super(message, nextCommand);
    }

    public LinkTrackCommand(Message message) {
        this(message, new WrongLinkCommand(message));
    }

    @Override
    protected boolean valid() {
        if (!notNull()) {
            return false;
        }

        try {
            URI uri = new URI(message.text());
        } catch (URISyntaxException e) {
            return false;
        }
        return true;
    }

    @Override
    public CommandComplete applyCommand() {
        if (!valid()) {
            return next.applyCommand();
        }
        /*
            TODO: start track link
                if DB doesn't answer - TRACK_ERROR
                if link already track - do nothing
                else - link start track
                ???
                profit
         */
        return new CommandComplete(LINK_START_TRACK, message.chat().id());
    }
}

package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import java.net.URI;

public final class TrackCommand extends AbstractCommand implements Script {
    private static final String LINK_SHOULD_TRACK = "Which link should I track?";
    private static final String LINKS_TRACKED = "Link is being tracked.";
    private static final String LINKS_ALREADY_TRACKED = "Link is already being tracked.";
    private static final String ADD_ERROR = "Add error.";

    public TrackCommand(Message message, Command nextCommand) {
        super(message, nextCommand);
    }

    public TrackCommand(Message message) {
        this(message, new EmptyCommand(message));
    }

    @Override
    protected boolean valid() {
        return notNull() && message.text().equals("/track");
    }

    @Override
    public CommandComplete applyCommand() {
        if (!valid()) {
            return next.applyCommand();
        } else {

        /*
            TODO: Logic with DB
         */

            return new CommandComplete(LINK_SHOULD_TRACK, message.chat().id(), true);
        }
    }

    private void startTrack(URI uri) {
        /*
            TODO: Start track in DB
                ???
                profit
         */
    }
    /*
        TODO: Methods check in DB
     */
}

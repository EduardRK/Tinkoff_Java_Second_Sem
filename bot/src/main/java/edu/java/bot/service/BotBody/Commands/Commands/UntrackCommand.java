package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import java.net.URI;

public final class UntrackCommand extends AbstractCommand implements Script {
    private static final String LINK_SHOULD_UNTRACK = "Which link should I untrack?";
    private static final String UNTRACK_SUCCESSFULLY = "Untrack successfully";
    private static final String REMOVE_ERROR = "Remove error";

    public UntrackCommand(Message message, Command nextCommand) {
        super(message, nextCommand);
    }

    public UntrackCommand(Message message) {
        this(message, new EmptyCommand(message));
    }

    @Override
    protected boolean valid() {
        return notNull() && message.text().equals("/untrack");
    }

    @Override
    public CommandComplete applyCommand() {
        if (!valid()) {
            return next.applyCommand();
        } else {


        /*
            TODO: Logic with DB
         */

            return new CommandComplete(LINK_SHOULD_UNTRACK, message.chat().id(), true);
        }
    }

    private void untrack(URI uri) {
        /*
            TODO: stop track in DB
                ???
                profit
         */
    }
    /*
        TODO: Methods check in DB
     */
}

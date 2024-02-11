package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import java.net.URI;
import java.net.URISyntaxException;

public final class LinkUntrackCommand extends AbstractCommand {
    private static final String LINK_UNTRACK = "Link is being untracked";
    private static final String UNTRACK_ERROR = "Untracking error.";

    public LinkUntrackCommand(Message message, Command nextCommand) {
        super(message, nextCommand);
    }

    public LinkUntrackCommand(Message message) {
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
                if DB doesn't answer - UNTRACK_ERROR
                else - link untrack
                ???
                profit
         */
        return new CommandComplete(LINK_UNTRACK, message.chat().id());
    }
}

package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;

public final class ListCommand extends AbstractCommand {
    private static final String NOTHING_TRACK = "Nothing to track.";

    public ListCommand(Message message, Command nextCommand) {
        super(message, nextCommand);
    }

    public ListCommand(Message message) {
        this(message, new EmptyCommand(message));
    }

    @Override
    protected boolean valid() {
        return notNull() && message.text().equals("/list");
    }

    @Override
    public CommandComplete applyCommand() {
        if (!valid()) {
            return next.applyCommand();
        } else {
            return new CommandComplete(trackedLinks(), message.chat().id());
        }
    }

    private String trackedLinks() {
        /*
            TODO: get list from DB
                create new string with links
                ???
                profit
         */
        return NOTHING_TRACK;
    }
}

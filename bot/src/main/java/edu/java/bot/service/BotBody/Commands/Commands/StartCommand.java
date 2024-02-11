package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;

public final class StartCommand extends AbstractCommand {
    private static final String USER_REGISTERED = "User registered.";
    private static final String USER_ALREADY_REGISTERED = "User is already registered.";
    private static final String USER_NOT_REGISTERED = "User not registered.";

    public StartCommand(Message message, Command nextCommand) {
        super(message, nextCommand);
    }

    public StartCommand(Message message) {
        this(message, new EmptyCommand(message));
    }

    @Override
    protected boolean valid() {
        return notNull() && message.text().equals("/start");
    }

    @Override
    public CommandComplete applyCommand() {
        if (!valid()) {
            return next.applyCommand();
        } else {
            try {
                return new CommandComplete(
                    userExists() ? USER_ALREADY_REGISTERED : USER_REGISTERED,
                    message.chat().id()
                );
            } catch (Exception e) {
                return new CommandComplete(USER_NOT_REGISTERED, message.chat().id());
            }
        }
    }

    private boolean userExists() throws Exception {
        /*
            TODO: find user in DB
                if exists - true;
                if not exists - false;
                if DB doesn't answer - exception;
                ???
                profit
         */
        return false;
    }
}

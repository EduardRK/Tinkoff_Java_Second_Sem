package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.scrapper_client.ScrapperClient;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class StartCommand extends AbstractCommand {
    private static final String ALREADY_REGISTRATION = "The user is already registered.";
    private static final String USER_REGISTERED = "The user has been successfully registered.";
    private static final String USER_NOT_REGISTER = "You are not registered. Use the command /start.";

    public StartCommand(ScrapperClient scrapperClient, Command next) {
        super(scrapperClient, next);
    }

    public StartCommand(ScrapperClient scrapperClient) {
        this(scrapperClient, new EmptyCommand());
    }

    public StartCommand() {
        this(null, null);
    }

    @Override
    public CommandComplete applyCommand(Message message) {
        if (notValid(message)) {
//            if (userRegistered()) {
//                return new CommandComplete(
//                    USER_NOT_REGISTER,
//                    message.chat().id()
//                );
//            }

            return nextCommand.applyCommand(message);
        }

        long id = message.chat().id();

        scrapperClient.registerChat(id);
        return new CommandComplete(USER_REGISTERED, id);
    }

    @Override
    protected boolean notValid(Message message) {
        return messageTextNull(message) || !message.text().equals("/start");
    }

//    private boolean userRegistered() {
//        return false;
//    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "/start - user registration";
    }
}

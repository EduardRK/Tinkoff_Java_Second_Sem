package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.scrapper_client.ScrapperClient;
import edu.java.exceptions.BadRequestException.BadRequestException;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public final class StartCommand extends AbstractCommand {
    private static final String USER_ALREADY_REGISTRATION = "The user is already registered.";
    private static final String USER_REGISTERED = "The user has been successfully registered.";

    public StartCommand(ScrapperClient scrapperClient, Command next) {
        super(scrapperClient, next);
    }

    public StartCommand(ScrapperClient scrapperClient) {
        super(scrapperClient, new EmptyCommand());
    }

    public StartCommand() {
        super(null, null);
    }

    @Override
    public CommandComplete applyCommand(Message message) {
        if (notValid(message)) {
            nextCommand.applyCommand(message);
        }

        long id = message.chat().id();

        Optional<CommandComplete> commandComplete = scrapperClient.registerChat(id)
            .onErrorResume(BadRequestException.class, e -> Mono.empty())
            .then(Mono.just(new CommandComplete(USER_REGISTERED, id)))
            .blockOptional();

        return commandComplete.orElseGet(() -> new CommandComplete(USER_ALREADY_REGISTRATION, id));
    }

    private void tryRegister(long id) {
        scrapperClient.registerChat(id)
            .onErrorResume(BadRequestException.class, e -> Mono.empty())
            .block();
    }

    @Override
    protected boolean notValid(Message message) {
        return messageTextNull(message) || !message.text().equals("/start");
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "/start - user registration";
    }
}

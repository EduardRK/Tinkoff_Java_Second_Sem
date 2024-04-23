package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.scrapper_client.ApiErrorException;
import edu.java.bot.service.scrapper_client.ScrapperClient;
import edu.java.responses.ApiErrorResponse;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

public final class DeleteCommand extends AbstractCommand {
    private static final String USER_DELETED = "The user has been successfully deleted";
    private static final String SOMETHING_WRONG = "Something went wrong, please wait";

    public DeleteCommand(ScrapperClient scrapperClient, Command command) {
        super(scrapperClient, command);
    }

    public DeleteCommand(ScrapperClient scrapperClient) {
        super(scrapperClient, new EmptyCommand());
    }

    public DeleteCommand() {
        super(null, null);
    }

    @Override
    public CommandComplete applyCommand(Message message) {
        if (notValid(message)) {
            return nextCommand.applyCommand(message);
        }

        long id = message.chat().id();

        Optional<CommandComplete> commandComplete = scrapperClient.deleteChat(id)
            .then(Mono.just(new CommandComplete(USER_DELETED, id)))
            .onErrorResume(ApiErrorException.class, e -> {
                ApiErrorResponse apiErrorResponse = e.apiErrorResponse();
                return Mono.just(new CommandComplete(apiErrorResponse.description(), id));
            })
            .blockOptional();

        return commandComplete.orElseGet(() -> new CommandComplete(SOMETHING_WRONG, id));
    }

    @Override
    protected boolean notValid(Message message) {
        return messageTextNull(message) || !message.text().equals("/delete");
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "/delete - user unregister";
    }
}

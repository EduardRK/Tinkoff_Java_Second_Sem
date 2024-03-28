package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.bot_body.commands.WrongLinkCommand;
import edu.java.bot.service.scrapper_client.ScrapperClient;
import edu.java.requests.RemoveLinkRequest;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class UntrackCommand extends AbstractCommand {
    private static final String UNTRACK = "untrack";
    private static final String LINK_TO_UNTRACK = "Which link should I untrack?";

    public UntrackCommand(ScrapperClient scrapperClient, Command command) {
        super(scrapperClient, command);
    }

    public UntrackCommand(ScrapperClient scrapperClient) {
        this(scrapperClient, new EmptyCommand());
    }

    public UntrackCommand() {
        this(null, null);
    }

    @Override
    public CommandComplete applyCommand(Message message) {
        long id = message.chat().id();

        if (WAITING_NEXT_COMMAND.getOrDefault(id, "").equals(UNTRACK)) {
            WAITING_NEXT_COMMAND.remove(id);
            return new LinkUntrackCommand(scrapperClient).applyCommand(message);
        }

        if (notValid(message)) {
            return nextCommand.applyCommand(message);
        }

        WAITING_NEXT_COMMAND.put(id, UNTRACK);
        return new CommandComplete(LINK_TO_UNTRACK, message.chat().id());
    }

    @Override
    protected boolean notValid(Message message) {
        return messageTextNull(message) || !message.text().equals("/untrack");
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "/untrack - stop tracking link";
    }

    private static final class LinkUntrackCommand extends AbstractCommand {
        private static final String LINK_NOT_TRACKED = "Link is not tracked.";
        private static final String LINK_STOP_TRACKED = "The link is no longer being tracked.";

        LinkUntrackCommand(ScrapperClient scrapperClient) {
            super(scrapperClient, new WrongLinkCommand());
        }

        @SneakyThrows
        @Override
        public CommandComplete applyCommand(Message message) {
            if (notValid(message)) {
                return nextCommand.applyCommand(message);
            }

            long id = message.chat().id();

            RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest(message.text());
            scrapperClient.stopTrackLink(id, removeLinkRequest);

            return new CommandComplete(LINK_STOP_TRACKED, id);
        }

        @Override
        protected boolean notValid(Message message) {
            return false;
        }
    }
}

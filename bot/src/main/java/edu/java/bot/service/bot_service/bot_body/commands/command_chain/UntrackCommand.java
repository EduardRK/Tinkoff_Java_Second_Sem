package edu.java.bot.service.bot_service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.bot_service.bot_body.commands.Command;
import edu.java.bot.service.bot_service.bot_body.commands.CommandComplete;
import edu.java.bot.service.bot_service.bot_body.commands.EmptyCommand;
import edu.java.bot.service.bot_service.bot_body.commands.WrongLinkCommand;
import edu.java.bot.service.bot_service.bot_body.data_base.InMemoryDataBase;
import edu.java.bot.service.bot_service.bot_body.data_classes.Link;
import java.net.URISyntaxException;
import java.util.Set;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class UntrackCommand extends AbstractCommand {
    private static final String UNTRACK = "untrack";
    private static final String LINK_TO_UNTRACK = "Which link should I untrack?";

    public UntrackCommand(InMemoryDataBase<Integer, Link> inMemoryDataBase, Message message, Command command) {
        super(inMemoryDataBase, message, command);
    }

    public UntrackCommand(InMemoryDataBase<Integer, Link> inMemoryDataBase, Message message) {
        this(inMemoryDataBase, message, new EmptyCommand(message));
    }

    public UntrackCommand() {
        this(null, null);
    }

    @Override
    public CommandComplete applyCommand() {
        int id = message.chat().id().intValue();

        if (inMemoryDataBase.waitingNextCommand().getOrDefault(id, "").equals(UNTRACK)) {
            inMemoryDataBase.waitingNextCommand().remove(id);
            return new LinkUntrackCommand(inMemoryDataBase, message).applyCommand();
        }

        if (notValid()) {
            return nextCommand.applyCommand();
        }

        inMemoryDataBase.waitingNextCommand().put(id, UNTRACK);
        return new CommandComplete(LINK_TO_UNTRACK, message.chat().id().intValue());
    }

    @Override
    protected boolean notValid() {
        return messageTextNull() || !message.text().equals("/untrack");
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "/untrack - stop tracking link";
    }

    private static final class LinkUntrackCommand extends AbstractCommand {
        private static final String LINK_NOT_TRACKED = "Link is not tracked.";
        private static final String LINK_STOP_TRACKED = "The link is no longer being tracked.";

        LinkUntrackCommand(InMemoryDataBase<Integer, Link> inMemoryDataBase, Message message) {
            super(inMemoryDataBase, message, new WrongLinkCommand(message));
        }

        @SneakyThrows @Override
        public CommandComplete applyCommand() {
            if (notValid()) {
                return nextCommand.applyCommand();
            }

            int id = message.chat().id().intValue();

            Link link = new Link(message.text());
            Set<Link> linkSet = inMemoryDataBase.dataBase().get(id);

            if (!linkSet.contains(link)) {
                return new CommandComplete(LINK_NOT_TRACKED, id);
            }

            linkSet.remove(link);
            inMemoryDataBase.dataBase().put(id, linkSet);

            return new CommandComplete(LINK_STOP_TRACKED, id);
        }

        @Override
        protected boolean notValid() {
            try {
                new Link(message.text());
            } catch (URISyntaxException e) {
                return true;
            }
            return false;
        }
    }
}

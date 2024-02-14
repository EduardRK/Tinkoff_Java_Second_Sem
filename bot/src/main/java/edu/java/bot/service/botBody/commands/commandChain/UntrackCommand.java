package edu.java.bot.service.botBody.commands.commandChain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.botBody.commands.Command;
import edu.java.bot.service.botBody.commands.CommandComplete;
import edu.java.bot.service.botBody.commands.EmptyCommand;
import edu.java.bot.service.botBody.commands.WrongLinkCommand;
import edu.java.bot.service.botBody.dataClasses.Link;
import edu.java.bot.service.dataBase.InMemoryDataBase;
import java.net.URISyntaxException;
import java.util.Set;
import lombok.SneakyThrows;

public final class UntrackCommand extends AbstractCommand {
    private static final String UNTRACK = "untrack";
    private static final String LINK_TO_UNTRACK = "Which link should I untrack?";

    public UntrackCommand(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message, Command command) {
        super(inMemoryDataBase, message, command);
    }

    public UntrackCommand(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message) {
        this(inMemoryDataBase, message, new EmptyCommand(message));
    }

    public UntrackCommand() {
        this(null, null);
    }

    @Override
    public CommandComplete applyCommand() {
        long id = message.chat().id();

        if (inMemoryDataBase.waitingNextCommand().getOrDefault(id, "").equals(UNTRACK)) {
            inMemoryDataBase.waitingNextCommand().remove(id);
            return new LinkUntrackCommand(inMemoryDataBase, message).applyCommand();
        }

        if (!valid()) {
            return nextCommand.applyCommand();
        }

        inMemoryDataBase.waitingNextCommand().put(id, UNTRACK);
        return new CommandComplete(LINK_TO_UNTRACK, message.chat().id());
    }

    @Override
    protected boolean valid() {
        return messageTextNotNull() && message.text().equals("/untrack");
    }

    @Override
    public String toString() {
        return "/untrack - stop tracking link";
    }

    private static class LinkUntrackCommand extends AbstractCommand {
        private static final String LINK_NOT_TRACKED = "Link is not tracked.";
        private static final String LINK_STOP_TRACKED = "The link is no longer being tracked.";

        LinkUntrackCommand(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message) {
            super(inMemoryDataBase, message, new WrongLinkCommand(message));
        }

        @SneakyThrows @Override
        public CommandComplete applyCommand() {
            if (!valid()) {
                return nextCommand.applyCommand();
            }

            long id = message.chat().id();

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
        protected boolean valid() {
            try {
                new Link(message.text());
            } catch (URISyntaxException e) {
                return false;
            }
            return true;
        }
    }
}

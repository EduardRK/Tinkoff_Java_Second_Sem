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

public final class TrackCommand extends AbstractCommand {
    private static final String TRACK = "track";
    private static final String LINK_TO_TRACK = "Which link should I track?";

    public TrackCommand(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message, Command next) {
        super(inMemoryDataBase, message, next);
    }

    public TrackCommand(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message) {
        this(inMemoryDataBase, message, new EmptyCommand(message));
    }

    public TrackCommand() {
        this(null, null);
    }

    @Override
    public CommandComplete applyCommand() {
        long id = message.chat().id();

        if (inMemoryDataBase.waitingNextCommand().getOrDefault(id, "").equals(TRACK)) {
            inMemoryDataBase.waitingNextCommand().remove(id);
            return new LinkTrackCommand(inMemoryDataBase, message).applyCommand();
        }

        if (!valid()) {
            return nextCommand.applyCommand();
        }

        inMemoryDataBase.waitingNextCommand().put(id, TRACK);
        return new CommandComplete(LINK_TO_TRACK, message.chat().id());
    }

    @Override
    protected boolean valid() {
        return messageTextNotNull() && message.text().equals("/track");
    }

    @Override
    public String toString() {
        return "/track - start tracking link";
    }

    private static class LinkTrackCommand extends AbstractCommand {
        private static final String LINK_START_TRACKED = "The link is being tracked.";

        LinkTrackCommand(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message) {
            super(inMemoryDataBase, message, new WrongLinkCommand(message));
        }

        @SneakyThrows
        @Override
        public CommandComplete applyCommand() {
            if (!valid()) {
                return nextCommand.applyCommand();
            }

            long id = message.chat().id();

            Link link = new Link(message.text());
            Set<Link> linkSet = inMemoryDataBase.dataBase().get(id);
            linkSet.add(link);
            inMemoryDataBase.dataBase().put(id, linkSet);

            return new CommandComplete(LINK_START_TRACKED, id);
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

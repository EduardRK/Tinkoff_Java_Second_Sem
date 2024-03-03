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

public final class TrackCommand extends AbstractCommand {
    private static final String TRACK = "track";
    private static final String LINK_TO_TRACK = "Which link should I track?";

    public TrackCommand(InMemoryDataBase<Integer, Link> inMemoryDataBase, Message message, Command next) {
        super(inMemoryDataBase, message, next);
    }

    public TrackCommand(InMemoryDataBase<Integer, Link> inMemoryDataBase, Message message) {
        this(inMemoryDataBase, message, new EmptyCommand(message));
    }

    public TrackCommand() {
        this(null, null);
    }

    @Override
    public CommandComplete applyCommand() {
        int id = message.chat().id().intValue();

        if (inMemoryDataBase.waitingNextCommand().getOrDefault(id, "").equals(TRACK)) {
            inMemoryDataBase.waitingNextCommand().remove(id);
            return new LinkTrackCommand(inMemoryDataBase, message).applyCommand();
        }

        if (notValid()) {
            return nextCommand.applyCommand();
        }

        inMemoryDataBase.waitingNextCommand().put(id, TRACK);
        return new CommandComplete(LINK_TO_TRACK, message.chat().id().intValue());
    }

    @Override
    protected boolean notValid() {
        return messageTextNull() || !message.text().equals("/track");
    }

    @Contract(pure = true)
    @Override
    public @NotNull String toString() {
        return "/track - start tracking link";
    }

    private static final class LinkTrackCommand extends AbstractCommand {
        private static final String LINK_START_TRACKED = "The link is being tracked.";

        LinkTrackCommand(InMemoryDataBase<Integer, Link> inMemoryDataBase, Message message) {
            super(inMemoryDataBase, message, new WrongLinkCommand(message));
        }

        @SneakyThrows
        @Override
        public CommandComplete applyCommand() {
            if (notValid()) {
                return nextCommand.applyCommand();
            }

            int id = message.chat().id().intValue();

            Link link = new Link(message.text());
            Set<Link> linkSet = inMemoryDataBase.dataBase().get(id);
            linkSet.add(link);
            inMemoryDataBase.dataBase().put(id, linkSet);

            return new CommandComplete(LINK_START_TRACKED, id);
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

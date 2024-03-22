package edu.java.bot.service.bot_body.commands.command_chain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.domain.InMemoryDataBase;
import edu.java.bot.service.bot_body.commands.Command;
import edu.java.bot.service.bot_body.data_classes.Link;

public abstract class AbstractCommand implements Command {
    protected final InMemoryDataBase<Long, Link> inMemoryDataBase;
    protected final Message message;
    protected final Command nextCommand;

    protected AbstractCommand(InMemoryDataBase<Long, Link> inMemoryDataBase, Message message, Command command) {
        this.inMemoryDataBase = inMemoryDataBase;
        this.message = message;
        this.nextCommand = command;
    }

    protected abstract boolean notValid();

    protected boolean messageTextNull() {
        return message.text() == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractCommand that)) {
            return false;
        }

        return message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return message.hashCode();
    }
}

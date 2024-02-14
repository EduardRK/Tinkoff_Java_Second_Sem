package edu.java.bot.service.botBody.commands.commandChain;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.botBody.commands.Command;
import edu.java.bot.service.botBody.dataClasses.Link;
import edu.java.bot.service.dataBase.InMemoryDataBase;

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

package edu.java.bot.service.BotBody.Commands.Commands;

import com.pengrad.telegrambot.model.Message;

public abstract class AbstractCommand implements Command {
    protected final Message message;
    protected final Command next;

    protected AbstractCommand(Message message, Command nextCommand) {
        this.message = message;
        this.next = nextCommand;
    }

    protected abstract boolean valid();

    protected boolean notNull() {
        return message.text() != null;
    }

    @Override
    public String toString() {
        return "Command{"
            + "message="
            + message
            + "}";
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

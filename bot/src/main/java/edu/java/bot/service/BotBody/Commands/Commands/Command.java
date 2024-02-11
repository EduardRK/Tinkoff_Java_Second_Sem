package edu.java.bot.service.BotBody.Commands.Commands;

import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;

public interface Command {
    CommandComplete applyCommand();
}

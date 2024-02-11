package edu.java.bot.service.BotBody.Commands.Chains;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Commands.HelpCommand;
import edu.java.bot.service.BotBody.Commands.Commands.ListCommand;
import edu.java.bot.service.BotBody.Commands.Commands.StartCommand;
import edu.java.bot.service.BotBody.Commands.Commands.TrackCommand;
import edu.java.bot.service.BotBody.Commands.Commands.UntrackCommand;

public final class CommandChain extends AbstractCommandChain {
    public CommandChain(Message message) {
        super(new StartCommand(
                message,
                new HelpCommand(
                    message,
                    new TrackCommand(
                        message,
                        new UntrackCommand(
                            message,
                            new ListCommand(
                                message
                            )
                        )
                    )
                )
            )
        );
    }
}

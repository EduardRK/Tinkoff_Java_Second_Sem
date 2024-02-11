package edu.java.bot.service.BotBody.Commands.Chains;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Commands.LinkUntrackCommand;

public final class LinkUntrackCommandChain extends AbstractCommandChain {
    public LinkUntrackCommandChain(Message message) {
        super(new LinkUntrackCommand(
                message
            )
        );
    }
}

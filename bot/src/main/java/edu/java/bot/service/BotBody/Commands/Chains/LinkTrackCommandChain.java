package edu.java.bot.service.BotBody.Commands.Chains;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Commands.LinkTrackCommand;

public final class LinkTrackCommandChain extends AbstractCommandChain {
    public LinkTrackCommandChain(Message message) {
        super(new LinkTrackCommand(
                message
            )
        );
    }
}

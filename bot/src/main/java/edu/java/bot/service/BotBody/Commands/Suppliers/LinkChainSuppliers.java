package edu.java.bot.service.BotBody.Commands.Suppliers;

import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Chains.Chain;
import edu.java.bot.service.BotBody.Commands.Chains.LinkTrackCommandChain;
import edu.java.bot.service.BotBody.Commands.Chains.LinkUntrackCommandChain;
import edu.java.bot.service.BotBody.Commands.Commands.WrongLinkCommand;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import edu.java.bot.service.BotBody.Messages.MessageResponse;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

public final class LinkChainSuppliers implements Supplier<Chain<CommandComplete>> {
    private final Message message;
    private final MessageResponse messageResponse;

    public LinkChainSuppliers(Message message, MessageResponse messageResponse) {
        this.message = message;
        this.messageResponse = messageResponse;
    }

    @Override
    public @NotNull Chain<CommandComplete> get() {
        if (messageResponse.message().text().equals("Which link should I track?")) {
            return new LinkTrackCommandChain(message);
        } else if (messageResponse.message().text().equals("Which link should I untrack?")) {
            return new LinkUntrackCommandChain(message);
        } else {
            return () -> new WrongLinkCommand(message).applyCommand();
        }
    }
}

package edu.java.bot.service.BotBody.Commands.Chains;

import edu.java.bot.service.BotBody.Commands.Commands.Command;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;

public abstract class AbstractCommandChain implements Chain<CommandComplete> {
    private final Command chain;

    protected AbstractCommandChain(Command chain) {
        this.chain = chain;
    }

    @Override
    public CommandComplete start() {
        return chain.applyCommand();
    }
}

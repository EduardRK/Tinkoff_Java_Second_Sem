package edu.java.bot.service.BotBody.TelegramBots;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.BotBody.Commands.Chains.Chain;
import edu.java.bot.service.BotBody.Commands.Chains.CommandChain;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import edu.java.bot.service.BotBody.Commands.Suppliers.LinkChainSuppliers;
import edu.java.bot.service.BotBody.Generators.Generator;
import edu.java.bot.service.BotBody.Generators.SendMessageGenerator;
import edu.java.bot.service.BotBody.Handlers.Handler;
import edu.java.bot.service.BotBody.Handlers.UpdateHandler;
import edu.java.bot.service.BotBody.Messages.MessageResponse;
import java.util.List;
import java.util.function.Supplier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

public final class NotificationsCollectorBot implements Bot {
    private static final Generator<CommandComplete, SendMessage> GENERATOR = new SendMessageGenerator();
    private static final Handler<Update, Message> HANDLER = new UpdateHandler();
    private final TelegramBot telegramBot;
    private boolean nextExpected = false;
    private MessageResponse messageResponse;

    @Autowired
    public NotificationsCollectorBot(@NotNull ApplicationConfig applicationConfig) {
        this.telegramBot = new TelegramBot.Builder(applicationConfig.telegramToken()).debug().build();
    }

    @Override
    public int process(@NotNull List<Update> updates) {
        for (Update update : updates) {
            HANDLER.put(update);
            if (nextExpected) {
                Supplier<Chain<CommandComplete>> supplier = new LinkChainSuppliers(
                    HANDLER.get(),
                    messageResponse
                );

                Chain<CommandComplete> commandCompleteChain = supplier.get();
                CommandComplete commandComplete = commandCompleteChain.start();

                messageResponse = sendMessage(GENERATOR.next(commandComplete));
                nextExpected = commandComplete.nextExpected();
            } else {
                Chain<CommandComplete> chain = new CommandChain(HANDLER.get());
                CommandComplete commandComplete = chain.start();

                messageResponse = sendMessage(GENERATOR.next(commandComplete));
                nextExpected = commandComplete.nextExpected();
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {
        telegramBot.removeGetUpdatesListener();
    }

    @Override
    public void start() {
        telegramBot.setUpdatesListener(this);
    }

    @Contract("_ -> new") @Override
    public @NotNull MessageResponse sendMessage(SendMessage sendMessage) {
        return new MessageResponse(telegramBot.execute(sendMessage));
    }
}

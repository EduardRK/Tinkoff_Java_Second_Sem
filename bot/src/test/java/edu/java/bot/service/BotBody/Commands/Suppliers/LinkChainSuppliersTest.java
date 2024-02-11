package edu.java.bot.service.BotBody.Commands.Suppliers;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import edu.java.bot.service.BotBody.Commands.Chains.Chain;
import edu.java.bot.service.BotBody.Commands.Completes.CommandComplete;
import edu.java.bot.service.BotBody.Messages.MessageResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class LinkChainSuppliersTest {
    @ParameterizedTest
    @CsvSource(value = {
        "https://stackoverflow.com/, Which link should I track?, Link is being tracked, false",
        "https://stackoverflow.com/, Which link should I untrack?, Link is being untracked, false",
        "Other link, Which link should I track?, Wrong link., false",
        "Other link, Which link should I untrack?, Wrong link., false",
    })
    void get(String link, String response, String answer, boolean nextExpected) {
        Message linkMessage = Mockito.mock(Message.class);
        Message responseMessage = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        Mockito.when(linkMessage.text()).thenReturn(link);
        Mockito.when(responseMessage.text()).thenReturn(response);
        Mockito.when(chat.id()).thenReturn(123L);
        Mockito.when(linkMessage.chat()).thenReturn(chat);

        LinkChainSuppliers linkChainSuppliers = new LinkChainSuppliers(
            linkMessage,
            new MessageResponse(responseMessage)
        );

        Chain<CommandComplete> chain = linkChainSuppliers.get();

        Assertions.assertEquals(
            new CommandComplete(answer, 123L, nextExpected),
            chain.start()
        );
    }
}

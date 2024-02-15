package edu.java.bot.service.botBody.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandCompleteTest {

    @Test
    void message() {
        long id = 123L;
        String string = "Some text for user";
        CommandComplete commandComplete = new CommandComplete(string, id);

        Assertions.assertEquals(
            string,
            commandComplete.message()
        );
    }

    @Test
    void id() {
        long id = 123L;
        String string = "Some text for user";
        CommandComplete commandComplete = new CommandComplete(string, id);

        Assertions.assertEquals(
            id,
            commandComplete.id()
        );
    }
}

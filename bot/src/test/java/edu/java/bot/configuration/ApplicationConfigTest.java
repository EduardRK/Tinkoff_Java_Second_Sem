package edu.java.bot.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApplicationConfigTest {

    @Test
    void telegramToken() {
        ApplicationConfig applicationConfig = new ApplicationConfig("Some token");

        Assertions.assertNotNull(applicationConfig.telegramToken());
        Assertions.assertEquals("Some token", applicationConfig.telegramToken());
    }
}

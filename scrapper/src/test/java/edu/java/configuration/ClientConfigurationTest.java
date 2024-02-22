package edu.java.configuration;

import edu.java.shadulers.LinkUpdaterScheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClientConfigurationTest {

    @Test
    void clientChain() {
        Assertions.assertDoesNotThrow(() -> {
            new ClientConfiguration().clientChain();
        });
    }

    @Test
    void linkUpdateScheduler() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        LinkUpdaterScheduler linkUpdaterScheduler = new LinkUpdaterScheduler();

        Assertions.assertEquals(
            linkUpdaterScheduler,
            clientConfiguration.linkUpdateScheduler()
        );
    }
}

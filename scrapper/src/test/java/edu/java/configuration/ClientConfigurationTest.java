package edu.java.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClientConfigurationTest {

    @Test
    void clientChain() {
        Assertions.assertDoesNotThrow(() -> {
            new ClientConfiguration().clientChain();
        });
    }
}

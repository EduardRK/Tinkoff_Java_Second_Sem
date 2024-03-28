package edu.java.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClientChainConfigTest {

    @Test
    void clientChain() {
        Assertions.assertDoesNotThrow(() -> {
            new ClientConfig().clientChain();
        });
    }
}

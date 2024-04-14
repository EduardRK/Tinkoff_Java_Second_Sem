package edu.java.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.util.retry.Retry;
import java.time.Duration;

class ClientChainConfigTest {
    Retry retry = Retry.fixedDelay(1, Duration.ofSeconds(10));

    @Test
    void clientChain() {
        Assertions.assertDoesNotThrow(() -> {
            new ClientConfig().clientChain(retry);
        });
    }
}

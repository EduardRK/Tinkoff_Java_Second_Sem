package edu.java.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.Duration;

class ApplicationConfigTest {

    @Test
    void scheduler() {
        ApplicationConfig applicationConfig = new ApplicationConfig(
            new ApplicationConfig.Scheduler(false, Duration.ZERO, Duration.ZERO)
        );

        Assertions.assertFalse(applicationConfig.scheduler().enable());
        Assertions.assertEquals(
            applicationConfig.scheduler().interval(),
            Duration.ZERO
        );
        Assertions.assertEquals(
            applicationConfig.scheduler().forceCheckDelay(),
            Duration.ZERO
        );
    }
}

package edu.java.configuration;

import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApplicationConfigTest {

    @Test
    void scheduler() {
        ApplicationConfig applicationConfig = new ApplicationConfig(
            new ApplicationConfig.Scheduler(false, Duration.ZERO, Duration.ZERO),
            ApplicationConfig.AccessType.JDBC,
            true
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

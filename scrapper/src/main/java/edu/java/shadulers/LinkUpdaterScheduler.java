package edu.java.shadulers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

public record LinkUpdaterScheduler() implements UpdateScheduler {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        LOGGER.info("Link update");
    }
}

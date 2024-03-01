package edu.java.schedulers;

import org.junit.jupiter.api.Test;

class LinkUpdaterSchedulerTest {

    @Test
    void update() {
        LinkUpdaterScheduler linkUpdaterScheduler = new LinkUpdaterScheduler();
        linkUpdaterScheduler.update();
    }
}

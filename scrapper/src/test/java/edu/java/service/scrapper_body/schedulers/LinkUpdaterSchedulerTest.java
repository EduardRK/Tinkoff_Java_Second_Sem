package edu.java.service.scrapper_body.schedulers;

import edu.java.service.schedulers.LinkUpdaterScheduler;
import org.junit.jupiter.api.Test;

class LinkUpdaterSchedulerTest {

    @Test
    void update() {
        LinkUpdaterScheduler linkUpdaterScheduler = new LinkUpdaterScheduler();
        linkUpdaterScheduler.update();
    }
}

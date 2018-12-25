package com.lemon.spring.component.schedule.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EveryMinuteTask {
    Logger logger= LoggerFactory.getLogger(EveryMinuteTask.class);

    //@Scheduled(fixedRate = 1000L)
    public void task() {
        logger.debug("Working The Schedule");
    }
}

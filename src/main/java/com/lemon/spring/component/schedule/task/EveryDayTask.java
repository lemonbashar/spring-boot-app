package com.lemon.spring.component.schedule.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EveryDayTask {
    private int periodOfDayCountToDeleteInactiveUser = 10;
    private int periodOfDayCountToDeleteOldToken = 20;

    /**
     * Not activated users should be automatically deleted after ${periodOfDayCountToDeleteInactiveUser} days.
     * <p/>
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteInactivatedUserIfTheyAreInactiveAGivenPeriod() {

    }

    /**
     * Persistent Token are used for providing automatic authentication, they should be automatically deleted after
     * ${periodOfDayCountToDeleteOldToken} days.
     * <p/>
     * <p>
     * This is scheduled to get fired everyday, at midnight.
     * </p>
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOldPersistentToken() {

    }
}

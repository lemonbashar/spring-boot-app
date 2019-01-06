package com.lemon.spring.component.schedule.task;

import com.lemon.framework.properties.ApplicationProperties;
import com.lemon.spring.repository.TokenStoreRepository;
import com.lemon.spring.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Date;

@Component
public class EveryDayTask {
    @Inject
    private TokenStoreRepository tokenStoreRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ApplicationProperties applicationProperties;

    /**
     * Not activated users should be automatically deleted after ${periodOfDayCountToDeleteInactiveUser} days.
     * <p/>
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteInactivatedUserIfTheyAreInactiveAGivenPeriod() {
        LocalDate localDate=LocalDate.now().minusDays(applicationProperties.settings.security.deleteAllInactiveUserIfTheyInactiveGivenDays);
        userRepository.deleteAll(userRepository.findAllInactiveAndBeforeUpdatedLastDate(localDate));
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
        LocalDate localDate=LocalDate.now().minusDays(applicationProperties.settings.security.authentication.clearInactiveJwtAfterGivenDays);
        tokenStoreRepository.deleteAll(tokenStoreRepository.findAllBeforeDate(localDate));
    }
}

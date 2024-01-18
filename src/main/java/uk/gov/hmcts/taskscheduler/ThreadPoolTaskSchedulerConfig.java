package uk.gov.hmcts.taskscheduler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
public class ThreadPoolTaskSchedulerConfig {
    
    private static final Integer POOL_SIZE = 10;
    
    private static final Integer AWAIT_TERMINATION_SECONDS = 30;
    
    private static final String THREAD_NAME_PREFIX = "ThreadPoolTaskScheduler";
    
    /** The software update checksum cron. */
    @Value("#{applicationConfiguration.softwareUpdateChecksumCron}")
    private String softwareUpdateChecksumCron;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        // As per applicationContext-task.xml
        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setAwaitTerminationSeconds(AWAIT_TERMINATION_SECONDS);
        threadPoolTaskScheduler.setThreadNamePrefix(THREAD_NAME_PREFIX);
        return threadPoolTaskScheduler;
    }
    
    @Bean
    public CronTrigger cronTrigger() {
        return new CronTrigger(softwareUpdateChecksumCron);
    }
    
}

package uk.gov.hmcts.taskscheduler;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ISoftwareUpdateService;

@Component
public class ThreadPoolTaskSchedulerJobs {
    
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    
    @Autowired
    private CronTrigger cronTrigger;
    
    @Autowired
    private ISoftwareUpdateService softwareUpdateService;
    
    @PostConstruct
    public void scheduleRunnableWithCronTrigger() {
        taskScheduler.schedule(new SoftwareUpdateTask("SoftwareUpdateService", softwareUpdateService), cronTrigger);
    }

}

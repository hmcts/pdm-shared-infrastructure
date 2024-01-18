package uk.gov.hmcts.taskscheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.hmcts.pdm.publicdisplay.manager.service.api.ISoftwareUpdateService;

public class SoftwareUpdateTask implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(SoftwareUpdateTask.class);

    private final String message;

    private final ISoftwareUpdateService softwareUpdateService;

    public SoftwareUpdateTask(String message, ISoftwareUpdateService softwareUpdateService) {
        this.message = message;
        this.softwareUpdateService = softwareUpdateService;
    }

    @Override
    public void run() {
        LOG.info(
            "Runnable Task with " + message + " on thread " + Thread.currentThread().getName());
        softwareUpdateService.checksumFiles();
    }

}

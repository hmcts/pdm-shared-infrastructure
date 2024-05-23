package uk.gov.hmcts.quartz;

import org.apache.commons.lang3.ArrayUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.util.Calendar;
import java.util.Properties;

@Configuration
public class QuartzConfig {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzConfig.class);
    /** Quartz Scheduler settings, taken from applicationContext-task.xml **/
    private static final Integer CORE_POOL_SIZE = 10;
    private static final Integer AWAIT_TERMINATION_SECONDS = 30;
    private static final String QUARTZ_JOBSTORE_CLASS = "org.quartz.impl.jdbcjobstore.JobStoreTX";
    private static final String QUARTZ_JOBSTORE_IS_CLUSTERED = "true";
    private static final String QUARTZ_JOBSTORE_MISFIRE_THRESHOLD = "120000";
    private static final String QUARTZ_JOBSTORE_TABLE_PREFIX = "pdda.XHB_DM_QZ_";
    private static final String QUARTZ_JOBSTORE_USE_PROPERTIES = "false";
    private static final String QUARTZ_SCHEDULER_INSTANCE_ID = "AUTO";
    private static final String QUARTZ_SCHEDULER_SKIP_UPDATE_CHECK = "true";
    private static final String QUARTZ_THREAD_EXECUTOR_CLASS =
        "uk.gov.hmcts.pdm.publicdisplay.common.task.LocalTaskExecutorThreadExecutor";
    private static final String QUARTZ_THREAD_POOL_CLASS =
        "uk.gov.hmcts.pdm.publicdisplay.common.task.LocalTaskExecutorThreadPool";
    private static final String QUARTZ_SCHEDULER_INSTANCE_NAME = "ClusteredScheduler";
    private static final String QUARTZ_JOBSTORE_DRIVER_DELEGATE_CLASS =
        "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate";
    private static final String QUARTZ_DATA_SOURCE = "xhibitDataSource";
    private static final String QUARTZ_DATA_SOURCE_DRIVER = "org.postgresql.Driver";
    private static final String DATA_SOURCE = "org.quartz.dataSource.";
    protected static final String DB_USER_NAME = "pdda.db_user_name";
    protected static final String DB_PASSWORD = "pdda.db_password";
    protected static final String DB_HOST = "pdda.db_host";
    protected static final String DB_PORT = "pdda.db_port";
    protected static final String DB_NAME = "pdda.db_name";

    @Autowired
    private Environment env;

    /** The rag status update threads. */
    @Value("#{applicationConfiguration.ragStatusUpdateThreads}")
    private String ragStatusUpdateThreads;

    @Value("${quartz.scheduler.enabled}")
    private Boolean quartzSchedulerEnabled;

    private final ApplicationContext applicationContext;

    public QuartzConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler(Trigger... triggers) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();

        schedulerFactory.setOverwriteExistingJobs(true);
        schedulerFactory.setAutoStartup(quartzSchedulerEnabled);
        schedulerFactory.setTaskExecutor(taskExecutor());
        schedulerFactory.setQuartzProperties(quartzProperties());
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true);

        if (ArrayUtils.isNotEmpty(triggers)) {
            schedulerFactory.setTriggers(triggers);
        }

        return schedulerFactory;
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // As per applicationContext-task.xml
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setAwaitTerminationSeconds(AWAIT_TERMINATION_SECONDS);
        return executor;
    }

    public Properties quartzProperties() {
        Properties properties = new Properties();

        properties.setProperty("org.quartz.jobStore.class", QUARTZ_JOBSTORE_CLASS);
        properties.setProperty("org.quartz.jobStore.isClustered", QUARTZ_JOBSTORE_IS_CLUSTERED);
        properties.setProperty("org.quartz.jobStore.misfireThreshold",
            QUARTZ_JOBSTORE_MISFIRE_THRESHOLD);
        properties.setProperty("org.quartz.jobStore.maxMisfiresToHandleAtATime",
            getMaxMisfiresToHandleAtATime(ragStatusUpdateThreads));
        properties.setProperty("org.quartz.jobStore.tablePrefix", QUARTZ_JOBSTORE_TABLE_PREFIX);
        properties.setProperty("org.quartz.jobStore.useProperties", QUARTZ_JOBSTORE_USE_PROPERTIES);
        properties.setProperty("org.quartz.scheduler.instanceId", QUARTZ_SCHEDULER_INSTANCE_ID);
        properties.setProperty("org.quartz.scheduler.skipUpdateCheck",
            QUARTZ_SCHEDULER_SKIP_UPDATE_CHECK);
        properties.setProperty("org.quartz.threadExecutor.class", QUARTZ_THREAD_EXECUTOR_CLASS);
        properties.setProperty("org.quartz.threadPool.class", QUARTZ_THREAD_POOL_CLASS);
        properties.setProperty("org.quartz.threadPool.threadCount", ragStatusUpdateThreads);
        properties.setProperty("org.quartz.scheduler.instanceName", QUARTZ_SCHEDULER_INSTANCE_NAME);
        properties.setProperty("org.quartz.jobStore.driverDelegateClass",
            QUARTZ_JOBSTORE_DRIVER_DELEGATE_CLASS);
        properties.setProperty("org.quartz.jobStore.dataSource", QUARTZ_DATA_SOURCE);
        properties.setProperty(DATA_SOURCE + QUARTZ_DATA_SOURCE + ".driver",
            QUARTZ_DATA_SOURCE_DRIVER);

        String url = getConnectionUrl();
        properties.setProperty(DATA_SOURCE + QUARTZ_DATA_SOURCE + ".URL", url);
        String username = env.getProperty(DB_USER_NAME);
        properties.setProperty(DATA_SOURCE + QUARTZ_DATA_SOURCE + ".user", username);
        String password = env.getProperty(DB_PASSWORD);
        properties.setProperty(DATA_SOURCE + QUARTZ_DATA_SOURCE + ".password", password);
        return properties;
    }

    private String getConnectionUrl() {
        String host = env.getProperty(DB_HOST);
        String port = env.getProperty(DB_PORT);
        String dbName = env.getProperty(DB_NAME);
        StringBuilder sb = new StringBuilder(100);
        sb.append("jdbc:postgresql://").append(host).append(':').append(port).append('/')
            .append(dbName);
        return sb.toString();
    }

    // As per applicationContext-task.xml
    private String getMaxMisfiresToHandleAtATime(String parseVal) {
        Integer retVal = Integer.parseInt(parseVal) - 2;
        return retVal.toString();
    }

    static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs,
        String triggerName) {
        LOG.debug("createTrigger(jobDetail={}, pollFrequencyMs={}, triggerName={})",
            jobDetail.toString(), pollFrequencyMs, triggerName);

        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setRepeatInterval(pollFrequencyMs);
        factoryBean.setName(triggerName);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        factoryBean.setMisfireInstruction(
            SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);

        return factoryBean;
    }

    static CronTriggerFactoryBean createCronTrigger(JobDetail jobDetail, String cronExpression,
        String triggerName) {
        LOG.debug("createCronTrigger(jobDetail={}, cronExpression={}, triggerName={})",
            jobDetail.toString(), cronExpression, triggerName);

        // To fix a known issue with time-based cron jobs
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setStartTime(calendar.getTime());
        factoryBean.setStartDelay(0L);
        factoryBean.setName(triggerName);
        factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);

        return factoryBean;
    }

    @SuppressWarnings("unchecked")
    static JobDetailFactoryBean createJobDetail(Class jobClass, String jobName) {
        LOG.debug("createJobDetail(jobClass={}, jobName={})", jobClass.getName(), jobName);

        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setName(jobName);
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(true);

        return factoryBean;
    }
}

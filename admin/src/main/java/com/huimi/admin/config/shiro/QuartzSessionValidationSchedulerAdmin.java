package com.huimi.admin.config.shiro;

import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dqw on 2016/5/26.
 */
public class QuartzSessionValidationSchedulerAdmin implements SessionValidationScheduler {
    public static final long DEFAULT_SESSION_VALIDATION_INTERVAL = 3600000L;
    private static final String JOB_NAME = "SessionValidationJobAdmin";
    private static final Logger log = LoggerFactory.getLogger(QuartzSessionValidationSchedulerAdmin.class);
    private Scheduler scheduler;
    private boolean schedulerImplicitlyCreated = false;
    private boolean enabled = false;
    private ValidatingSessionManager sessionManager;
    //一年
    private long sessionValidationInterval = 60 * 60 * 24 * 365;

    public QuartzSessionValidationSchedulerAdmin() {
    }

    public QuartzSessionValidationSchedulerAdmin(ValidatingSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    protected Scheduler getScheduler() throws SchedulerException {
        if (this.scheduler == null) {
            this.scheduler = StdSchedulerFactory.getDefaultScheduler();
            this.schedulerImplicitlyCreated = true;
        }

        return this.scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setSessionManager(ValidatingSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setSessionValidationInterval(long sessionValidationInterval) {
        this.sessionValidationInterval = sessionValidationInterval;
    }

    public void enableSessionValidation() {
        if (log.isDebugEnabled()) {
            log.debug("Scheduling session validation job using Quartz with session validation interval of [" + this.sessionValidationInterval + "]ms...");
        }

        try {
            SimpleTrigger e = new SimpleTrigger(this.getClass().getName(), "DEFAULT", SimpleTrigger.REPEAT_INDEFINITELY, this.sessionValidationInterval);
            JobDetail detail = new JobDetail(JOB_NAME, "DEFAULT", QuartzSessionValidationJob.class);
            detail.getJobDataMap().put("sessionManager", this.sessionManager);
            Scheduler scheduler = this.getScheduler();
            scheduler.scheduleJob(detail, e);
            if (this.schedulerImplicitlyCreated) {
                scheduler.start();
                if (log.isDebugEnabled()) {
                    log.debug("Successfully started implicitly created Quartz Scheduler instance.");
                }
            }

            this.enabled = true;
            if (log.isDebugEnabled()) {
                log.debug("Session validation job successfully scheduled with Quartz.");
            }
        } catch (SchedulerException var4) {
            if (log.isErrorEnabled()) {
                log.error("Error starting the Quartz session validation job.  Session validation may not occur.", var4);
            }
        }

    }

    public void disableSessionValidation() {
        if (log.isDebugEnabled()) {
            log.debug("Stopping Quartz session validation job...");
        }

        Scheduler scheduler;
        try {
            scheduler = this.getScheduler();
            if (scheduler == null) {
                if (log.isWarnEnabled()) {
                    log.warn("getScheduler() method returned a null Quartz scheduler, which is unexpected.  Please check your configuration and/or implementation.  Returning quietly since there is no validation job to remove (scheduler does not exist).");
                }

                return;
            }
        } catch (SchedulerException var11) {
            if (log.isWarnEnabled()) {
                log.warn("Unable to acquire Quartz Scheduler.  Ignoring and returning (already stopped?)", var11);
            }

            return;
        }

        try {
            scheduler.unscheduleJob(JOB_NAME, "DEFAULT");
            if (log.isDebugEnabled()) {
                log.debug("Quartz session validation job stopped successfully.");
            }
        } catch (SchedulerException var10) {
            if (log.isDebugEnabled()) {
                log.debug("Could not cleanly remove SessionValidationJobAdmin from Quartz scheduler.  Ignoring and stopping.", var10);
            }
        }

        this.enabled = false;
        if (this.schedulerImplicitlyCreated) {
            try {
                scheduler.shutdown();
            } catch (SchedulerException var8) {
                if (log.isWarnEnabled()) {
                    log.warn("Unable to cleanly shutdown implicitly created Quartz Scheduler instance.", var8);
                }
            } finally {
                this.setScheduler((Scheduler) null);
                this.schedulerImplicitlyCreated = false;
            }
        }

    }
}

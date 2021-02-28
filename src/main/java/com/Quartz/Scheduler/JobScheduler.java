package com.Quartz.Scheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.LoggerFactory;


import com.Quartz.Jobs.SampleJob3;

public class JobScheduler implements ServletContextListener{
	
	private Scheduler scheduler;
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JobScheduler.class);
	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent ctx) {
		logger.debug("Started");
		JobDetail sampleJob3 = JobBuilder.newJob(SampleJob3.class).withDescription("SAMPLE_JOB3").withIdentity("INSAMPLE_JOB3", "sample_group").build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("TRIG_SAMPLE_JOB3").startNow()
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInMinutes(2).repeatForever())
                .build();
		 try {
	            scheduler = ((StdSchedulerFactory) ctx.getServletContext()
	                    .getAttribute(
	                            QuartzInitializerListener.QUARTZ_FACTORY_KEY))
	                    .getScheduler();
	            scheduler.scheduleJob(sampleJob3, trigger);
	        } catch (SchedulerException e) {
	        	logger.error("Error in scheduling Job"+e);
	        }
		 try {
			 logger.debug((String)scheduler.getContext().get("QuartzTopic"));
		 }
		 catch(Exception e) {
			 logger.error("Error in getting context key : "+e);
		 }
		 logger.debug("Finished");
		 
	}
	
	
}


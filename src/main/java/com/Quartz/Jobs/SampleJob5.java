package com.Quartz.Jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.LoggerFactory;

public class SampleJob5 implements Job{
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SampleJob5.class);
	
	
	public SampleJob5() {
		super();
	}


	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
    		logger.debug("Started Processing");
    		String value=(String)context.getJobDetail().getJobDataMap().get("KEY");
    		logger.debug("JOB VALUE :"+value);
    		value=(String)context.getMergedJobDataMap().get("KEY");
			logger.debug("TRIGGER VALUE :"+value);
			context.getJobDetail().getJobDataMap().put("KEY", "VALUEJOB");
			context.getTrigger().getJobDataMap().put("KEY", "VALUETRIGGER");
    		Thread.sleep(30*1000);
    		context.put("Status","Success");
			logger.debug("Completed Processing");
		}
		catch(Exception e){
			context.put("Status","Failure");
			logger.error("Exception :  " +e.getMessage(),e.fillInStackTrace());
            throw new JobExecutionException(e);
		}
	}

}

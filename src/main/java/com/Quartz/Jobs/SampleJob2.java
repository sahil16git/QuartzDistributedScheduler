package com.Quartz.Jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.LoggerFactory;

public class SampleJob2 implements Job{
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SampleJob2.class);
	
	
	public SampleJob2() {
		super();
	}


	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
    		logger.debug("Started Processing");
    		Thread.sleep(1*60*1000);
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

package com.Quartz.Jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.LoggerFactory;

public class SampleJob4 implements Job{
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SampleJob4.class);
	
	
	public SampleJob4() {
		super();
	}


	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
    		logger.debug("Started Processing");
    		context.put("Status","Success");
    		logger.debug("Completed Processing");
    		throw new Exception();
		}
		catch(Exception e){
			context.put("Status","Failure");
			logger.error("Exception :  " +e.getMessage(),e.fillInStackTrace());
            throw new JobExecutionException(e);
		}
	}

}

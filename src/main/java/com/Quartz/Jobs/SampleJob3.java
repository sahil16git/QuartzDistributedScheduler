package com.Quartz.Jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.LoggerFactory;
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SampleJob3 implements Job{
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SampleJob3.class);
	
	
	public SampleJob3() {
		super();
	}


	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
    		logger.debug("Started Processing");
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

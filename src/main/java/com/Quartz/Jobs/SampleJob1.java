package com.Quartz.Jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.LoggerFactory;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SampleJob1 implements Job{
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SampleJob1.class);
	
	
	public SampleJob1() {
		super();
	}


	public void execute(JobExecutionContext context) throws JobExecutionException {
		try{
    		logger.debug("Started Processing");
    		Thread.sleep(10*1000);
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

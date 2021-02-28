package com.Quartz.Listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.slf4j.LoggerFactory;

public class SampleTriggerListener implements TriggerListener{
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SampleTriggerListener.class);

	public String getName() {
		return "SampleTriggerListener";
	}

	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		logger.debug("trigger : "+trigger);
	}

	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	public void triggerMisfired(Trigger trigger) {
		logger.debug("trigger : "+trigger);
		logger.debug("Job Name : "+trigger.getJobKey().getName());
	}

	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		logger.debug("trigger : "+trigger);
	}

}

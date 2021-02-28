package com.Quartz.Listener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.LoggerFactory;

import com.Quartz.DBConnection.ConnectionManager;

public class SampleJobListener implements JobListener{
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SampleJobListener.class);
	

	public String getName() {
		return "SampleJobListener";
	}

	public void jobExecutionVetoed(JobExecutionContext context) {
		// TODO Auto-generated method stub
		
	}

	public void jobToBeExecuted(JobExecutionContext context) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		try {
			if(context.getJobDetail().getDescription()!=null)
			 {
				logger.debug("Going To Start for Job Name: "+context.getJobDetail().getDescription()+" for context : "+context);
				/*****Fire Time*********************/
				Date startTime = context.getFireTime();
				SimpleDateFormat getTime = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
				logger.debug("starttime : "+getTime.format(startTime));
				/*****Scheduled Start Time*********************/
				Date schdeuledFireTime = context.getScheduledFireTime();
				/* Insert the job details */
				String insertJobDetails= "INSERT INTO TB_JOBS_HISTORY (V_JOB_NAME,"
						+"V_JOB_START_TIME, V_SCHEDULED_FIRE_TIME, V_JOB_STATUS,V_MANAGED_SERVER)VALUES (?,TO_DATE(?,'dd/mon/yyyy hh24:mi:ss'),TO_DATE(?,'dd/mon/yyyy hh24:mi:ss'),?, ?)";
					// establishing the connection
					conn = ConnectionManager.getConnection();
					if (pstmt == null) {
						pstmt = conn.prepareStatement(insertJobDetails);
					}
					pstmt.setString(1, context.getJobDetail().getDescription());
					pstmt.setString(2, getTime.format(startTime));
					pstmt.setString(3, getTime.format(schdeuledFireTime));
					pstmt.setString(4, "Started");
					pstmt.setString(5,System.getProperty("weblogic.Name"));
					pstmt.executeQuery();
			} 
		}
		catch (SQLException e) {
			logger.error("Exception in inserting JobDetails for Start Records :  "+e.getMessage(), e);
		}
		catch(Exception fre){
			logger.error("Error while persisting the job execution details : "+ fre.getMessage(), fre.fillInStackTrace());
		}
		finally {
			ConnectionManager.closeConnection(pstmt, null, conn);
		}
	}

	public void jobWasExecuted(JobExecutionContext context, JobExecutionException ex) {
		logger.debug("context : "+context);
		logger.debug("context.getJobDetail() : "+context.getJobDetail());
		PreparedStatement pstmt = null;
		Connection connDS = null;
		try {
			if (context.getJobDetail().getDescription() != null) {
				String Status = (String) context.get("Status");
				logger.debug("Status : "+Status);
				/*****Start Time*********************/
				Date startTime = context.getFireTime();
				SimpleDateFormat getTime = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
				logger.debug("starttime : "+getTime.format(startTime));
				/***********End Time****************/
				Calendar calEndTime = Calendar.getInstance();
				String endTime = getTime.format(calEndTime.getTime());
				logger.debug("endTime : "+endTime);
				/*********Duration*******************/
				double duration = (calEndTime.getTimeInMillis() - startTime
						.getTime()) / (1000 * 60.0);
				logger.debug("duration : "+duration);
				/************Setting JobDetailBean***************/
				String nextStartTime="";
				String recovered;
				if (context.isRecovering()) {//Recovering job doesn't have a nextStartTime
					nextStartTime=endTime;
					recovered="Y";
				} else {
					if(context.getNextFireTime()!=null)//Failed job doesn't has next fire time
						nextStartTime=getTime.format(context.getNextFireTime());
					recovered ="N";
				}
				logger.debug("nextStartTime : "+nextStartTime);
				logger.debug("recovered : "+recovered);
				String updateJobDetails = "update TB_JOBS_HISTORY set V_JOB_END_TIME=TO_DATE(?,'dd/mon/yyyy hh24:mi:ss') ,"+
				"V_JOB_DURATION=? ,V_NEXT_START_TIME=TO_DATE(?,'dd/mon/yyyy hh24:mi:ss') ,V_JOB_STATUS=? "+
						",V_MANAGED_SERVER=? , V_RECOVERED=? where v_job_name = ? and v_job_start_time = TO_DATE(?,'dd/mon/yyyy hh24:mi:ss')";
				
					// establishing the connection
					connDS = ConnectionManager.getConnection();
					pstmt = connDS.prepareStatement(updateJobDetails);
					pstmt.setString(1, endTime);
					pstmt.setString(2, duration + "");
					pstmt.setString(3, nextStartTime);
					pstmt.setString(4, Status);
					pstmt.setString(5,System.getProperty("weblogic.Name"));
					pstmt.setString(6,recovered);
					pstmt.setString(7, context.getJobDetail().getDescription());
					pstmt.setString(8, getTime.format(startTime));			
					pstmt.executeQuery();
			}
		}
		catch (SQLException e) {
				logger.error("Exception in updating JobDetails for Completed Records :  "+e.getMessage(), e);
		}
		catch (Exception e) {
			logger.error("Error in listener" + e.getMessage(),	e.fillInStackTrace());
		}
		finally {
			ConnectionManager.closeConnection(pstmt, null, connDS);
		}
	}

}

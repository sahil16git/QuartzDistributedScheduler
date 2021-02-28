package com.Quartz.DBConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.LoggerFactory;

import com.Quartz.Jobs.SampleJob1;

public class ConnectionManager {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	
	private static DataSource ds = null;
    private static String dbURL ="jdbc:oracle:thin:@CHDSEZ376625D:1521:orfrcl";
    private static String dbUserName = "FRENGINE";
    private static String dbPassword = "FRENGINE";
    private static String driverName =  "oracle.jdbc.driver.OracleDriver";
    private static String standalone =  "false";
    private static String dataSource =  "distSched";  
	
	public static Connection getConnection(){
    	Connection conn = null;
    	DataSource ds = null;
    	Context ctx = null;
        try {
        	long startTime = Calendar.getInstance().getTimeInMillis();		
        	//Deployed on weblogic
        	if("false".equalsIgnoreCase(standalone)){
        		if(ds==null){
        			 ctx = WeblogicContext.getInitialContext();
        			ds = (DataSource)ctx.lookup(dataSource);
        		} 
        		conn = ds.getConnection();
        	}
        	//StandAlone
        	else {
    			try {
					Class.forName (driverName).newInstance();
				} catch (Exception e) {
		        		logger.error("Driver load failed " + e.getMessage(), e);
				} 
	            conn = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
	    		long endTime = Calendar.getInstance().getTimeInMillis();
        	}

        }
        catch (SQLException e) {
        		logger.error("Database access failed " + e.getMessage(), e);
        } catch (NamingException e) {
			e.printStackTrace();
		}
        
        return conn;
    }
	
	/**
	 * Closes the client connection which is passed as a parameter.
	 *
	 * @param Connection
	 * @throws SQLException 
	 */
	public final void closeConnection(Connection con)
		throws SQLException {

		if (con != null) {
			con.close();
		}

	}
	
	/**
	 * This method closes the db connection,prepared statement and result set
	 * 
	 * @param ps
	 * @param rs
	 * @param conn
	 */
	public static void closeConnection(CallableStatement cs, Connection conn) {
		
		try {
			if (cs != null) {
				cs.close();
			}
		} catch (SQLException sqEx) {
			logger.warn("Unable to close prepared statment" + sqEx.getMessage(), sqEx);
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqEx) {
			logger.warn("Unable to close connection"+ sqEx.getMessage(), sqEx);
		}
	}
	
	public static void closeConnection(PreparedStatement pstmt) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException sqEx) {
            logger.warn("Unable to close prepared statment" + sqEx.getMessage(), sqEx);
        }
        
    }
	
	/**
	 * This method closes the db connection,prepared statement and result set
	 * 
	 * @param ps
	 * @param rs
	 * @param conn
	 */
	public static void closeConnection(PreparedStatement ps, ResultSet rs, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException sqEx) {
			logger.warn("Unable to close result set" + sqEx.getMessage(), sqEx);
		}
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException sqEx) {
			logger.warn("Unable to close prepared statment" + sqEx.getMessage(), sqEx);
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException sqEx) {
			logger.warn("Unable to close connection"+ sqEx.getMessage(), sqEx);
		}
	}
	public static void closeConnection(ResultSet rs,Statement smt,PreparedStatement psmt,CallableStatement csmt,Connection conn) throws SQLException,Exception {
		try {
			if (rs != null)rs.close();
			if (smt != null)smt.close();
			if (psmt != null)psmt.close();
			if (csmt != null)csmt.close();
			if (conn != null)conn.close();
		} catch (SQLException sqex) {
			logger.error("SQLException occured while closing resources" + sqex.getMessage(), sqex);
			throw sqex;
		}catch (Exception ex) {
			logger.error("Exception occured while closing resources" + ex.getMessage(), ex);
			throw ex;
		}
	}
}

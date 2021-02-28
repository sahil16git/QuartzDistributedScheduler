package com.Quartz.Scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Quartz.DBConnection.ConnectionManager;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs=null;
		try {
			String insertJobDetails= "select * from tb_exch_bank_holiday";
				// establishing the connection
				conn = ConnectionManager.getConnection();
				
				if (pstmt == null) {
					pstmt = conn.prepareStatement(insertJobDetails);
				}
				rs=pstmt.executeQuery();
				if(rs.next()){
					System.out.println("Hello");
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
	} 
}



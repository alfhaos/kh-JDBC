package com.kh.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * static 자원을 사용하는 jdbc 공용클래스
 * 
 *
 */
public class JDBCTemplate {
	
	private static String driverClass = "oracle.jdbc.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속 프로토콜 @url:port:sid
	private static String user = "student";
	private static String password = "student";
	
	static {
		
		try {
			// 1. driver class 등록 : 프로그램 실행시 최초 1회만 실행
			
			Class.forName(driverClass);
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static Connection getConnection() {
		// 2. Connection객체 생성(autoCommit false처리)
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		return conn;
	}

	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed())
				conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed())
				conn.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void close(Connection conn) {
		
		
			try {
				if(conn != null && !conn.isClosed()) 
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	public static void close(PreparedStatement pstmt) {
		try {
			if(pstmt != null && !pstmt.isClosed())
				pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed())
				rset.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	
	

}

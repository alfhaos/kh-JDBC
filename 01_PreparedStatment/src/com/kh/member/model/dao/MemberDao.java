package com.kh.member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.kh.member.model.vo.Member;

/**
 * 
 * Data Access Object Class
 * 
 * Database에 접근, 쿼리실행 및 결과를 담당하는 클래스
 * 
 * jdbc api
 * - 구현채 ojdbc8.jar 필수
 * 
 * DML : Connection, PreparedStatement 사용, 결과값 int
 * DQL : Connection, PreparedStatment 사용, 결과값 ResultSet
 * 
 * 
 *
 */
public class MemberDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = "insert into member values(?,?,?,?,?,?,default)";
	String delsql = "delete from member where id = ? ";
	int result = 0;
	
	private String driverClass = "oracle.jdbc.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 접속 프로토콜 @url:port:sid
	private String user = "student";
	private String password = "student";

	public int insertMember(Member member) {
		try {
		// 1. driver class 등록(프로그램 실행시 1회)
		Class.forName("oracle.jdbc.OracleDriver");
		
		// 2. Connection객체 생성(url, user, password) & auto-commit 설정
		conn = DriverManager.getConnection(url, user, password);
		conn.setAutoCommit(false);
		
		
		// 3. PreparedStatment 객체 생성
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, member.getId());
		pstmt.setString(2, member.getName());
		pstmt.setString(3, member.getGender());
		pstmt.setDate(4, member.getBirthday());
		pstmt.setString(5, member.getEmail());
		pstmt.setString(6, member.getAddress());
		
		
		// 4. 쿼리실행(DML : executeUpdate) & 결과값(int) 처리
		
		result = pstmt.executeUpdate();
		
		// 5. 트랜잭션 처리
		if(result > 0) conn.commit();
		else conn.rollback();
		
		}
		
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		// 6. 자원반납 : 생성 역순 반납
		finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return result;
	}

	public int deleteMember(String id) {
		try {
			// 1. driver class 등록(프로그램 실행시 1회)
			Class.forName("oracle.jdbc.OracleDriver");
			
			// 2. Connection객체 생성(url, user, password) & auto-commit 설정
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			
			
			// 3. PreparedStatment 객체 생성
			pstmt = conn.prepareStatement(delsql);
			pstmt.setString(1, id);
			
			
			// 4. 쿼리실행(DML : executeUpdate) & 결과값(int) 처리
			result = pstmt.executeUpdate();
			

			// 5. 트랜잭션 처리
			if(result > 0) conn.commit();
			else conn.rollback();
		}
		
		catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
			
		}
		
		
		// 6. 자원반납 : 생성 역순 반납
		finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return result;
	}
	
	public List<Member> selectAllMember(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member order by reg_date";
		List<Member> list = new ArrayList<>(); //결과 집합 0행인 경우도 null이 아닌 빈 list 객체 반환
		
		// 1. driverClass 등록
		try {
			// 1. driverClass 등록	: 클래스객체 생성

			Class.forName(driverClass);

			// 2. Connection 객체생성
			
			conn = DriverManager.getConnection(url, user, password);
			
			// 3. PreparedStatment 객체 생성(미완성 쿼리전달 & 값대입)
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. 쿼리실행 ResultSet 객체 변환
			// 결과집합이 0행이어도 rset이 null이 아니다.
			
			rset = pstmt.executeQuery();
			
			// 5. ResultSet객체 -> List<Member> 변환
			// rset에 한행씩 접근해서 Member객체 변환 -> list 추가
			while(rset.next()) {
				// 한행(record) -> Member 객체
				String id = rset.getString("id");
				String name = rset.getString("name");
				String gender = rset.getString("gender");
				Date birthday = rset.getDate("birthday");
				String email = rset.getString("email");
				String address = rset.getString("address");
				Timestamp regDate = rset.getTimestamp("reg_Date");
				
				Member member = new Member(id,name,gender,birthday,email,address,regDate);
				list.add(member);
				
				
			}
		
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		finally {
			// 6. 자원 반납(생성역순 : ResultSet -> PreparedStatment - Connection)
			
			try{rset.close();}
			catch(Exception e) {
				e.printStackTrace();
			}
			try{pstmt.close();}
			catch(Exception e) {
				e.printStackTrace();
			}	try{conn.close();}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return list;
		
	}
	
	public Member selectOneMember(String id) {
		
		String sql = "select * from member where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member member = null; // 조회된 결과가 없으면 null을 리턴
		
		
		
			try {
				// 1. driverClass 등록
				Class.forName(driverClass);
				
				// 2. Connection객체 생성
				conn = DriverManager.getConnection(url, user, password);
				// 3. PreparedStatment 객체 생성
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,id);
				
				// 4. 쿼리 실행
				rset = pstmt.executeQuery();
				
				// 5. ResultSet -> Member변환
				if(rset.next()) {
					member = new Member();
					member.setId(rset.getString(1));
					member.setName(rset.getString(2));
					member.setGender(rset.getString(3));
					member.setBirthday(rset.getDate(4));
					member.setEmail(rset.getString(5));
					member.setAddress(rset.getString(6));
					member.setRegDate(rset.getTimestamp(7));
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			finally {
				// 6. 자원 반납(생성역순 : ResultSet -> PreparedStatment - Connection)
				
				try{rset.close();}
				catch(Exception e) {
					e.printStackTrace();
				}
				try{pstmt.close();}
				catch(Exception e) {
					e.printStackTrace();
				}	try{conn.close();}
				catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			
		
		return member;
	}

	public Member selectSearchMember(String shname) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member order by reg_date";
		Member member = null;
		
		
		// 1. driverClass 등록
		try {
			// 1. driverClass 등록	: 클래스객체 생성

			Class.forName(driverClass);

			// 2. Connection 객체생성
			
			conn = DriverManager.getConnection(url, user, password);
			
			// 3. PreparedStatment 객체 생성(미완성 쿼리전달 & 값대입)
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. 쿼리실행 ResultSet 객체 변환
			// 결과집합이 0행이어도 rset이 null이 아니다.
			
			rset = pstmt.executeQuery();
			
			// 5. ResultSet객체 -> List<Member> 변환
			// rset에 한행씩 접근해서 Member객체 변환 -> list 추가
			while(rset.next()) {
				// 한행(record) -> Member 객체
				
				String name = rset.getString("name");
				
				if(name.contains(shname)) {
					String id = rset.getString("id");
					
					String gender = rset.getString("gender");
					Date birthday = rset.getDate("birthday");
					String email = rset.getString("email");
					String address = rset.getString("address");
					Timestamp regDate = rset.getTimestamp("reg_Date");
					
					member = new Member(id,name,gender,birthday,email,address,regDate);
					break;
					
				}
			
				
				
				
				
			}
		
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		finally {
			// 6. 자원 반납(생성역순 : ResultSet -> PreparedStatment - Connection)
			
			try{rset.close();}
			catch(Exception e) {
				e.printStackTrace();
			}
			try{pstmt.close();}
			catch(Exception e) {
				e.printStackTrace();
			}	try{conn.close();}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		return member;
	}

	public HashSet<String> idCheck() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select id from member";
		HashSet<String> idset = new HashSet<String>(); //결과 집합 0행인 경우도 null이 아닌 빈 list 객체 반환
		
		// 1. driverClass 등록
		try {
			// 1. driverClass 등록	: 클래스객체 생성

			Class.forName(driverClass);

			// 2. Connection 객체생성
			
			conn = DriverManager.getConnection(url, user, password);
			
			// 3. PreparedStatment 객체 생성(미완성 쿼리전달 & 값대입)
			pstmt = conn.prepareStatement(sql);
			
			
			// 4. 쿼리실행 ResultSet 객체 변환
			// 결과집합이 0행이어도 rset이 null이 아니다.
			
			rset = pstmt.executeQuery();
			
			// 5. ResultSet객체 -> List<Member> 변환
			// rset에 한행씩 접근해서 Member객체 변환 -> list 추가
			while(rset.next()) {
				// 한행(record) -> Member 객체
				String id = rset.getString("id");
			
				idset.add(id);
				
				
			}
		
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		finally {
			// 6. 자원 반납(생성역순 : ResultSet -> PreparedStatment - Connection)
			
			try{rset.close();}
			catch(Exception e) {
				e.printStackTrace();
			}
			try{pstmt.close();}
			catch(Exception e) {
				e.printStackTrace();
			}	try{conn.close();}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		return idset;
	}

	public int updateName(String name,String id) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "update member set name = ? where id = ?";
	
		try {
			// 1. driver class 등록(프로그램 실행시 1회)
			Class.forName(driverClass);
			
			// 2. Connection객체 생성(url, user, password) & auto-commit 설정
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			
			
			// 3. PreparedStatment 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			
			
			// 4. 쿼리실행(DML : executeUpdate) & 결과값(int) 처리
			result = pstmt.executeUpdate();
			

			// 5. 트랜잭션 처리
			if(result > 0) conn.commit();
			else conn.rollback();
		}
		
		catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
			
		}
		
		
		// 6. 자원반납 : 생성 역순 반납
		finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		return result;
	}

	public int updateEmail(String email, String id) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "update member set email = ? where id = ?";
	
		try {
			// 1. driver class 등록(프로그램 실행시 1회)
			Class.forName(driverClass);
			
			// 2. Connection객체 생성(url, user, password) & auto-commit 설정
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			
			
			// 3. PreparedStatment 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, id);
			
			
			// 4. 쿼리실행(DML : executeUpdate) & 결과값(int) 처리
			result = pstmt.executeUpdate();
			

			// 5. 트랜잭션 처리
			if(result > 0) conn.commit();
			else conn.rollback();
		}
		
		catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
			
		}

		// 6. 자원반납 : 생성 역순 반납
		finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return result;
	}

	public int updateaddress(String address, String id) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "update member set address = ? where id = ?";
	
		try {
			// 1. driver class 등록(프로그램 실행시 1회)
			Class.forName(driverClass);
			
			// 2. Connection객체 생성(url, user, password) & auto-commit 설정
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			
			
			// 3. PreparedStatment 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, address);
			pstmt.setString(2, id);
			
			
			// 4. 쿼리실행(DML : executeUpdate) & 결과값(int) 처리
			result = pstmt.executeUpdate();
			

			// 5. 트랜잭션 처리
			if(result > 0) conn.commit();
			else conn.rollback();
		}
		
		catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
			
		}

		// 6. 자원반납 : 생성 역순 반납
		finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return result;
	}

}

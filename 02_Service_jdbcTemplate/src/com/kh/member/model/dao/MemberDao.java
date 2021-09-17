package com.kh.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.vo.Member;



import static com.kh.common.JDBCTemplate.*;

public class MemberDao {

	public int insertMember(Connection conn, Member member) {
		String sql = "insert into member values(?,?,?,?,?,?, default)";
		PreparedStatement pstmt = null;
		int result = 0;
		
		
		
		try {
			//1. PreparedStatment객체 생성(미완성 쿼리 & 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getGender());
			pstmt.setDate(4, member.getBirthday());
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getAddress());
			
			//2. 쿼리실행(DML - int , DQL - ResultSet)  & VO객체 변환
			result = pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			
		
			close(pstmt);
			
			
		}
		
		
		return result;
	}

	public List<Member> selectMemberByName(Connection conn, String name) {
		String sql = "Select * from member where name like ?";
		List<Member> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		
		try {
			//1. PreaparedStatment 객체 생성 & 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + name + "%");
			
			//2. 쿼리실행
			rset = pstmt.executeQuery();
			
			//3. ResultSet 처리 - > List<Member>
			while(rset.next()) {
				Member member = new Member();
				member.setId(rset.getString("id"));
				member.setName(rset.getString("name"));
				member.setGender(rset.getString("gender"));
				member.setBirthday(rset.getDate("birthday"));
				member.setEmail(rset.getString("email"));
				member.setAddress(rset.getString("address"));
				member.setRegDate(rset.getTimestamp("reg_date"));
				
				list.add(member);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			//4. 자원반납
			close(rset);
			close(pstmt);
			
		}
		
		
		
		return list;
	}

	public List<Member> selectAllmember(Connection conn) {
		String sql = "Select * from member";
		List<Member> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			//1. PreaparedStatment 객체 생성 & 값대입
			pstmt = conn.prepareStatement(sql);
			
			//2. 쿼리실행
			rset = pstmt.executeQuery();
			
			//3. ResultSet 처리 - > List<Member>
			while(rset.next()) {
				Member member = new Member();
				member.setId(rset.getString("id"));
				member.setName(rset.getString("name"));
				member.setGender(rset.getString("gender"));
				member.setBirthday(rset.getDate("birthday"));
				member.setEmail(rset.getString("email"));
				member.setAddress(rset.getString("address"));
				member.setRegDate(rset.getTimestamp("reg_date"));
				
				list.add(member);
				
			}
			
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return list;
	}

	public Member searchId(Connection conn, String id) {
		String sql = "Select * from member where id = ?";
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		Member member = null;
		
		
		try {
			//1. PreaparedStatment 객체 생성 & 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			
			//2. 쿼리실행
			rset = pstmt.executeQuery();
			
			//3. ResultSet 처리 - > List<Member>
			while(rset.next()) {
				member = new Member();
				member.setId(rset.getString("id"));
				member.setName(rset.getString("name"));
				member.setGender(rset.getString("gender"));
				member.setBirthday(rset.getDate("birthday"));
				member.setEmail(rset.getString("email"));
				member.setAddress(rset.getString("address"));
				member.setRegDate(rset.getTimestamp("reg_date"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			//4. 자원반납
			close(rset);
			close(pstmt);
			
		}
		
		
		
		
		return member;
	}

	public int updateName(Connection conn, String name, String id) {
		String sql = "update member set name = ? where id = ? ";
		PreparedStatement pstmt = null;
		int result = 0;
		
		
		
		try {
			//1. PreparedStatment객체 생성(미완성 쿼리 & 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, id);
			
			//2. 쿼리실행(DML - int , DQL - ResultSet)  & VO객체 변환
			result = pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			
		
			close(pstmt);
			
			
		}
		
		
		return result;
	}

	public int updateEmail(Connection conn, String email, String id) {
		String sql = "update member set email = ? where id = ? ";
		PreparedStatement pstmt = null;
		int result = 0;
		
		
		
		try {
			//1. PreparedStatment객체 생성(미완성 쿼리 & 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, id);
			
			//2. 쿼리실행(DML - int , DQL - ResultSet)  & VO객체 변환
			result = pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			
		
			close(pstmt);
			
			
		}
		
		
		return result;
	}

	public int updateAddress(Connection conn, String address, String id) {
		String sql = "update member set address = ? where id = ? ";
		PreparedStatement pstmt = null;
		int result = 0;
		
		
		
		try {
			//1. PreparedStatment객체 생성(미완성 쿼리 & 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, address);
			pstmt.setString(2, id);
			
			//2. 쿼리실행(DML - int , DQL - ResultSet)  & VO객체 변환
			result = pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			
		
			close(pstmt);
			
			
		}
		
		
		return result;
	}

	public int deleteMember(Connection conn, String id) {
		String sql = "delete from member where id = ?";
		PreparedStatement pstmt = null;
		int result = 0;
		
		
		
		try {
			//1. PreparedStatment객체 생성(미완성 쿼리 & 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			//2. 쿼리실행(DML - int , DQL - ResultSet)  & VO객체 변환
			result = pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			
		
			close(pstmt);
			
			
		}
		
		
		return result;
	}

	public List<Member> selectDelmember(Connection conn) {
		String sql = "Select * from member_del";
		List<Member> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			//1. PreaparedStatment 객체 생성 & 값대입
			pstmt = conn.prepareStatement(sql);
			
			//2. 쿼리실행
			rset = pstmt.executeQuery();
			
			//3. ResultSet 처리 - > List<Member>
			while(rset.next()) {
				Member member = new Member();
				member.setId(rset.getString("id"));
				member.setName(rset.getString("name"));
				member.setGender(rset.getString("gender"));
				member.setBirthday(rset.getDate("birthday"));
				member.setEmail(rset.getString("email"));
				member.setAddress(rset.getString("address"));
				member.setRegDate(rset.getTimestamp("reg_date"));
				member.setDelDate(rset.getTimestamp("del_date"));
				list.add(member);
				
			}
			
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return list;
	}

}

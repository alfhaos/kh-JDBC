package com.kh.member.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.member.model.exception.ConstraintViolationException;
import com.kh.member.model.exception.MemberDataNotvalidException;
import com.kh.member.model.exception.MemberException;
import com.kh.member.model.vo.Member;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	public MemberDao() {
		
		final String sqlConfigPath = "resources/member-query.properties";
		try {
			prop.load(new FileReader(sqlConfigPath));
			System.out.println("prop = " + prop);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int insertMember(Connection conn, Member member) {
		String sql = prop.getProperty("insertMember");
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
			String message = "회원 가입 오류!";
			if(e.getMessage().contains("STUDENT.CK_TB_MEMBER_GENDER")) {
				message += String.format("(성별은 M 또는 F값만 가능합니다. - %s)", member.getGender());
				throw new MemberDataNotvalidException(message,e);
				
			}
			
			else if(e.getMessage().contains("STUDENT.PK_TB_MEMBER_ID")) {
				message += String.format("(중복된 아이디는 불가능합니다. - %s)", member.getId());
				throw new MemberDataNotvalidException(message,e);
				
			}
			else {
				throw new MemberException(message,e);
			}
		}
		
		finally {
			
		
			close(pstmt);
			
			
		}
		
		
		return result;
	}

	public List<Member> selectMemberByName(Connection conn, String name) {
		String sql = prop.getProperty("selectMemberByName");
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
				member.setDelDate(rset.getTimestamp("del_date"));
				member.setDelflag(rset.getString("del_flag"));
				list.add(member);
				
			}
			
		} catch (SQLException e) {
			// 발생한 예외를 프로그램 흐름을 제어하는 곳까지 던져준다(공식임 걍 기억하셈).
			// UncheckedException이면서 업무흐름을 설명가능한 구체적 예외로 변경해서 던져준다.
			throw new MemberException("회원 이름 검색 오류!(" + e.getMessage() + ")\n",e);
		
		}
		
		finally {
			//4. 자원반납
			close(rset);
			close(pstmt);
			
		}
		
		
		
		return list;
	}

	public List<Member> selectAllmember(Connection conn) {
		String sql = prop.getProperty("selectAllmember");
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
				member.setDelflag(rset.getString("del_flag"));
				list.add(member);
				
			}
			
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return list;
	}

	public Member searchId(Connection conn, String id) {
		String sql = prop.getProperty("searchId");
		
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
				member.setDelDate(rset.getTimestamp("del_date"));
				member.setDelflag(rset.getString("del_flag"));
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
		String sql = prop.getProperty("updateName");
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
		String sql = prop.getProperty("updateEmail");
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
			String message = "이메일 수정 오류!";
			if(e.getMessage().contains("STUDENT.UQ_TB_MEMBER_EMAIL")) {
				message += String.format("(중복된 이메일은 불가능 합니다.. - %s)", email);
				throw new ConstraintViolationException(message,e);
				
			}
			else {
				throw new MemberException(message,e);
			}
		}
		
		finally {
			
		
			close(pstmt);
			
			
		}
		
		
		return result;
	}

	public int updateAddress(Connection conn, String address, String id) {
		String sql = prop.getProperty("updateAddress");
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
		String sql = prop.getProperty("deleteMember");
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
		String sql = prop.getProperty("selectDelMember");
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
				member.setDelflag(rset.getString("del_flag"));
				list.add(member);
				
			}
			
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return list;
	}

}

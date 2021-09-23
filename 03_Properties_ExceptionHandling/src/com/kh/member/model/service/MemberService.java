package com.kh.member.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;


import static com.kh.common.JDBCTemplate.*;
/**
 * 
 * Service Class
 * 업무로직 담당.
 * 
 * Service
 * 1. driver class 등록
 * 2. Connection 객체 생성(autoCommit false 처리)
 * 3. Dao 요청(Connection 객체 전달)
 * 4. 트랜잭션처리(commit/rollback)
 * 5. 자원반납
 * 
 * 
 * Dao
 * 1. PreparedStatment객체 생성(미완성 쿼리 & 값대입)
 * 2. 쿼리실행(DML - int , DQL - ResultSet)  & VO객체 변환
 * 3. 자원반납(PreparedStatment / ResultSet)
 */
public class MemberService {
	
	private MemberDao memberDao = new MemberDao();
	
	public int insertMember(Member member) {
		int result = 0;
		//1. Connection 객체생성 & autoCommit false 처리
		Connection conn = getConnection();
		
		//2. Dao 업무요청
		result = memberDao.insertMember(conn, member);
		
		//3. 트랜잭션 처리(commit/rollback)
		if(result > 0 ) commit(conn);
		else rollback(conn);
		
		//4. 자원반납
		close(conn);
		
		return result;
		
		
	}

	public List<Member> selectMemberByName(String name) {
		//1. Connection 생성
		Connection conn = getConnection();
		
		//2. Dao 업무요청
		List<Member> list = memberDao.selectMemberByName(conn,name);
		
		//3. 자원반납
		close(conn);
		
		return list;
	}

	public List<Member> selectAllmember() {
		//1. Connection 생성
		Connection conn = getConnection();
		
		//2. Dao 업무요청
		List<Member> list = memberDao.selectAllmember(conn);
		
		//3. 자원반납
		close(conn);
		
		return list;
	}

	public Member searchId(String id) {
		//1. Connection 생성
		Connection conn = getConnection();
				
		//2. Dao 업무요청
		Member member = memberDao.searchId(conn,id);
				
		//3. 자원반납
		close(conn);
		return member;
	}

	public int updateName(String name, String id) {
		int result = 0;
		//1. Connection 객체생성 & autoCommit false 처리
		Connection conn = getConnection();
		
		//2. Dao 업무요청
		result = memberDao.updateName(conn,name,id);
		
		//3. 트랜잭션 처리(commit/rollback)
		if(result > 0 ) commit(conn);
		else rollback(conn);
		
		//4. 자원반납
		close(conn);
		
		return result;
	}

	public int updateEmail(String email, String id) {
		int result = 0;
		//1. Connection 객체생성 & autoCommit false 처리
		Connection conn = getConnection();
		
		//2. Dao 업무요청
		result = memberDao.updateEmail(conn,email,id);
		
		//3. 트랜잭션 처리(commit/rollback)
		if(result > 0 ) commit(conn);
		else rollback(conn);
		
		//4. 자원반납
		close(conn);
		
		return result;
	}

	public int updateAddress(String address, String id) {
		int result = 0;
		//1. Connection 객체생성 & autoCommit false 처리
		Connection conn = getConnection();
		
		//2. Dao 업무요청
		result = memberDao.updateAddress(conn,address,id);
		
		//3. 트랜잭션 처리(commit/rollback)
		if(result > 0 ) commit(conn);
		else rollback(conn);
		
		//4. 자원반납
		close(conn);
		
		return result;
	}

	public int deleteMember(String id) {
		int result = 0;
		//1. Connection 객체생성 & autoCommit false 처리
		Connection conn = getConnection();
		
		//2. Dao 업무요청
		result = memberDao.deleteMember(conn,id);
		
		//3. 트랜잭션 처리(commit/rollback)
		if(result > 0 ) commit(conn);
		else rollback(conn);
		
		//4. 자원반납
		close(conn);
		
		return result;
	}

	public List<Member> selectDelmember() {
		//1. Connection 생성
		Connection conn = getConnection();
		
		//2. Dao 업무요청
		List<Member> list = memberDao.selectDelmember(conn);
		
		//3. 자원반납
		close(conn);
		
		return list;
	}
	


}

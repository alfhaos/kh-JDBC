package com.kh.member.controller;

import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberController {

	
	private MemberDao memberDao = new MemberDao();
	
	public int insertMember(Member member) {
		
		//1. dao메소드 호출
		int result = memberDao.insertMember(member);
		
		//2. 리턴값 다시 view단 전달
		return result;
		
		
	}
	
	public int deleteMember(String id) {
		
		int result = memberDao.deleteMember(id);
		
		return result;
	}
}

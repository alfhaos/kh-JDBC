package com.kh.member.controller;

import java.util.HashSet;
import java.util.List;

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

	public List<Member> selectAllMmeber() {
		
		return memberDao.selectAllMember();
	}

	public Member selectOneMember(String id) {
		
		return memberDao.selectOneMember(id);
	}

	public Member selectSearchMember(String shname) {
		
		return memberDao.selectSearchMember(shname);
	}

	public HashSet<String> idCheck() {
		
		return memberDao.idCheck();
	}

	public int updateName(String name,String id) {
		
		return memberDao.updateName(name,id);
	}

	public int updateEmail(String email, String id) {
		
		return memberDao.updateEmail(email,id);
	}

	public int updateaddress(String address, String id) {
		return memberDao.updateaddress(address,id);
	}
}

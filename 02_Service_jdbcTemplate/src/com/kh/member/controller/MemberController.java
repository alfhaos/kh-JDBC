package com.kh.member.controller;

import java.util.List;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;
/**
 * 
 * 사용자요청을 최초로 받아 service단에 업무를 분담, 처리결과를 view단으로 전달하는 역할
 *
 */
public class MemberController {
	
	private MemberService memberService = new MemberService();
	
	
	public int insertMember(Member member) {
		
		// 1. service단 업무요청
		int result = memberService.insertMember(member);
		
		// 2. view단 처리결과 전달
		return result;
		
		
	}


	public List<Member> selectMemberByName(String name) {
		
		return memberService.selectMemberByName(name);
	}


	public List<Member> selectAllmember() {
		
		return memberService.selectAllmember();
	}


	public Member searchId(String id) {
		
		return memberService.searchId(id);
	}


	public int updateName(String name, String id) {
	
		return memberService.updateName(name,id);
	}


	public int updateEmail(String email, String id) {
	
		return memberService.updateEmail(email,id);
	}


	public int updateaddress(String address, String id) {
		
		return memberService.updateAddress(address,id);
	}


	public int deleteMember(String id) {
		
		return memberService.deleteMember(id);
	}


	public List<Member> selectDelmember() {
		
		return memberService.selectDelmember();
	}

}

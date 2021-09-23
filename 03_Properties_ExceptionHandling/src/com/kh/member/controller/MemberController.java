package com.kh.member.controller;

import java.util.List;

import com.kh.member.model.exception.ConstraintViolationException;
import com.kh.member.model.exception.MemberDataNotvalidException;
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
		int result = 0;
		
		try {
			result =  memberService.insertMember(member);
			
			
		}
		catch(MemberDataNotvalidException e) {
			System.err.println(e.getMessage());
		}
		catch(Exception e) {
			System.err.println("관리자에게 문의하세요");
		}
		// 2. view단 처리결과 전달
		return result;
		
		
	}


	public List<Member> selectMemberByName(String name) {
		List<Member> list = null;
		try {
			list = memberService.selectMemberByName(name);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.err.println("관리자에게 문의하세요 : " + e.getMessage());
			
		}
		return list;
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
		int result = 0;
		try {
			result = memberService.updateEmail(email,id);
			
		}
		
		catch(ConstraintViolationException e) {
			System.err.println(e.getMessage());

			
		}
		catch(Exception e) {
			System.err.println("관리자에게 문의하세요");
		}
		return result;
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

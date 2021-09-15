package com.kh.member.view;

import java.util.Calendar;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import com.kh.member.controller.MemberController;
import com.kh.member.model.vo.Member;

public class MemberMenu {
	
	private Scanner sc = new Scanner(System.in);
	private MemberController memberController = new MemberController();
	
	
	public void mainMenu() {
		String menu = "***** 회원 관리 프로그램 *****\n"
					+ "1. 전체조회\n"
					+ "2. 아이디 조회\n"
					+ "3. 이름 검색\n"
					+ "4. 회원 가입\n"
					+ "5. 회원 정보 변경\n"
					+ "6. 회원 탈퇴\n"
					+ "0. 프로그램 종료\n"
					+ "****************************\n"
					+ "선택 : ";
		while(true) {
			System.out.print(menu);
			String choice = sc.next();
			
			Member member = null;
			int result = 0;
			
			switch(choice) {
			case "1": break;
			case "2": break;
			case "3": break;
			case "4": 
				member = inputMember();
				
				System.out.println("member@menu = " + member);
				
				result = memberController.insertMember(member);	// 모든 dml요청은 정수값이 반환
				System.out.println(result > 0 ? ">회원 가입 성공" : "> 회원 가입 실패");
				break;
			case "5": break;
			case "6": 
				String delid = delmember();
				
				System.out.println("삭제할 멤버 아이디 : " + delid);
				result = memberController.deleteMember(delid);
				System.out.println(result > 0 ? ">회원 탈퇴 성공" : "> 회원 탈퇴 실패");
			
			case "0": return;
			default : System.out.println("> 잘못 입력하셧습니다.");
			}
		}
		
		
	}
	
	
	
	private Member inputMember() {
		System.out.println("> 새 회원정보를 입력하세요.");
		
		System.out.print("아이디 : ");
		String id = sc.next();
		
		System.out.print("이름 : ");
		String name = sc.next();
		
		System.out.print("성별(M/F) : ");
		String gender = String.valueOf(sc.next().toUpperCase().charAt(0));
		
		//사용자 입력값 -> java.util.Calendar -> java.sql.Date
		System.out.print("생년월일(예: 19900909) : ");
		String temp = sc.next();
		int year = Integer.valueOf(temp.substring(0, 4));
		int month = Integer.valueOf(temp.substring(4,6)) -1 ; //0 ~ 11월
		int date = Integer.valueOf(temp.substring(6,8));
		Calendar cal = new GregorianCalendar(year ,month,date);
		Date birthday = new Date(cal.getTimeInMillis());
		
		
		System.out.print("이메일 : ");
		String email = sc.next();
		
		sc.nextLine(); // 버퍼에 남은 개행문자 제거
		
		System.out.print("주소 : ");
		String address = sc.nextLine();
		
		return new Member(id,name,gender,birthday,email,address,null);
	}
	
	public String delmember() {
		System.out.print("삭제할 멤버 아이디 입력 : ");
		String id = sc.next();
		
		return id;
	}
	
}

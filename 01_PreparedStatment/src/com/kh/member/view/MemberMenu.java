package com.kh.member.view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
			List<Member> list = null;
			String id,name,email,address;
			HashSet<String> idset = new HashSet<String>();
			
			switch(choice) {
			case "1":
				list = memberController.selectAllMmeber();
				printMember(list);
				break;
			case "2": 
				id = inputId();
				member = memberController.selectOneMember(id);
				printMember(member);
				break;
			case "3":
				
				name = inputName();
				member = memberController.selectSearchMember(name);
				printMember(member);
				break;
			case "4":
				
				idset = memberController.idCheck();
				member = inputMember(idset);
				
				System.out.println("member@menu = " + member);
				
				result = memberController.insertMember(member);	// 모든 dml요청은 정수값이 반환
				System.out.println(result > 0 ? ">회원 가입 성공" : "> 회원 가입 실패");
				
				break;
			case "5": 
				id = inputId();
				member = memberController.selectOneMember(id);
				printMember(member);
				
				if(member != null) {
					updateMenu();
					int updatenum = sc.nextInt();
					switch(updatenum) {
					case 1: 
						name = inputupdate();
						result = memberController.updateName(name,id);
						System.out.println(result > 0 ? "이름 변경 완료" : "이름 변경 실패");
						System.out.println("=============================================");
						break;
					
					case 2: 
						email = inputupdate();
						result = memberController.updateEmail(email,id);
						System.out.println(result > 0 ? "이메일 변경 완료" : "이메일 변경 실패");
						System.out.println("=============================================");
						
						break;
					
					case 3: 
						address = inputaddress();
						result = memberController.updateaddress(address,id);
						System.out.println(result > 0 ? "주소 변경 완료" : "이메일 변경 실패");
						System.out.println("=============================================");
						
						break;
					case 0: return;
					
					
					}
				}
				
				break;
			case "6": 
				String delid = delmember();
				
				System.out.println("삭제할 멤버 아이디 : " + delid);
				result = memberController.deleteMember(delid);
				System.out.println(result > 0 ? ">회원 탈퇴 성공" : "> 회원 탈퇴 실패");
				break;
			case "0": return;
			default : System.out.println("> 잘못 입력하셧습니다.");
			}
		}
		
		
	}
	
	private String inputaddress() {
		sc.nextLine();
		System.out.print("변경할 주소 입력 : ");
		String address = sc.nextLine();
		
		return address;
	}

	private String inputupdate() {
		System.out.print("변경할 문자열 입력 : ");
		String name = sc.next();
		
		return name;
	}

	private String inputName() {
		System.out.print("name : ");
		String name = sc.next();
		
		return name;
	}

	private void updateMenu() {
		String menu = "-------------- 회원정보 수정메뉴 ----------------\n"
				+ "1. 이름 변경\n"
				+ "2. 이메일 변경\n"
				+ "3. 주소변경\n"
				+ "0. 메인메뉴 돌아가기\n"
				+"------------------------------------------\n"
				+"선택 : ";
		System.out.println(menu);
	}

	/**
	 * 회원객체 하나를 출력하는 메소드
	 * @param member
	 */
	private void printMember(Member member) {
		if(member == null) {
			System.out.println("> 해당하는 회원이 없습니다");
		}
		else {
			System.out.println("--------------------------------------");
			System.out.println("아이디 : " + member.getId());
			System.out.println("이름 : " + member.getName());
			System.out.println("성별 : " + member.getGender());
			System.out.println("생일 : " + member.getBirthday());
			System.out.println("이메일 : " + member.getEmail());
			System.out.println("주소 : " + member.getAddress());
			SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm");
			System.out.println("등록일 : " + sdf.format(member.getRegDate()));
			System.out.println("--------------------------------------");
			
		}
		
	}


	private String inputId() {
		System.out.print("조회할 아이디 : ");
		String id = sc.next();
		
		return id;
	}


	/*
	 * 복수계의 Member객체를 출력하는 메소드
	 * 
	 */
	private void printMember(List<Member> list) {
		if(list.isEmpty()) {
			System.out.println("> 조회된 행이 없습니다.");
		}
		else {
			System.out.println("-------------------------------------------------------------------------------------");
			System.out.println("id	name	gender	birthday	email	address	reg_date");
			System.out.println("-------------------------------------------------------------------------------------");
			for(Member member : list) {
				System.out.println(member);
			}
			System.out.println("-------------------------------------------------------------------------------------");
		}
		
	}



	private Member inputMember(HashSet idset) {
		String id,name,gender,email,address;
		Date birthday;
		while(true) {
			System.out.println("> 새 회원정보를 입력하세요.");
		
			System.out.print("아이디 : ");
			id = sc.next();
			if(idset.contains(id)) {
				System.out.println("중복된 아이디 입니다 다시 입력해주세요.");
				System.out.println("=====================================");
				continue;
			}
			
			
			System.out.print("이름 : ");
			name = sc.next();
		
			System.out.print("성별(M/F) : ");
			gender = String.valueOf(sc.next().toUpperCase().charAt(0));
		
			//사용자 입력값 -> java.util.Calendar -> java.sql.Date
			System.out.print("생년월일(예: 19900909) : ");
			String temp = sc.next();
			int year = Integer.valueOf(temp.substring(0, 4));
			int month = Integer.valueOf(temp.substring(4,6)) -1 ; //0 ~ 11월
			int date = Integer.valueOf(temp.substring(6,8));
			Calendar cal = new GregorianCalendar(year ,month,date);
			birthday = new Date(cal.getTimeInMillis());
		
		
			System.out.print("이메일 : ");
			email = sc.next();
			
			sc.nextLine(); // 버퍼에 남은 개행문자 제거
			
			System.out.print("주소 : ");
			address = sc.nextLine();
			break;
		}
		return new Member(id,name,gender,birthday,email,address,null);
		
	}
	
	public String delmember() {
		System.out.print("삭제할 멤버 아이디 입력 : ");
		String id = sc.next();
		
		return id;
	}
	
}

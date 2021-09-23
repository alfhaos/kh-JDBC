package com.kh.member.model.vo;

import java.sql.Timestamp;
import java.sql.Date;

/*
 * Vo class 	(Value Object)
 * DTO class 	(Data Transfer Object)
 * Entity class
 * DO class		(Domain Object)
 * Bean Class
 * 
 * 
 * VO객체는 DB테이블(entity)의 한행(record)과 대응한다.
 */

public class Member {
	
	private String id;
	private String name;
	private String gender;
	private Date birthday;
	private String email;
	private String address;
	private Timestamp regDate,delDate;
	private String delflag;
	
	
	
	@Override
	public String toString() {
		return id + "\t" + name + "\t" + gender + "\t" + birthday + "\t" + email + "\t" +
				address + "\t" + regDate + "\t" + delDate + "\t" + delflag;
	}




	public String getId() {
		return id;
	}




	public String getDelflag() {
		return delflag;
	}


	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}


	public void setId(String id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getGender() {
		return gender;
	}




	public void setGender(String gender) {
		this.gender = gender;
	}




	public Date getBirthday() {
		return birthday;
	}




	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getAddress() {
		return address;
	}




	public void setAddress(String address) {
		this.address = address;
	}




	public Timestamp getRegDate() {
		return regDate;
	}




	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}




	public Timestamp getDelDate() {
		return delDate;
	}




	public void setDelDate(Timestamp delDate) {
		this.delDate = delDate;
	}




	public Member(String id, String name, String gender, Date birthday, String email, String address,
			Timestamp regDate) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.email = email;
		this.address = address;
		this.regDate = regDate;
	}
	

	public Member(String id, String name, String gender, Date birthday, String email, String address,
			Timestamp regDate,Timestamp deldate,String delflag) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.email = email;
		this.address = address;
		this.regDate = regDate;
		this.delDate = deldate;
		this.delflag = delflag;
	}




	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

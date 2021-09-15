--===============================================
--관리자계정
--===============================================

-- student 계정 생성
alter session set"_oracle_script" = true;

create user student
identified by student
default tablespace users;

alter user student quota unlimited on users;

grant connect,resource to student;


--==============================================
-- student 계정
--==============================================

-- member 테이블 생성

create table member(
    id varchar2(20),
    name varchar2(100) not null,
    gender char(1),
    birthday date,
    email varchar2(500),
    address varchar2(1000),
    reg_date timestamp default systimestamp,
    constraints pk_member_id primary key(id),
    constraints uq_member_email unique(email),
    constraints ck_member_gender check(gender in ('M','F'))
);


insert into
    member
values(
    'honggd','홍길동','M','1990-09-09','honggd@naver.com','서울시 강남구 테헤란로 123',default
);

insert into
    member
values(
    'jonggd','조길동','F','1997-04-12','Jonggd@naver.com','서울시 강남구 테헤란로 456',default
);

insert into
    member
values(
    'songgd','손길동','M','1890-09-09','songgd@naver.com','서울시 서울역 실로암사우나 123',default
);

insert into
    member
values(
    'sinsa','신사임당','F','1954-10-19','sinsa@naver.com','경상남도 함양시 안의면 1234',default
);
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


select * from member;

commit;




-- 탈퇴회원 관리용 테이블
-- trigger 객체 trig_member_del을 생성해서 member테이블에 delete가 실행될때마다 member_del테이블에 insert할 것.

create table member_del
as
select
    m.*,
    systimestamp del_date
from
    member m
where
    1= 0;   -- false 실제 데이터는 제외하고 , 테이블구조만 본떠서 member_del 테이블 생성
    
    
select * from member_del;
desc member_del;

create or replace trigger trig_member_del
    after
    delete on member
    for each row
    
begin
   if deleting then
             --insert
        insert into
           member_del(id,name,gender,birthday,email,address,reg_date,del_date)
         values(
            :old.id,:old.name,:old.gender,:old.birthday,:old.email,:old.address,:old.reg_date,systimestamp
         );     
    end if;
end;
/
delete from member where id = 'qqq';
select * from member;
select * from member_del;

-- 탈퇴회원관리2 - 하나의 테이블에서 관리
-- not null을 제외한 제약조건, 컬럼 기본값이 모두 제거됨.
create table tb_member
as
select
    m.*
from
    member m;


select * from tb_member;

-- 테이블 수정
alter table tb_member
add del_date date
add del_flag char(1) default 'N'
add constraints ck_tb_member_del_flag check(del_flag in ('Y','N'))
add constraints pk_tb_member_id primary key(id)
add constraints uq_tb_member_email unique(email)
add constraints ck_tb_member_gender check(gender in ('M','F'));

alter table tb_member
modify reg_date default sysdate;

-- 제약조건 조회
select
    ucc.column_name,
    uc.*
from
    user_constraints uc join user_cons_columns ucc
    on uc.constraint_name = ucc.constraint_name
where
    uc.table_name = 'TB_MEMBER';
-- 기본값 확인
select
    *
from
    user_tab_columns
where
    table_name = 'TB_MEMBER';
    
commit;
package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//ORM -> java(다른언어언어) object ->테이블로 매핑해주는 기술(jpa의 역할)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더패턴 
@Entity //User 클래스가 변수를 읽어 mysql에 테이블 생성이 된다
//@DynamicInsert //insert할때 null인 필드를 제외시켜줌
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 db의 넘버링 전략을 따라감
	private int id;//auto_increment로 넘버링
	
	@Column(nullable = false, length =100,unique =true)
	private String username; //아이디
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Column(nullable = false,length =50)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private RoleType role; //타입강제지정 ,DB는 RoleType이란게 없다. 따라서 해당 enum이 String 이라고 알려줘야함
	
	private String oauth; //로그인포맷 구분
	
	@CreationTimestamp //시간자동입력
	private Timestamp createDate;
}

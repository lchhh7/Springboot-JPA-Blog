package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;
//해당 레파지토리는 User테이블이 관리하는 레파지토리이고 User테이블의 Primary key 는 Integer형이다.
//DAO 역할
//자동으로 bean등록이 되서(스프링Ioc에서 객체를 가지고 있어서 injection을 통해 DI 하는지 여부)
// @Repository 어노테이션을 사용하지 않아도 된다.
//User 클래스의 기본키는 숫자이다 = <User,Integer>
public interface UserRepository extends JpaRepository<User, Integer>{
	//네이밍 쿼리
	//select * from user where username=?;
	Optional<User> findByUsername(String username);
}






//로그인함수는 save와 달리 따로 만들어야 한다
	//Jpa 네이밍 쿼리 전략
	//jpa가 가지고 있는 함수는 아니지만
	//SELECT * FROM user where username=? AND password=?; 가 자동으로 들어온다
	//User findByUsernameAndPassword(String username,String password);
	
	/*
	 //네이티브 쿼리방식
	@Query(value="SELECT * FROM user where hsername=? AND password=?",nativeQuery = true)
	User login(String username,String password); 
	  
	*/
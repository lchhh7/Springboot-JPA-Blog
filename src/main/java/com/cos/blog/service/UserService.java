package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 bean에 등록을 해준다. ->IoC를 해준다
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired//di해서 주입이 됨
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public User searchUser(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return null;
		});
		return user;	
	}
	
	@Transactional
	public void JoinUs(User user) {
		String rawPassword = user.getPassword(); //원문
		String encPassword = encoder.encode(rawPassword); //해쉬패스워드값
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	@Transactional
	public void UserUpdate(User user) {
		//수정시에는 영속성컨텍스트 User 오브젝트를 영속화 시킨후, 영속화된 User object를 수정해야한다.
		//select 를해서 User object를 DB로 부터 가져오는 이유는 영속화를 하기 위해서이다.(transcational로 자동업데이트) 
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원찾기 실패");
		});
		if(persistance.getOauth()==null||persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}
		
		
	}
}

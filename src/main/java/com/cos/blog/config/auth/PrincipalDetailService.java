package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service //빈등록
public class PrincipalDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	//스프링이 로그인 요청을 가로챌 떄,username,password를 가로채는데
	//password부분 처리는 알아서 함
	//username이 db에 있는지만 확인해주면 된다. 이를 확인해주는 함수를 override 해준다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다 : "+username);
				});
		return new PrincipalDetail(principal);
		//시큐리티 세션에 유저정보 저장됨
	}
	
}

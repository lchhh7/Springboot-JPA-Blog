package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDTO;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	//DI가능여부 
	//스프링이 컴포넌트 스캔을 할때, 서비스 어노테이션 클래스를 보는 순간 Bean에 등록해서 메모리에 띄워줌(Ioc를 통해)
	//예외 : JpaRepository인터페이스는 자동으로 Bean에 등록하기 때문에 어노테이션없이도 DI가능
	@Autowired//서비스 dependency injection 하기
	private UserService userService;
		
	@Autowired//SecurityConfig 에 있는 Bean DI
	private AuthenticationManager authenticationManager;
	
	@Autowired//di해서 주입이 됨
	private BCryptPasswordEncoder encoder;
	
	@PostMapping("/auth/joinProc") //조인을 수행하는 auth/joinProc
	public ResponseDTO<Integer> save(@RequestBody User user) {
		userService.JoinUs(user);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 json으로 변환해서 리턴(jackson)
	}
	
	@PutMapping("/user")
	public ResponseDTO<Integer> update(@RequestBody User user) {
		userService.UserUpdate(user);
		//DB는 트랜잭션 적용으로 적용이 되었지만, 세션 값은 변경이 되어있지 않음
		//세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1); //자바오브젝트를 json으로 변환해서 리턴(jackson)
	}
	
}

package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//스프링 시큐리티를 이용해서 인증이 안된 사용자들이 출입할수 있는 경로 /auth/ **허용
//그냥 주소가 /이면 index.jsp 허용
//static 이하에 있는 /js나 /css /image
@Controller
public class UserController {
	//WEB-INF/views/user/joinForm.jsp
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return"user/updateForm";
	}
}

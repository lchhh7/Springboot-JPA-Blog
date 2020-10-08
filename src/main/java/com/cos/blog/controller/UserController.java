package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//스프링 시큐리티를 이용해서 인증이 안된 사용자들이 출입할수 있는 경로 /auth/ **허용
//그냥 주소가 /이면 index.jsp 허용
//static 이하에 있는 /js나 /css /image
@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	//WEB-INF/views/user/joinForm.jsp
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders(); //데이터 형태를 알려줌
		headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
		//HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type","authorization_code");
		params.add("client_id","204f5ab5db2f0a3ba52dcd34020aa125");
		params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		//HttpHeader 와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = 
				new HttpEntity<>(params,headers);
		//Http 요청하기 -Post방식으로 response변수의 응답 받음
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,kakaoTokenRequest, String.class
				);
		
		ObjectMapper obMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = obMapper.readValue(response.getBody(), OAuthToken.class);
		}catch(JsonMappingException e) {
			e.printStackTrace();
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders(); //데이터 형태를 알려줌
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
		//HttpBody 오브젝트 생성
		
		//HttpHeader 와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest = 
				new HttpEntity<>(headers2);
		//Http 요청하기 -Post방식으로 response변수의 응답 받음
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest,
				String.class
				);
		
		ObjectMapper obMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = obMapper2.readValue(response2.getBody(), KakaoProfile.class);
		}catch(JsonMappingException e) {
			e.printStackTrace();
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//User 오브젝트 : username,password,email
		System.out.println("카카오 아이디(번호) : "+kakaoProfile.getId());
		System.out.println("카카오 이메일 : "+kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그서버 유저네임 : "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("블로그서버 이메일 : "+kakaoProfile.getKakao_account().getEmail());
		System.out.println("블로그서버 패스워드 : "+cosKey);
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		User originUser = userService.searchUser(kakaoUser.getUsername());
		
		if(originUser==null) {
			userService.JoinUs(kakaoUser);
		}
		//자동로그인
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return"user/updateForm";
	}
	
}

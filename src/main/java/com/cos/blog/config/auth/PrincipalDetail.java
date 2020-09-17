package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Data;

//스프링시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가되면 
//UserDetails 타입의 오브젝트를 스프링 시큐리티 고유 세션저장소에 저장해준다.
@Data
public class PrincipalDetail implements UserDetails{
	private User user; //컴포지션(extends 는 상속)
	
	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정이 만료되지 않았는지 return(true 만료안됨)
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정이 잠겨있는지 return(true 가 unlock)
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		//비밀번호가 만료되지 않았는지 return (true 만료X)
		return true;
	}

	@Override
	public boolean isEnabled() {
		//계정활성화(사용가능) return (true :활성화)
		return true;
	}
	//계정이 갖고 있는 권한 목록을 리턴한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		/*//인터페이스라 익명클래스가 생성되고 추상메소드 오버라이딩 된다.
		collectors.add(new GrantedAuthority() { 
			
			@Override
			public String getAuthority() {
				return "ROLE_"+user.getRole(); 
				//스프링에서 ROLE을 받을때의 규칙(ROLE_USER가 리턴될 것이다)
			}
		});
		*/
		//위 내용을 람다식으로 표현
		collectors.add(()->{return "ROLE_"+user.getRole();});
		
		return collectors;
	}
}

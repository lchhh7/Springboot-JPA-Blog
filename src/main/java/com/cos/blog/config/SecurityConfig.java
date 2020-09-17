package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

//bean등록 : 스프링컨테이너에서 객체를 관리할수 있게 하는것

@Configuration //빈등록(Ioc관리)
@EnableWebSecurity //시큐리티필터 설정
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정주소로 접근을하면 권한 및 인증을 미리체크
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	//해쉬값 : $2a$10$EnQDkXouBKNHHYwunEN4FufNLe2Ws8iShqJgo8I1zQ.nus11iRqHW
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	//이 객체를 통해 인코딩해준다.
	@Bean //리턴값을 스프링이 관리(Ioc)
	public BCryptPasswordEncoder encodePWD() {
		String encPassword = new BCryptPasswordEncoder().encode("1234");
		System.out.println("암호화된 해시비밀번호 : "+encPassword);
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인하는데 password를 가로채기 하는데
	//해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	//같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교 할 수 있다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		  .csrf().disable() //csrf 토큰 비활성화(테스트시 걸어두는게 좋음)
		  .authorizeRequests()
			.antMatchers("/","/auth/**","/js/**","/css/**","/image/**") 
			.permitAll()  //antMatchers에 설정한 주소를 통해 들어오는건 무조건 들어올수있다
			.anyRequest()   //인증되지 않는 요청
			.authenticated() //요청받아야함
		  .and()
		  	.formLogin()
		  	.loginPage("/auth/loginForm")
		  	.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당주소로 요청오는 로그인을 가로챔
		  	.defaultSuccessUrl("/");
	}
}

package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션들 끔
                .and()
                .authorizeRequests() // URL별 권한 관리를 설정하는 옵션의 시작점 authorizeRequests가 선언되야만 antMatchers 사용 가능
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // "api/v1/**"주소는 USER권한을 가진 사람만 가능
                .anyRequest().authenticated() // 설정된 값들 이외 나머지 URL authenticated()를 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용
                .and()
                .logout()
                .logoutSuccessUrl("/") // 로그아웃 성공 시 "/"로 이동
                .and()
                .oauth2Login()
                .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들 담당
                .userService(customOAuth2UserService); // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록(사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시)
    }
}
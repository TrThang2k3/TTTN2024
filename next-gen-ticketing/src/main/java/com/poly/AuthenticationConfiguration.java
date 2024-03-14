package com.poly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.poly.util.service.implement.UserServiceImplement;

@Configuration
@EnableWebSecurity
public class AuthenticationConfiguration {
	@Autowired
	UserServiceImplement userService;

	/* Quản lý dữ liệu người sử dụng */
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// CSRF, CORS
		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable());
		// Phân quyền sử dụng
		http.authorizeHttpRequests(
				auth -> auth.requestMatchers("/nextgen.com/account/**").authenticated()
							.anyRequest().permitAll() // anonymous
		);

		// Điều khiển lỗi truy cập không đúng role
		http.exceptionHandling(handler -> handler.accessDeniedPage("/nextgen.com/access/denied")); // error

		// Đăng nhập
		http.formLogin((form) -> form.loginPage("/nextgen.com/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/nextgen.com/login/success", false)
				.failureUrl("/nextgen.com/login/error")
				.usernameParameter("email").passwordParameter("password"));
		http.rememberMe((remember) -> remember.rememberMeParameter("agree"));

		// Đăng xuất
		http.logout((logout) -> logout.logoutUrl("/nextgen.com/account/logout")
				.logoutSuccessUrl("/aliyah.com/logout/success"));

		return http.build();
	}
}

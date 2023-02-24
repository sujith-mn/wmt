package com.application.workmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguartion {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests()
				.requestMatchers("/profiles/**","/api/demands/**").authenticated()
				.requestMatchers("/registrations/**","/swagger-ui.html/**").permitAll()
				.anyRequest().hasRole("Admin")
				.and().formLogin()
				.and().httpBasic();
		return http.build();

	}

	@Bean
	PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}

}

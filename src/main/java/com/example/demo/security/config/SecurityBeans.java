package com.example.demo.security.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityBeans {
	@Bean
	public PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@SneakyThrows
	public AuthenticationManager authenticationManager( AuthenticationConfiguration authenticationConfiguration) {
		//Dangerous
		return authenticationConfiguration.getAuthenticationManager();
	}
}

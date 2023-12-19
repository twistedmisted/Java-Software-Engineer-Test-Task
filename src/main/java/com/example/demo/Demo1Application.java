package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
@EnableCaching
//@EnableWebSecurity(debug = true)
public class Demo1Application {
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver createMultipartResolver(){
		CommonsMultipartResolver resolver=new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}

	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);
	}

}

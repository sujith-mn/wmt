package com.cg.application.demandmanagement;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MgmtApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MgmtApiApplication.class, args);
		
		System.out.println(" ---->  R U N N I N G  O N  P O R T : 7 0 0 0");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}

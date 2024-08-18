package com.roadguardianbackend.roadguardianbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;

import static com.roadguardianbackend.roadguardianbackend.constant.FileConstant.USER_FOLDER;

@SpringBootApplication
//@SpringBootApplication(scanBasePackages = {"com.roadguardianbackend.roadguardianbackend"})
public class RoadGuardianBackendApplication {



	public static void main(String[] args) {
		SpringApplication.run(RoadGuardianBackendApplication.class, args);
		new File(USER_FOLDER).mkdirs();
	}




}

package com.example.languagelearningmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@EntityScan(basePackages = "com.example.languagelearningmodule")
public class LanguageLearningModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(LanguageLearningModuleApplication.class, args);
	}

}

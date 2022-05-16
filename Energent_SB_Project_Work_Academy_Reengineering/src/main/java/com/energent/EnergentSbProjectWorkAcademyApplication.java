package com.energent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootApplication
public class EnergentSbProjectWorkAcademyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergentSbProjectWorkAcademyApplication.class, args);
	}
	
	
	@Bean
	public Docket api() {

	return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any()).build();

	}

	 

	@SuppressWarnings("unused")
	private ApiInfo apiInfo() {

	return new ApiInfoBuilder().title("RestAPI").description("Academy and Student Service Exposed Operations")
			.version("1.0").build();

	}

}

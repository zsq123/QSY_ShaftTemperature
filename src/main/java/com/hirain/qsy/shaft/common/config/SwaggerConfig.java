package com.hirain.qsy.shaft.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
@ConditionalOnProperty(prefix = "hirain", name = "swagger-enable", havingValue = "true")
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				// controller所在包
				.apis(RequestHandlerSelectors.basePackage("com.hirain.qsy.shaft.controller")).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {

		return new ApiInfoBuilder().title("IT开发框架的API文档").version("1.0").build();
	}
}

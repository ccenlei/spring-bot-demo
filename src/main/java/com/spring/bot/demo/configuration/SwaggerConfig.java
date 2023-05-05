package com.spring.bot.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.spring.bot.demo.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("spring boot demo")
                .description("ccenlei's spring boot demo")
                .contact(new Contact("ccenlei", "https://github.com/ccenlei", "eosqianyeliu@gmail.com"))
                .version("v0.0.1")
                .build();
    }
}

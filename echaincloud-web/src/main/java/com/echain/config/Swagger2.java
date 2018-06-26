package com.echain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @version V1.0
 * @Title: Swagger2
 * @Description: Swagger2配置类
 * @author: jun.li
 * @date: 2017/5/9 15:01
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.echain.rest"))
                .paths(PathSelectors.any())
                .build()
//                .globalOperationParameters(globalOperationParameters())
                .apiInfo(apiInfo())
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts())
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Echain API")
                .description("created by echain")
                .termsOfServiceUrl("http://echain.echain.one/")
                .version("1.0")
                .build();
    }

//    private List<ApiKey> securitySchemes() {
//        List<ApiKey> list = new ArrayList<>();
//        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
//        list.add(apiKey);
//
//        return list;
//    }
//
//    private List<SecurityContext> securityContexts() {
//        List<SecurityContext> list = new ArrayList<>();
//
//        SecurityContext context = SecurityContext.builder().securityReferences(defaultAuth())
//                .forPaths(PathSelectors.regex("^(?!auth).*$")).build();
//
//        list.add(context);
//        return list;
//    }
//
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
//
//        SecurityReference securityReference = new SecurityReference("Authorization", authorizationScopes);
//
//        List<SecurityReference> list = new ArrayList<>();
//        list.add(securityReference);
//        return list;
//    }
}
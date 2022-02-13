package com.appsolute.soomapi.global.env.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String TITLE = "SOOM Schedule API ";
    private static final String DESCRIPTION = "\"학교 생활의 숨통을 틔우다, SOOM\" 그 첫번째 도전, 일정관리 API 입니다.";
    @Bean
    public Docket apiV1() {
        List<Parameter> globalParams = getCredentialParams();
        
        String version = "V1";
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(globalParams)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                .apiInfo(generateInfo(version));
    }

    private List<Parameter> getCredentialParams() {
        Parameter clientId = new ParameterBuilder()
                .name("X-Client-Id")
                .description("API 에 접근하기 위한 클라이언트 아이디 입니다.")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build();
        Parameter clientSecret = new ParameterBuilder()
                .name("X-Client-Secret")
                .description("API 에 접근하기 위한 클라이언트 비밀번호 입니다.")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build();
        return List.of(clientId, clientSecret);
    }

    private ApiInfo generateInfo(String version) {
        return new ApiInfoBuilder()
                .title(TITLE + version)
                .description(DESCRIPTION)
                .version(version)
                .build();
    }
}

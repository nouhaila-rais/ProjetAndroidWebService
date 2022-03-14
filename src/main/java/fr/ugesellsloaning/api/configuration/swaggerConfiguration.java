package fr.ugesellsloaning.api.configuration;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.awt.print.Pageable;
import java.sql.Date;
import java.util.List;

@Configuration
@EnableSwagger2
public class swaggerConfiguration {
    private static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket swaggerSpringfoxDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .useDefaultResponseMessages(false);

        docket = docket.select()
                .paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .apis(RequestHandlerSelectors.basePackage("fr.ugesellsloaning.api.controllers"))
                .build();
        return  docket;
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("REST API - University Gustave Effeil")
                .description("Ceci est l'API Rest-full du projet android du groupe 1 \n -Diallo Mamadou Hassimiou" +
                        "\n -Balde Mamadou Kanghe" +
                        "\n -Mouhsine" +
                        "\n -Fati" +
                        "\n -Yasmine Mekouar" +
                        "\n -Ahmed.").termsOfServiceUrl("")
                .contact(new Contact("Developper", "www.mondemarcheur.com", "mamadou-hassimiou.diallo@outlook.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .version("0.0.1")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
    }


}

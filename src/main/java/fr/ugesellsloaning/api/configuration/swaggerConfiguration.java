package fr.ugesellsloaning.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.awt.print.Pageable;
import java.sql.Date;

@Configuration
@EnableSwagger2
public class swaggerConfiguration {

    private static final String DEFAULT_INCLUDE_PATTERN = "/secured/.*";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket swaggerSpringfoxDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(Date.class)
                .directModelSubstitute(java.time.LocalDate.class, Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .useDefaultResponseMessages(false);

        docket = docket.select()
                .paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .apis(RequestHandlerSelectors.basePackage("fr.ugesellsloaning.api.controllers"))
                .build();
        return  docket;
        }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("REST API - University Gustave Effeil")
                .description("Ceci est l'API Rest-full du projet android\n -Nouhaila Rais" +
                        "\n -Mouna Serrar" +
                        "\n -Kaoutar Elmofatiche.").termsOfServiceUrl("")
                .contact(new Contact("Developper", "www.mondemarcheur.com", "mounaserrar21@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .version("1.0.0")
                .build();
    }

}

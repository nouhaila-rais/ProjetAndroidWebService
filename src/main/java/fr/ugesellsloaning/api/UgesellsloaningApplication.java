package fr.ugesellsloaning.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class UgesellsloaningApplication {

    public static void main(String[] args) {
        SpringApplication.run(UgesellsloaningApplication.class, args);
    }

    @Bean(name="passwordEncoder")
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

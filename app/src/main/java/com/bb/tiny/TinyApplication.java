package com.bb.tiny;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@SpringBootApplication
@EnableSwagger2
@EnableScan
@EnableWebMvc
public class TinyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinyApplication.class, args);
    }

}

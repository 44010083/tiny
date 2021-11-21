package com.bb.tiny.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * * Copyright (C) 2021  zhong002
 *
 * @author zhong002
 * @version 1.0
 * @mail 44010083@qq.com
 * @description
 * @create: 2021-11-20
 **/
@Configuration //标记配置类
@EnableSwagger2 //开启在线接口文档
public class Swagger2Config {


    @Value("${swagger.enable}")
    private String swaggerEnable;
    @Value("${swagger.title}")
    private String title;
    @Value("${swagger.contact.name}")
    private String contactName;
    @Value("${swagger.contact.email}")
    private String contactEmail;
    @Value("${swagger.version}")
    private String version;

    @Bean
    public Docket controllerApi() {
        boolean enableSwagger = StringUtils.isNotEmpty(swaggerEnable) && Boolean.parseBoolean(swaggerEnable);


        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .apiInfo(new ApiInfoBuilder()
                        .title(title)
                        .contact(new Contact(contactName, "", contactEmail))
                        .version(version)
                        .build())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();

    }


}

package com.lex.cat;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class CatApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CatApp.class).web(WebApplicationType.SERVLET).run(args);
    }
}

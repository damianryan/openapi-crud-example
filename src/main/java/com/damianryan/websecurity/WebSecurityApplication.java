package com.damianryan.websecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@SpringBootApplication
public class WebSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSecurityApplication.class, args);
    }

    @Bean
    public OncePerRequestFilter etagFilter() {
        return new ShallowEtagHeaderFilter();
    }
}

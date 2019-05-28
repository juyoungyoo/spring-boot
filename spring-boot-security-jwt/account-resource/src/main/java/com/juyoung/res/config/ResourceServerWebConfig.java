package com.juyoung.res.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebMvc
@ComponentScan({"com.juyoung.res.web"})
public class ResourceServerWebConfig implements WebMvcConfigurer {

}
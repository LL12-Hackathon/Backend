package com.example.meetpro.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "https://localhost:5173", "http://localhost:8080", "https://localhost:8080", "http://localhost:3000",
                "http://3.36.159.166:5173", "https://3.36.159.166:5173","http://ec2-3-36-159-166.ap-northeast-2.compute.amazonaws.com:5173", "https://ec2-3-36-159-166.ap-northeast-2.compute.amazonaws.com:5173"
                ,"https://meet-pro.netlify.app:5173", "https://meet-pro.netlify.app:8080");

    }
}

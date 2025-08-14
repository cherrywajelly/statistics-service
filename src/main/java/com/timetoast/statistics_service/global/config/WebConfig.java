package com.timetoast.statistics_service.global.config;


import com.timetoast.statistics_service.global.resolver.LoginMemberResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginMemberResolver loginMemberResolver;
    private final CorsProperties corsProperties;

    public WebConfig(final LoginMemberResolver loginMemberResolver, final CorsProperties corsProperties) {
        this.loginMemberResolver = loginMemberResolver;
        this.corsProperties = corsProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOriginPatterns(
                        corsProperties.getPaths().toArray(new String[0])
                )
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .exposedHeaders("*");

    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginMemberResolver);
    }


}

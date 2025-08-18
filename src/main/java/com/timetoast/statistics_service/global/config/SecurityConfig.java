package com.timetoast.statistics_service.global.config;

import com.timetoast.statistics_service.auth.adapter.in.AuthFilter;
import com.timetoast.statistics_service.auth.adapter.in.AuthForbiddenHandler;
import com.timetoast.statistics_service.auth.adapter.in.AuthenticationEntryPoint;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CorsProperties corsProperties;

    public SecurityConfig(final CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        return http
                //REST API 이므로, basic auth 및 csrf 보안 사용하지 않음.
                .httpBasic(AbstractHttpConfigurer::disable)
                //cookie 사용 안하면 끔.
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                //h2-console
                .headers(httpSecurityHeaders -> httpSecurityHeaders.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
                .authorizeHttpRequests(
                        request -> {
                            request.requestMatchers("/statistics/api/v4/**").hasAnyRole("MANAGER");

                            request.requestMatchers("/statistics/api/v3/**").hasAnyRole("MANAGER", "STAFF");

                            request.requestMatchers("/statistics/api/v2/**").hasAnyRole("CREATOR");


                            request.requestMatchers( "/actuator/**", "/docs/**", "/v3/api-docs/**",
                                    "/swagger-ui/**","/api-docs/**").permitAll();

                            request.anyRequest().authenticated();

                        }
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .addFilterAt((Filter) new AuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling( exceptionHandling -> exceptionHandling
                                        .authenticationEntryPoint(new AuthenticationEntryPoint())
                                        .accessDeniedHandler(new AuthForbiddenHandler())
                )
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(corsProperties.getPaths());
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

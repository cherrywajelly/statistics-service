package com.timetoast.statistics_service.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "security.cors")
@Setter
@Getter
public class CorsProperties {
    private List<String> paths;
}
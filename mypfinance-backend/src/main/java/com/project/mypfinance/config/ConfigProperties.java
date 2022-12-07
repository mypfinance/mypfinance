package com.project.mypfinance.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mypfinance")
public record ConfigProperties(String secretKey, String dbUsername, String dbPassword) {
}

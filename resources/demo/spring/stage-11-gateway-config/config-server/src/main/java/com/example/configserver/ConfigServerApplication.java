package com.example.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Spring Cloud Config Server
 *
 * Provides centralized external configuration management for distributed systems.
 * Services connect to this server to fetch their configuration at startup.
 *
 * Configuration sources (in order of precedence):
 * 1. Git repository (production)
 * 2. Local file system (development)
 * 3. Vault, JDBC, etc.
 *
 * Endpoints:
 * - GET /{application}/{profile}     - Get config for app and profile
 * - GET /{application}-{profile}.yml - Get config as YAML
 *
 * Examples:
 * - http://localhost:8888/task-service/default
 * - http://localhost:8888/user-service/dev
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}

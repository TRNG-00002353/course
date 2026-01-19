package com.example.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * User Service with Config Client
 *
 * This service:
 * - Fetches configuration from Config Server at startup
 * - Registers with Eureka Discovery Server
 *
 * Configuration is centralized in config-server.
 * The service name 'user-service' maps to user-service.yml in config-repo.
 */
@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

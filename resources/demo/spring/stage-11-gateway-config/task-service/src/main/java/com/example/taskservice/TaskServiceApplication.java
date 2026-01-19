package com.example.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Task Service with Config Client
 *
 * This service:
 * - Fetches configuration from Config Server at startup
 * - Registers with Eureka Discovery Server
 * - Uses Feign Client to call User Service
 *
 * Configuration is centralized in config-server.
 * The service name 'task-service' maps to task-service.yml in config-repo.
 */
@SpringBootApplication
@EnableFeignClients
public class TaskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApplication.class, args);
    }
}

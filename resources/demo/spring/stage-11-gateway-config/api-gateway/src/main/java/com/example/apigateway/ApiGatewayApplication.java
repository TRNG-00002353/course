package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API Gateway - Single Entry Point for Microservices
 *
 * Spring Cloud Gateway provides:
 * - Routing: Route requests to appropriate services
 * - Filtering: Add headers, modify requests/responses
 * - Load Balancing: Distribute load across service instances
 * - Circuit Breaking: Handle service failures gracefully
 *
 * Routes configured via config-server:
 * - /api/tasks/** → task-service
 * - /api/users/** → user-service
 *
 * All client requests go through this gateway on port 8080.
 * The gateway uses Eureka to discover service locations.
 */
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}

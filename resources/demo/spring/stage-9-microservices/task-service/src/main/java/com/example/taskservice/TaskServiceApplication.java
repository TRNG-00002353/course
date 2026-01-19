package com.example.taskservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(TaskServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TaskServiceApplication.class, args);
        log.info("===========================================");
        log.info("  Task Service started on port 8081");
        log.info("  API: http://localhost:8081/api/tasks");
        log.info("===========================================");
    }
}

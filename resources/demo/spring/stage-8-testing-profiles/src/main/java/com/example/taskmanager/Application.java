package com.example.taskmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * Spring Boot Application with profile-aware startup logging.
 */
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);
        Environment env = context.getEnvironment();

        String[] activeProfiles = env.getActiveProfiles();
        String profile = activeProfiles.length > 0 ? activeProfiles[0] : "default";

        log.info("============================================");
        log.info("  Task Manager API Started!");
        log.info("  Active Profile: {}", profile);
        log.info("  API: http://localhost:{}/api/tasks", env.getProperty("server.port", "8080"));
        if ("dev".equals(profile)) {
            log.info("  H2 Console: http://localhost:{}/h2-console", env.getProperty("server.port", "8080"));
        }
        log.info("============================================");
    }
}

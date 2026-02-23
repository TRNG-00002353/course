# SonarQube

## What is SonarQube?

SonarQube is an **open-source platform for code quality analysis**. It automatically reviews your code to detect bugs, vulnerabilities, and code smells.

**Think of it like a code health check**: Just like a doctor checks your health, SonarQube examines your code's health and tells you what needs attention.

```
┌─────────────────────────────────────────────────────────┐
│                      SonarQube                           │
├─────────────────────────────────────────────────────────┤
│                                                          │
│   Code ──▶ Analysis ──▶ Report ──▶ Quality Gate        │
│                           │                              │
│                    ┌──────┴──────┐                      │
│                    │  Detects:    │                      │
│                    │  - Bugs      │                      │
│                    │  - Security  │                      │
│                    │  - Smells    │                      │
│                    │  - Coverage  │                      │
│                    └─────────────┘                      │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

---

## Why Code Quality Matters

### The Cost of Bad Code

```
Early Detection (Development):    $1 to fix
Testing Phase:                    $10 to fix
Production:                       $100+ to fix
```

### What SonarQube Catches

| Category | What It Finds | Example |
|----------|---------------|---------|
| **Bugs** | Actual code errors | Null pointer, infinite loops |
| **Vulnerabilities** | Security issues | SQL injection, XSS |
| **Code Smells** | Maintainability issues | Long methods, duplicates |
| **Coverage** | Untested code | Methods without tests |

---

## Key Concepts

### Quality Gate

A **Quality Gate** is a set of conditions your code must meet to be considered "good enough."

```
Quality Gate: Pass ✓
├── Coverage > 80% ✓
├── Bugs = 0 ✓
├── Vulnerabilities = 0 ✓
└── Code Smells < 10 ✓

Quality Gate: Fail ✗
├── Coverage > 80% ✗ (currently 65%)
├── Bugs = 0 ✗ (found 2)
└── ...
```

### Issue Types

| Type | Severity | Description |
|------|----------|-------------|
| **Bug** | High | Code that is clearly wrong |
| **Vulnerability** | High | Security weakness |
| **Code Smell** | Medium | Maintainability issue |
| **Security Hotspot** | Review | Needs security review |

### Issue Severities

| Severity | Impact | Example |
|----------|--------|---------|
| **Blocker** | System crash | Infinite loop |
| **Critical** | Data corruption | Null pointer |
| **Major** | Performance issue | Inefficient algorithm |
| **Minor** | Confusion | Bad naming |
| **Info** | Style issue | Missing comments |

---

## Installing SonarQube

### Using Docker (Recommended)

```bash
# Create docker-compose.yml
cat > docker-compose.yml << 'EOF'
version: '3.8'
services:
  sonarqube:
    image: sonarqube:community
    ports:
      - "9000:9000"
    environment:
      - SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true
    volumes:
      - sonarqube_data:/opt/sonarqube/data
      - sonarqube_logs:/opt/sonarqube/logs
      - sonarqube_extensions:/opt/sonarqube/extensions

volumes:
  sonarqube_data:
  sonarqube_logs:
  sonarqube_extensions:
EOF

# Start SonarQube
docker-compose up -d

# Wait for startup (takes 1-2 minutes)
# Access at http://localhost:9000
# Default login: admin / admin
```

### First-Time Setup

1. Open `http://localhost:9000`
2. Login with `admin` / `admin`
3. Change password when prompted
4. Create a project

---

## Analyzing Code

### SonarScanner

The **SonarScanner** is the tool that analyzes your code and sends results to SonarQube.

### For Maven Projects

Add to your `pom.xml`:
```xml
<properties>
    <sonar.host.url>http://localhost:9000</sonar.host.url>
</properties>
```

Run analysis:
```bash
mvn sonar:sonar -Dsonar.token=your-token
```

### For Gradle Projects

Add to `build.gradle`:
```groovy
plugins {
    id "org.sonarqube" version "4.4.1.3373"
}

sonar {
    properties {
        property "sonar.host.url", "http://localhost:9000"
    }
}
```

Run analysis:
```bash
./gradlew sonar -Dsonar.token=your-token
```

### For Any Project (Generic Scanner)

1. Download SonarScanner
2. Create `sonar-project.properties`:

```properties
# Project identification
sonar.projectKey=my-project
sonar.projectName=My Project
sonar.projectVersion=1.0

# Source code location
sonar.sources=src
sonar.java.binaries=target/classes

# SonarQube server
sonar.host.url=http://localhost:9000
sonar.token=your-token

# Encoding
sonar.sourceEncoding=UTF-8
```

3. Run scanner:
```bash
sonar-scanner
```

---

## SonarQube Configuration

### Project Configuration File

Create `sonar-project.properties` in your project root:

```properties
# Required
sonar.projectKey=my-spring-app
sonar.projectName=My Spring App

# Sources
sonar.sources=src/main/java
sonar.tests=src/test/java
sonar.java.binaries=target/classes

# Test coverage (JaCoCo)
sonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml

# Exclusions
sonar.exclusions=**/generated/**,**/config/**
sonar.test.exclusions=**/test/**

# Quality gate
sonar.qualitygate.wait=true
```

### Code Coverage with JaCoCo

Add to `pom.xml`:
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Run tests with coverage:
```bash
mvn clean test jacoco:report
mvn sonar:sonar
```

---

## Quality Gates

### Default Quality Gate

```
┌─────────────────────────────────────────┐
│        Sonar Way Quality Gate           │
├─────────────────────────────────────────┤
│ New Code:                               │
│   Coverage >= 80%                       │
│   Duplicated Lines <= 3%                │
│   Maintainability Rating = A            │
│   Reliability Rating = A                │
│   Security Rating = A                   │
│   Security Hotspots Reviewed = 100%     │
└─────────────────────────────────────────┘
```

### Creating Custom Quality Gate

1. Quality Gates → Create
2. Add conditions:

```
Condition: Coverage on New Code
Operator: is less than
Value: 80%

Condition: Bugs
Operator: is greater than
Value: 0
```

### Quality Gate in CI/CD

```bash
# Analysis will fail if quality gate fails
mvn sonar:sonar -Dsonar.qualitygate.wait=true
```

---

## Jenkins Integration

### Install SonarQube Scanner Plugin

1. Manage Jenkins → Plugins
2. Search "SonarQube Scanner"
3. Install

### Configure SonarQube in Jenkins

1. Manage Jenkins → System
2. SonarQube Servers → Add SonarQube
   - Name: `sonarqube`
   - Server URL: `http://localhost:9000`
   - Server authentication token: (add credential)

### Jenkinsfile with SonarQube

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven 3.9'
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/user/my-app.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
    }
}
```

### Pipeline Flow with SonarQube

```
┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐
│  Build   │───▶│   Test   │───▶│ SonarQube│───▶│ Quality  │
│          │    │          │    │ Analysis │    │  Gate    │
└──────────┘    └──────────┘    └──────────┘    └────┬─────┘
                                                     │
                                              Pass   │   Fail
                                                ↓    ↓
                                           ┌──────┐ ┌──────┐
                                           │Deploy│ │ Stop │
                                           └──────┘ └──────┘
```

---

## Understanding the Dashboard

### Project Overview

```
┌─────────────────────────────────────────────────────────┐
│ My Spring App                      Quality Gate: Passed │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  Reliability    Security    Maintainability   Coverage  │
│     A              A              B             82.3%   │
│   0 Bugs       0 Vulns      12 Smells                   │
│                                                          │
│  Duplications: 2.1%        Lines of Code: 15,432        │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

### Ratings Explained

| Rating | Meaning |
|--------|---------|
| **A** | No issues or minimal debt |
| **B** | Slight technical debt |
| **C** | Moderate technical debt |
| **D** | Significant technical debt |
| **E** | Severe technical debt |

### Issue Details

Click on any metric to see details:
```
Bug: NullPointerException risk
├── File: UserService.java:45
├── Severity: Critical
├── Effort: 10 min
└── Explanation: "user" can be null here
```

---

## Common Code Smells

### 1. Duplicated Code

```java
// Before: Duplicated
public void processUser(User user) {
    log.info("Processing user: " + user.getName());
    validate(user);
    save(user);
}

public void processAdmin(Admin admin) {
    log.info("Processing admin: " + admin.getName());  // Duplicate!
    validate(admin);
    save(admin);
}

// After: Extracted
public void process(Person person) {
    log.info("Processing: " + person.getName());
    validate(person);
    save(person);
}
```

### 2. Long Methods

```java
// Before: 100+ lines
public void processOrder(Order order) {
    // validate
    // calculate totals
    // apply discounts
    // check inventory
    // process payment
    // send notifications
}

// After: Split into smaller methods
public void processOrder(Order order) {
    validateOrder(order);
    calculateTotals(order);
    applyDiscounts(order);
    checkInventory(order);
    processPayment(order);
    sendNotifications(order);
}
```

### 3. Too Many Parameters

```java
// Before: Too many parameters
public User createUser(String name, String email, int age,
                       String address, String phone, String dept) {
    // ...
}

// After: Use object
public User createUser(UserRequest request) {
    // ...
}
```

---

## Best Practices

### 1. Analyze Early and Often

```bash
# Run analysis on every commit
# In your CI pipeline
mvn sonar:sonar
```

### 2. Focus on New Code

```
Quality Gate for New Code:
├── Coverage > 80%
├── No new bugs
└── No new vulnerabilities
```

### 3. Fix Issues Incrementally

```
Week 1: Fix all Critical issues
Week 2: Fix all Major issues
Week 3: Improve coverage to 70%
Week 4: Improve coverage to 80%
```

### 4. Use Branch Analysis

```properties
# Analyze feature branches
sonar.branch.name=feature/user-auth
```

### 5. Exclude Generated Code

```properties
sonar.exclusions=**/generated/**,**/*DTO.java
```

---

## Troubleshooting

### Common Issues

| Issue | Solution |
|-------|----------|
| Analysis fails | Check SonarQube logs |
| No coverage | Ensure JaCoCo report exists |
| Wrong language | Check file extensions |
| Memory error | Increase scanner memory |

### Checking Logs

```bash
# Docker logs
docker logs sonarqube

# Scanner logs
mvn sonar:sonar -X

# Check web logs
http://localhost:9000/admin/system
```

### Memory Settings

```bash
# Increase scanner memory
export SONAR_SCANNER_OPTS="-Xmx512m"
```

---

## Complete Example

### Project Structure

```
my-spring-app/
├── src/
│   ├── main/java/
│   └── test/java/
├── pom.xml
├── sonar-project.properties
└── Jenkinsfile
```

### pom.xml (Key Parts)

```xml
<properties>
    <sonar.host.url>http://localhost:9000</sonar.host.url>
    <sonar.coverage.jacoco.xmlReportPaths>
        ${project.build.directory}/site/jacoco/jacoco.xml
    </sonar.coverage.jacoco.xmlReportPaths>
</properties>

<build>
    <plugins>
        <plugin>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.11</version>
            <executions>
                <execution>
                    <goals><goal>prepare-agent</goal></goals>
                </execution>
                <execution>
                    <id>report</id>
                    <phase>test</phase>
                    <goals><goal>report</goal></goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

### Complete Jenkinsfile

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven 3.9'
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/user/my-spring-app.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Package') {
            when {
                expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
            }
            steps {
                sh 'mvn package -DskipTests'
                archiveArtifacts 'target/*.jar'
            }
        }
    }

    post {
        failure {
            echo 'Pipeline failed - check SonarQube for details'
        }
    }
}
```

---

## Summary

| Concept | Key Points |
|---------|------------|
| **SonarQube** | Code quality platform |
| **Quality Gate** | Pass/fail conditions for code |
| **Bugs** | Actual code errors |
| **Vulnerabilities** | Security issues |
| **Code Smells** | Maintainability issues |
| **Coverage** | Percentage of code tested |

### Quick Commands

```bash
# Start SonarQube (Docker)
docker run -d -p 9000:9000 sonarqube:community

# Analyze Maven project
mvn sonar:sonar -Dsonar.token=xxx

# Analyze with quality gate check
mvn sonar:sonar -Dsonar.qualitygate.wait=true
```

### Key Metrics to Track

- **Coverage**: Aim for 80%+
- **Bugs**: Should be 0
- **Vulnerabilities**: Should be 0
- **Code Smells**: Minimize
- **Duplications**: Under 5%

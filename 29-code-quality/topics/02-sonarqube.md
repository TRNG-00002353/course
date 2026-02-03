# SonarQube

## Overview

SonarQube is an open-source platform for continuous inspection of code quality. It performs automatic reviews with static analysis to detect bugs, code smells, and security vulnerabilities.

---

## What is SonarQube?

### Core Features

```
┌─────────────────────────────────────────────────────────────┐
│                    SonarQube Platform                       │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │    Bugs     │  │ Code Smells │  │ Vulnerabil- │         │
│  │  Detection  │  │  Detection  │  │   ities     │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
│                                                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │   Code      │  │ Duplications│  │   Quality   │         │
│  │  Coverage   │  │   Tracking  │  │    Gates    │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
└─────────────────────────────────────────────────────────────┘
```

### Supported Languages

SonarQube supports 30+ languages including:
- Java, Kotlin, Scala
- JavaScript, TypeScript
- Python, Go, Ruby
- C, C++, C#
- HTML, CSS, XML

---

## SonarQube Architecture

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│  Developer  │    │   CI/CD     │    │  SonarQube  │
│  Workstation│    │   Server    │    │   Server    │
└──────┬──────┘    └──────┬──────┘    └──────┬──────┘
       │                  │                  │
       │  sonar-scanner   │  sonar-scanner   │
       │─────────────────>│─────────────────>│
       │                  │                  │
       │                  │    Analysis      │
       │                  │    Results       │
       │                  │<─────────────────│
       │                  │                  │
       │                  │                  ▼
       │                  │           ┌─────────────┐
       │                  │           │  Database   │
       │                  │           │ (PostgreSQL)│
       │                  │           └─────────────┘
       │                  │                  │
       ▼                  ▼                  ▼
┌─────────────────────────────────────────────────┐
│              SonarQube Dashboard                │
│         (View results in browser)               │
└─────────────────────────────────────────────────┘
```

### Components

| Component | Purpose |
|-----------|---------|
| **SonarQube Server** | Processes analysis reports, stores results |
| **SonarQube Database** | Stores configuration and analysis history |
| **SonarScanner** | Analyzes code and sends reports to server |
| **SonarLint** | IDE plugin for real-time feedback |

---

## SonarQube Dashboard

### Projects Overview

The main dashboard shows all analyzed projects with key metrics:

```
┌─────────────────────────────────────────────────────────────────┐
│  Projects                                           [+ Create]  │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ my-spring-app                              ✓ Passed     │   │
│  │ ─────────────────────────────────────────────────────── │   │
│  │ Bugs: 0    Vulnerabilities: 0    Code Smells: 12       │   │
│  │ Coverage: 78.5%    Duplications: 2.1%                  │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ payment-service                            ✗ Failed     │   │
│  │ ─────────────────────────────────────────────────────── │   │
│  │ Bugs: 3    Vulnerabilities: 2    Code Smells: 45       │   │
│  │ Coverage: 45.2%    Duplications: 8.7%                  │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### Project Dashboard

Detailed view for a specific project:

```
┌─────────────────────────────────────────────────────────────────┐
│  my-spring-app                                                  │
│  main branch | Last analysis: 2 hours ago                       │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Quality Gate: ✓ Passed                                         │
│                                                                 │
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────┐            │
│  │     Bugs     │ │Vulnerabilit. │ │ Code Smells  │            │
│  │      0       │ │      0       │ │     12       │            │
│  │    A rating  │ │   A rating   │ │   A rating   │            │
│  └──────────────┘ └──────────────┘ └──────────────┘            │
│                                                                 │
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────┐            │
│  │   Coverage   │ │ Duplications │ │    Lines     │            │
│  │    78.5%     │ │    2.1%      │ │   12,450     │            │
│  └──────────────┘ └──────────────┘ └──────────────┘            │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### Issue Types

#### Bugs

Reliability issues that represent something wrong in the code.

```java
// BUG: Null pointer dereference
public String getUserName(User user) {
    return user.getName().toUpperCase();  // user could be null
}

// FIXED
public String getUserName(User user) {
    return user != null ? user.getName().toUpperCase() : "";
}
```

#### Vulnerabilities

Security issues that could be exploited.

```java
// VULNERABILITY: SQL Injection
public User findUser(String username) {
    String sql = "SELECT * FROM users WHERE name = '" + username + "'";
    return jdbcTemplate.queryForObject(sql, User.class);
}

// FIXED: Use parameterized query
public User findUser(String username) {
    String sql = "SELECT * FROM users WHERE name = ?";
    return jdbcTemplate.queryForObject(sql, User.class, username);
}
```

#### Code Smells

Maintainability issues that make code harder to understand or modify.

```java
// CODE SMELL: Empty catch block
try {
    processFile(file);
} catch (IOException e) {
    // Ignored - bad practice!
}

// FIXED: Handle or log the exception
try {
    processFile(file);
} catch (IOException e) {
    logger.error("Failed to process file: {}", file, e);
    throw new ProcessingException("File processing failed", e);
}
```

#### Security Hotspots

Code that needs manual review for security implications.

```java
// HOTSPOT: Hardcoded credentials need review
private static final String API_KEY = "abc123";  // Review needed

// BETTER: Use environment variables or secrets management
private final String apiKey = System.getenv("API_KEY");
```

---

## Metrics Explained

### Reliability (Bugs)

| Rating | Criteria |
|--------|----------|
| A | 0 Bugs |
| B | 1+ Minor Bug |
| C | 1+ Major Bug |
| D | 1+ Critical Bug |
| E | 1+ Blocker Bug |

### Security (Vulnerabilities)

| Rating | Criteria |
|--------|----------|
| A | 0 Vulnerabilities |
| B | 1+ Minor Vulnerability |
| C | 1+ Major Vulnerability |
| D | 1+ Critical Vulnerability |
| E | 1+ Blocker Vulnerability |

### Maintainability (Code Smells)

Based on technical debt ratio:

| Rating | Technical Debt Ratio |
|--------|---------------------|
| A | ≤ 5% |
| B | 6-10% |
| C | 11-20% |
| D | 21-50% |
| E | > 50% |

### Coverage

```
                Coverage
        ┌───────────────────┐
   100% │ ████████████████  │ Excellent
    80% │ ████████████      │ Good
    60% │ █████████         │ Acceptable
    40% │ ██████            │ Poor
    20% │ ███               │ Critical
     0% │                   │ No Tests
        └───────────────────┘
```

### Duplications

```
Duplications %    Assessment
     0-3%         Excellent
     3-5%         Good
     5-10%        Acceptable
     10-20%       Should reduce
     20%+         Critical - needs refactoring
```

---

## Quality Gates

Quality gates are pass/fail conditions that determine if code meets the required quality standard.

### Default Quality Gate (Sonar way)

```
┌─────────────────────────────────────────────────────────────┐
│                    Quality Gate: Sonar way                  │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Condition                              Required   Status   │
│  ─────────────────────────────────────────────────────────  │
│  Coverage on New Code                   ≥ 80%      ✓        │
│  Duplicated Lines on New Code           ≤ 3%       ✓        │
│  Maintainability Rating on New Code     A          ✓        │
│  Reliability Rating on New Code         A          ✓        │
│  Security Rating on New Code            A          ✓        │
│  Security Hotspots Reviewed             100%       ✓        │
│                                                             │
│  Overall Status: ✓ Passed                                   │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Custom Quality Gate

```
┌─────────────────────────────────────────────────────────────┐
│                Quality Gate: My Project                     │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Condition                              Required   Status   │
│  ─────────────────────────────────────────────────────────  │
│  Bugs                                   = 0        ✓        │
│  Vulnerabilities                        = 0        ✓        │
│  Code Smells                            ≤ 10       ✗ (12)   │
│  Coverage                               ≥ 70%      ✓        │
│  Duplicated Lines                       ≤ 5%       ✓        │
│                                                             │
│  Overall Status: ✗ Failed                                   │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### New Code vs Overall Code

SonarQube distinguishes between:

- **New Code**: Changes since the last version/period (focus here!)
- **Overall Code**: Entire codebase

> **Best Practice**: Focus on keeping new code clean. Don't let quality debt grow.

---

## Quality Profiles

Quality profiles define the rules used during analysis.

### Built-in Profiles

```
┌─────────────────────────────────────────────────────────────┐
│  Quality Profiles for Java                                  │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Name                    Rules    Default                   │
│  ─────────────────────────────────────────────────────────  │
│  Sonar way               400      ✓                         │
│  FindBugs + FB-Contrib   750                                │
│  My Custom Profile       520                                │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### Rule Severities

| Severity | Description | Example |
|----------|-------------|---------|
| **Blocker** | High probability bug | Null pointer, infinite loop |
| **Critical** | Security or likely bug | SQL injection, unhandled exception |
| **Major** | Quality or productivity | Code smell, complexity |
| **Minor** | Small quality issue | Naming convention |
| **Info** | Informational | TODO comments |

---

## Setting Up SonarQube

### 1. Maven Project Configuration

Add to `pom.xml`:

```xml
<properties>
    <sonar.projectKey>my-project</sonar.projectKey>
    <sonar.projectName>My Project</sonar.projectName>
    <sonar.host.url>http://localhost:9000</sonar.host.url>
    <sonar.login>${env.SONAR_TOKEN}</sonar.login>
    <sonar.java.coveragePlugin>jacoco</sonar.java.coveragePlugin>
</properties>

<build>
    <plugins>
        <!-- JaCoCo for coverage -->
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
    </plugins>
</build>
```

### 2. Run Analysis

```bash
# Run tests with coverage, then analyze
mvn clean verify sonar:sonar

# Or with explicit server URL
mvn sonar:sonar \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=your_token_here
```

### 3. Gradle Configuration

```groovy
plugins {
    id "org.sonarqube" version "4.4.1.3373"
    id "jacoco"
}

sonarqube {
    properties {
        property "sonar.projectKey", "my-project"
        property "sonar.projectName", "My Project"
        property "sonar.host.url", "http://localhost:9000"
        property "sonar.token", System.getenv("SONAR_TOKEN")
    }
}

jacocoTestReport {
    reports {
        xml.required = true
    }
}

tasks.sonarqube.dependsOn jacocoTestReport
```

```bash
# Run analysis
./gradlew test jacocoTestReport sonarqube
```

---

## CI/CD Integration

### GitHub Actions

```yaml
name: Build and Analyze

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v4
        with:
          fetch-depth: 0  # Full history for accurate blame

      - name: Set up JDK 21
        uses: actions/setup-java@v4
        with:
          java-version: '21'
          distribution: 'temurin'

      - name: Cache SonarQube packages
        uses: actions/cache@v4
        with:
          path: ~/.sonar/cache
          key: ${{ runner.os }}-sonar

      - name: Build and analyze
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
          SONAR_HOST_URL: ${{ secrets.SONAR_HOST_URL }}
        run: |
          mvn -B verify sonar:sonar \
            -Dsonar.host.url=$SONAR_HOST_URL \
            -Dsonar.token=$SONAR_TOKEN
```

### Jenkins Pipeline

```groovy
pipeline {
    agent any

    stages {
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
                withSonarQubeEnv('SonarQube') {
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
    }
}
```

---

## SonarLint IDE Plugin

SonarLint provides real-time feedback in your IDE.

### Features

- Instant feedback as you code
- Same rules as SonarQube server
- Works offline
- Supports IntelliJ, Eclipse, VS Code

### Installation (IntelliJ)

1. File → Settings → Plugins
2. Search "SonarLint"
3. Install and restart

### Connected Mode

Link SonarLint to your SonarQube server:

1. Settings → Tools → SonarLint
2. Add SonarQube Connection
3. Enter server URL and token
4. Bind project to server project

Benefits of connected mode:
- Same rules as server
- Synced quality profile
- Suppressed issues sync

---

## Best Practices

### 1. Fix Issues Promptly

```
Priority order:
1. Blocker bugs and vulnerabilities (immediately)
2. Critical issues (same day)
3. Major issues (same sprint)
4. Minor issues (when convenient)
```

### 2. Don't Suppress Without Reason

```java
// BAD: Suppressing without justification
@SuppressWarnings("squid:S1135")
public void myMethod() { }

// GOOD: Document why suppression is needed
@SuppressWarnings("squid:S1135")  // TODO tracked in JIRA-1234
public void myMethod() { }
```

### 3. Focus on New Code

Keep the "Clean as You Code" philosophy:
- New code must pass quality gate
- Gradually improve existing code
- Don't add to technical debt

### 4. Integrate in CI/CD

```
Developer  →  SonarLint  →  Commit  →  CI  →  SonarQube  →  Quality Gate
    ↑                                                            │
    └────────────────── Fail build if gate fails ────────────────┘
```

---

## Summary

| Component | Purpose |
|-----------|---------|
| **SonarQube Server** | Central analysis platform |
| **SonarScanner** | Analyzes code, sends reports |
| **SonarLint** | IDE plugin for instant feedback |
| **Quality Gate** | Pass/fail criteria for releases |
| **Quality Profile** | Set of rules for analysis |

### Key Metrics

| Metric | Target |
|--------|--------|
| Bugs | 0 |
| Vulnerabilities | 0 |
| Code Smells | Minimize |
| Coverage (new code) | ≥ 80% |
| Duplications | ≤ 3% |

### Workflow

1. Code with SonarLint feedback
2. Run tests with coverage
3. Analyze in CI/CD pipeline
4. Check quality gate status
5. Fix issues before merge

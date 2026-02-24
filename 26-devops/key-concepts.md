# DevOps Key Concepts

## Quick Reference

Essential concepts for DevOps and CI/CD.

---

## 1. DevOps Fundamentals

### What is DevOps?

DevOps = Development + Operations working together to deliver software faster and more reliably.

### Core Principles

| Principle | Description |
|-----------|-------------|
| **Automation** | Automate builds, tests, deployments |
| **Collaboration** | Dev and Ops work as one team |
| **Fast Feedback** | Know quickly if something breaks |
| **Continuous Improvement** | Iterate and improve processes |

### DevOps Lifecycle

```
Plan → Code → Build → Test → Deploy → Operate → Monitor → Plan...
```

---

## 2. CI/CD Pipeline

### Pipeline Stages

```
┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐
│  Source  │───▶│  Build   │───▶│   Test   │───▶│  Deploy  │
└──────────┘    └──────────┘    └──────────┘    └──────────┘
   Git Push      Compile         Run Tests       Release
```

### CI vs CD

| Term | Definition |
|------|------------|
| **CI (Continuous Integration)** | Automatically build and test on every commit |
| **CD (Continuous Delivery)** | Code always ready to deploy (manual trigger) |
| **CD (Continuous Deployment)** | Fully automated deployment to production |

---

## 3. Jenkins *(Optional)*

### Jenkinsfile Structure

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
        stage('Deploy') {
            steps {
                sh './deploy.sh'
            }
        }
    }

    post {
        success { echo 'Build succeeded!' }
        failure { echo 'Build failed!' }
    }
}
```

### Key Concepts

| Term | Description |
|------|-------------|
| **Pipeline** | Scripted workflow in Jenkinsfile |
| **Stage** | Logical group of steps (Build, Test, Deploy) |
| **Step** | Individual command or action |
| **Agent** | Machine that runs the build |
| **Artifact** | Output files from build |

### Common Commands

```groovy
sh 'command'              // Run shell command
git 'repo-url'            // Clone repository
archiveArtifacts '*.jar'  // Save build output
junit 'reports/*.xml'     // Publish test results
```

---

## 4. SonarQube *(Optional)*

### What It Detects

| Category | Description |
|----------|-------------|
| **Bugs** | Actual code errors |
| **Vulnerabilities** | Security issues |
| **Code Smells** | Maintainability problems |
| **Coverage** | Percentage of code tested |

### Quality Gate

Pass/fail conditions for your code:
```
Coverage > 80%
Bugs = 0
Vulnerabilities = 0
Code Smells < 10
```

### Integration with Jenkins

```groovy
stage('SonarQube Analysis') {
    steps {
        withSonarQubeEnv('sonarqube') {
            sh 'mvn sonar:sonar'
        }
    }
}

stage('Quality Gate') {
    steps {
        waitForQualityGate abortPipeline: true
    }
}
```

---

## 5. Deployment Strategies

### Rolling Deployment
Update instances one at a time.
```
[v1] [v1] [v1] → [v2] [v1] [v1] → [v2] [v2] [v1] → [v2] [v2] [v2]
```

### Blue-Green Deployment
Run two environments, switch traffic.
```
Blue (v1) ← Traffic
Green (v2) idle

After switch:
Blue (v1) idle
Green (v2) ← Traffic
```

### Canary Deployment
Route small % of traffic to new version first.
```
95% → v1 (stable)
5%  → v2 (canary)
```

---

## 6. Quick Commands *(Optional)*

### Docker (for Jenkins/SonarQube)

```bash
# Run Jenkins
docker run -d -p 8080:8080 jenkins/jenkins:lts

# Run SonarQube
docker run -d -p 9000:9000 sonarqube:community
```

### Maven with SonarQube

```bash
# Run tests with coverage
mvn clean test jacoco:report

# Run SonarQube analysis
mvn sonar:sonar -Dsonar.token=xxx
```

### Jenkins Pipeline

```bash
# Trigger build via CLI
curl -X POST http://jenkins:8080/job/my-job/build
```

---

## 7. Best Practices Checklist

### CI/CD
- [ ] Commit frequently (multiple times/day)
- [ ] Run tests on every commit
- [ ] Keep builds fast (< 10 minutes)
- [ ] Fix broken builds immediately
- [ ] Automate deployments

### Code Quality
- [ ] Maintain 80%+ test coverage
- [ ] Zero bugs and vulnerabilities
- [ ] Run SonarQube on every build
- [ ] Use quality gates

### Security
- [ ] Never commit secrets
- [ ] Use credential management
- [ ] Scan for vulnerabilities
- [ ] Use least privilege

---

## 8. Project Structure

```
my-project/
├── src/
│   ├── main/java/
│   └── test/java/
├── pom.xml
├── Jenkinsfile              # CI/CD pipeline
├── sonar-project.properties # SonarQube config
├── Dockerfile               # Container build
└── docker-compose.yml       # Local development
```

---

## 9. Key Metrics

| Metric | Target |
|--------|--------|
| **Deployment Frequency** | Multiple times/day |
| **Lead Time** | < 1 day |
| **Change Failure Rate** | < 15% |
| **Mean Time to Recovery** | < 1 hour |
| **Test Coverage** | > 80% |

---

## 10. Troubleshooting

| Issue | Solution |
|-------|----------|
| Build fails | Check console output, fix errors |
| Tests failing | Run locally, check test reports |
| SonarQube issues | Review quality gate conditions |
| Pipeline stuck | Check agent availability |
| Deploy fails | Check logs, verify permissions |

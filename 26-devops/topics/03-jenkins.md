# Jenkins

## What is Jenkins?

Jenkins is an **open-source automation server** that helps automate building, testing, and deploying software. It's one of the most popular CI/CD tools in the industry.

**Think of Jenkins as a robot assistant**: You tell it what to do (build, test, deploy), and it does it automatically every time someone pushes code.

```
┌─────────────────────────────────────────────────────────┐
│                        Jenkins                           │
├─────────────────────────────────────────────────────────┤
│                                                          │
│   Git Push ──▶ Jenkins ──▶ Build ──▶ Test ──▶ Deploy   │
│                  │                                       │
│                  └── Notifies team of results           │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

### Why Jenkins?

| Feature | Benefit |
|---------|---------|
| **Free & Open Source** | No licensing costs |
| **Highly Extensible** | 1800+ plugins available |
| **Self-Hosted** | Full control over your data |
| **Large Community** | Easy to find help |
| **Platform Independent** | Runs on any OS |

---

## Installing Jenkins

### Option 1: Using Docker (Recommended)

```bash
# Pull Jenkins image
docker pull jenkins/jenkins:lts

# Run Jenkins
docker run -d \
  --name jenkins \
  -p 8080:8080 \
  -p 50000:50000 \
  -v jenkins_home:/var/jenkins_home \
  jenkins/jenkins:lts

# Get initial admin password
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

### Option 2: Direct Installation

**On Ubuntu/Debian:**
```bash
# Add Jenkins repository
curl -fsSL https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key | sudo tee \
  /usr/share/keyrings/jenkins-keyring.asc > /dev/null

echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
  /etc/apt/sources.list.d/jenkins.list > /dev/null

# Install
sudo apt update
sudo apt install jenkins

# Start Jenkins
sudo systemctl start jenkins
sudo systemctl enable jenkins

# Get initial password
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

**On macOS:**
```bash
brew install jenkins-lts
brew services start jenkins-lts
```

### Initial Setup

1. Open browser: `http://localhost:8080`
2. Enter the initial admin password
3. Install suggested plugins
4. Create admin user
5. Configure Jenkins URL

---

## Jenkins Interface

### Dashboard Overview

```
┌─────────────────────────────────────────────────────────┐
│ Jenkins Dashboard                                        │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  [New Item]  [People]  [Build History]  [Manage Jenkins]│
│                                                          │
│  Jobs:                                                   │
│  ├── my-spring-app     ● (last build successful)       │
│  ├── my-angular-app    ● (last build successful)       │
│  └── my-api-tests      ○ (last build failed)           │
│                                                          │
│  Build Queue: Empty                                      │
│  Build Executor Status: Idle                             │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

### Key Concepts

| Term | Description |
|------|-------------|
| **Job/Project** | A task Jenkins runs (build, test, deploy) |
| **Build** | One execution of a job |
| **Pipeline** | A series of stages defining CI/CD workflow |
| **Node/Agent** | Machine that runs builds |
| **Workspace** | Directory where build happens |
| **Artifact** | Files produced by a build |

---

## Creating Your First Job

### Freestyle Project

1. Click **New Item**
2. Enter name: `my-first-job`
3. Select **Freestyle project**
4. Configure:

**Source Code Management:**
```
Git:
  Repository URL: https://github.com/username/my-app.git
  Branch: */main
```

**Build Triggers:**
```
☑ Poll SCM: H/5 * * * *  (check every 5 minutes)
```

**Build Steps:**
```bash
# Execute shell
echo "Building the project..."
mvn clean package
```

**Post-build Actions:**
```
Archive artifacts: target/*.jar
```

### Running the Job

1. Click **Build Now**
2. Watch the build progress
3. Check **Console Output** for logs
4. View artifacts after success

---

## Jenkins Pipeline

### What is a Pipeline?

A Pipeline is a **script that defines your CI/CD workflow**. It's written in a file called `Jenkinsfile` that lives in your repository.

### Pipeline Types

| Type | Description |
|------|-------------|
| **Declarative** | Simpler, structured syntax (recommended) |
| **Scripted** | More flexible, Groovy-based |

### Declarative Pipeline Structure

```groovy
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building...'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }
    }
}
```

---

## Jenkinsfile Examples

### Spring Boot Application

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
                    url: 'https://github.com/username/spring-app.git'
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
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar',
                                 fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
```

### Angular Application

```groovy
pipeline {
    agent any

    tools {
        nodejs 'Node 18'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/username/angular-app.git'
            }
        }

        stage('Install Dependencies') {
            steps {
                sh 'npm ci'
            }
        }

        stage('Lint') {
            steps {
                sh 'npm run lint'
            }
        }

        stage('Test') {
            steps {
                sh 'npm run test -- --watch=false --browsers=ChromeHeadless'
            }
        }

        stage('Build') {
            steps {
                sh 'npm run build -- --configuration=production'
            }
        }

        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'dist/**/*',
                                 fingerprint: true
            }
        }
    }
}
```

### Docker Build and Push

```groovy
pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'myusername/myapp'
        DOCKER_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                sh "docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest"
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                    sh "docker push ${DOCKER_IMAGE}:latest"
                }
            }
        }
    }
}
```

---

## Pipeline Syntax

### Agent

Where the pipeline runs:

```groovy
// Run on any available agent
agent any

// Run on specific agent
agent { label 'linux' }

// Run in Docker container
agent {
    docker {
        image 'maven:3.9-eclipse-temurin-17'
    }
}

// No agent (stages define their own)
agent none
```

### Stages and Steps

```groovy
stages {
    stage('Stage Name') {
        steps {
            // Shell command
            sh 'mvn clean package'

            // Windows batch
            bat 'npm install'

            // Print message
            echo 'Hello, Jenkins!'

            // Change directory
            dir('subdirectory') {
                sh 'ls -la'
            }
        }
    }
}
```

### Environment Variables

```groovy
pipeline {
    agent any

    environment {
        // Pipeline-level variables
        APP_NAME = 'myapp'
        VERSION = '1.0.0'
    }

    stages {
        stage('Build') {
            environment {
                // Stage-level variables
                BUILD_ENV = 'production'
            }
            steps {
                sh 'echo "Building $APP_NAME version $VERSION"'
                sh 'echo "Environment: $BUILD_ENV"'
            }
        }
    }
}
```

### Credentials

```groovy
// Username/Password
withCredentials([usernamePassword(
    credentialsId: 'my-creds',
    usernameVariable: 'USER',
    passwordVariable: 'PASS'
)]) {
    sh 'curl -u $USER:$PASS https://api.example.com'
}

// Secret text
withCredentials([string(credentialsId: 'api-key', variable: 'API_KEY')]) {
    sh 'curl -H "Authorization: $API_KEY" https://api.example.com'
}

// SSH Key
withCredentials([sshUserPrivateKey(
    credentialsId: 'ssh-key',
    keyFileVariable: 'SSH_KEY'
)]) {
    sh 'ssh -i $SSH_KEY user@server'
}
```

### Conditional Execution

```groovy
stage('Deploy to Production') {
    when {
        branch 'main'
    }
    steps {
        sh './deploy-prod.sh'
    }
}

stage('Deploy to Staging') {
    when {
        branch 'develop'
    }
    steps {
        sh './deploy-staging.sh'
    }
}

stage('Run on Tag') {
    when {
        tag 'v*'
    }
    steps {
        sh './release.sh'
    }
}
```

### Post Actions

```groovy
post {
    always {
        // Always runs (cleanup)
        cleanWs()
    }
    success {
        // Only on success
        echo 'Build succeeded!'
        slackSend message: "Build succeeded: ${env.JOB_NAME}"
    }
    failure {
        // Only on failure
        echo 'Build failed!'
        slackSend message: "Build failed: ${env.JOB_NAME}"
    }
    unstable {
        // Tests passed but quality gate failed
        echo 'Build unstable'
    }
}
```

---

## Build Triggers

### Webhook (GitHub/GitLab)

Configure in Jenkins job:
```
☑ GitHub hook trigger for GITScm polling
```

In GitHub: Settings → Webhooks → Add webhook:
```
Payload URL: http://jenkins-server:8080/github-webhook/
Content type: application/json
```

### Poll SCM

Check repository periodically:
```
H/5 * * * *    # Every 5 minutes
H * * * *       # Every hour
H 2 * * *       # Daily at 2 AM
```

### Build Periodically

Run on schedule (regardless of changes):
```
H 0 * * *       # Daily at midnight
H 0 * * 1-5     # Weekdays at midnight
```

### Upstream Job

Trigger after another job completes:
```
Build after other projects are built: my-other-job
```

---

## Essential Plugins

### Must-Have Plugins

| Plugin | Purpose |
|--------|---------|
| **Git** | Git integration |
| **Pipeline** | Pipeline support |
| **Blue Ocean** | Modern UI |
| **Credentials** | Secure credential storage |
| **Docker Pipeline** | Docker support |
| **JUnit** | Test reports |
| **Slack Notification** | Slack alerts |
| **SonarQube Scanner** | Code quality |

### Installing Plugins

1. Manage Jenkins → Manage Plugins
2. Available tab
3. Search for plugin
4. Install without restart

---

## Jenkins with Docker

### Running Jenkins in Docker

```yaml
# docker-compose.yml
version: '3.8'
services:
  jenkins:
    image: jenkins/jenkins:lts
    ports:
      - "8080:8080"
      - "50000:50000"
    volumes:
      - jenkins_home:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
    environment:
      - JAVA_OPTS=-Djenkins.install.runSetupWizard=false

volumes:
  jenkins_home:
```

### Building Docker Images in Jenkins

```groovy
pipeline {
    agent {
        docker {
            image 'docker:latest'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    stages {
        stage('Build Image') {
            steps {
                sh 'docker build -t myapp:latest .'
            }
        }
    }
}
```

---

## Best Practices

### 1. Pipeline as Code

Store Jenkinsfile in your repository:
```
my-project/
├── src/
├── pom.xml
└── Jenkinsfile    ← Pipeline definition
```

### 2. Keep Pipelines Fast

```groovy
// Run stages in parallel
stage('Tests') {
    parallel {
        stage('Unit Tests') {
            steps { sh 'mvn test' }
        }
        stage('Integration Tests') {
            steps { sh 'mvn verify' }
        }
    }
}
```

### 3. Clean Workspace

```groovy
post {
    always {
        cleanWs()
    }
}
```

### 4. Use Shared Libraries

For common code across pipelines:
```groovy
@Library('my-shared-library') _

pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                mySharedBuildStep()
            }
        }
    }
}
```

### 5. Secure Credentials

- Never hardcode passwords
- Use Jenkins Credentials store
- Rotate credentials regularly

---

## Troubleshooting

### Common Issues

| Issue | Solution |
|-------|----------|
| Build stuck | Check agent availability |
| Permission denied | Check file permissions, run as jenkins user |
| Git clone fails | Check credentials, network |
| Out of disk space | Clean old builds, workspaces |
| Plugin conflicts | Check plugin compatibility |

### Viewing Logs

```
Build → Console Output    # Build logs
Manage Jenkins → System Log    # Jenkins logs
```

### Restarting Jenkins

```bash
# Via URL
http://localhost:8080/safeRestart

# Via CLI
sudo systemctl restart jenkins
```

---

## Summary

| Concept | Key Points |
|---------|------------|
| **Jenkins** | Open-source CI/CD automation server |
| **Job** | Task Jenkins executes (build, test, deploy) |
| **Pipeline** | Scripted workflow defined in Jenkinsfile |
| **Agent** | Machine that runs builds |
| **Stages** | Logical groups of steps |

### Essential Commands

```groovy
// Jenkinsfile basics
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
    post {
        always { cleanWs() }
    }
}
```

### Quick Reference

```bash
# Docker
docker run -p 8080:8080 jenkins/jenkins:lts

# Get admin password
cat /var/lib/jenkins/secrets/initialAdminPassword
```

## Next Topic

Continue to [SonarQube](./04-sonarqube.md) to learn about code quality analysis.

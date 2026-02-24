# DevOps and CI/CD

## Overview

Learn DevOps principles and CI/CD practices. This module covers concepts that are cloud-agnostic and can be applied to any environment. Optional topics cover hands-on experience with Jenkins and SonarQube.

## Learning Objectives

By the end of this module, you will be able to:
- Understand DevOps culture and principles
- Explain CI/CD concepts and benefits
- Apply best practices for continuous integration and delivery
- *(Optional)* Set up and configure Jenkins for automation
- *(Optional)* Create CI/CD pipelines for Java and Angular applications
- *(Optional)* Integrate SonarQube for code quality analysis

---

## Topics Covered

### 1. [DevOps Introduction](./topics/01-devops-introduction.md)
Understanding DevOps culture and principles.

- What is DevOps and why it matters
- Core principles: automation, collaboration, feedback
- DevOps lifecycle
- Key practices and tools overview

### 2. [CI/CD Fundamentals](./topics/02-cicd-fundamentals.md)
Deep dive into continuous integration and delivery.

- Continuous Integration (CI) concepts
- Continuous Delivery vs Deployment
- Pipeline stages and workflows
- Environment strategies
- Deployment patterns (rolling, blue-green, canary)

### 3. [Jenkins](./topics/03-jenkins.md) *(Optional)*
Hands-on with the most popular CI/CD server.

- Installing Jenkins (Docker and native)
- Creating jobs and pipelines
- Jenkinsfile syntax and examples
- Build triggers and webhooks
- Docker integration

### 4. [SonarQube](./topics/04-sonarqube.md) *(Optional)*
Code quality and security analysis.

- Understanding code quality metrics
- Installing and configuring SonarQube
- Quality gates
- Jenkins integration
- Best practices for code quality

---

## Topic Flow

```
┌─────────────────────┐
│ 1. DevOps Intro     │  Culture, principles, tools overview
└──────────┬──────────┘
           ▼
┌─────────────────────┐
│ 2. CI/CD Fundamentals│  Pipeline concepts, strategies
└─────────────────────┘

         OPTIONAL
           │
           ▼
┌─────────────────────┐
│ 3. Jenkins          │  Hands-on CI/CD automation
└──────────┬──────────┘
           ▼
┌─────────────────────┐
│ 4. SonarQube        │  Code quality integration
└─────────────────────┘
```

---

## Tools Used

| Tool | Purpose | Installation |
|------|---------|--------------|
| **Maven** | Build tool (Java) | Local install |
| **npm** | Build tool (Node/Angular) | Local install |
| **Docker** | Container platform | Docker Desktop |
| **Git** | Version control | Local install |
| **Jenkins** *(Optional)* | CI/CD automation server | Docker or native |
| **SonarQube** *(Optional)* | Code quality analysis | Docker |

---

## Sample Pipeline

```
┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐
│   Git    │───▶│  Build   │───▶│   Test   │───▶│  Deploy  │
│   Push   │    │          │    │          │    │          │
└──────────┘    └──────────┘    └──────────┘    └──────────┘
```

---

## Prerequisites

- Basic understanding of Git
- Java or Node.js development experience
- Code editor (VS Code recommended)
- Docker installed *(required only for optional topics)*

---

## Hands-On Exercises *(Optional)*

1. **Set up Jenkins locally** using Docker
2. **Create a pipeline** for a Spring Boot application
3. **Integrate SonarQube** for code quality analysis
4. **Configure quality gates** to fail builds on issues

---

## Additional Resources

- [Jenkins Documentation](https://www.jenkins.io/doc/)
- [SonarQube Documentation](https://docs.sonarsource.com/sonarqube/)
- [CI/CD Best Practices](https://www.atlassian.com/continuous-delivery/principles)

---

**Duration:** 2 days | **Difficulty:** Intermediate | **Prerequisites:** Git basics, Java or Node.js

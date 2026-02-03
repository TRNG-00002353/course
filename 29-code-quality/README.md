# Code Quality

## Overview

Code quality ensures software is maintainable, readable, and free from defects. This module covers metrics, technical debt, code smells, and tools like SonarQube for automated quality analysis.

## Learning Objectives

By the end of this module, you will be able to:
- Understand code quality metrics and their importance
- Identify and address technical debt
- Recognize common code smells and refactoring solutions
- Use SonarQube for automated code analysis
- Integrate quality gates into development workflow

---

## Topics Covered

### 1. [Code Quality Fundamentals](./topics/01-code-quality-fundamentals.md)
Understanding code quality concepts and metrics.

- What is code quality and why it matters
- Code quality metrics (complexity, duplication, coverage)
- Technical debt concepts
- Code smells and refactoring

### 2. [SonarQube](./topics/02-sonarqube.md)
Automated code quality analysis with SonarQube.

- SonarQube overview and architecture
- Dashboard walkthrough
- Quality profiles and rules
- Quality gates
- Integration with CI/CD

---

## Topic Flow

```
┌──────────────────────────┐
│ 1. Code Quality          │  Metrics, debt, smells
│    Fundamentals          │
└───────────┬──────────────┘
            ▼
┌──────────────────────────┐
│ 2. SonarQube             │  Automated analysis
└──────────────────────────┘
```

---

## Key Concepts Summary

| Concept | Description |
|---------|-------------|
| **Code Quality** | Measure of how well code meets standards for maintainability |
| **Technical Debt** | Cost of choosing quick solutions over better approaches |
| **Code Smell** | Surface indication of deeper design problems |
| **Cyclomatic Complexity** | Number of independent paths through code |
| **Code Coverage** | Percentage of code executed by tests |
| **SonarQube** | Platform for continuous code quality inspection |
| **Quality Gate** | Pass/fail criteria for code quality |

---

## Prerequisites

- Java fundamentals
- Basic testing knowledge (JUnit)
- Maven/Gradle basics

---

## Additional Resources

- [SonarQube Documentation](https://docs.sonarqube.org/)
- [Refactoring Guru - Code Smells](https://refactoring.guru/refactoring/smells)
- [Technical Debt Quadrant](https://martinfowler.com/bliki/TechnicalDebtQuadrant.html)

---

**Duration:** 1 day | **Difficulty:** Intermediate | **Prerequisites:** Module 12 (Testing)

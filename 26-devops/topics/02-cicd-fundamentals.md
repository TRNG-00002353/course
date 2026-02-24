# CI/CD Fundamentals

## What is CI/CD?

CI/CD stands for **Continuous Integration** and **Continuous Delivery/Deployment**. It's a practice that automates the building, testing, and deployment of code.

**Think of it like an assembly line**: Instead of building a car by hand (manual deployment), you have an automated factory that builds, tests, and delivers cars consistently every time.

```
Code → Build → Test → Deploy → Monitor
  ↑                               │
  └───────── Feedback ────────────┘
```

---

## Continuous Integration (CI)

### What is CI?

Continuous Integration is the practice of **automatically building and testing code** every time a developer pushes changes.

### How CI Works

```
┌──────────────────────────────────────────────────────────┐
│                    Continuous Integration                  │
├──────────────────────────────────────────────────────────┤
│                                                           │
│   Developer A ──┐                                        │
│                 │     ┌──────────┐    ┌──────────┐       │
│   Developer B ──┼────▶│  Build   │───▶│  Test    │       │
│                 │     │  Server  │    │  Suite   │       │
│   Developer C ──┘     └──────────┘    └────┬─────┘       │
│                                            │              │
│                                     Pass   │  Fail        │
│                                       ↓    ↓              │
│                                   ┌─────┐ ┌─────┐        │
│                                   │ ✓   │ │ ✗   │        │
│                                   │Merge│ │Alert│        │
│                                   └─────┘ └─────┘        │
└──────────────────────────────────────────────────────────┘
```

### CI Benefits

| Benefit | Explanation |
|---------|-------------|
| **Early Bug Detection** | Find issues minutes after code is pushed |
| **Reduced Integration Problems** | Small, frequent merges vs. big scary merges |
| **Faster Development** | Less time debugging integration issues |
| **Confidence** | Know that code works before deploying |

### CI Best Practices

1. **Commit frequently** - Multiple times per day
2. **Don't break the build** - Run tests locally first
3. **Fix broken builds immediately** - Top priority
4. **Keep builds fast** - Under 10 minutes ideally
5. **Test in a clone of production** - Same environment

---

## Continuous Delivery vs Continuous Deployment

### Continuous Delivery (CD)

Code is **always ready to deploy** but requires manual approval.

```
Code → Build → Test → [Ready to Deploy] → Manual Approval → Production
```

### Continuous Deployment (CD)

Code is **automatically deployed** to production after passing tests.

```
Code → Build → Test → [Auto Deploy] → Production
```

### Comparison

| Aspect | Continuous Delivery | Continuous Deployment |
|--------|--------------------|-----------------------|
| **Deployment** | Manual trigger | Fully automatic |
| **Human Review** | Before production | After code review only |
| **Risk** | Lower (human check) | Higher (requires good tests) |
| **Speed** | Fast | Fastest |
| **Best For** | Regulated industries | Fast-moving teams |

---

## The CI/CD Pipeline

### Pipeline Stages

A typical pipeline has these stages:

```
┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐
│  Source  │───▶│  Build   │───▶│   Test   │───▶│  Deploy  │───▶│ Monitor  │
│          │    │          │    │          │    │          │    │          │
│ Git push │    │ Compile  │    │ Run      │    │ Release  │    │ Track    │
│ triggers │    │ Package  │    │ tests    │    │ to env   │    │ health   │
└──────────┘    └──────────┘    └──────────┘    └──────────┘    └──────────┘
```

### Stage Details

#### 1. Source Stage
Triggered when code changes are pushed.

```bash
# Developer pushes code
git push origin main

# Pipeline automatically starts
```

#### 2. Build Stage
Compile code and create deployable artifacts.

```bash
# Java application
mvn clean package

# Node.js application
npm install
npm run build

# Output: JAR file, Docker image, or bundle
```

#### 3. Test Stage
Run automated tests to verify code quality.

```bash
# Unit tests
mvn test
npm test

# Integration tests
mvn verify

# Code quality
sonar-scanner
```

**Types of Tests:**

| Test Type | What It Tests | Speed |
|-----------|---------------|-------|
| **Unit Tests** | Individual functions/methods | Fast (seconds) |
| **Integration Tests** | Components working together | Medium (minutes) |
| **E2E Tests** | Full user workflows | Slow (minutes) |
| **Security Scans** | Vulnerabilities | Varies |

#### 4. Deploy Stage
Release the application to an environment.

- Copy build artifacts to the target server
- Restart the application or service
- Run smoke tests to verify deployment

#### 5. Monitor Stage
Track application health after deployment.

- Check health endpoints
- Monitor logs
- Track metrics
- Alert on issues

---

## Environment Strategy

### Multiple Environments

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│ Development │───▶│   Testing   │───▶│   Staging   │───▶│ Production  │
│             │    │             │    │             │    │             │
│ Developers  │    │ QA Team     │    │ Pre-prod    │    │ Live users  │
│ experiment  │    │ test here   │    │ final check │    │             │
└─────────────┘    └─────────────┘    └─────────────┘    └─────────────┘
```

### Environment Purposes

| Environment | Purpose | Who Uses It |
|-------------|---------|-------------|
| **Development** | Active development | Developers |
| **Testing/QA** | Manual testing | QA team |
| **Staging** | Pre-production testing | Everyone |
| **Production** | Live application | End users |

### Branch Strategy

```
main (production)
  │
  ├── develop (staging)
  │     │
  │     ├── feature/user-login
  │     │
  │     └── feature/payment
  │
  └── hotfix/critical-bug
```

**Deployment Rules:**
- `feature/*` → Build and test only
- `develop` → Deploy to staging
- `main` → Deploy to production

---

## Artifact Management

### What are Artifacts?

Artifacts are the **output of your build** - the deployable files.

| Language | Artifact Type |
|----------|---------------|
| Java | JAR, WAR files |
| Node.js | node_modules, dist folder |
| Angular | Bundled JS/CSS files |
| Docker | Container images |

### Artifact Storage

```
Build Server → Artifact Repository → Deployment
                     │
              ┌──────┴──────┐
              │  Examples:   │
              │  - Nexus     │
              │  - JFrog     │
              │  - Docker Hub│
              │  - S3        │
              └──────────────┘
```

### Versioning Artifacts

```bash
# Semantic versioning
myapp-1.0.0.jar
myapp-1.0.1.jar
myapp-1.1.0.jar

# With build number
myapp-1.0.0-build123.jar

# With commit hash
myapp-1.0.0-abc123f.jar
```

---

## Testing in CI/CD

### Test Pyramid

```
            /\
           /  \
          / E2E \        ← Few, slow, expensive
         /──────\
        /        \
       /Integration\     ← Some, medium speed
      /────────────\
     /              \
    /   Unit Tests   \   ← Many, fast, cheap
   /──────────────────\
```

### Test Best Practices

1. **Run fastest tests first** - Fail fast
2. **Run tests in parallel** - Speed up pipeline
3. **Don't skip tests** - Ever
4. **Mock external services** - Tests should be reliable
5. **Track test coverage** - Aim for 80%+

---

## Summary

| Concept | Key Points |
|---------|------------|
| **CI** | Automatically build and test on every commit |
| **CD** | Automatically deploy (or prepare to deploy) |
| **Pipeline** | Stages: Source → Build → Test → Deploy |
| **Environments** | Dev → Test → Staging → Production |
| **Artifacts** | Build outputs (JAR, Docker image, etc.) |

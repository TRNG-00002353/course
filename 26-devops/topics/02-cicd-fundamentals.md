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

```bash
# Deploy to server
scp app.jar user@server:/app/

# Deploy with Docker
docker push myapp:latest
docker pull myapp:latest && docker run -d myapp:latest

# Deploy to Kubernetes
kubectl apply -f deployment.yaml
```

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

## Pipeline Configuration

### Pipeline as Code

Define your pipeline in a file that lives with your code:

```yaml
# Jenkinsfile (Jenkins)
# .github/workflows/ci.yml (GitHub Actions)
# .gitlab-ci.yml (GitLab CI)
```

### Example Pipeline Structure

```yaml
# Generic pipeline structure
pipeline:
  stages:
    - build
    - test
    - deploy

  build:
    script:
      - mvn clean package

  test:
    script:
      - mvn test

  deploy:
    script:
      - ./deploy.sh
    only:
      - main
```

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

### Example Test Configuration

```yaml
test:
  stages:
    - lint          # Seconds
    - unit-test     # Minutes
    - integration   # Minutes
    - e2e           # Longer

  # Stop if any stage fails
  fail-fast: true
```

---

## Deployment Strategies

### 1. Rolling Deployment

Update instances one at a time.

```
Before:  [v1] [v1] [v1] [v1]
Step 1:  [v2] [v1] [v1] [v1]
Step 2:  [v2] [v2] [v1] [v1]
Step 3:  [v2] [v2] [v2] [v1]
After:   [v2] [v2] [v2] [v2]
```

**Pros:** Zero downtime, gradual rollout
**Cons:** Mixed versions during deployment

### 2. Blue-Green Deployment

Run two identical environments, switch traffic.

```
        Load Balancer
             │
     ┌───────┴───────┐
     ▼               ▼
┌─────────┐    ┌─────────┐
│  Blue   │    │  Green  │
│  (v1)   │    │  (v2)   │
│ ACTIVE  │    │  IDLE   │
└─────────┘    └─────────┘

After switch:
             │
     ┌───────┴───────┐
     ▼               ▼
┌─────────┐    ┌─────────┐
│  Blue   │    │  Green  │
│  (v1)   │    │  (v2)   │
│  IDLE   │    │ ACTIVE  │
└─────────┘    └─────────┘
```

**Pros:** Instant rollback, zero downtime
**Cons:** Requires double infrastructure

### 3. Canary Deployment

Route small percentage of traffic to new version.

```
            Load Balancer
                 │
         ┌───────┴───────┐
         │               │
        95%             5%
         │               │
    ┌────▼────┐    ┌────▼────┐
    │   v1    │    │   v2    │
    │ (stable)│    │(canary) │
    └─────────┘    └─────────┘
```

**Pros:** Low risk, test with real traffic
**Cons:** More complex to set up

---

## Rollback Strategies

### When to Rollback

- Health checks failing
- Error rate increasing
- Performance degrading
- Critical bug discovered

### Rollback Methods

```bash
# 1. Deploy previous version
./deploy.sh --version 1.0.0

# 2. Docker: Use previous image
docker run myapp:1.0.0

# 3. Git: Revert and redeploy
git revert HEAD
git push
# Pipeline deploys reverted code

# 4. Blue-green: Switch back
switch-traffic --to blue
```

### Automatic Rollback

```yaml
deploy:
  script:
    - ./deploy.sh
  health_check:
    url: http://myapp/health
    retries: 3
  on_failure:
    - ./rollback.sh
```

---

## CI/CD Best Practices

### 1. Keep Pipelines Fast

| Practice | Impact |
|----------|--------|
| Run tests in parallel | 2-3x faster |
| Cache dependencies | 50%+ faster |
| Fail fast | Stop on first failure |
| Use appropriate hardware | Faster builds |

### 2. Secure Your Pipeline

- Never commit secrets
- Use environment variables
- Scan for vulnerabilities
- Limit access to production

### 3. Monitor Everything

```
Pipeline Metrics to Track:
├── Build success rate
├── Average build time
├── Test pass rate
├── Deployment frequency
└── Mean time to recovery
```

### 4. Version Everything

```
Repository should contain:
├── src/                    # Application code
├── tests/                  # Test files
├── Jenkinsfile            # Pipeline definition
├── Dockerfile             # Container definition
├── docker-compose.yml     # Local development
└── scripts/               # Deployment scripts
```

---

## CI/CD Tools Comparison

### Popular CI/CD Tools

| Tool | Type | Best For |
|------|------|----------|
| **Jenkins** | Self-hosted | Full control, enterprise |
| **GitHub Actions** | Cloud | GitHub projects |
| **GitLab CI** | Cloud/Self-hosted | GitLab users |
| **CircleCI** | Cloud | Fast setup |
| **Travis CI** | Cloud | Open source |
| **Azure DevOps** | Cloud | Microsoft stack |

### Choosing a Tool

| Requirement | Recommended Tool |
|-------------|------------------|
| Free, self-hosted | Jenkins |
| Already using GitHub | GitHub Actions |
| Already using GitLab | GitLab CI |
| Quick setup, no maintenance | CircleCI, GitHub Actions |
| Enterprise, on-premise | Jenkins, GitLab |

---

## Summary

| Concept | Key Points |
|---------|------------|
| **CI** | Automatically build and test on every commit |
| **CD** | Automatically deploy (or prepare to deploy) |
| **Pipeline** | Stages: Source → Build → Test → Deploy |
| **Environments** | Dev → Test → Staging → Production |
| **Artifacts** | Build outputs (JAR, Docker image, etc.) |
| **Deployment** | Rolling, Blue-Green, or Canary |

### Key Takeaways

1. **Automate everything** - Build, test, deploy
2. **Fail fast** - Run quick tests first
3. **Keep builds fast** - Under 10 minutes
4. **Test thoroughly** - Unit, integration, E2E
5. **Always be able to rollback** - Have a plan

## Next Topic

Continue to [Jenkins](./03-jenkins.md) to learn how to set up and configure a CI/CD server.

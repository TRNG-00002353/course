# DevOps Introduction

## What is DevOps?

DevOps is a **culture and set of practices** that brings together software development (Dev) and IT operations (Ops) to deliver software faster and more reliably.

**Think of it like this**: In a restaurant, if chefs (developers) and waiters (operations) work in isolation, food gets cold and orders get mixed up. DevOps is like having them work as one team - chefs understand customer needs, waiters know what's cooking.

### The Old Way vs DevOps

```
Traditional Approach:
┌──────────────┐                    ┌──────────────┐
│  Developers  │ ──── Wall ────▶   │  Operations  │
│  Write code  │                    │  Deploy code │
│  "It works   │                    │  "Why is it  │
│   on my      │                    │   broken?"   │
│   machine"   │                    │              │
└──────────────┘                    └──────────────┘

DevOps Approach:
┌─────────────────────────────────────────────────┐
│           Development + Operations               │
│                                                  │
│   Write → Build → Test → Deploy → Monitor       │
│                                                  │
│   "We're all responsible for the product"       │
└─────────────────────────────────────────────────┘
```

---

## Why DevOps Matters

### Problems DevOps Solves

| Problem | DevOps Solution |
|---------|-----------------|
| "Works on my machine" | Consistent environments (containers, IaC) |
| Deployments are scary | Automated, frequent, small deployments |
| Bugs found late | Continuous testing catches issues early |
| Slow releases | Automation speeds up delivery |
| Blame game | Shared responsibility |

### Benefits

- **Faster Delivery**: Release features in days, not months
- **Higher Quality**: Automated testing catches bugs early
- **Better Reliability**: Consistent deployments, quick recovery
- **Improved Collaboration**: Teams work together
- **Quick Feedback**: Know immediately if something breaks

---

## Core DevOps Principles

### 1. Automation

Automate everything that can be automated:
- Building code
- Running tests
- Deploying applications
- Infrastructure provisioning

```
Manual Process:                 Automated:
┌───────────────────┐          ┌───────────────────┐
│ Developer builds  │          │ Push code         │
│ locally           │          │      ↓            │
│      ↓            │          │ Auto build        │
│ Copies files to   │          │      ↓            │
│ server            │          │ Auto test         │
│      ↓            │          │      ↓            │
│ Runs tests        │          │ Auto deploy       │
│ manually          │          │                   │
│      ↓            │          │ Done in minutes!  │
│ Hours later...    │          └───────────────────┘
└───────────────────┘
```

### 2. Continuous Improvement

- Measure everything
- Learn from failures
- Iterate and improve
- Blameless post-mortems

### 3. Collaboration

- Shared goals between teams
- Open communication
- Cross-functional teams
- Shared responsibility

### 4. Fast Feedback

- Know immediately if build fails
- Test results in minutes
- Monitor production in real-time
- Quick rollback capability

---

## The DevOps Lifecycle

DevOps is often represented as an infinite loop:

```
        ┌─────────────────────────────────────────┐
        │                                          │
        ▼                                          │
┌──────────────┐    ┌──────────────┐    ┌─────────┴────┐
│    PLAN      │───▶│    CODE      │───▶│    BUILD     │
│              │    │              │    │              │
│ Requirements │    │ Development  │    │ Compile      │
│ User stories │    │ Version ctrl │    │ Package      │
└──────────────┘    └──────────────┘    └──────┬───────┘
                                               │
┌──────────────┐    ┌──────────────┐    ┌──────▼───────┐
│   MONITOR    │◀───│   OPERATE    │◀───│    TEST      │
│              │    │              │    │              │
│ Logs, metrics│    │ Run in prod  │    │ Unit tests   │
│ Alerts       │    │ Maintain     │    │ Integration  │
└──────┬───────┘    └──────────────┘    └──────┬───────┘
       │                                       │
       │            ┌──────────────┐           │
       │            │   DEPLOY     │           │
       └───────────▶│              │◀──────────┘
                    │ Release to   │
                    │ production   │
                    └──────────────┘
```

### Phases Explained

| Phase | What Happens | Tools |
|-------|--------------|-------|
| **Plan** | Define features, user stories | Jira, Trello |
| **Code** | Write and review code | Git, GitHub, VS Code |
| **Build** | Compile, package | Maven, npm, Gradle |
| **Test** | Run automated tests | JUnit, Jest, Selenium |
| **Deploy** | Release to environment | Jenkins, Docker |
| **Operate** | Run in production | Kubernetes, servers |
| **Monitor** | Track health and metrics | Prometheus, Grafana |

---

## Key DevOps Practices

### 1. Version Control

Everything in Git - code, configuration, scripts:

```bash
# All code changes are tracked
git add .
git commit -m "Add user authentication"
git push origin feature/auth
```

### 2. Infrastructure as Code (IaC)

Define infrastructure in code files:

```yaml
# Instead of clicking in UI, define in code
server:
  type: linux
  memory: 4GB
  disk: 50GB
  software:
    - java
    - nginx
```

### 3. Continuous Integration (CI)

Automatically build and test on every commit:

```
Developer pushes code
        ↓
CI server detects change
        ↓
Builds the application
        ↓
Runs all tests
        ↓
Reports success/failure
```

### 4. Continuous Delivery/Deployment (CD)

Automatically deploy after tests pass:

- **Continuous Delivery**: Ready to deploy with one click
- **Continuous Deployment**: Fully automatic deployment

### 5. Monitoring & Logging

Track application health:

```
Application → Logs → Log aggregator → Dashboard
                                          ↓
                                     Alerts team
                                     if problems
```

---

## DevOps Tools Landscape

### By Category

```
┌────────────────────────────────────────────────────────────┐
│                     DevOps Tools                            │
├────────────────────────────────────────────────────────────┤
│                                                             │
│  Source Control:     Git, GitHub, GitLab, Bitbucket        │
│                                                             │
│  CI/CD:              Jenkins, GitHub Actions, GitLab CI    │
│                                                             │
│  Build:              Maven, Gradle, npm, webpack           │
│                                                             │
│  Testing:            JUnit, Jest, Selenium, SonarQube      │
│                                                             │
│  Containers:         Docker, Podman                        │
│                                                             │
│  Orchestration:      Kubernetes, Docker Swarm              │
│                                                             │
│  Configuration:      Ansible, Terraform, Chef              │
│                                                             │
│  Monitoring:         Prometheus, Grafana, ELK Stack        │
│                                                             │
└────────────────────────────────────────────────────────────┘
```

### Common Tool Combinations

| Stack | Tools |
|-------|-------|
| **Java Stack** | Git, Maven, Jenkins, JUnit, SonarQube, Docker |
| **JavaScript Stack** | Git, npm, GitHub Actions, Jest, Docker |
| **Cloud-Native** | Git, Docker, Kubernetes, Terraform, Prometheus |

---

## DevOps Metrics

### Key Metrics to Track

| Metric | What It Measures | Good Target |
|--------|------------------|-------------|
| **Deployment Frequency** | How often you deploy | Multiple times/day |
| **Lead Time** | Idea to production | Less than 1 day |
| **Change Failure Rate** | % of deployments causing issues | Less than 15% |
| **Mean Time to Recovery** | Time to fix production issues | Less than 1 hour |

### The Goal

```
Traditional:                    DevOps Goal:
- Deploy monthly               - Deploy daily/hourly
- Lead time: weeks             - Lead time: hours
- Recovery: hours/days         - Recovery: minutes
- 20% failure rate             - <5% failure rate
```

---

## Getting Started with DevOps

### First Steps

1. **Version Control Everything**
   - Put all code in Git
   - Include configuration files
   - Include deployment scripts

2. **Automate the Build**
   - Create build scripts (Maven, npm)
   - Remove manual steps

3. **Add Automated Tests**
   - Start with unit tests
   - Add integration tests
   - Run tests on every commit

4. **Set Up CI/CD**
   - Use Jenkins, GitHub Actions, or similar
   - Automate build and test
   - Eventually automate deployment

5. **Monitor Everything**
   - Add logging
   - Track metrics
   - Set up alerts

---

## DevOps Culture

### Mindset Shift

| From | To |
|------|-----|
| "That's not my job" | "We're all responsible" |
| "It works on my machine" | "Let's fix it together" |
| "We've always done it this way" | "Let's try and learn" |
| "Who broke it?" | "How do we prevent this?" |

### Blameless Culture

When something goes wrong:
- Focus on **what** happened, not **who** did it
- Ask "How do we prevent this?" not "Who's fault is this?"
- Learn from failures
- Share knowledge

---

## Summary

| Concept | Key Points |
|---------|------------|
| **DevOps** | Culture + practices combining Dev and Ops |
| **Goal** | Deliver software faster and more reliably |
| **Automation** | Automate build, test, deploy |
| **CI/CD** | Continuous Integration and Delivery |
| **Culture** | Collaboration, shared responsibility, learning |

### Key Takeaways

1. DevOps is about **culture** as much as tools
2. **Automate** repetitive tasks
3. **Fail fast** - catch issues early
4. **Continuous improvement** - always get better
5. **Shared responsibility** - everyone owns quality

## Next Topic

Continue to [CI/CD Fundamentals](./02-cicd-fundamentals.md) to understand continuous integration and delivery in depth.

# DevOps Key Concepts for Application Developers

## Overview

This document covers essential DevOps concepts and practices for modern software development. DevOps combines development and operations to increase the speed, quality, and reliability of software delivery through automation, continuous integration, continuous delivery, and monitoring.

---

## 1. DevOps Culture & Principles

### Why It Matters
- Break down silos between development and operations
- Faster delivery of features and fixes
- Improved collaboration and communication
- Higher quality and more stable releases
- Better customer satisfaction

### Key Principles

| Principle | Description | Benefit |
|-----------|-------------|---------|
| Collaboration | Dev and Ops work together | Shared responsibility |
| Automation | Automate repetitive tasks | Speed and consistency |
| Continuous Improvement | Iterate and optimize | Better processes |
| Customer Focus | Value delivery | Business alignment |
| Fail Fast | Detect issues early | Quick recovery |
| Infrastructure as Code | Version control infrastructure | Reproducibility |

### DevOps Lifecycle
```
┌──────────────────────────────────────────────────┐
│                  DevOps Cycle                    │
│                                                  │
│         Plan → Code → Build → Test               │
│           ↑                         ↓            │
│      Monitor ← Deploy ← Release ← Operate        │
│                                                  │
└──────────────────────────────────────────────────┘

Plan:    Requirements, user stories, backlog
Code:    Version control, branching, code review
Build:   Compile, package, containerize
Test:    Automated tests, quality gates
Release: Approval, staging validation
Deploy:  Automated deployment to production
Operate: Run applications, handle incidents
Monitor: Metrics, logs, alerts, feedback
```

---

## 2. Continuous Integration (CI)

### Why It Matters
- Detect integration issues early
- Automated testing catches bugs
- Consistent build process
- Faster feedback loop
- Reduced manual work

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| Version Control | Git, GitHub, GitLab | Source code management |
| Automated Build | Compile on every commit | Early detection |
| Automated Testing | Unit, integration tests | Quality assurance |
| Build Artifact | Deployable package | Consistent deployments |
| Build Pipeline | Automated workflow | Repeatability |

### CI Workflow
```
Developer → Commits Code → Git Repository
                              ↓
                      CI Server Triggered
                              ↓
                    ┌─────────┴─────────┐
                    │                   │
              Clone Repository    Install Dependencies
                    │                   │
                    └─────────┬─────────┘
                              ↓
                         Run Linter
                              ↓
                         Build Code
                              ↓
                        Run Unit Tests
                              ↓
                    Run Integration Tests
                              ↓
                      Generate Artifacts
                              ↓
                   ┌──────────┴──────────┐
                   │                     │
              Success ✓            Failure ✗
                   │                     │
              Notify Dev          Notify Dev
            Store Artifacts       Block Merge
```

### GitHub Actions CI Pipeline
```yaml
# .github/workflows/ci.yml
name: CI Pipeline

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    # Checkout code
    - name: Checkout repository
      uses: actions/checkout@v3

    # Setup Node.js
    - name: Setup Node.js
      uses: actions/setup-node@v3
      with:
        node-version: '18'
        cache: 'npm'

    # Install dependencies
    - name: Install dependencies
      run: npm ci

    # Lint code
    - name: Run linter
      run: npm run lint

    # Run tests
    - name: Run unit tests
      run: npm test

    # Run integration tests
    - name: Run integration tests
      run: npm run test:integration

    # Build application
    - name: Build application
      run: npm run build

    # Upload artifacts
    - name: Upload build artifacts
      uses: actions/upload-artifact@v3
      with:
        name: build
        path: dist/

  security:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v3

    # Security scan
    - name: Run security audit
      run: npm audit --audit-level=high

    # Dependency check
    - name: Check dependencies
      run: npm outdated || true
```

### GitLab CI Pipeline
```yaml
# .gitlab-ci.yml
stages:
  - build
  - test
  - deploy

variables:
  NODE_ENV: production

# Install dependencies
before_script:
  - npm ci

# Build stage
build:
  stage: build
  script:
    - npm run build
  artifacts:
    paths:
      - dist/
    expire_in: 1 week

# Test stage
test:unit:
  stage: test
  script:
    - npm run test
  coverage: '/Lines\s*:\s*(\d+\.\d+)%/'
  artifacts:
    reports:
      coverage_report:
        coverage_format: cobertura
        path: coverage/cobertura-coverage.xml

test:integration:
  stage: test
  script:
    - npm run test:integration
  services:
    - postgres:15

# Lint
lint:
  stage: test
  script:
    - npm run lint

# Deploy to staging
deploy:staging:
  stage: deploy
  script:
    - echo "Deploying to staging..."
    - ./deploy.sh staging
  environment:
    name: staging
    url: https://staging.example.com
  only:
    - develop

# Deploy to production
deploy:production:
  stage: deploy
  script:
    - echo "Deploying to production..."
    - ./deploy.sh production
  environment:
    name: production
    url: https://example.com
  only:
    - main
  when: manual
```

---

## 3. Continuous Delivery (CD)

### Why It Matters
- Automate deployment process
- Reduce deployment risk
- Deploy more frequently
- Faster time to market
- Consistent deployments across environments

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| Continuous Delivery | Code always ready to deploy | Manual production release |
| Continuous Deployment | Automatic production release | Full automation |
| Deployment Pipeline | Automated release process | Consistency |
| Environment Promotion | Dev → Staging → Production | Progressive validation |
| Rollback Strategy | Revert to previous version | Risk mitigation |

### CD vs Continuous Deployment

**Continuous Delivery:**
```
Code → Build → Test → Stage → [Manual Approval] → Production
```

**Continuous Deployment:**
```
Code → Build → Test → Stage → Production (Automatic)
```

### Multi-Environment Pipeline
```yaml
# .github/workflows/cd.yml
name: CD Pipeline

on:
  push:
    branches: [ main ]
    tags: [ 'v*' ]

jobs:
  deploy-staging:
    runs-on: ubuntu-latest
    environment:
      name: staging
      url: https://staging.example.com

    steps:
    - uses: actions/checkout@v3

    - name: Setup Cloud SDK
      uses: google-github-actions/setup-gcloud@v1
      with:
        service_account_key: ${{ secrets.GCP_SA_KEY }}
        project_id: ${{ secrets.GCP_PROJECT_ID }}

    - name: Build Docker image
      run: |
        docker build -t gcr.io/${{ secrets.GCP_PROJECT_ID }}/myapp:${{ github.sha }} .

    - name: Push to Container Registry
      run: |
        gcloud auth configure-docker
        docker push gcr.io/${{ secrets.GCP_PROJECT_ID }}/myapp:${{ github.sha }}

    - name: Deploy to Cloud Run (Staging)
      run: |
        gcloud run deploy myapp-staging \
          --image gcr.io/${{ secrets.GCP_PROJECT_ID }}/myapp:${{ github.sha }} \
          --region us-central1 \
          --platform managed

    - name: Run smoke tests
      run: |
        npm run test:smoke -- --url=https://staging.example.com

  deploy-production:
    needs: deploy-staging
    runs-on: ubuntu-latest
    environment:
      name: production
      url: https://example.com

    steps:
    - uses: actions/checkout@v3

    - name: Setup Cloud SDK
      uses: google-github-actions/setup-gcloud@v1
      with:
        service_account_key: ${{ secrets.GCP_SA_KEY }}
        project_id: ${{ secrets.GCP_PROJECT_ID }}

    - name: Deploy to Cloud Run (Production)
      run: |
        gcloud run deploy myapp-production \
          --image gcr.io/${{ secrets.GCP_PROJECT_ID }}/myapp:${{ github.sha }} \
          --region us-central1 \
          --platform managed

    - name: Run smoke tests
      run: |
        npm run test:smoke -- --url=https://example.com

    - name: Notify team
      run: |
        curl -X POST ${{ secrets.SLACK_WEBHOOK }} \
          -H 'Content-Type: application/json' \
          -d '{"text":"Deployed version ${{ github.sha }} to production"}'
```

---

## 4. Google Cloud Build

### Why It Matters
- Native GCP CI/CD service
- Serverless build execution
- Integration with GCP services
- Docker container builds
- Flexible and scalable

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| Build Trigger | Automated build start | CI/CD automation |
| Build Step | Individual task | Modular builds |
| Substitution Variables | Dynamic values | Configuration |
| Build Artifacts | Output files | Deployment packages |
| Build History | Past builds | Audit and debugging |

### cloudbuild.yaml Example
```yaml
# cloudbuild.yaml
steps:
  # Install dependencies
  - name: 'node:18'
    entrypoint: npm
    args: ['install']

  # Run tests
  - name: 'node:18'
    entrypoint: npm
    args: ['test']

  # Build application
  - name: 'node:18'
    entrypoint: npm
    args: ['run', 'build']

  # Build Docker image
  - name: 'gcr.io/cloud-builders/docker'
    args: [
      'build',
      '-t', 'gcr.io/$PROJECT_ID/myapp:$COMMIT_SHA',
      '-t', 'gcr.io/$PROJECT_ID/myapp:latest',
      '.'
    ]

  # Push Docker image
  - name: 'gcr.io/cloud-builders/docker'
    args: ['push', 'gcr.io/$PROJECT_ID/myapp:$COMMIT_SHA']

  - name: 'gcr.io/cloud-builders/docker'
    args: ['push', 'gcr.io/$PROJECT_ID/myapp:latest']

  # Deploy to Cloud Run
  - name: 'gcr.io/google.com/cloudsdktool/cloud-sdk'
    entrypoint: gcloud
    args:
      - 'run'
      - 'deploy'
      - 'myapp'
      - '--image=gcr.io/$PROJECT_ID/myapp:$COMMIT_SHA'
      - '--region=us-central1'
      - '--platform=managed'
      - '--allow-unauthenticated'

# Store images
images:
  - 'gcr.io/$PROJECT_ID/myapp:$COMMIT_SHA'
  - 'gcr.io/$PROJECT_ID/myapp:latest'

# Build options
options:
  machineType: 'N1_HIGHCPU_8'
  logging: CLOUD_LOGGING_ONLY

# Build timeout
timeout: 1200s
```

### Multi-Stage Build with Testing
```yaml
# cloudbuild.yaml
steps:
  # Run unit tests
  - name: 'node:18'
    id: 'unit-tests'
    entrypoint: 'npm'
    args: ['run', 'test:unit']

  # Run integration tests
  - name: 'node:18'
    id: 'integration-tests'
    entrypoint: 'npm'
    args: ['run', 'test:integration']
    waitFor: ['unit-tests']

  # Security scan
  - name: 'aquasec/trivy'
    id: 'security-scan'
    args: ['fs', '--severity', 'HIGH,CRITICAL', '.']
    waitFor: ['-']

  # Build Docker image
  - name: 'gcr.io/cloud-builders/docker'
    id: 'build-image'
    args: ['build', '-t', 'gcr.io/$PROJECT_ID/myapp:$SHORT_SHA', '.']
    waitFor: ['integration-tests', 'security-scan']

  # Scan Docker image
  - name: 'aquasec/trivy'
    id: 'scan-image'
    args: ['image', 'gcr.io/$PROJECT_ID/myapp:$SHORT_SHA']
    waitFor: ['build-image']

  # Push image
  - name: 'gcr.io/cloud-builders/docker'
    id: 'push-image'
    args: ['push', 'gcr.io/$PROJECT_ID/myapp:$SHORT_SHA']
    waitFor: ['scan-image']

  # Deploy to staging
  - name: 'gcr.io/cloud-builders/gcloud'
    id: 'deploy-staging'
    args:
      - 'run'
      - 'deploy'
      - 'myapp-staging'
      - '--image=gcr.io/$PROJECT_ID/myapp:$SHORT_SHA'
      - '--region=us-central1'
    waitFor: ['push-image']

  # Smoke test staging
  - name: 'curlimages/curl'
    id: 'smoke-test'
    args: ['https://myapp-staging.run.app/health']
    waitFor: ['deploy-staging']

images:
  - 'gcr.io/$PROJECT_ID/myapp:$SHORT_SHA'
```

### Setting up Build Triggers
```bash
# Create trigger from repository
gcloud builds triggers create github \
  --repo-name=my-repo \
  --repo-owner=my-org \
  --branch-pattern="^main$" \
  --build-config=cloudbuild.yaml

# Create trigger for pull requests
gcloud builds triggers create github \
  --repo-name=my-repo \
  --repo-owner=my-org \
  --pull-request-pattern="^main$" \
  --build-config=cloudbuild-pr.yaml \
  --comment-control=COMMENTS_ENABLED

# Create trigger with substitutions
gcloud builds triggers create github \
  --repo-name=my-repo \
  --repo-owner=my-org \
  --branch-pattern="^main$" \
  --build-config=cloudbuild.yaml \
  --substitutions=_ENVIRONMENT=production,_REGION=us-central1

# List triggers
gcloud builds triggers list

# Run trigger manually
gcloud builds triggers run TRIGGER_NAME --branch=main
```

---

## 5. Google Cloud Deploy

### Why It Matters
- Managed continuous delivery service
- Progressive deployment strategies
- Rollback capabilities
- Approval workflows
- Multi-environment promotion

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| Delivery Pipeline | Deployment workflow | Progressive delivery |
| Target | Deployment destination | GKE, Cloud Run |
| Release | Deployable artifact | Version to deploy |
| Rollout | Deployment execution | Actual deployment |
| Promotion | Move between stages | Environment progression |

### Delivery Pipeline Configuration
```yaml
# clouddeploy.yaml
apiVersion: deploy.cloud.google.com/v1
kind: DeliveryPipeline
metadata:
  name: myapp-pipeline
description: Application delivery pipeline
serialPipeline:
  stages:
  - targetId: dev
    profiles: [dev]
  - targetId: staging
    profiles: [staging]
  - targetId: production
    profiles: [production]
    strategy:
      canary:
        runtimeConfig:
          cloudRun:
            automaticTrafficControl: true
        canaryDeployment:
          percentages: [25, 50, 75]
          verify: false

---
apiVersion: deploy.cloud.google.com/v1
kind: Target
metadata:
  name: dev
description: Development environment
run:
  location: projects/PROJECT_ID/locations/us-central1

---
apiVersion: deploy.cloud.google.com/v1
kind: Target
metadata:
  name: staging
description: Staging environment
requireApproval: true
run:
  location: projects/PROJECT_ID/locations/us-central1

---
apiVersion: deploy.cloud.google.com/v1
kind: Target
metadata:
  name: production
description: Production environment
requireApproval: true
run:
  location: projects/PROJECT_ID/locations/us-central1
```

### Creating Releases
```bash
# Create delivery pipeline
gcloud deploy apply --file=clouddeploy.yaml --region=us-central1

# Create release
gcloud deploy releases create release-001 \
  --delivery-pipeline=myapp-pipeline \
  --region=us-central1 \
  --images=myapp=gcr.io/PROJECT_ID/myapp:v1.0.0

# List releases
gcloud deploy releases list \
  --delivery-pipeline=myapp-pipeline \
  --region=us-central1

# Promote release to next stage
gcloud deploy releases promote \
  --release=release-001 \
  --delivery-pipeline=myapp-pipeline \
  --region=us-central1

# Approve rollout
gcloud deploy rollouts approve ROLLOUT_NAME \
  --release=release-001 \
  --delivery-pipeline=myapp-pipeline \
  --region=us-central1

# Rollback
gcloud deploy targets rollback production \
  --delivery-pipeline=myapp-pipeline \
  --region=us-central1
```

---

## 6. Deployment Strategies

### Why It Matters
- Minimize deployment risk
- Enable zero-downtime deployments
- Test in production safely
- Quick rollback if needed

### Strategy Comparison

| Strategy | Downtime | Risk | Complexity | Cost |
|----------|----------|------|------------|------|
| Recreate | Yes | High | Low | Low |
| Rolling Update | No | Medium | Medium | Low |
| Blue-Green | No | Low | Medium | High |
| Canary | No | Low | High | Medium |

### Rolling Update
```yaml
# Kubernetes rolling update
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp
spec:
  replicas: 10
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 2        # Max pods above desired
      maxUnavailable: 1  # Max pods unavailable
  template:
    spec:
      containers:
      - name: myapp
        image: myapp:v2.0
```

```
Old Version (v1)     New Version (v2)
[■][■][■][■][■]
[■][■][■][■][□]      [□] ← New pod starting
[■][■][■][□][□]      [□][□]
[■][■][□][□][□]      [□][□][□]
[■][□][□][□][□]      [□][□][□][□]
[□][□][□][□][□]      [□][□][□][□][□] ← Complete
```

### Blue-Green Deployment
```yaml
# Service points to blue deployment
apiVersion: v1
kind: Service
metadata:
  name: myapp
spec:
  selector:
    app: myapp
    version: blue  # Switch to 'green' to flip traffic
  ports:
  - port: 80

---
# Blue deployment (current)
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-blue
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
      version: blue
  template:
    metadata:
      labels:
        app: myapp
        version: blue
    spec:
      containers:
      - name: myapp
        image: myapp:v1.0

---
# Green deployment (new)
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-green
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
      version: green
  template:
    metadata:
      labels:
        app: myapp
        version: green
    spec:
      containers:
      - name: myapp
        image: myapp:v2.0
```

```bash
# Deploy green version
kubectl apply -f deployment-green.yaml

# Test green version
kubectl port-forward deployment/myapp-green 8080:80

# Switch traffic to green
kubectl patch service myapp -p '{"spec":{"selector":{"version":"green"}}}'

# Rollback if needed
kubectl patch service myapp -p '{"spec":{"selector":{"version":"blue"}}}'
```

### Canary Deployment
```yaml
# Main deployment (90% traffic)
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-stable
spec:
  replicas: 9
  selector:
    matchLabels:
      app: myapp
      track: stable
  template:
    metadata:
      labels:
        app: myapp
        track: stable
    spec:
      containers:
      - name: myapp
        image: myapp:v1.0

---
# Canary deployment (10% traffic)
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp-canary
spec:
  replicas: 1
  selector:
    matchLabels:
      app: myapp
      track: canary
  template:
    metadata:
      labels:
        app: myapp
        track: canary
    spec:
      containers:
      - name: myapp
        image: myapp:v2.0

---
# Service load balances between both
apiVersion: v1
kind: Service
metadata:
  name: myapp
spec:
  selector:
    app: myapp  # Both stable and canary
  ports:
  - port: 80
```

---

## 7. Infrastructure as Code (IaC)

### Why It Matters
- Version control infrastructure
- Reproducible environments
- Disaster recovery
- Team collaboration
- Audit trail

### Terraform Example
```hcl
# main.tf
terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 5.0"
    }
  }
}

provider "google" {
  project = var.project_id
  region  = var.region
}

# VPC Network
resource "google_compute_network" "vpc" {
  name                    = "myapp-vpc"
  auto_create_subnetworks = false
}

# Subnet
resource "google_compute_subnetwork" "subnet" {
  name          = "myapp-subnet"
  ip_cidr_range = "10.0.1.0/24"
  region        = var.region
  network       = google_compute_network.vpc.id
}

# Cloud SQL
resource "google_sql_database_instance" "main" {
  name             = "myapp-db"
  database_version = "POSTGRES_15"
  region           = var.region

  settings {
    tier = "db-f1-micro"

    ip_configuration {
      ipv4_enabled = false
      private_network = google_compute_network.vpc.id
    }

    backup_configuration {
      enabled = true
      start_time = "03:00"
    }
  }
}

resource "google_sql_database" "database" {
  name     = "myapp"
  instance = google_sql_database_instance.main.name
}

# GKE Cluster
resource "google_container_cluster" "primary" {
  name     = "myapp-gke"
  location = var.region

  remove_default_node_pool = true
  initial_node_count       = 1

  network    = google_compute_network.vpc.name
  subnetwork = google_compute_subnetwork.subnet.name
}

resource "google_container_node_pool" "primary_nodes" {
  name       = "main-pool"
  location   = var.region
  cluster    = google_container_cluster.primary.name
  node_count = 2

  node_config {
    machine_type = "e2-medium"

    oauth_scopes = [
      "https://www.googleapis.com/auth/cloud-platform"
    ]
  }
}

# Cloud Run Service
resource "google_cloud_run_service" "default" {
  name     = "myapp"
  location = var.region

  template {
    spec {
      containers {
        image = "gcr.io/${var.project_id}/myapp:latest"

        resources {
          limits = {
            memory = "512Mi"
            cpu    = "1000m"
          }
        }

        env {
          name  = "DATABASE_URL"
          value = "postgresql://${google_sql_database_instance.main.connection_name}"
        }
      }
    }
  }

  traffic {
    percent         = 100
    latest_revision = true
  }
}

# IAM for Cloud Run
resource "google_cloud_run_service_iam_member" "public" {
  service  = google_cloud_run_service.default.name
  location = google_cloud_run_service.default.location
  role     = "roles/run.invoker"
  member   = "allUsers"
}

# Outputs
output "cloud_run_url" {
  value = google_cloud_run_service.default.status[0].url
}

output "gke_cluster_name" {
  value = google_container_cluster.primary.name
}
```

```bash
# Initialize Terraform
terraform init

# Plan changes
terraform plan

# Apply changes
terraform apply

# Destroy infrastructure
terraform destroy
```

---

## 8. Monitoring & Logging

### Why It Matters
- Detect issues proactively
- Understand system behavior
- Debug production problems
- Performance optimization
- Compliance and auditing

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| Metrics | Numerical measurements | Performance tracking |
| Logs | Event records | Debugging |
| Traces | Request flow | Distributed tracing |
| Alerts | Automated notifications | Issue detection |
| Dashboards | Visual displays | System overview |

### Application Logging
```javascript
// Node.js with Winston and Cloud Logging
const winston = require('winston');
const { LoggingWinston } = require('@google-cloud/logging-winston');

const loggingWinston = new LoggingWinston();

const logger = winston.createLogger({
  level: 'info',
  transports: [
    new winston.transports.Console(),
    loggingWinston,
  ],
});

// Use in application
app.get('/api/users', async (req, res) => {
  logger.info('Fetching users', {
    requestId: req.id,
    userId: req.user.id
  });

  try {
    const users = await userService.getUsers();
    logger.info('Successfully fetched users', {
      count: users.length
    });
    res.json(users);
  } catch (error) {
    logger.error('Error fetching users', {
      error: error.message,
      stack: error.stack
    });
    res.status(500).json({ error: 'Internal server error' });
  }
});
```

### Cloud Monitoring Alerts
```yaml
# alert-policy.yaml
displayName: "High Error Rate"
conditions:
  - displayName: "Error rate above 5%"
    conditionThreshold:
      filter: |
        resource.type = "cloud_run_revision"
        AND metric.type = "run.googleapis.com/request_count"
        AND metric.label.response_code_class = "5xx"
      comparison: COMPARISON_GT
      thresholdValue: 5
      duration: 60s
      aggregations:
        - alignmentPeriod: 60s
          perSeriesAligner: ALIGN_RATE

notificationChannels:
  - projects/PROJECT_ID/notificationChannels/CHANNEL_ID

alertStrategy:
  autoClose: 604800s
```

---

## Quick Reference Card

### CI/CD Commands
```bash
# GitHub Actions
# Edit .github/workflows/ci.yml
# Push triggers automatically

# Cloud Build
gcloud builds submit --config=cloudbuild.yaml
gcloud builds list
gcloud builds log BUILD_ID

# Cloud Deploy
gcloud deploy apply --file=clouddeploy.yaml --region=us-central1
gcloud deploy releases create release-001 \
  --delivery-pipeline=myapp-pipeline \
  --region=us-central1

# Terraform
terraform init
terraform plan
terraform apply
terraform destroy
```

### Deployment Patterns
```bash
# Rolling update
kubectl set image deployment/myapp myapp=myapp:v2
kubectl rollout status deployment/myapp
kubectl rollout undo deployment/myapp

# Blue-Green
kubectl apply -f deployment-green.yaml
kubectl patch service myapp -p '{"spec":{"selector":{"version":"green"}}}'

# Canary
kubectl scale deployment/myapp-canary --replicas=1
# Monitor metrics
kubectl scale deployment/myapp-canary --replicas=5
kubectl scale deployment/myapp-stable --replicas=5
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Understand DevOps culture and principles
- [ ] Set up CI pipelines with GitHub Actions
- [ ] Configure Cloud Build for GCP
- [ ] Implement CD pipelines
- [ ] Use Cloud Deploy for progressive delivery
- [ ] Implement different deployment strategies
- [ ] Write Infrastructure as Code with Terraform
- [ ] Set up monitoring and logging
- [ ] Create alerts for system health
- [ ] Perform rollbacks safely
- [ ] Automate testing in pipelines
- [ ] Implement security scanning
- [ ] Use environment variables and secrets
- [ ] Deploy containerized applications

---

## Next Steps

After mastering these concepts:
- Practice building complete CI/CD pipelines
- Implement GitOps workflows with ArgoCD or Flux
- Learn advanced monitoring with Prometheus
- Study chaos engineering principles
- Explore service mesh technologies
- Implement progressive delivery with feature flags

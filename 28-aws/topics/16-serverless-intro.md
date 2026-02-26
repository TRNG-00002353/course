# Introduction to Serverless Computing

## What is Serverless?

Serverless computing lets you run code without provisioning or managing servers. You upload your code, and AWS handles everything else.

```
Traditional (EC2)                  Serverless (Lambda)
┌─────────────────────┐           ┌─────────────────────┐
│ You manage:         │           │ AWS manages:        │
│ - Server setup      │           │ - All infrastructure│
│ - OS updates        │     →     │ - Scaling           │
│ - Scaling           │           │ - High availability │
│ - Availability      │           │                     │
│                     │           │ You manage:         │
│ Pay: 24/7 running   │           │ - Just your code    │
│                     │           │                     │
│                     │           │ Pay: Per execution  │
└─────────────────────┘           └─────────────────────┘
```

---

## Serverless vs Traditional

| Aspect | Traditional (EC2) | Serverless (Lambda) |
|--------|------------------|---------------------|
| **Management** | You manage servers | No server management |
| **Scaling** | Manual or Auto Scaling | Automatic, instant |
| **Pricing** | Pay for uptime | Pay per execution |
| **Startup** | Minutes (new instance) | Milliseconds |
| **Max Runtime** | Unlimited | 15 minutes |
| **Best For** | Long-running apps | Event-driven tasks |

---

## Key Serverless Services on AWS

```
AWS Serverless Stack
┌─────────────────────────────────────────────────────┐
│                                                     │
│  Compute:        AWS Lambda                         │
│                  Run code without servers           │
│                                                     │
│  API:            API Gateway                        │
│                  Create REST/HTTP APIs              │
│                                                     │
│  Database:       DynamoDB                           │
│                  NoSQL database, serverless         │
│                                                     │
│  Storage:        S3                                 │
│                  Object storage, triggers Lambda    │
│                                                     │
│  Orchestration:  Step Functions                     │
│                  Coordinate multiple functions      │
│                                                     │
└─────────────────────────────────────────────────────┘
```

---

## AWS Lambda Overview

Lambda is the core of AWS serverless computing.

### How Lambda Works

```
Lambda Execution Flow
┌─────────────────────────────────────────────────────┐
│                                                     │
│  1. Event Occurs                                    │
│     (HTTP request, file upload, schedule)          │
│                        │                            │
│                        ▼                            │
│  2. Lambda Invoked                                  │
│     AWS spins up execution environment             │
│                        │                            │
│                        ▼                            │
│  3. Code Executes                                   │
│     Your function processes the event              │
│                        │                            │
│                        ▼                            │
│  4. Response Returned                               │
│     Result sent back to caller                     │
│                        │                            │
│                        ▼                            │
│  5. Environment Recycled                            │
│     May be reused for next request                 │
│                                                     │
└─────────────────────────────────────────────────────┘
```

### Lambda Pricing

```
Lambda Free Tier (Always Free!)
┌─────────────────────────────────────────────────────┐
│ Requests: 1 million per month                      │
│ Compute: 400,000 GB-seconds per month              │
│                                                     │
│ Example:                                            │
│ - 128 MB function running 1 second                 │
│ - 3.2 million free executions per month!           │
│                                                     │
│ Beyond free tier:                                   │
│ - $0.20 per 1 million requests                     │
│ - $0.0000166667 per GB-second                      │
└─────────────────────────────────────────────────────┘
```

---

## When to Use Serverless

### Good Use Cases

```
✓ Event-driven processing
  - Process file uploads
  - Handle webhooks
  - React to database changes

✓ APIs and microservices
  - REST APIs with API Gateway
  - Backend for mobile apps
  - Microservices architecture

✓ Scheduled tasks
  - Daily reports
  - Cleanup jobs
  - Data synchronization

✓ Data processing
  - ETL jobs
  - Image/video processing
  - Log analysis
```

### Not Ideal For

```
✗ Long-running processes (>15 minutes)
  - Use EC2 or ECS instead

✗ Stateful applications
  - Sessions need external storage

✗ High-performance computing
  - Limited CPU/memory options

✗ Applications needing specific OS
  - Lambda has fixed runtime environment
```

---

## Serverless Architecture Patterns

### Pattern 1: Simple API

```
Client → API Gateway → Lambda → DynamoDB
                                   │
                                   ▼
                              Response
```

### Pattern 2: File Processing

```
User uploads file
       │
       ▼
   S3 Bucket ──trigger──► Lambda ──► Process file
                              │
                              ▼
                         Store results
                         (S3, DynamoDB)
```

### Pattern 3: Web Application

```
                    ┌─────────────────┐
     ┌─────────────►│  S3 (Frontend)  │
     │              │  Static files   │
     │              └─────────────────┘
Internet
     │              ┌─────────────────┐
     └─────────────►│  API Gateway    │
                    │       │         │
                    │       ▼         │
                    │    Lambda       │
                    │       │         │
                    │       ▼         │
                    │   DynamoDB      │
                    └─────────────────┘
```

### Pattern 4: Event-Driven

```
┌──────────┐   ┌──────────┐   ┌──────────┐
│  Event   │──►│  Lambda  │──►│  Action  │
│  Source  │   │ Function │   │          │
└──────────┘   └──────────┘   └──────────┘

Event Sources:
- S3 (file upload)
- DynamoDB (data change)
- SNS (notification)
- SQS (queue message)
- CloudWatch Events (schedule)
- API Gateway (HTTP request)
```

---

## Lambda Concepts

### Function

Your code packaged and deployed to Lambda.

```
Lambda Function Components
┌─────────────────────────────────────────────────────┐
│ Handler: The entry point for your code             │
│          (e.g., index.handler)                     │
│                                                     │
│ Runtime: The language environment                   │
│          (Python, Node.js, Java, etc.)             │
│                                                     │
│ Memory: 128 MB - 10 GB                             │
│         (CPU scales with memory)                   │
│                                                     │
│ Timeout: 1 second - 15 minutes                     │
│                                                     │
│ Environment Variables: Configuration               │
└─────────────────────────────────────────────────────┘
```

### Trigger

What causes your function to run.

```
Common Triggers
┌─────────────────────────────────────────────────────┐
│ API Gateway    │ HTTP requests                     │
│ S3             │ Object created/deleted            │
│ DynamoDB       │ Table data changes                │
│ SQS            │ Queue messages                    │
│ SNS            │ Notifications                     │
│ CloudWatch     │ Scheduled events (cron)           │
│ EventBridge    │ AWS events, custom events         │
└─────────────────────────────────────────────────────┘
```

### Execution Environment

Temporary container that runs your code.

```
Execution Environment Lifecycle
┌─────────────────────────────────────────────────────┐
│                                                     │
│  Cold Start                  Warm Start             │
│  ───────────                 ──────────             │
│  Download code               Reuse existing         │
│  Start container             container              │
│  Initialize runtime                                 │
│  Run handler                 Run handler            │
│                                                     │
│  ~100ms - 1s                 ~1-10ms                │
│                                                     │
│  First request or            Subsequent requests    │
│  after idle period           (container reused)     │
│                                                     │
└─────────────────────────────────────────────────────┘
```

---

## Supported Runtimes

| Language | Runtime |
|----------|---------|
| **Python** | 3.9, 3.10, 3.11, 3.12 |
| **Node.js** | 18.x, 20.x |
| **Java** | 11, 17, 21 |
| **C#/.NET** | 6, 8 |
| **Go** | 1.x |
| **Ruby** | 3.2, 3.3 |
| **Custom** | Any language via custom runtime |

---

## Serverless vs Containers vs EC2

```
Comparison Chart
┌────────────────────────────────────────────────────────────┐
│                  EC2        Containers    Serverless       │
├────────────────────────────────────────────────────────────┤
│ Management     High        Medium        Low               │
│ Scaling        Manual/ASG  Orchestrated  Automatic         │
│ Startup Time   Minutes     Seconds       Milliseconds      │
│ Max Runtime    Unlimited   Unlimited     15 minutes        │
│ Cost Model     Hourly      Per-task      Per-execution     │
│ Flexibility    Highest     High          Limited           │
│ Complexity     Highest     High          Lowest            │
└────────────────────────────────────────────────────────────┘
```

---

## Serverless Benefits

### 1. No Server Management

```
You don't worry about:
- Operating system updates
- Security patches
- Capacity planning
- Server failures
```

### 2. Automatic Scaling

```
Traffic             Lambda Instances
Low:    ●           ●
Medium: ●●●●●       ●●●●●
High:   ●●●●●●●●●●  ●●●●●●●●●●

Scales automatically from 0 to thousands
```

### 3. Pay Per Use

```
Traditional: Pay 24/7 even when idle
┌──────────────────────────────────┐
│████████████████████████████████│ $100/month
└──────────────────────────────────┘

Serverless: Pay only when code runs
┌──────────────────────────────────┐
│██░░░░██░░░░██░░░░██░░░░██░░░░██│ $5/month
└──────────────────────────────────┘
```

### 4. Built-in High Availability

```
Lambda automatically:
- Runs in multiple AZs
- Handles failures
- Retries failed executions
```

---

## Limitations to Consider

| Limitation | Details |
|------------|---------|
| **Execution time** | Max 15 minutes |
| **Memory** | Max 10 GB |
| **Package size** | 50 MB zipped, 250 MB unzipped |
| **Concurrent executions** | 1000 default (can increase) |
| **Cold starts** | Initial latency for first request |
| **Stateless** | No persistent local storage |

---

## Getting Started Checklist

Before diving into Lambda:

```
Prerequisites
┌─────────────────────────────────────────────────────┐
│ □ Understand event-driven programming              │
│ □ Familiar with at least one supported language    │
│ □ AWS account with IAM user                        │
│ □ Basic understanding of IAM roles                 │
│ □ (Optional) AWS CLI installed                     │
└─────────────────────────────────────────────────────┘
```

---

## Summary

| Concept | Description |
|---------|-------------|
| **Serverless** | Run code without managing servers |
| **Lambda** | AWS compute service for serverless |
| **Trigger** | Event that invokes a function |
| **Cold Start** | Initial latency when function starts |
| **Pay per use** | Only pay when code executes |

### Key Takeaways

1. **Serverless = No server management** - Focus on code, not infrastructure
2. **Event-driven** - Functions respond to events
3. **Auto-scaling** - From zero to thousands automatically
4. **Cost-effective** - Pay only for execution time
5. **Not for everything** - Best for short, event-driven tasks

## Next Topic

Continue to [Creating Lambda Functions](./17-lambda-functions.md) to build your first serverless function.

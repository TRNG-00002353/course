# Cloud Service Models

## The Three Service Models

Cloud computing offers three fundamental service models that define **how much you manage** versus **how much the provider manages**.

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    THE THREE SERVICE MODELS                              │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│     IaaS                    PaaS                    SaaS                │
│  (Infrastructure)        (Platform)              (Software)             │
│                                                                          │
│  ┌─────────────┐        ┌─────────────┐        ┌─────────────┐         │
│  │             │        │             │        │             │         │
│  │   Virtual   │        │   Deploy    │        │   Use the   │         │
│  │   Machines  │        │   Your App  │        │   Software  │         │
│  │             │        │             │        │             │         │
│  └─────────────┘        └─────────────┘        └─────────────┘         │
│                                                                          │
│  Most control                                           Least control   │
│  Most responsibility                               Least responsibility │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Understanding Through Analogies

### The Housing Analogy

```
┌─────────────────────────────────────────────────────────────────────────┐
│                       HOUSING ANALOGY                                    │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  BUILD YOUR OWN HOUSE     RENT APARTMENT       STAY AT HOTEL            │
│    (On-Premise)              (IaaS)               (SaaS)                │
│                                                                          │
│  ┌─────────────┐        ┌─────────────┐        ┌─────────────┐         │
│  │ 🏗️ Buy land │        │ 🏢 Rented   │        │ 🏨 Room     │         │
│  │ 🔨 Build    │        │    space    │        │    ready    │         │
│  │ 🪑 Furnish  │        │ 🪑 You add  │        │ 🛏️ Furnished│         │
│  │ 🔧 Maintain │        │    furniture│        │ 🧹 Cleaned  │         │
│  └─────────────┘        └─────────────┘        └─────────────┘         │
│                                                                          │
│  You do everything      You furnish &         Everything is             │
│                         decorate              provided                   │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### The Pizza Analogy

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    PIZZA AS A SERVICE                                    │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ON-PREMISE         IaaS              PaaS              SaaS            │
│  (Homemade)      (Take & Bake)     (Delivered)       (Dine In)         │
│                                                                          │
│  🏠 Make at home  🍕 Buy unbaked   🛵 Order delivery  🍽️ Eat at rest.  │
│                                                                          │
│  You provide:     You provide:     You provide:      You provide:       │
│  ✓ Kitchen        ✓ Oven           ✓ Plates          ✓ Nothing          │
│  ✓ Oven           ✓ Plates         ✓ Drinks          │                  │
│  ✓ Ingredients                                       │                  │
│  ✓ Recipe                                            │                  │
│  ✓ Cooking                                           │                  │
│  ✓ Plates                                            │                  │
│                                                                          │
│  ◄─────── You do more                 You do less ─────────►           │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## The Responsibility Stack

This diagram shows what YOU manage vs what the PROVIDER manages:

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    WHO MANAGES WHAT?                                     │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│           On-Premise      IaaS          PaaS          SaaS              │
│         ┌───────────┐ ┌───────────┐ ┌───────────┐ ┌───────────┐        │
│ App     │    You    │ │    You    │ │    You    │ │  Provider │        │
│         ├───────────┤ ├───────────┤ ├───────────┤ ├───────────┤        │
│ Data    │    You    │ │    You    │ │    You    │ │  Provider │        │
│         ├───────────┤ ├───────────┤ ├───────────┤ ├───────────┤        │
│ Runtime │    You    │ │    You    │ │  Provider │ │  Provider │        │
│         ├───────────┤ ├───────────┤ ├───────────┤ ├───────────┤        │
│ Middle- │    You    │ │    You    │ │  Provider │ │  Provider │        │
│ ware    ├───────────┤ ├───────────┤ ├───────────┤ ├───────────┤        │
│ OS      │    You    │ │    You    │ │  Provider │ │  Provider │        │
│         ├───────────┤ ├───────────┤ ├───────────┤ ├───────────┤        │
│ Virtual │    You    │ │  Provider │ │  Provider │ │  Provider │        │
│         ├───────────┤ ├───────────┤ ├───────────┤ ├───────────┤        │
│ Servers │    You    │ │  Provider │ │  Provider │ │  Provider │        │
│         ├───────────┤ ├───────────┤ ├───────────┤ ├───────────┤        │
│ Storage │    You    │ │  Provider │ │  Provider │ │  Provider │        │
│         ├───────────┤ ├───────────┤ ├───────────┤ ├───────────┤        │
│ Network │    You    │ │  Provider │ │  Provider │ │  Provider │        │
│         └───────────┘ └───────────┘ └───────────┘ └───────────┘        │
│                                                                          │
│         ████ You manage          ░░░░ Provider manages                  │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## IaaS - Infrastructure as a Service

### What is IaaS?

IaaS provides **virtualized computing resources** over the internet. You rent virtual machines, storage, and networks instead of buying physical hardware.

**Think of it as:** Renting empty computing infrastructure that you set up yourself.

### What You Get

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    IaaS - WHAT YOU GET                                   │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  PROVIDER GIVES YOU:                 YOU MANAGE:                        │
│  ┌────────────────────┐             ┌────────────────────┐             │
│  │ ☑️ Physical servers│             │ ☑️ Operating system│             │
│  │ ☑️ Virtualization  │             │ ☑️ Middleware      │             │
│  │ ☑️ Storage systems │             │ ☑️ Runtime (Java)  │             │
│  │ ☑️ Networking      │             │ ☑️ Your application│             │
│  │ ☑️ Data centers    │             │ ☑️ Your data       │             │
│  │ ☑️ Power/cooling   │             │ ☑️ Security config │             │
│  └────────────────────┘             └────────────────────┘             │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Common IaaS Services

| Service Type | What It Provides | Examples |
|--------------|------------------|----------|
| **Virtual Machines** | Compute instances | AWS EC2, Azure VMs, GCP Compute Engine |
| **Block Storage** | Disk volumes for VMs | AWS EBS, Azure Disks, GCP Persistent Disk |
| **Object Storage** | File storage | AWS S3, Azure Blob, GCP Cloud Storage |
| **Virtual Networks** | Network configuration | AWS VPC, Azure VNet, GCP VPC |
| **Load Balancers** | Traffic distribution | AWS ELB, Azure Load Balancer |

### IaaS Use Cases

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    WHEN TO USE IaaS                                      │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ✅ GOOD FOR:                        ❌ NOT IDEAL FOR:                  │
│                                                                          │
│  • Lift-and-shift migrations         • Teams without IT expertise       │
│  • Development/test environments     • Simple web applications          │
│  • High-performance computing        • When you want minimal management │
│  • Big data analysis                 • Quick prototypes                 │
│  • Website hosting with full control                                    │
│  • Backup and recovery                                                  │
│  • Custom software requirements                                         │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### IaaS Example Scenario

```
Scenario: Your company needs to run a custom application

Traditional (On-Premise):
1. Buy servers ($20,000)
2. Set up data center space
3. Install networking equipment
4. Wait 4-6 weeks
5. Install OS and software
6. Deploy application

IaaS Approach:
1. Log into cloud console
2. Select VM size (CPU, RAM)
3. Choose operating system
4. Click "Create" → VM ready in 5 minutes
5. Install your software
6. Deploy application

Cost: ~$100-500/month instead of $20,000 upfront
```

---

## PaaS - Platform as a Service

### What is PaaS?

PaaS provides a **complete development and deployment environment** in the cloud. You focus only on your application code - the platform handles everything else.

**Think of it as:** A ready-to-use platform where you just deploy your code.

### What You Get

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    PaaS - WHAT YOU GET                                   │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  PROVIDER GIVES YOU:                 YOU MANAGE:                        │
│  ┌────────────────────┐             ┌────────────────────┐             │
│  │ ☑️ Everything IaaS │             │ ☑️ Your application│             │
│  │    provides        │             │ ☑️ Your data       │             │
│  │ ☑️ Operating system│             │                    │             │
│  │ ☑️ Middleware      │             │ That's it!         │             │
│  │ ☑️ Runtime (Java,  │             │                    │             │
│  │    Node, Python)   │             │ Just write code    │             │
│  │ ☑️ Development     │             │ and deploy         │             │
│  │    tools           │             │                    │             │
│  └────────────────────┘             └────────────────────┘             │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Common PaaS Services

| Service Type | What It Provides | Examples |
|--------------|------------------|----------|
| **App Hosting** | Run web applications | Heroku, AWS Elastic Beanstalk, Azure App Service |
| **Database PaaS** | Managed databases | AWS RDS, Azure SQL, GCP Cloud SQL |
| **Serverless** | Run code without servers | AWS Lambda, Azure Functions, GCP Cloud Functions |
| **Container Platforms** | Managed containers | AWS ECS, Azure Container Apps, GCP Cloud Run |

### PaaS Workflow

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    PaaS DEPLOYMENT WORKFLOW                              │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  Developer writes code                                                   │
│        │                                                                 │
│        ▼                                                                 │
│  ┌─────────────┐                                                        │
│  │  git push   │  ← Push code to platform                               │
│  └─────────────┘                                                        │
│        │                                                                 │
│        ▼                                                                 │
│  ┌─────────────────────────────────────────────────┐                   │
│  │              PaaS PLATFORM HANDLES:              │                   │
│  │                                                  │                   │
│  │  1. Detects language (Java, Node, Python...)    │                   │
│  │  2. Installs dependencies                        │                   │
│  │  3. Builds application                           │                   │
│  │  4. Configures web server                        │                   │
│  │  5. Sets up SSL certificate                      │                   │
│  │  6. Deploys to server                            │                   │
│  │  7. Configures load balancing                    │                   │
│  │  8. Enables auto-scaling                         │                   │
│  │                                                  │                   │
│  └─────────────────────────────────────────────────┘                   │
│        │                                                                 │
│        ▼                                                                 │
│  ┌─────────────┐                                                        │
│  │ App is live!│  ← https://myapp.herokuapp.com                        │
│  └─────────────┘                                                        │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### PaaS Use Cases

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    WHEN TO USE PaaS                                      │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ✅ GOOD FOR:                        ❌ NOT IDEAL FOR:                  │
│                                                                          │
│  • Web application development       • Custom OS requirements           │
│  • API backends                      • Very specific infrastructure     │
│  • Microservices                     • Legacy applications              │
│  • Rapid prototyping                 • When you need low-level control  │
│  • Startups (focus on product)                                          │
│  • Teams without DevOps expertise                                       │
│  • CI/CD pipelines                                                      │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## SaaS - Software as a Service

### What is SaaS?

SaaS delivers **complete software applications** over the internet. You simply use the software - no installation, no maintenance, no infrastructure concerns.

**Think of it as:** Software you access through your web browser.

### What You Get

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    SaaS - WHAT YOU GET                                   │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  PROVIDER GIVES YOU:                 YOU MANAGE:                        │
│  ┌────────────────────┐             ┌────────────────────┐             │
│  │ ☑️ Complete        │             │ ☑️ Your account    │             │
│  │    application     │             │ ☑️ Your data       │             │
│  │ ☑️ All updates     │             │ ☑️ Your users      │             │
│  │ ☑️ Security        │             │                    │             │
│  │ ☑️ Availability    │             │ Just use the       │             │
│  │ ☑️ Backups         │             │ software!          │             │
│  │ ☑️ Support         │             │                    │             │
│  └────────────────────┘             └────────────────────┘             │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Common SaaS Examples

| Category | Examples |
|----------|----------|
| **Email** | Gmail, Outlook.com, Yahoo Mail |
| **Office Productivity** | Google Workspace, Microsoft 365, Notion |
| **CRM** | Salesforce, HubSpot, Zoho |
| **Communication** | Slack, Zoom, Microsoft Teams |
| **Project Management** | Jira, Trello, Asana |
| **HR/Payroll** | Workday, BambooHR, Gusto |
| **Accounting** | QuickBooks Online, Xero, FreshBooks |
| **Design** | Figma, Canva, Adobe Creative Cloud |

### SaaS Benefits

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    SaaS BENEFITS                                         │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                                                                   │  │
│  │  📱 Access from anywhere (just need a browser)                   │  │
│  │                                                                   │  │
│  │  🔄 Automatic updates (always latest version)                    │  │
│  │                                                                   │  │
│  │  💰 Subscription pricing (no big upfront cost)                   │  │
│  │                                                                   │  │
│  │  🚀 Instant availability (sign up and start using)              │  │
│  │                                                                   │  │
│  │  👥 Easy collaboration (built-in sharing features)               │  │
│  │                                                                   │  │
│  │  📈 Scales automatically (handles millions of users)             │  │
│  │                                                                   │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### SaaS Use Cases

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    WHEN TO USE SaaS                                      │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ✅ GOOD FOR:                        ❌ NOT IDEAL FOR:                  │
│                                                                          │
│  • Standard business applications    • Highly customized needs          │
│  • Email and collaboration           • Strict data residency rules      │
│  • CRM and sales tools               • Offline-only requirements        │
│  • Accounting software               • When you need source code access │
│  • Project management                                                   │
│  • Any common business need                                             │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Comparing the Three Models

### Quick Comparison

| Aspect | IaaS | PaaS | SaaS |
|--------|------|------|------|
| **You manage** | OS, Apps, Data | Apps, Data | Just Data |
| **Provider manages** | Infrastructure | Infrastructure + Platform | Everything |
| **Flexibility** | Highest | Medium | Lowest |
| **Complexity** | Highest | Medium | Lowest |
| **Control** | Most | Moderate | Least |
| **Setup time** | Hours/Days | Minutes/Hours | Minutes |
| **Target user** | IT admins | Developers | End users |

### Decision Tree

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    WHICH MODEL TO CHOOSE?                                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  START HERE                                                              │
│      │                                                                   │
│      ▼                                                                   │
│  Do you need to build custom software?                                  │
│      │                                                                   │
│      ├── NO ──► Does a SaaS product exist for your need?               │
│      │              │                                                    │
│      │              ├── YES ──► Use SaaS ✅                             │
│      │              │                                                    │
│      │              └── NO ──► Consider building with PaaS             │
│      │                                                                   │
│      └── YES ──► Do you need control over the OS/infrastructure?       │
│                      │                                                   │
│                      ├── YES ──► Use IaaS ✅                            │
│                      │                                                   │
│                      └── NO ──► Use PaaS ✅                             │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Real-World Scenario

```
SCENARIO: Building an e-commerce website

SaaS APPROACH:
└── Use Shopify (complete e-commerce platform)
    • Sign up, add products, start selling
    • Zero development needed
    • Limited customization

PaaS APPROACH:
└── Build custom app on Heroku/App Service
    • Write code in Java/Node/Python
    • Deploy with git push
    • Full custom features
    • Don't manage servers

IaaS APPROACH:
└── Deploy on EC2/VM with custom setup
    • Full control over everything
    • Install specific software versions
    • Configure security precisely
    • Need IT expertise
```

---

## The Shared Responsibility Model

Understanding who is responsible for what is critical for security:

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    SHARED RESPONSIBILITY                                 │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ALWAYS YOUR RESPONSIBILITY:                                            │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │ • Your data                                                       │  │
│  │ • Who can access your resources (identity management)             │  │
│  │ • Your account security (passwords, MFA)                          │  │
│  │ • Compliance with laws/regulations                                │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                                                                          │
│  ALWAYS PROVIDER'S RESPONSIBILITY:                                      │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │ • Physical security of data centers                               │  │
│  │ • Hardware maintenance                                            │  │
│  │ • Network infrastructure                                          │  │
│  │ • Power, cooling, physical security                               │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                                                                          │
│  VARIES BY MODEL:                                                       │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │ IaaS: You secure OS, applications, network config                 │  │
│  │ PaaS: You secure application code and data                        │  │
│  │ SaaS: You secure user access and data                             │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Summary

| Model | What You Get | You Manage | Best For |
|-------|--------------|------------|----------|
| **IaaS** | Virtual infrastructure | OS, runtime, apps | IT teams needing control |
| **PaaS** | Development platform | Just your code | Developers building apps |
| **SaaS** | Ready software | Just your data | End users |

### Key Takeaways

1. **IaaS** = Rent infrastructure (VMs, storage, networks)
2. **PaaS** = Rent a platform (just deploy your code)
3. **SaaS** = Rent software (just use it)
4. Moving from IaaS → PaaS → SaaS = Less control, less responsibility
5. Choose based on your needs, expertise, and requirements

---

## Next Topic

Continue to [Cloud Deployment Models](./03-deployment-models.md) to learn about public, private, hybrid, and multi-cloud strategies.

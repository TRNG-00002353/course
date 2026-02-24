# Cloud Service Models

## The Three Service Models

Cloud computing offers three service models that define **how much you manage** versus **how much the provider manages**.

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

## The Pizza Analogy

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

## Who Manages What?

This is the most important diagram - it shows responsibility at each layer:

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
└─────────────────────────────────────────────────────────────────────────┘
```

---

## IaaS - Infrastructure as a Service

**What it is:** Rent virtual machines, storage, and networks instead of buying hardware.

**Analogy:** Renting an empty apartment - you furnish and maintain it yourself.

| Examples | Best For |
|----------|----------|
| AWS EC2, Azure VMs, GCP Compute Engine | Lift-and-shift migrations |
| AWS S3, Azure Blob Storage | Custom infrastructure needs |
| AWS VPC, Azure VNet | High-performance computing |

---

## PaaS - Platform as a Service

**What it is:** A ready platform where you just deploy your code - no server management.

**Analogy:** Renting a furnished apartment - just bring your belongings.

| Examples | Best For |
|----------|----------|
| Heroku, AWS Elastic Beanstalk | Web applications and APIs |
| Azure App Service, GCP App Engine | Rapid prototyping |
| AWS Lambda, Azure Functions (serverless) | Teams without DevOps expertise |

---

## SaaS - Software as a Service

**What it is:** Complete software you access through your browser - just use it.

**Analogy:** Staying at a hotel - everything is provided.

| Examples | Best For |
|----------|----------|
| Gmail, Microsoft 365 | Email and productivity |
| Salesforce, HubSpot | CRM and sales |
| Slack, Zoom, Teams | Communication |
| Jira, Trello | Project management |

---

## Quick Comparison

| Aspect | IaaS | PaaS | SaaS |
|--------|------|------|------|
| **You manage** | OS, Apps, Data | Apps, Data | Just Data |
| **Provider manages** | Infrastructure | Infra + Platform | Everything |
| **Control** | Most | Moderate | Least |
| **Setup time** | Hours/Days | Minutes/Hours | Minutes |
| **Target user** | IT admins | Developers | End users |

---

## Decision Guide

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    WHICH MODEL TO CHOOSE?                                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
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

---

## Real-World Example

**Building an e-commerce website:**

| Approach | How | Pros/Cons |
|----------|-----|-----------|
| **SaaS** | Use Shopify | Zero coding, limited customization |
| **PaaS** | Build on Heroku | Write code, deploy easily, no server management |
| **IaaS** | Deploy on EC2 | Full control, requires IT expertise |

---

## Summary

| Model | What You Get | You Manage | Best For |
|-------|--------------|------------|----------|
| **IaaS** | Virtual infrastructure | OS, runtime, apps | IT teams needing control |
| **PaaS** | Development platform | Just your code | Developers building apps |
| **SaaS** | Ready software | Just your data | End users |

**Key takeaway:** Moving from IaaS → PaaS → SaaS means less control but also less responsibility. Choose based on your needs and expertise.

# Cloud Deployment Models

## What are Deployment Models?

Deployment models describe **how cloud infrastructure is organized and who can access it**. They define the ownership, location, and accessibility of cloud resources.

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    FOUR DEPLOYMENT MODELS                                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│   PUBLIC CLOUD        PRIVATE CLOUD       HYBRID CLOUD    MULTI-CLOUD   │
│                                                                          │
│   ┌───────────┐      ┌───────────┐      ┌───────────┐    ┌───────────┐ │
│   │ ☁️ Shared │      │ 🔒 Dedicated│     │ ☁️ + 🔒   │    │ ☁️☁️☁️    │ │
│   │   with    │      │    to one │      │  Combined │    │ Multiple  │ │
│   │  others   │      │    org    │      │           │    │ providers │ │
│   └───────────┘      └───────────┘      └───────────┘    └───────────┘ │
│                                                                          │
│   Pay per use         Higher control     Flexibility    Avoid lock-in   │
│   Less control        Higher cost        Complex setup  Complex mgmt    │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Public Cloud

### What is Public Cloud?

Public cloud is cloud infrastructure that is **owned and operated by a third-party provider** and **shared among multiple organizations** (tenants). Resources are delivered over the public internet.

**Think of it as:** Public transportation - shared with others, pay per ride.

### How Public Cloud Works

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    PUBLIC CLOUD ARCHITECTURE                             │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│           CLOUD PROVIDER'S DATA CENTER                                   │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                                                                   │  │
│  │  ┌────────────────────────────────────────────────────────────┐  │  │
│  │  │              SHARED INFRASTRUCTURE                          │  │  │
│  │  │   ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐      │  │  │
│  │  │   │ Server  │  │ Server  │  │ Server  │  │ Server  │      │  │  │
│  │  │   └─────────┘  └─────────┘  └─────────┘  └─────────┘      │  │  │
│  │  │   ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐      │  │  │
│  │  │   │ Storage │  │ Storage │  │ Storage │  │ Storage │      │  │  │
│  │  │   └─────────┘  └─────────┘  └─────────┘  └─────────┘      │  │  │
│  │  └────────────────────────────────────────────────────────────┘  │  │
│  │              │              │              │                      │  │
│  └──────────────┼──────────────┼──────────────┼──────────────────────┘  │
│                 │              │              │                          │
│                 ▼              ▼              ▼                          │
│            ┌────────┐    ┌────────┐    ┌────────┐                       │
│            │Company │    │Company │    │Company │                       │
│            │   A    │    │   B    │    │   C    │                       │
│            └────────┘    └────────┘    └────────┘                       │
│                                                                          │
│  All companies share the same physical infrastructure                   │
│  but are logically isolated from each other                             │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Public Cloud Characteristics

| Characteristic | Description |
|----------------|-------------|
| **Ownership** | Provider owns everything |
| **Location** | Provider's data centers |
| **Access** | Over public internet |
| **Multi-tenancy** | Shared with other customers |
| **Cost Model** | Pay-as-you-go |
| **Scaling** | Virtually unlimited |

### Public Cloud Providers

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    MAJOR PUBLIC CLOUD PROVIDERS                          │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│   AWS (Amazon)        Azure (Microsoft)      GCP (Google)               │
│   ┌───────────┐      ┌───────────┐         ┌───────────┐               │
│   │           │      │           │         │           │               │
│   │  ~33%     │      │  ~22%     │         │  ~10%     │               │
│   │  market   │      │  market   │         │  market   │               │
│   │  share    │      │  share    │         │  share    │               │
│   │           │      │           │         │           │               │
│   └───────────┘      └───────────┘         └───────────┘               │
│                                                                          │
│   Other providers: Oracle Cloud, IBM Cloud, Alibaba Cloud, etc.         │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Pros and Cons

| Pros | Cons |
|------|------|
| ✅ No upfront cost | ❌ Less control |
| ✅ Pay only for what you use | ❌ Shared infrastructure |
| ✅ Virtually unlimited scale | ❌ Data location concerns |
| ✅ No maintenance responsibility | ❌ Potential compliance issues |
| ✅ Global availability | ❌ Internet dependency |
| ✅ Latest technology | ❌ Vendor lock-in risk |

### When to Use Public Cloud

```
✅ IDEAL FOR:
├── Startups and small businesses
├── Variable or unpredictable workloads
├── Development and testing environments
├── Web applications and APIs
├── Big data and analytics
├── Disaster recovery
└── Global deployments

❌ AVOID WHEN:
├── Strict data residency requirements
├── Highly regulated industries (sometimes)
├── Need complete infrastructure control
└── Sensitive data with compliance restrictions
```

---

## Private Cloud

### What is Private Cloud?

Private cloud is cloud infrastructure **dedicated to a single organization**. It can be hosted on-premises in your own data center or by a third-party provider, but resources are not shared with others.

**Think of it as:** Your own car - only you use it, more control, higher cost.

### Private Cloud Architecture

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    PRIVATE CLOUD ARCHITECTURE                            │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│   OPTION 1: On-Premises                OPTION 2: Hosted Private         │
│                                                                          │
│   ┌────────────────────────┐          ┌────────────────────────┐       │
│   │  YOUR DATA CENTER       │          │  PROVIDER DATA CENTER  │       │
│   │                         │          │                        │       │
│   │  ┌──────────────────┐  │          │  ┌──────────────────┐ │       │
│   │  │ YOUR SERVERS     │  │          │  │ DEDICATED TO YOU │ │       │
│   │  │ YOUR STORAGE     │  │          │  │ Managed by       │ │       │
│   │  │ YOUR NETWORK     │  │          │  │ provider         │ │       │
│   │  └──────────────────┘  │          │  └──────────────────┘ │       │
│   │                         │          │                        │       │
│   │  🔧 You manage all     │          │  🔧 Provider manages   │       │
│   │     hardware            │          │     some/all           │       │
│   │                         │          │                        │       │
│   └────────────────────────┘          └────────────────────────┘       │
│                                                                          │
│   Only YOUR organization accesses these resources                       │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Private Cloud Types

| Type | Description | Who Manages |
|------|-------------|-------------|
| **On-Premises** | In your own data center | You manage everything |
| **Hosted** | In provider's data center, dedicated to you | Provider manages hardware |
| **Virtual Private** | Isolated section in public cloud | Provider manages, you configure |

### Private Cloud Characteristics

| Characteristic | Description |
|----------------|-------------|
| **Ownership** | You own or lease dedicated infrastructure |
| **Location** | Your premises or dedicated hosted |
| **Access** | Private network (VPN, direct connect) |
| **Single-tenancy** | Only your organization uses it |
| **Cost Model** | Higher fixed costs |
| **Scaling** | Limited by your infrastructure |

### Pros and Cons

| Pros | Cons |
|------|------|
| ✅ Complete control | ❌ Higher cost |
| ✅ Enhanced security | ❌ Limited scalability |
| ✅ Compliance friendly | ❌ Maintenance responsibility |
| ✅ Customization | ❌ Requires expertise |
| ✅ Data stays local | ❌ Slower to provision |
| ✅ No multi-tenancy | ❌ Capital expenditure |

### When to Use Private Cloud

```
✅ IDEAL FOR:
├── Regulated industries (healthcare, finance, government)
├── Sensitive data requirements
├── Strict compliance needs (HIPAA, PCI-DSS)
├── Predictable, steady workloads
├── Legacy application requirements
├── Complete control needed
└── Data sovereignty requirements

❌ AVOID WHEN:
├── Limited IT budget
├── Need rapid scaling
├── Variable workloads
├── Small IT team
└── Quick time-to-market needed
```

---

## Hybrid Cloud

### What is Hybrid Cloud?

Hybrid cloud **combines public and private cloud**, allowing data and applications to be shared between them. Organizations can keep sensitive data on private cloud while leveraging public cloud for other workloads.

**Think of it as:** Using your car for daily commute but taking a flight for long trips - best of both worlds.

### Hybrid Cloud Architecture

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    HYBRID CLOUD ARCHITECTURE                             │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ┌─────────────────────────┐         ┌─────────────────────────┐       │
│  │     PRIVATE CLOUD       │         │      PUBLIC CLOUD       │       │
│  │                         │         │                         │       │
│  │  ┌───────────────────┐ │         │ ┌───────────────────┐   │       │
│  │  │ Sensitive Data    │ │◄───────►│ │ Web Applications  │   │       │
│  │  │ Core Banking      │ │ Connect │ │ Dev/Test          │   │       │
│  │  │ Patient Records   │ │         │ │ Analytics         │   │       │
│  │  │ Legacy Apps       │ │         │ │ Burst Capacity    │   │       │
│  │  └───────────────────┘ │         │ └───────────────────┘   │       │
│  │                         │         │                         │       │
│  │  🔒 High Security      │         │  📈 Elastic Scale       │       │
│  │  📋 Compliance         │         │  💰 Pay per use         │       │
│  │                         │         │                         │       │
│  └─────────────────────────┘         └─────────────────────────┘       │
│                                                                          │
│              Connected via VPN, Direct Connect, etc.                    │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Common Hybrid Cloud Patterns

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    HYBRID CLOUD USE PATTERNS                             │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  PATTERN 1: Cloud Bursting                                              │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                                                                   │  │
│  │  Normal Load:              Peak Load (Holiday Sale):             │  │
│  │  ┌─────────┐               ┌─────────┐  ┌─────────┐              │  │
│  │  │ Private │               │ Private │──│ Public  │              │  │
│  │  │  100%   │               │  100%   │  │ +200%   │              │  │
│  │  └─────────┘               └─────────┘  └─────────┘              │  │
│  │                                                                   │  │
│  │  Use private cloud normally, "burst" to public for spikes        │  │
│  │                                                                   │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                                                                          │
│  PATTERN 2: Tiered Data                                                 │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                                                                   │  │
│  │  Private Cloud:                    Public Cloud:                 │  │
│  │  ┌───────────────────┐            ┌───────────────────┐         │  │
│  │  │ Hot data (recent) │            │ Cold data (archive)│         │  │
│  │  │ Frequently used   │───────────▶│ Rarely accessed    │         │  │
│  │  │ High performance  │            │ Low cost storage   │         │  │
│  │  └───────────────────┘            └───────────────────┘         │  │
│  │                                                                   │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                                                                          │
│  PATTERN 3: Dev/Test in Cloud, Production On-Prem                       │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │                                                                   │  │
│  │  Public Cloud:                    Private Cloud:                 │  │
│  │  ┌───────────────────┐            ┌───────────────────┐         │  │
│  │  │ Development       │            │ Production        │         │  │
│  │  │ Testing           │───Deploy──▶│ Live systems      │         │  │
│  │  │ Staging           │            │ Customer data     │         │  │
│  │  └───────────────────┘            └───────────────────┘         │  │
│  │                                                                   │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Pros and Cons

| Pros | Cons |
|------|------|
| ✅ Flexibility | ❌ Complex setup |
| ✅ Cost optimization | ❌ Requires expertise |
| ✅ Compliance + scale | ❌ Integration challenges |
| ✅ Avoid vendor lock-in | ❌ Security complexity |
| ✅ Gradual migration path | ❌ Higher management overhead |

### When to Use Hybrid Cloud

```
✅ IDEAL FOR:
├── Organizations with compliance requirements AND need for scale
├── Gradual cloud migration strategies
├── Variable workloads with baseline on-prem
├── Disaster recovery (replicate to cloud)
├── Data sovereignty + global presence needs
└── Legacy + modern application mix

❌ CHALLENGING WHEN:
├── Small IT teams
├── Limited cloud expertise
├── Simple workloads
└── Budget constraints for hybrid infrastructure
```

---

## Multi-Cloud

### What is Multi-Cloud?

Multi-cloud is using **multiple public cloud providers** simultaneously. An organization might use AWS for compute, Azure for AI services, and GCP for data analytics.

**Think of it as:** Having accounts with multiple airlines - flexibility to choose the best for each trip.

### Multi-Cloud Architecture

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    MULTI-CLOUD ARCHITECTURE                              │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│                         YOUR ORGANIZATION                                │
│                               │                                          │
│           ┌───────────────────┼───────────────────┐                     │
│           │                   │                   │                      │
│           ▼                   ▼                   ▼                      │
│   ┌───────────────┐   ┌───────────────┐   ┌───────────────┐            │
│   │     AWS       │   │    AZURE      │   │     GCP       │            │
│   │               │   │               │   │               │            │
│   │ • EC2 Compute │   │ • AI/ML       │   │ • BigQuery    │            │
│   │ • S3 Storage  │   │ • Office 365  │   │ • Kubernetes  │            │
│   │ • Lambda      │   │ • .NET Apps   │   │ • TensorFlow  │            │
│   │               │   │               │   │               │            │
│   └───────────────┘   └───────────────┘   └───────────────┘            │
│                                                                          │
│   Use the best service from each provider                               │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Multi-Cloud Strategies

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    MULTI-CLOUD STRATEGIES                                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  STRATEGY 1: Best-of-Breed                                              │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │  Use each provider for what they do best:                         │  │
│  │  • AWS: Broad service catalog, EC2, Lambda                        │  │
│  │  • Azure: Enterprise integrations, .NET, Office                   │  │
│  │  • GCP: Data analytics, BigQuery, Kubernetes                      │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                                                                          │
│  STRATEGY 2: Redundancy/DR                                              │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │  Same workload on multiple clouds for resilience:                 │  │
│  │  • Primary: AWS us-east-1                                         │  │
│  │  • Backup: Azure East US                                          │  │
│  │  • If AWS fails, traffic shifts to Azure                          │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                                                                          │
│  STRATEGY 3: Avoid Lock-in                                              │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │  Distribute workloads to prevent dependency:                      │  │
│  │  • 50% on AWS                                                     │  │
│  │  • 50% on Azure                                                   │  │
│  │  • Can negotiate better pricing                                   │  │
│  │  • Can migrate if needed                                          │  │
│  └──────────────────────────────────────────────────────────────────┘  │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Pros and Cons

| Pros | Cons |
|------|------|
| ✅ Avoid vendor lock-in | ❌ Complex management |
| ✅ Best-of-breed services | ❌ Multiple skill sets needed |
| ✅ Negotiate better pricing | ❌ Data transfer costs |
| ✅ Redundancy | ❌ Security complexity |
| ✅ Geographic flexibility | ❌ Integration challenges |
| ✅ Compliance flexibility | ❌ Inconsistent tooling |

### When to Use Multi-Cloud

```
✅ IDEAL FOR:
├── Large enterprises with diverse needs
├── Avoiding vendor dependency
├── Regulatory requirements for multiple regions
├── Best-of-breed service selection
├── Mergers/acquisitions (inherited different clouds)
└── Disaster recovery across providers

❌ CHALLENGING WHEN:
├── Small organizations
├── Limited cloud expertise
├── Simple workloads
├── Budget constraints
└── Need for simplicity
```

---

## Comparing Deployment Models

### Quick Comparison

| Aspect | Public | Private | Hybrid | Multi-Cloud |
|--------|--------|---------|--------|-------------|
| **Cost** | Pay-per-use | High fixed | Medium | High |
| **Control** | Low | High | Medium | Medium |
| **Security** | Shared | Dedicated | Mixed | Complex |
| **Scalability** | Unlimited | Limited | Flexible | Unlimited |
| **Complexity** | Low | Medium | High | Very High |
| **Compliance** | Challenging | Easier | Flexible | Complex |

### Decision Guide

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    WHICH DEPLOYMENT MODEL?                               │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  START: What are your main requirements?                                │
│                                                                          │
│  "I need lowest cost and fastest setup"                                 │
│       └──► PUBLIC CLOUD                                                 │
│                                                                          │
│  "I need complete control and have compliance requirements"             │
│       └──► PRIVATE CLOUD                                                │
│                                                                          │
│  "I need compliance AND scalability"                                    │
│       └──► HYBRID CLOUD                                                 │
│                                                                          │
│  "I want best services from each provider and no lock-in"               │
│       └──► MULTI-CLOUD                                                  │
│                                                                          │
│  "I'm not sure / We're just starting"                                   │
│       └──► Start with PUBLIC CLOUD, evolve as needed                   │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Real-World Examples

### Public Cloud
- **Netflix**: Runs entirely on AWS
- **Spotify**: Uses Google Cloud
- **Startups**: Most start on public cloud

### Private Cloud
- **Banks**: Core banking on private infrastructure
- **Healthcare**: Patient data systems
- **Government**: Classified systems

### Hybrid Cloud
- **Retail**: Private for POS, public for e-commerce
- **Manufacturing**: Private for factory systems, public for analytics
- **Finance**: Private for trading, public for customer apps

### Multi-Cloud
- **Large enterprises**: Using AWS, Azure, and GCP together
- **Media companies**: Content on multiple providers
- **Global companies**: Different providers in different regions

---

## Summary

| Model | Best For | Key Benefit |
|-------|----------|-------------|
| **Public** | Startups, variable workloads | Cost efficiency, scalability |
| **Private** | Regulated industries | Control, compliance |
| **Hybrid** | Enterprises with mixed needs | Flexibility |
| **Multi-Cloud** | Large orgs avoiding lock-in | Best-of-breed |

### Key Takeaways

1. **Public cloud** = Shared, pay-per-use, most common choice
2. **Private cloud** = Dedicated, secure, compliance-friendly
3. **Hybrid cloud** = Best of both worlds, complex to manage
4. **Multi-cloud** = Multiple providers, maximum flexibility
5. Most organizations end up with **hybrid** or **multi-cloud** over time

---

## Next Topic

Continue to [Cloud Providers Overview](./04-cloud-providers-overview.md) to learn about the major cloud providers and their services.

# Java SE to EE Evolution

## Java Editions Overview

Java comes in three editions, each targeting different use cases:

```
┌─────────────────────────────────────────────────────────────┐
│                    Java Editions                            │
├─────────────────┬─────────────────┬─────────────────────────┤
│    Java SE      │    Java EE      │      Java ME            │
│   (Standard)    │  (Enterprise)   │      (Micro)            │
├─────────────────┼─────────────────┼─────────────────────────┤
│ Desktop apps    │ Web apps        │ Mobile/Embedded         │
│ Console apps    │ Enterprise apps │ IoT devices             │
│ Core libraries  │ Distributed     │ Resource-constrained    │
└─────────────────┴─────────────────┴─────────────────────────┘
```

---

## Java SE (Standard Edition)

Java SE provides the core Java platform:

| Component | What It Provides |
|-----------|------------------|
| **JVM** | Runtime environment |
| **Core APIs** | Collections, I/O, Networking, Concurrency |
| **JDBC** | Database connectivity |
| **Swing/JavaFX** | Desktop GUI |

**Limitations for Enterprise Development:**
- No built-in web server
- No standard component model for distributed systems
- Manual threading and connection management
- No declarative transaction management

---

## Java EE (Enterprise Edition)

Java EE extends SE with specifications for enterprise features:

```
┌─────────────────────────────────────────┐
│            Java EE                       │
│  ┌─────────────────────────────────┐    │
│  │  Servlets, JSP    (Web Layer)   │    │
│  ├─────────────────────────────────┤    │
│  │  EJB, CDI     (Business Layer)  │    │
│  ├─────────────────────────────────┤    │
│  │  JPA, JTA      (Data Layer)     │    │
│  ├─────────────────────────────────┤    │
│  │  JMS, JAX-RS  (Integration)     │    │
│  └─────────────────────────────────┘    │
│         Built on Java SE                 │
└─────────────────────────────────────────┘
```

### Key Java EE Specifications

| Specification | Purpose |
|---------------|---------|
| **Servlet** | HTTP request/response handling |
| **JSP** | Dynamic HTML generation |
| **EJB** | Business components with transactions, security |
| **JPA** | Object-relational mapping |
| **CDI** | Dependency injection |
| **JAX-RS** | RESTful web services |
| **JMS** | Asynchronous messaging |

---

## The Evolution Timeline

```
1995  Java 1.0 (SE only)
  │
1999  J2EE 1.2 - First enterprise edition
  │              Servlets, JSP, EJB, JDBC
  │
2006  Java EE 5 - Simplified with annotations
  │              JPA introduced, EJB 3.0
  │
2013  Java EE 7 - WebSocket, JSON-P, Batch
  │
2017  Java EE 8 - Final Oracle release
  │
2018  Jakarta EE 8 - Eclipse Foundation takes over
  │                  (javax.* → jakarta.*)
  │
2023  Jakarta EE 10 - Active development continues
```

---

## Why Spring Emerged

Java EE had pain points that Spring addressed:

| Java EE Challenge | Spring Solution |
|-------------------|-----------------|
| Heavy application servers (WebLogic, WebSphere) | Runs on lightweight Tomcat |
| Complex EJB configuration | Simple POJOs with annotations |
| Tight coupling to container | Container-agnostic design |
| Slow development cycle | Rapid development, hot reload |
| Difficult unit testing | Designed for testability |

### The Result

```
Java SE  ──►  Java EE (heavyweight)  ──►  Spring (lightweight alternative)
                    │                           │
                    └───────────────────────────┘
                      Spring uses EE specs (JPA, Servlet)
                      but simplifies the programming model
```

---

## Modern Landscape

Today, most enterprise Java applications use:

- **Spring Framework** - For the programming model and DI
- **Java EE/Jakarta EE specs** - JPA, Servlet, Bean Validation
- **Spring Boot** - For auto-configuration and embedded servers

Spring didn't replace Java EE; it built a better developer experience on top of its standards.

---

## Summary

| Edition | Target | Key Point |
|---------|--------|-----------|
| **Java SE** | Core/Desktop | Foundation for all Java |
| **Java EE** | Enterprise | Specifications for web, transactions, messaging |
| **Spring** | Enterprise | Lightweight implementation using EE specs |

**Next:** [Spring Framework Overview](./01-spring-overview.md) - Understanding Spring's architecture and ecosystem

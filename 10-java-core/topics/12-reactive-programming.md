# Reactive Programming in Java

## Table of Contents

### Required Topics
- [Introduction to Reactive Programming](#introduction-to-reactive-programming)
- [The Reactive Manifesto](#the-reactive-manifesto)
- [Reactive Streams Specification](#reactive-streams-specification)
- [Core Concepts](#core-concepts)
- [Reactive vs Traditional Programming](#reactive-vs-traditional-programming)
- [Summary](#summary)

### Optional / Additional Reference (Library Implementation)
- [Project Reactor Overview](#project-reactor-overview-optional)
- [Working with Mono](#working-with-mono-optional)
- [Working with Flux](#working-with-flux-optional)
- [Operators](#operators-optional)
- [Building Reactive Applications](#building-reactive-applications-optional)

---

# Introduction to Reactive Programming

## What is Reactive Programming?

Reactive programming is a **declarative programming paradigm** focused on **data streams** and the **propagation of change**. Instead of writing code that explicitly handles each step, you describe what should happen when data arrives.

### Key Idea: Data Flows Like a Stream

Think of reactive programming like a spreadsheet:
- When you change cell A1, cells that depend on A1 automatically update
- You don't manually tell each cell to recalculate
- Changes **propagate** through the system

```
Traditional (Imperative):
    You ask for data → Wait → Get data → Process → Ask for more...

Reactive:
    Subscribe to data stream → Data comes to you → React to each item
```

### Real-World Analogies

| Analogy | Traditional | Reactive |
|---------|-------------|----------|
| Restaurant | Waiter checks if food is ready (polling) | Kitchen rings bell when ready (push) |
| News | Buy newspaper daily | Subscribe to news feed |
| Email | Refresh inbox manually | Notifications push to you |
| Stock Prices | Check website periodically | Real-time ticker updates |

### Why Reactive Programming?

1. **Scalability**: Handle thousands of concurrent connections with minimal resources
2. **Responsiveness**: System stays responsive under load
3. **Resilience**: Built-in error handling and recovery mechanisms
4. **Resource Efficiency**: Better utilization of CPU and memory
5. **Real-time Data**: Natural fit for streaming data, events, and notifications

---

# The Reactive Manifesto

The [Reactive Manifesto](https://www.reactivemanifesto.org/) defines four key principles for building reactive systems. Published in 2014, it provides a foundation for understanding reactive architecture.

## The Four Pillars

```
                    ┌─────────────────┐
                    │   RESPONSIVE    │
                    │   (The Goal)    │
                    └────────┬────────┘
                             │
              ┌──────────────┴──────────────┐
              │                             │
     ┌────────▼────────┐          ┌─────────▼───────┐
     │    RESILIENT    │          │     ELASTIC     │
     │ (Handle Failure)│          │  (Handle Load)  │
     └────────┬────────┘          └─────────┬───────┘
              │                             │
              └──────────────┬──────────────┘
                             │
                    ┌────────▼────────┐
                    │  MESSAGE-DRIVEN │
                    │ (The Foundation)│
                    └─────────────────┘
```

## 1. Responsive

> "The system responds in a timely manner if at all possible."

**What it means:**
- Users receive responses quickly
- Consistent response times (not fast sometimes, slow other times)
- Problems are detected rapidly
- System provides meaningful feedback

**How to achieve:**
- Set timeouts on operations
- Provide fallback responses when primary fails
- Monitor response times
- Fail fast rather than hang

```
Example: Online Store Search

Non-Responsive:
User searches → Waits 30 seconds → Finally gets results (or timeout)

Responsive:
User searches → Gets results in 200ms
               → If slow, shows cached results with "updating..." message
               → If service down, shows "Try again" immediately
```

## 2. Resilient

> "The system stays responsive in the face of failure."

**What it means:**
- Failures are contained (don't cascade through system)
- Failed components can recover
- System degrades gracefully, not catastrophically

**How to achieve:**
- Isolate components (failure in one doesn't break others)
- Use replication (backup systems)
- Implement retry logic with limits
- Provide fallback behavior

```
Example: E-commerce Checkout

Non-Resilient:
Payment service down → Entire website crashes

Resilient:
Payment service down → Checkout shows "Payment temporarily unavailable"
                     → Rest of site works normally
                     → Retries payment service in background
                     → Alerts operations team
```

## 3. Elastic

> "The system stays responsive under varying workload."

**What it means:**
- Scale up when demand increases
- Scale down when demand decreases (cost efficiency)
- No single point of contention
- Handle traffic spikes without degradation

**How to achieve:**
- Design for horizontal scaling (add more servers)
- Avoid shared mutable state
- Use load balancing
- Implement backpressure (control flow when overwhelmed)

```
Example: Video Streaming Service

Non-Elastic:
Normal: 1000 users, works fine
Spike: 10000 users, crashes

Elastic:
Normal: 1000 users, 2 servers
Spike: 10000 users, auto-scales to 20 servers
After spike: Scales back down to 2 servers
```

## 4. Message-Driven

> "The system relies on asynchronous message-passing."

**What it means:**
- Components communicate via messages, not direct calls
- Asynchronous (sender doesn't wait for receiver)
- Location transparency (don't need to know where receiver is)
- Enables loose coupling between components

**How to achieve:**
- Use message queues
- Implement event-driven architecture
- Non-blocking I/O
- Support backpressure in message flow

```
Example: Order Processing

Synchronous (Not Message-Driven):
User → Order Service → Inventory Service → Payment Service → Shipping Service
       (waits)         (waits)             (waits)           (waits)
Total time: Sum of all service times

Message-Driven:
User → Order Service → [Message Queue]
                            ↓
       Inventory, Payment, Shipping all process independently
       User gets immediate confirmation
       Services communicate via messages
```

## Why These Four Together?

| Principle | Enables | Depends On |
|-----------|---------|------------|
| Message-Driven | Foundation for all others | - |
| Resilient | Stay responsive during failure | Message-Driven |
| Elastic | Stay responsive under load | Message-Driven |
| Responsive | The ultimate goal | Resilient + Elastic |

---

# Reactive Streams Specification

Reactive Streams is a **standard specification** (not a library) for asynchronous stream processing with non-blocking backpressure. It was created to provide interoperability between reactive libraries.

## The Problem It Solves

```
Without Standard:
Library A ──(incompatible)──✗── Library B
RxJava ──(incompatible)──✗── Akka Streams
Custom Publisher ──(incompatible)──✗── Any Subscriber

With Reactive Streams Standard:
Library A ──(Publisher interface)── Library B ✓
RxJava ──(implements spec)── Akka Streams ✓
Any Publisher ──(standard)── Any Subscriber ✓
```

## The Four Core Interfaces

Reactive Streams defines just **four interfaces** in the `org.reactivestreams` package:

### 1. Publisher<T>

The **source** of data. Produces elements and sends them to subscribers.

```java
public interface Publisher<T> {
    void subscribe(Subscriber<? super T> subscriber);
}
```

- Can have multiple subscribers
- Sends data only when subscriber is ready (backpressure)
- Notifies subscriber of completion or errors

### 2. Subscriber<T>

The **consumer** of data. Receives elements from a publisher.

```java
public interface Subscriber<T> {
    void onSubscribe(Subscription subscription);  // Called first
    void onNext(T item);                          // Called for each item
    void onError(Throwable throwable);            // Called on error (terminal)
    void onComplete();                            // Called when done (terminal)
}
```

**Lifecycle:**
```
onSubscribe → onNext* → (onComplete | onError)

* = zero or more times
Only ONE of onComplete OR onError is called (never both)
```

### 3. Subscription

The **connection** between Publisher and Subscriber. This is where **backpressure** happens.

```java
public interface Subscription {
    void request(long n);  // "I'm ready for n more items"
    void cancel();         // "I don't want any more items"
}
```

- Subscriber controls the flow by calling `request(n)`
- Publisher sends at most `n` items until next `request()`
- This prevents fast publisher from overwhelming slow subscriber

### 4. Processor<T, R>

Both a Subscriber AND a Publisher. **Transforms** data in the middle of a stream.

```java
public interface Processor<T, R> extends Subscriber<T>, Publisher<R> {
    // Receives T, publishes R
}
```

## How They Work Together

```
┌──────────────┐                              ┌──────────────┐
│   Publisher  │                              │  Subscriber  │
│              │  1. subscribe(subscriber)    │              │
│              │ ─────────────────────────►   │              │
│              │                              │              │
│              │  2. onSubscribe(subscription)│              │
│              │ ◄─────────────────────────   │              │
│              │                              │              │
│              │  3. request(n)               │              │
│              │ ◄─────────────────────────   │ (via sub-    │
│              │                              │  scription)  │
│   sends      │  4. onNext(item) × n times   │              │
│   n items    │ ─────────────────────────►   │  receives    │
│              │                              │  items       │
│              │  5. request(m)               │              │
│              │ ◄─────────────────────────   │              │
│              │                              │              │
│              │  6. onNext(item) × m times   │              │
│              │ ─────────────────────────►   │              │
│              │                              │              │
│              │  7. onComplete() or onError()│              │
│              │ ─────────────────────────►   │              │
└──────────────┘                              └──────────────┘
```

## Backpressure: The Key Innovation

**Backpressure** is a mechanism for the subscriber to control how fast data flows.

### The Problem Without Backpressure

```
Fast Publisher (1000 items/sec)  →  Slow Subscriber (10 items/sec)
                                          ↓
                                    Buffer grows...
                                    Buffer grows...
                                    OUT OF MEMORY! 💥
```

### The Solution With Backpressure

```
Publisher                              Subscriber
    │                                      │
    │  ◄── request(10) ───────────────────│  "I can handle 10"
    │                                      │
    │  ─── sends 10 items ───────────────►│
    │                                      │  (processes 10 items)
    │  ◄── request(10) ───────────────────│  "Ready for 10 more"
    │                                      │
    │  ─── sends 10 items ───────────────►│
    │                                      │

No buffer overflow! Subscriber controls the pace.
```

### Backpressure Strategies

| Strategy | Description | Use Case |
|----------|-------------|----------|
| **Request** | Subscriber requests exact amount needed | Most common |
| **Buffer** | Buffer excess items (with limit) | Bursty data |
| **Drop** | Drop items when overwhelmed | Real-time data where old = stale |
| **Latest** | Keep only most recent item | UI updates, sensor readings |
| **Error** | Signal error when overwhelmed | Strict flow control required |

---

# Core Concepts

## Push vs Pull

| Aspect | Pull (Traditional) | Push (Reactive) |
|--------|-------------------|-----------------|
| Who initiates | Consumer asks for data | Producer sends data |
| Blocking | Consumer waits | Consumer notified |
| Example | `iterator.next()` | `subscriber.onNext(item)` |
| Analogy | Checking mailbox | Mail delivered to you |

```
Pull Model:
while (iterator.hasNext()) {     // "Do you have data?"
    Item item = iterator.next();  // "Give me the data"
    process(item);                // Wait for next iteration
}

Push Model:
source.subscribe(item -> {        // "Tell me when you have data"
    process(item);                // Called automatically when data arrives
});
// Code continues, not blocked
```

## Synchronous vs Asynchronous

```
Synchronous (Blocking):
┌─────────────────────────────────────────────────┐
│ Thread 1                                        │
│ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐│
│ │ Task A  │ │ Wait... │ │ Task B  │ │ Wait... ││
│ │ (work)  │ │(blocked)│ │ (work)  │ │(blocked)││
│ └─────────┘ └─────────┘ └─────────┘ └─────────┘│
└─────────────────────────────────────────────────┘
Thread is blocked during waits → wasted resource

Asynchronous (Non-Blocking):
┌─────────────────────────────────────────────────┐
│ Thread 1                                        │
│ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐│
│ │ Task A  │ │ Task C  │ │ Task B  │ │ Task D  ││
│ │ (start) │ │ (start) │ │(continue│ │ (start) ││
│ └─────────┘ └─────────┘ └─────────┘ └─────────┘│
└─────────────────────────────────────────────────┘
Thread handles other work while waiting → efficient
```

## Hot vs Cold Streams

| Type | Behavior | Example |
|------|----------|---------|
| **Cold** | Starts fresh for each subscriber | Reading a file |
| **Hot** | Emits regardless of subscribers | Mouse clicks, stock prices |

```
Cold Stream:
Subscriber 1 joins → Gets: 1, 2, 3, 4, 5
Subscriber 2 joins → Gets: 1, 2, 3, 4, 5  (starts from beginning)

Hot Stream:
Stream emitting: ... 3, 4, 5, 6, 7, 8 ...
Subscriber 1 joins at 4 → Gets: 4, 5, 6, 7, 8 ...
Subscriber 2 joins at 7 → Gets: 7, 8 ...  (misses 4, 5, 6)
```

## Operators: Transforming Streams

Operators are functions that transform streams. They form a **pipeline**.

```
Source Stream: ──(1)──(2)──(3)──(4)──(5)──|

     ↓ filter(x > 2)

Filtered:      ──(3)──(4)──(5)──|

     ↓ map(x * 10)

Mapped:        ──(30)──(40)──(50)──|

     ↓ take(2)

Final:         ──(30)──(40)──|
```

### Categories of Operators

| Category | Purpose | Examples |
|----------|---------|----------|
| **Creation** | Create streams | just, from, range, interval |
| **Transformation** | Change elements | map, flatMap, scan |
| **Filtering** | Select elements | filter, take, skip, distinct |
| **Combining** | Merge streams | merge, zip, concat |
| **Error Handling** | Handle failures | onError, retry, fallback |
| **Utility** | Side effects, timing | doOnNext, delay, timeout |

---

# Reactive vs Traditional Programming

## Comparison Table

| Aspect | Traditional (Imperative) | Reactive |
|--------|-------------------------|----------|
| **Execution** | Sequential, step-by-step | Declarative pipeline |
| **Blocking** | Threads block waiting for I/O | Non-blocking, async |
| **Data Flow** | Pull (request data) | Push (data arrives) |
| **Concurrency** | Thread per request | Few threads, many requests |
| **Error Handling** | try-catch blocks | Error signals in stream |
| **Composition** | Nested callbacks, complex | Fluent operator chains |
| **Backpressure** | Manual (queues, throttling) | Built-in mechanism |
| **Resource Usage** | High (blocked threads) | Low (efficient) |

## Code Comparison

### Traditional Approach

```java
// Blocking, sequential
public List<String> getUserEmails(List<Long> userIds) {
    List<String> emails = new ArrayList<>();

    for (Long id : userIds) {
        try {
            User user = userRepository.findById(id);  // Blocks
            if (user != null && user.isActive()) {
                String email = emailService.getEmail(user);  // Blocks
                if (email != null) {
                    emails.add(email.toLowerCase());
                }
            }
        } catch (Exception e) {
            log.error("Failed for user: " + id, e);
            // Continue to next user
        }
    }

    return emails;
}
```

**Problems:**
- Each database/service call blocks the thread
- Thread sits idle waiting for I/O
- With 1000 concurrent requests, need ~1000 threads
- Hard to compose and maintain

### Reactive Approach (Conceptual)

```java
// Non-blocking, declarative
public Stream<String> getUserEmails(List<Long> userIds) {
    return userIds.stream()
        .map(id -> userRepository.findById(id))      // Non-blocking
        .filter(user -> user != null && user.isActive())
        .map(user -> emailService.getEmail(user))    // Non-blocking
        .filter(email -> email != null)
        .map(String::toLowerCase)
        .onError(e -> log.error("Failed", e));       // Error handling
}
```

**Benefits:**
- Operations don't block threads
- Resources freed while waiting
- Same 1000 requests handled by ~10 threads
- Clear, composable pipeline

## When to Use Reactive

### Good Use Cases

| Scenario | Why Reactive Helps |
|----------|-------------------|
| High concurrency | Handle many connections with few threads |
| Streaming data | Natural fit for continuous data flow |
| Microservices | Non-blocking service-to-service calls |
| Real-time apps | WebSockets, live updates, notifications |
| I/O heavy apps | Don't waste threads waiting for I/O |

### Not Ideal For

| Scenario | Why |
|----------|-----|
| CPU-intensive | Reactive doesn't help with computation time |
| Simple CRUD | Adds complexity without much benefit |
| Small scale | Overhead not justified |
| Team unfamiliar | Learning curve can slow development |

---

## Summary

### Required Concepts

| Concept | Description |
|---------|-------------|
| **Reactive Programming** | Declarative paradigm for async data streams |
| **Reactive Manifesto** | Responsive, Resilient, Elastic, Message-Driven |
| **Publisher** | Source that produces data |
| **Subscriber** | Consumer that receives data |
| **Subscription** | Connection enabling backpressure |
| **Backpressure** | Subscriber controls data flow rate |
| **Push vs Pull** | Data pushed to you vs you request data |
| **Hot vs Cold** | Streams that share vs restart for each subscriber |

### The Four Reactive Streams Interfaces

```java
Publisher<T>    → void subscribe(Subscriber<? super T> s)
Subscriber<T>   → onSubscribe, onNext, onError, onComplete
Subscription    → void request(long n), void cancel()
Processor<T,R>  → extends Subscriber<T>, Publisher<R>
```

### Key Takeaways

1. **Reactive = Async + Streams + Backpressure**
2. **Responsive** is the goal, **Message-Driven** is the foundation
3. **Backpressure** prevents fast producers from overwhelming slow consumers
4. **Non-blocking** means threads aren't wasted waiting
5. Use reactive for **high concurrency** and **I/O-bound** applications

---

# Additional Reference (Optional)

> The following sections cover implementation using Project Reactor library. These require understanding of Maven/Gradle for dependency management and are provided for reference when you're ready to implement reactive code.

---

## Project Reactor Overview (Optional)

**Project Reactor** is the reactive library used by Spring WebFlux. It implements the Reactive Streams specification.

### Setup (Maven)

```xml
<dependency>
    <groupId>io.projectreactor</groupId>
    <artifactId>reactor-core</artifactId>
    <version>3.5.0</version>
</dependency>
```

### Two Main Types

| Type | Description | Conceptual |
|------|-------------|------------|
| `Mono<T>` | 0 or 1 element | Like `Optional<T>` but async |
| `Flux<T>` | 0 to N elements | Like `Stream<T>` but async |

```
Mono<T>:  ────(value)────|   or   ────|   or   ────X
           (0 or 1 value)       (empty)      (error)

Flux<T>:  ──(v1)──(v2)──(v3)──|   or   ──(v1)──X
            (0 to N values)              (error)
```

---

## Working with Mono (Optional)

### Creating Mono

```java
import reactor.core.publisher.Mono;

// From value
Mono<String> mono1 = Mono.just("Hello");

// Empty Mono
Mono<String> mono2 = Mono.empty();

// From nullable (empty if null)
Mono<String> mono3 = Mono.justOrEmpty(nullableValue);

// From Supplier (lazy)
Mono<String> mono4 = Mono.fromSupplier(() -> expensiveOperation());

// Error Mono
Mono<String> mono5 = Mono.error(new RuntimeException("Failed"));
```

### Subscribing

```java
Mono<String> mono = Mono.just("Hello");

// With all handlers
mono.subscribe(
    value -> System.out.println("Value: " + value),
    error -> System.err.println("Error: " + error),
    () -> System.out.println("Completed!")
);
```

### Transforming

```java
Mono<String> mono = Mono.just("hello");

mono.map(String::toUpperCase)         // Transform value
    .filter(s -> s.length() > 3)      // Keep if matches
    .defaultIfEmpty("default")         // Fallback if empty
    .subscribe(System.out::println);   // HELLO
```

---

## Working with Flux (Optional)

### Creating Flux

```java
import reactor.core.publisher.Flux;

// From values
Flux<String> flux1 = Flux.just("A", "B", "C");

// From collection
Flux<String> flux2 = Flux.fromIterable(list);

// Range
Flux<Integer> flux3 = Flux.range(1, 5);  // 1, 2, 3, 4, 5

// Interval (emits every duration)
Flux<Long> flux4 = Flux.interval(Duration.ofSeconds(1));
```

### Transforming

```java
Flux<Integer> flux = Flux.range(1, 10);

flux.filter(n -> n % 2 == 0)    // Keep even: 2, 4, 6, 8, 10
    .map(n -> n * 10)            // Multiply: 20, 40, 60, 80, 100
    .take(3)                     // First 3: 20, 40, 60
    .subscribe(System.out::println);
```

### Combining

```java
Flux<String> letters = Flux.just("A", "B", "C");
Flux<Integer> numbers = Flux.just(1, 2, 3);

// Zip: Combine element by element
Flux.zip(letters, numbers, (l, n) -> l + n)
    .subscribe(System.out::println);  // A1, B2, C3
```

---

## Operators (Optional)

### Common Operators

```java
Flux<Integer> flux = Flux.range(1, 10);

// Transformation
flux.map(n -> n * 2);           // Transform each
flux.flatMap(n -> fetchData(n)); // Async transform

// Filtering
flux.filter(n -> n > 5);        // Keep matching
flux.take(3);                   // First 3
flux.skip(3);                   // Skip first 3
flux.distinct();                // Remove duplicates

// Aggregation
flux.count();                   // Mono<Long>
flux.reduce((a, b) -> a + b);   // Mono<Integer>
flux.collectList();             // Mono<List<Integer>>

// Error Handling
flux.onErrorReturn(-1);         // Return fallback
flux.onErrorResume(e -> fallbackFlux);  // Switch to fallback
flux.retry(3);                  // Retry on error
```

---

## Building Reactive Applications (Optional)

### Example: Reactive Service

```java
public class ReactiveUserService {

    public Mono<User> findById(Long id) {
        return Mono.justOrEmpty(users.get(id))
            .switchIfEmpty(Mono.error(
                new UserNotFoundException("User not found: " + id)));
    }

    public Flux<User> findAll() {
        return Flux.fromIterable(users.values());
    }

    public Flux<User> findByAgeGreaterThan(int age) {
        return findAll()
            .filter(user -> user.getAge() > age);
    }
}

// Usage
service.findByAgeGreaterThan(25)
    .map(User::getName)
    .subscribe(name -> System.out.println("Name: " + name));
```

### Example: Error Handling

```java
public Mono<Data> fetchWithResilience(String url) {
    return webClient.get(url)
        .timeout(Duration.ofSeconds(5))    // Timeout
        .retry(3)                           // Retry 3 times
        .onErrorResume(e -> fallback());   // Fallback on failure
}
```

---

## Next Topic

Continue to [JDBC](../../11-jdbc/README.md) to learn about database connectivity in Java.

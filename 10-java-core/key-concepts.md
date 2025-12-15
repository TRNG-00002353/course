# Core Java Key Concepts for Application Developers

## Overview

This document outlines the essential Java concepts every application developer must understand. Java is one of the most widely used programming languages for enterprise applications, providing a robust, object-oriented platform for building scalable software.

---

## 1. Java Fundamentals

### Why It Matters
- Platform-independent (Write Once, Run Anywhere)
- Industry-standard for enterprise development
- Strong typing prevents many bugs

### Key Concepts

| Component | Description | Purpose |
|-----------|-------------|---------|
| JDK | Java Development Kit | Compile & develop |
| JRE | Java Runtime Environment | Run applications |
| JVM | Java Virtual Machine | Execute bytecode |
| Bytecode | Compiled Java code | Platform-independent |

### Java Architecture
```
Source Code (.java) → Compiler (javac) → Bytecode (.class) → JVM → Machine Code
```

### Basic Structure
```java
package com.example;  // Package declaration

import java.util.List;  // Import statements

public class HelloWorld {  // Class declaration

    // Main method - entry point
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

### Primitive Data Types

| Type | Size | Range | Default |
|------|------|-------|---------|
| `byte` | 8-bit | -128 to 127 | 0 |
| `short` | 16-bit | -32,768 to 32,767 | 0 |
| `int` | 32-bit | -2.1B to 2.1B | 0 |
| `long` | 64-bit | -9.2×10¹⁸ to 9.2×10¹⁸ | 0L |
| `float` | 32-bit | ~7 decimal digits | 0.0f |
| `double` | 64-bit | ~15 decimal digits | 0.0d |
| `char` | 16-bit | Unicode characters | '\u0000' |
| `boolean` | 1-bit | true/false | false |

```java
// Primitive declarations
int count = 10;
double price = 19.99;
char grade = 'A';
boolean isActive = true;
long bigNumber = 9876543210L;
float decimal = 3.14f;

// Wrapper classes (objects)
Integer countObj = Integer.valueOf(10);
Double priceObj = Double.valueOf(19.99);

// Autoboxing/Unboxing
Integer auto = 10;       // Autoboxing
int primitive = auto;    // Unboxing
```

---

## 2. Object-Oriented Programming

### Why It Matters
- Models real-world concepts
- Promotes code reuse
- Enables maintainable design

### Four Pillars of OOP

| Pillar | Description | Keyword/Mechanism |
|--------|-------------|-------------------|
| Encapsulation | Hide internal state | `private`, getters/setters |
| Inheritance | Reuse through hierarchy | `extends` |
| Polymorphism | Many forms | Overloading, Overriding |
| Abstraction | Hide complexity | `abstract`, `interface` |

### Encapsulation
```java
public class BankAccount {
    // Private fields - encapsulated
    private String accountNumber;
    private double balance;

    // Constructor
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // Getters and setters - controlled access
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    // Business logic with validation
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}
```

### Inheritance
```java
// Parent class (superclass)
public class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println(name + " is eating");
    }

    public void sleep() {
        System.out.println(name + " is sleeping");
    }
}

// Child class (subclass)
public class Dog extends Animal {
    private String breed;

    public Dog(String name, String breed) {
        super(name);  // Call parent constructor
        this.breed = breed;
    }

    // Override parent method
    @Override
    public void eat() {
        System.out.println(name + " the dog is eating dog food");
    }

    // Additional method
    public void bark() {
        System.out.println(name + " says: Woof!");
    }
}
```

### Polymorphism
```java
// Method Overloading (compile-time polymorphism)
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }
}

// Method Overriding (runtime polymorphism)
Animal animal = new Dog("Rex", "German Shepherd");
animal.eat();  // Calls Dog's eat() method

// Polymorphic collections
List<Animal> animals = new ArrayList<>();
animals.add(new Dog("Rex", "German Shepherd"));
animals.add(new Cat("Whiskers"));

for (Animal a : animals) {
    a.eat();  // Each calls its own implementation
}
```

### Abstraction
```java
// Abstract class
public abstract class Shape {
    protected String color;

    public Shape(String color) {
        this.color = color;
    }

    // Abstract method - must be implemented
    public abstract double calculateArea();

    // Concrete method
    public void displayColor() {
        System.out.println("Color: " + color);
    }
}

public class Circle extends Shape {
    private double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

// Interface
public interface Drawable {
    void draw();

    // Default method (Java 8+)
    default void print() {
        System.out.println("Printing...");
    }

    // Static method
    static void staticMethod() {
        System.out.println("Static method in interface");
    }
}

public class Circle extends Shape implements Drawable {
    @Override
    public void draw() {
        System.out.println("Drawing circle");
    }
}
```

---

## 3. Access Modifiers

### Why It Matters
- Control visibility
- Enforce encapsulation
- API design

### Access Levels

| Modifier | Class | Package | Subclass | World |
|----------|-------|---------|----------|-------|
| `public` | ✓ | ✓ | ✓ | ✓ |
| `protected` | ✓ | ✓ | ✓ | ✗ |
| (default) | ✓ | ✓ | ✗ | ✗ |
| `private` | ✓ | ✗ | ✗ | ✗ |

### Non-Access Modifiers

| Modifier | Usage |
|----------|-------|
| `static` | Belongs to class, not instance |
| `final` | Cannot be changed/overridden |
| `abstract` | Must be implemented |
| `synchronized` | Thread-safe method |
| `volatile` | Thread-safe variable |
| `transient` | Skip serialization |

---

## 4. Exception Handling

### Why It Matters
- Graceful error handling
- Program stability
- Debugging information

### Exception Hierarchy
```
Throwable
├── Error (unchecked - system errors)
│   ├── OutOfMemoryError
│   └── StackOverflowError
└── Exception
    ├── RuntimeException (unchecked)
    │   ├── NullPointerException
    │   ├── IndexOutOfBoundsException
    │   └── IllegalArgumentException
    └── Checked Exceptions
        ├── IOException
        ├── SQLException
        └── FileNotFoundException
```

### Try-Catch-Finally
```java
public void readFile(String path) {
    FileReader reader = null;
    try {
        reader = new FileReader(path);
        // Read file
    } catch (FileNotFoundException e) {
        System.err.println("File not found: " + e.getMessage());
    } catch (IOException e) {
        System.err.println("IO error: " + e.getMessage());
    } finally {
        // Always executed
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                // Handle close error
            }
        }
    }
}

// Multi-catch (Java 7+)
try {
    // risky code
} catch (FileNotFoundException | IllegalArgumentException e) {
    // Handle both exceptions
}
```

### Try-with-Resources
```java
// Automatic resource management (Java 7+)
public void readFile(String path) {
    try (FileReader reader = new FileReader(path);
         BufferedReader br = new BufferedReader(reader)) {

        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

    } catch (IOException e) {
        System.err.println("Error: " + e.getMessage());
    }
    // Resources automatically closed
}
```

### Custom Exceptions
```java
// Checked exception
public class InsufficientFundsException extends Exception {
    private double amount;

    public InsufficientFundsException(double amount) {
        super("Insufficient funds. Needed: " + amount);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

// Unchecked exception
public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
}

// Usage
public void withdraw(double amount) throws InsufficientFundsException {
    if (amount > balance) {
        throw new InsufficientFundsException(amount - balance);
    }
    balance -= amount;
}
```

---

## 5. Collections Framework

### Why It Matters
- Store and manipulate data
- Built-in data structures
- Essential for all applications

### Collection Hierarchy
```
Iterable
└── Collection
    ├── List (ordered, duplicates allowed)
    │   ├── ArrayList
    │   ├── LinkedList
    │   └── Vector
    ├── Set (no duplicates)
    │   ├── HashSet
    │   ├── LinkedHashSet
    │   └── TreeSet
    └── Queue
        ├── PriorityQueue
        └── Deque
            └── ArrayDeque

Map (key-value pairs)
├── HashMap
├── LinkedHashMap
├── TreeMap
└── Hashtable
```

### List Operations
```java
// ArrayList - fast random access
List<String> list = new ArrayList<>();
list.add("Apple");
list.add("Banana");
list.add(1, "Orange");      // Insert at index
list.get(0);                // Get by index
list.set(0, "Mango");       // Update
list.remove("Banana");      // Remove by object
list.remove(0);             // Remove by index
list.contains("Mango");     // Check existence
list.size();                // Size

// LinkedList - fast insertion/deletion
List<String> linked = new LinkedList<>();
((LinkedList<String>) linked).addFirst("First");
((LinkedList<String>) linked).addLast("Last");

// Iterate
for (String item : list) {
    System.out.println(item);
}

list.forEach(System.out::println);  // Lambda
```

### Set Operations
```java
// HashSet - no order, no duplicates
Set<String> set = new HashSet<>();
set.add("Apple");
set.add("Apple");  // Ignored - duplicate
set.contains("Apple");
set.remove("Apple");

// LinkedHashSet - maintains insertion order
Set<String> linkedSet = new LinkedHashSet<>();

// TreeSet - sorted order
Set<String> treeSet = new TreeSet<>();
treeSet.add("Banana");
treeSet.add("Apple");
treeSet.add("Cherry");
// Iteration: Apple, Banana, Cherry
```

### Map Operations
```java
// HashMap - key-value pairs
Map<String, Integer> map = new HashMap<>();
map.put("Apple", 100);
map.put("Banana", 50);
map.get("Apple");           // 100
map.getOrDefault("Cherry", 0);  // Default if not found
map.containsKey("Apple");   // true
map.containsValue(100);     // true
map.remove("Apple");

// Iterate
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

map.forEach((key, value) -> System.out.println(key + ": " + value));

// Compute methods
map.computeIfAbsent("Cherry", k -> 75);
map.computeIfPresent("Banana", (k, v) -> v + 10);
map.merge("Apple", 10, Integer::sum);
```

### Queue Operations
```java
// Queue - FIFO
Queue<String> queue = new LinkedList<>();
queue.offer("First");   // Add to end
queue.offer("Second");
queue.peek();           // View front (null if empty)
queue.poll();           // Remove front (null if empty)

// Deque - double-ended queue
Deque<String> deque = new ArrayDeque<>();
deque.offerFirst("First");
deque.offerLast("Last");
deque.pollFirst();
deque.pollLast();

// PriorityQueue - sorted order
Queue<Integer> pq = new PriorityQueue<>();
pq.offer(3);
pq.offer(1);
pq.offer(2);
pq.poll();  // 1 (smallest first)
```

---

## 6. Stream API

### Why It Matters
- Functional programming in Java
- Declarative data processing
- Cleaner, more readable code

### Stream Operations
```java
List<String> names = Arrays.asList("John", "Jane", "Bob", "Alice");

// Filter
List<String> filtered = names.stream()
    .filter(name -> name.startsWith("J"))
    .collect(Collectors.toList());

// Map (transform)
List<Integer> lengths = names.stream()
    .map(String::length)
    .collect(Collectors.toList());

// Sort
List<String> sorted = names.stream()
    .sorted()
    .collect(Collectors.toList());

// Find
Optional<String> first = names.stream()
    .filter(name -> name.length() > 3)
    .findFirst();

// Match
boolean anyMatch = names.stream().anyMatch(n -> n.startsWith("J"));
boolean allMatch = names.stream().allMatch(n -> n.length() > 2);
boolean noneMatch = names.stream().noneMatch(n -> n.isEmpty());

// Reduce
int totalLength = names.stream()
    .mapToInt(String::length)
    .sum();

String concatenated = names.stream()
    .reduce("", (a, b) -> a + b);
```

### Collectors
```java
List<Person> people = getPeople();

// To List/Set
List<String> nameList = people.stream()
    .map(Person::getName)
    .collect(Collectors.toList());

Set<String> nameSet = people.stream()
    .map(Person::getName)
    .collect(Collectors.toSet());

// To Map
Map<Integer, Person> byId = people.stream()
    .collect(Collectors.toMap(Person::getId, p -> p));

// Grouping
Map<String, List<Person>> byCity = people.stream()
    .collect(Collectors.groupingBy(Person::getCity));

// Partitioning
Map<Boolean, List<Person>> partition = people.stream()
    .collect(Collectors.partitioningBy(p -> p.getAge() > 18));

// Joining strings
String joined = people.stream()
    .map(Person::getName)
    .collect(Collectors.joining(", "));

// Statistics
IntSummaryStatistics stats = people.stream()
    .mapToInt(Person::getAge)
    .summaryStatistics();
// stats.getAverage(), stats.getMax(), stats.getMin(), stats.getSum()
```

---

## 7. Functional Programming

### Why It Matters
- Cleaner code with lambdas
- Built-in functional interfaces
- Method references

### Lambda Expressions
```java
// Without lambda
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Running");
    }
};

// With lambda
Runnable r2 = () -> System.out.println("Running");

// Lambda syntax
(parameters) -> expression
(parameters) -> { statements; }

// Examples
(a, b) -> a + b
x -> x * 2
() -> System.out.println("Hello")
(String s) -> s.length()
```

### Functional Interfaces
```java
// Predicate - takes T, returns boolean
Predicate<String> isEmpty = s -> s.isEmpty();
Predicate<Integer> isPositive = n -> n > 0;
boolean result = isEmpty.test("");  // true

// Function - takes T, returns R
Function<String, Integer> length = String::length;
Integer len = length.apply("Hello");  // 5

// Consumer - takes T, returns void
Consumer<String> print = System.out::println;
print.accept("Hello");

// Supplier - takes nothing, returns T
Supplier<Double> random = Math::random;
Double value = random.get();

// BiFunction - takes T and U, returns R
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
Integer sum = add.apply(5, 3);  // 8

// Comparator
Comparator<String> byLength = (s1, s2) -> s1.length() - s2.length();
// Or
Comparator<String> byLength2 = Comparator.comparingInt(String::length);
```

### Method References
```java
// Static method reference
Function<String, Integer> parseInt = Integer::parseInt;

// Instance method reference
String str = "Hello";
Supplier<Integer> length = str::length;

// Instance method of arbitrary object
Function<String, String> toUpper = String::toUpperCase;

// Constructor reference
Supplier<ArrayList<String>> listSupplier = ArrayList::new;
Function<Integer, int[]> arrayCreator = int[]::new;
```

### Optional
```java
// Creating Optional
Optional<String> opt1 = Optional.of("Hello");       // Must not be null
Optional<String> opt2 = Optional.ofNullable(null);  // Can be null
Optional<String> opt3 = Optional.empty();           // Empty optional

// Using Optional
Optional<String> opt = findUserById(1);

// Check and get
if (opt.isPresent()) {
    String value = opt.get();
}

// Safer alternatives
String value = opt.orElse("default");
String value2 = opt.orElseGet(() -> computeDefault());
String value3 = opt.orElseThrow(() -> new RuntimeException("Not found"));

// Transform
Optional<Integer> length = opt.map(String::length);

// Filter
Optional<String> longString = opt.filter(s -> s.length() > 5);

// Chain operations
String result = findUser(1)
    .flatMap(User::getAddress)
    .map(Address::getCity)
    .orElse("Unknown");
```

---

## 8. Multithreading

### Why It Matters
- Concurrent execution
- Better performance
- Responsive applications

### Creating Threads
```java
// Extending Thread
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread running: " + getName());
    }
}

MyThread thread = new MyThread();
thread.start();

// Implementing Runnable
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable running");
    }
}

Thread thread2 = new Thread(new MyRunnable());
thread2.start();

// Lambda
Thread thread3 = new Thread(() -> {
    System.out.println("Lambda thread running");
});
thread3.start();
```

### Thread Lifecycle
```
NEW → RUNNABLE → RUNNING → BLOCKED/WAITING → TERMINATED
```

### Synchronization
```java
public class Counter {
    private int count = 0;

    // Synchronized method
    public synchronized void increment() {
        count++;
    }

    // Synchronized block
    public void increment2() {
        synchronized (this) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}

// Using locks
private final Lock lock = new ReentrantLock();

public void increment() {
    lock.lock();
    try {
        count++;
    } finally {
        lock.unlock();
    }
}
```

### ExecutorService
```java
// Fixed thread pool
ExecutorService executor = Executors.newFixedThreadPool(5);

// Submit tasks
executor.submit(() -> {
    System.out.println("Task executed by: " + Thread.currentThread().getName());
});

// Submit with result
Future<Integer> future = executor.submit(() -> {
    Thread.sleep(1000);
    return 42;
});

// Get result (blocks until done)
Integer result = future.get();
Integer resultWithTimeout = future.get(2, TimeUnit.SECONDS);

// Shutdown
executor.shutdown();
executor.awaitTermination(5, TimeUnit.SECONDS);
```

---

## 9. Design Patterns

### Why It Matters
- Proven solutions
- Industry best practices
- Maintainable code

### Singleton
```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}

// Enum singleton (recommended)
public enum Singleton {
    INSTANCE;

    public void doSomething() {
        // ...
    }
}
```

### Factory
```java
public interface Vehicle {
    void drive();
}

public class Car implements Vehicle {
    @Override
    public void drive() { System.out.println("Driving car"); }
}

public class Bike implements Vehicle {
    @Override
    public void drive() { System.out.println("Riding bike"); }
}

public class VehicleFactory {
    public static Vehicle createVehicle(String type) {
        return switch (type.toLowerCase()) {
            case "car" -> new Car();
            case "bike" -> new Bike();
            default -> throw new IllegalArgumentException("Unknown type");
        };
    }
}

// Usage
Vehicle vehicle = VehicleFactory.createVehicle("car");
vehicle.drive();
```

### Builder
```java
public class User {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final int age;

    private User(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.age = builder.age;
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private int age;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

// Usage
User user = new User.Builder()
    .firstName("John")
    .lastName("Doe")
    .email("john@example.com")
    .age(30)
    .build();
```

---

## Quick Reference Card

### Class Structure
```java
public class ClassName {
    // Fields
    private String field;

    // Constructor
    public ClassName(String field) {
        this.field = field;
    }

    // Methods
    public String getField() {
        return field;
    }
}
```

### Collections
```java
List<String> list = new ArrayList<>();
Set<String> set = new HashSet<>();
Map<String, Integer> map = new HashMap<>();
```

### Stream Pipeline
```java
list.stream()
    .filter(x -> condition)
    .map(x -> transform)
    .collect(Collectors.toList());
```

### Exception Pattern
```java
try {
    // risky code
} catch (Exception e) {
    // handle
} finally {
    // cleanup
}
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Write Java programs with proper structure
- [ ] Apply OOP principles (encapsulation, inheritance, polymorphism, abstraction)
- [ ] Use access modifiers correctly
- [ ] Handle exceptions appropriately
- [ ] Work with collections (List, Set, Map)
- [ ] Use Stream API for data processing
- [ ] Write functional code with lambdas
- [ ] Create and manage threads
- [ ] Apply common design patterns
- [ ] Use modern Java features (records, sealed classes)

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 11: JDBC](../11-jdbc/) - Database connectivity
- Practice building Java applications
- Explore advanced Java features

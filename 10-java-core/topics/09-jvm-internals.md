# JVM Internals and Performance

This topic covers the internal workings of the Java Virtual Machine, memory management, and performance optimization.

## JVM Architecture

The JVM is an abstract computing machine that enables Java's "write once, run anywhere" capability.

### JVM Components

```
┌─────────────────────────────────────────────────────────────────┐
│                    Java Virtual Machine                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │                  Class Loader Subsystem                     │ │
│  │  ┌──────────────┐ ┌──────────────┐ ┌──────────────────┐    │ │
│  │  │  Bootstrap   │ │  Extension   │ │   Application    │    │ │
│  │  │   Loader     │ │   Loader     │ │     Loader       │    │ │
│  │  └──────────────┘ └──────────────┘ └──────────────────┘    │ │
│  └────────────────────────────────────────────────────────────┘ │
│                              ↓                                   │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │                   Runtime Data Areas                        │ │
│  │  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────────┐   │ │
│  │  │  Method  │ │   Heap   │ │  Stack   │ │  PC Register │   │ │
│  │  │   Area   │ │          │ │(per thrd)│ │  (per thread)│   │ │
│  │  └──────────┘ └──────────┘ └──────────┘ └──────────────┘   │ │
│  └────────────────────────────────────────────────────────────┘ │
│                              ↓                                   │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │                    Execution Engine                         │ │
│  │  ┌──────────────┐ ┌──────────────┐ ┌──────────────────┐    │ │
│  │  │ Interpreter  │ │ JIT Compiler │ │ Garbage Collector│    │ │
│  │  └──────────────┘ └──────────────┘ └──────────────────┘    │ │
│  └────────────────────────────────────────────────────────────┘ │
│                                                                  │
│  ┌────────────────────────────────────────────────────────────┐ │
│  │              Native Method Interface (JNI)                  │ │
│  └────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

### Class Loader Subsystem

Responsible for loading, linking, and initializing classes.

| Loader | Responsibility |
|--------|---------------|
| **Bootstrap** | Loads core Java classes (rt.jar, java.lang, java.util) |
| **Extension** | Loads classes from jre/lib/ext directory |
| **Application** | Loads classes from classpath |

```java
// Class loading demonstration
public class ClassLoaderDemo {
    public static void main(String[] args) {
        // String class loaded by Bootstrap loader (returns null)
        System.out.println(String.class.getClassLoader());  // null

        // Custom class loaded by Application loader
        System.out.println(ClassLoaderDemo.class.getClassLoader());
        // sun.misc.Launcher$AppClassLoader
    }
}
```

### Class Loading Process

```
1. Loading     → Read .class file, create Class object
2. Linking
   ├─ Verify   → Check bytecode validity
   ├─ Prepare  → Allocate memory for static variables
   └─ Resolve  → Replace symbolic references with direct references
3. Initialize  → Execute static initializers and blocks
```

---

## JIT Compiler

The JIT (Just-In-Time) compiler converts bytecode to native machine code at runtime for better performance.

### How JIT Works

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│  Bytecode   │ ──► │ Interpreter │ ──► │  Execution  │
│  (.class)   │     │  (slow)     │     │             │
└─────────────┘     └─────────────┘     └─────────────┘
                           │
                    Profile & Monitor
                           │
                           ▼
                    ┌─────────────┐
                    │ Hot Method  │
                    │  Detected   │
                    └─────────────┘
                           │
                           ▼
                    ┌─────────────┐     ┌─────────────┐
                    │JIT Compiler │ ──► │Native Code  │
                    │             │     │  (fast)     │
                    └─────────────┘     └─────────────┘
```

**Process:**
1. Initially, bytecode is interpreted (slower but starts immediately)
2. JVM monitors which methods are called frequently ("hot spots")
3. Hot methods are compiled to native machine code
4. Compiled code runs much faster than interpreted bytecode
5. JVM can reoptimize based on runtime behavior

### JIT Benefits

| Benefit | Description |
|---------|-------------|
| **Adaptive Optimization** | Identifies hot spots and optimizes them |
| **Method Inlining** | Replaces method calls with actual code |
| **Dead Code Elimination** | Removes code that will never execute |
| **Loop Optimization** | Unrolls loops, optimizes iterations |
| **Platform-Specific Code** | Generates native code for current CPU |

### JIT Optimization Examples

#### Method Inlining

JIT replaces method calls with the actual method body to eliminate call overhead.

```java
// Original code
public int calculateTotal(int price, int quantity) {
    return multiply(price, quantity);
}

private int multiply(int a, int b) {
    return a * b;
}

// After JIT inlining (conceptually)
public int calculateTotal(int price, int quantity) {
    return price * quantity;  // Method call replaced with actual code
}
```

**Why it helps:** Each method call has overhead (stack frame creation, parameter passing). Inlining eliminates this for frequently called small methods.

#### Loop Optimization (Loop Unrolling)

JIT unrolls loops to reduce iteration overhead.

```java
// Original loop
for (int i = 0; i < 4; i++) {
    sum += array[i];
}

// After JIT loop unrolling (conceptually)
sum += array[0];
sum += array[1];
sum += array[2];
sum += array[3];
```

**Why it helps:** Reduces loop control overhead (increment, compare, jump) for small, fixed-size loops.

#### Dead Code Elimination

JIT removes code that can never be reached or has no effect.

```java
// Original code
public void process(boolean debug) {
    int result = calculate();

    if (false) {  // Dead code - never executes
        System.out.println("Debug mode");
    }

    int unused = 100;  // Dead code - never used

    return result;
}

// After JIT optimization (conceptually)
public void process(boolean debug) {
    int result = calculate();
    return result;
}
```

**Why it helps:** Removes unnecessary instructions, reducing memory usage and execution time.

#### Escape Analysis

JIT determines if objects can be allocated on the stack instead of the heap.

```java
// Original code
public int sumPoints() {
    Point p = new Point(10, 20);  // Object created
    return p.x + p.y;
}

// After JIT escape analysis (conceptually)
public int sumPoints() {
    int p_x = 10;  // Object "dissolved" into local variables
    int p_y = 20;  // Allocated on stack, not heap
    return p_x + p_y;
}
```

**Why it helps:** Stack allocation is faster than heap allocation and doesn't require garbage collection.

### Tiered Compilation

Modern JVMs use tiered compilation with multiple levels:

| Level | Compiler | Description |
|-------|----------|-------------|
| 0 | Interpreter | Initial execution, collects profiling data |
| 1 | C1 (Client) | Quick compilation, basic optimizations |
| 2 | C1 + Profiling | Compilation with profiling data collection |
| 3 | C1 + Full Profiling | More detailed profiling |
| 4 | C2 (Server) | Aggressive optimizations, best performance |

```bash
# View JIT compilation activity
java -XX:+PrintCompilation MyApp

# Disable tiered compilation (use only C2)
java -XX:-TieredCompilation MyApp
```

---

## Memory Architecture

### JVM Memory Structure

```
┌───────────────────────────────────────────────────────────────────┐
│                        JVM Memory Structure                        │
├───────────────────────────────────────────────────────────────────┤
│                                                                    │
│  ┌─────────────────────────────────────────────────────────────┐  │
│  │                         HEAP MEMORY                          │  │
│  │  (Shared among all threads - Objects and Instance Variables) │  │
│  │                                                               │  │
│  │  ┌─────────────────┐  ┌─────────────────────────────────┐   │  │
│  │  │   Young Gen     │  │         Old Generation          │   │  │
│  │  │  ┌─────────┐    │  │                                 │   │  │
│  │  │  │  Eden   │    │  │   Long-lived objects            │   │  │
│  │  │  │  Space  │    │  │                                 │   │  │
│  │  │  ├─────────┤    │  │                                 │   │  │
│  │  │  │   S0    │    │  │                                 │   │  │
│  │  │  ├─────────┤    │  │                                 │   │  │
│  │  │  │   S1    │    │  │                                 │   │  │
│  │  │  └─────────┘    │  └─────────────────────────────────┘   │  │
│  │  └─────────────────┘                                         │  │
│  └─────────────────────────────────────────────────────────────┘  │
│                                                                    │
│  ┌─────────────────────────────────────────────────────────────┐  │
│  │                        STACK MEMORY                          │  │
│  │        (Thread-private - Local Variables and References)     │  │
│  │                                                               │  │
│  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐        │  │
│  │  │   Thread 1   │  │   Thread 2   │  │   Thread 3   │        │  │
│  │  │  ┌────────┐  │  │  ┌────────┐  │  │  ┌────────┐  │        │  │
│  │  │  │ Frame  │  │  │  │ Frame  │  │  │  │ Frame  │  │        │  │
│  │  │  │ Frame  │  │  │  │ Frame  │  │  │  │ Frame  │  │        │  │
│  │  │  │ Frame  │  │  │  └────────┘  │  │  │ Frame  │  │        │  │
│  │  │  └────────┘  │  │              │  │  │ Frame  │  │        │  │
│  │  └──────────────┘  └──────────────┘  └──────────────┘        │  │
│  └─────────────────────────────────────────────────────────────┘  │
│                                                                    │
│  ┌─────────────────────────────────────────────────────────────┐  │
│  │                       METASPACE                              │  │
│  │   (Class metadata, static variables, method bytecode)        │  │
│  └─────────────────────────────────────────────────────────────┘  │
└───────────────────────────────────────────────────────────────────┘
```

### Stack Memory

The stack is thread-private memory for storing local variables and method call information.

| Property | Description |
|----------|-------------|
| **Thread-private** | Each thread has its own stack |
| **LIFO structure** | Last In, First Out (push/pop operations) |
| **Fixed size** | Size determined at thread creation |
| **Fast access** | Direct memory access, no GC overhead |
| **Stores** | Primitive values, object references, method frames |

```java
public class StackExample {
    public void methodA() {
        int x = 10;              // Primitive stored in stack
        String name = "Hello";   // Reference stored in stack
                                 // (String object in heap)
        methodB(x);              // New stack frame created
    }

    public void methodB(int param) {
        double result = param * 2.5;  // Stored in stack
        int[] arr = new int[5];       // Reference in stack, array in heap
    }
}
```

#### Stack Frame Structure

Each method call creates a stack frame containing:

```
┌─────────────────────────────┐
│   Local Variable Array      │ ← Primitives and references
├─────────────────────────────┤
│   Operand Stack             │ ← Intermediate calculations
├─────────────────────────────┤
│   Frame Data                │
│   - Return address          │
│   - Exception table ref     │
│   - Constant pool ref       │
└─────────────────────────────┘
```

#### Stack Overflow

```java
public class StackOverflowDemo {
    public static void infiniteRecursion() {
        infiniteRecursion();  // No base case!
    }

    public static void main(String[] args) {
        try {
            infiniteRecursion();
        } catch (StackOverflowError e) {
            System.out.println("Stack overflow occurred!");
        }
    }
}
```

**Causes:**
- Infinite recursion
- Very deep recursion
- Large local arrays in recursive methods

**Configuration:**
```bash
java -Xss512k MyProgram   # Set stack size to 512KB
java -Xss2m MyProgram     # Set stack size to 2MB
```

### Heap Memory

The heap is shared memory where all objects and instance variables are stored.

| Property | Description |
|----------|-------------|
| **Shared** | All threads share the same heap |
| **Dynamic size** | Can grow/shrink at runtime |
| **GC managed** | Garbage collector reclaims unused memory |
| **Stores** | Objects, instance variables, arrays |

```java
public class HeapExample {
    private String name;           // Instance variable - in heap with object
    private int[] scores;

    public HeapExample(String name) {
        this.name = name;                    // String object in heap
        this.scores = new int[]{90, 85, 88}; // Array object in heap
    }
}
```

#### Heap Regions

```
┌─────────────────────────────────────────────────────────────┐
│                        HEAP MEMORY                           │
├─────────────────────────────────────────────────────────────┤
│  ┌────────────────────────────────────────────────────────┐ │
│  │              YOUNG GENERATION (~1/3 of heap)           │ │
│  │  ┌──────────────────────────────────────────────────┐  │ │
│  │  │                    EDEN SPACE                     │  │ │
│  │  │     (New objects are allocated here first)        │  │ │
│  │  └──────────────────────────────────────────────────┘  │ │
│  │  ┌──────────────┐           ┌──────────────┐          │ │
│  │  │ Survivor S0  │           │ Survivor S1  │          │ │
│  │  │ (From Space) │           │ (To Space)   │          │ │
│  │  └──────────────┘           └──────────────┘          │ │
│  └────────────────────────────────────────────────────────┘ │
│                                                              │
│  ┌────────────────────────────────────────────────────────┐ │
│  │             OLD GENERATION (~2/3 of heap)              │ │
│  │   Objects that survived multiple GC cycles             │ │
│  │   - Long-lived objects                                  │ │
│  │   - Large objects (may go directly here)                │ │
│  └────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

#### Heap Configuration

```bash
# Set initial heap size
java -Xms256m MyProgram

# Set maximum heap size
java -Xmx1024m MyProgram

# Set both (recommended to be equal for consistent performance)
java -Xms512m -Xmx512m MyProgram

# Set young generation size
java -Xmn256m MyProgram
```

### Stack vs Heap Comparison

| Aspect | Stack | Heap |
|--------|-------|------|
| **Storage** | Primitives, references | Objects, instance variables |
| **Scope** | Thread-private | Shared among threads |
| **Size** | Fixed, smaller | Dynamic, larger |
| **Speed** | Very fast | Slower (GC overhead) |
| **Management** | Automatic (LIFO) | Garbage collected |
| **Error** | StackOverflowError | OutOfMemoryError |
| **Lifetime** | Method execution | Until no references exist |

### Memory Allocation Example

```java
public class MemoryDemo {
    private int instanceVar = 10;        // Heap (with object)
    private static int staticVar = 20;   // Metaspace

    public void process() {
        int localVar = 30;               // Stack
        String localRef = new String("Hello");  // Reference: Stack, Object: Heap
        calculate(localVar);             // New stack frame
    }

    public int calculate(int param) {    // param: Stack
        int result = param * 2;          // result: Stack
        return result;
    }
}
```

```
STACK (Thread-1)                    HEAP
┌─────────────────────┐            ┌─────────────────────────────┐
│ process()           │            │   ┌─────────────────────┐   │
│  ├─ this (ref) ────────────────► │   │   MemoryDemo object │   │
│  ├─ localVar = 30   │            │   │  instanceVar = 10   │   │
│  ├─ localRef (ref) ────────────► │   └─────────────────────┘   │
├─────────────────────┤            │   String "Hello"            │
│ calculate()         │            └─────────────────────────────┘
│  ├─ param = 30      │
│  ├─ result = 60     │            METASPACE
└─────────────────────┘            ┌─────────────────────────────┐
                                   │  staticVar = 20             │
                                   │  Class metadata             │
                                   └─────────────────────────────┘
```

---

## Method Execution on the Stack

When a method is called, a new stack frame is created. When the method returns, the frame is destroyed.

### Stack Frame Lifecycle

```java
public class MethodStackDemo {
    public static void main(String[] args) {
        int result = methodA(5);
    }

    public static int methodA(int x) {
        int y = methodB(x + 10);
        return y * 2;
    }

    public static int methodB(int a) {
        return a + 5;
    }
}
```

**Stack evolution:**

```
Step 1: main() called          Step 2: methodA() called
┌──────────────────┐           ┌──────────────────┐
│      main()      │           │    methodA()     │ ← Top
│  args, result    │           │  x=5, y=?        │
└──────────────────┘           ├──────────────────┤
                               │      main()      │
                               └──────────────────┘

Step 3: methodB() called       Step 4: After returns
┌──────────────────┐           ┌──────────────────┐
│    methodB()     │ ← Top     │      main()      │ ← Top
│  a=15            │           │  args, result=40 │
├──────────────────┤           └──────────────────┘
│    methodA()     │
├──────────────────┤
│      main()      │
└──────────────────┘
```

### Recursion and Stack Frames

```java
public static int factorial(int n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}
```

**Stack during factorial(5):**

```
┌────────────────────────┐
│ factorial(1) → returns 1│
├────────────────────────┤
│ factorial(2) → 2 * 1 = 2│
├────────────────────────┤
│ factorial(3) → 3 * 2 = 6│
├────────────────────────┤
│ factorial(4) → 4 * 6 = 24│
├────────────────────────┤
│ factorial(5) → 5 * 24 = 120│
├────────────────────────┤
│ main()                  │
└────────────────────────┘
```

---

## Garbage Collection

Garbage Collection (GC) automatically reclaims memory from objects that are no longer reachable.

### Object Reachability

An object is eligible for GC when it's no longer reachable from any live thread.

```java
public class GCDemo {
    public static void main(String[] args) {
        // Object created - reachable via 'obj'
        MyObject obj = new MyObject();

        // Object now unreachable - eligible for GC
        obj = null;

        // Request GC (not guaranteed to run immediately)
        System.gc();
    }
}
```

### Generational Garbage Collection

Most JVMs use generational GC based on the observation that most objects die young.

```
┌─────────────────────────────────────────────────────────────┐
│                    Object Lifecycle                          │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  1. Object created in Eden                                   │
│     ↓                                                        │
│  2. Minor GC: Survivors move to S0                          │
│     ↓                                                        │
│  3. Next Minor GC: Survivors move to S1                     │
│     ↓                                                        │
│  4. After several GCs: Promoted to Old Generation           │
│     ↓                                                        │
│  5. Major GC: Clean Old Generation                          │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

### GC Types

| GC Type | Scope | Trigger | Performance Impact |
|---------|-------|---------|-------------------|
| **Minor GC** | Young Generation | Eden full | Fast, frequent |
| **Major GC** | Old Generation | Old Gen full | Slower |
| **Full GC** | Entire heap | Critical situation | Stop-the-world, slowest |

### GC Algorithms

| Algorithm | Flag | Best For |
|-----------|------|----------|
| **Serial GC** | `-XX:+UseSerialGC` | Single-threaded, small heaps |
| **Parallel GC** | `-XX:+UseParallelGC` | Throughput-focused, batch processing |
| **G1 GC** | `-XX:+UseG1GC` | Large heaps, balanced latency (default Java 9+) |
| **ZGC** | `-XX:+UseZGC` | Ultra-low latency, very large heaps |
| **Shenandoah** | `-XX:+UseShenandoahGC` | Low latency, concurrent |

### G1 Garbage Collector

G1 (Garbage First) is the default collector since Java 9.

```
┌─────────────────────────────────────────────────────────────┐
│                      G1 Heap Layout                          │
├─────────────────────────────────────────────────────────────┤
│  ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐   │
│  │ E  │ │ E  │ │ S  │ │ O  │ │ O  │ │ O  │ │ H  │ │Free│   │
│  └────┘ └────┘ └────┘ └────┘ └────┘ └────┘ └────┘ └────┘   │
│  ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐   │
│  │ O  │ │Free│ │ E  │ │ S  │ │ O  │ │Free│ │ O  │ │ E  │   │
│  └────┘ └────┘ └────┘ └────┘ └────┘ └────┘ └────┘ └────┘   │
│                                                              │
│  E = Eden    S = Survivor    O = Old    H = Humongous       │
└─────────────────────────────────────────────────────────────┘
```

**Features:**
- Divides heap into equal-sized regions
- Prioritizes collecting regions with most garbage
- Concurrent marking
- Predictable pause times

### Reference Types

Java provides different reference types for advanced memory management.

```java
import java.lang.ref.*;

public class ReferenceDemo {
    public static void main(String[] args) {
        Object obj = new Object();

        // Strong Reference (default) - prevents GC
        Object strong = obj;

        // Weak Reference - collected at next GC
        WeakReference<Object> weak = new WeakReference<>(obj);

        // Soft Reference - collected when memory is low
        SoftReference<Object> soft = new SoftReference<>(obj);

        // Phantom Reference - for cleanup actions
        ReferenceQueue<Object> queue = new ReferenceQueue<>();
        PhantomReference<Object> phantom = new PhantomReference<>(obj, queue);

        obj = null;  // Remove strong reference

        // Accessing weak reference
        Object retrieved = weak.get();  // May be null if GC occurred
    }
}
```

| Reference Type | GC Behavior | Use Case |
|---------------|-------------|----------|
| **Strong** | Never collected while reachable | Normal references |
| **Soft** | Collected when memory is low | Caches |
| **Weak** | Collected at next GC | Canonical mappings |
| **Phantom** | Already collected, for finalization | Resource cleanup |

### Memory Leaks

Even with GC, memory leaks can occur when objects are unintentionally retained.

```java
// Common memory leak patterns

// 1. Static collections that grow unbounded
public class LeakExample {
    private static List<Object> cache = new ArrayList<>();  // Grows forever!

    public void addToCache(Object obj) {
        cache.add(obj);  // Never removed
    }
}

// 2. Unclosed resources
public void readFile() {
    InputStream is = new FileInputStream("file.txt");
    // If exception occurs, stream is never closed
    is.read();
}

// Fix: Use try-with-resources
public void readFileSafe() {
    try (InputStream is = new FileInputStream("file.txt")) {
        is.read();
    }  // Automatically closed
}

// 3. Inner class holding outer class reference
public class Outer {
    private byte[] largeData = new byte[1000000];

    public Runnable getTask() {
        return new Runnable() {  // Holds reference to Outer
            public void run() { }
        };
    }
}
```

### GC Tuning

```bash
# Set heap size
java -Xms512m -Xmx2g MyApp

# Choose GC algorithm
java -XX:+UseG1GC MyApp

# Set GC pause time goal (G1)
java -XX:MaxGCPauseMillis=200 MyApp

# Enable GC logging
java -Xlog:gc*:file=gc.log MyApp

# Print GC details
java -XX:+PrintGCDetails -XX:+PrintGCDateStamps MyApp
```

---

## Performance Monitoring

### JVM Flags for Monitoring

```bash
# Print JIT compilation
java -XX:+PrintCompilation MyApp

# Print GC activity
java -verbose:gc MyApp

# Detailed GC logging
java -Xlog:gc*=debug:file=gc.log MyApp

# Print class loading
java -verbose:class MyApp

# Heap dump on OutOfMemoryError
java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/dump.hprof MyApp
```

### Common JVM Tools

| Tool | Purpose |
|------|---------|
| **jps** | List running Java processes |
| **jstat** | Monitor JVM statistics |
| **jmap** | Memory map, heap dump |
| **jstack** | Thread dump |
| **jconsole** | GUI monitoring |
| **jvisualvm** | Advanced profiling |

```bash
# List Java processes
jps -l

# Monitor GC activity
jstat -gc <pid> 1000

# Generate heap dump
jmap -dump:format=b,file=heap.hprof <pid>

# Get thread dump
jstack <pid>
```

---

## Summary

| Concept | Key Points |
|---------|------------|
| JVM Architecture | Class Loader → Runtime Data Areas → Execution Engine |
| JIT Compiler | Converts hot bytecode to native code; inlining, loop optimization |
| Stack Memory | Thread-private, LIFO, stores primitives and references |
| Heap Memory | Shared, stores objects, managed by GC |
| Metaspace | Class metadata, static variables, method bytecode |
| Garbage Collection | Automatic memory reclamation; generational approach |
| GC Algorithms | Serial, Parallel, G1, ZGC - choose based on requirements |
| Reference Types | Strong, Soft, Weak, Phantom - for advanced memory control |

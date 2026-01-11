# Functional Programming

## Table of Contents

### Required Topics
- [Lambdas](#lambdas)
- [Method References](#method-references)
- [Functional Interfaces](#functional-interfaces)
- [Optional Class](#optional-class)
- [Summary](#summary)

### Optional / Additional Reference
- [Functional Programming Patterns](#functional-programming-patterns-optional)

---

## Lambdas

Lambda expressions provide a concise way to represent anonymous functions. They enable functional programming in Java.

### What is a Lambda?

A lambda expression is essentially a block of code that can be passed around and executed later. It replaces verbose anonymous inner class syntax.

```java
// Before lambdas (anonymous class)
Runnable runnable1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Running");
    }
};

// With lambda (concise)
Runnable runnable2 = () -> System.out.println("Running");
```

### Lambda Syntax

```java
// No parameters
() -> System.out.println("Hello")
() -> { return 42; }
() -> 42  // Implicit return

// One parameter (parentheses optional)
x -> x * x
(x) -> x * x
x -> { return x * x; }

// Multiple parameters
(a, b) -> a + b
(a, b) -> { return a + b; }
(String s, int i) -> s.substring(i)  // With explicit types

// Multiple statements (requires braces and return)
(a, b) -> {
    int sum = a + b;
    System.out.println("Sum: " + sum);
    return sum;
}
```

### Lambda Examples

```java
// Runnable (no parameters, no return)
Runnable task = () -> System.out.println("Task running");
task.run();

// Comparator (two parameters, returns int)
Comparator<String> comparator = (s1, s2) -> s1.compareTo(s2);
Comparator<String> lengthComparator = (s1, s2) -> s1.length() - s2.length();

// Sorting with lambda
List<String> names = Arrays.asList("Charlie", "Alice", "Bob");
names.sort((s1, s2) -> s1.compareTo(s2));

// Collections.sort
Collections.sort(names, (s1, s2) -> s1.length() - s2.length());

// Event handling
button.addActionListener(e -> System.out.println("Button clicked"));
```

### Type Inference

The compiler infers types from context:

```java
// Type inferred from variable declaration
Predicate<String> isEmpty = str -> str.isEmpty();

// Explicitly typed (optional)
Predicate<String> isEmptyExplicit = (String str) -> str.isEmpty();

// Type inference with generics
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.sort((s1, s2) -> s1.compareTo(s2));  // Types inferred as String

// Type inference in streams
names.stream()
    .filter(s -> s.length() > 3)  // s is inferred as String
    .map(s -> s.toUpperCase())    // s is inferred as String
    .forEach(s -> System.out.println(s));
```

### Capturing Variables

Lambdas can access variables from the enclosing scope, but they must be **effectively final**.

```java
public void example() {
    int factor = 2;  // Effectively final (never modified)

    // Lambda captures 'factor' from enclosing scope
    Function<Integer, Integer> multiply = num -> num * factor;

    System.out.println(multiply.apply(5));  // 10

    // factor = 3;  // ERROR: Would make 'factor' not effectively final
}

// Instance variables can be accessed and modified
class Counter {
    private int count = 0;

    public void increment() {
        Runnable incrementer = () -> count++;  // OK - instance variable
        incrementer.run();
        System.out.println(count);  // 1
    }
}
```

### Lambdas vs Anonymous Classes

| Aspect | Lambda | Anonymous Class |
|--------|--------|-----------------|
| Syntax | Concise | Verbose |
| `this` keyword | Refers to enclosing class | Refers to anonymous class instance |
| State | Cannot have instance variables | Can have instance variables |
| Multiple methods | Only one (SAM) | Can implement multiple methods |

```java
// Anonymous class - 'this' refers to the Runnable instance
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println(this.getClass());  // Anonymous class
    }
};

// Lambda - 'this' refers to the enclosing class
Runnable r2 = () -> {
    System.out.println(this.getClass());  // Enclosing class
};
```

---

## Method References

Method references are shorthand for lambdas that call a single method. They make code more readable.

### Four Types of Method References

| Type | Syntax | Lambda Equivalent |
|------|--------|-------------------|
| Static method | `Class::staticMethod` | `x -> Class.staticMethod(x)` |
| Instance method on object | `obj::instanceMethod` | `x -> obj.instanceMethod(x)` |
| Instance method on parameter | `Class::instanceMethod` | `x -> x.instanceMethod()` |
| Constructor | `Class::new` | `x -> new Class(x)` |

### 1. Static Method Reference

```java
// Lambda
Function<String, Integer> parseInt1 = str -> Integer.parseInt(str);

// Method reference
Function<String, Integer> parseInt2 = Integer::parseInt;

// Usage
int num = parseInt2.apply("123");  // 123

// More examples
BiFunction<Integer, Integer, Integer> max = Math::max;
System.out.println(max.apply(5, 3));  // 5

// In streams
List<String> numbers = Arrays.asList("1", "2", "3");
List<Integer> ints = numbers.stream()
    .map(Integer::parseInt)
    .collect(Collectors.toList());
```

### 2. Instance Method Reference on Specific Object

```java
String prefix = "Hello ";

// Lambda
Function<String, String> addPrefix1 = str -> prefix.concat(str);

// Method reference
Function<String, String> addPrefix2 = prefix::concat;

// Usage
String result = addPrefix2.apply("World");  // "Hello World"

// Common example: System.out::println
Consumer<String> print = System.out::println;
print.accept("Message");  // Prints: Message

List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.forEach(System.out::println);  // Print each name
```

### 3. Instance Method Reference on Arbitrary Object

The method is called on the first parameter:

```java
// Lambda
Function<String, String> toUpper1 = str -> str.toUpperCase();

// Method reference
Function<String, String> toUpper2 = String::toUpperCase;

// Usage
String upper = toUpper2.apply("hello");  // "HELLO"

// For Comparator (two parameters)
// Lambda: (s1, s2) -> s1.compareTo(s2)
// Method reference: s1.compareTo(s2) where s1 is first param
Comparator<String> comp = String::compareTo;

List<String> names = Arrays.asList("Charlie", "Alice", "Bob");
names.sort(String::compareToIgnoreCase);  // Sort ignoring case

// Check empty strings
Predicate<String> isEmpty = String::isEmpty;
List<String> nonEmpty = list.stream()
    .filter(isEmpty.negate())
    .collect(Collectors.toList());
```

### 4. Constructor Reference

```java
// Lambda
Supplier<ArrayList<String>> listSupplier1 = () -> new ArrayList<>();

// Constructor reference
Supplier<ArrayList<String>> listSupplier2 = ArrayList::new;

// Usage
List<String> list = listSupplier2.get();

// With parameters
Function<Integer, ArrayList<String>> listCreator = ArrayList::new;
List<String> listWithCapacity = listCreator.apply(100);

// Person class example
class Person {
    private String name;
    private int age;

    public Person() {}
    public Person(String name) { this.name = name; }
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

// No-arg constructor
Supplier<Person> personSupplier = Person::new;
Person p1 = personSupplier.get();

// One-arg constructor
Function<String, Person> personByName = Person::new;
Person p2 = personByName.apply("Alice");

// Two-arg constructor
BiFunction<String, Integer, Person> personFactory = Person::new;
Person p3 = personFactory.apply("Bob", 30);

// Array constructor
Function<Integer, String[]> arrayCreator = String[]::new;
String[] arr = arrayCreator.apply(10);  // Array of size 10
```

### Method Reference Examples in Streams

```java
List<String> names = Arrays.asList("Charlie", "Alice", "Bob");

// Sorting
names.sort(String::compareTo);

// Mapping
List<String> upperNames = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());

// Printing
names.forEach(System.out::println);

// Parsing
List<String> numberStrings = Arrays.asList("1", "2", "3");
List<Integer> numbers = numberStrings.stream()
    .map(Integer::parseInt)
    .collect(Collectors.toList());

// Removing elements
List<String> items = new ArrayList<>(Arrays.asList("a", "", "b", "", "c"));
items.removeIf(String::isEmpty);  // Remove empty strings
```

---

## Functional Interfaces

A functional interface has exactly **one abstract method** (SAM - Single Abstract Method). Lambdas can be used wherever a functional interface is expected.

### @FunctionalInterface Annotation

```java
@FunctionalInterface
public interface Calculator {
    int calculate(int a, int b);  // Single abstract method

    // Default methods allowed
    default void log() {
        System.out.println("Calculating...");
    }

    // Static methods allowed
    static void info() {
        System.out.println("Calculator interface");
    }
}

// Usage with lambda
Calculator add = (a, b) -> a + b;
Calculator multiply = (a, b) -> a * b;

System.out.println(add.calculate(5, 3));       // 8
System.out.println(multiply.calculate(5, 3));  // 15
```

### Built-in Functional Interfaces (java.util.function)

Java provides common functional interfaces in the `java.util.function` package.

#### Predicate<T> - Test Condition

```java
import java.util.function.Predicate;

// Predicate<T>: T -> boolean
Predicate<Integer> isEven = num -> num % 2 == 0;
Predicate<String> isEmpty = str -> str.isEmpty();
Predicate<Integer> isPositive = num -> num > 0;

// Test
System.out.println(isEven.test(4));      // true
System.out.println(isEmpty.test(""));    // true
System.out.println(isPositive.test(10)); // true

// Combining predicates
Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
Predicate<Integer> isEvenOrPositive = isEven.or(isPositive);
Predicate<Integer> isOdd = isEven.negate();

// Use in streams
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
List<Integer> evenNumbers = numbers.stream()
    .filter(isEven)
    .collect(Collectors.toList());  // [2, 4, 6, 8, 10]
```

#### Function<T, R> - Transform Input to Output

```java
import java.util.function.Function;

// Function<T, R>: T -> R
Function<String, Integer> stringLength = str -> str.length();
Function<Integer, Integer> square = num -> num * num;
Function<String, String> toUpperCase = str -> str.toUpperCase();

// Apply
System.out.println(stringLength.apply("Hello"));  // 5
System.out.println(square.apply(5));              // 25
System.out.println(toUpperCase.apply("hello"));   // HELLO

// Chaining functions
Function<String, Integer> lengthSquared = stringLength.andThen(square);
System.out.println(lengthSquared.apply("Hello"));  // 25 (5^2)

// Compose (reverse order)
Function<Integer, Integer> addOne = num -> num + 1;
Function<Integer, Integer> multiplyTwo = num -> num * 2;
Function<Integer, Integer> composed = multiplyTwo.compose(addOne);
System.out.println(composed.apply(5));  // 12 ((5+1)*2)
```

#### Consumer<T> - Perform Action (Side Effect)

```java
import java.util.function.Consumer;

// Consumer<T>: T -> void
Consumer<String> print = str -> System.out.println(str);
Consumer<Integer> printSquare = num -> System.out.println(num * num);

// Accept
print.accept("Hello");     // Hello
printSquare.accept(5);     // 25

// Chaining consumers
Consumer<String> log = str -> System.out.println("LOG: " + str);
Consumer<String> printAndLog = print.andThen(log);
printAndLog.accept("Message"); // Message, then LOG: Message

// Use with forEach
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.forEach(print);  // Print each name
```

#### Supplier<T> - Provide Value

```java
import java.util.function.Supplier;

// Supplier<T>: () -> T
Supplier<Double> randomValue = () -> Math.random();
Supplier<String> greeting = () -> "Hello, World!";
Supplier<LocalDate> today = () -> LocalDate.now();

// Get
System.out.println(randomValue.get());  // Random number
System.out.println(greeting.get());     // Hello, World!
System.out.println(today.get());        // Current date

// Lazy initialization
Supplier<ExpensiveObject> supplier = ExpensiveObject::new;
// Object not created yet...
ExpensiveObject obj = supplier.get();  // Created now
```

#### BiFunction<T, U, R> and BiPredicate<T, U>

For operations with two inputs:

```java
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

// BiFunction<T, U, R>: (T, U) -> R
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
BiFunction<String, String, String> concat = (a, b) -> a + b;

System.out.println(add.apply(5, 3));          // 8
System.out.println(concat.apply("Hi", "!"));  // Hi!

// BiPredicate<T, U>: (T, U) -> boolean
BiPredicate<Integer, Integer> isGreater = (a, b) -> a > b;
BiPredicate<String, Integer> isLengthEqual = (str, len) -> str.length() == len;

System.out.println(isGreater.test(5, 3));         // true
System.out.println(isLengthEqual.test("Hi", 2));  // true
```

#### UnaryOperator<T> and BinaryOperator<T>

Special cases where input and output types are the same:

```java
import java.util.function.UnaryOperator;
import java.util.function.BinaryOperator;

// UnaryOperator<T>: T -> T
UnaryOperator<Integer> square = num -> num * num;
UnaryOperator<String> toUpper = String::toUpperCase;

System.out.println(square.apply(5));        // 25
System.out.println(toUpper.apply("hello")); // HELLO

// BinaryOperator<T>: (T, T) -> T
BinaryOperator<Integer> sum = (a, b) -> a + b;
BinaryOperator<Integer> max = Integer::max;

// Used in reduce operations
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int total = numbers.stream().reduce(0, sum);  // 15
```

### Summary of Functional Interfaces

| Interface | Method | Signature | Purpose |
|-----------|--------|-----------|---------|
| `Predicate<T>` | `test(T)` | `T -> boolean` | Test condition |
| `Function<T,R>` | `apply(T)` | `T -> R` | Transform |
| `Consumer<T>` | `accept(T)` | `T -> void` | Side effect |
| `Supplier<T>` | `get()` | `() -> T` | Provide value |
| `BiPredicate<T,U>` | `test(T,U)` | `(T,U) -> boolean` | Test with 2 inputs |
| `BiFunction<T,U,R>` | `apply(T,U)` | `(T,U) -> R` | Transform 2 inputs |
| `BiConsumer<T,U>` | `accept(T,U)` | `(T,U) -> void` | Action with 2 inputs |
| `UnaryOperator<T>` | `apply(T)` | `T -> T` | Transform same type |
| `BinaryOperator<T>` | `apply(T,T)` | `(T,T) -> T` | Combine same type |

---

## Optional Class

`Optional<T>` is a container that may or may not contain a non-null value. It helps avoid `NullPointerException`.

### Creating Optional

```java
import java.util.Optional;

// Empty Optional
Optional<String> empty = Optional.empty();

// Optional with value (throws NPE if null)
Optional<String> present = Optional.of("Hello");

// Optional with potentially null value (safe)
String nullableValue = null;
Optional<String> nullable = Optional.ofNullable(nullableValue);  // Empty

String value = "Hello";
Optional<String> withValue = Optional.ofNullable(value);  // Present
```

### Checking Presence

```java
Optional<String> optional = Optional.of("Hello");

// Check if present
if (optional.isPresent()) {
    System.out.println("Value: " + optional.get());
}

// Check if empty (Java 11+)
if (optional.isEmpty()) {
    System.out.println("No value");
}
```

### Getting Values Safely

```java
Optional<String> optional = Optional.of("Hello");

// Get value (throws NoSuchElementException if empty - avoid this!)
String value = optional.get();

// Get or default value
String result1 = optional.orElse("Default");        // "Hello"
String result2 = Optional.<String>empty().orElse("Default"); // "Default"

// Get or compute default (lazy evaluation)
String result3 = optional.orElseGet(() -> computeExpensiveDefault());

// Get or throw custom exception
String result4 = optional.orElseThrow(() ->
    new IllegalStateException("Value required"));
```

### Conditional Actions

```java
Optional<String> optional = Optional.of("Hello");

// If present, do action
optional.ifPresent(value -> System.out.println("Value: " + value));

// If present or else (Java 9+)
optional.ifPresentOrElse(
    value -> System.out.println("Value: " + value),
    () -> System.out.println("No value")
);
```

### Transforming Values

```java
Optional<String> optional = Optional.of("Hello");

// map: Transform value if present
Optional<Integer> length = optional.map(String::length);  // Optional[5]
Optional<String> upper = optional.map(String::toUpperCase);  // Optional[HELLO]

// Empty optional stays empty
Optional<String> empty = Optional.empty();
Optional<Integer> emptyLength = empty.map(String::length);  // Optional.empty

// flatMap: When transformation returns Optional
class Person {
    private String email;
    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }
}

Optional<Person> person = Optional.of(new Person());

// flatMap avoids Optional<Optional<String>>
Optional<String> email = person.flatMap(Person::getEmail);
```

### Filtering Values

```java
Optional<Integer> optional = Optional.of(42);

// Filter: Keep value only if predicate is true
Optional<Integer> even = optional.filter(num -> num % 2 == 0);  // Optional[42]
Optional<Integer> odd = optional.filter(num -> num % 2 != 0);   // Optional.empty

// Chain operations
Optional<String> result = Optional.of("Hello")
    .filter(s -> s.length() > 3)
    .map(String::toUpperCase);  // Optional[HELLO]
```

### Optional Best Practices

```java
// DON'T use Optional as field
class User {
    private Optional<String> email;  // BAD
}

// DO use Optional as return type
class User {
    private String email;

    public Optional<String> getEmail() {  // GOOD
        return Optional.ofNullable(email);
    }
}

// DON'T use isPresent() + get()
if (optional.isPresent()) {  // BAD
    System.out.println(optional.get());
}

// DO use ifPresent() or orElse()
optional.ifPresent(System.out::println);  // GOOD
String value = optional.orElse("default");  // GOOD

// DON'T use Optional.of() with nullable values
Optional<String> bad = Optional.of(nullableValue);  // May throw NPE

// DO use Optional.ofNullable()
Optional<String> good = Optional.ofNullable(nullableValue);  // Safe

// Real-world example: Chain of optional operations
public String getUserEmailOrDefault(Long userId) {
    return findUserById(userId)
        .flatMap(User::getEmail)
        .orElse("no-email@example.com");
}
```

---

# Additional Reference (Optional)

> The following section is optional and provided for additional learning. Focus on the required topics above first.

---

## Functional Programming Patterns (Optional)

### Immutability

```java
// Immutable class
public final class ImmutablePerson {
    private final String name;
    private final int age;

    public ImmutablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    // Return new instance instead of modifying
    public ImmutablePerson withAge(int newAge) {
        return new ImmutablePerson(this.name, newAge);
    }
}
```

### Pure Functions

Functions with no side effects - same input always produces same output.

```java
// Pure function (no side effects)
public int add(int a, int b) {
    return a + b;
}

// Impure function (modifies external state)
private int total = 0;
public int addToTotal(int value) {
    total += value;  // Side effect!
    return total;
}
```

### Function Composition

```java
Function<Integer, Integer> multiplyBy2 = x -> x * 2;
Function<Integer, Integer> add3 = x -> x + 3;

// andThen: execute in order
Function<Integer, Integer> multiplyThenAdd = multiplyBy2.andThen(add3);
System.out.println(multiplyThenAdd.apply(5));  // 13 ((5*2)+3)

// compose: execute in reverse order
Function<Integer, Integer> addThenMultiply = multiplyBy2.compose(add3);
System.out.println(addThenMultiply.apply(5));  // 16 ((5+3)*2)
```

---

## Summary

### Required Concepts

| Concept | Key Points |
|---------|------------|
| Lambdas | `(params) -> expression` or `(params) -> { statements; }` |
| Method References | `Class::staticMethod`, `obj::method`, `Class::instanceMethod`, `Class::new` |
| Functional Interfaces | Single abstract method (SAM), `@FunctionalInterface` annotation |
| Built-in Interfaces | `Predicate<T>`, `Function<T,R>`, `Consumer<T>`, `Supplier<T>` |
| Optional | `Optional.ofNullable()`, `orElse()`, `map()`, `flatMap()`, `ifPresent()` |

### Quick Reference

```java
// Lambda
Comparator<String> comp = (s1, s2) -> s1.length() - s2.length();

// Method Reference
Consumer<String> print = System.out::println;
Function<String, Integer> parse = Integer::parseInt;
Supplier<List<String>> listFactory = ArrayList::new;

// Functional Interface
@FunctionalInterface
interface Transformer<T> {
    T transform(T input);
}
Transformer<String> upper = s -> s.toUpperCase();

// Optional
Optional<String> opt = Optional.ofNullable(value);
String result = opt.map(String::toUpperCase).orElse("DEFAULT");
```

## Next Topic

Continue to [Java APIs](./07-java-apis.md) to learn about Stream API, Date/Time API, and Reflection.

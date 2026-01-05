# Functional Programming

## Table of Contents
- [Functional Interfaces](#functional-interfaces)
- [Lambdas](#lambdas)
- [Method References](#method-references)
- [Optional Class](#optional-class)
- [Functional Programming Patterns](#functional-programming-patterns)
- [Summary](#summary)

---

## Functional Interfaces

A functional interface has exactly one abstract method (SAM - Single Abstract Method). It can have multiple default or static methods.

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

Java provides many functional interfaces out of the box.

#### Predicate<T>

Tests a condition, returns boolean.

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

System.out.println(isEvenAndPositive.test(4));  // true
System.out.println(isEvenAndPositive.test(-4)); // false
System.out.println(isOdd.test(5));              // true

// Real-world example
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
List<Integer> evenNumbers = numbers.stream()
    .filter(isEven)
    .collect(Collectors.toList());
```

#### Function<T, R>

Takes input, returns output.

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
Function<String, Integer> stringLengthSquared = stringLength.andThen(square);
System.out.println(stringLengthSquared.apply("Hello"));  // 25 (5^2)

Function<Integer, String> squareToString = square.andThen(Object::toString);
System.out.println(squareToString.apply(5));  // "25"

// Compose (reverse order)
Function<Integer, Integer> addOne = num -> num + 1;
Function<Integer, Integer> multiplyTwo = num -> num * 2;
Function<Integer, Integer> composed = multiplyTwo.compose(addOne);
System.out.println(composed.apply(5));  // 12 ((5+1)*2)

// Real-world example
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
List<Integer> nameLengths = names.stream()
    .map(stringLength)
    .collect(Collectors.toList());
```

#### Consumer<T>

Takes input, returns nothing (performs side effect).

```java
import java.util.function.Consumer;

// Consumer<T>: T -> void
Consumer<String> print = str -> System.out.println(str);
Consumer<Integer> printSquare = num -> System.out.println(num * num);
Consumer<String> log = str -> System.out.println("LOG: " + str);

// Accept
print.accept("Hello");        // Hello
printSquare.accept(5);        // 25
log.accept("Important");      // LOG: Important

// Chaining consumers
Consumer<String> printAndLog = print.andThen(log);
printAndLog.accept("Message"); // Message, then LOG: Message

// Real-world example
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.forEach(print);  // Print each name
```

#### Supplier<T>

Takes nothing, returns output.

```java
import java.util.function.Supplier;

// Supplier<T>: () -> T
Supplier<Double> randomValue = () -> Math.random();
Supplier<String> greeting = () -> "Hello, World!";
Supplier<Integer> currentTime = () -> (int) System.currentTimeMillis();

// Get
System.out.println(randomValue.get());  // Random number
System.out.println(greeting.get());     // Hello, World!
System.out.println(currentTime.get());  // Current timestamp

// Real-world example: Lazy initialization
class ExpensiveObject {
    public ExpensiveObject() {
        System.out.println("Creating expensive object");
    }
}

Supplier<ExpensiveObject> supplier = ExpensiveObject::new;
// Object not created yet

ExpensiveObject obj = supplier.get();  // Created now
```

#### BiPredicate<T, U>

Tests condition with two inputs.

```java
import java.util.function.BiPredicate;

// BiPredicate<T, U>: (T, U) -> boolean
BiPredicate<Integer, Integer> isGreater = (a, b) -> a > b;
BiPredicate<String, Integer> isLengthEqual = (str, len) -> str.length() == len;

System.out.println(isGreater.test(5, 3));         // true
System.out.println(isLengthEqual.test("Hi", 2));  // true
```

#### BiFunction<T, U, R>

Takes two inputs, returns output.

```java
import java.util.function.BiFunction;

// BiFunction<T, U, R>: (T, U) -> R
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
BiFunction<String, String, String> concat = (a, b) -> a + b;
BiFunction<Integer, Integer, Double> divide = (a, b) -> (double) a / b;

System.out.println(add.apply(5, 3));          // 8
System.out.println(concat.apply("Hi", "!"));  // Hi!
System.out.println(divide.apply(10, 3));      // 3.333...
```

#### BiConsumer<T, U>

Takes two inputs, returns nothing.

```java
import java.util.function.BiConsumer;

// BiConsumer<T, U>: (T, U) -> void
BiConsumer<String, Integer> print = (str, num) ->
    System.out.println(str + ": " + num);

print.accept("Age", 25);  // Age: 25

// Real-world example: Iterate map
Map<String, Integer> map = new HashMap<>();
map.put("Alice", 25);
map.put("Bob", 30);
map.forEach(print);  // Print each entry
```

#### UnaryOperator<T> and BinaryOperator<T>

Special cases of Function and BiFunction where all types are the same.

```java
import java.util.function.UnaryOperator;
import java.util.function.BinaryOperator;

// UnaryOperator<T>: T -> T (same input and output type)
UnaryOperator<Integer> square = num -> num * num;
UnaryOperator<String> toUpper = String::toUpperCase;

System.out.println(square.apply(5));        // 25
System.out.println(toUpper.apply("hello")); // HELLO

// BinaryOperator<T>: (T, T) -> T (all same type)
BinaryOperator<Integer> add = (a, b) -> a + b;
BinaryOperator<String> concat = (a, b) -> a + b;

System.out.println(add.apply(5, 3));          // 8
System.out.println(concat.apply("Hi", "!"));  // Hi!

// Used in reduce operations
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int sum = numbers.stream()
    .reduce(0, add);  // Sum all numbers
```

### Summary of Functional Interfaces

| Interface | Abstract Method | Purpose |
|-----------|----------------|---------|
| Predicate<T> | boolean test(T t) | Test condition |
| Function<T,R> | R apply(T t) | Transform input to output |
| Consumer<T> | void accept(T t) | Perform action (side effect) |
| Supplier<T> | T get() | Provide value |
| BiPredicate<T,U> | boolean test(T t, U u) | Test with 2 inputs |
| BiFunction<T,U,R> | R apply(T t, U u) | Transform 2 inputs |
| BiConsumer<T,U> | void accept(T t, U u) | Action with 2 inputs |
| UnaryOperator<T> | T apply(T t) | Transform same type |
| BinaryOperator<T> | T apply(T t1, T t2) | Combine same type |

---

## Lambdas

Lambda expressions provide a concise way to represent functional interface implementations.

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
(String s, int i) -> s.substring(i)  // With types

// Multiple statements
(a, b) -> {
    int sum = a + b;
    System.out.println("Sum: " + sum);
    return sum;
}
```

### Lambda Examples

```java
// Runnable
Runnable runnable = () -> System.out.println("Running");

// Comparator
Comparator<String> comparator = (s1, s2) -> s1.compareTo(s2);
Comparator<String> lengthComparator = (s1, s2) -> s1.length() - s2.length();

// ActionListener (Swing)
button.addActionListener(e -> System.out.println("Button clicked"));

// Custom functional interface
@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}

MathOperation add = (a, b) -> a + b;
MathOperation subtract = (a, b) -> a - b;
MathOperation multiply = (a, b) -> a * b;

System.out.println(add.operate(5, 3));       // 8
System.out.println(subtract.operate(5, 3));  // 2
System.out.println(multiply.operate(5, 3));  // 15
```

### Type Inference

```java
// Type inferred from context
Predicate<String> isEmpty = str -> str.isEmpty();

// Explicitly typed
Predicate<String> isEmptyExplicit = (String str) -> str.isEmpty();

// Multiple parameters with types
BiFunction<String, Integer, String> substring =
    (String s, Integer i) -> s.substring(i);

// Type inference with generics
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.sort((s1, s2) -> s1.compareTo(s2));  // Types inferred
```

### Capturing Variables

Lambdas can access variables from enclosing scope (must be effectively final).

```java
public void example() {
    int factor = 2;  // Effectively final

    // Lambda captures factor
    Function<Integer, Integer> multiply = num -> num * factor;

    System.out.println(multiply.apply(5));  // 10

    // factor = 3;  // ERROR: Cannot modify captured variable
}

// Instance variables can be accessed and modified
class Example {
    private int value = 10;

    public void method() {
        Consumer<Integer> add = num -> value += num;  // OK
        add.accept(5);
        System.out.println(value);  // 15
    }
}
```

### Lambdas vs Anonymous Classes

```java
// Anonymous class (verbose)
Runnable runnable1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Running");
    }
};

// Lambda (concise)
Runnable runnable2 = () -> System.out.println("Running");

// Comparator with anonymous class
Collections.sort(names, new Comparator<String>() {
    @Override
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
});

// Comparator with lambda
Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
```

---

## Method References

Shorthand for lambdas that call a single method.

### Types of Method References

#### 1. Static Method Reference

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
```

#### 2. Instance Method Reference on Particular Object

```java
String prefix = "Hello ";

// Lambda
Function<String, String> addPrefix1 = str -> prefix.concat(str);

// Method reference
Function<String, String> addPrefix2 = prefix::concat;

// Usage
String result = addPrefix2.apply("World");  // Hello World

// More examples
Consumer<String> print = System.out::println;
print.accept("Message");  // Message

List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.forEach(System.out::println);  // Print each
```

#### 3. Instance Method Reference on Arbitrary Object

```java
// Lambda
Function<String, String> toUpper1 = str -> str.toUpperCase();

// Method reference
Function<String, String> toUpper2 = String::toUpperCase;

// Usage
String upper = toUpper2.apply("hello");  // HELLO

// More examples
List<String> names = Arrays.asList("Charlie", "Alice", "Bob");
names.sort(String::compareToIgnoreCase);  // Sort

// Filter example
Predicate<String> isEmpty = String::isEmpty;
List<String> nonEmpty = list.stream()
    .filter(isEmpty.negate())
    .collect(Collectors.toList());
```

#### 4. Constructor Reference

```java
// Lambda
Supplier<List<String>> listSupplier1 = () -> new ArrayList<>();

// Constructor reference
Supplier<List<String>> listSupplier2 = ArrayList::new;

// Usage
List<String> list = listSupplier2.get();

// With parameters
Function<Integer, List<String>> listCreator = ArrayList::new;
List<String> listWithCapacity = listCreator.apply(100);

// More examples
class Person {
    private String name;
    private int age;

    public Person() {}

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

// No-arg constructor
Supplier<Person> personSupplier = Person::new;
Person p1 = personSupplier.get();

// One-arg constructor
Function<String, Person> personCreator = Person::new;
Person p2 = personCreator.apply("Alice");

// Two-arg constructor
BiFunction<String, Integer, Person> personFactory = Person::new;
Person p3 = personFactory.apply("Bob", 30);

// Array constructor
Function<Integer, int[]> arrayCreator = int[]::new;
int[] arr = arrayCreator.apply(10);  // Array of size 10
```

### Method Reference Examples

```java
// Sorting
List<String> names = Arrays.asList("Charlie", "Alice", "Bob");

// Lambda
names.sort((s1, s2) -> s1.compareTo(s2));

// Method reference
names.sort(String::compareTo);

// Natural order
names.sort(Comparator.naturalOrder());

// Stream operations
List<String> upperNames = names.stream()
    .map(String::toUpperCase)  // Method reference
    .collect(Collectors.toList());

// Convert to integers
List<String> numbers = Arrays.asList("1", "2", "3");
List<Integer> ints = numbers.stream()
    .map(Integer::parseInt)  // Method reference
    .collect(Collectors.toList());

// Remove elements
List<String> list = new ArrayList<>(Arrays.asList("a", "", "b", "", "c"));
list.removeIf(String::isEmpty);  // Remove empty strings
```

---

## Optional Class

Container object for potentially absent values, avoiding null checks.

### Creating Optional

```java
import java.util.Optional;

// Empty Optional
Optional<String> empty = Optional.empty();

// Optional with value
Optional<String> optional = Optional.of("Hello");

// Optional.of throws NPE if null
// Optional<String> willThrow = Optional.of(null);  // NullPointerException

// Optional with potentially null value
String nullableValue = null;
Optional<String> nullable = Optional.ofNullable(nullableValue);  // Empty

String value = "Hello";
Optional<String> present = Optional.ofNullable(value);  // Present
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

### Getting Value

```java
Optional<String> optional = Optional.of("Hello");

// Get value (throws NoSuchElementException if empty)
String value = optional.get();

// Get or default
String result1 = optional.orElse("Default");        // Hello
String result2 = Optional.empty().orElse("Default"); // Default

// Get or supplier (lazy evaluation)
String result3 = optional.orElseGet(() -> "Computed Default");

// Get or throw
String result4 = optional.orElseThrow();  // Throws NoSuchElementException
String result5 = optional.orElseThrow(() -> new CustomException("No value"));
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

// Map: Transform value if present
Optional<Integer> length = optional.map(String::length);  // Optional[5]
Optional<String> upper = optional.map(String::toUpperCase);  // Optional[HELLO]

// Map returns Optional with transformed value
Optional<String> empty = Optional.empty();
Optional<Integer> emptyLength = empty.map(String::length);  // Optional.empty

// FlatMap: Transform to Optional (avoid nested Optional)
Optional<String> optional2 = Optional.of("Hello");

// This would create Optional<Optional<String>>
// Optional<Optional<String>> nested = optional2.map(s -> Optional.of(s.toUpperCase()));

// FlatMap flattens it to Optional<String>
Optional<String> flattened = optional2.flatMap(s -> Optional.of(s.toUpperCase()));

// Real-world example
class Person {
    private Optional<String> email;

    public Optional<String> getEmail() {
        return email;
    }
}

Optional<Person> person = Optional.of(new Person());

// Using flatMap to avoid Optional<Optional<String>>
Optional<String> email = person.flatMap(Person::getEmail);
```

### Filtering Values

```java
Optional<Integer> optional = Optional.of(42);

// Filter: Keep value only if predicate is true
Optional<Integer> even = optional.filter(num -> num % 2 == 0);  // Optional[42]
Optional<Integer> odd = optional.filter(num -> num % 2 != 0);   // Optional.empty

// Chain filtering and mapping
Optional<String> result = Optional.of("Hello")
    .filter(s -> s.length() > 3)
    .map(String::toUpperCase);  // Optional[HELLO]
```

### Optional in Streams (Java 9+)

```java
// Stream of Optional
List<Optional<String>> optionals = Arrays.asList(
    Optional.of("A"),
    Optional.empty(),
    Optional.of("B"),
    Optional.empty(),
    Optional.of("C")
);

// Extract present values
List<String> values = optionals.stream()
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList());  // [A, B, C]

// Or using flatMap with stream() (Java 9+)
List<String> values2 = optionals.stream()
    .flatMap(Optional::stream)  // Converts Optional to Stream
    .collect(Collectors.toList());  // [A, B, C]
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

// DON'T call get() without checking
Optional<String> optional = Optional.empty();
String value = optional.get();  // BAD - throws exception

// DO use orElse or orElseThrow
String value2 = optional.orElse("Default");  // GOOD

// DON'T use isPresent() + get()
if (optional.isPresent()) {  // BAD
    System.out.println(optional.get());
}

// DO use ifPresent()
optional.ifPresent(System.out::println);  // GOOD

// DON'T use Optional.of with potentially null values
String nullValue = null;
Optional<String> willThrow = Optional.of(nullValue);  // BAD - throws NPE

// DO use Optional.ofNullable
Optional<String> safe = Optional.ofNullable(nullValue);  // GOOD

// Real-world example: Avoiding nested if-else
public String getUserEmailOrDefault(Long userId) {
    return findUserById(userId)
        .flatMap(User::getEmail)
        .orElse("no-email@example.com");
}

// Instead of:
public String getUserEmailOrDefaultOld(Long userId) {
    User user = findUserByIdOld(userId);
    if (user != null) {
        String email = user.getEmailOld();
        if (email != null) {
            return email;
        }
    }
    return "no-email@example.com";
}
```

### Optional with Streams

```java
// Example: Process list of users
class User {
    private String name;
    private Optional<String> email;

    public Optional<String> getEmail() {
        return email;
    }
}

List<User> users = Arrays.asList(/* users */);

// Get all emails that are present
List<String> emails = users.stream()
    .map(User::getEmail)
    .filter(Optional::isPresent)
    .map(Optional::get)
    .collect(Collectors.toList());

// Or using flatMap (Java 9+)
List<String> emails2 = users.stream()
    .map(User::getEmail)
    .flatMap(Optional::stream)
    .collect(Collectors.toList());
```

---

## Functional Programming Patterns

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

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Return new instance instead of modifying
    public ImmutablePerson withAge(int newAge) {
        return new ImmutablePerson(this.name, newAge);
    }
}
```

### Pure Functions

Functions with no side effects.

```java
// Pure function (no side effects)
public int add(int a, int b) {
    return a + b;
}

// Impure function (side effect: modifies state)
private int total = 0;
public int addToTotal(int value) {
    total += value;  // Modifies external state
    return total;
}

// Pure function (no side effects, always same output for same input)
public List<Integer> doubleNumbers(List<Integer> numbers) {
    return numbers.stream()
        .map(n -> n * 2)
        .collect(Collectors.toList());
}
```

### Function Composition

```java
Function<Integer, Integer> multiplyBy2 = x -> x * 2;
Function<Integer, Integer> add3 = x -> x + 3;

// Compose: execute in reverse order
Function<Integer, Integer> composed = multiplyBy2.compose(add3);
System.out.println(composed.apply(5));  // 16 ((5+3)*2)

// AndThen: execute in order
Function<Integer, Integer> andThen = multiplyBy2.andThen(add3);
System.out.println(andThen.apply(5));  // 13 ((5*2)+3)
```

---

## Summary

| Concept | Key Points |
|---------|------------|
| Functional Interfaces | Single abstract method, can use @FunctionalInterface |
| Built-in Interfaces | Predicate, Function, Consumer, Supplier, BiFunction, etc. |
| Lambdas | Concise syntax for functional interfaces: (params) -> expression |
| Method References | Shorthand for lambdas: Class::method |
| Optional | Container for potentially absent values, avoid null checks |
| Immutability | Create new instances instead of modifying existing |
| Pure Functions | No side effects, same output for same input |

## Next Topic

Continue to [Stream API](./07-stream-api.md) to learn about processing collections with streams.

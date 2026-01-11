# Java APIs

## Table of Contents

### Required Topics
- [Stream API](#stream-api)
  - [Stream Basics](#stream-basics)
  - [Intermediate Operations](#intermediate-operations)
  - [Terminal Operations](#terminal-operations)
  - [Collectors](#collectors)
  - [Parallel Streams](#parallel-streams)
- [Date and Time API](#date-and-time-api)
  - [LocalDate](#localdate)
  - [LocalTime](#localtime)
  - [LocalDateTime](#localdatetime)
  - [Formatting and Parsing](#formatting-and-parsing)
  - [Period and Duration](#period-and-duration)
- [Reflection API](#reflection-api)
  - [Class Object](#class-object)
  - [Inspecting Methods](#inspecting-methods)
  - [Inspecting Fields](#inspecting-fields)
  - [Working with Annotations](#working-with-annotations)
- [Summary](#summary)

### Optional / Additional Reference
- [Primitive Streams](#primitive-streams-optional)
- [Advanced Collectors](#advanced-collectors-optional)
- [Advanced Reflection](#advanced-reflection-optional)

---

# Stream API

The Stream API provides a functional approach to processing collections of objects.

---

## Stream Basics

A stream is a sequence of elements supporting sequential and parallel aggregate operations.

**Key Characteristics:**
- Not a data structure (doesn't store elements)
- Functional in nature (operations produce result without modifying source)
- Lazy evaluation (intermediate operations not executed until terminal operation)
- Consumable (can only be traversed once)

### Creating Streams

```java
import java.util.stream.*;
import java.util.Arrays;
import java.util.List;

// From collection
List<String> list = Arrays.asList("A", "B", "C");
Stream<String> stream1 = list.stream();

// From array
String[] array = {"A", "B", "C"};
Stream<String> stream2 = Arrays.stream(array);
Stream<String> stream3 = Stream.of("A", "B", "C");

// Empty stream
Stream<String> empty = Stream.empty();

// Infinite streams
Stream<Integer> infinite = Stream.iterate(0, n -> n + 1);  // 0, 1, 2, 3, ...
Stream<Double> random = Stream.generate(Math::random);

// From range (IntStream)
IntStream range = IntStream.range(0, 10);        // 0 to 9
IntStream rangeClosed = IntStream.rangeClosed(0, 10);  // 0 to 10
```

### Stream Pipeline

Stream operations form a pipeline: **Source → Intermediate Operations → Terminal Operation**

```
Source → filter() → map() → sorted() → collect()
         ↑ Intermediate operations ↑   ↑ Terminal
```

```java
List<String> result = list.stream()         // Source
    .filter(s -> s.length() > 2)            // Intermediate
    .map(String::toUpperCase)               // Intermediate
    .sorted()                               // Intermediate
    .collect(Collectors.toList());          // Terminal

// Nothing happens until terminal operation is called!
Stream<String> stream = list.stream()
    .filter(s -> s.length() > 2)
    .map(String::toUpperCase);
// No processing yet!

long count = stream.count();  // Now processing happens
```

---

## Intermediate Operations

Intermediate operations return a new stream and are **lazy** (not executed until terminal operation).

### filter()

Keep elements that match predicate.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Filter even numbers
List<Integer> even = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());  // [2, 4, 6, 8, 10]

// Multiple filters
List<Integer> result = numbers.stream()
    .filter(n -> n > 3)
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());  // [4, 6, 8, 10]
```

### map()

Transform elements.

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

// Transform to uppercase
List<String> upper = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());  // [ALICE, BOB, CHARLIE]

// Extract length
List<Integer> lengths = names.stream()
    .map(String::length)
    .collect(Collectors.toList());  // [5, 3, 7]

// Transform objects
List<Person> people = Arrays.asList(
    new Person("Alice", 25),
    new Person("Bob", 30)
);

List<String> personNames = people.stream()
    .map(Person::getName)
    .collect(Collectors.toList());  // [Alice, Bob]
```

### flatMap()

Flatten nested structures.

```java
// List of lists
List<List<Integer>> listOfLists = Arrays.asList(
    Arrays.asList(1, 2, 3),
    Arrays.asList(4, 5),
    Arrays.asList(6, 7, 8, 9)
);

// Flatten to single list
List<Integer> flattened = listOfLists.stream()
    .flatMap(List::stream)
    .collect(Collectors.toList());  // [1, 2, 3, 4, 5, 6, 7, 8, 9]

// Split strings into words
List<String> sentences = Arrays.asList("Hello World", "Java Stream API");

List<String> words = sentences.stream()
    .flatMap(s -> Arrays.stream(s.split(" ")))
    .collect(Collectors.toList());  // [Hello, World, Java, Stream, API]
```

### distinct()

Remove duplicates (uses `equals()` and `hashCode()`).

```java
List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 5, 5);

List<Integer> unique = numbers.stream()
    .distinct()
    .collect(Collectors.toList());  // [1, 2, 3, 4, 5]
```

### sorted()

Sort elements.

```java
List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9, 3);

// Natural order
List<Integer> sorted = numbers.stream()
    .sorted()
    .collect(Collectors.toList());  // [1, 2, 3, 5, 8, 9]

// Reverse order
List<Integer> reversed = numbers.stream()
    .sorted(Comparator.reverseOrder())
    .collect(Collectors.toList());  // [9, 8, 5, 3, 2, 1]

// Custom comparator
List<String> names = Arrays.asList("Charlie", "Alice", "Bob");

List<String> byLength = names.stream()
    .sorted(Comparator.comparingInt(String::length))
    .collect(Collectors.toList());  // [Bob, Alice, Charlie]
```

### limit() and skip()

Truncate or skip elements.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Take first 5
List<Integer> first5 = numbers.stream()
    .limit(5)
    .collect(Collectors.toList());  // [1, 2, 3, 4, 5]

// Skip first 5
List<Integer> skip5 = numbers.stream()
    .skip(5)
    .collect(Collectors.toList());  // [6, 7, 8, 9, 10]

// Pagination: Skip first 20, take next 10
List<Integer> page = numbers.stream()
    .skip(20)
    .limit(10)
    .collect(Collectors.toList());
```

### peek()

Perform action without modifying stream (useful for debugging).

```java
List<Integer> result = numbers.stream()
    .peek(n -> System.out.println("Original: " + n))
    .map(n -> n * 2)
    .peek(n -> System.out.println("Doubled: " + n))
    .filter(n -> n > 5)
    .collect(Collectors.toList());
```

---

## Terminal Operations

Terminal operations produce a result or side effect and **close the stream**.

### forEach()

Perform action for each element.

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

names.stream().forEach(System.out::println);

// Or simply
names.forEach(System.out::println);
```

### collect()

Convert stream to collection or other data structure.

```java
List<String> list = stream.collect(Collectors.toList());
Set<String> set = stream.collect(Collectors.toSet());
```

### reduce()

Reduce elements to single value.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Sum with identity
int sum = numbers.stream()
    .reduce(0, (a, b) -> a + b);  // 15

// Using method reference
int sum2 = numbers.stream()
    .reduce(0, Integer::sum);  // 15

// Without identity (returns Optional)
Optional<Integer> max = numbers.stream()
    .reduce(Integer::max);  // Optional[5]

// String concatenation
List<String> words = Arrays.asList("Hello", "World");
String sentence = words.stream()
    .reduce("", (a, b) -> a + " " + b).trim();  // "Hello World"
```

### count()

Count elements.

```java
long count = names.stream().count();  // 3

// Count with filter
long longNames = names.stream()
    .filter(s -> s.length() > 4)
    .count();  // 2
```

### min() and max()

Find minimum or maximum element.

```java
List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9, 3);

Optional<Integer> min = numbers.stream()
    .min(Comparator.naturalOrder());  // Optional[1]

Optional<Integer> max = numbers.stream()
    .max(Comparator.naturalOrder());  // Optional[9]
```

### anyMatch(), allMatch(), noneMatch()

Check if elements match predicate.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// anyMatch: At least one matches
boolean hasEven = numbers.stream()
    .anyMatch(n -> n % 2 == 0);  // true

// allMatch: All match
boolean allPositive = numbers.stream()
    .allMatch(n -> n > 0);  // true

// noneMatch: None match
boolean noNegative = numbers.stream()
    .noneMatch(n -> n < 0);  // true
```

### findFirst() and findAny()

Find an element.

```java
Optional<String> first = names.stream()
    .findFirst();  // Optional[Alice]

// With filter
Optional<String> firstLong = names.stream()
    .filter(s -> s.length() > 5)
    .findFirst();  // Optional[Charlie]
```

### toArray()

Convert stream to array.

```java
String[] array = names.stream()
    .toArray(String[]::new);
```

---

## Collectors

Collectors are used with `collect()` to accumulate elements into collections or other results.

### Basic Collectors

```java
import java.util.stream.Collectors;

// To List
List<String> list = stream.collect(Collectors.toList());

// To Set
Set<String> set = stream.collect(Collectors.toSet());

// To specific collection
LinkedList<String> linkedList = stream
    .collect(Collectors.toCollection(LinkedList::new));
```

### toMap()

```java
List<Person> people = Arrays.asList(
    new Person("Alice", 25),
    new Person("Bob", 30)
);

// Map name to person
Map<String, Person> byName = people.stream()
    .collect(Collectors.toMap(
        Person::getName,     // Key
        person -> person     // Value
    ));

// Map name to age
Map<String, Integer> nameToAge = people.stream()
    .collect(Collectors.toMap(
        Person::getName,
        Person::getAge
    ));

// Handle duplicate keys
Map<String, Integer> wordLength = words.stream()
    .collect(Collectors.toMap(
        word -> word,
        String::length,
        (existing, replacement) -> existing  // Keep existing on duplicate
    ));
```

### joining()

Concatenate strings.

```java
List<String> words = Arrays.asList("Hello", "World", "!");

// Simple join
String joined = words.stream()
    .collect(Collectors.joining());  // "HelloWorld!"

// With delimiter
String withComma = words.stream()
    .collect(Collectors.joining(", "));  // "Hello, World, !"

// With delimiter, prefix, suffix
String formatted = words.stream()
    .collect(Collectors.joining(", ", "[", "]"));  // "[Hello, World, !]"
```

### groupingBy()

Group elements by classifier.

```java
List<Person> people = Arrays.asList(
    new Person("Alice", 25),
    new Person("Bob", 25),
    new Person("Charlie", 30)
);

// Group by age
Map<Integer, List<Person>> byAge = people.stream()
    .collect(Collectors.groupingBy(Person::getAge));
// {25=[Alice, Bob], 30=[Charlie]}

// Group and count
Map<Integer, Long> countByAge = people.stream()
    .collect(Collectors.groupingBy(
        Person::getAge,
        Collectors.counting()
    ));
// {25=2, 30=1}

// Group and collect names
Map<Integer, List<String>> namesByAge = people.stream()
    .collect(Collectors.groupingBy(
        Person::getAge,
        Collectors.mapping(Person::getName, Collectors.toList())
    ));
// {25=[Alice, Bob], 30=[Charlie]}
```

### partitioningBy()

Partition into two groups based on predicate.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// Partition by even/odd
Map<Boolean, List<Integer>> partitioned = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));
// {false=[1, 3, 5, 7, 9], true=[2, 4, 6, 8, 10]}
```

---

## Parallel Streams

Process streams in parallel for better performance on multi-core systems.

### Creating Parallel Streams

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// From collection
Stream<Integer> parallel = numbers.parallelStream();

// Convert sequential to parallel
Stream<Integer> parallel2 = numbers.stream().parallel();

// Convert parallel to sequential
Stream<Integer> sequential = parallel.sequential();
```

### When to Use Parallel Streams

**Use parallel streams when:**
- Large data set (thousands of elements)
- Computationally expensive operations
- Stateless operations
- No shared mutable state

**Avoid parallel streams when:**
- Small data set (overhead > benefit)
- IO-bound operations
- Order matters
- Shared mutable state (race conditions)

### Thread Safety

```java
// WRONG: Not thread-safe
List<Integer> list = new ArrayList<>();
IntStream.range(0, 1000).parallel()
    .forEach(list::add);  // Race condition!

// CORRECT: Use collect()
List<Integer> list2 = IntStream.range(0, 1000)
    .parallel()
    .boxed()
    .collect(Collectors.toList());  // Thread-safe
```

---

# Date and Time API

Java 8 introduced the `java.time` package with immutable, thread-safe date/time classes.

---

## LocalDate

Represents a date without time (year-month-day).

```java
import java.time.LocalDate;
import java.time.Month;

// Create LocalDate
LocalDate today = LocalDate.now();                    // Current date
LocalDate specific = LocalDate.of(2024, 1, 15);       // 2024-01-15
LocalDate fromMonth = LocalDate.of(2024, Month.JANUARY, 15);
LocalDate parsed = LocalDate.parse("2024-01-15");     // ISO format

// Get components
int year = today.getYear();           // 2024
Month month = today.getMonth();       // JANUARY
int monthValue = today.getMonthValue(); // 1
int dayOfMonth = today.getDayOfMonth(); // 15
int dayOfYear = today.getDayOfYear();   // 15
java.time.DayOfWeek dayOfWeek = today.getDayOfWeek(); // MONDAY

// Manipulate dates (returns new instance - immutable)
LocalDate tomorrow = today.plusDays(1);
LocalDate nextWeek = today.plusWeeks(1);
LocalDate nextMonth = today.plusMonths(1);
LocalDate nextYear = today.plusYears(1);

LocalDate yesterday = today.minusDays(1);
LocalDate lastMonth = today.minusMonths(1);

// Compare dates
boolean isBefore = today.isBefore(tomorrow);  // true
boolean isAfter = today.isAfter(yesterday);   // true
boolean isEqual = today.isEqual(LocalDate.now());

// Check properties
boolean isLeapYear = today.isLeapYear();
int lengthOfMonth = today.lengthOfMonth();    // 31 for January
int lengthOfYear = today.lengthOfYear();      // 365 or 366
```

---

## LocalTime

Represents a time without date (hour-minute-second-nanosecond).

```java
import java.time.LocalTime;

// Create LocalTime
LocalTime now = LocalTime.now();                  // Current time
LocalTime specific = LocalTime.of(14, 30);        // 14:30
LocalTime withSeconds = LocalTime.of(14, 30, 45); // 14:30:45
LocalTime parsed = LocalTime.parse("14:30:45");   // ISO format

// Get components
int hour = now.getHour();         // 0-23
int minute = now.getMinute();     // 0-59
int second = now.getSecond();     // 0-59
int nano = now.getNano();         // 0-999999999

// Manipulate times (immutable)
LocalTime later = now.plusHours(2);
LocalTime earlier = now.minusMinutes(30);
LocalTime plusSeconds = now.plusSeconds(45);

// Compare times
boolean isBefore = now.isBefore(later);    // true
boolean isAfter = now.isAfter(earlier);    // true

// Constants
LocalTime midnight = LocalTime.MIDNIGHT;   // 00:00
LocalTime noon = LocalTime.NOON;           // 12:00
LocalTime max = LocalTime.MAX;             // 23:59:59.999999999
LocalTime min = LocalTime.MIN;             // 00:00
```

---

## LocalDateTime

Combines date and time without timezone.

```java
import java.time.LocalDateTime;

// Create LocalDateTime
LocalDateTime now = LocalDateTime.now();
LocalDateTime specific = LocalDateTime.of(2024, 1, 15, 14, 30);
LocalDateTime fromDateAndTime = LocalDateTime.of(
    LocalDate.of(2024, 1, 15),
    LocalTime.of(14, 30)
);
LocalDateTime parsed = LocalDateTime.parse("2024-01-15T14:30:00");

// Get components
LocalDate date = now.toLocalDate();
LocalTime time = now.toLocalTime();
int year = now.getYear();
int hour = now.getHour();

// Manipulate (immutable)
LocalDateTime tomorrow = now.plusDays(1);
LocalDateTime nextHour = now.plusHours(1);

// Convert between types
LocalDate dateOnly = now.toLocalDate();
LocalTime timeOnly = now.toLocalTime();

// Combine date and time
LocalDate date2 = LocalDate.of(2024, 1, 15);
LocalTime time2 = LocalTime.of(14, 30);
LocalDateTime combined = LocalDateTime.of(date2, time2);
LocalDateTime atTime = date2.atTime(14, 30);
LocalDateTime atDate = time2.atDate(date2);
```

---

## Formatting and Parsing

```java
import java.time.format.DateTimeFormatter;

LocalDateTime now = LocalDateTime.now();

// Predefined formatters
String isoDate = now.format(DateTimeFormatter.ISO_LOCAL_DATE);      // 2024-01-15
String isoDateTime = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME); // 2024-01-15T14:30:00

// Custom patterns
DateTimeFormatter custom = DateTimeFormatter.ofPattern("dd/MM/yyyy");
String formatted = now.format(custom);  // 15/01/2024

DateTimeFormatter fullFormat = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm:ss");
String full = now.format(fullFormat);  // Monday, January 15, 2024 14:30:00

DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("hh:mm a");
String time12Hour = now.format(timeFormat);  // 02:30 PM

// Common patterns
// yyyy - Year (2024)
// MM   - Month (01-12)
// dd   - Day (01-31)
// HH   - Hour 24h (00-23)
// hh   - Hour 12h (01-12)
// mm   - Minute (00-59)
// ss   - Second (00-59)
// a    - AM/PM
// EEEE - Full day name (Monday)
// MMMM - Full month name (January)

// Parsing
LocalDate parsedDate = LocalDate.parse("15/01/2024", custom);
LocalDateTime parsedDateTime = LocalDateTime.parse(
    "Monday, January 15, 2024 14:30:00",
    fullFormat
);
```

---

## Period and Duration

### Period (Date-based)

```java
import java.time.Period;

LocalDate start = LocalDate.of(2024, 1, 1);
LocalDate end = LocalDate.of(2024, 6, 15);

// Calculate period between dates
Period period = Period.between(start, end);
int years = period.getYears();    // 0
int months = period.getMonths();  // 5
int days = period.getDays();      // 14

// Create period
Period twoWeeks = Period.ofWeeks(2);
Period oneMonth = Period.ofMonths(1);
Period oneYear = Period.ofYears(1);
Period complex = Period.of(1, 2, 3);  // 1 year, 2 months, 3 days

// Add period to date
LocalDate futureDate = start.plus(period);
LocalDate nextMonth = start.plus(Period.ofMonths(1));
```

### Duration (Time-based)

```java
import java.time.Duration;

LocalTime start = LocalTime.of(9, 0);
LocalTime end = LocalTime.of(17, 30);

// Calculate duration between times
Duration duration = Duration.between(start, end);
long hours = duration.toHours();      // 8
long minutes = duration.toMinutes();  // 510

// Create duration
Duration twoHours = Duration.ofHours(2);
Duration thirtyMinutes = Duration.ofMinutes(30);
Duration tenSeconds = Duration.ofSeconds(10);

// Add duration to time
LocalTime later = start.plus(Duration.ofHours(2));
```

---

# Reflection API

Reflection allows inspection and manipulation of classes, methods, and fields at runtime.

---

## Class Object

The `Class` object represents a class at runtime.

```java
// Get Class object
Class<?> class1 = String.class;                    // From type
Class<?> class2 = "Hello".getClass();              // From instance
Class<?> class3 = Class.forName("java.lang.String"); // From name

// Class information
String name = class1.getName();           // java.lang.String
String simpleName = class1.getSimpleName(); // String
String packageName = class1.getPackageName(); // java.lang

// Check class type
boolean isInterface = class1.isInterface();
boolean isArray = class1.isArray();
boolean isEnum = class1.isEnum();
boolean isPrimitive = int.class.isPrimitive();

// Superclass and interfaces
Class<?> superclass = class1.getSuperclass();      // Object
Class<?>[] interfaces = class1.getInterfaces();    // Serializable, Comparable, etc.

// Modifiers
int modifiers = class1.getModifiers();
boolean isPublic = Modifier.isPublic(modifiers);
boolean isFinal = Modifier.isFinal(modifiers);
boolean isAbstract = Modifier.isAbstract(modifiers);
```

### Example Class for Reflection

```java
public class Person {
    private String name;
    private int age;
    public String email;

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private void privateMethod() {
        System.out.println("Private method called");
    }

    public String greet(String greeting) {
        return greeting + ", " + name;
    }
}
```

---

## Inspecting Methods

```java
Class<?> clazz = Person.class;

// Get all public methods (including inherited)
Method[] publicMethods = clazz.getMethods();

// Get all declared methods (including private, excluding inherited)
Method[] allMethods = clazz.getDeclaredMethods();

// Get specific method by name and parameter types
Method getNameMethod = clazz.getMethod("getName");
Method greetMethod = clazz.getMethod("greet", String.class);

// Method information
String methodName = getNameMethod.getName();            // getName
Class<?> returnType = getNameMethod.getReturnType();    // String.class
Class<?>[] paramTypes = greetMethod.getParameterTypes(); // [String.class]
int modifiers = getNameMethod.getModifiers();

// Invoke method
Person person = new Person("Alice", 25);

// Invoke no-arg method
String name = (String) getNameMethod.invoke(person);  // "Alice"

// Invoke method with arguments
String greeting = (String) greetMethod.invoke(person, "Hello");  // "Hello, Alice"

// Access private method
Method privateMethod = clazz.getDeclaredMethod("privateMethod");
privateMethod.setAccessible(true);  // Bypass access check
privateMethod.invoke(person);       // "Private method called"
```

---

## Inspecting Fields

```java
Class<?> clazz = Person.class;

// Get all public fields
Field[] publicFields = clazz.getFields();

// Get all declared fields (including private)
Field[] allFields = clazz.getDeclaredFields();

// Get specific field
Field emailField = clazz.getField("email");           // Public field
Field nameField = clazz.getDeclaredField("name");     // Private field

// Field information
String fieldName = nameField.getName();      // name
Class<?> fieldType = nameField.getType();    // String.class
int modifiers = nameField.getModifiers();

// Read field value
Person person = new Person("Alice", 25);
person.email = "alice@example.com";

String email = (String) emailField.get(person);  // "alice@example.com"

// Access private field
nameField.setAccessible(true);
String name = (String) nameField.get(person);    // "Alice"

// Set field value
nameField.set(person, "Bob");
System.out.println(person.getName());  // "Bob"
```

---

## Working with Annotations

```java
import java.lang.annotation.*;

// Define custom annotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface MyAnnotation {
    String value() default "";
    int priority() default 0;
}

// Use annotation
@MyAnnotation(value = "Person class", priority = 1)
public class Person {

    @MyAnnotation("name field")
    private String name;

    @MyAnnotation(value = "greet method", priority = 2)
    public String greet() {
        return "Hello, " + name;
    }
}

// Read annotations via reflection
Class<?> clazz = Person.class;

// Check if annotation is present
boolean hasAnnotation = clazz.isAnnotationPresent(MyAnnotation.class);

// Get annotation
MyAnnotation classAnnotation = clazz.getAnnotation(MyAnnotation.class);
if (classAnnotation != null) {
    String value = classAnnotation.value();      // "Person class"
    int priority = classAnnotation.priority();   // 1
}

// Get annotations from methods
Method greetMethod = clazz.getMethod("greet");
MyAnnotation methodAnnotation = greetMethod.getAnnotation(MyAnnotation.class);

// Get annotations from fields
Field nameField = clazz.getDeclaredField("name");
MyAnnotation fieldAnnotation = nameField.getAnnotation(MyAnnotation.class);

// Get all annotations
Annotation[] allAnnotations = clazz.getAnnotations();
```

### Creating Instances via Reflection

```java
Class<?> clazz = Person.class;

// Using no-arg constructor
Person person1 = (Person) clazz.getDeclaredConstructor().newInstance();

// Using parameterized constructor
Constructor<?> constructor = clazz.getDeclaredConstructor(String.class, int.class);
Person person2 = (Person) constructor.newInstance("Alice", 25);
```

---

## Summary

### Required Concepts

| Topic | Key Points |
|-------|------------|
| Stream Basics | `stream()`, pipeline (source → intermediate → terminal), lazy evaluation |
| Intermediate Ops | `filter()`, `map()`, `flatMap()`, `distinct()`, `sorted()`, `limit()`, `skip()` |
| Terminal Ops | `forEach()`, `collect()`, `reduce()`, `count()`, `findFirst()`, `anyMatch()` |
| Collectors | `toList()`, `toSet()`, `toMap()`, `joining()`, `groupingBy()`, `partitioningBy()` |
| Parallel Streams | `parallelStream()`, thread safety, when to use |
| LocalDate | Date without time, `of()`, `now()`, `plusDays()`, `isBefore()` |
| LocalTime | Time without date, `of()`, `now()`, `plusHours()` |
| LocalDateTime | Date and time combined, conversions |
| DateTimeFormatter | `ofPattern()`, parsing and formatting |
| Period/Duration | Date-based vs time-based intervals |
| Class Object | `getClass()`, `Class.forName()`, class information |
| Method Reflection | `getMethods()`, `invoke()`, accessing private methods |
| Field Reflection | `getFields()`, `get()`, `set()`, accessing private fields |
| Annotations | `@Retention`, `@Target`, `getAnnotation()` |

### Quick Reference

```java
// Stream
List<String> result = list.stream()
    .filter(s -> s.length() > 3)
    .map(String::toUpperCase)
    .collect(Collectors.toList());

// Date/Time
LocalDate date = LocalDate.now().plusDays(7);
String formatted = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

// Reflection
Method method = obj.getClass().getMethod("methodName", String.class);
Object result = method.invoke(obj, "argument");
```

---

# Additional Reference (Optional)

> The following sections are optional and provided for additional learning. Focus on the required topics above first.

---

## Primitive Streams (Optional)

Specialized streams for primitives to avoid boxing/unboxing overhead.

### IntStream, LongStream, DoubleStream

```java
// IntStream
IntStream intStream = IntStream.range(1, 10);        // 1 to 9
IntStream intStreamClosed = IntStream.rangeClosed(1, 10);  // 1 to 10

// Direct operations
int sum = IntStream.range(1, 10).sum();              // 45
OptionalDouble avg = IntStream.range(1, 10).average(); // 5.0
OptionalInt max = IntStream.range(1, 10).max();      // 9

// Summary statistics
IntSummaryStatistics stats = IntStream.range(1, 10).summaryStatistics();
System.out.println("Count: " + stats.getCount());
System.out.println("Sum: " + stats.getSum());
System.out.println("Min: " + stats.getMin());
System.out.println("Max: " + stats.getMax());
System.out.println("Average: " + stats.getAverage());

// Convert between stream types
Stream<Integer> boxed = IntStream.range(1, 10).boxed();
IntStream ints = Stream.of(1, 2, 3).mapToInt(Integer::intValue);
```

---

## Advanced Collectors (Optional)

### Summarizing Statistics

```java
List<Person> people = Arrays.asList(
    new Person("Alice", 25),
    new Person("Bob", 30),
    new Person("Charlie", 35)
);

// Summary statistics for ages
IntSummaryStatistics ageStats = people.stream()
    .collect(Collectors.summarizingInt(Person::getAge));

// Individual statistics
int sum = people.stream().collect(Collectors.summingInt(Person::getAge));
double avg = people.stream().collect(Collectors.averagingInt(Person::getAge));
```

### Multi-level Grouping

```java
Map<Integer, Map<String, List<Person>>> grouped = people.stream()
    .collect(Collectors.groupingBy(
        Person::getAge,
        Collectors.groupingBy(p -> p.getName().substring(0, 1))
    ));
```

### Teeing Collector (Java 12+)

```java
// Collect into two different results simultaneously
var result = numbers.stream()
    .collect(Collectors.teeing(
        Collectors.summingInt(Integer::intValue),
        Collectors.counting(),
        (sum, count) -> "Sum: " + sum + ", Count: " + count
    ));
```

---

## Advanced Reflection (Optional)

### Creating Instances Dynamically

```java
// Create instance from class name
String className = "com.example.Person";
Class<?> clazz = Class.forName(className);
Object instance = clazz.getDeclaredConstructor().newInstance();

// Create array dynamically
Object array = Array.newInstance(String.class, 10);
Array.set(array, 0, "Hello");
String value = (String) Array.get(array, 0);
```

### Proxy Classes

```java
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;

interface Greeting {
    String sayHello(String name);
}

// Create dynamic proxy
Greeting proxy = (Greeting) Proxy.newProxyInstance(
    Greeting.class.getClassLoader(),
    new Class<?>[] { Greeting.class },
    (proxyObj, method, args) -> {
        System.out.println("Method called: " + method.getName());
        return "Hello, " + args[0];
    }
);

String result = proxy.sayHello("World");  // "Hello, World"
```

### Generic Type Information

```java
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class StringList extends ArrayList<String> {}

// Get generic type at runtime
Type superclass = StringList.class.getGenericSuperclass();
if (superclass instanceof ParameterizedType) {
    ParameterizedType pt = (ParameterizedType) superclass;
    Type[] typeArgs = pt.getActualTypeArguments();
    System.out.println(typeArgs[0]);  // class java.lang.String
}
```

---

## Next Topic

Continue to [File I/O and Serialization](./08-file-io-serialization.md) to learn about file handling and object serialization in Java.

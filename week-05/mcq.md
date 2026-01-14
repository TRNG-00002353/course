# Week 05 - Multiple Choice Questions

This document contains 80 multiple choice questions covering the key concepts from Week 05 topics: Reactive Programming, Testing (JUnit & Mockito), and Logging Frameworks.

**Topic Distribution:**
- Reactive Programming: 20 questions
- JUnit Testing: 25 questions
- Mockito Framework: 20 questions
- Logging Frameworks: 15 questions

---

**Note:** Answers and explanations are in `mcq-answers.md`

---

## Reactive Programming

### Question 1
**[Reactive]**

What is reactive programming primarily focused on?

- A) Object-oriented design patterns
- B) Data streams and propagation of change
- C) Synchronous data processing
- D) Database transactions

---

### Question 2
**[Reactive]**

Which is NOT one of the four pillars of the Reactive Manifesto?

- A) Responsive
- B) Resilient
- C) Scalable
- D) Elastic

---

### Question 3
**[Reactive]**

What is backpressure in reactive programming?

- A) A technique to compress data
- B) A mechanism for the consumer to control the rate of data flow
- C) A method to increase network bandwidth
- D) A way to prioritize messages

---

### Question 4
**[Reactive]**

In Java's Flow API, which interface emits data items?

- A) Subscriber
- B) Publisher
- C) Subscription
- D) Processor

---

### Question 5
**[Reactive]**

What method does a Subscriber call to request more items from the Publisher?

- A) subscribe()
- B) request()
- C) onNext()
- D) poll()

---

### Question 6
**[Reactive]**

Which Subscriber method is called when an error occurs?

- A) onComplete()
- B) onError()
- C) onFail()
- D) onException()

---

### Question 7
**[Reactive]**

What is the purpose of SubmissionPublisher in Java?

- A) To submit forms to a server
- B) To handle Publisher implementation with backpressure support
- C) To publish articles to a website
- D) To submit batch jobs

---

### Question 8
**[Reactive]**

The Reactive Manifesto was published in which year?

- A) 2010
- B) 2014
- C) 2018
- D) 2020

---

### Question 9
**[Reactive]**

Which pillar of the Reactive Manifesto is described as "the system stays responsive in the face of failure"?

- A) Responsive
- B) Resilient
- C) Elastic
- D) Message-Driven

---

### Question 10
**[Reactive]**

In reactive streams, what is a Processor?

- A) A CPU component
- B) Both a Publisher and a Subscriber
- C) A data serializer
- D) A network handler

---

### Question 11
**[Reactive]**

What is the foundation pillar that enables all other properties in the Reactive Manifesto?

- A) Responsive
- B) Resilient
- C) Elastic
- D) Message-Driven

---

### Question 12
**[Reactive]**

Which pattern does reactive programming follow for data flow?

- A) Pull-based
- B) Push-based
- C) Batch-based
- D) Queue-based

---

### Question 13
**[Reactive]**

What package contains Java's built-in reactive streams interfaces?

- A) java.reactive
- B) java.util.concurrent.Flow
- C) java.stream.reactive
- D) javax.reactive.streams

---

### Question 14
**[Reactive]**

Which method is called on a Subscriber when the subscription is established?

- A) onNext()
- B) onSubscribe()
- C) onStart()
- D) onConnect()

---

### Question 15
**[Reactive]**

What happens when a Publisher calls onComplete()?

- A) An error occurred
- B) The stream is paused
- C) No more items will be emitted
- D) The subscription is cancelled

---

### Question 16
**[Reactive]**

Which backpressure strategy discards excess items?

- A) Buffer
- B) Drop
- C) Error
- D) Block

---

### Question 17
**[Reactive]**

What does "elastic" mean in the context of the Reactive Manifesto?

- A) The system can be stretched physically
- B) The system adapts to varying workload
- C) The system is flexible to code changes
- D) The system uses flexible data types

---

### Question 18
**[Reactive]**

Which Java version introduced the Flow API?

- A) Java 8
- B) Java 9
- C) Java 11
- D) Java 17

---

### Question 19
**[Reactive]**

In reactive programming, what is the main advantage over traditional blocking I/O?

- A) Simpler code
- B) Better resource utilization and scalability
- C) Faster single-threaded performance
- D) Easier debugging

---

### Question 20
**[Reactive]**

What method is used to cancel a subscription?

- A) subscription.stop()
- B) subscription.cancel()
- C) subscription.terminate()
- D) subscription.end()

---

## JUnit Testing

### Question 21
**[JUnit]**

Which annotation marks a method as a test in JUnit 5?

- A) @TestMethod
- B) @UnitTest
- C) @Test
- D) @RunTest

---

### Question 22
**[JUnit]**

What is the correct order of execution in JUnit test lifecycle?

- A) @Test, @BeforeEach, @AfterEach
- B) @BeforeAll, @BeforeEach, @Test, @AfterEach, @AfterAll
- C) @BeforeEach, @Test, @BeforeAll, @AfterAll
- D) @Test, @BeforeAll, @AfterAll

---

### Question 23
**[JUnit]**

Which assertion method checks if two values are equal?

- A) assertSame()
- B) assertEquals()
- C) assertIdentical()
- D) assertMatch()

---

### Question 24
**[JUnit]**

What does @BeforeEach do?

- A) Runs once before all tests
- B) Runs before each test method
- C) Runs after each test method
- D) Runs at the start of the test suite

---

### Question 25
**[JUnit]**

Which method is used to verify that an exception is thrown?

- A) assertException()
- B) expectThrows()
- C) assertThrows()
- D) catchException()

---

### Question 26
**[JUnit]**

What does AAA stand for in testing?

- A) Assert-Act-Arrange
- B) Arrange-Act-Assert
- C) Act-Assert-Arrange
- D) Analyze-Act-Assert

---

### Question 27
**[JUnit]**

Which annotation is used to skip a test?

- A) @Skip
- B) @Ignore
- C) @Disabled
- D) @Exclude

---

### Question 28
**[JUnit]**

What is the purpose of @DisplayName?

- A) To name the test class
- B) To provide a custom name for the test in reports
- C) To set the display resolution
- D) To name the test file

---

### Question 29
**[JUnit]**

Which assertion checks that a value is not null?

- A) assertExists()
- B) assertNotNull()
- C) assertDefined()
- D) assertPresent()

---

### Question 30
**[JUnit]**

What is the Maven artifact ID for JUnit 5?

- A) junit
- B) junit5
- C) junit-jupiter
- D) junit-platform

---

### Question 31
**[JUnit]**

Which method groups multiple assertions together?

- A) assertGroup()
- B) assertMultiple()
- C) assertAll()
- D) assertBatch()

---

### Question 32
**[JUnit]**

@BeforeAll and @AfterAll methods must be:

- A) public
- B) private
- C) static
- D) final

---

### Question 33
**[JUnit]**

What is a unit test?

- A) A test that tests the entire application
- B) A test that tests a single unit of code in isolation
- C) A test that tests the database
- D) A test that tests the UI

---

### Question 34
**[JUnit]**

Which level of the testing pyramid should have the most tests?

- A) E2E tests
- B) Integration tests
- C) Unit tests
- D) Manual tests

---

### Question 35
**[JUnit]**

What does assertTrue(condition) verify?

- A) The condition is false
- B) The condition is true
- C) The condition is null
- D) The condition is not null

---

### Question 36
**[JUnit]**

What is the purpose of assertDoesNotThrow()?

- A) To verify an exception is thrown
- B) To verify no exception is thrown
- C) To catch any exception
- D) To ignore exceptions

---

### Question 37
**[JUnit]**

Which is the correct way to test for expected exception messages?

- A) assertEquals(exception.getMessage(), "expected")
- B) The return value of assertThrows() can be used to check the message
- C) @ExpectedException annotation
- D) try-catch block only

---

### Question 38
**[JUnit]**

What naming convention is commonly used for test classes?

- A) TestClassName
- B) ClassNameTest
- C) _ClassNameTest
- D) ClassName_Test

---

### Question 39
**[JUnit]**

Where should test files be placed in a Maven project?

- A) src/main/java
- B) src/test/java
- C) test/java
- D) src/tests

---

### Question 40
**[JUnit]**

What does assertSame() check?

- A) Equal values
- B) Same object reference
- C) Same type
- D) Same class

---

### Question 41
**[JUnit]**

Which assertion compares floating-point numbers with a delta?

- A) assertEquals(expected, actual, delta)
- B) assertFloatEquals()
- C) assertApproximate()
- D) assertClose()

---

### Question 42
**[JUnit]**

What is the purpose of @Nested in JUnit 5?

- A) To create nested loops
- B) To group related tests in inner classes
- C) To nest assertions
- D) To create test dependencies

---

### Question 43
**[JUnit]**

How do you run parameterized tests in JUnit 5?

- A) @Parameters annotation
- B) @ParameterizedTest annotation
- C) @ValueSource only
- D) @TestWithParams annotation

---

### Question 44
**[JUnit]**

What happens when an assertion fails?

- A) The test continues
- B) The test fails immediately
- C) An exception is logged
- D) The test is skipped

---

### Question 45
**[JUnit]**

Which is NOT a valid source for @ParameterizedTest?

- A) @ValueSource
- B) @CsvSource
- C) @MethodSource
- D) @DatabaseSource

---

## Mockito Framework

### Question 46
**[Mockito]**

What is Mockito primarily used for?

- A) Database testing
- B) Creating mock objects for unit testing
- C) UI testing
- D) Performance testing

---

### Question 47
**[Mockito]**

Which annotation creates a mock object?

- A) @MockObject
- B) @Mock
- C) @Fake
- D) @Stub

---

### Question 48
**[Mockito]**

What does @InjectMocks do?

- A) Injects data into the database
- B) Creates the object under test and injects mocks into it
- C) Injects dependencies manually
- D) Creates mock objects

---

### Question 49
**[Mockito]**

Which method is used to define mock behavior?

- A) mock().return()
- B) when().thenReturn()
- C) stub().returns()
- D) define().return()

---

### Question 50
**[Mockito]**

How do you verify a method was called on a mock?

- A) check()
- B) verify()
- C) assert()
- D) confirm()

---

### Question 51
**[Mockito]**

What is the extension class needed to enable Mockito in JUnit 5?

- A) MockitoRunner
- B) MockitoJUnitRunner
- C) MockitoExtension
- D) MockitoPlugin

---

### Question 52
**[Mockito]**

Which matcher matches any integer value?

- A) any()
- B) anyInt()
- C) matchInt()
- D) integer()

---

### Question 53
**[Mockito]**

What does verify(mock, times(2)).method() check?

- A) The method was called at least twice
- B) The method was called exactly twice
- C) The method was called at most twice
- D) The method takes 2 arguments

---

### Question 54
**[Mockito]**

What is the difference between @Mock and @Spy?

- A) No difference
- B) @Spy wraps a real object, @Mock creates a fake
- C) @Mock wraps a real object, @Spy creates a fake
- D) @Spy is for static methods

---

### Question 55
**[Mockito]**

Which matcher matches any String?

- A) anyString()
- B) string()
- C) matchString()
- D) any(String.class)

---

### Question 56
**[Mockito]**

What does when(mock.method()).thenThrow(Exception.class) do?

- A) Returns an exception
- B) Throws an exception when the method is called
- C) Catches an exception
- D) Logs an exception

---

### Question 57
**[Mockito]**

If you use matchers for one argument, what must you do for other arguments?

- A) Nothing special
- B) Use matchers for all arguments
- C) Use null for others
- D) Use eq() only

---

### Question 58
**[Mockito]**

What does verify(mock, never()).method() check?

- A) The method was called once
- B) The method was never called
- C) The method should never be called
- D) The method will not be called

---

### Question 59
**[Mockito]**

What is an ArgumentCaptor used for?

- A) To capture screenshots
- B) To capture arguments passed to mock methods
- C) To capture exceptions
- D) To capture return values

---

### Question 60
**[Mockito]**

What does doReturn().when() syntax allow that when().thenReturn() doesn't?

- A) Nothing, they're identical
- B) Stubbing void methods and spies without calling the real method
- C) Better performance
- D) More readable code

---

### Question 61
**[Mockito]**

Which verification mode checks a method was called at least 3 times?

- A) times(3)
- B) atLeast(3)
- C) minimum(3)
- D) min(3)

---

### Question 62
**[Mockito]**

What is the default return value for a mock returning an object?

- A) An empty object
- B) null
- C) A default instance
- D) Throws exception

---

### Question 63
**[Mockito]**

How do you stub a void method to throw an exception?

- A) when(mock.method()).thenThrow()
- B) doThrow().when(mock).method()
- C) mock.method().throws()
- D) throw().when(mock.method())

---

### Question 64
**[Mockito]**

What does @Captor annotation do?

- A) Captures test results
- B) Creates an ArgumentCaptor
- C) Captures mock objects
- D) Captures test duration

---

### Question 65
**[Mockito]**

Which import is needed for argument matchers?

- A) org.mockito.Matchers
- B) org.mockito.ArgumentMatchers
- C) org.mockito.Args
- D) org.mockito.Match

---

## Logging Frameworks

### Question 66
**[Logging]**

Which is NOT a standard log level in Log4J?

- A) DEBUG
- B) INFO
- C) VERBOSE
- D) ERROR

---

### Question 67
**[Logging]**

What is the correct log level hierarchy from least to most severe?

- A) DEBUG, INFO, WARN, ERROR, FATAL
- B) TRACE, DEBUG, INFO, WARN, ERROR, FATAL
- C) INFO, DEBUG, WARN, ERROR
- D) ERROR, WARN, INFO, DEBUG

---

### Question 68
**[Logging]**

What is an appender in Log4J?

- A) A log message formatter
- B) A component that defines where logs are written
- C) A log level filter
- D) A log file compressor

---

### Question 69
**[Logging]**

Which appender writes logs to the console?

- A) FileAppender
- B) ConsoleAppender
- C) OutputAppender
- D) TerminalAppender

---

### Question 70
**[Logging]**

What is SLF4J?

- A) A logging implementation
- B) A logging facade/abstraction
- C) A log file format
- D) A log viewer

---

### Question 71
**[Logging]**

What is the advantage of using parameterized logging?

- A) Prettier output
- B) Avoids string concatenation if log level is disabled
- C) Required by Log4J
- D) Supports more data types

---

### Question 72
**[Logging]**

Which pattern displays the log level in a PatternLayout?

- A) %l
- B) %level or %p
- C) %lv
- D) %severity

---

### Question 73
**[Logging]**

What does RollingFileAppender do?

- A) Rotates log files based on size or time
- B) Rolls back log entries
- C) Creates round-robin log files
- D) Compresses log files

---

### Question 74
**[Logging]**

What is the purpose of MDC (Mapped Diagnostic Context)?

- A) To map log files to directories
- B) To add contextual information to log entries
- C) To diagnose logging issues
- D) To create log message maps

---

### Question 75
**[Logging]**

Which log level should be used for important business events in production?

- A) DEBUG
- B) TRACE
- C) INFO
- D) WARN

---

### Question 76
**[Logging]**

What is the default configuration file name for Log4J 2?

- A) log4j.properties
- B) log4j2.xml
- C) logging.xml
- D) logger.properties

---

### Question 77
**[Logging]**

Which pattern displays the date/time in a PatternLayout?

- A) %t
- B) %d
- C) %date
- D) %time

---

### Question 78
**[Logging]**

What is asynchronous logging?

- A) Logging to remote servers
- B) Logging on a background thread for better performance
- C) Logging at scheduled intervals
- D) Logging in reverse order

---

### Question 79
**[Logging]**

Why should you NOT log sensitive data like passwords?

- A) It slows down logging
- B) It's a security risk
- C) Log files would be too large
- D) It's not supported

---

### Question 80
**[Logging]**

What is the correct way to log with SLF4J?

- A) Logger.log("message");
- B) logger.info("User {} logged in", username);
- C) System.out.println("message");
- D) Log.write("message");

---

## End of Questions

**Total: 80 Questions**
- Reactive Programming: 20
- JUnit Testing: 25
- Mockito Framework: 20
- Logging Frameworks: 15

---

*Proceed to `mcq-answers.md` for answers and explanations.*

# Exercise Set 7: Classes and Access Modifiers

Master TypeScript classes with encapsulation, inheritance, and decorators.

---

## Exercise 7.1: Basic Class Syntax

Create classes with proper TypeScript syntax.

```typescript
// TODO: Create these classes

// 1. Person class
//    - Properties: firstName, lastName, age
//    - Constructor with all properties
//    - Method: getFullName() returns string
//    - Method: isAdult() returns boolean

// 2. Rectangle class
//    - Properties: width, height
//    - Constructor with width and height
//    - Method: getArea() returns number
//    - Method: getPerimeter() returns number
//    - Getter: aspectRatio

// 3. Counter class
//    - Property: count (starts at 0)
//    - Method: increment()
//    - Method: decrement()
//    - Method: reset()
//    - Getter: value
```

<details>
<summary>Solution</summary>

```typescript
// 1. Person class
class Person {
    firstName: string;
    lastName: string;
    age: number;

    constructor(firstName: string, lastName: string, age: number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    getFullName(): string {
        return `${this.firstName} ${this.lastName}`;
    }

    isAdult(): boolean {
        return this.age >= 18;
    }
}

const person = new Person("Alice", "Smith", 25);
console.log(person.getFullName());  // "Alice Smith"
console.log(person.isAdult());      // true

// 2. Rectangle class
class Rectangle {
    constructor(
        public width: number,
        public height: number
    ) {}

    getArea(): number {
        return this.width * this.height;
    }

    getPerimeter(): number {
        return 2 * (this.width + this.height);
    }

    get aspectRatio(): number {
        return this.width / this.height;
    }
}

const rect = new Rectangle(10, 5);
console.log(rect.getArea());       // 50
console.log(rect.aspectRatio);     // 2

// 3. Counter class
class Counter {
    private count: number = 0;

    increment(): void {
        this.count++;
    }

    decrement(): void {
        this.count--;
    }

    reset(): void {
        this.count = 0;
    }

    get value(): number {
        return this.count;
    }
}

const counter = new Counter();
counter.increment();
counter.increment();
console.log(counter.value);  // 2
counter.reset();
console.log(counter.value);  // 0
```

</details>

---

## Exercise 7.2: Access Modifiers

Practice using public, private, protected, and readonly.

```typescript
// TODO: Add appropriate access modifiers

class BankAccount {
    accountNumber: string;       // Should not change after creation
    ownerName: string;           // Can be changed
    balance: number;             // Should only be modified by class methods
    transactionHistory: string[];// Should only be accessible within class

    constructor(accountNumber: string, ownerName: string, initialBalance: number) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialBalance;
        this.transactionHistory = [];
    }

    deposit(amount: number): void {
        if (amount > 0) {
            this.balance += amount;
            this.logTransaction(`Deposit: +$${amount}`);
        }
    }

    withdraw(amount: number): boolean {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            this.logTransaction(`Withdrawal: -$${amount}`);
            return true;
        }
        return false;
    }

    logTransaction(message: string): void {
        this.transactionHistory.push(`${new Date().toISOString()}: ${message}`);
    }

    getBalance(): number {
        return this.balance;
    }
}

// This should cause errors after you add access modifiers:
const account = new BankAccount("123456", "Alice", 1000);
// account.balance = 1000000;  // Should error: balance is private
// account.accountNumber = "999";  // Should error: accountNumber is readonly
// account.transactionHistory;  // Should error: transactionHistory is private
```

<details>
<summary>Solution</summary>

```typescript
class BankAccount {
    readonly accountNumber: string;
    public ownerName: string;
    private balance: number;
    private transactionHistory: string[];

    constructor(accountNumber: string, ownerName: string, initialBalance: number) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialBalance;
        this.transactionHistory = [];
    }

    public deposit(amount: number): void {
        if (amount > 0) {
            this.balance += amount;
            this.logTransaction(`Deposit: +$${amount}`);
        }
    }

    public withdraw(amount: number): boolean {
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            this.logTransaction(`Withdrawal: -$${amount}`);
            return true;
        }
        return false;
    }

    private logTransaction(message: string): void {
        this.transactionHistory.push(`${new Date().toISOString()}: ${message}`);
    }

    public getBalance(): number {
        return this.balance;
    }

    public getTransactionCount(): number {
        return this.transactionHistory.length;
    }
}

const account = new BankAccount("123456", "Alice", 1000);

// These work:
account.ownerName = "Alice Smith";  // public, can modify
console.log(account.accountNumber);  // readonly, can read
account.deposit(500);
console.log(account.getBalance());   // 1500

// These would cause errors:
// account.balance = 1000000;        // Error: private
// account.accountNumber = "999";    // Error: readonly
// account.transactionHistory;       // Error: private
// account.logTransaction("hack");   // Error: private
```

</details>

---

## Exercise 7.3: Inheritance

Create class hierarchies using extends.

```typescript
// TODO: Create this class hierarchy

// Base class: Animal
//   - Properties: name, age
//   - Method: makeSound() - abstract
//   - Method: describe() returns string

// Derived class: Dog extends Animal
//   - Property: breed
//   - Override: makeSound() returns "Woof!"
//   - Method: fetch() returns string

// Derived class: Cat extends Animal
//   - Property: isIndoor
//   - Override: makeSound() returns "Meow!"
//   - Method: scratch() returns string

// Derived class: Bird extends Animal
//   - Property: canFly
//   - Override: makeSound() returns "Tweet!"
//   - Method: fly() returns string (only if canFly is true)

// Create instances and test polymorphism
const animals: Animal[] = [
    new Dog("Buddy", 3, "Golden Retriever"),
    new Cat("Whiskers", 5, true),
    new Bird("Tweety", 1, true)
];

animals.forEach(animal => console.log(animal.makeSound()));
```

<details>
<summary>Solution</summary>

```typescript
// Base class: Animal
abstract class Animal {
    constructor(
        public name: string,
        public age: number
    ) {}

    abstract makeSound(): string;

    describe(): string {
        return `${this.name} is ${this.age} years old`;
    }
}

// Derived class: Dog
class Dog extends Animal {
    constructor(
        name: string,
        age: number,
        public breed: string
    ) {
        super(name, age);
    }

    makeSound(): string {
        return "Woof!";
    }

    fetch(): string {
        return `${this.name} fetches the ball!`;
    }

    describe(): string {
        return `${super.describe()} and is a ${this.breed}`;
    }
}

// Derived class: Cat
class Cat extends Animal {
    constructor(
        name: string,
        age: number,
        public isIndoor: boolean
    ) {
        super(name, age);
    }

    makeSound(): string {
        return "Meow!";
    }

    scratch(): string {
        return `${this.name} scratches the furniture!`;
    }
}

// Derived class: Bird
class Bird extends Animal {
    constructor(
        name: string,
        age: number,
        public canFly: boolean
    ) {
        super(name, age);
    }

    makeSound(): string {
        return "Tweet!";
    }

    fly(): string {
        if (this.canFly) {
            return `${this.name} soars through the sky!`;
        }
        return `${this.name} cannot fly.`;
    }
}

// Create instances and test polymorphism
const animals: Animal[] = [
    new Dog("Buddy", 3, "Golden Retriever"),
    new Cat("Whiskers", 5, true),
    new Bird("Tweety", 1, true)
];

animals.forEach(animal => {
    console.log(animal.describe());
    console.log(animal.makeSound());
});

// Type-specific operations
const dog = animals[0] as Dog;
console.log(dog.fetch());

const bird = animals[2] as Bird;
console.log(bird.fly());
```

</details>

---

## Exercise 7.4: Interfaces with Classes

Implement interfaces in classes.

```typescript
// TODO: Create interfaces and implementing classes

// 1. Interface: Serializable
//    - serialize(): string
//    - deserialize(data: string): void

// 2. Interface: Comparable<T>
//    - compareTo(other: T): number

// 3. Interface: Cloneable<T>
//    - clone(): T

// 4. Create a Task class that implements all three
interface Task {
    id: string;
    title: string;
    priority: number;
    completed: boolean;
}

class TaskItem implements Serializable, Comparable<TaskItem>, Cloneable<TaskItem> {
    // TODO: Implement all interfaces
}

// Test:
const task1 = new TaskItem("1", "Learn TypeScript", 1, false);
const task2 = new TaskItem("2", "Build Project", 2, false);

console.log(task1.compareTo(task2));  // -1 (lower priority)
console.log(task1.serialize());        // JSON string
const cloned = task1.clone();
```

<details>
<summary>Solution</summary>

```typescript
// 1. Serializable interface
interface Serializable {
    serialize(): string;
    deserialize(data: string): void;
}

// 2. Comparable interface
interface Comparable<T> {
    compareTo(other: T): number;
}

// 3. Cloneable interface
interface Cloneable<T> {
    clone(): T;
}

// 4. TaskItem implementing all interfaces
class TaskItem implements Serializable, Comparable<TaskItem>, Cloneable<TaskItem> {
    constructor(
        public id: string,
        public title: string,
        public priority: number,
        public completed: boolean
    ) {}

    // Serializable
    serialize(): string {
        return JSON.stringify({
            id: this.id,
            title: this.title,
            priority: this.priority,
            completed: this.completed
        });
    }

    deserialize(data: string): void {
        const parsed = JSON.parse(data);
        this.id = parsed.id;
        this.title = parsed.title;
        this.priority = parsed.priority;
        this.completed = parsed.completed;
    }

    // Comparable
    compareTo(other: TaskItem): number {
        // Compare by priority (lower number = higher priority)
        if (this.priority !== other.priority) {
            return this.priority - other.priority;
        }
        // Then by title
        return this.title.localeCompare(other.title);
    }

    // Cloneable
    clone(): TaskItem {
        return new TaskItem(
            this.id,
            this.title,
            this.priority,
            this.completed
        );
    }

    // Additional utility methods
    toString(): string {
        const status = this.completed ? "✓" : "○";
        return `[${status}] (${this.priority}) ${this.title}`;
    }
}

// Test
const task1 = new TaskItem("1", "Learn TypeScript", 1, false);
const task2 = new TaskItem("2", "Build Project", 2, false);

console.log(task1.compareTo(task2));  // -1 (task1 has higher priority)
console.log(task1.serialize());
// {"id":"1","title":"Learn TypeScript","priority":1,"completed":false}

const cloned = task1.clone();
cloned.completed = true;
console.log(task1.completed);  // false (original unchanged)
console.log(cloned.completed); // true

// Deserialize example
const newTask = new TaskItem("", "", 0, false);
newTask.deserialize('{"id":"3","title":"Review Code","priority":1,"completed":true}');
console.log(newTask.toString());  // [✓] (1) Review Code
```

</details>

---

## Exercise 7.5: Static Members

Work with static properties and methods.

```typescript
// TODO: Add static members to these classes

// 1. MathUtils class with static methods
class MathUtils {
    // Static constants: PI, E
    // Static methods: add, multiply, power, factorial
    // No instance methods needed
}

// 2. IdGenerator class
class IdGenerator {
    // Static counter that increments
    // Static method: generate() returns unique ID string
    // Static method: reset()
}

// 3. Logger class with log levels
class Logger {
    // Static log level (debug, info, warn, error)
    // Static methods: debug, info, warn, error
    // Each method only logs if level is appropriate
}

// 4. Configuration singleton
class Config {
    // Private static instance
    // Static getInstance() method
    // Instance methods: get, set
}

// Test:
console.log(MathUtils.PI);
console.log(IdGenerator.generate());  // "ID-1"
console.log(IdGenerator.generate());  // "ID-2"
Logger.setLevel("warn");
Logger.debug("This won't show");
Logger.warn("This will show");
```

<details>
<summary>Solution</summary>

```typescript
// 1. MathUtils class
class MathUtils {
    static readonly PI: number = 3.14159265359;
    static readonly E: number = 2.71828182846;

    private constructor() {}  // Prevent instantiation

    static add(a: number, b: number): number {
        return a + b;
    }

    static multiply(a: number, b: number): number {
        return a * b;
    }

    static power(base: number, exponent: number): number {
        return Math.pow(base, exponent);
    }

    static factorial(n: number): number {
        if (n <= 1) return 1;
        return n * MathUtils.factorial(n - 1);
    }
}

// 2. IdGenerator class
class IdGenerator {
    private static counter: number = 0;
    private static prefix: string = "ID";

    private constructor() {}

    static generate(): string {
        IdGenerator.counter++;
        return `${IdGenerator.prefix}-${IdGenerator.counter}`;
    }

    static generateWithPrefix(prefix: string): string {
        IdGenerator.counter++;
        return `${prefix}-${IdGenerator.counter}`;
    }

    static reset(): void {
        IdGenerator.counter = 0;
    }

    static setPrefix(prefix: string): void {
        IdGenerator.prefix = prefix;
    }
}

// 3. Logger class
type LogLevel = "debug" | "info" | "warn" | "error";

class Logger {
    private static level: LogLevel = "info";
    private static readonly levels: Record<LogLevel, number> = {
        debug: 0,
        info: 1,
        warn: 2,
        error: 3
    };

    private constructor() {}

    static setLevel(level: LogLevel): void {
        Logger.level = level;
    }

    private static shouldLog(level: LogLevel): boolean {
        return Logger.levels[level] >= Logger.levels[Logger.level];
    }

    static debug(message: string): void {
        if (Logger.shouldLog("debug")) {
            console.log(`[DEBUG] ${message}`);
        }
    }

    static info(message: string): void {
        if (Logger.shouldLog("info")) {
            console.log(`[INFO] ${message}`);
        }
    }

    static warn(message: string): void {
        if (Logger.shouldLog("warn")) {
            console.warn(`[WARN] ${message}`);
        }
    }

    static error(message: string): void {
        if (Logger.shouldLog("error")) {
            console.error(`[ERROR] ${message}`);
        }
    }
}

// 4. Configuration singleton
class Config {
    private static instance: Config | null = null;
    private settings: Map<string, unknown> = new Map();

    private constructor() {}

    static getInstance(): Config {
        if (Config.instance === null) {
            Config.instance = new Config();
        }
        return Config.instance;
    }

    get<T>(key: string): T | undefined {
        return this.settings.get(key) as T | undefined;
    }

    set<T>(key: string, value: T): void {
        this.settings.set(key, value);
    }

    has(key: string): boolean {
        return this.settings.has(key);
    }
}

// Test
console.log(MathUtils.PI);              // 3.14159265359
console.log(MathUtils.factorial(5));    // 120

console.log(IdGenerator.generate());    // "ID-1"
console.log(IdGenerator.generate());    // "ID-2"
IdGenerator.setPrefix("USER");
console.log(IdGenerator.generate());    // "USER-3"

Logger.setLevel("warn");
Logger.debug("This won't show");
Logger.info("This won't show");
Logger.warn("This will show");          // [WARN] This will show
Logger.error("This will show");         // [ERROR] This will show

const config = Config.getInstance();
config.set("apiUrl", "https://api.example.com");
console.log(config.get<string>("apiUrl"));

const sameConfig = Config.getInstance();
console.log(config === sameConfig);     // true (same instance)
```

</details>

---

## Exercise 7.6: Abstract Classes

Create abstract base classes with template patterns.

```typescript
// TODO: Create abstract class hierarchy

// Abstract class: DataProcessor<T>
//   - Abstract method: parse(raw: string): T
//   - Abstract method: validate(data: T): boolean
//   - Abstract method: transform(data: T): T
//   - Concrete method: process(raw: string): T (template method)
//     1. Parse raw data
//     2. Validate parsed data
//     3. Transform valid data
//     4. Return result

// Concrete class: JsonProcessor
//   - Parses JSON strings
//   - Validates required fields exist
//   - Transforms by adding metadata

// Concrete class: CsvProcessor
//   - Parses CSV strings into objects
//   - Validates row count and column count
//   - Transforms by trimming whitespace

// Test both processors
```

<details>
<summary>Solution</summary>

```typescript
// Abstract base class
abstract class DataProcessor<T> {
    // Abstract methods to be implemented by subclasses
    protected abstract parse(raw: string): T;
    protected abstract validate(data: T): boolean;
    protected abstract transform(data: T): T;

    // Template method - defines the algorithm
    public process(raw: string): T {
        console.log("Starting data processing...");

        // Step 1: Parse
        const parsed = this.parse(raw);
        console.log("Data parsed successfully");

        // Step 2: Validate
        if (!this.validate(parsed)) {
            throw new Error("Validation failed");
        }
        console.log("Data validated successfully");

        // Step 3: Transform
        const transformed = this.transform(parsed);
        console.log("Data transformed successfully");

        return transformed;
    }
}

// JSON Processor
interface JsonData {
    id?: string;
    name?: string;
    [key: string]: unknown;
}

interface ProcessedJsonData extends JsonData {
    _metadata: {
        processedAt: string;
        version: string;
    };
}

class JsonProcessor extends DataProcessor<ProcessedJsonData> {
    private requiredFields: string[];

    constructor(requiredFields: string[] = ["id", "name"]) {
        super();
        this.requiredFields = requiredFields;
    }

    protected parse(raw: string): ProcessedJsonData {
        try {
            const parsed = JSON.parse(raw);
            return {
                ...parsed,
                _metadata: {
                    processedAt: "",
                    version: ""
                }
            };
        } catch {
            throw new Error("Invalid JSON format");
        }
    }

    protected validate(data: ProcessedJsonData): boolean {
        for (const field of this.requiredFields) {
            if (!(field in data) || data[field] === undefined) {
                console.log(`Missing required field: ${field}`);
                return false;
            }
        }
        return true;
    }

    protected transform(data: ProcessedJsonData): ProcessedJsonData {
        return {
            ...data,
            _metadata: {
                processedAt: new Date().toISOString(),
                version: "1.0"
            }
        };
    }
}

// CSV Processor
interface CsvRow {
    [key: string]: string;
}

interface CsvData {
    headers: string[];
    rows: CsvRow[];
}

class CsvProcessor extends DataProcessor<CsvData> {
    private expectedColumns: number;
    private minRows: number;

    constructor(expectedColumns: number = 0, minRows: number = 1) {
        super();
        this.expectedColumns = expectedColumns;
        this.minRows = minRows;
    }

    protected parse(raw: string): CsvData {
        const lines = raw.trim().split("\n");
        if (lines.length === 0) {
            throw new Error("Empty CSV");
        }

        const headers = lines[0].split(",").map(h => h.trim());
        const rows: CsvRow[] = [];

        for (let i = 1; i < lines.length; i++) {
            const values = lines[i].split(",");
            const row: CsvRow = {};

            headers.forEach((header, index) => {
                row[header] = values[index] || "";
            });

            rows.push(row);
        }

        return { headers, rows };
    }

    protected validate(data: CsvData): boolean {
        if (this.expectedColumns > 0 && data.headers.length !== this.expectedColumns) {
            console.log(`Expected ${this.expectedColumns} columns, got ${data.headers.length}`);
            return false;
        }

        if (data.rows.length < this.minRows) {
            console.log(`Expected at least ${this.minRows} rows, got ${data.rows.length}`);
            return false;
        }

        return true;
    }

    protected transform(data: CsvData): CsvData {
        const trimmedRows = data.rows.map(row => {
            const trimmed: CsvRow = {};
            for (const key in row) {
                trimmed[key] = row[key].trim();
            }
            return trimmed;
        });

        return {
            headers: data.headers.map(h => h.trim()),
            rows: trimmedRows
        };
    }
}

// Test JSON Processor
const jsonProcessor = new JsonProcessor();
const jsonResult = jsonProcessor.process('{"id": "123", "name": "Widget", "price": 29.99}');
console.log(jsonResult);

// Test CSV Processor
const csvProcessor = new CsvProcessor(3, 1);
const csvData = `name, age, city
Alice, 30, NYC
Bob, 25, LA`;
const csvResult = csvProcessor.process(csvData);
console.log(csvResult);
```

</details>

---

## Challenge: Plugin System

Build a type-safe plugin system using classes and interfaces.

```typescript
// TODO: Build the plugin system

// Plugin interface
interface Plugin<TConfig = unknown> {
    name: string;
    version: string;
    initialize(config: TConfig): Promise<void>;
    execute(context: PluginContext): Promise<void>;
    cleanup(): Promise<void>;
}

// Plugin context passed to execute
interface PluginContext {
    logger: Logger;
    storage: Storage;
    emit(event: string, data: unknown): void;
}

// Plugin manager class
class PluginManager {
    // Register plugins
    // Initialize all plugins
    // Execute plugins in order
    // Handle errors gracefully
    // Cleanup on shutdown
}

// Create example plugins:
// 1. LoggerPlugin - logs all events
// 2. CachePlugin - caches data with TTL
// 3. MetricsPlugin - tracks execution time

// Test the system
const manager = new PluginManager();

manager.register(new LoggerPlugin({ level: "debug" }));
manager.register(new CachePlugin({ ttl: 60000 }));
manager.register(new MetricsPlugin());

await manager.initialize();
await manager.execute();
await manager.shutdown();
```

<details>
<summary>Solution</summary>

```typescript
// Logger interface (simplified)
interface ILogger {
    debug(message: string): void;
    info(message: string): void;
    warn(message: string): void;
    error(message: string): void;
}

// Storage interface
interface IStorage {
    get<T>(key: string): T | undefined;
    set<T>(key: string, value: T): void;
    delete(key: string): boolean;
    clear(): void;
}

// Plugin context
interface PluginContext {
    logger: ILogger;
    storage: IStorage;
    emit(event: string, data: unknown): void;
}

// Base plugin interface
interface Plugin<TConfig = unknown> {
    readonly name: string;
    readonly version: string;
    initialize(config: TConfig): Promise<void>;
    execute(context: PluginContext): Promise<void>;
    cleanup(): Promise<void>;
}

// Abstract base plugin class
abstract class BasePlugin<TConfig> implements Plugin<TConfig> {
    abstract readonly name: string;
    abstract readonly version: string;
    protected config!: TConfig;
    protected initialized: boolean = false;

    async initialize(config: TConfig): Promise<void> {
        this.config = config;
        await this.onInitialize();
        this.initialized = true;
    }

    abstract execute(context: PluginContext): Promise<void>;

    async cleanup(): Promise<void> {
        await this.onCleanup();
        this.initialized = false;
    }

    protected async onInitialize(): Promise<void> {}
    protected async onCleanup(): Promise<void> {}
}

// Console Logger implementation
class ConsoleLogger implements ILogger {
    constructor(private level: "debug" | "info" | "warn" | "error" = "info") {}

    debug(message: string): void {
        if (this.level === "debug") console.log(`[DEBUG] ${message}`);
    }
    info(message: string): void {
        console.log(`[INFO] ${message}`);
    }
    warn(message: string): void {
        console.warn(`[WARN] ${message}`);
    }
    error(message: string): void {
        console.error(`[ERROR] ${message}`);
    }
}

// In-memory storage implementation
class MemoryStorage implements IStorage {
    private data: Map<string, unknown> = new Map();

    get<T>(key: string): T | undefined {
        return this.data.get(key) as T | undefined;
    }
    set<T>(key: string, value: T): void {
        this.data.set(key, value);
    }
    delete(key: string): boolean {
        return this.data.delete(key);
    }
    clear(): void {
        this.data.clear();
    }
}

// Plugin Manager
class PluginManager {
    private plugins: Plugin<unknown>[] = [];
    private context: PluginContext;
    private eventHandlers: Map<string, ((data: unknown) => void)[]> = new Map();

    constructor() {
        this.context = {
            logger: new ConsoleLogger("debug"),
            storage: new MemoryStorage(),
            emit: (event, data) => this.emit(event, data)
        };
    }

    register<TConfig>(plugin: Plugin<TConfig>, config: TConfig): void {
        this.plugins.push(plugin as Plugin<unknown>);
        this.context.logger.info(`Registered plugin: ${plugin.name} v${plugin.version}`);
    }

    on(event: string, handler: (data: unknown) => void): void {
        if (!this.eventHandlers.has(event)) {
            this.eventHandlers.set(event, []);
        }
        this.eventHandlers.get(event)!.push(handler);
    }

    private emit(event: string, data: unknown): void {
        const handlers = this.eventHandlers.get(event) || [];
        handlers.forEach(handler => handler(data));
    }

    async initialize(): Promise<void> {
        this.context.logger.info("Initializing plugins...");

        for (const plugin of this.plugins) {
            try {
                await plugin.initialize({});
                this.context.logger.info(`Initialized: ${plugin.name}`);
            } catch (error) {
                this.context.logger.error(`Failed to initialize ${plugin.name}: ${error}`);
            }
        }
    }

    async execute(): Promise<void> {
        this.context.logger.info("Executing plugins...");

        for (const plugin of this.plugins) {
            try {
                await plugin.execute(this.context);
            } catch (error) {
                this.context.logger.error(`Error in ${plugin.name}: ${error}`);
            }
        }
    }

    async shutdown(): Promise<void> {
        this.context.logger.info("Shutting down plugins...");

        // Cleanup in reverse order
        for (const plugin of [...this.plugins].reverse()) {
            try {
                await plugin.cleanup();
                this.context.logger.info(`Cleaned up: ${plugin.name}`);
            } catch (error) {
                this.context.logger.error(`Error cleaning up ${plugin.name}: ${error}`);
            }
        }
    }
}

// Example Plugins

// 1. Logger Plugin
interface LoggerPluginConfig {
    level: "debug" | "info" | "warn" | "error";
}

class LoggerPlugin extends BasePlugin<LoggerPluginConfig> {
    readonly name = "LoggerPlugin";
    readonly version = "1.0.0";

    async execute(context: PluginContext): Promise<void> {
        context.logger.info("LoggerPlugin executed");
        context.emit("log", { message: "Logger is active" });
    }
}

// 2. Cache Plugin
interface CachePluginConfig {
    ttl: number;
}

class CachePlugin extends BasePlugin<CachePluginConfig> {
    readonly name = "CachePlugin";
    readonly version = "1.0.0";
    private cache: Map<string, { value: unknown; expires: number }> = new Map();

    async execute(context: PluginContext): Promise<void> {
        context.logger.info(`CachePlugin running with TTL: ${this.config.ttl}ms`);

        // Store something in cache
        this.set("example", { data: "cached value" });
        context.emit("cache", { action: "set", key: "example" });
    }

    set(key: string, value: unknown): void {
        this.cache.set(key, {
            value,
            expires: Date.now() + this.config.ttl
        });
    }

    get(key: string): unknown | undefined {
        const entry = this.cache.get(key);
        if (!entry) return undefined;
        if (Date.now() > entry.expires) {
            this.cache.delete(key);
            return undefined;
        }
        return entry.value;
    }

    protected async onCleanup(): Promise<void> {
        this.cache.clear();
    }
}

// 3. Metrics Plugin
interface MetricsPluginConfig {
    prefix?: string;
}

class MetricsPlugin extends BasePlugin<MetricsPluginConfig> {
    readonly name = "MetricsPlugin";
    readonly version = "1.0.0";
    private metrics: Map<string, number> = new Map();
    private startTime: number = 0;

    protected async onInitialize(): Promise<void> {
        this.startTime = Date.now();
    }

    async execute(context: PluginContext): Promise<void> {
        const executionTime = Date.now() - this.startTime;
        this.metrics.set("executionTime", executionTime);

        context.logger.info(`MetricsPlugin: Execution time: ${executionTime}ms`);
        context.emit("metrics", {
            executionTime,
            timestamp: new Date().toISOString()
        });
    }

    getMetrics(): Record<string, number> {
        return Object.fromEntries(this.metrics);
    }
}

// Test the system
async function testPluginSystem() {
    const manager = new PluginManager();

    // Register event handlers
    manager.on("log", (data) => console.log("Log event:", data));
    manager.on("cache", (data) => console.log("Cache event:", data));
    manager.on("metrics", (data) => console.log("Metrics event:", data));

    // Register plugins
    manager.register(new LoggerPlugin(), { level: "debug" });
    manager.register(new CachePlugin(), { ttl: 60000 });
    manager.register(new MetricsPlugin(), { prefix: "app" });

    await manager.initialize();
    await manager.execute();
    await manager.shutdown();
}

testPluginSystem();
```

</details>

---

## Summary

You've learned:
- Basic class syntax and constructors
- Access modifiers (public, private, protected, readonly)
- Inheritance with extends
- Implementing interfaces
- Static members and singletons
- Abstract classes and template patterns

## Next Steps

Return to [TypeScript Fundamentals Exercise](../../submissions/20-typescript/EXERCISE.md) to apply all concepts in a complete project.

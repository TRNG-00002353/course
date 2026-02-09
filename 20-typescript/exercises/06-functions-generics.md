# Exercise Set 6: Functions and Generics

Master typed functions and generic programming.

---

## Exercise 6.1: Function Type Annotations

Add complete type annotations to functions.

```typescript
// TODO: Add full type annotations

// 1. Basic function types
function add(a, b) {
    return a + b;
}

function greet(name, greeting = "Hello") {
    return `${greeting}, ${name}!`;
}

function printAll(items) {
    items.forEach(item => console.log(item));
}

// 2. Arrow function types
const multiply = (a, b) => a * b;

const isEven = (n) => n % 2 === 0;

const getFirst = (arr) => arr[0];

// 3. Function type aliases
type MathOperation = // Define: takes two numbers, returns number

type Predicate = // Define: takes a value, returns boolean

type Transformer = // Define: takes string, returns string
```

<details>
<summary>Solution</summary>

```typescript
// 1. Basic function types
function add(a: number, b: number): number {
    return a + b;
}

function greet(name: string, greeting: string = "Hello"): string {
    return `${greeting}, ${name}!`;
}

function printAll(items: string[]): void {
    items.forEach(item => console.log(item));
}

// 2. Arrow function types
const multiply: (a: number, b: number) => number = (a, b) => a * b;

const isEven: (n: number) => boolean = (n) => n % 2 === 0;

const getFirst: <T>(arr: T[]) => T | undefined = (arr) => arr[0];

// 3. Function type aliases
type MathOperation = (a: number, b: number) => number;

type Predicate<T> = (value: T) => boolean;

type Transformer = (input: string) => string;

// Using the type aliases
const subtract: MathOperation = (a, b) => a - b;
const isPositive: Predicate<number> = (n) => n > 0;
const uppercase: Transformer = (s) => s.toUpperCase();
```

</details>

---

## Exercise 6.2: Basic Generics

Create generic functions.

```typescript
// TODO: Convert these to generic functions

// 1. Identity function - returns what it receives
function identity(value: any): any {
    return value;
}

// 2. Wrap in array
function wrapInArray(value: any): any[] {
    return [value];
}

// 3. Get first element
function first(array: any[]): any {
    return array[0];
}

// 4. Get last element
function last(array: any[]): any {
    return array[array.length - 1];
}

// 5. Reverse tuple (2 elements)
function reverseTuple(tuple: [any, any]): [any, any] {
    return [tuple[1], tuple[0]];
}

// 6. Create pair
function makePair(first: any, second: any): [any, any] {
    return [first, second];
}
```

<details>
<summary>Solution</summary>

```typescript
// 1. Identity function
function identity<T>(value: T): T {
    return value;
}

const str = identity("hello");  // type: string
const num = identity(42);       // type: number

// 2. Wrap in array
function wrapInArray<T>(value: T): T[] {
    return [value];
}

const strArray = wrapInArray("hello");  // type: string[]
const numArray = wrapInArray(42);       // type: number[]

// 3. Get first element
function first<T>(array: T[]): T | undefined {
    return array[0];
}

const firstNum = first([1, 2, 3]);      // type: number | undefined
const firstStr = first(["a", "b"]);     // type: string | undefined

// 4. Get last element
function last<T>(array: T[]): T | undefined {
    return array[array.length - 1];
}

// 5. Reverse tuple
function reverseTuple<T, U>(tuple: [T, U]): [U, T] {
    return [tuple[1], tuple[0]];
}

const reversed = reverseTuple([1, "hello"]);  // type: [string, number]

// 6. Create pair
function makePair<T, U>(first: T, second: U): [T, U] {
    return [first, second];
}

const pair = makePair("name", 42);  // type: [string, number]
```

</details>

---

## Exercise 6.3: Generic Constraints

Use extends to constrain generic types.

```typescript
// TODO: Add appropriate constraints

// 1. Get length - works with anything that has a length property
function getLength<T>(item: T): number {
    return item.length;  // Error without constraint!
}

// 2. Get property - T must be an object, K must be a key of T
function getProperty<T, K>(obj: T, key: K): T[K] {
    return obj[key];  // Error without constraint!
}

// 3. Merge objects - both must be objects
function merge<T, U>(obj1: T, obj2: U): T & U {
    return { ...obj1, ...obj2 };
}

// 4. Compare - T must have a compare method
interface Comparable {
    compare(other: this): number;
}

function findMax<T>(items: T[]): T | undefined {
    // Should use compare method
}

// 5. Clone - T must be an object with specific structure
function clone<T>(source: T): T {
    return JSON.parse(JSON.stringify(source));
}
```

<details>
<summary>Solution</summary>

```typescript
// 1. Get length - constrained to things with length
interface HasLength {
    length: number;
}

function getLength<T extends HasLength>(item: T): number {
    return item.length;
}

getLength("hello");      // 5
getLength([1, 2, 3]);    // 3
getLength({ length: 10 }); // 10
// getLength(42);        // Error: number doesn't have length

// 2. Get property - constrained to object keys
function getProperty<T extends object, K extends keyof T>(obj: T, key: K): T[K] {
    return obj[key];
}

const user = { name: "Alice", age: 30 };
const name = getProperty(user, "name");  // type: string
const age = getProperty(user, "age");    // type: number
// getProperty(user, "foo");              // Error: "foo" is not a key

// 3. Merge objects - constrained to objects
function merge<T extends object, U extends object>(obj1: T, obj2: U): T & U {
    return { ...obj1, ...obj2 };
}

const merged = merge({ a: 1 }, { b: 2 });  // type: { a: number } & { b: number }

// 4. Compare - constrained to Comparable
interface Comparable<T> {
    compare(other: T): number;
}

function findMax<T extends Comparable<T>>(items: T[]): T | undefined {
    if (items.length === 0) return undefined;

    return items.reduce((max, item) =>
        item.compare(max) > 0 ? item : max
    );
}

class Version implements Comparable<Version> {
    constructor(public major: number, public minor: number) {}

    compare(other: Version): number {
        if (this.major !== other.major) {
            return this.major - other.major;
        }
        return this.minor - other.minor;
    }
}

const versions = [new Version(1, 0), new Version(2, 1), new Version(1, 5)];
const latest = findMax(versions);  // Version 2.1

// 5. Clone - constrained to serializable objects
type Serializable = Record<string, unknown> | unknown[];

function clone<T extends Serializable>(source: T): T {
    return JSON.parse(JSON.stringify(source));
}

const original = { name: "Alice", scores: [1, 2, 3] };
const cloned = clone(original);
```

</details>

---

## Exercise 6.4: Generic Interfaces and Classes

Create generic interfaces and classes.

```typescript
// TODO: Implement these generic types

// 1. Generic Stack interface and implementation
interface Stack<T> {
    push(item: T): void;
    pop(): T | undefined;
    peek(): T | undefined;
    isEmpty(): boolean;
    size(): number;
}

class ArrayStack<T> implements Stack<T> {
    // TODO: Implement
}

// 2. Generic Result type for error handling
type Result<T, E = Error> = // Success or Failure

// Helper functions
function success<T>(data: T): Result<T, never> {
    // TODO
}

function failure<E>(error: E): Result<never, E> {
    // TODO
}

// 3. Generic Repository interface
interface Repository<T, ID> {
    findById(id: ID): Promise<T | null>;
    findAll(): Promise<T[]>;
    save(entity: T): Promise<T>;
    delete(id: ID): Promise<boolean>;
}

// 4. Generic EventEmitter
class EventEmitter<Events extends Record<string, unknown[]>> {
    // TODO: Implement typed event emitter
}
```

<details>
<summary>Solution</summary>

```typescript
// 1. Generic Stack
interface Stack<T> {
    push(item: T): void;
    pop(): T | undefined;
    peek(): T | undefined;
    isEmpty(): boolean;
    size(): number;
}

class ArrayStack<T> implements Stack<T> {
    private items: T[] = [];

    push(item: T): void {
        this.items.push(item);
    }

    pop(): T | undefined {
        return this.items.pop();
    }

    peek(): T | undefined {
        return this.items[this.items.length - 1];
    }

    isEmpty(): boolean {
        return this.items.length === 0;
    }

    size(): number {
        return this.items.length;
    }
}

// Usage
const numberStack = new ArrayStack<number>();
numberStack.push(1);
numberStack.push(2);
console.log(numberStack.pop());  // 2

// 2. Generic Result type
type Result<T, E = Error> =
    | { success: true; data: T }
    | { success: false; error: E };

function success<T>(data: T): Result<T, never> {
    return { success: true, data };
}

function failure<E>(error: E): Result<never, E> {
    return { success: false, error };
}

// Usage
function divide(a: number, b: number): Result<number, string> {
    if (b === 0) {
        return failure("Division by zero");
    }
    return success(a / b);
}

const result = divide(10, 2);
if (result.success) {
    console.log(result.data);  // 5
} else {
    console.log(result.error);
}

// 3. Generic Repository
interface Entity {
    id: string;
}

interface Repository<T extends Entity, ID = string> {
    findById(id: ID): Promise<T | null>;
    findAll(): Promise<T[]>;
    save(entity: T): Promise<T>;
    delete(id: ID): Promise<boolean>;
}

// Example implementation
interface User extends Entity {
    name: string;
    email: string;
}

class InMemoryUserRepository implements Repository<User> {
    private users: Map<string, User> = new Map();

    async findById(id: string): Promise<User | null> {
        return this.users.get(id) ?? null;
    }

    async findAll(): Promise<User[]> {
        return Array.from(this.users.values());
    }

    async save(entity: User): Promise<User> {
        this.users.set(entity.id, entity);
        return entity;
    }

    async delete(id: string): Promise<boolean> {
        return this.users.delete(id);
    }
}

// 4. Generic EventEmitter
type EventMap = Record<string, unknown[]>;
type EventHandler<T extends unknown[]> = (...args: T) => void;

class EventEmitter<Events extends EventMap> {
    private listeners: {
        [K in keyof Events]?: EventHandler<Events[K]>[];
    } = {};

    on<K extends keyof Events>(event: K, handler: EventHandler<Events[K]>): void {
        if (!this.listeners[event]) {
            this.listeners[event] = [];
        }
        this.listeners[event]!.push(handler);
    }

    off<K extends keyof Events>(event: K, handler: EventHandler<Events[K]>): void {
        const handlers = this.listeners[event];
        if (handlers) {
            const index = handlers.indexOf(handler);
            if (index > -1) {
                handlers.splice(index, 1);
            }
        }
    }

    emit<K extends keyof Events>(event: K, ...args: Events[K]): void {
        const handlers = this.listeners[event];
        if (handlers) {
            handlers.forEach(handler => handler(...args));
        }
    }
}

// Usage
interface AppEvents {
    login: [userId: string, timestamp: Date];
    logout: [userId: string];
    error: [message: string, code: number];
}

const emitter = new EventEmitter<AppEvents>();

emitter.on("login", (userId, timestamp) => {
    console.log(`User ${userId} logged in at ${timestamp}`);
});

emitter.emit("login", "user-123", new Date());
```

</details>

---

## Exercise 6.5: Practical Generic Patterns

Implement advanced generic patterns.

```typescript
// TODO: Implement these advanced patterns

// 1. Generic memoization
function memoize<Args extends unknown[], Result>(
    fn: (...args: Args) => Result
): (...args: Args) => Result {
    // TODO: Implement with cache
}

// 2. Generic pipe function
function pipe<T>(...fns: ((arg: T) => T)[]): (arg: T) => T {
    // TODO: Compose functions left-to-right
}

// 3. Generic deep clone with type safety
function deepClone<T>(obj: T): T {
    // TODO: Handle arrays, objects, dates, etc.
}

// 4. Generic object mapping
function mapObject<T, U>(
    obj: Record<string, T>,
    fn: (value: T, key: string) => U
): Record<string, U> {
    // TODO: Transform object values
}

// 5. Generic async retry
function withRetry<T>(
    fn: () => Promise<T>,
    maxRetries: number,
    delay: number
): Promise<T> {
    // TODO: Retry on failure
}
```

<details>
<summary>Solution</summary>

```typescript
// 1. Generic memoization
function memoize<Args extends unknown[], Result>(
    fn: (...args: Args) => Result
): (...args: Args) => Result {
    const cache = new Map<string, Result>();

    return (...args: Args): Result => {
        const key = JSON.stringify(args);

        if (cache.has(key)) {
            return cache.get(key)!;
        }

        const result = fn(...args);
        cache.set(key, result);
        return result;
    };
}

// Usage
const expensiveCalc = memoize((a: number, b: number) => {
    console.log("Computing...");
    return a + b;
});

expensiveCalc(1, 2);  // "Computing..." -> 3
expensiveCalc(1, 2);  // 3 (cached)

// 2. Generic pipe function
function pipe<T>(...fns: ((arg: T) => T)[]): (arg: T) => T {
    return (arg: T): T => {
        return fns.reduce((result, fn) => fn(result), arg);
    };
}

// Usage
const processString = pipe<string>(
    s => s.trim(),
    s => s.toLowerCase(),
    s => s.replace(/\s+/g, "-")
);

console.log(processString("  Hello World  "));  // "hello-world"

// 3. Generic deep clone
function deepClone<T>(obj: T): T {
    if (obj === null || typeof obj !== "object") {
        return obj;
    }

    if (obj instanceof Date) {
        return new Date(obj.getTime()) as T;
    }

    if (Array.isArray(obj)) {
        return obj.map(item => deepClone(item)) as T;
    }

    if (obj instanceof Map) {
        const clonedMap = new Map();
        obj.forEach((value, key) => {
            clonedMap.set(deepClone(key), deepClone(value));
        });
        return clonedMap as T;
    }

    if (obj instanceof Set) {
        const clonedSet = new Set();
        obj.forEach(value => {
            clonedSet.add(deepClone(value));
        });
        return clonedSet as T;
    }

    const clonedObj: Record<string, unknown> = {};
    for (const key in obj) {
        if (Object.prototype.hasOwnProperty.call(obj, key)) {
            clonedObj[key] = deepClone((obj as Record<string, unknown>)[key]);
        }
    }
    return clonedObj as T;
}

// 4. Generic object mapping
function mapObject<T, U>(
    obj: Record<string, T>,
    fn: (value: T, key: string) => U
): Record<string, U> {
    const result: Record<string, U> = {};

    for (const key of Object.keys(obj)) {
        result[key] = fn(obj[key], key);
    }

    return result;
}

// Usage
const prices = { apple: 1.5, banana: 0.75, orange: 2.0 };
const discounted = mapObject(prices, (price) => price * 0.9);
// { apple: 1.35, banana: 0.675, orange: 1.8 }

// 5. Generic async retry
async function withRetry<T>(
    fn: () => Promise<T>,
    maxRetries: number,
    delay: number
): Promise<T> {
    let lastError: Error | undefined;

    for (let attempt = 0; attempt <= maxRetries; attempt++) {
        try {
            return await fn();
        } catch (error) {
            lastError = error instanceof Error ? error : new Error(String(error));

            if (attempt < maxRetries) {
                await new Promise(resolve => setTimeout(resolve, delay));
            }
        }
    }

    throw lastError;
}

// Usage
async function fetchWithRetry() {
    const data = await withRetry(
        () => fetch("/api/data").then(r => r.json()),
        3,      // max 3 retries
        1000    // 1 second delay
    );
    return data;
}
```

</details>

---

## Challenge: Generic Collection Library

Build a type-safe collection library with common functional operations.

```typescript
// TODO: Build the collection library

// Base Collection interface
interface Collection<T> {
    // Basic operations
    add(item: T): Collection<T>;
    remove(predicate: (item: T) => boolean): Collection<T>;
    get(index: number): T | undefined;
    size(): number;
    isEmpty(): boolean;
    toArray(): T[];

    // Functional operations
    map<U>(fn: (item: T, index: number) => U): Collection<U>;
    filter(predicate: (item: T, index: number) => boolean): Collection<T>;
    reduce<U>(fn: (acc: U, item: T, index: number) => U, initial: U): U;
    find(predicate: (item: T) => boolean): T | undefined;
    some(predicate: (item: T) => boolean): boolean;
    every(predicate: (item: T) => boolean): boolean;

    // Chaining operations
    take(n: number): Collection<T>;
    skip(n: number): Collection<T>;
    distinct(): Collection<T>;
    sortBy<K>(keyFn: (item: T) => K): Collection<T>;

    // Grouping
    groupBy<K>(keyFn: (item: T) => K): Map<K, Collection<T>>;
    partition(predicate: (item: T) => boolean): [Collection<T>, Collection<T>];
}

// Implement the Collection class
class List<T> implements Collection<T> {
    // TODO: Implement all methods
}

// Test your implementation
const numbers = new List([1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);

const result = numbers
    .filter(n => n % 2 === 0)
    .map(n => n * 2)
    .take(3)
    .toArray();

console.log(result);  // [4, 8, 12]
```

<details>
<summary>Solution</summary>

```typescript
interface Collection<T> {
    add(item: T): Collection<T>;
    remove(predicate: (item: T) => boolean): Collection<T>;
    get(index: number): T | undefined;
    size(): number;
    isEmpty(): boolean;
    toArray(): T[];
    map<U>(fn: (item: T, index: number) => U): Collection<U>;
    filter(predicate: (item: T, index: number) => boolean): Collection<T>;
    reduce<U>(fn: (acc: U, item: T, index: number) => U, initial: U): U;
    find(predicate: (item: T) => boolean): T | undefined;
    some(predicate: (item: T) => boolean): boolean;
    every(predicate: (item: T) => boolean): boolean;
    take(n: number): Collection<T>;
    skip(n: number): Collection<T>;
    distinct(): Collection<T>;
    sortBy<K>(keyFn: (item: T) => K): Collection<T>;
    groupBy<K>(keyFn: (item: T) => K): Map<K, Collection<T>>;
    partition(predicate: (item: T) => boolean): [Collection<T>, Collection<T>];
}

class List<T> implements Collection<T> {
    private items: T[];

    constructor(items: T[] = []) {
        this.items = [...items];
    }

    add(item: T): Collection<T> {
        return new List([...this.items, item]);
    }

    remove(predicate: (item: T) => boolean): Collection<T> {
        return new List(this.items.filter(item => !predicate(item)));
    }

    get(index: number): T | undefined {
        return this.items[index];
    }

    size(): number {
        return this.items.length;
    }

    isEmpty(): boolean {
        return this.items.length === 0;
    }

    toArray(): T[] {
        return [...this.items];
    }

    map<U>(fn: (item: T, index: number) => U): Collection<U> {
        return new List(this.items.map(fn));
    }

    filter(predicate: (item: T, index: number) => boolean): Collection<T> {
        return new List(this.items.filter(predicate));
    }

    reduce<U>(fn: (acc: U, item: T, index: number) => U, initial: U): U {
        return this.items.reduce(fn, initial);
    }

    find(predicate: (item: T) => boolean): T | undefined {
        return this.items.find(predicate);
    }

    some(predicate: (item: T) => boolean): boolean {
        return this.items.some(predicate);
    }

    every(predicate: (item: T) => boolean): boolean {
        return this.items.every(predicate);
    }

    take(n: number): Collection<T> {
        return new List(this.items.slice(0, n));
    }

    skip(n: number): Collection<T> {
        return new List(this.items.slice(n));
    }

    distinct(): Collection<T> {
        return new List([...new Set(this.items)]);
    }

    sortBy<K>(keyFn: (item: T) => K): Collection<T> {
        const sorted = [...this.items].sort((a, b) => {
            const keyA = keyFn(a);
            const keyB = keyFn(b);
            if (keyA < keyB) return -1;
            if (keyA > keyB) return 1;
            return 0;
        });
        return new List(sorted);
    }

    groupBy<K>(keyFn: (item: T) => K): Map<K, Collection<T>> {
        const groups = new Map<K, T[]>();

        for (const item of this.items) {
            const key = keyFn(item);
            if (!groups.has(key)) {
                groups.set(key, []);
            }
            groups.get(key)!.push(item);
        }

        const result = new Map<K, Collection<T>>();
        groups.forEach((items, key) => {
            result.set(key, new List(items));
        });

        return result;
    }

    partition(predicate: (item: T) => boolean): [Collection<T>, Collection<T>] {
        const pass: T[] = [];
        const fail: T[] = [];

        for (const item of this.items) {
            if (predicate(item)) {
                pass.push(item);
            } else {
                fail.push(item);
            }
        }

        return [new List(pass), new List(fail)];
    }
}

// Test
const numbers = new List([1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);

const result = numbers
    .filter(n => n % 2 === 0)
    .map(n => n * 2)
    .take(3)
    .toArray();

console.log(result);  // [4, 8, 12]

// More examples
interface Person {
    name: string;
    age: number;
    city: string;
}

const people = new List<Person>([
    { name: "Alice", age: 30, city: "NYC" },
    { name: "Bob", age: 25, city: "LA" },
    { name: "Charlie", age: 35, city: "NYC" },
    { name: "Diana", age: 28, city: "LA" }
]);

const byCity = people.groupBy(p => p.city);
console.log(byCity.get("NYC")?.toArray());

const [adults, minors] = people.partition(p => p.age >= 21);
console.log(adults.size());  // 4

const sorted = people.sortBy(p => p.age);
console.log(sorted.get(0)?.name);  // "Bob" (youngest)
```

</details>

---

## Next Steps

Continue to [Exercise Set 7: Classes and Access Modifiers](./07-classes.md)

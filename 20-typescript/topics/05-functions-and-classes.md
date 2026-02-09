# TypeScript Functions and Classes

## Functions

### Basic Function Types

```typescript
// Function with parameter and return types
function add(x: number, y: number): number {
    return x + y;
}

// Function with no return value
function logMessage(message: string): void {
    console.log(message);
}

// Arrow function
const multiply = (x: number, y: number): number => x * y;
```

### Optional and Default Parameters

```typescript
// Optional parameters (use ?)
function greet(name: string, greeting?: string): string {
    return greeting ? `${greeting}, ${name}!` : `Hello, ${name}!`;
}

greet("Alice");                        // "Hello, Alice!"
greet("Bob", "Hi");                    // "Hi, Bob!"

// Default parameters
function createUser(name: string, age: number = 18) {
    return { name, age };
}

createUser("Alice");                   // age defaults to 18
createUser("Bob", 25);                 // age is 25
```

### Rest Parameters

```typescript
function sum(...numbers: number[]): number {
    return numbers.reduce((total, n) => total + n, 0);
}

sum(1, 2, 3, 4, 5);                    // 15

function concatenate(separator: string, ...strings: string[]): string {
    return strings.join(separator);
}

concatenate(", ", "apple", "banana"); // "apple, banana"
```

### Function Types

```typescript
// Function type definition
type MathOperation = (x: number, y: number) => number;

const add: MathOperation = (x, y) => x + y;
const subtract: MathOperation = (x, y) => x - y;

// Function as parameter
function applyOperation(
    x: number,
    y: number,
    operation: (a: number, b: number) => number
): number {
    return operation(x, y);
}

applyOperation(5, 3, add);             // 8
```

### Generic Functions

Functions that work with multiple types:

```typescript
// Basic generic function
function identity<T>(value: T): T {
    return value;
}

const num = identity<number>(42);      // Type: number
const str = identity<string>("hello"); // Type: string
const auto = identity(true);           // Type inferred as boolean

// Generic with constraints
interface Lengthwise {
    length: number;
}

function logLength<T extends Lengthwise>(item: T): void {
    console.log(item.length);
}

logLength("hello");                    // OK - string has length
logLength([1, 2, 3]);                  // OK - array has length

// Generic array operations
function getFirst<T>(array: T[]): T | undefined {
    return array[0];
}

const firstNumber = getFirst([1, 2, 3]);     // number | undefined
const firstName = getFirst(["a", "b", "c"]); // string | undefined
```

---

## Classes

### Basic Class Definition

```typescript
class Person {
    name: string;
    age: number;

    constructor(name: string, age: number) {
        this.name = name;
        this.age = age;
    }

    greet(): string {
        return `Hello, I'm ${this.name}`;
    }
}

const person = new Person("Alice", 30);
console.log(person.greet());
```

### Parameter Properties (Shorthand)

```typescript
// TypeScript shorthand - automatically creates and assigns properties
class Product {
    constructor(
        public name: string,
        public price: number,
        private sku: string
    ) {
        // Properties automatically created and assigned
    }
}

const product = new Product("Widget", 19.99, "ABC123");
console.log(product.name);             // "Widget"
console.log(product.sku);              // Error - 'sku' is private
```

### Getters and Setters

```typescript
class Employee {
    private _salary: number = 0;

    get salary(): number {
        return this._salary;
    }

    set salary(value: number) {
        if (value < 0) {
            throw new Error("Salary cannot be negative");
        }
        this._salary = value;
    }

    get annualSalary(): number {
        return this._salary * 12;
    }
}

const emp = new Employee();
emp.salary = 5000;                     // Uses setter
console.log(emp.salary);               // Uses getter: 5000
console.log(emp.annualSalary);         // 60000
```

### Static Members

```typescript
class MathUtils {
    static PI: number = 3.14159;

    static circleArea(radius: number): number {
        return MathUtils.PI * radius * radius;
    }
}

// Access on class, not instance
console.log(MathUtils.PI);             // 3.14159
console.log(MathUtils.circleArea(5));  // 78.53975
```

### Class Inheritance

```typescript
class Animal {
    constructor(public name: string) {}

    move(distance: number = 0): void {
        console.log(`${this.name} moved ${distance}m.`);
    }
}

class Dog extends Animal {
    constructor(name: string, public breed: string) {
        super(name);               // Call parent constructor
    }

    // Override method
    bark(): void {
        console.log("Woof!");
    }
}

const dog = new Dog("Buddy", "Golden Retriever");
dog.move(10);                          // "Buddy moved 10m."
dog.bark();                            // "Woof!"
```

### Abstract Classes

Classes that cannot be instantiated directly:

```typescript
abstract class Shape {
    constructor(public color: string) {}

    // Abstract method - must be implemented
    abstract getArea(): number;

    // Concrete method - can be used by all
    describe(): string {
        return `A ${this.color} shape`;
    }
}

// const shape = new Shape("red");     // Error: Cannot instantiate abstract

class Circle extends Shape {
    constructor(color: string, public radius: number) {
        super(color);
    }

    getArea(): number {
        return Math.PI * this.radius ** 2;
    }
}

const circle = new Circle("blue", 5);
console.log(circle.getArea());         // 78.54...
```

### Implementing Interfaces

```typescript
interface Printable {
    print(): void;
}

interface Serializable {
    serialize(): string;
}

// Class implementing multiple interfaces
class Report implements Printable, Serializable {
    constructor(public title: string, public data: any) {}

    print(): void {
        console.log(`Report: ${this.title}`);
    }

    serialize(): string {
        return JSON.stringify({ title: this.title, data: this.data });
    }
}
```

---

## Access Modifiers

TypeScript provides access modifiers to control member visibility.

### Public (Default)

```typescript
class Person {
    public name: string;               // Explicitly public
    age: number;                       // Implicitly public

    constructor(name: string, age: number) {
        this.name = name;
        this.age = age;
    }
}

const person = new Person("Alice", 30);
console.log(person.name);              // OK - public
```

### Private

```typescript
class BankAccount {
    private balance: number = 0;

    deposit(amount: number): void {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    getBalance(): number {
        return this.balance;
    }
}

const account = new BankAccount();
account.deposit(100);
console.log(account.getBalance());     // 100
console.log(account.balance);          // Error - 'balance' is private
```

### Protected

```typescript
class Vehicle {
    protected speed: number = 0;

    accelerate(amount: number): void {
        this.speed += amount;
    }
}

class Car extends Vehicle {
    getCurrentSpeed(): number {
        return this.speed;             // OK - protected in derived class
    }
}

const car = new Car();
car.accelerate(50);
console.log(car.getCurrentSpeed());    // 50
console.log(car.speed);                // Error - 'speed' is protected
```

### Readonly

```typescript
class Person {
    readonly id: number;
    readonly createdAt: Date = new Date();

    constructor(id: number) {
        this.id = id;                  // OK in constructor
    }
}

const person = new Person(1);
person.id = 2;                         // Error - readonly property
```

### Access Modifier Summary

| Modifier | Class | Derived Class | Outside |
|----------|-------|---------------|---------|
| `public` | Yes | Yes | Yes |
| `protected` | Yes | Yes | No |
| `private` | Yes | No | No |
| `readonly` | Can set in constructor only |

---

## Decorators

Decorators are special declarations that can modify classes, methods, and properties. Required for Angular.

### Enabling Decorators

```json
// tsconfig.json
{
  "compilerOptions": {
    "experimentalDecorators": true
  }
}
```

### Class Decorators

```typescript
// Simple class decorator
function sealed(constructor: Function) {
    Object.seal(constructor);
    Object.seal(constructor.prototype);
}

@sealed
class Greeter {
    greeting: string;
    constructor(message: string) {
        this.greeting = message;
    }
}

// Decorator factory - returns a decorator
function component(name: string) {
    return function(constructor: Function) {
        console.log(`Registering component: ${name}`);
    };
}

@component("MyComponent")
class MyComponent {
    render() {
        console.log("Rendering");
    }
}
```

### Method Decorators

```typescript
function log(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
    const originalMethod = descriptor.value;

    descriptor.value = function(...args: any[]) {
        console.log(`Calling ${propertyKey} with:`, args);
        const result = originalMethod.apply(this, args);
        console.log(`Result:`, result);
        return result;
    };

    return descriptor;
}

class Calculator {
    @log
    add(x: number, y: number): number {
        return x + y;
    }
}

const calc = new Calculator();
calc.add(5, 3);
// Logs: Calling add with: [5, 3]
// Logs: Result: 8
```

### Property Decorators

```typescript
function MinLength(length: number) {
    return function(target: any, propertyKey: string) {
        let value: string;

        Object.defineProperty(target, propertyKey, {
            get: () => value,
            set: (newVal: string) => {
                if (newVal.length < length) {
                    throw new Error(`${propertyKey} must be at least ${length} characters`);
                }
                value = newVal;
            }
        });
    };
}

class User {
    @MinLength(3)
    username: string;

    constructor(username: string) {
        this.username = username;
    }
}

const user1 = new User("Alice");       // OK
const user2 = new User("Al");          // Error
```

### Angular Decorator Example

```typescript
// This is how Angular uses decorators
@Component({
    selector: 'app-root',
    template: '<h1>Hello</h1>'
})
class AppComponent {
    title = 'My App';
}

// Injectable service
@Injectable({
    providedIn: 'root'
})
class UserService {
    getUsers() {
        return [];
    }
}
```

---

## Summary

| Feature | Purpose | Example |
|---------|---------|---------|
| Function Types | Type-safe functions | `(x: number) => number` |
| Optional Params | Parameters that may be omitted | `greet(name: string, title?: string)` |
| Generics | Reusable with different types | `<T>(value: T) => T` |
| Classes | Object blueprints | `class User { }` |
| Inheritance | Extend classes | `class Admin extends User` |
| `public` | Accessible everywhere | Default |
| `private` | Only in class | `private password` |
| `protected` | Class and derived classes | `protected id` |
| `readonly` | Cannot be modified | `readonly createdAt` |
| Decorators | Modify class behavior | `@Component` |

## Next Steps

You have completed the TypeScript fundamentals! These concepts are essential for Angular development:
- Type annotations keep your code safe
- Interfaces define component contracts
- Classes structure your components and services
- Decorators configure Angular metadata

Return to [TypeScript Module README](../README.md)

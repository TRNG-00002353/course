# TypeScript Key Concepts for Application Developers

## Overview

This document covers essential TypeScript concepts that every application developer must master. TypeScript is a statically-typed superset of JavaScript that compiles to plain JavaScript, providing type safety, enhanced IDE support, and better code maintainability for large-scale applications.

---

## 1. TypeScript Fundamentals

### Why It Matters
- Catch errors at compile-time, not runtime
- Better IDE support (autocomplete, refactoring)
- Self-documenting code through types
- Easier to maintain large codebases
- Industry standard for modern web development

### Key Concepts

| Concept | JavaScript | TypeScript |
|---------|-----------|-----------|
| Type System | Dynamic, runtime | Static, compile-time |
| Type Annotations | None | Explicit types |
| Type Checking | Runtime only | Compile-time + runtime |
| IDE Support | Basic | Advanced (IntelliSense) |
| Refactoring | Error-prone | Safe and reliable |

### Setup and Configuration

**Installation:**
```bash
# Global installation
npm install -g typescript

# Project installation
npm install --save-dev typescript

# Initialize TypeScript config
npx tsc --init
```

**tsconfig.json:**
```json
{
  "compilerOptions": {
    "target": "ES2020",                    // ECMAScript target version
    "module": "commonjs",                  // Module system
    "lib": ["ES2020", "DOM"],             // Type definitions to include
    "outDir": "./dist",                    // Output directory
    "rootDir": "./src",                    // Source directory
    "strict": true,                        // Enable all strict type checks
    "esModuleInterop": true,              // Enable interop with CommonJS
    "skipLibCheck": true,                  // Skip type checking of declaration files
    "forceConsistentCasingInFileNames": true,
    "resolveJsonModule": true,            // Import JSON files
    "declaration": true,                   // Generate .d.ts files
    "sourceMap": true,                     // Generate source maps
    "removeComments": true,                // Remove comments in output
    "noUnusedLocals": true,               // Report unused local variables
    "noUnusedParameters": true,           // Report unused parameters
    "noImplicitReturns": true,            // Report missing return statements
    "noFallthroughCasesInSwitch": true    // Report fallthrough cases in switch
  },
  "include": ["src/**/*"],
  "exclude": ["node_modules", "dist", "**/*.spec.ts"]
}
```

**Compilation:**
```bash
# Compile single file
tsc file.ts

# Compile all files
tsc

# Watch mode
tsc --watch

# Run TypeScript directly (development)
npm install -g ts-node
ts-node file.ts
```

---

## 2. Basic Types

### Why It Matters
- Type safety prevents bugs
- Clear data contracts
- Better documentation
- Improved autocomplete

### Key Concepts

**Primitive Types:**
```typescript
// String
let username: string = "john_doe";
let message: string = `Hello, ${username}!`;

// Number
let age: number = 25;
let price: number = 19.99;
let binary: number = 0b1010;
let hex: number = 0xf00d;

// Boolean
let isActive: boolean = true;
let hasPermission: boolean = false;

// Null and Undefined
let nullable: null = null;
let notDefined: undefined = undefined;

// Any (avoid when possible)
let anything: any = "can be anything";
anything = 42;
anything = true;

// Unknown (safer than any)
let uncertain: unknown = "something";
if (typeof uncertain === "string") {
  console.log(uncertain.toUpperCase()); // Type guard required
}

// Void (no return value)
function logMessage(message: string): void {
  console.log(message);
}

// Never (never returns)
function throwError(message: string): never {
  throw new Error(message);
}

function infiniteLoop(): never {
  while (true) {}
}
```

**Arrays:**
```typescript
// Array syntax
let numbers: number[] = [1, 2, 3, 4, 5];
let names: string[] = ["Alice", "Bob", "Charlie"];

// Generic array syntax
let scores: Array<number> = [95, 87, 92];

// Mixed types (use union)
let mixed: (string | number)[] = [1, "two", 3, "four"];

// Readonly arrays
let readonlyNumbers: ReadonlyArray<number> = [1, 2, 3];
// readonlyNumbers.push(4); // Error: Property 'push' does not exist

// Tuple (fixed-length array with specific types)
let person: [string, number] = ["John", 30];
let coordinate: [number, number] = [10.5, 20.3];

// Optional tuple elements
let optionalTuple: [string, number?] = ["Alice"];

// Rest elements in tuple
let restTuple: [string, ...number[]] = ["Numbers:", 1, 2, 3, 4];
```

**Objects:**
```typescript
// Object type annotation
let user: {
  name: string;
  age: number;
  email: string;
} = {
  name: "John Doe",
  age: 30,
  email: "john@example.com"
};

// Optional properties
let product: {
  name: string;
  price: number;
  description?: string; // Optional
} = {
  name: "Widget",
  price: 19.99
};

// Readonly properties
let config: {
  readonly apiUrl: string;
  readonly timeout: number;
} = {
  apiUrl: "https://api.example.com",
  timeout: 5000
};
// config.apiUrl = "new url"; // Error: Cannot assign to 'apiUrl'

// Index signatures (dynamic properties)
let dictionary: {
  [key: string]: number;
} = {
  apple: 5,
  banana: 3,
  orange: 7
};
```

---

## 3. Interfaces

### Why It Matters
- Define contracts for objects
- Ensure consistency across codebase
- Enable code reuse
- Support extension and composition

### Key Concepts

**Basic Interface:**
```typescript
interface User {
  id: number;
  username: string;
  email: string;
  createdAt: Date;
}

const user: User = {
  id: 1,
  username: "john_doe",
  email: "john@example.com",
  createdAt: new Date()
};
```

**Optional and Readonly:**
```typescript
interface Product {
  readonly id: number;        // Cannot be modified
  name: string;
  price: number;
  description?: string;       // Optional
  category?: string;
  inStock?: boolean;
}

const product: Product = {
  id: 1,
  name: "Laptop",
  price: 999.99
};

// product.id = 2; // Error: Cannot assign to 'id'
```

**Function Types:**
```typescript
interface SearchFunction {
  (query: string, limit: number): string[];
}

const search: SearchFunction = (query, limit) => {
  // Implementation
  return [`Result 1 for ${query}`, `Result 2 for ${query}`];
};

interface Calculator {
  add(a: number, b: number): number;
  subtract(a: number, b: number): number;
  multiply(a: number, b: number): number;
  divide(a: number, b: number): number;
}
```

**Indexable Types:**
```typescript
interface StringArray {
  [index: number]: string;
}

const fruits: StringArray = ["Apple", "Banana", "Orange"];

interface Dictionary {
  [key: string]: any;
}

const userSettings: Dictionary = {
  theme: "dark",
  language: "en",
  notifications: true
};
```

**Extending Interfaces:**
```typescript
interface Person {
  firstName: string;
  lastName: string;
  age: number;
}

interface Employee extends Person {
  employeeId: number;
  department: string;
  salary: number;
}

const employee: Employee = {
  firstName: "John",
  lastName: "Doe",
  age: 30,
  employeeId: 12345,
  department: "Engineering",
  salary: 75000
};

// Multiple inheritance
interface Manager extends Employee {
  teamSize: number;
  reports: Employee[];
}
```

**Interface vs Type Alias:**
```typescript
// Interface (can be extended)
interface IUser {
  name: string;
  email: string;
}

// Type alias (more flexible)
type TUser = {
  name: string;
  email: string;
};

// Use interfaces for object shapes that may be extended
// Use type aliases for unions, primitives, tuples

type ID = string | number;
type Coordinate = [number, number];
type Callback = (data: string) => void;
```

---

## 4. Classes

### Why It Matters
- Object-oriented programming support
- Encapsulation and inheritance
- Code organization
- Widely used in frameworks (Angular, NestJS)

### Key Concepts

**Basic Class:**
```typescript
class User {
  // Properties
  id: number;
  username: string;
  email: string;
  private password: string;

  // Constructor
  constructor(id: number, username: string, email: string, password: string) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
  }

  // Methods
  getDisplayName(): string {
    return `${this.username} (${this.email})`;
  }

  validatePassword(password: string): boolean {
    return this.password === password;
  }
}

const user = new User(1, "john_doe", "john@example.com", "secret");
console.log(user.getDisplayName());
// console.log(user.password); // Error: Property 'password' is private
```

**Access Modifiers:**
```typescript
class BankAccount {
  public accountNumber: string;      // Accessible everywhere
  protected balance: number;         // Accessible in class and subclasses
  private pin: string;               // Accessible only in this class

  constructor(accountNumber: string, initialBalance: number, pin: string) {
    this.accountNumber = accountNumber;
    this.balance = initialBalance;
    this.pin = pin;
  }

  public deposit(amount: number): void {
    this.balance += amount;
  }

  public withdraw(amount: number, pin: string): boolean {
    if (this.validatePin(pin) && this.balance >= amount) {
      this.balance -= amount;
      return true;
    }
    return false;
  }

  private validatePin(pin: string): boolean {
    return this.pin === pin;
  }

  public getBalance(pin: string): number | null {
    return this.validatePin(pin) ? this.balance : null;
  }
}
```

**Constructor Shorthand:**
```typescript
class Product {
  constructor(
    public id: number,
    public name: string,
    public price: number,
    private cost: number
  ) {}

  getProfit(): number {
    return this.price - this.cost;
  }
}

// Equivalent to:
// class Product {
//   public id: number;
//   public name: string;
//   public price: number;
//   private cost: number;
//   constructor(id, name, price, cost) { ... }
// }
```

**Inheritance:**
```typescript
class Animal {
  constructor(public name: string) {}

  move(distance: number = 0): void {
    console.log(`${this.name} moved ${distance} meters.`);
  }
}

class Dog extends Animal {
  constructor(name: string, public breed: string) {
    super(name);
  }

  bark(): void {
    console.log("Woof! Woof!");
  }

  override move(distance: number = 5): void {
    console.log("Running...");
    super.move(distance);
  }
}

const dog = new Dog("Buddy", "Golden Retriever");
dog.bark();
dog.move(10);
```

**Abstract Classes:**
```typescript
abstract class Shape {
  constructor(public color: string) {}

  abstract calculateArea(): number;
  abstract calculatePerimeter(): number;

  describe(): string {
    return `A ${this.color} shape with area ${this.calculateArea()}`;
  }
}

class Circle extends Shape {
  constructor(color: string, public radius: number) {
    super(color);
  }

  calculateArea(): number {
    return Math.PI * this.radius ** 2;
  }

  calculatePerimeter(): number {
    return 2 * Math.PI * this.radius;
  }
}

class Rectangle extends Shape {
  constructor(color: string, public width: number, public height: number) {
    super(color);
  }

  calculateArea(): number {
    return this.width * this.height;
  }

  calculatePerimeter(): number {
    return 2 * (this.width + this.height);
  }
}

// const shape = new Shape("red"); // Error: Cannot create instance of abstract class
const circle = new Circle("red", 5);
const rectangle = new Rectangle("blue", 10, 20);
```

**Getters and Setters:**
```typescript
class Temperature {
  private _celsius: number = 0;

  get celsius(): number {
    return this._celsius;
  }

  set celsius(value: number) {
    if (value < -273.15) {
      throw new Error("Temperature below absolute zero is impossible");
    }
    this._celsius = value;
  }

  get fahrenheit(): number {
    return (this._celsius * 9/5) + 32;
  }

  set fahrenheit(value: number) {
    this.celsius = (value - 32) * 5/9;
  }
}

const temp = new Temperature();
temp.celsius = 25;
console.log(temp.fahrenheit); // 77
temp.fahrenheit = 32;
console.log(temp.celsius); // 0
```

**Static Members:**
```typescript
class MathUtils {
  static PI: number = 3.14159;

  static calculateCircleArea(radius: number): number {
    return this.PI * radius ** 2;
  }

  static max(...numbers: number[]): number {
    return Math.max(...numbers);
  }
}

console.log(MathUtils.PI);
console.log(MathUtils.calculateCircleArea(5));
console.log(MathUtils.max(1, 5, 3, 9, 2));
```

---

## 5. Generics

### Why It Matters
- Write reusable, type-safe code
- Avoid code duplication
- Maintain type information
- Create flexible APIs

### Key Concepts

**Generic Functions:**
```typescript
// Without generics (loses type information)
function identityAny(arg: any): any {
  return arg;
}

// With generics (preserves type)
function identity<T>(arg: T): T {
  return arg;
}

const numberResult = identity<number>(42);     // Type: number
const stringResult = identity<string>("hello"); // Type: string
const autoInferred = identity(true);           // Type: boolean (inferred)

// Generic with multiple type parameters
function pair<T, U>(first: T, second: U): [T, U] {
  return [first, second];
}

const coords = pair<number, number>(10, 20);
const userInfo = pair<string, number>("John", 30);
```

**Generic Interfaces:**
```typescript
interface Repository<T> {
  findById(id: number): T | null;
  findAll(): T[];
  save(entity: T): T;
  delete(id: number): void;
}

class UserRepository implements Repository<User> {
  private users: User[] = [];

  findById(id: number): User | null {
    return this.users.find(u => u.id === id) || null;
  }

  findAll(): User[] {
    return this.users;
  }

  save(user: User): User {
    this.users.push(user);
    return user;
  }

  delete(id: number): void {
    this.users = this.users.filter(u => u.id !== id);
  }
}
```

**Generic Classes:**
```typescript
class Stack<T> {
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

const numberStack = new Stack<number>();
numberStack.push(1);
numberStack.push(2);
console.log(numberStack.pop()); // 2

const stringStack = new Stack<string>();
stringStack.push("hello");
stringStack.push("world");
```

**Generic Constraints:**
```typescript
// Constraint: T must have length property
interface Lengthwise {
  length: number;
}

function logLength<T extends Lengthwise>(arg: T): T {
  console.log(arg.length);
  return arg;
}

logLength("hello");           // OK: string has length
logLength([1, 2, 3]);        // OK: array has length
// logLength(123);           // Error: number doesn't have length

// Using type parameters in constraints
function getProperty<T, K extends keyof T>(obj: T, key: K): T[K] {
  return obj[key];
}

const user = { name: "John", age: 30, email: "john@example.com" };
const name = getProperty(user, "name");    // Type: string
const age = getProperty(user, "age");      // Type: number
// const invalid = getProperty(user, "invalid"); // Error
```

**Generic Utility Types:**
```typescript
interface User {
  id: number;
  name: string;
  email: string;
  age: number;
}

// Partial: All properties optional
type PartialUser = Partial<User>;
const updateUser: PartialUser = { name: "John" };

// Required: All properties required
type RequiredUser = Required<User>;

// Readonly: All properties readonly
type ReadonlyUser = Readonly<User>;

// Pick: Select specific properties
type UserSummary = Pick<User, "id" | "name">;
const summary: UserSummary = { id: 1, name: "John" };

// Omit: Exclude specific properties
type UserWithoutId = Omit<User, "id">;
const newUser: UserWithoutId = { name: "John", email: "john@example.com", age: 30 };

// Record: Create object type with specific keys and values
type UserRoles = Record<string, boolean>;
const roles: UserRoles = {
  admin: true,
  editor: false,
  viewer: true
};

// ReturnType: Extract return type of function
function getUser(): User {
  return { id: 1, name: "John", email: "john@example.com", age: 30 };
}
type UserReturnType = ReturnType<typeof getUser>; // Type: User
```

---

## 6. Advanced Types

### Why It Matters
- Handle complex type scenarios
- Create flexible type definitions
- Improve type safety
- Enable sophisticated patterns

### Key Concepts

**Union Types:**
```typescript
// Variable can be one of several types
type ID = string | number;

let userId: ID = 123;
userId = "abc-123";

type Status = "pending" | "approved" | "rejected";
let orderStatus: Status = "pending";
// orderStatus = "invalid"; // Error

function formatValue(value: string | number): string {
  if (typeof value === "string") {
    return value.toUpperCase();
  }
  return value.toFixed(2);
}
```

**Intersection Types:**
```typescript
interface Person {
  name: string;
  age: number;
}

interface Employee {
  employeeId: number;
  department: string;
}

type EmployeePerson = Person & Employee;

const employee: EmployeePerson = {
  name: "John",
  age: 30,
  employeeId: 12345,
  department: "Engineering"
};
```

**Type Guards:**
```typescript
// typeof type guard
function processValue(value: string | number) {
  if (typeof value === "string") {
    console.log(value.toUpperCase());
  } else {
    console.log(value.toFixed(2));
  }
}

// instanceof type guard
class Dog {
  bark() { console.log("Woof!"); }
}

class Cat {
  meow() { console.log("Meow!"); }
}

function makeSound(animal: Dog | Cat) {
  if (animal instanceof Dog) {
    animal.bark();
  } else {
    animal.meow();
  }
}

// Custom type guard
interface Fish {
  swim(): void;
}

interface Bird {
  fly(): void;
}

function isFish(pet: Fish | Bird): pet is Fish {
  return (pet as Fish).swim !== undefined;
}

function move(pet: Fish | Bird) {
  if (isFish(pet)) {
    pet.swim();
  } else {
    pet.fly();
  }
}
```

**Literal Types:**
```typescript
// String literal types
type Direction = "north" | "south" | "east" | "west";
let heading: Direction = "north";

// Numeric literal types
type DiceRoll = 1 | 2 | 3 | 4 | 5 | 6;
let roll: DiceRoll = 4;

// Boolean literal types
type Success = true;
type Failure = false;

// Template literal types
type EmailAddress = `${string}@${string}.${string}`;
const email: EmailAddress = "user@example.com";

type HTTPMethod = "GET" | "POST" | "PUT" | "DELETE";
type Endpoint = `/api/${string}`;
type APIRoute = `${HTTPMethod} ${Endpoint}`;

const route: APIRoute = "GET /api/users";
```

**Mapped Types:**
```typescript
interface User {
  id: number;
  name: string;
  email: string;
}

// Make all properties optional
type Optional<T> = {
  [P in keyof T]?: T[P];
};

type OptionalUser = Optional<User>;

// Make all properties readonly
type ReadOnly<T> = {
  readonly [P in keyof T]: T[P];
};

type ReadOnlyUser = ReadOnly<User>;

// Transform property types
type Nullable<T> = {
  [P in keyof T]: T[P] | null;
};

type NullableUser = Nullable<User>;

// Getters
type Getters<T> = {
  [P in keyof T as `get${Capitalize<string & P>}`]: () => T[P];
};

type UserGetters = Getters<User>;
// Result: { getId: () => number; getName: () => string; getEmail: () => string; }
```

**Conditional Types:**
```typescript
// Basic conditional type
type IsString<T> = T extends string ? true : false;

type Test1 = IsString<string>;  // true
type Test2 = IsString<number>;  // false

// Practical example
type NonNullable<T> = T extends null | undefined ? never : T;

type Value1 = NonNullable<string | null>;      // string
type Value2 = NonNullable<number | undefined>;  // number

// Extract function return type
type ReturnType<T> = T extends (...args: any[]) => infer R ? R : never;

function getUser() {
  return { id: 1, name: "John" };
}

type User = ReturnType<typeof getUser>; // { id: number; name: string; }
```

---

## 7. Decorators

### Why It Matters
- Metaprogramming capabilities
- Used extensively in frameworks (Angular, NestJS)
- Add functionality without modifying class
- Enable aspect-oriented programming

### Key Concepts

**Enable Decorators:**
```json
// tsconfig.json
{
  "compilerOptions": {
    "experimentalDecorators": true,
    "emitDecoratorMetadata": true
  }
}
```

**Class Decorators:**
```typescript
function sealed(constructor: Function) {
  Object.seal(constructor);
  Object.seal(constructor.prototype);
}

@sealed
class User {
  constructor(public name: string) {}
}

// Decorator with parameters
function Component(config: { selector: string; template: string }) {
  return function(constructor: Function) {
    constructor.prototype.selector = config.selector;
    constructor.prototype.template = config.template;
  };
}

@Component({
  selector: "app-user",
  template: "<div>{{name}}</div>"
})
class UserComponent {
  name: string = "John";
}
```

**Method Decorators:**
```typescript
function log(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
  const originalMethod = descriptor.value;

  descriptor.value = function(...args: any[]) {
    console.log(`Calling ${propertyKey} with args:`, args);
    const result = originalMethod.apply(this, args);
    console.log(`${propertyKey} returned:`, result);
    return result;
  };

  return descriptor;
}

class Calculator {
  @log
  add(a: number, b: number): number {
    return a + b;
  }
}

const calc = new Calculator();
calc.add(2, 3);
// Logs: Calling add with args: [2, 3]
// Logs: add returned: 5
```

**Property Decorators:**
```typescript
function validate(target: any, propertyKey: string) {
  let value: any;

  const getter = () => value;
  const setter = (newValue: any) => {
    if (typeof newValue !== "string" || newValue.length < 3) {
      throw new Error(`${propertyKey} must be a string with at least 3 characters`);
    }
    value = newValue;
  };

  Object.defineProperty(target, propertyKey, {
    get: getter,
    set: setter,
    enumerable: true,
    configurable: true
  });
}

class User {
  @validate
  username: string;

  constructor(username: string) {
    this.username = username;
  }
}

const user = new User("john_doe");
// user.username = "ab"; // Error: username must be a string with at least 3 characters
```

**Parameter Decorators:**
```typescript
function required(target: Object, propertyKey: string, parameterIndex: number) {
  const existingRequiredParameters: number[] =
    Reflect.getOwnMetadata("required", target, propertyKey) || [];
  existingRequiredParameters.push(parameterIndex);
  Reflect.defineMetadata("required", existingRequiredParameters, target, propertyKey);
}

class UserService {
  createUser(@required name: string, age: number) {
    console.log(`Creating user: ${name}, age ${age}`);
  }
}
```

**Real-World Examples (Angular-style):**
```typescript
// Dependency injection
function Injectable() {
  return function(target: Function) {
    // Mark class as injectable
  };
}

@Injectable()
class UserService {
  getUsers() {
    return [];
  }
}

// HTTP endpoint
function Get(path: string) {
  return function(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
    // Register GET endpoint
  };
}

function Post(path: string) {
  return function(target: any, propertyKey: string, descriptor: PropertyDescriptor) {
    // Register POST endpoint
  };
}

class UserController {
  @Get("/users")
  getAllUsers() {
    return [];
  }

  @Post("/users")
  createUser(userData: any) {
    return userData;
  }
}
```

---

## 8. Modules

### Why It Matters
- Code organization and reusability
- Encapsulation and namespacing
- Dependency management
- Tree-shaking for smaller bundles

### Key Concepts

**ES6 Modules (Recommended):**
```typescript
// user.ts
export interface User {
  id: number;
  name: string;
  email: string;
}

export class UserService {
  getUsers(): User[] {
    return [];
  }
}

export function validateEmail(email: string): boolean {
  return email.includes("@");
}

export const MAX_USERS = 100;

// Default export
export default class AuthService {
  login(username: string, password: string) {
    // ...
  }
}
```

**Importing:**
```typescript
// Import named exports
import { User, UserService, validateEmail, MAX_USERS } from "./user";

// Import default export
import AuthService from "./user";

// Import all as namespace
import * as UserModule from "./user";

// Import with alias
import { UserService as US } from "./user";

// Import for side effects only
import "./polyfills";

// Re-export
export { User, UserService } from "./user";
export * from "./user";
```

**Barrel Files (index.ts):**
```typescript
// models/index.ts
export * from "./user";
export * from "./product";
export * from "./order";

// Now can import from directory
import { User, Product, Order } from "./models";
```

**Module Resolution:**
```typescript
// Relative imports
import { User } from "./user";           // Same directory
import { Product } from "../product";     // Parent directory
import { Order } from "../../order";      // Two levels up

// Non-relative imports (from node_modules)
import express from "express";
import { Component } from "@angular/core";
```

**Declaration Files (.d.ts):**
```typescript
// types.d.ts
declare module "my-library" {
  export function doSomething(value: string): void;
  export class MyClass {
    constructor(value: number);
    getValue(): number;
  }
}

// Ambient declarations
declare const API_URL: string;
declare function legacyFunction(x: number): number;

// Global augmentation
declare global {
  interface Window {
    customProperty: string;
  }
}
```

---

## 9. Practical Patterns

### Why It Matters
- Apply TypeScript in real applications
- Follow best practices
- Build maintainable code
- Leverage type system effectively

### Key Concepts

**Repository Pattern:**
```typescript
interface Entity {
  id: number;
}

interface IRepository<T extends Entity> {
  findById(id: number): Promise<T | null>;
  findAll(): Promise<T[]>;
  create(entity: Omit<T, "id">): Promise<T>;
  update(id: number, entity: Partial<T>): Promise<T>;
  delete(id: number): Promise<void>;
}

class UserRepository implements IRepository<User> {
  private users: User[] = [];
  private nextId = 1;

  async findById(id: number): Promise<User | null> {
    return this.users.find(u => u.id === id) || null;
  }

  async findAll(): Promise<User[]> {
    return this.users;
  }

  async create(userData: Omit<User, "id">): Promise<User> {
    const user: User = { ...userData, id: this.nextId++ };
    this.users.push(user);
    return user;
  }

  async update(id: number, userData: Partial<User>): Promise<User> {
    const user = await this.findById(id);
    if (!user) throw new Error("User not found");
    Object.assign(user, userData);
    return user;
  }

  async delete(id: number): Promise<void> {
    this.users = this.users.filter(u => u.id !== id);
  }
}
```

**Service Layer:**
```typescript
class UserService {
  constructor(private userRepository: UserRepository) {}

  async getUserById(id: number): Promise<User> {
    const user = await this.userRepository.findById(id);
    if (!user) {
      throw new Error(`User not found: ${id}`);
    }
    return user;
  }

  async createUser(userData: CreateUserDTO): Promise<User> {
    // Validation
    this.validateUserData(userData);

    // Business logic
    const user = await this.userRepository.create({
      ...userData,
      createdAt: new Date(),
      updatedAt: new Date()
    });

    // Post-processing (send email, etc.)
    await this.sendWelcomeEmail(user);

    return user;
  }

  private validateUserData(data: CreateUserDTO): void {
    if (!data.email.includes("@")) {
      throw new Error("Invalid email");
    }
    if (data.password.length < 8) {
      throw new Error("Password too short");
    }
  }

  private async sendWelcomeEmail(user: User): Promise<void> {
    // Email sending logic
  }
}
```

**Error Handling:**
```typescript
// Custom error classes
class AppError extends Error {
  constructor(
    public message: string,
    public statusCode: number = 500,
    public isOperational: boolean = true
  ) {
    super(message);
    Object.setPrototypeOf(this, AppError.prototype);
  }
}

class NotFoundError extends AppError {
  constructor(message: string) {
    super(message, 404);
    Object.setPrototypeOf(this, NotFoundError.prototype);
  }
}

class ValidationError extends AppError {
  constructor(message: string) {
    super(message, 400);
    Object.setPrototypeOf(this, ValidationError.prototype);
  }
}

// Usage
class UserController {
  constructor(private userService: UserService) {}

  async getUser(id: number): Promise<User> {
    const user = await this.userService.getUserById(id);
    if (!user) {
      throw new NotFoundError(`User not found: ${id}`);
    }
    return user;
  }

  async createUser(data: CreateUserDTO): Promise<User> {
    try {
      return await this.userService.createUser(data);
    } catch (error) {
      if (error instanceof ValidationError) {
        throw error;
      }
      throw new AppError("Failed to create user");
    }
  }
}
```

**Async/Await with Types:**
```typescript
// API response type
interface ApiResponse<T> {
  data: T;
  status: number;
  message: string;
}

// Fetch with type safety
async function fetchUser(id: number): Promise<User> {
  const response = await fetch(`/api/users/${id}`);

  if (!response.ok) {
    throw new Error(`HTTP error: ${response.status}`);
  }

  const data: ApiResponse<User> = await response.json();
  return data.data;
}

// Multiple async operations
async function getUserWithOrders(userId: number): Promise<UserWithOrders> {
  const [user, orders] = await Promise.all([
    fetchUser(userId),
    fetchUserOrders(userId)
  ]);

  return { ...user, orders };
}

// Error handling
async function safelyFetchUser(id: number): Promise<User | null> {
  try {
    return await fetchUser(id);
  } catch (error) {
    console.error("Failed to fetch user:", error);
    return null;
  }
}
```

---

## Quick Reference Card

### Type Annotations
```typescript
// Primitives
let str: string = "hello";
let num: number = 42;
let bool: boolean = true;

// Arrays
let numbers: number[] = [1, 2, 3];
let strings: Array<string> = ["a", "b"];

// Objects
let user: { name: string; age: number } = { name: "John", age: 30 };

// Functions
function greet(name: string): string {
  return `Hello, ${name}`;
}

const add = (a: number, b: number): number => a + b;

// Union types
let id: string | number = "abc";

// Literal types
let status: "pending" | "approved" | "rejected" = "pending";
```

### Interfaces and Types
```typescript
// Interface
interface User {
  id: number;
  name: string;
  email?: string;          // optional
  readonly created: Date;   // readonly
}

// Type alias
type ID = string | number;
type Point = { x: number; y: number };

// Extending
interface Employee extends User {
  role: string;
}
```

### Generics
```typescript
// Generic function
function identity<T>(arg: T): T {
  return arg;
}

// Generic interface
interface Repository<T> {
  findAll(): T[];
  findById(id: number): T | null;
}

// Generic class
class Box<T> {
  constructor(public value: T) {}
}

// Constraints
function getLength<T extends { length: number }>(arg: T): number {
  return arg.length;
}
```

### Utility Types
```typescript
Partial<T>          // All properties optional
Required<T>         // All properties required
Readonly<T>         // All properties readonly
Pick<T, K>          // Select specific properties
Omit<T, K>          // Exclude specific properties
Record<K, T>        // Object with specific keys
ReturnType<T>       // Extract return type
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Set up and configure TypeScript projects
- [ ] Use basic types (string, number, boolean, arrays, objects)
- [ ] Define and implement interfaces
- [ ] Create classes with access modifiers and inheritance
- [ ] Write generic functions, classes, and interfaces
- [ ] Apply advanced types (unions, intersections, mapped types)
- [ ] Use decorators for metaprogramming
- [ ] Organize code with modules
- [ ] Apply TypeScript in real-world patterns
- [ ] Handle errors with custom error types
- [ ] Use async/await with proper typing
- [ ] Leverage TypeScript tooling and IDE features

---

## Next Steps

After mastering these concepts, proceed to:
- Apply TypeScript in framework projects (React, Angular, Vue)
- Explore advanced topics: conditional types, template literal types
- Learn TypeScript with Node.js and Express
- Practice building type-safe APIs
- Study TypeScript best practices and design patterns
- Contribute to TypeScript open-source projects

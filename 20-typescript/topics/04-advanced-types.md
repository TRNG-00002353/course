# TypeScript Advanced Types

## Type Aliases

Type aliases create custom names for types, making code more readable and reusable.

```typescript
// Primitive type alias
type ID = string | number;
let userId: ID = "abc123";
userId = 456;                          // OK

// Function type alias
type MathOperation = (x: number, y: number) => number;
const add: MathOperation = (x, y) => x + y;

// Object type alias
type Point = {
    x: number;
    y: number;
};

// Union type alias
type Status = "pending" | "approved" | "rejected";

// Tuple type alias
type Coordinate = [number, number];
```

---

## Interfaces

Interfaces define the structure of objects and classes.

### Basic Interfaces

```typescript
interface User {
    id: number;
    name: string;
    email: string;
}

const user: User = {
    id: 1,
    name: "Alice",
    email: "alice@example.com"
};

// Optional properties
interface Product {
    id: number;
    name: string;
    description?: string;              // Optional
}

// Readonly properties
interface Config {
    readonly apiKey: string;
    timeout: number;
}

const config: Config = { apiKey: "secret123", timeout: 5000 };
config.timeout = 10000;                // OK
config.apiKey = "newkey";              // Error: readonly
```

### Index Signatures

```typescript
interface StringMap {
    [key: string]: string;
}

const translations: StringMap = {
    hello: "Hola",
    goodbye: "Adiós"
};
```

### Extending Interfaces

```typescript
interface Animal {
    name: string;
    age: number;
}

interface Dog extends Animal {
    breed: string;
    bark(): void;
}

const myDog: Dog = {
    name: "Buddy",
    age: 3,
    breed: "Golden Retriever",
    bark() { console.log("Woof!"); }
};

// Multiple inheritance
interface Flyable {
    fly(): void;
}

interface Swimmable {
    swim(): void;
}

interface Duck extends Animal, Flyable, Swimmable {
    quack(): void;
}
```

### Type Aliases vs Interfaces

```typescript
// Interfaces can be extended
interface Person {
    name: string;
}
interface Employee extends Person {
    employeeId: number;
}

// Type aliases can represent unions, primitives
type ID = string | number;             // Can't do with interface

// Interface declaration merging
interface Window {
    title: string;
}
interface Window {
    tabs: number;
}
// Both declarations merge into one

// When to use what:
// - Use interfaces for object shapes and class contracts
// - Use type aliases for unions, intersections, primitives, tuples
```

### Intersection Types

Combine multiple types into one:

```typescript
type Timestamped = {
    createdAt: Date;
    updatedAt: Date;
};

type Article = {
    id: number;
    title: string;
} & Timestamped;

const article: Article = {
    id: 1,
    title: "TypeScript Guide",
    createdAt: new Date(),
    updatedAt: new Date()
};
```

---

## Type Casting

Type casting (type assertion) tells TypeScript to treat a value as a specific type.

### Basic Casting

```typescript
// Using 'as' syntax (recommended)
let someValue: unknown = "this is a string";
let strLength: number = (someValue as string).length;

// DOM elements
const input = document.getElementById("myInput") as HTMLInputElement;
input.value = "Hello";

// Null handling with casting
const element = document.getElementById("app");
if (element) {
    const div = element as HTMLDivElement;
    div.style.color = "blue";
}

// Non-null assertion operator
const element2 = document.getElementById("app")!; // Assert it's not null
```

### Const Assertions

```typescript
// Without const assertion
let config = {
    endpoint: "https://api.example.com",
    timeout: 5000
};
// Type: { endpoint: string; timeout: number }

// With const assertion
let config2 = {
    endpoint: "https://api.example.com",
    timeout: 5000
} as const;
// Type: { readonly endpoint: "https://api.example.com"; readonly timeout: 5000 }

// Array const assertion
const colors = ["red", "green", "blue"] as const;
// Type: readonly ["red", "green", "blue"]

type Color = typeof colors[number];    // "red" | "green" | "blue"
```

---

## Type Guards

Custom type guards for type narrowing:

```typescript
interface Fish {
    swim(): void;
}

interface Bird {
    fly(): void;
}

// Type guard with type predicate
function isFish(pet: Fish | Bird): pet is Fish {
    return (pet as Fish).swim !== undefined;
}

function movePet(pet: Fish | Bird) {
    if (isFish(pet)) {
        pet.swim();                    // TypeScript knows pet is Fish
    } else {
        pet.fly();                     // TypeScript knows pet is Bird
    }
}

// typeof guard
function process(value: string | number) {
    if (typeof value === "string") {
        return value.toUpperCase();    // value is string
    }
    return value.toFixed(2);           // value is number
}

// instanceof guard
function formatDate(value: Date | string): string {
    if (value instanceof Date) {
        return value.toISOString();
    }
    return value;
}
```

---

## Utility Types

TypeScript provides built-in utility types for common type transformations.

### Partial<T>

Makes all properties optional:

```typescript
interface User {
    id: number;
    name: string;
    email: string;
}

// All properties become optional
type PartialUser = Partial<User>;

// Use case: Update functions
function updateUser(id: number, updates: Partial<User>): User {
    const user = getUserById(id);
    return { ...user, ...updates };
}

updateUser(1, { name: "Alice" });      // OK - only update name
```

### Required<T>

Makes all properties required:

```typescript
interface Props {
    title?: string;
    description?: string;
}

type RequiredProps = Required<Props>;
// All properties become required
```

### Readonly<T>

Makes all properties readonly:

```typescript
interface Mutable {
    id: number;
    name: string;
}

type Immutable = Readonly<Mutable>;

const user: Immutable = { id: 1, name: "Alice" };
user.name = "Bob";                     // Error: readonly
```

### Record<K, T>

Creates an object type with specific keys and value type:

```typescript
type Roles = "admin" | "user" | "guest";
type Permissions = Record<Roles, string[]>;

const permissions: Permissions = {
    admin: ["read", "write", "delete"],
    user: ["read", "write"],
    guest: ["read"]
};
```

### Pick<T, K>

Creates a type by picking specific properties:

```typescript
interface User {
    id: number;
    name: string;
    email: string;
    password: string;
}

type UserPreview = Pick<User, "id" | "name">;
// Type: { id: number; name: string; }
```

### Omit<T, K>

Creates a type by omitting specific properties:

```typescript
type SafeUser = Omit<User, "password">;
// Type: { id: number; name: string; email: string; }

// Omit multiple properties
type PublicUser = Omit<User, "password" | "email">;
```

### Exclude<T, U>

Excludes types from a union:

```typescript
type AllTypes = string | number | boolean;
type StringAndNumber = Exclude<AllTypes, boolean>;
// Type: string | number

type Status = "pending" | "approved" | "rejected" | "cancelled";
type ActiveStatus = Exclude<Status, "rejected" | "cancelled">;
// Type: "pending" | "approved"
```

### Extract<T, U>

Extracts types from a union:

```typescript
type AllTypes = string | number | boolean;
type OnlyNumber = Extract<AllTypes, number>;
// Type: number
```

### NonNullable<T>

Removes null and undefined from a type:

```typescript
type MaybeString = string | null | undefined;
type DefiniteString = NonNullable<MaybeString>;
// Type: string
```

### ReturnType<T>

Extracts the return type of a function:

```typescript
function getUser() {
    return { id: 1, name: "Alice" };
}

type User = ReturnType<typeof getUser>;
// Type: { id: number; name: string; }
```

### Parameters<T>

Extracts parameter types as a tuple:

```typescript
function createUser(name: string, age: number) {
    return { name, age };
}

type CreateUserParams = Parameters<typeof createUser>;
// Type: [name: string, age: number]
```

### keyof Operator

Gets all property keys of a type:

```typescript
interface User {
    id: number;
    name: string;
    email: string;
}

type UserKeys = keyof User;
// Type: "id" | "name" | "email"

function getProperty<T, K extends keyof T>(obj: T, key: K): T[K] {
    return obj[key];
}

const user: User = { id: 1, name: "Alice", email: "alice@example.com" };
const name = getProperty(user, "name");  // string
```

---

## Summary

| Feature | Purpose | Example |
|---------|---------|---------|
| Type Alias | Custom type names | `type ID = string \| number` |
| Interface | Object structure | `interface User { name: string }` |
| Casting | Type assertion | `value as string` |
| as const | Readonly literals | `["a", "b"] as const` |
| Type Guard | Runtime type check | `pet is Fish` |
| Partial | All props optional | `Partial<User>` |
| Required | All props required | `Required<Props>` |
| Readonly | All props readonly | `Readonly<Config>` |
| Pick | Select properties | `Pick<User, "id" \| "name">` |
| Omit | Exclude properties | `Omit<User, "password">` |
| Record | Key-value mapping | `Record<string, number>` |
| keyof | Get property keys | `keyof User` |

## Next Topic

Continue to [Functions and Classes](./05-functions-and-classes.md)

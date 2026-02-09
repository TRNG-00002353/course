# TypeScript Basic Types

## Simple/Variable Types

### Primitive Types

#### String
```typescript
let firstName: string = "Alice";
let fullName: string = `${firstName} Smith`;

// Type inference
let greeting = "Hello";  // inferred as string
```

#### Number
```typescript
let age: number = 25;
let price: number = 99.99;
let hex: number = 0xff;

// Type inference
let count = 10;  // inferred as number
```

#### Boolean
```typescript
let isActive: boolean = true;
let isComplete: boolean = false;

// From expressions
let isAdult: boolean = age >= 18;
```

### Type Inference

TypeScript automatically infers types when possible:

```typescript
let name = "Alice";     // inferred as string
let age = 25;           // inferred as number
let active = true;      // inferred as boolean

function getAge() {
    return 25;          // return type inferred as number
}
```

---

## Special Types

### any

Disables type checking - **avoid when possible**:

```typescript
let flexible: any = 4;
flexible = "string";    // OK
flexible = false;       // OK
flexible.anything();    // OK - no checking (might fail at runtime!)
```

### unknown

Type-safe alternative to `any` - must check type before use:

```typescript
let userInput: unknown = "hello";

// Error: can't use directly
// console.log(userInput.toUpperCase());

// Must narrow type first
if (typeof userInput === "string") {
    console.log(userInput.toUpperCase());  // OK
}
```

### void

Functions that don't return a value:

```typescript
function logMessage(message: string): void {
    console.log(message);
}
```

### never

Functions that never return (throw or infinite loop):

```typescript
function throwError(message: string): never {
    throw new Error(message);
}
```

### null and undefined

```typescript
let nullValue: null = null;
let undefinedValue: undefined = undefined;

// With strictNullChecks, must explicitly allow null
let name: string | null = null;
name = "Alice";  // OK
name = null;     // OK
```

---

## Object Types

### Basic Objects

```typescript
// Inline object type
let person: { name: string; age: number } = {
    name: "Alice",
    age: 30
};

// Optional properties
let user: { id: number; email?: string } = {
    id: 1
    // email is optional
};

// Readonly properties
let config: { readonly apiUrl: string } = {
    apiUrl: "https://api.example.com"
};
// config.apiUrl = "new";  // Error: readonly
```

### Arrays

```typescript
// Two syntaxes
let numbers: number[] = [1, 2, 3];
let strings: Array<string> = ["a", "b", "c"];

// Array of objects
let users: { id: number; name: string }[] = [
    { id: 1, name: "Alice" },
    { id: 2, name: "Bob" }
];

// Type-safe operations
numbers.push(4);      // OK
numbers.push("5");    // Error: string not assignable to number
```

### Tuples

Fixed-length arrays with specific types per position:

```typescript
// Basic tuple
let point: [number, number] = [10, 20];

// Mixed types
let person: [string, number] = ["Alice", 30];
let [name, age] = person;  // Destructuring

// Optional elements
let response: [number, string?] = [200];
response = [404, "Not Found"];
```

### Enums

Named constants:

```typescript
// Numeric enum (auto-incremented from 0)
enum Direction {
    Up,      // 0
    Down,    // 1
    Left,    // 2
    Right    // 3
}
let move: Direction = Direction.Up;

// String enum (recommended)
enum Status {
    Pending = "PENDING",
    Active = "ACTIVE",
    Completed = "COMPLETED"
}
let status: Status = Status.Active;
console.log(status);  // "ACTIVE"

// Using in switch
function handleStatus(s: Status): string {
    switch (s) {
        case Status.Pending: return "Waiting...";
        case Status.Active: return "In progress";
        case Status.Completed: return "Done!";
    }
}
```

---

## Union Types

Variables that can be one of several types:

### Basic Unions

```typescript
let id: number | string;
id = 101;       // OK
id = "ABC";     // OK
id = true;      // Error

function printId(id: number | string): void {
    console.log(`ID: ${id}`);
}
```

### Type Narrowing

TypeScript narrows types based on checks:

```typescript
function process(value: string | number): string {
    if (typeof value === "string") {
        return value.toUpperCase();  // value is string here
    } else {
        return value.toFixed(2);     // value is number here
    }
}

// Narrowing with truthiness
function printLength(text: string | null): void {
    if (text) {
        console.log(text.length);    // text is string here
    }
}

// Narrowing with instanceof
function formatDate(value: Date | string): string {
    if (value instanceof Date) {
        return value.toISOString();
    }
    return value;
}
```

### Literal Types

Specific literal values:

```typescript
type Direction = "north" | "south" | "east" | "west";
let heading: Direction = "north";  // OK
heading = "up";  // Error

type DiceRoll = 1 | 2 | 3 | 4 | 5 | 6;
let roll: DiceRoll = 4;  // OK
roll = 7;  // Error

type HttpMethod = "GET" | "POST" | "PUT" | "DELETE";
function request(method: HttpMethod, url: string): void {
    // ...
}
request("GET", "/api/users");   // OK
request("PATCH", "/api/users"); // Error
```

### Discriminated Unions

Object unions with a common property to distinguish types:

```typescript
interface Dog {
    type: "dog";
    bark(): void;
}

interface Cat {
    type: "cat";
    meow(): void;
}

type Pet = Dog | Cat;

function handlePet(pet: Pet): void {
    if (pet.type === "dog") {
        pet.bark();   // pet is Dog here
    } else {
        pet.meow();   // pet is Cat here
    }
}
```

---

## Type Assertions (Casting)

Tell TypeScript to treat a value as a specific type:

### as Syntax (Recommended)

```typescript
let value: unknown = "hello";
let length: number = (value as string).length;

// DOM elements
let input = document.getElementById("myInput") as HTMLInputElement;
input.value = "Hello";
```

### as const

Makes values readonly and literal:

```typescript
// Without as const
let color1 = "red";        // type: string

// With as const
let color2 = "red" as const;  // type: "red" (literal)

// Readonly object
const config = {
    api: "https://api.example.com",
    timeout: 5000
} as const;
// config.timeout = 10000;  // Error: readonly
```

---

## Summary

| Type | Example | Use Case |
|------|---------|----------|
| `string` | `"hello"` | Text |
| `number` | `42`, `3.14` | Numbers |
| `boolean` | `true`, `false` | Flags |
| `any` | Anything | Avoid - disables checking |
| `unknown` | Must check first | Safe alternative to any |
| `void` | No return | Functions without return |
| `never` | Never returns | Throw/infinite loop |
| `array` | `number[]` | Lists |
| `tuple` | `[string, number]` | Fixed-length arrays |
| `enum` | Named constants | Status codes, directions |
| `union` | `string \| number` | Multiple possible types |
| `literal` | `"GET" \| "POST"` | Specific values |

## Best Practices

```typescript
// Let TypeScript infer when obvious
let count = 0;                    // number inferred

// Be explicit for complex types
let ids: (string | number)[] = [];

// Avoid 'any', use 'unknown' instead
let data: unknown = JSON.parse(input);

// Use literal unions for fixed values
type Status = "pending" | "active" | "done";
```

## Next Topic

Continue to [Advanced Types](./04-advanced-types.md)

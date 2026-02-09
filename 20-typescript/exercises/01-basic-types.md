# Exercise Set 1: Basic Type Annotations

Practice TypeScript's fundamental type annotations with these progressive exercises.

---

## Exercise 1.1: Variable Type Annotations

Add proper type annotations to these variable declarations.

```typescript
// TODO: Add type annotations
let userName = "Alice";
let userAge = 25;
let isActive = true;
let accountBalance = 1500.50;
let memberSince = new Date("2020-01-15");
```

<details>
<summary>Solution</summary>

```typescript
let userName: string = "Alice";
let userAge: number = 25;
let isActive: boolean = true;
let accountBalance: number = 1500.50;
let memberSince: Date = new Date("2020-01-15");
```

</details>

---

## Exercise 1.2: Function Parameter Types

Add type annotations to function parameters and return types.

```typescript
// TODO: Add type annotations to parameters and return type
function calculateArea(width, height) {
    return width * height;
}

function greetUser(name, age) {
    return `Hello ${name}, you are ${age} years old.`;
}

function isEligible(age) {
    return age >= 18;
}
```

<details>
<summary>Solution</summary>

```typescript
function calculateArea(width: number, height: number): number {
    return width * height;
}

function greetUser(name: string, age: number): string {
    return `Hello ${name}, you are ${age} years old.`;
}

function isEligible(age: number): boolean {
    return age >= 18;
}
```

</details>

---

## Exercise 1.3: Fix Type Errors

The following code has type errors. Fix them without changing the logic.

```typescript
let productName: string = 100;
let quantity: number = "five";
let inStock: boolean = "yes";

function multiply(a: number, b: number): string {
    return a * b;
}

let result: string = multiply(5, 10);
```

<details>
<summary>Solution</summary>

```typescript
let productName: string = "Widget";  // Changed value to string
let quantity: number = 5;            // Changed value to number
let inStock: boolean = true;         // Changed value to boolean

function multiply(a: number, b: number): number {  // Changed return type
    return a * b;
}

let result: number = multiply(5, 10);  // Changed variable type
```

</details>

---

## Exercise 1.4: Type Inference

For each declaration, predict what type TypeScript will infer.

```typescript
// What type is inferred for each variable?

const firstName = "John";           // Type: ?
let counter = 0;                    // Type: ?
const PI = 3.14159;                 // Type: ?
let prices = [10.99, 20.50, 5.25];  // Type: ?
const isValid = true;               // Type: ?

function getFullName(first: string, last: string) {
    return first + " " + last;      // Return type: ?
}
```

<details>
<summary>Solution</summary>

```typescript
const firstName = "John";           // Type: "John" (literal type due to const)
let counter = 0;                    // Type: number
const PI = 3.14159;                 // Type: 3.14159 (literal type due to const)
let prices = [10.99, 20.50, 5.25];  // Type: number[]
const isValid = true;               // Type: true (literal type due to const)

function getFullName(first: string, last: string) {
    return first + " " + last;      // Return type: string (inferred)
}
```

</details>

---

## Exercise 1.5: Optional Parameters

Create a function that formats a user profile with optional middle name and suffix.

```typescript
// TODO: Create formatName function
// - firstName is required
// - middleName is optional
// - lastName is required
// - suffix is optional (e.g., "Jr.", "PhD")
// Returns: "John Smith" or "John Michael Smith" or "John Smith, PhD"

// Test cases:
console.log(formatName("John", undefined, "Smith"));           // "John Smith"
console.log(formatName("John", "Michael", "Smith"));           // "John Michael Smith"
console.log(formatName("John", undefined, "Smith", "PhD"));    // "John Smith, PhD"
console.log(formatName("John", "Michael", "Smith", "Jr."));    // "John Michael Smith, Jr."
```

<details>
<summary>Solution</summary>

```typescript
function formatName(
    firstName: string,
    middleName: string | undefined,
    lastName: string,
    suffix?: string
): string {
    let fullName = firstName;

    if (middleName) {
        fullName += " " + middleName;
    }

    fullName += " " + lastName;

    if (suffix) {
        fullName += ", " + suffix;
    }

    return fullName;
}

// Test cases
console.log(formatName("John", undefined, "Smith"));           // "John Smith"
console.log(formatName("John", "Michael", "Smith"));           // "John Michael Smith"
console.log(formatName("John", undefined, "Smith", "PhD"));    // "John Smith, PhD"
console.log(formatName("John", "Michael", "Smith", "Jr."));    // "John Michael Smith, Jr."
```

</details>

---

## Exercise 1.6: Void and Never

Identify which return type each function should have: `void`, `never`, or a specific type.

```typescript
// TODO: Add the correct return type to each function

function logMessage(message: string) {
    console.log(`[LOG]: ${message}`);
}

function throwError(message: string) {
    throw new Error(message);
}

function processItems(items: string[]) {
    items.forEach(item => console.log(item));
}

function infiniteLoop() {
    while (true) {
        // Process indefinitely
    }
}

function getLength(text: string) {
    return text.length;
}
```

<details>
<summary>Solution</summary>

```typescript
function logMessage(message: string): void {
    console.log(`[LOG]: ${message}`);
}

function throwError(message: string): never {
    throw new Error(message);
}

function processItems(items: string[]): void {
    items.forEach(item => console.log(item));
}

function infiniteLoop(): never {
    while (true) {
        // Process indefinitely
    }
}

function getLength(text: string): number {
    return text.length;
}
```

</details>

---

## Challenge: Build a Simple Calculator

Create a calculator module with properly typed functions.

```typescript
// TODO: Implement these calculator functions with proper types

// 1. add - adds two numbers
// 2. subtract - subtracts second from first
// 3. multiply - multiplies two numbers
// 4. divide - divides first by second (return null if dividing by zero)
// 5. calculate - takes two numbers and an operation string
//    operation can be: "add", "subtract", "multiply", "divide"

// Test cases:
console.log(add(5, 3));              // 8
console.log(subtract(10, 4));        // 6
console.log(multiply(3, 7));         // 21
console.log(divide(20, 5));          // 4
console.log(divide(10, 0));          // null
console.log(calculate(10, 5, "add")); // 15
```

<details>
<summary>Solution</summary>

```typescript
function add(a: number, b: number): number {
    return a + b;
}

function subtract(a: number, b: number): number {
    return a - b;
}

function multiply(a: number, b: number): number {
    return a * b;
}

function divide(a: number, b: number): number | null {
    if (b === 0) {
        return null;
    }
    return a / b;
}

function calculate(
    a: number,
    b: number,
    operation: "add" | "subtract" | "multiply" | "divide"
): number | null {
    switch (operation) {
        case "add":
            return add(a, b);
        case "subtract":
            return subtract(a, b);
        case "multiply":
            return multiply(a, b);
        case "divide":
            return divide(a, b);
    }
}

// Test cases
console.log(add(5, 3));              // 8
console.log(subtract(10, 4));        // 6
console.log(multiply(3, 7));         // 21
console.log(divide(20, 5));          // 4
console.log(divide(10, 0));          // null
console.log(calculate(10, 5, "add")); // 15
```

</details>

---

## Next Steps

Continue to [Exercise Set 2: Arrays, Tuples, and Enums](./02-arrays-tuples-enums.md)

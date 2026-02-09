# TypeScript Introduction

## What is TypeScript?

TypeScript is a superset of JavaScript that adds optional static typing. It compiles to plain JavaScript, meaning any valid JavaScript code is also valid TypeScript code.

### Key Characteristics

- **Superset of JavaScript**: All JavaScript code is valid TypeScript
- **Static Typing**: Optional type annotations for better tooling and error detection
- **Compile-time Checking**: Catches errors before runtime
- **Tool Support**: Excellent IDE support with IntelliSense and refactoring

### Compilation Process

```
TypeScript Code (.ts)
        |
        | TypeScript Compiler (tsc)
        v
JavaScript Code (.js)
        |
        | JavaScript Runtime (Node.js, Browser)
        v
    Execution
```

---

## JavaScript vs TypeScript

| Feature | JavaScript | TypeScript |
|---------|-----------|------------|
| Type System | Dynamic typing | Static typing (optional) |
| Type Errors | Runtime | Compile-time |
| Tooling | Basic | Advanced (IntelliSense) |
| File Extension | .js | .ts, .tsx |
| Browser Support | Native | Requires compilation |

### Code Comparison

**JavaScript - Errors at Runtime:**
```javascript
function add(a, b) {
    return a + b;
}

add(5, 10);      // 15 - works
add("5", 10);    // "510" - unexpected string concatenation!
add(5, null);    // 5 - unexpected behavior
```

**TypeScript - Errors at Compile-time:**
```typescript
function add(a: number, b: number): number {
    return a + b;
}

add(5, 10);      // 15 - works
add("5", 10);    // Error: Argument of type 'string' is not assignable to type 'number'
add(5, null);    // Error: Argument of type 'null' is not assignable to type 'number'
```

**TypeScript Catches Typos:**
```typescript
interface User {
    name: string;
    age: number;
}

const user: User = { name: "Alice", age: 30 };

console.log(user.nmae);  // Error: Property 'nmae' does not exist. Did you mean 'name'?
```

---

## Why Use TypeScript?

### 1. Early Error Detection
```typescript
interface Config {
    apiUrl: string;
    timeout: number;
}

function initializeApp(config: Config) {
    fetch(config.apiUrl);
}

initializeApp({ apiUrl: "https://api.example.com" });
// Error: Property 'timeout' is missing
```

### 2. Better IDE Support
```typescript
const text = "Hello";
text.  // IDE suggests: charAt, concat, includes, indexOf, etc.

interface Customer {
    firstName: string;
    lastName: string;
}

function process(customer: Customer) {
    customer.  // IDE suggests: firstName, lastName
}
```

### 3. Self-Documenting Code
```typescript
// Function signature documents expected types
function calculateShipping(
    weight: number,      // in kilograms
    distance: number,    // in kilometers
    isExpress: boolean
): number {              // returns price
    return weight * distance * (isExpress ? 2 : 1);
}
```

### 4. Easier Refactoring
When you rename a property, TypeScript flags all places that need updating.

---

## When to Use TypeScript

**Use TypeScript for:**
- Large applications
- Team projects
- Long-term projects
- Angular development (required)

**Consider plain JavaScript for:**
- Small scripts
- Quick prototypes

---

## Summary

| Concept | Key Points |
|---------|------------|
| What is TypeScript | Superset of JavaScript with static typing |
| JS vs TS | TS adds compile-time checking, better tooling |
| Why Use | Early error detection, IDE support, self-documenting |

## Next Topic

Continue to [Setup and Configuration](./02-setup-and-configuration.md)

# Exercise Set 4: Type Aliases and Interfaces

Learn to define and use custom types effectively.

---

## Exercise 4.1: Basic Interfaces

Create interfaces for common data structures.

```typescript
// TODO: Define interfaces for the following

// 1. User interface with id, username, email, isAdmin

// 2. Product interface with id, name, price, description (optional), inStock

// 3. Address interface with street, city, state, zipCode, country (optional)

// 4. Create objects that match these interfaces
const user = // Create a user

const product = // Create a product

const address = // Create an address
```

<details>
<summary>Solution</summary>

```typescript
// 1. User interface
interface User {
    id: number;
    username: string;
    email: string;
    isAdmin: boolean;
}

// 2. Product interface
interface Product {
    id: number;
    name: string;
    price: number;
    description?: string;
    inStock: boolean;
}

// 3. Address interface
interface Address {
    street: string;
    city: string;
    state: string;
    zipCode: string;
    country?: string;
}

// 4. Create objects
const user: User = {
    id: 1,
    username: "alice123",
    email: "alice@example.com",
    isAdmin: false
};

const product: Product = {
    id: 101,
    name: "Wireless Mouse",
    price: 29.99,
    inStock: true
};

const address: Address = {
    street: "123 Main St",
    city: "Boston",
    state: "MA",
    zipCode: "02101"
};
```

</details>

---

## Exercise 4.2: Interface Methods

Add method signatures to interfaces.

```typescript
// TODO: Add methods to these interfaces

interface Calculator {
    // Methods: add, subtract, multiply, divide (all take two numbers, return number)
    // Method: clear (no params, returns void)
    // Method: getHistory (no params, returns string array)
}

interface Logger {
    // Method: log (message: string) returns void
    // Method: error (message: string, code?: number) returns void
    // Method: setLevel (level: "debug" | "info" | "warn" | "error") returns void
}

interface Validator {
    // Method: validate (value: unknown) returns boolean
    // Method: getErrors () returns string[]
    // Property: isValid (readonly boolean)
}

// Implement one of these interfaces
class ConsoleLogger implements Logger {
    // TODO: Implement
}
```

<details>
<summary>Solution</summary>

```typescript
interface Calculator {
    add(a: number, b: number): number;
    subtract(a: number, b: number): number;
    multiply(a: number, b: number): number;
    divide(a: number, b: number): number;
    clear(): void;
    getHistory(): string[];
}

interface Logger {
    log(message: string): void;
    error(message: string, code?: number): void;
    setLevel(level: "debug" | "info" | "warn" | "error"): void;
}

interface Validator {
    validate(value: unknown): boolean;
    getErrors(): string[];
    readonly isValid: boolean;
}

// Implementation
class ConsoleLogger implements Logger {
    private level: "debug" | "info" | "warn" | "error" = "info";

    log(message: string): void {
        console.log(`[LOG] ${message}`);
    }

    error(message: string, code?: number): void {
        if (code !== undefined) {
            console.error(`[ERROR ${code}] ${message}`);
        } else {
            console.error(`[ERROR] ${message}`);
        }
    }

    setLevel(level: "debug" | "info" | "warn" | "error"): void {
        this.level = level;
        console.log(`Log level set to: ${level}`);
    }
}
```

</details>

---

## Exercise 4.3: Extending Interfaces

Create interface hierarchies using extends.

```typescript
// TODO: Create a hierarchy of interfaces

// Base: Entity (id: number, createdAt: Date, updatedAt: Date)

// Extends Entity: Person (firstName, lastName, email)

// Extends Person: Employee (employeeId, department, salary)

// Extends Person: Customer (customerId, loyaltyPoints)

// Create instances of Employee and Customer
```

<details>
<summary>Solution</summary>

```typescript
// Base interface
interface Entity {
    id: number;
    createdAt: Date;
    updatedAt: Date;
}

// Person extends Entity
interface Person extends Entity {
    firstName: string;
    lastName: string;
    email: string;
}

// Employee extends Person
interface Employee extends Person {
    employeeId: string;
    department: string;
    salary: number;
}

// Customer extends Person
interface Customer extends Person {
    customerId: string;
    loyaltyPoints: number;
}

// Instances
const employee: Employee = {
    id: 1,
    createdAt: new Date("2020-01-15"),
    updatedAt: new Date(),
    firstName: "Alice",
    lastName: "Smith",
    email: "alice.smith@company.com",
    employeeId: "EMP-001",
    department: "Engineering",
    salary: 85000
};

const customer: Customer = {
    id: 2,
    createdAt: new Date("2021-06-20"),
    updatedAt: new Date(),
    firstName: "Bob",
    lastName: "Johnson",
    email: "bob.johnson@email.com",
    customerId: "CUST-002",
    loyaltyPoints: 1500
};
```

</details>

---

## Exercise 4.4: Index Signatures

Use index signatures for dynamic object shapes.

```typescript
// TODO: Define interfaces with index signatures

// 1. Dictionary: string keys, string values
interface Dictionary {
    // TODO
}

// 2. NumberMap: string keys, number values, with a required 'total' property
interface NumberMap {
    // TODO
}

// 3. ConfigMap: string keys, values can be string, number, or boolean
interface ConfigMap {
    // TODO
}

// 4. Implement a word frequency counter
function countWords(text: string): Dictionary {
    // Split text and count each word
}

// Test:
console.log(countWords("the quick brown fox jumps over the lazy dog"));
// { the: "2", quick: "1", brown: "1", ... }
```

<details>
<summary>Solution</summary>

```typescript
// 1. Dictionary: string keys, string values
interface Dictionary {
    [key: string]: string;
}

// 2. NumberMap: string keys, number values, with required 'total'
interface NumberMap {
    [key: string]: number;
    total: number;
}

// 3. ConfigMap: string keys, mixed values
interface ConfigMap {
    [key: string]: string | number | boolean;
}

// 4. Word frequency counter (modified to return numbers)
interface WordCount {
    [word: string]: number;
}

function countWords(text: string): WordCount {
    const words = text.toLowerCase().split(/\s+/);
    const counts: WordCount = {};

    for (const word of words) {
        counts[word] = (counts[word] || 0) + 1;
    }

    return counts;
}

// Test:
console.log(countWords("the quick brown fox jumps over the lazy dog"));
// { the: 2, quick: 1, brown: 1, fox: 1, jumps: 1, over: 1, lazy: 1, dog: 1 }

// Using Dictionary (string values):
function countWordsAsStrings(text: string): Dictionary {
    const counts = countWords(text);
    const result: Dictionary = {};

    for (const [word, count] of Object.entries(counts)) {
        result[word] = count.toString();
    }

    return result;
}
```

</details>

---

## Exercise 4.5: Type Aliases vs Interfaces

Understand when to use each.

```typescript
// TODO: For each scenario, decide: type alias or interface?

// 1. A union of string literals for status
// Answer: ___

// 2. An object representing a User entity that might be extended
// Answer: ___

// 3. A function signature for event handlers
// Answer: ___

// 4. A tuple representing coordinates
// Answer: ___

// 5. An object that a class will implement
// Answer: ___

// 6. A conditional type based on input
// Answer: ___

// Now implement each:
```

<details>
<summary>Solution</summary>

```typescript
// 1. Union of string literals - TYPE ALIAS
type Status = "pending" | "active" | "completed" | "cancelled";

// 2. User entity that might be extended - INTERFACE
interface User {
    id: number;
    name: string;
    email: string;
}

interface AdminUser extends User {
    permissions: string[];
}

// 3. Function signature - TYPE ALIAS (more readable for functions)
type EventHandler = (event: Event) => void;
type ClickHandler = (event: MouseEvent) => void;

// 4. Tuple - TYPE ALIAS
type Coordinate = [number, number];
type RGB = [number, number, number];

// 5. Object for class implementation - INTERFACE
interface Serializable {
    serialize(): string;
    deserialize(data: string): void;
}

class Document implements Serializable {
    constructor(public content: string) {}

    serialize(): string {
        return JSON.stringify({ content: this.content });
    }

    deserialize(data: string): void {
        const parsed = JSON.parse(data);
        this.content = parsed.content;
    }
}

// 6. Conditional type - TYPE ALIAS (only option)
type NonNullable<T> = T extends null | undefined ? never : T;
type Flatten<T> = T extends Array<infer U> ? U : T;
```

**Guidelines:**
- Use **interface** for: Object shapes, class contracts, things that might be extended
- Use **type alias** for: Unions, tuples, function types, mapped types, conditional types

</details>

---

## Exercise 4.6: Readonly and Immutability

Create immutable data structures.

```typescript
// TODO: Make these interfaces immutable

interface Config {
    apiUrl: string;
    timeout: number;
    features: string[];
}

interface User {
    id: number;
    name: string;
    settings: {
        theme: string;
        notifications: boolean;
    };
}

// 1. Create a readonly version of Config
type ReadonlyConfig = // TODO

// 2. Create a deeply readonly version of User
type DeepReadonlyUser = // TODO (hint: use Readonly recursively)

// 3. Create a function that freezes an object deeply
function deepFreeze<T>(obj: T): Readonly<T> {
    // TODO
}

// 4. Demonstrate that readonly prevents modification
const config: ReadonlyConfig = {
    apiUrl: "https://api.example.com",
    timeout: 5000,
    features: ["auth", "logging"]
};

// This should cause a TypeScript error:
// config.timeout = 10000;
```

<details>
<summary>Solution</summary>

```typescript
interface Config {
    apiUrl: string;
    timeout: number;
    features: string[];
}

interface User {
    id: number;
    name: string;
    settings: {
        theme: string;
        notifications: boolean;
    };
}

// 1. Readonly version of Config
type ReadonlyConfig = Readonly<Config>;
// Note: features array is still mutable internally!

// 2. Deeply readonly version (custom type)
type DeepReadonly<T> = {
    readonly [P in keyof T]: T[P] extends object
        ? DeepReadonly<T[P]>
        : T[P];
};

type DeepReadonlyUser = DeepReadonly<User>;

// 3. Deep freeze function
function deepFreeze<T extends object>(obj: T): Readonly<T> {
    Object.keys(obj).forEach(key => {
        const value = (obj as Record<string, unknown>)[key];
        if (value && typeof value === "object") {
            deepFreeze(value as object);
        }
    });
    return Object.freeze(obj);
}

// 4. Demonstration
const config: ReadonlyConfig = {
    apiUrl: "https://api.example.com",
    timeout: 5000,
    features: ["auth", "logging"]
};

// These would cause TypeScript errors:
// config.timeout = 10000;  // Error: Cannot assign to 'timeout'
// config.apiUrl = "new";   // Error: Cannot assign to 'apiUrl'

// Note: This still works with Readonly (shallow):
// config.features.push("new");  // No error! Array methods still work

// For true deep immutability:
const user: DeepReadonlyUser = {
    id: 1,
    name: "Alice",
    settings: {
        theme: "dark",
        notifications: true
    }
};

// user.settings.theme = "light";  // Error with DeepReadonly!
```

</details>

---

## Challenge: E-commerce Type System

Build a complete type system for an e-commerce application.

```typescript
// TODO: Design and implement these types

// Categories (use enum or union)
// ProductStatus: draft, published, archived
// OrderStatus: pending, confirmed, shipped, delivered, cancelled

// Core interfaces:
// Product: id, name, description, price, category, status, inventory, images[]

// CartItem: product, quantity, priceAtAddition

// Customer: id, firstName, lastName, email, addresses[], defaultAddressId?

// Order: id, customer, items[], status, shippingAddress, billingAddress,
//        subtotal, tax, total, createdAt

// Implement these functions with proper types:

function createProduct(input: /* partial product data */): Product {
    // Create a new product with defaults
}

function addToCart(cart: CartItem[], product: Product, quantity: number): CartItem[] {
    // Add or update item in cart
}

function calculateOrderTotal(items: CartItem[], taxRate: number): {
    subtotal: number;
    tax: number;
    total: number;
} {
    // Calculate order totals
}

function validateOrder(order: Order): string[] {
    // Return array of validation errors (empty if valid)
}
```

<details>
<summary>Solution</summary>

```typescript
// Enums/Types for status
type ProductStatus = "draft" | "published" | "archived";
type OrderStatus = "pending" | "confirmed" | "shipped" | "delivered" | "cancelled";

enum Category {
    Electronics = "electronics",
    Clothing = "clothing",
    Books = "books",
    Home = "home",
    Sports = "sports"
}

// Address interface
interface Address {
    id: string;
    street: string;
    city: string;
    state: string;
    zipCode: string;
    country: string;
    isDefault?: boolean;
}

// Product interface
interface Product {
    id: string;
    name: string;
    description: string;
    price: number;
    category: Category;
    status: ProductStatus;
    inventory: number;
    images: string[];
    createdAt: Date;
    updatedAt: Date;
}

// CartItem interface
interface CartItem {
    product: Product;
    quantity: number;
    priceAtAddition: number;
}

// Customer interface
interface Customer {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    addresses: Address[];
    defaultAddressId?: string;
}

// Order interface
interface Order {
    id: string;
    customer: Customer;
    items: CartItem[];
    status: OrderStatus;
    shippingAddress: Address;
    billingAddress: Address;
    subtotal: number;
    tax: number;
    total: number;
    createdAt: Date;
}

// Input type for creating products
interface CreateProductInput {
    name: string;
    description: string;
    price: number;
    category: Category;
    inventory?: number;
    images?: string[];
}

// Functions
function createProduct(input: CreateProductInput): Product {
    return {
        id: `PROD-${Date.now()}`,
        name: input.name,
        description: input.description,
        price: input.price,
        category: input.category,
        status: "draft",
        inventory: input.inventory ?? 0,
        images: input.images ?? [],
        createdAt: new Date(),
        updatedAt: new Date()
    };
}

function addToCart(cart: CartItem[], product: Product, quantity: number): CartItem[] {
    const existingIndex = cart.findIndex(item => item.product.id === product.id);

    if (existingIndex >= 0) {
        // Update existing item
        const newCart = [...cart];
        newCart[existingIndex] = {
            ...newCart[existingIndex],
            quantity: newCart[existingIndex].quantity + quantity
        };
        return newCart;
    } else {
        // Add new item
        return [...cart, {
            product,
            quantity,
            priceAtAddition: product.price
        }];
    }
}

function calculateOrderTotal(items: CartItem[], taxRate: number): {
    subtotal: number;
    tax: number;
    total: number;
} {
    const subtotal = items.reduce((sum, item) => {
        return sum + (item.priceAtAddition * item.quantity);
    }, 0);

    const tax = subtotal * taxRate;
    const total = subtotal + tax;

    return {
        subtotal: Math.round(subtotal * 100) / 100,
        tax: Math.round(tax * 100) / 100,
        total: Math.round(total * 100) / 100
    };
}

function validateOrder(order: Order): string[] {
    const errors: string[] = [];

    if (order.items.length === 0) {
        errors.push("Order must have at least one item");
    }

    for (const item of order.items) {
        if (item.quantity > item.product.inventory) {
            errors.push(`Insufficient inventory for ${item.product.name}`);
        }
        if (item.product.status !== "published") {
            errors.push(`Product ${item.product.name} is not available`);
        }
    }

    if (!order.shippingAddress.zipCode) {
        errors.push("Shipping address must have a zip code");
    }

    if (order.total <= 0) {
        errors.push("Order total must be greater than zero");
    }

    return errors;
}

// Test
const laptop = createProduct({
    name: "Laptop Pro",
    description: "High-performance laptop",
    price: 1299.99,
    category: Category.Electronics,
    inventory: 10
});

let cart: CartItem[] = [];
cart = addToCart(cart, laptop, 2);

const totals = calculateOrderTotal(cart, 0.08);
console.log(totals);  // { subtotal: 2599.98, tax: 207.99, total: 2807.97 }
```

</details>

---

## Next Steps

Continue to [Exercise Set 5: Utility Types](./05-utility-types.md)

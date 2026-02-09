# Exercise Set 2: Arrays, Tuples, and Enums

Practice working with TypeScript's collection types and enums.

---

## Exercise 2.1: Array Type Annotations

Add proper type annotations to these array declarations.

```typescript
// TODO: Add type annotations

let names = ["Alice", "Bob", "Charlie"];
let scores = [95, 87, 92, 78];
let flags = [true, false, true];
let mixed = [];  // Will contain numbers

// Array of objects
let products = [
    { id: 1, name: "Widget", price: 9.99 },
    { id: 2, name: "Gadget", price: 19.99 }
];
```

<details>
<summary>Solution</summary>

```typescript
let names: string[] = ["Alice", "Bob", "Charlie"];
let scores: number[] = [95, 87, 92, 78];
let flags: boolean[] = [true, false, true];
let mixed: number[] = [];

// Array of objects
let products: { id: number; name: string; price: number }[] = [
    { id: 1, name: "Widget", price: 9.99 },
    { id: 2, name: "Gadget", price: 19.99 }
];
```

</details>

---

## Exercise 2.2: Array Methods with Types

Complete these array operations with proper type annotations.

```typescript
const numbers: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

// TODO: Add return type annotations to each variable

// Filter even numbers
const evens = numbers.filter(n => n % 2 === 0);

// Double each number
const doubled = numbers.map(n => n * 2);

// Sum all numbers
const sum = numbers.reduce((acc, n) => acc + n, 0);

// Find first number greater than 5
const firstBig = numbers.find(n => n > 5);

// Check if all numbers are positive
const allPositive = numbers.every(n => n > 0);

// Convert to strings
const stringNumbers = numbers.map(n => n.toString());
```

<details>
<summary>Solution</summary>

```typescript
const numbers: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

// Filter even numbers
const evens: number[] = numbers.filter(n => n % 2 === 0);

// Double each number
const doubled: number[] = numbers.map(n => n * 2);

// Sum all numbers
const sum: number = numbers.reduce((acc, n) => acc + n, 0);

// Find first number greater than 5
const firstBig: number | undefined = numbers.find(n => n > 5);

// Check if all numbers are positive
const allPositive: boolean = numbers.every(n => n > 0);

// Convert to strings
const stringNumbers: string[] = numbers.map(n => n.toString());
```

</details>

---

## Exercise 2.3: Tuple Types

Create and use tuples for structured data.

```typescript
// TODO: Define tuple types and create instances

// 1. Coordinate: x and y as numbers
let point;  // Should be [10, 20]

// 2. RGB Color: three numbers 0-255
let red;    // Should be [255, 0, 0]

// 3. User record: id (number), name (string), active (boolean)
let user;   // Should be [1, "Alice", true]

// 4. Named tuple for HTTP response: status code, message, data
let response;  // Should be [200, "OK", { items: [] }]

// 5. Function that returns a tuple [success, data or error]
function parseNumber(input: string) {
    // TODO: Return [true, number] or [false, error message]
}
```

<details>
<summary>Solution</summary>

```typescript
// 1. Coordinate: x and y as numbers
let point: [number, number] = [10, 20];

// 2. RGB Color: three numbers 0-255
let red: [number, number, number] = [255, 0, 0];

// 3. User record: id (number), name (string), active (boolean)
let user: [number, string, boolean] = [1, "Alice", true];

// 4. Named tuple for HTTP response: status code, message, data
let response: [status: number, message: string, data: object] = [200, "OK", { items: [] }];

// 5. Function that returns a tuple [success, data or error]
function parseNumber(input: string): [boolean, number | string] {
    const num = parseInt(input);
    if (isNaN(num)) {
        return [false, `"${input}" is not a valid number`];
    }
    return [true, num];
}

// Usage:
const [success, result] = parseNumber("42");
if (success) {
    console.log("Parsed:", result);  // result is number | string here
}
```

</details>

---

## Exercise 2.4: Enum Basics

Create and use enums for fixed sets of values.

```typescript
// TODO: Create the following enums

// 1. OrderStatus: Pending, Processing, Shipped, Delivered, Cancelled

// 2. HttpMethod: GET, POST, PUT, DELETE, PATCH (use string values)

// 3. LogLevel: Debug=0, Info=1, Warning=2, Error=3, Critical=4

// 4. Write a function that returns a message based on OrderStatus
function getOrderMessage(status: OrderStatus): string {
    // TODO: Return appropriate message for each status
}

// Test:
console.log(getOrderMessage(OrderStatus.Shipped));  // "Your order is on its way!"
```

<details>
<summary>Solution</summary>

```typescript
// 1. OrderStatus: Pending, Processing, Shipped, Delivered, Cancelled
enum OrderStatus {
    Pending,
    Processing,
    Shipped,
    Delivered,
    Cancelled
}

// 2. HttpMethod: GET, POST, PUT, DELETE, PATCH (use string values)
enum HttpMethod {
    GET = "GET",
    POST = "POST",
    PUT = "PUT",
    DELETE = "DELETE",
    PATCH = "PATCH"
}

// 3. LogLevel: Debug=0, Info=1, Warning=2, Error=3, Critical=4
enum LogLevel {
    Debug = 0,
    Info = 1,
    Warning = 2,
    Error = 3,
    Critical = 4
}

// 4. Function that returns a message based on OrderStatus
function getOrderMessage(status: OrderStatus): string {
    switch (status) {
        case OrderStatus.Pending:
            return "Your order is pending confirmation.";
        case OrderStatus.Processing:
            return "Your order is being processed.";
        case OrderStatus.Shipped:
            return "Your order is on its way!";
        case OrderStatus.Delivered:
            return "Your order has been delivered.";
        case OrderStatus.Cancelled:
            return "Your order has been cancelled.";
    }
}

// Test:
console.log(getOrderMessage(OrderStatus.Shipped));  // "Your order is on its way!"
```

</details>

---

## Exercise 2.5: Const Enums and Enum Alternatives

Compare enums with union types.

```typescript
// TODO: Implement both approaches

// Approach 1: Using an enum
enum Color {
    // Define: Red, Green, Blue, Yellow
}

// Approach 2: Using a union type (often preferred)
type ColorType = // Define the same colors as literals

// Approach 3: Using const object (for runtime values)
const Colors = {
    // Define with 'as const'
} as const;

type ColorValue = // Extract type from Colors object

// Create a function that works with each approach
function paintWithEnum(color: Color): void { }
function paintWithUnion(color: ColorType): void { }
function paintWithConst(color: ColorValue): void { }
```

<details>
<summary>Solution</summary>

```typescript
// Approach 1: Using an enum
enum Color {
    Red = "RED",
    Green = "GREEN",
    Blue = "BLUE",
    Yellow = "YELLOW"
}

// Approach 2: Using a union type (often preferred)
type ColorType = "red" | "green" | "blue" | "yellow";

// Approach 3: Using const object (for runtime values)
const Colors = {
    Red: "RED",
    Green: "GREEN",
    Blue: "BLUE",
    Yellow: "YELLOW"
} as const;

type ColorValue = typeof Colors[keyof typeof Colors];
// Equivalent to: "RED" | "GREEN" | "BLUE" | "YELLOW"

// Functions that work with each approach
function paintWithEnum(color: Color): void {
    console.log(`Painting with enum: ${color}`);
}

function paintWithUnion(color: ColorType): void {
    console.log(`Painting with union: ${color}`);
}

function paintWithConst(color: ColorValue): void {
    console.log(`Painting with const: ${color}`);
}

// Usage:
paintWithEnum(Color.Red);      // "Painting with enum: RED"
paintWithUnion("red");         // "Painting with union: red"
paintWithConst(Colors.Red);    // "Painting with const: RED"
```

</details>

---

## Exercise 2.6: Multi-dimensional Arrays

Work with nested arrays and matrices.

```typescript
// TODO: Add proper type annotations

// 1. 2D matrix of numbers
let matrix = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
];

// 2. Create a function to get a value from the matrix
function getCell(matrix, row, col) {
    return matrix[row][col];
}

// 3. Create a function to sum all values in the matrix
function sumMatrix(matrix) {
    // TODO: Implement
}

// 4. Jagged array (rows of different lengths)
let jagged = [
    [1, 2],
    [3, 4, 5],
    [6]
];

// 5. 3D array (like a Rubik's cube)
let cube = [
    [[1, 2], [3, 4]],
    [[5, 6], [7, 8]]
];
```

<details>
<summary>Solution</summary>

```typescript
// 1. 2D matrix of numbers
let matrix: number[][] = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
];

// 2. Create a function to get a value from the matrix
function getCell(matrix: number[][], row: number, col: number): number {
    return matrix[row][col];
}

// 3. Create a function to sum all values in the matrix
function sumMatrix(matrix: number[][]): number {
    let total = 0;
    for (const row of matrix) {
        for (const cell of row) {
            total += cell;
        }
    }
    return total;

    // Or using reduce:
    // return matrix.flat().reduce((sum, n) => sum + n, 0);
}

// 4. Jagged array (rows of different lengths)
let jagged: number[][] = [
    [1, 2],
    [3, 4, 5],
    [6]
];

// 5. 3D array (like a Rubik's cube)
let cube: number[][][] = [
    [[1, 2], [3, 4]],
    [[5, 6], [7, 8]]
];
```

</details>

---

## Challenge: Inventory System

Build a simple inventory tracking system using arrays and enums.

```typescript
// TODO: Implement the inventory system

// Define an enum for product categories
enum Category {
    // Electronics, Clothing, Food, Books
}

// Define a Product type (use a type alias or interface)
// Properties: id, name, category, quantity, price

// Create an inventory array with at least 5 products

// Implement these functions:
function getTotalValue(inventory): number {
    // Sum of (quantity * price) for all products
}

function getProductsByCategory(inventory, category): Product[] {
    // Filter products by category
}

function getLowStockProducts(inventory, threshold): Product[] {
    // Products with quantity below threshold
}

function updateStock(inventory, productId, quantityChange): boolean {
    // Update quantity, return true if successful
}

// Test your implementation
console.log("Total inventory value:", getTotalValue(inventory));
console.log("Electronics:", getProductsByCategory(inventory, Category.Electronics));
console.log("Low stock:", getLowStockProducts(inventory, 10));
```

<details>
<summary>Solution</summary>

```typescript
// Define an enum for product categories
enum Category {
    Electronics = "ELECTRONICS",
    Clothing = "CLOTHING",
    Food = "FOOD",
    Books = "BOOKS"
}

// Define a Product type
interface Product {
    id: number;
    name: string;
    category: Category;
    quantity: number;
    price: number;
}

// Create an inventory array
const inventory: Product[] = [
    { id: 1, name: "Laptop", category: Category.Electronics, quantity: 15, price: 999.99 },
    { id: 2, name: "T-Shirt", category: Category.Clothing, quantity: 50, price: 19.99 },
    { id: 3, name: "Coffee Beans", category: Category.Food, quantity: 5, price: 12.99 },
    { id: 4, name: "TypeScript Handbook", category: Category.Books, quantity: 25, price: 39.99 },
    { id: 5, name: "Headphones", category: Category.Electronics, quantity: 8, price: 149.99 }
];

function getTotalValue(inventory: Product[]): number {
    return inventory.reduce((total, product) => {
        return total + (product.quantity * product.price);
    }, 0);
}

function getProductsByCategory(inventory: Product[], category: Category): Product[] {
    return inventory.filter(product => product.category === category);
}

function getLowStockProducts(inventory: Product[], threshold: number): Product[] {
    return inventory.filter(product => product.quantity < threshold);
}

function updateStock(inventory: Product[], productId: number, quantityChange: number): boolean {
    const product = inventory.find(p => p.id === productId);
    if (!product) {
        return false;
    }

    const newQuantity = product.quantity + quantityChange;
    if (newQuantity < 0) {
        return false;  // Can't have negative stock
    }

    product.quantity = newQuantity;
    return true;
}

// Test the implementation
console.log("Total inventory value:", getTotalValue(inventory).toFixed(2));
console.log("Electronics:", getProductsByCategory(inventory, Category.Electronics));
console.log("Low stock:", getLowStockProducts(inventory, 10));
console.log("Update stock:", updateStock(inventory, 3, 20));  // Add 20 coffee bags
console.log("Updated coffee:", inventory.find(p => p.id === 3));
```

</details>

---

## Next Steps

Continue to [Exercise Set 3: Union Types and Type Narrowing](./03-union-types.md)

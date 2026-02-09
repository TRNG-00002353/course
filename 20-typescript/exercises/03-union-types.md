# Exercise Set 3: Union Types and Type Narrowing

Master TypeScript's union types and learn to narrow types safely.

---

## Exercise 3.1: Basic Union Types

Create variables and functions that use union types.

```typescript
// TODO: Define these types and implement the functions

// 1. ID can be a string or number
let userId;     // Assign a number
let orderId;    // Assign a string

// 2. Function that accepts string or number
function formatId(id) {
    // Return "ID-" + id
}

// 3. Value that can be string, number, or boolean
let config;

// 4. Function parameter that accepts multiple types
function printValue(value) {
    console.log(value);
}
```

<details>
<summary>Solution</summary>

```typescript
// 1. ID can be a string or number
let userId: string | number = 12345;
let orderId: string | number = "ORD-2024-001";

// 2. Function that accepts string or number
function formatId(id: string | number): string {
    return "ID-" + id;
}

// 3. Value that can be string, number, or boolean
let config: string | number | boolean = true;

// 4. Function parameter that accepts multiple types
function printValue(value: string | number | boolean): void {
    console.log(value);
}
```

</details>

---

## Exercise 3.2: Type Narrowing with typeof

Use typeof checks to narrow union types.

```typescript
// TODO: Implement these functions with proper type narrowing

// 1. Double a value: if number, multiply by 2; if string, repeat twice
function double(value: string | number): string | number {
    // TODO: Use typeof to narrow
}

// 2. Get length: return string length or "number has no length"
function getLength(value: string | number): number | string {
    // TODO: Use typeof to narrow
}

// 3. Convert to string with formatting
function stringify(value: string | number | boolean): string {
    // string -> wrap in quotes: "hello"
    // number -> format with 2 decimals: "123.00"
    // boolean -> uppercase: "TRUE" or "FALSE"
}

// Test cases:
console.log(double(5));         // 10
console.log(double("hi"));      // "hihi"
console.log(stringify(42));     // "42.00"
console.log(stringify(true));   // "TRUE"
```

<details>
<summary>Solution</summary>

```typescript
// 1. Double a value
function double(value: string | number): string | number {
    if (typeof value === "number") {
        return value * 2;
    } else {
        return value + value;  // or value.repeat(2)
    }
}

// 2. Get length
function getLength(value: string | number): number | string {
    if (typeof value === "string") {
        return value.length;
    } else {
        return "number has no length";
    }
}

// 3. Convert to string with formatting
function stringify(value: string | number | boolean): string {
    if (typeof value === "string") {
        return `"${value}"`;
    } else if (typeof value === "number") {
        return value.toFixed(2);
    } else {
        return value.toString().toUpperCase();
    }
}

// Test cases:
console.log(double(5));         // 10
console.log(double("hi"));      // "hihi"
console.log(stringify(42));     // "42.00"
console.log(stringify(true));   // "TRUE"
```

</details>

---

## Exercise 3.3: Literal Types

Work with specific literal values.

```typescript
// TODO: Define literal types and implement functions

// 1. Define a type for cardinal directions
type Direction = // "north" | "south" | "east" | "west"

// 2. Define a type for T-shirt sizes
type Size = // "xs" | "s" | "m" | "l" | "xl"

// 3. Define a type for traffic light colors
type TrafficLight = // "red" | "yellow" | "green"

// 4. Function that returns next direction (clockwise)
function turnRight(direction: Direction): Direction {
    // TODO: Implement
}

// 5. Function that returns next traffic light color
function nextLight(current: TrafficLight): TrafficLight {
    // red -> green -> yellow -> red
}

// 6. Function that returns size price multiplier
function getSizeMultiplier(size: Size): number {
    // xs: 0.8, s: 0.9, m: 1.0, l: 1.1, xl: 1.2
}
```

<details>
<summary>Solution</summary>

```typescript
// 1. Define a type for cardinal directions
type Direction = "north" | "south" | "east" | "west";

// 2. Define a type for T-shirt sizes
type Size = "xs" | "s" | "m" | "l" | "xl";

// 3. Define a type for traffic light colors
type TrafficLight = "red" | "yellow" | "green";

// 4. Function that returns next direction (clockwise)
function turnRight(direction: Direction): Direction {
    switch (direction) {
        case "north": return "east";
        case "east": return "south";
        case "south": return "west";
        case "west": return "north";
    }
}

// 5. Function that returns next traffic light color
function nextLight(current: TrafficLight): TrafficLight {
    switch (current) {
        case "red": return "green";
        case "green": return "yellow";
        case "yellow": return "red";
    }
}

// 6. Function that returns size price multiplier
function getSizeMultiplier(size: Size): number {
    const multipliers: Record<Size, number> = {
        xs: 0.8,
        s: 0.9,
        m: 1.0,
        l: 1.1,
        xl: 1.2
    };
    return multipliers[size];
}
```

</details>

---

## Exercise 3.4: Discriminated Unions

Create and use discriminated unions for type-safe branching.

```typescript
// TODO: Define these discriminated union types

// 1. Shape type with "kind" discriminator
//    - Circle: kind="circle", radius
//    - Rectangle: kind="rectangle", width, height
//    - Triangle: kind="triangle", base, height

type Shape = // Define the union

// 2. Function to calculate area
function calculateArea(shape: Shape): number {
    // TODO: Use switch on shape.kind
}

// 3. API Response type with "status" discriminator
//    - Success: status="success", data (any)
//    - Error: status="error", message (string), code (number)
//    - Loading: status="loading"

type ApiResponse = // Define the union

// 4. Function to handle API response
function handleResponse(response: ApiResponse): string {
    // TODO: Return appropriate message for each status
}
```

<details>
<summary>Solution</summary>

```typescript
// 1. Shape type with "kind" discriminator
type Circle = {
    kind: "circle";
    radius: number;
};

type Rectangle = {
    kind: "rectangle";
    width: number;
    height: number;
};

type Triangle = {
    kind: "triangle";
    base: number;
    height: number;
};

type Shape = Circle | Rectangle | Triangle;

// 2. Function to calculate area
function calculateArea(shape: Shape): number {
    switch (shape.kind) {
        case "circle":
            return Math.PI * shape.radius ** 2;
        case "rectangle":
            return shape.width * shape.height;
        case "triangle":
            return (shape.base * shape.height) / 2;
    }
}

// 3. API Response type with "status" discriminator
type SuccessResponse = {
    status: "success";
    data: unknown;
};

type ErrorResponse = {
    status: "error";
    message: string;
    code: number;
};

type LoadingResponse = {
    status: "loading";
};

type ApiResponse = SuccessResponse | ErrorResponse | LoadingResponse;

// 4. Function to handle API response
function handleResponse(response: ApiResponse): string {
    switch (response.status) {
        case "success":
            return `Success! Data received: ${JSON.stringify(response.data)}`;
        case "error":
            return `Error ${response.code}: ${response.message}`;
        case "loading":
            return "Loading...";
    }
}

// Test:
const circle: Shape = { kind: "circle", radius: 5 };
console.log(calculateArea(circle));  // ~78.54

const error: ApiResponse = { status: "error", message: "Not found", code: 404 };
console.log(handleResponse(error));  // "Error 404: Not found"
```

</details>

---

## Exercise 3.5: Nullish Types and Optional Chaining

Handle nullable types safely.

```typescript
// TODO: Implement these functions with proper null handling

interface User {
    id: number;
    name: string;
    email?: string;  // Optional
    address?: {
        street: string;
        city: string;
        zipCode?: string;
    };
}

// 1. Get user email or default
function getUserEmail(user: User): string {
    // Return email or "No email provided"
}

// 2. Get formatted address
function getFormattedAddress(user: User): string | null {
    // Return "street, city zipCode" or null if no address
    // Use optional chaining
}

// 3. Get zip code with fallback
function getZipCode(user: User, fallback: string): string {
    // Return zipCode or fallback using nullish coalescing
}

// 4. Update user email safely
function updateEmail(user: User | null, newEmail: string): boolean {
    // Return false if user is null, otherwise update and return true
}
```

<details>
<summary>Solution</summary>

```typescript
interface User {
    id: number;
    name: string;
    email?: string;
    address?: {
        street: string;
        city: string;
        zipCode?: string;
    };
}

// 1. Get user email or default
function getUserEmail(user: User): string {
    return user.email ?? "No email provided";
    // or: return user.email || "No email provided";
}

// 2. Get formatted address
function getFormattedAddress(user: User): string | null {
    if (!user.address) {
        return null;
    }

    const { street, city, zipCode } = user.address;
    return zipCode
        ? `${street}, ${city} ${zipCode}`
        : `${street}, ${city}`;
}

// 3. Get zip code with fallback
function getZipCode(user: User, fallback: string): string {
    return user.address?.zipCode ?? fallback;
}

// 4. Update user email safely
function updateEmail(user: User | null, newEmail: string): boolean {
    if (user === null) {
        return false;
    }
    user.email = newEmail;
    return true;
}

// Test:
const user: User = {
    id: 1,
    name: "Alice",
    address: {
        street: "123 Main St",
        city: "Boston"
    }
};

console.log(getUserEmail(user));           // "No email provided"
console.log(getFormattedAddress(user));    // "123 Main St, Boston"
console.log(getZipCode(user, "00000"));    // "00000"
```

</details>

---

## Exercise 3.6: Type Guards with in Operator

Use the `in` operator to narrow object types.

```typescript
// TODO: Implement type guards using the 'in' operator

interface Car {
    type: "car";
    wheels: 4;
    drive(): void;
}

interface Motorcycle {
    type: "motorcycle";
    wheels: 2;
    wheelie(): void;
}

interface Bicycle {
    type: "bicycle";
    wheels: 2;
    pedal(): void;
}

type Vehicle = Car | Motorcycle | Bicycle;

// 1. Check if vehicle can do a wheelie
function canDoWheelie(vehicle: Vehicle): boolean {
    // Use 'in' operator to check
}

// 2. Get vehicle action
function getAction(vehicle: Vehicle): string {
    // Return appropriate action based on vehicle type
    // Car: "driving", Motorcycle: "riding", Bicycle: "pedaling"
}

// 3. Type guard function using 'in'
function isTwoWheeled(vehicle: Vehicle): vehicle is Motorcycle | Bicycle {
    // TODO: Implement type guard
}
```

<details>
<summary>Solution</summary>

```typescript
interface Car {
    type: "car";
    wheels: 4;
    drive(): void;
}

interface Motorcycle {
    type: "motorcycle";
    wheels: 2;
    wheelie(): void;
}

interface Bicycle {
    type: "bicycle";
    wheels: 2;
    pedal(): void;
}

type Vehicle = Car | Motorcycle | Bicycle;

// 1. Check if vehicle can do a wheelie
function canDoWheelie(vehicle: Vehicle): boolean {
    return "wheelie" in vehicle;
}

// 2. Get vehicle action
function getAction(vehicle: Vehicle): string {
    if ("drive" in vehicle) {
        return "driving";
    } else if ("wheelie" in vehicle) {
        return "riding";
    } else {
        return "pedaling";
    }
}

// 3. Type guard function using 'in'
function isTwoWheeled(vehicle: Vehicle): vehicle is Motorcycle | Bicycle {
    return vehicle.wheels === 2;
}

// Test:
const motorcycle: Motorcycle = {
    type: "motorcycle",
    wheels: 2,
    wheelie() { console.log("Wheelie!"); }
};

console.log(canDoWheelie(motorcycle));  // true
console.log(getAction(motorcycle));      // "riding"
console.log(isTwoWheeled(motorcycle));   // true
```

</details>

---

## Challenge: Payment Processor

Build a type-safe payment processor using union types and discriminated unions.

```typescript
// TODO: Define types and implement the payment processor

// Payment methods with discriminated unions
// - CreditCard: type="credit", cardNumber, expiry, cvv
// - PayPal: type="paypal", email
// - BankTransfer: type="bank", accountNumber, routingNumber
// - Crypto: type="crypto", walletAddress, currency (BTC | ETH | USDT)

type PaymentMethod = // Define

// Payment result discriminated union
// - Success: status="success", transactionId, amount
// - Failure: status="failed", reason, code
// - Pending: status="pending", estimatedTime

type PaymentResult = // Define

// Implement these functions:

function validatePaymentMethod(method: PaymentMethod): boolean {
    // Validate based on payment type
    // Credit card: 16 digits, valid expiry format (MM/YY)
    // PayPal: valid email format
    // Bank: account and routing numbers not empty
    // Crypto: wallet address starts with correct prefix
}

function processPayment(method: PaymentMethod, amount: number): PaymentResult {
    // Simulate payment processing
    // Return success 80% of time, pending 15%, failure 5%
}

function getPaymentDescription(method: PaymentMethod): string {
    // Return human-readable description
    // "Credit card ending in 1234"
    // "PayPal account user@email.com"
    // etc.
}

// Test your implementation
```

<details>
<summary>Solution</summary>

```typescript
// Payment method types
interface CreditCard {
    type: "credit";
    cardNumber: string;
    expiry: string;
    cvv: string;
}

interface PayPal {
    type: "paypal";
    email: string;
}

interface BankTransfer {
    type: "bank";
    accountNumber: string;
    routingNumber: string;
}

interface Crypto {
    type: "crypto";
    walletAddress: string;
    currency: "BTC" | "ETH" | "USDT";
}

type PaymentMethod = CreditCard | PayPal | BankTransfer | Crypto;

// Payment result types
interface PaymentSuccess {
    status: "success";
    transactionId: string;
    amount: number;
}

interface PaymentFailure {
    status: "failed";
    reason: string;
    code: number;
}

interface PaymentPending {
    status: "pending";
    estimatedTime: string;
}

type PaymentResult = PaymentSuccess | PaymentFailure | PaymentPending;

// Validation
function validatePaymentMethod(method: PaymentMethod): boolean {
    switch (method.type) {
        case "credit":
            const cardValid = /^\d{16}$/.test(method.cardNumber.replace(/\s/g, ""));
            const expiryValid = /^\d{2}\/\d{2}$/.test(method.expiry);
            const cvvValid = /^\d{3,4}$/.test(method.cvv);
            return cardValid && expiryValid && cvvValid;

        case "paypal":
            return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(method.email);

        case "bank":
            return method.accountNumber.length > 0 && method.routingNumber.length > 0;

        case "crypto":
            const prefixes: Record<Crypto["currency"], string> = {
                BTC: "1",
                ETH: "0x",
                USDT: "T"
            };
            return method.walletAddress.startsWith(prefixes[method.currency]);
    }
}

// Process payment
function processPayment(method: PaymentMethod, amount: number): PaymentResult {
    if (!validatePaymentMethod(method)) {
        return {
            status: "failed",
            reason: "Invalid payment method",
            code: 400
        };
    }

    const random = Math.random();

    if (random < 0.80) {
        return {
            status: "success",
            transactionId: `TXN-${Date.now()}-${Math.random().toString(36).substr(2, 9)}`,
            amount
        };
    } else if (random < 0.95) {
        return {
            status: "pending",
            estimatedTime: method.type === "bank" ? "2-3 business days" : "1 hour"
        };
    } else {
        return {
            status: "failed",
            reason: "Payment declined",
            code: 402
        };
    }
}

// Get description
function getPaymentDescription(method: PaymentMethod): string {
    switch (method.type) {
        case "credit":
            const last4 = method.cardNumber.slice(-4);
            return `Credit card ending in ${last4}`;

        case "paypal":
            return `PayPal account ${method.email}`;

        case "bank":
            const lastDigits = method.accountNumber.slice(-4);
            return `Bank account ending in ${lastDigits}`;

        case "crypto":
            const shortWallet = `${method.walletAddress.slice(0, 6)}...${method.walletAddress.slice(-4)}`;
            return `${method.currency} wallet ${shortWallet}`;
    }
}

// Test
const creditCard: PaymentMethod = {
    type: "credit",
    cardNumber: "4111111111111234",
    expiry: "12/25",
    cvv: "123"
};

console.log(validatePaymentMethod(creditCard));      // true
console.log(getPaymentDescription(creditCard));      // "Credit card ending in 1234"
console.log(processPayment(creditCard, 99.99));      // Success, pending, or failure
```

</details>

---

## Next Steps

Continue to [Exercise Set 4: Type Aliases and Interfaces](./04-interfaces.md)

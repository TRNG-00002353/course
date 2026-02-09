# Exercise Set 5: Utility Types

Master TypeScript's built-in utility types for type transformations.

---

## Exercise 5.1: Partial and Required

Transform required and optional properties.

```typescript
interface User {
    id: number;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    age: number;
}

// TODO: Create these types using Partial and Required

// 1. CreateUserInput: all fields optional except username and email
type CreateUserInput = // TODO

// 2. UpdateUserInput: all fields optional (for PATCH updates)
type UpdateUserInput = // TODO

// 3. UserProfile: all fields required
type UserProfile = // TODO

// Implement these functions:
function createUser(input: CreateUserInput): User {
    // TODO: Return user with defaults for missing fields
}

function updateUser(id: number, updates: UpdateUserInput): User {
    // TODO: Merge updates with existing user
}
```

<details>
<summary>Solution</summary>

```typescript
interface User {
    id: number;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    age: number;
}

// 1. CreateUserInput: required username/email, rest optional
type CreateUserInput = Pick<User, "username" | "email"> &
    Partial<Omit<User, "id" | "username" | "email">>;

// 2. UpdateUserInput: all optional (for PATCH)
type UpdateUserInput = Partial<Omit<User, "id">>;

// 3. UserProfile: all required
type UserProfile = Required<User>;

// Mock database
let users: User[] = [];
let nextId = 1;

function createUser(input: CreateUserInput): User {
    const user: User = {
        id: nextId++,
        username: input.username,
        email: input.email,
        firstName: input.firstName ?? "",
        lastName: input.lastName ?? "",
        age: input.age ?? 0
    };
    users.push(user);
    return user;
}

function updateUser(id: number, updates: UpdateUserInput): User {
    const index = users.findIndex(u => u.id === id);
    if (index === -1) {
        throw new Error("User not found");
    }

    users[index] = { ...users[index], ...updates };
    return users[index];
}

// Test
const newUser = createUser({
    username: "alice",
    email: "alice@example.com",
    firstName: "Alice"
});

const updated = updateUser(newUser.id, { lastName: "Smith", age: 25 });
```

</details>

---

## Exercise 5.2: Pick and Omit

Select or exclude specific properties.

```typescript
interface Product {
    id: string;
    name: string;
    description: string;
    price: number;
    cost: number;
    inventory: number;
    category: string;
    createdAt: Date;
    updatedAt: Date;
    internalNotes: string;
}

// TODO: Create these types

// 1. ProductDisplay: only id, name, description, price (for customers)
type ProductDisplay = // TODO

// 2. ProductAdmin: everything except internalNotes
type ProductAdmin = // TODO

// 3. ProductInventory: only id, name, inventory
type ProductInventory = // TODO

// 4. ProductUpdate: everything except id, createdAt, updatedAt
type ProductUpdate = // TODO

// 5. Create a function that converts full Product to ProductDisplay
function toProductDisplay(product: Product): ProductDisplay {
    // TODO
}
```

<details>
<summary>Solution</summary>

```typescript
interface Product {
    id: string;
    name: string;
    description: string;
    price: number;
    cost: number;
    inventory: number;
    category: string;
    createdAt: Date;
    updatedAt: Date;
    internalNotes: string;
}

// 1. ProductDisplay: only id, name, description, price
type ProductDisplay = Pick<Product, "id" | "name" | "description" | "price">;

// 2. ProductAdmin: everything except internalNotes
type ProductAdmin = Omit<Product, "internalNotes">;

// 3. ProductInventory: only id, name, inventory
type ProductInventory = Pick<Product, "id" | "name" | "inventory">;

// 4. ProductUpdate: everything except id, createdAt, updatedAt
type ProductUpdate = Omit<Product, "id" | "createdAt" | "updatedAt">;

// 5. Convert function
function toProductDisplay(product: Product): ProductDisplay {
    return {
        id: product.id,
        name: product.name,
        description: product.description,
        price: product.price
    };

    // Alternative using destructuring:
    // const { id, name, description, price } = product;
    // return { id, name, description, price };
}

// Test
const product: Product = {
    id: "PROD-001",
    name: "Widget",
    description: "A useful widget",
    price: 29.99,
    cost: 15.00,
    inventory: 100,
    category: "tools",
    createdAt: new Date(),
    updatedAt: new Date(),
    internalNotes: "Margin is 50%"
};

const display = toProductDisplay(product);
console.log(display);  // Only shows id, name, description, price
```

</details>

---

## Exercise 5.3: Record Type

Create typed dictionaries and maps.

```typescript
// TODO: Use Record to create these types

// 1. Role permissions map
type Role = "admin" | "editor" | "viewer";
type Permission = "create" | "read" | "update" | "delete";

type RolePermissions = // Record mapping Role to Permission[]

const permissions: RolePermissions = {
    // TODO: Define permissions for each role
};

// 2. HTTP status code messages
type StatusCode = 200 | 201 | 400 | 401 | 403 | 404 | 500;

type StatusMessages = // Record mapping StatusCode to string

// 3. Cache with expiration
interface CacheEntry<T> {
    value: T;
    expiresAt: Date;
}

type Cache<T> = // Record with string keys and CacheEntry<T> values

// Implement cache functions:
function setCache<T>(cache: Cache<T>, key: string, value: T, ttlMs: number): void {
    // TODO
}

function getCache<T>(cache: Cache<T>, key: string): T | undefined {
    // TODO: Return undefined if expired
}
```

<details>
<summary>Solution</summary>

```typescript
// 1. Role permissions map
type Role = "admin" | "editor" | "viewer";
type Permission = "create" | "read" | "update" | "delete";

type RolePermissions = Record<Role, Permission[]>;

const permissions: RolePermissions = {
    admin: ["create", "read", "update", "delete"],
    editor: ["create", "read", "update"],
    viewer: ["read"]
};

// 2. HTTP status code messages
type StatusCode = 200 | 201 | 400 | 401 | 403 | 404 | 500;

type StatusMessages = Record<StatusCode, string>;

const statusMessages: StatusMessages = {
    200: "OK",
    201: "Created",
    400: "Bad Request",
    401: "Unauthorized",
    403: "Forbidden",
    404: "Not Found",
    500: "Internal Server Error"
};

// 3. Cache with expiration
interface CacheEntry<T> {
    value: T;
    expiresAt: Date;
}

type Cache<T> = Record<string, CacheEntry<T>>;

function setCache<T>(cache: Cache<T>, key: string, value: T, ttlMs: number): void {
    cache[key] = {
        value,
        expiresAt: new Date(Date.now() + ttlMs)
    };
}

function getCache<T>(cache: Cache<T>, key: string): T | undefined {
    const entry = cache[key];

    if (!entry) {
        return undefined;
    }

    if (new Date() > entry.expiresAt) {
        delete cache[key];  // Clean up expired entry
        return undefined;
    }

    return entry.value;
}

// Test
const userCache: Cache<{ name: string }> = {};

setCache(userCache, "user-1", { name: "Alice" }, 60000);  // 1 minute TTL
console.log(getCache(userCache, "user-1"));  // { name: "Alice" }
console.log(getCache(userCache, "user-2"));  // undefined
```

</details>

---

## Exercise 5.4: Exclude and Extract

Filter union types.

```typescript
// TODO: Use Exclude and Extract

type AllEvents = "click" | "hover" | "focus" | "blur" | "keydown" | "keyup" | "scroll" | "resize";

// 1. Extract only keyboard events
type KeyboardEvents = // TODO

// 2. Exclude scroll and resize (window events)
type ElementEvents = // TODO

// 3. Complex types
type ApiResponse =
    | { type: "success"; data: unknown }
    | { type: "error"; message: string }
    | { type: "loading" }
    | { type: "idle" };

// Extract only states with data or message
type ActiveResponse = // TODO

// Exclude idle state
type BusyResponse = // TODO

// 4. Filter function parameter types
type PrimitiveTypes = string | number | boolean | null | undefined | symbol | bigint;

// Only serializable primitives (exclude symbol)
type SerializablePrimitive = // TODO

// Only nullable types
type Nullable = // TODO
```

<details>
<summary>Solution</summary>

```typescript
type AllEvents = "click" | "hover" | "focus" | "blur" | "keydown" | "keyup" | "scroll" | "resize";

// 1. Extract only keyboard events
type KeyboardEvents = Extract<AllEvents, "keydown" | "keyup">;
// Result: "keydown" | "keyup"

// 2. Exclude scroll and resize (window events)
type ElementEvents = Exclude<AllEvents, "scroll" | "resize">;
// Result: "click" | "hover" | "focus" | "blur" | "keydown" | "keyup"

// 3. Complex types
type ApiResponse =
    | { type: "success"; data: unknown }
    | { type: "error"; message: string }
    | { type: "loading" }
    | { type: "idle" };

// Extract states with additional properties
type ActiveResponse = Extract<ApiResponse, { type: "success" } | { type: "error" }>;
// Result: { type: "success"; data: unknown } | { type: "error"; message: string }

// Exclude idle state
type BusyResponse = Exclude<ApiResponse, { type: "idle" }>;
// Result: { type: "success"; data: unknown } | { type: "error"; message: string } | { type: "loading" }

// 4. Primitive type filtering
type PrimitiveTypes = string | number | boolean | null | undefined | symbol | bigint;

// Only serializable primitives (exclude symbol)
type SerializablePrimitive = Exclude<PrimitiveTypes, symbol>;
// Result: string | number | boolean | null | undefined | bigint

// Only nullable types
type Nullable = Extract<PrimitiveTypes, null | undefined>;
// Result: null | undefined

// Usage example:
function handleEvent(event: ElementEvents): void {
    console.log(`Handling ${event} event`);
}

handleEvent("click");    // OK
// handleEvent("scroll"); // Error: Argument of type '"scroll"' is not assignable
```

</details>

---

## Exercise 5.5: ReturnType and Parameters

Extract function type information.

```typescript
// TODO: Use ReturnType and Parameters

// Given functions:
function fetchUser(id: number): Promise<{ id: number; name: string }> {
    return Promise.resolve({ id, name: "User " + id });
}

function calculatePrice(
    basePrice: number,
    quantity: number,
    discount?: number
): { subtotal: number; total: number } {
    const subtotal = basePrice * quantity;
    const total = discount ? subtotal * (1 - discount) : subtotal;
    return { subtotal, total };
}

function processItems<T>(items: T[], processor: (item: T) => void): void {
    items.forEach(processor);
}

// 1. Extract return types
type FetchUserResult = // TODO
type PriceCalculation = // TODO

// 2. Extract parameter types
type FetchUserParams = // TODO
type CalculatePriceParams = // TODO

// 3. Create wrapper functions using extracted types
function cachedFetchUser(...args: FetchUserParams): FetchUserResult {
    // TODO: Implement with caching
}

// 4. Extract and use Awaited with ReturnType
type User = // TODO: Get the actual user type (unwrap Promise)
```

<details>
<summary>Solution</summary>

```typescript
// Given functions
function fetchUser(id: number): Promise<{ id: number; name: string }> {
    return Promise.resolve({ id, name: "User " + id });
}

function calculatePrice(
    basePrice: number,
    quantity: number,
    discount?: number
): { subtotal: number; total: number } {
    const subtotal = basePrice * quantity;
    const total = discount ? subtotal * (1 - discount) : subtotal;
    return { subtotal, total };
}

// 1. Extract return types
type FetchUserResult = ReturnType<typeof fetchUser>;
// Result: Promise<{ id: number; name: string }>

type PriceCalculation = ReturnType<typeof calculatePrice>;
// Result: { subtotal: number; total: number }

// 2. Extract parameter types
type FetchUserParams = Parameters<typeof fetchUser>;
// Result: [id: number]

type CalculatePriceParams = Parameters<typeof calculatePrice>;
// Result: [basePrice: number, quantity: number, discount?: number]

// 3. Create wrapper functions
const userCache = new Map<number, { id: number; name: string }>();

function cachedFetchUser(...args: FetchUserParams): FetchUserResult {
    const [id] = args;

    if (userCache.has(id)) {
        return Promise.resolve(userCache.get(id)!);
    }

    return fetchUser(id).then(user => {
        userCache.set(id, user);
        return user;
    });
}

// 4. Extract actual user type (unwrap Promise)
type User = Awaited<ReturnType<typeof fetchUser>>;
// Result: { id: number; name: string }

// Usage
async function displayUser() {
    const user: User = await cachedFetchUser(1);
    console.log(user.name);
}

// Create a typed price calculator wrapper
function applyBulkDiscount(
    ...args: CalculatePriceParams
): PriceCalculation {
    const [basePrice, quantity, discount = 0] = args;
    const bulkDiscount = quantity >= 10 ? 0.1 : 0;
    return calculatePrice(basePrice, quantity, discount + bulkDiscount);
}
```

</details>

---

## Exercise 5.6: NonNullable and Practical Patterns

Handle nullable types effectively.

```typescript
// TODO: Implement these exercises

// 1. Use NonNullable to remove null/undefined
type MaybeUser = { id: number; name: string } | null | undefined;
type DefiniteUser = // TODO

// 2. Create a simple Nullable type alias
type Nullable<T> = // TODO: Make T nullable

// 3. Filter null values from array
function filterNullish<T>(array: (T | null | undefined)[]): T[] {
    // TODO: Return only non-null values with proper typing
}

// 4. Safe property access with fallback
interface User {
    id: number;
    name: string;
    email?: string;
    profile?: {
        bio?: string;
        avatar?: string;
    };
}

function getUserEmail(user: User): string {
    // TODO: Return email or "No email"
}

function getUserBio(user: User): string {
    // TODO: Return bio or "No bio available"
}

// 5. Type-safe object update
function updateUser(user: User, updates: Partial<User>): User {
    // TODO: Merge updates into user
}

// Test:
const users: (User | null)[] = [
    { id: 1, name: "Alice", email: "alice@example.com" },
    null,
    { id: 2, name: "Bob" }
];

const validUsers = filterNullish(users);
console.log(validUsers.length);  // 2
```

<details>
<summary>Solution</summary>

```typescript
// 1. Use NonNullable
type MaybeUser = { id: number; name: string } | null | undefined;
type DefiniteUser = NonNullable<MaybeUser>;
// Result: { id: number; name: string }

// 2. Simple Nullable type
type Nullable<T> = T | null | undefined;

type NullableString = Nullable<string>;  // string | null | undefined

// 3. Filter null values with type guard
function filterNullish<T>(array: (T | null | undefined)[]): T[] {
    return array.filter((item): item is T => item != null);
}

const mixed = ["a", null, "b", undefined, "c"];
const strings = filterNullish(mixed);  // string[]
console.log(strings);  // ["a", "b", "c"]

// 4. Safe property access
interface User {
    id: number;
    name: string;
    email?: string;
    profile?: {
        bio?: string;
        avatar?: string;
    };
}

function getUserEmail(user: User): string {
    return user.email ?? "No email";
}

function getUserBio(user: User): string {
    return user.profile?.bio ?? "No bio available";
}

// 5. Type-safe object update using Partial
function updateUser(user: User, updates: Partial<User>): User {
    return { ...user, ...updates };
}

// Test
const users: (User | null)[] = [
    { id: 1, name: "Alice", email: "alice@example.com" },
    null,
    { id: 2, name: "Bob" }
];

const validUsers = filterNullish(users);
console.log(validUsers.length);  // 2

const user = validUsers[0];
console.log(getUserEmail(user));  // "alice@example.com"
console.log(getUserBio(user));    // "No bio available"

const updated = updateUser(user, { email: "new@example.com" });
console.log(updated.email);  // "new@example.com"
```

</details>

---

## Challenge: Form Validation System

Build a type-safe form validation system using utility types.

```typescript
// TODO: Build the validation system

// Define a form schema type
interface FormSchema {
    username: string;
    email: string;
    password: string;
    confirmPassword: string;
    age: number;
    bio?: string;
    newsletter: boolean;
}

// Create these utility types:

// 1. FormErrors<T>: Maps each field to an optional error message
type FormErrors<T> = // TODO

// 2. FormTouched<T>: Maps each field to optional boolean (was field touched?)
type FormTouched<T> = // TODO

// 3. FormState<T>: Complete form state
interface FormState<T> {
    values: T;
    errors: FormErrors<T>;
    touched: FormTouched<T>;
    isValid: boolean;
    isSubmitting: boolean;
    isDirty: boolean;
}

// 4. Validator type: function that returns error message or undefined
type Validator<T> = // TODO

// 5. ValidationRules<T>: Maps fields to arrays of validators
type ValidationRules<T> = // TODO

// Implement:
function createFormState<T>(initialValues: T): FormState<T> {
    // TODO
}

function validateField<T, K extends keyof T>(
    value: T[K],
    validators: Validator<T[K]>[]
): string | undefined {
    // TODO
}

function validateForm<T>(
    values: T,
    rules: ValidationRules<T>
): FormErrors<T> {
    // TODO
}

// Common validators
const required: Validator<unknown> = // TODO
const minLength = (min: number): Validator<string> => // TODO
const isEmail: Validator<string> = // TODO
const minValue = (min: number): Validator<number> => // TODO

// Test
const rules: ValidationRules<FormSchema> = {
    username: [required, minLength(3)],
    email: [required, isEmail],
    password: [required, minLength(8)],
    age: [required, minValue(18)]
};
```

<details>
<summary>Solution</summary>

```typescript
interface FormSchema {
    username: string;
    email: string;
    password: string;
    confirmPassword: string;
    age: number;
    bio?: string;
    newsletter: boolean;
}

// 1. FormErrors<T>: Maps each field to optional error message
type FormErrors<T> = Partial<Record<keyof T, string>>;

// 2. FormTouched<T>: Maps each field to optional boolean
type FormTouched<T> = Partial<Record<keyof T, boolean>>;

// 3. FormState<T>: Complete form state
interface FormState<T> {
    values: T;
    errors: FormErrors<T>;
    touched: FormTouched<T>;
    isValid: boolean;
    isSubmitting: boolean;
    isDirty: boolean;
}

// 4. Validator type
type Validator<T> = (value: T) => string | undefined;

// 5. ValidationRules<T>
type ValidationRules<T> = Partial<{
    [K in keyof T]: Validator<T[K]>[];
}>;

// Implementation
function createFormState<T>(initialValues: T): FormState<T> {
    return {
        values: initialValues,
        errors: {},
        touched: {},
        isValid: true,
        isSubmitting: false,
        isDirty: false
    };
}

function validateField<T>(
    value: T,
    validators: Validator<T>[]
): string | undefined {
    for (const validator of validators) {
        const error = validator(value);
        if (error) {
            return error;
        }
    }
    return undefined;
}

function validateForm<T extends Record<string, unknown>>(
    values: T,
    rules: ValidationRules<T>
): FormErrors<T> {
    const errors: FormErrors<T> = {};

    for (const key of Object.keys(rules) as (keyof T)[]) {
        const fieldRules = rules[key];
        if (fieldRules) {
            const error = validateField(values[key], fieldRules as Validator<T[keyof T]>[]);
            if (error) {
                errors[key] = error;
            }
        }
    }

    return errors;
}

// Common validators
const required: Validator<unknown> = (value) => {
    if (value === undefined || value === null || value === "") {
        return "This field is required";
    }
    return undefined;
};

const minLength = (min: number): Validator<string> => (value) => {
    if (typeof value === "string" && value.length < min) {
        return `Must be at least ${min} characters`;
    }
    return undefined;
};

const isEmail: Validator<string> = (value) => {
    if (typeof value === "string" && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value)) {
        return "Invalid email address";
    }
    return undefined;
};

const minValue = (min: number): Validator<number> => (value) => {
    if (typeof value === "number" && value < min) {
        return `Must be at least ${min}`;
    }
    return undefined;
};

const matches = <T>(
    fieldName: string,
    getValue: () => T
): Validator<T> => (value) => {
    if (value !== getValue()) {
        return `Must match ${fieldName}`;
    }
    return undefined;
};

// Test
const rules: ValidationRules<FormSchema> = {
    username: [required, minLength(3)],
    email: [required, isEmail],
    password: [required, minLength(8)],
    age: [required, minValue(18)],
    newsletter: [required]
};

const initialValues: FormSchema = {
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
    age: 0,
    newsletter: false
};

const formState = createFormState(initialValues);
const errors = validateForm(formState.values, rules);

console.log(errors);
// {
//   username: "This field is required",
//   email: "This field is required",
//   password: "This field is required",
//   age: "Must be at least 18",
//   newsletter: "This field is required"
// }

// Test with valid data
const validValues: FormSchema = {
    username: "alice",
    email: "alice@example.com",
    password: "securePass123",
    confirmPassword: "securePass123",
    age: 25,
    newsletter: true
};

const noErrors = validateForm(validValues, rules);
console.log(noErrors);  // {}
```

</details>

---

## Next Steps

Continue to [Exercise Set 6: Functions and Generics](./06-functions-generics.md)

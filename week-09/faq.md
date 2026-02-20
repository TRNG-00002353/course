# Week 09 - Interview FAQ

This document contains frequently asked interview questions and comprehensive answers for Week 09 topics: TypeScript Fundamentals and Angular Basics.

---

## Table of Contents

1. [TypeScript Fundamentals](#typescript-fundamentals)
2. [TypeScript Types and Interfaces](#typescript-types-and-interfaces)
3. [Angular Overview](#angular-overview)
4. [Components and Templates](#components-and-templates)
5. [Data Binding](#data-binding)
6. [Directives](#directives)
7. [Component Communication](#component-communication)

---

## TypeScript Fundamentals

### Q1: What is TypeScript and why use it?

**Answer:**

**TypeScript** is a strongly-typed superset of JavaScript developed by Microsoft. It compiles to plain JavaScript and runs anywhere JavaScript runs.

**Why use TypeScript:**

| Benefit | Description |
|---------|-------------|
| **Static Typing** | Catch errors at compile time, not runtime |
| **Better IDE Support** | Autocomplete, navigation, refactoring |
| **Self-Documenting** | Types serve as documentation |
| **OOP Features** | Classes, interfaces, access modifiers |
| **Future JS Features** | Use ES6+ features with backward compatibility |

**Example:**

```typescript
// JavaScript - error only at runtime
function add(a, b) {
  return a + b;
}
add("5", 3);  // Returns "53" - silent bug!

// TypeScript - error at compile time
function add(a: number, b: number): number {
  return a + b;
}
add("5", 3);  // Error: Argument of type 'string' is not assignable
```

**Key point:** TypeScript adds type safety without changing JavaScript's runtime behavior.

---

### Q2: Explain the difference between `any`, `unknown`, and `never`.

**Answer:**

These three types represent different concepts in TypeScript:

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     SPECIAL TYPES COMPARISON                             │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  any                    unknown                  never                   │
│  ─────────────────      ─────────────────        ─────────────────       │
│  "I don't care"         "I don't know yet"       "This never happens"   │
│                                                                          │
│  - Accepts anything     - Accepts anything       - Accepts nothing       │
│  - No type checking     - Must check before use  - No value exists       │
│  - Unsafe               - Safe                   - For impossible cases  │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**Examples:**

```typescript
// any - bypasses type checking (avoid!)
let anything: any = "hello";
anything.foo.bar.baz;  // No error, but crashes at runtime!

// unknown - requires type checking before use (safe)
let uncertain: unknown = "hello";
// uncertain.length;  // Error: Object is of type 'unknown'
if (typeof uncertain === "string") {
  console.log(uncertain.length);  // OK after type check
}

// never - for impossible values
function throwError(msg: string): never {
  throw new Error(msg);  // Never returns
}

function infiniteLoop(): never {
  while (true) {}  // Never ends
}

// Also used in exhaustive checks
type Status = "active" | "inactive";
function handleStatus(status: Status) {
  switch (status) {
    case "active": return "Active";
    case "inactive": return "Inactive";
    default:
      const _exhaustive: never = status;  // Error if case missed
  }
}
```

**Best practice:** Prefer `unknown` over `any` when type is uncertain.

---

### Q3: What are Union Types and Intersection Types?

**Answer:**

**Union Types (`|`)**: A value that can be ONE of several types.

**Intersection Types (`&`)**: A value that is ALL of several types combined.

```
Union (OR):  Type A | Type B  →  Value is A OR B
Intersection (AND):  Type A & Type B  →  Value is A AND B
```

**Examples:**

```typescript
// UNION TYPES - value can be one type or another
type StringOrNumber = string | number;

function formatId(id: StringOrNumber): string {
  if (typeof id === "string") {
    return id.toUpperCase();
  }
  return id.toString();
}

formatId("abc123");  // OK
formatId(12345);     // OK
formatId(true);      // Error: boolean not allowed

// Discriminated union with literal types
type Result =
  | { success: true; data: string }
  | { success: false; error: string };

function handleResult(result: Result) {
  if (result.success) {
    console.log(result.data);   // TypeScript knows data exists
  } else {
    console.log(result.error);  // TypeScript knows error exists
  }
}
```

```typescript
// INTERSECTION TYPES - combine multiple types
interface HasName {
  name: string;
}

interface HasAge {
  age: number;
}

type Person = HasName & HasAge;

const person: Person = {
  name: "John",  // Required from HasName
  age: 30        // Required from HasAge
};

// Useful for mixins
type Admin = User & { permissions: string[] };
```

---

### Q4: Explain interfaces vs type aliases. When to use each?

**Answer:**

Both define object shapes, but have key differences:

| Feature | Interface | Type Alias |
|---------|-----------|------------|
| Extends | `extends` keyword | `&` intersection |
| Declaration Merging | Yes (adds properties) | No |
| Primitives | Cannot represent | Can represent |
| Unions | Cannot represent | Can represent |
| Classes | Can implement | Can implement |

**Examples:**

```typescript
// INTERFACE - best for object shapes and extending
interface User {
  name: string;
  email: string;
}

interface Admin extends User {
  permissions: string[];
}

// Declaration merging (interface only)
interface User {
  age: number;  // Adds to existing User interface
}

// TYPE ALIAS - more flexible
type ID = string | number;  // Union (interface can't do this)
type Coordinate = [number, number];  // Tuple

type UserType = {
  name: string;
  email: string;
};

type AdminType = UserType & {  // Intersection instead of extends
  permissions: string[];
};
```

**When to use:**

```
USE INTERFACE when:
├── Defining object shapes
├── Creating class contracts
├── You might extend later
└── You want declaration merging

USE TYPE ALIAS when:
├── Creating union types
├── Creating tuple types
├── Creating primitive aliases
└── Creating complex mapped types
```

**Best practice:** Use `interface` for objects, `type` for everything else.

---

## TypeScript Types and Interfaces

### Q5: What are Generics and why are they useful?

**Answer:**

**Generics** allow you to create reusable components that work with multiple types while maintaining type safety.

```
Without Generics:                With Generics:
─────────────────                ────────────────
function identity(arg: any)      function identity<T>(arg: T): T
  → Returns any                    → Returns same type as input
  → Loses type info                → Preserves type info
```

**Examples:**

```typescript
// Generic function
function identity<T>(arg: T): T {
  return arg;
}

const str = identity("hello");  // Type: string
const num = identity(42);       // Type: number

// Generic interface
interface Box<T> {
  value: T;
}

const stringBox: Box<string> = { value: "hello" };
const numberBox: Box<number> = { value: 42 };

// Generic class
class Queue<T> {
  private items: T[] = [];

  enqueue(item: T): void {
    this.items.push(item);
  }

  dequeue(): T | undefined {
    return this.items.shift();
  }
}

const numberQueue = new Queue<number>();
numberQueue.enqueue(1);
numberQueue.enqueue(2);
// numberQueue.enqueue("hello");  // Error!

// Generic constraints
interface HasLength {
  length: number;
}

function logLength<T extends HasLength>(arg: T): number {
  return arg.length;  // Now we know T has length
}

logLength("hello");     // OK - string has length
logLength([1, 2, 3]);   // OK - array has length
// logLength(42);       // Error - number has no length
```

**Common use cases:**
- Collection classes (Array, Set, Map)
- API response wrappers
- Utility functions (pick, omit, partial)

---

### Q6: Explain TypeScript utility types: Partial, Required, Pick, Omit.

**Answer:**

TypeScript provides built-in utility types for common type transformations:

```typescript
interface User {
  id: number;
  name: string;
  email: string;
  password: string;
}
```

**Partial<T>** - Makes all properties optional:

```typescript
type PartialUser = Partial<User>;
// Equivalent to:
// { id?: number; name?: string; email?: string; password?: string; }

// Use case: Update functions
function updateUser(id: number, updates: Partial<User>) {
  // Only pass the fields you want to update
}

updateUser(1, { name: "New Name" });  // OK
updateUser(1, { email: "new@email.com" });  // OK
```

**Required<T>** - Makes all properties required:

```typescript
interface Config {
  host?: string;
  port?: number;
}

type RequiredConfig = Required<Config>;
// { host: string; port: number; } - no optional

function connect(config: RequiredConfig) {
  // Guaranteed both properties exist
}
```

**Pick<T, K>** - Select specific properties:

```typescript
type UserPreview = Pick<User, "id" | "name">;
// { id: number; name: string; }

// Use case: API responses with subset of fields
function getUserPreview(): UserPreview {
  return { id: 1, name: "John" };
}
```

**Omit<T, K>** - Exclude specific properties:

```typescript
type UserWithoutPassword = Omit<User, "password">;
// { id: number; name: string; email: string; }

// Use case: API responses without sensitive data
function getPublicUser(user: User): UserWithoutPassword {
  const { password, ...publicUser } = user;
  return publicUser;
}
```

**Combining utility types:**

```typescript
// Create user input (no id, password optional)
type CreateUserInput = Omit<User, "id"> & Partial<Pick<User, "password">>;
```

---

## Angular Overview

### Q7: What is Angular? Explain its architecture.

**Answer:**

**Angular** is a TypeScript-based platform for building web applications, developed by Google.

**Angular Architecture:**

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        ANGULAR ARCHITECTURE                              │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│   ┌─────────────┐                                                        │
│   │   MODULES   │  ← Organize app into cohesive blocks                  │
│   │ (@NgModule) │    - declarations (components)                        │
│   └──────┬──────┘    - imports (other modules)                          │
│          │           - providers (services)                              │
│          ↓                                                               │
│   ┌─────────────┐                                                        │
│   │ COMPONENTS  │  ← Building blocks with template + logic              │
│   │ (@Component)│    - template (HTML)                                  │
│   └──────┬──────┘    - class (TypeScript)                               │
│          │           - styles (CSS)                                      │
│          ↓                                                               │
│   ┌─────────────┐                                                        │
│   │  TEMPLATES  │  ← Define the view (HTML + Angular syntax)            │
│   │   (HTML)    │    - data binding {{ }}                               │
│   └──────┬──────┘    - directives *ngIf, *ngFor                         │
│          │           - pipes | date, currency                            │
│          ↓                                                               │
│   ┌─────────────┐                                                        │
│   │  SERVICES   │  ← Reusable business logic                            │
│   │(@Injectable)│    - API calls                                        │
│   └──────┬──────┘    - shared state                                      │
│          │           - utilities                                         │
│          ↓                                                               │
│   ┌─────────────┐                                                        │
│   │ DEPENDENCY  │  ← Provides services to components                    │
│   │  INJECTION  │    - singleton services                               │
│   └─────────────┘    - hierarchical injectors                           │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**Key concepts:**

| Concept | Description |
|---------|-------------|
| **Components** | UI building blocks with template, class, and styles |
| **Modules** | Containers organizing related code |
| **Services** | Shared logic/data, injected via DI |
| **Directives** | Modify DOM (structural: *ngIf, attribute: ngClass) |
| **Pipes** | Transform data in templates |
| **Routing** | Navigation between views |

**Why Angular?**
- Complete framework (routing, HTTP, forms, testing)
- TypeScript by default
- Strong conventions and structure
- Enterprise-ready with large team support

---

### Q8: What is a Component in Angular? Explain its structure.

**Answer:**

A **Component** is the fundamental building block of Angular applications, consisting of:

1. **TypeScript class** - Logic and data
2. **HTML template** - View structure
3. **CSS styles** - Component-specific styling
4. **Metadata** - Configuration via @Component decorator

**Component structure:**

```typescript
// user-card.component.ts
import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-user-card',      // HTML tag to use this component
  templateUrl: './user-card.component.html',  // External template
  // OR template: `<div>...</div>`,  // Inline template
  styleUrls: ['./user-card.component.css'],   // External styles
  // OR styles: [`div { color: red; }`],  // Inline styles
})
export class UserCardComponent {
  // Properties (data)
  @Input() user: User;           // Receives data from parent
  @Output() selected = new EventEmitter<User>();  // Sends events to parent

  isExpanded = false;            // Internal state

  // Methods (behavior)
  toggleExpand(): void {
    this.isExpanded = !this.isExpanded;
  }

  onSelect(): void {
    this.selected.emit(this.user);
  }
}
```

```html
<!-- user-card.component.html -->
<div class="user-card" [class.expanded]="isExpanded">
  <h3>{{ user.name }}</h3>
  <p>{{ user.email }}</p>

  <button (click)="toggleExpand()">
    {{ isExpanded ? 'Less' : 'More' }}
  </button>

  <div *ngIf="isExpanded">
    <p>Age: {{ user.age }}</p>
    <button (click)="onSelect()">Select</button>
  </div>
</div>
```

**Usage in parent:**

```html
<app-user-card
  [user]="currentUser"
  (selected)="handleSelection($event)">
</app-user-card>
```

**Component lifecycle:**

```
Constructor → ngOnChanges → ngOnInit → ngDoCheck →
ngAfterContentInit → ngAfterContentChecked →
ngAfterViewInit → ngAfterViewChecked → ngOnDestroy
```

---

## Data Binding

### Q9: Explain the four types of data binding in Angular.

**Answer:**

Angular provides four types of data binding:

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    ANGULAR DATA BINDING TYPES                            │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  1. INTERPOLATION  {{ expression }}                                      │
│     Component → Template (one-way)                                       │
│     Display values in HTML                                               │
│                                                                          │
│  2. PROPERTY BINDING  [property]="expression"                            │
│     Component → Template (one-way)                                       │
│     Bind to element/directive properties                                 │
│                                                                          │
│  3. EVENT BINDING  (event)="handler($event)"                             │
│     Template → Component (one-way)                                       │
│     React to user actions                                                │
│                                                                          │
│  4. TWO-WAY BINDING  [(ngModel)]="property"                              │
│     Component ↔ Template (both ways)                                     │
│     Sync data between input and component                                │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**Examples:**

```typescript
@Component({
  selector: 'app-demo',
  template: `
    <!-- 1. INTERPOLATION - Display data -->
    <h1>Hello, {{ userName }}</h1>
    <p>{{ 2 + 2 }}</p>
    <p>{{ user.email | uppercase }}</p>

    <!-- 2. PROPERTY BINDING - Set properties -->
    <img [src]="imageUrl" [alt]="imageAlt">
    <button [disabled]="isLoading">Submit</button>
    <div [class.active]="isActive"></div>
    <div [style.color]="textColor"></div>

    <!-- 3. EVENT BINDING - Handle events -->
    <button (click)="handleClick()">Click me</button>
    <input (input)="onInput($event)">
    <form (submit)="onSubmit($event)">

    <!-- 4. TWO-WAY BINDING - Sync data -->
    <input [(ngModel)]="searchTerm">
    <p>You typed: {{ searchTerm }}</p>
  `
})
export class DemoComponent {
  userName = 'Angular';
  user = { email: 'test@example.com' };
  imageUrl = '/assets/logo.png';
  imageAlt = 'Logo';
  isLoading = false;
  isActive = true;
  textColor = 'blue';
  searchTerm = '';

  handleClick() {
    console.log('Button clicked');
  }

  onInput(event: Event) {
    const input = event.target as HTMLInputElement;
    console.log(input.value);
  }

  onSubmit(event: Event) {
    event.preventDefault();
    console.log('Form submitted');
  }
}
```

**Important:** Two-way binding requires `FormsModule` to be imported.

---

### Q10: What is the difference between property binding and interpolation?

**Answer:**

Both transfer data from component to template, but have different use cases:

| Feature | Interpolation `{{ }}` | Property Binding `[ ]` |
|---------|----------------------|------------------------|
| **Syntax** | `{{ expression }}` | `[property]="expression"` |
| **Output** | Always string | Any type |
| **Use for** | Text content | Element properties |
| **With strings** | Either works | Either works |
| **With objects** | Only if stringifiable | Preserves object |

**Examples:**

```html
<!-- INTERPOLATION - Always converts to string -->
<p>{{ userName }}</p>            <!-- Output: "John" -->
<p>{{ user }}</p>                <!-- Output: "[object Object]" -->
<p>Welcome, {{ isAdmin ? 'Admin' : 'User' }}</p>

<!-- PROPERTY BINDING - Preserves type -->
<img [src]="imageUrl">           <!-- Sets src property -->
<button [disabled]="isLoading">  <!-- Sets disabled (boolean) -->
<app-child [user]="userObject">  <!-- Passes object to child -->

<!-- Both work for string attributes -->
<p title="{{ tooltipText }}">Hover</p>
<p [title]="tooltipText">Hover</p>

<!-- But property binding required for non-strings -->
<button [disabled]="isLoading">   <!-- Boolean -->
<div [hidden]="shouldHide">       <!-- Boolean -->
<app-list [items]="itemsArray">   <!-- Array/Object -->
```

**When to use:**

```
Use INTERPOLATION when:
├── Displaying text content
├── Simple string values
└── Text between tags

Use PROPERTY BINDING when:
├── Setting boolean attributes
├── Binding to element properties
├── Passing objects/arrays to child components
└── Dynamic attribute values
```

---

## Directives

### Q11: What are directives? Explain structural vs attribute directives.

**Answer:**

**Directives** are classes that add behavior to elements in Angular templates.

**Three types:**

```
┌─────────────────────────────────────────────────────────────────────────┐
│                         DIRECTIVE TYPES                                  │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  1. COMPONENTS                                                           │
│     └── Directives with templates (most common)                         │
│                                                                          │
│  2. STRUCTURAL DIRECTIVES  (*ngIf, *ngFor, *ngSwitch)                   │
│     └── Change DOM layout (add/remove elements)                         │
│     └── Marked with asterisk *                                          │
│                                                                          │
│  3. ATTRIBUTE DIRECTIVES  (ngClass, ngStyle, custom)                    │
│     └── Change appearance or behavior                                   │
│     └── Don't change DOM structure                                      │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**Structural Directives:**

```html
<!-- *ngIf - Conditionally include element -->
<div *ngIf="isLoggedIn">Welcome back!</div>
<div *ngIf="isLoggedIn; else loginTemplate">Welcome!</div>
<ng-template #loginTemplate>Please log in</ng-template>

<!-- *ngFor - Repeat element for each item -->
<ul>
  <li *ngFor="let item of items; let i = index; let odd = odd"
      [class.odd]="odd">
    {{ i + 1 }}. {{ item.name }}
  </li>
</ul>

<!-- *ngSwitch - Switch between views -->
<div [ngSwitch]="status">
  <p *ngSwitchCase="'active'">Active</p>
  <p *ngSwitchCase="'pending'">Pending</p>
  <p *ngSwitchDefault>Unknown</p>
</div>
```

**Attribute Directives:**

```html
<!-- ngClass - Dynamic CSS classes -->
<div [ngClass]="{ 'active': isActive, 'disabled': isDisabled }">
<div [ngClass]="['class1', 'class2']">
<div [ngClass]="classExpression">

<!-- ngStyle - Dynamic inline styles -->
<div [ngStyle]="{ 'color': textColor, 'font-size': fontSize + 'px' }">

<!-- Single class/style binding -->
<div [class.highlight]="isHighlighted">
<div [style.background-color]="bgColor">
```

**Key difference:**

```
Structural (*):  Modifies DOM structure
                 <div *ngIf="show"> → Adds/removes element

Attribute ([]):  Modifies existing element
                 <div [ngClass]="..."> → Element stays, class changes
```

---

### Q12: How does *ngFor work? Explain its features.

**Answer:**

`*ngFor` iterates over a collection and renders a template for each item.

**Basic syntax:**

```html
<li *ngFor="let item of items">{{ item }}</li>
```

**Full syntax with local variables:**

```html
<li *ngFor="let item of items;
            let i = index;      // Current index (0-based)
            let first = first;  // true if first item
            let last = last;    // true if last item
            let even = even;    // true if even index
            let odd = odd;      // true if odd index
            trackBy: trackByFn">
```

**Example:**

```typescript
@Component({
  template: `
    <table>
      <tr *ngFor="let user of users;
                  let i = index;
                  let isFirst = first;
                  let isLast = last;
                  trackBy: trackById">
        <td>{{ i + 1 }}</td>
        <td>{{ user.name }}</td>
        <td>
          <span *ngIf="isFirst">First!</span>
          <span *ngIf="isLast">Last!</span>
        </td>
      </tr>
    </table>
  `
})
export class UserListComponent {
  users = [
    { id: 1, name: 'Alice' },
    { id: 2, name: 'Bob' },
    { id: 3, name: 'Charlie' }
  ];

  // trackBy improves performance for large lists
  trackById(index: number, user: User): number {
    return user.id;  // Unique identifier
  }
}
```

**Why use trackBy?**

```
Without trackBy:
- Angular re-renders ALL items when list changes
- Poor performance for large lists

With trackBy:
- Angular tracks items by unique ID
- Only re-renders changed items
- Much better performance
```

```typescript
// trackBy function signature
trackByFn(index: number, item: Item): any {
  return item.id;  // Return unique identifier
}
```

---

## Component Communication

### Q13: How do parent and child components communicate?

**Answer:**

Angular provides several ways for components to communicate:

```
┌─────────────────────────────────────────────────────────────────────────┐
│                  COMPONENT COMMUNICATION PATTERNS                        │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  1. PARENT → CHILD:  @Input()                                           │
│     Pass data down via property binding                                 │
│                                                                          │
│  2. CHILD → PARENT:  @Output() + EventEmitter                           │
│     Emit events up via event binding                                    │
│                                                                          │
│  3. ANY → ANY:  Service with Subject/BehaviorSubject                    │
│     Shared state via dependency injection                               │
│                                                                          │
│  4. PARENT → CHILD:  @ViewChild()                                       │
│     Direct access to child component instance                           │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**1. @Input - Parent to Child:**

```typescript
// Child component
@Component({
  selector: 'app-child',
  template: `<h2>{{ title }}</h2>`
})
export class ChildComponent {
  @Input() title: string;
  @Input() user: User;
}

// Parent template
<app-child
  [title]="parentTitle"
  [user]="selectedUser">
</app-child>
```

**2. @Output - Child to Parent:**

```typescript
// Child component
@Component({
  selector: 'app-child',
  template: `<button (click)="onSave()">Save</button>`
})
export class ChildComponent {
  @Output() saved = new EventEmitter<User>();

  onSave() {
    this.saved.emit(this.user);
  }
}

// Parent component
@Component({
  template: `
    <app-child
      [user]="selectedUser"
      (saved)="handleSave($event)">
    </app-child>
  `
})
export class ParentComponent {
  handleSave(user: User) {
    console.log('User saved:', user);
  }
}
```

**Communication flow:**

```
┌─────────────────┐                    ┌─────────────────┐
│     PARENT      │                    │      CHILD      │
│                 │   [data]="value"   │                 │
│  parentData ────┼───────────────────>│ @Input() data   │
│                 │                    │                 │
│  handler($e) <──┼────────────────────┼ @Output() event │
│                 │   (event)="..."    │   .emit(value)  │
└─────────────────┘                    └─────────────────┘
```

---

### Q14: What are Pipes? How do you create a custom pipe?

**Answer:**

**Pipes** transform displayed values in templates without changing the underlying data.

**Built-in pipes:**

```html
<!-- DatePipe -->
{{ birthday | date }}              <!-- Jan 15, 2024 -->
{{ birthday | date:'short' }}      <!-- 1/15/24, 9:30 AM -->
{{ birthday | date:'yyyy-MM-dd' }} <!-- 2024-01-15 -->

<!-- CurrencyPipe -->
{{ price | currency }}             <!-- $9.99 -->
{{ price | currency:'EUR' }}       <!-- €9.99 -->

<!-- DecimalPipe -->
{{ value | number:'1.2-2' }}       <!-- 3.14 (1 digit, 2-2 decimals) -->

<!-- PercentPipe -->
{{ ratio | percent }}              <!-- 75% -->

<!-- UpperCase/LowerCase -->
{{ name | uppercase }}             <!-- JOHN -->
{{ name | lowercase }}             <!-- john -->
{{ name | titlecase }}             <!-- John Doe -->

<!-- JSON (debugging) -->
{{ object | json }}                <!-- {"name":"John"} -->

<!-- Chaining pipes -->
{{ birthday | date:'fullDate' | uppercase }}
```

**Creating a custom pipe:**

```typescript
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truncate'  // Usage: {{ text | truncate:20 }}
})
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit: number = 50, trail: string = '...'): string {
    if (!value) return '';
    if (value.length <= limit) return value;
    return value.substring(0, limit) + trail;
  }
}

// Usage in template
// {{ longText | truncate }}         → First 50 chars...
// {{ longText | truncate:20 }}      → First 20 chars...
// {{ longText | truncate:20:'>>>' }} → First 20 chars>>>
```

**Pure vs Impure pipes:**

```typescript
// Pure pipe (default) - only runs when input reference changes
@Pipe({ name: 'myPipe', pure: true })

// Impure pipe - runs on every change detection cycle
@Pipe({ name: 'myPipe', pure: false })  // Use sparingly!
```

**Register in module:**

```typescript
@NgModule({
  declarations: [TruncatePipe],
  // Or use standalone: true in @Pipe
})
```

---

## Summary

| Topic | Key Concepts |
|-------|--------------|
| **TypeScript** | Static typing, interfaces, generics, utility types |
| **Type System** | any vs unknown vs never, unions, intersections |
| **Angular Architecture** | Components, modules, services, DI |
| **Components** | @Component, selector, template, styles |
| **Data Binding** | Interpolation, property, event, two-way |
| **Directives** | Structural (*ngIf, *ngFor), Attribute (ngClass) |
| **Communication** | @Input (down), @Output (up), Services (any) |
| **Pipes** | Transform data, built-in and custom |

---

*Week 09 covers TypeScript fundamentals and Angular basics essential for building Angular applications.*

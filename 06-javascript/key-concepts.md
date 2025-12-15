# JavaScript Key Concepts for Application Developers

## Overview

This document outlines the essential JavaScript concepts every application developer must understand. JavaScript is the programming language of the web, enabling interactivity, dynamic content, and modern web applications.

---

## 1. JavaScript Fundamentals

### Why It Matters
- Only programming language that runs in browsers
- Powers both frontend and backend (Node.js)
- Essential for web development

### Key Concepts

| Concept | Description | Example |
|---------|-------------|---------|
| Variable | Stores data | `let name = "John";` |
| Data Type | Type of value | string, number, boolean |
| Operator | Performs operations | `+`, `-`, `===` |
| Statement | Instruction | `if`, `for`, `return` |
| Expression | Produces value | `5 + 3`, `x > 10` |

### Variable Declarations

| Keyword | Scope | Reassign | Redeclare | Hoisting |
|---------|-------|----------|-----------|----------|
| `var` | Function | Yes | Yes | Yes (undefined) |
| `let` | Block | Yes | No | No (TDZ) |
| `const` | Block | No | No | No (TDZ) |

```javascript
// var - function scoped, avoid in modern code
var oldWay = "outdated";

// let - block scoped, reassignable
let count = 0;
count = 1; // OK

// const - block scoped, not reassignable
const PI = 3.14159;
// PI = 3; // Error!

// const with objects - reference is constant, not contents
const user = { name: "John" };
user.name = "Jane"; // OK - modifying property
// user = {}; // Error - reassigning reference
```

### Data Types

| Type | Category | Examples |
|------|----------|----------|
| `string` | Primitive | `"hello"`, `'world'` |
| `number` | Primitive | `42`, `3.14`, `NaN` |
| `boolean` | Primitive | `true`, `false` |
| `null` | Primitive | `null` |
| `undefined` | Primitive | `undefined` |
| `symbol` | Primitive | `Symbol('id')` |
| `bigint` | Primitive | `9007199254740991n` |
| `object` | Reference | `{}`, `[]`, `function` |

```javascript
// Type checking
typeof "hello"     // "string"
typeof 42          // "number"
typeof true        // "boolean"
typeof undefined   // "undefined"
typeof null        // "object" (historical bug)
typeof {}          // "object"
typeof []          // "object" (arrays are objects)
typeof function(){} // "function"

// Better array check
Array.isArray([1, 2, 3]); // true
```

### Operators
```javascript
// Arithmetic
5 + 3    // 8
10 - 4   // 6
3 * 4    // 12
15 / 3   // 5
17 % 5   // 2 (remainder)
2 ** 3   // 8 (exponent)

// Comparison
5 == "5"   // true (loose equality, type coercion)
5 === "5"  // false (strict equality, no coercion)
5 != "5"   // false
5 !== "5"  // true

// Logical
true && false  // false (AND)
true || false  // true (OR)
!true          // false (NOT)

// Nullish coalescing
null ?? "default"      // "default"
undefined ?? "default" // "default"
0 ?? "default"         // 0 (0 is not nullish)

// Optional chaining
user?.address?.city    // undefined if any is null/undefined
```

---

## 2. Control Flow

### Why It Matters
- Directs program execution
- Implements logic and decisions
- Essential for any program

### Conditionals
```javascript
// if-else
if (condition) {
    // code
} else if (anotherCondition) {
    // code
} else {
    // code
}

// Ternary operator
const result = condition ? valueIfTrue : valueIfFalse;

// Switch
switch (value) {
    case 1:
        console.log("One");
        break;
    case 2:
        console.log("Two");
        break;
    default:
        console.log("Other");
}
```

### Loops
```javascript
// for loop
for (let i = 0; i < 5; i++) {
    console.log(i);
}

// while loop
let i = 0;
while (i < 5) {
    console.log(i);
    i++;
}

// do-while (executes at least once)
let j = 0;
do {
    console.log(j);
    j++;
} while (j < 5);

// for...of (iterate values)
const arr = [1, 2, 3];
for (const value of arr) {
    console.log(value);
}

// for...in (iterate keys/indices)
const obj = { a: 1, b: 2 };
for (const key in obj) {
    console.log(key, obj[key]);
}

// Array methods (preferred)
arr.forEach(item => console.log(item));
```

---

## 3. Functions

### Why It Matters
- Reusable code blocks
- Foundation of JavaScript programming
- Enable modular design

### Key Concepts

| Type | Syntax | Hoisting | `this` binding |
|------|--------|----------|----------------|
| Declaration | `function name() {}` | Yes | Dynamic |
| Expression | `const fn = function() {}` | No | Dynamic |
| Arrow | `const fn = () => {}` | No | Lexical |

### Function Types
```javascript
// Function Declaration (hoisted)
function greet(name) {
    return `Hello, ${name}!`;
}

// Function Expression
const greet = function(name) {
    return `Hello, ${name}!`;
};

// Arrow Function (ES6+)
const greet = (name) => `Hello, ${name}!`;

// Arrow function variations
const double = x => x * 2;              // Single param, implicit return
const add = (a, b) => a + b;            // Multiple params, implicit return
const process = (x) => {                // With block, explicit return
    const result = x * 2;
    return result;
};
```

### Parameters and Arguments
```javascript
// Default parameters
function greet(name = "World") {
    return `Hello, ${name}!`;
}

// Rest parameters (collect remaining args)
function sum(...numbers) {
    return numbers.reduce((a, b) => a + b, 0);
}
sum(1, 2, 3, 4); // 10

// Spread operator (expand array to args)
const nums = [1, 2, 3];
Math.max(...nums); // 3

// Destructuring parameters
function createUser({ name, age, role = "user" }) {
    return { name, age, role };
}
createUser({ name: "John", age: 30 });
```

### Higher-Order Functions
```javascript
// Function that takes function as argument
function doOperation(a, b, operation) {
    return operation(a, b);
}
doOperation(5, 3, (a, b) => a + b); // 8

// Function that returns function
function multiplier(factor) {
    return (number) => number * factor;
}
const double = multiplier(2);
double(5); // 10

// Common array methods (higher-order functions)
const numbers = [1, 2, 3, 4, 5];

// map - transform each element
numbers.map(n => n * 2);        // [2, 4, 6, 8, 10]

// filter - select elements
numbers.filter(n => n > 2);      // [3, 4, 5]

// reduce - accumulate to single value
numbers.reduce((sum, n) => sum + n, 0); // 15

// find - first match
numbers.find(n => n > 2);        // 3

// some/every - test conditions
numbers.some(n => n > 4);        // true
numbers.every(n => n > 0);       // true
```

---

## 4. Scope and Closures

### Why It Matters
- Controls variable visibility
- Prevents naming conflicts
- Enables powerful patterns

### Scope Types
```javascript
// Global scope
var globalVar = "global"; // Available everywhere

// Function scope
function example() {
    var functionScoped = "function"; // Only in this function
}

// Block scope
if (true) {
    let blockScoped = "block";  // Only in this block
    const alsoBlock = "block";  // Only in this block
}

// Lexical scope (nested functions)
function outer() {
    const outerVar = "outer";

    function inner() {
        console.log(outerVar); // Can access outer's variables
    }
    inner();
}
```

### Closures
```javascript
// Closure - function remembers its lexical scope
function createCounter() {
    let count = 0;  // Private variable

    return {
        increment: () => ++count,
        decrement: () => --count,
        getCount: () => count
    };
}

const counter = createCounter();
counter.increment(); // 1
counter.increment(); // 2
counter.getCount();  // 2
// count is not accessible directly

// Practical closure example
function makeAdder(x) {
    return function(y) {
        return x + y;  // x is "closed over"
    };
}
const add5 = makeAdder(5);
add5(3);  // 8
add5(10); // 15
```

---

## 5. Objects

### Why It Matters
- Primary data structure in JavaScript
- Foundation for OOP
- Used everywhere in JS applications

### Key Concepts
```javascript
// Object literal
const user = {
    name: "John",
    age: 30,
    email: "john@example.com",
    greet() {
        return `Hello, I'm ${this.name}`;
    }
};

// Accessing properties
user.name;           // "John" (dot notation)
user["age"];         // 30 (bracket notation)
user.greet();        // "Hello, I'm John"

// Adding/modifying properties
user.phone = "555-1234";
user.age = 31;

// Deleting properties
delete user.phone;

// Property shorthand
const name = "Jane";
const age = 25;
const person = { name, age }; // { name: "Jane", age: 25 }

// Computed property names
const prop = "dynamic";
const obj = { [prop]: "value" }; // { dynamic: "value" }
```

### Object Methods
```javascript
// Object.keys/values/entries
const user = { name: "John", age: 30 };
Object.keys(user);    // ["name", "age"]
Object.values(user);  // ["John", 30]
Object.entries(user); // [["name", "John"], ["age", 30]]

// Object.assign (shallow copy/merge)
const copy = Object.assign({}, user);
const merged = Object.assign({}, obj1, obj2);

// Spread operator (shallow copy)
const copy = { ...user };
const merged = { ...obj1, ...obj2 };

// Object.freeze/seal
Object.freeze(user);  // No changes at all
Object.seal(user);    // Can modify, can't add/delete

// Check property existence
"name" in user;                  // true
user.hasOwnProperty("name");     // true
```

### Destructuring
```javascript
// Object destructuring
const { name, age } = user;
const { name: userName, age: userAge } = user; // Rename
const { name, role = "user" } = user;          // Default value

// Nested destructuring
const { address: { city } } = user;

// Function parameter destructuring
function greet({ name, age }) {
    return `${name} is ${age}`;
}
```

---

## 6. Arrays

### Why It Matters
- Store ordered collections
- Essential data structure
- Rich built-in methods

### Array Methods

| Method | Purpose | Returns | Mutates |
|--------|---------|---------|---------|
| `push()` | Add to end | new length | Yes |
| `pop()` | Remove from end | removed item | Yes |
| `unshift()` | Add to start | new length | Yes |
| `shift()` | Remove from start | removed item | Yes |
| `splice()` | Add/remove at index | removed items | Yes |
| `slice()` | Extract portion | new array | No |
| `concat()` | Join arrays | new array | No |
| `map()` | Transform all | new array | No |
| `filter()` | Select some | new array | No |
| `reduce()` | Accumulate | single value | No |
| `find()` | First match | element | No |
| `findIndex()` | First match index | index | No |
| `includes()` | Check existence | boolean | No |
| `sort()` | Sort in place | same array | Yes |

```javascript
const arr = [1, 2, 3, 4, 5];

// Transform
arr.map(x => x * 2);           // [2, 4, 6, 8, 10]

// Filter
arr.filter(x => x > 2);         // [3, 4, 5]

// Reduce
arr.reduce((sum, x) => sum + x, 0); // 15

// Find
arr.find(x => x > 3);           // 4
arr.findIndex(x => x > 3);      // 3

// Check
arr.includes(3);                // true
arr.some(x => x > 4);           // true
arr.every(x => x > 0);          // true

// Sort (mutates!)
const nums = [3, 1, 4, 1, 5];
nums.sort((a, b) => a - b);     // [1, 1, 3, 4, 5]

// Spread operator
const combined = [...arr1, ...arr2];
const copy = [...arr];
```

### Array Destructuring
```javascript
const [first, second, ...rest] = [1, 2, 3, 4, 5];
// first = 1, second = 2, rest = [3, 4, 5]

// Swap values
let a = 1, b = 2;
[a, b] = [b, a]; // a = 2, b = 1

// Skip elements
const [, , third] = [1, 2, 3]; // third = 3
```

---

## 7. DOM Manipulation

### Why It Matters
- Interact with web pages
- Create dynamic interfaces
- Respond to user actions

### Selecting Elements
```javascript
// Single element
document.getElementById("id");
document.querySelector(".class");
document.querySelector("#id");

// Multiple elements
document.getElementsByClassName("class");  // HTMLCollection
document.getElementsByTagName("div");      // HTMLCollection
document.querySelectorAll(".class");       // NodeList

// Modern approach (returns first match)
const element = document.querySelector(".my-class");

// Multiple elements (returns all matches)
const elements = document.querySelectorAll(".my-class");
elements.forEach(el => console.log(el));
```

### Modifying Elements
```javascript
const el = document.querySelector("#myElement");

// Content
el.textContent = "New text";          // Text only
el.innerHTML = "<strong>Bold</strong>"; // HTML

// Attributes
el.setAttribute("data-id", "123");
el.getAttribute("data-id");
el.removeAttribute("data-id");
el.id = "newId";
el.className = "new-class";

// Classes
el.classList.add("active");
el.classList.remove("inactive");
el.classList.toggle("visible");
el.classList.contains("active");      // true/false

// Styles
el.style.color = "red";
el.style.backgroundColor = "blue";
el.style.cssText = "color: red; background: blue;";
```

### Creating and Removing Elements
```javascript
// Create element
const div = document.createElement("div");
div.textContent = "Hello";
div.className = "greeting";

// Add to DOM
parent.appendChild(div);
parent.insertBefore(div, reference);
parent.append(div);                   // Can append multiple
parent.prepend(div);

// Remove from DOM
element.remove();
parent.removeChild(element);

// Clone element
const clone = element.cloneNode(true); // true = deep clone
```

---

## 8. Events

### Why It Matters
- User interaction handling
- Responsive interfaces
- Core of web applications

### Event Handling
```javascript
// addEventListener (recommended)
element.addEventListener("click", function(event) {
    console.log("Clicked!", event);
});

// Arrow function
element.addEventListener("click", (e) => {
    console.log(e.target);
});

// Named function (can be removed)
function handleClick(e) {
    console.log("Clicked!");
}
element.addEventListener("click", handleClick);
element.removeEventListener("click", handleClick);

// Event object properties
element.addEventListener("click", (e) => {
    e.target;           // Element that triggered event
    e.currentTarget;    // Element listener is attached to
    e.type;             // Event type ("click")
    e.preventDefault(); // Prevent default behavior
    e.stopPropagation(); // Stop bubbling
});
```

### Common Events

| Event | Triggered When |
|-------|----------------|
| `click` | Element clicked |
| `dblclick` | Double-clicked |
| `mouseenter` | Mouse enters element |
| `mouseleave` | Mouse leaves element |
| `keydown` | Key pressed |
| `keyup` | Key released |
| `submit` | Form submitted |
| `input` | Input value changes |
| `change` | Input loses focus with new value |
| `focus` | Element gains focus |
| `blur` | Element loses focus |
| `load` | Page/resource loaded |
| `DOMContentLoaded` | DOM ready |

### Event Delegation
```javascript
// Instead of adding listener to each item
// Add one listener to parent

document.querySelector("#list").addEventListener("click", (e) => {
    // Check if clicked element is a list item
    if (e.target.matches("li")) {
        console.log("List item clicked:", e.target.textContent);
    }
});

// Works for dynamically added elements too!
```

---

## 9. Asynchronous JavaScript

### Why It Matters
- Web APIs are asynchronous
- Prevents UI blocking
- Essential for modern web apps

### Callbacks
```javascript
// Traditional callback pattern
function fetchData(callback) {
    setTimeout(() => {
        callback("Data received");
    }, 1000);
}

fetchData((data) => {
    console.log(data);
});

// Callback hell (problem)
getData((data) => {
    processData(data, (processed) => {
        saveData(processed, (saved) => {
            // Nested callbacks get messy
        });
    });
});
```

### Promises
```javascript
// Creating a promise
const promise = new Promise((resolve, reject) => {
    setTimeout(() => {
        const success = true;
        if (success) {
            resolve("Data received");
        } else {
            reject("Error occurred");
        }
    }, 1000);
});

// Using promises
promise
    .then(data => {
        console.log(data);
        return processData(data);
    })
    .then(processed => {
        console.log(processed);
    })
    .catch(error => {
        console.error(error);
    })
    .finally(() => {
        console.log("Done");
    });

// Promise methods
Promise.all([p1, p2, p3]);      // Wait for all
Promise.race([p1, p2, p3]);     // First to resolve/reject
Promise.allSettled([p1, p2]);   // Wait for all, get all results
Promise.any([p1, p2, p3]);      // First to resolve
```

### Async/Await
```javascript
// Async function
async function fetchUser() {
    try {
        const response = await fetch("/api/user");
        const user = await response.json();
        return user;
    } catch (error) {
        console.error("Error:", error);
        throw error;
    }
}

// Using async function
fetchUser()
    .then(user => console.log(user))
    .catch(error => console.error(error));

// Or in another async function
async function main() {
    const user = await fetchUser();
    console.log(user);
}

// Parallel async operations
async function fetchAll() {
    const [users, posts] = await Promise.all([
        fetch("/api/users").then(r => r.json()),
        fetch("/api/posts").then(r => r.json())
    ]);
    return { users, posts };
}
```

### Fetch API
```javascript
// GET request
fetch("/api/users")
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => console.log(data))
    .catch(error => console.error(error));

// POST request
fetch("/api/users", {
    method: "POST",
    headers: {
        "Content-Type": "application/json"
    },
    body: JSON.stringify({ name: "John", email: "john@example.com" })
})
    .then(response => response.json())
    .then(data => console.log(data));

// With async/await
async function createUser(userData) {
    const response = await fetch("/api/users", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(userData)
    });

    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    return response.json();
}
```

---

## 10. ES6+ Features

### Why It Matters
- Modern JavaScript syntax
- Cleaner, more readable code
- Used in all modern frameworks

### Template Literals
```javascript
const name = "World";
const greeting = `Hello, ${name}!`;  // String interpolation
const multiline = `
    Line 1
    Line 2
    Line 3
`;

// Tagged templates
function highlight(strings, ...values) {
    return strings.reduce((result, str, i) =>
        `${result}${str}<mark>${values[i] || ''}</mark>`, ''
    );
}
const text = highlight`Hello ${name}, you have ${count} messages`;
```

### Modules
```javascript
// Named exports (math.js)
export const PI = 3.14159;
export function add(a, b) { return a + b; }
export function subtract(a, b) { return a - b; }

// Default export (user.js)
export default class User {
    constructor(name) {
        this.name = name;
    }
}

// Named imports
import { add, subtract, PI } from './math.js';
import { add as sum } from './math.js';  // Rename
import * as math from './math.js';        // All as namespace

// Default import
import User from './user.js';

// Mixed
import User, { add, PI } from './module.js';
```

### Classes
```javascript
class Animal {
    constructor(name) {
        this.name = name;
    }

    speak() {
        console.log(`${this.name} makes a sound`);
    }

    static isAnimal(obj) {
        return obj instanceof Animal;
    }
}

class Dog extends Animal {
    constructor(name, breed) {
        super(name);  // Call parent constructor
        this.breed = breed;
    }

    speak() {
        console.log(`${this.name} barks`);
    }

    // Getter
    get info() {
        return `${this.name} is a ${this.breed}`;
    }

    // Setter
    set nickname(value) {
        this._nickname = value;
    }
}

const dog = new Dog("Rex", "German Shepherd");
dog.speak();        // "Rex barks"
dog.info;           // "Rex is a German Shepherd"
Dog.isAnimal(dog);  // true
```

---

## Quick Reference Card

### Variable Declaration
```javascript
const constant = "unchangeable";
let variable = "can change";
// Avoid var
```

### Arrow Functions
```javascript
const fn = (a, b) => a + b;
const fn = x => x * 2;
const fn = () => ({ key: "value" });
```

### Destructuring
```javascript
const { name, age } = object;
const [first, second] = array;
const { name: n, age: a = 0 } = object;
```

### Spread/Rest
```javascript
const copy = [...array];
const merged = { ...obj1, ...obj2 };
const fn = (...args) => args.reduce((a, b) => a + b);
```

### Array Methods
```javascript
arr.map(x => x * 2);
arr.filter(x => x > 0);
arr.reduce((acc, x) => acc + x, 0);
arr.find(x => x > 5);
arr.includes(value);
```

### Async/Await
```javascript
async function getData() {
    try {
        const res = await fetch(url);
        return await res.json();
    } catch (err) {
        console.error(err);
    }
}
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Declare variables with appropriate scope
- [ ] Understand all JavaScript data types
- [ ] Write functions using various syntax styles
- [ ] Work with objects and arrays effectively
- [ ] Understand scope and closures
- [ ] Manipulate the DOM
- [ ] Handle events and user interactions
- [ ] Work with Promises and async/await
- [ ] Use ES6+ features confidently
- [ ] Debug JavaScript code

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 7: Bootstrap](../07-bootstrap/) - CSS framework
- Practice building interactive web applications
- Explore JavaScript frameworks (Angular, React, Vue)

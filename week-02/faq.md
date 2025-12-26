# Week 02 - Interview FAQ

This document contains frequently asked interview questions and comprehensive answers for Week 02 topics: JavaScript, Bootstrap, and jQuery.

---

## Table of Contents

1. [JavaScript Fundamentals](#javascript-fundamentals)
2. [JavaScript DOM & Events](#javascript-dom--events)
3. [JavaScript ES6+ Features](#javascript-es6-features)
4. [Bootstrap Framework](#bootstrap-framework)
5. [jQuery](#jquery)

---

## JavaScript Fundamentals

### Q1: What is JavaScript? How does it differ from Java?

**Answer:**

JavaScript is a high-level, interpreted, dynamic programming language primarily used for adding interactivity to web pages. Despite the similar name, JavaScript and Java are completely different languages.

**Key differences:**

| Aspect | JavaScript | Java |
|--------|------------|------|
| Type | Interpreted, dynamic | Compiled, static |
| Typing | Weakly typed | Strongly typed |
| Execution | Browser or Node.js | JVM (Java Virtual Machine) |
| OOP | Prototype-based | Class-based |
| Use case | Web interactivity, full-stack | Enterprise, Android, backend |
| Syntax | C-like but flexible | C-like but strict |

**JavaScript characteristics:**
- Runs in browsers (client-side) and servers (Node.js)
- Single-threaded with event loop
- First-class functions
- Dynamic typing
- Prototype-based inheritance

---

### Q2: Explain the difference between `var`, `let`, and `const`.

**Answer:**

These are three ways to declare variables in JavaScript:

| Feature | var | let | const |
|---------|-----|-----|-------|
| Scope | Function-scoped | Block-scoped | Block-scoped |
| Hoisting | Hoisted (undefined) | Hoisted (TDZ) | Hoisted (TDZ) |
| Redeclaration | Allowed | Not allowed | Not allowed |
| Reassignment | Allowed | Allowed | Not allowed |
| Must initialize | No | No | Yes |

**Examples:**

```javascript
// var - function scoped, hoisted
function example() {
    console.log(x); // undefined (hoisted)
    var x = 5;
    var x = 10; // redeclaration OK
}

// let - block scoped
if (true) {
    let y = 5;
}
console.log(y); // ReferenceError

// const - block scoped, immutable binding
const PI = 3.14159;
PI = 3; // TypeError

// const with objects (reference is immutable, not content)
const obj = { name: 'John' };
obj.name = 'Jane'; // OK - modifying content
obj = {}; // TypeError - can't reassign reference
```

**Best practice:** Use `const` by default, `let` when reassignment is needed, avoid `var`.

---

### Q3: What are JavaScript data types?

**Answer:**

JavaScript has **8 data types** divided into primitives and objects:

**Primitive types (7):**
1. **string** - Text: `"Hello"`, `'World'`
2. **number** - All numbers: `42`, `3.14`, `NaN`, `Infinity`
3. **bigint** - Large integers: `9007199254740991n`
4. **boolean** - `true` or `false`
5. **undefined** - Variable declared but not assigned
6. **null** - Intentional absence of value
7. **symbol** - Unique identifier: `Symbol('id')`

**Non-primitive (1):**
8. **object** - Collections: `{}`, `[]`, `function(){}`, `new Date()`

**Type checking:**
```javascript
typeof "hello"      // "string"
typeof 42           // "number"
typeof true         // "boolean"
typeof undefined    // "undefined"
typeof null         // "object" (historical bug)
typeof Symbol()     // "symbol"
typeof {}           // "object"
typeof []           // "object"
typeof function(){} // "function"

// Better array check
Array.isArray([])   // true
```

---

### Q4: Explain truthy and falsy values in JavaScript.

**Answer:**

In JavaScript, values are coerced to boolean in conditional contexts.

**Falsy values (8 total):**
```javascript
false       // boolean false
0           // zero
-0          // negative zero
0n          // BigInt zero
""          // empty string
null        // null
undefined   // undefined
NaN         // Not a Number
```

**Truthy values (everything else):**
```javascript
true
42, -42, 3.14      // non-zero numbers
"hello", "0", " "  // non-empty strings
[]                 // empty array (object)
{}                 // empty object
function(){}       // functions
```

**Common gotchas:**
```javascript
Boolean([])        // true (empty array is truthy!)
Boolean({})        // true (empty object is truthy!)
Boolean("0")       // true (string "0" is truthy!)
Boolean("false")   // true (string "false" is truthy!)

// Safe checks
if (array.length > 0) { }  // Check array has items
if (Object.keys(obj).length > 0) { }  // Check object has properties
```

---

### Q5: What is the difference between `==` and `===`?

**Answer:**

| Operator | Name | Type Coercion |
|----------|------|---------------|
| `==` | Loose equality | Yes (converts types) |
| `===` | Strict equality | No (types must match) |

**Examples:**
```javascript
// Loose equality (==) - converts types
5 == "5"           // true (string converted to number)
0 == false         // true (false converted to 0)
null == undefined  // true (special rule)
"" == false        // true (both convert to 0)

// Strict equality (===) - no conversion
5 === "5"          // false (different types)
0 === false        // false (different types)
null === undefined // false (different types)

// Objects compared by reference
{} === {}          // false (different objects)
[] === []          // false (different arrays)

const a = { x: 1 };
const b = a;
a === b            // true (same reference)
```

**Best practice:** Always use `===` and `!==` to avoid unexpected type coercion bugs.

---

### Q6: Explain closures in JavaScript.

**Answer:**

A **closure** is a function that remembers and can access variables from its outer (enclosing) scope, even after the outer function has finished executing.

**How it works:**
```javascript
function outer() {
    let count = 0;  // Variable in outer scope

    function inner() {
        count++;    // Accesses outer variable
        return count;
    }

    return inner;   // Return the inner function
}

const counter = outer();
console.log(counter()); // 1
console.log(counter()); // 2
console.log(counter()); // 3
// 'count' is preserved in closure
```

**Practical uses:**

1. **Data privacy:**
```javascript
function createBankAccount(initialBalance) {
    let balance = initialBalance;  // Private variable

    return {
        deposit(amount) { balance += amount; },
        withdraw(amount) { balance -= amount; },
        getBalance() { return balance; }
    };
}

const account = createBankAccount(100);
account.deposit(50);
console.log(account.getBalance()); // 150
console.log(account.balance);      // undefined (private!)
```

2. **Function factories:**
```javascript
function multiplier(factor) {
    return function(number) {
        return number * factor;
    };
}

const double = multiplier(2);
const triple = multiplier(3);
console.log(double(5)); // 10
console.log(triple(5)); // 15
```

---

### Q7: What is hoisting in JavaScript?

**Answer:**

**Hoisting** is JavaScript's behavior of moving declarations to the top of their scope during compilation, before code execution.

**Variable hoisting:**
```javascript
// What you write:
console.log(x);  // undefined (not ReferenceError!)
var x = 5;

// How JS interprets it:
var x;           // Declaration hoisted
console.log(x);  // undefined
x = 5;           // Assignment stays in place

// let/const are hoisted but in "Temporal Dead Zone"
console.log(y);  // ReferenceError: Cannot access 'y' before initialization
let y = 5;
```

**Function hoisting:**
```javascript
// Function declarations are fully hoisted
sayHello();  // "Hello!" - works!

function sayHello() {
    console.log("Hello!");
}

// Function expressions are NOT hoisted
sayBye();  // TypeError: sayBye is not a function

var sayBye = function() {
    console.log("Bye!");
};
```

**Key points:**
- `var` declarations hoisted, initialized as `undefined`
- `let`/`const` hoisted but in Temporal Dead Zone (TDZ)
- Function declarations fully hoisted (name + body)
- Function expressions only have variable hoisted

---

### Q8: Explain the `this` keyword in JavaScript.

**Answer:**

`this` refers to the context in which a function is called, not where it's defined.

**Different contexts:**

```javascript
// 1. Global context
console.log(this);  // window (browser) or global (Node.js)

// 2. Object method
const obj = {
    name: 'John',
    greet() {
        console.log(this.name);  // 'John' - this = obj
    }
};
obj.greet();

// 3. Regular function (non-strict mode)
function showThis() {
    console.log(this);  // window/global
}

// 4. Arrow function (inherits from enclosing scope)
const obj2 = {
    name: 'Jane',
    greet: () => {
        console.log(this.name);  // undefined - this = outer scope
    },
    greetCorrect() {
        const arrow = () => console.log(this.name);
        arrow();  // 'Jane' - inherits from greetCorrect
    }
};

// 5. Constructor function
function Person(name) {
    this.name = name;  // this = new instance
}
const p = new Person('Bob');

// 6. Explicit binding
function greet() {
    console.log(this.name);
}
greet.call({ name: 'Alice' });   // 'Alice'
greet.apply({ name: 'Bob' });    // 'Bob'
const bound = greet.bind({ name: 'Charlie' });
bound();  // 'Charlie'
```

---

## JavaScript DOM & Events

### Q9: What is the DOM? How do you access elements?

**Answer:**

The **DOM (Document Object Model)** is a programming interface that represents HTML as a tree of objects, allowing JavaScript to access and manipulate page content.

**Selecting elements:**
```javascript
// By ID (returns single element)
const header = document.getElementById('header');

// By class (returns HTMLCollection)
const items = document.getElementsByClassName('item');

// By tag (returns HTMLCollection)
const paragraphs = document.getElementsByTagName('p');

// CSS selector (returns first match)
const firstBtn = document.querySelector('.btn');

// CSS selector (returns all matches - NodeList)
const allBtns = document.querySelectorAll('.btn');

// Modern preference: querySelector/querySelectorAll
const nav = document.querySelector('nav.main-nav');
const links = document.querySelectorAll('nav a');
```

**Traversing the DOM:**
```javascript
const element = document.querySelector('.item');

// Parent
element.parentElement
element.parentNode

// Children
element.children           // HTMLCollection of child elements
element.childNodes         // NodeList including text nodes
element.firstElementChild
element.lastElementChild

// Siblings
element.nextElementSibling
element.previousElementSibling
```

---

### Q10: How do you create and modify DOM elements?

**Answer:**

**Creating elements:**
```javascript
// Create element
const div = document.createElement('div');
div.id = 'myDiv';
div.className = 'container';
div.textContent = 'Hello World';

// Create with innerHTML
div.innerHTML = '<span class="highlight">Hello</span> World';

// Append to DOM
document.body.appendChild(div);

// Modern methods
parent.append(child);        // Append at end
parent.prepend(child);       // Insert at beginning
element.before(newElement);  // Insert before
element.after(newElement);   // Insert after
element.replaceWith(newElement);  // Replace
```

**Modifying elements:**
```javascript
const el = document.querySelector('.box');

// Content
el.textContent = 'New text';     // Text only (safer)
el.innerHTML = '<b>Bold</b>';    // HTML content

// Attributes
el.setAttribute('data-id', '123');
el.getAttribute('data-id');       // '123'
el.removeAttribute('data-id');
el.hasAttribute('data-id');       // false

// Data attributes
el.dataset.id = '123';            // Sets data-id
console.log(el.dataset.id);       // '123'

// Classes
el.classList.add('active');
el.classList.remove('hidden');
el.classList.toggle('visible');
el.classList.contains('active');  // true
el.classList.replace('old', 'new');

// Styles
el.style.backgroundColor = 'blue';
el.style.fontSize = '16px';
el.style.cssText = 'color: red; padding: 10px;';
```

**Removing elements:**
```javascript
element.remove();                  // Modern
parent.removeChild(child);         // Traditional
element.innerHTML = '';            // Remove all children
```

---

### Q11: Explain event handling in JavaScript.

**Answer:**

Events are actions that occur in the browser (clicks, key presses, form submissions) that JavaScript can respond to.

**Adding event listeners:**
```javascript
const button = document.querySelector('#myButton');

// addEventListener (recommended)
button.addEventListener('click', function(event) {
    console.log('Clicked!', event.target);
});

// Arrow function
button.addEventListener('click', (e) => {
    console.log('Clicked!');
});

// Named function (easier to remove)
function handleClick(e) {
    console.log('Clicked!');
}
button.addEventListener('click', handleClick);
button.removeEventListener('click', handleClick);

// Options
button.addEventListener('click', handleClick, {
    once: true,      // Remove after first trigger
    capture: true,   // Capture phase instead of bubble
    passive: true    // Won't call preventDefault
});
```

**The event object:**
```javascript
element.addEventListener('click', function(e) {
    e.target          // Element that triggered event
    e.currentTarget   // Element listener is attached to
    e.type            // Event type ('click')
    e.preventDefault()   // Prevent default action
    e.stopPropagation()  // Stop event bubbling

    // Mouse events
    e.clientX, e.clientY  // Mouse position

    // Keyboard events
    e.key             // Key pressed ('Enter', 'a')
    e.code            // Physical key ('KeyA')
    e.altKey, e.ctrlKey, e.shiftKey  // Modifier keys
});
```

**Common events:**
- **Mouse:** click, dblclick, mouseenter, mouseleave, mousemove
- **Keyboard:** keydown, keyup, keypress
- **Form:** submit, change, input, focus, blur
- **Window:** load, DOMContentLoaded, resize, scroll

---

### Q12: What is event bubbling and capturing?

**Answer:**

When an event occurs on a nested element, it goes through three phases:

```
1. CAPTURING PHASE: Window → Document → HTML → Body → ... → Target
2. TARGET PHASE: Event reaches the target element
3. BUBBLING PHASE: Target → ... → Body → HTML → Document → Window
```

**Example:**
```html
<div id="outer">
    <div id="inner">
        <button id="btn">Click</button>
    </div>
</div>
```

```javascript
// By default, handlers run during BUBBLING phase
document.getElementById('outer').addEventListener('click', () => {
    console.log('Outer clicked');  // Runs 3rd
});

document.getElementById('inner').addEventListener('click', () => {
    console.log('Inner clicked');  // Runs 2nd
});

document.getElementById('btn').addEventListener('click', () => {
    console.log('Button clicked'); // Runs 1st
});

// Click button outputs:
// "Button clicked"
// "Inner clicked"
// "Outer clicked"
```

**Capturing phase:**
```javascript
// Third parameter true = capture phase
element.addEventListener('click', handler, true);
// or
element.addEventListener('click', handler, { capture: true });
```

**Stopping propagation:**
```javascript
button.addEventListener('click', (e) => {
    e.stopPropagation();  // Stop bubbling to parents
    console.log('Only button handler runs');
});
```

**Event delegation:**
```javascript
// Instead of adding listeners to each <li>
document.querySelector('ul').addEventListener('click', (e) => {
    if (e.target.tagName === 'LI') {
        console.log('Clicked:', e.target.textContent);
    }
});
```

---

### Q13: What is event delegation and why is it useful?

**Answer:**

**Event delegation** is a pattern where you attach a single event listener to a parent element to handle events for all its children, including dynamically added ones.

**Why it's useful:**
1. **Performance:** One listener instead of many
2. **Dynamic elements:** Works with elements added later
3. **Memory:** Fewer event listeners = less memory
4. **Cleaner code:** Centralized event handling

**Example without delegation (problematic):**
```javascript
// Must add listener to each button
// New buttons don't get the listener
document.querySelectorAll('.delete-btn').forEach(btn => {
    btn.addEventListener('click', handleDelete);
});
```

**With delegation (better):**
```javascript
// Single listener on parent
document.querySelector('#todo-list').addEventListener('click', (e) => {
    // Check if clicked element matches
    if (e.target.classList.contains('delete-btn')) {
        const todoItem = e.target.closest('.todo-item');
        todoItem.remove();
    }

    if (e.target.classList.contains('edit-btn')) {
        // Handle edit
    }
});

// Works for dynamically added items too!
function addTodo(text) {
    const li = document.createElement('li');
    li.className = 'todo-item';
    li.innerHTML = `
        <span>${text}</span>
        <button class="edit-btn">Edit</button>
        <button class="delete-btn">Delete</button>
    `;
    document.querySelector('#todo-list').appendChild(li);
    // No need to add event listeners!
}
```

**Using `closest()` for nested elements:**
```javascript
document.addEventListener('click', (e) => {
    const card = e.target.closest('.card');
    if (card) {
        // Handle click anywhere inside .card
    }
});
```

---

## JavaScript ES6+ Features

### Q14: What are arrow functions? How do they differ from regular functions?

**Answer:**

Arrow functions are a concise syntax for writing functions introduced in ES6.

**Syntax:**
```javascript
// Regular function
function add(a, b) {
    return a + b;
}

// Arrow function
const add = (a, b) => a + b;

// Variations
const greet = name => `Hello, ${name}`;     // Single param: no parentheses
const random = () => Math.random();          // No params: empty parentheses
const calculate = (a, b) => {               // Multi-line: use braces
    const result = a * b;
    return result + 10;
};
```

**Key differences:**

| Feature | Regular Function | Arrow Function |
|---------|-----------------|----------------|
| `this` binding | Dynamic (caller) | Lexical (enclosing scope) |
| `arguments` object | Yes | No (use rest params) |
| Constructor | Can use `new` | Cannot use `new` |
| `prototype` property | Yes | No |
| Method definition | Suitable | Not ideal |

**`this` difference:**
```javascript
const obj = {
    name: 'Timer',

    // Regular function - `this` depends on caller
    startRegular: function() {
        setTimeout(function() {
            console.log(this.name);  // undefined (this = window)
        }, 1000);
    },

    // Arrow function - `this` from enclosing scope
    startArrow: function() {
        setTimeout(() => {
            console.log(this.name);  // 'Timer' (this = obj)
        }, 1000);
    }
};
```

**When NOT to use arrow functions:**
- Object methods (need dynamic `this`)
- Event handlers needing `this` as element
- Prototype methods
- Functions using `arguments`

---

### Q15: Explain destructuring in JavaScript.

**Answer:**

Destructuring extracts values from arrays or properties from objects into distinct variables.

**Array destructuring:**
```javascript
const colors = ['red', 'green', 'blue'];

// Basic
const [first, second, third] = colors;
console.log(first);  // 'red'

// Skip elements
const [, , last] = colors;
console.log(last);  // 'blue'

// Default values
const [a, b, c, d = 'yellow'] = colors;
console.log(d);  // 'yellow'

// Rest pattern
const [head, ...tail] = colors;
console.log(tail);  // ['green', 'blue']

// Swapping variables
let x = 1, y = 2;
[x, y] = [y, x];
console.log(x, y);  // 2, 1
```

**Object destructuring:**
```javascript
const user = { name: 'John', age: 30, city: 'NYC' };

// Basic
const { name, age } = user;
console.log(name);  // 'John'

// Renaming
const { name: userName, age: userAge } = user;
console.log(userName);  // 'John'

// Default values
const { name, country = 'USA' } = user;
console.log(country);  // 'USA'

// Nested destructuring
const person = {
    name: 'Jane',
    address: { city: 'LA', zip: '90001' }
};
const { address: { city } } = person;
console.log(city);  // 'LA'

// Rest pattern
const { name, ...rest } = user;
console.log(rest);  // { age: 30, city: 'NYC' }
```

**Function parameters:**
```javascript
// Object parameter destructuring
function greet({ name, age = 18 }) {
    console.log(`${name} is ${age}`);
}
greet({ name: 'Alice', age: 25 });

// Array parameter destructuring
function getFirst([first]) {
    return first;
}
getFirst([1, 2, 3]);  // 1
```

---

### Q16: What are template literals?

**Answer:**

Template literals are string literals with embedded expressions, using backticks (`` ` ``).

**Features:**

```javascript
// 1. String interpolation
const name = 'John';
const age = 30;
const greeting = `Hello, ${name}! You are ${age} years old.`;

// 2. Expressions inside ${}
const price = 19.99;
const tax = 0.08;
const total = `Total: $${(price * (1 + tax)).toFixed(2)}`;

// 3. Multi-line strings
const html = `
    <div class="card">
        <h2>${title}</h2>
        <p>${description}</p>
    </div>
`;

// 4. Nesting
const items = ['Apple', 'Banana', 'Orange'];
const list = `
    <ul>
        ${items.map(item => `<li>${item}</li>`).join('')}
    </ul>
`;

// 5. Tagged templates
function highlight(strings, ...values) {
    return strings.reduce((acc, str, i) => {
        return acc + str + (values[i] ? `<mark>${values[i]}</mark>` : '');
    }, '');
}

const result = highlight`Hello ${name}, you have ${count} messages`;
```

**Comparison with concatenation:**
```javascript
// Old way (hard to read)
const message = 'Hello, ' + name + '! You are ' + age + ' years old.';

// Template literal (cleaner)
const message = `Hello, ${name}! You are ${age} years old.`;
```

---

### Q17: Explain the spread and rest operators.

**Answer:**

Both use `...` syntax but serve opposite purposes.

**Spread operator (expands):**
```javascript
// Arrays
const arr1 = [1, 2, 3];
const arr2 = [4, 5, 6];
const combined = [...arr1, ...arr2];  // [1,2,3,4,5,6]

// Copy array
const copy = [...arr1];

// Array to arguments
function sum(a, b, c) {
    return a + b + c;
}
sum(...arr1);  // 6

// Objects
const defaults = { theme: 'dark', lang: 'en' };
const userPrefs = { lang: 'es' };
const settings = { ...defaults, ...userPrefs };
// { theme: 'dark', lang: 'es' }

// Copy object
const clone = { ...original };
```

**Rest operator (collects):**
```javascript
// Function parameters
function sum(...numbers) {
    return numbers.reduce((a, b) => a + b, 0);
}
sum(1, 2, 3, 4, 5);  // 15

// With other parameters (must be last)
function greet(greeting, ...names) {
    return `${greeting}, ${names.join(' and ')}!`;
}
greet('Hello', 'John', 'Jane');  // "Hello, John and Jane!"

// Array destructuring
const [first, ...rest] = [1, 2, 3, 4];
console.log(rest);  // [2, 3, 4]

// Object destructuring
const { id, ...otherProps } = { id: 1, name: 'John', age: 30 };
console.log(otherProps);  // { name: 'John', age: 30 }
```

**Key difference:**
- **Spread**: Used in function calls, array/object literals (expands)
- **Rest**: Used in function definitions, destructuring (collects)

---

### Q18: What are Promises? How do you handle asynchronous operations?

**Answer:**

A **Promise** represents a value that may be available now, later, or never. It's the modern way to handle asynchronous operations.

**Promise states:**
1. **Pending**: Initial state, operation ongoing
2. **Fulfilled**: Operation completed successfully
3. **Rejected**: Operation failed

**Creating a Promise:**
```javascript
const promise = new Promise((resolve, reject) => {
    setTimeout(() => {
        const success = true;
        if (success) {
            resolve('Data loaded!');
        } else {
            reject(new Error('Failed to load'));
        }
    }, 1000);
});
```

**Consuming Promises:**
```javascript
// .then() and .catch()
promise
    .then(result => {
        console.log(result);  // 'Data loaded!'
        return processData(result);
    })
    .then(processed => {
        console.log(processed);
    })
    .catch(error => {
        console.error(error);
    })
    .finally(() => {
        console.log('Cleanup');  // Runs regardless
    });
```

**async/await (cleaner syntax):**
```javascript
async function fetchData() {
    try {
        const response = await fetch('/api/data');
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
}
```

**Promise utilities:**
```javascript
// Wait for all promises
Promise.all([promise1, promise2, promise3])
    .then(([result1, result2, result3]) => { });

// Wait for first to settle
Promise.race([promise1, promise2])
    .then(firstResult => { });

// Wait for all to settle (fulfilled or rejected)
Promise.allSettled([promise1, promise2])
    .then(results => {
        results.forEach(r => console.log(r.status));
    });
```

---

### Q19: What are JavaScript modules (import/export)?

**Answer:**

ES6 modules allow you to split code into separate files with explicit imports and exports.

**Named exports:**
```javascript
// math.js
export const PI = 3.14159;
export function add(a, b) { return a + b; }
export function subtract(a, b) { return a - b; }

// Alternative: export at end
const PI = 3.14159;
function add(a, b) { return a + b; }
export { PI, add };
```

**Named imports:**
```javascript
// Import specific exports
import { PI, add } from './math.js';

// Rename on import
import { add as addNumbers } from './math.js';

// Import all as namespace
import * as math from './math.js';
console.log(math.PI);
console.log(math.add(2, 3));
```

**Default exports:**
```javascript
// user.js - one default per file
export default class User {
    constructor(name) {
        this.name = name;
    }
}

// Or
class User { }
export default User;
```

**Default imports:**
```javascript
// Name can be anything for default imports
import User from './user.js';
import MyUser from './user.js';  // Same thing

// Combined
import User, { helper } from './user.js';
```

**In HTML:**
```html
<script type="module" src="app.js"></script>
```

---

## Bootstrap Framework

### Q20: What is Bootstrap? What are its key features?

**Answer:**

**Bootstrap** is a popular front-end CSS framework for building responsive, mobile-first websites quickly.

**Key features:**
1. **Responsive grid system** (12-column)
2. **Pre-built components** (navbar, cards, modals, etc.)
3. **Utility classes** (spacing, colors, display)
4. **JavaScript plugins** (dropdowns, carousels, tooltips)
5. **Cross-browser compatibility**
6. **Customizable** via Sass variables

**Including Bootstrap:**
```html
<!-- CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- JS (for interactive components) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
```

**Bootstrap 5 changes from 4:**
- Dropped jQuery dependency
- New utility API
- RTL support
- New components (offcanvas, accordion)
- Updated form controls
- CSS custom properties

---

### Q21: Explain the Bootstrap grid system.

**Answer:**

Bootstrap uses a **12-column flexbox grid** with containers, rows, and columns.

**Basic structure:**
```html
<div class="container">
    <div class="row">
        <div class="col-md-6">Column 1</div>
        <div class="col-md-6">Column 2</div>
    </div>
</div>
```

**Breakpoints:**

| Breakpoint | Class infix | Dimensions |
|------------|-------------|------------|
| Extra small | (none) | < 576px |
| Small | `sm` | >= 576px |
| Medium | `md` | >= 768px |
| Large | `lg` | >= 992px |
| Extra large | `xl` | >= 1200px |
| XXL | `xxl` | >= 1400px |

**Column classes:**
```html
<!-- Fixed width -->
<div class="col-6">50% width</div>
<div class="col-md-4">33% on medium+</div>

<!-- Auto-sizing -->
<div class="col">Equal width</div>
<div class="col-auto">Fit content</div>

<!-- Responsive -->
<div class="col-12 col-md-6 col-lg-4">
    Full on mobile, half on tablet, third on desktop
</div>
```

**Containers:**
```html
<div class="container">Fixed-width responsive</div>
<div class="container-fluid">Full-width</div>
<div class="container-md">Fluid until md, then fixed</div>
```

**Row options:**
```html
<div class="row g-3">Gap of 1rem</div>
<div class="row row-cols-3">3 columns per row</div>
<div class="row align-items-center">Vertical center</div>
<div class="row justify-content-between">Space between</div>
```

---

### Q22: What are Bootstrap utility classes?

**Answer:**

Utility classes are single-purpose CSS classes for quick styling without custom CSS.

**Spacing (margin/padding):**
```html
<!-- Format: {property}{sides}-{size} -->
<!-- property: m (margin), p (padding) -->
<!-- sides: t,b,s,e,x,y or blank (all) -->
<!-- size: 0-5 or auto -->

<div class="mt-3">margin-top: 1rem</div>
<div class="px-4">padding left/right: 1.5rem</div>
<div class="mb-0">margin-bottom: 0</div>
<div class="py-5">padding top/bottom: 3rem</div>
<div class="mx-auto">center horizontally</div>
```

**Display:**
```html
<div class="d-none">hidden</div>
<div class="d-block">display: block</div>
<div class="d-flex">display: flex</div>
<div class="d-none d-md-block">Hidden on mobile, visible on md+</div>
```

**Flexbox:**
```html
<div class="d-flex justify-content-between align-items-center">
<div class="d-flex flex-column">
<div class="flex-grow-1">
```

**Text:**
```html
<p class="text-center">Centered text</p>
<p class="text-primary">Primary color</p>
<p class="fw-bold">Bold</p>
<p class="fs-4">Font size 4</p>
<p class="text-uppercase">UPPERCASE</p>
<p class="text-truncate">Truncated with ellipsis...</p>
```

**Colors:**
```html
<div class="text-primary">Primary text</div>
<div class="bg-danger">Danger background</div>
<div class="text-white bg-dark">White on dark</div>
<!-- Colors: primary, secondary, success, danger, warning, info, light, dark -->
```

**Sizing:**
```html
<div class="w-50">width: 50%</div>
<div class="h-100">height: 100%</div>
<div class="mw-100">max-width: 100%</div>
```

---

### Q23: What are common Bootstrap components?

**Answer:**

**Navbar:**
```html
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">Brand</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navMenu">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navMenu">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
```

**Cards:**
```html
<div class="card" style="width: 18rem;">
    <img src="..." class="card-img-top" alt="...">
    <div class="card-body">
        <h5 class="card-title">Card Title</h5>
        <p class="card-text">Card content here.</p>
        <a href="#" class="btn btn-primary">Go somewhere</a>
    </div>
</div>
```

**Buttons:**
```html
<button class="btn btn-primary">Primary</button>
<button class="btn btn-outline-secondary">Outline</button>
<button class="btn btn-lg btn-success">Large</button>
<button class="btn btn-sm btn-danger">Small</button>
```

**Forms:**
```html
<form>
    <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control" id="email">
    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Password</label>
        <input type="password" class="form-control" id="password">
    </div>
    <div class="mb-3 form-check">
        <input type="checkbox" class="form-check-input" id="remember">
        <label class="form-check-label" for="remember">Remember me</label>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
```

**Modal:**
```html
<button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#myModal">
    Open Modal
</button>

<div class="modal fade" id="myModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Modal Title</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">Content here</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>
```

---

### Q24: How do you make a layout responsive with Bootstrap?

**Answer:**

**1. Use responsive column classes:**
```html
<div class="row">
    <!-- Stack on mobile, 2 cols on tablet, 4 cols on desktop -->
    <div class="col-12 col-md-6 col-lg-3">Item 1</div>
    <div class="col-12 col-md-6 col-lg-3">Item 2</div>
    <div class="col-12 col-md-6 col-lg-3">Item 3</div>
    <div class="col-12 col-md-6 col-lg-3">Item 4</div>
</div>
```

**2. Use responsive display utilities:**
```html
<!-- Hide on mobile, show on desktop -->
<div class="d-none d-lg-block">Desktop only</div>

<!-- Show on mobile, hide on desktop -->
<div class="d-block d-lg-none">Mobile only</div>
```

**3. Use responsive spacing:**
```html
<div class="p-2 p-md-4 p-lg-5">Responsive padding</div>
<div class="mt-3 mt-lg-5">Responsive margin</div>
```

**4. Responsive images:**
```html
<img src="..." class="img-fluid" alt="...">
<!-- img-fluid = max-width: 100%; height: auto; -->
```

**5. Responsive typography:**
```html
<h1 class="display-4">Large heading</h1>
<p class="lead">Larger introductory text</p>
<p class="fs-6 fs-md-4">Responsive font size</p>
```

**6. Row columns for auto-layout:**
```html
<!-- Always 2 cols on mobile, 4 on lg -->
<div class="row row-cols-2 row-cols-lg-4 g-4">
    <div class="col"><div class="card">...</div></div>
    <div class="col"><div class="card">...</div></div>
    <div class="col"><div class="card">...</div></div>
    <div class="col"><div class="card">...</div></div>
</div>
```

---

## jQuery

### Q25: What is jQuery? Is it still relevant today?

**Answer:**

**jQuery** is a JavaScript library that simplifies DOM manipulation, event handling, animations, and AJAX.

**Key features:**
- Cross-browser compatibility
- Concise syntax (`$` function)
- Method chaining
- Easy DOM traversal
- AJAX simplified
- Plugin ecosystem

**jQuery vs Vanilla JavaScript:**
```javascript
// DOM selection
// jQuery
$('.item')
// Vanilla
document.querySelectorAll('.item')

// Event handling
// jQuery
$('#btn').click(function() { });
// Vanilla
document.querySelector('#btn').addEventListener('click', () => { });

// AJAX
// jQuery
$.ajax({ url: '/api', success: fn });
// Vanilla
fetch('/api').then(res => res.json());
```

**Is it still relevant?**
- **Legacy projects**: Many existing sites use it
- **Quick prototyping**: Fast and simple
- **Plugins**: Large ecosystem still maintained
- **Modern alternatives**: Vanilla JS, frameworks (React, Vue, Angular)

**When to use:**
- Maintaining existing jQuery codebase
- Small projects without framework needs
- When browser compatibility is critical

**When to avoid:**
- New large-scale applications
- When bundle size matters (~87KB)
- When learning modern JavaScript

---

### Q26: What is the difference between $(document).ready() and window.onload?

**Answer:**

| Feature | $(document).ready() | window.onload |
|---------|---------------------|---------------|
| Fires when | DOM is ready | All resources loaded |
| Speed | Faster | Slower |
| Images | Before images load | After images load |
| Multiple handlers | Yes (additive) | No (overwrites) |

**Examples:**
```javascript
// jQuery - DOM ready (fires earlier)
$(document).ready(function() {
    console.log('DOM ready');
});

// Shorthand
$(function() {
    console.log('DOM ready');
});

// Vanilla equivalent
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM ready');
});

// Window onload (waits for images, styles, etc.)
window.onload = function() {
    console.log('Everything loaded');
};

// Multiple handlers issue:
window.onload = firstHandler;  // Gets overwritten
window.onload = secondHandler; // Only this runs

// With addEventListener (both run):
window.addEventListener('load', firstHandler);
window.addEventListener('load', secondHandler);
```

**Best practice:**
- Use `$(document).ready()` or `DOMContentLoaded` for most cases
- Use `window.onload` only when you need all images/resources loaded

---

### Q27: Explain jQuery selectors and traversal methods.

**Answer:**

**Basic selectors:**
```javascript
$('#id')              // By ID
$('.class')           // By class
$('element')          // By tag
$('div.container')    // Combined
$('[data-id]')        // By attribute
$('[data-id="123"]')  // Attribute with value
$('ul li')            // Descendant
$('ul > li')          // Direct child
$(':first')           // First element
$(':last')            // Last element
$(':eq(2)')           // By index
$(':even')            // Even indexed
$(':odd')             // Odd indexed
```

**Traversal methods:**
```javascript
const $el = $('.item');

// Parents
$el.parent()          // Direct parent
$el.parents()         // All ancestors
$el.parents('.wrapper')  // Specific ancestor
$el.closest('.wrapper')  // Nearest ancestor matching

// Children
$el.children()        // Direct children
$el.children('.active')  // Filtered children
$el.find('.nested')   // All descendants matching

// Siblings
$el.siblings()        // All siblings
$el.next()            // Next sibling
$el.prev()            // Previous sibling
$el.nextAll()         // All following siblings
$el.prevAll()         // All previous siblings

// Filtering
$el.first()           // First in collection
$el.last()            // Last in collection
$el.eq(2)             // By index
$el.filter('.active') // Match selector
$el.not('.disabled')  // Exclude matching
$el.has('.child')     // Has matching descendants
```

**Method chaining:**
```javascript
$('#list')
    .find('li')
    .filter('.active')
    .css('color', 'red')
    .addClass('highlighted')
    .parent()
    .addClass('has-active');
```

---

### Q28: How do you handle events in jQuery?

**Answer:**

**Attaching events:**
```javascript
// .on() - recommended method
$('#btn').on('click', function(e) {
    console.log('Clicked!');
});

// Multiple events
$('#input').on('focus blur', function(e) {
    console.log(e.type);  // 'focus' or 'blur'
});

// Event with data
$('#btn').on('click', { name: 'John' }, function(e) {
    console.log(e.data.name);  // 'John'
});

// Shorthand methods
$('#btn').click(function() { });
$('#form').submit(function() { });
$('#input').change(function() { });
$('#input').keydown(function() { });
$(window).scroll(function() { });
```

**Event delegation:**
```javascript
// Handle events on dynamic elements
$('#list').on('click', 'li', function() {
    $(this).toggleClass('selected');
});

// Works for elements added later
$('#list').append('<li>New Item</li>');
// ^ Will respond to clicks
```

**Removing events:**
```javascript
$('#btn').off('click');           // Remove all click handlers
$('#btn').off('click', handler);  // Remove specific handler
$('#btn').off();                  // Remove all handlers
```

**One-time events:**
```javascript
$('#btn').one('click', function() {
    console.log('Only runs once');
});
```

**Triggering events:**
```javascript
$('#btn').trigger('click');
$('#btn').click();  // Shorthand
$('#form').submit();
```

---

### Q29: How do you manipulate the DOM with jQuery?

**Answer:**

**Getting/Setting content:**
```javascript
// Text content
$('#el').text();              // Get text
$('#el').text('New text');    // Set text

// HTML content
$('#el').html();              // Get HTML
$('#el').html('<b>Bold</b>'); // Set HTML

// Form values
$('#input').val();            // Get value
$('#input').val('New value'); // Set value
```

**Attributes and properties:**
```javascript
// Attributes
$('img').attr('src');                    // Get
$('img').attr('src', 'new.jpg');         // Set
$('img').attr({ src: 'new.jpg', alt: 'Image' }); // Multiple
$('img').removeAttr('alt');              // Remove

// Properties (for boolean attributes)
$('#checkbox').prop('checked');          // Get: true/false
$('#checkbox').prop('checked', true);    // Set

// Data attributes
$('#el').data('id');                     // Get data-id
$('#el').data('id', 123);                // Set data-id
```

**Classes:**
```javascript
$('#el').addClass('active');
$('#el').removeClass('active');
$('#el').toggleClass('active');
$('#el').hasClass('active');  // Returns boolean

// Multiple classes
$('#el').addClass('one two three');
```

**CSS:**
```javascript
$('#el').css('color');                   // Get
$('#el').css('color', 'red');            // Set single
$('#el').css({                           // Set multiple
    color: 'red',
    fontSize: '16px',
    backgroundColor: '#fff'
});
```

**Adding elements:**
```javascript
$('#list').append('<li>Last</li>');      // Inside, at end
$('#list').prepend('<li>First</li>');    // Inside, at start
$('#el').after('<p>After</p>');          // Outside, after
$('#el').before('<p>Before</p>');        // Outside, before
$('<li>New</li>').appendTo('#list');     // Reverse of append
```

**Removing elements:**
```javascript
$('#el').remove();    // Remove element and events
$('#el').detach();    // Remove but keep events (for reinsertion)
$('#el').empty();     // Remove all children
```

---

### Q30: How do you make AJAX requests with jQuery?

**Answer:**

**Basic AJAX:**
```javascript
$.ajax({
    url: '/api/users',
    method: 'GET',
    dataType: 'json',
    success: function(data) {
        console.log(data);
    },
    error: function(xhr, status, error) {
        console.error(error);
    },
    complete: function() {
        console.log('Request finished');
    }
});
```

**Shorthand methods:**
```javascript
// GET request
$.get('/api/users', function(data) {
    console.log(data);
});

// GET with parameters
$.get('/api/users', { id: 123 }, function(data) {
    console.log(data);
});

// POST request
$.post('/api/users', { name: 'John', age: 30 }, function(response) {
    console.log(response);
});

// Get JSON
$.getJSON('/api/users', function(data) {
    console.log(data);
});

// Load HTML into element
$('#content').load('/page.html');
$('#content').load('/page.html #section');  // Load specific part
```

**With Promises (modern jQuery):**
```javascript
$.ajax({
    url: '/api/users',
    method: 'GET'
})
.done(function(data) {
    console.log('Success:', data);
})
.fail(function(xhr, status, error) {
    console.error('Error:', error);
})
.always(function() {
    console.log('Complete');
});

// Or using .then()
$.get('/api/users')
    .then(function(data) {
        return $.get('/api/posts?userId=' + data.id);
    })
    .then(function(posts) {
        console.log(posts);
    })
    .catch(function(error) {
        console.error(error);
    });
```

**Sending JSON data:**
```javascript
$.ajax({
    url: '/api/users',
    method: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({ name: 'John', age: 30 }),
    success: function(response) {
        console.log(response);
    }
});
```

---

## Quick Reference Tips

### JavaScript Concepts to Know
- Variables: var, let, const, scope, hoisting
- Data types: primitives, objects, type coercion
- Functions: declarations, expressions, arrows, closures
- DOM: selection, manipulation, traversal, events
- ES6+: destructuring, spread/rest, template literals, modules
- Async: callbacks, promises, async/await

### Bootstrap Classes to Know
```
Grid: container, row, col-*, g-*
Display: d-none, d-flex, d-block
Flex: justify-content-*, align-items-*, flex-*
Spacing: m-*, p-*, mt-*, mb-*, mx-*, my-*
Text: text-center, text-primary, fw-bold, fs-*
Colors: bg-*, text-*
Components: btn, card, navbar, modal, form-control
```

### jQuery Methods to Know
```
Selectors: $(), find(), filter(), closest()
DOM: html(), text(), val(), attr(), css(), addClass()
Events: on(), off(), click(), submit(), trigger()
Effects: show(), hide(), toggle(), fadeIn(), slideUp()
AJAX: $.ajax(), $.get(), $.post(), $.getJSON()
Traversal: parent(), children(), siblings(), next(), prev()
```

---

*Generated for Week 02 curriculum - Revature Training Program*

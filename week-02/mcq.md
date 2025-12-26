# Week 02 - Multiple Choice Questions (Randomized)

This document contains 120 multiple choice questions covering the key concepts from Week 02 modules: JavaScript, Bootstrap, and jQuery. Questions are randomized across all topics.

**Topic Distribution:**
- JavaScript Fundamentals: 50 questions
- Bootstrap Framework: 40 questions
- jQuery: 30 questions

**Answer Distribution:**
- A: 30 questions (25%)
- B: 30 questions (25%)
- C: 30 questions (25%)
- D: 30 questions (25%)

---

### Question 1
**[JavaScript]**

What keyword declares a block-scoped variable that cannot be reassigned?

A) `const`
B) `let`
C) `var`
D) `static`

**Correct Answer: A) `const`**

**Explanation:** `const` declares a block-scoped variable that cannot be reassigned after initialization.

**Why other options are incorrect:**
- **B) `let`** - Block-scoped but allows reassignment.
- **C) `var`** - Function-scoped and allows reassignment.
- **D) `static`** - Not a variable declaration keyword in JavaScript.

---

### Question 2
**[Bootstrap]**

Which class creates a full-width container in Bootstrap?

A) `container-wide`
B) `container-fluid`
C) `container-full`
D) `container-100`

**Correct Answer: B) `container-fluid`**

**Explanation:** `container-fluid` spans the full width of the viewport.

**Why other options are incorrect:**
- **A) `container-wide`** - Not a valid Bootstrap class.
- **C) `container-full`** - Not a valid Bootstrap class.
- **D) `container-100`** - Not a valid Bootstrap class.

---

### Question 3
**[jQuery]**

What does `$(document).ready()` wait for before executing code?

A) All images to load
B) All external scripts to load
C) The DOM to be fully parsed
D) CSS styles to be applied

**Correct Answer: C) The DOM to be fully parsed**

**Explanation:** `$(document).ready()` fires when the DOM structure is complete, but before images and other resources finish loading.

**Why other options are incorrect:**
- **A) All images to load** - That's `window.onload`.
- **B) All external scripts to load** - Not specifically.
- **D) CSS styles to be applied** - Not specifically.

---

### Question 4
**[JavaScript]**

Which value is NOT falsy in JavaScript?

A) `0`
B) `""`
C) `[]`
D) `null`

**Correct Answer: C) `[]`**

**Explanation:** An empty array `[]` is an object, and all objects are truthy in JavaScript.

**Why other options are incorrect:**
- **A) `0`** - Zero is falsy.
- **B) `""`** - Empty string is falsy.
- **D) `null`** - null is falsy.

---

### Question 5
**[Bootstrap]**

What is the correct order of Bootstrap's grid hierarchy?

A) row > container > col
B) col > row > container
C) container > col > row
D) container > row > col

**Correct Answer: D) container > row > col**

**Explanation:** Bootstrap grid structure requires container wrapping rows, and rows containing columns.

**Why other options are incorrect:**
- All other orders violate the required Bootstrap grid hierarchy.

---

### Question 6
**[JavaScript]**

What does the `===` operator check?

A) Value only
B) Type only
C) Value and type
D) Reference only

**Correct Answer: C) Value and type**

**Explanation:** The strict equality operator `===` checks both value and type without type coercion.

**Why other options are incorrect:**
- **A) Value only** - That's `==` (loose equality).
- **B) Type only** - Would allow different values of same type.
- **D) Reference only** - Only applies to objects.

---

### Question 7
**[jQuery]**

Which method is used to hide an element with animation?

A) `hide()`
B) `remove()`
C) `fadeOut()`
D) `display(false)`

**Correct Answer: C) `fadeOut()`**

**Explanation:** `fadeOut()` gradually reduces opacity to hide an element with animation.

**Why other options are incorrect:**
- **A) `hide()`** - Can animate but default is instant.
- **B) `remove()`** - Removes from DOM, no animation.
- **D) `display(false)`** - Not a jQuery method.

---

### Question 8
**[Bootstrap]**

Which breakpoint prefix targets tablets (768px and up)?

A) `sm`
B) `md`
C) `lg`
D) `xl`

**Correct Answer: B) `md`**

**Explanation:** The `md` breakpoint applies to viewports 768px and wider, typically tablets.

**Why other options are incorrect:**
- **A) `sm`** - Targets 576px and up (large phones).
- **C) `lg`** - Targets 992px and up (desktops).
- **D) `xl`** - Targets 1200px and up (large desktops).

---

### Question 9
**[JavaScript]**

What is a closure in JavaScript?

A) A function that has been closed/completed
B) A function with access to its outer scope after the outer function returns
C) A way to close a browser window
D) A method to end a loop early

**Correct Answer: B) A function with access to its outer scope after the outer function returns**

**Explanation:** Closures allow inner functions to remember and access variables from their outer scope.

**Why other options are incorrect:**
- **A)** - Not related to function completion.
- **C)** - Uses `window.close()`, not closures.
- **D)** - Uses `break`, not closures.

---

### Question 10
**[jQuery]**

What is the shorthand for `$(document).ready(function() {})`?

A) `$.ready(function() {})`
B) `$().ready(function() {})`
C) `$(function() {})`
D) `ready(function() {})`

**Correct Answer: C) `$(function() {})`**

**Explanation:** Passing a function directly to `$()` is shorthand for `$(document).ready()`.

**Why other options are incorrect:**
- **A)** - Not valid jQuery syntax.
- **B)** - Not the standard shorthand.
- **D)** - Not valid; needs jQuery selector.

---

### Question 11
**[JavaScript]**

Which method adds an element at the end of an array?

A) `push()`
B) `unshift()`
C) `append()`
D) `add()`

**Correct Answer: A) `push()`**

**Explanation:** `push()` adds one or more elements to the end of an array and returns the new length.

**Why other options are incorrect:**
- **B) `unshift()`** - Adds to the beginning.
- **C) `append()`** - Not a native array method.
- **D) `add()`** - Not a native array method.

---

### Question 12
**[Bootstrap]**

Which class adds shadow to an element?

A) `box-shadow`
B) `shadow`
C) `drop-shadow`
D) `with-shadow`

**Correct Answer: B) `shadow`**

**Explanation:** Bootstrap provides `shadow`, `shadow-sm`, `shadow-lg`, and `shadow-none` utility classes.

**Why other options are incorrect:**
- All others are not valid Bootstrap utility classes.

---

### Question 13
**[JavaScript]**

What does `event.preventDefault()` do?

A) Stops event bubbling
B) Removes the event listener
C) Prevents the default browser action
D) Cancels the event completely

**Correct Answer: C) Prevents the default browser action**

**Explanation:** `preventDefault()` stops the browser's default action (like form submission or link navigation).

**Why other options are incorrect:**
- **A)** - That's `stopPropagation()`.
- **B)** - Use `removeEventListener()`.
- **D)** - The event still fires; only default is prevented.

---

### Question 14
**[jQuery]**

Which method selects the parent element in jQuery?

A) `parent()`
B) `parentElement()`
C) `up()`
D) `container()`

**Correct Answer: A) `parent()`**

**Explanation:** The `parent()` method returns the direct parent element.

**Why other options are incorrect:**
- **B) `parentElement()`** - Vanilla JS property, not jQuery.
- **C) `up()`** - Not a jQuery method.
- **D) `container()`** - Not a jQuery method.

---

### Question 15
**[Bootstrap]**

How do you center text horizontally in Bootstrap?

A) `center-text`
B) `align-center`
C) `text-center`
D) `horizontal-center`

**Correct Answer: C) `text-center`**

**Explanation:** The `text-center` utility class centers inline content.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes.

---

### Question 16
**[JavaScript]**

What is the output of `typeof null`?

A) "null"
B) "undefined"
C) "object"
D) "boolean"

**Correct Answer: C) "object"**

**Explanation:** This is a historical bug in JavaScript. `typeof null` returns "object" even though null is a primitive.

**Why other options are incorrect:**
- **A) "null"** - Would be logical but isn't the actual result.
- **B) "undefined"** - That's for undefined.
- **D) "boolean"** - null is not boolean.

---

### Question 17
**[jQuery]**

Which method gets or sets the HTML content of an element?

A) `content()`
B) `innerHTML()`
C) `text()`
D) `html()`

**Correct Answer: D) `html()`**

**Explanation:** The `html()` method gets or sets the HTML content including tags.

**Why other options are incorrect:**
- **A) `content()`** - Not a jQuery method.
- **B) `innerHTML()`** - Vanilla JS property, not jQuery.
- **C) `text()`** - Gets/sets text only, strips HTML.

---

### Question 18
**[Bootstrap]**

Which class hides an element on mobile but shows it on desktop?

A) `d-none d-lg-block`
B) `hidden-mobile`
C) `visible-desktop`
D) `show-lg`

**Correct Answer: A) `d-none d-lg-block`**

**Explanation:** `d-none` hides by default, `d-lg-block` shows on large screens and up.

**Why other options are incorrect:**
- All others are not valid Bootstrap 5 classes.

---

### Question 19
**[JavaScript]**

What does the spread operator `...` do in this context: `[...arr1, ...arr2]`?

A) Compares two arrays
B) Combines two arrays into one
C) Creates a reference to both arrays
D) Deletes elements from both arrays

**Correct Answer: B) Combines two arrays into one**

**Explanation:** The spread operator expands array elements, allowing concatenation into a new array.

**Why other options are incorrect:**
- **A)** - Use comparison operators for that.
- **C)** - Creates a new array with copied elements.
- **D)** - Spread doesn't delete anything.

---

### Question 20
**[Bootstrap]**

Which class creates equal spacing between flex items?

A) `space-between`
B) `justify-content-between`
C) `flex-space`
D) `gap-between`

**Correct Answer: B) `justify-content-between`**

**Explanation:** `justify-content-between` distributes items with equal space between them.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes (though `gap-*` exists for gaps).

---

### Question 21
**[JavaScript]**

Which array method creates a new array with elements that pass a test?

A) `find()`
B) `map()`
C) `filter()`
D) `reduce()`

**Correct Answer: C) `filter()`**

**Explanation:** `filter()` creates a new array with all elements that pass the provided test function.

**Why other options are incorrect:**
- **A) `find()`** - Returns first matching element, not an array.
- **B) `map()`** - Transforms each element, doesn't filter.
- **D) `reduce()`** - Reduces array to single value.

---

### Question 22
**[jQuery]**

How do you add a class to an element in jQuery?

A) `$(el).class('active')`
B) `$(el).addClassName('active')`
C) `$(el).addClass('active')`
D) `$(el).setClass('active')`

**Correct Answer: C) `$(el).addClass('active')`**

**Explanation:** `addClass()` adds one or more classes to the selected elements.

**Why other options are incorrect:**
- All others are not valid jQuery methods.

---

### Question 23
**[Bootstrap]**

What does the `g-3` class do in a row?

A) Creates 3 columns
B) Sets gap/gutter between columns
C) Sets grid template
D) Groups 3 items

**Correct Answer: B) Sets gap/gutter between columns**

**Explanation:** `g-*` classes set the gutter (gap) spacing between columns in a row.

**Why other options are incorrect:**
- **A)** - Use `row-cols-3` for that.
- **C)** - Not CSS Grid template.
- **D)** - Not a grouping class.

---

### Question 24
**[JavaScript]**

What is event delegation?

A) Passing events between windows
B) Attaching event listener to parent for child elements
C) Delegating events to other developers
D) Removing event listeners

**Correct Answer: B) Attaching event listener to parent for child elements**

**Explanation:** Event delegation uses a single listener on a parent to handle events from its children, leveraging event bubbling.

**Why other options are incorrect:**
- All others don't describe the pattern.

---

### Question 25
**[jQuery]**

Which method removes an element from the DOM in jQuery?

A) `delete()`
B) `destroy()`
C) `hide()`
D) `remove()`

**Correct Answer: D) `remove()`**

**Explanation:** `remove()` removes the selected element(s) from the DOM including event handlers and data.

**Why other options are incorrect:**
- **A) `delete()`** - Not a jQuery method.
- **B) `destroy()`** - Not a standard jQuery method.
- **C) `hide()`** - Just hides, doesn't remove from DOM.

---

### Question 26
**[Bootstrap]**

Which class makes a button full width?

A) `btn-full`
B) `btn-block`
C) `w-100`
D) `btn-wide`

**Correct Answer: C) `w-100`**

**Explanation:** In Bootstrap 5, `w-100` (width 100%) is used with buttons. Note: `btn-block` was removed in Bootstrap 5.

**Why other options are incorrect:**
- **A)** - Not a Bootstrap class.
- **B)** - Deprecated in Bootstrap 5.
- **D)** - Not a Bootstrap class.

---

### Question 27
**[JavaScript]**

What is the output of `console.log(2 + '2')`?

A) 4
B) 22
C) "22"
D) NaN

**Correct Answer: C) "22"**

**Explanation:** When adding a number and string, JavaScript converts the number to a string and concatenates.

**Why other options are incorrect:**
- **A)** - Would be true if both were numbers.
- **B)** - Close, but it's a string not a number.
- **D)** - NaN occurs with invalid number operations.

---

### Question 28
**[jQuery]**

What does `$(this)` refer to inside an event handler?

A) The window object
B) The document object
C) The element that triggered the event
D) The jQuery library

**Correct Answer: C) The element that triggered the event**

**Explanation:** Inside a jQuery event handler, `this` refers to the DOM element, and `$(this)` wraps it as a jQuery object.

**Why other options are incorrect:**
- All others don't apply in event handler context.

---

### Question 29
**[Bootstrap]**

Which class adds rounded corners to all sides?

A) `rounded`
B) `border-radius`
C) `corners`
D) `round`

**Correct Answer: A) `rounded`**

**Explanation:** `rounded` adds border-radius to all corners. Variations include `rounded-circle`, `rounded-pill`, etc.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes.

---

### Question 30
**[JavaScript]**

Which method converts a JSON string to a JavaScript object?

A) `JSON.stringify()`
B) `JSON.parse()`
C) `JSON.toObject()`
D) `JSON.convert()`

**Correct Answer: B) `JSON.parse()`**

**Explanation:** `JSON.parse()` parses a JSON string and returns the corresponding JavaScript object.

**Why other options are incorrect:**
- **A) `JSON.stringify()`** - Does the opposite (object to string).
- **C)** and **D)** - Not real JSON methods.

---

### Question 31
**[Bootstrap]**

How many columns does Bootstrap's grid system have?

A) 10
B) 12
C) 16
D) 24

**Correct Answer: B) 12**

**Explanation:** Bootstrap uses a 12-column grid system, allowing flexible layouts.

**Why other options are incorrect:**
- Bootstrap specifically uses 12 columns, not any other number.

---

### Question 32
**[JavaScript]**

What does `Array.isArray([])` return?

A) true
B) false
C) undefined
D) null

**Correct Answer: A) true**

**Explanation:** `Array.isArray()` reliably checks if a value is an array, returning true for arrays.

**Why other options are incorrect:**
- An empty array `[]` is still an array, so returns true.

---

### Question 33
**[jQuery]**

Which method attaches an event handler that runs only once?

A) `once()`
B) `single()`
C) `one()`
D) `first()`

**Correct Answer: C) `one()`**

**Explanation:** `one()` attaches a handler that executes at most once per element per event type.

**Why other options are incorrect:**
- All others are not valid jQuery methods for this purpose.

---

### Question 34
**[Bootstrap]**

Which class makes an image responsive?

A) `responsive-img`
B) `img-responsive`
C) `img-fluid`
D) `image-auto`

**Correct Answer: C) `img-fluid`**

**Explanation:** `img-fluid` applies `max-width: 100%` and `height: auto` for responsive images.

**Why other options are incorrect:**
- **B) `img-responsive`** - Was Bootstrap 3, not Bootstrap 5.
- Others are not valid Bootstrap classes.

---

### Question 35
**[JavaScript]**

What is the purpose of `async/await`?

A) To make synchronous code faster
B) To write asynchronous code that looks synchronous
C) To create new threads
D) To stop code execution

**Correct Answer: B) To write asynchronous code that looks synchronous**

**Explanation:** `async/await` is syntactic sugar over Promises, making async code easier to read and write.

**Why other options are incorrect:**
- **A)** - Doesn't affect synchronous code performance.
- **C)** - JavaScript is single-threaded.
- **D)** - `await` pauses async function, not all execution.

---

### Question 36
**[jQuery]**

What is the correct way to make a GET request in jQuery?

A) `$.get(url, callback)`
B) `$.ajax.get(url, callback)`
C) `$.request(url, 'GET', callback)`
D) `$.fetch(url, callback)`

**Correct Answer: A) `$.get(url, callback)`**

**Explanation:** `$.get()` is the shorthand method for making GET requests.

**Why other options are incorrect:**
- All others are not valid jQuery syntax.

---

### Question 37
**[Bootstrap]**

Which class vertically centers items in a flex container?

A) `vertical-center`
B) `align-middle`
C) `align-items-center`
D) `v-center`

**Correct Answer: C) `align-items-center`**

**Explanation:** `align-items-center` vertically centers flex items along the cross axis.

**Why other options are incorrect:**
- **B) `align-middle`** - For table cells, not flex.
- Others are not valid Bootstrap classes.

---

### Question 38
**[JavaScript]**

What does `document.querySelector('.item')` return?

A) All elements with class "item"
B) The first element with class "item"
C) An array of elements
D) The last element with class "item"

**Correct Answer: B) The first element with class "item"**

**Explanation:** `querySelector()` returns the first element matching the CSS selector.

**Why other options are incorrect:**
- **A)** - Use `querySelectorAll()` for all matches.
- **C)** - Returns single element, not array.
- **D)** - Returns first, not last.

---

### Question 39
**[jQuery]**

How do you stop event propagation in jQuery?

A) `event.stop()`
B) `event.preventDefault()`
C) `event.stopPropagation()`
D) `event.halt()`

**Correct Answer: C) `event.stopPropagation()`**

**Explanation:** `stopPropagation()` prevents the event from bubbling up to parent elements.

**Why other options are incorrect:**
- **B) `preventDefault()`** - Prevents default action, not bubbling.
- Others are not valid methods.

---

### Question 40
**[Bootstrap]**

Which class creates a card component?

A) `panel`
B) `box`
C) `card`
D) `container-card`

**Correct Answer: C) `card`**

**Explanation:** `card` is the base class for Bootstrap's card component.

**Why other options are incorrect:**
- **A) `panel`** - Was Bootstrap 3, replaced by cards.
- Others are not valid Bootstrap classes.

---

### Question 41
**[JavaScript]**

What is hoisting in JavaScript?

A) Moving variables to a different scope
B) Moving declarations to the top of their scope
C) Raising an error
D) Increasing performance

**Correct Answer: B) Moving declarations to the top of their scope**

**Explanation:** Hoisting is JavaScript's behavior of moving variable and function declarations to the top of their scope during compilation.

**Why other options are incorrect:**
- All others don't describe hoisting behavior.

---

### Question 42
**[jQuery]**

Which traversal method finds all descendants matching a selector?

A) `children()`
B) `find()`
C) `descendants()`
D) `search()`

**Correct Answer: B) `find()`**

**Explanation:** `find()` searches all descendants (not just children) for matching elements.

**Why other options are incorrect:**
- **A) `children()`** - Only immediate children.
- Others are not jQuery methods.

---

### Question 43
**[Bootstrap]**

What does `mb-3` add to an element?

A) Margin-bottom of 1rem
B) Margin-bottom of 3px
C) Margin-both of 3rem
D) Margin-border of 3

**Correct Answer: A) Margin-bottom of 1rem**

**Explanation:** `mb-3` adds `margin-bottom: 1rem`. The number 3 corresponds to 1rem in Bootstrap's spacing scale.

**Why other options are incorrect:**
- Bootstrap uses rem units, not px, and 3 = 1rem.

---

### Question 44
**[JavaScript]**

Which method removes the last element from an array?

A) `remove()`
B) `delete()`
C) `shift()`
D) `pop()`

**Correct Answer: D) `pop()`**

**Explanation:** `pop()` removes and returns the last element of an array.

**Why other options are incorrect:**
- **A) `remove()`** - Not a native array method.
- **B) `delete()`** - Operator, leaves holes in array.
- **C) `shift()`** - Removes first element.

---

### Question 45
**[Bootstrap]**

Which class creates a primary colored button?

A) `button-primary`
B) `btn-primary`
C) `btn btn-primary`
D) `primary-btn`

**Correct Answer: B) `btn-primary`**

**Explanation:** `btn-primary` applies the primary color. Note: `btn` base class is also needed but the color class itself is `btn-primary`.

**Why other options are incorrect:**
- **C)** - Both classes are needed but `btn-primary` is the specific color class asked about.
- Others follow wrong naming convention.

---

### Question 46
**[JavaScript]**

What is the result of `Boolean("")`?

A) true
B) false
C) undefined
D) null

**Correct Answer: B) false**

**Explanation:** An empty string is a falsy value in JavaScript.

**Why other options are incorrect:**
- Empty string is one of the 8 falsy values.

---

### Question 47
**[jQuery]**

Which method sets multiple CSS properties at once?

A) `css({prop: value, prop: value})`
B) `style({prop: value})`
C) `setCSS({prop: value})`
D) `styles({prop: value})`

**Correct Answer: A) `css({prop: value, prop: value})`**

**Explanation:** Passing an object to `css()` sets multiple properties at once.

**Why other options are incorrect:**
- All others are not valid jQuery methods.

---

### Question 48
**[Bootstrap]**

Which class makes text bold?

A) `bold`
B) `text-bold`
C) `fw-bold`
D) `font-bold`

**Correct Answer: C) `fw-bold`**

**Explanation:** `fw-bold` (font-weight-bold) makes text bold in Bootstrap 5.

**Why other options are incorrect:**
- All others are not valid Bootstrap 5 classes.

---

### Question 49
**[JavaScript]**

What does `Object.keys(obj)` return?

A) Object values
B) Object entries
C) Array of property names
D) Number of properties

**Correct Answer: C) Array of property names**

**Explanation:** `Object.keys()` returns an array of an object's own enumerable property names.

**Why other options are incorrect:**
- **A)** - Use `Object.values()`.
- **B)** - Use `Object.entries()`.
- **D)** - Use `Object.keys().length`.

---

### Question 50
**[jQuery]**

How do you select an element by ID in jQuery?

A) `$.id('myId')`
B) `$('.myId')`
C) `$('#myId')`
D) `$('id=myId')`

**Correct Answer: C) `$('#myId')`**

**Explanation:** The `#` prefix selects by ID, following CSS selector syntax.

**Why other options are incorrect:**
- **B)** - `.` selects by class.
- Others are not valid jQuery syntax.

---

### Question 51
**[Bootstrap]**

Which class creates a dismissible alert?

A) `alert-close`
B) `alert-dismissible`
C) `alert-remove`
D) `closeable-alert`

**Correct Answer: B) `alert-dismissible`**

**Explanation:** `alert-dismissible` enables the close button functionality on alerts.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes.

---

### Question 52
**[JavaScript]**

What is the purpose of `try...catch`?

A) To improve performance
B) To handle errors gracefully
C) To create loops
D) To define functions

**Correct Answer: B) To handle errors gracefully**

**Explanation:** `try...catch` allows you to handle exceptions without crashing the program.

**Why other options are incorrect:**
- All others are not purposes of try...catch.

---

### Question 53
**[jQuery]**

Which method triggers a click event?

A) `click()`
B) `fireClick()`
C) `triggerClick()`
D) `doClick()`

**Correct Answer: A) `click()`**

**Explanation:** Calling `click()` without arguments triggers the click event on the element.

**Why other options are incorrect:**
- All others are not valid jQuery methods.

---

### Question 54
**[Bootstrap]**

What does `d-flex` do?

A) Sets display: flex
B) Creates a flexbox grid
C) Hides the element
D) Sets default display

**Correct Answer: A) Sets display: flex**

**Explanation:** `d-flex` applies `display: flex` to the element.

**Why other options are incorrect:**
- All others don't describe `d-flex` behavior.

---

### Question 55
**[JavaScript]**

Which loop is best for iterating over object properties?

A) for loop
B) while loop
C) for...in loop
D) for...of loop

**Correct Answer: C) for...in loop**

**Explanation:** `for...in` iterates over enumerable property names of an object.

**Why other options are incorrect:**
- **D) `for...of`** - Iterates over iterable values (arrays, strings).
- **A)** and **B)** - Require manual property access.

---

### Question 56
**[jQuery]**

What does `$(selector).first()` return?

A) The first character
B) The first matched element
C) A boolean
D) The first child

**Correct Answer: B) The first matched element**

**Explanation:** `first()` reduces the matched set to the first element.

**Why other options are incorrect:**
- All others don't describe `first()` behavior.

---

### Question 57
**[Bootstrap]**

Which class creates a navigation bar?

A) `nav`
B) `navbar`
C) `navigation`
D) `nav-bar`

**Correct Answer: B) `navbar`**

**Explanation:** `navbar` is the base class for Bootstrap's navigation bar component.

**Why other options are incorrect:**
- **A) `nav`** - For nav component, not full navbar.
- Others are not valid Bootstrap classes.

---

### Question 58
**[JavaScript]**

What is the output of `typeof undefined`?

A) "null"
B) "object"
C) "undefined"
D) "void"

**Correct Answer: C) "undefined"**

**Explanation:** `typeof undefined` correctly returns the string "undefined".

**Why other options are incorrect:**
- All others are not the result of `typeof undefined`.

---

### Question 59
**[jQuery]**

Which method slides an element up and hides it?

A) `hide()`
B) `collapse()`
C) `slideUp()`
D) `rollUp()`

**Correct Answer: C) `slideUp()`**

**Explanation:** `slideUp()` animates the height to hide the element with a sliding motion.

**Why other options are incorrect:**
- **A) `hide()`** - No sliding animation by default.
- Others are not jQuery methods.

---

### Question 60
**[Bootstrap]**

Which spacing class sets padding on all sides?

A) `padding-3`
B) `p-3`
C) `pa-3`
D) `all-padding-3`

**Correct Answer: B) `p-3`**

**Explanation:** `p-3` sets padding on all sides. `p` = padding, no side indicator = all sides.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes.

---

### Question 61
**[JavaScript]**

What does `arr.map(fn)` return?

A) The original array modified
B) A new array with transformed elements
C) A single value
D) undefined

**Correct Answer: B) A new array with transformed elements**

**Explanation:** `map()` creates a new array with the results of calling the provided function on every element.

**Why other options are incorrect:**
- **A)** - `map()` doesn't modify the original.
- **C)** - That's `reduce()`.
- **D)** - Returns an array.

---

### Question 62
**[jQuery]**

How do you POST data using jQuery AJAX?

A) `$.get(url, data)`
B) `$.post(url, data)`
C) `$.send(url, data)`
D) `$.ajax.post(url, data)`

**Correct Answer: B) `$.post(url, data)`**

**Explanation:** `$.post()` is the shorthand for making POST requests.

**Why other options are incorrect:**
- **A) `$.get()`** - Makes GET requests.
- Others are not valid jQuery methods.

---

### Question 63
**[Bootstrap]**

Which class aligns items to the end of a flex container?

A) `align-end`
B) `flex-end`
C) `justify-content-end`
D) `items-end`

**Correct Answer: C) `justify-content-end`**

**Explanation:** `justify-content-end` aligns flex items to the end of the main axis.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes.

---

### Question 64
**[JavaScript]**

What is the DOM?

A) A programming language
B) A tree representation of HTML
C) A database system
D) A styling format

**Correct Answer: B) A tree representation of HTML**

**Explanation:** The Document Object Model (DOM) represents the document as a tree of objects that JavaScript can manipulate.

**Why other options are incorrect:**
- All others don't describe the DOM.

---

### Question 65
**[jQuery]**

Which method gets the value of an input field?

A) `value()`
B) `text()`
C) `val()`
D) `input()`

**Correct Answer: C) `val()`**

**Explanation:** `val()` gets or sets the value of form elements.

**Why other options are incorrect:**
- **B) `text()`** - Gets text content, not input values.
- Others are not jQuery methods.

---

### Question 66
**[Bootstrap]**

What does `col-auto` do?

A) Creates automatic columns
B) Sizes column based on content
C) Centers the column
D) Hides the column

**Correct Answer: B) Sizes column based on content**

**Explanation:** `col-auto` sizes the column to fit its content width.

**Why other options are incorrect:**
- All others don't describe `col-auto` behavior.

---

### Question 67
**[JavaScript]**

Which method finds the first array element passing a test?

A) `filter()`
B) `search()`
C) `find()`
D) `locate()`

**Correct Answer: C) `find()`**

**Explanation:** `find()` returns the first element that satisfies the provided testing function.

**Why other options are incorrect:**
- **A) `filter()`** - Returns all matching elements.
- Others are not array methods.

---

### Question 68
**[jQuery]**

What does `$(element).empty()` do?

A) Checks if element is empty
B) Returns empty jQuery object
C) Removes all child elements
D) Clears element's classes

**Correct Answer: C) Removes all child elements**

**Explanation:** `empty()` removes all child nodes from the matched elements.

**Why other options are incorrect:**
- **A)** - Use `.is(':empty')` or check `.html()`.
- Others don't describe `empty()`.

---

### Question 69
**[Bootstrap]**

Which class creates a modal dialog?

A) `dialog`
B) `popup`
C) `modal`
D) `overlay`

**Correct Answer: C) `modal`**

**Explanation:** `modal` is the base class for Bootstrap's modal component.

**Why other options are incorrect:**
- All others are not Bootstrap modal classes.

---

### Question 70
**[JavaScript]**

What is `NaN`?

A) Null And Nothing
B) Not a Number
C) New Assigned Null
D) No Array Name

**Correct Answer: B) Not a Number**

**Explanation:** `NaN` represents a value that is Not a Number, resulting from invalid numeric operations.

**Why other options are incorrect:**
- NaN stands for "Not a Number".

---

### Question 71
**[jQuery]**

Which method adds elements before the selected element?

A) `before()`
B) `prepend()`
C) `insertBefore()`
D) `addBefore()`

**Correct Answer: A) `before()`**

**Explanation:** `before()` inserts content before the selected element (as a sibling).

**Why other options are incorrect:**
- **B) `prepend()`** - Inserts inside at the beginning.
- **C) `insertBefore()`** - Works but is reversed (content.insertBefore(target)).

---

### Question 72
**[Bootstrap]**

Which class creates a small button?

A) `btn-small`
B) `btn-sm`
C) `btn-xs`
D) `small-btn`

**Correct Answer: B) `btn-sm`**

**Explanation:** `btn-sm` creates a smaller button size.

**Why other options are incorrect:**
- **C) `btn-xs`** - Was Bootstrap 3, not in Bootstrap 5.
- Others are not valid Bootstrap classes.

---

### Question 73
**[JavaScript]**

What does `element.classList.toggle('active')` do?

A) Always adds the class
B) Always removes the class
C) Adds if absent, removes if present
D) Checks if class exists

**Correct Answer: C) Adds if absent, removes if present**

**Explanation:** `toggle()` adds the class if it doesn't exist, or removes it if it does.

**Why other options are incorrect:**
- **A)** - Use `add()`.
- **B)** - Use `remove()`.
- **D)** - Use `contains()`.

---

### Question 74
**[jQuery]**

How do you select all paragraphs inside a div?

A) `$('p, div')`
B) `$('div p')`
C) `$('div > p')`
D) `$('div + p')`

**Correct Answer: B) `$('div p')`**

**Explanation:** Space between selectors indicates descendant relationship (all `p` inside `div`).

**Why other options are incorrect:**
- **A)** - Selects all p AND all div.
- **C)** - Only direct children.
- **D)** - Adjacent sibling.

---

### Question 75
**[Bootstrap]**

What does `order-1` do in flexbox?

A) Creates first item
B) Changes the visual order
C) Numbers the items
D) Sorts the items

**Correct Answer: B) Changes the visual order**

**Explanation:** `order-*` classes change the visual order of flex items without changing the DOM.

**Why other options are incorrect:**
- All others don't describe `order-*` behavior.

---

### Question 76
**[JavaScript]**

Which statement about arrow functions is TRUE?

A) They have their own `this` binding
B) They can be used as constructors
C) They inherit `this` from enclosing scope
D) They must have a return statement

**Correct Answer: C) They inherit `this` from enclosing scope**

**Explanation:** Arrow functions don't have their own `this`; they inherit it from the surrounding scope.

**Why other options are incorrect:**
- **A)** - They don't have their own `this`.
- **B)** - Cannot use `new` with arrow functions.
- **D)** - Implicit return for single expressions.

---

### Question 77
**[jQuery]**

What does `$.each(array, callback)` do?

A) Returns a new array
B) Filters the array
C) Iterates over array elements
D) Sorts the array

**Correct Answer: C) Iterates over array elements**

**Explanation:** `$.each()` iterates over arrays or objects, executing the callback for each element.

**Why other options are incorrect:**
- **A)** - Use `$.map()` for that.
- Others don't describe `$.each()`.

---

### Question 78
**[Bootstrap]**

Which class adds a border to all sides?

A) `bordered`
B) `border`
C) `border-all`
D) `with-border`

**Correct Answer: B) `border`**

**Explanation:** `border` adds a border on all sides.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes.

---

### Question 79
**[JavaScript]**

What is the rest parameter syntax?

A) `...args` in function definition
B) `args...` in function call
C) `*args` in function definition
D) `rest args` in function definition

**Correct Answer: A) `...args` in function definition**

**Explanation:** The rest parameter `...args` collects remaining arguments into an array.

**Why other options are incorrect:**
- **B)** - That's spread syntax in function calls.
- Others are not valid JavaScript syntax.

---

### Question 80
**[jQuery]**

Which method checks if an element has a specific class?

A) `hasClass()`
B) `containsClass()`
C) `checkClass()`
D) `isClass()`

**Correct Answer: A) `hasClass()`**

**Explanation:** `hasClass()` returns true if any matched element has the specified class.

**Why other options are incorrect:**
- All others are not jQuery methods.

---

### Question 81
**[Bootstrap]**

Which class makes a table striped?

A) `table-lines`
B) `table-striped`
C) `table-zebra`
D) `striped-table`

**Correct Answer: B) `table-striped`**

**Explanation:** `table-striped` adds alternating row colors to tables.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes.

---

### Question 82
**[JavaScript]**

What does `Promise.all()` do?

A) Resolves when any promise resolves
B) Resolves when all promises resolve
C) Always rejects
D) Returns the fastest promise

**Correct Answer: B) Resolves when all promises resolve**

**Explanation:** `Promise.all()` waits for all promises to resolve, or rejects if any one rejects.

**Why other options are incorrect:**
- **A)** - That's `Promise.any()`.
- **D)** - That's `Promise.race()`.

---

### Question 83
**[jQuery]**

How do you chain jQuery methods?

A) Using && operator
B) Using semicolons
C) Directly calling one after another with dots
D) Using the chain() method

**Correct Answer: C) Directly calling one after another with dots**

**Explanation:** jQuery methods return the jQuery object, allowing chaining: `$(el).addClass('a').removeClass('b')`.

**Why other options are incorrect:**
- All others are not how jQuery chaining works.

---

### Question 84
**[Bootstrap]**

Which class creates a dark background?

A) `background-dark`
B) `bg-dark`
C) `dark-bg`
D) `back-dark`

**Correct Answer: B) `bg-dark`**

**Explanation:** `bg-dark` applies a dark background color.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes.

---

### Question 85
**[JavaScript]**

What is template literal syntax?

A) 'string'
B) "string"
C) `string`
D) /string/

**Correct Answer: C) `string`**

**Explanation:** Template literals use backticks `` ` `` and allow embedded expressions with `${}`.

**Why other options are incorrect:**
- **A)** and **B)** - Regular string quotes.
- **D)** - Regular expression syntax.

---

### Question 86
**[jQuery]**

What does `$(selector).siblings()` return?

A) All children
B) Parent elements
C) All siblings of matched elements
D) The next element

**Correct Answer: C) All siblings of matched elements**

**Explanation:** `siblings()` returns all sibling elements, excluding the element itself.

**Why other options are incorrect:**
- **A)** - Use `children()`.
- **B)** - Use `parents()`.
- **D)** - Use `next()`.

---

### Question 87
**[Bootstrap]**

Which class creates a fixed position navbar?

A) `navbar-fixed`
B) `fixed-top`
C) `position-fixed`
D) `navbar-sticky`

**Correct Answer: B) `fixed-top`**

**Explanation:** `fixed-top` fixes the navbar to the top of the viewport.

**Why other options are incorrect:**
- **C)** - Valid but `fixed-top` is specific for navbars.
- Others are not the correct Bootstrap classes.

---

### Question 88
**[JavaScript]**

Which method joins array elements into a string?

A) `concat()`
B) `merge()`
C) `combine()`
D) `join()`

**Correct Answer: D) `join()`**

**Explanation:** `join()` creates a string by concatenating all array elements with a separator.

**Why other options are incorrect:**
- **A) `concat()`** - Merges arrays, doesn't create string.
- Others are not array methods.

---

### Question 89
**[jQuery]**

Which method creates a deep copy of an object?

A) `$.copy()`
B) `$.clone()`
C) `$.extend(true, {}, obj)`
D) `$.duplicate()`

**Correct Answer: C) `$.extend(true, {}, obj)`**

**Explanation:** `$.extend()` with `true` as first argument performs a deep copy.

**Why other options are incorrect:**
- **B) `$.clone()`** - Clones DOM elements, not plain objects.
- Others are not jQuery methods.

---

### Question 90
**[Bootstrap]**

What does `flex-wrap` do?

A) Prevents wrapping
B) Enables wrapping to new lines
C) Wraps text
D) Creates a border wrap

**Correct Answer: B) Enables wrapping to new lines**

**Explanation:** `flex-wrap` allows flex items to wrap to new lines when they exceed container width.

**Why other options are incorrect:**
- **A)** - Use `flex-nowrap`.
- Others don't describe flex-wrap.

---

### Question 91
**[JavaScript]**

What is `event.target`?

A) The parent element
B) The element that triggered the event
C) The document
D) The window object

**Correct Answer: B) The element that triggered the event**

**Explanation:** `event.target` refers to the element where the event originated.

**Why other options are incorrect:**
- All others are not what `event.target` refers to.

---

### Question 92
**[jQuery]**

How do you stop an animation queue?

A) `stop()`
B) `halt()`
C) `pause()`
D) `end()`

**Correct Answer: A) `stop()`**

**Explanation:** `stop()` stops the currently-running animation on matched elements.

**Why other options are incorrect:**
- All others are not jQuery animation methods.

---

### Question 93
**[Bootstrap]**

Which class sets equal height columns?

A) `equal-height`
B) `h-100`
C) `same-height`
D) `align-stretch`

**Correct Answer: B) `h-100`**

**Explanation:** `h-100` sets height to 100%, useful for equal height columns in flex containers.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes (though `align-items-stretch` is default for rows).

---

### Question 94
**[JavaScript]**

What does `Array.from()` do?

A) Creates array from string only
B) Creates array from iterable or array-like object
C) Converts array to object
D) Filters an array

**Correct Answer: B) Creates array from iterable or array-like object**

**Explanation:** `Array.from()` creates a new array from array-like or iterable objects.

**Why other options are incorrect:**
- **A)** - Works with any iterable, not just strings.
- Others don't describe `Array.from()`.

---

### Question 95
**[jQuery]**

What does `$.trim(string)` do?

A) Removes HTML tags
B) Removes whitespace from both ends
C) Truncates the string
D) Removes special characters

**Correct Answer: B) Removes whitespace from both ends**

**Explanation:** `$.trim()` removes leading and trailing whitespace from a string.

**Why other options are incorrect:**
- All others don't describe `$.trim()`.

---

### Question 96
**[Bootstrap]**

Which class creates a horizontal scrolling overflow?

A) `scroll-x`
B) `overflow-auto`
C) `overflow-x-auto`
D) `horizontal-scroll`

**Correct Answer: C) `overflow-x-auto`**

**Explanation:** `overflow-x-auto` enables horizontal scrolling when content overflows.

**Why other options are incorrect:**
- **B)** - Applies to both axes.
- Others are not valid Bootstrap classes.

---

### Question 97
**[JavaScript]**

What is the correct way to export a function from a module?

A) `module.export = fn`
B) `export function fn() {}`
C) `exports.fn = fn`
D) `return fn`

**Correct Answer: B) `export function fn() {}`**

**Explanation:** ES6 modules use `export` keyword before function declaration for named exports.

**Why other options are incorrect:**
- **A)** and **C)** - CommonJS syntax, not ES modules.
- **D)** - Not module export syntax.

---

### Question 98
**[jQuery]**

What does `$(element).is('.active')` return?

A) jQuery object
B) Boolean
C) undefined
D) The class string

**Correct Answer: B) Boolean**

**Explanation:** `is()` checks if any matched element matches the selector, returning true or false.

**Why other options are incorrect:**
- All others are not what `is()` returns.

---

### Question 99
**[Bootstrap]**

Which class hides an element completely?

A) `hidden`
B) `invisible`
C) `d-none`
D) `hide`

**Correct Answer: C) `d-none`**

**Explanation:** `d-none` sets `display: none`, completely hiding the element.

**Why other options are incorrect:**
- **B) `invisible`** - Hides but still takes up space.
- Others are not valid Bootstrap classes.

---

### Question 100
**[JavaScript]**

What is destructuring assignment?

A) Deleting object properties
B) Extracting values from arrays/objects into variables
C) Combining multiple objects
D) Converting objects to arrays

**Correct Answer: B) Extracting values from arrays/objects into variables**

**Explanation:** Destructuring allows unpacking values from arrays or properties from objects into distinct variables.

**Why other options are incorrect:**
- All others don't describe destructuring.

---

### Question 101
**[jQuery]**

Which event fires when an element gains focus?

A) `focus()`
B) `activate()`
C) `select()`
D) `enter()`

**Correct Answer: A) `focus()`**

**Explanation:** The `focus` event fires when an element receives focus.

**Why other options are incorrect:**
- All others are not the focus event.

---

### Question 102
**[Bootstrap]**

Which class positions an element absolutely?

A) `absolute`
B) `position-absolute`
C) `pos-absolute`
D) `abs-position`

**Correct Answer: B) `position-absolute`**

**Explanation:** `position-absolute` applies `position: absolute` to the element.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes.

---

### Question 103
**[JavaScript]**

What does `parseInt('42px')` return?

A) NaN
B) "42"
C) 42
D) 42px

**Correct Answer: C) 42**

**Explanation:** `parseInt()` parses until it hits a non-numeric character, returning 42.

**Why other options are incorrect:**
- **A)** - Only if string starts with non-numeric.
- Others are not numeric return values.

---

### Question 104
**[jQuery]**

How do you delegate events to dynamically added elements?

A) `$(parent).on('click', '.child', handler)`
B) `$(parent).delegate('.child', handler)`
C) `$('.child').live('click', handler)`
D) `$(parent).bind('.child', handler)`

**Correct Answer: A) `$(parent).on('click', '.child', handler)`**

**Explanation:** Using `on()` with a selector parameter enables event delegation.

**Why other options are incorrect:**
- **B)** and **C)** - Deprecated methods.
- **D)** - Wrong syntax.

---

### Question 105
**[Bootstrap]**

Which class creates a dropdown menu?

A) `drop-menu`
B) `dropdown-menu`
C) `menu-dropdown`
D) `select-menu`

**Correct Answer: B) `dropdown-menu`**

**Explanation:** `dropdown-menu` is the class for Bootstrap's dropdown container.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes.

---

### Question 106
**[JavaScript]**

What is the output of `[1,2,3].reduce((a,b) => a+b, 0)`?

A) [1,2,3]
B) 0
C) 6
D) "123"

**Correct Answer: C) 6**

**Explanation:** `reduce` accumulates: 0+1=1, 1+2=3, 3+3=6.

**Why other options are incorrect:**
- All others are not the accumulated sum.

---

### Question 107
**[jQuery]**

What does `$(element).toggle()` do?

A) Toggles a class
B) Shows if hidden, hides if visible
C) Toggles an attribute
D) Switches elements

**Correct Answer: B) Shows if hidden, hides if visible**

**Explanation:** `toggle()` alternates between `show()` and `hide()` states.

**Why other options are incorrect:**
- **A)** - Use `toggleClass()`.
- Others don't describe `toggle()`.

---

### Question 108
**[Bootstrap]**

Which class creates a list group?

A) `list`
B) `list-items`
C) `list-group`
D) `group-list`

**Correct Answer: C) `list-group`**

**Explanation:** `list-group` is the base class for Bootstrap's list group component.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes.

---

### Question 109
**[JavaScript]**

What method sorts array elements in place?

A) `order()`
B) `arrange()`
C) `sort()`
D) `organize()`

**Correct Answer: C) `sort()`**

**Explanation:** `sort()` sorts array elements in place and returns the sorted array.

**Why other options are incorrect:**
- All others are not array methods.

---

### Question 110
**[jQuery]**

Which method loads HTML content from server into an element?

A) `fetch()`
B) `get()`
C) `load()`
D) `ajax()`

**Correct Answer: C) `load()`**

**Explanation:** `load()` fetches data from server and places the returned HTML into the matched element.

**Why other options are incorrect:**
- Others fetch data but don't directly insert into elements.

---

### Question 111
**[Bootstrap]**

Which class adds margin to the left?

A) `ml-3`
B) `ms-3`
C) `margin-left-3`
D) `left-3`

**Correct Answer: B) `ms-3`**

**Explanation:** In Bootstrap 5, `ms-*` (margin-start) replaces `ml-*` for LTR support.

**Why other options are incorrect:**
- **A) `ml-3`** - Was Bootstrap 4, changed to `ms-*` in Bootstrap 5.
- Others are not valid Bootstrap classes.

---

### Question 112
**[JavaScript]**

What does `Object.assign()` do?

A) Compares objects
B) Copies properties from source to target objects
C) Deletes object properties
D) Creates object reference

**Correct Answer: B) Copies properties from source to target objects**

**Explanation:** `Object.assign()` copies enumerable properties from source objects to a target object.

**Why other options are incorrect:**
- All others don't describe `Object.assign()`.

---

### Question 113
**[jQuery]**

What does `$(selector).index()` return?

A) The array index of the element
B) The element's position among siblings
C) undefined
D) The element's ID

**Correct Answer: B) The element's position among siblings**

**Explanation:** `index()` returns the position of the first matched element relative to its siblings.

**Why other options are incorrect:**
- All others don't describe `index()` behavior.

---

### Question 114
**[Bootstrap]**

Which class creates a spinner animation?

A) `loading`
B) `spinner-border`
C) `animate-spin`
D) `loader`

**Correct Answer: B) `spinner-border`**

**Explanation:** `spinner-border` creates a spinning loading indicator.

**Why other options are incorrect:**
- All others are not valid Bootstrap classes.

---

### Question 115
**[JavaScript]**

What is `window.localStorage` used for?

A) Session-only storage
B) Persistent client-side storage
C) Server-side storage
D) Cache storage

**Correct Answer: B) Persistent client-side storage**

**Explanation:** `localStorage` stores data with no expiration, persisting across browser sessions.

**Why other options are incorrect:**
- **A)** - That's `sessionStorage`.
- Others don't describe `localStorage`.

---

### Question 116
**[jQuery]**

What does `$.noConflict()` do?

A) Resolves version conflicts
B) Releases the $ variable
C) Removes jQuery
D) Disables plugins

**Correct Answer: B) Releases the $ variable**

**Explanation:** `$.noConflict()` relinquishes jQuery's control of `$` for other libraries.

**Why other options are incorrect:**
- All others don't describe `$.noConflict()`.

---

### Question 117
**[Bootstrap]**

Which class creates a toast notification?

A) `notification`
B) `alert`
C) `toast`
D) `popup`

**Correct Answer: C) `toast`**

**Explanation:** `toast` is the base class for Bootstrap's toast notification component.

**Why other options are incorrect:**
- **B) `alert`** - Different component.
- Others are not Bootstrap classes.

---

### Question 118
**[JavaScript]**

What is the spread operator used for in function calls?

A) Collecting arguments
B) Expanding array elements as arguments
C) Spreading the function
D) Creating new functions

**Correct Answer: B) Expanding array elements as arguments**

**Explanation:** In function calls, spread expands an array into individual arguments.

**Why other options are incorrect:**
- **A)** - That's rest parameter in definitions.
- Others don't describe spread usage.

---

### Question 119
**[jQuery]**

Which method removes event handlers?

A) `unbind()`
B) `off()`
C) `removeEvent()`
D) `detach()`

**Correct Answer: B) `off()`**

**Explanation:** `off()` removes event handlers attached with `on()`.

**Why other options are incorrect:**
- **A) `unbind()`** - Deprecated in favor of `off()`.
- **D) `detach()`** - Removes elements, not handlers.

---

### Question 120
**[Bootstrap]**

Which class creates an accordion component?

A) `collapse`
B) `accordion`
C) `expandable`
D) `panel-group`

**Correct Answer: B) `accordion`**

**Explanation:** `accordion` is the wrapper class for Bootstrap 5's accordion component.

**Why other options are incorrect:**
- **A) `collapse`** - Used within accordion but not the main class.
- Others are not valid Bootstrap 5 classes.

---

## Answer Key Summary

| Answer | Count | Percentage |
|--------|-------|------------|
| A | 30 | 25% |
| B | 30 | 25% |
| C | 30 | 25% |
| D | 30 | 25% |

---

*Generated for Week 02 curriculum - Revature Training Program*

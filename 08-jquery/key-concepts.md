# jQuery Key Concepts for Application Developers

## Overview

This document outlines the essential jQuery concepts every application developer must understand. jQuery simplifies DOM manipulation, event handling, and AJAX calls, making it easier to write cross-browser compatible JavaScript.

---

## 1. jQuery Fundamentals

### Why It Matters
- Simplifies JavaScript syntax
- Cross-browser compatibility
- Extensive plugin ecosystem
- Still used in many legacy applications

### Key Concepts

| Concept | Description | Example |
|---------|-------------|---------|
| jQuery Object | Wrapper for DOM elements | `$('selector')` |
| Selector | Find DOM elements | `$('.class')`, `$('#id')` |
| Method Chaining | Call multiple methods | `$('el').css().hide()` |
| Document Ready | Run code when DOM loads | `$(document).ready()` |

### Setup
```html
<!-- CDN (recommended) -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

<!-- Local file -->
<script src="js/jquery.min.js"></script>
```

### Document Ready
```javascript
// Standard syntax
$(document).ready(function() {
    // Code runs when DOM is ready
});

// Shorthand (recommended)
$(function() {
    // Code runs when DOM is ready
});

// Arrow function
$(() => {
    // Code runs when DOM is ready
});
```

---

## 2. jQuery Selectors

### Why It Matters
- Select elements quickly and easily
- CSS-like syntax
- More powerful than native selectors

### Basic Selectors

| Selector | Description | Example |
|----------|-------------|---------|
| `*` | All elements | `$('*')` |
| `element` | By tag name | `$('div')` |
| `.class` | By class | `$('.button')` |
| `#id` | By ID | `$('#header')` |
| `selector1, selector2` | Multiple | `$('h1, h2, h3')` |

### Hierarchy Selectors

| Selector | Description | Example |
|----------|-------------|---------|
| `parent child` | Descendant | `$('ul li')` |
| `parent > child` | Direct child | `$('ul > li')` |
| `prev + next` | Adjacent sibling | `$('h1 + p')` |
| `prev ~ siblings` | All siblings after | `$('h1 ~ p')` |

### Attribute Selectors
```javascript
// Has attribute
$('[href]')

// Exact match
$('[type="text"]')

// Contains value
$('[class*="btn"]')

// Starts with
$('[id^="user"]')

// Ends with
$('[href$=".pdf"]')

// Not equal
$('[type!="hidden"]')
```

### Filter Selectors
```javascript
// Position filters
$('li:first')         // First element
$('li:last')          // Last element
$('li:eq(2)')         // Index 2 (third item)
$('li:lt(3)')         // Index less than 3
$('li:gt(2)')         // Index greater than 2
$('li:even')          // Even indices (0, 2, 4...)
$('li:odd')           // Odd indices (1, 3, 5...)

// Content filters
$('p:contains("text")')    // Contains text
$('div:empty')             // No children
$('div:has(p)')            // Contains element
$('td:parent')             // Has children

// Form filters
$(':input')           // All form elements
$(':text')            // Text inputs
$(':checkbox')        // Checkboxes
$(':checked')         // Checked elements
$(':selected')        // Selected options
$(':disabled')        // Disabled elements
$(':enabled')         // Enabled elements

// Visibility
$(':visible')         // Visible elements
$(':hidden')          // Hidden elements
```

---

## 3. DOM Manipulation

### Why It Matters
- Modify page content dynamically
- Create interactive experiences
- Build dynamic applications

### Getting/Setting Content
```javascript
// Text content
$('#el').text()               // Get text
$('#el').text('New text')     // Set text

// HTML content
$('#el').html()               // Get HTML
$('#el').html('<b>Bold</b>')  // Set HTML

// Form values
$('#input').val()             // Get value
$('#input').val('New value')  // Set value

// Multiple values (select)
$('#multiselect').val(['a', 'b'])
```

### Attributes and Properties
```javascript
// Attributes
$('#el').attr('href')                    // Get attribute
$('#el').attr('href', 'http://new.url')  // Set attribute
$('#el').attr({                          // Set multiple
    'href': 'http://new.url',
    'title': 'New title'
})
$('#el').removeAttr('title')             // Remove attribute

// Properties (for dynamic states)
$('#checkbox').prop('checked')           // Get property
$('#checkbox').prop('checked', true)     // Set property

// Data attributes
$('#el').data('id')                      // Get data-id
$('#el').data('id', '123')               // Set data-id

// Attribute vs Property:
// - attr() for HTML attributes
// - prop() for DOM properties (checked, disabled, selected)
```

### CSS Manipulation
```javascript
// CSS property
$('#el').css('color')                    // Get CSS
$('#el').css('color', 'red')             // Set CSS
$('#el').css({                           // Set multiple
    'color': 'red',
    'font-size': '16px'
})

// Classes
$('#el').addClass('active')              // Add class
$('#el').removeClass('active')           // Remove class
$('#el').toggleClass('active')           // Toggle class
$('#el').hasClass('active')              // Check class (returns boolean)

// Multiple classes
$('#el').addClass('class1 class2')
$('#el').removeClass('class1 class2')
```

### Adding and Removing Elements
```javascript
// Append (inside, at end)
$('#parent').append('<li>New item</li>')
$('<li>New item</li>').appendTo('#parent')

// Prepend (inside, at start)
$('#parent').prepend('<li>First item</li>')
$('<li>First item</li>').prependTo('#parent')

// After (outside, after element)
$('#el').after('<p>After</p>')
$('<p>After</p>').insertAfter('#el')

// Before (outside, before element)
$('#el').before('<p>Before</p>')
$('<p>Before</p>').insertBefore('#el')

// Wrap elements
$('p').wrap('<div class="wrapper"></div>')
$('p').wrapAll('<div class="all-wrapper"></div>')
$('p').wrapInner('<span></span>')

// Remove/Empty
$('#el').remove()         // Remove element and data
$('#el').detach()         // Remove but keep data (for reinsertion)
$('#el').empty()          // Remove children only

// Replace
$('#el').replaceWith('<div>New</div>')
$('<div>New</div>').replaceAll('#el')

// Clone
$('#el').clone()                      // Shallow clone
$('#el').clone(true)                  // Clone with events
```

---

## 4. DOM Traversal

### Why It Matters
- Navigate element relationships
- Find related elements
- Work with complex DOM structures

### Traversal Methods
```javascript
// Parents
$('#el').parent()             // Direct parent
$('#el').parents()            // All ancestors
$('#el').parents('div')       // Filtered ancestors
$('#el').parentsUntil('#stop') // Ancestors until match
$('#el').closest('div')       // Nearest matching ancestor

// Children
$('#el').children()           // Direct children
$('#el').children('li')       // Filtered children
$('#el').find('span')         // All descendants matching
$('#el').contents()           // All children including text nodes

// Siblings
$('#el').siblings()           // All siblings
$('#el').siblings('.class')   // Filtered siblings
$('#el').next()               // Next sibling
$('#el').nextAll()            // All following siblings
$('#el').nextUntil('.stop')   // Following until match
$('#el').prev()               // Previous sibling
$('#el').prevAll()            // All preceding siblings
$('#el').prevUntil('.stop')   // Preceding until match

// Filtering
$('li').first()               // First element
$('li').last()                // Last element
$('li').eq(2)                 // Element at index
$('li').filter('.active')     // Match selector
$('li').not('.inactive')      // Exclude selector
$('li').has('a')              // Has descendant
$('li').is('.active')         // Test condition (returns boolean)
```

---

## 5. Event Handling

### Why It Matters
- Respond to user interactions
- Create interactive interfaces
- Handle form submissions

### Event Methods
```javascript
// Click events
$('#btn').click(function() {
    console.log('Clicked!');
});

// With event object
$('#btn').click(function(event) {
    event.preventDefault();  // Stop default action
    event.stopPropagation(); // Stop bubbling
    console.log(event.target);
});

// Common events
$('#el').click(handler)
$('#el').dblclick(handler)
$('#el').mouseenter(handler)
$('#el').mouseleave(handler)
$('#el').hover(enterHandler, leaveHandler)
$('#el').focus(handler)
$('#el').blur(handler)
$('#el').change(handler)
$('#el').submit(handler)
$('#el').keydown(handler)
$('#el').keyup(handler)
$('#el').keypress(handler)
```

### The `on()` Method (Recommended)
```javascript
// Single event
$('#btn').on('click', function() {
    console.log('Clicked!');
});

// Multiple events
$('#el').on('mouseenter mouseleave', function() {
    $(this).toggleClass('hover');
});

// Different handlers
$('#el').on({
    mouseenter: function() { $(this).addClass('hover'); },
    mouseleave: function() { $(this).removeClass('hover'); }
});

// Event delegation (for dynamic elements)
$('#parent').on('click', '.child', function() {
    // Works for existing and future .child elements
    console.log('Child clicked!');
});

// Pass data to handler
$('#btn').on('click', { name: 'John' }, function(event) {
    console.log(event.data.name);
});

// Namespaced events
$('#el').on('click.myPlugin', handler);
$('#el').off('click.myPlugin');  // Remove only namespaced
```

### Removing Events
```javascript
// Remove all handlers
$('#el').off()

// Remove specific event
$('#el').off('click')

// Remove specific handler
$('#el').off('click', handlerFunction)

// Remove delegated events
$('#parent').off('click', '.child')
```

### Event Helpers
```javascript
// Trigger events
$('#btn').trigger('click')
$('#btn').click()                // Shorthand

// Custom events
$('#el').on('myEvent', function(e, data) {
    console.log(data);
});
$('#el').trigger('myEvent', ['custom data']);

// One-time event
$('#btn').one('click', function() {
    console.log('Runs only once');
});
```

---

## 6. jQuery Effects

### Why It Matters
- Create visual feedback
- Smooth transitions
- Professional user experience

### Show/Hide
```javascript
// Basic
$('#el').hide()
$('#el').show()
$('#el').toggle()

// With duration (ms or 'slow'/'fast')
$('#el').hide(400)
$('#el').show('slow')
$('#el').toggle('fast')

// With callback
$('#el').hide(400, function() {
    console.log('Hidden!');
});
```

### Fade Effects
```javascript
$('#el').fadeIn()
$('#el').fadeOut()
$('#el').fadeToggle()
$('#el').fadeTo(400, 0.5)    // Fade to opacity

// With duration and callback
$('#el').fadeIn(400, function() {
    console.log('Faded in!');
});
```

### Slide Effects
```javascript
$('#el').slideDown()
$('#el').slideUp()
$('#el').slideToggle()

// With duration
$('#el').slideDown(400)
$('#el').slideUp('slow')
```

### Custom Animations
```javascript
// Animate CSS properties
$('#el').animate({
    opacity: 0.5,
    width: '200px',
    marginLeft: '50px'
}, 400);

// With callback
$('#el').animate({
    height: '300px'
}, 400, function() {
    console.log('Animation complete!');
});

// Chained animations
$('#el')
    .animate({ width: '200px' }, 400)
    .animate({ height: '200px' }, 400)
    .animate({ opacity: 0.5 }, 400);

// Stop animations
$('#el').stop()              // Stop current animation
$('#el').stop(true)          // Clear queue
$('#el').stop(true, true)    // Clear queue, jump to end

// Animation queue
$('#el').delay(500).fadeIn() // Delay before effect
$('#el').finish()            // Finish all animations
```

---

## 7. AJAX with jQuery

### Why It Matters
- Load data without page refresh
- Communicate with servers
- Build dynamic applications

### Basic Methods
```javascript
// GET request
$.get('/api/data', function(response) {
    console.log(response);
});

// GET with parameters
$.get('/api/data', { id: 123 }, function(response) {
    console.log(response);
});

// POST request
$.post('/api/data', { name: 'John' }, function(response) {
    console.log(response);
});

// Load HTML into element
$('#container').load('/page.html');
$('#container').load('/page.html #section'); // Load specific part
```

### The `$.ajax()` Method
```javascript
// Full AJAX request
$.ajax({
    url: '/api/users',
    method: 'GET',           // or 'POST', 'PUT', 'DELETE'
    dataType: 'json',        // Expected response type
    data: { id: 123 },       // Data to send
    headers: {               // Custom headers
        'Authorization': 'Bearer token'
    },
    success: function(response) {
        console.log('Success:', response);
    },
    error: function(xhr, status, error) {
        console.error('Error:', error);
    },
    complete: function() {
        console.log('Request complete');
    }
});

// POST with JSON
$.ajax({
    url: '/api/users',
    method: 'POST',
    contentType: 'application/json',
    data: JSON.stringify({ name: 'John', email: 'john@example.com' }),
    success: function(response) {
        console.log(response);
    }
});
```

### Promise-based AJAX
```javascript
// Using done/fail/always
$.ajax({ url: '/api/data' })
    .done(function(response) {
        console.log('Success:', response);
    })
    .fail(function(xhr, status, error) {
        console.error('Error:', error);
    })
    .always(function() {
        console.log('Complete');
    });

// Chaining multiple requests
$.when(
    $.get('/api/users'),
    $.get('/api/posts')
).done(function(usersResponse, postsResponse) {
    console.log('Users:', usersResponse[0]);
    console.log('Posts:', postsResponse[0]);
});
```

### Loading Indicators
```javascript
// Show/hide loading
$(document)
    .ajaxStart(function() {
        $('#loading').show();
    })
    .ajaxStop(function() {
        $('#loading').hide();
    });

// Or per-request
$.ajax({
    url: '/api/data',
    beforeSend: function() {
        $('#loading').show();
    },
    complete: function() {
        $('#loading').hide();
    }
});
```

---

## 8. jQuery Utilities

### Why It Matters
- Common operations simplified
- Type checking helpers
- Array and object utilities

### Utility Methods
```javascript
// Type checking
$.isArray([1, 2, 3])          // true
$.isFunction(function(){})     // true
$.isNumeric('123')            // true
$.isEmptyObject({})           // true
$.isPlainObject({})           // true

// Array operations
$.each([1, 2, 3], function(index, value) {
    console.log(index, value);
});

$.map([1, 2, 3], function(value) {
    return value * 2;
});  // [2, 4, 6]

$.grep([1, 2, 3, 4, 5], function(value) {
    return value > 2;
});  // [3, 4, 5]

$.inArray(2, [1, 2, 3])       // 1 (index)
$.merge([1, 2], [3, 4])       // [1, 2, 3, 4]
$.unique([1, 1, 2, 2, 3])     // [1, 2, 3] (DOM elements only)

// Object operations
$.extend({}, obj1, obj2)      // Merge objects (shallow)
$.extend(true, {}, obj1, obj2) // Deep merge

// String operations
$.trim('  text  ')            // 'text'

// Parse
$.parseJSON('{"a":1}')        // {a: 1} - use JSON.parse() instead
$.parseHTML('<div>Hi</div>')  // Array of DOM elements
```

### Iterating with `each()`
```javascript
// Array
$.each([1, 2, 3], function(index, value) {
    console.log(index + ': ' + value);
});

// Object
$.each({ a: 1, b: 2 }, function(key, value) {
    console.log(key + ': ' + value);
});

// jQuery collection
$('li').each(function(index) {
    console.log(index + ': ' + $(this).text());
});

// Break loop
$('li').each(function() {
    if ($(this).hasClass('stop')) {
        return false;  // Break
    }
    // return true or nothing to continue
});
```

---

## 9. Method Chaining

### Why It Matters
- Cleaner, more readable code
- Fewer variable declarations
- More efficient execution

### Chaining Examples
```javascript
// Chain multiple methods
$('#el')
    .addClass('highlight')
    .css('color', 'red')
    .fadeIn(400)
    .text('Updated!');

// Methods that break the chain
$('#el').text()               // Returns string, not jQuery
$('#el').attr('href')         // Returns string
$('#el').val()                // Returns value

// Use end() to restore previous selection
$('#list')
    .find('li')
    .addClass('item')
    .end()                    // Back to #list
    .addClass('processed');

// Practical example
$('<div>')
    .attr('id', 'newDiv')
    .addClass('box')
    .text('Hello!')
    .appendTo('body')
    .hide()
    .fadeIn(400);
```

---

## 10. jQuery vs Vanilla JavaScript

### Why It Matters
- Understand when jQuery is needed
- Modern JavaScript has improved
- Make informed decisions

### Comparison

| Task | jQuery | Vanilla JS |
|------|--------|------------|
| Select element | `$('#id')` | `document.querySelector('#id')` |
| Select all | `$('.class')` | `document.querySelectorAll('.class')` |
| Add class | `$(el).addClass('x')` | `el.classList.add('x')` |
| Remove class | `$(el).removeClass('x')` | `el.classList.remove('x')` |
| Set text | `$(el).text('Hi')` | `el.textContent = 'Hi'` |
| Set HTML | `$(el).html('<b>Hi</b>')` | `el.innerHTML = '<b>Hi</b>'` |
| Set attribute | `$(el).attr('x', 'y')` | `el.setAttribute('x', 'y')` |
| Event listener | `$(el).on('click', fn)` | `el.addEventListener('click', fn)` |
| Hide element | `$(el).hide()` | `el.style.display = 'none'` |
| AJAX GET | `$.get(url)` | `fetch(url)` |

### When to Use jQuery
- Legacy codebases that already use it
- Rapid prototyping
- Complex DOM manipulation
- Plugin ecosystem needed

### When to Use Vanilla JS
- Modern browsers only
- Performance critical
- Minimizing dependencies
- Learning fundamentals

---

## Quick Reference Card

### Selection
```javascript
$('#id')                  // By ID
$('.class')               // By class
$('element')              // By tag
$('[attr="value"]')       // By attribute
```

### Content
```javascript
$(el).text('text')        // Set text
$(el).html('<b>html</b>') // Set HTML
$(el).val('value')        // Set value
```

### Classes
```javascript
$(el).addClass('class')
$(el).removeClass('class')
$(el).toggleClass('class')
$(el).hasClass('class')
```

### Events
```javascript
$(el).on('click', fn)
$(el).off('click')
$(el).trigger('click')
$(parent).on('click', '.child', fn)  // Delegation
```

### AJAX
```javascript
$.get(url, callback)
$.post(url, data, callback)
$.ajax({ url, method, data, success, error })
```

### Effects
```javascript
$(el).hide() / .show() / .toggle()
$(el).fadeIn() / .fadeOut() / .fadeToggle()
$(el).slideUp() / .slideDown() / .slideToggle()
$(el).animate({ prop: value }, duration)
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Include jQuery in a project
- [ ] Use selectors to find elements
- [ ] Manipulate DOM content and attributes
- [ ] Traverse the DOM tree
- [ ] Handle events with `on()`
- [ ] Apply effects and animations
- [ ] Make AJAX requests
- [ ] Use utility functions
- [ ] Chain methods effectively
- [ ] Know when to use jQuery vs vanilla JS

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 9: MySQL](../09-mysql/) - Database fundamentals
- Practice building interactive web pages
- Explore jQuery plugins and extensions

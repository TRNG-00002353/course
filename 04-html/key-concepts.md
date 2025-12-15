# HTML5 Key Concepts for Application Developers

## Overview

This document outlines the essential HTML5 concepts every application developer must understand. HTML is the foundation of all web content and is critical for building accessible, semantic, and well-structured web applications.

---

## 1. HTML Document Structure

### Why It Matters
- Every web page starts with proper HTML structure
- Browsers rely on structure for rendering
- Search engines use structure for indexing

### Key Concepts

| Element | Purpose | Required |
|---------|---------|----------|
| `<!DOCTYPE html>` | Declares HTML5 document | Yes |
| `<html>` | Root element | Yes |
| `<head>` | Metadata container | Yes |
| `<body>` | Visible content | Yes |
| `<meta charset>` | Character encoding | Yes |
| `<title>` | Page title | Yes |

### Basic HTML5 Template
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Page description for SEO">
    <title>Page Title</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <!-- Page content goes here -->
    <script src="script.js"></script>
</body>
</html>
```

### Essential Meta Tags
```html
<!-- Character encoding -->
<meta charset="UTF-8">

<!-- Responsive design -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- SEO -->
<meta name="description" content="Brief page description">
<meta name="keywords" content="keyword1, keyword2">

<!-- Social media (Open Graph) -->
<meta property="og:title" content="Page Title">
<meta property="og:description" content="Description">
<meta property="og:image" content="image.jpg">
```

---

## 2. Semantic HTML Elements

### Why It Matters
- Improves accessibility for screen readers
- Better SEO ranking
- Clearer code structure
- Easier maintenance

### Key Concepts

| Element | Purpose | Use Case |
|---------|---------|----------|
| `<header>` | Introductory content | Page/section header |
| `<nav>` | Navigation links | Menu, breadcrumbs |
| `<main>` | Primary content | Main page content |
| `<article>` | Self-contained content | Blog post, news item |
| `<section>` | Thematic grouping | Chapter, tab panel |
| `<aside>` | Related content | Sidebar, callout |
| `<footer>` | Footer content | Copyright, links |
| `<figure>` | Self-contained media | Image with caption |

### Semantic Page Structure
```html
<body>
    <header>
        <nav>
            <ul>
                <li><a href="/">Home</a></li>
                <li><a href="/about">About</a></li>
            </ul>
        </nav>
    </header>

    <main>
        <article>
            <header>
                <h1>Article Title</h1>
                <time datetime="2024-01-15">January 15, 2024</time>
            </header>

            <section>
                <h2>Section Heading</h2>
                <p>Content...</p>
            </section>

            <footer>
                <p>Author: John Doe</p>
            </footer>
        </article>

        <aside>
            <h3>Related Articles</h3>
            <ul>...</ul>
        </aside>
    </main>

    <footer>
        <p>&copy; 2024 Company Name</p>
    </footer>
</body>
```

### Semantic vs Non-Semantic

| Non-Semantic | Semantic | Why Better |
|--------------|----------|------------|
| `<div class="header">` | `<header>` | Built-in meaning |
| `<div class="nav">` | `<nav>` | Screen readers understand |
| `<div class="sidebar">` | `<aside>` | SEO benefits |
| `<span class="bold">` | `<strong>` | Semantic importance |
| `<i>` for icons | `<em>` for emphasis | Correct usage |

---

## 3. Block vs Inline Elements

### Why It Matters
- Affects layout and styling
- Determines how elements flow
- Essential for CSS layout

### Key Concepts

| Type | Behavior | Examples |
|------|----------|----------|
| Block | Takes full width, new line | `div, p, h1-h6, section` |
| Inline | Only needed width, same line | `span, a, strong, em` |
| Inline-block | Inline but accepts dimensions | `img, button, input` |

### Block Elements
```html
<!-- Each starts on a new line, takes full width -->
<div>Division</div>
<p>Paragraph</p>
<h1>Heading</h1>
<ul><li>List</li></ul>
<section>Section</section>
<article>Article</article>
```

### Inline Elements
```html
<!-- Flow within text, only take needed space -->
<p>
    This is <strong>bold</strong> and <em>italic</em> text
    with a <a href="#">link</a> and <span>span</span>.
</p>
```

### Common Mistake
```html
<!-- WRONG: Block inside inline -->
<span>
    <div>This is invalid</div>
</span>

<!-- CORRECT: Inline inside block -->
<div>
    <span>This is valid</span>
</div>
```

---

## 4. HTML Forms

### Why It Matters
- Primary way users input data
- Essential for web applications
- Foundation for frontend-backend communication

### Key Concepts

| Element | Purpose | Attributes |
|---------|---------|------------|
| `<form>` | Form container | action, method |
| `<input>` | Data entry field | type, name, value |
| `<label>` | Field description | for |
| `<textarea>` | Multi-line text | rows, cols |
| `<select>` | Dropdown menu | name, multiple |
| `<button>` | Clickable button | type |

### Form Attributes

| Attribute | Values | Purpose |
|-----------|--------|---------|
| `action` | URL | Where to send data |
| `method` | GET, POST | How to send data |
| `enctype` | multipart/form-data | For file uploads |
| `novalidate` | boolean | Skip browser validation |

### Complete Form Example
```html
<form action="/submit" method="POST">
    <!-- Text input with label -->
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username"
               required minlength="3" maxlength="20">
    </div>

    <!-- Email input -->
    <div>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
    </div>

    <!-- Password input -->
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password"
               required minlength="8">
    </div>

    <!-- Select dropdown -->
    <div>
        <label for="role">Role:</label>
        <select id="role" name="role">
            <option value="">Select...</option>
            <option value="user">User</option>
            <option value="admin">Admin</option>
        </select>
    </div>

    <!-- Radio buttons -->
    <fieldset>
        <legend>Gender:</legend>
        <label>
            <input type="radio" name="gender" value="male"> Male
        </label>
        <label>
            <input type="radio" name="gender" value="female"> Female
        </label>
    </fieldset>

    <!-- Checkbox -->
    <div>
        <label>
            <input type="checkbox" name="terms" required>
            I accept the terms
        </label>
    </div>

    <!-- Textarea -->
    <div>
        <label for="bio">Bio:</label>
        <textarea id="bio" name="bio" rows="4" cols="50"></textarea>
    </div>

    <!-- Submit button -->
    <button type="submit">Register</button>
</form>
```

### GET vs POST

| Aspect | GET | POST |
|--------|-----|------|
| Data location | URL query string | Request body |
| Visibility | Visible in URL | Hidden |
| Data limit | ~2000 characters | Unlimited |
| Caching | Can be cached | Not cached |
| Use case | Search, filters | Forms, sensitive data |

---

## 5. HTML5 Input Types

### Why It Matters
- Better user experience with native controls
- Built-in validation
- Mobile keyboard optimization

### Key Concepts

| Type | Purpose | Mobile Keyboard |
|------|---------|-----------------|
| `text` | General text | Standard |
| `email` | Email address | @ symbol visible |
| `password` | Hidden text | Standard |
| `number` | Numeric input | Number pad |
| `tel` | Phone number | Phone pad |
| `url` | Web address | .com button |
| `date` | Date picker | Date picker |
| `time` | Time picker | Time picker |
| `datetime-local` | Date and time | Combined picker |
| `color` | Color picker | Color selector |
| `range` | Slider | Slider |
| `search` | Search field | Clear button |
| `file` | File upload | File browser |

### Input Type Examples
```html
<!-- Email with validation -->
<input type="email" placeholder="user@example.com">

<!-- Number with range -->
<input type="number" min="0" max="100" step="5">

<!-- Date picker -->
<input type="date" min="2024-01-01" max="2024-12-31">

<!-- Color picker -->
<input type="color" value="#ff0000">

<!-- Range slider -->
<input type="range" min="0" max="100" value="50">

<!-- File upload -->
<input type="file" accept=".pdf,.doc" multiple>

<!-- Search with clear button -->
<input type="search" placeholder="Search...">
```

---

## 6. HTML5 Form Validation

### Why It Matters
- Immediate feedback to users
- Reduces server load
- Better user experience

### Key Concepts

| Attribute | Purpose | Example |
|-----------|---------|---------|
| `required` | Field must be filled | `required` |
| `minlength` | Minimum characters | `minlength="3"` |
| `maxlength` | Maximum characters | `maxlength="50"` |
| `min` | Minimum number/date | `min="0"` |
| `max` | Maximum number/date | `max="100"` |
| `pattern` | Regex pattern | `pattern="[A-Za-z]+"` |
| `step` | Number increment | `step="0.01"` |

### Validation Examples
```html
<!-- Required field -->
<input type="text" required>

<!-- Length constraints -->
<input type="text" minlength="3" maxlength="20">

<!-- Number range -->
<input type="number" min="1" max="100">

<!-- Pattern matching -->
<input type="text" pattern="[A-Za-z]{3,}"
       title="At least 3 letters">

<!-- Custom validation message -->
<input type="email"
       oninvalid="this.setCustomValidity('Please enter valid email')"
       oninput="this.setCustomValidity('')">
```

### CSS Validation Styling
```css
/* Valid input */
input:valid {
    border-color: green;
}

/* Invalid input */
input:invalid {
    border-color: red;
}

/* Required field indicator */
input:required {
    border-left: 3px solid red;
}
```

---

## 7. Tables

### Why It Matters
- Display tabular data
- Accessibility for data relationships
- Structured data presentation

### Key Concepts

| Element | Purpose | Required |
|---------|---------|----------|
| `<table>` | Table container | Yes |
| `<thead>` | Header rows | Recommended |
| `<tbody>` | Body rows | Recommended |
| `<tfoot>` | Footer rows | Optional |
| `<tr>` | Table row | Yes |
| `<th>` | Header cell | In thead |
| `<td>` | Data cell | In tbody |

### Complete Table Example
```html
<table>
    <caption>Employee Directory</caption>
    <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Department</th>
            <th scope="col">Email</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>John Doe</td>
            <td>Engineering</td>
            <td>john@example.com</td>
        </tr>
        <tr>
            <td>Jane Smith</td>
            <td>Marketing</td>
            <td>jane@example.com</td>
        </tr>
    </tbody>
    <tfoot>
        <tr>
            <td colspan="3">Total: 2 employees</td>
        </tr>
    </tfoot>
</table>
```

### Table with Spanning
```html
<table>
    <tr>
        <th colspan="2">Contact Info</th>
    </tr>
    <tr>
        <td rowspan="2">John</td>
        <td>john@email.com</td>
    </tr>
    <tr>
        <td>555-1234</td>
    </tr>
</table>
```

---

## 8. Media Elements

### Why It Matters
- Rich multimedia content
- Native browser support
- No plugins required

### Key Concepts

| Element | Purpose | Formats |
|---------|---------|---------|
| `<img>` | Images | jpg, png, gif, svg, webp |
| `<video>` | Video content | mp4, webm, ogg |
| `<audio>` | Audio content | mp3, wav, ogg |
| `<picture>` | Responsive images | Multiple sources |
| `<figure>` | Media with caption | Any media |

### Image Best Practices
```html
<!-- Basic image with alt text -->
<img src="photo.jpg" alt="Description of image" width="800" height="600">

<!-- Responsive image with srcset -->
<img src="small.jpg"
     srcset="small.jpg 300w,
             medium.jpg 600w,
             large.jpg 1200w"
     sizes="(max-width: 600px) 300px,
            (max-width: 1200px) 600px,
            1200px"
     alt="Responsive image">

<!-- Picture element for art direction -->
<picture>
    <source media="(min-width: 1200px)" srcset="desktop.jpg">
    <source media="(min-width: 768px)" srcset="tablet.jpg">
    <img src="mobile.jpg" alt="Responsive image">
</picture>

<!-- Figure with caption -->
<figure>
    <img src="chart.png" alt="Sales chart">
    <figcaption>Q4 2024 Sales Performance</figcaption>
</figure>
```

### Video and Audio
```html
<!-- Video with controls -->
<video controls width="640" height="360" poster="thumbnail.jpg">
    <source src="video.mp4" type="video/mp4">
    <source src="video.webm" type="video/webm">
    Your browser doesn't support video.
</video>

<!-- Audio with controls -->
<audio controls>
    <source src="audio.mp3" type="audio/mpeg">
    <source src="audio.ogg" type="audio/ogg">
    Your browser doesn't support audio.
</audio>
```

---

## 9. HTML5 Web Storage

### Why It Matters
- Store data in browser
- No server round-trips
- Better user experience

### Key Concepts

| Storage | Capacity | Persistence | Scope |
|---------|----------|-------------|-------|
| localStorage | ~5MB | Permanent | Domain |
| sessionStorage | ~5MB | Session only | Tab |
| Cookies | ~4KB | Configurable | Domain |

### Storage Examples
```html
<script>
// localStorage - persists until cleared
localStorage.setItem('username', 'john');
const user = localStorage.getItem('username');
localStorage.removeItem('username');
localStorage.clear(); // Remove all

// sessionStorage - cleared when tab closes
sessionStorage.setItem('token', 'abc123');
const token = sessionStorage.getItem('token');

// Storing objects (must stringify)
const settings = { theme: 'dark', lang: 'en' };
localStorage.setItem('settings', JSON.stringify(settings));
const saved = JSON.parse(localStorage.getItem('settings'));
</script>
```

---

## 10. Accessibility (a11y)

### Why It Matters
- Legal requirements (ADA, WCAG)
- Larger user base
- Better SEO
- Improved usability for all

### Key Concepts

| Concept | Purpose | Implementation |
|---------|---------|----------------|
| Alt text | Image description | `alt="description"` |
| Labels | Form field names | `<label for="id">` |
| ARIA | Accessibility attributes | `aria-*` attributes |
| Headings | Content structure | Proper h1-h6 order |
| Focus | Keyboard navigation | `tabindex` |
| Contrast | Readability | Color contrast ratios |

### Accessibility Best Practices
```html
<!-- Always provide alt text -->
<img src="logo.png" alt="Company Logo">
<img src="decorative.png" alt=""> <!-- Empty for decorative -->

<!-- Associate labels with inputs -->
<label for="email">Email:</label>
<input type="email" id="email" name="email">

<!-- Use ARIA when needed -->
<button aria-label="Close menu" onclick="closeMenu()">
    <span aria-hidden="true">&times;</span>
</button>

<!-- Proper heading hierarchy -->
<h1>Page Title</h1>
    <h2>Section</h2>
        <h3>Subsection</h3>

<!-- Skip navigation link -->
<a href="#main-content" class="skip-link">Skip to main content</a>

<!-- Landmark roles (often implicit) -->
<nav role="navigation">...</nav>
<main role="main">...</main>

<!-- Live regions for dynamic content -->
<div aria-live="polite" aria-atomic="true">
    <!-- Updates announced to screen readers -->
</div>
```

### ARIA Attributes

| Attribute | Purpose | Example |
|-----------|---------|---------|
| `aria-label` | Label for element | Button with icon only |
| `aria-labelledby` | Reference to label | Complex widgets |
| `aria-describedby` | Additional description | Form errors |
| `aria-hidden` | Hide from screen readers | Decorative elements |
| `aria-expanded` | Expandable state | Accordions, menus |
| `aria-live` | Announce changes | Notifications |

---

## Quick Reference Card

### Document Structure
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page Title</title>
</head>
<body>
    <header>...</header>
    <nav>...</nav>
    <main>...</main>
    <aside>...</aside>
    <footer>...</footer>
</body>
</html>
```

### Common Form Pattern
```html
<form action="/api" method="POST">
    <label for="field">Label:</label>
    <input type="text" id="field" name="field" required>
    <button type="submit">Submit</button>
</form>
```

### Image Pattern
```html
<img src="image.jpg" alt="Description" width="100" height="100">
```

### Link Pattern
```html
<a href="url" target="_blank" rel="noopener noreferrer">Link Text</a>
```

### List Patterns
```html
<!-- Unordered -->
<ul>
    <li>Item</li>
</ul>

<!-- Ordered -->
<ol>
    <li>Item</li>
</ol>

<!-- Definition -->
<dl>
    <dt>Term</dt>
    <dd>Definition</dd>
</dl>
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Create properly structured HTML5 documents
- [ ] Use semantic elements appropriately
- [ ] Understand block vs inline elements
- [ ] Build forms with various input types
- [ ] Implement HTML5 form validation
- [ ] Create accessible tables
- [ ] Embed images, video, and audio
- [ ] Use HTML5 web storage APIs
- [ ] Apply accessibility best practices
- [ ] Write clean, maintainable HTML

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 5: CSS3](../05-css/) - Style your HTML content
- Practice building complete web pages
- Test with screen readers and accessibility tools

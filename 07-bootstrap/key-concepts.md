# Bootstrap 5 Key Concepts for Application Developers

## Overview

This document outlines the essential Bootstrap 5 concepts every application developer must understand. Bootstrap is the most popular CSS framework, enabling rapid development of responsive, mobile-first web applications.

---

## 1. Bootstrap Fundamentals

### Why It Matters
- Accelerates development with pre-built components
- Ensures cross-browser compatibility
- Provides consistent, professional design
- Mobile-first responsive design built-in

### Key Concepts

| Concept | Description | Example |
|---------|-------------|---------|
| CDN | Content Delivery Network | Link to hosted files |
| Grid System | 12-column layout | Rows and columns |
| Utilities | Pre-built CSS classes | `mt-3`, `text-center` |
| Components | Pre-built UI elements | Buttons, cards, modals |
| Breakpoints | Responsive thresholds | sm, md, lg, xl, xxl |

### Setup with CDN
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bootstrap Page</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body>
    <!-- Your content here -->

    <!-- Bootstrap JS (at end of body) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
    </script>
</body>
</html>
```

### Breakpoints

| Breakpoint | Class infix | Dimensions |
|------------|-------------|------------|
| Extra small | None | <576px |
| Small | `sm` | ≥576px |
| Medium | `md` | ≥768px |
| Large | `lg` | ≥992px |
| Extra large | `xl` | ≥1200px |
| Extra extra large | `xxl` | ≥1400px |

---

## 2. Grid System

### Why It Matters
- Creates responsive layouts easily
- Consistent spacing and alignment
- Flexible column widths

### Key Concepts
```html
<!-- Basic grid structure -->
<div class="container">
    <div class="row">
        <div class="col">Column 1</div>
        <div class="col">Column 2</div>
        <div class="col">Column 3</div>
    </div>
</div>
```

### Container Types

| Class | Behavior | Max Width |
|-------|----------|-----------|
| `.container` | Fixed width per breakpoint | Varies |
| `.container-fluid` | Full width always | 100% |
| `.container-{breakpoint}` | Full until breakpoint | Varies |

```html
<!-- Fixed-width container -->
<div class="container">...</div>

<!-- Full-width container -->
<div class="container-fluid">...</div>

<!-- Full width until lg breakpoint -->
<div class="container-lg">...</div>
```

### Column Classes
```html
<!-- Equal width columns -->
<div class="row">
    <div class="col">1/3</div>
    <div class="col">1/3</div>
    <div class="col">1/3</div>
</div>

<!-- Specific column widths -->
<div class="row">
    <div class="col-4">4/12</div>
    <div class="col-8">8/12</div>
</div>

<!-- Responsive columns -->
<div class="row">
    <div class="col-12 col-md-6 col-lg-4">
        Full on mobile, half on tablet, third on desktop
    </div>
</div>

<!-- Auto-width columns -->
<div class="row">
    <div class="col-auto">Fits content</div>
    <div class="col">Takes remaining space</div>
</div>
```

### Row/Column Utilities
```html
<!-- Gutters (spacing between columns) -->
<div class="row g-3">...</div>         <!-- Gap: 1rem -->
<div class="row gx-4">...</div>        <!-- Horizontal gap -->
<div class="row gy-2">...</div>        <!-- Vertical gap -->
<div class="row g-0">...</div>         <!-- No gap -->

<!-- Row alignment -->
<div class="row justify-content-center">...</div>
<div class="row justify-content-between">...</div>
<div class="row align-items-center">...</div>

<!-- Column alignment -->
<div class="col align-self-start">...</div>
<div class="col align-self-center">...</div>
<div class="col align-self-end">...</div>

<!-- Column ordering -->
<div class="col order-3">First in markup, third visually</div>
<div class="col order-1">Second in markup, first visually</div>
<div class="col order-2">Third in markup, second visually</div>

<!-- Column offset -->
<div class="col-md-4 offset-md-4">Centered column</div>
```

---

## 3. Spacing Utilities

### Why It Matters
- Quick margin and padding adjustments
- Consistent spacing across components
- No custom CSS needed

### Key Concepts

| Property | Prefix |
|----------|--------|
| Margin | `m` |
| Padding | `p` |

| Side | Suffix |
|------|--------|
| Top | `t` |
| Bottom | `b` |
| Start (left) | `s` |
| End (right) | `e` |
| Horizontal | `x` |
| Vertical | `y` |
| All sides | (none) |

| Size | Value |
|------|-------|
| 0 | 0 |
| 1 | 0.25rem |
| 2 | 0.5rem |
| 3 | 1rem |
| 4 | 1.5rem |
| 5 | 3rem |
| auto | auto |

### Examples
```html
<!-- Margin examples -->
<div class="m-3">Margin all sides: 1rem</div>
<div class="mt-4">Margin top: 1.5rem</div>
<div class="mb-2">Margin bottom: 0.5rem</div>
<div class="mx-auto">Horizontal center (auto margins)</div>
<div class="my-5">Vertical margin: 3rem</div>
<div class="ms-3">Margin start (left): 1rem</div>
<div class="me-2">Margin end (right): 0.5rem</div>

<!-- Padding examples -->
<div class="p-3">Padding all sides: 1rem</div>
<div class="pt-4">Padding top: 1.5rem</div>
<div class="px-5">Horizontal padding: 3rem</div>

<!-- Responsive spacing -->
<div class="p-2 p-md-4 p-lg-5">
    Responsive padding
</div>

<!-- Negative margins -->
<div class="mt-n3">Negative margin top</div>
```

---

## 4. Typography and Text Utilities

### Why It Matters
- Consistent text styling
- Responsive typography
- Quick text manipulation

### Display Headings
```html
<h1 class="display-1">Display 1</h1>
<h1 class="display-2">Display 2</h1>
<h1 class="display-3">Display 3</h1>
<h1 class="display-4">Display 4</h1>
<h1 class="display-5">Display 5</h1>
<h1 class="display-6">Display 6</h1>
```

### Text Utilities
```html
<!-- Text alignment -->
<p class="text-start">Left aligned</p>
<p class="text-center">Center aligned</p>
<p class="text-end">Right aligned</p>
<p class="text-md-center">Centered on md and up</p>

<!-- Text transformation -->
<p class="text-lowercase">LOWERCASE</p>
<p class="text-uppercase">uppercase</p>
<p class="text-capitalize">capitalize words</p>

<!-- Font weight and style -->
<p class="fw-bold">Bold text</p>
<p class="fw-normal">Normal weight</p>
<p class="fw-light">Light weight</p>
<p class="fst-italic">Italic text</p>

<!-- Font size -->
<p class="fs-1">Font size 1 (largest)</p>
<p class="fs-6">Font size 6 (smallest)</p>

<!-- Text wrapping and overflow -->
<p class="text-wrap">Text wraps</p>
<p class="text-nowrap">No wrap</p>
<p class="text-truncate">Truncate with ellipsis...</p>

<!-- Line height -->
<p class="lh-1">Line height 1</p>
<p class="lh-base">Base line height</p>
<p class="lh-lg">Large line height</p>
```

---

## 5. Color Utilities

### Why It Matters
- Consistent color scheme
- Quick color application
- Built-in color system

### Text Colors
```html
<p class="text-primary">Primary (blue)</p>
<p class="text-secondary">Secondary (gray)</p>
<p class="text-success">Success (green)</p>
<p class="text-danger">Danger (red)</p>
<p class="text-warning">Warning (yellow)</p>
<p class="text-info">Info (cyan)</p>
<p class="text-light bg-dark">Light</p>
<p class="text-dark">Dark</p>
<p class="text-muted">Muted</p>
<p class="text-white bg-dark">White</p>
```

### Background Colors
```html
<div class="bg-primary text-white">Primary</div>
<div class="bg-secondary text-white">Secondary</div>
<div class="bg-success text-white">Success</div>
<div class="bg-danger text-white">Danger</div>
<div class="bg-warning text-dark">Warning</div>
<div class="bg-info text-dark">Info</div>
<div class="bg-light text-dark">Light</div>
<div class="bg-dark text-white">Dark</div>
<div class="bg-white text-dark">White</div>
<div class="bg-transparent">Transparent</div>
```

### Background Opacity
```html
<div class="bg-primary bg-opacity-75">75% opacity</div>
<div class="bg-primary bg-opacity-50">50% opacity</div>
<div class="bg-primary bg-opacity-25">25% opacity</div>
<div class="bg-primary bg-opacity-10">10% opacity</div>
```

---

## 6. Display Utilities

### Why It Matters
- Control element visibility
- Responsive display changes
- Quick layout adjustments

### Display Classes
```html
<div class="d-none">Hidden</div>
<div class="d-inline">Inline</div>
<div class="d-inline-block">Inline-block</div>
<div class="d-block">Block</div>
<div class="d-flex">Flexbox</div>
<div class="d-grid">Grid</div>

<!-- Responsive display -->
<div class="d-none d-md-block">Hidden on mobile, visible on md+</div>
<div class="d-block d-lg-none">Visible until lg</div>

<!-- Print display -->
<div class="d-print-none">Hidden when printing</div>
<div class="d-none d-print-block">Only visible when printing</div>
```

---

## 7. Flexbox Utilities

### Why It Matters
- Powerful layout control
- Alignment and distribution
- Responsive flexibility

### Flex Container
```html
<!-- Enable flex -->
<div class="d-flex">Flex container</div>
<div class="d-inline-flex">Inline flex</div>

<!-- Direction -->
<div class="d-flex flex-row">Row (default)</div>
<div class="d-flex flex-column">Column</div>
<div class="d-flex flex-row-reverse">Row reverse</div>

<!-- Justify content (main axis) -->
<div class="d-flex justify-content-start">Start</div>
<div class="d-flex justify-content-center">Center</div>
<div class="d-flex justify-content-end">End</div>
<div class="d-flex justify-content-between">Space between</div>
<div class="d-flex justify-content-around">Space around</div>
<div class="d-flex justify-content-evenly">Space evenly</div>

<!-- Align items (cross axis) -->
<div class="d-flex align-items-start">Start</div>
<div class="d-flex align-items-center">Center</div>
<div class="d-flex align-items-end">End</div>
<div class="d-flex align-items-stretch">Stretch</div>

<!-- Wrap -->
<div class="d-flex flex-wrap">Wrap</div>
<div class="d-flex flex-nowrap">No wrap</div>
```

### Flex Items
```html
<!-- Align self -->
<div class="align-self-center">Centered item</div>

<!-- Flex grow/shrink -->
<div class="flex-grow-1">Grows to fill space</div>
<div class="flex-shrink-0">Won't shrink</div>

<!-- Fill (equal width) -->
<div class="d-flex">
    <div class="flex-fill">Fill</div>
    <div class="flex-fill">Fill</div>
</div>

<!-- Order -->
<div class="order-3">Third</div>
<div class="order-1">First</div>
<div class="order-2">Second</div>
```

---

## 8. Common Components

### Why It Matters
- Pre-built, tested components
- Consistent design
- Saves development time

### Buttons
```html
<!-- Button styles -->
<button class="btn btn-primary">Primary</button>
<button class="btn btn-secondary">Secondary</button>
<button class="btn btn-success">Success</button>
<button class="btn btn-danger">Danger</button>
<button class="btn btn-warning">Warning</button>
<button class="btn btn-info">Info</button>
<button class="btn btn-light">Light</button>
<button class="btn btn-dark">Dark</button>
<button class="btn btn-link">Link</button>

<!-- Outline buttons -->
<button class="btn btn-outline-primary">Outline</button>

<!-- Button sizes -->
<button class="btn btn-primary btn-lg">Large</button>
<button class="btn btn-primary btn-sm">Small</button>

<!-- Block button -->
<button class="btn btn-primary w-100">Full width</button>

<!-- Disabled -->
<button class="btn btn-primary" disabled>Disabled</button>
```

### Cards
```html
<div class="card" style="width: 18rem;">
    <img src="..." class="card-img-top" alt="...">
    <div class="card-body">
        <h5 class="card-title">Card title</h5>
        <p class="card-text">Card content goes here.</p>
        <a href="#" class="btn btn-primary">Go somewhere</a>
    </div>
</div>

<!-- Card with header/footer -->
<div class="card">
    <div class="card-header">Header</div>
    <div class="card-body">Body content</div>
    <div class="card-footer">Footer</div>
</div>
```

### Alerts
```html
<div class="alert alert-primary" role="alert">
    Primary alert message
</div>
<div class="alert alert-success" role="alert">
    Success alert message
</div>
<div class="alert alert-danger" role="alert">
    Danger alert message
</div>

<!-- Dismissible alert -->
<div class="alert alert-warning alert-dismissible fade show" role="alert">
    Warning message
    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
</div>
```

### Forms
```html
<form>
    <!-- Text input -->
    <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control" id="email">
    </div>

    <!-- Select -->
    <div class="mb-3">
        <label for="role" class="form-label">Role</label>
        <select class="form-select" id="role">
            <option value="">Choose...</option>
            <option value="1">Admin</option>
            <option value="2">User</option>
        </select>
    </div>

    <!-- Checkbox -->
    <div class="form-check mb-3">
        <input class="form-check-input" type="checkbox" id="check1">
        <label class="form-check-label" for="check1">Check me</label>
    </div>

    <!-- Radio -->
    <div class="form-check">
        <input class="form-check-input" type="radio" name="radio" id="radio1">
        <label class="form-check-label" for="radio1">Option 1</label>
    </div>

    <!-- Floating labels -->
    <div class="form-floating mb-3">
        <input type="email" class="form-control" id="floatingEmail" placeholder="email">
        <label for="floatingEmail">Email address</label>
    </div>

    <!-- Input group -->
    <div class="input-group mb-3">
        <span class="input-group-text">@</span>
        <input type="text" class="form-control" placeholder="Username">
    </div>

    <button type="submit" class="btn btn-primary">Submit</button>
</form>
```

### Navbar
```html
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="#">Brand</a>
        <button class="navbar-toggler" type="button"
                data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link active" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">About</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#"
                       data-bs-toggle="dropdown">Dropdown</a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#">Action</a></li>
                        <li><a class="dropdown-item" href="#">Another</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
```

### Modal
```html
<!-- Trigger button -->
<button type="button" class="btn btn-primary"
        data-bs-toggle="modal" data-bs-target="#myModal">
    Open Modal
</button>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Modal title</h5>
                <button type="button" class="btn-close"
                        data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                Modal content here...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary"
                        data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save</button>
            </div>
        </div>
    </div>
</div>
```

---

## 9. Border and Shadow Utilities

### Why It Matters
- Quick visual styling
- Depth and hierarchy
- Border radius variations

### Borders
```html
<!-- Add borders -->
<div class="border">All borders</div>
<div class="border-top">Top border</div>
<div class="border-end">Right border</div>
<div class="border-bottom">Bottom border</div>
<div class="border-start">Left border</div>

<!-- Remove borders -->
<div class="border-0">No borders</div>
<div class="border border-top-0">No top border</div>

<!-- Border colors -->
<div class="border border-primary">Primary border</div>
<div class="border border-danger">Danger border</div>

<!-- Border width -->
<div class="border border-1">1px border</div>
<div class="border border-2">2px border</div>
<div class="border border-3">3px border</div>
```

### Border Radius
```html
<div class="rounded">Default rounded</div>
<div class="rounded-0">No rounding</div>
<div class="rounded-1">Small radius</div>
<div class="rounded-2">Medium radius</div>
<div class="rounded-3">Large radius</div>
<div class="rounded-circle">Circle</div>
<div class="rounded-pill">Pill shape</div>
<div class="rounded-top">Top corners only</div>
```

### Shadows
```html
<div class="shadow-none">No shadow</div>
<div class="shadow-sm">Small shadow</div>
<div class="shadow">Regular shadow</div>
<div class="shadow-lg">Large shadow</div>
```

---

## 10. Responsive Visibility

### Why It Matters
- Show/hide content per device
- Optimize mobile experience
- Conditional content display

### Visibility Classes
```html
<!-- Hidden on specific breakpoints -->
<div class="d-none">Always hidden</div>
<div class="d-sm-none">Hidden on sm+</div>
<div class="d-none d-md-block">Hidden below md</div>
<div class="d-lg-none">Hidden on lg+</div>

<!-- Visible on specific breakpoints -->
<div class="d-block d-sm-none">Only on xs</div>
<div class="d-none d-sm-block d-md-none">Only on sm</div>
<div class="d-none d-md-block d-lg-none">Only on md</div>
<div class="d-none d-lg-block">Only on lg+</div>

<!-- Common patterns -->
<div class="d-none d-md-block">Desktop only</div>
<div class="d-block d-md-none">Mobile only</div>
```

---

## Quick Reference Card

### Grid Basics
```html
<div class="container">
    <div class="row">
        <div class="col-12 col-md-6 col-lg-4">Column</div>
    </div>
</div>
```

### Spacing Pattern
```html
<div class="m-{size}">Margin</div>
<div class="p-{size}">Padding</div>
<div class="mt-3 mb-4 mx-auto">Mixed</div>
```

### Flex Center
```html
<div class="d-flex justify-content-center align-items-center">
    Centered content
</div>
```

### Card Pattern
```html
<div class="card">
    <div class="card-body">
        <h5 class="card-title">Title</h5>
        <p class="card-text">Content</p>
    </div>
</div>
```

### Responsive Hide/Show
```html
<div class="d-none d-md-block">Desktop only</div>
<div class="d-block d-md-none">Mobile only</div>
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Set up Bootstrap via CDN
- [ ] Use the 12-column grid system
- [ ] Apply responsive breakpoints
- [ ] Use spacing utilities effectively
- [ ] Style text with typography utilities
- [ ] Apply color utilities
- [ ] Control display and visibility
- [ ] Use flexbox utilities
- [ ] Implement common components
- [ ] Build responsive layouts

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 8: jQuery](../08-jquery/) - DOM manipulation library
- Practice building complete responsive pages
- Explore Bootstrap customization with Sass

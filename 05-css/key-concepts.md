# CSS3 Key Concepts for Application Developers

## Overview

This document outlines the essential CSS3 concepts every application developer must understand. CSS is critical for styling web applications, creating responsive layouts, and delivering polished user interfaces.

---

## 1. CSS Fundamentals

### Why It Matters
- Controls visual presentation of HTML
- Separates content from styling
- Enables consistent design across pages

### Key Concepts

| Concept | Description | Example |
|---------|-------------|---------|
| Selector | Targets HTML elements | `div`, `.class`, `#id` |
| Property | What to style | `color`, `font-size` |
| Value | How to style it | `red`, `16px` |
| Declaration | Property + value | `color: red;` |
| Rule | Selector + declarations | `p { color: red; }` |

### CSS Syntax
```css
selector {
    property: value;
    property: value;
}

/* Example */
.button {
    background-color: blue;
    color: white;
    padding: 10px 20px;
    border-radius: 4px;
}
```

### Ways to Apply CSS

| Method | Syntax | Use Case |
|--------|--------|----------|
| External | `<link href="style.css">` | Production (recommended) |
| Internal | `<style>` in head | Single page styles |
| Inline | `style=""` attribute | Quick overrides |

```html
<!-- External (recommended) -->
<link rel="stylesheet" href="styles.css">

<!-- Internal -->
<style>
    .header { color: blue; }
</style>

<!-- Inline (avoid) -->
<div style="color: red;">Text</div>
```

---

## 2. CSS Selectors

### Why It Matters
- Target specific elements precisely
- Reduce code duplication
- Build maintainable stylesheets

### Key Concepts

| Selector Type | Syntax | Example |
|---------------|--------|---------|
| Element | `element` | `p { }` |
| Class | `.class` | `.button { }` |
| ID | `#id` | `#header { }` |
| Universal | `*` | `* { }` |
| Attribute | `[attr]` | `[type="text"] { }` |
| Descendant | `A B` | `nav a { }` |
| Child | `A > B` | `ul > li { }` |
| Adjacent | `A + B` | `h1 + p { }` |
| Sibling | `A ~ B` | `h1 ~ p { }` |

### Pseudo-classes
```css
/* State-based */
a:hover { color: red; }
input:focus { border-color: blue; }
button:active { transform: scale(0.98); }
input:disabled { opacity: 0.5; }

/* Structural */
li:first-child { font-weight: bold; }
li:last-child { border-bottom: none; }
li:nth-child(odd) { background: #f5f5f5; }
li:nth-child(3n) { color: red; } /* Every 3rd */

/* Form states */
input:valid { border-color: green; }
input:invalid { border-color: red; }
input:required { border-left: 3px solid red; }
```

### Pseudo-elements
```css
/* Before and after content */
.quote::before { content: '"'; }
.quote::after { content: '"'; }

/* First letter/line */
p::first-letter { font-size: 2em; }
p::first-line { font-weight: bold; }

/* Selection styling */
::selection { background: yellow; }

/* Placeholder text */
input::placeholder { color: #999; }
```

### Selector Specificity

| Selector | Specificity | Score |
|----------|-------------|-------|
| `*` | Lowest | 0,0,0,0 |
| `element` | Low | 0,0,0,1 |
| `.class` | Medium | 0,0,1,0 |
| `#id` | High | 0,1,0,0 |
| `inline style` | Highest | 1,0,0,0 |
| `!important` | Override all | - |

```css
/* Specificity examples */
p { }                    /* 0,0,0,1 */
.text { }                /* 0,0,1,0 */
p.text { }               /* 0,0,1,1 */
#main { }                /* 0,1,0,0 */
#main .text { }          /* 0,1,1,0 */
#main p.text { }         /* 0,1,1,1 */
```

---

## 3. Box Model

### Why It Matters
- Foundation of CSS layout
- Controls element dimensions
- Critical for spacing and alignment

### Key Concepts

```
┌─────────────────────────────────────┐
│             MARGIN                   │
│   ┌─────────────────────────────┐   │
│   │         BORDER               │   │
│   │   ┌─────────────────────┐   │   │
│   │   │      PADDING         │   │   │
│   │   │   ┌─────────────┐   │   │   │
│   │   │   │   CONTENT   │   │   │   │
│   │   │   └─────────────┘   │   │   │
│   │   └─────────────────────┘   │   │
│   └─────────────────────────────┘   │
└─────────────────────────────────────┘
```

| Property | Affects | Example |
|----------|---------|---------|
| `width/height` | Content area | `width: 200px;` |
| `padding` | Inside border | `padding: 10px;` |
| `border` | Around padding | `border: 1px solid;` |
| `margin` | Outside border | `margin: 20px;` |

### Box-sizing Property
```css
/* Default: width = content only */
.box-content {
    box-sizing: content-box;
    width: 200px;
    padding: 20px;
    border: 5px solid;
    /* Total width: 200 + 40 + 10 = 250px */
}

/* Recommended: width includes padding/border */
.box-border {
    box-sizing: border-box;
    width: 200px;
    padding: 20px;
    border: 5px solid;
    /* Total width: 200px (content shrinks) */
}

/* Global reset (recommended) */
*, *::before, *::after {
    box-sizing: border-box;
}
```

### Margin and Padding Shorthand
```css
/* All sides */
margin: 10px;

/* Vertical | Horizontal */
margin: 10px 20px;

/* Top | Horizontal | Bottom */
margin: 10px 20px 30px;

/* Top | Right | Bottom | Left (clockwise) */
margin: 10px 20px 30px 40px;

/* Individual sides */
margin-top: 10px;
margin-right: 20px;
margin-bottom: 10px;
margin-left: 20px;
```

---

## 4. Display Property

### Why It Matters
- Controls how elements render
- Foundation for layout systems
- Affects element behavior

### Key Concepts

| Value | Behavior | Use Case |
|-------|----------|----------|
| `block` | Full width, new line | Sections, divs |
| `inline` | Content width, same line | Text elements |
| `inline-block` | Inline but accepts dimensions | Buttons |
| `none` | Removes from layout | Hide elements |
| `flex` | Flexbox container | One-dimensional layout |
| `grid` | Grid container | Two-dimensional layout |

```css
/* Block - takes full width */
.block { display: block; }

/* Inline - flows with text */
.inline { display: inline; }

/* Inline-block - inline but with dimensions */
.inline-block {
    display: inline-block;
    width: 100px;
    height: 50px;
}

/* None - hidden and removed from flow */
.hidden { display: none; }

/* Visibility - hidden but keeps space */
.invisible { visibility: hidden; }
```

---

## 5. Flexbox Layout

### Why It Matters
- Simplifies complex layouts
- Automatic spacing and alignment
- Responsive by nature

### Key Concepts

| Property | Container/Item | Purpose |
|----------|----------------|---------|
| `display: flex` | Container | Enable flexbox |
| `flex-direction` | Container | Main axis direction |
| `justify-content` | Container | Main axis alignment |
| `align-items` | Container | Cross axis alignment |
| `flex-wrap` | Container | Allow wrapping |
| `flex-grow` | Item | Growth factor |
| `flex-shrink` | Item | Shrink factor |
| `flex-basis` | Item | Initial size |

### Container Properties
```css
.container {
    display: flex;

    /* Direction */
    flex-direction: row;        /* Default: horizontal */
    flex-direction: column;     /* Vertical */
    flex-direction: row-reverse;
    flex-direction: column-reverse;

    /* Main axis alignment (justify-content) */
    justify-content: flex-start;    /* Default */
    justify-content: flex-end;
    justify-content: center;
    justify-content: space-between; /* Even space between */
    justify-content: space-around;  /* Even space around */
    justify-content: space-evenly;  /* Equal space */

    /* Cross axis alignment (align-items) */
    align-items: stretch;      /* Default: fill height */
    align-items: flex-start;   /* Top */
    align-items: flex-end;     /* Bottom */
    align-items: center;       /* Center */
    align-items: baseline;     /* Text baseline */

    /* Wrapping */
    flex-wrap: nowrap;         /* Default: single line */
    flex-wrap: wrap;           /* Multiple lines */

    /* Gap between items */
    gap: 20px;                 /* Both directions */
    row-gap: 20px;
    column-gap: 10px;
}
```

### Item Properties
```css
.item {
    /* Growth factor (how much to grow) */
    flex-grow: 0;    /* Default: don't grow */
    flex-grow: 1;    /* Grow to fill space */

    /* Shrink factor (how much to shrink) */
    flex-shrink: 1;  /* Default: can shrink */
    flex-shrink: 0;  /* Don't shrink */

    /* Base size before grow/shrink */
    flex-basis: auto;  /* Default: content size */
    flex-basis: 200px; /* Fixed base size */

    /* Shorthand: grow | shrink | basis */
    flex: 0 1 auto;    /* Default */
    flex: 1;           /* flex: 1 1 0 - equal size items */
    flex: auto;        /* flex: 1 1 auto */

    /* Individual alignment */
    align-self: center;

    /* Order */
    order: 0;    /* Default */
    order: -1;   /* Move to start */
    order: 1;    /* Move to end */
}
```

### Common Flexbox Patterns
```css
/* Center everything */
.center {
    display: flex;
    justify-content: center;
    align-items: center;
}

/* Space between with centering */
.navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

/* Equal width columns */
.columns {
    display: flex;
}
.columns > * {
    flex: 1;
}

/* Sidebar layout */
.layout {
    display: flex;
}
.sidebar {
    flex: 0 0 250px; /* Fixed width */
}
.content {
    flex: 1; /* Take remaining space */
}
```

---

## 6. CSS Grid Layout

### Why It Matters
- Two-dimensional layout control
- Complex layouts made simple
- Explicit row and column definition

### Key Concepts

| Property | Purpose | Example |
|----------|---------|---------|
| `display: grid` | Enable grid | - |
| `grid-template-columns` | Define columns | `1fr 1fr 1fr` |
| `grid-template-rows` | Define rows | `100px auto` |
| `gap` | Spacing | `20px` |
| `grid-column` | Item column span | `1 / 3` |
| `grid-row` | Item row span | `1 / 2` |
| `grid-area` | Named area | `header` |

### Container Properties
```css
.grid-container {
    display: grid;

    /* Define columns */
    grid-template-columns: 200px 1fr 1fr;    /* Fixed + flexible */
    grid-template-columns: repeat(3, 1fr);   /* 3 equal columns */
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); /* Responsive */

    /* Define rows */
    grid-template-rows: 100px auto 50px;
    grid-template-rows: repeat(3, 1fr);

    /* Gap */
    gap: 20px;
    row-gap: 20px;
    column-gap: 10px;

    /* Alignment */
    justify-items: center;    /* Horizontal alignment of items */
    align-items: center;      /* Vertical alignment of items */
    justify-content: center;  /* Horizontal alignment of grid */
    align-content: center;    /* Vertical alignment of grid */
}
```

### Item Placement
```css
.item {
    /* Span columns */
    grid-column: 1 / 3;      /* Start at 1, end at 3 */
    grid-column: span 2;     /* Span 2 columns */

    /* Span rows */
    grid-row: 1 / 3;
    grid-row: span 2;

    /* Shorthand */
    grid-area: 1 / 1 / 3 / 3; /* row-start / col-start / row-end / col-end */
}
```

### Grid Template Areas
```css
.layout {
    display: grid;
    grid-template-areas:
        "header header header"
        "sidebar main main"
        "footer footer footer";
    grid-template-columns: 200px 1fr 1fr;
    grid-template-rows: 80px 1fr 60px;
}

.header { grid-area: header; }
.sidebar { grid-area: sidebar; }
.main { grid-area: main; }
.footer { grid-area: footer; }
```

### Common Grid Patterns
```css
/* 12-column grid */
.grid-12 {
    display: grid;
    grid-template-columns: repeat(12, 1fr);
    gap: 20px;
}

/* Responsive card grid */
.card-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 20px;
}

/* Holy grail layout */
.holy-grail {
    display: grid;
    grid-template:
        "header header header" auto
        "nav main aside" 1fr
        "footer footer footer" auto
        / 200px 1fr 200px;
    min-height: 100vh;
}
```

---

## 7. Responsive Design

### Why It Matters
- Mobile users are majority
- Different devices need different layouts
- Better user experience

### Key Concepts

| Concept | Description | Example |
|---------|-------------|---------|
| Viewport | Visible area | `<meta viewport>` |
| Media query | Conditional styles | `@media (min-width)` |
| Breakpoint | Width threshold | 768px, 1024px |
| Mobile-first | Start with mobile | `min-width` queries |

### Media Queries
```css
/* Mobile first approach (recommended) */
.container {
    width: 100%;
    padding: 10px;
}

/* Tablet */
@media (min-width: 768px) {
    .container {
        width: 750px;
        padding: 20px;
    }
}

/* Desktop */
@media (min-width: 1024px) {
    .container {
        width: 960px;
    }
}

/* Large desktop */
@media (min-width: 1200px) {
    .container {
        width: 1140px;
    }
}
```

### Common Breakpoints

| Device | Width | Media Query |
|--------|-------|-------------|
| Mobile | < 576px | Default |
| Small tablet | >= 576px | `@media (min-width: 576px)` |
| Tablet | >= 768px | `@media (min-width: 768px)` |
| Desktop | >= 992px | `@media (min-width: 992px)` |
| Large desktop | >= 1200px | `@media (min-width: 1200px)` |

### Responsive Units

| Unit | Relative To | Use Case |
|------|-------------|----------|
| `%` | Parent element | Widths, responsive sizing |
| `em` | Parent font-size | Spacing, padding |
| `rem` | Root font-size | Typography, consistent spacing |
| `vw` | Viewport width | Full-width elements |
| `vh` | Viewport height | Full-height sections |
| `vmin` | Smaller of vw/vh | Responsive squares |
| `vmax` | Larger of vw/vh | - |

```css
/* Responsive font sizing */
html { font-size: 16px; }

h1 { font-size: 2rem; }     /* 32px */
h2 { font-size: 1.5rem; }   /* 24px */
p { font-size: 1rem; }      /* 16px */

/* Viewport units */
.hero {
    height: 100vh;
    width: 100vw;
}

/* clamp() for fluid typography */
h1 {
    font-size: clamp(1.5rem, 5vw, 3rem);
    /* min: 1.5rem, preferred: 5vw, max: 3rem */
}
```

---

## 8. Positioning

### Why It Matters
- Control exact element placement
- Create overlays and sticky elements
- Break out of normal flow when needed

### Key Concepts

| Value | Behavior | Reference |
|-------|----------|-----------|
| `static` | Normal flow (default) | - |
| `relative` | Offset from normal position | Self |
| `absolute` | Removed from flow | Nearest positioned ancestor |
| `fixed` | Removed from flow | Viewport |
| `sticky` | Hybrid relative/fixed | Scroll container |

```css
/* Relative - offset from original position */
.relative {
    position: relative;
    top: 10px;
    left: 20px;
    /* Original space is preserved */
}

/* Absolute - positioned within container */
.container {
    position: relative; /* Creates positioning context */
}
.absolute {
    position: absolute;
    top: 0;
    right: 0;
    /* Removed from flow */
}

/* Fixed - stays in place during scroll */
.fixed-header {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 1000;
}

/* Sticky - sticks when scrolled past */
.sticky-nav {
    position: sticky;
    top: 0;
    z-index: 100;
}
```

### Z-Index and Stacking
```css
/* Higher z-index = closer to viewer */
.behind { z-index: 1; }
.middle { z-index: 10; }
.front { z-index: 100; }
.modal { z-index: 1000; }

/* Only works on positioned elements */
.needs-position {
    position: relative; /* or absolute, fixed, sticky */
    z-index: 10;
}
```

---

## 9. Colors and Backgrounds

### Why It Matters
- Visual hierarchy and branding
- User interface clarity
- Aesthetic appeal

### Color Formats
```css
/* Named colors */
color: red;
color: transparent;

/* Hexadecimal */
color: #ff0000;      /* Full */
color: #f00;         /* Short */
color: #ff000080;    /* With alpha */

/* RGB / RGBA */
color: rgb(255, 0, 0);
color: rgba(255, 0, 0, 0.5);

/* HSL / HSLA */
color: hsl(0, 100%, 50%);
color: hsla(0, 100%, 50%, 0.5);

/* CSS Variables */
:root {
    --primary: #007bff;
    --primary-rgb: 0, 123, 255;
}
.element {
    color: var(--primary);
    background: rgba(var(--primary-rgb), 0.1);
}
```

### Backgrounds
```css
.element {
    /* Solid color */
    background-color: #f0f0f0;

    /* Image */
    background-image: url('image.jpg');
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover; /* or contain, 100% 100% */

    /* Shorthand */
    background: #f0f0f0 url('image.jpg') no-repeat center/cover;

    /* Multiple backgrounds */
    background:
        url('overlay.png') no-repeat center,
        url('background.jpg') no-repeat center/cover;
}
```

### Gradients
```css
/* Linear gradient */
background: linear-gradient(to right, red, blue);
background: linear-gradient(45deg, #ff0000, #0000ff);
background: linear-gradient(to bottom, #fff 0%, #000 100%);

/* Radial gradient */
background: radial-gradient(circle, red, blue);
background: radial-gradient(ellipse at center, #fff, #000);

/* Multiple color stops */
background: linear-gradient(
    90deg,
    red 0%,
    orange 25%,
    yellow 50%,
    green 75%,
    blue 100%
);
```

---

## 10. Transitions and Animations

### Why It Matters
- Smooth user experience
- Visual feedback
- Professional polish

### Transitions
```css
.button {
    background: blue;
    color: white;

    /* Transition specific property */
    transition: background-color 0.3s ease;

    /* Transition multiple properties */
    transition: background-color 0.3s ease,
                transform 0.2s ease-in-out;

    /* Transition all properties */
    transition: all 0.3s ease;
}

.button:hover {
    background: darkblue;
    transform: scale(1.05);
}

/* Transition shorthand: property duration timing-function delay */
transition: opacity 0.3s ease-in-out 0.1s;
```

### Timing Functions

| Function | Behavior |
|----------|----------|
| `linear` | Constant speed |
| `ease` | Slow-fast-slow (default) |
| `ease-in` | Slow start |
| `ease-out` | Slow end |
| `ease-in-out` | Slow start and end |
| `cubic-bezier()` | Custom curve |

### Keyframe Animations
```css
/* Define animation */
@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(-20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

/* Or with percentages */
@keyframes pulse {
    0% { transform: scale(1); }
    50% { transform: scale(1.1); }
    100% { transform: scale(1); }
}

/* Apply animation */
.element {
    animation: fadeIn 0.5s ease forwards;

    /* Multiple animations */
    animation: fadeIn 0.5s ease, pulse 2s infinite;
}

/* Animation properties */
.animated {
    animation-name: fadeIn;
    animation-duration: 0.5s;
    animation-timing-function: ease;
    animation-delay: 0.2s;
    animation-iteration-count: 1; /* or infinite */
    animation-direction: normal; /* or reverse, alternate */
    animation-fill-mode: forwards; /* or backwards, both */
    animation-play-state: running; /* or paused */
}
```

### Transform Property
```css
.element {
    /* Translate (move) */
    transform: translateX(50px);
    transform: translateY(20px);
    transform: translate(50px, 20px);

    /* Scale (resize) */
    transform: scale(1.5);
    transform: scaleX(2);

    /* Rotate */
    transform: rotate(45deg);
    transform: rotateX(45deg); /* 3D */
    transform: rotateY(45deg); /* 3D */

    /* Skew */
    transform: skew(10deg, 5deg);

    /* Multiple transforms */
    transform: translateX(50px) rotate(45deg) scale(1.2);

    /* Transform origin */
    transform-origin: center; /* default */
    transform-origin: top left;
    transform-origin: 50% 100%;
}
```

---

## Quick Reference Card

### Box Model Reset
```css
*, *::before, *::after {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}
```

### Flexbox Centering
```css
.center {
    display: flex;
    justify-content: center;
    align-items: center;
}
```

### Responsive Grid
```css
.grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 20px;
}
```

### Smooth Transitions
```css
.interactive {
    transition: all 0.3s ease;
}
```

### Screen Reader Only
```css
.sr-only {
    position: absolute;
    width: 1px;
    height: 1px;
    padding: 0;
    margin: -1px;
    overflow: hidden;
    clip: rect(0, 0, 0, 0);
    border: 0;
}
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Write CSS selectors with appropriate specificity
- [ ] Understand and apply the box model
- [ ] Use display property effectively
- [ ] Create layouts with Flexbox
- [ ] Create layouts with CSS Grid
- [ ] Build responsive designs with media queries
- [ ] Use positioning for complex layouts
- [ ] Apply colors, backgrounds, and gradients
- [ ] Create smooth transitions and animations
- [ ] Write maintainable, organized CSS

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 6: JavaScript](../06-javascript/) - Add interactivity
- Practice building complete responsive layouts
- Explore CSS frameworks like Bootstrap

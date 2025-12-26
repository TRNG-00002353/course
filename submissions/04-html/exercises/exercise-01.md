# Exercise: Build a Personal Portfolio Page

| Property | Value |
|----------|-------|
| **Topic** | 04-html |
| **Type** | Exercise |
| **Difficulty** | Beginner |

## Objective
Create a semantic HTML5 portfolio page that showcases your skills, projects, and contact information.

## Requirements

Build an HTML page (`portfolio.html`) that includes:

### 1. Document Structure
- Proper DOCTYPE declaration
- HTML5 semantic elements
- Meta tags for charset and viewport

### 2. Required Sections

#### Header Section
- Your name as the main heading (h1)
- A navigation menu with links to page sections
- A professional tagline or title

#### About Section
- A brief biography (2-3 paragraphs)
- A profile image with proper alt text
- A list of your key skills (unordered list)

#### Projects Section
- At least 3 project cards containing:
  - Project title (h3)
  - Project description (paragraph)
  - Technology tags
  - A "View Project" link

#### Contact Section
- A contact form with:
  - Name field (required)
  - Email field (required, with email validation)
  - Message textarea (required)
  - Submit button
- Social media links (can be placeholder URLs)

#### Footer
- Copyright notice
- Current year

### 3. HTML Elements to Use
- Semantic elements: `<header>`, `<nav>`, `<main>`, `<section>`, `<article>`, `<footer>`
- Form elements: `<form>`, `<input>`, `<textarea>`, `<button>`, `<label>`
- Media: `<img>`, `<figure>`, `<figcaption>`
- Text: `<h1>`-`<h6>`, `<p>`, `<ul>`, `<ol>`, `<a>`, `<strong>`, `<em>`
- Grouping: `<div>`, `<span>`

## Skills Tested
- HTML5 semantic structure
- Form creation and validation attributes
- Proper use of headings hierarchy
- Accessibility basics (alt text, labels)
- Linking and navigation

## Validation
Your HTML should pass the W3C Validator without errors.

---

## Progressive Enhancement Versions

Complete this exercise in 3 versions to demonstrate progressive enhancement:

### Version 1: Raw HTML
**File:** `portfolio-v1.html`

Build the portfolio using only semantic HTML5 - no styling.

**Focus on:**
- Proper document structure
- Semantic elements (header, nav, main, section, footer)
- Accessibility (alt text, labels, ARIA where needed)
- Valid HTML5

**Expected output:** A functional but unstyled page that works in any browser.

---

### Version 2: HTML + CSS3
**Files:** `portfolio-v2.html`, `portfolio-v2.css`

Enhance Version 1 with custom CSS3 styling.

**Add these CSS features:**
- Flexbox or Grid layout for page structure
- Custom typography (font-family, font-size, line-height)
- Color scheme with CSS custom properties (variables)
- Hover effects and transitions
- Responsive design with media queries
- Box shadows, border-radius for cards
- Form styling (inputs, buttons)

**CSS concepts to demonstrate:**
```css
/* Example CSS features to use */
:root { --primary-color: #3498db; }
.container { display: flex; }
.card { box-shadow: 0 2px 8px rgba(0,0,0,0.1); }
@media (max-width: 768px) { /* responsive styles */ }
```

---

### Version 3: HTML + Bootstrap 5
**File:** `portfolio-v3.html`

Rebuild using Bootstrap 5 framework.

**Use these Bootstrap components:**
- Container, Row, Col for grid layout
- Navbar component for navigation
- Card component for projects
- Form classes for contact form
- Button styles
- Utility classes (spacing, text, colors)
- Responsive breakpoints (sm, md, lg, xl)

**Bootstrap setup:**
```html
<!-- Add to <head> -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<!-- Add before </body> -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
```

**Example Bootstrap structure:**
```html
<div class="container">
    <div class="row">
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Project Name</h5>
                </div>
            </div>
        </div>
    </div>
</div>
```

---

## Submission

### Required Files
| File | Description |
|------|-------------|
| `portfolio-v1.html` | Raw HTML version |
| `portfolio-v2.html` | CSS3 enhanced version |
| `portfolio-v2.css` | CSS3 stylesheet |
| `portfolio-v3.html` | Bootstrap version |

### Folder Structure
Submit your solution in your repository under:
```
your-repo/
└── 04-html/
    ├── portfolio-v1.html
    ├── portfolio-v2.html
    ├── portfolio-v2.css
    └── portfolio-v3.html
```

### Technical Checklist
- [ ] All 3 versions present
- [ ] Valid HTML5 DOCTYPE declaration
- [ ] Uses semantic elements (header, nav, main, section, footer)
- [ ] All images have alt attributes
- [ ] Form has proper labels and validation
- [ ] Passes W3C Validator

### Evaluation Criteria
| Criteria | Points |
|----------|--------|
| **Version 1 (Raw HTML)** | **30** |
| Valid HTML5 structure | 10 |
| Semantic elements used correctly | 10 |
| Accessibility (alt, labels) | 10 |
| **Version 2 (CSS3)** | **35** |
| Layout (Flexbox/Grid) | 10 |
| Typography & colors | 10 |
| Responsive design | 10 |
| Transitions & effects | 5 |
| **Version 3 (Bootstrap)** | **35** |
| Bootstrap grid used correctly | 10 |
| Components used appropriately | 15 |
| Responsive breakpoints | 10 |
| **Total** | **100** |

### How to Submit
```bash
# In your repository
mkdir -p 04-html
git add 04-html/portfolio-v1.html 04-html/portfolio-v2.html 04-html/portfolio-v2.css 04-html/portfolio-v3.html
git commit -m "Complete 04-html exercise-01: portfolio page (3 versions)"
git push
```

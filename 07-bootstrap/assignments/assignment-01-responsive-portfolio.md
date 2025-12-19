# Assignment 1: Responsive Portfolio Page with Bootstrap

## Objective
Create a responsive personal portfolio page using Bootstrap's grid system and utility classes.

## Instructions

### Part 1: Page Structure (30 points)

Create an `index.html` file with the following sections:

1. **Navigation Bar**
   - Fixed-top responsive navbar
   - Brand/Logo on the left
   - Navigation links: Home, About, Projects, Contact
   - Collapsible hamburger menu on mobile

2. **Hero Section**
   - Full-width background
   - Centered heading with your name
   - Brief tagline/title
   - Call-to-action button

3. **About Section**
   - Two-column layout (image + text)
   - Should stack on mobile devices
   - Brief bio and skills list

### Part 2: Projects Grid (40 points)

1. Create a projects section with:
   - Section heading
   - Grid of 6 project cards (3 columns on desktop, 2 on tablet, 1 on mobile)

2. Each project card should include:
   - Project image/placeholder
   - Project title
   - Brief description
   - Technology tags/badges
   - "View Project" button

3. Use Bootstrap classes:
   - `row` and `col-*` for grid layout
   - `card` component for project cards
   - `badge` for technology tags
   - `btn` for buttons

### Part 3: Contact & Footer (30 points)

1. **Contact Section**
   - Contact form with fields: Name, Email, Message
   - Form validation using Bootstrap classes
   - Submit button with appropriate styling
   - Two-column layout: form + contact info

2. **Footer**
   - Three-column layout with:
     - Quick links
     - Social media icons
     - Copyright notice
   - Should stack on mobile

## Requirements

- Use Bootstrap 5.x CDN
- Mobile-first responsive design
- Minimum breakpoints: sm, md, lg
- Use Bootstrap utility classes for spacing and colors
- No custom CSS required (Bootstrap only)

## Starter Template

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Portfolio</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- Your content here -->

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```

## Submission Requirements

Submit a folder containing:
- `index.html` - main portfolio page
- `images/` folder with any images used
- `README.md` with setup instructions

## Grading Criteria

| Criteria | Points |
|----------|--------|
| Navigation & Hero | 15 |
| About Section | 15 |
| Projects Grid | 40 |
| Contact Form | 20 |
| Footer | 10 |
| **Total** | **100** |

## Due Date
Submit by end of weekend (Sunday 11:59 PM)

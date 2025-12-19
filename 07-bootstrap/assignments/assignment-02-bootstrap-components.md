# Assignment 2: Bootstrap Components Showcase

## Objective
Build a multi-page website demonstrating proficiency with various Bootstrap components and JavaScript plugins.

## Instructions

### Part 1: Landing Page with Carousel (25 points)

Create `index.html` with:

1. **Image Carousel**
   - Minimum 3 slides with images
   - Captions on each slide
   - Previous/Next controls
   - Slide indicators

2. **Feature Cards**
   - 3-4 feature cards below carousel
   - Use icons (Bootstrap Icons or Font Awesome)
   - Hover effects using Bootstrap classes

3. **Testimonials Section**
   - Use Bootstrap Cards or Blockquotes
   - Include user avatar, quote, and name
   - Implement as a carousel or grid

### Part 2: Interactive Components Page (35 points)

Create `components.html` demonstrating:

1. **Accordion** (10 points)
   - FAQ section with 5+ questions
   - Only one item open at a time
   - Custom styling with Bootstrap utilities

2. **Modal Windows** (10 points)
   - Login modal with form
   - Confirmation modal with buttons
   - Image gallery modal (lightbox effect)

3. **Tabs & Pills** (10 points)
   - Tabbed content section (minimum 4 tabs)
   - Pill navigation for different category
   - Vertical tabs for sidebar navigation

4. **Tooltips & Popovers** (5 points)
   - Add tooltips to navigation items
   - Add popovers to information icons

### Part 3: Forms & Validation Page (25 points)

Create `forms.html` with:

1. **Registration Form**
   - Input groups with icons
   - Floating labels
   - Select dropdowns
   - Radio buttons and checkboxes
   - File upload field

2. **Form Validation**
   - Required field validation
   - Email format validation
   - Password strength indicator
   - Custom validation messages
   - Success/error states

3. **Form Layouts**
   - Horizontal form layout
   - Inline form for search
   - Multi-step form with progress indicator

### Part 4: Navigation & Utilities (15 points)

1. **Consistent Navigation**
   - Same navbar across all pages
   - Active state for current page
   - Dropdown menu in navigation
   - Offcanvas sidebar menu

2. **Breadcrumbs**
   - Add breadcrumb navigation to inner pages

3. **Alerts & Toasts**
   - Display success alert on form submission
   - Toast notifications for actions

## File Structure

```
bootstrap-showcase/
├── index.html
├── components.html
├── forms.html
├── images/
│   └── (carousel and other images)
└── README.md
```

## Requirements

- Bootstrap 5.x CDN
- Bootstrap Icons or Font Awesome
- Initialize JavaScript components properly
- Responsive on all screen sizes
- Semantic HTML structure

## JavaScript Initialization Example

```javascript
// Initialize tooltips
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl)
})
```

## Submission Requirements

Submit a zip file containing:
- All HTML files
- Images folder
- README.md with:
  - Description of each page
  - List of Bootstrap components used
  - Any challenges faced

## Grading Criteria

| Criteria | Points |
|----------|--------|
| Landing Page & Carousel | 25 |
| Accordion & Modals | 20 |
| Tabs, Tooltips & Popovers | 15 |
| Forms & Validation | 25 |
| Navigation & Utilities | 15 |
| **Total** | **100** |

## Due Date
Submit by end of weekend (Sunday 11:59 PM)

# Project 1: Restaurant Website

## Project Overview

**Project Name:** The Gourmet Kitchen
**Industry:** Food & Dining
**Type:** Multi-page Restaurant Website

### Description
Build a professional restaurant website for "The Gourmet Kitchen," an upscale dining establishment. The website should showcase the restaurant's menu, atmosphere, and allow customers to make reservations and contact the restaurant.

### Target Audience
- Local diners looking for fine dining options
- Tourists searching for quality restaurants
- Event planners seeking venues for private dining

---

## Pages Required

| Page | Primary Owner | Description |
|------|---------------|-------------|
| Home | Member A | Landing page with hero, highlights, testimonials |
| Menu | Member C | Full menu with categories, prices, dietary info |
| About | Member D | Restaurant story, chef profile, values |
| Reservations | Member B | Booking form with date/time selection |
| Gallery | Member D | Photo gallery of food, ambiance, events |
| Contact | Member B | Contact form, location map, hours |

---

## User Stories by Team Member

### Member A - Core Structure & Home

#### Feature 1: Responsive Navigation Header
**Issue Title:** `feat: Implement responsive navigation header`

**User Story:**
> As a **visitor**, I want to **see a clear navigation menu on all pages** so that **I can easily browse different sections of the website**.

**Acceptance Criteria:**
- [ ] Logo on the left side links to home page
- [ ] Navigation links: Home, Menu, About, Reservations, Gallery, Contact
- [ ] Desktop: Horizontal navigation bar
- [ ] Mobile: Hamburger menu with slide-out or dropdown navigation
- [ ] Current page is highlighted in navigation
- [ ] Navigation is fixed/sticky on scroll
- [ ] Uses Bootstrap navbar component

**Technical Notes:**
- Bootstrap `navbar`, `navbar-expand-lg`, `navbar-toggler`
- Active state styling for current page

---

#### Feature 2: Home Page Hero Section
**Issue Title:** `feat: Create home page hero section with CTA`

**User Story:**
> As a **potential customer**, I want to **see an appealing hero section when I land on the website** so that **I immediately understand what the restaurant offers and feel compelled to explore**.

**Acceptance Criteria:**
- [ ] Full-width hero image with overlay
- [ ] Restaurant name as main heading (h1)
- [ ] Tagline/subtitle describing the cuisine
- [ ] Two CTA buttons: "View Menu" and "Make Reservation"
- [ ] Responsive image handling (different sizes for mobile/desktop)
- [ ] Minimum height of 70vh on desktop

**Technical Notes:**
- Background image with CSS overlay
- Bootstrap buttons with custom styling
- Consider `picture` element or CSS background-image

---

#### Feature 3: Site-wide Footer
**Issue Title:** `feat: Implement site-wide footer component`

**User Story:**
> As a **visitor**, I want to **find quick links, contact info, and social media in the footer** so that **I can easily access important information from any page**.

**Acceptance Criteria:**
- [ ] Three-column layout (responsive to single column on mobile)
- [ ] Column 1: Quick links to all pages
- [ ] Column 2: Contact information (address, phone, email)
- [ ] Column 3: Operating hours
- [ ] Social media icons with links (placeholder URLs acceptable)
- [ ] Copyright notice with current year
- [ ] Newsletter signup form (email input + subscribe button)

**Technical Notes:**
- Bootstrap grid for columns
- Font Awesome or Bootstrap icons for social media
- Use `<address>` element for contact info

---

### Member B - Forms & Interaction

#### Feature 4: Reservation Booking Form
**Issue Title:** `feat: Create reservation booking form`

**User Story:**
> As a **customer**, I want to **book a table online** so that **I can secure my dining spot without calling the restaurant**.

**Acceptance Criteria:**
- [ ] Form fields: Name, Email, Phone, Date, Time, Party Size, Special Occasion, Special Requests
- [ ] Date picker (HTML5 date input)
- [ ] Time selection via dropdown with optgroups (Lunch/Dinner slots)
- [ ] Party size: number input (min 1, max 20)
- [ ] Special occasion dropdown (Birthday, Anniversary, Business, etc.)
- [ ] Special requests textarea
- [ ] All required fields marked with asterisk
- [ ] Form validation with HTML5 attributes
- [ ] Submit and Reset buttons

**Technical Notes:**
- Bootstrap form classes
- `required`, `min`, `max`, `pattern` attributes
- `<optgroup>` for time slots
- Form `action="#"` and `method="POST"`

---

#### Feature 5: Contact Page with Form and Map
**Issue Title:** `feat: Build contact page with form and location`

**User Story:**
> As a **visitor**, I want to **contact the restaurant and see their location** so that **I can ask questions or find directions**.

**Acceptance Criteria:**
- [ ] Contact form: Name, Email, Phone, Subject (dropdown), Message
- [ ] Subject options: General Inquiry, Reservation Question, Private Events, Feedback, Other
- [ ] Embedded Google Map (iframe) showing restaurant location
- [ ] Contact information card (address, phone, email)
- [ ] Operating hours displayed
- [ ] Form validation with appropriate input types

**Technical Notes:**
- Bootstrap grid for form and map side-by-side on desktop
- Google Maps embed iframe (use placeholder coordinates)
- Cards for contact info display

---

#### Feature 6: Newsletter Signup Section
**Issue Title:** `feat: Add newsletter signup with validation`

**User Story:**
> As a **restaurant**, I want to **collect visitor emails for our newsletter** so that **we can inform them about specials and events**.

**Acceptance Criteria:**
- [ ] Standalone section on home page (above footer)
- [ ] Compelling headline and brief description
- [ ] Email input with validation
- [ ] Subscribe button
- [ ] Checkbox for terms acceptance
- [ ] Visual feedback styling (focus states, validation states)
- [ ] Background styling to make section stand out

**Technical Notes:**
- Bootstrap form-inline or input-group
- Email validation pattern
- Consider Bootstrap alerts for feedback messaging

---

### Member C - Menu & Data Display

#### Feature 7: Menu Page with Categories
**Issue Title:** `feat: Create categorized menu page`

**User Story:**
> As a **customer**, I want to **browse the menu by category** so that **I can easily find dishes I'm interested in**.

**Acceptance Criteria:**
- [ ] Menu categories: Appetizers, Main Courses, Desserts, Beverages
- [ ] In-page navigation to jump to categories
- [ ] Each category in its own section with heading
- [ ] Minimum 4-5 items per category
- [ ] Sticky category navigation on scroll (optional)

**Technical Notes:**
- Bootstrap scrollspy (optional)
- Section IDs for anchor navigation
- Cards or custom layout for menu items

---

#### Feature 8: Menu Item Cards with Details
**Issue Title:** `feat: Design menu item cards with pricing and dietary info`

**User Story:**
> As a **customer**, I want to **see dish names, descriptions, prices, and dietary information** so that **I can make informed ordering decisions**.

**Acceptance Criteria:**
- [ ] Each item displays: Name, Description, Price
- [ ] Dietary icons/badges (V-Vegetarian, VG-Vegan, GF-Gluten Free, N-Contains Nuts)
- [ ] Optional: Spicy indicator
- [ ] Featured/Chef's Special items highlighted
- [ ] Responsive grid (2 columns tablet, 1 column mobile)
- [ ] Hover effects on cards

**Technical Notes:**
- Bootstrap cards or custom flexbox/grid layout
- `<abbr>` tags for dietary abbreviations
- Badge component for dietary indicators
- Description list `<dl>` alternative layout

---

#### Feature 9: Wine & Beverages Table
**Issue Title:** `feat: Build wine and beverages pricing table`

**User Story:**
> As a **customer**, I want to **see the wine list with glass/bottle pricing** so that **I can choose wine to pair with my meal**.

**Acceptance Criteria:**
- [ ] Table with columns: Wine Name, Region/Type, Glass Price, Bottle Price
- [ ] Table caption describing the selection
- [ ] Proper table headers with scope attributes
- [ ] At least 8 wine entries
- [ ] Categories: Red, White, Sparkling (use rowspan or separate tables)
- [ ] Non-alcoholic section with description list format
- [ ] Responsive table (horizontal scroll on mobile)

**Technical Notes:**
- Bootstrap table classes (`table`, `table-striped`, `table-hover`)
- `<caption>`, `<thead>`, `<tbody>` elements
- `scope="col"` and `scope="row"` for accessibility
- `table-responsive` wrapper

---

### Member D - Content & Media

#### Feature 10: About Page - Restaurant Story
**Issue Title:** `feat: Create about page with restaurant story`

**User Story:**
> As a **visitor**, I want to **learn about the restaurant's history and values** so that **I can connect with the brand before dining**.

**Acceptance Criteria:**
- [ ] Hero section with restaurant interior image
- [ ] "Our Story" section with 2-3 paragraphs
- [ ] "Our Philosophy" section with values/mission
- [ ] Timeline or milestones (optional)
- [ ] Pull quotes or highlighted text
- [ ] Responsive layout with images and text

**Technical Notes:**
- Bootstrap grid for text/image layouts
- `<blockquote>` for pull quotes
- `<figure>` and `<figcaption>` for images

---

#### Feature 11: Chef & Team Profile Section
**Issue Title:** `feat: Add chef and team profiles`

**User Story:**
> As a **customer**, I want to **learn about the chef and kitchen team** so that **I feel confident in the quality of the food**.

**Acceptance Criteria:**
- [ ] Head Chef profile with photo, name, title, bio
- [ ] 2-3 additional team member cards
- [ ] Each profile: Photo, Name, Role, Brief bio
- [ ] Responsive grid layout
- [ ] Professional styling with hover effects
- [ ] Social media links (optional)

**Technical Notes:**
- Bootstrap cards for profiles
- Circular or rounded images
- Grid: 4 columns desktop, 2 tablet, 1 mobile

---

#### Feature 12: Photo Gallery Page
**Issue Title:** `feat: Build photo gallery with categories`

**User Story:**
> As a **potential customer**, I want to **see photos of the food and restaurant atmosphere** so that **I know what to expect when I visit**.

**Acceptance Criteria:**
- [ ] Gallery categories: Food, Interior, Events
- [ ] Filter buttons to show/hide categories (CSS-based or simple)
- [ ] Minimum 12 photos total
- [ ] Responsive grid layout (4 cols desktop, 2 tablet, 1 mobile)
- [ ] Images with proper alt text
- [ ] Lightbox effect on click (optional - CSS-only or Bootstrap modal)
- [ ] Figure with captions for each image

**Technical Notes:**
- Bootstrap grid for gallery layout
- `<figure>` and `<figcaption>` elements
- Data attributes for filtering (CSS approach)
- Consider `loading="lazy"` for images

---

## Technical Requirements Checklist

### HTML Elements to Use
- [ ] Semantic structure: `header`, `nav`, `main`, `section`, `article`, `aside`, `footer`
- [ ] Forms: `form`, `fieldset`, `legend`, `label`, `input`, `select`, `optgroup`, `textarea`, `button`
- [ ] Tables: `table`, `caption`, `thead`, `tbody`, `tfoot`, `th`, `td`, `scope`
- [ ] Lists: `ul`, `ol`, `li`, `dl`, `dt`, `dd`
- [ ] Media: `img`, `figure`, `figcaption`, `picture` (optional)
- [ ] Text: proper heading hierarchy, `p`, `blockquote`, `abbr`, `time`, `address`

### Bootstrap Components to Use
- [ ] Navbar with responsive toggle
- [ ] Grid system (container, row, col)
- [ ] Cards
- [ ] Forms and form controls
- [ ] Buttons
- [ ] Tables
- [ ] Badges
- [ ] Modal (optional for gallery)
- [ ] Utilities (spacing, colors, display)

### Responsive Breakpoints
- [ ] Mobile: < 576px
- [ ] Tablet: 576px - 991px
- [ ] Desktop: 992px+

---

## Git Issues to Create

Create these issues in your repository before starting:

1. `feat: Implement responsive navigation header`
2. `feat: Create home page hero section with CTA`
3. `feat: Implement site-wide footer component`
4. `feat: Create reservation booking form`
5. `feat: Build contact page with form and location`
6. `feat: Add newsletter signup with validation`
7. `feat: Create categorized menu page`
8. `feat: Design menu item cards with pricing and dietary info`
9. `feat: Build wine and beverages pricing table`
10. `feat: Create about page with restaurant story`
11. `feat: Add chef and team profiles`
12. `feat: Build photo gallery with categories`

---

## Mockup Suggestions

Consider these layout approaches:

### Home Page
```
┌─────────────────────────────────────┐
│           NAVIGATION                │
├─────────────────────────────────────┤
│                                     │
│            HERO SECTION             │
│     [View Menu] [Reservation]       │
│                                     │
├─────────────────────────────────────┤
│  Welcome Text / About Snippet       │
├──────────┬──────────┬───────────────┤
│ Feature  │ Feature  │ Feature       │
│  Card 1  │  Card 2  │  Card 3       │
├──────────┴──────────┴───────────────┤
│       TESTIMONIALS SECTION          │
├─────────────────────────────────────┤
│       NEWSLETTER SIGNUP             │
├─────────────────────────────────────┤
│            FOOTER                   │
└─────────────────────────────────────┘
```

### Menu Page
```
┌─────────────────────────────────────┐
│           NAVIGATION                │
├─────────────────────────────────────┤
│         MENU PAGE HEADER            │
├─────────────────────────────────────┤
│ [Appetizers][Mains][Desserts][Drinks]│
├─────────────────────────────────────┤
│         APPETIZERS                  │
│  ┌─────────┐  ┌─────────┐          │
│  │ Item 1  │  │ Item 2  │          │
│  └─────────┘  └─────────┘          │
├─────────────────────────────────────┤
│         MAIN COURSES                │
│  ┌─────────┐  ┌─────────┐          │
│  │ Item 1  │  │ Item 2  │          │
│  └─────────┘  └─────────┘          │
├─────────────────────────────────────┤
│            FOOTER                   │
└─────────────────────────────────────┘
```

---

## Resources

### Sample Content
- Use [Lorem Ipsum](https://www.lipsum.com/) for placeholder text
- Use [Unsplash](https://unsplash.com/s/photos/restaurant) for food/restaurant images
- Use [Placeholder.com](https://placeholder.com/) for placeholder images

### Design Inspiration
- Search "restaurant website design" on Dribbble or Behance
- Look at real restaurant websites for layout ideas

---

## Definition of Done

A feature is considered **DONE** when:
- [ ] Code is committed to feature branch
- [ ] Pull request created with description
- [ ] Code reviewed by at least 1 team member
- [ ] All acceptance criteria met
- [ ] HTML validates without errors
- [ ] Page is responsive on all breakpoints
- [ ] Merged to develop branch
- [ ] Issue closed with PR reference

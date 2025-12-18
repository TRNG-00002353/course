# Project 4: Hotel & Resort Website

## Project Overview

**Project Name:** Azure Bay Resort
**Industry:** Hospitality / Travel
**Type:** Multi-page Hotel Website

### Description
Build an elegant website for "Azure Bay Resort," a luxury beachfront resort. The website should showcase accommodations, amenities, and allow guests to inquire about bookings and explore the destination.

### Target Audience
- Vacationers planning beach holidays
- Business travelers seeking conference facilities
- Couples looking for romantic getaways
- Families planning resort vacations

---

## Pages Required

| Page | Primary Owner | Description |
|------|---------------|-------------|
| Home | Member A | Landing with hero, highlights, offers |
| Rooms & Suites | Member C | Room types with details and pricing |
| Amenities | Member C | Facilities and services listing |
| Dining | Member D | Restaurant and cuisine showcase |
| Gallery | Member D | Photo gallery of resort |
| Booking | Member B | Reservation inquiry form |
| Contact | Member B | Location, contact form |

---

## User Stories by Team Member

### Member A - Core Structure & Home

#### Feature 1: Elegant Resort Navigation
**Issue Title:** `feat: Implement elegant resort navigation with booking CTA`

**User Story:**
> As a **resort visitor**, I want to **navigate easily through all resort offerings** so that **I can explore accommodations and amenities**.

**Acceptance Criteria:**
- [ ] Resort logo/wordmark on left
- [ ] Nav links: Rooms, Amenities, Dining, Gallery, Contact
- [ ] "Book Now" prominent CTA button
- [ ] Language selector dropdown (UI only)
- [ ] Transparent overlay on hero, solid when scrolled
- [ ] Mobile hamburger menu
- [ ] Elegant typography and spacing

**Technical Notes:**
- Bootstrap navbar with custom luxury styling
- Scroll-triggered background change
- Dropdown for language (non-functional)

---

#### Feature 2: Home Page Hero with Video Background
**Issue Title:** `feat: Create immersive hero section with video/image`

**User Story:**
> As a **potential guest**, I want to **see stunning visuals of the resort immediately** so that **I'm inspired to book a stay**.

**Acceptance Criteria:**
- [ ] Full-screen hero (100vh) with background video or carousel
- [ ] Video fallback to image on mobile
- [ ] Resort name and tagline overlay
- [ ] "Explore Rooms" and "View Offers" CTA buttons
- [ ] Scroll indicator animation
- [ ] Muted autoplay for video

**Technical Notes:**
- HTML5 `<video>` with poster attribute
- CSS overlay for text readability
- Bootstrap carousel as alternative

---

#### Feature 3: Footer with Resort Information
**Issue Title:** `feat: Build comprehensive resort footer`

**User Story:**
> As a **visitor**, I want to **find contact information, quick links, and social media** so that **I can connect with the resort easily**.

**Acceptance Criteria:**
- [ ] Four columns: About, Quick Links, Contact, Newsletter
- [ ] About: Brief resort description
- [ ] Quick Links: Rooms, Dining, Spa, Activities, Events
- [ ] Contact: Address, Phone, Email, Reception hours
- [ ] Embedded mini map or map image
- [ ] Social media icons
- [ ] Awards/certifications badges
- [ ] Copyright and legal links

**Technical Notes:**
- Bootstrap grid for columns
- `<address>` for contact
- Icon set for social links

---

### Member B - Forms & Interaction

#### Feature 4: Room Booking Inquiry Form
**Issue Title:** `feat: Create detailed booking inquiry form`

**User Story:**
> As a **potential guest**, I want to **submit a booking inquiry with my preferences** so that **the resort can prepare a personalized offer**.

**Acceptance Criteria:**
- [ ] Check-in and Check-out date pickers
- [ ] Number of rooms dropdown
- [ ] Adults and Children number inputs per room
- [ ] Room type preference dropdown (Standard, Deluxe, Suite, Villa)
- [ ] Guest details: Name, Email, Phone, Country
- [ ] Special requests textarea (dietary, accessibility, celebrations)
- [ ] Promo code input (optional)
- [ ] Booking policies checkbox
- [ ] Submit inquiry button
- [ ] Estimated stay summary display

**Technical Notes:**
- Bootstrap form with date inputs
- Fieldsets for grouping
- Number inputs with min/max
- Cards for summary display

---

#### Feature 5: Contact Page with Location
**Issue Title:** `feat: Build contact page with form and directions`

**User Story:**
> As a **visitor**, I want to **contact the resort and get directions** so that **I can plan my visit or ask questions**.

**Acceptance Criteria:**
- [ ] Contact form: Name, Email, Phone, Subject dropdown, Message
- [ ] Subject options: General Inquiry, Reservations, Events, Feedback
- [ ] Large embedded Google Map
- [ ] Getting here section: From airport, By car, By ferry
- [ ] Contact cards: Reception, Reservations, Events department
- [ ] Emergency contact number
- [ ] Resort address with postal code

**Technical Notes:**
- Google Maps iframe embed
- Cards for department contacts
- Icons for transportation methods

---

#### Feature 6: Newsletter & Special Offers Signup
**Issue Title:** `feat: Add newsletter signup with offer preferences`

**User Story:**
> As a **resort**, I want to **collect guest emails and preferences** so that **we can send relevant offers and updates**.

**Acceptance Criteria:**
- [ ] Newsletter signup section on home page
- [ ] Form fields: Email, Name (optional), Interests checkboxes
- [ ] Interest options: Room Offers, Dining, Spa, Events, Family Packages
- [ ] Current offers showcase (2-3 offer cards with images)
- [ ] Each offer: Title, Description, Valid dates, "Learn More"
- [ ] Privacy policy link
- [ ] Subscribe button

**Technical Notes:**
- Bootstrap cards for offers
- Checkbox group for interests
- `<time>` for validity dates

---

### Member C - Rooms & Amenities

#### Feature 7: Room Types Listing Page
**Issue Title:** `feat: Create rooms and suites listing page`

**User Story:**
> As a **potential guest**, I want to **browse different room types and their features** so that **I can choose the best accommodation for my stay**.

**Acceptance Criteria:**
- [ ] Page header with overview text
- [ ] Filter/sort options: Room type, Capacity, View, Price range
- [ ] 4-5 room type cards
- [ ] Each card: Main image, Room name, Brief description, Key features icons
- [ ] Starting price display ("From $XXX/night")
- [ ] Capacity indicator (guests, beds)
- [ ] "View Details" and "Book Now" buttons
- [ ] Room comparison feature section

**Technical Notes:**
- Bootstrap cards with consistent sizing
- Icon set for amenities
- Filter as form elements (visual only)

---

#### Feature 8: Room Detail with Specifications
**Issue Title:** `feat: Design detailed room specification display`

**User Story:**
> As a **potential guest**, I want to **see full details about a specific room type** so that **I can make an informed booking decision**.

**Acceptance Criteria:**
- [ ] Room name and category
- [ ] Image gallery with thumbnail navigation
- [ ] Detailed description (2-3 paragraphs)
- [ ] Specifications table: Size, Bed type, Max occupancy, View
- [ ] Amenities list with icons (WiFi, AC, Mini-bar, Safe, etc.)
- [ ] Room policies (check-in/out times, cancellation)
- [ ] Pricing table (season rates: Low, High, Peak)
- [ ] "Book This Room" CTA
- [ ] Similar rooms suggestions

**Technical Notes:**
- Bootstrap tabs for gallery
- Table for specifications
- List groups for amenities
- Cards for similar rooms

---

#### Feature 9: Amenities & Facilities Page
**Issue Title:** `feat: Build amenities page showcasing all facilities`

**User Story:**
> As a **potential guest**, I want to **see all resort facilities and services** so that **I know what experiences are available during my stay**.

**Acceptance Criteria:**
- [ ] Amenity categories: Pool & Beach, Spa & Wellness, Dining, Recreation, Business
- [ ] Category navigation (tabs or in-page links)
- [ ] Each category: Header image, description, list of features
- [ ] Operating hours table for facilities
- [ ] Special services highlight (Kids club, Airport transfer, Concierge)
- [ ] "Reserve" or "Learn More" buttons per facility
- [ ] Image gallery per category

**Technical Notes:**
- Bootstrap tabs or nav pills for categories
- Tables for operating hours
- Cards or list groups for features

---

### Member D - Content & Media

#### Feature 10: Dining & Restaurants Page
**Issue Title:** `feat: Create dining page showcasing restaurants`

**User Story:**
> As a **potential guest**, I want to **explore dining options at the resort** so that **I can plan my culinary experience**.

**Acceptance Criteria:**
- [ ] Overview section about resort dining
- [ ] 3-4 restaurant profiles
- [ ] Each restaurant: Name, Cuisine type, Ambiance image, Description
- [ ] Menu highlights or sample dishes
- [ ] Operating hours and dress code
- [ ] Reservation recommendation
- [ ] Special dining experiences (Private beach dinner, Wine tasting)
- [ ] Dietary accommodations note

**Technical Notes:**
- Cards for each restaurant
- `<dl>` for restaurant details
- Modal for sample menus (optional)

---

#### Feature 11: Photo Gallery Page
**Issue Title:** `feat: Build immersive photo gallery`

**User Story:**
> As a **potential guest**, I want to **browse photos of the resort** so that **I can visualize my stay and get excited about visiting**.

**Acceptance Criteria:**
- [ ] Gallery categories: Rooms, Beach & Pool, Dining, Spa, Activities, Weddings
- [ ] Filter buttons for categories
- [ ] Masonry or grid layout
- [ ] Minimum 20 photos total
- [ ] Image thumbnails with lightbox/modal on click
- [ ] Caption for each image
- [ ] Lazy loading for performance
- [ ] Video section with resort tour video

**Technical Notes:**
- Bootstrap grid for gallery
- Modal for lightbox effect
- `loading="lazy"` attribute
- `<figure>` and `<figcaption>`

---

#### Feature 12: Experiences & Activities Section
**Issue Title:** `feat: Add experiences and activities showcase`

**User Story:**
> As a **potential guest**, I want to **discover activities and experiences offered** so that **I can plan an exciting vacation**.

**Acceptance Criteria:**
- [ ] Activity categories: Water Sports, Wellness, Excursions, Family
- [ ] Each activity card: Image, Name, Duration, Description, Pricing
- [ ] Featured experience highlight section
- [ ] Booking/inquiry button per activity
- [ ] Seasonal availability notes
- [ ] Package deals section (combining multiple activities)
- [ ] Guest testimonials about activities

**Technical Notes:**
- Bootstrap cards for activities
- Badge for pricing
- Carousel for testimonials
- Tables for package pricing

---

## Technical Requirements Checklist

### HTML Elements to Use
- [ ] Semantic structure: `header`, `nav`, `main`, `section`, `article`, `aside`, `footer`
- [ ] Forms: `form`, `fieldset`, `legend`, `label`, `input` (date, number, email, tel), `select`, `textarea`
- [ ] Tables: `table`, `thead`, `tbody`, `th`, `td`, `scope`
- [ ] Lists: `ul`, `ol`, `li`, `dl`, `dt`, `dd`
- [ ] Media: `img`, `video`, `figure`, `figcaption`, `iframe`
- [ ] Text: headings, `p`, `blockquote`, `time`, `address`

### Bootstrap Components to Use
- [ ] Navbar with transparent/solid states
- [ ] Carousel
- [ ] Cards
- [ ] Grid system
- [ ] Forms
- [ ] Tables
- [ ] Tabs/Pills
- [ ] Modal (lightbox)
- [ ] List groups
- [ ] Badges

---

## Git Issues to Create

1. `feat: Implement elegant resort navigation with booking CTA`
2. `feat: Create immersive hero section with video/image`
3. `feat: Build comprehensive resort footer`
4. `feat: Create detailed booking inquiry form`
5. `feat: Build contact page with form and directions`
6. `feat: Add newsletter signup with offer preferences`
7. `feat: Create rooms and suites listing page`
8. `feat: Design detailed room specification display`
9. `feat: Build amenities page showcasing all facilities`
10. `feat: Create dining page showcasing restaurants`
11. `feat: Build immersive photo gallery`
12. `feat: Add experiences and activities showcase`

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

# Project 5: Fitness Center Website

## Project Overview

**Project Name:** FitLife Gym & Wellness
**Industry:** Health & Fitness
**Type:** Multi-page Gym Website

### Description
Build an energetic website for "FitLife Gym & Wellness," a modern fitness center offering gym facilities, group classes, personal training, and wellness services. The website should motivate visitors to join and help members stay engaged.

### Target Audience
- Fitness enthusiasts looking for a new gym
- Beginners starting their fitness journey
- People interested in group fitness classes
- Those seeking personal training and wellness services

---

## Pages Required

| Page | Primary Owner | Description |
|------|---------------|-------------|
| Home | Member A | Landing with hero, highlights, testimonials |
| Classes | Member C | Class schedule and descriptions |
| Trainers | Member D | Personal trainer profiles |
| Membership | Member C | Membership plans and pricing |
| Facilities | Member D | Gym facilities and equipment |
| Join Now | Member B | Membership signup form |
| Contact | Member B | Contact form, locations |

---

## User Stories by Team Member

### Member A - Core Structure & Home

#### Feature 1: Dynamic Fitness Navigation
**Issue Title:** `feat: Implement energetic fitness navigation`

**User Story:**
> As a **visitor**, I want to **navigate the gym website easily** so that **I can find information about classes, memberships, and facilities**.

**Acceptance Criteria:**
- [ ] Bold logo on left
- [ ] Nav links: Classes, Trainers, Membership, Facilities, Contact
- [ ] "Join Now" prominent CTA button
- [ ] Member login link
- [ ] Mobile hamburger menu with full-screen overlay
- [ ] Sticky navigation with color transition on scroll
- [ ] Active state indicators

**Technical Notes:**
- Bootstrap navbar with custom fitness branding
- Bold typography and energetic colors
- Smooth scroll behavior

---

#### Feature 2: Motivational Hero Section
**Issue Title:** `feat: Create motivational hero with class preview`

**User Story:**
> As a **potential member**, I want to **see inspiring imagery and clear value proposition** so that **I'm motivated to learn more about joining**.

**Acceptance Criteria:**
- [ ] Full-width hero with dynamic fitness imagery
- [ ] Powerful headline ("Transform Your Life")
- [ ] Supporting text about gym benefits
- [ ] "Start Free Trial" and "View Classes" CTA buttons
- [ ] Quick stats: Members, Classes/week, Years experience
- [ ] Background image or video of gym activities
- [ ] Responsive sizing

**Technical Notes:**
- Background image with overlay
- Bootstrap buttons with energetic styling
- Counter-style statistics

---

#### Feature 3: Footer with Quick Access
**Issue Title:** `feat: Build comprehensive gym footer`

**User Story:**
> As a **visitor**, I want to **find gym hours, locations, and social links quickly** so that **I can plan my visit or follow the gym online**.

**Acceptance Criteria:**
- [ ] Four columns: Quick Links, Class Categories, Contact, Follow Us
- [ ] Operating hours prominently displayed
- [ ] Multiple location addresses (if applicable)
- [ ] Emergency contact
- [ ] App download badges (placeholder)
- [ ] Social media links with icons
- [ ] Newsletter signup
- [ ] Copyright and terms

**Technical Notes:**
- Bootstrap grid
- Icon fonts for social media
- `<address>` for locations

---

### Member B - Forms & Interaction

#### Feature 4: Membership Signup Form
**Issue Title:** `feat: Create comprehensive membership signup form`

**User Story:**
> As a **potential member**, I want to **sign up for a membership online** so that **I can start my fitness journey quickly**.

**Acceptance Criteria:**
- [ ] Plan selection (radio buttons showing selected plan summary)
- [ ] Personal info: Name, Email, Phone, Date of Birth, Gender
- [ ] Address fields: Street, City, State, ZIP
- [ ] Emergency contact: Name, Phone, Relationship
- [ ] Health questionnaire: Pre-existing conditions checkboxes
- [ ] Terms and waiver acceptance checkboxes
- [ ] Preferred start date picker
- [ ] Payment preference (Pay at gym / Set up online)
- [ ] Submit button with clear CTA

**Technical Notes:**
- Multi-section form with fieldsets
- Conditional fields based on plan selection
- Date picker for DOB and start date

---

#### Feature 5: Contact & Trial Request Page
**Issue Title:** `feat: Build contact page with free trial request`

**User Story:**
> As a **potential member**, I want to **request a free trial or ask questions** so that **I can experience the gym before committing**.

**Acceptance Criteria:**
- [ ] Free trial request form: Name, Email, Phone, Preferred location, Fitness goals
- [ ] Fitness goals checkboxes: Weight loss, Muscle gain, General fitness, Sports training
- [ ] General inquiry form: Name, Email, Subject, Message
- [ ] Location cards with address, phone, hours, map
- [ ] Multiple locations (2-3)
- [ ] FAQ accordion (5+ questions about trials and memberships)

**Technical Notes:**
- Bootstrap accordion for FAQ
- Cards for location information
- Google Maps embeds

---

#### Feature 6: Class Booking Request Form
**Issue Title:** `feat: Add class booking/interest form`

**User Story:**
> As a **member or prospect**, I want to **express interest in specific classes** so that **I can be contacted about availability**.

**Acceptance Criteria:**
- [ ] Class selection dropdown (populated with class types)
- [ ] Preferred days checkboxes (Mon-Sun)
- [ ] Preferred time slots: Morning, Afternoon, Evening
- [ ] Experience level: Beginner, Intermediate, Advanced
- [ ] Contact info: Name, Email, Phone
- [ ] Member status: Current member / Prospective member
- [ ] Special requirements textarea
- [ ] Submit interest button

**Technical Notes:**
- Multi-select or checkboxes for days
- Radio buttons for time slots
- Dropdown for class types

---

### Member C - Classes & Membership

#### Feature 7: Class Schedule Page
**Issue Title:** `feat: Create interactive class schedule page`

**User Story:**
> As a **member or visitor**, I want to **view the weekly class schedule** so that **I can plan which classes to attend**.

**Acceptance Criteria:**
- [ ] Weekly schedule table (days as columns, time slots as rows)
- [ ] Each class cell: Class name, Time, Trainer, Duration
- [ ] Color coding by class type (Cardio, Strength, Yoga, etc.)
- [ ] Filter by: Day, Class type, Trainer, Intensity level
- [ ] Legend explaining color codes
- [ ] Click on class for more details (modal or expand)
- [ ] Printable version link
- [ ] Mobile: Stack days vertically

**Technical Notes:**
- Responsive table with horizontal scroll
- Color-coded cells with CSS classes
- Bootstrap modal for class details
- Data attributes for filtering

---

#### Feature 8: Class Description Cards
**Issue Title:** `feat: Design class type cards with details`

**User Story:**
> As a **visitor**, I want to **learn about each class type** so that **I can choose classes that match my fitness goals**.

**Acceptance Criteria:**
- [ ] 8-10 class type cards
- [ ] Each card: Image, Class name, Duration, Intensity level, Description
- [ ] Benefits list (3-4 bullet points)
- [ ] Suitable for: badges (beginners, all levels, advanced)
- [ ] Required equipment note
- [ ] "View Schedule" link
- [ ] Category filtering (Cardio, Strength, Mind-Body, Dance)
- [ ] Hover effects with additional info

**Technical Notes:**
- Bootstrap cards in grid layout
- Badges for intensity and suitability
- Filter buttons above grid

---

#### Feature 9: Membership Plans Comparison
**Issue Title:** `feat: Build membership pricing page with comparison`

**User Story:**
> As a **potential member**, I want to **compare membership options and pricing** so that **I can choose the best plan for my needs**.

**Acceptance Criteria:**
- [ ] 3-4 membership tiers (Basic, Standard, Premium, VIP)
- [ ] Monthly and annual pricing options
- [ ] Each plan card: Name, Price, Key benefits list
- [ ] Feature comparison table below cards
- [ ] Features: Gym access hours, Classes included, Personal training sessions, Guest passes, etc.
- [ ] Checkmarks and X marks for feature availability
- [ ] "Most Popular" badge on recommended plan
- [ ] "Join Now" buttons linking to signup
- [ ] Corporate/Family rates section

**Technical Notes:**
- Bootstrap cards for pricing tiers
- Responsive table for comparison
- Toggle for monthly/annual pricing
- `scope` attributes for accessibility

---

### Member D - Trainers & Facilities

#### Feature 10: Personal Trainer Profiles
**Issue Title:** `feat: Create trainer profiles page`

**User Story:**
> As a **member**, I want to **learn about personal trainers and their specialties** so that **I can choose the right trainer for my goals**.

**Acceptance Criteria:**
- [ ] 4-6 trainer profile cards
- [ ] Each profile: Photo, Name, Title, Certifications, Specialties
- [ ] Specialties as badges (Weight loss, Bodybuilding, Rehabilitation, etc.)
- [ ] Brief bio paragraph
- [ ] Availability indicator (Full/Taking clients)
- [ ] "Book Session" button
- [ ] Expandable section with full bio and achievements
- [ ] Client testimonials per trainer (1-2)

**Technical Notes:**
- Bootstrap cards with consistent image dimensions
- Badge component for specialties
- Accordion or modal for expanded info
- Blockquote for testimonials

---

#### Feature 11: Facilities & Equipment Page
**Issue Title:** `feat: Build facilities showcase page`

**User Story:**
> As a **potential member**, I want to **see the gym facilities and equipment** so that **I know what's available for my workouts**.

**Acceptance Criteria:**
- [ ] Facility zones: Cardio, Free Weights, Machines, Functional Training, Studio
- [ ] Each zone: Hero image, Description, Equipment list
- [ ] Equipment highlights with images
- [ ] Amenities section: Locker rooms, Showers, Sauna, Juice bar
- [ ] Accessibility features noted
- [ ] Virtual tour section (placeholder video embed)
- [ ] Operating hours per facility zone (if different)

**Technical Notes:**
- Tabs or accordion for zones
- Image gallery for equipment
- Lists for equipment and amenities
- Video embed placeholder

---

#### Feature 12: Success Stories & Testimonials
**Issue Title:** `feat: Add member success stories section`

**User Story:**
> As a **potential member**, I want to **see real member transformation stories** so that **I'm inspired and trust the gym's effectiveness**.

**Acceptance Criteria:**
- [ ] 3-4 detailed success stories
- [ ] Each story: Before/after photos, Name, Achievement, Quote
- [ ] Transformation details: Time period, Weight lost/gained, Goals achieved
- [ ] Trainer who helped (linked to trainer profile)
- [ ] Video testimonial embed (optional)
- [ ] "Start Your Transformation" CTA
- [ ] Shorter testimonial carousel for homepage

**Technical Notes:**
- Cards for success stories
- Before/after image layout
- Blockquote for quotes
- Carousel component for homepage version

---

## Technical Requirements Checklist

### HTML Elements to Use
- [ ] Semantic structure: `header`, `nav`, `main`, `section`, `article`, `aside`, `footer`
- [ ] Forms: `form`, `fieldset`, `legend`, `label`, `input` (various types), `select`, `textarea`, `button`
- [ ] Tables: `table`, `thead`, `tbody`, `th`, `td`, `scope`, `colspan`
- [ ] Lists: `ul`, `ol`, `li`, `dl`, `dt`, `dd`
- [ ] Media: `img`, `video`, `figure`, `figcaption`
- [ ] Text: headings, `p`, `blockquote`, `time`, `strong`, `em`

### Bootstrap Components to Use
- [ ] Navbar with scroll effects
- [ ] Cards
- [ ] Grid system
- [ ] Forms and validation
- [ ] Tables (responsive)
- [ ] Badges
- [ ] Accordion
- [ ] Modal
- [ ] Carousel
- [ ] Tabs/Pills
- [ ] Progress bars (for intensity levels)

---

## Git Issues to Create

1. `feat: Implement energetic fitness navigation`
2. `feat: Create motivational hero with class preview`
3. `feat: Build comprehensive gym footer`
4. `feat: Create comprehensive membership signup form`
5. `feat: Build contact page with free trial request`
6. `feat: Add class booking/interest form`
7. `feat: Create interactive class schedule page`
8. `feat: Design class type cards with details`
9. `feat: Build membership pricing page with comparison`
10. `feat: Create trainer profiles page`
11. `feat: Build facilities showcase page`
12. `feat: Add member success stories section`

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

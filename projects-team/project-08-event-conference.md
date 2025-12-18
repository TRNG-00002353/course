# Project 8: Event & Conference Website

## Project Overview

**Project Name:** TechSummit 2025
**Industry:** Events / Conferences
**Type:** Multi-page Conference Website

### Description
Build an engaging website for "TechSummit 2025," an annual technology conference bringing together developers, designers, and tech leaders. The website should showcase speakers, schedule, and facilitate event registration.

### Target Audience
- Software developers and engineers
- Tech industry professionals
- Startup founders and entrepreneurs
- Students interested in technology careers

---

## Pages Required

| Page | Primary Owner | Description |
|------|---------------|-------------|
| Home | Member A | Landing with countdown, highlights |
| Schedule | Member C | Conference agenda and sessions |
| Speakers | Member D | Speaker profiles and topics |
| Venue | Member D | Location, travel, accommodations |
| Register | Member B | Ticket purchase and registration |
| Sponsors | Member C | Sponsor tiers and opportunities |
| Contact | Member B | Contact form, FAQ |

---

## User Stories by Team Member

### Member A - Core Structure & Home

#### Feature 1: Conference Navigation with Registration CTA
**Issue Title:** `feat: Implement conference navigation with ticket CTA`

**User Story:**
> As a **potential attendee**, I want to **easily navigate conference information and register** so that **I can learn about and sign up for the event**.

**Acceptance Criteria:**
- [ ] Conference logo/branding on left
- [ ] Nav links: Schedule, Speakers, Venue, Sponsors, Contact
- [ ] "Get Tickets" prominent CTA button
- [ ] Conference dates displayed
- [ ] Mobile hamburger menu
- [ ] Sticky navigation with background on scroll
- [ ] Active page indicator

**Technical Notes:**
- Bootstrap navbar with custom branding
- Date display in nav (visible on desktop)
- Bold CTA styling

---

#### Feature 2: Home Page Hero with Countdown
**Issue Title:** `feat: Create hero section with event countdown`

**User Story:**
> As a **visitor**, I want to **see the event highlights and countdown** so that **I'm excited about the conference and aware of registration urgency**.

**Acceptance Criteria:**
- [ ] Full-width hero with conference theme imagery
- [ ] Conference name, tagline, dates, and location
- [ ] Countdown timer (Days, Hours, Minutes, Seconds - static display)
- [ ] "Register Now" and "View Schedule" CTA buttons
- [ ] Key statistics: Speakers, Sessions, Attendees expected
- [ ] Animated elements (optional CSS animations)
- [ ] Video teaser button (links to modal or section)

**Technical Notes:**
- Background image/video with overlay
- Countdown display boxes (CSS styled)
- Bootstrap buttons
- Statistics counters

---

#### Feature 3: Footer with Conference Information
**Issue Title:** `feat: Build conference footer with essential links`

**User Story:**
> As a **visitor**, I want to **find quick links and social channels** so that **I can access information and follow updates**.

**Acceptance Criteria:**
- [ ] Four columns: Event, Resources, Connect, Newsletter
- [ ] Event: Schedule, Speakers, Venue, Sponsors
- [ ] Resources: FAQ, Code of Conduct, Accessibility, Press Kit
- [ ] Connect: Social media links, Contact, Email
- [ ] Newsletter signup for updates
- [ ] Previous conference links (TechSummit 2024, etc.)
- [ ] Mobile app download badges (placeholder)
- [ ] Copyright and organizer info

**Technical Notes:**
- Bootstrap grid
- Icon links for social
- Email validation on newsletter

---

### Member B - Registration & Forms

#### Feature 4: Ticket Registration Form
**Issue Title:** `feat: Create ticket selection and registration form`

**User Story:**
> As a **potential attendee**, I want to **select a ticket type and register** so that **I can secure my spot at the conference**.

**Acceptance Criteria:**
- [ ] Ticket tiers display: Early Bird, Regular, VIP, Student
- [ ] Each tier shows: Price, What's included list
- [ ] Select ticket quantity per type
- [ ] Attendee info per ticket: Name, Email, Job Title, Company
- [ ] Add additional attendees (button to add more)
- [ ] Dietary requirements dropdown
- [ ] Accessibility needs textarea
- [ ] Promo code input
- [ ] Order summary with total
- [ ] Payment method selection (UI only)
- [ ] Terms acceptance
- [ ] Complete registration button

**Technical Notes:**
- Bootstrap cards for ticket tiers
- Dynamic form (conceptual - HTML structure)
- Order summary card
- Form validation

---

#### Feature 5: Contact & FAQ Page
**Issue Title:** `feat: Build contact page with comprehensive FAQ`

**User Story:**
> As a **visitor**, I want to **find answers to common questions and contact organizers** so that **I can resolve concerns before registering**.

**Acceptance Criteria:**
- [ ] FAQ accordion with 10+ questions organized by category:
  - Registration & Tickets
  - Schedule & Sessions
  - Venue & Travel
  - Sponsorship
- [ ] Contact form: Name, Email, Subject dropdown, Message
- [ ] Subject options: General, Registration, Speaking, Sponsorship, Press, Other
- [ ] Organizer contact information
- [ ] Social media links
- [ ] Response time expectation
- [ ] Emergency contact for event day

**Technical Notes:**
- Bootstrap accordion for FAQ
- Category headings within accordion
- Form with select dropdown

---

#### Feature 6: Speaker Application Form
**Issue Title:** `feat: Add call for speakers application form`

**User Story:**
> As a **potential speaker**, I want to **submit a talk proposal** so that **I can be considered for a speaking slot**.

**Acceptance Criteria:**
- [ ] Speaker info: Name, Email, Phone, Company, Title, Bio
- [ ] Social links: Twitter, LinkedIn, Website
- [ ] Photo upload (UI only)
- [ ] Talk details:
  - Talk title
  - Talk format dropdown (Keynote, Session, Workshop, Panel)
  - Talk duration preference
  - Abstract textarea (300 words max)
  - Target audience level (Beginner, Intermediate, Advanced)
  - Topic category checkboxes
- [ ] Previous speaking experience textarea
- [ ] Equipment requirements
- [ ] Terms acceptance
- [ ] Submit proposal button

**Technical Notes:**
- Multi-section form with fieldsets
- Textarea with character counter (visual)
- Checkbox group for topics
- File input UI

---

### Member C - Schedule & Sponsors

#### Feature 7: Conference Schedule Page
**Issue Title:** `feat: Create multi-day conference schedule`

**User Story:**
> As an **attendee**, I want to **view the conference schedule by day and track** so that **I can plan which sessions to attend**.

**Acceptance Criteria:**
- [ ] Day tabs (Day 1, Day 2, Day 3)
- [ ] Track filters (Main Stage, Workshop, Networking)
- [ ] Time-based schedule layout
- [ ] Each session shows: Time, Title, Speaker, Track, Room
- [ ] Session type badges (Keynote, Talk, Workshop, Panel, Break)
- [ ] Click for session details (modal or expand)
- [ ] Filter by: Track, Topic, Speaker
- [ ] "Add to Calendar" button (UI only)
- [ ] Print schedule option

**Technical Notes:**
- Bootstrap tabs for days
- Table or list layout for schedule
- Modal for session details
- Badges for session types

---

#### Feature 8: Session Detail Cards
**Issue Title:** `feat: Design session cards with full details`

**User Story:**
> As an **attendee**, I want to **see detailed session information** so that **I can decide which sessions align with my interests**.

**Acceptance Criteria:**
- [ ] Session title and description
- [ ] Speaker name with photo (linked to speaker profile)
- [ ] Date, Time, Duration
- [ ] Track and Room location
- [ ] Difficulty level indicator
- [ ] Topics/tags
- [ ] Prerequisites if any
- [ ] Materials link (placeholder)
- [ ] Feedback/rating section (UI only)
- [ ] Related sessions

**Technical Notes:**
- Bootstrap cards
- Badge for difficulty
- Speaker mini-profile
- Tag badges for topics

---

#### Feature 9: Sponsors Page with Tiers
**Issue Title:** `feat: Build sponsors page with sponsorship tiers`

**User Story:**
> As a **visitor**, I want to **see conference sponsors** and as a **potential sponsor**, I want to **understand sponsorship opportunities**.

**Acceptance Criteria:**
- [ ] Sponsor tiers: Platinum, Gold, Silver, Bronze, Community Partners
- [ ] Current sponsors displayed by tier with logos
- [ ] Each sponsor: Logo, Company name, Link to website
- [ ] Larger display for higher tiers
- [ ] Sponsorship opportunities section:
  - Tier comparison table
  - Benefits per tier (booth, speaking slot, branding, tickets)
  - Pricing (or "Contact for pricing")
- [ ] "Become a Sponsor" CTA
- [ ] Sponsor contact form or link
- [ ] Sponsor testimonials

**Technical Notes:**
- Logo grid by tier
- Table for tier comparison
- Different card sizes by tier
- `scope` attributes for table

---

### Member D - Speakers & Venue

#### Feature 10: Speaker Listing Page
**Issue Title:** `feat: Create speaker profiles page`

**User Story:**
> As an **attendee**, I want to **learn about the speakers** so that **I can identify sessions of interest based on speaker expertise**.

**Acceptance Criteria:**
- [ ] Speaker cards grid (12-15 speakers)
- [ ] Each card: Photo, Name, Title, Company, Talk title
- [ ] Keynote speakers highlighted prominently
- [ ] Filter by: Track, Topic expertise
- [ ] Search speakers by name
- [ ] Social media links on cards
- [ ] "View Session" link
- [ ] Different styling for keynote vs regular speakers

**Technical Notes:**
- Bootstrap cards with consistent images
- Featured/hero section for keynotes
- Badge for keynote indicator
- Social icon links

---

#### Feature 11: Speaker Detail Page
**Issue Title:** `feat: Build detailed speaker profile page`

**User Story:**
> As an **attendee**, I want to **learn more about a specific speaker** so that **I can understand their background and expertise**.

**Acceptance Criteria:**
- [ ] Large speaker photo
- [ ] Name, Title, Company
- [ ] Full bio (multiple paragraphs)
- [ ] Social links: Twitter, LinkedIn, GitHub, Website
- [ ] Sessions they're presenting (with links)
- [ ] Topics/expertise tags
- [ ] Previous conference appearances
- [ ] Q&A form to submit questions (optional)
- [ ] Related speakers in same track

**Technical Notes:**
- Two-column layout (photo + info)
- Cards for sessions
- Badge for expertise tags
- List for previous appearances

---

#### Feature 12: Venue & Travel Page
**Issue Title:** `feat: Create venue page with travel information`

**User Story:**
> As an **attendee**, I want to **find venue details and travel information** so that **I can plan my trip to the conference**.

**Acceptance Criteria:**
- [ ] Venue overview with photos
- [ ] Venue address and embedded map
- [ ] Floor plan or venue map (image)
- [ ] Getting there: By plane, Car, Public transit
- [ ] Airport information with distance
- [ ] Hotel recommendations table:
  - Hotel name, Distance, Price range, Discount code
- [ ] Official hotel partner highlight
- [ ] Parking information
- [ ] Local attractions section
- [ ] Weather/What to pack tips
- [ ] Accessibility information

**Technical Notes:**
- Image gallery for venue
- Google Maps embed
- Table for hotels
- Cards for transportation options
- `<address>` for venue location

---

## Technical Requirements Checklist

### HTML Elements to Use
- [ ] Semantic structure: `header`, `nav`, `main`, `section`, `article`, `aside`, `footer`
- [ ] Forms: `form`, `fieldset`, `legend`, `label`, `input`, `select`, `textarea`, `button`
- [ ] Tables: `table`, `thead`, `tbody`, `th`, `td`, `scope`, `colspan`
- [ ] Lists: `ul`, `ol`, `li`, `dl`, `dt`, `dd`
- [ ] Media: `img`, `figure`, `figcaption`, `iframe`, `video` (optional)
- [ ] Text: headings, `p`, `time`, `address`

### Bootstrap Components to Use
- [ ] Navbar
- [ ] Tabs
- [ ] Cards
- [ ] Grid system
- [ ] Forms
- [ ] Tables
- [ ] Badges
- [ ] Accordion
- [ ] Modal
- [ ] Buttons

---

## Git Issues to Create

1. `feat: Implement conference navigation with ticket CTA`
2. `feat: Create hero section with event countdown`
3. `feat: Build conference footer with essential links`
4. `feat: Create ticket selection and registration form`
5. `feat: Build contact page with comprehensive FAQ`
6. `feat: Add call for speakers application form`
7. `feat: Create multi-day conference schedule`
8. `feat: Design session cards with full details`
9. `feat: Build sponsors page with sponsorship tiers`
10. `feat: Create speaker profiles page`
11. `feat: Build detailed speaker profile page`
12. `feat: Create venue page with travel information`

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

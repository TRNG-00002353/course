# Project 7: Real Estate Listing Website

## Project Overview

**Project Name:** HomeQuest Realty
**Industry:** Real Estate
**Type:** Multi-page Property Listing Website

### Description
Build a professional real estate website for "HomeQuest Realty," a real estate agency helping clients buy, sell, and rent properties. The website should showcase property listings, agent profiles, and facilitate inquiries.

### Target Audience
- Home buyers searching for properties
- Property sellers looking for agents
- Renters searching for apartments
- Real estate investors exploring opportunities

---

## Pages Required

| Page | Primary Owner | Description |
|------|---------------|-------------|
| Home | Member A | Landing with search, featured listings |
| Listings | Member C | Property search results with filters |
| Property Detail | Member C | Single property with full details |
| Agents | Member D | Agent profiles and contact |
| About | Member D | Company story, services |
| Contact | Member B | Contact form, office locations |
| List Property | Member B | Property submission form |

---

## User Stories by Team Member

### Member A - Core Structure & Home

#### Feature 1: Real Estate Navigation with Search
**Issue Title:** `feat: Implement real estate navigation with quick search`

**User Story:**
> As a **property seeker**, I want to **access property search quickly from any page** so that **I can find listings efficiently**.

**Acceptance Criteria:**
- [ ] Company logo on left
- [ ] Nav links: Buy, Rent, Sell, Agents, About, Contact
- [ ] Quick search dropdown in nav (Location, Type)
- [ ] "List Your Property" CTA button
- [ ] Agent login link
- [ ] Phone number displayed prominently
- [ ] Mobile hamburger menu
- [ ] Sticky navigation

**Technical Notes:**
- Bootstrap navbar with inline form elements
- Dropdown for property type
- Contact phone visible on desktop

---

#### Feature 2: Homepage Hero with Search Form
**Issue Title:** `feat: Create hero section with property search form`

**User Story:**
> As a **visitor**, I want to **search for properties directly from the homepage** so that **I can start my property search immediately**.

**Acceptance Criteria:**
- [ ] Full-width hero with property image background
- [ ] Search form overlay with:
  - Buy/Rent toggle tabs
  - Location input with autocomplete (UI only)
  - Property type dropdown (House, Apartment, Condo, etc.)
  - Min/Max price range selects
  - Bedrooms dropdown
  - Search button
- [ ] "Advanced Search" link
- [ ] Headline about finding dream home
- [ ] Statistics: Listings, Sold properties, Happy clients

**Technical Notes:**
- Bootstrap tabs for Buy/Rent
- Form controls with dropdowns
- Background image with overlay

---

#### Feature 3: Footer with Office Information
**Issue Title:** `feat: Build comprehensive real estate footer`

**User Story:**
> As a **visitor**, I want to **find office locations and contact details** so that **I can visit or call the agency**.

**Acceptance Criteria:**
- [ ] Four columns: Quick Links, Popular Searches, Offices, Newsletter
- [ ] Quick Links: Buy, Rent, Sell, Mortgage calculator, Resources
- [ ] Popular Searches: Popular neighborhoods/areas
- [ ] Multiple office locations with addresses and phones
- [ ] Newsletter signup for new listings alerts
- [ ] Social media links
- [ ] Real estate license numbers
- [ ] Copyright and legal (Equal Housing, MLS logos)

**Technical Notes:**
- Bootstrap grid
- `<address>` for office locations
- Image badges for certifications

---

### Member B - Forms & Inquiries

#### Feature 4: Property Inquiry Form
**Issue Title:** `feat: Create property inquiry/contact form`

**User Story:**
> As a **potential buyer/renter**, I want to **inquire about a specific property** so that **an agent can contact me with more information**.

**Acceptance Criteria:**
- [ ] Form on property detail page sidebar
- [ ] Fields: Name, Email, Phone, Message (pre-filled with property reference)
- [ ] Preferred contact method (radio: Email, Phone, Either)
- [ ] Best time to contact (checkboxes: Morning, Afternoon, Evening)
- [ ] Pre-approved for mortgage checkbox
- [ ] TCPA consent checkbox
- [ ] "Request Info" and "Schedule Tour" buttons
- [ ] Agent info displayed above form

**Technical Notes:**
- Bootstrap card for form container
- Agent profile mini-card
- Pre-filled hidden field for property ID

---

#### Feature 5: List Your Property Form
**Issue Title:** `feat: Build property listing submission form`

**User Story:**
> As a **property owner**, I want to **submit my property details for listing** so that **the agency can help me sell or rent**.

**Acceptance Criteria:**
- [ ] Multi-step form or accordion sections:
  1. Property Type & Location
  2. Property Details
  3. Features & Amenities
  4. Photos (file upload UI)
  5. Contact Information
- [ ] Property type: House, Apartment, Condo, Land, Commercial
- [ ] Listing type: Sale, Rent
- [ ] Address fields: Street, City, State, ZIP
- [ ] Details: Bedrooms, Bathrooms, Square footage, Year built, Lot size
- [ ] Features checkboxes: Pool, Garage, Garden, AC, Fireplace, etc.
- [ ] Asking price input
- [ ] Owner contact: Name, Email, Phone
- [ ] Terms acceptance
- [ ] Submit for review button

**Technical Notes:**
- Bootstrap accordion for sections
- Number inputs with appropriate ranges
- Checkbox groups for features
- File input for photos (UI only)

---

#### Feature 6: Contact & Office Locations Page
**Issue Title:** `feat: Create contact page with office locations`

**User Story:**
> As a **visitor**, I want to **contact the agency or visit an office** so that **I can get personalized assistance**.

**Acceptance Criteria:**
- [ ] General inquiry form: Name, Email, Phone, Inquiry type, Message
- [ ] Inquiry types: Buying, Selling, Renting, Mortgage, Careers, Other
- [ ] 2-3 office location cards with:
  - Office name
  - Address
  - Phone and Email
  - Operating hours
  - Embedded map
- [ ] FAQ accordion about services
- [ ] Careers section with job listings link

**Technical Notes:**
- Google Maps embeds per location
- Bootstrap cards for offices
- Accordion for FAQ

---

### Member C - Property Listings

#### Feature 7: Property Search Results Page
**Issue Title:** `feat: Build property listing page with filters`

**User Story:**
> As a **property seeker**, I want to **filter and sort property listings** so that **I can find properties matching my criteria**.

**Acceptance Criteria:**
- [ ] Filter sidebar:
  - Property type checkboxes
  - Price range (min/max inputs or slider)
  - Bedrooms (1+, 2+, 3+, 4+, 5+)
  - Bathrooms (1+, 2+, 3+)
  - Square footage range
  - Year built range
  - Features checkboxes
- [ ] Sort dropdown: Price Low-High, Price High-Low, Newest, Bedrooms
- [ ] View toggle: Grid / List
- [ ] Results count ("Showing X of Y properties")
- [ ] Save search button (UI only)
- [ ] Map view toggle (placeholder)
- [ ] Pagination

**Technical Notes:**
- Bootstrap grid for sidebar + content
- Form elements for filters
- Responsive collapse on mobile
- Toggle buttons for view

---

#### Feature 8: Property Listing Cards
**Issue Title:** `feat: Design property listing cards with key details`

**User Story:**
> As a **property seeker**, I want to **quickly scan property details from listing cards** so that **I can identify interesting properties**.

**Acceptance Criteria:**
- [ ] Property image (main photo)
- [ ] Price prominently displayed
- [ ] Property type badge (House, Apt, etc.)
- [ ] Status badge (For Sale, For Rent, Sold, Pending)
- [ ] Address/Location
- [ ] Key stats: Beds, Baths, Sq ft
- [ ] "New Listing" badge if recent
- [ ] Favorite/Save heart icon
- [ ] "View Details" button
- [ ] Grid and List view variations
- [ ] Hover effects

**Technical Notes:**
- Bootstrap cards with image top
- Badge component for status
- Icon + number for stats
- CSS for grid/list toggle

---

#### Feature 9: Property Detail Page
**Issue Title:** `feat: Create comprehensive property detail page`

**User Story:**
> As a **potential buyer/renter**, I want to **see complete property information** so that **I can decide if I want to schedule a viewing**.

**Acceptance Criteria:**
- [ ] Image gallery with thumbnails
- [ ] Price, Status, Property type header
- [ ] Address with neighborhood info
- [ ] Key facts table: Beds, Baths, Sq ft, Lot size, Year built, MLS #
- [ ] Description (2-3 paragraphs)
- [ ] Features & Amenities organized lists:
  - Interior features
  - Exterior features
  - Community amenities
- [ ] Schools nearby table
- [ ] Walk score / Transit score display
- [ ] Map with location marker
- [ ] Similar properties section (3-4 cards)
- [ ] Mortgage calculator widget (basic UI)

**Technical Notes:**
- Bootstrap carousel for gallery
- Tables for specifications
- Lists for features
- Google Maps embed

---

### Member D - Agents & Content

#### Feature 10: Agent Listing Page
**Issue Title:** `feat: Build agent directory page`

**User Story:**
> As a **visitor**, I want to **find and choose a real estate agent** so that **I can work with someone who fits my needs**.

**Acceptance Criteria:**
- [ ] Agent cards grid (6-8 agents)
- [ ] Each card: Photo, Name, Title, Specialties, Contact info
- [ ] Specialties badges (Luxury, First-time buyers, Commercial, etc.)
- [ ] Listings count per agent
- [ ] Rating/Reviews (star display)
- [ ] "View Profile" and "Contact" buttons
- [ ] Filter by: Specialty, Language, Office location
- [ ] Sort by: Name, Experience, Listings
- [ ] Search agents by name

**Technical Notes:**
- Bootstrap cards for profiles
- Badge for specialties
- Star rating display
- Filter form elements

---

#### Feature 11: Agent Profile Detail
**Issue Title:** `feat: Create detailed agent profile page`

**User Story:**
> As a **client**, I want to **learn about an agent's experience and listings** so that **I can decide if they're the right fit**.

**Acceptance Criteria:**
- [ ] Agent header: Large photo, Name, Title, Contact info
- [ ] About section with full bio
- [ ] Statistics: Years experience, Properties sold, Active listings
- [ ] Specialties and certifications list
- [ ] Languages spoken
- [ ] Current listings (property cards)
- [ ] Client testimonials (3-4)
- [ ] Contact form specific to agent
- [ ] Social media links
- [ ] Office information

**Technical Notes:**
- Bootstrap grid for layout
- Cards for listings
- Blockquote for testimonials
- Form with agent reference

---

#### Feature 12: About & Services Page
**Issue Title:** `feat: Create about page with company services`

**User Story:**
> As a **visitor**, I want to **learn about the company and their services** so that **I can trust them with my real estate needs**.

**Acceptance Criteria:**
- [ ] Company story and history
- [ ] Mission and values section
- [ ] Services offered:
  - Buying services
  - Selling services
  - Property management
  - Mortgage assistance
- [ ] Service cards with icons and descriptions
- [ ] Statistics (Years in business, Properties sold, Agents)
- [ ] Awards and recognition
- [ ] Community involvement section
- [ ] Partner logos (lenders, home warranty, etc.)

**Technical Notes:**
- Cards for services
- Icon library for service icons
- Statistics with large numbers
- Logo bar for partners

---

## Technical Requirements Checklist

### HTML Elements to Use
- [ ] Semantic structure: `header`, `nav`, `main`, `section`, `article`, `aside`, `footer`
- [ ] Forms: `form`, `fieldset`, `legend`, `label`, `input` (text, number, email, tel, range), `select`, `textarea`, `button`
- [ ] Tables: `table`, `thead`, `tbody`, `th`, `td`, `scope`
- [ ] Lists: `ul`, `ol`, `li`, `dl`, `dt`, `dd`
- [ ] Media: `img`, `figure`, `figcaption`, `iframe` (maps)
- [ ] Text: headings, `p`, `blockquote`, `address`, `time`

### Bootstrap Components to Use
- [ ] Navbar with inline form
- [ ] Cards (extensively)
- [ ] Grid system
- [ ] Forms with validation
- [ ] Tables
- [ ] Badges
- [ ] Carousel
- [ ] Tabs
- [ ] Accordion
- [ ] Pagination
- [ ] Buttons and button groups

---

## Git Issues to Create

1. `feat: Implement real estate navigation with quick search`
2. `feat: Create hero section with property search form`
3. `feat: Build comprehensive real estate footer`
4. `feat: Create property inquiry/contact form`
5. `feat: Build property listing submission form`
6. `feat: Create contact page with office locations`
7. `feat: Build property listing page with filters`
8. `feat: Design property listing cards with key details`
9. `feat: Create comprehensive property detail page`
10. `feat: Build agent directory page`
11. `feat: Create detailed agent profile page`
12. `feat: Create about page with company services`

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

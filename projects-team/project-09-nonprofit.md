# Project 9: Non-Profit Organization Website

## Project Overview

**Project Name:** Hope Foundation
**Industry:** Non-Profit / Charity
**Type:** Multi-page Charity Website

### Description
Build an impactful website for "Hope Foundation," a non-profit organization focused on education and community development. The website should inspire donations, showcase programs, and recruit volunteers.

### Target Audience
- Potential donors looking to support causes
- Volunteers seeking opportunities
- Community members needing program services
- Corporate sponsors and partners

---

## Pages Required

| Page | Primary Owner | Description |
|------|---------------|-------------|
| Home | Member A | Landing with mission, impact, appeals |
| About | Member D | Organization story, team, history |
| Programs | Member C | Programs and initiatives |
| Get Involved | Member B | Volunteer and donate options |
| Donate | Member B | Donation form and options |
| Events | Member C | Upcoming events and campaigns |
| Contact | Member B | Contact form, locations |
| Stories | Member D | Impact stories and testimonials |

---

## User Stories by Team Member

### Member A - Core Structure & Home

#### Feature 1: Non-Profit Navigation with Donate CTA
**Issue Title:** `feat: Implement charity navigation with donate button`

**User Story:**
> As a **visitor**, I want to **easily find information about the charity and donate** so that **I can learn about their work and contribute**.

**Acceptance Criteria:**
- [ ] Organization logo on left
- [ ] Nav links: About, Programs, Get Involved, Events, Stories, Contact
- [ ] Prominent "Donate" button (red/orange color)
- [ ] Search icon (optional)
- [ ] Mobile hamburger menu
- [ ] Sticky navigation
- [ ] Emergency appeal banner (optional, above nav)

**Technical Notes:**
- Bootstrap navbar with highlighted CTA
- Alert banner for urgent appeals
- Mobile-friendly

---

#### Feature 2: Home Page Hero with Impact Message
**Issue Title:** `feat: Create emotional hero section with donation appeal`

**User Story:**
> As a **visitor**, I want to **immediately understand the organization's mission and impact** so that **I'm inspired to get involved**.

**Acceptance Criteria:**
- [ ] Full-width hero with impactful imagery (people helped)
- [ ] Mission statement headline
- [ ] Brief description of work
- [ ] "Donate Now" and "Learn More" CTA buttons
- [ ] Impact statistics: People helped, Communities served, Years of service
- [ ] Current campaign highlight (if applicable)
- [ ] Scroll down indicator

**Technical Notes:**
- Background image with overlay
- Counter-style statistics
- Bootstrap buttons with urgency styling

---

#### Feature 3: Footer with Quick Donate and Links
**Issue Title:** `feat: Build comprehensive charity footer`

**User Story:**
> As a **visitor**, I want to **find quick links and ways to connect** so that **I can easily donate or learn more**.

**Acceptance Criteria:**
- [ ] Four columns: Quick Links, Programs, Contact, Stay Connected
- [ ] Quick Links: Donate, Volunteer, Programs, Events, Annual Report
- [ ] Programs: List main program areas
- [ ] Contact: Address, Phone, Email
- [ ] Newsletter signup
- [ ] Social media links
- [ ] Tax ID/Registration numbers
- [ ] Charity ratings badges (placeholder)
- [ ] Copyright and privacy policy

**Technical Notes:**
- Bootstrap grid
- `<address>` for contact
- Badge images for ratings

---

### Member B - Donations & Engagement

#### Feature 4: Donation Form Page
**Issue Title:** `feat: Create comprehensive donation form`

**User Story:**
> As a **donor**, I want to **make a donation easily** so that **I can support the organization's work**.

**Acceptance Criteria:**
- [ ] Donation amount options: $25, $50, $100, $250, Custom amount
- [ ] Donation frequency: One-time, Monthly, Quarterly, Annually
- [ ] Designation dropdown: Where Most Needed, Education, Healthcare, etc.
- [ ] In honor/memory of option with name field
- [ ] Donor info: Name, Email, Phone, Address
- [ ] Anonymous donation checkbox
- [ ] Payment method selection (Credit card, PayPal, Bank - UI only)
- [ ] Matching gift employer field
- [ ] Newsletter opt-in checkbox
- [ ] Secure donation trust badges
- [ ] Impact statement for each amount level

**Technical Notes:**
- Radio buttons for amounts
- Conditional fields for tributes
- Card layout for amount selection
- Form validation

---

#### Feature 5: Volunteer Signup Form
**Issue Title:** `feat: Build volunteer application form`

**User Story:**
> As a **potential volunteer**, I want to **sign up to volunteer** so that **I can contribute my time to the cause**.

**Acceptance Criteria:**
- [ ] Personal info: Name, Email, Phone, Address
- [ ] Availability checkboxes: Weekdays, Weekends, Evenings
- [ ] Hours per week/month dropdown
- [ ] Skills/expertise checkboxes: Teaching, Admin, Events, Fundraising, etc.
- [ ] Program interest checkboxes
- [ ] Languages spoken checkboxes
- [ ] Previous volunteer experience textarea
- [ ] Emergency contact info
- [ ] Background check consent checkbox
- [ ] Why volunteer? textarea
- [ ] Submit application button

**Technical Notes:**
- Multi-section form with fieldsets
- Checkbox groups
- Textarea for open responses

---

#### Feature 6: Contact & Support Page
**Issue Title:** `feat: Create contact page with multiple inquiry types`

**User Story:**
> As a **visitor**, I want to **contact the organization for various reasons** so that **I can get information or support**.

**Acceptance Criteria:**
- [ ] General contact form: Name, Email, Inquiry type, Message
- [ ] Inquiry types: General, Donations, Volunteering, Programs, Media, Partnerships
- [ ] Office locations with addresses and maps (2-3 locations)
- [ ] Department contacts: Donations, Volunteer coordinator, Media
- [ ] Office hours
- [ ] FAQ accordion (5+ questions)
- [ ] Request program services form/link
- [ ] Report concerns/feedback option

**Technical Notes:**
- Bootstrap cards for locations
- Maps embeds
- Accordion for FAQ
- Multiple form types

---

### Member C - Programs & Events

#### Feature 7: Programs Overview Page
**Issue Title:** `feat: Create programs page showcasing initiatives`

**User Story:**
> As a **visitor**, I want to **learn about the organization's programs** so that **I can understand where my donation goes**.

**Acceptance Criteria:**
- [ ] Programs overview introduction
- [ ] 4-5 program cards
- [ ] Each program: Image, Name, Description, Impact metrics
- [ ] Program areas: Education, Healthcare, Community Development, etc.
- [ ] Beneficiary statistics per program
- [ ] "Learn More" link to detailed program info
- [ ] "Support This Program" donate link
- [ ] Success metrics visualization (numbers/charts)

**Technical Notes:**
- Bootstrap cards in grid
- Progress bars for goals
- Statistics display
- Badge for program type

---

#### Feature 8: Program Detail Sections
**Issue Title:** `feat: Design detailed program information sections`

**User Story:**
> As a **donor**, I want to **see detailed information about specific programs** so that **I can direct my donation appropriately**.

**Acceptance Criteria:**
- [ ] Program hero with image
- [ ] Problem statement section
- [ ] Our approach/solution
- [ ] Activities and services list
- [ ] Impact metrics table
- [ ] Beneficiary stories/quotes
- [ ] Photos/gallery from program
- [ ] How to help section
- [ ] Related programs
- [ ] Download program fact sheet (link placeholder)

**Technical Notes:**
- Semantic sections
- Blockquote for stories
- Table for metrics
- Figure for images

---

#### Feature 9: Events & Campaigns Page
**Issue Title:** `feat: Build events listing and campaign page`

**User Story:**
> As a **supporter**, I want to **see upcoming events and campaigns** so that **I can participate and support fundraising efforts**.

**Acceptance Criteria:**
- [ ] Featured campaign section with goal thermometer
- [ ] Campaign: Title, Goal amount, Current raised, Days left, Description
- [ ] Upcoming events listing (5-6 events)
- [ ] Each event: Date, Title, Location, Description, Register button
- [ ] Event types: Fundraiser, Volunteer day, Awareness, Gala
- [ ] Calendar view or list view toggle
- [ ] Past events archive link
- [ ] Create your own fundraiser CTA
- [ ] Filter by event type

**Technical Notes:**
- Progress bar for thermometer
- Cards for events
- `<time>` element for dates
- Badge for event type

---

### Member D - Stories & About

#### Feature 10: About Page with Organization Story
**Issue Title:** `feat: Create about page with history and mission`

**User Story:**
> As a **visitor**, I want to **learn about the organization's history and mission** so that **I can trust them with my donation**.

**Acceptance Criteria:**
- [ ] Founding story narrative
- [ ] Mission, Vision, Values section
- [ ] Timeline of milestones
- [ ] Impact over the years summary
- [ ] Financials transparency section (pie chart visual of fund allocation)
- [ ] Annual reports links
- [ ] Accreditations and ratings
- [ ] Partners and supporters logos

**Technical Notes:**
- Timeline using list or custom CSS
- Cards for mission/vision/values
- Pie chart as image or CSS
- Logo bar for partners

---

#### Feature 11: Leadership & Team Page
**Issue Title:** `feat: Add leadership team and board profiles`

**User Story:**
> As a **donor**, I want to **see who leads the organization** so that **I can trust the organization's governance**.

**Acceptance Criteria:**
- [ ] Executive team section (CEO, Directors)
- [ ] Board of Directors listing
- [ ] Each profile: Photo, Name, Title, Brief bio
- [ ] LinkedIn links (where appropriate)
- [ ] Expandable bios
- [ ] Staff section or "Our Team" group photo
- [ ] Advisory council (if applicable)
- [ ] Join our team CTA with careers link

**Technical Notes:**
- Bootstrap cards for profiles
- Modal or accordion for full bios
- Group photo figure

---

#### Feature 12: Impact Stories Page
**Issue Title:** `feat: Build impact stories and testimonials page`

**User Story:**
> As a **visitor**, I want to **read stories of people helped** so that **I can see the real impact of donations**.

**Acceptance Criteria:**
- [ ] Featured story hero (main story with large image)
- [ ] Story cards grid (6-8 stories)
- [ ] Each story: Photo, Name, Location, Story excerpt, "Read More"
- [ ] Story detail view: Full narrative, Multiple photos, Quote highlight
- [ ] Video testimonials section (embed placeholders)
- [ ] Filter by program/region
- [ ] "Share Your Story" form/CTA
- [ ] Related stories at bottom

**Technical Notes:**
- Cards for story previews
- Figure/figcaption for story images
- Blockquote for quotes
- Video embed (YouTube/placeholder)

---

## Technical Requirements Checklist

### HTML Elements to Use
- [ ] Semantic structure: `header`, `nav`, `main`, `section`, `article`, `aside`, `footer`
- [ ] Forms: `form`, `fieldset`, `legend`, `label`, `input` (text, email, tel, number, checkbox, radio), `select`, `textarea`, `button`
- [ ] Tables: `table`, `thead`, `tbody`, `th`, `td`, `scope`
- [ ] Lists: `ul`, `ol`, `li`, `dl`, `dt`, `dd`
- [ ] Media: `img`, `figure`, `figcaption`, `video`, `iframe`
- [ ] Text: headings, `p`, `blockquote`, `time`, `address`, `strong`, `em`

### Bootstrap Components to Use
- [ ] Navbar with alert banner
- [ ] Cards
- [ ] Grid system
- [ ] Forms
- [ ] Progress bars
- [ ] Badges
- [ ] Accordion
- [ ] Modal
- [ ] Carousel (stories)
- [ ] Buttons

---

## Git Issues to Create

1. `feat: Implement charity navigation with donate button`
2. `feat: Create emotional hero section with donation appeal`
3. `feat: Build comprehensive charity footer`
4. `feat: Create comprehensive donation form`
5. `feat: Build volunteer application form`
6. `feat: Create contact page with multiple inquiry types`
7. `feat: Create programs page showcasing initiatives`
8. `feat: Design detailed program information sections`
9. `feat: Build events listing and campaign page`
10. `feat: Create about page with history and mission`
11. `feat: Add leadership team and board profiles`
12. `feat: Build impact stories and testimonials page`

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

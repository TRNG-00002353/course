# Project 3: Tech Startup Landing (3-Member Version)

## Project Overview

**Project Name:** CloudSync Pro
**Industry:** SaaS / Technology
**Type:** Multi-page Product Landing Website

### Description
Build a professional landing website for "CloudSync Pro," a cloud-based project management and collaboration platform. The website should showcase features, pricing plans, and convert visitors into trial users.

### Target Audience
- Business decision-makers evaluating project management tools
- Team leads looking for collaboration solutions
- Startups and enterprises seeking productivity software

---

## Pages Required

| Page | Primary Owner | Description |
|------|---------------|-------------|
| Home | Member A | Landing with hero, features overview, social proof |
| Features | Member C | Detailed feature breakdown with benefits |
| Pricing | Member C | Pricing tiers comparison table |
| About | Member C | Company story, team, values |
| Contact | Member B | Contact form, demo request |
| Sign Up | Member B | Registration form |

---

## User Stories by Team Member

### Member A - Core Structure & Home

#### Feature 1: SaaS Navigation with CTA
**Issue Title:** `feat: Implement SaaS-style navigation with sign-up CTA`

**User Story:**
> As a **visitor**, I want to **easily navigate the site and find the sign-up option** so that **I can explore and try the product quickly**.

**Acceptance Criteria:**
- [ ] Logo on the left
- [ ] Nav links: Features, Pricing, About, Contact
- [ ] "Login" text link
- [ ] "Start Free Trial" CTA button (highlighted)
- [ ] Transparent navbar on hero, solid on scroll
- [ ] Mobile hamburger menu
- [ ] Sticky navigation

**Technical Notes:**
- Bootstrap navbar with scroll effect (CSS/JS)
- Button variants for CTA emphasis
- `navbar-light` and `navbar-dark` based on scroll position

---

#### Feature 2: Hero Section with Product Demo
**Issue Title:** `feat: Create compelling hero with product showcase`

**User Story:**
> As a **potential customer**, I want to **immediately understand what the product does and see it in action** so that **I can decide if it's relevant to my needs**.

**Acceptance Criteria:**
- [ ] Bold headline communicating value proposition
- [ ] Supporting subheadline (2 sentences max)
- [ ] Two CTA buttons: "Start Free Trial" and "Watch Demo"
- [ ] Product screenshot/mockup image or video embed
- [ ] Trust badges (e.g., "Trusted by 10,000+ teams")
- [ ] Minimum 80vh height
- [ ] Animated entrance (optional CSS animations)

**Technical Notes:**
- Bootstrap grid for text + image layout
- Responsive image sizing
- Consider Bootstrap ratio for video embed

---

#### Feature 3: Social Proof & Footer
**Issue Title:** `feat: Add social proof section and comprehensive footer`

**User Story:**
> As a **potential customer**, I want to **see that other companies trust this product** so that **I feel confident in my decision**.

**Acceptance Criteria:**
- [ ] Client logo bar (6-8 company logos in grayscale)
- [ ] Testimonial cards (3 testimonials with photo, name, role, company, quote)
- [ ] Statistics section (Users, Countries, Uptime %)
- [ ] Footer: Product, Company, Resources, Legal columns
- [ ] Newsletter signup
- [ ] Social media links
- [ ] Copyright and legal links

**Technical Notes:**
- Logo bar with flexbox or grid
- Carousel for testimonials on mobile (optional)
- Cards for testimonial display

---

### Member B - Forms & Communication

#### Feature 4: Free Trial Sign Up Form
**Issue Title:** `feat: Create free trial registration form`

**User Story:**
> As a **potential user**, I want to **quickly sign up for a free trial** so that **I can start using the product**.

**Acceptance Criteria:**
- [ ] Full name input
- [ ] Work email input (with company email validation hint)
- [ ] Password with strength indicator (visual only)
- [ ] Company name input
- [ ] Company size dropdown (1-10, 11-50, 51-200, 200+)
- [ ] Terms acceptance checkbox
- [ ] "Start Free Trial" submit button
- [ ] Google/Microsoft sign-up options (buttons, non-functional)
- [ ] "Already have an account? Login" link

**Technical Notes:**
- Bootstrap form with validation states
- Progress bar or badges for password strength
- Social login buttons with icons

---

#### Feature 5: Contact & Demo Request Page
**Issue Title:** `feat: Build contact page with demo request form`

**User Story:**
> As a **business decision-maker**, I want to **request a personalized demo** so that **I can see how the product fits my specific needs**.

**Acceptance Criteria:**
- [ ] Demo request form: Name, Email, Phone, Company, Job Title, Company Size, Message
- [ ] "How did you hear about us?" dropdown
- [ ] Preferred demo time (select with time slots)
- [ ] General contact form (Name, Email, Subject, Message)
- [ ] Contact information card (email, phone)
- [ ] Office locations with addresses
- [ ] FAQ accordion (5 common questions)

**Technical Notes:**
- Two-column layout: Form + Contact info
- Bootstrap accordion for FAQ
- `<address>` for locations

---

#### Feature 6: Newsletter & Lead Capture
**Issue Title:** `feat: Add newsletter signup and lead capture forms`

**User Story:**
> As a **marketing team**, I want to **capture visitor emails at multiple touchpoints** so that **we can nurture leads**.

**Acceptance Criteria:**
- [ ] Homepage inline newsletter signup
- [ ] Exit-intent popup signup (CSS-based, shows on page)
- [ ] Resource download form (Name, Email, Company for whitepaper)
- [ ] Email validation on all forms
- [ ] GDPR consent checkbox
- [ ] Success message styling

**Technical Notes:**
- Bootstrap modal for popup
- Input groups for inline forms
- Alert components for success messages

---

### Member C - Features, Pricing & About

#### Feature 7: Features Page with Categorized Capabilities
**Issue Title:** `feat: Create features page with categorized capabilities`

**User Story:**
> As a **potential customer**, I want to **understand all the product features in detail** so that **I can evaluate if it meets my requirements**.

**Acceptance Criteria:**
- [ ] Hero section with features overview
- [ ] Feature categories: Project Management, Collaboration, Reporting, Integrations
- [ ] Category navigation (in-page links)
- [ ] Each category: Icon, title, description, 3-4 sub-features
- [ ] 8-12 feature cards with icon, title, short description
- [ ] Hover effect showing expanded details
- [ ] Grid layout: 4 columns desktop, 2 tablet, 1 mobile
- [ ] "See it in action" CTA with each category

**Technical Notes:**
- Bootstrap grid for layouts
- Icon library for feature icons
- Cards for features with CSS transitions for hover

---

#### Feature 8: Pricing Table with Plan Comparison
**Issue Title:** `feat: Build pricing page with tier comparison`

**User Story:**
> As a **potential customer**, I want to **compare pricing plans and features** so that **I can choose the right plan for my team**.

**Acceptance Criteria:**
- [ ] 3-4 pricing tiers (Free, Starter, Professional, Enterprise)
- [ ] Monthly/Annual toggle (with discount percentage shown)
- [ ] Each plan: Name, Price, Description, Feature list, CTA button
- [ ] Recommended/Popular plan highlighted
- [ ] Feature comparison table below plans
- [ ] Checkmarks and X marks for feature availability
- [ ] Enterprise "Contact Sales" instead of price
- [ ] FAQ section about billing

**Technical Notes:**
- Bootstrap cards for pricing tiers
- Table with sticky first column for comparison
- Radio or switch for billing toggle
- `scope` attributes for table accessibility

---

#### Feature 9: About Page with Company Story & Team
**Issue Title:** `feat: Create about page with company narrative and team`

**User Story:**
> As a **visitor**, I want to **learn about the company and team behind the product** so that **I can trust them with my business**.

**Acceptance Criteria:**
- [ ] Hero with company mission statement
- [ ] "Our Story" narrative (founding, milestones)
- [ ] Mission, Vision, Values section (3 cards)
- [ ] Company statistics (employees, customers, countries)
- [ ] Leadership team section (4-6 team member cards)
- [ ] Each team card: Photo, Name, Title, Short bio
- [ ] LinkedIn/Twitter links for team members
- [ ] Investor/partner logos
- [ ] "Join Our Team" CTA with careers link

**Technical Notes:**
- Cards for values and team profiles
- Logo bar for investors/partners
- Social media icon links
- Consistent image dimensions (circular or square)

---

## Technical Requirements Checklist

### HTML Elements to Use
- [ ] Semantic structure: `header`, `nav`, `main`, `section`, `article`, `aside`, `footer`
- [ ] Forms: `form`, `fieldset`, `legend`, `label`, `input`, `select`, `textarea`, `button`
- [ ] Tables: `table`, `thead`, `tbody`, `th`, `td`, `scope`
- [ ] Lists: `ul`, `ol`, `li`, `dl`, `dt`, `dd`
- [ ] Media: `img`, `figure`, `figcaption`, `iframe` (for video embed)
- [ ] Text: proper heading hierarchy, `p`, `blockquote`, `time`, `address`
- [ ] Interactive: Bootstrap modal for popups

### Bootstrap Components to Use
- [ ] Navbar with scroll effects
- [ ] Cards
- [ ] Grid system
- [ ] Forms with validation states
- [ ] Tables
- [ ] Badges
- [ ] Accordion
- [ ] Modal
- [ ] Carousel (testimonials)
- [ ] Progress bars

### Responsive Breakpoints
- [ ] Mobile: < 576px
- [ ] Tablet: 576px - 991px
- [ ] Desktop: 992px+

---

## Git Issues to Create

Create these issues in your repository before starting:

1. `feat: Implement SaaS-style navigation with sign-up CTA`
2. `feat: Create compelling hero with product showcase`
3. `feat: Add social proof section and comprehensive footer`
4. `feat: Create free trial registration form`
5. `feat: Build contact page with demo request form`
6. `feat: Add newsletter signup and lead capture forms`
7. `feat: Create features page with categorized capabilities`
8. `feat: Build pricing page with tier comparison`
9. `feat: Create about page with company narrative and team`

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

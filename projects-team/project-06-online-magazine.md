# Project 6: Online Magazine

## Project Overview

**Project Name:** Pulse Digital Magazine
**Industry:** Media / Publishing
**Type:** Multi-page News & Magazine Website

### Description
Build a modern online magazine website for "Pulse Digital Magazine," covering technology, lifestyle, and culture. The website should present articles attractively, allow category browsing, and engage readers with newsletter subscriptions.

### Target Audience
- News and culture enthusiasts
- Tech-savvy readers
- Professionals seeking industry insights
- Casual browsers looking for engaging content

---

## Pages Required

| Page | Primary Owner | Description |
|------|---------------|-------------|
| Home | Member A | Landing with featured articles, latest news |
| Category | Member C | Category-filtered article listing |
| Article | Member D | Single article page template |
| About | Member D | Magazine story, editorial team |
| Subscribe | Member B | Subscription plans and signup |
| Contact | Member B | Contact form, editorial inquiries |
| Authors | Member C | Writer profiles and their articles |

---

## User Stories by Team Member

### Member A - Core Structure & Home

#### Feature 1: Magazine Navigation with Search
**Issue Title:** `feat: Implement magazine navigation with categories and search`

**User Story:**
> As a **reader**, I want to **navigate through different content categories** so that **I can find articles that interest me**.

**Acceptance Criteria:**
- [ ] Magazine logo/masthead on left
- [ ] Category dropdown: Technology, Lifestyle, Business, Culture, Opinion
- [ ] Search bar with icon
- [ ] "Subscribe" CTA button
- [ ] Social media follow icons
- [ ] Breaking news ticker below nav (optional)
- [ ] Mobile hamburger menu
- [ ] Sticky navigation

**Technical Notes:**
- Bootstrap navbar with mega menu or dropdown
- Input group for search
- Marquee or animated ticker (CSS)

---

#### Feature 2: Homepage Featured Content Layout
**Issue Title:** `feat: Create dynamic homepage with featured articles`

**User Story:**
> As a **reader**, I want to **see featured and trending articles prominently** so that **I can quickly access the best content**.

**Acceptance Criteria:**
- [ ] Hero section with main featured article (large image, title, excerpt)
- [ ] Secondary featured articles grid (2-3 articles)
- [ ] "Latest News" section with 4-6 article cards
- [ ] "Trending Now" sidebar widget
- [ ] Category quick links section
- [ ] Each article card: Image, Category badge, Title, Excerpt, Author, Date
- [ ] Responsive grid layout

**Technical Notes:**
- Bootstrap grid for magazine layout
- Cards for article previews
- Badge for category labels

---

#### Feature 3: Footer with Quick Access
**Issue Title:** `feat: Build magazine footer with newsletter signup`

**User Story:**
> As a **reader**, I want to **subscribe to the newsletter and access important links** so that **I stay connected with the magazine**.

**Acceptance Criteria:**
- [ ] Four columns: Categories, Company, Connect, Newsletter
- [ ] Categories: All main content categories
- [ ] Company: About, Careers, Advertise, Press
- [ ] Connect: Contact, Social links, RSS feed
- [ ] Newsletter signup form with email validation
- [ ] Recent posts widget (3 recent articles)
- [ ] Copyright and legal links
- [ ] Back to top button

**Technical Notes:**
- Bootstrap grid for columns
- List groups for links
- Email validation on form

---

### Member B - Forms & Subscription

#### Feature 4: Newsletter & Subscription Forms
**Issue Title:** `feat: Create subscription and newsletter signup forms`

**User Story:**
> As a **magazine**, I want to **convert readers into subscribers** so that **we can build a loyal audience**.

**Acceptance Criteria:**
- [ ] Free newsletter signup: Email only
- [ ] Premium subscription form: Name, Email, Password
- [ ] Subscription plans display (Free, Premium, Annual)
- [ ] Plan features comparison
- [ ] Content preference checkboxes (categories of interest)
- [ ] Frequency preference (Daily digest, Weekly, Breaking news only)
- [ ] Terms acceptance checkbox
- [ ] Social signup options (Google, Apple)

**Technical Notes:**
- Bootstrap cards for plan comparison
- Checkbox groups for preferences
- Radio buttons for frequency

---

#### Feature 5: Contact & Editorial Page
**Issue Title:** `feat: Build contact page with editorial inquiry form`

**User Story:**
> As a **reader or contributor**, I want to **contact the editorial team** so that **I can submit tips, feedback, or pitches**.

**Acceptance Criteria:**
- [ ] General contact form: Name, Email, Subject dropdown, Message
- [ ] Subject options: General Inquiry, News Tip, Pitch Story, Advertising, Corrections
- [ ] Editorial contact cards (Editor-in-Chief, Section Editors)
- [ ] "Write for Us" section with submission guidelines
- [ ] Contributor application form: Name, Email, Portfolio URL, Bio, Sample pitch
- [ ] Response time expectation
- [ ] Social media contact alternatives

**Technical Notes:**
- Two-column layout
- Cards for editorial contacts
- URL input validation for portfolio

---

#### Feature 6: User Preferences & Settings
**Issue Title:** `feat: Add reader preferences form section`

**User Story:**
> As a **subscriber**, I want to **customize my reading preferences** so that **I receive relevant content recommendations**.

**Acceptance Criteria:**
- [ ] Reading interests selection (category checkboxes)
- [ ] Email notification preferences (toggle switches or checkboxes)
- [ ] Reading history privacy setting
- [ ] Dark/Light mode toggle (CSS-based)
- [ ] Font size preference (slider or buttons)
- [ ] Save preferences button
- [ ] Reset to defaults option
- [ ] Profile update form (Name, Bio, Avatar upload UI)

**Technical Notes:**
- Bootstrap form switches for toggles
- Range input for font size
- CSS custom properties for theming

---

### Member C - Content Listings

#### Feature 7: Category Page with Article Grid
**Issue Title:** `feat: Create category page with filtered article listing`

**User Story:**
> As a **reader**, I want to **browse articles by category** so that **I can find content in my areas of interest**.

**Acceptance Criteria:**
- [ ] Category header with name and description
- [ ] Featured article for category (large hero card)
- [ ] Article grid (6-9 articles)
- [ ] Sidebar with: Popular in category, Related categories
- [ ] Sort options: Latest, Popular, Editor's picks
- [ ] Tag filter chips
- [ ] Pagination (numbered pages)
- [ ] "Load More" button alternative
- [ ] Article count display

**Technical Notes:**
- Bootstrap grid for article cards
- Sidebar using col-* classes
- Pagination component
- Badge for tags

---

#### Feature 8: Article Cards with Metadata
**Issue Title:** `feat: Design article cards with complete metadata`

**User Story:**
> As a **reader**, I want to **quickly scan article previews** so that **I can decide which articles to read**.

**Acceptance Criteria:**
- [ ] Article image (consistent aspect ratio)
- [ ] Category badge
- [ ] Article title (linked)
- [ ] Excerpt (2-3 lines with truncation)
- [ ] Author name with avatar
- [ ] Publication date
- [ ] Read time estimate
- [ ] Bookmark/save icon
- [ ] Share icon
- [ ] Hover effects

**Technical Notes:**
- Bootstrap cards with custom styling
- Text truncation CSS
- Icon buttons for actions
- `<time>` element for dates

---

#### Feature 9: Author Listing Page
**Issue Title:** `feat: Build author directory page`

**User Story:**
> As a **reader**, I want to **discover writers and see their work** so that **I can follow my favorite authors**.

**Acceptance Criteria:**
- [ ] Author cards grid (8-10 authors)
- [ ] Each card: Photo, Name, Title/Role, Bio snippet, Article count
- [ ] Social media links per author
- [ ] "View Articles" button
- [ ] Sort: Name, Most articles, Newest
- [ ] Search/filter authors by name
- [ ] Staff vs Contributor distinction
- [ ] Author detail expansion (modal or inline)
- [ ] Recent articles by author

**Technical Notes:**
- Bootstrap cards for profiles
- Modal for expanded profile
- Badge for staff/contributor
- List group for recent articles

---

### Member D - Article & Content

#### Feature 10: Single Article Page
**Issue Title:** `feat: Create comprehensive article page template`

**User Story:**
> As a **reader**, I want to **read articles in a clean, distraction-free layout** so that **I can focus on the content**.

**Acceptance Criteria:**
- [ ] Breadcrumb navigation (Home > Category > Article)
- [ ] Article header: Title, Subtitle, Author, Date, Read time
- [ ] Featured image with caption
- [ ] Article body with rich formatting (headings, paragraphs, quotes, lists)
- [ ] Pull quotes styled distinctively
- [ ] Image gallery or embedded media support
- [ ] Author bio box at end
- [ ] Tags section
- [ ] Social share buttons (floating or fixed)
- [ ] "Related Articles" section (3-4 cards)

**Technical Notes:**
- Semantic `<article>` element
- `<blockquote>` for pull quotes
- `<figure>` and `<figcaption>` for images
- Responsive typography

---

#### Feature 11: Article Engagement Section
**Issue Title:** `feat: Add article comments and engagement features`

**User Story:**
> As a **reader**, I want to **engage with articles through comments and reactions** so that **I can participate in discussions**.

**Acceptance Criteria:**
- [ ] Reaction buttons (Like, Love, Insightful, etc.) with counts
- [ ] Comments section
- [ ] Comment form: Name, Email, Comment textarea
- [ ] Display 5-6 sample comments
- [ ] Each comment: Author, Date, Comment text, Reply button
- [ ] Nested replies (1 level)
- [ ] "Load More Comments" button
- [ ] Comment count display
- [ ] Report/Flag comment option (UI only)

**Technical Notes:**
- Button group for reactions
- Cards or list items for comments
- Nested list for replies
- Form validation

---

#### Feature 12: About & Editorial Team Page
**Issue Title:** `feat: Create about page with editorial team profiles`

**User Story:**
> As a **reader**, I want to **learn about the magazine and its team** so that **I can trust the source and understand their perspective**.

**Acceptance Criteria:**
- [ ] Magazine mission statement and history
- [ ] Editorial values/standards section
- [ ] Awards and recognition
- [ ] Editorial team hierarchy: Editor-in-Chief, Editors, Senior Writers
- [ ] Team member cards with photo, name, role, bio
- [ ] Contact information per team member
- [ ] Join our team CTA
- [ ] Timeline of magazine milestones (optional)

**Technical Notes:**
- Cards for team members
- Semantic headings for hierarchy
- `<address>` for contact info
- Timeline with CSS or list

---

## Technical Requirements Checklist

### HTML Elements to Use
- [ ] Semantic structure: `header`, `nav`, `main`, `section`, `article`, `aside`, `footer`
- [ ] Forms: `form`, `fieldset`, `legend`, `label`, `input`, `select`, `textarea`, `button`
- [ ] Lists: `ul`, `ol`, `li`, `dl`, `dt`, `dd`
- [ ] Media: `img`, `figure`, `figcaption`, `video` (optional)
- [ ] Text: headings (h1-h6), `p`, `blockquote`, `cite`, `time`, `address`
- [ ] Navigation: `nav`, breadcrumbs

### Bootstrap Components to Use
- [ ] Navbar with dropdown
- [ ] Cards (extensively)
- [ ] Grid system
- [ ] Forms
- [ ] Badges
- [ ] Pagination
- [ ] Modal
- [ ] Breadcrumbs
- [ ] List groups
- [ ] Buttons and button groups
- [ ] Alerts

---

## Git Issues to Create

1. `feat: Implement magazine navigation with categories and search`
2. `feat: Create dynamic homepage with featured articles`
3. `feat: Build magazine footer with newsletter signup`
4. `feat: Create subscription and newsletter signup forms`
5. `feat: Build contact page with editorial inquiry form`
6. `feat: Add reader preferences form section`
7. `feat: Create category page with filtered article listing`
8. `feat: Design article cards with complete metadata`
9. `feat: Build author directory page`
10. `feat: Create comprehensive article page template`
11. `feat: Add article comments and engagement features`
12. `feat: Create about page with editorial team profiles`

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

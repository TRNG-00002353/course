# Project 10: Educational Portal Website

## Project Overview

**Project Name:** Horizon Academy
**Industry:** Education
**Type:** Multi-page School/Course Website

### Description
Build a professional website for "Horizon Academy," an educational institution offering diploma and certificate programs. The website should showcase courses, faculty, and facilitate student admissions.

### Target Audience
- Prospective students seeking education programs
- Parents researching schools for their children
- Working professionals looking for continuing education
- Career changers exploring new fields

---

## Pages Required

| Page | Primary Owner | Description |
|------|---------------|-------------|
| Home | Member A | Landing with highlights, programs overview |
| Programs | Member C | Course catalog and program details |
| Admissions | Member B | Application process and requirements |
| Faculty | Member D | Instructor profiles |
| Campus | Member D | Facilities and virtual tour |
| Student Life | Member C | Activities, resources, testimonials |
| Contact | Member B | Contact form, locations |
| Apply | Member B | Online application form |

---

## User Stories by Team Member

### Member A - Core Structure & Home

#### Feature 1: Educational Institution Navigation
**Issue Title:** `feat: Implement academic navigation with application CTA`

**User Story:**
> As a **prospective student**, I want to **easily navigate to program and admissions information** so that **I can evaluate the school and apply**.

**Acceptance Criteria:**
- [ ] Institution logo on left
- [ ] Nav links: Programs, Admissions, Faculty, Campus, Student Life, Contact
- [ ] "Apply Now" prominent CTA button
- [ ] "Request Info" secondary link
- [ ] Search functionality (UI)
- [ ] Student/Parent/Staff portal links (small, top corner)
- [ ] Mobile hamburger menu
- [ ] Sticky navigation

**Technical Notes:**
- Bootstrap navbar with dropdown for Programs
- Secondary navigation row for portals
- Mega menu option for programs

---

#### Feature 2: Home Page Hero with Program Highlights
**Issue Title:** `feat: Create hero section with educational value proposition`

**User Story:**
> As a **prospective student**, I want to **understand what makes this school unique** so that **I'm motivated to explore programs**.

**Acceptance Criteria:**
- [ ] Hero image featuring students/campus
- [ ] Tagline and value proposition headline
- [ ] Key differentiators (3 brief points)
- [ ] "Explore Programs" and "Schedule Visit" CTA buttons
- [ ] Statistics: Students enrolled, Graduation rate, Employment rate, Programs offered
- [ ] Upcoming event or deadline banner
- [ ] Video introduction button (opens modal)

**Technical Notes:**
- Background image with overlay
- Statistics counter display
- Bootstrap modal for video
- Alert for deadlines

---

#### Feature 3: Footer with Academic Resources
**Issue Title:** `feat: Build comprehensive academic footer`

**User Story:**
> As a **visitor**, I want to **find quick access to important resources** so that **I can navigate efficiently**.

**Acceptance Criteria:**
- [ ] Five columns: Programs, Admissions, Resources, About, Connect
- [ ] Programs: List of departments/program areas
- [ ] Admissions: Apply, Requirements, Tuition, Financial Aid
- [ ] Resources: Library, Career Services, Academic Calendar, Student Portal
- [ ] About: History, Accreditation, Leadership, Contact
- [ ] Connect: Social media, Newsletter, Blog
- [ ] Newsletter signup
- [ ] Accreditation logos
- [ ] Contact information
- [ ] Copyright and legal

**Technical Notes:**
- Bootstrap grid
- Logo images for accreditation
- Email validation for newsletter

---

### Member B - Admissions & Applications

#### Feature 4: Online Application Form
**Issue Title:** `feat: Create comprehensive student application form`

**User Story:**
> As a **prospective student**, I want to **apply online** so that **I can start the admissions process conveniently**.

**Acceptance Criteria:**
- [ ] Multi-step form or accordion sections:
  1. Personal Information
  2. Educational Background
  3. Program Selection
  4. Additional Information
  5. Documents & Submit
- [ ] Personal: Name, DOB, Gender, Email, Phone, Address
- [ ] Education: Previous schools, Degrees, GPA, Graduation year
- [ ] Program: Intended program dropdown, Start term, Full/Part-time
- [ ] Additional: Essay textarea, Activities, Work experience
- [ ] Documents: Transcript upload, ID upload, Resume upload (UI only)
- [ ] Terms acceptance checkbox
- [ ] Application fee acknowledgment
- [ ] Submit application button
- [ ] Save progress option (UI)

**Technical Notes:**
- Bootstrap accordion or tabs for steps
- Progress indicator
- File input (UI only)
- Form validation per section

---

#### Feature 5: Admissions Information Page
**Issue Title:** `feat: Build admissions requirements and process page`

**User Story:**
> As a **prospective student**, I want to **understand admission requirements and deadlines** so that **I can prepare my application**.

**Acceptance Criteria:**
- [ ] Admissions overview section
- [ ] Requirements by program type (table or cards)
- [ ] Required documents checklist
- [ ] Application deadlines table (by term)
- [ ] Application process steps (numbered/visual)
- [ ] International students section
- [ ] Transfer students section
- [ ] FAQ accordion
- [ ] "Apply Now" CTA
- [ ] Contact admissions office card

**Technical Notes:**
- Tables for deadlines
- Cards for requirements
- Steps using list or custom CSS
- Accordion for FAQ

---

#### Feature 6: Contact & Request Information Form
**Issue Title:** `feat: Create contact page with info request form`

**User Story:**
> As a **prospective student**, I want to **request more information and contact the school** so that **I can get answers to my questions**.

**Acceptance Criteria:**
- [ ] Request info form: Name, Email, Phone, Program interest, Questions
- [ ] How did you hear about us dropdown
- [ ] Preferred contact method
- [ ] Campus tour scheduling option
- [ ] Department directory with contacts
- [ ] Campus locations with maps (2-3 campuses)
- [ ] General inquiry form
- [ ] Office hours
- [ ] Emergency contacts

**Technical Notes:**
- Bootstrap cards for departments
- Maps embeds
- Two-column layout
- Form validation

---

### Member C - Programs & Student Life

#### Feature 7: Program Catalog Page
**Issue Title:** `feat: Create program catalog with filtering`

**User Story:**
> As a **prospective student**, I want to **browse available programs** so that **I can find one that matches my interests**.

**Acceptance Criteria:**
- [ ] Programs organized by department/school
- [ ] Filter by: Department, Degree type, Duration, Delivery mode
- [ ] Sort: Alphabetical, Popularity, Duration
- [ ] Program cards: Name, Degree type, Duration, Brief description
- [ ] Degree type badges (Certificate, Diploma, Associate, Bachelor's)
- [ ] Online/Hybrid indicators
- [ ] Search programs by keyword
- [ ] "Compare Programs" feature (checkbox selection)
- [ ] Results count

**Technical Notes:**
- Bootstrap cards for programs
- Badge component for degree types
- Form elements for filters
- Sidebar layout on desktop

---

#### Feature 8: Program Detail Page
**Issue Title:** `feat: Design comprehensive program detail page`

**User Story:**
> As a **prospective student**, I want to **see detailed program information** so that **I can decide if it's right for me**.

**Acceptance Criteria:**
- [ ] Program header: Name, Degree, Department
- [ ] Program overview description
- [ ] Learning outcomes list
- [ ] Curriculum/Course list table (Course code, Name, Credits)
- [ ] Program requirements (GPA, prerequisites)
- [ ] Career outcomes section with job titles
- [ ] Faculty teaching in program
- [ ] Tuition and fees table
- [ ] Student testimonials
- [ ] Related programs
- [ ] "Apply Now" and "Request Info" CTAs

**Technical Notes:**
- Table for curriculum
- Cards for career outcomes
- Blockquote for testimonials
- `<dl>` for requirements

---

#### Feature 9: Student Life & Resources Page
**Issue Title:** `feat: Build student life page with activities and resources`

**User Story:**
> As a **prospective student**, I want to **see what student life is like** so that **I can envision myself at the school**.

**Acceptance Criteria:**
- [ ] Student life overview
- [ ] Clubs and organizations listing
- [ ] Athletics section (if applicable)
- [ ] Student services: Counseling, Health, Career, Library
- [ ] Academic support resources
- [ ] Housing information (if applicable)
- [ ] Student events calendar (upcoming 5-6 events)
- [ ] Student testimonials/quotes
- [ ] Photo gallery of student life
- [ ] Student handbook link

**Technical Notes:**
- Cards for services
- List for clubs
- Carousel for photos
- Blockquote for testimonials

---

### Member D - Faculty & Campus

#### Feature 10: Faculty Directory Page
**Issue Title:** `feat: Create faculty directory with profiles`

**User Story:**
> As a **prospective student**, I want to **learn about the faculty** so that **I can assess the quality of instruction**.

**Acceptance Criteria:**
- [ ] Faculty cards grid (12-15 faculty)
- [ ] Each card: Photo, Name, Title, Department, Specialization
- [ ] Filter by: Department, Program
- [ ] Search by name
- [ ] Sort: Name, Department
- [ ] "View Profile" link
- [ ] Contact icons (email)
- [ ] Office hours note
- [ ] Adjunct vs Full-time indicator

**Technical Notes:**
- Bootstrap cards
- Badge for department
- Grid responsive layout
- Search/filter form

---

#### Feature 11: Faculty Profile Page
**Issue Title:** `feat: Build detailed faculty profile page`

**User Story:**
> As a **prospective student**, I want to **learn about a specific faculty member** so that **I understand their expertise and teaching style**.

**Acceptance Criteria:**
- [ ] Large photo with name and title
- [ ] Department and programs taught
- [ ] Full biography
- [ ] Educational background list
- [ ] Research interests and publications
- [ ] Courses currently teaching
- [ ] Office location and hours
- [ ] Contact information
- [ ] Professional affiliations
- [ ] Links to academic profiles (Google Scholar, etc.)

**Technical Notes:**
- Two-column layout
- Lists for education and publications
- `<address>` for contact
- Link list for profiles

---

#### Feature 12: Campus & Facilities Page
**Issue Title:** `feat: Create campus tour and facilities page`

**User Story:**
> As a **prospective student**, I want to **explore the campus and facilities** so that **I know what resources are available**.

**Acceptance Criteria:**
- [ ] Campus overview with aerial/hero image
- [ ] Interactive campus map (image with hotspots or placeholder)
- [ ] Facilities listing: Library, Labs, Classrooms, Student Center
- [ ] Each facility: Image, Description, Hours, Location
- [ ] Virtual tour video embed (placeholder)
- [ ] Photo gallery of campus
- [ ] Getting to campus: Directions, Parking, Public transit
- [ ] Accessibility information
- [ ] Schedule campus visit CTA

**Technical Notes:**
- Image gallery
- Cards for facilities
- Video embed
- Maps for location
- Accordion for directions

---

## Technical Requirements Checklist

### HTML Elements to Use
- [ ] Semantic structure: `header`, `nav`, `main`, `section`, `article`, `aside`, `footer`
- [ ] Forms: `form`, `fieldset`, `legend`, `label`, `input` (text, email, date, file, checkbox, radio), `select`, `textarea`, `button`
- [ ] Tables: `table`, `thead`, `tbody`, `th`, `td`, `scope`
- [ ] Lists: `ul`, `ol`, `li`, `dl`, `dt`, `dd`
- [ ] Media: `img`, `figure`, `figcaption`, `video`, `iframe`
- [ ] Text: headings, `p`, `blockquote`, `time`, `address`

### Bootstrap Components to Use
- [ ] Navbar with mega menu option
- [ ] Cards
- [ ] Grid system
- [ ] Forms with validation
- [ ] Tables
- [ ] Badges
- [ ] Tabs/Pills
- [ ] Accordion
- [ ] Modal
- [ ] Carousel
- [ ] Progress bars
- [ ] Breadcrumbs

---

## Git Issues to Create

1. `feat: Implement academic navigation with application CTA`
2. `feat: Create hero section with educational value proposition`
3. `feat: Build comprehensive academic footer`
4. `feat: Create comprehensive student application form`
5. `feat: Build admissions requirements and process page`
6. `feat: Create contact page with info request form`
7. `feat: Create program catalog with filtering`
8. `feat: Design comprehensive program detail page`
9. `feat: Build student life page with activities and resources`
10. `feat: Create faculty directory with profiles`
11. `feat: Build detailed faculty profile page`
12. `feat: Create campus tour and facilities page`

---

## Sample Program Data

```
Program: Web Development Certificate
Department: Information Technology
Duration: 6 months
Format: Hybrid (Online + On-campus labs)
Credits: 24

Courses:
- WEB101: Introduction to HTML/CSS (3 credits)
- WEB102: JavaScript Fundamentals (3 credits)
- WEB201: Responsive Web Design (3 credits)
- WEB202: Front-End Frameworks (3 credits)
- WEB301: Back-End Development (4 credits)
- WEB302: Database Integration (4 credits)
- WEB401: Capstone Project (4 credits)

Career Outcomes: Web Developer, Front-End Developer, UI Developer
```

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

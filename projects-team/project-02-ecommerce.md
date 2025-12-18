# Project 2: E-Commerce Store

## Project Overview

**Project Name:** TechNova Store
**Industry:** Online Retail / Electronics
**Type:** Multi-page E-Commerce Website

### Description
Build an e-commerce website for "TechNova Store," an online electronics retailer. The website should display products, allow customers to view product details, compare products, and simulate a shopping cart experience.

### Target Audience
- Tech enthusiasts shopping for electronics
- Consumers comparing product specifications
- Gift shoppers looking for tech products

---

## Pages Required

| Page | Primary Owner | Description |
|------|---------------|-------------|
| Home | Member A | Landing with featured products, categories, deals |
| Products | Member C | Product listing with filters |
| Product Detail | Member C | Single product view with specs |
| Compare | Member C | Product comparison table |
| Cart | Member B | Shopping cart with order form |
| Contact/Support | Member B | Support form, FAQ |
| About | Member D | Company story, values |

---

## User Stories by Team Member

### Member A - Core Structure & Home

#### Feature 1: E-Commerce Navigation with Categories
**Issue Title:** `feat: Implement e-commerce navigation with category dropdowns`

**User Story:**
> As a **shopper**, I want to **navigate through product categories easily** so that **I can quickly find the type of product I'm looking for**.

**Acceptance Criteria:**
- [ ] Logo linking to home page
- [ ] Main nav: Home, Products, Compare, About, Contact
- [ ] Products dropdown with categories (Laptops, Phones, Tablets, Accessories)
- [ ] Search bar in navigation (non-functional, UI only)
- [ ] Cart icon with item count badge
- [ ] Mobile responsive with hamburger menu
- [ ] Sticky navigation on scroll

**Technical Notes:**
- Bootstrap navbar with dropdown
- Badge component for cart count
- Input group for search bar

---

#### Feature 2: Home Page Hero with Promotional Banners
**Issue Title:** `feat: Create home page hero carousel and promotions`

**User Story:**
> As a **shopper**, I want to **see current deals and promotions prominently** so that **I can take advantage of special offers**.

**Acceptance Criteria:**
- [ ] Hero carousel with 3 promotional slides
- [ ] Each slide: Image, headline, description, CTA button
- [ ] Auto-rotating with manual controls
- [ ] Below hero: 3-4 promotional banner cards (New Arrivals, Sale, Free Shipping)
- [ ] Responsive sizing for all devices

**Technical Notes:**
- Bootstrap carousel component
- Cards for promotional banners
- Use high-quality product images

---

#### Feature 3: Footer with E-Commerce Links
**Issue Title:** `feat: Build comprehensive e-commerce footer`

**User Story:**
> As a **shopper**, I want to **find shipping info, policies, and support links in the footer** so that **I can access important information easily**.

**Acceptance Criteria:**
- [ ] Four columns: Shop, Customer Service, About Us, Newsletter
- [ ] Shop links: All Products, New Arrivals, Best Sellers, On Sale
- [ ] Customer Service: Shipping, Returns, FAQ, Track Order
- [ ] About: Our Story, Careers, Press
- [ ] Newsletter signup form
- [ ] Payment method icons (Visa, MC, PayPal - images/icons)
- [ ] Social media links
- [ ] Copyright and legal links

**Technical Notes:**
- Bootstrap grid for columns
- Icon fonts or image sprites for payment methods
- Responsive collapse for mobile

---

### Member B - Forms & Interaction

#### Feature 4: Shopping Cart Page
**Issue Title:** `feat: Create shopping cart page with item management`

**User Story:**
> As a **shopper**, I want to **view and manage items in my cart** so that **I can review my order before checkout**.

**Acceptance Criteria:**
- [ ] Cart table: Product image, Name, Price, Quantity, Subtotal, Remove
- [ ] Quantity input with +/- buttons (UI only)
- [ ] Remove item button
- [ ] Cart summary: Subtotal, Shipping estimate, Tax, Total
- [ ] Promo code input field
- [ ] Continue Shopping and Proceed to Checkout buttons
- [ ] Empty cart state message

**Technical Notes:**
- Bootstrap table with product rows
- Input number for quantity
- Cards for cart summary
- Alert for empty cart

---

#### Feature 5: Checkout Form
**Issue Title:** `feat: Build multi-section checkout form`

**User Story:**
> As a **shopper**, I want to **enter my shipping and payment information** so that **I can complete my purchase**.

**Acceptance Criteria:**
- [ ] Shipping Information: Name, Email, Phone, Address, City, State (dropdown), ZIP
- [ ] Shipping Method: Radio buttons (Standard, Express, Overnight)
- [ ] Payment Information: Card Number, Expiration (month/year dropdowns), CVV
- [ ] Billing same as shipping checkbox
- [ ] Order review section showing cart summary
- [ ] Terms and conditions checkbox
- [ ] Place Order button
- [ ] Form validation on all required fields

**Technical Notes:**
- Bootstrap form with fieldset sections
- Input masks/patterns for card number
- Accordion or tabs for sections (optional)

---

#### Feature 6: Contact & Support Page
**Issue Title:** `feat: Create support page with FAQ and contact form`

**User Story:**
> As a **customer**, I want to **find answers to common questions and contact support** so that **I can resolve issues quickly**.

**Acceptance Criteria:**
- [ ] FAQ section using accordion (5+ questions)
- [ ] Contact form: Name, Email, Order Number (optional), Category dropdown, Message
- [ ] Category options: Order Status, Returns, Product Question, Technical Support, Other
- [ ] Support hours and contact information
- [ ] Live chat button (UI only, non-functional)
- [ ] Response time expectation message

**Technical Notes:**
- Bootstrap accordion for FAQ
- Card for contact information
- Button for live chat trigger

---

### Member C - Products & Data Display

#### Feature 7: Product Listing Page with Filters
**Issue Title:** `feat: Build product listing page with filter sidebar`

**User Story:**
> As a **shopper**, I want to **filter products by category, price, and brand** so that **I can find products that match my preferences**.

**Acceptance Criteria:**
- [ ] Sidebar with filter options:
  - Category checkboxes
  - Price range (min-max number inputs or range slider)
  - Brand checkboxes
  - Rating filter (checkbox or select)
- [ ] Sort dropdown (Price Low-High, Price High-Low, Newest, Popular)
- [ ] Product grid showing 6-8 products
- [ ] Results count ("Showing X products")
- [ ] Apply Filters and Clear Filters buttons
- [ ] Responsive: Filters collapse on mobile

**Technical Notes:**
- Bootstrap grid for sidebar + content
- Form elements for filters
- Card grid for products
- Offcanvas for mobile filters (optional)

---

#### Feature 8: Product Cards with Quick Actions
**Issue Title:** `feat: Design product cards with details and actions`

**User Story:**
> As a **shopper**, I want to **see product image, name, price, and rating at a glance** so that **I can quickly evaluate products**.

**Acceptance Criteria:**
- [ ] Product image with hover effect
- [ ] Product name (links to detail page)
- [ ] Price (show original and sale price if applicable)
- [ ] Star rating display (filled/empty stars)
- [ ] "Add to Cart" button
- [ ] "Compare" checkbox
- [ ] Sale badge for discounted items
- [ ] Out of stock indicator (some items)
- [ ] Wishlist icon (heart)

**Technical Notes:**
- Bootstrap cards
- Badge for sale/new indicators
- Star rating with icons or Unicode characters
- Hover effects with CSS transitions

---

#### Feature 9: Product Comparison Table
**Issue Title:** `feat: Create product comparison table page`

**User Story:**
> As a **shopper**, I want to **compare multiple products side by side** so that **I can make an informed purchase decision**.

**Acceptance Criteria:**
- [ ] Comparison table for 3-4 products
- [ ] Product images in header row
- [ ] Specifications rows: Price, Brand, Screen Size, Processor, RAM, Storage, Battery, Rating
- [ ] Highlight differences (optional)
- [ ] "Add to Cart" buttons in footer row
- [ ] Remove from comparison button
- [ ] Empty state with "Add products to compare" message
- [ ] Table scrolls horizontally on mobile

**Technical Notes:**
- Bootstrap table-responsive
- Product images in `<th>` cells
- `scope` attributes for accessibility
- Sticky first column (optional)

---

### Member D - Content & Media

#### Feature 10: Product Detail Page
**Issue Title:** `feat: Build comprehensive product detail page`

**User Story:**
> As a **shopper**, I want to **see detailed product information, images, and specifications** so that **I can decide if the product meets my needs**.

**Acceptance Criteria:**
- [ ] Large product image with thumbnail gallery
- [ ] Product title, price, rating, availability status
- [ ] Short description paragraph
- [ ] Quantity selector
- [ ] Add to Cart and Add to Wishlist buttons
- [ ] Expandable sections: Description, Specifications (table), Reviews
- [ ] Related products section (3-4 cards)
- [ ] Breadcrumb navigation

**Technical Notes:**
- Bootstrap grid for image + details layout
- Accordion or tabs for expandable sections
- Table for specifications
- Breadcrumb component

---

#### Feature 11: Customer Reviews Section
**Issue Title:** `feat: Add customer reviews with rating breakdown`

**User Story:**
> As a **shopper**, I want to **read customer reviews and see rating distribution** so that **I can trust the product quality**.

**Acceptance Criteria:**
- [ ] Overall rating display with star visualization
- [ ] Rating breakdown bar chart (5 star, 4 star, etc. with progress bars)
- [ ] Review count total
- [ ] 3-5 sample reviews with: Name, Date, Rating, Title, Review text
- [ ] Helpful/Not Helpful buttons
- [ ] "Write a Review" button
- [ ] Sort reviews dropdown (Newest, Highest, Lowest)

**Technical Notes:**
- Progress bars for rating distribution
- Cards or list items for individual reviews
- `<time>` element for dates
- `<blockquote>` for review text

---

#### Feature 12: About Us Page with Company Info
**Issue Title:** `feat: Create about page with company values and team`

**User Story:**
> As a **visitor**, I want to **learn about the company behind the store** so that **I can trust them with my purchase**.

**Acceptance Criteria:**
- [ ] Hero section with company tagline
- [ ] "Our Story" narrative section
- [ ] Company values/mission (3-4 value cards with icons)
- [ ] Statistics section (Years in business, Products sold, Happy customers)
- [ ] Leadership team cards (3-4 members)
- [ ] "Why Choose Us" section with benefits
- [ ] Call to action to shop

**Technical Notes:**
- Cards for values and team
- Counter-style statistics section
- Icon library for value icons
- Bootstrap utilities for spacing

---

## Technical Requirements Checklist

### HTML Elements to Use
- [ ] Semantic structure: `header`, `nav`, `main`, `section`, `article`, `aside`, `footer`
- [ ] Forms: `form`, `fieldset`, `legend`, `label`, `input` (text, email, number, tel, checkbox, radio), `select`, `textarea`, `button`
- [ ] Tables: `table`, `thead`, `tbody`, `tfoot`, `th`, `td`, `scope`, `colspan`
- [ ] Lists: `ul`, `ol`, `li`, `dl`, `dt`, `dd`
- [ ] Media: `img`, `figure`, `figcaption`
- [ ] Text: headings, `p`, `blockquote`, `time`, `small`, `strong`, `em`
- [ ] Interactive: `details`, `summary` (optional)

### Bootstrap Components to Use
- [ ] Navbar with dropdown menus
- [ ] Carousel
- [ ] Cards
- [ ] Grid system
- [ ] Forms and validation styles
- [ ] Tables
- [ ] Badges
- [ ] Progress bars
- [ ] Accordion
- [ ] Breadcrumbs
- [ ] Alerts
- [ ] Buttons and button groups
- [ ] Input groups

### Responsive Breakpoints
- [ ] Mobile: < 576px
- [ ] Tablet: 576px - 991px
- [ ] Desktop: 992px+

---

## Git Issues to Create

1. `feat: Implement e-commerce navigation with category dropdowns`
2. `feat: Create home page hero carousel and promotions`
3. `feat: Build comprehensive e-commerce footer`
4. `feat: Create shopping cart page with item management`
5. `feat: Build multi-section checkout form`
6. `feat: Create support page with FAQ and contact form`
7. `feat: Build product listing page with filter sidebar`
8. `feat: Design product cards with details and actions`
9. `feat: Create product comparison table page`
10. `feat: Build comprehensive product detail page`
11. `feat: Add customer reviews with rating breakdown`
12. `feat: Create about page with company values and team`

---

## Sample Product Data

```
Product: TechNova Pro Laptop 15"
Price: $1,299.99 (was $1,499.99)
Brand: TechNova
Category: Laptops
Rating: 4.5/5 (234 reviews)
Specs:
  - Display: 15.6" FHD IPS
  - Processor: Intel Core i7-12700H
  - RAM: 16GB DDR5
  - Storage: 512GB NVMe SSD
  - Battery: Up to 10 hours
  - Weight: 1.8 kg
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

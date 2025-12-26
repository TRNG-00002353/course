# Exercise: Build a Product Comparison Page

## Objective
Create a product comparison page that combines tables, lists, forms, and media elements to help users compare and select products.

## Requirements

Build an HTML page (`product-comparison.html`) that includes:

### 1. Page Structure
- Proper DOCTYPE and meta tags
- Page title: "Product Comparison - Laptops"
- Header with company logo and navigation

### 2. Product Filter Form

Create a filter section at the top:

```html
<aside class="filters">
    <form id="filter-form">
        <fieldset>
            <legend>Filter Products</legend>

            <!-- Brand checkboxes -->
            <div class="filter-group">
                <span>Brand:</span>
                <label><input type="checkbox" name="brand" value="apple"> Apple</label>
                <label><input type="checkbox" name="brand" value="dell"> Dell</label>
                <label><input type="checkbox" name="brand" value="hp"> HP</label>
                <label><input type="checkbox" name="brand" value="lenovo"> Lenovo</label>
            </div>

            <!-- Price range -->
            <div class="filter-group">
                <label for="price-range">Max Price: $<output id="price-output">2000</output></label>
                <input type="range" id="price-range" name="max-price"
                       min="500" max="3000" value="2000" step="100">
            </div>

            <!-- Sort select -->
            <div class="filter-group">
                <label for="sort-by">Sort By:</label>
                <select id="sort-by" name="sort">
                    <option value="price-low">Price: Low to High</option>
                    <option value="price-high">Price: High to Low</option>
                    <option value="rating">Rating</option>
                    <option value="name">Name</option>
                </select>
            </div>

            <button type="submit">Apply Filters</button>
            <button type="reset">Reset</button>
        </fieldset>
    </form>
</aside>
```

### 3. Product Comparison Table

Create a detailed comparison table for 4 products:

```html
<table class="comparison-table">
    <caption>Laptop Comparison Chart</caption>
    <thead>
        <tr>
            <th scope="col">Feature</th>
            <th scope="col">
                <img src="laptop1.jpg" alt="MacBook Pro" width="100">
                <br>MacBook Pro 14"
            </th>
            <th scope="col">
                <img src="laptop2.jpg" alt="Dell XPS 15" width="100">
                <br>Dell XPS 15
            </th>
            <th scope="col">
                <img src="laptop3.jpg" alt="HP Spectre" width="100">
                <br>HP Spectre x360
            </th>
            <th scope="col">
                <img src="laptop4.jpg" alt="ThinkPad X1" width="100">
                <br>ThinkPad X1 Carbon
            </th>
        </tr>
    </thead>
    <tbody>
        <!-- Pricing -->
        <tr>
            <th scope="row">Price</th>
            <td>$1,999</td>
            <td>$1,499</td>
            <td>$1,399</td>
            <td>$1,649</td>
        </tr>

        <!-- Display -->
        <tr>
            <th scope="row">Display</th>
            <td>14.2" Liquid Retina XDR</td>
            <td>15.6" OLED 3.5K</td>
            <td>13.5" OLED 3K</td>
            <td>14" 2.8K IPS</td>
        </tr>

        <!-- Processor -->
        <tr>
            <th scope="row">Processor</th>
            <td>Apple M3 Pro</td>
            <td>Intel Core i7-13700H</td>
            <td>Intel Core i7-1360P</td>
            <td>Intel Core i7-1365U</td>
        </tr>

        <!-- RAM -->
        <tr>
            <th scope="row">RAM</th>
            <td>18GB Unified</td>
            <td>16GB DDR5</td>
            <td>16GB DDR5</td>
            <td>16GB DDR5</td>
        </tr>

        <!-- Storage -->
        <tr>
            <th scope="row">Storage</th>
            <td>512GB SSD</td>
            <td>512GB SSD</td>
            <td>512GB SSD</td>
            <td>512GB SSD</td>
        </tr>

        <!-- Battery -->
        <tr>
            <th scope="row">Battery Life</th>
            <td>17 hours</td>
            <td>13 hours</td>
            <td>15 hours</td>
            <td>15 hours</td>
        </tr>

        <!-- Weight -->
        <tr>
            <th scope="row">Weight</th>
            <td>1.6 kg</td>
            <td>1.86 kg</td>
            <td>1.36 kg</td>
            <td>1.12 kg</td>
        </tr>

        <!-- Rating -->
        <tr>
            <th scope="row">Rating</th>
            <td>★★★★★ (4.8)</td>
            <td>★★★★☆ (4.5)</td>
            <td>★★★★☆ (4.3)</td>
            <td>★★★★★ (4.7)</td>
        </tr>

        <!-- Features checklist -->
        <tr>
            <th scope="row">Touchscreen</th>
            <td>✗</td>
            <td>✓</td>
            <td>✓</td>
            <td>✗</td>
        </tr>
        <tr>
            <th scope="row">Backlit Keyboard</th>
            <td>✓</td>
            <td>✓</td>
            <td>✓</td>
            <td>✓</td>
        </tr>
        <tr>
            <th scope="row">Fingerprint Reader</th>
            <td>✓</td>
            <td>✓</td>
            <td>✓</td>
            <td>✓</td>
        </tr>
    </tbody>
    <tfoot>
        <tr>
            <th scope="row">Action</th>
            <td><button type="button">Add to Cart</button></td>
            <td><button type="button">Add to Cart</button></td>
            <td><button type="button">Add to Cart</button></td>
            <td><button type="button">Add to Cart</button></td>
        </tr>
    </tfoot>
</table>
```

### 4. Product Cards with Details

Below the table, include detailed product cards:

```html
<section class="product-details">
    <article class="product-card" data-product-id="1">
        <figure>
            <img src="macbook.jpg" alt="MacBook Pro 14 inch" width="300">
            <figcaption>MacBook Pro 14"</figcaption>
        </figure>

        <h3>MacBook Pro 14" (2024)</h3>
        <p class="price">$1,999</p>

        <details>
            <summary>View Specifications</summary>
            <dl>
                <dt>Processor</dt>
                <dd>Apple M3 Pro (11-core CPU, 14-core GPU)</dd>

                <dt>Memory</dt>
                <dd>18GB Unified Memory</dd>

                <dt>Storage</dt>
                <dd>512GB SSD</dd>

                <dt>Display</dt>
                <dd>14.2" Liquid Retina XDR, 3024x1964</dd>

                <dt>Ports</dt>
                <dd>3x Thunderbolt 4, HDMI, SD Card, MagSafe</dd>
            </dl>
        </details>

        <details>
            <summary>What's in the Box</summary>
            <ul>
                <li>MacBook Pro</li>
                <li>96W USB-C Power Adapter</li>
                <li>USB-C to MagSafe Cable (2m)</li>
            </ul>
        </details>

        <form class="add-to-cart-form">
            <label for="quantity-1">Quantity:</label>
            <input type="number" id="quantity-1" name="quantity" min="1" max="10" value="1">

            <label for="color-1">Color:</label>
            <select id="color-1" name="color">
                <option value="silver">Silver</option>
                <option value="space-gray">Space Gray</option>
            </select>

            <button type="submit">Add to Cart</button>
        </form>
    </article>

    <!-- More product cards... -->
</section>
```

### 5. Customer Review Section

```html
<section class="reviews">
    <h2>Customer Reviews</h2>

    <article class="review">
        <header>
            <strong>John D.</strong>
            <span>★★★★★</span>
            <time datetime="2024-01-15">January 15, 2024</time>
        </header>
        <blockquote>
            <p>Excellent laptop! The battery life is incredible and the screen is gorgeous.</p>
        </blockquote>
        <footer>
            <span>Verified Purchase</span>
            <button type="button">Helpful (24)</button>
        </footer>
    </article>

    <!-- More reviews... -->

    <!-- Review submission form -->
    <form class="review-form">
        <h3>Write a Review</h3>

        <fieldset>
            <legend>Your Rating</legend>
            <label><input type="radio" name="rating" value="5" required> ★★★★★</label>
            <label><input type="radio" name="rating" value="4"> ★★★★☆</label>
            <label><input type="radio" name="rating" value="3"> ★★★☆☆</label>
            <label><input type="radio" name="rating" value="2"> ★★☆☆☆</label>
            <label><input type="radio" name="rating" value="1"> ★☆☆☆☆</label>
        </fieldset>

        <div class="form-group">
            <label for="review-title">Review Title</label>
            <input type="text" id="review-title" name="title" required maxlength="100">
        </div>

        <div class="form-group">
            <label for="review-text">Your Review</label>
            <textarea id="review-text" name="review" rows="5" required
                      minlength="50" maxlength="1000"
                      placeholder="Share your experience with this product..."></textarea>
        </div>

        <div class="form-group">
            <label>
                <input type="checkbox" name="recommend" value="yes">
                I would recommend this product
            </label>
        </div>

        <button type="submit">Submit Review</button>
    </form>
</section>
```

## Elements Checklist

### Tables
- [ ] `<table>`, `<caption>`, `<thead>`, `<tbody>`, `<tfoot>`
- [ ] `<th>` with `scope` attribute
- [ ] Images in table headers
- [ ] Action buttons in table footer

### Forms
- [ ] Checkboxes for brand filtering
- [ ] Range input for price
- [ ] Select dropdown for sorting
- [ ] Number input for quantity
- [ ] Radio buttons for ratings
- [ ] Textarea for review

### Lists
- [ ] Unordered list for box contents
- [ ] Description list (`<dl>`, `<dt>`, `<dd>`) for specs

### Media
- [ ] Product images with alt text
- [ ] Figure with figcaption

### Interactive
- [ ] Details/summary for expandable specs
- [ ] Output element for range value

### Semantic
- [ ] Article for product cards and reviews
- [ ] Section for page areas
- [ ] Time element for dates
- [ ] Blockquote for review text

## Bonus Challenges

1. **Compare Checkbox**: Add checkboxes to select products for comparison
2. **Sticky Header**: Make table header sticky while scrolling
3. **Image Gallery**: Add multiple product images with thumbnails
4. **Meter Element**: Use `<meter>` for battery life visualization
5. **Progress Element**: Use `<progress>` for stock availability

## Skills Tested
- Complex table structures
- Form elements (all types)
- Lists (ul, ol, dl)
- Media elements
- Semantic HTML
- Accessibility

## Validation
Your HTML should pass the W3C Validator without errors.

---

## Progressive Enhancement Versions

Complete this exercise in 3 versions to demonstrate progressive enhancement:

### Version 1: Raw HTML
**File:** `product-comparison-v1.html`

Build the product comparison page using only semantic HTML5 - no styling.

**Focus on:**
- Complex table structure with proper headers
- Scope attributes for accessibility
- Forms with various input types
- Details/summary for expandable content
- Description lists for specifications

**Expected output:** A functional but unstyled page with working forms and expandable sections.

---

### Version 2: HTML + CSS3
**Files:** `product-comparison-v2.html`, `product-comparison-v2.css`

Enhance Version 1 with custom CSS3 styling.

**Add these CSS features:**
- Sticky table header
- Comparison highlight on hover (column highlight)
- Price range slider styling
- Expandable details animation
- Product card layout with Flexbox/Grid
- Responsive table with horizontal scroll
- Star rating styling

**CSS concepts to demonstrate:**
```css
/* Example CSS features to use */
.comparison-table { border-collapse: collapse; }
.comparison-table th { position: sticky; top: 0; }
.comparison-table td:hover { background-color: #f0f8ff; }
details[open] summary { font-weight: bold; }
details[open] > *:not(summary) { animation: slideDown 0.3s; }
.price { font-size: 1.5rem; color: #28a745; }
.star-rating { color: #ffc107; }
```

---

### Version 3: HTML + Bootstrap 5
**File:** `product-comparison-v3.html`

Rebuild using Bootstrap 5 framework.

**Use these Bootstrap components:**
- Table classes with sticky header
- Accordion for expandable specs
- Form controls with input groups
- Card grid for product details
- Badge for features (checkmark/cross)
- Button groups for actions
- Star rating with icons

**Example Bootstrap comparison:**
```html
<div class="table-responsive">
    <table class="table table-bordered align-middle">
        <thead class="table-dark sticky-top">
            <tr>
                <th>Feature</th>
                <th class="text-center">
                    <img src="..." class="img-fluid mb-2" style="max-width: 80px;">
                    <br>Product A
                </th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <th>Touchscreen</th>
                <td class="text-center"><i class="bi bi-check-lg text-success"></i></td>
            </tr>
        </tbody>
    </table>
</div>

<!-- Accordion for specs -->
<div class="accordion" id="productSpecs">
    <div class="accordion-item">
        <h2 class="accordion-header">
            <button class="accordion-button" data-bs-toggle="collapse" data-bs-target="#specs1">
                Specifications
            </button>
        </h2>
        <div id="specs1" class="accordion-collapse collapse show">
            <div class="accordion-body">
                <dl class="row">
                    <dt class="col-sm-4">Processor</dt>
                    <dd class="col-sm-8">Apple M3 Pro</dd>
                </dl>
            </div>
        </div>
    </div>
</div>
```

---

## Submission

### Required Files
| File | Description |
|------|-------------|
| `product-comparison-v1.html` | Raw HTML version |
| `product-comparison-v2.html` | CSS3 enhanced version |
| `product-comparison-v2.css` | CSS3 stylesheet |
| `product-comparison-v3.html` | Bootstrap version |

### Folder Structure
```
your-repo/
└── 04-html/
    ├── product-comparison-v1.html
    ├── product-comparison-v2.html
    ├── product-comparison-v2.css
    └── product-comparison-v3.html
```

### Evaluation Criteria
| Criteria | Points |
|----------|--------|
| **Version 1 (Raw HTML)** | **30** |
| Table structure correct | 10 |
| Forms functional | 10 |
| Details/summary works | 10 |
| **Version 2 (CSS3)** | **35** |
| Table styling & hover | 15 |
| Form controls styled | 10 |
| Responsive design | 10 |
| **Version 3 (Bootstrap)** | **35** |
| Bootstrap components | 15 |
| Accordion works | 10 |
| Professional appearance | 10 |
| **Total** | **100** |

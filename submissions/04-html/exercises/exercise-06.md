# Exercise: Build a Restaurant Menu Page

## Objective
Create a restaurant menu page that demonstrates lists, description lists, tables, images, and semantic HTML with accessibility considerations.

## Requirements

Build an HTML page (`restaurant-menu.html`) that includes:

### 1. Page Structure
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>The Gourmet Kitchen - Menu</title>
    <meta name="description" content="View our delicious menu featuring appetizers, main courses, desserts, and beverages.">
</head>
<body>
    <header>
        <h1>The Gourmet Kitchen</h1>
        <p>Fine Dining Since 1985</p>
        <nav><!-- Navigation --></nav>
    </header>
    <main>
        <!-- Menu sections -->
    </main>
    <footer>
        <!-- Restaurant info -->
    </footer>
</body>
</html>
```

### 2. Menu Categories

Create sections for each category using semantic HTML:

#### Appetizers Section
```html
<section id="appetizers" aria-labelledby="appetizers-heading">
    <h2 id="appetizers-heading">Appetizers</h2>

    <article class="menu-item">
        <figure>
            <img src="bruschetta.jpg" alt="Fresh tomato bruschetta on toasted bread"
                 width="200" height="150" loading="lazy">
        </figure>
        <div class="item-details">
            <h3>Classic Bruschetta</h3>
            <p class="description">Fresh tomatoes, basil, garlic, and olive oil on toasted ciabatta bread</p>
            <p class="price">$12.99</p>
            <ul class="dietary-info">
                <li><abbr title="Vegetarian">V</abbr></li>
                <li><abbr title="Contains Gluten">G</abbr></li>
            </ul>
        </div>
    </article>

    <!-- More appetizers... -->
</section>
```

#### Main Courses Section
Include at least 6 main course items with:
- Item name
- Description
- Price
- Dietary symbols (V=Vegetarian, VG=Vegan, GF=Gluten-Free, etc.)
- Optional: Image

#### Desserts Section
Include at least 4 dessert items

#### Beverages Section
Use a different format - description list:

```html
<section id="beverages" aria-labelledby="beverages-heading">
    <h2 id="beverages-heading">Beverages</h2>

    <h3>Hot Drinks</h3>
    <dl class="beverage-list">
        <dt>Espresso</dt>
        <dd>Single shot - $3.50</dd>
        <dd>Double shot - $4.50</dd>

        <dt>Cappuccino</dt>
        <dd>Classic Italian style with steamed milk foam - $5.00</dd>

        <dt>Latte</dt>
        <dd>Espresso with steamed milk - $5.50</dd>
        <dd>Add flavor shot (vanilla, caramel, hazelnut) - $0.75</dd>

        <dt>Hot Tea</dt>
        <dd>Selection of premium loose-leaf teas - $4.00</dd>
    </dl>

    <h3>Cold Drinks</h3>
    <dl class="beverage-list">
        <dt>Soft Drinks</dt>
        <dd>Coca-Cola, Sprite, Fanta - $3.00</dd>

        <dt>Fresh Juices</dt>
        <dd>Orange, Apple, Grapefruit - $5.00</dd>

        <dt>Iced Tea</dt>
        <dd>House-made peach or lemon - $4.00</dd>
    </dl>

    <h3>Wine List</h3>
    <table class="wine-table">
        <caption>House Wine Selection</caption>
        <thead>
            <tr>
                <th scope="col">Wine</th>
                <th scope="col">Region</th>
                <th scope="col">Glass</th>
                <th scope="col">Bottle</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <th scope="row">Chardonnay</th>
                <td>Napa Valley</td>
                <td>$12</td>
                <td>$48</td>
            </tr>
            <tr>
                <th scope="row">Pinot Noir</th>
                <td>Sonoma</td>
                <td>$14</td>
                <td>$56</td>
            </tr>
            <tr>
                <th scope="row">Cabernet Sauvignon</th>
                <td>Bordeaux</td>
                <td>$15</td>
                <td>$60</td>
            </tr>
        </tbody>
    </table>
</section>
```

### 3. Special Menus

#### Chef's Specials (Highlighted)
```html
<aside class="chef-specials">
    <h2>Chef's Specials</h2>
    <p><em>Available for a limited time</em></p>

    <article class="special-item">
        <h3>Pan-Seared Duck Breast</h3>
        <p>With cherry reduction, roasted vegetables, and truffle mashed potatoes</p>
        <p class="price"><strong>$38.99</strong></p>
    </article>
</aside>
```

#### Set Menu / Prix Fixe
```html
<section id="set-menu">
    <h2>Prix Fixe Menu - $65 per person</h2>
    <p>Three-course dining experience</p>

    <div class="course">
        <h3>First Course <small>(Choose one)</small></h3>
        <ul>
            <li>French Onion Soup</li>
            <li>Caesar Salad</li>
            <li>Shrimp Cocktail</li>
        </ul>
    </div>

    <div class="course">
        <h3>Main Course <small>(Choose one)</small></h3>
        <ul>
            <li>Filet Mignon with red wine sauce</li>
            <li>Grilled Atlantic Salmon</li>
            <li>Mushroom Risotto <abbr title="Vegetarian">V</abbr></li>
        </ul>
    </div>

    <div class="course">
        <h3>Dessert <small>(Choose one)</small></h3>
        <ul>
            <li>Chocolate Lava Cake</li>
            <li>Crème Brûlée</li>
            <li>Fresh Fruit Sorbet <abbr title="Vegan">VG</abbr></li>
        </ul>
    </div>
</section>
```

### 4. Dietary Information Legend

```html
<aside class="dietary-legend">
    <h2>Dietary Information</h2>
    <dl>
        <dt><abbr title="Vegetarian">V</abbr></dt>
        <dd>Vegetarian - Contains no meat or fish</dd>

        <dt><abbr title="Vegan">VG</abbr></dt>
        <dd>Vegan - Contains no animal products</dd>

        <dt><abbr title="Gluten-Free">GF</abbr></dt>
        <dd>Gluten-Free - Contains no gluten</dd>

        <dt><abbr title="Contains Nuts">N</abbr></dt>
        <dd>Contains Nuts - May contain tree nuts or peanuts</dd>

        <dt><abbr title="Spicy">🌶️</abbr></dt>
        <dd>Spicy - Contains hot peppers</dd>
    </dl>
    <p><small>Please inform your server of any allergies</small></p>
</aside>
```

### 5. Reservation Form

```html
<section id="reservations">
    <h2>Make a Reservation</h2>

    <form action="#" method="POST">
        <fieldset>
            <legend>Reservation Details</legend>

            <div class="form-group">
                <label for="res-name">Name *</label>
                <input type="text" id="res-name" name="name" required>
            </div>

            <div class="form-group">
                <label for="res-email">Email *</label>
                <input type="email" id="res-email" name="email" required>
            </div>

            <div class="form-group">
                <label for="res-phone">Phone *</label>
                <input type="tel" id="res-phone" name="phone" required>
            </div>

            <div class="form-group">
                <label for="res-date">Date *</label>
                <input type="date" id="res-date" name="date" required>
            </div>

            <div class="form-group">
                <label for="res-time">Time *</label>
                <select id="res-time" name="time" required>
                    <option value="">Select a time</option>
                    <optgroup label="Lunch">
                        <option value="11:30">11:30 AM</option>
                        <option value="12:00">12:00 PM</option>
                        <option value="12:30">12:30 PM</option>
                        <option value="13:00">1:00 PM</option>
                        <option value="13:30">1:30 PM</option>
                    </optgroup>
                    <optgroup label="Dinner">
                        <option value="17:00">5:00 PM</option>
                        <option value="17:30">5:30 PM</option>
                        <option value="18:00">6:00 PM</option>
                        <option value="18:30">6:30 PM</option>
                        <option value="19:00">7:00 PM</option>
                        <option value="19:30">7:30 PM</option>
                        <option value="20:00">8:00 PM</option>
                        <option value="20:30">8:30 PM</option>
                    </optgroup>
                </select>
            </div>

            <div class="form-group">
                <label for="res-guests">Number of Guests *</label>
                <input type="number" id="res-guests" name="guests" min="1" max="20" required>
            </div>

            <div class="form-group">
                <label for="res-occasion">Special Occasion</label>
                <select id="res-occasion" name="occasion">
                    <option value="">None</option>
                    <option value="birthday">Birthday</option>
                    <option value="anniversary">Anniversary</option>
                    <option value="business">Business Dinner</option>
                    <option value="other">Other</option>
                </select>
            </div>

            <div class="form-group">
                <label for="res-requests">Special Requests</label>
                <textarea id="res-requests" name="requests" rows="3"
                          placeholder="Dietary restrictions, seating preferences, etc."></textarea>
            </div>
        </fieldset>

        <button type="submit">Request Reservation</button>
    </form>
</section>
```

### 6. Footer with Restaurant Info

```html
<footer>
    <section class="contact-info">
        <h2>Contact Us</h2>
        <address>
            <strong>The Gourmet Kitchen</strong><br>
            123 Culinary Street<br>
            Food City, FC 12345<br>
            <a href="tel:+1234567890">Phone: (123) 456-7890</a><br>
            <a href="mailto:info@gourmetkitchen.com">Email: info@gourmetkitchen.com</a>
        </address>
    </section>

    <section class="hours">
        <h2>Hours of Operation</h2>
        <dl>
            <dt>Monday - Thursday</dt>
            <dd><time>11:00 AM</time> - <time>10:00 PM</time></dd>

            <dt>Friday - Saturday</dt>
            <dd><time>11:00 AM</time> - <time>11:00 PM</time></dd>

            <dt>Sunday</dt>
            <dd><time>10:00 AM</time> - <time>9:00 PM</time> (Brunch until 2 PM)</dd>
        </dl>
    </section>

    <p><small>&copy; 2024 The Gourmet Kitchen. All rights reserved.</small></p>
</footer>
```

## Elements Checklist

### Lists
- [ ] Unordered lists (`<ul>`) for menu items and dietary symbols
- [ ] Ordered lists (`<ol>`) for numbered items (if applicable)
- [ ] Description lists (`<dl>`, `<dt>`, `<dd>`) for beverages and hours

### Tables
- [ ] Wine list table with `<caption>`, `<thead>`, `<tbody>`
- [ ] Proper use of `scope` attributes

### Forms
- [ ] Text, email, tel inputs
- [ ] Date input
- [ ] Number input with min/max
- [ ] Select with `<optgroup>`
- [ ] Textarea

### Media
- [ ] Food images with proper alt text
- [ ] Figure/figcaption for food photos

### Semantic HTML
- [ ] `<article>` for menu items
- [ ] `<section>` for categories
- [ ] `<aside>` for specials and legend
- [ ] `<address>` for contact info
- [ ] `<time>` for hours
- [ ] `<abbr>` for dietary symbols
- [ ] `<small>` for fine print

## Bonus Challenges

1. **Allergen Table**: Create a table showing allergens for each dish
2. **Kids Menu**: Add a separate kids menu section
3. **Calorie Info**: Add calorie counts using `<data>` element
4. **QR Code**: Add a placeholder for QR code to digital menu
5. **Multi-language**: Add `lang` attributes for any foreign food names

## Skills Tested
- Various list types
- Description lists
- Tables with semantic markup
- Forms with various inputs
- Semantic HTML elements
- Accessibility with ARIA and proper labeling

## Validation
Your HTML should pass the W3C Validator without errors.

---

## Progressive Enhancement Versions

Complete this exercise in 3 versions to demonstrate progressive enhancement:

### Version 1: Raw HTML
**File:** `restaurant-menu-v1.html`

Build the restaurant menu using only semantic HTML5 - no styling.

**Focus on:**
- Proper use of lists (ul, ol, dl)
- Table for wine list
- Form for reservations
- Semantic elements (article, section, aside, address)
- Abbreviations for dietary symbols

**Expected output:** A functional but unstyled menu page with working reservation form.

---

### Version 2: HTML + CSS3
**Files:** `restaurant-menu-v2.html`, `restaurant-menu-v2.css`

Enhance Version 1 with custom CSS3 styling.

**Add these CSS features:**
- Elegant typography (Google Fonts)
- Two-column menu layout with CSS Grid
- Price alignment with flexbox and dotted leader
- Dietary badge styling
- Card layout for menu sections
- Hover effects on menu items
- Responsive single column on mobile

**CSS concepts to demonstrate:**
```css
/* Example CSS features to use */
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display&display=swap');
body { font-family: 'Playfair Display', serif; }

.menu-item {
    display: flex;
    justify-content: space-between;
    border-bottom: 1px dotted #ccc;
}
.menu-item .name { flex: 1; }
.menu-item .price { font-weight: bold; }

.dietary-badge {
    display: inline-block;
    padding: 2px 6px;
    border-radius: 4px;
    font-size: 0.75rem;
}
.dietary-v { background-color: #28a745; color: white; }
.dietary-gf { background-color: #ffc107; }
```

---

### Version 3: HTML + Bootstrap 5
**File:** `restaurant-menu-v3.html`

Rebuild using Bootstrap 5 framework.

**Use these Bootstrap components:**
- Card component for menu sections
- List group for menu items
- Badge component for dietary info and prices
- Table classes for wine list
- Form classes for reservation
- Offcanvas for mobile menu (bonus)
- Grid system for layout

**Example Bootstrap menu:**
```html
<div class="card mb-4">
    <div class="card-header bg-dark text-white">
        <h2 class="h5 mb-0">Appetizers</h2>
    </div>
    <ul class="list-group list-group-flush">
        <li class="list-group-item d-flex justify-content-between align-items-start">
            <div class="ms-2 me-auto">
                <div class="fw-bold">Classic Bruschetta</div>
                <small class="text-muted">Fresh tomatoes, basil, garlic on toasted ciabatta</small>
                <span class="badge bg-success">V</span>
            </div>
            <span class="badge bg-primary rounded-pill">$12.99</span>
        </li>
    </ul>
</div>

<!-- Reservation Form -->
<form class="row g-3">
    <div class="col-md-6">
        <label class="form-label">Name</label>
        <input type="text" class="form-control" required>
    </div>
    <div class="col-md-6">
        <label class="form-label">Date</label>
        <input type="date" class="form-control" required>
    </div>
</form>
```

---

## Submission

### Required Files
| File | Description |
|------|-------------|
| `restaurant-menu-v1.html` | Raw HTML version |
| `restaurant-menu-v2.html` | CSS3 enhanced version |
| `restaurant-menu-v2.css` | CSS3 stylesheet |
| `restaurant-menu-v3.html` | Bootstrap version |

### Folder Structure
```
your-repo/
└── 04-html/
    ├── restaurant-menu-v1.html
    ├── restaurant-menu-v2.html
    ├── restaurant-menu-v2.css
    └── restaurant-menu-v3.html
```

### Evaluation Criteria
| Criteria | Points |
|----------|--------|
| **Version 1 (Raw HTML)** | **30** |
| List types correct | 10 |
| Semantic elements | 10 |
| Form functional | 10 |
| **Version 2 (CSS3)** | **35** |
| Typography & layout | 15 |
| Menu item styling | 10 |
| Responsive design | 10 |
| **Version 3 (Bootstrap)** | **35** |
| Bootstrap components | 15 |
| Cards/List groups | 10 |
| Professional appearance | 10 |
| **Total** | **100** |

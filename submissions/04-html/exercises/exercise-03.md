# Exercise: Build a Survey Form

## Objective
Create a comprehensive survey form using all HTML5 form elements including select dropdowns, checkboxes, radio buttons, textareas, and various input types.

## Requirements

Build an HTML page (`survey-form.html`) that includes:

### 1. Page Structure
- Proper DOCTYPE and meta tags
- Page title: "Customer Satisfaction Survey"
- Form should use `POST` method with action `#`

### 2. Form Sections

Organize the form using `<fieldset>` and `<legend>`:

#### Section 1: Personal Information
```html
<fieldset>
    <legend>Personal Information</legend>
    <!-- fields here -->
</fieldset>
```

Required fields:
- **Full Name** - text input (required, minlength=2, maxlength=100)
- **Email** - email input (required)
- **Phone** - tel input (pattern for phone format)
- **Age Range** - select dropdown:
  - Under 18
  - 18-24
  - 25-34
  - 35-44
  - 45-54
  - 55+
- **Gender** - radio buttons:
  - Male
  - Female
  - Prefer not to say

#### Section 2: Product Feedback
- **Products Used** - checkboxes (select multiple):
  - [ ] Product A - Web Application
  - [ ] Product B - Mobile App
  - [ ] Product C - Desktop Software
  - [ ] Product D - API Services
  - [ ] Product E - Cloud Storage

- **Primary Product** - select dropdown (same options as above)

- **How long have you been a customer?** - select dropdown:
  - Less than 1 month
  - 1-6 months
  - 6-12 months
  - 1-2 years
  - More than 2 years

#### Section 3: Satisfaction Rating
- **Overall Satisfaction** - radio buttons (1-5 stars)
- **Product Quality** - range input (1-10)
- **Customer Support** - range input (1-10)
- **Value for Money** - range input (1-10)
- **Would you recommend us?** - radio buttons:
  - Definitely
  - Probably
  - Not sure
  - Probably not
  - Definitely not

#### Section 4: Detailed Feedback
- **What do you like most?** - textarea (rows=4, maxlength=500)
- **What can we improve?** - textarea (rows=4, maxlength=500)
- **Additional Comments** - textarea (rows=6, maxlength=1000)

#### Section 5: Contact Preferences
- **Preferred Contact Method** - checkboxes:
  - [ ] Email
  - [ ] Phone
  - [ ] SMS
  - [ ] No contact

- **Best Time to Contact** - select (multiple):
  - Morning (9am-12pm)
  - Afternoon (12pm-5pm)
  - Evening (5pm-8pm)

- **Subscribe to Newsletter** - checkbox
- **Agree to Terms** - checkbox (required)

### 3. Form Controls to Include

```html
<!-- Text Inputs -->
<input type="text">
<input type="email">
<input type="tel">
<input type="number">

<!-- Selection -->
<select>
<select multiple>
<option>
<optgroup>
<datalist>

<!-- Choices -->
<input type="radio">
<input type="checkbox">

<!-- Range -->
<input type="range">

<!-- Text Area -->
<textarea>

<!-- Buttons -->
<button type="submit">
<button type="reset">
```

### 4. Required Attributes

Use these HTML5 form attributes:
- `required` - for mandatory fields
- `placeholder` - for input hints
- `pattern` - for phone number format
- `minlength` / `maxlength` - for text limits
- `min` / `max` - for range inputs
- `disabled` - for conditional fields
- `readonly` - for display-only fields
- `autofocus` - on first field
- `autocomplete` - appropriate values

### 5. Accessibility Requirements
- Every input must have a `<label>` with `for` attribute
- Use `aria-describedby` for helper text
- Group related radio/checkbox with `fieldset`
- Include `aria-required="true"` for required fields

## Sample Code Structure

```html
<form action="#" method="POST">
    <fieldset>
        <legend>Personal Information</legend>

        <div class="form-group">
            <label for="fullname">Full Name *</label>
            <input type="text" id="fullname" name="fullname"
                   required minlength="2" maxlength="100"
                   placeholder="Enter your full name"
                   aria-required="true">
        </div>

        <div class="form-group">
            <label for="age-range">Age Range</label>
            <select id="age-range" name="age-range">
                <option value="">-- Select --</option>
                <option value="under-18">Under 18</option>
                <option value="18-24">18-24</option>
                <!-- more options -->
            </select>
        </div>

        <div class="form-group">
            <span id="gender-label">Gender</span>
            <div role="radiogroup" aria-labelledby="gender-label">
                <label>
                    <input type="radio" name="gender" value="male"> Male
                </label>
                <label>
                    <input type="radio" name="gender" value="female"> Female
                </label>
                <label>
                    <input type="radio" name="gender" value="other"> Prefer not to say
                </label>
            </div>
        </div>
    </fieldset>

    <fieldset>
        <legend>Products Used (Select all that apply)</legend>
        <label>
            <input type="checkbox" name="products" value="web-app">
            Product A - Web Application
        </label>
        <!-- more checkboxes -->
    </fieldset>

    <fieldset>
        <legend>Satisfaction Rating</legend>
        <div class="form-group">
            <label for="quality">Product Quality: <output id="quality-output">5</output>/10</label>
            <input type="range" id="quality" name="quality"
                   min="1" max="10" value="5"
                   oninput="document.getElementById('quality-output').value = this.value">
        </div>
    </fieldset>

    <fieldset>
        <legend>Feedback</legend>
        <div class="form-group">
            <label for="comments">Additional Comments</label>
            <textarea id="comments" name="comments"
                      rows="6" maxlength="1000"
                      placeholder="Share your thoughts..."></textarea>
            <small>Maximum 1000 characters</small>
        </div>
    </fieldset>

    <div class="form-actions">
        <button type="submit">Submit Survey</button>
        <button type="reset">Clear Form</button>
    </div>
</form>
```

## Bonus Challenges

1. **Conditional Fields**: Show/hide fields based on selections
2. **Character Counter**: Display remaining characters for textareas
3. **Multi-Select with Optgroup**: Group select options by category
4. **Datalist**: Add autocomplete suggestions for text inputs
5. **Output Element**: Display calculated values (e.g., average rating)

## Skills Tested
- Form structure (`form`, `fieldset`, `legend`)
- Input types (text, email, tel, number, range)
- Selection elements (`select`, `option`, `optgroup`, `datalist`)
- Choice inputs (radio, checkbox)
- Textarea
- Form validation attributes
- Accessibility (labels, ARIA)

## Validation
Your HTML should pass the W3C Validator without errors.

---

## Progressive Enhancement Versions

Complete this exercise in 3 versions to demonstrate progressive enhancement:

### Version 1: Raw HTML
**File:** `survey-form-v1.html`

Build the survey form using only semantic HTML5 - no styling.

**Focus on:**
- Proper form structure (fieldset, legend)
- All input types (text, email, radio, checkbox, select, range, textarea)
- HTML5 validation attributes (required, pattern, min, max)
- Accessibility (labels, aria attributes)

**Expected output:** A functional but unstyled form with native browser validation.

---

### Version 2: HTML + CSS3
**Files:** `survey-form-v2.html`, `survey-form-v2.css`

Enhance Version 1 with custom CSS3 styling.

**Add these CSS features:**
- Form layout with CSS Grid or Flexbox
- Custom styled inputs and focus states
- Custom radio buttons and checkboxes
- Range slider styling
- Validation state styling (:valid, :invalid)
- Responsive form layout

**CSS concepts to demonstrate:**
```css
/* Example CSS features to use */
.form-group { display: flex; flex-direction: column; gap: 0.5rem; }
input:focus { outline: 2px solid var(--primary-color); }
input:invalid { border-color: #dc3545; }
input:valid { border-color: #28a745; }
input[type="range"] { -webkit-appearance: none; }
/* Custom checkbox styling */
input[type="checkbox"] { appearance: none; /* custom styles */ }
```

---

### Version 3: HTML + Bootstrap 5
**File:** `survey-form-v3.html`

Rebuild using Bootstrap 5 framework.

**Use these Bootstrap components:**
- Form classes (form-control, form-label, form-check)
- Form layout (row, col, mb-3)
- Input groups
- Floating labels (bonus)
- Form validation classes (was-validated, is-valid, is-invalid)
- Button styles

**Example Bootstrap form:**
```html
<form class="needs-validation" novalidate>
    <div class="mb-3">
        <label for="name" class="form-label">Full Name</label>
        <input type="text" class="form-control" id="name" required>
        <div class="invalid-feedback">Please enter your name.</div>
    </div>
    <div class="mb-3">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" id="terms">
            <label class="form-check-label" for="terms">I agree to terms</label>
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
</form>
```

---

## Submission

### Required Files
| File | Description |
|------|-------------|
| `survey-form-v1.html` | Raw HTML version |
| `survey-form-v2.html` | CSS3 enhanced version |
| `survey-form-v2.css` | CSS3 stylesheet |
| `survey-form-v3.html` | Bootstrap version |

### Folder Structure
```
your-repo/
└── 04-html/
    ├── survey-form-v1.html
    ├── survey-form-v2.html
    ├── survey-form-v2.css
    └── survey-form-v3.html
```

### Evaluation Criteria
| Criteria | Points |
|----------|--------|
| **Version 1 (Raw HTML)** | **30** |
| Form structure correct | 10 |
| All input types used | 10 |
| Accessibility (labels, aria) | 10 |
| **Version 2 (CSS3)** | **35** |
| Custom input styling | 15 |
| Validation states | 10 |
| Responsive layout | 10 |
| **Version 3 (Bootstrap)** | **35** |
| Bootstrap form classes | 15 |
| Validation feedback | 10 |
| Layout and spacing | 10 |
| **Total** | **100** |

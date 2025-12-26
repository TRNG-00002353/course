# Exercise: Build an Employee Directory Table

## Objective
Create a well-structured HTML table displaying employee information with proper semantic markup, accessibility features, and styling hooks.

## Requirements

Build an HTML page (`employee-table.html`) that includes:

### 1. Page Structure
- Proper DOCTYPE and meta tags
- Page title: "Employee Directory"
- A header with company name and department filter

### 2. Employee Table

Create a table with the following structure:

#### Table Caption
- "Company Employee Directory - 2024"

#### Table Header (`<thead>`)
Columns:
| ID | Photo | Name | Department | Position | Email | Phone | Start Date | Status |

#### Table Body (`<tbody>`)
Add at least 8 employees with varied data:
- Mix of departments (Engineering, Marketing, HR, Sales, Finance)
- Mix of positions (Manager, Senior, Junior, Intern)
- Mix of statuses (Active, On Leave, Remote)
- Use placeholder images for photos

#### Table Footer (`<tfoot>`)
- Total employee count
- Department summary

### 3. Required Table Features

```html
<!-- Use these table elements -->
<table>
<caption>
<thead>
<tbody>
<tfoot>
<tr>
<th> (with scope attribute)
<td>
<colgroup> and <col>
```

### 4. Accessibility Requirements
- Use `scope="col"` for column headers
- Use `scope="row"` for row headers (if applicable)
- Add `aria-label` to the table
- Include proper `alt` text for employee photos

### 5. Advanced Features
- Use `colspan` to span the footer across columns
- Use `rowspan` to group employees by department (bonus)
- Add `data-*` attributes for department and status

## Sample Data Structure

```html
<tr data-department="engineering" data-status="active">
    <td>EMP001</td>
    <td><img src="..." alt="John Doe"></td>
    <td>John Doe</td>
    <td>Engineering</td>
    <td>Senior Developer</td>
    <td><a href="mailto:john@company.com">john@company.com</a></td>
    <td><a href="tel:+1234567890">123-456-7890</a></td>
    <td><time datetime="2022-03-15">Mar 15, 2022</time></td>
    <td><span class="status active">Active</span></td>
</tr>
```

## Expected Output Structure

```
+------------------------------------------------------------------------+
|                  Company Employee Directory - 2024                      |
+------+-------+------------+------------+----------+-------+------+-----+
|  ID  | Photo |    Name    | Department | Position | Email | Phone| Date|
+------+-------+------------+------------+----------+-------+------+-----+
| E001 |  [img]| John Doe   | Engineering| Sr. Dev  | ...   | ...  | ... |
| E002 |  [img]| Jane Smith | Marketing  | Manager  | ...   | ...  | ... |
| ...  |  ...  | ...        | ...        | ...      | ...   | ...  | ... |
+------+-------+------------+------------+----------+-------+------+-----+
|                    Total Employees: 8 | Engineering: 3 | Marketing: 2   |
+------------------------------------------------------------------------+
```

## Bonus Challenges

1. **Sortable Headers**: Add `data-sort` attributes to headers
2. **Row Grouping**: Group rows by department using `rowspan`
3. **Responsive Table**: Wrap in a scrollable container
4. **Status Colors**: Use different classes for status (active/leave/remote)

## Skills Tested
- Table structure (`table`, `thead`, `tbody`, `tfoot`)
- Table cells (`th`, `td`) with attributes
- Accessibility (`scope`, `aria-label`)
- Semantic elements (`time`, `a` with mailto/tel)
- Data attributes

## Validation
Your HTML should pass the W3C Validator without errors.

---

## Progressive Enhancement Versions

Complete this exercise in 3 versions to demonstrate progressive enhancement:

### Version 1: Raw HTML
**File:** `employee-table-v1.html`

Build the employee table using only semantic HTML5 - no styling.

**Focus on:**
- Proper table structure (thead, tbody, tfoot)
- Scope attributes for accessibility
- Data attributes for filtering hooks
- Semantic elements for page layout

**Expected output:** A functional but unstyled table.

---

### Version 2: HTML + CSS3
**Files:** `employee-table-v2.html`, `employee-table-v2.css`

Enhance Version 1 with custom CSS3 styling.

**Add these CSS features:**
- Table styling (borders, striped rows, hover effects)
- Sticky table header
- Status badges with different colors
- Responsive table (horizontal scroll on mobile)
- Avatar image styling (rounded)
- Custom scrollbar styling

**CSS concepts to demonstrate:**
```css
/* Example CSS features to use */
table { border-collapse: collapse; width: 100%; }
thead { position: sticky; top: 0; }
tr:nth-child(even) { background-color: #f8f9fa; }
tr:hover { background-color: #e9ecef; }
.status-active { background-color: #28a745; color: white; }
@media (max-width: 768px) {
    .table-responsive { overflow-x: auto; }
}
```

---

### Version 3: HTML + Bootstrap 5
**File:** `employee-table-v3.html`

Rebuild using Bootstrap 5 framework.

**Use these Bootstrap components:**
- Table classes (table, table-striped, table-hover, table-bordered)
- Table responsive wrapper
- Badge component for status
- Avatar with rounded-circle class
- Button groups for actions
- Pagination component (bonus)

**Example Bootstrap table:**
```html
<div class="table-responsive">
    <table class="table table-striped table-hover">
        <thead class="table-dark">
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Name</th>
                <!-- ... -->
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>E001</td>
                <td>
                    <img src="avatar.jpg" class="rounded-circle" width="40">
                    John Doe
                </td>
                <td><span class="badge bg-success">Active</span></td>
            </tr>
        </tbody>
    </table>
</div>
```

---

## Submission

### Required Files
| File | Description |
|------|-------------|
| `employee-table-v1.html` | Raw HTML version |
| `employee-table-v2.html` | CSS3 enhanced version |
| `employee-table-v2.css` | CSS3 stylesheet |
| `employee-table-v3.html` | Bootstrap version |

### Folder Structure
```
your-repo/
└── 04-html/
    ├── employee-table-v1.html
    ├── employee-table-v2.html
    ├── employee-table-v2.css
    └── employee-table-v3.html
```

### Evaluation Criteria
| Criteria | Points |
|----------|--------|
| **Version 1 (Raw HTML)** | **30** |
| Table structure correct | 15 |
| Accessibility (scope, aria) | 15 |
| **Version 2 (CSS3)** | **35** |
| Table styling & hover | 15 |
| Responsive design | 10 |
| Status badges styled | 10 |
| **Version 3 (Bootstrap)** | **35** |
| Bootstrap table classes | 15 |
| Components used correctly | 10 |
| Responsive wrapper | 10 |
| **Total** | **100** |

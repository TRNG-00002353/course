# Code Challenges: MySQL

SQL query challenges to practice database skills. Write each solution in a separate `.sql` file.

---

## Setup

Use this schema for all challenges:

```sql
CREATE TABLE employees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    department VARCHAR(50),
    salary DECIMAL(10,2),
    hire_date DATE,
    manager_id INT
);

CREATE TABLE projects (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    budget DECIMAL(12,2),
    start_date DATE,
    end_date DATE
);

CREATE TABLE assignments (
    employee_id INT,
    project_id INT,
    role VARCHAR(50),
    hours_allocated INT,
    PRIMARY KEY (employee_id, project_id)
);
```

---

## Challenge 1: Department Stats
**File:** `department-stats.sql`

Write a query that returns each department with:
- Total employees
- Average salary (rounded to 2 decimals)
- Highest salary
- Only departments with more than 2 employees

**Expected columns:** `department`, `employee_count`, `avg_salary`, `max_salary`

---

## Challenge 2: Employee Hierarchy
**File:** `employee-hierarchy.sql`

Write a query to show employees with their manager's name.

**Expected columns:** `employee_name`, `manager_name`

**Hint:** Use self-join. Show `NULL` if no manager.

---

## Challenge 3: Project Workload
**File:** `project-workload.sql`

Find projects with total allocated hours and number of team members.
Only include projects with budget > 50000.
Order by total hours descending.

**Expected columns:** `project_name`, `budget`, `team_size`, `total_hours`

---

## Challenge 4: Top Earners
**File:** `top-earners.sql`

Write a query to find the top 3 highest-paid employees per department using window functions.

**Expected columns:** `department`, `name`, `salary`, `rank_in_dept`

**Hint:** Use `ROW_NUMBER()` or `DENSE_RANK()`

---

## Challenge 5: Unassigned Resources
**File:** `unassigned-resources.sql`

Find all employees who are NOT assigned to any project.

**Expected columns:** `id`, `name`, `department`

**Hint:** Use `LEFT JOIN` or `NOT EXISTS`

---

## Challenge 6: Employee Name Formatter
**File:** `name-formatter.sql`

Write a query using string functions that returns:
- Employee name formatted as "LAST, First" (uppercase last name)
- Email generated as firstname.lastname@company.com (all lowercase)
- Initials (first letter of first and last name)

**Expected columns:** `formatted_name`, `email`, `initials`

**Example output:** `SMITH, John`, `john.smith@company.com`, `JS`

---

## Challenge 7: Tenure Calculator Function
**File:** `tenure-function.sql`

Create a user-defined function `GetTenureCategory(hire_date DATE)` that returns:
- 'Veteran' if hired more than 5 years ago
- 'Experienced' if hired 2-5 years ago
- 'New Hire' if hired less than 2 years ago

Then write a query using this function to show all employees with their tenure category.

**Expected columns:** `name`, `hire_date`, `tenure_category`

---

## Challenge 8: Salary Adjustment Procedure
**File:** `salary-adjustment.sql`

Create a stored procedure `AdjustDepartmentSalary(IN dept VARCHAR(50), IN percent DECIMAL(5,2), OUT affected_count INT)` that:
- Increases salary by the given percentage for all employees in the department
- Returns the count of affected employees via OUT parameter
- Does nothing if percentage is negative (safety check)

Include test calls demonstrating the procedure.

---

## Challenge 9: Budget Utilization Procedure
**File:** `budget-utilization.sql`

Create a stored procedure `GetBudgetUtilization()` that returns each project with:
- Project name and budget
- Total salary cost (sum of employee salaries × hours allocated / 2080)
- Remaining budget
- Utilization percentage

**Expected columns:** `project_name`, `budget`, `salary_cost`, `remaining`, `utilization_pct`

**Hint:** Assume 2080 working hours per year for salary calculation.

---

## Challenge 10: Salary Change Audit Trigger
**File:** `salary-audit-trigger.sql`

First create this audit table:
```sql
CREATE TABLE salary_audit (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id INT,
    old_salary DECIMAL(10,2),
    new_salary DECIMAL(10,2),
    change_percent DECIMAL(5,2),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

Then create a trigger `trg_salary_audit` that:
- Fires AFTER UPDATE on employees
- Only logs when salary actually changes
- Calculates and stores the percentage change

Include INSERT/UPDATE statements to demonstrate the trigger working.

---

## Challenge 11: Project Assignment Validation Trigger
**File:** `assignment-validation-trigger.sql`

Create a trigger `trg_validate_assignment` (BEFORE INSERT on assignments) that:
- Prevents an employee from being assigned more than 3 projects
- Prevents total hours_allocated exceeding 2080 across all projects
- Uses SIGNAL to return meaningful error messages

Include test cases showing both successful inserts and blocked inserts.

---

## Challenge 12: Date-Based Report Function
**File:** `date-report-function.sql`

Create a function `GetProjectStatus(project_id INT)` that returns:
- 'Not Started' if start_date is in the future
- 'In Progress' if between start_date and end_date
- 'Completed' if end_date is in the past
- 'Overdue' if end_date is past but project not marked complete
- 'Unknown' if project not found

Write a query showing all projects with their calculated status.

**Expected columns:** `name`, `start_date`, `end_date`, `status`

---

## Submission
Place all challenge files in: `09-mysql/challenges/`

| Challenge | File | Status |
|-----------|------|--------|
| Department Stats | `department-stats.sql` | ⬜ |
| Employee Hierarchy | `employee-hierarchy.sql` | ⬜ |
| Project Workload | `project-workload.sql` | ⬜ |
| Top Earners | `top-earners.sql` | ⬜ |
| Unassigned Resources | `unassigned-resources.sql` | ⬜ |
| Employee Name Formatter | `name-formatter.sql` | ⬜ |
| Tenure Calculator Function | `tenure-function.sql` | ⬜ |
| Salary Adjustment Procedure | `salary-adjustment.sql` | ⬜ |
| Budget Utilization Procedure | `budget-utilization.sql` | ⬜ |
| Salary Change Audit Trigger | `salary-audit-trigger.sql` | ⬜ |
| Project Assignment Validation | `assignment-validation-trigger.sql` | ⬜ |
| Date-Based Report Function | `date-report-function.sql` | ⬜ |

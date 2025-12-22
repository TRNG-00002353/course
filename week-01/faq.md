# Week 01 - Interview FAQ

This document contains frequently asked interview questions and comprehensive answers for Week 01 topics: Linux, Git, Agile, HTML, and CSS.

---

## Table of Contents

1. [Linux Fundamentals](#linux-fundamentals)
2. [Git Version Control](#git-version-control)
3. [Agile Methodology](#agile-methodology)
4. [HTML5](#html5)
5. [CSS3](#css3)

---

## Linux Fundamentals

### Q1: What is Linux? Why is it popular in enterprise environments?

**Answer:**
Linux is an open-source, Unix-like operating system kernel created by Linus Torvalds in 1991. It forms the foundation of many operating systems called "distributions" (e.g., Ubuntu, CentOS, Red Hat).

**Why it's popular in enterprises:**
- **Cost-effective**: Free to use and distribute
- **Security**: Strong permission system, fewer vulnerabilities than proprietary systems
- **Stability**: Can run for years without rebooting
- **Flexibility**: Highly customizable for specific needs
- **Server dominance**: Powers majority of web servers, cloud infrastructure, and containers
- **Community support**: Large developer community for troubleshooting

---

### Q2: Explain the Linux file system hierarchy.

**Answer:**
Linux follows the Filesystem Hierarchy Standard (FHS):

| Directory | Purpose |
|-----------|---------|
| `/` | Root directory, top of the hierarchy |
| `/home` | User home directories |
| `/etc` | System configuration files |
| `/var` | Variable data (logs, databases, mail) |
| `/tmp` | Temporary files (cleared on reboot) |
| `/usr` | User programs and utilities |
| `/bin` | Essential user command binaries |
| `/sbin` | System binaries (admin commands) |
| `/opt` | Optional/third-party software |
| `/dev` | Device files |
| `/proc` | Virtual filesystem for process info |

**Key point:** Everything in Linux is either a file or a directory, including hardware devices.

---

### Q3: What are file permissions in Linux? How do you modify them?

**Answer:**
Linux file permissions control who can read, write, or execute files.

**Three permission types:**
- **r (read)** = 4: View file contents
- **w (write)** = 2: Modify file contents
- **x (execute)** = 1: Run as program

**Three user categories:**
- **Owner (u)**: File creator
- **Group (g)**: Users in the file's group
- **Others (o)**: Everyone else

**Reading permissions:**
```
-rwxr-xr--
│└┬┘└┬┘└┬┘
│ │  │  └── Others: read only (4)
│ │  └───── Group: read + execute (5)
│ └──────── Owner: read + write + execute (7)
└────────── File type (- = file, d = directory)
```

**Modifying permissions:**
```bash
chmod 755 file.sh      # Octal notation
chmod u+x file.sh      # Symbolic: add execute for owner
chmod go-w file.sh     # Remove write for group and others
```

---

### Q4: What is the difference between a process and a thread?

**Answer:**

| Aspect | Process | Thread |
|--------|---------|--------|
| Definition | Independent program in execution | Lightweight unit within a process |
| Memory | Has its own memory space | Shares memory with other threads |
| Communication | Inter-process communication (IPC) required | Direct memory sharing |
| Creation overhead | Higher (new memory allocation) | Lower (shares parent's resources) |
| Isolation | Crash doesn't affect other processes | Crash can affect entire process |

**Commands to view:**
- `ps aux` - List all processes
- `top` or `htop` - Real-time process monitoring
- `kill PID` - Terminate a process

---

### Q5: Explain input/output redirection and piping.

**Answer:**

**Redirection** changes where input comes from or output goes to:

```bash
# Output redirection
command > file.txt      # Overwrite file with output
command >> file.txt     # Append output to file
command 2> error.txt    # Redirect errors (stderr)
command &> all.txt      # Redirect both stdout and stderr

# Input redirection
command < input.txt     # Read input from file
```

**Piping** connects output of one command to input of another:

```bash
ls -la | grep ".txt"           # Find .txt files in listing
cat file.txt | wc -l           # Count lines in file
ps aux | grep nginx | wc -l    # Count nginx processes
```

**Key difference:** Redirection works with files; piping works between commands.

---

### Q6: What is a shell script? What's the purpose of the shebang?

**Answer:**

A **shell script** is a text file containing a sequence of commands that the shell executes. It automates repetitive tasks.

**Shebang** (`#!`) is the first line specifying the interpreter:

```bash
#!/bin/bash
# This script runs with Bash

echo "Hello, World!"
```

**Common shebangs:**
- `#!/bin/bash` - Bash shell
- `#!/bin/sh` - POSIX shell
- `#!/usr/bin/python3` - Python 3
- `#!/usr/bin/env node` - Node.js (portable)

**Making a script executable:**
```bash
chmod +x script.sh
./script.sh
```

---

### Q7: How do you find files and search content in Linux?

**Answer:**

**Finding files by name/attributes (`find`):**
```bash
find /path -name "*.log"           # Find by name pattern
find /path -type f -size +100M     # Files larger than 100MB
find /path -mtime -7               # Modified in last 7 days
find /path -user john              # Files owned by john
```

**Searching content within files (`grep`):**
```bash
grep "error" logfile.txt           # Find lines containing "error"
grep -r "TODO" ./src               # Recursive search in directory
grep -i "warning" file.txt         # Case-insensitive search
grep -n "pattern" file.txt         # Show line numbers
grep -c "error" file.txt           # Count matches
```

**Quick file location (`locate`):**
```bash
locate filename                     # Fast search using database
updatedb                           # Update the locate database
```

---

### Q8: What are environment variables? Name some important ones.

**Answer:**

Environment variables are dynamic values that affect process behavior.

**Important variables:**

| Variable | Purpose |
|----------|---------|
| `$PATH` | Directories searched for executables |
| `$HOME` | Current user's home directory |
| `$USER` | Current username |
| `$SHELL` | Current shell path |
| `$PWD` | Current working directory |
| `$LANG` | System language/locale |

**Working with variables:**
```bash
echo $PATH                    # Display variable
export MY_VAR="value"         # Set for current session
echo 'export VAR="x"' >> ~/.bashrc  # Permanent setting
```

---

## Git Version Control

### Q9: What is Git? How does it differ from other version control systems?

**Answer:**

Git is a **distributed version control system (DVCS)** created by Linus Torvalds in 2005.

**Key differences from centralized VCS (like SVN):**

| Feature | Git (Distributed) | SVN (Centralized) |
|---------|------------------|-------------------|
| Repository | Full copy on each machine | Single central server |
| Offline work | Full functionality | Limited (need server) |
| Speed | Fast (local operations) | Slower (network dependent) |
| Branching | Lightweight, encouraged | Heavy, discouraged |
| Single point of failure | None | Server is critical |

**Core concepts:**
- **Repository**: Project folder tracked by Git
- **Commit**: Snapshot of changes
- **Branch**: Independent line of development
- **Remote**: Server-hosted repository copy

---

### Q10: Explain the three states of files in Git.

**Answer:**

Git tracks files in three states:

```
Working Directory → Staging Area → Repository
    (Modified)        (Staged)      (Committed)
```

1. **Modified**: File changed but not staged
   - Changes exist only in working directory
   - `git status` shows as "Changes not staged"

2. **Staged**: File marked for next commit
   - Added with `git add`
   - `git status` shows as "Changes to be committed"

3. **Committed**: File safely stored in local database
   - Saved with `git commit`
   - Part of project history

**Workflow:**
```bash
# Edit file (Modified)
vim file.txt

# Stage changes (Staged)
git add file.txt

# Commit changes (Committed)
git commit -m "Update file"
```

---

### Q11: What is the difference between `git merge` and `git rebase`?

**Answer:**

Both integrate changes from one branch to another, but differently:

**Git Merge:**
- Creates a new "merge commit"
- Preserves complete history
- Non-destructive operation

```
Before:       After merge:
A---B---C     A---B---C---M (merge commit)
     \             \     /
      D---E         D---E
```

**Git Rebase:**
- Moves commits to new base
- Creates linear history
- Rewrites commit history

```
Before:       After rebase:
A---B---C     A---B---C---D'---E'
     \
      D---E
```

**When to use:**
- **Merge**: Public/shared branches, preserve history
- **Rebase**: Local/feature branches, clean linear history

**Golden rule:** Never rebase public branches that others are using.

---

### Q12: How do you resolve a merge conflict?

**Answer:**

Merge conflicts occur when Git can't automatically merge changes.

**Steps to resolve:**

1. **Identify conflicts:**
```bash
git status  # Shows conflicted files
```

2. **Open conflicted file:**
```
<<<<<<< HEAD
Your changes
=======
Their changes
>>>>>>> branch-name
```

3. **Edit file** to keep desired changes (remove conflict markers)

4. **Stage resolved file:**
```bash
git add resolved-file.txt
```

5. **Complete the merge:**
```bash
git commit -m "Resolve merge conflict"
```

**Prevention tips:**
- Pull frequently to stay updated
- Communicate with team about file ownership
- Use smaller, focused commits

---

### Q13: Explain `git fetch` vs `git pull`.

**Answer:**

| Aspect | git fetch | git pull |
|--------|-----------|----------|
| Action | Downloads remote changes | Downloads AND merges |
| Working directory | Unchanged | Updated |
| Safety | Safe, review first | May cause conflicts |
| Use case | Check what's new | Quick sync |

**git fetch:**
```bash
git fetch origin           # Download remote changes
git log origin/main        # Review changes
git merge origin/main      # Merge when ready
```

**git pull:**
```bash
git pull origin main       # Fetch + merge in one step
# Equivalent to:
git fetch origin && git merge origin/main
```

**Best practice:** Use `fetch` first to review changes, then merge or rebase.

---

### Q14: What is a pull request (PR)? What's the typical workflow?

**Answer:**

A **pull request** is a request to merge code changes from one branch to another, enabling code review before integration.

**Typical workflow:**

1. **Create feature branch:**
```bash
git checkout -b feature/new-login
```

2. **Make changes and commit:**
```bash
git add .
git commit -m "Add new login feature"
```

3. **Push to remote:**
```bash
git push -u origin feature/new-login
```

4. **Create PR** on GitHub/GitLab:
   - Compare branches
   - Add description
   - Request reviewers

5. **Code review process:**
   - Reviewers comment/approve
   - Author addresses feedback
   - CI/CD runs tests

6. **Merge after approval:**
   - Squash, merge, or rebase
   - Delete feature branch

**PR best practices:**
- Keep PRs small and focused
- Write clear descriptions
- Link related issues
- Respond promptly to feedback

---

### Q15: What should be included in a `.gitignore` file?

**Answer:**

`.gitignore` specifies files Git should not track.

**Common patterns to ignore:**

```gitignore
# Dependencies
node_modules/
vendor/
.venv/

# Build outputs
dist/
build/
*.class
*.o

# IDE/Editor files
.idea/
.vscode/
*.swp

# Environment files
.env
.env.local
*.pem

# OS files
.DS_Store
Thumbs.db

# Logs
*.log
logs/

# Temporary files
tmp/
*.tmp
```

**Pattern syntax:**
- `*` - Match any characters
- `**` - Match nested directories
- `!` - Negate (don't ignore)
- `/` at end - Directory only

**Best practice:** Add `.gitignore` before first commit to avoid tracking unwanted files.

---

### Q16: How do you undo changes in Git?

**Answer:**

Different commands for different situations:

**Discard working directory changes:**
```bash
git restore file.txt           # Discard uncommitted changes
git restore .                  # Discard all changes
```

**Unstage files:**
```bash
git restore --staged file.txt  # Remove from staging
git reset HEAD file.txt        # Alternative method
```

**Undo last commit (keep changes):**
```bash
git reset --soft HEAD~1        # Undo commit, keep staged
git reset --mixed HEAD~1       # Undo commit, unstage changes
```

**Undo last commit (discard changes):**
```bash
git reset --hard HEAD~1        # Undo commit, delete changes
```

**Undo a pushed commit (safe):**
```bash
git revert <commit-hash>       # Creates new commit that undoes changes
```

**Recover deleted branch:**
```bash
git reflog                     # Find commit hash
git checkout -b branch-name <hash>
```

---

## Agile Methodology

### Q17: What is Agile? What are its core values?

**Answer:**

**Agile** is an iterative approach to software development that delivers value incrementally through collaboration and adaptation.

**The 4 Agile Manifesto Values:**

1. **Individuals and interactions** over processes and tools
2. **Working software** over comprehensive documentation
3. **Customer collaboration** over contract negotiation
4. **Responding to change** over following a plan

**Key characteristics:**
- Iterative development in short cycles
- Frequent delivery of working software
- Embrace changing requirements
- Close collaboration with stakeholders
- Self-organizing teams
- Continuous improvement

**Popular Agile frameworks:**
- Scrum
- Kanban
- Extreme Programming (XP)
- Lean

---

### Q18: Explain the Scrum framework and its key components.

**Answer:**

**Scrum** is an Agile framework for managing work in fixed-length iterations called Sprints.

**Three Pillars:**
- **Transparency**: Visible process and progress
- **Inspection**: Regular review of progress
- **Adaptation**: Adjust based on findings

**Scrum Roles:**

| Role | Responsibility |
|------|----------------|
| Product Owner | Maximizes value, manages backlog, stakeholder voice |
| Scrum Master | Facilitates process, removes impediments, coaches team |
| Development Team | Self-organizing, cross-functional, delivers increment |

**Scrum Artifacts:**

| Artifact | Purpose |
|----------|---------|
| Product Backlog | Prioritized list of all desired features |
| Sprint Backlog | Items selected for current sprint + plan |
| Increment | Usable product at sprint end (meets DoD) |

**Scrum Events:**
1. Sprint Planning
2. Daily Standup
3. Sprint Review
4. Sprint Retrospective
5. Backlog Refinement (ongoing)

---

### Q19: What happens in each Scrum ceremony?

**Answer:**

**1. Sprint Planning** (Start of Sprint)
- **Duration**: 2-4 hours for 2-week sprint
- **Purpose**: Select backlog items, create sprint goal
- **Outcome**: Sprint backlog with committed items

**2. Daily Standup** (Daily)
- **Duration**: 15 minutes max
- **Purpose**: Synchronize work, identify blockers
- **Format**: What I did, what I'll do, any blockers

**3. Sprint Review** (End of Sprint)
- **Duration**: 1-2 hours
- **Purpose**: Demo completed work to stakeholders
- **Outcome**: Feedback for backlog refinement

**4. Sprint Retrospective** (End of Sprint)
- **Duration**: 1-1.5 hours
- **Purpose**: Reflect on process, identify improvements
- **Format**: What went well, what didn't, action items

**5. Backlog Refinement** (Ongoing)
- **Duration**: ~10% of sprint capacity
- **Purpose**: Clarify, estimate, prioritize backlog items
- **Outcome**: Ready items for future sprints

---

### Q20: How do you write a good user story?

**Answer:**

**User Story Format:**
```
As a [type of user],
I want [an action/feature],
So that [benefit/value].
```

**Example:**
```
As a registered customer,
I want to reset my password via email,
So that I can regain access to my account.
```

**INVEST Criteria:**
- **I**ndependent: Can be developed separately
- **N**egotiable: Details can be discussed
- **V**aluable: Delivers value to user/business
- **E**stimable: Team can estimate effort
- **S**mall: Fits in one sprint
- **T**estable: Has clear acceptance criteria

**Acceptance Criteria Example:**
```
Given I am on the login page
When I click "Forgot Password" and enter my email
Then I receive a password reset link within 5 minutes
And the link expires after 24 hours
```

---

### Q21: What are Story Points? How is estimation done in Agile?

**Answer:**

**Story Points** are relative units measuring effort, complexity, and uncertainty.

**Why not hours?**
- People estimate time poorly
- Relative sizing is more accurate
- Accounts for complexity, not just time

**Fibonacci Sequence** (1, 2, 3, 5, 8, 13, 21):
- Gaps increase with size
- Reflects uncertainty in larger items
- Forces meaningful distinctions

**Planning Poker Process:**
1. Product Owner presents story
2. Team discusses and asks questions
3. Each member privately selects card
4. All reveal simultaneously
5. Discuss differences (especially outliers)
6. Re-vote until consensus

**Velocity:**
- Average story points completed per sprint
- Used for sprint capacity planning
- Stabilizes after 3-4 sprints

---

### Q22: What is the difference between Scrum and Kanban?

**Answer:**

| Aspect | Scrum | Kanban |
|--------|-------|--------|
| Iterations | Fixed sprints (1-4 weeks) | Continuous flow |
| Roles | PO, SM, Dev Team | No prescribed roles |
| Planning | Sprint planning at start | Continuous prioritization |
| Changes | Not during sprint | Anytime |
| Metrics | Velocity, burndown | Lead time, cycle time |
| WIP Limits | Sprint capacity | Per column limits |
| Board | Reset each sprint | Persistent |
| Ceremonies | Defined events | Optional |

**When to use Scrum:**
- New teams learning Agile
- Projects with defined features
- Need regular delivery cadence

**When to use Kanban:**
- Support/maintenance work
- Unpredictable priorities
- Continuous delivery needed

---

### Q23: What is a Sprint Retrospective? Why is it important?

**Answer:**

The **Sprint Retrospective** is a meeting where the team reflects on their process and identifies improvements.

**Structure:**
1. **What went well?** (Keep doing)
2. **What didn't go well?** (Stop doing)
3. **What can we improve?** (Action items)

**Popular formats:**
- Start/Stop/Continue
- Mad/Sad/Glad
- 4Ls (Liked, Learned, Lacked, Longed for)
- Sailboat (Wind = helps, Anchors = slows)

**Why it's important:**
- Enables continuous improvement
- Builds team trust and communication
- Addresses issues before they grow
- Empowers team ownership
- Improves future sprint performance

**Best practices:**
- Safe environment for honest feedback
- Focus on process, not individuals
- Limit to 2-3 actionable improvements
- Follow up on previous action items
- Rotate facilitation

---

## HTML5

### Q24: What is semantic HTML? Why is it important?

**Answer:**

**Semantic HTML** uses elements that clearly describe their meaning and purpose.

**Semantic vs Non-semantic:**

| Semantic | Non-semantic |
|----------|--------------|
| `<header>` | `<div class="header">` |
| `<nav>` | `<div id="navigation">` |
| `<main>` | `<div class="content">` |
| `<article>` | `<div class="post">` |
| `<footer>` | `<div id="footer">` |

**Key semantic elements:**
- `<header>` - Introductory content
- `<nav>` - Navigation links
- `<main>` - Primary content
- `<article>` - Self-contained content
- `<section>` - Thematic grouping
- `<aside>` - Sidebar content
- `<footer>` - Footer information
- `<figure>` / `<figcaption>` - Images with captions

**Why it matters:**
1. **Accessibility**: Screen readers understand page structure
2. **SEO**: Search engines better understand content
3. **Maintainability**: Code is self-documenting
4. **Consistency**: Standard structure across projects

---

### Q25: What is the difference between block and inline elements?

**Answer:**

**Block elements:**
- Start on a new line
- Take full available width
- Can contain other block and inline elements
- Examples: `<div>`, `<p>`, `<h1>`, `<section>`, `<form>`

**Inline elements:**
- Don't start on new line
- Only take needed width
- Can only contain data or other inline elements
- Examples: `<span>`, `<a>`, `<strong>`, `<em>`, `<img>`

**Visual comparison:**
```
Block:    |████████████████████|
          |████████████████████|

Inline:   |██ ███ ████ ██ ███ |
```

**Inline-block:** Hybrid that flows inline but accepts width/height

```css
.element {
  display: inline-block;
  width: 200px;
  height: 100px;
}
```

---

### Q26: Explain HTML5 form validation attributes.

**Answer:**

HTML5 provides built-in form validation without JavaScript:

**Validation attributes:**

| Attribute | Purpose | Example |
|-----------|---------|---------|
| `required` | Field must be filled | `<input required>` |
| `type` | Validates format | `type="email"` |
| `pattern` | Custom regex pattern | `pattern="[A-Za-z]+"` |
| `min` / `max` | Number/date range | `min="0" max="100"` |
| `minlength` / `maxlength` | Text length | `maxlength="50"` |
| `step` | Number increment | `step="0.01"` |

**Input types with validation:**
```html
<input type="email">      <!-- Email format -->
<input type="url">        <!-- URL format -->
<input type="tel">        <!-- Phone pattern -->
<input type="number">     <!-- Numeric only -->
<input type="date">       <!-- Date picker -->
```

**Custom pattern example:**
```html
<input type="text"
       pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"
       title="Format: 123-456-7890"
       placeholder="Phone number">
```

**Styling validation states:**
```css
input:valid { border-color: green; }
input:invalid { border-color: red; }
```

---

### Q27: What is the difference between `localStorage` and `sessionStorage`?

**Answer:**

Both are Web Storage APIs for client-side data storage.

| Feature | localStorage | sessionStorage |
|---------|--------------|----------------|
| Persistence | Until explicitly deleted | Until tab/window closes |
| Scope | Shared across all tabs | Per tab only |
| Capacity | ~5-10 MB | ~5-10 MB |
| Accessible | Same origin only | Same origin + same tab |

**Usage:**
```javascript
// localStorage
localStorage.setItem('user', 'John');
localStorage.getItem('user');      // "John"
localStorage.removeItem('user');
localStorage.clear();              // Remove all

// sessionStorage (same API)
sessionStorage.setItem('token', 'abc123');
```

**Storing objects:**
```javascript
const user = { name: 'John', age: 30 };
localStorage.setItem('user', JSON.stringify(user));
const retrieved = JSON.parse(localStorage.getItem('user'));
```

**Use cases:**
- **localStorage**: User preferences, themes, shopping cart
- **sessionStorage**: Form data, temporary state, wizard progress

---

### Q28: What are ARIA attributes and why are they important?

**Answer:**

**ARIA** (Accessible Rich Internet Applications) attributes enhance accessibility for assistive technologies.

**Why needed:**
- HTML alone can't describe all interactive behaviors
- Custom widgets need accessibility info
- Screen readers need context

**Key ARIA attributes:**

| Attribute | Purpose | Example |
|-----------|---------|---------|
| `role` | Defines element type | `role="button"` |
| `aria-label` | Accessible name | `aria-label="Close"` |
| `aria-labelledby` | Reference to label | `aria-labelledby="title"` |
| `aria-describedby` | Reference to description | `aria-describedby="hint"` |
| `aria-hidden` | Hide from screen readers | `aria-hidden="true"` |
| `aria-expanded` | Expandable state | `aria-expanded="false"` |
| `aria-live` | Dynamic content updates | `aria-live="polite"` |

**Example - Custom button:**
```html
<div role="button"
     tabindex="0"
     aria-pressed="false"
     aria-label="Toggle dark mode">
  🌙
</div>
```

**Best practices:**
1. Use semantic HTML first (ARIA as supplement)
2. Don't change native semantics unnecessarily
3. Interactive elements must be keyboard accessible
4. Test with screen readers

---

### Q29: Explain the HTML document structure.

**Answer:**

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Page description for SEO">
    <title>Page Title</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <header>
        <nav><!-- Navigation --></nav>
    </header>

    <main>
        <article>
            <h1>Main Heading</h1>
            <section><!-- Content sections --></section>
        </article>
        <aside><!-- Sidebar --></aside>
    </main>

    <footer><!-- Footer content --></footer>

    <script src="app.js"></script>
</body>
</html>
```

**Key components:**
- `<!DOCTYPE html>` - HTML5 declaration
- `<html lang="en">` - Root element with language
- `<head>` - Metadata, title, styles, scripts
- `<meta charset>` - Character encoding
- `<meta viewport>` - Responsive design
- `<body>` - Visible content
- Scripts at bottom for performance

---

## CSS3

### Q30: Explain the CSS Box Model.

**Answer:**

Every HTML element is a rectangular box with four layers:

```
┌──────────────────────────────────────┐
│              MARGIN                  │
│   ┌──────────────────────────────┐   │
│   │          BORDER              │   │
│   │   ┌──────────────────────┐   │   │
│   │   │      PADDING         │   │   │
│   │   │   ┌──────────────┐   │   │   │
│   │   │   │   CONTENT    │   │   │   │
│   │   │   └──────────────┘   │   │   │
│   │   └──────────────────────┘   │   │
│   └──────────────────────────────┘   │
└──────────────────────────────────────┘
```

**Components:**
- **Content**: Actual content (text, images)
- **Padding**: Space between content and border
- **Border**: Edge around padding
- **Margin**: Space outside the border

**Box-sizing:**
```css
/* Default - width/height = content only */
box-sizing: content-box;

/* Modern - width/height includes padding + border */
box-sizing: border-box;
```

**Best practice:**
```css
*, *::before, *::after {
  box-sizing: border-box;
}
```

---

### Q31: What is CSS specificity? How is it calculated?

**Answer:**

**Specificity** determines which CSS rule applies when multiple rules target the same element.

**Specificity hierarchy (highest to lowest):**
1. Inline styles (`style="..."`)
2. IDs (`#id`)
3. Classes, attributes, pseudo-classes (`.class`, `[attr]`, `:hover`)
4. Elements, pseudo-elements (`div`, `::before`)

**Calculation format: (a, b, c, d)**
- a = inline styles
- b = ID selectors
- c = class/attribute/pseudo-class selectors
- d = element/pseudo-element selectors

**Examples:**
```css
p { }                        /* (0,0,0,1) */
.class { }                   /* (0,0,1,0) */
#id { }                      /* (0,1,0,0) */
div.class { }                /* (0,0,1,1) */
#id .class p { }             /* (0,1,1,1) */
style="color: red"           /* (1,0,0,0) */
```

**Rules:**
- Higher specificity wins
- Equal specificity: last rule wins
- `!important` overrides all (avoid using)

---

### Q32: Explain Flexbox and its main properties.

**Answer:**

**Flexbox** is a one-dimensional layout model for arranging items in rows or columns.

**Container properties:**
```css
.container {
  display: flex;

  /* Main axis direction */
  flex-direction: row | column | row-reverse | column-reverse;

  /* Wrapping behavior */
  flex-wrap: nowrap | wrap | wrap-reverse;

  /* Main axis alignment */
  justify-content: flex-start | flex-end | center |
                   space-between | space-around | space-evenly;

  /* Cross axis alignment */
  align-items: stretch | flex-start | flex-end | center | baseline;

  /* Multi-line alignment */
  align-content: flex-start | flex-end | center |
                 space-between | space-around | stretch;

  /* Gap between items */
  gap: 10px;
}
```

**Item properties:**
```css
.item {
  flex-grow: 1;      /* Grow factor */
  flex-shrink: 1;    /* Shrink factor */
  flex-basis: auto;  /* Initial size */

  /* Shorthand */
  flex: 1 1 auto;    /* grow shrink basis */

  /* Individual alignment */
  align-self: auto | flex-start | flex-end | center;

  /* Order */
  order: 0;
}
```

**Common patterns:**
```css
/* Center everything */
.center {
  display: flex;
  justify-content: center;
  align-items: center;
}

/* Space between with wrap */
.cards {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}
```

---

### Q33: Explain CSS Grid and its main properties.

**Answer:**

**CSS Grid** is a two-dimensional layout system for creating complex layouts.

**Container properties:**
```css
.container {
  display: grid;

  /* Define columns */
  grid-template-columns: 200px 1fr 1fr;
  grid-template-columns: repeat(3, 1fr);
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));

  /* Define rows */
  grid-template-rows: 100px auto 50px;

  /* Gap between cells */
  gap: 20px;
  row-gap: 20px;
  column-gap: 10px;

  /* Alignment */
  justify-items: start | end | center | stretch;
  align-items: start | end | center | stretch;

  /* Content alignment */
  justify-content: start | end | center | space-between;
  align-content: start | end | center | space-between;
}
```

**Item properties:**
```css
.item {
  /* Positioning */
  grid-column: 1 / 3;        /* Start / end line */
  grid-column: span 2;       /* Span 2 columns */
  grid-row: 1 / 2;

  /* Shorthand */
  grid-area: 1 / 1 / 2 / 3;  /* row-start / col-start / row-end / col-end */

  /* Self alignment */
  justify-self: start | end | center | stretch;
  align-self: start | end | center | stretch;
}
```

**Named areas:**
```css
.container {
  grid-template-areas:
    "header header header"
    "sidebar main main"
    "footer footer footer";
}

.header { grid-area: header; }
.sidebar { grid-area: sidebar; }
.main { grid-area: main; }
.footer { grid-area: footer; }
```

---

### Q34: How do you make a website responsive?

**Answer:**

**1. Viewport meta tag:**
```html
<meta name="viewport" content="width=device-width, initial-scale=1.0">
```

**2. Fluid layouts (relative units):**
```css
.container {
  width: 90%;
  max-width: 1200px;
  margin: 0 auto;
}

.column {
  width: 50%;  /* Instead of fixed pixels */
}
```

**3. Flexible images:**
```css
img {
  max-width: 100%;
  height: auto;
}
```

**4. Media queries:**
```css
/* Mobile first approach */
.container {
  padding: 10px;
}

/* Tablet and up */
@media (min-width: 768px) {
  .container {
    padding: 20px;
  }
}

/* Desktop and up */
@media (min-width: 1024px) {
  .container {
    padding: 40px;
  }
}
```

**5. Modern CSS units:**
- `%` - Relative to parent
- `vw/vh` - Viewport width/height
- `rem` - Relative to root font size
- `em` - Relative to parent font size
- `clamp()` - Responsive values with min/max

```css
.title {
  font-size: clamp(1.5rem, 4vw, 3rem);
}
```

**6. Flexbox/Grid for layouts:**
```css
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 20px;
}
```

---

### Q35: What is the difference between `position: relative`, `absolute`, `fixed`, and `sticky`?

**Answer:**

| Position | Behavior | Reference Point | In Document Flow? |
|----------|----------|-----------------|-------------------|
| `static` | Default, normal flow | N/A | Yes |
| `relative` | Offset from original position | Itself | Yes (space preserved) |
| `absolute` | Positioned precisely | Nearest positioned ancestor | No |
| `fixed` | Positioned to viewport | Viewport | No |
| `sticky` | Hybrid relative/fixed | Scroll container | Yes (until stuck) |

**Examples:**

```css
/* Relative - offset from original position */
.relative {
  position: relative;
  top: 10px;
  left: 20px;
}

/* Absolute - removed from flow, positioned to ancestor */
.parent {
  position: relative;  /* Creates positioning context */
}
.absolute {
  position: absolute;
  top: 0;
  right: 0;
}

/* Fixed - stays in viewport position */
.fixed-header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
}

/* Sticky - scrolls until threshold, then sticks */
.sticky-nav {
  position: sticky;
  top: 0;
}
```

**Common use cases:**
- **relative**: Positioning context for absolute children
- **absolute**: Tooltips, dropdowns, overlays
- **fixed**: Navigation bars, floating buttons
- **sticky**: Table headers, section headers

---

### Q36: How do CSS transitions and animations differ?

**Answer:**

**CSS Transitions:**
- Animate between two states
- Triggered by state change (hover, class change)
- Simpler syntax

```css
.button {
  background: blue;
  transition: background 0.3s ease, transform 0.2s ease;
}

.button:hover {
  background: red;
  transform: scale(1.1);
}
```

**Transition properties:**
```css
transition: property duration timing-function delay;
transition: all 0.3s ease-in-out 0s;
```

**CSS Animations:**
- Multiple keyframes
- Can run automatically
- More complex sequences
- Can loop infinitely

```css
@keyframes pulse {
  0% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(1.1);
    opacity: 0.7;
  }
  100% {
    transform: scale(1);
    opacity: 1;
  }
}

.pulse {
  animation: pulse 2s ease-in-out infinite;
}
```

**Animation properties:**
```css
animation: name duration timing-function delay iteration-count direction fill-mode;
animation: pulse 2s ease 0s infinite alternate forwards;
```

**When to use:**
- **Transitions**: Simple state changes (hover, active)
- **Animations**: Complex sequences, auto-playing, looping

---

### Q37: What are CSS custom properties (variables)?

**Answer:**

CSS custom properties store reusable values throughout stylesheets.

**Declaration:**
```css
:root {
  /* Global variables */
  --primary-color: #3498db;
  --secondary-color: #2ecc71;
  --font-size-base: 16px;
  --spacing-unit: 8px;
  --border-radius: 4px;
}
```

**Usage:**
```css
.button {
  background: var(--primary-color);
  padding: calc(var(--spacing-unit) * 2);
  border-radius: var(--border-radius);
}

.button-secondary {
  background: var(--secondary-color);
}
```

**Fallback values:**
```css
.element {
  color: var(--undefined-color, black);  /* Falls back to black */
}
```

**Scoped variables:**
```css
.dark-theme {
  --primary-color: #1a1a2e;
  --text-color: #ffffff;
}

.light-theme {
  --primary-color: #ffffff;
  --text-color: #1a1a2e;
}
```

**JavaScript access:**
```javascript
// Get value
getComputedStyle(document.documentElement)
  .getPropertyValue('--primary-color');

// Set value
document.documentElement.style
  .setProperty('--primary-color', '#ff0000');
```

**Benefits:**
- Centralized theming
- Dynamic updates with JavaScript
- Reduced repetition
- Easier maintenance

---

## Quick Reference Tips

### Linux Commands to Know
```bash
pwd, ls, cd, mkdir, rm, cp, mv, touch, cat,
grep, find, chmod, chown, ps, kill, top,
head, tail, echo, export, man
```

### Git Commands to Know
```bash
git init, clone, add, commit, status, log,
push, pull, fetch, branch, checkout, merge,
diff, stash, reset, revert, remote
```

### Agile Terms to Know
- Sprint, Backlog, Velocity, Story Points
- Scrum Master, Product Owner, Development Team
- User Story, Acceptance Criteria, Definition of Done
- Burndown Chart, Planning Poker, Retrospective

### HTML Elements to Know
- Semantic: `header, nav, main, article, section, aside, footer`
- Forms: `form, input, textarea, select, button, label, fieldset`
- Media: `img, video, audio, picture, figure`

### CSS Concepts to Know
- Box Model, Specificity, Cascade
- Flexbox, Grid, Media Queries
- Positioning, Transitions, Animations
- Pseudo-classes, Pseudo-elements, Variables

---

*Generated for Week 01 curriculum - Revature Training Program*

# Git Key Concepts for Application Developers

## Overview

This document covers essential Git concepts that every application developer must master. These skills are critical for managing code, collaborating with teams, and maintaining professional development workflows.

---

## 1. Repository Fundamentals

### Why It Matters
- Every project needs version control
- Track changes, revert mistakes, collaborate with others
- Essential for any professional development environment

### Key Concepts

| Concept | Description | Developer Use Case |
|---------|-------------|-------------------|
| Repository | Project folder tracked by Git | Your codebase |
| Working Directory | Current state of files | Where you edit code |
| Staging Area (Index) | Changes ready to commit | Review before saving |
| Commit | Snapshot of staged changes | Save point in history |
| HEAD | Pointer to current commit | Where you are now |
| .git directory | Git's database | Never modify directly |

### Essential Commands
```bash
# Initialize new repository
git init

# Clone existing repository
git clone https://github.com/user/repo.git

# Check repository status
git status

# View commit history
git log --oneline
```

### Developer Workflow
```bash
# Start a new project
mkdir my-app && cd my-app
git init
echo "# My App" > README.md
git add README.md
git commit -m "Initial commit"
```

---

## 2. The Three States of Git

### Why It Matters
- Understanding states prevents lost work
- Gives control over what goes into each commit
- Enables atomic, focused commits

### Key Concepts

```
Working Directory    →    Staging Area    →    Repository
   (Modified)              (Staged)            (Committed)
       │                      │                     │
       │    git add          │    git commit       │
       └──────────────────►──┴──────────────────►──┘
```

| State | Location | Commands |
|-------|----------|----------|
| Modified | Working directory | `git diff` to view |
| Staged | Index/staging | `git diff --staged` |
| Committed | .git repository | `git log` to view |

### Developer Scenarios
```bash
# Check what's changed (not staged)
git diff

# Check what's staged (ready to commit)
git diff --staged

# Stage specific files
git add src/app.js src/utils.js

# Stage all changes
git add .

# Stage parts of a file interactively
git add -p filename.js

# Unstage a file (keep changes)
git restore --staged filename.js

# Discard changes in working directory
git restore filename.js
```

---

## 3. Committing Best Practices

### Why It Matters
- Commits are your project's history
- Good commits make debugging easier
- Enable effective code reviews and collaboration

### Key Concepts

| Aspect | Good Practice | Bad Practice |
|--------|---------------|--------------|
| Size | Small, focused | Large, multiple features |
| Frequency | Often, logical units | Rarely, everything at once |
| Message | Descriptive, imperative | Vague, past tense |
| Content | Related changes only | Unrelated changes mixed |

### Commit Message Format
```bash
# Short format
git commit -m "Add user authentication endpoint"

# Detailed format (opens editor)
git commit

# Message structure:
# Subject line (50 chars max, imperative mood)
#
# Body (72 chars per line, explain what and why)
# - Bullet points are fine
# - Reference issues: Fixes #123
```

### Conventional Commits
```bash
# Feature
git commit -m "feat(auth): add JWT token validation"

# Bug fix
git commit -m "fix(api): resolve null pointer in user service"

# Documentation
git commit -m "docs(readme): update installation instructions"

# Refactoring
git commit -m "refactor(db): simplify connection pooling"

# Tests
git commit -m "test(auth): add unit tests for login flow"

# Chore/maintenance
git commit -m "chore(deps): update dependencies to latest"
```

### Developer Scenarios
```bash
# Amend last commit (before push)
git commit --amend -m "New message"

# Add forgotten file to last commit
git add forgotten-file.js
git commit --amend --no-edit

# Undo last commit, keep changes staged
git reset --soft HEAD~1

# Undo last commit, keep changes unstaged
git reset HEAD~1

# Undo last commit, discard changes (DANGEROUS)
git reset --hard HEAD~1
```

---

## 4. Branching Strategy

### Why It Matters
- Isolate features and experiments
- Enable parallel development
- Support code review workflows
- Maintain stable production code

### Key Concepts

| Branch Type | Purpose | Naming Convention |
|-------------|---------|-------------------|
| main/master | Production code | `main` |
| develop | Integration branch | `develop` |
| feature | New functionality | `feature/user-auth` |
| bugfix | Bug fixes | `bugfix/login-error` |
| hotfix | Urgent production fixes | `hotfix/security-patch` |
| release | Release preparation | `release/v1.2.0` |

### Essential Commands
```bash
# List branches
git branch              # Local
git branch -r           # Remote
git branch -a           # All

# Create branch
git branch feature/new-feature

# Switch branch
git switch feature/new-feature
git checkout feature/new-feature  # older syntax

# Create and switch
git switch -c feature/new-feature
git checkout -b feature/new-feature  # older syntax

# Delete branch
git branch -d feature/merged-feature    # Safe delete
git branch -D feature/unmerged-feature  # Force delete

# Delete remote branch
git push origin --delete feature/old-feature
```

### Feature Branch Workflow
```bash
# 1. Start from updated main
git checkout main
git pull origin main

# 2. Create feature branch
git checkout -b feature/user-profile

# 3. Develop feature (multiple commits)
git add .
git commit -m "Add user profile component"
git add .
git commit -m "Add profile API endpoint"

# 4. Push feature branch
git push -u origin feature/user-profile

# 5. Create Pull Request (on GitHub/GitLab)

# 6. After merge, cleanup
git checkout main
git pull origin main
git branch -d feature/user-profile
```

---

## 5. Merging & Rebasing

### Why It Matters
- Integrate work from different branches
- Maintain clean project history
- Resolve conflicting changes

### Key Concepts

| Operation | Result | When to Use |
|-----------|--------|-------------|
| Merge | Creates merge commit | Preserving history |
| Fast-forward | Linear history | No divergent commits |
| Squash merge | Single commit | Clean feature integration |
| Rebase | Replays commits | Linear history, local branches |

### Merge Types Visualized
```
# Fast-forward (no divergence)
main:    A---B
              \
feature:       C---D
                    ↓ merge
main:    A---B---C---D

# Three-way merge (with divergence)
main:    A---B---E
              \
feature:       C---D
                    ↓ merge
main:    A---B---E---F (merge commit)
              \     /
               C---D
```

### Essential Commands
```bash
# Merge feature into main
git checkout main
git merge feature/user-auth

# Merge with no fast-forward (always create merge commit)
git merge --no-ff feature/user-auth

# Squash merge (combine all commits into one)
git merge --squash feature/user-auth
git commit -m "Add user authentication"

# Rebase feature onto main
git checkout feature/user-auth
git rebase main

# Interactive rebase (edit/squash commits)
git rebase -i HEAD~3

# Abort merge/rebase if stuck
git merge --abort
git rebase --abort
```

### Resolving Merge Conflicts
```bash
# 1. Attempt merge
git merge feature-branch
# CONFLICT (content): Merge conflict in app.js

# 2. Check conflicted files
git status

# 3. Open file, find conflict markers
<<<<<<< HEAD
console.log("main version");
=======
console.log("feature version");
>>>>>>> feature-branch

# 4. Resolve by editing (remove markers, keep desired code)
console.log("resolved version");

# 5. Stage resolved file
git add app.js

# 6. Complete merge
git commit
```

---

## 6. Remote Repository Operations

### Why It Matters
- Backup code to cloud
- Collaborate with team members
- Deploy through CI/CD pipelines
- Share code publicly or privately

### Key Concepts

| Command | Purpose | Use Case |
|---------|---------|----------|
| `clone` | Copy remote repo | Start working on project |
| `fetch` | Download changes | See what's new |
| `pull` | Fetch + merge | Update local branch |
| `push` | Upload changes | Share your work |
| `remote` | Manage remotes | Add/remove connections |

### Essential Commands
```bash
# Clone repository
git clone https://github.com/user/repo.git
git clone git@github.com:user/repo.git  # SSH

# View remotes
git remote -v

# Add remote
git remote add origin https://github.com/user/repo.git
git remote add upstream https://github.com/original/repo.git

# Fetch changes (doesn't merge)
git fetch origin
git fetch --all

# Pull changes (fetch + merge)
git pull origin main

# Push changes
git push origin main
git push -u origin feature-branch  # Set upstream

# Push all branches
git push --all origin

# Force push (use with caution!)
git push --force-with-lease origin feature-branch
```

### Working with Forks
```bash
# Fork workflow (open source contribution)

# 1. Fork repo on GitHub (web interface)

# 2. Clone your fork
git clone https://github.com/YOUR-USER/repo.git

# 3. Add upstream remote
git remote add upstream https://github.com/ORIGINAL/repo.git

# 4. Keep fork updated
git fetch upstream
git checkout main
git merge upstream/main
git push origin main

# 5. Create feature branch and work
git checkout -b feature/my-contribution

# 6. Push to your fork
git push origin feature/my-contribution

# 7. Create PR from your fork to upstream
```

---

## 7. Git Stash

### Why It Matters
- Save work without committing
- Switch contexts quickly
- Handle urgent interruptions
- Clean working directory temporarily

### Key Concepts

| Command | Purpose |
|---------|---------|
| `stash` | Save changes temporarily |
| `stash list` | View all stashes |
| `stash pop` | Apply and remove latest stash |
| `stash apply` | Apply but keep stash |
| `stash drop` | Remove a stash |

### Essential Commands
```bash
# Stash current changes
git stash

# Stash with message
git stash save "work in progress on login"

# Stash including untracked files
git stash -u

# List all stashes
git stash list
# stash@{0}: On feature: work in progress on login
# stash@{1}: WIP on main: abc123 Previous commit

# Apply latest stash (and remove from list)
git stash pop

# Apply specific stash (keep in list)
git stash apply stash@{1}

# Drop specific stash
git stash drop stash@{0}

# Clear all stashes
git stash clear

# Create branch from stash
git stash branch new-branch stash@{0}
```

### Developer Scenarios
```bash
# Scenario: Urgent bug fix while working on feature
git stash save "feature work in progress"
git checkout main
git checkout -b hotfix/urgent-bug
# Fix bug
git commit -am "Fix urgent bug"
git checkout main
git merge hotfix/urgent-bug
git push origin main
git checkout feature/my-feature
git stash pop
# Continue feature work
```

---

## 8. Git History & Investigation

### Why It Matters
- Debug issues by finding when bugs were introduced
- Understand why code was written a certain way
- Review project evolution
- Find who to ask about specific code

### Key Concepts

| Command | Purpose | Use Case |
|---------|---------|----------|
| `log` | View commit history | See what changed |
| `show` | View specific commit | Examine changes |
| `diff` | Compare changes | Review modifications |
| `blame` | Line-by-line authorship | Find who wrote code |
| `bisect` | Binary search for bugs | Find bug introduction |

### Essential Commands
```bash
# View history
git log --oneline                    # Compact view
git log --graph --all --oneline      # Visual branch graph
git log -p                           # With diffs
git log --stat                       # With file stats
git log -n 5                         # Last 5 commits

# Search history
git log --grep="fix"                 # Search messages
git log --author="John"              # By author
git log --since="2 weeks ago"        # By date
git log -- path/to/file.js           # File history
git log -S "functionName"            # Code search (pickaxe)

# View specific commit
git show abc123
git show abc123:path/to/file.js      # File at commit

# Blame (who changed what)
git blame path/to/file.js
git blame -L 10,20 file.js           # Lines 10-20

# Find when bug was introduced
git bisect start
git bisect bad                       # Current is bad
git bisect good abc123               # Known good commit
# Git checks out commits; you test and mark
git bisect good                      # or git bisect bad
# Repeat until bug commit found
git bisect reset
```

### Developer Scenarios
```bash
# Find who last modified a line
git blame src/api/users.js -L 45,50

# See what changed in last commit
git show HEAD

# Compare branches
git diff main..feature-branch
git diff main...feature-branch       # Three dots: from common ancestor

# See commits in feature not in main
git log main..feature-branch

# Find commit that introduced a function
git log -S "calculateTotal" --oneline
```

---

## 9. Undoing Changes

### Why It Matters
- Mistakes happen - know how to recover
- Experiment safely
- Clean up before commits
- Revert problematic changes

### Key Concepts

| Scenario | Command | Effect |
|----------|---------|--------|
| Discard working directory changes | `git restore file` | Loses uncommitted changes |
| Unstage file | `git restore --staged file` | Moves from staged to modified |
| Amend last commit | `git commit --amend` | Modifies last commit |
| Undo commit (keep changes) | `git reset --soft HEAD~1` | Uncommit but keep staged |
| Undo commit (unstage) | `git reset HEAD~1` | Uncommit and unstage |
| Undo commit (discard) | `git reset --hard HEAD~1` | Lose all changes |
| Revert commit (safe) | `git revert abc123` | New commit that undoes |

### Essential Commands
```bash
# Discard changes in working directory
git restore file.js
git restore .                        # All files
git checkout -- file.js              # Older syntax

# Unstage files
git restore --staged file.js
git reset HEAD file.js               # Older syntax

# Amend last commit
git commit --amend -m "New message"
git add forgotten.js && git commit --amend --no-edit

# Soft reset (uncommit, keep staged)
git reset --soft HEAD~1

# Mixed reset (uncommit, unstage, keep changes)
git reset HEAD~1

# Hard reset (DANGEROUS - loses changes)
git reset --hard HEAD~1
git reset --hard origin/main         # Reset to remote

# Revert (safe for shared branches)
git revert abc123                    # Creates new commit
git revert HEAD                      # Revert last commit
git revert -n abc123                 # No auto-commit
```

### Recovery Scenarios
```bash
# Recover deleted branch
git reflog                           # Find commit hash
git branch recovered-branch abc123

# Recover after hard reset
git reflog
git reset --hard abc123              # Reset to found commit

# Recover deleted file
git checkout HEAD~1 -- deleted-file.js
```

---

## 10. .gitignore

### Why It Matters
- Keep repository clean
- Avoid committing sensitive data
- Exclude build artifacts
- Prevent merge conflicts in generated files

### Key Concepts

| Pattern | Example | Matches |
|---------|---------|---------|
| Literal | `file.txt` | Exact file |
| Wildcard | `*.log` | All .log files |
| Directory | `dist/` | Entire directory |
| Negation | `!important.log` | Exception to rule |
| Double star | `**/temp` | temp in any directory |

### Common .gitignore Patterns
```bash
# Dependencies
node_modules/
vendor/
.venv/
__pycache__/

# Build outputs
dist/
build/
*.class
*.jar
*.war

# IDE/Editor
.idea/
.vscode/
*.swp
*.swo
.DS_Store

# Environment & Secrets
.env
.env.local
*.pem
credentials.json
secrets/

# Logs
*.log
logs/
npm-debug.log*

# Testing
coverage/
.nyc_output/

# OS files
.DS_Store
Thumbs.db
```

### Essential Commands
```bash
# Check if file is ignored
git check-ignore -v filename

# Force add ignored file
git add -f ignored-file.txt

# Remove file from tracking (keep locally)
git rm --cached filename
git rm -r --cached directory/

# Global gitignore
git config --global core.excludesFile ~/.gitignore_global
```

### Project-Specific Templates
```bash
# Node.js
node_modules/
.env
dist/
coverage/

# Python
__pycache__/
*.py[cod]
.venv/
.env

# Java
*.class
*.jar
target/
.gradle/

# React/Frontend
node_modules/
build/
.env.local
```

---

## 11. Pull Request Workflow

### Why It Matters
- Code review catches bugs early
- Knowledge sharing across team
- Documentation of changes
- Quality gates before merge

### Key Concepts

| Step | Action | Purpose |
|------|--------|---------|
| 1 | Create branch | Isolate changes |
| 2 | Commit changes | Save work |
| 3 | Push branch | Share remotely |
| 4 | Open PR | Request review |
| 5 | Address feedback | Improve code |
| 6 | Merge | Integrate changes |
| 7 | Delete branch | Cleanup |

### GitHub CLI Commands
```bash
# Create pull request
gh pr create --title "Add feature" --body "Description"

# Create draft PR
gh pr create --draft

# List pull requests
gh pr list

# View PR details
gh pr view 123

# Checkout PR locally
gh pr checkout 123

# Merge PR
gh pr merge 123 --squash

# Add reviewers
gh pr edit 123 --add-reviewer username
```

### PR Description Template
```markdown
## Summary
Brief description of changes.

## Changes
- Added user authentication
- Updated API endpoints
- Fixed validation bug

## Testing
- [ ] Unit tests pass
- [ ] Manual testing completed
- [ ] Tested in staging

## Related Issues
Fixes #123
Related to #456

## Screenshots (if UI changes)
[Add screenshots]
```

### Code Review Best Practices
```bash
# Before creating PR
git pull origin main            # Update from main
git rebase main                 # Or merge main
npm test                        # Run tests
npm run lint                    # Check linting

# After review feedback
git add .
git commit -m "Address review: add validation"
git push

# Keeping PR updated
git fetch origin
git rebase origin/main
git push --force-with-lease     # Safe force push
```

---

## 12. Git Configuration

### Why It Matters
- Personalize your Git environment
- Set up identity for commits
- Configure default behaviors
- Improve productivity

### Key Concepts

| Level | Flag | Location | Scope |
|-------|------|----------|-------|
| System | `--system` | `/etc/gitconfig` | All users |
| Global | `--global` | `~/.gitconfig` | Current user |
| Local | `--local` | `.git/config` | Current repo |

### Essential Configuration
```bash
# Identity (required)
git config --global user.name "Your Name"
git config --global user.email "you@example.com"

# Default branch name
git config --global init.defaultBranch main

# Default editor
git config --global core.editor "code --wait"

# Line endings
git config --global core.autocrlf input    # Mac/Linux
git config --global core.autocrlf true     # Windows

# Aliases (shortcuts)
git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status
git config --global alias.last "log -1 HEAD"
git config --global alias.lg "log --oneline --graph --all"

# Pull behavior
git config --global pull.rebase true       # Rebase on pull

# Push behavior
git config --global push.default current   # Push current branch

# View all configuration
git config --list
git config --list --show-origin
```

### SSH Setup for GitHub/GitLab
```bash
# Generate SSH key
ssh-keygen -t ed25519 -C "your.email@example.com"

# Start SSH agent
eval "$(ssh-agent -s)"

# Add key to agent
ssh-add ~/.ssh/id_ed25519

# Copy public key
cat ~/.ssh/id_ed25519.pub
# Add to GitHub: Settings > SSH Keys

# Test connection
ssh -T git@github.com

# Switch remote URL to SSH
git remote set-url origin git@github.com:user/repo.git
```

---

## 13. Git Hooks

### Why It Matters
- Automate quality checks
- Enforce coding standards
- Prevent bad commits
- Run tests automatically

### Key Concepts

| Hook | Trigger | Use Case |
|------|---------|----------|
| `pre-commit` | Before commit | Lint, format code |
| `commit-msg` | After message entered | Validate message format |
| `pre-push` | Before push | Run tests |
| `post-merge` | After merge | Install dependencies |

### Common Hook Examples
```bash
# pre-commit hook (.git/hooks/pre-commit)
#!/bin/bash
npm run lint
if [ $? -ne 0 ]; then
  echo "Linting failed. Commit aborted."
  exit 1
fi

# commit-msg hook (.git/hooks/commit-msg)
#!/bin/bash
commit_msg=$(cat $1)
if ! echo "$commit_msg" | grep -qE "^(feat|fix|docs|style|refactor|test|chore)"; then
  echo "Commit message must start with: feat, fix, docs, style, refactor, test, chore"
  exit 1
fi
```

### Using Husky (npm project)
```bash
# Install husky
npm install husky --save-dev
npx husky install

# Add to package.json
"scripts": {
  "prepare": "husky install"
}

# Add pre-commit hook
npx husky add .husky/pre-commit "npm run lint"

# Add pre-push hook
npx husky add .husky/pre-push "npm test"
```

---

## 14. Common Workflows Summary

### Daily Development Workflow
```bash
# Start of day
git checkout main
git pull origin main

# Start new task
git checkout -b feature/TICKET-123-description

# During development
git add .
git commit -m "feat: implement feature"
# ... more commits

# Before PR
git fetch origin
git rebase origin/main
git push -u origin feature/TICKET-123-description

# After PR merged
git checkout main
git pull origin main
git branch -d feature/TICKET-123-description
```

### Quick Fix Workflow
```bash
# Urgent fix on production
git checkout main
git pull origin main
git checkout -b hotfix/critical-bug

# Fix and commit
git add .
git commit -m "fix: resolve critical bug"

# Push and create PR
git push -u origin hotfix/critical-bug
# Create PR, get quick review, merge

# Cleanup
git checkout main
git pull origin main
git branch -d hotfix/critical-bug
```

### Code Review Workflow
```bash
# Fetch PR locally
git fetch origin pull/123/head:pr-123
git checkout pr-123

# Review and test
npm test

# Leave comments on GitHub/GitLab
# After approval, merge via web interface
```

---

## Quick Reference Card

### Most Used Commands
```bash
# Daily commands
git status                    # What's changed?
git add .                     # Stage all changes
git commit -m "message"       # Save changes
git push                      # Upload to remote
git pull                      # Download from remote

# Branching
git checkout -b feature/name  # New branch
git checkout main             # Switch branch
git merge feature/name        # Merge branch
git branch -d feature/name    # Delete branch

# History
git log --oneline             # View history
git diff                      # View changes
git show HEAD                 # Last commit details

# Undo
git restore file.js           # Discard changes
git restore --staged file.js  # Unstage
git reset HEAD~1              # Undo commit

# Remote
git clone URL                 # Copy repository
git remote -v                 # View remotes
git fetch origin              # Download changes
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Initialize repositories and clone existing ones
- [ ] Understand working directory, staging area, and repository
- [ ] Create meaningful, atomic commits with good messages
- [ ] Create, switch, and merge branches confidently
- [ ] Resolve merge conflicts
- [ ] Push to and pull from remote repositories
- [ ] Use stash for context switching
- [ ] Investigate history using log, blame, and diff
- [ ] Undo changes at any stage safely
- [ ] Configure .gitignore appropriately
- [ ] Participate in pull request workflows
- [ ] Set up Git configuration and SSH keys
- [ ] Use Git hooks for automation

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 3: Agile Methodology](../03-agile/) - Learn team collaboration practices
- Practice daily Git workflows in real projects
- Contribute to open source projects to practice fork workflows

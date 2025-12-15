# Agile Key Concepts for Application Developers

## Overview

This document outlines the essential Agile concepts every application developer must understand. Agile methodology is critical for working effectively in modern development teams, delivering value iteratively, and collaborating with stakeholders.

---

## 1. Agile Manifesto and Principles

### Why It Matters
- Foundation of all Agile frameworks and practices
- Guides decision-making in daily work
- Shapes team culture and collaboration

### Key Concepts

| Value | Over | Developer Impact |
|-------|------|------------------|
| Individuals and interactions | Processes and tools | Communicate directly with team |
| Working software | Comprehensive documentation | Focus on delivering features |
| Customer collaboration | Contract negotiation | Engage with stakeholders |
| Responding to change | Following a plan | Embrace requirement changes |

### Twelve Principles Summary

| Principle | What It Means for Developers |
|-----------|------------------------------|
| Early, continuous delivery | Ship small increments frequently |
| Welcome changing requirements | Be flexible, don't resist changes |
| Deliver working software frequently | Prefer weeks over months |
| Business and developers together | Daily collaboration |
| Motivated individuals | Trust and support team members |
| Face-to-face conversation | Prefer direct communication |
| Working software as progress measure | Features over documentation |
| Sustainable pace | Avoid burnout, maintain quality |
| Technical excellence | Write clean, maintainable code |
| Simplicity | Build only what's needed |
| Self-organizing teams | Team decides how to work |
| Regular reflection | Continuous improvement |

---

## 2. Scrum Framework

### Why It Matters
- Most widely used Agile framework
- Provides structure for team collaboration
- Defines clear roles, events, and artifacts

### Key Concepts

| Component | Description | Purpose |
|-----------|-------------|---------|
| Sprint | Fixed time-box (1-4 weeks) | Deliver potentially shippable increment |
| Product Backlog | Ordered list of requirements | Single source of work |
| Sprint Backlog | Items selected for current sprint | Team's commitment |
| Increment | Sum of completed items | Deliverable product |
| Definition of Done | Quality criteria | Ensures completeness |

### Scrum Roles

| Role | Responsibilities | Key Activities |
|------|------------------|----------------|
| Product Owner | Maximize product value | Prioritize backlog, accept work |
| Scrum Master | Facilitate Scrum process | Remove impediments, coach team |
| Development Team | Deliver increment | Self-organize, build features |

### Scrum Theory (Three Pillars)
```
Transparency → Inspection → Adaptation
    ↓              ↓            ↓
 Visible work   Regular review   Adjust process
```

---

## 3. Scrum Events (Ceremonies)

### Why It Matters
- Structured opportunities for collaboration
- Regular inspection and adaptation
- Time-boxed to maintain efficiency

### Key Concepts

| Event | Time Box | Purpose | Participants |
|-------|----------|---------|--------------|
| Sprint Planning | 2-4 hours | Plan sprint work | Entire Scrum team |
| Daily Standup | 15 minutes | Sync and identify blockers | Development team |
| Sprint Review | 1-2 hours | Demo and gather feedback | Team + stakeholders |
| Sprint Retrospective | 1-1.5 hours | Process improvement | Scrum team only |
| Backlog Refinement | 10% of sprint | Prepare future work | Team + PO |

### Daily Standup Format
```
Three Questions:
1. What did I do yesterday?
2. What will I do today?
3. Are there any blockers?

Rules:
- Same time, same place daily
- Stand up to keep it short
- Focus on progress, not status
- Take detailed discussions offline
```

### Sprint Retrospective Techniques

**Start-Stop-Continue:**
```
┌─────────────────────────────────────────────────┐
│ START          │ STOP           │ CONTINUE      │
│ What should    │ What should    │ What's working│
│ we begin?      │ we stop?       │ well?         │
└─────────────────────────────────────────────────┘
```

**What Went Well / What Didn't / Action Items:**
```
+ Went Well        - Didn't Go Well    → Action Items
- Daily deploys    - Unclear specs     → Improve story writing
- Team collab      - Long meetings     → Time-box discussions
```

---

## 4. User Stories

### Why It Matters
- Primary way requirements are captured
- Focus on user value, not technical tasks
- Enable estimation and planning

### Key Concepts

| Component | Description | Example |
|-----------|-------------|---------|
| Role (Who) | Type of user | Customer, Admin, Guest |
| Goal (What) | Desired action | Filter products, Export data |
| Benefit (Why) | Business value | Find items faster, Analyze trends |

### User Story Format
```
As a [type of user],
I want [an action/goal],
So that [benefit/value].
```

### INVEST Criteria

| Letter | Meaning | Good Practice |
|--------|---------|---------------|
| I | Independent | Can be developed separately |
| N | Negotiable | Details can be discussed |
| V | Valuable | Delivers user/business value |
| E | Estimable | Team can size it |
| S | Small | Fits within a sprint |
| T | Testable | Has clear acceptance criteria |

### Writing Acceptance Criteria
```markdown
Story: As a customer, I want to filter products by price range

Acceptance Criteria:
- [ ] Min and max price inputs are visible
- [ ] Filter applies when user clicks "Apply"
- [ ] Results update without page reload
- [ ] "Clear filter" button resets the range
- [ ] Invalid inputs show error message
```

### Story Examples

**Good Story:**
```
As a registered user,
I want to reset my password via email,
So that I can regain access if I forget my password.

Acceptance Criteria:
- "Forgot Password" link on login page
- User enters email and receives reset link
- Link expires after 24 hours
- New password must meet security requirements
- Confirmation message after successful reset
```

**Bad Story (Too Technical):**
```
As a developer,
I want to add a SHA-256 hash function,
So that we can store passwords.
```
*Problem: Written from developer perspective, no user value*

---

## 5. Estimation and Planning

### Why It Matters
- Enables sprint planning and forecasting
- Helps team understand complexity
- Tracks team capacity over time

### Key Concepts

| Technique | Description | When to Use |
|-----------|-------------|-------------|
| Story Points | Relative complexity measure | Sprint planning |
| Planning Poker | Team consensus estimation | New stories |
| T-Shirt Sizing | Quick rough estimate | Early backlog |
| Velocity | Points completed per sprint | Capacity planning |

### Story Point Scale (Fibonacci)

| Points | Complexity | Example |
|--------|------------|---------|
| 1 | Trivial | Fix typo, update config |
| 2 | Simple | Add form field, style change |
| 3 | Small | New component, simple endpoint |
| 5 | Medium | Feature with some complexity |
| 8 | Large | Multiple components, integrations |
| 13 | Very Large | Consider splitting |
| 21+ | Epic | Must be broken down |

### Planning Poker Process
```
1. Product Owner presents story
2. Team asks clarifying questions
3. Each member selects a card privately
4. All reveal cards simultaneously
5. Discuss outliers (highest and lowest)
6. Re-estimate until consensus
```

### Velocity Tracking
```
Sprint 1: 28 points completed
Sprint 2: 32 points completed
Sprint 3: 30 points completed
─────────────────────────────
Average Velocity: 30 points

Future sprint capacity: ~30 points
```

---

## 6. Sprint Execution

### Why It Matters
- How work actually gets done
- Visualization of progress
- Identifying blockers early

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| Sprint Goal | Single objective for sprint | Focus the team |
| Sprint Board | Visual workflow | Track progress |
| WIP Limits | Max items in progress | Reduce context switching |
| Blockers | Impediments to progress | Requires escalation |

### Sprint Board Columns
```
┌──────────┬─────────────┬─────────────┬──────────┬────────┐
│ BACKLOG  │ IN PROGRESS │ CODE REVIEW │ TESTING  │ DONE   │
├──────────┼─────────────┼─────────────┼──────────┼────────┤
│ Story-7  │ Story-4     │ Story-2     │ Story-1  │ Story-3│
│ Story-8  │ Story-5     │             │          │        │
│ Story-9  │             │             │          │        │
└──────────┴─────────────┴─────────────┴──────────┴────────┘
          WIP: 2 max      WIP: 2 max    WIP: 2 max
```

### Burndown Chart
```
Points │
  30   │●
  25   │  ●─────●  (ideal)
  20   │    ●───────●
  15   │      ●       ●
  10   │        ●───────●  (actual)
   5   │          ●───────●
   0   │────────────────────●
       └────────────────────────
         Day 1  3  5  7  9  10
```

### Handling Blockers
```
1. Identify → Raise in standup immediately
2. Escalate → Scrum Master helps remove
3. Document → Track in project board
4. Resolve → Update team when unblocked
5. Prevent → Discuss in retrospective
```

---

## 7. Agile vs Waterfall

### Why It Matters
- Understanding trade-offs helps choose approach
- Most projects use hybrid approaches
- Know when each is appropriate

### Key Concepts

| Aspect | Agile | Waterfall |
|--------|-------|-----------|
| Approach | Iterative, incremental | Sequential, linear |
| Requirements | Evolving, flexible | Fixed upfront |
| Delivery | Frequent releases | Single release at end |
| Change | Welcomed | Costly, discouraged |
| Testing | Continuous | Phase at the end |
| Documentation | Just enough | Comprehensive |
| Customer Involvement | Throughout | Beginning and end |
| Risk | Identified early | Discovered late |

### When to Use Each

**Agile is better for:**
- Uncertain or evolving requirements
- Need for frequent feedback
- Innovation and new products
- Complex projects with unknowns
- Small to medium teams

**Waterfall is better for:**
- Well-defined, stable requirements
- Regulated industries (some aspects)
- Fixed-price contracts
- Hardware-dependent projects
- Large-scale system migrations

---

## 8. Kanban Basics

### Why It Matters
- Alternative/complement to Scrum
- Visualize workflow
- Optimize flow and reduce waste

### Key Concepts

| Concept | Description | Benefit |
|---------|-------------|---------|
| Visual Board | Cards on columns | Transparency |
| WIP Limits | Cap on concurrent work | Focus, reduce waste |
| Pull System | Pull work when capacity | Smooth flow |
| Continuous Flow | No fixed iterations | Flexible delivery |

### Kanban vs Scrum

| Aspect | Scrum | Kanban |
|--------|-------|--------|
| Iterations | Fixed sprints | Continuous flow |
| Roles | Defined (PO, SM, Dev) | No prescribed roles |
| Meetings | Sprint ceremonies | As needed |
| Changes | Next sprint | Anytime |
| Metrics | Velocity | Lead time, throughput |
| Planning | Sprint planning | Continuous |

---

## 9. Agile Tools

### Why It Matters
- Tools support Agile processes
- Enables distributed teams
- Tracks metrics and progress

### Key Concepts

| Tool | Best For | Key Features |
|------|----------|--------------|
| Jira | Enterprise teams | Customizable workflows, reports |
| Trello | Simple projects | Visual boards, easy setup |
| Azure DevOps | Microsoft ecosystem | Full ALM integration |
| GitHub Projects | Open source, dev teams | Close to code |
| Asana | Cross-functional teams | Multiple views |

### Jira Workflow Example
```
┌──────────┐   ┌─────────────┐   ┌─────────────┐   ┌────────┐
│ OPEN     │──▶│ IN PROGRESS │──▶│ IN REVIEW   │──▶│ DONE   │
└──────────┘   └─────────────┘   └─────────────┘   └────────┘
     │                                    │
     └────────────────────────────────────┘
                   (Reopen)
```

---

## 10. Definition of Done (DoD)

### Why It Matters
- Ensures quality and completeness
- Common understanding across team
- Prevents "almost done" syndrome

### Key Concepts

| Category | Example Criteria |
|----------|------------------|
| Code | Code reviewed, follows standards |
| Testing | Unit tests pass, coverage >80% |
| Documentation | API documented, README updated |
| Deployment | Deployed to staging, verified |
| Acceptance | PO has accepted |

### Sample Definition of Done
```markdown
## Definition of Done

A story is DONE when:

### Development
- [ ] Code complete and compiles
- [ ] Code reviewed by at least one peer
- [ ] Follows coding standards
- [ ] No critical code smells

### Testing
- [ ] Unit tests written and passing
- [ ] Integration tests passing
- [ ] No regression in existing tests
- [ ] Code coverage >= 80%

### Documentation
- [ ] Code is self-documenting or commented
- [ ] API documentation updated
- [ ] User documentation updated (if applicable)

### Deployment
- [ ] Successfully deployed to staging
- [ ] Smoke tests passing
- [ ] No increase in error rates

### Acceptance
- [ ] Meets acceptance criteria
- [ ] Product Owner has reviewed and accepted
- [ ] Demo ready for Sprint Review
```

---

## Quick Reference Card

### Daily Developer Activities
```
Morning:
- Check board for assigned items
- Prepare standup update
- Review any blocked items

During Sprint:
- Pull from "Ready" when capacity
- Update card status as you work
- Raise blockers immediately
- Request reviews when ready

End of Day:
- Update progress in tool
- Move cards to correct column
- Note any blockers for tomorrow
```

### User Story Quick Format
```
As a [user type],
I want [action],
So that [benefit].

Acceptance Criteria:
- [ ] Criterion 1
- [ ] Criterion 2
- [ ] Criterion 3
```

### Estimation Quick Reference
```
1 point = Trivial (< 1 hour)
2 points = Simple (few hours)
3 points = Small (half day)
5 points = Medium (1 day)
8 points = Large (2-3 days)
13 points = Very large (split it)
```

### Sprint Metrics
```
Velocity = Points completed per sprint
Capacity = Available team hours
Commitment = Points planned
Burndown = Remaining work over time
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Explain Agile values and principles
- [ ] Understand Scrum roles, events, and artifacts
- [ ] Write effective user stories with acceptance criteria
- [ ] Participate in sprint planning and estimation
- [ ] Contribute effectively in daily standups
- [ ] Use sprint boards to track work
- [ ] Participate in retrospectives constructively
- [ ] Understand velocity and capacity planning
- [ ] Apply Definition of Done criteria
- [ ] Use Agile tools (Jira, Trello, etc.)

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 4: HTML5](../04-html/) - Start building web content
- Apply Agile practices in your project work
- Take on more active roles in Scrum ceremonies

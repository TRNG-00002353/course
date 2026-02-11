# Angular Introduction & Setup

## What is Angular?

Angular is a **TypeScript-based** framework for building single-page applications (SPAs). Developed by Google, it provides a complete solution for front-end development.

### Angular vs Other Frameworks

| Feature | Angular | React | Vue |
|---------|---------|-------|-----|
| Type | Full Framework | Library | Progressive Framework |
| Language | TypeScript | JavaScript/JSX | JavaScript |
| Data Binding | Two-way | One-way | Two-way |
| Learning Curve | Steeper | Moderate | Gentle |
| CLI | Built-in | Create React App | Vue CLI |

---

## Single Page Applications (SPA)

### What is a SPA?

A Single Page Application loads a single HTML page and dynamically updates content as the user interacts, without full page reloads.

```
Traditional Web App:
┌──────────┐    Request    ┌──────────┐
│ Browser  │ ────────────► │  Server  │
│          │ ◄──────────── │          │
└──────────┘  Full HTML    └──────────┘
     │                           │
     │    Click Link             │
     ▼                           ▼
┌──────────┐    Request    ┌──────────┐
│ Browser  │ ────────────► │  Server  │
│ (reload) │ ◄──────────── │          │
└──────────┘  Full HTML    └──────────┘

SPA:
┌──────────┐   Initial     ┌──────────┐
│ Browser  │ ────────────► │  Server  │
│          │ ◄──────────── │          │
└──────────┘  App Bundle   └──────────┘
     │
     │    Click Link
     ▼
┌──────────┐   API Call    ┌──────────┐
│ Browser  │ ────────────► │  Server  │
│ (no      │ ◄──────────── │          │
│  reload) │  JSON Data    └──────────┘
└──────────┘
```

### SPA Benefits
- **Fast**: No full page reloads
- **Smooth UX**: App-like experience
- **Offline capable**: Can cache resources

### SPA Challenges
- **SEO**: Search engines may struggle (mitigated with SSR)
- **Initial Load**: Larger bundle to download
- **Browser History**: Needs client-side routing

---

## Client-Side Routing

In SPAs, routing happens in the browser, not the server:

```typescript
// Angular handles URL changes without server requests
/products      → ProductListComponent
/products/123  → ProductDetailComponent
/cart          → CartComponent
```

The browser URL changes, but Angular intercepts and renders the appropriate component.

---

## Webpack (Brief Overview)

Angular CLI uses **Webpack** under the hood to:

1. **Bundle** - Combine all TypeScript, HTML, CSS into optimized files
2. **Transpile** - Convert TypeScript to JavaScript
3. **Tree Shake** - Remove unused code
4. **Minify** - Reduce file size for production

```
Source Files                    Build Output
┌─────────────┐                ┌─────────────┐
│ app.ts      │                │ main.js     │
│ home.ts     │   Webpack      │ (bundled)   │
│ user.ts     │ ───────────►   ├─────────────┤
│ styles.css  │                │ styles.css  │
│ templates   │                │ (optimized) │
└─────────────┘                └─────────────┘
```

You rarely configure Webpack directly - Angular CLI handles it.

---

## Setup and Installation

### Prerequisites

```bash
# Check Node.js (v16+ recommended)
node --version

# Check npm
npm --version
```

### Install Angular CLI

```bash
# Install globally
npm install -g @angular/cli

# Verify installation
ng version
```

### Create New Project

```bash
# Create new app
ng new my-angular-app

# Options during creation:
# ? Would you like to add Angular routing? Yes
# ? Which stylesheet format? CSS (or SCSS)

# Navigate to project
cd my-angular-app

# Start development server
ng serve
# or
ng serve --open  # Opens browser automatically
```

Application runs at `http://localhost:4200`

---

## Angular Project Structure

```
my-angular-app/
├── src/
│   ├── app/                    # Application code
│   │   ├── app.component.ts    # Root component
│   │   ├── app.component.html  # Root template
│   │   ├── app.component.css   # Root styles
│   │   ├── app.component.spec.ts # Tests
│   │   ├── app.module.ts       # Root module
│   │   └── app-routing.module.ts # Routing config
│   │
│   ├── assets/                 # Static files (images, etc.)
│   ├── environments/           # Environment configs
│   ├── index.html              # Main HTML file
│   ├── main.ts                 # Application entry point
│   └── styles.css              # Global styles
│
├── angular.json                # Angular CLI config
├── package.json                # Dependencies
├── tsconfig.json               # TypeScript config
└── node_modules/               # Installed packages
```

### Key Files Explained

#### `src/main.ts` - Entry Point
```typescript
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';

// Bootstrap the application
platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
```

#### `src/app/app.module.ts` - Root Module
```typescript
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [AppComponent],  // Components in this module
  imports: [BrowserModule],      // Other modules needed
  providers: [],                 // Services
  bootstrap: [AppComponent]      // Root component
})
export class AppModule { }
```

#### `src/app/app.component.ts` - Root Component
```typescript
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',          // HTML tag name
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'my-angular-app';
}
```

---

## Angular CLI Commands

### Essential Commands

```bash
# Create new project
ng new project-name

# Generate component
ng generate component component-name
ng g c component-name  # shorthand

# Generate service
ng g s service-name

# Generate module
ng g m module-name

# Generate directive
ng g d directive-name

# Generate pipe
ng g p pipe-name

# Start dev server
ng serve

# Build for production
ng build --configuration production

# Run tests
ng test

# Run linting
ng lint
```

### Generate Component Examples

```bash
# Generate in specific folder
ng g c components/header

# Generate without test file
ng g c footer --skip-tests

# Generate with inline template/styles
ng g c simple --inline-template --inline-style
```

---

## Your First Component

### Understanding the Root Component

```typescript
// app.component.ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <h1>Welcome to {{ title }}!</h1>
    <p>Angular is running.</p>
  `,
  styles: [`
    h1 { color: #1976d2; }
  `]
})
export class AppComponent {
  title = 'My First Angular App';
}
```

### How It Renders

```html
<!-- index.html -->
<body>
  <app-root></app-root>  <!-- Angular replaces this -->
</body>

<!-- After Angular bootstraps -->
<body>
  <app-root>
    <h1>Welcome to My First Angular App!</h1>
    <p>Angular is running.</p>
  </app-root>
</body>
```

---

## Templates and Interpolation

### Text Interpolation `{{ }}`

```typescript
@Component({
  selector: 'app-greeting',
  template: `
    <h1>Hello, {{ username }}!</h1>
    <p>Today is {{ currentDate }}</p>
    <p>2 + 2 = {{ 2 + 2 }}</p>
    <p>Uppercase: {{ username.toUpperCase() }}</p>
  `
})
export class GreetingComponent {
  username = 'John';
  currentDate = new Date().toDateString();
}
```

### What Can Go Inside `{{ }}`

```typescript
// ✓ Variables
{{ name }}

// ✓ Object properties
{{ user.email }}

// ✓ Array access
{{ items[0] }}

// ✓ Method calls
{{ getFullName() }}

// ✓ Expressions
{{ price * quantity }}
{{ isActive ? 'Yes' : 'No' }}

// ✗ Assignments (not allowed)
{{ name = 'New' }}

// ✗ Multiple statements
{{ doThis(); doThat() }}
```

---

## Exercise: Setup and First Component

### Task 1: Create New Project
```bash
ng new todo-app --routing --style=css
cd todo-app
ng serve --open
```

### Task 2: Modify App Component
Edit `src/app/app.component.ts`:
```typescript
@Component({
  selector: 'app-root',
  template: `
    <header>
      <h1>{{ appName }}</h1>
      <p>Version: {{ version }}</p>
    </header>
    <main>
      <p>Welcome! Today is {{ today }}</p>
    </main>
  `,
  styles: [`
    header {
      background: #1976d2;
      color: white;
      padding: 20px;
    }
    main {
      padding: 20px;
    }
  `]
})
export class AppComponent {
  appName = 'My Todo App';
  version = '1.0.0';
  today = new Date().toLocaleDateString();
}
```

### Task 3: Generate Header Component
```bash
ng g c components/header
```

Move the header content to the new component and use `<app-header>` in AppComponent.

---

## Summary

| Concept | Key Points |
|---------|------------|
| Angular | TypeScript framework for SPAs |
| SPA | Single page, dynamic updates, no full reloads |
| Webpack | Bundles, transpiles, optimizes (handled by CLI) |
| CLI | `ng new`, `ng serve`, `ng generate` |
| Project Structure | `src/app/` contains components and modules |
| Interpolation | `{{ expression }}` displays data in templates |

## Next Topic

Continue to [Components Basics](./02-components-basics.md) to learn about building components with templates, styles, and data binding.

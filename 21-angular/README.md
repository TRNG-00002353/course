# Angular (v16+)

## Overview
Angular is a comprehensive TypeScript-based framework for building single-page applications (SPAs).

## Learning Objectives
By the end of this module, you will be able to:
- Build components with templates, styles, and data binding
- Use directives and pipes to manipulate DOM and transform data
- Implement services and dependency injection
- Configure routing with guards and lazy loading
- Make HTTP requests using RxJS and HttpClient
- Build forms with validation
- Test components and services

## Duration
**7-8 Days** (excluding review)

---

## Progressive Topic Flow

### Day 1: Introduction & Setup
**[01-introduction.md](./topics/01-introduction.md)**
- What is Angular? SPA concepts
- Setup and CLI commands
- Project structure
- Webpack overview
- Templates and interpolation

### Day 2: Components Basics
**[02-components-basics.md](./topics/02-components-basics.md)**
- @Component decorator (selector, template, styles)
- Interpolation `{{ }}`
- Property binding `[property]`
- Event binding `(event)`
- Template reference variables `#ref`
- Two-way binding `[(ngModel)]`
- Component styles and encapsulation

### Day 3: Directives & Pipes
**[03-directives-pipes.md](./topics/03-directives-pipes.md)**
- Structural directives (`*ngIf`, `*ngFor`, `*ngSwitch`)
- Attribute directives (`ngClass`, `ngStyle`)
- `ng-container` and `ng-template`
- Built-in pipes (date, currency, uppercase, async)
- Custom pipes

### Day 4: Component Communication & Services
**[04-component-communication-services.md](./topics/04-component-communication-services.md)**
- `@Input()` - Parent to child
- `@Output()` and EventEmitter - Child to parent
- `@ViewChild` - Direct access
- Dependency Injection basics
- Creating and injecting services
- Injector hierarchy
- Service communication (shared state)

### Day 5: Routing
**[05-routing.md](./topics/05-routing.md)**
- RouterModule configuration
- `routerLink` and navigation
- Route parameters and query params
- Route guards (CanActivate, CanDeactivate)
- Lazy loading modules
- Signals (Angular 16+)

### Day 6: RxJS & HTTP Client
**[06-rxjs-http.md](./topics/06-rxjs-http.md)**
- Observables and subscriptions
- RxJS operators (map, filter, switchMap, catchError)
- Subjects and BehaviorSubject
- Async pipe
- HttpClient (GET, POST, PUT, DELETE)
- Error handling
- HTTP interceptors

### Day 7: Forms & State Management
**[07-forms-state.md](./topics/07-forms-state.md)**
- Template-driven forms
- Reactive forms (FormGroup, FormControl, FormBuilder)
- Form validation (built-in and custom)
- Dynamic forms
- State management with BehaviorSubject pattern

### Day 8: Advanced Topics (Reference)
**[08-advanced.md](./topics/08-advanced.md)**
- Component lifecycle hooks (full)
- Change detection strategies
- Dynamic components
- NgModule deep dive (Feature, Shared, Core modules)

### Day 9: Testing & Debugging
**[09-testing.md](./topics/09-testing.md)**
- Jasmine framework
- Karma test runner
- Testing components
- Testing services
- Testing pipes
- Debugging with Chrome DevTools
- Debugging with VS Code

---

## Quick Reference

### Essential CLI Commands
```bash
# Create new project
ng new my-app --routing --style=css

# Start development server
ng serve --open

# Generate component
ng g c components/header

# Generate service
ng g s services/user

# Generate module
ng g m features/products --route products --module app

# Run tests
ng test

# Build for production
ng build --configuration production
```

### Key Concepts Summary

| Concept | Description |
|---------|-------------|
| Components | Building blocks with template, styles, and logic |
| Directives | Add behavior to elements (`*ngIf`, `*ngFor`, `ngClass`) |
| Pipes | Transform data for display (`date`, `currency`, `async`) |
| Services | Reusable business logic and data management |
| DI | Dependency injection for loose coupling |
| Routing | Navigation between views |
| RxJS | Reactive programming with Observables |
| HTTP Client | Communication with backend APIs |
| Forms | User input (Template-driven & Reactive) |
| Testing | Jasmine + Karma for unit tests |

---

## Curriculum Alignment

This module covers all topics required for **Week 9-10** of the curriculum:

| Curriculum Topic | Covered In |
|------------------|------------|
| Introduction, Setup, SPA, Webpack | Day 1 |
| Components, Styles, Templates | Day 2 |
| Change Detection, Dynamic Components | Day 8 |
| Event Emitters, Data Sharing | Day 4 |
| Modules (NgModule, Feature, Shared) | Day 8 |
| Structural/Attribute Directives | Day 3 |
| Pipes (Built-in, Custom) | Day 3 |
| DI, Services, Injector Hierarchy | Day 4 |
| Routing, Guards, Lazy Loading | Day 5 |
| Signals (Angular 16+) | Day 5 |
| RxJS (Observables, Operators, Subjects) | Day 6 |
| HTTP Client, Interceptors | Day 6 |
| Forms (Template, Reactive, Validation) | Day 7 |
| State Management (BehaviorSubject) | Day 7 |
| Testing (Karma, Jasmine) | Day 9 |
| Debugging (DevTools, VS Code) | Day 9 |

---

## Additional Resources

### Official Documentation
- [Angular Official Documentation](https://angular.io/docs)
- [Angular CLI Documentation](https://angular.io/cli)
- [RxJS Documentation](https://rxjs.dev/)

### Learning Resources
- [Angular Official Tutorial - Tour of Heroes](https://angular.io/tutorial)
- [Angular Style Guide](https://angular.io/guide/styleguide)

---

## Assessment
Ensure you are comfortable with all topics before proceeding to the next module.

**Time Estimate:** 7-8 days | **Difficulty:** Intermediate | **Prerequisites:** TypeScript

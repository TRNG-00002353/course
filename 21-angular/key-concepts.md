# Angular Key Concepts for Application Developers

## Overview

This document covers essential Angular concepts that every application developer must master. Angular is a comprehensive framework for building dynamic web applications with TypeScript, offering a complete solution with built-in routing, forms, HTTP client, and more.

---

## 1. Components - Building Blocks of Angular

### Why It Matters
- Components are the fundamental building blocks of Angular applications
- Encapsulate view, logic, and styles in reusable units
- Enable modular, testable, and maintainable code
- Form the tree structure of your application

### Key Concepts

| Concept | Description | Developer Use Case |
|---------|-------------|-------------------|
| Component Class | TypeScript class with logic | Business logic and state |
| Template | HTML view | User interface |
| Styles | CSS/SCSS | Component-specific styling |
| Metadata | @Component decorator | Configuration and binding |
| Selector | HTML tag name | Component usage in templates |
| Lifecycle Hooks | Method callbacks | React to component changes |

### Component Structure
```typescript
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  // Properties
  userName: string = 'John Doe';
  age: number = 30;
  isActive: boolean = true;

  // Constructor - Dependency Injection
  constructor() { }

  // Lifecycle hook
  ngOnInit(): void {
    console.log('Component initialized');
  }

  // Methods
  updateName(newName: string): void {
    this.userName = newName;
  }
}
```

### Template Syntax
```html
<!-- user-profile.component.html -->

<!-- Interpolation -->
<h1>{{ userName }}</h1>
<p>Age: {{ age }}</p>

<!-- Property Binding -->
<img [src]="userImageUrl" [alt]="userName">
<button [disabled]="!isActive">Submit</button>

<!-- Event Binding -->
<button (click)="updateName('Jane')">Change Name</button>
<input (input)="onInputChange($event)">

<!-- Two-way Binding -->
<input [(ngModel)]="userName">

<!-- Structural Directives -->
<div *ngIf="isActive">User is active</div>
<ul>
  <li *ngFor="let item of items">{{ item.name }}</li>
</ul>

<!-- Attribute Binding -->
<div [attr.data-id]="userId">Content</div>
<button [class.active]="isActive">Button</button>
<div [style.color]="isActive ? 'green' : 'red'">Status</div>
```

### Component Lifecycle Hooks

| Hook | Timing | Use Case |
|------|--------|----------|
| `ngOnChanges()` | When input properties change | React to @Input changes |
| `ngOnInit()` | After first ngOnChanges | Initialize component, fetch data |
| `ngDoCheck()` | During change detection | Custom change detection |
| `ngAfterViewInit()` | After view initialization | Access @ViewChild elements |
| `ngOnDestroy()` | Before component destruction | Cleanup, unsubscribe |

```typescript
export class LifecycleComponent implements OnInit, OnDestroy {
  subscription: Subscription;

  ngOnInit(): void {
    // Initialize data
    this.subscription = this.dataService.getData().subscribe();
  }

  ngOnDestroy(): void {
    // Cleanup
    this.subscription.unsubscribe();
  }
}
```

---

## 2. Modules - Organization Units

### Why It Matters
- Organize application into cohesive blocks of functionality
- Control dependencies and exports
- Enable lazy loading for performance
- Define compilation context

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| `declarations` | Components, directives, pipes | What belongs to this module |
| `imports` | Other modules | External dependencies |
| `exports` | Public declarations | What other modules can use |
| `providers` | Services | Dependency injection |
| `bootstrap` | Root component | Application entry point |

### Root Module (AppModule)
```typescript
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';

@NgModule({
  declarations: [
    // Components, Directives, Pipes that belong to this module
    AppComponent,
    HeaderComponent
  ],
  imports: [
    // Other modules this module depends on
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    // Services available application-wide
  ],
  bootstrap: [AppComponent] // Root component
})
export class AppModule { }
```

### Feature Module
```typescript
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserRoutingModule } from './user-routing.module';
import { UserListComponent } from './components/user-list/user-list.component';
import { UserDetailComponent } from './components/user-detail/user-detail.component';
import { UserService } from './services/user.service';

@NgModule({
  declarations: [
    UserListComponent,
    UserDetailComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule
  ],
  providers: [
    UserService
  ]
})
export class UserModule { }
```

### Shared Module (Reusable Components)
```typescript
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoaderComponent } from './components/loader/loader.component';
import { CardComponent } from './components/card/card.component';

@NgModule({
  declarations: [
    LoaderComponent,
    CardComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    // Make these available to importing modules
    LoaderComponent,
    CardComponent,
    CommonModule,
    FormsModule
  ]
})
export class SharedModule { }
```

---

## 3. Services & Dependency Injection

### Why It Matters
- Share data and logic across components
- Separate concerns (UI vs business logic)
- Enable testability through mocking
- Manage application state

### Key Concepts

| Concept | Description | Use Case |
|---------|-------------|----------|
| Service | Injectable class | Business logic, data access |
| @Injectable | Decorator | Marks class for DI |
| providedIn | Scope definition | Where service is available |
| Constructor Injection | DI mechanism | Receive dependencies |
| Singleton | Single instance | Shared state |

### Creating a Service
```typescript
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root' // Available application-wide (singleton)
})
export class UserService {
  private users: User[] = [];
  private usersSubject = new BehaviorSubject<User[]>([]);
  public users$ = this.usersSubject.asObservable();

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>('/api/users');
  }

  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`/api/users/${id}`);
  }

  addUser(user: User): Observable<User> {
    return this.http.post<User>('/api/users', user);
  }

  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`/api/users/${id}`, user);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`/api/users/${id}`);
  }
}
```

### Using Services in Components
```typescript
import { Component, OnInit } from '@angular/core';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html'
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  loading: boolean = false;
  error: string = '';

  // Dependency Injection
  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading = true;
    this.userService.getUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Failed to load users';
        this.loading = false;
      }
    });
  }
}
```

### Provider Scopes

| Scope | Configuration | Lifetime |
|-------|--------------|----------|
| Root | `providedIn: 'root'` | Application lifetime |
| Module | `providers: []` in NgModule | Module lifetime |
| Component | `providers: []` in @Component | Component lifetime |

```typescript
// Application-wide singleton
@Injectable({ providedIn: 'root' })
export class GlobalService { }

// Module-scoped
@NgModule({
  providers: [ModuleService]
})
export class FeatureModule { }

// Component-scoped (new instance per component)
@Component({
  selector: 'app-example',
  providers: [ComponentService]
})
export class ExampleComponent { }
```

---

## 4. Routing & Navigation

### Why It Matters
- Enable single-page application navigation
- Deep linking and browser history
- Lazy loading for performance
- Route guards for security

### Key Concepts

| Concept | Description | Use Case |
|---------|-------------|----------|
| Routes | Path configurations | Define navigation structure |
| RouterModule | Angular routing | Enable routing |
| RouterOutlet | Placeholder | Where components render |
| RouterLink | Navigation directive | Create links |
| ActivatedRoute | Current route info | Access route parameters |
| Route Guards | Access control | Protect routes |

### Basic Routing Setup
```typescript
// app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { UserDetailComponent } from './components/user-detail/user-detail.component';
import { NotFoundComponent } from './components/not-found/not-found.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'users', component: UserListComponent },
  { path: 'users/:id', component: UserDetailComponent },
  { path: '**', component: NotFoundComponent } // Wildcard route
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
```

### Router Outlet & Links
```html
<!-- app.component.html -->
<nav>
  <a routerLink="/home" routerLinkActive="active">Home</a>
  <a routerLink="/users" routerLinkActive="active">Users</a>
  <a routerLink="/about" routerLinkActive="active">About</a>
</nav>

<!-- Components render here -->
<router-outlet></router-outlet>
```

### Accessing Route Parameters
```typescript
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html'
})
export class UserDetailComponent implements OnInit {
  user: User | undefined;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    // Get route parameter
    const id = this.route.snapshot.paramMap.get('id');

    // Or subscribe to parameter changes
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
        const id = params.get('id')!;
        return this.userService.getUserById(+id);
      })
    ).subscribe(user => this.user = user);

    // Query parameters
    this.route.queryParamMap.subscribe(params => {
      const filter = params.get('filter');
    });
  }

  goBack(): void {
    this.router.navigate(['/users']);
  }

  editUser(): void {
    // Navigate with parameters
    this.router.navigate(['/users', this.user.id, 'edit']);
  }
}
```

### Lazy Loading Modules
```typescript
// app-routing.module.ts
const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  {
    path: 'admin',
    loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
    canActivate: [AuthGuard] // Protect route
  },
  {
    path: 'products',
    loadChildren: () => import('./products/products.module').then(m => m.ProductsModule)
  }
];
```

### Route Guards
```typescript
// auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    if (this.authService.isAuthenticated()) {
      return true;
    }

    // Redirect to login
    this.router.navigate(['/login'], {
      queryParams: { returnUrl: state.url }
    });
    return false;
  }
}

// Usage in routing
{
  path: 'admin',
  component: AdminComponent,
  canActivate: [AuthGuard]
}
```

---

## 5. Forms - Template-Driven & Reactive

### Why It Matters
- Handle user input and validation
- Two approaches: template-driven (simpler) and reactive (powerful)
- Built-in validation and error handling
- Form state management

### Key Concepts

| Approach | Module | Complexity | Use Case |
|----------|--------|------------|----------|
| Template-Driven | FormsModule | Low | Simple forms |
| Reactive | ReactiveFormsModule | High | Complex forms, dynamic validation |

### Template-Driven Forms
```typescript
// Import FormsModule in module
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [FormsModule]
})
```

```typescript
// Component
export class LoginComponent {
  model = {
    username: '',
    password: ''
  };

  onSubmit(): void {
    console.log('Form submitted:', this.model);
  }
}
```

```html
<!-- Template -->
<form #loginForm="ngForm" (ngSubmit)="onSubmit()">
  <div>
    <label>Username:</label>
    <input
      type="text"
      name="username"
      [(ngModel)]="model.username"
      required
      minlength="3"
      #username="ngModel">
    <div *ngIf="username.invalid && username.touched">
      <small *ngIf="username.errors?.['required']">Username is required</small>
      <small *ngIf="username.errors?.['minlength']">Minimum 3 characters</small>
    </div>
  </div>

  <div>
    <label>Password:</label>
    <input
      type="password"
      name="password"
      [(ngModel)]="model.password"
      required
      minlength="6"
      #password="ngModel">
    <div *ngIf="password.invalid && password.touched">
      <small *ngIf="password.errors?.['required']">Password is required</small>
    </div>
  </div>

  <button type="submit" [disabled]="loginForm.invalid">Login</button>
</form>
```

### Reactive Forms
```typescript
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html'
})
export class RegistrationComponent implements OnInit {
  registrationForm: FormGroup;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.registrationForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', Validators.required],
      age: ['', [Validators.required, Validators.min(18)]],
      address: this.fb.group({
        street: [''],
        city: ['', Validators.required],
        state: [''],
        zipCode: ['', Validators.pattern(/^\d{5}$/)]
      }),
      hobbies: this.fb.array([])
    }, { validators: this.passwordMatchValidator });
  }

  // Custom validator
  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { passwordMismatch: true };
  }

  // Getters for convenience
  get username() { return this.registrationForm.get('username'); }
  get email() { return this.registrationForm.get('email'); }
  get hobbies() { return this.registrationForm.get('hobbies') as FormArray; }

  addHobby(): void {
    this.hobbies.push(this.fb.control(''));
  }

  removeHobby(index: number): void {
    this.hobbies.removeAt(index);
  }

  onSubmit(): void {
    if (this.registrationForm.valid) {
      console.log('Form data:', this.registrationForm.value);
    } else {
      // Mark all fields as touched to show errors
      this.registrationForm.markAllAsTouched();
    }
  }
}
```

```html
<!-- Reactive form template -->
<form [formGroup]="registrationForm" (ngSubmit)="onSubmit()">
  <div>
    <label>Username:</label>
    <input type="text" formControlName="username">
    <div *ngIf="username?.invalid && username?.touched">
      <small *ngIf="username?.errors?.['required']">Username is required</small>
      <small *ngIf="username?.errors?.['minlength']">Minimum 3 characters</small>
    </div>
  </div>

  <div>
    <label>Email:</label>
    <input type="email" formControlName="email">
    <div *ngIf="email?.invalid && email?.touched">
      <small *ngIf="email?.errors?.['required']">Email is required</small>
      <small *ngIf="email?.errors?.['email']">Invalid email format</small>
    </div>
  </div>

  <!-- Nested form group -->
  <div formGroupName="address">
    <h3>Address</h3>
    <input type="text" formControlName="street" placeholder="Street">
    <input type="text" formControlName="city" placeholder="City">
    <input type="text" formControlName="zipCode" placeholder="Zip Code">
  </div>

  <!-- Form array -->
  <div>
    <h3>Hobbies</h3>
    <div formArrayName="hobbies">
      <div *ngFor="let hobby of hobbies.controls; let i = index">
        <input type="text" [formControlName]="i">
        <button type="button" (click)="removeHobby(i)">Remove</button>
      </div>
    </div>
    <button type="button" (click)="addHobby()">Add Hobby</button>
  </div>

  <button type="submit" [disabled]="registrationForm.invalid">Register</button>
</form>
```

---

## 6. HTTP Client & Backend Communication

### Why It Matters
- Communicate with REST APIs
- Handle asynchronous operations
- Interceptors for authentication and error handling
- Type-safe HTTP requests

### Key Concepts

| Concept | Description | Use Case |
|---------|-------------|----------|
| HttpClient | Angular HTTP service | Make API calls |
| Observable | Async data stream | Handle responses |
| Interceptor | Request/response middleware | Auth, logging, errors |
| HTTP Methods | GET, POST, PUT, DELETE | CRUD operations |

### HttpClient Setup
```typescript
// Import in AppModule
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [HttpClientModule]
})
export class AppModule { }
```

### Service with HTTP Operations
```typescript
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry, map } from 'rxjs/operators';

export interface User {
  id: number;
  name: string;
  email: string;
}

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'https://api.example.com';

  constructor(private http: HttpClient) { }

  // GET request
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/users`).pipe(
      retry(2), // Retry failed requests
      catchError(this.handleError)
    );
  }

  // GET with parameters
  getUsersFiltered(role: string, page: number): Observable<User[]> {
    const params = new HttpParams()
      .set('role', role)
      .set('page', page.toString());

    return this.http.get<User[]>(`${this.apiUrl}/users`, { params });
  }

  // GET single user
  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/users/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // POST request
  createUser(user: User): Observable<User> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    return this.http.post<User>(`${this.apiUrl}/users`, user, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  // PUT request
  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/users/${id}`, user).pipe(
      catchError(this.handleError)
    );
  }

  // PATCH request
  patchUser(id: number, updates: Partial<User>): Observable<User> {
    return this.http.patch<User>(`${this.apiUrl}/users/${id}`, updates).pipe(
      catchError(this.handleError)
    );
  }

  // DELETE request
  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/users/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Error handling
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An error occurred';

    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }

    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
```

### HTTP Interceptor
```typescript
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Get token from storage
    const token = localStorage.getItem('auth_token');

    // Clone request and add authorization header
    if (token) {
      const clonedRequest = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${token}`)
      });
      return next.handle(clonedRequest);
    }

    return next.handle(req);
  }
}

// Register in AppModule
import { HTTP_INTERCEPTORS } from '@angular/common/http';

@NgModule({
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ]
})
export class AppModule { }
```

### Error Handling Interceptor
```typescript
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private router: Router) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          // Unauthorized - redirect to login
          this.router.navigate(['/login']);
        } else if (error.status === 403) {
          // Forbidden
          console.error('Access denied');
        } else if (error.status === 500) {
          // Server error
          console.error('Server error');
        }
        return throwError(() => error);
      })
    );
  }
}
```

---

## 7. Directives - Structural & Attribute

### Why It Matters
- Extend HTML with custom behavior
- Create reusable DOM manipulation logic
- Built-in directives for common patterns
- Custom directives for specific needs

### Key Concepts

| Type | Examples | Purpose |
|------|----------|---------|
| Structural | *ngIf, *ngFor, *ngSwitch | Modify DOM structure |
| Attribute | ngClass, ngStyle, ngModel | Modify element appearance/behavior |
| Custom | Your own directives | Reusable behavior |

### Built-in Structural Directives
```html
<!-- *ngIf - Conditional rendering -->
<div *ngIf="isLoggedIn">Welcome back!</div>
<div *ngIf="isLoggedIn; else loginBlock">Welcome back!</div>
<ng-template #loginBlock>
  <div>Please log in</div>
</ng-template>

<!-- *ngIf with as (store result) -->
<div *ngIf="user$ | async as user">
  Hello {{ user.name }}
</div>

<!-- *ngFor - Loop through items -->
<ul>
  <li *ngFor="let item of items; let i = index; let isFirst = first; let isLast = last">
    {{ i + 1 }}. {{ item.name }}
    <span *ngIf="isFirst">(First)</span>
    <span *ngIf="isLast">(Last)</span>
  </li>
</ul>

<!-- *ngFor with trackBy for performance -->
<div *ngFor="let item of items; trackBy: trackByItemId">
  {{ item.name }}
</div>

<!-- *ngSwitch - Multiple conditions -->
<div [ngSwitch]="userRole">
  <div *ngSwitchCase="'admin'">Admin Dashboard</div>
  <div *ngSwitchCase="'user'">User Dashboard</div>
  <div *ngSwitchCase="'guest'">Guest View</div>
  <div *ngSwitchDefault>Unknown Role</div>
</div>
```

### Built-in Attribute Directives
```html
<!-- ngClass - Dynamic classes -->
<div [ngClass]="{'active': isActive, 'disabled': isDisabled}">Content</div>
<div [ngClass]="isActive ? 'active' : 'inactive'">Content</div>
<div [ngClass]="['class1', 'class2', dynamicClass]">Content</div>

<!-- ngStyle - Dynamic styles -->
<div [ngStyle]="{'color': textColor, 'font-size': fontSize + 'px'}">Text</div>
<div [ngStyle]="getStyles()">Styled content</div>

<!-- ngModel - Two-way binding -->
<input [(ngModel)]="username" name="username">
```

### Custom Attribute Directive
```typescript
import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[appHighlight]'
})
export class HighlightDirective {
  @Input() appHighlight: string = 'yellow';
  @Input() defaultColor: string = 'transparent';

  constructor(private el: ElementRef) { }

  @HostListener('mouseenter') onMouseEnter() {
    this.highlight(this.appHighlight);
  }

  @HostListener('mouseleave') onMouseLeave() {
    this.highlight(this.defaultColor);
  }

  private highlight(color: string) {
    this.el.nativeElement.style.backgroundColor = color;
  }
}

// Usage
// <p appHighlight="lightblue" defaultColor="white">Hover over me!</p>
```

### Custom Structural Directive
```typescript
import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appUnless]'
})
export class UnlessDirective {
  private hasView = false;

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef
  ) { }

  @Input() set appUnless(condition: boolean) {
    if (!condition && !this.hasView) {
      this.viewContainer.createEmbeddedView(this.templateRef);
      this.hasView = true;
    } else if (condition && this.hasView) {
      this.viewContainer.clear();
      this.hasView = false;
    }
  }
}

// Usage (opposite of *ngIf)
// <p *appUnless="condition">Show when condition is false</p>
```

---

## 8. Pipes - Transform Display Data

### Why It Matters
- Format data in templates without changing the underlying value
- Reusable transformation logic
- Built-in pipes for common tasks
- Custom pipes for specific needs

### Key Concepts

| Pipe | Purpose | Example |
|------|---------|---------|
| date | Format dates | `{{ today \| date:'short' }}` |
| uppercase/lowercase | Change case | `{{ name \| uppercase }}` |
| currency | Format money | `{{ price \| currency:'USD' }}` |
| decimal | Format numbers | `{{ value \| number:'1.2-2' }}` |
| json | Display objects | `{{ obj \| json }}` |
| async | Subscribe to observables | `{{ users$ \| async }}` |

### Built-in Pipes
```html
<!-- Date pipe -->
<p>{{ today | date }}</p>
<p>{{ today | date:'short' }}</p>
<p>{{ today | date:'MM/dd/yyyy' }}</p>
<p>{{ today | date:'fullDate' }}</p>

<!-- Number pipes -->
<p>{{ 1234.56 | number }}</p>
<p>{{ 1234.56 | number:'1.0-0' }}</p>
<p>{{ 0.25 | percent }}</p>
<p>{{ 99.99 | currency }}</p>
<p>{{ 99.99 | currency:'EUR' }}</p>

<!-- String pipes -->
<p>{{ 'hello world' | uppercase }}</p>
<p>{{ 'HELLO WORLD' | lowercase }}</p>
<p>{{ 'hello world' | titlecase }}</p>

<!-- Slice pipe -->
<p>{{ 'Hello World' | slice:0:5 }}</p>
<ul>
  <li *ngFor="let item of items | slice:0:3">{{ item }}</li>
</ul>

<!-- JSON pipe (for debugging) -->
<pre>{{ user | json }}</pre>

<!-- Async pipe (automatically subscribes/unsubscribes) -->
<div *ngIf="users$ | async as users">
  <div *ngFor="let user of users">{{ user.name }}</div>
</div>

<!-- Chaining pipes -->
<p>{{ today | date:'fullDate' | uppercase }}</p>
```

### Custom Pipe
```typescript
import { Pipe, PipeTransform } from '@angular/core';

// Simple pipe
@Pipe({
  name: 'exponential'
})
export class ExponentialPipe implements PipeTransform {
  transform(value: number, exponent: number = 1): number {
    return Math.pow(value, exponent);
  }
}

// Usage: {{ 2 | exponential:3 }} outputs 8

// Filtering pipe
@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {
  transform(items: any[], searchText: string, property: string): any[] {
    if (!items || !searchText) {
      return items;
    }

    searchText = searchText.toLowerCase();
    return items.filter(item => {
      return item[property].toLowerCase().includes(searchText);
    });
  }
}

// Usage: *ngFor="let user of users | filter:searchTerm:'name'"

// Pure vs Impure Pipes
@Pipe({
  name: 'impureFilter',
  pure: false // Pipe runs on every change detection (use carefully)
})
export class ImpureFilterPipe implements PipeTransform {
  transform(items: any[], callback: (item: any) => boolean): any[] {
    if (!items || !callback) {
      return items;
    }
    return items.filter(callback);
  }
}
```

---

## Quick Reference Card

### Component Basics
```typescript
// Create component
ng generate component user-profile

// Component decorator
@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})

// Template syntax
{{ expression }}              // Interpolation
[property]="value"            // Property binding
(event)="handler()"           // Event binding
[(ngModel)]="property"        // Two-way binding
```

### Common Commands
```bash
# Create new app
ng new my-app

# Serve application
ng serve
ng serve --open --port 4200

# Generate components, services, etc.
ng generate component user-list
ng generate service user
ng generate module feature --routing
ng generate guard auth
ng generate pipe custom

# Build for production
ng build --prod
ng build --configuration production

# Run tests
ng test
ng e2e

# Update Angular
ng update @angular/cli @angular/core
```

### Dependency Injection
```typescript
// Service
@Injectable({ providedIn: 'root' })
export class DataService { }

// Inject in component
constructor(private dataService: DataService) { }
```

### Routing
```typescript
// Route configuration
{ path: 'users/:id', component: UserComponent }

// Navigate
this.router.navigate(['/users', userId]);

// Get params
this.route.snapshot.paramMap.get('id');
this.route.paramMap.subscribe(params => { });
```

### Forms
```typescript
// Template-driven
<input [(ngModel)]="username" name="username" required>

// Reactive
this.form = this.fb.group({
  username: ['', Validators.required]
});
```

### HTTP
```typescript
// GET
this.http.get<User[]>('/api/users')

// POST
this.http.post<User>('/api/users', user)

// Subscribe
.subscribe({
  next: (data) => { },
  error: (err) => { }
})
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Create and configure components with templates and styles
- [ ] Understand component lifecycle hooks
- [ ] Organize applications using modules
- [ ] Create and inject services for shared logic
- [ ] Implement routing and navigation
- [ ] Use route guards for access control
- [ ] Build template-driven and reactive forms
- [ ] Validate user input and handle form submission
- [ ] Make HTTP requests and handle responses
- [ ] Create and use interceptors for authentication
- [ ] Use built-in structural and attribute directives
- [ ] Create custom directives for reusable behavior
- [ ] Apply pipes to transform display data
- [ ] Build custom pipes for specific formatting needs

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 22: RxJS](../22-rxjs/) - Master reactive programming with Observables
- Practice by building a full CRUD application
- Explore Angular Material for pre-built UI components
- Learn NgRx for advanced state management
- Study Angular testing with Jasmine and Karma

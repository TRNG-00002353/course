# Week 10 - Interview FAQ

This document contains frequently asked interview questions and comprehensive answers for Week 10 topics: Advanced Angular (Dependency Injection, Routing, RxJS/HTTP, Forms).

---

## Table of Contents

1. [Dependency Injection](#dependency-injection)
2. [Services](#services)
3. [Routing](#routing)
4. [Route Guards](#route-guards)
5. [RxJS Fundamentals](#rxjs-fundamentals)
6. [HTTP Client](#http-client)
7. [Forms](#forms)
8. [State Management](#state-management)

---

## Dependency Injection

### Q1: What is Dependency Injection? Why is it important in Angular?

**Answer:**

**Dependency Injection (DI)** is a design pattern where a class receives its dependencies from external sources rather than creating them internally.

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    WITHOUT DI vs WITH DI                                 │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  WITHOUT DI:                        WITH DI (Angular):                  │
│  ────────────                       ─────────────────                   │
│  class UserComponent {              class UserComponent {               │
│    private service: UserService;      constructor(                      │
│                                         private service: UserService    │
│    constructor() {                    ) {}                              │
│      this.service = new UserService();  // Angular provides instance   │
│    }                                  }                                 │
│  }                                                                      │
│                                                                          │
│  Problems:                          Benefits:                           │
│  • Tightly coupled                  • Loose coupling                    │
│  • Hard to test                     • Easy to test (inject mocks)      │
│  • Hard to change                   • Easy to swap implementations      │
│  • Creates its own dependencies     • Single instance (singleton)       │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**How Angular DI works:**

```typescript
// 1. Define a service
@Injectable({
  providedIn: 'root'  // Register with root injector
})
export class UserService {
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>('/api/users');
  }
}

// 2. Inject into component
@Component({...})
export class UserListComponent {
  constructor(private userService: UserService) {
    // Angular automatically provides UserService instance
  }
}
```

**Why DI matters:**

| Benefit | Explanation |
|---------|-------------|
| **Testability** | Inject mock services in tests |
| **Maintainability** | Change implementation without changing consumers |
| **Reusability** | Same service instance shared across app |
| **Modularity** | Clear separation of concerns |

---

### Q2: Explain the Angular injector hierarchy and providedIn options.

**Answer:**

Angular has a **hierarchical injector system** with different scopes:

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     INJECTOR HIERARCHY                                   │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│   ┌─────────────────────────────────────────────────────────┐           │
│   │           ROOT INJECTOR (Application-wide)              │           │
│   │                                                          │           │
│   │  providedIn: 'root'                                      │           │
│   │  or AppModule providers: []                              │           │
│   │                                                          │           │
│   │  → Singleton for entire application                     │           │
│   └─────────────────────────────────────────────────────────┘           │
│                           │                                              │
│           ┌───────────────┼───────────────┐                             │
│           ▼               ▼               ▼                              │
│   ┌─────────────┐ ┌─────────────┐ ┌─────────────┐                       │
│   │  Module A   │ │  Module B   │ │  Module C   │                       │
│   │  Injector   │ │  Injector   │ │  Injector   │                       │
│   └─────────────┘ └─────────────┘ └─────────────┘                       │
│           │                                                              │
│           ▼                                                              │
│   ┌─────────────────────────────────────────────────────────┐           │
│   │        COMPONENT INJECTOR (Component-scoped)            │           │
│   │                                                          │           │
│   │  @Component({ providers: [MyService] })                  │           │
│   │                                                          │           │
│   │  → New instance for component and children              │           │
│   └─────────────────────────────────────────────────────────┘           │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**providedIn options:**

```typescript
// Option 1: Root (recommended) - Application singleton
@Injectable({
  providedIn: 'root'
})
export class GlobalService {}

// Option 2: Specific module - Module-scoped singleton
@Injectable({
  providedIn: SomeModule
})
export class ModuleService {}

// Option 3: 'any' - Instance per lazy-loaded module
@Injectable({
  providedIn: 'any'
})
export class PerModuleService {}

// Option 4: Component providers - Instance per component
@Component({
  providers: [ComponentScopedService]
})
export class MyComponent {}
```

**Injection resolution:**

```
Component requests UserService
         │
         ▼
Check Component Injector → Found? Use it
         │ Not found
         ▼
Check Parent Component Injector → Found? Use it
         │ Not found
         ▼
Check Module Injector → Found? Use it
         │ Not found
         ▼
Check Root Injector → Found? Use it
         │ Not found
         ▼
ERROR: No provider found
```

---

## Services

### Q3: When should you create a service? What belongs in a service vs component?

**Answer:**

**Service responsibilities:**

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    SERVICE vs COMPONENT RESPONSIBILITIES                 │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  COMPONENT (Presentation Layer):    SERVICE (Business Layer):           │
│  ─────────────────────────────      ───────────────────────             │
│  • Display data to user             • HTTP/API calls                    │
│  • Handle user interactions         • Business logic                    │
│  • Template bindings                • Data transformation               │
│  • Local UI state                   • Shared state management           │
│  • Component-specific logic         • Cross-component communication     │
│                                      • Caching                           │
│                                      • Logging                           │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**Example - Good separation:**

```typescript
// SERVICE - handles data and business logic
@Injectable({ providedIn: 'root' })
export class UserService {
  private usersUrl = '/api/users';

  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  createUser(user: CreateUserDto): Observable<User> {
    return this.http.post<User>(this.usersUrl, user);
  }

  // Business logic
  isEligibleForDiscount(user: User): boolean {
    return user.memberSince.getFullYear() < 2020;
  }
}

// COMPONENT - handles presentation
@Component({...})
export class UserListComponent implements OnInit {
  users: User[] = [];
  loading = false;
  error: string | null = null;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading = true;
    this.userService.getUsers().subscribe({
      next: users => {
        this.users = users;
        this.loading = false;
      },
      error: err => {
        this.error = 'Failed to load users';
        this.loading = false;
      }
    });
  }

  // UI logic only
  trackByUserId(index: number, user: User): number {
    return user.id;
  }
}
```

**When to create a service:**
- Data needs to be shared across components
- HTTP calls or external API interactions
- Complex business logic
- State that persists across route changes
- Reusable utility functions

---

## Routing

### Q4: Explain Angular routing. How do you configure routes?

**Answer:**

**Angular Router** enables navigation between views/components based on URL.

**Basic setup:**

```typescript
// app.routes.ts
import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'users', component: UserListComponent },
  { path: 'users/:id', component: UserDetailComponent },
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard] },
  { path: '**', component: NotFoundComponent }  // Wildcard (404)
];

// app.config.ts (standalone)
export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes)]
};

// OR app.module.ts (NgModule)
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
```

**Template setup:**

```html
<!-- Navigation -->
<nav>
  <a routerLink="/home" routerLinkActive="active">Home</a>
  <a routerLink="/users" routerLinkActive="active">Users</a>
  <a [routerLink]="['/users', userId]">User Detail</a>
</nav>

<!-- Router outlet displays matched component -->
<router-outlet></router-outlet>
```

**Route configuration options:**

```typescript
{
  path: 'products',
  component: ProductListComponent,

  // Child routes
  children: [
    { path: ':id', component: ProductDetailComponent },
    { path: ':id/edit', component: ProductEditComponent }
  ],

  // Guards
  canActivate: [AuthGuard],
  canDeactivate: [UnsavedChangesGuard],
  canActivateChild: [ChildGuard],

  // Data
  data: { title: 'Products', role: 'user' },

  // Resolver
  resolve: { products: ProductResolver },

  // Lazy loading
  loadChildren: () => import('./products/products.module')
    .then(m => m.ProductsModule)
}
```

**Programmatic navigation:**

```typescript
@Component({...})
export class MyComponent {
  constructor(private router: Router) {}

  goToUser(id: number): void {
    // Navigate with path
    this.router.navigate(['/users', id]);

    // With query params
    this.router.navigate(['/users'], {
      queryParams: { page: 1, sort: 'name' }
    });

    // With navigation extras
    this.router.navigate(['/checkout'], {
      state: { cart: this.cart }
    });
  }
}
```

---

### Q5: How do you access route parameters and query parameters?

**Answer:**

**Route parameters** (path parameters) and **query parameters** are accessed through `ActivatedRoute`:

```typescript
// Route: /users/:id?sort=name&page=1

@Component({...})
export class UserDetailComponent implements OnInit {
  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    // SNAPSHOT - One-time read (use when component doesn't reuse)
    const id = this.route.snapshot.paramMap.get('id');
    const sort = this.route.snapshot.queryParamMap.get('sort');

    // OBSERVABLE - Reactive (use when same component reloads with new params)
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      this.loadUser(id);
    });

    this.route.queryParamMap.subscribe(params => {
      const sort = params.get('sort');
      const page = params.get('page');
    });
  }
}
```

**When to use each:**

```
┌─────────────────────────────────────────────────────────────────────────┐
│                  SNAPSHOT vs OBSERVABLE                                  │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  SNAPSHOT (paramMap):                                                    │
│  └── Use when: Component is destroyed on route change                   │
│  └── Example: /user/1 → /products → /user/2 (new instance)             │
│                                                                          │
│  OBSERVABLE (paramMap):                                                  │
│  └── Use when: Same component instance, different params                │
│  └── Example: /user/1 → /user/2 (same instance, params change)         │
│  └── Required for: Links within same component                         │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**Complete example:**

```typescript
@Component({...})
export class ProductComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Combine params and queryParams
    combineLatest([
      this.route.paramMap,
      this.route.queryParamMap
    ]).pipe(
      takeUntil(this.destroy$)
    ).subscribe(([params, queryParams]) => {
      const productId = params.get('id');
      const tab = queryParams.get('tab') || 'details';
      this.loadProduct(productId, tab);
    });
  }

  changeTab(tab: string): void {
    // Update query params without changing route
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { tab },
      queryParamsHandling: 'merge'  // Keep existing params
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
```

---

## Route Guards

### Q6: What are route guards? Explain the different types.

**Answer:**

**Route guards** are interfaces that control navigation to/from routes.

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        ROUTE GUARD TYPES                                 │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  CanActivate        → Can the user navigate TO this route?              │
│  CanActivateChild   → Can the user navigate to CHILD routes?            │
│  CanDeactivate      → Can the user LEAVE this route?                    │
│  CanMatch           → Should this route be considered for matching?     │
│  Resolve            → Fetch data BEFORE activating route                │
│                                                                          │
│  Navigation Flow:                                                        │
│                                                                          │
│  User clicks link                                                        │
│       │                                                                  │
│       ▼                                                                  │
│  CanMatch? ──No──> Try next route                                       │
│       │ Yes                                                              │
│       ▼                                                                  │
│  CanActivate? ──No──> Block navigation                                  │
│       │ Yes                                                              │
│       ▼                                                                  │
│  Resolve data                                                            │
│       │                                                                  │
│       ▼                                                                  │
│  Activate component                                                      │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**CanActivate example (Authentication):**

```typescript
// auth.guard.ts
export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    return true;
  }

  // Redirect to login with return URL
  return router.createUrlTree(['/login'], {
    queryParams: { returnUrl: state.url }
  });
};

// Usage in routes
{
  path: 'dashboard',
  component: DashboardComponent,
  canActivate: [authGuard]
}
```

**CanDeactivate example (Unsaved changes):**

```typescript
// unsaved-changes.guard.ts
export interface CanComponentDeactivate {
  canDeactivate: () => boolean | Observable<boolean>;
}

export const unsavedChangesGuard: CanDeactivateFn<CanComponentDeactivate> =
  (component) => {
    if (component.canDeactivate()) {
      return true;
    }
    return confirm('You have unsaved changes. Leave anyway?');
  };

// Component implementation
@Component({...})
export class EditFormComponent implements CanComponentDeactivate {
  form: FormGroup;

  canDeactivate(): boolean {
    return !this.form.dirty;  // Allow if no changes
  }
}

// Route
{
  path: 'edit/:id',
  component: EditFormComponent,
  canDeactivate: [unsavedChangesGuard]
}
```

---

## RxJS Fundamentals

### Q7: What is RxJS? Explain Observables vs Promises.

**Answer:**

**RxJS (Reactive Extensions for JavaScript)** is a library for reactive programming using Observables.

```
┌─────────────────────────────────────────────────────────────────────────┐
│                   PROMISE vs OBSERVABLE                                  │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  PROMISE                           OBSERVABLE                            │
│  ───────                           ──────────                            │
│  Single value                      Multiple values over time             │
│  Eager (executes immediately)      Lazy (executes on subscribe)         │
│  Not cancellable                   Cancellable (unsubscribe)            │
│  No operators                      Rich operator library                 │
│  Built into JS                     RxJS library                          │
│                                                                          │
│  Promise:                          Observable:                           │
│  ─────────────────                 ─────────────────                     │
│  getData().then(data => ...)       getData().subscribe(data => ...)     │
│       │                                   │                              │
│       ▼                                   ▼                              │
│  [Single Value]                    [Value] [Value] [Value] ...          │
│       │                                   │                              │
│       ▼                                   ▼                              │
│     Done                           Complete or Error                     │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**Examples:**

```typescript
// PROMISE - Single value
async function fetchUser(): Promise<User> {
  const response = await fetch('/api/user');
  return response.json();
}

// Executes immediately, returns one value
fetchUser().then(user => console.log(user));

// OBSERVABLE - Stream of values
function getMouseClicks(): Observable<MouseEvent> {
  return fromEvent(document, 'click');
}

// Doesn't execute until subscribed
const subscription = getMouseClicks()
  .pipe(
    debounceTime(300),
    map(event => ({ x: event.clientX, y: event.clientY }))
  )
  .subscribe(position => console.log(position));

// Can cancel
subscription.unsubscribe();
```

**Why Observables in Angular:**

```typescript
// HTTP requests return Observables
this.http.get<User[]>('/api/users')
  .pipe(
    retry(3),
    catchError(this.handleError)
  )
  .subscribe(users => this.users = users);

// Form value changes are Observables
this.searchControl.valueChanges
  .pipe(
    debounceTime(300),
    distinctUntilChanged(),
    switchMap(term => this.searchService.search(term))
  )
  .subscribe(results => this.results = results);

// Route params are Observables
this.route.paramMap
  .pipe(map(params => params.get('id')))
  .subscribe(id => this.loadUser(id));
```

---

### Q8: Explain common RxJS operators: map, filter, switchMap, mergeMap.

**Answer:**

**Transformation operators:**

```typescript
import { map, filter, switchMap, mergeMap, tap } from 'rxjs/operators';

// MAP - Transform each value
of(1, 2, 3).pipe(
  map(x => x * 10)
).subscribe(console.log);
// Output: 10, 20, 30

// FILTER - Only emit values passing condition
of(1, 2, 3, 4, 5).pipe(
  filter(x => x % 2 === 0)
).subscribe(console.log);
// Output: 2, 4

// TAP - Side effects without modifying stream
of(1, 2, 3).pipe(
  tap(x => console.log('Before:', x)),
  map(x => x * 2),
  tap(x => console.log('After:', x))
).subscribe();
```

**Flattening operators (handling inner Observables):**

```
┌─────────────────────────────────────────────────────────────────────────┐
│                   FLATTENING OPERATORS                                   │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  switchMap:  Cancel previous, use latest                                │
│              Best for: Search, autocomplete, route changes              │
│                                                                          │
│  mergeMap:   Run all in parallel                                        │
│              Best for: Independent requests, bulk operations            │
│                                                                          │
│  concatMap:  Run sequentially, wait for each                            │
│              Best for: Order matters, queue operations                  │
│                                                                          │
│  exhaustMap: Ignore new until current completes                         │
│              Best for: Prevent duplicate submissions                    │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**Practical examples:**

```typescript
// SWITCHMAP - Search with autocomplete (cancel previous)
this.searchInput.valueChanges.pipe(
  debounceTime(300),
  distinctUntilChanged(),
  switchMap(term => this.searchService.search(term))  // Cancel previous search
).subscribe(results => this.results = results);

// Why switchMap? If user types "ang" then "angular":
// - Request for "ang" is cancelled
// - Only "angular" results returned

// MERGEMAP - Bulk operations (parallel)
this.selectedIds$.pipe(
  mergeMap(ids =>
    ids.map(id => this.http.delete(`/api/items/${id}`))
  )
).subscribe(() => console.log('All deleted'));

// CONCATMAP - Sequential operations (order matters)
this.uploadQueue$.pipe(
  concatMap(file => this.uploadService.upload(file))
).subscribe(result => console.log('Uploaded:', result));

// EXHAUSTMAP - Prevent double submission
this.submitButton.pipe(
  exhaustMap(() => this.saveData())  // Ignore clicks while saving
).subscribe(() => console.log('Saved'));
```

---

## HTTP Client

### Q9: How do you make HTTP requests in Angular? Explain error handling.

**Answer:**

Angular's `HttpClient` makes HTTP requests and returns Observables.

**Setup:**

```typescript
// app.config.ts
export const appConfig: ApplicationConfig = {
  providers: [provideHttpClient()]
};
```

**Service with HTTP calls:**

```typescript
@Injectable({ providedIn: 'root' })
export class UserService {
  private apiUrl = '/api/users';

  constructor(private http: HttpClient) {}

  // GET all
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  // GET one
  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }

  // POST
  createUser(user: CreateUserDto): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }

  // PUT
  updateUser(id: number, user: UpdateUserDto): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, user);
  }

  // DELETE
  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // With options
  getUsersWithHeaders(): Observable<User[]> {
    const headers = new HttpHeaders({
      'Authorization': 'Bearer token',
      'Content-Type': 'application/json'
    });

    return this.http.get<User[]>(this.apiUrl, { headers });
  }
}
```

**Error handling:**

```typescript
@Injectable({ providedIn: 'root' })
export class UserService {
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl).pipe(
      retry(2),  // Retry failed requests
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Unknown error occurred';

    if (error.status === 0) {
      // Client-side or network error
      errorMessage = 'Network error. Please check your connection.';
    } else if (error.status === 401) {
      errorMessage = 'Please log in to continue.';
    } else if (error.status === 403) {
      errorMessage = 'You do not have permission.';
    } else if (error.status === 404) {
      errorMessage = 'Resource not found.';
    } else if (error.status >= 500) {
      errorMessage = 'Server error. Please try again later.';
    }

    console.error('HTTP Error:', error);
    return throwError(() => new Error(errorMessage));
  }
}

// Component usage
this.userService.getUsers().subscribe({
  next: users => this.users = users,
  error: err => this.showError(err.message)
});
```

---

### Q10: What are HTTP Interceptors? How do you implement one?

**Answer:**

**HTTP Interceptors** intercept and modify HTTP requests/responses globally.

**Common use cases:**
- Add authentication headers
- Handle errors globally
- Add loading indicators
- Log requests
- Cache responses

```typescript
// auth.interceptor.ts
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  if (token) {
    const clonedRequest = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
    return next(clonedRequest);
  }

  return next(req);
};

// loading.interceptor.ts
export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const loadingService = inject(LoadingService);

  loadingService.show();

  return next(req).pipe(
    finalize(() => loadingService.hide())
  );
};

// error.interceptor.ts
export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        router.navigate(['/login']);
      }
      return throwError(() => error);
    })
  );
};

// Register interceptors in app.config.ts
export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(
      withInterceptors([
        authInterceptor,
        loadingInterceptor,
        errorInterceptor
      ])
    )
  ]
};
```

**Request flow:**

```
Request:  authInterceptor → loadingInterceptor → errorInterceptor → Server
Response: Server → errorInterceptor → loadingInterceptor → authInterceptor
```

---

## Forms

### Q11: Compare Template-driven and Reactive forms. When to use each?

**Answer:**

```
┌─────────────────────────────────────────────────────────────────────────┐
│              TEMPLATE-DRIVEN vs REACTIVE FORMS                          │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  TEMPLATE-DRIVEN:                  REACTIVE:                            │
│  ────────────────                  ─────────                            │
│  • Define in HTML template         • Define in TypeScript class         │
│  • Less code for simple forms      • More code, more control            │
│  • Two-way binding (ngModel)       • FormControl, FormGroup             │
│  • Directives-based                • Class-based                        │
│  • Async (change detection)        • Sync (direct access)               │
│  • Harder to test                  • Easier to unit test                │
│  • Mutable data model              • Immutable data model               │
│                                                                          │
│  USE WHEN:                         USE WHEN:                            │
│  • Simple forms                    • Complex forms                      │
│  • Prototype/quick forms           • Dynamic fields                     │
│  • Simple validation               • Custom validation                  │
│                                    • Unit testing needed                │
│                                    • Form logic in TypeScript           │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**Template-driven example:**

```typescript
// Import FormsModule
@Component({
  template: `
    <form #userForm="ngForm" (ngSubmit)="onSubmit(userForm)">
      <input name="name" [(ngModel)]="user.name" required #name="ngModel">
      <div *ngIf="name.invalid && name.touched">Name is required</div>

      <input name="email" [(ngModel)]="user.email" required email>

      <button [disabled]="userForm.invalid">Submit</button>
    </form>
  `
})
export class TemplateFormComponent {
  user = { name: '', email: '' };

  onSubmit(form: NgForm) {
    if (form.valid) {
      console.log('Form data:', this.user);
    }
  }
}
```

**Reactive example:**

```typescript
// Import ReactiveFormsModule
@Component({
  template: `
    <form [formGroup]="userForm" (ngSubmit)="onSubmit()">
      <input formControlName="name">
      <div *ngIf="userForm.get('name')?.invalid && userForm.get('name')?.touched">
        Name is required
      </div>

      <input formControlName="email">

      <button [disabled]="userForm.invalid">Submit</button>
    </form>
  `
})
export class ReactiveFormComponent implements OnInit {
  userForm: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.userForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSubmit() {
    if (this.userForm.valid) {
      console.log('Form data:', this.userForm.value);
    }
  }
}
```

---

### Q12: How do you create custom validators in Angular?

**Answer:**

**Synchronous validator:**

```typescript
// Custom validator function
function forbiddenName(forbidden: string): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const value = control.value;
    if (!value) return null;

    const isForbidden = value.toLowerCase() === forbidden.toLowerCase();
    return isForbidden ? { forbiddenName: { value } } : null;
  };
}

// Usage
this.userForm = this.fb.group({
  username: ['', [
    Validators.required,
    forbiddenName('admin')  // Custom validator
  ]]
});

// Template
<div *ngIf="userForm.get('username')?.errors?.['forbiddenName']">
  "{{ userForm.get('username')?.errors?.['forbiddenName'].value }}" is not allowed
</div>
```

**Asynchronous validator (API check):**

```typescript
// Async validator for checking username availability
function uniqueUsername(userService: UserService): AsyncValidatorFn {
  return (control: AbstractControl): Observable<ValidationErrors | null> => {
    if (!control.value) {
      return of(null);
    }

    return userService.checkUsername(control.value).pipe(
      map(isAvailable => isAvailable ? null : { usernameTaken: true }),
      catchError(() => of(null))
    );
  };
}

// Usage
this.userForm = this.fb.group({
  username: ['',
    [Validators.required],           // Sync validators
    [uniqueUsername(this.userService)]  // Async validators
  ]
});
```

**Cross-field validator:**

```typescript
// Validator for password confirmation
function passwordMatch(controlName: string, matchingControlName: string): ValidatorFn {
  return (formGroup: AbstractControl): ValidationErrors | null => {
    const control = formGroup.get(controlName);
    const matchingControl = formGroup.get(matchingControlName);

    if (!control || !matchingControl) return null;

    if (control.value !== matchingControl.value) {
      matchingControl.setErrors({ passwordMismatch: true });
      return { passwordMismatch: true };
    }

    matchingControl.setErrors(null);
    return null;
  };
}

// Usage
this.registerForm = this.fb.group({
  password: ['', Validators.required],
  confirmPassword: ['', Validators.required]
}, {
  validators: passwordMatch('password', 'confirmPassword')
});
```

---

## State Management

### Q13: How do you manage state in Angular applications?

**Answer:**

**State management options:**

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    STATE MANAGEMENT APPROACHES                          │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  1. COMPONENT STATE (Simple)                                            │
│     └── Properties in component class                                   │
│     └── Good for: Local UI state                                        │
│                                                                          │
│  2. SERVICE + SUBJECT (Medium)                                          │
│     └── BehaviorSubject in shared service                               │
│     └── Good for: Shared state, medium apps                            │
│                                                                          │
│  3. NGRX / NGXS (Complex)                                               │
│     └── Redux-style state management                                    │
│     └── Good for: Large apps, complex state                            │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**Service + BehaviorSubject pattern:**

```typescript
// state.service.ts
@Injectable({ providedIn: 'root' })
export class CartService {
  // Private state
  private cartItems = new BehaviorSubject<CartItem[]>([]);

  // Public observable (read-only)
  readonly cartItems$ = this.cartItems.asObservable();

  // Derived state
  readonly totalItems$ = this.cartItems$.pipe(
    map(items => items.reduce((sum, item) => sum + item.quantity, 0))
  );

  readonly totalPrice$ = this.cartItems$.pipe(
    map(items => items.reduce((sum, item) => sum + item.price * item.quantity, 0))
  );

  // Actions
  addItem(item: CartItem): void {
    const current = this.cartItems.value;
    const existing = current.find(i => i.id === item.id);

    if (existing) {
      existing.quantity += item.quantity;
      this.cartItems.next([...current]);
    } else {
      this.cartItems.next([...current, item]);
    }
  }

  removeItem(id: number): void {
    const current = this.cartItems.value;
    this.cartItems.next(current.filter(item => item.id !== id));
  }

  clearCart(): void {
    this.cartItems.next([]);
  }
}

// Component usage
@Component({
  template: `
    <div *ngFor="let item of cartItems$ | async">
      {{ item.name }} - {{ item.quantity }}
      <button (click)="remove(item.id)">Remove</button>
    </div>
    <p>Total: {{ totalPrice$ | async | currency }}</p>
  `
})
export class CartComponent {
  cartItems$ = this.cartService.cartItems$;
  totalPrice$ = this.cartService.totalPrice$;

  constructor(private cartService: CartService) {}

  remove(id: number): void {
    this.cartService.removeItem(id);
  }
}
```

---

### Q14: What is the async pipe? Why should you use it?

**Answer:**

The **async pipe** subscribes to Observables/Promises and returns the latest emitted value.

**Benefits:**

```
┌─────────────────────────────────────────────────────────────────────────┐
│                      ASYNC PIPE BENEFITS                                 │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  1. AUTOMATIC SUBSCRIPTION                                               │
│     └── No need to manually subscribe in component                      │
│                                                                          │
│  2. AUTOMATIC UNSUBSCRIPTION                                             │
│     └── Unsubscribes when component destroys (no memory leaks!)        │
│                                                                          │
│  3. TRIGGERS CHANGE DETECTION                                            │
│     └── View updates automatically when new value emits                 │
│                                                                          │
│  4. CLEANER CODE                                                         │
│     └── Less boilerplate, more declarative                              │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

**Comparison:**

```typescript
// WITHOUT async pipe (manual subscription)
@Component({
  template: `<li *ngFor="let user of users">{{ user.name }}</li>`
})
export class UserListComponent implements OnInit, OnDestroy {
  users: User[] = [];
  private subscription: Subscription;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.subscription = this.userService.getUsers()
      .subscribe(users => this.users = users);
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();  // Must remember to unsubscribe!
  }
}

// WITH async pipe (cleaner)
@Component({
  template: `<li *ngFor="let user of users$ | async">{{ user.name }}</li>`
})
export class UserListComponent {
  users$ = this.userService.getUsers();

  constructor(private userService: UserService) {}
  // No ngOnInit, no ngOnDestroy, no subscription management!
}
```

**Advanced patterns:**

```html
<!-- Handle loading and error states -->
<ng-container *ngIf="users$ | async as users; else loading">
  <li *ngFor="let user of users">{{ user.name }}</li>
</ng-container>
<ng-template #loading>Loading...</ng-template>

<!-- Multiple async pipes -->
<div *ngIf="(user$ | async) as user">
  <h1>{{ user.name }}</h1>
  <p>Orders: {{ (orders$ | async)?.length }}</p>
</div>

<!-- With ngIf for null checking -->
<div *ngIf="data$ | async as data">
  {{ data.value }}  <!-- data is guaranteed to exist -->
</div>
```

---

## Summary

| Topic | Key Concepts |
|-------|--------------|
| **Dependency Injection** | @Injectable, providedIn, injector hierarchy |
| **Services** | Business logic, HTTP calls, shared state |
| **Routing** | RouterModule, routes, router-outlet, guards |
| **Route Guards** | CanActivate, CanDeactivate, Resolve |
| **RxJS** | Observable, Subject, operators (map, switchMap) |
| **HTTP Client** | HttpClient, Observables, interceptors |
| **Template Forms** | ngModel, FormsModule, two-way binding |
| **Reactive Forms** | FormControl, FormGroup, FormBuilder, Validators |
| **State Management** | Service + BehaviorSubject, async pipe |

---

*Week 10 covers advanced Angular concepts essential for building production-ready applications.*

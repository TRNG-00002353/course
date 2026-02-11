# RxJS & HTTP Client

## Introduction to RxJS

RxJS (Reactive Extensions for JavaScript) is a library for composing asynchronous and event-based programs using observable sequences.

```
┌─────────────────────────────────────────────────────────┐
│                      RxJS FLOW                          │
├─────────────────────────────────────────────────────────┤
│                                                         │
│   Data Source ──► Observable ──► Operators ──► Observer │
│                                                         │
│   Examples:                                             │
│   • HTTP responses                                      │
│   • User events (clicks, input)                         │
│   • Timers and intervals                                │
│   • WebSocket messages                                  │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

## Observables

An Observable is a stream of data that can emit multiple values over time.

### Creating Observables

```typescript
import { Observable, of, from, interval, timer } from 'rxjs';

// From static values
const numbers$ = of(1, 2, 3, 4, 5);

// From array
const array$ = from([1, 2, 3, 4, 5]);

// From promise
const promise$ = from(fetch('/api/data'));

// Interval (emits every n milliseconds)
const interval$ = interval(1000);  // 0, 1, 2, 3... every second

// Timer (emits after delay, then optionally repeats)
const timer$ = timer(3000);        // Emits 0 after 3 seconds
const timerRepeat$ = timer(1000, 500);  // Start after 1s, then every 500ms

// Custom Observable
const custom$ = new Observable(subscriber => {
  subscriber.next('Hello');
  subscriber.next('World');

  setTimeout(() => {
    subscriber.next('Delayed');
    subscriber.complete();
  }, 2000);

  // Cleanup function
  return () => {
    console.log('Observable cleaned up');
  };
});
```

### Subscribing to Observables

```typescript
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, interval } from 'rxjs';

@Component({...})
export class DemoComponent implements OnInit, OnDestroy {
  private subscription!: Subscription;

  ngOnInit(): void {
    // Subscribe with observer object
    this.subscription = interval(1000).subscribe({
      next: value => console.log('Value:', value),
      error: err => console.error('Error:', err),
      complete: () => console.log('Complete')
    });

    // Or shorthand (just next callback)
    of(1, 2, 3).subscribe(value => console.log(value));
  }

  ngOnDestroy(): void {
    // IMPORTANT: Always unsubscribe to prevent memory leaks!
    this.subscription.unsubscribe();
  }
}
```

---

## RxJS Operators

Operators transform, filter, or combine observable streams.

### Using pipe()

```typescript
import { of } from 'rxjs';
import { map, filter, tap } from 'rxjs/operators';

of(1, 2, 3, 4, 5).pipe(
  filter(n => n % 2 === 0),  // Keep even numbers
  map(n => n * 10),          // Multiply by 10
  tap(n => console.log(n))   // Side effect (logging)
).subscribe(result => {
  console.log('Final:', result);
});
// Output: 20, 40
```

### Common Operators

#### Transformation Operators

```typescript
import { map, switchMap, mergeMap, concatMap } from 'rxjs/operators';

// map - Transform each value
of(1, 2, 3).pipe(
  map(x => x * 2)
).subscribe(console.log);  // 2, 4, 6

// switchMap - Switch to new observable (cancels previous)
searchInput$.pipe(
  switchMap(query => this.http.get(`/api/search?q=${query}`))
).subscribe(results => console.log(results));

// mergeMap - Merge multiple observables (all run in parallel)
clicks$.pipe(
  mergeMap(() => this.http.get('/api/data'))
).subscribe(data => console.log(data));

// concatMap - Sequential execution (waits for previous)
clicks$.pipe(
  concatMap(() => this.http.post('/api/save', data))
).subscribe(response => console.log(response));
```

#### Filtering Operators

```typescript
import { filter, take, takeUntil, first, distinctUntilChanged, debounceTime } from 'rxjs/operators';

// filter - Keep values matching condition
numbers$.pipe(
  filter(n => n > 5)
).subscribe();

// take - Take first n values then complete
interval(100).pipe(
  take(5)
).subscribe();  // 0, 1, 2, 3, 4, complete

// first - Take first value then complete
users$.pipe(
  first()
).subscribe();

// distinctUntilChanged - Emit only when value changes
input$.pipe(
  distinctUntilChanged()
).subscribe();

// debounceTime - Wait for pause in emissions
searchInput$.pipe(
  debounceTime(300)  // Wait 300ms after user stops typing
).subscribe();
```

#### Error Handling Operators

```typescript
import { catchError, retry, retryWhen } from 'rxjs/operators';
import { of, throwError, delay } from 'rxjs';

// catchError - Handle errors
this.http.get('/api/data').pipe(
  catchError(error => {
    console.error('Error:', error);
    return of([]);  // Return fallback value
  })
).subscribe();

// retry - Retry on failure
this.http.get('/api/data').pipe(
  retry(3)  // Retry up to 3 times
).subscribe();
```

#### Combination Operators

```typescript
import { combineLatest, forkJoin, merge, zip } from 'rxjs';

// combineLatest - Emit when any source emits (after all have emitted once)
combineLatest([users$, products$]).subscribe(([users, products]) => {
  console.log(users, products);
});

// forkJoin - Emit when ALL complete (like Promise.all)
forkJoin({
  users: this.http.get('/api/users'),
  products: this.http.get('/api/products')
}).subscribe(({ users, products }) => {
  console.log(users, products);
});

// merge - Merge multiple streams into one
merge(clicks$, keypress$).subscribe(event => {
  console.log('Event:', event);
});
```

---

## Subjects

Subjects are both Observable and Observer - they can emit and receive values.

### Subject Types

```typescript
import { Subject, BehaviorSubject, ReplaySubject, AsyncSubject } from 'rxjs';

// Subject - No initial value, only emits to current subscribers
const subject = new Subject<string>();
subject.subscribe(val => console.log('A:', val));
subject.next('Hello');  // A: Hello
subject.subscribe(val => console.log('B:', val));
subject.next('World');  // A: World, B: World

// BehaviorSubject - Has initial value, new subscribers get last value
const behavior = new BehaviorSubject<number>(0);
behavior.subscribe(val => console.log('A:', val));  // A: 0
behavior.next(1);  // A: 1
behavior.subscribe(val => console.log('B:', val));  // B: 1 (gets current value)
behavior.next(2);  // A: 2, B: 2

// ReplaySubject - Replays n previous values to new subscribers
const replay = new ReplaySubject<number>(2);  // Remember last 2
replay.next(1);
replay.next(2);
replay.next(3);
replay.subscribe(val => console.log(val));  // 2, 3 (last 2 values)

// AsyncSubject - Only emits last value, and only on complete
const async = new AsyncSubject<number>();
async.next(1);
async.next(2);
async.next(3);
async.subscribe(val => console.log(val));  // Nothing yet
async.complete();  // Now emits: 3
```

### BehaviorSubject for State Management

```typescript
// state.service.ts
@Injectable({ providedIn: 'root' })
export class StateService {
  private userSubject = new BehaviorSubject<User | null>(null);

  // Expose as Observable (read-only)
  user$ = this.userSubject.asObservable();

  // Current value
  get currentUser(): User | null {
    return this.userSubject.value;
  }

  setUser(user: User): void {
    this.userSubject.next(user);
  }

  clearUser(): void {
    this.userSubject.next(null);
  }
}
```

---

## Async Pipe

The `async` pipe subscribes to an Observable and automatically unsubscribes.

```typescript
@Component({
  selector: 'app-user-list',
  template: `
    <!-- Auto-subscribe and unsubscribe -->
    <div *ngIf="users$ | async as users">
      <div *ngFor="let user of users">
        {{ user.name }}
      </div>
    </div>

    <!-- With loading state -->
    <ng-container *ngIf="data$ | async as data; else loading">
      <p>{{ data.message }}</p>
    </ng-container>
    <ng-template #loading>
      <p>Loading...</p>
    </ng-template>

    <!-- Multiple observables -->
    <div *ngIf="{ users: users$ | async, products: products$ | async } as vm">
      <p>Users: {{ vm.users?.length }}</p>
      <p>Products: {{ vm.products?.length }}</p>
    </div>
  `
})
export class UserListComponent {
  users$ = this.http.get<User[]>('/api/users');
  products$ = this.http.get<Product[]>('/api/products');
  data$ = this.http.get('/api/data');

  constructor(private http: HttpClient) {}
}
```

---

## Unsubscribing Patterns

### Manual Unsubscribe

```typescript
@Component({...})
export class MyComponent implements OnDestroy {
  private subscription = new Subscription();

  ngOnInit(): void {
    this.subscription.add(
      this.service.data$.subscribe(data => this.data = data)
    );
    this.subscription.add(
      this.service.events$.subscribe(event => this.handleEvent(event))
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
```

### takeUntil Pattern

```typescript
@Component({...})
export class MyComponent implements OnDestroy {
  private destroy$ = new Subject<void>();

  ngOnInit(): void {
    this.service.data$.pipe(
      takeUntil(this.destroy$)
    ).subscribe(data => this.data = data);

    this.service.events$.pipe(
      takeUntil(this.destroy$)
    ).subscribe(event => this.handleEvent(event));
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
```

### DestroyRef (Angular 16+)

```typescript
import { Component, DestroyRef, inject } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({...})
export class MyComponent {
  private destroyRef = inject(DestroyRef);

  ngOnInit(): void {
    this.service.data$.pipe(
      takeUntilDestroyed(this.destroyRef)
    ).subscribe(data => this.data = data);
  }
}
```

---

## HTTP Client

Angular's HttpClient provides a powerful API for making HTTP requests.

### Setup

```typescript
// app.module.ts
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule  // Add this
  ]
})
export class AppModule { }
```

### Making Requests

```typescript
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private apiUrl = 'https://api.example.com';

  constructor(private http: HttpClient) {}

  // GET request
  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/users`);
  }

  // GET with params
  searchUsers(query: string, page: number): Observable<User[]> {
    const params = new HttpParams()
      .set('q', query)
      .set('page', page.toString());

    return this.http.get<User[]>(`${this.apiUrl}/users`, { params });
  }

  // POST request
  createUser(user: User): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/users`, user);
  }

  // PUT request
  updateUser(id: number, user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/users/${id}`, user);
  }

  // PATCH request
  partialUpdate(id: number, changes: Partial<User>): Observable<User> {
    return this.http.patch<User>(`${this.apiUrl}/users/${id}`, changes);
  }

  // DELETE request
  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/users/${id}`);
  }

  // With headers
  getWithAuth(): Observable<any> {
    const headers = new HttpHeaders()
      .set('Authorization', 'Bearer ' + this.token)
      .set('Content-Type', 'application/json');

    return this.http.get(`${this.apiUrl}/protected`, { headers });
  }
}
```

### Using in Components

```typescript
@Component({
  selector: 'app-user-list',
  template: `
    <div *ngIf="loading">Loading...</div>
    <div *ngIf="error">{{ error }}</div>

    <ul *ngIf="users.length">
      <li *ngFor="let user of users">{{ user.name }}</li>
    </ul>
  `
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  loading = false;
  error = '';

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading = true;
    this.error = '';

    this.apiService.getUsers().subscribe({
      next: users => {
        this.users = users;
        this.loading = false;
      },
      error: err => {
        this.error = 'Failed to load users';
        this.loading = false;
        console.error(err);
      }
    });
  }
}
```

---

## Error Handling

### Service-Level Error Handling

```typescript
@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>('/api/users').pipe(
      retry(2),  // Retry twice on failure
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An error occurred';

    if (error.status === 0) {
      // Network error
      errorMessage = 'Unable to connect to server';
    } else if (error.status === 404) {
      errorMessage = 'Resource not found';
    } else if (error.status === 401) {
      errorMessage = 'Unauthorized';
    } else if (error.status >= 500) {
      errorMessage = 'Server error';
    }

    console.error('API Error:', error);
    return throwError(() => new Error(errorMessage));
  }
}
```

---

## HTTP Interceptors

Interceptors modify requests/responses globally.

### Creating an Interceptor

```typescript
// auth.interceptor.ts
import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.authService.getToken();

    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(request);
  }
}
```

### Functional Interceptor (Angular 15+)

```typescript
// auth.interceptor.ts
import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();

  if (token) {
    req = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });
  }

  return next(req);
};
```

### Logging Interceptor

```typescript
export const loggingInterceptor: HttpInterceptorFn = (req, next) => {
  const started = Date.now();

  return next(req).pipe(
    tap({
      next: event => {
        if (event instanceof HttpResponse) {
          const elapsed = Date.now() - started;
          console.log(`${req.method} ${req.url} - ${elapsed}ms`);
        }
      },
      error: error => {
        const elapsed = Date.now() - started;
        console.error(`${req.method} ${req.url} FAILED - ${elapsed}ms`, error);
      }
    })
  );
};
```

### Error Interceptor

```typescript
export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        router.navigate(['/login']);
      } else if (error.status === 403) {
        router.navigate(['/forbidden']);
      }
      return throwError(() => error);
    })
  );
};
```

### Registering Interceptors

```typescript
// app.config.ts (Angular 17+ standalone)
import { provideHttpClient, withInterceptors } from '@angular/common/http';

export const appConfig = {
  providers: [
    provideHttpClient(
      withInterceptors([authInterceptor, loggingInterceptor, errorInterceptor])
    )
  ]
};

// Or in app.module.ts (module-based)
@NgModule({
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoggingInterceptor,
      multi: true
    }
  ]
})
export class AppModule { }
```

---

## Best Practices

### Service Pattern

```typescript
@Injectable({ providedIn: 'root' })
export class ProductService {
  private apiUrl = '/api/products';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Product[]> {
    return this.http.get<Product[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  getById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  create(product: Omit<Product, 'id'>): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, product).pipe(
      catchError(this.handleError)
    );
  }

  update(id: number, product: Product): Observable<Product> {
    return this.http.put<Product>(`${this.apiUrl}/${id}`, product).pipe(
      catchError(this.handleError)
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('API Error:', error);
    return throwError(() => error);
  }
}
```

---

## Exercise: CRUD with JSONPlaceholder

```typescript
// user.service.ts
@Injectable({ providedIn: 'root' })
export class UserService {
  private apiUrl = 'https://jsonplaceholder.typicode.com/users';

  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`);
  }

  createUser(user: Partial<User>): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }

  updateUser(id: number, user: Partial<User>): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, user);
  }

  deleteUser(id: number): Observable<{}> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
```

```typescript
// user-list.component.ts
@Component({
  selector: 'app-user-list',
  template: `
    <h2>Users</h2>
    <div *ngIf="users$ | async as users; else loading">
      <div *ngFor="let user of users" class="user-card">
        <h3>{{ user.name }}</h3>
        <p>{{ user.email }}</p>
        <button (click)="deleteUser(user.id)">Delete</button>
      </div>
    </div>
    <ng-template #loading>Loading...</ng-template>
  `
})
export class UserListComponent {
  users$ = this.userService.getUsers();

  constructor(private userService: UserService) {}

  deleteUser(id: number): void {
    this.userService.deleteUser(id).subscribe(() => {
      // Refresh list
      this.users$ = this.userService.getUsers();
    });
  }
}
```

---

## Summary

| Concept | Purpose |
|---------|---------|
| Observable | Stream of async data |
| Subject | Observable + Observer |
| BehaviorSubject | Subject with current value |
| `pipe()` | Chain operators |
| `map`, `filter` | Transform/filter values |
| `switchMap` | Switch to new observable |
| `catchError` | Handle errors |
| `async` pipe | Auto-subscribe in template |
| HttpClient | Make HTTP requests |
| Interceptors | Global request/response handling |

## Next Topic

Continue to [Forms & State Management](./07-forms-state.md) to learn about handling user input and application state.

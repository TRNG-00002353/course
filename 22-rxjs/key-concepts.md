# RxJS Key Concepts for Application Developers

## Overview

This document covers essential RxJS (Reactive Extensions for JavaScript) concepts for building reactive applications. RxJS provides powerful tools for handling asynchronous data streams, events, and complex async patterns using Observables.

---

## 1. Observables - Foundation of Reactive Programming

### Why It Matters
- Handle asynchronous data streams uniformly
- Better alternative to callbacks and promises for complex scenarios
- Cancellable and composable
- Core of Angular's reactive architecture

### Key Concepts

| Concept | Description | Use Case |
|---------|-------------|----------|
| Observable | Data stream over time | HTTP requests, user events |
| Observer | Consumer of Observable | Subscribe to receive data |
| Subscription | Connection between them | Control stream lifecycle |
| Operator | Transform stream data | Map, filter, combine streams |
| Subject | Both Observable and Observer | Share data between components |

### Creating Observables
```typescript
import { Observable, of, from, interval, fromEvent } from 'rxjs';

// 1. Using 'of' - Emit values in sequence
const numbers$ = of(1, 2, 3, 4, 5);
numbers$.subscribe(value => console.log(value));
// Output: 1, 2, 3, 4, 5

// 2. Using 'from' - Convert array/promise to Observable
const array$ = from([10, 20, 30]);
const promise$ = from(fetch('/api/data'));

// 3. Using 'interval' - Emit values periodically
const timer$ = interval(1000); // Emits 0, 1, 2... every second

// 4. Using 'fromEvent' - DOM events
const button = document.getElementById('myButton');
const clicks$ = fromEvent(button, 'click');

// 5. Custom Observable
const custom$ = new Observable(observer => {
  observer.next('First value');
  observer.next('Second value');

  setTimeout(() => {
    observer.next('After 1 second');
    observer.complete();
  }, 1000);

  // Cleanup function
  return () => {
    console.log('Unsubscribed');
  };
});
```

### Observable vs Promise

| Feature | Observable | Promise |
|---------|-----------|---------|
| Execution | Lazy (on subscribe) | Eager (immediate) |
| Values | Multiple over time | Single value |
| Cancellation | Yes (unsubscribe) | No |
| Operators | Rich operator library | Limited (then, catch) |
| Use Case | Streams, events | Single async operations |

```typescript
// Promise - executes immediately
const promise = fetch('/api/data');

// Observable - executes on subscribe
const observable$ = new Observable(observer => {
  fetch('/api/data')
    .then(response => observer.next(response))
    .catch(err => observer.error(err));
});
```

---

## 2. Observers & Subscriptions

### Why It Matters
- Observers define how to handle emitted values
- Subscriptions manage the lifecycle
- Proper cleanup prevents memory leaks
- Control when and how data is consumed

### Key Concepts

| Concept | Description | Purpose |
|---------|-------------|---------|
| next() | Handle emitted values | Process data |
| error() | Handle errors | Error handling |
| complete() | Stream completed | Cleanup actions |
| unsubscribe() | Stop receiving values | Prevent memory leaks |

### Observer Object
```typescript
import { Observable } from 'rxjs';

const observable$ = new Observable(observer => {
  observer.next(1);
  observer.next(2);
  observer.next(3);
  observer.complete();
});

// Full observer object
const observer = {
  next: (value: number) => console.log('Next:', value),
  error: (err: Error) => console.error('Error:', err),
  complete: () => console.log('Complete!')
};

observable$.subscribe(observer);

// Partial observer (common pattern)
observable$.subscribe({
  next: (value) => console.log(value),
  error: (err) => console.error(err),
  complete: () => console.log('Done')
});

// Simple subscription (just next handler)
observable$.subscribe(value => console.log(value));
```

### Managing Subscriptions
```typescript
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html'
})
export class UserListComponent implements OnInit, OnDestroy {
  // Single subscription
  private subscription: Subscription;

  // Multiple subscriptions
  private subscriptions = new Subscription();

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    // Pattern 1: Store subscription
    this.subscription = this.userService.getUsers().subscribe({
      next: (users) => console.log(users),
      error: (err) => console.error(err)
    });

    // Pattern 2: Add multiple subscriptions
    this.subscriptions.add(
      this.userService.getUsers().subscribe(users => this.users = users)
    );

    this.subscriptions.add(
      this.searchService.search$.subscribe(term => this.search(term))
    );
  }

  ngOnDestroy(): void {
    // Cleanup - prevent memory leaks
    this.subscription?.unsubscribe();
    this.subscriptions.unsubscribe();
  }
}
```

### Automatic Unsubscription with Async Pipe
```typescript
// Component
export class UserListComponent {
  users$: Observable<User[]>;

  constructor(private userService: UserService) {
    this.users$ = this.userService.getUsers();
  }

  // No need for manual subscription/unsubscription
}
```

```html
<!-- Template - async pipe automatically subscribes/unsubscribes -->
<div *ngIf="users$ | async as users">
  <div *ngFor="let user of users">
    {{ user.name }}
  </div>
</div>
```

---

## 3. Creation Operators

### Why It Matters
- Different ways to create Observables
- Choose the right operator for your data source
- Convert various data types to streams
- Generate data sequences

### Key Operators

| Operator | Purpose | Use Case |
|----------|---------|----------|
| `of()` | Emit values in sequence | Static data |
| `from()` | Convert to Observable | Arrays, promises, iterables |
| `interval()` | Emit numbers periodically | Timers |
| `timer()` | Delay then interval | Delayed execution |
| `fromEvent()` | DOM events | User interactions |
| `ajax()` | HTTP requests | API calls |

### Examples
```typescript
import { of, from, interval, timer, fromEvent, range, generate, EMPTY, throwError } from 'rxjs';

// of - emit static values
of(1, 2, 3).subscribe(console.log);

// from - convert array
from([1, 2, 3]).subscribe(console.log);

// from - convert promise
from(fetch('/api/data')).subscribe(console.log);

// interval - emit every N milliseconds
interval(1000).subscribe(n => console.log(`${n} seconds`));

// timer - delay, then optionally interval
timer(3000).subscribe(() => console.log('After 3 seconds'));
timer(1000, 500).subscribe(n => console.log(n)); // 1s delay, then every 500ms

// fromEvent - DOM events
const button = document.getElementById('btn');
fromEvent(button, 'click').subscribe(() => console.log('Clicked!'));

// range - emit sequence of numbers
range(1, 5).subscribe(console.log); // 1, 2, 3, 4, 5

// generate - custom sequence
generate(
  0,                          // Initial value
  x => x < 10,                // Condition
  x => x + 2                  // Increment
).subscribe(console.log);     // 0, 2, 4, 6, 8

// EMPTY - completes immediately
EMPTY.subscribe({
  next: () => console.log('This will not run'),
  complete: () => console.log('Complete!')
});

// throwError - emit error
throwError(() => new Error('Something went wrong')).subscribe({
  error: (err) => console.error(err)
});
```

---

## 4. Transformation Operators

### Why It Matters
- Transform data as it flows through the stream
- Essential for data manipulation
- Keep components clean with business logic in streams
- Chain multiple transformations

### Key Operators

| Operator | Purpose | Example |
|----------|---------|---------|
| `map()` | Transform each value | Multiply by 2 |
| `mapTo()` | Map to constant | Always return 'clicked' |
| `pluck()` | Extract property | Get user.name |
| `scan()` | Accumulate over time | Running total |
| `reduce()` | Reduce to single value | Sum array |

### Map Operator
```typescript
import { of } from 'rxjs';
import { map } from 'rxjs/operators';

// Transform values
of(1, 2, 3, 4, 5).pipe(
  map(x => x * 2)
).subscribe(console.log); // 2, 4, 6, 8, 10

// Transform objects
interface User {
  id: number;
  firstName: string;
  lastName: string;
}

this.http.get<User[]>('/api/users').pipe(
  map(users => users.map(user => ({
    ...user,
    fullName: `${user.firstName} ${user.lastName}`
  })))
).subscribe(users => console.log(users));

// Extract specific data
this.http.get<ApiResponse>('/api/data').pipe(
  map(response => response.data)
).subscribe(data => console.log(data));
```

### Scan & Reduce
```typescript
import { of } from 'rxjs';
import { scan, reduce } from 'rxjs/operators';

// scan - emit accumulated value at each step
of(1, 2, 3, 4, 5).pipe(
  scan((acc, value) => acc + value, 0)
).subscribe(console.log);
// Output: 1, 3, 6, 10, 15 (running total)

// reduce - emit only final accumulated value
of(1, 2, 3, 4, 5).pipe(
  reduce((acc, value) => acc + value, 0)
).subscribe(console.log);
// Output: 15 (only at completion)

// Real-world example: Shopping cart total
interface CartItem {
  price: number;
  quantity: number;
}

cartItems$.pipe(
  scan((total, item) => total + (item.price * item.quantity), 0)
).subscribe(total => console.log('Cart total:', total));
```

---

## 5. Filtering Operators

### Why It Matters
- Control which values pass through the stream
- Reduce unnecessary processing
- Implement conditional logic
- Handle specific events or data

### Key Operators

| Operator | Purpose | Use Case |
|----------|---------|----------|
| `filter()` | Emit values that pass test | Only even numbers |
| `take()` | Take first N values | First 5 items |
| `takeWhile()` | Take while condition true | Until value > 10 |
| `skip()` | Skip first N values | Ignore initial values |
| `distinct()` | Emit unique values | Remove duplicates |
| `debounceTime()` | Delay emissions | Search input |
| `throttleTime()` | Limit rate | Button clicks |

### Filter Examples
```typescript
import { fromEvent, interval } from 'rxjs';
import { filter, take, takeWhile, skip, first, last, distinct } from 'rxjs/operators';

// filter - conditional emission
of(1, 2, 3, 4, 5, 6).pipe(
  filter(x => x % 2 === 0)
).subscribe(console.log); // 2, 4, 6

// take - first N values
interval(1000).pipe(
  take(5)
).subscribe(console.log); // 0, 1, 2, 3, 4, then completes

// takeWhile - take until condition fails
of(1, 2, 3, 4, 5, 6).pipe(
  takeWhile(x => x < 4)
).subscribe(console.log); // 1, 2, 3

// skip - skip first N values
of(1, 2, 3, 4, 5).pipe(
  skip(2)
).subscribe(console.log); // 3, 4, 5

// first - take first value (or that matches condition)
of(1, 2, 3, 4, 5).pipe(
  first(x => x > 3)
).subscribe(console.log); // 4

// last - take last value
of(1, 2, 3, 4, 5).pipe(
  last()
).subscribe(console.log); // 5

// distinct - unique values only
of(1, 1, 2, 2, 3, 3).pipe(
  distinct()
).subscribe(console.log); // 1, 2, 3
```

### Time-Based Filtering
```typescript
import { fromEvent } from 'rxjs';
import { debounceTime, throttleTime, auditTime, sampleTime } from 'rxjs/operators';

// debounceTime - wait for silence, then emit last value
const searchInput = document.getElementById('search');
fromEvent(searchInput, 'input').pipe(
  debounceTime(300) // Wait 300ms after user stops typing
).subscribe(event => {
  console.log('Search for:', event.target.value);
});

// throttleTime - emit first value, then ignore for N ms
const button = document.getElementById('btn');
fromEvent(button, 'click').pipe(
  throttleTime(1000) // Allow click only once per second
).subscribe(() => console.log('Button clicked'));

// auditTime - emit most recent value after N ms
fromEvent(document, 'mousemove').pipe(
  auditTime(100) // Emit mouse position every 100ms
).subscribe(event => console.log(event.clientX, event.clientY));
```

---

## 6. Combination Operators

### Why It Matters
- Combine multiple Observables
- Coordinate multiple data sources
- Handle parallel operations
- Merge different event streams

### Key Operators

| Operator | Behavior | Use Case |
|----------|----------|----------|
| `merge()` | Emit from all | Multiple event sources |
| `concat()` | Sequential execution | Run after completion |
| `combineLatest()` | Latest from each | Form validation |
| `forkJoin()` | Wait for all to complete | Parallel API calls |
| `zip()` | Pair emissions | Coordinate streams |
| `withLatestFrom()` | Combine with latest | Add context |

### Merge vs Concat
```typescript
import { merge, concat, interval } from 'rxjs';
import { map, take } from 'rxjs/operators';

const source1$ = interval(1000).pipe(
  take(3),
  map(x => `Source 1: ${x}`)
);

const source2$ = interval(1500).pipe(
  take(3),
  map(x => `Source 2: ${x}`)
);

// merge - interleaved emissions
merge(source1$, source2$).subscribe(console.log);
// Output: Source 1: 0, Source 1: 1, Source 2: 0, Source 1: 2, Source 2: 1...

// concat - wait for first to complete
concat(source1$, source2$).subscribe(console.log);
// Output: Source 1: 0, Source 1: 1, Source 1: 2, then Source 2: 0, Source 2: 1...
```

### CombineLatest
```typescript
import { combineLatest } from 'rxjs';

// Form validation example
const username$ = this.form.get('username').valueChanges;
const password$ = this.form.get('password').valueChanges;
const email$ = this.form.get('email').valueChanges;

combineLatest([username$, password$, email$]).pipe(
  map(([username, password, email]) => ({
    isValid: username.length > 3 && password.length > 8 && email.includes('@')
  }))
).subscribe(result => {
  this.isFormValid = result.isValid;
});

// Alternative syntax
combineLatest({
  username: username$,
  password: password$,
  email: email$
}).subscribe(values => console.log(values));
```

### ForkJoin
```typescript
import { forkJoin } from 'rxjs';

// Wait for multiple HTTP requests
forkJoin({
  users: this.http.get('/api/users'),
  posts: this.http.get('/api/posts'),
  comments: this.http.get('/api/comments')
}).subscribe(({ users, posts, comments }) => {
  console.log('All data loaded:', users, posts, comments);
});

// Array syntax
forkJoin([
  this.userService.getUser(1),
  this.userService.getUser(2),
  this.userService.getUser(3)
]).subscribe(([user1, user2, user3]) => {
  console.log('Users:', user1, user2, user3);
});
```

### WithLatestFrom
```typescript
import { fromEvent } from 'rxjs';
import { withLatestFrom } from 'rxjs/operators';

// Add user context to click events
const clicks$ = fromEvent(button, 'click');
const currentUser$ = this.authService.currentUser$;

clicks$.pipe(
  withLatestFrom(currentUser$)
).subscribe(([clickEvent, user]) => {
  console.log(`${user.name} clicked the button`);
});
```

---

## 7. Error Handling Operators

### Why It Matters
- Gracefully handle errors in streams
- Retry failed operations
- Provide fallback values
- Prevent application crashes

### Key Operators

| Operator | Purpose | Use Case |
|----------|---------|----------|
| `catchError()` | Handle errors | Recovery logic |
| `retry()` | Retry on error | Transient failures |
| `retryWhen()` | Custom retry logic | Exponential backoff |
| `throwError()` | Create error Observable | Testing |

### CatchError
```typescript
import { of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

// Basic error handling
this.http.get<User[]>('/api/users').pipe(
  catchError(error => {
    console.error('Error loading users:', error);
    return of([]); // Return empty array as fallback
  })
).subscribe(users => console.log(users));

// Transform and rethrow
this.http.get<Data>('/api/data').pipe(
  map(data => data.value),
  catchError(error => {
    console.error('API Error:', error);
    return throwError(() => new Error('Failed to load data'));
  })
).subscribe({
  next: (value) => console.log(value),
  error: (err) => console.error('Component error:', err)
});

// Different handling based on error type
this.http.get('/api/data').pipe(
  catchError(error => {
    if (error.status === 404) {
      return of({ data: [] }); // Not found, return empty
    } else if (error.status === 401) {
      this.router.navigate(['/login']);
      return throwError(() => new Error('Unauthorized'));
    } else {
      return throwError(() => error);
    }
  })
).subscribe();
```

### Retry Strategies
```typescript
import { retry, retryWhen, delay, take, tap } from 'rxjs/operators';

// Simple retry - retry N times
this.http.get('/api/data').pipe(
  retry(3), // Retry up to 3 times
  catchError(error => {
    console.error('Failed after 3 retries');
    return of(null);
  })
).subscribe();

// Retry with delay
this.http.get('/api/data').pipe(
  retryWhen(errors => errors.pipe(
    delay(1000), // Wait 1 second between retries
    take(3),     // Maximum 3 retries
    tap(() => console.log('Retrying...'))
  )),
  catchError(error => {
    console.error('Failed after retries with delay');
    return of(null);
  })
).subscribe();

// Exponential backoff
this.http.get('/api/data').pipe(
  retryWhen(errors => errors.pipe(
    scan((retryCount, error) => {
      if (retryCount >= 3) {
        throw error;
      }
      return retryCount + 1;
    }, 0),
    delay(1000), // Could make this exponential: retryCount * 1000
    tap(retryCount => console.log(`Retry attempt ${retryCount}`))
  )),
  catchError(error => of(null))
).subscribe();
```

---

## 8. Subjects - Multicast Observables

### Why It Matters
- Share data between multiple subscribers
- Act as both Observable and Observer
- Implement event buses
- Manage shared state

### Key Subject Types

| Type | Behavior | Use Case |
|------|----------|----------|
| `Subject` | No initial value | Simple event bus |
| `BehaviorSubject` | Has initial value | State management |
| `ReplaySubject` | Replay N values | Recent history |
| `AsyncSubject` | Only last value | Single result |

### Subject
```typescript
import { Subject } from 'rxjs';

// Simple Subject
const subject = new Subject<number>();

// Multiple subscribers
subject.subscribe(value => console.log('Subscriber A:', value));
subject.subscribe(value => console.log('Subscriber B:', value));

// Emit values
subject.next(1); // Both subscribers receive 1
subject.next(2); // Both subscribers receive 2

// New subscriber won't receive previous values
subject.subscribe(value => console.log('Subscriber C:', value));
subject.next(3); // All three receive 3
```

### BehaviorSubject
```typescript
import { BehaviorSubject } from 'rxjs';

// State management with BehaviorSubject
export class UserService {
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadCurrentUser();
  }

  private loadCurrentUser(): void {
    this.http.get<User>('/api/current-user').subscribe(
      user => this.currentUserSubject.next(user)
    );
  }

  login(credentials: Credentials): Observable<User> {
    return this.http.post<User>('/api/login', credentials).pipe(
      tap(user => this.currentUserSubject.next(user))
    );
  }

  logout(): void {
    this.http.post('/api/logout', {}).subscribe(() => {
      this.currentUserSubject.next(null);
    });
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value; // Get current value synchronously
  }
}

// Component usage
export class HeaderComponent implements OnInit {
  currentUser$: Observable<User | null>;

  constructor(private userService: UserService) {
    this.currentUser$ = this.userService.currentUser$;
  }
}
```

```html
<!-- Template -->
<div *ngIf="currentUser$ | async as user">
  Welcome, {{ user.name }}!
</div>
```

### ReplaySubject
```typescript
import { ReplaySubject } from 'rxjs';

// Replay last N values to new subscribers
const replay$ = new ReplaySubject<number>(3); // Buffer size 3

replay$.next(1);
replay$.next(2);
replay$.next(3);
replay$.next(4);

// New subscriber receives last 3 values
replay$.subscribe(value => console.log('Subscriber:', value));
// Output: 2, 3, 4

// Use case: Recent notifications
export class NotificationService {
  private notificationsSubject = new ReplaySubject<Notification>(5);
  public notifications$ = this.notificationsSubject.asObservable();

  addNotification(notification: Notification): void {
    this.notificationsSubject.next(notification);
  }
}
```

### AsyncSubject
```typescript
import { AsyncSubject } from 'rxjs';

// Only emits last value when completed
const async$ = new AsyncSubject<number>();

async$.subscribe(value => console.log('Subscriber:', value));

async$.next(1);
async$.next(2);
async$.next(3);
async$.complete(); // Only now subscriber receives: 3
```

---

## 9. Higher-Order Operators

### Why It Matters
- Handle Observables that emit Observables
- Flatten nested streams
- Manage concurrent operations
- Control subscription behavior

### Key Operators

| Operator | Behavior | Use Case |
|----------|---------|----------|
| `mergeMap` | Concurrent subscriptions | Parallel requests |
| `switchMap` | Cancel previous | Search typeahead |
| `concatMap` | Sequential subscriptions | Order matters |
| `exhaustMap` | Ignore while active | Prevent duplicate submissions |

### MergeMap (flatMap)
```typescript
import { of } from 'rxjs';
import { mergeMap, delay } from 'rxjs/operators';

// Parallel execution
of(1, 2, 3).pipe(
  mergeMap(id => this.http.get(`/api/users/${id}`))
).subscribe(user => console.log(user));
// All requests run in parallel

// Real-world: Multiple simultaneous operations
this.userIds$.pipe(
  mergeMap(id => this.userService.getUserDetails(id))
).subscribe(details => console.log(details));
```

### SwitchMap
```typescript
import { fromEvent } from 'rxjs';
import { map, switchMap, debounceTime, distinctUntilChanged } from 'rxjs/operators';

// Search typeahead - cancel previous search
const searchInput = document.getElementById('search');

fromEvent(searchInput, 'input').pipe(
  map(event => event.target.value),
  debounceTime(300),
  distinctUntilChanged(),
  switchMap(searchTerm => this.http.get(`/api/search?q=${searchTerm}`))
).subscribe(results => console.log(results));

// Angular example
export class SearchComponent {
  searchControl = new FormControl('');
  results$: Observable<SearchResult[]>;

  constructor(private searchService: SearchService) {
    this.results$ = this.searchControl.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap(term => this.searchService.search(term))
    );
  }
}
```

### ConcatMap
```typescript
import { of } from 'rxjs';
import { concatMap, delay } from 'rxjs/operators';

// Sequential execution - wait for each to complete
of(1, 2, 3).pipe(
  concatMap(id => this.http.post('/api/process', { id }).pipe(delay(1000)))
).subscribe(result => console.log(result));
// Executes one at a time, in order

// Use case: Sequential file uploads
this.files$.pipe(
  concatMap(file => this.uploadService.upload(file))
).subscribe(response => console.log('Uploaded:', response));
```

### ExhaustMap
```typescript
import { fromEvent } from 'rxjs';
import { exhaustMap } from 'rxjs/operators';

// Ignore clicks while request is in progress
const saveButton = document.getElementById('save');

fromEvent(saveButton, 'click').pipe(
  exhaustMap(() => this.http.post('/api/save', data))
).subscribe(() => console.log('Saved'));
// Subsequent clicks ignored until request completes

// Login form - prevent multiple submissions
this.loginForm.valueChanges.pipe(
  exhaustMap(credentials => this.authService.login(credentials))
).subscribe(result => console.log('Logged in'));
```

---

## 10. Utility Operators

### Why It Matters
- Inspect stream behavior
- Add side effects without modifying stream
- Debug complex pipelines
- Monitor performance

### Key Operators

| Operator | Purpose | Use Case |
|----------|---------|----------|
| `tap()` | Side effects | Logging, debugging |
| `delay()` | Delay emissions | Simulate latency |
| `timeout()` | Error if too slow | Prevent hanging |
| `shareReplay()` | Share and cache | HTTP requests |

### Tap (do)
```typescript
import { tap } from 'rxjs/operators';

// Logging and debugging
this.http.get<User[]>('/api/users').pipe(
  tap(users => console.log('Received users:', users.length)),
  map(users => users.filter(u => u.active)),
  tap(active => console.log('Active users:', active.length))
).subscribe(users => this.users = users);

// Side effects (careful with state mutation)
this.http.get<User>('/api/current-user').pipe(
  tap(user => {
    // Update analytics
    analytics.identify(user.id);
    // Update cache
    this.cache.set('currentUser', user);
  })
).subscribe(user => this.currentUser = user);
```

### ShareReplay
```typescript
import { shareReplay } from 'rxjs/operators';

// Prevent multiple HTTP requests
export class DataService {
  private users$: Observable<User[]>;

  constructor(private http: HttpClient) {
    // Create shared, cached observable
    this.users$ = this.http.get<User[]>('/api/users').pipe(
      shareReplay(1) // Cache last emission, share with all subscribers
    );
  }

  getUsers(): Observable<User[]> {
    return this.users$; // Always returns same observable
  }
}

// Multiple subscriptions trigger only one HTTP request
this.dataService.getUsers().subscribe(users => console.log('A:', users));
this.dataService.getUsers().subscribe(users => console.log('B:', users));
// Only one HTTP request made
```

### Delay & Timeout
```typescript
import { delay, timeout } from 'rxjs/operators';

// Add delay
of('Hello').pipe(
  delay(1000)
).subscribe(console.log); // Logs after 1 second

// Timeout if too slow
this.http.get('/api/data').pipe(
  timeout(5000), // Error if takes more than 5 seconds
  catchError(error => {
    if (error.name === 'TimeoutError') {
      console.error('Request timed out');
    }
    return of(null);
  })
).subscribe();
```

---

## 11. Async Patterns & Best Practices

### Why It Matters
- Write cleaner, more maintainable code
- Prevent common pitfalls
- Optimize performance
- Follow Angular conventions

### Common Patterns

| Pattern | Implementation | Benefit |
|---------|---------------|---------|
| Async Pipe | `{{ data$ \| async }}` | Auto subscribe/unsubscribe |
| Service with Subject | BehaviorSubject in service | State management |
| Smart/Dumb Components | Observable inputs | Separation of concerns |
| Error Handling | catchError + retry | Resilience |

### Pattern: Async Pipe
```typescript
// Component - expose Observable
export class UsersComponent {
  users$: Observable<User[]>;
  loading$: Observable<boolean>;
  error$: Observable<string | null>;

  constructor(private userService: UserService) {
    this.users$ = this.userService.getUsers();
  }
}
```

```html
<!-- Template - use async pipe -->
<div *ngIf="users$ | async as users; else loading">
  <div *ngFor="let user of users">
    {{ user.name }}
  </div>
</div>

<ng-template #loading>
  <div>Loading...</div>
</ng-template>
```

### Pattern: State Management Service
```typescript
interface AppState {
  users: User[];
  loading: boolean;
  error: string | null;
}

@Injectable({ providedIn: 'root' })
export class StateService {
  private stateSubject = new BehaviorSubject<AppState>({
    users: [],
    loading: false,
    error: null
  });

  public state$ = this.stateSubject.asObservable();

  // Selectors
  public users$ = this.state$.pipe(map(state => state.users));
  public loading$ = this.state$.pipe(map(state => state.loading));
  public error$ = this.state$.pipe(map(state => state.error));

  loadUsers(): void {
    this.updateState({ loading: true, error: null });

    this.http.get<User[]>('/api/users').subscribe({
      next: (users) => this.updateState({ users, loading: false }),
      error: (error) => this.updateState({ loading: false, error: error.message })
    });
  }

  private updateState(partial: Partial<AppState>): void {
    this.stateSubject.next({
      ...this.stateSubject.value,
      ...partial
    });
  }
}
```

### Pattern: Unsubscribe Strategies
```typescript
// Strategy 1: takeUntil with destroy$
export class Component implements OnDestroy {
  private destroy$ = new Subject<void>();

  ngOnInit(): void {
    this.dataService.getData().pipe(
      takeUntil(this.destroy$)
    ).subscribe(data => this.data = data);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}

// Strategy 2: Subscription collection
export class Component implements OnDestroy {
  private subscriptions = new Subscription();

  ngOnInit(): void {
    this.subscriptions.add(
      this.dataService.getData().subscribe(data => this.data = data)
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }
}

// Strategy 3: Async pipe (preferred)
export class Component {
  data$ = this.dataService.getData();
  // Template: {{ data$ | async }}
}
```

---

## Quick Reference Card

### Essential Operators
```typescript
// Creation
of(1, 2, 3)
from([1, 2, 3])
interval(1000)

// Transformation
map(x => x * 2)
scan((acc, x) => acc + x, 0)

// Filtering
filter(x => x > 5)
take(5)
debounceTime(300)
distinctUntilChanged()

// Combination
merge(obs1$, obs2$)
combineLatest([obs1$, obs2$])
forkJoin({ a: obs1$, b: obs2$ })

// Higher-order
switchMap(x => this.http.get(`/api/${x}`))
mergeMap(x => this.process(x))
concatMap(x => this.sequential(x))

// Error handling
catchError(err => of([]))
retry(3)

// Utility
tap(x => console.log(x))
shareReplay(1)
delay(1000)
```

### Common Patterns
```typescript
// HTTP with error handling
this.http.get('/api/data').pipe(
  retry(2),
  catchError(err => {
    console.error(err);
    return of([]);
  })
)

// Search typeahead
this.searchControl.valueChanges.pipe(
  debounceTime(300),
  distinctUntilChanged(),
  switchMap(term => this.search(term))
)

// State management
private subject = new BehaviorSubject<T>(initial);
public data$ = this.subject.asObservable();
```

---

## Summary Checklist

By the end of this module, developers should be able to:

- [ ] Create Observables using various creation operators
- [ ] Subscribe to Observables with proper observer patterns
- [ ] Manage subscriptions and prevent memory leaks
- [ ] Transform data using map, scan, and related operators
- [ ] Filter streams with conditional and time-based operators
- [ ] Combine multiple Observables using combination operators
- [ ] Handle errors gracefully with retry and catchError
- [ ] Use Subjects for multicasting and state management
- [ ] Apply higher-order operators for nested Observables
- [ ] Implement common async patterns in Angular
- [ ] Use async pipe for automatic subscription management
- [ ] Debug Observable streams effectively

---

## Next Steps

After mastering these concepts, proceed to:
- [Module 23: Docker](../23-docker/) - Containerize your Angular applications
- Practice by refactoring Promise-based code to Observables
- Explore advanced RxJS patterns and operators
- Learn NgRx for large-scale state management
- Study RxJS testing strategies with marble diagrams

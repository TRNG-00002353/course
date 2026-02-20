# Testing & Debugging

## Why Testing Matters

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        WHY WRITE TESTS?                                  │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  Without Tests:                      With Tests:                         │
│  ┌────────────────────┐              ┌────────────────────┐              │
│  │ Change code        │              │ Change code        │              │
│  │        ↓           │              │        ↓           │              │
│  │ Manually test      │              │ Run tests (1 sec)  │              │
│  │ everything (30min) │              │        ↓           │              │
│  │        ↓           │              │ ✓ All pass? Ship!  │              │
│  │ Hope nothing broke │              │ ✗ Fail? Fix issue  │              │
│  │        ↓           │              │        ↓           │              │
│  │ Users find bugs    │              │ Confident deploy   │              │
│  └────────────────────┘              └────────────────────┘              │
│                                                                          │
│  Tests give you CONFIDENCE that your code works correctly!               │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Real-World Example

Imagine you're building a shopping cart:
- You add a "remove item" feature
- Without tests: Did you break "add item"? "Calculate total"? "Checkout"?
- With tests: Run all tests in 2 seconds → Everything still works!

---

## Angular Testing Stack

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     ANGULAR TESTING STACK                                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ┌──────────────┐                                                        │
│  │   JASMINE    │  Testing FRAMEWORK - provides the test structure       │
│  │              │  • describe() - groups related tests                   │
│  │  "The Rules" │  • it() - individual test case                         │
│  │              │  • expect() - assertions (checks)                      │
│  └──────────────┘                                                        │
│         ↓                                                                │
│  ┌──────────────┐                                                        │
│  │    KARMA     │  Test RUNNER - executes your tests                     │
│  │              │  • Opens a browser                                     │
│  │ "The Runner" │  • Runs all test files                                 │
│  │              │  • Shows pass/fail results                             │
│  └──────────────┘                                                        │
│         ↓                                                                │
│  ┌──────────────┐                                                        │
│  │   TestBed    │  Angular's testing utilities                           │
│  │              │  • Creates components for testing                      │
│  │ "The Helper" │  • Mocks services                                      │
│  │              │  • Simulates Angular environment                       │
│  └──────────────┘                                                        │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Test Types Comparison

| Type | What It Tests | Speed | Example |
|------|---------------|-------|---------|
| **Unit Test** | One piece in isolation | Very Fast | Does `add(2,3)` return `5`? |
| **Integration Test** | Component + Template together | Fast | Does clicking button update view? |
| **E2E Test** | Full app from user perspective | Slow | Can user login and see dashboard? |

> **Focus on Unit and Integration tests** - They catch most bugs and run fast!

---

## Jasmine Basics: Your First Test

### The Anatomy of a Test

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        TEST FILE STRUCTURE                               │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  describe('Calculator', () => {      ← TEST SUITE (group of tests)      │
│    │                                                                     │
│    │  beforeEach(() => {             ← SETUP (runs before EACH test)    │
│    │    // prepare stuff                                                 │
│    │  });                                                                │
│    │                                                                     │
│    │  it('should add numbers', () => {  ← TEST CASE (one test)          │
│    │    │                                                                │
│    │    │  // Arrange - set up data                                      │
│    │    │  const a = 2, b = 3;                                           │
│    │    │                                                                │
│    │    │  // Act - do the thing                                         │
│    │    │  const result = add(a, b);                                     │
│    │    │                                                                │
│    │    │  // Assert - check the result                                  │
│    │    │  expect(result).toBe(5);      ← ASSERTION (the actual check)  │
│    │    │                                                                │
│    │  });                                                                │
│    │                                                                     │
│  });                                                                     │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Simple Example: Testing a Calculator

```typescript
// calculator.ts - The code we want to test
export function add(a: number, b: number): number {
  return a + b;
}

export function subtract(a: number, b: number): number {
  return a - b;
}

export function divide(a: number, b: number): number {
  if (b === 0) throw new Error('Cannot divide by zero');
  return a / b;
}
```

```typescript
// calculator.spec.ts - The test file (.spec.ts is the convention)
import { add, subtract, divide } from './calculator';

describe('Calculator', () => {

  // Test 1: Simple addition
  it('should add two numbers', () => {
    const result = add(2, 3);
    expect(result).toBe(5);  // "I expect result to be 5"
  });

  // Test 2: Subtraction
  it('should subtract two numbers', () => {
    const result = subtract(10, 4);
    expect(result).toBe(6);
  });

  // Test 3: Edge case - negative numbers
  it('should handle negative numbers', () => {
    const result = add(-5, 3);
    expect(result).toBe(-2);
  });

  // Test 4: Error handling
  it('should throw error when dividing by zero', () => {
    expect(() => divide(10, 0)).toThrowError('Cannot divide by zero');
  });
});
```

### Reading Test Results

When you run `ng test`, you'll see:

```
Chrome Headless: Executed 4 of 4 SUCCESS (0.05 secs)

Calculator
  ✓ should add two numbers
  ✓ should subtract two numbers
  ✓ should handle negative numbers
  ✓ should throw error when dividing by zero
```

If a test fails:

```
Calculator
  ✗ should add two numbers
    FAILED: Expected 6 to be 5.
    ← This tells you EXACTLY what went wrong!
```

---

## Common Matchers (Assertions)

```
┌─────────────────────────────────────────────────────────────────────────┐
│                   JASMINE MATCHERS CHEAT SHEET                          │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  EQUALITY                                                                │
│  ─────────                                                               │
│  expect(4).toBe(4);              // Strict equals (===)                  │
│  expect({a:1}).toEqual({a:1});   // Deep equals (for objects/arrays)    │
│                                                                          │
│  ⚠️  toBe vs toEqual:                                                    │
│      toBe:    {a:1} === {a:1}  →  FALSE (different objects in memory)   │
│      toEqual: {a:1} == {a:1}   →  TRUE (same content)                   │
│                                                                          │
│  TRUTHINESS                                                              │
│  ──────────                                                              │
│  expect(true).toBeTruthy();      // Any truthy value                    │
│  expect(false).toBeFalsy();      // Any falsy value (0, '', null, etc)  │
│  expect(null).toBeNull();                                               │
│  expect(undefined).toBeUndefined();                                     │
│  expect(value).toBeDefined();    // Not undefined                       │
│                                                                          │
│  NUMBERS                                                                 │
│  ───────                                                                 │
│  expect(10).toBeGreaterThan(5);                                         │
│  expect(5).toBeLessThan(10);                                            │
│  expect(3.14159).toBeCloseTo(3.14, 2);  // 2 decimal places             │
│                                                                          │
│  STRINGS & ARRAYS                                                        │
│  ────────────────                                                        │
│  expect('Hello World').toContain('World');                              │
│  expect('Hello').toMatch(/^He/);   // Regex match                       │
│  expect([1,2,3]).toContain(2);                                          │
│  expect([1,2,3]).toHaveSize(3);                                         │
│                                                                          │
│  NEGATION (add .not)                                                     │
│  ───────────────────                                                     │
│  expect(5).not.toBe(3);                                                 │
│  expect('Hello').not.toContain('Bye');                                  │
│                                                                          │
│  ERRORS                                                                  │
│  ──────                                                                  │
│  expect(() => badFunction()).toThrow();                                 │
│  expect(() => badFunction()).toThrowError('specific message');          │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Running Tests with Karma

### Basic Commands

```bash
# Run tests (opens browser, watches for changes)
ng test

# Run tests once and exit (for CI/CD)
ng test --watch=false

# Run with code coverage report
ng test --code-coverage

# Run specific test file
ng test --include=**/calculator.spec.ts

# Run in headless mode (no browser window)
ng test --watch=false --browsers=ChromeHeadless
```

### Understanding the Karma Browser Window

```
┌─────────────────────────────────────────────────────────────────────────┐
│ Karma - Chrome                                                    - □ x │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  Karma v6.4.1 - connected                                               │
│                                                                          │
│  ┌─────────────────────────────────────────────────────────────────┐    │
│  │  12 specs, 0 failures                              [Debug]      │    │
│  │                                                                  │    │
│  │  Calculator                                                      │    │
│  │    ✓ should add two numbers                                      │    │
│  │    ✓ should subtract two numbers                                 │    │
│  │                                                                  │    │
│  │  GreetingComponent                                               │    │
│  │    ✓ should create                                               │    │
│  │    ✓ should display name                                         │    │
│  │    ...                                                           │    │
│  └─────────────────────────────────────────────────────────────────┘    │
│                                                                          │
│  Click "Debug" to see detailed output and debug in browser DevTools     │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Testing Components: Step by Step

### The Component Testing Flow

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    COMPONENT TEST FLOW                                   │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  1. CONFIGURE                                                            │
│     TestBed.configureTestingModule({...})                               │
│              ↓                                                           │
│     Creates a mini Angular module just for testing                      │
│                                                                          │
│  2. CREATE                                                               │
│     fixture = TestBed.createComponent(MyComponent)                      │
│              ↓                                                           │
│     fixture = { component, nativeElement, debugElement }                │
│                                                                          │
│  3. DETECT CHANGES                                                       │
│     fixture.detectChanges()                                             │
│              ↓                                                           │
│     Triggers Angular's change detection (updates the view)              │
│                                                                          │
│  4. QUERY & ASSERT                                                       │
│     fixture.nativeElement.querySelector('h1')                           │
│              ↓                                                           │
│     Find elements and check their content                               │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Example 1: Simple Component Test

```typescript
// greeting.component.ts
@Component({
  selector: 'app-greeting',
  template: `<h1>Hello, {{ name }}!</h1>`
})
export class GreetingComponent {
  @Input() name = 'World';
}
```

```typescript
// greeting.component.spec.ts
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { GreetingComponent } from './greeting.component';

describe('GreetingComponent', () => {
  // Variables we'll use in our tests
  let component: GreetingComponent;      // The component instance
  let fixture: ComponentFixture<GreetingComponent>;  // Test wrapper

  // Runs BEFORE EACH test
  beforeEach(async () => {
    // Step 1: Configure the testing module
    await TestBed.configureTestingModule({
      declarations: [GreetingComponent]  // Components to test
    }).compileComponents();

    // Step 2: Create the component
    fixture = TestBed.createComponent(GreetingComponent);
    component = fixture.componentInstance;

    // Step 3: Trigger change detection
    fixture.detectChanges();
  });

  // Test 1: Does it create?
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test 2: Check default value
  it('should display default name "World"', () => {
    // Query the DOM
    const h1 = fixture.nativeElement.querySelector('h1');

    // Check the content
    expect(h1.textContent).toContain('Hello, World!');
  });

  // Test 3: Check @Input works
  it('should display custom name when provided', () => {
    // Change the input
    component.name = 'Angular';

    // IMPORTANT: Must detect changes after modifying data!
    fixture.detectChanges();

    // Check updated view
    const h1 = fixture.nativeElement.querySelector('h1');
    expect(h1.textContent).toContain('Hello, Angular!');
  });
});
```

### Understanding fixture.detectChanges()

```
┌─────────────────────────────────────────────────────────────────────────┐
│                   WHY detectChanges() IS NEEDED                          │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  In a real app, Angular automatically detects changes.                  │
│  In tests, YOU control when Angular updates the view.                   │
│                                                                          │
│  component.name = 'Angular';   ← Data changed, but view is still old    │
│                                                                          │
│  VIEW:  <h1>Hello, World!</h1>  ← Still shows "World"!                  │
│                                                                          │
│  fixture.detectChanges();      ← Tell Angular to update                 │
│                                                                          │
│  VIEW:  <h1>Hello, Angular!</h1>  ← Now it shows "Angular"              │
│                                                                          │
│  ⚠️  RULE: Call detectChanges() after ANY data change!                  │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Testing User Interactions

### Example: Testing Button Clicks

```typescript
// counter.component.ts
@Component({
  selector: 'app-counter',
  template: `
    <button class="decrement" (click)="decrement()">-</button>
    <span class="count">{{ count }}</span>
    <button class="increment" (click)="increment()">+</button>
  `
})
export class CounterComponent {
  count = 0;

  increment() { this.count++; }
  decrement() { this.count--; }
}
```

```typescript
// counter.component.spec.ts
describe('CounterComponent', () => {
  let component: CounterComponent;
  let fixture: ComponentFixture<CounterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CounterComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(CounterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should start with count 0', () => {
    const span = fixture.nativeElement.querySelector('.count');
    expect(span.textContent).toContain('0');
  });

  it('should increment count when + button clicked', () => {
    // Find the button
    const incrementBtn = fixture.nativeElement.querySelector('.increment');

    // Simulate click
    incrementBtn.click();

    // Update view
    fixture.detectChanges();

    // Check result
    const span = fixture.nativeElement.querySelector('.count');
    expect(span.textContent).toContain('1');
  });

  it('should decrement count when - button clicked', () => {
    // Set initial value
    component.count = 5;
    fixture.detectChanges();

    // Click decrement
    const decrementBtn = fixture.nativeElement.querySelector('.decrement');
    decrementBtn.click();
    fixture.detectChanges();

    // Verify
    expect(component.count).toBe(4);
  });
});
```

---

## Testing @Output Events

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    TESTING @OUTPUT EVENTS                                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  Component emits event  →  Parent receives it  →  Something happens     │
│                                                                          │
│  In tests, we need to:                                                  │
│  1. SPY on the EventEmitter                                             │
│  2. Trigger the action (like clicking delete)                           │
│  3. VERIFY the event was emitted with correct data                      │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

```typescript
// task-item.component.ts
@Component({
  selector: 'app-task-item',
  template: `
    <div class="task">
      <span>{{ task.title }}</span>
      <button (click)="onDelete()">Delete</button>
    </div>
  `
})
export class TaskItemComponent {
  @Input() task!: { id: number; title: string };
  @Output() deleted = new EventEmitter<number>();

  onDelete(): void {
    this.deleted.emit(this.task.id);  // Emit the task ID
  }
}
```

```typescript
// task-item.component.spec.ts
describe('TaskItemComponent', () => {
  let component: TaskItemComponent;
  let fixture: ComponentFixture<TaskItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TaskItemComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(TaskItemComponent);
    component = fixture.componentInstance;

    // Provide required @Input
    component.task = { id: 42, title: 'Learn Testing' };
    fixture.detectChanges();
  });

  it('should display task title', () => {
    const span = fixture.nativeElement.querySelector('span');
    expect(span.textContent).toContain('Learn Testing');
  });

  it('should emit deleted event with task id when delete clicked', () => {
    // Step 1: Create a spy on the EventEmitter
    spyOn(component.deleted, 'emit');

    // Step 2: Click the delete button
    const deleteBtn = fixture.nativeElement.querySelector('button');
    deleteBtn.click();

    // Step 3: Verify the event was emitted with correct value
    expect(component.deleted.emit).toHaveBeenCalledWith(42);
    expect(component.deleted.emit).toHaveBeenCalledTimes(1);
  });
});
```

---

## Testing Services

### Why Mock Dependencies?

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     WHY WE MOCK (FAKE) SERVICES                          │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  Real Service:                        Mocked Service:                    │
│  ┌──────────────────┐                 ┌──────────────────┐               │
│  │ UserService      │                 │ Mock UserService │               │
│  │   ↓              │                 │   ↓              │               │
│  │ HttpClient       │                 │ Returns fake     │               │
│  │   ↓              │                 │ data instantly   │               │
│  │ Real Server      │                 └──────────────────┘               │
│  │   ↓              │                                                    │
│  │ Database         │                 Benefits:                          │
│  └──────────────────┘                 • Fast (no network)                │
│                                        • Predictable (same data)         │
│  Problems:                             • Isolated (tests one thing)      │
│  • Slow (network call)                 • No side effects                 │
│  • Flaky (server might be down)                                          │
│  • Changes data (might create real users!)                               │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Example: Testing a Service with HttpClient

```typescript
// user.service.ts
@Injectable({ providedIn: 'root' })
export class UserService {
  constructor(private http: HttpClient) {}

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>('/api/users');
  }

  getUser(id: number): Observable<User> {
    return this.http.get<User>(`/api/users/${id}`);
  }
}
```

```typescript
// user.service.spec.ts
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;  // Controls fake HTTP requests

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],  // Fake HttpClient
      providers: [UserService]
    });

    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  // IMPORTANT: Verify no unexpected requests after each test
  afterEach(() => {
    httpMock.verify();  // Fails if there are outstanding requests
  });

  it('should fetch all users', () => {
    // Fake data that our mock will return
    const mockUsers: User[] = [
      { id: 1, name: 'John', email: 'john@test.com' },
      { id: 2, name: 'Jane', email: 'jane@test.com' }
    ];

    // Call the service method
    service.getUsers().subscribe(users => {
      expect(users.length).toBe(2);
      expect(users[0].name).toBe('John');
    });

    // Expect a GET request to this URL
    const req = httpMock.expectOne('/api/users');
    expect(req.request.method).toBe('GET');

    // Respond with mock data
    req.flush(mockUsers);
  });

  it('should fetch single user by id', () => {
    const mockUser = { id: 1, name: 'John', email: 'john@test.com' };

    service.getUser(1).subscribe(user => {
      expect(user.name).toBe('John');
      expect(user.id).toBe(1);
    });

    const req = httpMock.expectOne('/api/users/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);
  });

  it('should handle HTTP error', () => {
    service.getUsers().subscribe({
      next: () => fail('should have failed'),
      error: (error) => {
        expect(error.status).toBe(500);
      }
    });

    const req = httpMock.expectOne('/api/users');
    req.flush('Server error', { status: 500, statusText: 'Internal Server Error' });
  });
});
```

### HTTP Testing Flow

```
┌─────────────────────────────────────────────────────────────────────────┐
│                      HTTP TESTING FLOW                                   │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  1. service.getUsers().subscribe(...)   ← Make the "request"            │
│                     ↓                                                    │
│  2. httpMock.expectOne('/api/users')    ← Catch the request             │
│                     ↓                                                    │
│  3. req.flush(mockData)                 ← Send fake response            │
│                     ↓                                                    │
│  4. subscribe callback runs with mockData                               │
│                     ↓                                                    │
│  5. httpMock.verify()                   ← Ensure no pending requests    │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Testing Components with Service Dependencies

```typescript
// user-list.component.ts
@Component({
  selector: 'app-user-list',
  template: `
    <div *ngIf="loading">Loading...</div>
    <ul *ngIf="!loading">
      <li *ngFor="let user of users">{{ user.name }}</li>
    </ul>
  `
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  loading = false;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loading = true;
    this.userService.getUsers().subscribe(users => {
      this.users = users;
      this.loading = false;
    });
  }
}
```

```typescript
// user-list.component.spec.ts
describe('UserListComponent', () => {
  let component: UserListComponent;
  let fixture: ComponentFixture<UserListComponent>;
  let userServiceSpy: jasmine.SpyObj<UserService>;

  beforeEach(async () => {
    // Create a spy object with the methods we need
    userServiceSpy = jasmine.createSpyObj('UserService', ['getUsers']);

    await TestBed.configureTestingModule({
      declarations: [UserListComponent],
      providers: [
        // Replace real service with our spy
        { provide: UserService, useValue: userServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(UserListComponent);
    component = fixture.componentInstance;
  });

  it('should show loading initially', () => {
    // Make getUsers return an Observable that never completes
    userServiceSpy.getUsers.and.returnValue(new Subject<User[]>());

    fixture.detectChanges();  // Triggers ngOnInit

    const loading = fixture.nativeElement.querySelector('div');
    expect(loading.textContent).toContain('Loading...');
  });

  it('should display users after loading', () => {
    const mockUsers = [
      { id: 1, name: 'John', email: 'john@test.com' },
      { id: 2, name: 'Jane', email: 'jane@test.com' }
    ];

    // Make getUsers return our mock data immediately
    userServiceSpy.getUsers.and.returnValue(of(mockUsers));

    fixture.detectChanges();

    const listItems = fixture.nativeElement.querySelectorAll('li');
    expect(listItems.length).toBe(2);
    expect(listItems[0].textContent).toContain('John');
    expect(listItems[1].textContent).toContain('Jane');
  });
});
```

---

## Testing Pipes

Pipes are the **easiest** to test - they're just classes with a `transform` method!

```typescript
// truncate.pipe.ts
@Pipe({ name: 'truncate' })
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit: number = 50): string {
    if (!value) return '';
    return value.length > limit ? value.substring(0, limit) + '...' : value;
  }
}
```

```typescript
// truncate.pipe.spec.ts
describe('TruncatePipe', () => {
  let pipe: TruncatePipe;

  beforeEach(() => {
    pipe = new TruncatePipe();  // Just create the pipe directly!
  });

  it('should return empty string for null', () => {
    expect(pipe.transform(null as any)).toBe('');
  });

  it('should not truncate short strings', () => {
    expect(pipe.transform('Hello', 10)).toBe('Hello');
  });

  it('should truncate long strings and add ellipsis', () => {
    const input = 'This is a very long string';
    expect(pipe.transform(input, 10)).toBe('This is a ...');
  });

  it('should use default limit of 50', () => {
    const input = 'x'.repeat(60);  // 60 x's
    const result = pipe.transform(input);
    expect(result.length).toBe(53);  // 50 chars + '...'
  });
});
```

---

## Debugging Angular Applications

### Console Debugging

```typescript
// Quick debugging tricks in your component
export class DebugComponent {
  data: any;

  ngOnInit(): void {
    this.service.getData().subscribe({
      next: data => {
        console.log('Data:', data);           // Basic log
        console.table(data);                   // Nice table format
        console.dir(data);                     // Expandable object
        debugger;                              // Pause execution here!
        this.data = data;
      },
      error: err => {
        console.error('Error:', err);         // Red error message
        console.trace();                       // Show call stack
      }
    });
  }
}
```

### Template Debugging

```html
<!-- Show object contents in the template -->
<pre>{{ myObject | json }}</pre>

<!-- Debug specific values -->
<p>Debug: count = {{ count }}, loading = {{ loading }}</p>
```

### Chrome DevTools Tips

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    CHROME DEVTOOLS FOR ANGULAR                          │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  1. ELEMENTS TAB                                                         │
│     Select element → Console → ng.getComponent($0)                      │
│     Returns the component instance for that element                     │
│                                                                          │
│  2. NETWORK TAB                                                          │
│     See all HTTP requests, their payloads, and responses                │
│     Filter by "XHR" to see only AJAX calls                              │
│                                                                          │
│  3. SOURCES TAB                                                          │
│     • Find your .ts files under webpack://                              │
│     • Click line numbers to set breakpoints                             │
│     • Step through code with F10 (over) / F11 (into)                    │
│                                                                          │
│  4. CONSOLE TAB                                                          │
│     Type variable names to inspect their values                         │
│     Use console.log() in your code to output data                       │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

### Angular DevTools Extension

Install the [Angular DevTools](https://chrome.google.com/webstore/detail/angular-devtools) Chrome extension for:

- **Component Tree**: See all components and their hierarchy
- **State Inspection**: View and edit component properties
- **Change Detection Profiler**: Find performance issues

---

## Common Testing Mistakes

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     COMMON MISTAKES TO AVOID                             │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ✗ MISTAKE                           ✓ FIX                              │
│  ─────────────────────────────────────────────────────────────────────  │
│                                                                          │
│  Forgetting detectChanges()          Always call after data changes     │
│  component.name = 'Test';            component.name = 'Test';           │
│  expect(h1.textContent)...           fixture.detectChanges();           │
│                                       expect(h1.textContent)...          │
│                                                                          │
│  ─────────────────────────────────────────────────────────────────────  │
│                                                                          │
│  Using toBe for objects              Use toEqual for objects/arrays     │
│  expect({a:1}).toBe({a:1})  // FAIL  expect({a:1}).toEqual({a:1})      │
│                                                                          │
│  ─────────────────────────────────────────────────────────────────────  │
│                                                                          │
│  Testing implementation              Test behavior instead              │
│  expect(component.flag).toBe(true)   expect(button.disabled).toBe(true)│
│                                                                          │
│  ─────────────────────────────────────────────────────────────────────  │
│                                                                          │
│  Not providing required @Inputs      Provide all @Inputs before test    │
│  fixture.detectChanges() // ERROR    component.user = mockUser;         │
│                                       fixture.detectChanges();           │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Quick Reference

### Test File Naming

```
component.ts      → component.spec.ts
service.ts        → service.spec.ts
pipe.ts           → pipe.spec.ts
```

### Minimal Test Template

```typescript
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MyComponent } from './my.component';

describe('MyComponent', () => {
  let component: MyComponent;
  let fixture: ComponentFixture<MyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MyComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(MyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Add more tests here...
});
```

### Summary Table

| What to Test | How to Test |
|--------------|-------------|
| Component creates | `expect(component).toBeTruthy()` |
| Text displays | `querySelector('h1').textContent` |
| @Input works | Set input, detectChanges, check view |
| @Output works | `spyOn(output, 'emit')`, trigger, verify |
| Button click | `button.click()`, detectChanges |
| Service method | Mock with `jasmine.createSpyObj` |
| HTTP calls | Use `HttpClientTestingModule` |
| Pipe transform | Create instance, call `transform()` |

---

## Best Practices Checklist

- [ ] Test behavior, not implementation details
- [ ] Keep tests independent (no shared state)
- [ ] Use descriptive test names: "should show error when email is invalid"
- [ ] Always call `detectChanges()` after changing data
- [ ] Mock external dependencies (services, HTTP)
- [ ] Test edge cases (null, empty, boundary values)
- [ ] Run tests before committing code
- [ ] Aim for meaningful coverage, not 100% coverage

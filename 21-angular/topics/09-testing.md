# Testing & Debugging

## Testing Overview

Angular provides built-in support for testing with Jasmine and Karma.

```
┌─────────────────────────────────────────────────────────┐
│                 ANGULAR TESTING STACK                   │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  Jasmine       → Testing framework (describe, it, expect)
│  Karma         → Test runner (executes in browser)      │
│  TestBed       → Angular testing utilities              │
│                                                         │
│  Test Types:                                            │
│  • Unit Tests      → Test isolated pieces               │
│  • Integration     → Test component + template          │
│  • E2E Tests       → Test full application flow         │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

## Jasmine Basics

### Test Structure

```typescript
describe('Calculator', () => {
  // Setup before each test
  beforeEach(() => {
    // Initialize
  });

  // Cleanup after each test
  afterEach(() => {
    // Cleanup
  });

  // Individual test
  it('should add two numbers', () => {
    const result = add(2, 3);
    expect(result).toBe(5);
  });

  it('should subtract two numbers', () => {
    const result = subtract(5, 3);
    expect(result).toBe(2);
  });

  // Nested describe for grouping
  describe('division', () => {
    it('should divide two numbers', () => {
      expect(divide(10, 2)).toBe(5);
    });

    it('should throw error for division by zero', () => {
      expect(() => divide(10, 0)).toThrowError();
    });
  });
});
```

### Common Matchers

```typescript
// Equality
expect(value).toBe(expected);           // Strict equality (===)
expect(value).toEqual(expected);        // Deep equality for objects
expect(value).toBeTruthy();
expect(value).toBeFalsy();
expect(value).toBeNull();
expect(value).toBeUndefined();
expect(value).toBeDefined();

// Numbers
expect(value).toBeGreaterThan(3);
expect(value).toBeLessThan(10);
expect(value).toBeCloseTo(3.14, 2);     // 2 decimal places

// Strings
expect(str).toContain('substring');
expect(str).toMatch(/pattern/);

// Arrays
expect(array).toContain(item);
expect(array).toHaveSize(3);

// Objects
expect(obj).toHaveProperty('name');

// Negation
expect(value).not.toBe(other);

// Errors
expect(() => fn()).toThrow();
expect(() => fn()).toThrowError('message');
```

### Spies (Mocking)

```typescript
describe('UserService', () => {
  let service: UserService;
  let httpSpy: jasmine.SpyObj<HttpClient>;

  beforeEach(() => {
    // Create spy object
    httpSpy = jasmine.createSpyObj('HttpClient', ['get', 'post']);

    service = new UserService(httpSpy);
  });

  it('should fetch users', () => {
    const mockUsers = [{ id: 1, name: 'John' }];
    httpSpy.get.and.returnValue(of(mockUsers));

    service.getUsers().subscribe(users => {
      expect(users).toEqual(mockUsers);
    });

    expect(httpSpy.get).toHaveBeenCalledWith('/api/users');
    expect(httpSpy.get).toHaveBeenCalledTimes(1);
  });

  it('should create user', () => {
    const newUser = { name: 'Jane' };
    httpSpy.post.and.returnValue(of({ id: 2, ...newUser }));

    service.createUser(newUser).subscribe(user => {
      expect(user.id).toBe(2);
    });

    expect(httpSpy.post).toHaveBeenCalledWith('/api/users', newUser);
  });
});
```

---

## Karma Test Runner

### Running Tests

```bash
# Run tests once
ng test

# Run tests with coverage
ng test --code-coverage

# Run specific test file
ng test --include=**/user.service.spec.ts

# Watch mode (default)
ng test

# Single run (CI)
ng test --watch=false --browsers=ChromeHeadless
```

### Karma Configuration

```javascript
// karma.conf.js
module.exports = function (config) {
  config.set({
    basePath: '',
    frameworks: ['jasmine', '@angular-devkit/build-angular'],
    plugins: [
      require('karma-jasmine'),
      require('karma-chrome-launcher'),
      require('karma-jasmine-html-reporter'),
      require('karma-coverage'),
      require('@angular-devkit/build-angular/plugins/karma')
    ],
    client: {
      jasmine: {},
      clearContext: false
    },
    jasmineHtmlReporter: {
      suppressAll: true
    },
    coverageReporter: {
      dir: require('path').join(__dirname, './coverage'),
      subdir: '.',
      reporters: [
        { type: 'html' },
        { type: 'text-summary' }
      ]
    },
    reporters: ['progress', 'kjhtml'],
    browsers: ['Chrome'],
    restartOnFileChange: true
  });
};
```

---

## Testing Components

### Basic Component Test

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
  let component: GreetingComponent;
  let fixture: ComponentFixture<GreetingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GreetingComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(GreetingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display default name', () => {
    const h1 = fixture.nativeElement.querySelector('h1');
    expect(h1.textContent).toContain('Hello, World!');
  });

  it('should display custom name', () => {
    component.name = 'Angular';
    fixture.detectChanges();

    const h1 = fixture.nativeElement.querySelector('h1');
    expect(h1.textContent).toContain('Hello, Angular!');
  });
});
```

### Testing with Dependencies

```typescript
// user-profile.component.ts
@Component({
  selector: 'app-user-profile',
  template: `
    <div *ngIf="user">
      <h2>{{ user.name }}</h2>
      <p>{{ user.email }}</p>
    </div>
    <p *ngIf="loading">Loading...</p>
  `
})
export class UserProfileComponent implements OnInit {
  user: User | null = null;
  loading = false;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loading = true;
    this.userService.getCurrentUser().subscribe(user => {
      this.user = user;
      this.loading = false;
    });
  }
}
```

```typescript
// user-profile.component.spec.ts
describe('UserProfileComponent', () => {
  let component: UserProfileComponent;
  let fixture: ComponentFixture<UserProfileComponent>;
  let userServiceSpy: jasmine.SpyObj<UserService>;

  beforeEach(async () => {
    userServiceSpy = jasmine.createSpyObj('UserService', ['getCurrentUser']);

    await TestBed.configureTestingModule({
      declarations: [UserProfileComponent],
      providers: [
        { provide: UserService, useValue: userServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(UserProfileComponent);
    component = fixture.componentInstance;
  });

  it('should show loading initially', () => {
    userServiceSpy.getCurrentUser.and.returnValue(new Subject());
    fixture.detectChanges();

    const loading = fixture.nativeElement.querySelector('p');
    expect(loading.textContent).toContain('Loading...');
  });

  it('should display user after load', () => {
    const mockUser = { id: 1, name: 'John', email: 'john@test.com' };
    userServiceSpy.getCurrentUser.and.returnValue(of(mockUser));

    fixture.detectChanges();

    const h2 = fixture.nativeElement.querySelector('h2');
    const p = fixture.nativeElement.querySelector('p');
    expect(h2.textContent).toContain('John');
    expect(p.textContent).toContain('john@test.com');
  });
});
```

### Testing Events

```typescript
// counter.component.ts
@Component({
  selector: 'app-counter',
  template: `
    <button (click)="decrement()">-</button>
    <span>{{ count }}</span>
    <button (click)="increment()">+</button>
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
  let fixture: ComponentFixture<CounterComponent>;
  let component: CounterComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CounterComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(CounterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should increment count on click', () => {
    const incrementBtn = fixture.nativeElement.querySelectorAll('button')[1];

    incrementBtn.click();
    fixture.detectChanges();

    const span = fixture.nativeElement.querySelector('span');
    expect(span.textContent).toContain('1');
  });

  it('should decrement count on click', () => {
    component.count = 5;
    fixture.detectChanges();

    const decrementBtn = fixture.nativeElement.querySelectorAll('button')[0];
    decrementBtn.click();
    fixture.detectChanges();

    expect(component.count).toBe(4);
  });
});
```

### Testing Outputs

```typescript
// item.component.ts
@Component({
  selector: 'app-item',
  template: `
    <div>
      {{ item.name }}
      <button (click)="onDelete()">Delete</button>
    </div>
  `
})
export class ItemComponent {
  @Input() item!: { id: number; name: string };
  @Output() deleted = new EventEmitter<number>();

  onDelete(): void {
    this.deleted.emit(this.item.id);
  }
}
```

```typescript
// item.component.spec.ts
describe('ItemComponent', () => {
  let fixture: ComponentFixture<ItemComponent>;
  let component: ItemComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ItemComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(ItemComponent);
    component = fixture.componentInstance;
    component.item = { id: 1, name: 'Test Item' };
    fixture.detectChanges();
  });

  it('should emit deleted event with item id', () => {
    spyOn(component.deleted, 'emit');

    const deleteBtn = fixture.nativeElement.querySelector('button');
    deleteBtn.click();

    expect(component.deleted.emit).toHaveBeenCalledWith(1);
  });
});
```

---

## Testing Services

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

  createUser(user: Partial<User>): Observable<User> {
    return this.http.post<User>('/api/users', user);
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
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    });

    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    // Verify no outstanding requests
    httpMock.verify();
  });

  it('should fetch users', () => {
    const mockUsers: User[] = [
      { id: 1, name: 'John', email: 'john@test.com' },
      { id: 2, name: 'Jane', email: 'jane@test.com' }
    ];

    service.getUsers().subscribe(users => {
      expect(users.length).toBe(2);
      expect(users).toEqual(mockUsers);
    });

    const req = httpMock.expectOne('/api/users');
    expect(req.request.method).toBe('GET');
    req.flush(mockUsers);
  });

  it('should fetch single user', () => {
    const mockUser: User = { id: 1, name: 'John', email: 'john@test.com' };

    service.getUser(1).subscribe(user => {
      expect(user).toEqual(mockUser);
    });

    const req = httpMock.expectOne('/api/users/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);
  });

  it('should create user', () => {
    const newUser = { name: 'New User', email: 'new@test.com' };
    const createdUser = { id: 3, ...newUser };

    service.createUser(newUser).subscribe(user => {
      expect(user.id).toBe(3);
    });

    const req = httpMock.expectOne('/api/users');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newUser);
    req.flush(createdUser);
  });

  it('should handle error', () => {
    service.getUsers().subscribe({
      next: () => fail('should have failed'),
      error: (error) => {
        expect(error.status).toBe(500);
      }
    });

    const req = httpMock.expectOne('/api/users');
    req.flush('Error', { status: 500, statusText: 'Server Error' });
  });
});
```

---

## Testing Pipes

```typescript
// truncate.pipe.ts
@Pipe({ name: 'truncate' })
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit: number = 50, trail: string = '...'): string {
    if (!value) return '';
    return value.length > limit ? value.substring(0, limit) + trail : value;
  }
}
```

```typescript
// truncate.pipe.spec.ts
describe('TruncatePipe', () => {
  let pipe: TruncatePipe;

  beforeEach(() => {
    pipe = new TruncatePipe();
  });

  it('should return empty string for null/undefined', () => {
    expect(pipe.transform(null as any)).toBe('');
    expect(pipe.transform(undefined as any)).toBe('');
  });

  it('should not truncate short strings', () => {
    expect(pipe.transform('Hello', 10)).toBe('Hello');
  });

  it('should truncate long strings', () => {
    const input = 'This is a very long string that should be truncated';
    expect(pipe.transform(input, 20)).toBe('This is a very long ...');
  });

  it('should use default limit of 50', () => {
    const input = 'x'.repeat(60);
    const result = pipe.transform(input);
    expect(result.length).toBe(53); // 50 + '...'
  });

  it('should use custom trail', () => {
    expect(pipe.transform('Hello World', 5, '---')).toBe('Hello---');
  });
});
```

---

## Debugging Angular Applications

### Chrome DevTools

```typescript
// Access component in console
// 1. Select element in Elements tab
// 2. In Console, type:
ng.getComponent($0)  // Get component instance

// Inspect Angular state
ng.getContext($0)    // Get component context
ng.getOwningComponent($0)  // Get parent component
```

### Angular DevTools Extension

Install [Angular DevTools](https://chrome.google.com/webstore/detail/angular-devtools) Chrome extension.

Features:
- Component tree visualization
- Change detection profiling
- Dependency injection inspection
- Component state editing

### Console Debugging

```typescript
@Component({...})
export class DebugComponent implements OnInit {
  data: any;

  ngOnInit(): void {
    this.service.getData().subscribe({
      next: data => {
        console.log('Data received:', data);  // Basic logging
        console.table(data);                   // Table format
        console.dir(data);                     // Object inspection
        debugger;                              // Breakpoint
        this.data = data;
      },
      error: err => {
        console.error('Error:', err);
        console.trace();  // Stack trace
      }
    });
  }
}
```

### VS Code Debugging

1. Install "Debugger for Chrome" extension
2. Create `.vscode/launch.json`:

```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "chrome",
      "request": "launch",
      "name": "Debug Angular",
      "url": "http://localhost:4200",
      "webRoot": "${workspaceFolder}",
      "sourceMapPathOverrides": {
        "webpack:/*": "${webRoot}/*"
      }
    }
  ]
}
```

3. Run `ng serve`
4. Press F5 to start debugging
5. Set breakpoints in TypeScript files

### Common Debug Techniques

```typescript
// Temporary debug output in template
<pre>{{ someObject | json }}</pre>

// Log lifecycle events
ngOnInit() { console.log('Component initialized'); }
ngOnChanges(changes) { console.log('Changes:', changes); }

// Track change detection
ngDoCheck() { console.log('Change detection ran'); }

// Network tab for HTTP debugging
// - Check request/response
// - Verify headers
// - Check payload
```

---

## Summary

| Concept | Purpose |
|---------|---------|
| Jasmine | Testing framework (describe, it, expect) |
| Karma | Test runner (browser execution) |
| TestBed | Angular testing utilities |
| `ComponentFixture` | Wrapper for component testing |
| `HttpTestingController` | Mock HTTP requests |
| Spies | Mock functions and track calls |
| Chrome DevTools | Browser debugging |
| Angular DevTools | Angular-specific debugging |
| VS Code Debugger | IDE debugging with breakpoints |

### Test File Naming

```
component.ts       → component.spec.ts
service.ts         → service.spec.ts
pipe.ts           → pipe.spec.ts
directive.ts      → directive.spec.ts
```

### Best Practices

1. Test behavior, not implementation
2. Keep tests independent
3. Use descriptive test names
4. Mock external dependencies
5. Test edge cases and error scenarios
6. Run tests before committing

---

## Angular Module Complete

You have completed the Angular curriculum covering:
- Components, Templates, Data Binding
- Directives and Pipes
- Services and Dependency Injection
- Routing and Navigation
- RxJS and HTTP Client
- Forms (Template-driven and Reactive)
- State Management
- Lifecycle Hooks and Change Detection
- Testing with Jasmine and Karma

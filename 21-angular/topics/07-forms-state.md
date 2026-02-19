# Forms & State Management

## Forms Overview

Angular provides two approaches to handling forms:

```
┌─────────────────────────────────────────────────────────┐
│                  ANGULAR FORMS                          │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  Template-Driven Forms          Reactive Forms          │
│  ─────────────────────          ──────────────          │
│  • Defined in template          • Defined in component  │
│  • [(ngModel)] binding          • FormControl objects   │
│  • Async validation             • Sync validation       │
│  • Less boilerplate             • More testable         │
│  • Good for simple forms        • Good for complex forms│
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

## Template-Driven Forms

### Setup

```typescript
// app.module.ts
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [FormsModule]
})
export class AppModule { }
```

### Basic Form

```typescript
@Component({
  selector: 'app-login',
  template: `
    <form #loginForm="ngForm" (ngSubmit)="onSubmit(loginForm)">
      <div class="form-group">
        <label for="email">Email</label>
        <input
          type="email"
          id="email"
          name="email"
          [(ngModel)]="user.email"
          required
          email
          #emailInput="ngModel">
        <div *ngIf="emailInput.invalid && emailInput.touched" class="error">
          <span *ngIf="emailInput.errors?.['required']">Email is required</span>
          <span *ngIf="emailInput.errors?.['email']">Invalid email format</span>
        </div>
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input
          type="password"
          id="password"
          name="password"
          [(ngModel)]="user.password"
          required
          minlength="6"
          #passwordInput="ngModel">
        <div *ngIf="passwordInput.invalid && passwordInput.touched" class="error">
          <span *ngIf="passwordInput.errors?.['required']">Password is required</span>
          <span *ngIf="passwordInput.errors?.['minlength']">
            Minimum 6 characters
          </span>
        </div>
      </div>

      <button type="submit" [disabled]="loginForm.invalid">Login</button>

      <pre>Form Valid: {{ loginForm.valid }}</pre>
      <pre>Form Value: {{ loginForm.value | json }}</pre>
    </form>
  `
})
export class LoginComponent {
  user = {
    email: '',
    password: ''
  };

  onSubmit(form: NgForm): void {
    if (form.valid) {
      console.log('Form submitted:', form.value);
    }
  }
}
```

### Form States

| State | Description |
|-------|-------------|
| `pristine` | Form has not been modified |
| `dirty` | Form has been modified |
| `touched` | Form field has been focused and blurred |
| `untouched` | Form field has not been touched |
| `valid` | Form passes all validations |
| `invalid` | Form has validation errors |

### Built-in Validators (Template-Driven)

```html
<!-- Required -->
<input required>

<!-- Minimum length -->
<input minlength="3">

<!-- Maximum length -->
<input maxlength="50">

<!-- Pattern (regex) -->
<input pattern="[a-zA-Z]*">

<!-- Email -->
<input type="email" email>

<!-- Min/Max for numbers -->
<input type="number" min="0" max="100">
```

---

## Reactive Forms

### Setup

```typescript
// app.module.ts
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [ReactiveFormsModule]
})
export class AppModule { }
```

### Basic Reactive Form

```typescript
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  template: `
    <form [formGroup]="registerForm" (ngSubmit)="onSubmit()">
      <div class="form-group">
        <label for="username">Username</label>
        <input id="username" formControlName="username">
        <div *ngIf="registerForm.get('username')?.invalid &&
                    registerForm.get('username')?.touched"
             class="error">
          <span *ngIf="registerForm.get('username')?.errors?.['required']">
            Username is required
          </span>
          <span *ngIf="registerForm.get('username')?.errors?.['minlength']">
            Minimum 3 characters
          </span>
        </div>
      </div>

      <div class="form-group">
        <label for="email">Email</label>
        <input id="email" formControlName="email">
        <div *ngIf="email.invalid && email.touched" class="error">
          <span *ngIf="email.errors?.['required']">Email is required</span>
          <span *ngIf="email.errors?.['email']">Invalid email</span>
        </div>
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input id="password" type="password" formControlName="password">
      </div>

      <button type="submit" [disabled]="registerForm.invalid">Register</button>
    </form>
  `
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;

  ngOnInit(): void {
    this.registerForm = new FormGroup({
      username: new FormControl('', [
        Validators.required,
        Validators.minLength(3)
      ]),
      email: new FormControl('', [
        Validators.required,
        Validators.email
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(6)
      ])
    });
  }

  // Getter for easy access in template
  get email() {
    return this.registerForm.get('email')!;
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      console.log('Form submitted:', this.registerForm.value);
    }
  }
}
```

### FormBuilder (Cleaner Syntax)

```typescript
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({...})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required],
      address: this.fb.group({
        street: [''],
        city: [''],
        zipCode: ['', Validators.pattern(/^\d{5}$/)]
      })
    });
  }
}
```

### Nested Form Groups

```typescript
@Component({
  selector: 'app-profile',
  template: `
    <form [formGroup]="profileForm" (ngSubmit)="onSubmit()">
      <input formControlName="name">

      <div formGroupName="address">
        <input formControlName="street" placeholder="Street">
        <input formControlName="city" placeholder="City">
        <input formControlName="zipCode" placeholder="Zip">
      </div>

      <button type="submit">Save</button>
    </form>
  `
})
export class ProfileComponent implements OnInit {
  profileForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.profileForm = this.fb.group({
      name: ['', Validators.required],
      address: this.fb.group({
        street: [''],
        city: [''],
        zipCode: ['']
      })
    });
  }
}
```

### FormArray (Dynamic Fields)

```typescript
@Component({
  selector: 'app-skills',
  template: `
    <form [formGroup]="skillsForm">
      <div formArrayName="skills">
        <div *ngFor="let skill of skills.controls; let i = index">
          <input [formControlName]="i">
          <button type="button" (click)="removeSkill(i)">Remove</button>
        </div>
      </div>

      <button type="button" (click)="addSkill()">Add Skill</button>
    </form>
  `
})
export class SkillsComponent implements OnInit {
  skillsForm!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.skillsForm = this.fb.group({
      skills: this.fb.array([
        this.fb.control('Angular'),
        this.fb.control('TypeScript')
      ])
    });
  }

  get skills(): FormArray {
    return this.skillsForm.get('skills') as FormArray;
  }

  addSkill(): void {
    this.skills.push(this.fb.control(''));
  }

  removeSkill(index: number): void {
    this.skills.removeAt(index);
  }
}
```

---

## Form Validation

### Built-in Validators

```typescript
import { Validators } from '@angular/forms';

this.form = this.fb.group({
  name: ['', Validators.required],
  email: ['', [Validators.required, Validators.email]],
  age: ['', [Validators.required, Validators.min(18), Validators.max(100)]],
  username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
  phone: ['', Validators.pattern(/^\d{10}$/)]
});
```

### Custom Validators

```typescript
// custom-validators.ts
import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

// Simple custom validator
export function noWhitespace(control: AbstractControl): ValidationErrors | null {
  if (control.value && control.value.trim().length === 0) {
    return { noWhitespace: true };
  }
  return null;
}

// Validator factory (with parameters)
export function minAge(minAge: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const age = control.value;
    if (age !== null && age < minAge) {
      return { minAge: { requiredAge: minAge, actualAge: age } };
    }
    return null;
  };
}

// Cross-field validator
export function passwordMatch(control: AbstractControl): ValidationErrors | null {
  const password = control.get('password');
  const confirmPassword = control.get('confirmPassword');

  if (password && confirmPassword && password.value !== confirmPassword.value) {
    return { passwordMismatch: true };
  }
  return null;
}
```

```typescript
// Usage
this.form = this.fb.group({
  username: ['', [Validators.required, noWhitespace]],
  age: ['', [Validators.required, minAge(18)]],
  password: ['', Validators.required],
  confirmPassword: ['', Validators.required]
}, { validators: passwordMatch });
```

### Async Validators

```typescript
import { AsyncValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';
import { Observable, of } from 'rxjs';
import { map, catchError, debounceTime, switchMap } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class UsernameValidator {
  constructor(private userService: UserService) {}

  checkUsername(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      if (!control.value) {
        return of(null);
      }

      return control.valueChanges.pipe(
        debounceTime(300),
        switchMap(value => this.userService.checkUsername(value)),
        map(isAvailable => isAvailable ? null : { usernameTaken: true }),
        catchError(() => of(null))
      );
    };
  }
}
```

```typescript
// Usage
this.form = this.fb.group({
  username: ['',
    [Validators.required],
    [this.usernameValidator.checkUsername()]
  ]
});
```

### Displaying Errors

```typescript
@Component({
  template: `
    <form [formGroup]="form">
      <input formControlName="email">
      <div class="errors" *ngIf="form.get('email')?.invalid && form.get('email')?.touched">
        <span *ngIf="form.get('email')?.errors?.['required']">Required</span>
        <span *ngIf="form.get('email')?.errors?.['email']">Invalid email</span>
      </div>

      <!-- Reusable error component -->
      <app-field-error [control]="form.get('email')"></app-field-error>
    </form>
  `
})
```

```typescript
// field-error.component.ts
@Component({
  selector: 'app-field-error',
  template: `
    <div class="error" *ngIf="control?.invalid && control?.touched">
      <span *ngIf="control?.errors?.['required']">This field is required</span>
      <span *ngIf="control?.errors?.['email']">Invalid email format</span>
      <span *ngIf="control?.errors?.['minlength']">
        Minimum {{ control?.errors?.['minlength'].requiredLength }} characters
      </span>
    </div>
  `
})
export class FieldErrorComponent {
  @Input() control: AbstractControl | null = null;
}
```

---

## Dynamic Forms

Build forms from configuration/API data.

```typescript
interface FormFieldConfig {
  name: string;
  label: string;
  type: 'text' | 'email' | 'number' | 'select' | 'checkbox';
  options?: { value: string; label: string }[];
  validators?: ValidatorFn[];
}

@Component({
  selector: 'app-dynamic-form',
  template: `
    <form [formGroup]="form" (ngSubmit)="onSubmit()">
      <div *ngFor="let field of fields" class="form-group">
        <label [for]="field.name">{{ field.label }}</label>

        <ng-container [ngSwitch]="field.type">
          <input *ngSwitchCase="'text'" [id]="field.name"
                 [formControlName]="field.name" type="text">

          <input *ngSwitchCase="'email'" [id]="field.name"
                 [formControlName]="field.name" type="email">

          <input *ngSwitchCase="'number'" [id]="field.name"
                 [formControlName]="field.name" type="number">

          <select *ngSwitchCase="'select'" [id]="field.name"
                  [formControlName]="field.name">
            <option *ngFor="let opt of field.options" [value]="opt.value">
              {{ opt.label }}
            </option>
          </select>

          <input *ngSwitchCase="'checkbox'" [id]="field.name"
                 [formControlName]="field.name" type="checkbox">
        </ng-container>
      </div>

      <button type="submit" [disabled]="form.invalid">Submit</button>
    </form>
  `
})
export class DynamicFormComponent implements OnInit {
  @Input() fields: FormFieldConfig[] = [];
  form!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.form = this.fb.group({});

    this.fields.forEach(field => {
      this.form.addControl(
        field.name,
        this.fb.control('', field.validators || [])
      );
    });
  }

  onSubmit(): void {
    console.log(this.form.value);
  }
}
```

```typescript
// Usage
@Component({
  template: `
    <app-dynamic-form [fields]="formConfig"></app-dynamic-form>
  `
})
export class AppComponent {
  formConfig: FormFieldConfig[] = [
    { name: 'name', label: 'Name', type: 'text', validators: [Validators.required] },
    { name: 'email', label: 'Email', type: 'email', validators: [Validators.required, Validators.email] },
    {
      name: 'role',
      label: 'Role',
      type: 'select',
      options: [
        { value: 'user', label: 'User' },
        { value: 'admin', label: 'Admin' }
      ]
    }
  ];
}
```

---

## State Management

### What is Application State?

**State** is data that changes over time and affects what users see in your application. Think of it as the "memory" of your application.

```
┌─────────────────────────────────────────────────────────────────┐
│                    APPLICATION STATE                             │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌─────────────┐   ┌─────────────┐   ┌─────────────────────┐    │
│  │ UI State    │   │ User State  │   │ Server/Domain State │    │
│  │─────────────│   │─────────────│   │─────────────────────│    │
│  │ • Is modal  │   │ • Logged in │   │ • Products list     │    │
│  │   open?     │   │   user info │   │ • Orders            │    │
│  │ • Selected  │   │ • User      │   │ • Cart items        │    │
│  │   tab       │   │   preferences│  │ • Comments          │    │
│  │ • Dark/Light│   │ • Auth token│   │ • Search results    │    │
│  │   theme     │   │             │   │                     │    │
│  └─────────────┘   └─────────────┘   └─────────────────────┘    │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
```

### Types of State

| Type | Description | Example | Scope |
|------|-------------|---------|-------|
| **Local/Component State** | State owned by one component | Form input, dropdown open | Single component |
| **Shared State** | State needed by multiple components | Shopping cart, user info | Multiple components |
| **Server State** | Data from backend APIs | Product list, user profile | Entire app |
| **URL State** | Current route and parameters | `/products/42`, `?sort=price` | Browser URL |

### The Problem: Data Sharing Between Components

Without state management, sharing data becomes complex:

```
THE PROBLEM: Passing data through many levels (Props Drilling)
──────────────────────────────────────────────────────────────

       AppComponent (has user data)
            │
            │ [user]="user"
            ▼
       LayoutComponent (doesn't need user, just passes it)
            │
            │ [user]="user"
            ▼
       SidebarComponent (doesn't need user, just passes it)
            │
            │ [user]="user"
            ▼
       ProfileWidget (finally uses user!)

Problem: Every component in between must know about "user"
         even if they don't use it!
```

### State Management Patterns

```
┌───────────────────────────────────────────────────────────────────┐
│              STATE MANAGEMENT PATTERNS                             │
├───────────────────────────────────────────────────────────────────┤
│                                                                    │
│  1. Component State        2. Service + Subject    3. Redux/NgRx  │
│     (for local data)         (for shared data)       (for large   │
│                                                        apps)       │
│  ┌─────────────┐          ┌─────────────────┐     ┌─────────────┐ │
│  │  Component  │          │    Service      │     │    Store    │ │
│  │  ─────────  │          │  ───────────    │     │  ─────────  │ │
│  │  state = {} │          │ BehaviorSubject │     │  Actions    │ │
│  │             │          │       ↓         │     │  Reducers   │ │
│  │ Only this   │          │  Components     │     │  Effects    │ │
│  │ component   │          │  subscribe      │     │  Selectors  │ │
│  │ uses it     │          │                 │     │             │ │
│  └─────────────┘          └─────────────────┘     └─────────────┘ │
│                                                                    │
│  Best for:                Best for:              Best for:         │
│  • Form inputs            • Auth state           • Enterprise apps │
│  • UI toggles             • Shopping cart        • Complex flows   │
│  • Local flags            • User preferences     • Time-travel     │
│                           • Shared data            debugging       │
│                                                                    │
└───────────────────────────────────────────────────────────────────┘
```

For most Angular applications, **Service + BehaviorSubject** is the recommended approach.

---

### Understanding BehaviorSubject (The Foundation)

Before diving into state management, let's understand `BehaviorSubject`:

```
BehaviorSubject vs Regular Variable
───────────────────────────────────

Regular Variable:
  let count = 0;
  count = 5;  // Only the code that runs THIS LINE knows about the change
              // Other parts of the app DON'T know count changed!

BehaviorSubject:
  count$ = new BehaviorSubject<number>(0);
  count$.next(5);  // EVERYONE subscribed gets notified automatically!

         ┌──────────────────┐
         │  BehaviorSubject │
         │    value: 5      │
         └────────┬─────────┘
                  │ broadcasts to all subscribers
        ┌─────────┼─────────┐
        ▼         ▼         ▼
   Component1  Component2  Component3
   "I see 5!"  "I see 5!"  "I see 5!"
```

#### BehaviorSubject Key Characteristics

```typescript
import { BehaviorSubject } from 'rxjs';

// 1. MUST have an initial value (unlike Subject)
const counter$ = new BehaviorSubject<number>(0);  // starts at 0

// 2. New subscribers get the CURRENT value immediately
counter$.subscribe(value => console.log('A:', value));  // prints "A: 0"

// 3. Update the value with .next()
counter$.next(5);  // A: 5

// 4. Late subscriber gets CURRENT value right away
counter$.subscribe(value => console.log('B:', value));  // prints "B: 5" immediately!

// 5. Access current value synchronously (without subscribing)
console.log(counter$.value);  // prints 5
```

#### Why BehaviorSubject for State?

| Feature | Why It Matters for State |
|---------|-------------------------|
| Has initial value | State always starts with something |
| Remembers last value | New components get current state immediately |
| `.value` property | Can read current state synchronously |
| Multiple subscribers | Many components can listen to the same state |

---

### Service-Based Store Pattern

The **Service-Based Store** is Angular's recommended pattern for state management. It combines:
- A **Service** (singleton, injectable)
- A **BehaviorSubject** (holds state, notifies subscribers)
- **Methods** (actions to modify state)
- **Observables** (selectors to read state)

```
┌─────────────────────────────────────────────────────────────────┐
│                   SERVICE-BASED STORE                            │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│    ┌─────────────────────────────────────────────────────┐      │
│    │                  StateService                        │      │
│    │  ─────────────────────────────────────────────────── │      │
│    │                                                      │      │
│    │   private state$ = BehaviorSubject<State>           │      │
│    │        │                                             │      │
│    │        │ holds current state                         │      │
│    │        ▼                                             │      │
│    │   ┌─────────────────────────────┐                   │      │
│    │   │  { user: {...},             │                   │      │
│    │   │    items: [...],            │  ◄── Single       │      │
│    │   │    loading: false }         │      Source of    │      │
│    │   └─────────────────────────────┘      Truth        │      │
│    │                                                      │      │
│    │   SELECTORS (read)         ACTIONS (write)          │      │
│    │   ─────────────────        ────────────────         │      │
│    │   user$ = state$.pipe(     setUser(user) {          │      │
│    │     map(s => s.user)         state$.next({...})     │      │
│    │   )                        }                        │      │
│    │                                                      │      │
│    └─────────────────────────────────────────────────────┘      │
│                          │                                       │
│           ┌──────────────┼──────────────┐                       │
│           ▼              ▼              ▼                       │
│     HeaderComponent  CartComponent  ProfileComponent            │
│     subscribes to    subscribes to  subscribes to               │
│     user$            items$         user$                       │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
```

### Building a Service-Based Store (Step-by-Step)

#### Step 1: Define Your State Interface

```typescript
// models/app-state.model.ts

// What data does your app need to track?
export interface User {
  id: number;
  name: string;
  email: string;
}

export interface CartItem {
  productId: number;
  name: string;
  price: number;
  quantity: number;
}

// The complete state of your app
export interface AppState {
  user: User | null;           // null = not logged in
  cartItems: CartItem[];       // shopping cart
  theme: 'light' | 'dark';     // user preference
  isLoading: boolean;          // show spinner?
}

// Initial state when app starts
export const initialState: AppState = {
  user: null,
  cartItems: [],
  theme: 'light',
  isLoading: false
};
```

#### Step 2: Create the State Service

```typescript
// services/app-state.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { AppState, initialState, User, CartItem } from '../models/app-state.model';

@Injectable({
  providedIn: 'root'  // Singleton - one instance shared everywhere
})
export class AppStateService {

  // ═══════════════════════════════════════════════════════════
  // THE STATE (private - only this service can modify it)
  // ═══════════════════════════════════════════════════════════
  private state = new BehaviorSubject<AppState>(initialState);

  // ═══════════════════════════════════════════════════════════
  // SELECTORS (public observables - components subscribe to these)
  // ═══════════════════════════════════════════════════════════

  // Full state (rarely needed)
  state$ = this.state.asObservable();

  // Select specific pieces of state
  user$ = this.state$.pipe(
    map(state => state.user)
  );

  isLoggedIn$ = this.state$.pipe(
    map(state => state.user !== null)
  );

  cartItems$ = this.state$.pipe(
    map(state => state.cartItems)
  );

  cartTotal$ = this.state$.pipe(
    map(state => state.cartItems.reduce(
      (sum, item) => sum + (item.price * item.quantity), 0
    ))
  );

  cartCount$ = this.state$.pipe(
    map(state => state.cartItems.reduce(
      (sum, item) => sum + item.quantity, 0
    ))
  );

  theme$ = this.state$.pipe(
    map(state => state.theme)
  );

  isLoading$ = this.state$.pipe(
    map(state => state.isLoading)
  );

  // ═══════════════════════════════════════════════════════════
  // ACTIONS (public methods - how components update state)
  // ═══════════════════════════════════════════════════════════

  // User actions
  login(user: User): void {
    this.updateState({ user, isLoading: false });
  }

  logout(): void {
    this.updateState({ user: null, cartItems: [] });
  }

  // Cart actions
  addToCart(item: CartItem): void {
    const currentItems = this.state.value.cartItems;
    const existingIndex = currentItems.findIndex(
      i => i.productId === item.productId
    );

    let newItems: CartItem[];
    if (existingIndex >= 0) {
      // Item exists - update quantity
      newItems = currentItems.map((i, index) =>
        index === existingIndex
          ? { ...i, quantity: i.quantity + item.quantity }
          : i
      );
    } else {
      // New item - add to cart
      newItems = [...currentItems, item];
    }

    this.updateState({ cartItems: newItems });
  }

  removeFromCart(productId: number): void {
    const newItems = this.state.value.cartItems.filter(
      item => item.productId !== productId
    );
    this.updateState({ cartItems: newItems });
  }

  clearCart(): void {
    this.updateState({ cartItems: [] });
  }

  // Theme actions
  toggleTheme(): void {
    const newTheme = this.state.value.theme === 'light' ? 'dark' : 'light';
    this.updateState({ theme: newTheme });
  }

  // Loading actions
  setLoading(isLoading: boolean): void {
    this.updateState({ isLoading });
  }

  // ═══════════════════════════════════════════════════════════
  // HELPER (private - updates state immutably)
  // ═══════════════════════════════════════════════════════════
  private updateState(partialState: Partial<AppState>): void {
    const currentState = this.state.value;
    const newState = { ...currentState, ...partialState };
    this.state.next(newState);
  }
}
```

#### Step 3: Use in Components

```typescript
// components/header.component.ts
@Component({
  selector: 'app-header',
  template: `
    <!-- Show user info or login link -->
    <div class="user-section">
      <ng-container *ngIf="isLoggedIn$ | async; else showLogin">
        <span>Welcome, {{ (user$ | async)?.name }}</span>
        <button (click)="logout()">Logout</button>
      </ng-container>
      <ng-template #showLogin>
        <a routerLink="/login">Login</a>
      </ng-template>
    </div>

    <!-- Cart icon with count -->
    <div class="cart">
      <a routerLink="/cart">
        🛒 Cart ({{ cartCount$ | async }})
      </a>
    </div>

    <!-- Theme toggle -->
    <button (click)="toggleTheme()">
      {{ (theme$ | async) === 'dark' ? '☀️ Light' : '🌙 Dark' }}
    </button>
  `
})
export class HeaderComponent {
  // Subscribe to state slices
  user$ = this.stateService.user$;
  isLoggedIn$ = this.stateService.isLoggedIn$;
  cartCount$ = this.stateService.cartCount$;
  theme$ = this.stateService.theme$;

  constructor(private stateService: AppStateService) {}

  logout(): void {
    this.stateService.logout();
  }

  toggleTheme(): void {
    this.stateService.toggleTheme();
  }
}
```

```typescript
// components/product-card.component.ts
@Component({
  selector: 'app-product-card',
  template: `
    <div class="product">
      <h3>{{ product.name }}</h3>
      <p>{{ product.price | currency }}</p>
      <button (click)="addToCart()">Add to Cart</button>
    </div>
  `
})
export class ProductCardComponent {
  @Input() product!: { id: number; name: string; price: number };

  constructor(private stateService: AppStateService) {}

  addToCart(): void {
    this.stateService.addToCart({
      productId: this.product.id,
      name: this.product.name,
      price: this.product.price,
      quantity: 1
    });
  }
}
```

```typescript
// components/cart.component.ts
@Component({
  selector: 'app-cart',
  template: `
    <h2>Shopping Cart</h2>

    <div *ngIf="(cartItems$ | async)?.length === 0">
      Your cart is empty
    </div>

    <div *ngFor="let item of cartItems$ | async" class="cart-item">
      <span>{{ item.name }} x {{ item.quantity }}</span>
      <span>{{ item.price * item.quantity | currency }}</span>
      <button (click)="remove(item.productId)">Remove</button>
    </div>

    <div class="total">
      <strong>Total: {{ cartTotal$ | async | currency }}</strong>
    </div>

    <button (click)="clearCart()">Clear Cart</button>
  `
})
export class CartComponent {
  cartItems$ = this.stateService.cartItems$;
  cartTotal$ = this.stateService.cartTotal$;

  constructor(private stateService: AppStateService) {}

  remove(productId: number): void {
    this.stateService.removeFromCart(productId);
  }

  clearCart(): void {
    this.stateService.clearCart();
  }
}
```

### How Data Flows

```
USER ACTION → SERVICE METHOD → STATE UPDATE → COMPONENTS RE-RENDER
────────────────────────────────────────────────────────────────────

Example: User clicks "Add to Cart"

1. User clicks button
   │
   ▼
2. Component calls: stateService.addToCart(item)
   │
   ▼
3. Service updates state:
   state.next({ ...currentState, cartItems: [...items, newItem] })
   │
   ▼
4. BehaviorSubject broadcasts new state to ALL subscribers
   │
   ├──► HeaderComponent (cartCount$ updates) → shows "Cart (3)"
   │
   └──► CartComponent (cartItems$ updates) → shows new item in list
```

### Best Practices Summary

| Practice | Why |
|----------|-----|
| Keep state private | Only service should modify state |
| Use selectors (observables) | Components subscribe, don't read directly |
| Immutable updates | `{ ...state, property: newValue }` |
| Small, focused actions | `addToCart()` not `updateState()` |
| Use `async` pipe | Auto-subscribes and unsubscribes |
| One service per domain | `CartStateService`, `UserStateService` |

---

## Exercise: Registration Form with Validation

```typescript
@Component({
  selector: 'app-register',
  template: `
    <h2>Create Account</h2>

    <form [formGroup]="registerForm" (ngSubmit)="onSubmit()">
      <div class="form-group">
        <label>Username</label>
        <input formControlName="username">
        <app-field-error [control]="registerForm.get('username')"></app-field-error>
      </div>

      <div class="form-group">
        <label>Email</label>
        <input formControlName="email" type="email">
        <app-field-error [control]="registerForm.get('email')"></app-field-error>
      </div>

      <div class="form-group">
        <label>Password</label>
        <input formControlName="password" type="password">
        <div class="password-strength">
          Strength: {{ getPasswordStrength() }}
        </div>
        <app-field-error [control]="registerForm.get('password')"></app-field-error>
      </div>

      <div class="form-group">
        <label>Confirm Password</label>
        <input formControlName="confirmPassword" type="password">
        <div *ngIf="registerForm.errors?.['passwordMismatch']" class="error">
          Passwords do not match
        </div>
      </div>

      <div formGroupName="profile">
        <div class="form-group">
          <label>First Name</label>
          <input formControlName="firstName">
        </div>
        <div class="form-group">
          <label>Last Name</label>
          <input formControlName="lastName">
        </div>
      </div>

      <div class="form-group">
        <label>
          <input formControlName="acceptTerms" type="checkbox">
          I accept the terms and conditions
        </label>
      </div>

      <button type="submit" [disabled]="registerForm.invalid || submitting">
        {{ submitting ? 'Creating...' : 'Create Account' }}
      </button>
    </form>
  `
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  submitting = false;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', Validators.required],
      profile: this.fb.group({
        firstName: ['', Validators.required],
        lastName: ['', Validators.required]
      }),
      acceptTerms: [false, Validators.requiredTrue]
    }, { validators: passwordMatch });
  }

  getPasswordStrength(): string {
    const password = this.registerForm.get('password')?.value || '';
    if (password.length < 6) return 'Weak';
    if (password.length < 10) return 'Medium';
    if (/[A-Z]/.test(password) && /[0-9]/.test(password)) return 'Strong';
    return 'Medium';
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      this.submitting = true;
      console.log('Form data:', this.registerForm.value);
      // API call here
    }
  }
}
```

---

## Summary

| Concept | Template-Driven | Reactive |
|---------|-----------------|----------|
| Setup | FormsModule | ReactiveFormsModule |
| Form Creation | In template | In component |
| Data Binding | [(ngModel)] | formControlName |
| Validation | Attributes | Validators class |
| Testing | Harder | Easier |
| Use Case | Simple forms | Complex forms |

| State Pattern | Description |
|---------------|-------------|
| BehaviorSubject | Holds current value, emits to subscribers |
| Selectors | Derived state via map/pipe |
| Actions | Methods that update state |
| Entity State | Products, users, etc. with loading/error |

## Next Topic

Continue to [Advanced Topics](./08-advanced.md) to learn about lifecycle hooks, change detection, and dynamic components.

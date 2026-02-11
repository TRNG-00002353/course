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

### Service-Based State (BehaviorSubject)

```typescript
// app-state.service.ts
interface AppState {
  user: User | null;
  theme: 'light' | 'dark';
  notifications: Notification[];
}

const initialState: AppState = {
  user: null,
  theme: 'light',
  notifications: []
};

@Injectable({ providedIn: 'root' })
export class AppStateService {
  private state = new BehaviorSubject<AppState>(initialState);

  // Expose state as observable
  state$ = this.state.asObservable();

  // Selectors
  user$ = this.state$.pipe(map(state => state.user));
  theme$ = this.state$.pipe(map(state => state.theme));
  notifications$ = this.state$.pipe(map(state => state.notifications));

  // Get current value
  get currentState(): AppState {
    return this.state.value;
  }

  // Actions
  setUser(user: User | null): void {
    this.updateState({ user });
  }

  setTheme(theme: 'light' | 'dark'): void {
    this.updateState({ theme });
  }

  addNotification(notification: Notification): void {
    const notifications = [...this.currentState.notifications, notification];
    this.updateState({ notifications });
  }

  removeNotification(id: string): void {
    const notifications = this.currentState.notifications.filter(n => n.id !== id);
    this.updateState({ notifications });
  }

  private updateState(partial: Partial<AppState>): void {
    this.state.next({ ...this.currentState, ...partial });
  }
}
```

### Using State in Components

```typescript
@Component({
  selector: 'app-header',
  template: `
    <header [class.dark]="(theme$ | async) === 'dark'">
      <ng-container *ngIf="user$ | async as user; else loginLink">
        <span>Welcome, {{ user.name }}</span>
        <button (click)="logout()">Logout</button>
      </ng-container>
      <ng-template #loginLink>
        <a routerLink="/login">Login</a>
      </ng-template>

      <button (click)="toggleTheme()">
        {{ (theme$ | async) === 'dark' ? 'Light' : 'Dark' }} Mode
      </button>
    </header>
  `
})
export class HeaderComponent {
  user$ = this.stateService.user$;
  theme$ = this.stateService.theme$;

  constructor(private stateService: AppStateService) {}

  toggleTheme(): void {
    const newTheme = this.stateService.currentState.theme === 'dark' ? 'light' : 'dark';
    this.stateService.setTheme(newTheme);
  }

  logout(): void {
    this.stateService.setUser(null);
  }
}
```

### Entity State Pattern

```typescript
// product-state.service.ts
interface ProductState {
  products: Product[];
  selectedId: number | null;
  loading: boolean;
  error: string | null;
}

@Injectable({ providedIn: 'root' })
export class ProductStateService {
  private state = new BehaviorSubject<ProductState>({
    products: [],
    selectedId: null,
    loading: false,
    error: null
  });

  // Selectors
  products$ = this.state.pipe(map(s => s.products));
  loading$ = this.state.pipe(map(s => s.loading));
  error$ = this.state.pipe(map(s => s.error));

  selectedProduct$ = this.state.pipe(
    map(s => s.products.find(p => p.id === s.selectedId) || null)
  );

  constructor(private http: HttpClient) {}

  loadProducts(): void {
    this.patchState({ loading: true, error: null });

    this.http.get<Product[]>('/api/products').pipe(
      tap(products => this.patchState({ products, loading: false })),
      catchError(error => {
        this.patchState({ loading: false, error: error.message });
        return EMPTY;
      })
    ).subscribe();
  }

  selectProduct(id: number): void {
    this.patchState({ selectedId: id });
  }

  addProduct(product: Product): void {
    const products = [...this.state.value.products, product];
    this.patchState({ products });
  }

  updateProduct(updated: Product): void {
    const products = this.state.value.products.map(p =>
      p.id === updated.id ? updated : p
    );
    this.patchState({ products });
  }

  deleteProduct(id: number): void {
    const products = this.state.value.products.filter(p => p.id !== id);
    this.patchState({ products });
  }

  private patchState(partial: Partial<ProductState>): void {
    this.state.next({ ...this.state.value, ...partial });
  }
}
```

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

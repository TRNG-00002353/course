# Routing

## What is Routing?

Routing enables navigation between different views (components) in a Single Page Application without full page reloads.

```
┌─────────────────────────────────────────────────────────┐
│                     ANGULAR ROUTING                     │
├─────────────────────────────────────────────────────────┤
│                                                         │
│   URL: /products                                        │
│         ↓                                               │
│   Router matches route: { path: 'products', ... }       │
│         ↓                                               │
│   Loads: ProductListComponent                           │
│         ↓                                               │
│   Displays in: <router-outlet>                          │
│                                                         │
│   Browser URL changes, but NO full page reload!         │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

## Setting Up Routing

### Create App with Routing

```bash
ng new my-app --routing
```

This creates `app-routing.module.ts`:

```typescript
// app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
```

### Add Router Outlet

```typescript
// app.component.ts
@Component({
  selector: 'app-root',
  template: `
    <app-header></app-header>

    <!-- Routed components render here -->
    <router-outlet></router-outlet>

    <app-footer></app-footer>
  `
})
export class AppComponent { }
```

---

## Defining Routes

### Basic Routes

```typescript
// app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ProductListComponent } from './product-list/product-list.component';
import { AboutComponent } from './about/about.component';
import { NotFoundComponent } from './not-found/not-found.component';

const routes: Routes = [
  // Home route
  { path: '', component: HomeComponent },

  // Feature routes
  { path: 'products', component: ProductListComponent },
  { path: 'about', component: AboutComponent },

  // Wildcard route (404) - must be last!
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
```

### Route with Parameters

```typescript
const routes: Routes = [
  { path: 'products', component: ProductListComponent },
  { path: 'products/:id', component: ProductDetailComponent },
  { path: 'users/:userId/orders/:orderId', component: OrderDetailComponent }
];
```

### Accessing Route Parameters

```typescript
// product-detail.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-product-detail',
  template: `
    <h2>Product Details</h2>
    <p>Product ID: {{ productId }}</p>
  `
})
export class ProductDetailComponent implements OnInit {
  productId: string = '';

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    // Snapshot (one-time read)
    this.productId = this.route.snapshot.paramMap.get('id') || '';

    // Observable (for when params change without component reload)
    this.route.paramMap.subscribe(params => {
      this.productId = params.get('id') || '';
      // Fetch product data here
    });
  }
}
```

### Query Parameters

```typescript
// URL: /products?category=electronics&sort=price

// Reading query params
@Component({...})
export class ProductListComponent implements OnInit {
  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    // Snapshot
    const category = this.route.snapshot.queryParamMap.get('category');

    // Observable
    this.route.queryParamMap.subscribe(params => {
      const category = params.get('category');
      const sort = params.get('sort');
    });
  }
}
```

---

## Navigation

### routerLink Directive

```typescript
@Component({
  selector: 'app-header',
  template: `
    <nav>
      <!-- Basic links -->
      <a routerLink="/">Home</a>
      <a routerLink="/products">Products</a>
      <a routerLink="/about">About</a>

      <!-- With parameters -->
      <a [routerLink]="['/products', product.id]">View Product</a>

      <!-- With query params -->
      <a [routerLink]="['/products']"
         [queryParams]="{ category: 'electronics', sort: 'price' }">
        Electronics
      </a>

      <!-- Preserve query params on navigation -->
      <a [routerLink]="['/products', 'details']"
         queryParamsHandling="preserve">
        Details
      </a>
    </nav>
  `
})
```

### routerLinkActive Directive

```typescript
@Component({
  selector: 'app-nav',
  template: `
    <nav>
      <a routerLink="/"
         routerLinkActive="active"
         [routerLinkActiveOptions]="{ exact: true }">
        Home
      </a>
      <a routerLink="/products"
         routerLinkActive="active">
        Products
      </a>
      <a routerLink="/about"
         routerLinkActive="active">
        About
      </a>
    </nav>
  `,
  styles: [`
    .active {
      font-weight: bold;
      color: #1976d2;
      border-bottom: 2px solid #1976d2;
    }
  `]
})
```

### Programmatic Navigation

```typescript
import { Router } from '@angular/router';

@Component({...})
export class ProductComponent {
  constructor(private router: Router) {}

  goToProducts(): void {
    this.router.navigate(['/products']);
  }

  goToProduct(id: number): void {
    this.router.navigate(['/products', id]);
  }

  goWithQueryParams(): void {
    this.router.navigate(['/products'], {
      queryParams: { category: 'electronics' }
    });
  }

  goRelative(): void {
    // Navigate relative to current route
    this.router.navigate(['details'], { relativeTo: this.route });
  }
}
```

---

## Route Guards

Guards control access to routes. They can allow, deny, or redirect navigation.

### CanActivate - Protect Route Entry

```typescript
// auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(): boolean | UrlTree | Observable<boolean | UrlTree> {
    if (this.authService.isLoggedIn()) {
      return true;
    }

    // Redirect to login
    return this.router.createUrlTree(['/login']);
  }
}
```

```typescript
// Apply guard to route
const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent }
];
```

### Functional Guards (Angular 15+)

```typescript
// auth.guard.ts
import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    return true;
  }

  return router.createUrlTree(['/login'], {
    queryParams: { returnUrl: state.url }
  });
};
```

```typescript
// Usage
const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] }
];
```

### CanDeactivate - Confirm Before Leaving

```typescript
// can-deactivate.guard.ts
import { CanDeactivateFn } from '@angular/router';

export interface CanComponentDeactivate {
  canDeactivate: () => boolean | Observable<boolean>;
}

export const canDeactivateGuard: CanDeactivateFn<CanComponentDeactivate> = (component) => {
  return component.canDeactivate ? component.canDeactivate() : true;
};
```

```typescript
// edit-form.component.ts
@Component({...})
export class EditFormComponent implements CanComponentDeactivate {
  hasUnsavedChanges = false;

  canDeactivate(): boolean {
    if (this.hasUnsavedChanges) {
      return confirm('You have unsaved changes. Leave anyway?');
    }
    return true;
  }
}
```

```typescript
// Route config
{
  path: 'edit/:id',
  component: EditFormComponent,
  canDeactivate: [canDeactivateGuard]
}
```

### Role-Based Guard

```typescript
// role.guard.ts
export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const requiredRoles = route.data['roles'] as string[];
  const userRole = authService.getUserRole();

  if (requiredRoles.includes(userRole)) {
    return true;
  }

  return router.createUrlTree(['/unauthorized']);
};
```

```typescript
// Routes with role data
const routes: Routes = [
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [authGuard, roleGuard],
    data: { roles: ['admin'] }
  },
  {
    path: 'manager',
    component: ManagerComponent,
    canActivate: [authGuard, roleGuard],
    data: { roles: ['admin', 'manager'] }
  }
];
```

---

## Lazy Loading

Load feature modules only when needed, reducing initial bundle size.

### Create Feature Module

```bash
ng generate module features/products --route products --module app.module
```

This creates:
- `products.module.ts`
- `products-routing.module.ts`
- `products.component.ts`

### Lazy Load Configuration

```typescript
// app-routing.module.ts
const routes: Routes = [
  { path: '', component: HomeComponent },

  // Lazy loaded module
  {
    path: 'products',
    loadChildren: () => import('./features/products/products.module')
      .then(m => m.ProductsModule)
  },

  // Another lazy module
  {
    path: 'admin',
    loadChildren: () => import('./features/admin/admin.module')
      .then(m => m.AdminModule),
    canActivate: [authGuard]
  }
];
```

```typescript
// products-routing.module.ts
const routes: Routes = [
  { path: '', component: ProductListComponent },
  { path: ':id', component: ProductDetailComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],  // Note: forChild, not forRoot
  exports: [RouterModule]
})
export class ProductsRoutingModule { }
```

### Preloading Strategies

```typescript
// Preload all lazy modules after initial load
import { PreloadAllModules } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      preloadingStrategy: PreloadAllModules
    })
  ]
})
```

---

## Signals (Angular 16+)

Signals provide a reactive way to handle state in Angular.

### Basic Signals

```typescript
import { Component, signal, computed, effect } from '@angular/core';

@Component({
  selector: 'app-counter',
  template: `
    <h2>Count: {{ count() }}</h2>
    <h3>Double: {{ doubleCount() }}</h3>
    <button (click)="increment()">+</button>
    <button (click)="decrement()">-</button>
    <button (click)="reset()">Reset</button>
  `
})
export class CounterComponent {
  // Writable signal
  count = signal(0);

  // Computed signal (derived state)
  doubleCount = computed(() => this.count() * 2);

  constructor() {
    // Effect (side effect when signal changes)
    effect(() => {
      console.log('Count changed to:', this.count());
    });
  }

  increment(): void {
    this.count.update(c => c + 1);
  }

  decrement(): void {
    this.count.update(c => c - 1);
  }

  reset(): void {
    this.count.set(0);
  }
}
```

### Signals with Objects

```typescript
interface User {
  name: string;
  email: string;
}

@Component({
  selector: 'app-user',
  template: `
    <p>Name: {{ user().name }}</p>
    <p>Email: {{ user().email }}</p>
    <button (click)="updateName('Jane')">Change Name</button>
  `
})
export class UserComponent {
  user = signal<User>({ name: 'John', email: 'john@example.com' });

  updateName(name: string): void {
    this.user.update(u => ({ ...u, name }));
  }
}
```

### Signal Inputs (Angular 17+)

```typescript
import { Component, input, computed } from '@angular/core';

@Component({
  selector: 'app-greeting',
  template: `<h1>Hello, {{ upperName() }}!</h1>`
})
export class GreetingComponent {
  // Signal-based input
  name = input<string>('Guest');

  // Required input
  userId = input.required<number>();

  // Computed from input
  upperName = computed(() => this.name().toUpperCase());
}
```

### Signals in Router

```typescript
import { Component } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { ActivatedRoute } from '@angular/router';
import { map } from 'rxjs';

@Component({
  selector: 'app-product-detail',
  template: `
    <h2>Product ID: {{ productId() }}</h2>
  `
})
export class ProductDetailComponent {
  private route = inject(ActivatedRoute);

  // Convert route param observable to signal
  productId = toSignal(
    this.route.paramMap.pipe(
      map(params => params.get('id'))
    )
  );
}
```

---

## Child Routes

Nested routes for complex layouts.

```typescript
const routes: Routes = [
  {
    path: 'admin',
    component: AdminLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: AdminDashboardComponent },
      { path: 'users', component: UserManagementComponent },
      { path: 'users/:id', component: UserDetailComponent },
      { path: 'settings', component: SettingsComponent }
    ]
  }
];
```

```typescript
// admin-layout.component.ts
@Component({
  selector: 'app-admin-layout',
  template: `
    <div class="admin-layout">
      <aside class="sidebar">
        <nav>
          <a routerLink="dashboard" routerLinkActive="active">Dashboard</a>
          <a routerLink="users" routerLinkActive="active">Users</a>
          <a routerLink="settings" routerLinkActive="active">Settings</a>
        </nav>
      </aside>

      <main class="content">
        <router-outlet></router-outlet>  <!-- Child routes render here -->
      </main>
    </div>
  `
})
export class AdminLayoutComponent { }
```

---

## Exercise: Multi-Page App with Auth

### Create Routes

```typescript
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  {
    path: 'products',
    loadChildren: () => import('./products/products.module').then(m => m.ProductsModule)
  },
  {
    path: 'account',
    canActivate: [authGuard],
    children: [
      { path: 'profile', component: ProfileComponent },
      { path: 'orders', component: OrdersComponent }
    ]
  },
  { path: '**', component: NotFoundComponent }
];
```

### Create Auth Guard

```typescript
export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.isLoggedIn()
    ? true
    : router.createUrlTree(['/login']);
};
```

### Create Navigation

```typescript
@Component({
  selector: 'app-nav',
  template: `
    <nav>
      <a routerLink="/" routerLinkActive="active"
         [routerLinkActiveOptions]="{exact: true}">Home</a>
      <a routerLink="/products" routerLinkActive="active">Products</a>

      <ng-container *ngIf="authService.isLoggedIn(); else loginLink">
        <a routerLink="/account/profile" routerLinkActive="active">Profile</a>
        <button (click)="logout()">Logout</button>
      </ng-container>

      <ng-template #loginLink>
        <a routerLink="/login" routerLinkActive="active">Login</a>
      </ng-template>
    </nav>
  `
})
export class NavComponent {
  constructor(public authService: AuthService, private router: Router) {}

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
```

---

## Summary

| Concept | Purpose |
|---------|---------|
| `RouterModule.forRoot()` | Configure app-level routes |
| `router-outlet` | Where routed components render |
| `routerLink` | Navigate via template |
| `Router.navigate()` | Navigate programmatically |
| Route params (`:id`) | Dynamic route segments |
| Query params | Optional parameters |
| `CanActivate` | Guard route entry |
| `CanDeactivate` | Confirm before leaving |
| Lazy loading | Load modules on demand |
| Signals | Reactive state (Angular 16+) |

## Next Topic

Continue to [RxJS & HTTP](./06-rxjs-http.md) to learn about async operations and API communication.

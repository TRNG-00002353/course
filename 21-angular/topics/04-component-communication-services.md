# Component Communication & Services

## Communication Patterns Overview

```
┌─────────────────────────────────────────────────────────────┐
│              COMPONENT COMMUNICATION                        │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Parent ──[@Input]──────────────► Child                     │
│                                                             │
│  Parent ◄──[@Output/EventEmitter]── Child                   │
│                                                             │
│  Parent ──[@ViewChild]──────────► Child (direct access)     │
│                                                             │
│  Any ◄────────[Service]─────────► Any (shared state)        │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

## @Input - Parent to Child

Pass data from parent component to child component.

### Basic Usage

```typescript
// child.component.ts
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-child',
  template: `
    <div class="child">
      <h3>{{ title }}</h3>
      <p>Count: {{ count }}</p>
    </div>
  `
})
export class ChildComponent {
  @Input() title: string = '';
  @Input() count: number = 0;
}
```

```typescript
// parent.component.ts
@Component({
  selector: 'app-parent',
  template: `
    <h2>Parent Component</h2>

    <!-- Pass static value -->
    <app-child title="Static Title" [count]="10"></app-child>

    <!-- Pass dynamic value -->
    <app-child [title]="parentTitle" [count]="parentCount"></app-child>
  `
})
export class ParentComponent {
  parentTitle = 'Dynamic Title';
  parentCount = 42;
}
```

### Input with Alias

```typescript
@Component({
  selector: 'app-user-card',
  template: `<p>{{ userName }}</p>`
})
export class UserCardComponent {
  @Input('name') userName: string = '';  // 'name' is the external property name
}
```

```html
<!-- Usage with alias -->
<app-user-card [name]="'John Doe'"></app-user-card>
```

### Input with Setter (Transform/Validate)

```typescript
@Component({
  selector: 'app-greeting',
  template: `<h1>Hello, {{ name }}!</h1>`
})
export class GreetingComponent {
  private _name: string = '';

  @Input()
  set name(value: string) {
    // Transform or validate input
    this._name = value?.trim().toUpperCase() || 'GUEST';
  }

  get name(): string {
    return this._name;
  }
}
```

### Required Input (Angular 16+)

```typescript
@Component({
  selector: 'app-product',
  template: `<h3>{{ product.name }}</h3>`
})
export class ProductComponent {
  @Input({ required: true }) product!: Product;
}
```

### Passing Objects

```typescript
// product-card.component.ts
interface Product {
  id: number;
  name: string;
  price: number;
  inStock: boolean;
}

@Component({
  selector: 'app-product-card',
  template: `
    <div class="card">
      <h3>{{ product.name }}</h3>
      <p class="price">\${{ product.price }}</p>
      <span [class.in-stock]="product.inStock">
        {{ product.inStock ? 'In Stock' : 'Out of Stock' }}
      </span>
    </div>
  `
})
export class ProductCardComponent {
  @Input() product!: Product;
}
```

```typescript
// product-list.component.ts
@Component({
  selector: 'app-product-list',
  template: `
    <div class="product-grid">
      <app-product-card
        *ngFor="let product of products"
        [product]="product">
      </app-product-card>
    </div>
  `
})
export class ProductListComponent {
  products: Product[] = [
    { id: 1, name: 'Laptop', price: 999, inStock: true },
    { id: 2, name: 'Mouse', price: 29, inStock: true },
    { id: 3, name: 'Keyboard', price: 79, inStock: false }
  ];
}
```

---

## @Output & EventEmitter - Child to Parent

Send events from child component to parent component.

### Basic Usage

```typescript
// child.component.ts
import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-child',
  template: `
    <button (click)="sendMessage()">Send to Parent</button>
  `
})
export class ChildComponent {
  @Output() messageEvent = new EventEmitter<string>();

  sendMessage(): void {
    this.messageEvent.emit('Hello from child!');
  }
}
```

```typescript
// parent.component.ts
@Component({
  selector: 'app-parent',
  template: `
    <h2>Parent Component</h2>
    <p>Message: {{ message }}</p>

    <app-child (messageEvent)="onMessageReceived($event)"></app-child>
  `
})
export class ParentComponent {
  message = '';

  onMessageReceived(msg: string): void {
    this.message = msg;
  }
}
```

### Emitting Complex Data

```typescript
// product-card.component.ts
interface CartItem {
  product: Product;
  quantity: number;
}

@Component({
  selector: 'app-product-card',
  template: `
    <div class="card">
      <h3>{{ product.name }}</h3>
      <p>\${{ product.price }}</p>

      <div class="quantity">
        <button (click)="decrementQty()">-</button>
        <span>{{ quantity }}</span>
        <button (click)="incrementQty()">+</button>
      </div>

      <button (click)="addToCart()">Add to Cart</button>
    </div>
  `
})
export class ProductCardComponent {
  @Input() product!: Product;
  @Output() addedToCart = new EventEmitter<CartItem>();

  quantity = 1;

  incrementQty(): void {
    this.quantity++;
  }

  decrementQty(): void {
    if (this.quantity > 1) this.quantity--;
  }

  addToCart(): void {
    this.addedToCart.emit({
      product: this.product,
      quantity: this.quantity
    });
  }
}
```

```typescript
// product-list.component.ts
@Component({
  selector: 'app-product-list',
  template: `
    <app-product-card
      *ngFor="let product of products"
      [product]="product"
      (addedToCart)="handleAddToCart($event)">
    </app-product-card>

    <div class="cart">
      <h3>Cart ({{ cartItems.length }} items)</h3>
      <div *ngFor="let item of cartItems">
        {{ item.quantity }}x {{ item.product.name }}
      </div>
    </div>
  `
})
export class ProductListComponent {
  products: Product[] = [...];
  cartItems: CartItem[] = [];

  handleAddToCart(item: CartItem): void {
    this.cartItems.push(item);
    console.log('Added to cart:', item);
  }
}
```

### Output with Alias

```typescript
@Output('itemAdded') addedToCart = new EventEmitter<CartItem>();
```

---

## @ViewChild - Parent Accessing Child

Directly access child component or DOM element from parent.

### Access Child Component

```typescript
// countdown.component.ts
@Component({
  selector: 'app-countdown',
  template: `<p>Time: {{ seconds }}</p>`
})
export class CountdownComponent {
  seconds = 10;

  start(): void {
    const interval = setInterval(() => {
      this.seconds--;
      if (this.seconds === 0) {
        clearInterval(interval);
      }
    }, 1000);
  }

  reset(): void {
    this.seconds = 10;
  }
}
```

```typescript
// parent.component.ts
import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { CountdownComponent } from './countdown.component';

@Component({
  selector: 'app-parent',
  template: `
    <app-countdown></app-countdown>

    <button (click)="startCountdown()">Start</button>
    <button (click)="resetCountdown()">Reset</button>
  `
})
export class ParentComponent implements AfterViewInit {
  @ViewChild(CountdownComponent) countdown!: CountdownComponent;

  ngAfterViewInit(): void {
    // Child is available after view init
    console.log('Initial seconds:', this.countdown.seconds);
  }

  startCountdown(): void {
    this.countdown.start();
  }

  resetCountdown(): void {
    this.countdown.reset();
  }
}
```

### Access DOM Element

```typescript
@Component({
  selector: 'app-form',
  template: `
    <input #nameInput type="text">
    <button (click)="focusInput()">Focus</button>
  `
})
export class FormComponent implements AfterViewInit {
  @ViewChild('nameInput') nameInput!: ElementRef<HTMLInputElement>;

  ngAfterViewInit(): void {
    // DOM element available after view init
    this.nameInput.nativeElement.focus();
  }

  focusInput(): void {
    this.nameInput.nativeElement.focus();
  }
}
```

### ViewChild Options

```typescript
// Get first match
@ViewChild(ChildComponent) child!: ChildComponent;

// Get by template reference
@ViewChild('myRef') element!: ElementRef;

// Static: true (available in ngOnInit, before change detection)
@ViewChild('input', { static: true }) input!: ElementRef;

// Static: false (default, available in ngAfterViewInit)
@ViewChild('input', { static: false }) input!: ElementRef;
```

---

## Dependency Injection (DI)

Angular's DI system provides services to components that need them.

```
┌─────────────────────────────────────────────────────────┐
│                 DEPENDENCY INJECTION                    │
├─────────────────────────────────────────────────────────┤
│                                                         │
│   Component                    Injector                 │
│   ┌─────────┐                 ┌─────────┐              │
│   │ needs   │ ──requests──►   │ Service │              │
│   │ Service │ ◄──provides──   │ Instance│              │
│   └─────────┘                 └─────────┘              │
│                                                         │
│   The component declares what it needs,                 │
│   Angular provides (injects) it automatically.          │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Benefits of DI
- **Loose coupling** - Components don't create their dependencies
- **Testability** - Easy to mock services in tests
- **Reusability** - Same service instance shared across components
- **Maintainability** - Change implementation without changing consumers

---

## Services

Services are classes that handle business logic, data access, and shared state.

### Creating a Service

```bash
ng generate service services/product
# or
ng g s services/product
```

```typescript
// product.service.ts
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'  // Available application-wide
})
export class ProductService {
  private products: Product[] = [
    { id: 1, name: 'Laptop', price: 999 },
    { id: 2, name: 'Mouse', price: 29 },
    { id: 3, name: 'Keyboard', price: 79 }
  ];

  getProducts(): Product[] {
    return this.products;
  }

  getProduct(id: number): Product | undefined {
    return this.products.find(p => p.id === id);
  }

  addProduct(product: Product): void {
    this.products.push(product);
  }
}
```

### Injecting a Service

```typescript
// product-list.component.ts
import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-product-list',
  template: `
    <div *ngFor="let product of products">
      {{ product.name }} - \${{ product.price }}
    </div>
  `
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  // Inject service via constructor
  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.products = this.productService.getProducts();
  }
}
```

---

## @Injectable and Providers

### providedIn Options

```typescript
// Application-wide singleton (recommended)
@Injectable({
  providedIn: 'root'
})
export class GlobalService { }

// Provided in a specific module
@Injectable({
  providedIn: SomeModule
})
export class ModuleService { }

// No automatic providing (must add to providers array)
@Injectable()
export class ManualService { }
```

### Provider in Module

```typescript
// app.module.ts
@NgModule({
  providers: [
    ManualService,  // Simple provider

    // Full provider syntax
    { provide: LoggerService, useClass: LoggerService },

    // Use different implementation
    { provide: LoggerService, useClass: ConsoleLoggerService },

    // Use factory
    {
      provide: ConfigService,
      useFactory: () => new ConfigService(environment.production)
    },

    // Use value
    { provide: API_URL, useValue: 'https://api.example.com' }
  ]
})
export class AppModule { }
```

### Injection Tokens

```typescript
import { InjectionToken } from '@angular/core';

// Create token
export const API_URL = new InjectionToken<string>('API_URL');

// Provide
@NgModule({
  providers: [
    { provide: API_URL, useValue: 'https://api.example.com' }
  ]
})

// Inject
@Injectable({ providedIn: 'root' })
export class ApiService {
  constructor(@Inject(API_URL) private apiUrl: string) {
    console.log('API URL:', this.apiUrl);
  }
}
```

---

## Injector Hierarchy

Angular has a hierarchical injection system.

```
┌─────────────────────────────────────────────────────────┐
│                  INJECTOR HIERARCHY                     │
├─────────────────────────────────────────────────────────┤
│                                                         │
│   Root Injector (Application-wide)                      │
│         │                                               │
│         ├── Module Injector                             │
│         │         │                                     │
│         │         ├── Component Injector                │
│         │         │         │                           │
│         │         │         └── Child Component         │
│         │         │                                     │
│         │         └── Component Injector                │
│         │                                               │
│         └── Lazy Module Injector                        │
│                                                         │
│   * Services bubble UP the tree until found             │
│   * Each level can override parent providers            │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Component-Level Provider

```typescript
@Component({
  selector: 'app-user-list',
  template: '...',
  providers: [UserService]  // New instance for this component tree
})
export class UserListComponent {
  constructor(private userService: UserService) {}
}
```

---

## Service Communication Between Components

Services enable communication between unrelated components.

### Shared State Service

```typescript
// cart.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

interface CartItem {
  product: Product;
  quantity: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private items: CartItem[] = [];
  private itemsSubject = new BehaviorSubject<CartItem[]>([]);

  // Observable for components to subscribe
  items$: Observable<CartItem[]> = this.itemsSubject.asObservable();

  get itemCount(): number {
    return this.items.reduce((total, item) => total + item.quantity, 0);
  }

  get total(): number {
    return this.items.reduce(
      (sum, item) => sum + (item.product.price * item.quantity), 0
    );
  }

  addItem(product: Product, quantity: number = 1): void {
    const existing = this.items.find(i => i.product.id === product.id);
    if (existing) {
      existing.quantity += quantity;
    } else {
      this.items.push({ product, quantity });
    }
    this.itemsSubject.next([...this.items]);
  }

  removeItem(productId: number): void {
    this.items = this.items.filter(i => i.product.id !== productId);
    this.itemsSubject.next([...this.items]);
  }

  clearCart(): void {
    this.items = [];
    this.itemsSubject.next([]);
  }
}
```

### Components Using Shared Service

```typescript
// product-card.component.ts (adds to cart)
@Component({
  selector: 'app-product-card',
  template: `
    <div class="card">
      <h3>{{ product.name }}</h3>
      <p>\${{ product.price }}</p>
      <button (click)="addToCart()">Add to Cart</button>
    </div>
  `
})
export class ProductCardComponent {
  @Input() product!: Product;

  constructor(private cartService: CartService) {}

  addToCart(): void {
    this.cartService.addItem(this.product);
  }
}
```

```typescript
// cart-icon.component.ts (displays count in header)
@Component({
  selector: 'app-cart-icon',
  template: `
    <div class="cart-icon">
      <span>Cart ({{ itemCount }})</span>
    </div>
  `
})
export class CartIconComponent implements OnInit, OnDestroy {
  itemCount = 0;
  private subscription!: Subscription;

  constructor(private cartService: CartService) {}

  ngOnInit(): void {
    this.subscription = this.cartService.items$.subscribe(items => {
      this.itemCount = items.reduce((sum, i) => sum + i.quantity, 0);
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }
}
```

```typescript
// cart-page.component.ts (full cart view)
@Component({
  selector: 'app-cart-page',
  template: `
    <h2>Shopping Cart</h2>

    <div *ngIf="items$ | async as items">
      <div *ngFor="let item of items" class="cart-item">
        <span>{{ item.product.name }}</span>
        <span>{{ item.quantity }} x \${{ item.product.price }}</span>
        <button (click)="remove(item.product.id)">Remove</button>
      </div>

      <div class="total">
        <strong>Total: \${{ cartService.total }}</strong>
      </div>

      <button (click)="cartService.clearCart()">Clear Cart</button>
    </div>
  `
})
export class CartPageComponent {
  items$ = this.cartService.items$;

  constructor(public cartService: CartService) {}

  remove(productId: number): void {
    this.cartService.removeItem(productId);
  }
}
```

---

## Exercise: Build a Product Store

Create a mini e-commerce app with:
- ProductService (data)
- CartService (shared state)
- Product list with add to cart
- Cart summary in header
- Cart page with items

### Project Structure

```
src/app/
├── services/
│   ├── product.service.ts
│   └── cart.service.ts
├── components/
│   ├── header/
│   ├── product-list/
│   ├── product-card/
│   └── cart/
└── models/
    └── product.model.ts
```

### Implementation Steps

1. Create Product model and service
2. Create Cart service with BehaviorSubject
3. Build ProductCard with @Input and addToCart
4. Build ProductList using ProductService
5. Build Header with CartIcon
6. Build Cart page subscribing to cart items

---

## Summary

| Pattern | Use Case | Mechanism |
|---------|----------|-----------|
| `@Input` | Parent → Child data | Property binding |
| `@Output` | Child → Parent events | EventEmitter |
| `@ViewChild` | Parent accesses child | Direct reference |
| Service | Shared state/logic | DI + BehaviorSubject |
| `providedIn: 'root'` | App-wide singleton | Root injector |

## Next Topic

Continue to [Routing](./05-routing.md) to learn about navigation and multiple views in Angular.

# Components Basics

## What is a Component?

A component is the fundamental building block of Angular applications. It controls a portion of the screen (a **view**) and consists of:

1. **TypeScript Class** - Logic and data
2. **HTML Template** - View structure
3. **CSS Styles** - Appearance
4. **Metadata** - Configuration via `@Component`

```
┌─────────────────────────────────────┐
│           Component                 │
├─────────────────────────────────────┤
│  @Component({                       │
│    selector: 'app-product',         │  ← Metadata
│    templateUrl: '...',              │
│    styleUrls: ['...']               │
│  })                                 │
├─────────────────────────────────────┤
│  export class ProductComponent {    │
│    name = 'Laptop';                 │  ← Class (Logic)
│    price = 999;                     │
│  }                                  │
├─────────────────────────────────────┤
│  <div class="product">              │
│    <h2>{{ name }}</h2>              │  ← Template (View)
│    <p>${{ price }}</p>              │
│  </div>                             │
├─────────────────────────────────────┤
│  .product { border: 1px solid; }    │  ← Styles
└─────────────────────────────────────┘
```

---

## @Component Decorator

The `@Component` decorator marks a class as an Angular component and provides configuration.

### Basic Structure

```typescript
import { Component } from '@angular/core';

@Component({
  selector: 'app-greeting',           // HTML tag name
  templateUrl: './greeting.component.html',  // External template
  styleUrls: ['./greeting.component.css']    // External styles
})
export class GreetingComponent {
  message = 'Hello, Angular!';
}
```

### Inline vs External Templates

```typescript
// Inline template (for simple components)
@Component({
  selector: 'app-simple',
  template: `
    <h1>{{ title }}</h1>
    <p>{{ description }}</p>
  `,
  styles: [`
    h1 { color: blue; }
  `]
})
export class SimpleComponent {
  title = 'Simple';
  description = 'This is inline';
}

// External template (recommended for complex components)
@Component({
  selector: 'app-complex',
  templateUrl: './complex.component.html',
  styleUrls: ['./complex.component.css']
})
export class ComplexComponent { }
```

### Selector Types

```typescript
// Element selector (most common)
@Component({ selector: 'app-header' })
// Usage: <app-header></app-header>

// Attribute selector
@Component({ selector: '[appHighlight]' })
// Usage: <div appHighlight></div>

// Class selector (rare)
@Component({ selector: '.app-widget' })
// Usage: <div class="app-widget"></div>
```

---

## Data Binding Overview

Angular provides four types of data binding:

```
┌─────────────────────────────────────────────────────────┐
│                    DATA BINDING                         │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  Component ──────────────────────────► Template         │
│             Interpolation: {{ value }}                  │
│             Property: [property]="value"                │
│                                                         │
│  Component ◄────────────────────────── Template         │
│             Event: (event)="handler()"                  │
│                                                         │
│  Component ◄────────────────────────► Template          │
│             Two-way: [(ngModel)]="value"                │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

## Interpolation `{{ }}`

Display component data in the template.

```typescript
@Component({
  selector: 'app-user',
  template: `
    <h1>{{ title }}</h1>
    <p>Name: {{ user.name }}</p>
    <p>Email: {{ user.email }}</p>
    <p>Age in 5 years: {{ user.age + 5 }}</p>
    <p>Status: {{ isActive ? 'Active' : 'Inactive' }}</p>
    <p>Full Name: {{ getFullName() }}</p>
  `
})
export class UserComponent {
  title = 'User Profile';
  user = {
    name: 'John',
    email: 'john@example.com',
    age: 25
  };
  isActive = true;

  getFullName(): string {
    return `${this.user.name} Doe`;
  }
}
```

---

## Property Binding `[property]`

Bind component data to element properties.

```typescript
@Component({
  selector: 'app-product',
  template: `
    <!-- Bind to element properties -->
    <img [src]="imageUrl" [alt]="productName">

    <!-- Bind to disabled state -->
    <button [disabled]="isOutOfStock">Buy Now</button>

    <!-- Bind to CSS class -->
    <div [class.highlight]="isOnSale">Product</div>

    <!-- Bind to inline style -->
    <p [style.color]="priceColor">{{ price }}</p>
    <p [style.font-size.px]="fontSize">Text</p>

    <!-- Bind to attribute (for non-property attributes) -->
    <td [attr.colspan]="columnSpan">Cell</td>
  `
})
export class ProductComponent {
  imageUrl = 'assets/laptop.jpg';
  productName = 'Laptop';
  isOutOfStock = false;
  isOnSale = true;
  price = '$999';
  priceColor = 'green';
  fontSize = 16;
  columnSpan = 2;
}
```

### Property vs Interpolation

```html
<!-- These are equivalent -->
<img src="{{ imageUrl }}">
<img [src]="imageUrl">

<!-- But for boolean/complex values, use property binding -->
<button disabled="{{ isDisabled }}">  <!-- Won't work as expected! -->
<button [disabled]="isDisabled">      <!-- Correct -->
```

---

## Event Binding `(event)`

Respond to user actions.

```typescript
@Component({
  selector: 'app-counter',
  template: `
    <h2>Count: {{ count }}</h2>

    <!-- Basic click event -->
    <button (click)="increment()">+</button>
    <button (click)="decrement()">-</button>

    <!-- Pass event object -->
    <input (input)="onInput($event)">

    <!-- Keyboard events -->
    <input (keyup.enter)="onSubmit()">
    <input (keydown.escape)="onCancel()">

    <!-- Mouse events -->
    <div (mouseenter)="onHover(true)"
         (mouseleave)="onHover(false)">
      Hover me
    </div>

    <!-- Inline statement -->
    <button (click)="count = 0">Reset</button>
  `
})
export class CounterComponent {
  count = 0;

  increment(): void {
    this.count++;
  }

  decrement(): void {
    this.count--;
  }

  onInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    console.log('Input value:', input.value);
  }

  onSubmit(): void {
    console.log('Enter pressed');
  }

  onCancel(): void {
    console.log('Escape pressed');
  }

  onHover(isHovering: boolean): void {
    console.log('Hovering:', isHovering);
  }
}
```

### Common Events

| Event | Description |
|-------|-------------|
| `(click)` | Mouse click |
| `(dblclick)` | Double click |
| `(input)` | Input value change |
| `(change)` | Input change (on blur) |
| `(focus)` | Element focused |
| `(blur)` | Element lost focus |
| `(keyup)` | Key released |
| `(keydown)` | Key pressed |
| `(submit)` | Form submitted |
| `(mouseenter)` | Mouse enters element |
| `(mouseleave)` | Mouse leaves element |

---

## Template Reference Variables `#ref`

Create a reference to a DOM element or component.

```typescript
@Component({
  selector: 'app-form',
  template: `
    <!-- Reference to input element -->
    <input #nameInput type="text" placeholder="Enter name">
    <button (click)="greet(nameInput.value)">Greet</button>

    <!-- Reference for focus -->
    <input #emailInput type="email">
    <button (click)="emailInput.focus()">Focus Email</button>

    <!-- Pass to method -->
    <input #searchBox (keyup.enter)="search(searchBox.value)">

    <!-- Display value reactively -->
    <input #liveInput (input)="0">
    <p>You typed: {{ liveInput.value }}</p>
  `
})
export class FormComponent {
  greet(name: string): void {
    alert(`Hello, ${name}!`);
  }

  search(query: string): void {
    console.log('Searching for:', query);
  }
}
```

---

## Two-Way Binding `[(ngModel)]`

Sync data between component and input element.

### Setup Required

```typescript
// app.module.ts
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule  // Required for ngModel
  ]
})
export class AppModule { }
```

### Usage

```typescript
@Component({
  selector: 'app-profile',
  template: `
    <!-- Two-way binding -->
    <input [(ngModel)]="username" placeholder="Username">
    <p>Hello, {{ username }}!</p>

    <!-- Checkbox -->
    <input type="checkbox" [(ngModel)]="isSubscribed">
    <span>Subscribe to newsletter: {{ isSubscribed }}</span>

    <!-- Select -->
    <select [(ngModel)]="selectedColor">
      <option value="red">Red</option>
      <option value="blue">Blue</option>
      <option value="green">Green</option>
    </select>
    <p>Selected: {{ selectedColor }}</p>

    <!-- Textarea -->
    <textarea [(ngModel)]="bio"></textarea>
    <p>Bio: {{ bio }}</p>
  `
})
export class ProfileComponent {
  username = '';
  isSubscribed = false;
  selectedColor = 'blue';
  bio = '';
}
```

### How Two-Way Binding Works

```typescript
// [(ngModel)]="username" is shorthand for:
<input [ngModel]="username" (ngModelChange)="username = $event">

// It combines:
// [ngModel]="username"     → Property binding (component → view)
// (ngModelChange)="..."    → Event binding (view → component)
```

---

## Component Styles

### Styling Methods

```typescript
// 1. External stylesheet (recommended)
@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})

// 2. Inline styles
@Component({
  selector: 'app-alert',
  template: '<div class="alert">{{ message }}</div>',
  styles: [`
    .alert {
      padding: 15px;
      background: #f8d7da;
      border: 1px solid #f5c6cb;
      border-radius: 4px;
    }
  `]
})
```

### Style Encapsulation

By default, component styles are **scoped** to that component only.

```typescript
// card.component.css
h1 { color: blue; }  // Only affects <h1> inside CardComponent

// This won't affect <h1> in other components!
```

```typescript
import { Component, ViewEncapsulation } from '@angular/core';

// Emulated (default) - Styles scoped to component
@Component({
  encapsulation: ViewEncapsulation.Emulated
})

// None - Styles are global
@Component({
  encapsulation: ViewEncapsulation.None
})

// ShadowDom - Uses native Shadow DOM
@Component({
  encapsulation: ViewEncapsulation.ShadowDom
})
```

### Special Selectors

```css
/* :host - Style the component's host element */
:host {
  display: block;
  border: 1px solid #ccc;
  padding: 10px;
}

/* :host with condition */
:host(.active) {
  border-color: blue;
}

/* :host-context - Style based on ancestor */
:host-context(.dark-theme) {
  background: #333;
  color: white;
}
```

---

## Nesting Components

Components can contain other components.

```typescript
// header.component.ts
@Component({
  selector: 'app-header',
  template: `
    <header>
      <h1>{{ title }}</h1>
      <nav>
        <a href="#">Home</a>
        <a href="#">Products</a>
      </nav>
    </header>
  `
})
export class HeaderComponent {
  title = 'My Store';
}

// footer.component.ts
@Component({
  selector: 'app-footer',
  template: `<footer>&copy; {{ year }} My Store</footer>`
})
export class FooterComponent {
  year = new Date().getFullYear();
}

// app.component.ts
@Component({
  selector: 'app-root',
  template: `
    <app-header></app-header>
    <main>
      <h2>Welcome!</h2>
      <p>Main content goes here.</p>
    </main>
    <app-footer></app-footer>
  `
})
export class AppComponent { }
```

```
Rendered Structure:
┌────────────────────────────┐
│ <app-root>                 │
│   ┌────────────────────┐   │
│   │ <app-header>       │   │
│   │   My Store | Nav   │   │
│   └────────────────────┘   │
│   ┌────────────────────┐   │
│   │ <main>             │   │
│   │   Welcome!         │   │
│   └────────────────────┘   │
│   ┌────────────────────┐   │
│   │ <app-footer>       │   │
│   │   © 2024 My Store  │   │
│   └────────────────────┘   │
└────────────────────────────┘
```

---

## Exercise: Build a Product Card

### Task 1: Create Product Card Component

```bash
ng g c components/product-card
```

### Task 2: Implement the Component

```typescript
// product-card.component.ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-product-card',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.css']
})
export class ProductCardComponent {
  product = {
    name: 'Wireless Headphones',
    price: 79.99,
    image: 'https://via.placeholder.com/200',
    inStock: true,
    rating: 4.5
  };

  quantity = 1;

  addToCart(): void {
    console.log(`Added ${this.quantity} ${this.product.name}(s) to cart`);
  }

  incrementQty(): void {
    this.quantity++;
  }

  decrementQty(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }
}
```

```html
<!-- product-card.component.html -->
<div class="card">
  <img [src]="product.image" [alt]="product.name">
  <h3>{{ product.name }}</h3>
  <p class="price">${{ product.price }}</p>
  <p [class.in-stock]="product.inStock"
     [class.out-of-stock]="!product.inStock">
    {{ product.inStock ? 'In Stock' : 'Out of Stock' }}
  </p>

  <div class="quantity">
    <button (click)="decrementQty()">-</button>
    <span>{{ quantity }}</span>
    <button (click)="incrementQty()">+</button>
  </div>

  <button (click)="addToCart()" [disabled]="!product.inStock">
    Add to Cart
  </button>
</div>
```

```css
/* product-card.component.css */
.card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  width: 250px;
  text-align: center;
}

.card img {
  max-width: 100%;
  border-radius: 4px;
}

.price {
  font-size: 1.5rem;
  font-weight: bold;
  color: #1976d2;
}

.in-stock { color: green; }
.out-of-stock { color: red; }

.quantity {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin: 10px 0;
}

.quantity button {
  width: 30px;
  height: 30px;
}

button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
```

### Task 3: Use in App Component

```typescript
// app.component.ts
@Component({
  selector: 'app-root',
  template: `
    <h1>My Store</h1>
    <app-product-card></app-product-card>
  `
})
export class AppComponent { }
```

---

## Summary

| Concept | Syntax | Direction |
|---------|--------|-----------|
| Interpolation | `{{ value }}` | Component → View |
| Property Binding | `[property]="value"` | Component → View |
| Event Binding | `(event)="handler()"` | View → Component |
| Two-Way Binding | `[(ngModel)]="value"` | Both ways |
| Template Ref | `#refName` | Access DOM element |

## Next Topic

Continue to [Directives and Pipes](./03-directives-pipes.md) to learn about controlling DOM structure and transforming data.

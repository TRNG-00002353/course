# Directives and Pipes

## What are Directives?

Directives are classes that add behavior to elements in Angular. There are three types:

```
┌─────────────────────────────────────────────────────────┐
│                    DIRECTIVES                           │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  1. Components        → Directives WITH a template      │
│     <app-header>                                        │
│                                                         │
│  2. Structural        → Change DOM structure            │
│     *ngIf, *ngFor     (add/remove elements)             │
│                                                         │
│  3. Attribute         → Change appearance/behavior      │
│     ngClass, ngStyle  (modify existing elements)        │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

---

## Structural Directives

Structural directives modify the DOM layout by adding or removing elements. They are prefixed with an asterisk `*`.

### *ngIf - Conditional Rendering

```typescript
@Component({
  selector: 'app-login-status',
  template: `
    <!-- Basic *ngIf -->
    <div *ngIf="isLoggedIn">
      Welcome back, {{ username }}!
    </div>

    <!-- *ngIf with else -->
    <div *ngIf="isLoggedIn; else loginPrompt">
      Welcome back, {{ username }}!
    </div>
    <ng-template #loginPrompt>
      <p>Please log in to continue.</p>
    </ng-template>

    <!-- *ngIf with then and else -->
    <ng-container *ngIf="isLoading; then loadingTpl else contentTpl">
    </ng-container>
    <ng-template #loadingTpl>
      <p>Loading...</p>
    </ng-template>
    <ng-template #contentTpl>
      <p>Content loaded!</p>
    </ng-template>

    <!-- *ngIf with as (store result in variable) -->
    <div *ngIf="user$ | async as user">
      <p>Name: {{ user.name }}</p>
      <p>Email: {{ user.email }}</p>
    </div>
  `
})
export class LoginStatusComponent {
  isLoggedIn = true;
  isLoading = false;
  username = 'John';
}
```

### *ngFor - List Rendering

```typescript
@Component({
  selector: 'app-product-list',
  template: `
    <!-- Basic *ngFor -->
    <ul>
      <li *ngFor="let product of products">
        {{ product.name }} - ${{ product.price }}
      </li>
    </ul>

    <!-- With index -->
    <ul>
      <li *ngFor="let product of products; let i = index">
        {{ i + 1 }}. {{ product.name }}
      </li>
    </ul>

    <!-- With all available variables -->
    <div *ngFor="let item of items;
                 let i = index;
                 let first = first;
                 let last = last;
                 let even = even;
                 let odd = odd">
      <span [class.first-item]="first"
            [class.last-item]="last"
            [class.even-row]="even"
            [class.odd-row]="odd">
        {{ i }}: {{ item }}
      </span>
    </div>

    <!-- With trackBy (performance optimization) -->
    <div *ngFor="let product of products; trackBy: trackById">
      {{ product.name }}
    </div>
  `
})
export class ProductListComponent {
  products = [
    { id: 1, name: 'Laptop', price: 999 },
    { id: 2, name: 'Mouse', price: 29 },
    { id: 3, name: 'Keyboard', price: 79 }
  ];

  items = ['A', 'B', 'C', 'D'];

  // trackBy function for better performance
  trackById(index: number, product: any): number {
    return product.id;
  }
}
```

### *ngFor Variables

| Variable | Type | Description |
|----------|------|-------------|
| `index` | number | Index of current item (0-based) |
| `first` | boolean | True if first item |
| `last` | boolean | True if last item |
| `even` | boolean | True if even index |
| `odd` | boolean | True if odd index |
| `count` | number | Total number of items |

### *ngSwitch - Multiple Conditions

```typescript
@Component({
  selector: 'app-status-badge',
  template: `
    <div [ngSwitch]="status">
      <span *ngSwitchCase="'pending'" class="badge warning">
        Pending
      </span>
      <span *ngSwitchCase="'approved'" class="badge success">
        Approved
      </span>
      <span *ngSwitchCase="'rejected'" class="badge danger">
        Rejected
      </span>
      <span *ngSwitchDefault class="badge">
        Unknown
      </span>
    </div>

    <!-- With components -->
    <div [ngSwitch]="userRole">
      <app-admin-panel *ngSwitchCase="'admin'"></app-admin-panel>
      <app-user-dashboard *ngSwitchCase="'user'"></app-user-dashboard>
      <app-guest-view *ngSwitchDefault></app-guest-view>
    </div>
  `
})
export class StatusBadgeComponent {
  status = 'pending';
  userRole = 'user';
}
```

---

## ng-container and ng-template

### ng-container

A grouping element that doesn't render in the DOM.

```typescript
@Component({
  template: `
    <!-- Problem: Can't use two structural directives on same element -->
    <!-- <div *ngIf="show" *ngFor="let item of items"> INVALID! -->

    <!-- Solution: Use ng-container -->
    <ng-container *ngIf="showList">
      <div *ngFor="let item of items">
        {{ item }}
      </div>
    </ng-container>

    <!-- No extra wrapper in DOM -->
    <ul>
      <ng-container *ngFor="let item of items">
        <li>{{ item.name }}</li>
        <li class="details">{{ item.description }}</li>
      </ng-container>
    </ul>
  `
})
```

### ng-template

Define a template that isn't rendered until used.

```typescript
@Component({
  template: `
    <!-- Define templates -->
    <ng-template #loading>
      <div class="spinner">Loading...</div>
    </ng-template>

    <ng-template #error>
      <div class="error">Something went wrong!</div>
    </ng-template>

    <!-- Use with *ngIf -->
    <div *ngIf="data; else loading">
      {{ data }}
    </div>

    <!-- Use with ngTemplateOutlet -->
    <ng-container *ngTemplateOutlet="loading"></ng-container>

    <!-- Template with context -->
    <ng-template #itemTemplate let-item let-idx="index">
      <div>{{ idx }}: {{ item.name }}</div>
    </ng-template>

    <ng-container *ngFor="let product of products; let i = index">
      <ng-container *ngTemplateOutlet="itemTemplate;
                    context: { $implicit: product, index: i }">
      </ng-container>
    </ng-container>
  `
})
```

---

## Attribute Directives

Attribute directives change the appearance or behavior of an element.

### ngClass - Dynamic CSS Classes

```typescript
@Component({
  selector: 'app-class-demo',
  template: `
    <!-- Single class toggle -->
    <div [class.active]="isActive">Toggle class</div>
    <div [class.error]="hasError">Error state</div>

    <!-- Multiple classes with object -->
    <div [ngClass]="{
      'active': isActive,
      'disabled': isDisabled,
      'highlighted': isHighlighted
    }">
      Multiple classes
    </div>

    <!-- Classes from array -->
    <div [ngClass]="['class-a', 'class-b']">Array of classes</div>

    <!-- Classes from method -->
    <div [ngClass]="getClasses()">From method</div>

    <!-- Combined static and dynamic -->
    <div class="base-class" [ngClass]="{ 'special': isSpecial }">
      Combined
    </div>
  `,
  styles: [`
    .active { background: lightgreen; }
    .disabled { opacity: 0.5; }
    .highlighted { border: 2px solid yellow; }
    .error { color: red; }
  `]
})
export class ClassDemoComponent {
  isActive = true;
  isDisabled = false;
  isHighlighted = true;
  isSpecial = true;
  hasError = false;

  getClasses() {
    return {
      'active': this.isActive,
      'error': this.hasError
    };
  }
}
```

### ngStyle - Dynamic Inline Styles

```typescript
@Component({
  selector: 'app-style-demo',
  template: `
    <!-- Single style -->
    <div [style.color]="textColor">Colored text</div>
    <div [style.background-color]="bgColor">Background</div>

    <!-- Style with unit -->
    <div [style.font-size.px]="fontSize">Font size in px</div>
    <div [style.width.%]="widthPercent">Width in percent</div>

    <!-- Multiple styles with object -->
    <div [ngStyle]="{
      'color': textColor,
      'font-size': fontSize + 'px',
      'font-weight': isBold ? 'bold' : 'normal',
      'background-color': bgColor
    }">
      Multiple styles
    </div>

    <!-- Styles from method -->
    <div [ngStyle]="getStyles()">From method</div>
  `
})
export class StyleDemoComponent {
  textColor = 'blue';
  bgColor = '#f0f0f0';
  fontSize = 18;
  widthPercent = 50;
  isBold = true;

  getStyles() {
    return {
      'color': this.textColor,
      'padding': '10px',
      'border-radius': '5px'
    };
  }
}
```

---

## Pipes

Pipes transform data for display in templates. They don't change the original data.

```
{{ value | pipeName }}
{{ value | pipeName:arg1:arg2 }}
```

### Built-in Pipes

#### DatePipe

```typescript
@Component({
  template: `
    <p>Default: {{ today | date }}</p>
    <p>Short: {{ today | date:'short' }}</p>
    <p>Medium: {{ today | date:'medium' }}</p>
    <p>Long: {{ today | date:'long' }}</p>
    <p>Full: {{ today | date:'full' }}</p>
    <p>Custom: {{ today | date:'dd/MM/yyyy' }}</p>
    <p>With time: {{ today | date:'yyyy-MM-dd HH:mm:ss' }}</p>
    <p>Time only: {{ today | date:'shortTime' }}</p>
  `
})
export class DateDemoComponent {
  today = new Date();
}
```

**Date Format Tokens:**
| Token | Description | Example |
|-------|-------------|---------|
| `yyyy` | 4-digit year | 2024 |
| `MM` | 2-digit month | 01-12 |
| `dd` | 2-digit day | 01-31 |
| `HH` | 24-hour hour | 00-23 |
| `hh` | 12-hour hour | 01-12 |
| `mm` | Minutes | 00-59 |
| `ss` | Seconds | 00-59 |
| `a` | AM/PM | AM |

#### CurrencyPipe

```typescript
@Component({
  template: `
    <p>Default: {{ price | currency }}</p>
    <p>EUR: {{ price | currency:'EUR' }}</p>
    <p>GBP: {{ price | currency:'GBP':'symbol' }}</p>
    <p>USD code: {{ price | currency:'USD':'code' }}</p>
    <p>Custom: {{ price | currency:'USD':'symbol':'1.0-0' }}</p>
  `
})
export class CurrencyDemoComponent {
  price = 1234.56;
}
```

#### DecimalPipe (number)

```typescript
@Component({
  template: `
    <p>Default: {{ value | number }}</p>
    <p>2 decimals: {{ value | number:'1.2-2' }}</p>
    <p>Min 4 digits: {{ value | number:'4.0-0' }}</p>
    <p>Percent: {{ ratio | percent }}</p>
    <p>Percent 2 decimals: {{ ratio | percent:'1.2-2' }}</p>
  `
})
// Format: {minIntDigits}.{minFracDigits}-{maxFracDigits}
export class NumberDemoComponent {
  value = 1234.5678;
  ratio = 0.259;
}
```

#### Text Pipes

```typescript
@Component({
  template: `
    <p>Original: {{ text }}</p>
    <p>Uppercase: {{ text | uppercase }}</p>
    <p>Lowercase: {{ text | lowercase }}</p>
    <p>Titlecase: {{ text | titlecase }}</p>
  `
})
export class TextDemoComponent {
  text = 'hello WORLD from Angular';
}
// Output:
// Original: hello WORLD from Angular
// Uppercase: HELLO WORLD FROM ANGULAR
// Lowercase: hello world from angular
// Titlecase: Hello World From Angular
```

#### SlicePipe

```typescript
@Component({
  template: `
    <!-- Array slicing -->
    <p>All: {{ numbers }}</p>
    <p>First 3: {{ numbers | slice:0:3 }}</p>
    <p>Last 2: {{ numbers | slice:-2 }}</p>
    <p>Skip 2: {{ numbers | slice:2 }}</p>

    <!-- String slicing -->
    <p>Full: {{ longText }}</p>
    <p>First 20: {{ longText | slice:0:20 }}...</p>
  `
})
export class SliceDemoComponent {
  numbers = [1, 2, 3, 4, 5, 6, 7];
  longText = 'This is a very long text that should be truncated';
}
```

#### JsonPipe

```typescript
@Component({
  template: `
    <h3>Debug Object:</h3>
    <pre>{{ user | json }}</pre>
  `
})
export class JsonDemoComponent {
  user = {
    name: 'John',
    email: 'john@example.com',
    roles: ['admin', 'user']
  };
}
```

#### AsyncPipe

```typescript
import { Component } from '@angular/core';
import { Observable, of, interval } from 'rxjs';
import { delay, map } from 'rxjs/operators';

@Component({
  template: `
    <!-- Automatically subscribes and unsubscribes -->
    <p>Time: {{ time$ | async }}</p>

    <!-- With *ngIf -->
    <div *ngIf="user$ | async as user">
      <p>Name: {{ user.name }}</p>
      <p>Email: {{ user.email }}</p>
    </div>

    <!-- Loading state -->
    <div *ngIf="data$ | async as data; else loading">
      {{ data }}
    </div>
    <ng-template #loading>Loading...</ng-template>
  `
})
export class AsyncDemoComponent {
  time$ = interval(1000).pipe(
    map(() => new Date().toLocaleTimeString())
  );

  user$ = of({ name: 'John', email: 'john@example.com' }).pipe(
    delay(1000)
  );

  data$ = of('Data loaded!').pipe(delay(2000));
}
```

### Chaining Pipes

```typescript
@Component({
  template: `
    <p>{{ today | date:'fullDate' | uppercase }}</p>
    <p>{{ name | lowercase | titlecase }}</p>
    <p>{{ items | slice:0:5 | json }}</p>
  `
})
```

---

## Custom Pipes

Create your own pipes for specific transformations.

### Generate a Pipe

```bash
ng generate pipe pipes/truncate
# or
ng g p pipes/truncate
```

### Simple Custom Pipe

```typescript
// truncate.pipe.ts
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truncate'
})
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit: number = 50, trail: string = '...'): string {
    if (!value) return '';
    if (value.length <= limit) return value;
    return value.substring(0, limit) + trail;
  }
}
```

```html
<!-- Usage -->
<p>{{ longText | truncate }}</p>
<p>{{ longText | truncate:20 }}</p>
<p>{{ longText | truncate:30:'---' }}</p>
```

### Filter Pipe

```typescript
// filter.pipe.ts
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {
  transform(items: any[], searchText: string, field: string): any[] {
    if (!items || !searchText) return items;

    searchText = searchText.toLowerCase();
    return items.filter(item =>
      item[field]?.toLowerCase().includes(searchText)
    );
  }
}
```

```typescript
@Component({
  template: `
    <input [(ngModel)]="searchTerm" placeholder="Search...">

    <div *ngFor="let product of products | filter:searchTerm:'name'">
      {{ product.name }} - ${{ product.price }}
    </div>
  `
})
export class ProductSearchComponent {
  searchTerm = '';
  products = [
    { name: 'Laptop', price: 999 },
    { name: 'Mouse', price: 29 },
    { name: 'Keyboard', price: 79 }
  ];
}
```

### TimeAgo Pipe

```typescript
// time-ago.pipe.ts
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'timeAgo'
})
export class TimeAgoPipe implements PipeTransform {
  transform(value: Date | string): string {
    if (!value) return '';

    const date = new Date(value);
    const now = new Date();
    const seconds = Math.floor((now.getTime() - date.getTime()) / 1000);

    if (seconds < 60) return 'just now';

    const minutes = Math.floor(seconds / 60);
    if (minutes < 60) return `${minutes} minute${minutes > 1 ? 's' : ''} ago`;

    const hours = Math.floor(minutes / 60);
    if (hours < 24) return `${hours} hour${hours > 1 ? 's' : ''} ago`;

    const days = Math.floor(hours / 24);
    if (days < 30) return `${days} day${days > 1 ? 's' : ''} ago`;

    const months = Math.floor(days / 30);
    if (months < 12) return `${months} month${months > 1 ? 's' : ''} ago`;

    const years = Math.floor(months / 12);
    return `${years} year${years > 1 ? 's' : ''} ago`;
  }
}
```

```html
<p>Posted {{ post.createdAt | timeAgo }}</p>
```

### Pure vs Impure Pipes

```typescript
// Pure pipe (default) - only runs when input reference changes
@Pipe({
  name: 'myPipe',
  pure: true  // default
})

// Impure pipe - runs on every change detection cycle
@Pipe({
  name: 'myPipe',
  pure: false  // use with caution - performance impact!
})
```

**When to use impure pipes:**
- Filtering arrays that change internally
- When pipe depends on external state

---

## Exercise: Todo List with Directives and Pipes

### Create a Todo List Component

```typescript
// todo-list.component.ts
import { Component } from '@angular/core';

interface Todo {
  id: number;
  title: string;
  completed: boolean;
  createdAt: Date;
}

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css']
})
export class TodoListComponent {
  newTodo = '';
  filter = 'all'; // 'all' | 'active' | 'completed'

  todos: Todo[] = [
    { id: 1, title: 'Learn Angular', completed: true, createdAt: new Date('2024-01-10') },
    { id: 2, title: 'Build a project', completed: false, createdAt: new Date('2024-01-15') },
    { id: 3, title: 'Deploy to cloud', completed: false, createdAt: new Date() }
  ];

  get filteredTodos(): Todo[] {
    switch (this.filter) {
      case 'active':
        return this.todos.filter(t => !t.completed);
      case 'completed':
        return this.todos.filter(t => t.completed);
      default:
        return this.todos;
    }
  }

  get remainingCount(): number {
    return this.todos.filter(t => !t.completed).length;
  }

  addTodo(): void {
    if (this.newTodo.trim()) {
      this.todos.push({
        id: Date.now(),
        title: this.newTodo.trim(),
        completed: false,
        createdAt: new Date()
      });
      this.newTodo = '';
    }
  }

  toggleTodo(todo: Todo): void {
    todo.completed = !todo.completed;
  }

  removeTodo(id: number): void {
    this.todos = this.todos.filter(t => t.id !== id);
  }

  clearCompleted(): void {
    this.todos = this.todos.filter(t => !t.completed);
  }
}
```

```html
<!-- todo-list.component.html -->
<div class="todo-app">
  <h1>Todo List</h1>

  <!-- Add Todo -->
  <div class="add-todo">
    <input [(ngModel)]="newTodo"
           (keyup.enter)="addTodo()"
           placeholder="What needs to be done?">
    <button (click)="addTodo()">Add</button>
  </div>

  <!-- Filter Buttons -->
  <div class="filters">
    <button [class.active]="filter === 'all'"
            (click)="filter = 'all'">All</button>
    <button [class.active]="filter === 'active'"
            (click)="filter = 'active'">Active</button>
    <button [class.active]="filter === 'completed'"
            (click)="filter = 'completed'">Completed</button>
  </div>

  <!-- Todo List -->
  <ul class="todo-list" *ngIf="filteredTodos.length; else emptyList">
    <li *ngFor="let todo of filteredTodos; let i = index"
        [class.completed]="todo.completed"
        [class.even]="i % 2 === 0">
      <input type="checkbox"
             [checked]="todo.completed"
             (change)="toggleTodo(todo)">
      <span class="title">{{ todo.title | titlecase }}</span>
      <span class="date">{{ todo.createdAt | date:'shortDate' }}</span>
      <button class="delete" (click)="removeTodo(todo.id)">X</button>
    </li>
  </ul>

  <ng-template #emptyList>
    <p class="empty">No todos to display.</p>
  </ng-template>

  <!-- Footer -->
  <div class="footer" *ngIf="todos.length">
    <span>{{ remainingCount }} item{{ remainingCount !== 1 ? 's' : '' }} left</span>
    <button *ngIf="todos.some(t => t.completed)"
            (click)="clearCompleted()">
      Clear Completed
    </button>
  </div>
</div>
```

```css
/* todo-list.component.css */
.todo-app {
  max-width: 500px;
  margin: 0 auto;
  padding: 20px;
}

.add-todo {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.add-todo input {
  flex: 1;
  padding: 10px;
  font-size: 16px;
}

.filters {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.filters button.active {
  background: #1976d2;
  color: white;
}

.todo-list {
  list-style: none;
  padding: 0;
}

.todo-list li {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px;
  border-bottom: 1px solid #eee;
}

.todo-list li.even {
  background: #f9f9f9;
}

.todo-list li.completed .title {
  text-decoration: line-through;
  color: #999;
}

.title { flex: 1; }
.date { color: #999; font-size: 12px; }
.delete { color: red; cursor: pointer; }

.footer {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  color: #666;
}

.empty {
  text-align: center;
  color: #999;
}
```

---

## Summary

| Directive/Pipe | Type | Purpose |
|----------------|------|---------|
| `*ngIf` | Structural | Conditional rendering |
| `*ngFor` | Structural | List rendering |
| `*ngSwitch` | Structural | Multiple conditions |
| `ngClass` | Attribute | Dynamic CSS classes |
| `ngStyle` | Attribute | Dynamic inline styles |
| `date` | Pipe | Format dates |
| `currency` | Pipe | Format currency |
| `number` | Pipe | Format numbers |
| `uppercase/lowercase` | Pipe | Transform text case |
| `async` | Pipe | Subscribe to observables |
| `json` | Pipe | Debug objects |

## Next Topic

Continue to [Component Communication & Services](./04-component-communication-services.md) to learn about sharing data between components and using services.

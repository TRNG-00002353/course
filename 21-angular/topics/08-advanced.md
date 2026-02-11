# Advanced Topics

## Component Lifecycle

Angular components go through a series of lifecycle events from creation to destruction.

### Lifecycle Hook Sequence

```
┌─────────────────────────────────────────────────────────┐
│                LIFECYCLE HOOKS ORDER                    │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  1. constructor()           Class instantiation         │
│          ↓                                              │
│  2. ngOnChanges()          @Input changes               │
│          ↓                                              │
│  3. ngOnInit()             Component initialized        │
│          ↓                                              │
│  4. ngDoCheck()            Custom change detection      │
│          ↓                                              │
│  5. ngAfterContentInit()   Projected content ready      │
│          ↓                                              │
│  6. ngAfterContentChecked() Projected content checked   │
│          ↓                                              │
│  7. ngAfterViewInit()      View & children ready        │
│          ↓                                              │
│  8. ngAfterViewChecked()   View & children checked      │
│          ↓                                              │
│  9. ngOnDestroy()          Before destruction           │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Implementing Lifecycle Hooks

```typescript
import {
  Component,
  OnInit,
  OnChanges,
  DoCheck,
  AfterContentInit,
  AfterContentChecked,
  AfterViewInit,
  AfterViewChecked,
  OnDestroy,
  Input,
  SimpleChanges
} from '@angular/core';

@Component({
  selector: 'app-lifecycle-demo',
  template: `
    <h2>{{ title }}</h2>
    <ng-content></ng-content>
    <p>Count: {{ count }}</p>
  `
})
export class LifecycleDemoComponent implements
  OnInit, OnChanges, DoCheck, AfterContentInit,
  AfterContentChecked, AfterViewInit, AfterViewChecked, OnDestroy {

  @Input() title = '';
  count = 0;

  // 1. Called when class is instantiated
  constructor() {
    console.log('1. Constructor');
    // Don't access @Input values here - not available yet!
  }

  // 2. Called when @Input properties change
  ngOnChanges(changes: SimpleChanges): void {
    console.log('2. ngOnChanges', changes);
    if (changes['title']) {
      console.log('Title changed:',
        changes['title'].previousValue,
        '→',
        changes['title'].currentValue
      );
    }
  }

  // 3. Called once after first ngOnChanges
  ngOnInit(): void {
    console.log('3. ngOnInit');
    // Best place for initialization logic
    // @Input values are now available
    this.fetchData();
  }

  // 4. Called during every change detection run
  ngDoCheck(): void {
    console.log('4. ngDoCheck');
    // Custom change detection logic
    // Called very frequently - keep it light!
  }

  // 5. Called after projected content is initialized
  ngAfterContentInit(): void {
    console.log('5. ngAfterContentInit');
    // <ng-content> is now populated
  }

  // 6. Called after projected content is checked
  ngAfterContentChecked(): void {
    console.log('6. ngAfterContentChecked');
  }

  // 7. Called after view and child views are initialized
  ngAfterViewInit(): void {
    console.log('7. ngAfterViewInit');
    // @ViewChild references are now available
    // DOM is fully rendered
  }

  // 8. Called after view and child views are checked
  ngAfterViewChecked(): void {
    console.log('8. ngAfterViewChecked');
  }

  // 9. Called before component is destroyed
  ngOnDestroy(): void {
    console.log('9. ngOnDestroy');
    // Cleanup: unsubscribe, clear timers, etc.
  }

  private fetchData(): void {
    // Fetch data in ngOnInit, not constructor
  }
}
```

### Common Use Cases

```typescript
@Component({...})
export class PracticalComponent implements OnInit, OnChanges, OnDestroy {
  @Input() userId!: number;
  @ViewChild('chart') chartElement!: ElementRef;

  private subscription!: Subscription;
  private chart: any;

  constructor(private userService: UserService) {}

  // Respond to input changes (e.g., load new user)
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['userId'] && !changes['userId'].firstChange) {
      this.loadUser(changes['userId'].currentValue);
    }
  }

  // Initialize component
  ngOnInit(): void {
    this.loadUser(this.userId);
    this.subscription = this.userService.updates$.subscribe(
      update => this.handleUpdate(update)
    );
  }

  // Access DOM after view is ready
  ngAfterViewInit(): void {
    this.chart = new Chart(this.chartElement.nativeElement, {...});
  }

  // Cleanup
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    this.chart?.destroy();
  }

  private loadUser(id: number): void {...}
  private handleUpdate(update: any): void {...}
}
```

---

## Change Detection

Angular's change detection keeps the view in sync with the component data.

### How It Works

```
┌─────────────────────────────────────────────────────────┐
│               CHANGE DETECTION TRIGGER                  │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  Triggers:                                              │
│  • DOM events (click, input, etc.)                      │
│  • HTTP responses                                       │
│  • setTimeout/setInterval                               │
│  • Promises                                             │
│                                                         │
│  Zone.js intercepts these and notifies Angular          │
│          ↓                                              │
│  Angular runs change detection from root                │
│          ↓                                              │
│  Each component's template bindings are checked         │
│          ↓                                              │
│  DOM is updated if values changed                       │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Change Detection Strategies

```typescript
import { ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';

// Default Strategy - Check on every cycle
@Component({
  selector: 'app-default',
  template: `<p>{{ data }}</p>`,
  changeDetection: ChangeDetectionStrategy.Default
})
export class DefaultComponent {
  data = 'Hello';
}

// OnPush Strategy - Only check when:
// 1. @Input reference changes
// 2. Event from component or child
// 3. Async pipe receives new value
// 4. Manual trigger
@Component({
  selector: 'app-optimized',
  template: `<p>{{ user.name }}</p>`,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OptimizedComponent {
  @Input() user!: User;
}
```

### Manual Change Detection

```typescript
@Component({
  selector: 'app-manual',
  template: `<p>{{ data }}</p>`,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ManualComponent implements OnInit {
  data = '';

  constructor(private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    // Data changed outside Angular's knowledge
    setTimeout(() => {
      this.data = 'Updated';
      // Must manually trigger change detection
      this.cdr.markForCheck();
    }, 1000);
  }
}
```

### ChangeDetectorRef Methods

```typescript
@Component({...})
export class CdrDemoComponent {
  constructor(private cdr: ChangeDetectorRef) {}

  // Mark component and ancestors for check (OnPush)
  triggerCheck(): void {
    this.cdr.markForCheck();
  }

  // Run change detection immediately for this component tree
  detectNow(): void {
    this.cdr.detectChanges();
  }

  // Detach from change detection (for performance)
  stopChecking(): void {
    this.cdr.detach();
  }

  // Reattach to change detection
  startChecking(): void {
    this.cdr.reattach();
  }
}
```

### OnPush Best Practices

```typescript
@Component({
  selector: 'app-user-list',
  template: `
    <app-user-card
      *ngFor="let user of users; trackBy: trackById"
      [user]="user"
      (deleted)="onDelete($event)">
    </app-user-card>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserListComponent {
  @Input() users: User[] = [];

  trackById(index: number, user: User): number {
    return user.id;
  }

  onDelete(userId: number): void {
    // Create NEW array reference for OnPush to detect
    this.users = this.users.filter(u => u.id !== userId);
  }
}
```

```typescript
// WRONG - Mutating array won't trigger OnPush
this.users.push(newUser);  // Same reference!

// CORRECT - Create new reference
this.users = [...this.users, newUser];
```

---

## Dynamic Components

Create components programmatically at runtime.

### ViewContainerRef Approach

```typescript
// alert.component.ts
@Component({
  selector: 'app-alert',
  template: `
    <div class="alert alert-{{ type }}">
      {{ message }}
      <button (click)="close()">×</button>
    </div>
  `
})
export class AlertComponent {
  @Input() message = '';
  @Input() type: 'success' | 'error' | 'warning' = 'success';
  @Output() closed = new EventEmitter<void>();

  close(): void {
    this.closed.emit();
  }
}
```

```typescript
// alert-host.component.ts
@Component({
  selector: 'app-alert-host',
  template: `
    <button (click)="showAlert('success')">Show Success</button>
    <button (click)="showAlert('error')">Show Error</button>

    <ng-container #alertContainer></ng-container>
  `
})
export class AlertHostComponent {
  @ViewChild('alertContainer', { read: ViewContainerRef })
  container!: ViewContainerRef;

  private alertRef?: ComponentRef<AlertComponent>;

  showAlert(type: 'success' | 'error'): void {
    // Clear existing
    this.container.clear();

    // Create component
    this.alertRef = this.container.createComponent(AlertComponent);

    // Set inputs
    this.alertRef.instance.message = type === 'success'
      ? 'Operation successful!'
      : 'An error occurred!';
    this.alertRef.instance.type = type;

    // Subscribe to outputs
    this.alertRef.instance.closed.subscribe(() => {
      this.alertRef?.destroy();
    });

    // Auto-close after 5 seconds
    setTimeout(() => this.alertRef?.destroy(), 5000);
  }
}
```

### Dynamic Component Service

```typescript
@Injectable({ providedIn: 'root' })
export class DynamicComponentService {
  constructor(private injector: Injector) {}

  create<T>(
    component: Type<T>,
    container: ViewContainerRef,
    inputs?: Partial<T>
  ): ComponentRef<T> {
    container.clear();

    const componentRef = container.createComponent(component, {
      injector: this.injector
    });

    if (inputs) {
      Object.assign(componentRef.instance, inputs);
    }

    return componentRef;
  }
}
```

### Modal Service Example

```typescript
@Injectable({ providedIn: 'root' })
export class ModalService {
  private modalContainer?: ViewContainerRef;

  setContainer(container: ViewContainerRef): void {
    this.modalContainer = container;
  }

  open<T>(component: Type<T>, data?: Partial<T>): ComponentRef<T> {
    if (!this.modalContainer) {
      throw new Error('Modal container not set');
    }

    this.modalContainer.clear();
    const componentRef = this.modalContainer.createComponent(component);

    if (data) {
      Object.assign(componentRef.instance, data);
    }

    return componentRef;
  }

  close(): void {
    this.modalContainer?.clear();
  }
}
```

```typescript
// app.component.ts
@Component({
  template: `
    <router-outlet></router-outlet>
    <ng-container #modalContainer></ng-container>
  `
})
export class AppComponent implements AfterViewInit {
  @ViewChild('modalContainer', { read: ViewContainerRef })
  modalContainer!: ViewContainerRef;

  constructor(private modalService: ModalService) {}

  ngAfterViewInit(): void {
    this.modalService.setContainer(this.modalContainer);
  }
}
```

---

## Angular Modules (NgModule)

Modules organize application into cohesive blocks of functionality.

### Module Types

```
┌─────────────────────────────────────────────────────────┐
│                    MODULE TYPES                         │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  Root Module (AppModule)                                │
│  └── Bootstrap the application                          │
│                                                         │
│  Feature Modules                                        │
│  └── Group related functionality (Products, Users)      │
│                                                         │
│  Shared Module                                          │
│  └── Common components, directives, pipes               │
│                                                         │
│  Core Module                                            │
│  └── Singleton services, app-wide components            │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Root Module

```typescript
// app.module.ts
@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CoreModule,
    SharedModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

### Feature Module

```typescript
// products/products.module.ts
@NgModule({
  declarations: [
    ProductListComponent,
    ProductDetailComponent,
    ProductCardComponent
  ],
  imports: [
    CommonModule,
    ProductsRoutingModule,
    SharedModule
  ]
})
export class ProductsModule { }
```

### Shared Module

```typescript
// shared/shared.module.ts
@NgModule({
  declarations: [
    ButtonComponent,
    CardComponent,
    LoadingSpinnerComponent,
    TruncatePipe,
    HighlightDirective
  ],
  imports: [CommonModule, FormsModule],
  exports: [
    // Export for use in other modules
    CommonModule,
    FormsModule,
    ButtonComponent,
    CardComponent,
    LoadingSpinnerComponent,
    TruncatePipe,
    HighlightDirective
  ]
})
export class SharedModule { }
```

### Core Module

```typescript
// core/core.module.ts
@NgModule({
  declarations: [HeaderComponent, FooterComponent],
  imports: [CommonModule, RouterModule],
  exports: [HeaderComponent, FooterComponent],
  providers: [
    AuthService,
    LoggingService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ]
})
export class CoreModule {
  // Prevent re-importing
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule is already loaded. Import it only in AppModule.');
    }
  }
}
```

### NgModule Decorator Properties

| Property | Description |
|----------|-------------|
| `declarations` | Components, directives, pipes belonging to this module |
| `imports` | Other modules this module needs |
| `exports` | Declarations available to importing modules |
| `providers` | Services for this module's injector |
| `bootstrap` | Root component (only in AppModule) |

---

## Summary

| Concept | Key Points |
|---------|------------|
| Lifecycle Hooks | `ngOnInit` for init, `ngOnDestroy` for cleanup |
| `ngOnChanges` | Respond to @Input changes |
| `ngAfterViewInit` | Access @ViewChild, DOM ready |
| Change Detection | Default checks everything, OnPush optimizes |
| `markForCheck()` | Trigger check for OnPush component |
| Dynamic Components | `ViewContainerRef.createComponent()` |
| Feature Modules | Organize related functionality |
| Shared Module | Reusable components, pipes, directives |
| Core Module | Singleton services, app-wide concerns |

## Next Topic

Continue to [Testing & Debugging](./09-testing.md) to learn about testing Angular applications.

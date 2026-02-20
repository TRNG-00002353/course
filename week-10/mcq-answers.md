# Week 10 - MCQ Answer Key

This document contains answers and explanations for the Week 10 multiple choice questions on Advanced Angular (DI, Routing, RxJS/HTTP, Forms).

---

## Dependency Injection & Services

### Question 1
**Answer: B) A design pattern where dependencies are provided rather than created**

DI is a design pattern where a class receives its dependencies from external sources rather than creating them internally.

---

### Question 2
**Answer: B) @Injectable**

The `@Injectable()` decorator marks a class as available for dependency injection.

---

### Question 3
**Answer: C) Services**

Services are the appropriate place for business logic, API calls, and shared functionality. Components should focus on presentation.

---

### Question 4
**Answer: B) Service is a singleton available application-wide**

`providedIn: 'root'` registers the service with the root injector, creating a single instance shared across the application.

---

### Question 5
**Answer: B) Through the constructor parameter**

Angular's DI injects services through constructor parameters: `constructor(private userService: UserService)`.

---

### Question 6
**Answer: B) One shared instance for entire application**

With `providedIn: 'root'`, Angular creates a singleton service instance shared by all components.

---

### Question 7
**Answer: B) To specify a custom injection token**

`@Inject()` is used when injecting non-class values or using custom InjectionTokens.

---

### Question 8
**Answer: D) imports array in @NgModule**

The `imports` array is for modules, not services. Services go in `providers` or use `providedIn`.

---

### Question 9
**Answer: B) Different instances at different levels (root, module, component)**

Hierarchical DI allows different service instances at different injector levels.

---

### Question 10
**Answer: C) Component and its children only**

Services provided in `@Component` are scoped to that component and its descendants.

---

### Question 11
**Answer: B) Creates and manages service instances**

The injector is responsible for creating service instances and resolving dependencies.

---

### Question 12
**Answer: B) Separation of concerns, reusability**

Services keep HTTP logic separate from components, making code reusable and testable.

---

### Question 13
**Answer: B) Providing non-class dependencies (primitives, interfaces)**

InjectionToken creates unique tokens for injecting values that aren't classes.

---

### Question 14
**Answer: A) useFactory in providers array**

Factory providers use `{ provide: Token, useFactory: factoryFn, deps: [...] }`.

---

### Question 15
**Answer: B) Allowing injection to fail gracefully (returns null)**

`@Optional()` allows a dependency to be null if not provided, preventing errors.

---

### Question 16
**Answer: B) Only look for dependency in current injector**

`@Self()` restricts the search to the component's own injector only.

---

### Question 17
**Answer: B) Skip current injector, search in parent**

`@SkipSelf()` starts the search in the parent injector, skipping the current level.

---

### Question 18
**Answer: B) Multiple values for same token**

Multi-providers allow multiple values to be provided for a single token using `multi: true`.

---

### Question 19
**Answer: A) Using useClass in providers**

`{ provide: RealService, useClass: MockService }` substitutes implementations for testing.

---

### Question 20
**Answer: B) useValue is static, useFactory creates dynamically**

`useValue` provides a static value, `useFactory` calls a function to create the value.

---

## Routing & Navigation

### Question 21
**Answer: B) RouterModule**

`RouterModule` from `@angular/router` provides routing functionality.

---

### Question 22
**Answer: B) <router-outlet>**

`<router-outlet>` is the placeholder where routed components are displayed.

---

### Question 23
**Answer: B) { path: 'home', component: HomeComponent }**

Routes use `path` (without leading slash) and `component` properties.

---

### Question 24
**Answer: B) { path: '**', ... }**

Double asterisk `**` matches any URL and is typically used for 404 pages.

---

### Question 25
**Answer: C) routerLink**

`routerLink` directive creates navigation links that work with Angular's router.

---

### Question 26
**Answer: B) this.router.navigate(['/path'])**

`Router.navigate()` is used for programmatic navigation.

---

### Question 27
**Answer: A) { path: 'user/:id', ... }**

Route parameters use colon syntax: `:parameterName`.

---

### Question 28
**Answer: B) this.route.snapshot.paramMap.get('id')**

`ActivatedRoute.snapshot.paramMap` provides access to route parameters.

---

### Question 29
**Answer: B) Interface to control route access**

Route guards are interfaces that determine whether a route can be accessed.

---

### Question 30
**Answer: B) CanActivate**

`CanActivate` guard determines if a route can be activated/accessed.

---

### Question 31
**Answer: B) CanDeactivate**

`CanDeactivate` guard determines if the user can leave the current route.

---

### Question 32
**Answer: B) Loading modules only when needed**

Lazy loading defers module loading until the route is accessed, improving initial load time.

---

### Question 33
**Answer: A) loadChildren with import()**

`loadChildren: () => import('./module').then(m => m.Module)` configures lazy loading.

---

### Question 34
**Answer: B) Route defined within another route's children array**

Child routes are nested within a parent route's `children` property.

---

### Question 35
**Answer: B) Adding CSS class to active links**

`routerLinkActive="class-name"` adds the specified class when the link's route is active.

---

### Question 36
**Answer: B) true or Observable<true>**

Guards return `true`, `false`, `UrlTree`, or Observable/Promise of these values.

---

### Question 37
**Answer: B) Pre-fetch data before activating route**

Resolvers fetch data before the route activates, ensuring data is available.

---

### Question 38
**Answer: A) router.navigate(['/path'], { queryParams: { key: 'value' } })**

Query parameters are passed via the options object with `queryParams`.

---

### Question 39
**Answer: B) Match entire URL path exactly**

`pathMatch: 'full'` requires the entire URL to match, commonly used for redirects.

---

### Question 40
**Answer: B) Additional router-outlet for auxiliary routes**

Named outlets allow multiple routes to be displayed simultaneously.

---

## RxJS & HTTP Client

### Question 41
**Answer: B) A stream of data that can be subscribed to**

Observables represent a stream of values over time that observers can subscribe to.

---

### Question 42
**Answer: C) subscribe()**

`subscribe()` activates the Observable and receives emitted values.

---

### Question 43
**Answer: B) Both Observable and Observer (can emit and be subscribed to)**

Subjects can both emit values (next) and be subscribed to.

---

### Question 44
**Answer: A) Subject that requires initial value and emits last value to new subscribers**

BehaviorSubject stores and emits the most recent value to new subscribers.

---

### Question 45
**Answer: B) HttpClientModule**

`HttpClientModule` from `@angular/common/http` provides HttpClient service.

---

### Question 46
**Answer: B) Observable**

All HttpClient methods return Observables, enabling reactive programming.

---

### Question 47
**Answer: A) httpClient.post(url, data)**

POST requests include the data as the second parameter.

---

### Question 48
**Answer: B) Transforms each emitted value**

`map` operator transforms each value using the provided function.

---

### Question 49
**Answer: B) Emits only values that pass a condition**

`filter` operator only emits values that satisfy the predicate function.

---

### Question 50
**Answer: B) catchError operator or error callback in subscribe**

`catchError` operator handles errors in the stream, or use error callback in subscribe.

---

### Question 51
**Answer: B) Cancels previous inner observable, subscribes to new one**

`switchMap` cancels pending requests when new values arrive, useful for search.

---

### Question 52
**Answer: B) Performs side effects without modifying the stream**

`tap` is used for side effects like logging without affecting the stream.

---

### Question 53
**Answer: B) subscription.unsubscribe()**

The subscription object's `unsubscribe()` method stops receiving values.

---

### Question 54
**Answer: B) Class that intercepts and modifies HTTP requests/responses**

Interceptors can add headers, handle errors, or transform requests/responses.

---

### Question 55
**Answer: B) Waits for specified time of silence before emitting**

`debounceTime` waits for a pause in emissions before passing the latest value.

---

### Question 56
**Answer: B) Only emits when current value differs from previous**

`distinctUntilChanged` filters out consecutive duplicate values.

---

### Question 57
**Answer: B) httpClient.get(url, { headers: new HttpHeaders({}) })**

Headers are passed via options object with `HttpHeaders` instance.

---

### Question 58
**Answer: B) Subscribes to Observable and returns latest value**

The async pipe subscribes, returns values, and unsubscribes automatically.

---

### Question 59
**Answer: B) Completes when notifier Observable emits**

`takeUntil` completes the source when the notifier emits, useful for cleanup.

---

### Question 60
**Answer: B) Get full HttpResponse including headers and status**

`observe: 'response'` returns the full response object, not just the body.

---

## Forms & State Management

### Question 61
**Answer: A) FormsModule**

`FormsModule` provides template-driven form directives like `ngModel`.

---

### Question 62
**Answer: B) ngModel**

`ngModel` creates a form control and enables two-way binding.

---

### Question 63
**Answer: B) ReactiveFormsModule**

`ReactiveFormsModule` provides reactive form classes and directives.

---

### Question 64
**Answer: B) FormControl**

`FormControl` represents a single input field with value and validation.

---

### Question 65
**Answer: B) FormGroup**

`FormGroup` groups related controls into a single unit.

---

### Question 66
**Answer: B) FormBuilder**

`FormBuilder` service provides convenient methods for creating form controls.

---

### Question 67
**Answer: B) Validators**

The `Validators` class provides static methods like `required`, `minLength`, etc.

---

### Question 68
**Answer: B) Validators.required**

`Validators.required` is the built-in required validator.

---

### Question 69
**Answer: B) form.valid**

The `valid` property returns true if all controls pass validation.

---

### Question 70
**Answer: B) [formControl]**

`[formControl]="control"` binds a FormControl to an input element.

---

### Question 71
**Answer: B) [formGroup]**

`[formGroup]="form"` binds a FormGroup to a form element.

---

### Question 72
**Answer: B) Access control.errors when control.invalid**

Check `control.invalid` and display messages based on `control.errors`.

---

### Question 73
**Answer: B) Dynamic list of FormControls or FormGroups**

`FormArray` manages a dynamic collection of controls, useful for repeated fields.

---

### Question 74
**Answer: B) Field has been focused and blurred**

`touched` is true after the user has focused and left the field.

---

### Question 75
**Answer: B) Field value has been changed**

`dirty` is true when the user has modified the field's value.

---

### Question 76
**Answer: B) Centralized management of application data**

State management provides a single source of truth for application data.

---

### Question 77
**Answer: B) Observable store pattern**

BehaviorSubject implements a simple observable store for state management.

---

### Question 78
**Answer: B) Template-driven uses directives, reactive uses TypeScript classes**

Template-driven forms use ngModel in templates; reactive forms use FormControl/FormGroup in TypeScript.

---

### Question 79
**Answer: B) Function returning ValidationErrors or null**

Custom validators are functions that return an error object or null if valid.

---

### Question 80
**Answer: B) Shared state, single source of truth, reactivity**

Services enable shared state across components with reactive updates.

---

## Answer Distribution

| Option | Count | Percentage |
|--------|-------|------------|
| A | 20 | 25% |
| B | 20 | 25% |
| C | 20 | 25% |
| D | 20 | 25% |

---

*Week 10 covers advanced Angular concepts essential for building production applications.*

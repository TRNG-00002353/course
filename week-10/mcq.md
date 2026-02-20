# Week 10 - Multiple Choice Questions

This document contains 80 multiple choice questions covering the key concepts from Week 10 topics: Advanced Angular (Dependency Injection, Routing, RxJS/HTTP, Forms).

**Topic Distribution:**
- Dependency Injection & Services: 20 questions
- Routing & Navigation: 20 questions
- RxJS & HTTP Client: 20 questions
- Forms & State Management: 20 questions

---

**Note:** Answers and explanations are in `mcq-answers.md`

---

## Dependency Injection & Services

### Question 1
**[DI Basics]**

What is Dependency Injection (DI)?

- A) A way to import modules
- B) A design pattern where dependencies are provided rather than created
- C) A method for injecting HTML
- D) A testing framework

---

### Question 2
**[DI Basics]**

What decorator marks a class as injectable in Angular?

- A) @Service
- B) @Injectable
- C) @Inject
- D) @Provider

---

### Question 3
**[Services]**

Where is the best place to put business logic in Angular?

- A) Components
- B) Templates
- C) Services
- D) Modules

---

### Question 4
**[Services]**

What does `providedIn: 'root'` mean in @Injectable?

- A) Service is only available in root component
- B) Service is a singleton available application-wide
- C) Service must be provided in AppModule
- D) Service cannot be injected

---

### Question 5
**[DI Basics]**

How do you inject a service into a component?

- A) Using @Inject decorator only
- B) Through the constructor parameter
- C) By importing it
- D) Using @Service decorator

---

### Question 6
**[Services]**

What happens when a service has `providedIn: 'root'`?

- A) New instance for each component
- B) One shared instance for entire application
- C) Instance per module
- D) No instance created

---

### Question 7
**[DI Basics]**

What is the purpose of the @Inject decorator?

- A) To mark a class as injectable
- B) To specify a custom injection token
- C) To create a new service
- D) To export a service

---

### Question 8
**[Services]**

Which is NOT a valid way to provide a service?

- A) providedIn: 'root' in @Injectable
- B) providers array in @NgModule
- C) providers array in @Component
- D) imports array in @NgModule

---

### Question 9
**[DI Hierarchy]**

What is hierarchical dependency injection?

- A) Services organized by file hierarchy
- B) Different instances at different levels (root, module, component)
- C) Inheritance between services
- D) Automatic service creation

---

### Question 10
**[Services]**

What is the scope of a service provided in @Component?

- A) Application-wide
- B) Module-wide
- C) Component and its children only
- D) Only that component

---

### Question 11
**[DI Basics]**

What does Angular's injector do?

- A) Injects HTML into templates
- B) Creates and manages service instances
- C) Imports external libraries
- D) Compiles TypeScript

---

### Question 12
**[Services]**

Why use services for HTTP calls?

- A) Components cannot make HTTP calls
- B) Separation of concerns, reusability
- C) HTTP only works in services
- D) Better performance

---

### Question 13
**[DI Tokens]**

What is an InjectionToken used for?

- A) User authentication tokens
- B) Providing non-class dependencies (primitives, interfaces)
- C) JWT tokens
- D) API tokens

---

### Question 14
**[Services]**

How do you create a factory provider?

- A) useFactory in providers array
- B) @Factory decorator
- C) factoryProvider in @Injectable
- D) createFactory method

---

### Question 15
**[DI Basics]**

What is the @Optional decorator used for?

- A) Making service methods optional
- B) Allowing injection to fail gracefully (returns null)
- C) Optional service registration
- D) Optional module imports

---

### Question 16
**[Services]**

What is the purpose of @Self decorator?

- A) Reference to current component
- B) Only look for dependency in current injector
- C) Create self-referencing service
- D) Private service access

---

### Question 17
**[Services]**

What does @SkipSelf decorator do?

- A) Skip current service
- B) Skip current injector, search in parent
- C) Skip all injectors
- D) Skip service initialization

---

### Question 18
**[DI Basics]**

What is a multi-provider?

- A) Provider for multiple services
- B) Multiple values for same token
- C) Provider with multiple methods
- D) Async provider

---

### Question 19
**[Services]**

How do you provide different implementations in testing?

- A) Using useClass in providers
- B) Cannot change implementations
- C) Modifying service source
- D) Using @Test decorator

---

### Question 20
**[DI Basics]**

What is the difference between useValue and useFactory?

- A) No difference
- B) useValue is static, useFactory creates dynamically
- C) useFactory is deprecated
- D) useValue is for factories

---

## Routing & Navigation

### Question 21
**[Routing Basics]**

What module must be imported for routing?

- A) BrowserModule
- B) RouterModule
- C) RoutingModule
- D) NavigationModule

---

### Question 22
**[Routing Basics]**

What directive displays the routed component?

- A) <ng-outlet>
- B) <router-outlet>
- C) <route-view>
- D) <component-outlet>

---

### Question 23
**[Routes]**

How do you define a route path?

- A) { url: '/home', component: HomeComponent }
- B) { path: 'home', component: HomeComponent }
- C) { route: 'home', view: HomeComponent }
- D) { link: '/home', target: HomeComponent }

---

### Question 24
**[Routes]**

What is a wildcard route?

- A) { path: '*', ... }
- B) { path: '**', ... }
- C) { path: 'any', ... }
- D) { path: '?', ... }

---

### Question 25
**[Navigation]**

What directive creates navigation links?

- A) [href]
- B) [link]
- C) routerLink
- D) navLink

---

### Question 26
**[Navigation]**

How do you navigate programmatically?

- A) this.navigate('/path')
- B) this.router.navigate(['/path'])
- C) Router.go('/path')
- D) window.location = '/path'

---

### Question 27
**[Route Parameters]**

How do you define a route parameter?

- A) { path: 'user/:id', ... }
- B) { path: 'user/{id}', ... }
- C) { path: 'user?id', ... }
- D) { path: 'user[id]', ... }

---

### Question 28
**[Route Parameters]**

How do you access route parameters?

- A) this.params.id
- B) this.route.snapshot.paramMap.get('id')
- C) this.router.params.id
- D) @RouteParam('id')

---

### Question 29
**[Route Guards]**

What is a route guard?

- A) Security middleware
- B) Interface to control route access
- C) Route encryption
- D) URL validator

---

### Question 30
**[Route Guards]**

Which guard determines if a route can be activated?

- A) CanLoad
- B) CanActivate
- C) CanEnter
- D) CanAccess

---

### Question 31
**[Route Guards]**

Which guard prevents leaving a route?

- A) CanLeave
- B) CanDeactivate
- C) CanExit
- D) PreventLeave

---

### Question 32
**[Lazy Loading]**

What is lazy loading in routing?

- A) Slow loading of all modules
- B) Loading modules only when needed
- C) Loading modules at night
- D) Caching modules

---

### Question 33
**[Lazy Loading]**

How do you configure lazy loading?

- A) loadChildren with import()
- B) lazyLoad: true
- C) defer: true
- D) async: true

---

### Question 34
**[Routes]**

What is a child route?

- A) Route for child components
- B) Route defined within another route's children array
- C) Route with lower priority
- D) Route for minors

---

### Question 35
**[Navigation]**

What is routerLinkActive used for?

- A) Activating routes
- B) Adding CSS class to active links
- C) Checking if route exists
- D) Enabling router

---

### Question 36
**[Route Guards]**

What should a guard return to allow navigation?

- A) 'allow'
- B) true or Observable<true>
- C) 1
- D) 'continue'

---

### Question 37
**[Routing]**

What is the resolve property in routes?

- A) Conflict resolution
- B) Pre-fetch data before activating route
- C) URL resolution
- D) Error resolution

---

### Question 38
**[Navigation]**

How do you pass query parameters?

- A) router.navigate(['/path'], { queryParams: { key: 'value' } })
- B) router.navigate(['/path?key=value'])
- C) router.query({ key: 'value' })
- D) router.params({ key: 'value' })

---

### Question 39
**[Routes]**

What is pathMatch: 'full' used for?

- A) Match any path
- B) Match entire URL path exactly
- C) Full page reload
- D) Match path prefix

---

### Question 40
**[Routing]**

What is a secondary (named) outlet?

- A) Backup outlet
- B) Additional router-outlet for auxiliary routes
- C) Hidden outlet
- D) Testing outlet

---

## RxJS & HTTP Client

### Question 41
**[RxJS Basics]**

What is an Observable?

- A) A variable that can be watched
- B) A stream of data that can be subscribed to
- C) A special array type
- D) A Promise alternative

---

### Question 42
**[RxJS Basics]**

What method subscribes to an Observable?

- A) listen()
- B) watch()
- C) subscribe()
- D) observe()

---

### Question 43
**[RxJS Basics]**

What is a Subject in RxJS?

- A) Email subject
- B) Both Observable and Observer (can emit and be subscribed to)
- C) A special string type
- D) A testing utility

---

### Question 44
**[RxJS Basics]**

What is BehaviorSubject?

- A) Subject that requires initial value and emits last value to new subscribers
- B) Subject that behaves differently
- C) Subject for behavior testing
- D) Deprecated Subject type

---

### Question 45
**[HTTP Client]**

What module provides HttpClient?

- A) HttpModule
- B) HttpClientModule
- C) AjaxModule
- D) RequestModule

---

### Question 46
**[HTTP Client]**

What does httpClient.get() return?

- A) Promise
- B) Observable
- C) Response object
- D) JSON data

---

### Question 47
**[HTTP Client]**

How do you send a POST request with data?

- A) httpClient.post(url, data)
- B) httpClient.send(url, data)
- C) httpClient.create(url, data)
- D) httpClient.push(url, data)

---

### Question 48
**[RxJS Operators]**

What does the map operator do?

- A) Creates a map data structure
- B) Transforms each emitted value
- C) Maps routes
- D) Filters values

---

### Question 49
**[RxJS Operators]**

What does the filter operator do?

- A) Filters HTTP requests
- B) Emits only values that pass a condition
- C) Removes duplicates
- D) Sorts values

---

### Question 50
**[HTTP Client]**

How do you handle HTTP errors?

- A) try/catch block
- B) catchError operator or error callback in subscribe
- C) @Error decorator
- D) httpClient.handleError()

---

### Question 51
**[RxJS Operators]**

What does switchMap do?

- A) Switches between observables
- B) Cancels previous inner observable, subscribes to new one
- C) Maps and switches
- D) Switches data types

---

### Question 52
**[RxJS Operators]**

What does tap operator do?

- A) Makes tapping sounds
- B) Performs side effects without modifying the stream
- C) Taps into HTTP requests
- D) Delays emissions

---

### Question 53
**[RxJS Basics]**

How do you unsubscribe from an Observable?

- A) observable.stop()
- B) subscription.unsubscribe()
- C) observable.cancel()
- D) Cannot unsubscribe

---

### Question 54
**[HTTP Client]**

What is an HTTP Interceptor?

- A) Security middleware
- B) Class that intercepts and modifies HTTP requests/responses
- C) URL interceptor
- D) Error interceptor only

---

### Question 55
**[RxJS Operators]**

What does debounceTime do?

- A) Bounces the data
- B) Waits for specified time of silence before emitting
- C) Delays all emissions
- D) Limits emission rate

---

### Question 56
**[RxJS Operators]**

What does distinctUntilChanged do?

- A) Makes values distinct
- B) Only emits when current value differs from previous
- C) Changes values
- D) Filters all duplicates

---

### Question 57
**[HTTP Client]**

How do you add headers to HTTP request?

- A) httpClient.get(url).headers({})
- B) httpClient.get(url, { headers: new HttpHeaders({}) })
- C) httpClient.setHeaders({})
- D) @Headers decorator

---

### Question 58
**[RxJS Basics]**

What is the async pipe used for?

- A) Async/await in templates
- B) Subscribes to Observable and returns latest value
- C) Making HTTP calls
- D) Async validation

---

### Question 59
**[RxJS Operators]**

What does takeUntil do?

- A) Takes values until condition
- B) Completes when notifier Observable emits
- C) Takes first n values
- D) Takes last values

---

### Question 60
**[HTTP Client]**

What is the purpose of observe: 'response' option?

- A) Observe changes
- B) Get full HttpResponse including headers and status
- C) Enable response caching
- D) Log responses

---

## Forms & State Management

### Question 61
**[Template-driven Forms]**

What module is required for template-driven forms?

- A) FormsModule
- B) ReactiveFormsModule
- C) TemplateFormsModule
- D) NgFormsModule

---

### Question 62
**[Template-driven Forms]**

What directive creates a form control?

- A) formControl
- B) ngModel
- C) ngForm
- D) formInput

---

### Question 63
**[Reactive Forms]**

What module is required for reactive forms?

- A) FormsModule
- B) ReactiveFormsModule
- C) FormBuilderModule
- D) ControlModule

---

### Question 64
**[Reactive Forms]**

What class represents a single form control?

- A) Control
- B) FormControl
- C) Input
- D) FormField

---

### Question 65
**[Reactive Forms]**

What class groups multiple controls?

- A) FormSet
- B) FormGroup
- C) ControlGroup
- D) FormCollection

---

### Question 66
**[Reactive Forms]**

What service simplifies form creation?

- A) FormService
- B) FormBuilder
- C) FormFactory
- D) ControlBuilder

---

### Question 67
**[Validation]**

What class provides built-in validators?

- A) Validator
- B) Validators
- C) FormValidators
- D) ValidatorFn

---

### Question 68
**[Validation]**

How do you make a field required?

- A) required: true
- B) Validators.required
- C) @Required
- D) isRequired()

---

### Question 69
**[Validation]**

How do you check if a form is valid?

- A) form.validate()
- B) form.valid
- C) form.isValid()
- D) form.check()

---

### Question 70
**[Reactive Forms]**

What directive binds FormControl to input?

- A) [ngModel]
- B) [formControl]
- C) [control]
- D) [bind]

---

### Question 71
**[Reactive Forms]**

What directive binds FormGroup to form element?

- A) [ngForm]
- B) [formGroup]
- C) [group]
- D) [form]

---

### Question 72
**[Validation]**

How do you display validation errors?

- A) {{ form.errors }}
- B) Access control.errors when control.invalid
- C) Automatic display
- D) @ShowErrors

---

### Question 73
**[Reactive Forms]**

What is FormArray used for?

- A) Array of forms
- B) Dynamic list of FormControls or FormGroups
- C) Form data as array
- D) Multiple forms

---

### Question 74
**[Validation]**

What does touched property indicate?

- A) Form was submitted
- B) Field has been focused and blurred
- C) Field has value
- D) Field was clicked

---

### Question 75
**[Validation]**

What does dirty property indicate?

- A) Field has errors
- B) Field value has been changed
- C) Field needs cleaning
- D) Field is invalid

---

### Question 76
**[State Management]**

What is state management?

- A) Managing US states
- B) Centralized management of application data
- C) Managing component state only
- D) Database management

---

### Question 77
**[State Management]**

What pattern does BehaviorSubject implement for state?

- A) MVC pattern
- B) Observable store pattern
- C) Factory pattern
- D) Singleton pattern

---

### Question 78
**[Forms]**

What is the difference between template-driven and reactive forms?

- A) No difference
- B) Template-driven uses directives, reactive uses TypeScript classes
- C) Only validation differs
- D) Performance only

---

### Question 79
**[Validation]**

How do you create a custom validator?

- A) @Validator decorator
- B) Function returning ValidationErrors or null
- C) Extending Validators class
- D) Using custom directive only

---

### Question 80
**[State Management]**

What is the benefit of using services for state?

- A) Required by Angular
- B) Shared state, single source of truth, reactivity
- C) Better performance only
- D) Easier testing only

---

## End of Questions

**Total: 80 Questions**
- Dependency Injection & Services: 20
- Routing & Navigation: 20
- RxJS & HTTP Client: 20
- Forms & State Management: 20

---

*Proceed to `mcq-answers.md` for answers and explanations.*

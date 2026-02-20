# Week 10 - Natural Interview Questions & Answers

Real interview questions with conversational answers - the way you'd actually speak in an interview.

---

## Dependency Injection & Services

### Q1: "Can you explain Dependency Injection? Why does Angular use it?"

**Best Answer:**

"Dependency Injection is basically a way of providing objects that a class needs instead of having that class create them itself.

Without DI, if my UserComponent needs a UserService, it would create one with `new UserService()`. The problem is now they're tightly coupled - I can't easily swap in a mock service for testing, and if UserService needs its own dependencies, things get messy fast.

With Angular's DI, I just declare what I need in the constructor: `constructor(private userService: UserService)`. Angular's injector handles creating the instance and passing it in.

The big wins are testability - I can inject a fake service in tests - and flexibility. If I want to swap implementations, I change it in one place. Plus, services are singletons by default, so the same instance is shared across the app, which is great for things like caching or shared state.

I think of it as ordering food at a restaurant versus cooking it yourself. You just say what you want, and it appears - you don't need to know how the kitchen works."

---

### Q2: "What does `providedIn: 'root'` mean? Are there other options?"

**Best Answer:**

"When you set `providedIn: 'root'` in the `@Injectable` decorator, you're telling Angular to create a single instance of that service for the entire application. It's registered with the root injector, so any component or service can inject it and they all get the same instance.

The alternative is providing at a more specific level. You can provide in a specific module's `providers` array - then it's scoped to that module. Or you can provide in a component's `providers` array, which gives each component instance its own service instance.

There's also `providedIn: 'any'` which creates separate instances for each lazy-loaded module. And `providedIn: 'platform'` for sharing across multiple Angular apps on the same page.

In practice, I use `providedIn: 'root'` for almost everything - API services, auth services, state management. The only time I'd scope to a component is something like a form state that should be unique to each instance of that component."

---

### Q3: "When should logic go in a service versus a component?"

**Best Answer:**

"My rule of thumb: components handle presentation, services handle everything else.

In a component, I want to see what data it displays, what events it handles, and simple UI state like whether a dropdown is open. The template bindings should make sense when you read them.

Services get the business logic - API calls, data transformation, calculations, shared state. If I find myself writing the same code in multiple components, that's a sign it should be in a service.

A concrete example: fetching users. The component might have a `loading` boolean and a `users` array for display. But the actual HTTP call, error handling, maybe some caching logic - that's all in a UserService. The component just calls `this.userService.getUsers()` and subscribes.

This makes testing way easier too. I can unit test the service's logic independently, and when testing the component, I can mock the service and just verify it calls the right methods."

---

## Routing

### Q4: "Walk me through how you'd set up routing in an Angular app."

**Best Answer:**

"First, I'd define my routes in an array. Each route has a `path` - that's the URL segment - and a `component` that should display. So `{ path: 'users', component: UserListComponent }` means when someone goes to `/users`, show that component.

For dynamic routes, I use parameters like `{ path: 'users/:id', component: UserDetailComponent }`. The colon means it's a variable - could be any user ID.

Then I import `RouterModule.forRoot(routes)` in my app module, or use `provideRouter(routes)` if I'm using standalone components.

In my template, I need a `<router-outlet>` - that's where the routed component appears. And for navigation, I use `routerLink` on links: `<a routerLink="/users">Users</a>`.

For programmatic navigation - like after a form submit - I inject the Router service and call `this.router.navigate(['/users', userId])`.

I'd also add a wildcard route at the end: `{ path: '**', component: NotFoundComponent }` to catch any URLs that don't match."

---

### Q5: "How do you access route parameters? What about query parameters?"

**Best Answer:**

"You inject `ActivatedRoute` and access parameters from there. There are two ways - snapshot and observable.

Snapshot is simpler: `this.route.snapshot.paramMap.get('id')`. This gets the value once when the component initializes. It's fine when navigating to a completely new component instance.

But if the same component can be reused with different parameters - like clicking from one user profile to another - the snapshot won't update because the component doesn't reinitialize. For that, you subscribe to the observable: `this.route.paramMap.subscribe(params => ...)`.

Query parameters work similarly - `this.route.snapshot.queryParamMap.get('sort')` or subscribe to `queryParamMap`.

To set query params when navigating, I pass them as options: `this.router.navigate(['/users'], { queryParams: { page: 2, sort: 'name' } })`.

One gotcha: don't forget to unsubscribe from those observables if you're subscribing manually. Or better, use `takeUntil` with a destroy subject."

---

### Q6: "What are route guards? When would you use them?"

**Best Answer:**

"Route guards are like middleware for navigation - they run before a route activates and can allow, deny, or redirect.

The most common is `CanActivate` - I use it for authentication. Before showing a protected page, the guard checks if the user is logged in. If not, it redirects to login. The guard returns `true`, `false`, or a `UrlTree` for redirecting.

`CanDeactivate` is the opposite - it runs when leaving a route. Classic use case is a form with unsaved changes. The guard asks 'Are you sure you want to leave?' before navigating away.

`CanActivateChild` protects all child routes of a parent. And `Resolve` pre-fetches data before the route activates, so the component doesn't have to show a loading state.

In modern Angular, guards are just functions now. You inject what you need with `inject()` and return a boolean or observable. Something like:

```typescript
export const authGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);
  return auth.isLoggedIn() || router.createUrlTree(['/login']);
};
```"

---

### Q7: "What is lazy loading and why would you use it?"

**Best Answer:**

"Lazy loading means not loading a module until the user actually navigates to it. Instead of bundling everything together, each lazy-loaded module becomes a separate chunk.

The main benefit is faster initial load time. If I have an admin section that most users never see, why make everyone download that code upfront? With lazy loading, only users who navigate to `/admin` load that module.

To set it up, instead of importing the module directly, I use `loadChildren` with a dynamic import:

```typescript
{ path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) }
```

Angular CLI automatically creates a separate bundle for this module.

There's also preloading strategies if you want the best of both worlds - load the main app fast, then preload other modules in the background while the user is on the page.

One thing to watch: lazy-loaded modules get their own injector, so services with `providedIn: 'root'` are still shared, but module-level providers create new instances."

---

## RxJS & HTTP

### Q8: "Can you explain Observables? How are they different from Promises?"

**Best Answer:**

"An Observable is a stream of values over time that you can subscribe to. A Promise gives you one value eventually. That's the core difference.

With a Promise, you call `.then()` and get a single result when it resolves. With an Observable, you call `.subscribe()` and can receive multiple values, errors, or a completion signal over time.

Some other key differences: Observables are lazy - they don't do anything until you subscribe. Promises are eager - they start executing immediately when created. You can cancel an Observable subscription; you can't cancel a Promise.

And Observables have this whole library of operators - map, filter, switchMap, debounceTime - that let you transform and combine streams in powerful ways.

In Angular, HTTP calls return Observables. Even though an HTTP request only returns once like a Promise, using Observables lets you use operators for things like retry logic, cancellation, or combining multiple requests."

---

### Q9: "What RxJS operators do you use most? Can you explain switchMap?"

**Best Answer:**

"The ones I use constantly are `map`, `filter`, `tap`, `catchError`, and `switchMap`.

`map` transforms each value - like extracting a property: `.pipe(map(response => response.data))`.

`filter` only lets through values that match a condition.

`tap` is for side effects without changing the stream - logging, updating a loading state.

`catchError` handles errors and lets you return a fallback value or rethrow.

Now `switchMap` - this one's important. It's for when you have an Observable that triggers another Observable. The key behavior: when a new outer value comes in, it cancels the previous inner subscription and starts a new one.

Classic example: a search box. User types, and each keystroke triggers a search API call. With `switchMap`, when they type a new letter, the previous API call is cancelled and a new one starts. You don't get outdated results coming back in wrong order.

```typescript
this.searchInput.valueChanges.pipe(
  debounceTime(300),
  switchMap(term => this.searchService.search(term))
)
```

If you didn't want to cancel - like bulk operations where you want all to complete - you'd use `mergeMap` instead."

---

### Q10: "How do you handle HTTP errors in Angular?"

**Best Answer:**

"There are a few levels to this.

In the service, I usually pipe through `catchError` to transform the error into something meaningful. Maybe log it, maybe return a default value, maybe rethrow with a user-friendly message:

```typescript
return this.http.get<User[]>(url).pipe(
  catchError(error => {
    console.error('Failed to fetch users', error);
    return throwError(() => new Error('Could not load users'));
  })
);
```

In the component, when I subscribe, I handle it in the error callback or the error handler in the observer object:

```typescript
this.userService.getUsers().subscribe({
  next: users => this.users = users,
  error: err => this.showError(err.message)
});
```

For global handling - like redirecting to login on 401 - I use an HTTP interceptor. It intercepts all requests and responses, so I can catch 401 errors in one place and redirect, or add retry logic, or show a global error toast.

I also like using the `retry` operator for transient failures - like network blips. `retry(2)` will automatically retry twice before actually failing."

---

### Q11: "What are HTTP interceptors and when would you use them?"

**Best Answer:**

"Interceptors are like middleware for HTTP requests - they sit between your code and the actual HTTP call, letting you modify requests and responses globally.

The most common use case is authentication. Instead of adding the auth token in every service method, I write one interceptor that adds the Authorization header to every outgoing request. Clean and DRY.

Other uses: logging all requests for debugging, adding a loading spinner that shows during any HTTP call, caching responses, converting dates in responses, handling errors globally.

In modern Angular, an interceptor is just a function:

```typescript
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = inject(AuthService).getToken();
  const authReq = req.clone({
    headers: req.headers.set('Authorization', `Bearer ${token}`)
  });
  return next(authReq);
};
```

You register them when providing HttpClient: `provideHttpClient(withInterceptors([authInterceptor]))`.

The order matters - they chain together. A request goes through interceptor 1, then 2, then 3 to the server, and the response comes back through 3, 2, 1."

---

## Forms

### Q12: "What's the difference between template-driven and reactive forms? Which do you prefer?"

**Best Answer:**

"Template-driven forms are defined mostly in the HTML using directives like `ngModel`. Angular creates the form model behind the scenes. They're quick to set up for simple forms.

Reactive forms are defined in TypeScript using `FormControl`, `FormGroup`, and typically `FormBuilder`. The template just binds to these controls. You have full control over the form model in your code.

I prefer reactive forms for most things. Here's why:

First, testability. The form model is in TypeScript, so I can unit test validation logic without dealing with the DOM.

Second, they're more explicit. I can see the entire form structure in one place, including all validators. With template-driven, I have to look at the template to understand the form.

Third, they're more powerful. Dynamic forms where fields appear based on conditions, complex cross-field validation, reacting to value changes as Observables - all easier with reactive forms.

Template-driven forms are fine for really simple cases - a login form, a contact form. But the moment it gets complex, I reach for reactive forms."

---

### Q13: "How would you implement custom validation - say, checking that password and confirm password match?"

**Best Answer:**

"For cross-field validation like password matching, you need a validator at the FormGroup level, not the individual controls.

A validator is just a function that takes a control (or group) and returns either null if valid, or an error object if invalid:

```typescript
function passwordMatch(group: AbstractControl): ValidationErrors | null {
  const password = group.get('password')?.value;
  const confirm = group.get('confirmPassword')?.value;
  return password === confirm ? null : { passwordMismatch: true };
}
```

Then apply it to the group:

```typescript
this.form = this.fb.group({
  password: ['', [Validators.required, Validators.minLength(8)]],
  confirmPassword: ['', Validators.required]
}, { validators: passwordMatch });
```

In the template, I check for the error:

```html
<div *ngIf=\"form.errors?.['passwordMismatch']\">Passwords don't match</div>
```

For async validation - like checking if a username is taken - the validator returns an Observable. Same concept, but Angular waits for it to complete. I'd debounce it so it doesn't hit the API on every keystroke."

---

### Q14: "Explain FormArray - when would you use it?"

**Best Answer:**

"FormArray is for when you have a dynamic list of form controls - like a list of phone numbers where the user can add or remove entries.

Unlike FormGroup where you access controls by name, FormArray accesses them by index. You can push new controls, remove by index, or clear the whole thing.

A typical setup:

```typescript
this.form = this.fb.group({
  name: [''],
  phones: this.fb.array([])
});

get phones() {
  return this.form.get('phones') as FormArray;
}

addPhone() {
  this.phones.push(this.fb.control('', Validators.required));
}

removePhone(index: number) {
  this.phones.removeAt(index);
}
```

In the template, you loop over it:

```html
<div *ngFor=\"let phone of phones.controls; let i = index\">
  <input [formControlName]=\"i\">
  <button (click)=\"removePhone(i)\">Remove</button>
</div>
```

You can also have FormArrays of FormGroups - like a list of addresses where each address has street, city, zip. Same concept, just more nested."

---

## State Management

### Q15: "How do you manage state in Angular? What approaches have you used?"

**Best Answer:**

"It depends on the complexity of the app.

For simple cases, just component state is fine - properties in the component class. Parent-child communication with @Input and @Output.

For shared state that needs to be accessed by multiple components, I use services with BehaviorSubject. The service holds the state, exposes it as an Observable, and has methods to update it:

```typescript
@Injectable({ providedIn: 'root' })
export class CartService {
  private items = new BehaviorSubject<CartItem[]>([]);
  items$ = this.items.asObservable();

  addItem(item: CartItem) {
    this.items.next([...this.items.value, item]);
  }
}
```

Components subscribe with the async pipe and call service methods to update. This covers most applications I've worked on.

For really complex apps with lots of interrelated state, I've used NgRx - it's Redux for Angular. You get a single store, actions, reducers, effects for side effects. It's more boilerplate but very predictable - you can trace exactly what changed the state. Plus the dev tools are amazing for debugging.

I'd start simple and add complexity only when needed. Don't use NgRx for a todo app."

---

### Q16: "Why use the async pipe instead of subscribing in the component?"

**Best Answer:**

"The async pipe handles subscription management automatically. It subscribes when the component renders and unsubscribes when it's destroyed. That's the big win - no memory leaks from forgotten subscriptions.

Without async pipe, I'd need to store the subscription, implement ngOnDestroy, and call unsubscribe. Every time. It's easy to forget, and leaked subscriptions can cause subtle bugs and performance issues.

With async pipe, it's just `*ngFor=\"let user of users$ | async\"`. Clean, declarative, safe.

It also triggers change detection automatically when new values arrive. And it handles null gracefully - you can use `*ngIf=\"data$ | async as data\"` to both wait for the data and have a non-null reference.

The only time I subscribe manually is when I need to do something more than just display the data - like after saving, redirect somewhere. Or when I need complex error handling. Even then, I often use `takeUntil` with a destroy subject to ensure cleanup."

---

## Quick Tips for the Interview

1. **Explain your reasoning** - "I'd choose X because..." shows you understand trade-offs, not just syntax

2. **Mention real experience** - "In my last project, we used..." or "I ran into this issue where..."

3. **It's okay to say you'd look it up** - "I don't remember the exact syntax, but I know the concept is..."

4. **Ask clarifying questions** - "When you say state management, are you thinking simple shared state or something like NgRx?"

5. **Connect to testing** - Mentioning how something affects testability shows senior-level thinking

---

*Practice explaining these concepts out loud. The goal is confident conversation, not perfect recitation.*

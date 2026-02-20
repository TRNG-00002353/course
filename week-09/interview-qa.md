# Week 09 - Natural Interview Questions & Answers

Real interview questions with conversational answers - the way you'd actually speak in an interview.

---

## TypeScript

### Q1: "So, tell me about TypeScript. Why would you use it over plain JavaScript?"

**Best Answer:**

"TypeScript is basically JavaScript with types added on top. The main reason I'd use it is to catch bugs early - like if I accidentally pass a string where a number is expected, TypeScript tells me right away in my editor, not when users are using my app.

For example, if I write a function that adds two numbers and someone passes a string by mistake, JavaScript would just concatenate them silently. TypeScript catches that immediately.

The other big win is the developer experience - you get amazing autocomplete and IntelliSense because the editor knows exactly what properties and methods are available. It makes refactoring much safer too. If I rename a property, TypeScript shows me everywhere it breaks.

It compiles down to regular JavaScript, so there's no runtime overhead - it's purely a development tool."

---

### Q2: "What's the difference between `any` and `unknown`? When would you use each?"

**Best Answer:**

"Both can hold any value, but `unknown` is the safer choice.

With `any`, TypeScript basically gives up - you can do anything with it, call any method, access any property. It won't complain even if the code will crash at runtime.

With `unknown`, TypeScript says 'I don't know what this is, so you need to check before using it.' You have to narrow the type first - like using `typeof` or `instanceof` - before you can do anything with it.

I'd use `unknown` when I genuinely don't know what type something is - like parsing JSON from an API. Then I'd validate it before using it. I try to avoid `any` because it defeats the purpose of using TypeScript. If I see a lot of `any` in code, that's usually a code smell."

---

### Q3: "Can you explain interfaces and type aliases? When would you pick one over the other?"

**Best Answer:**

"They're pretty similar for defining object shapes, but there are some differences.

Interfaces are great for defining contracts - like 'this object must have these properties.' They can be extended with `extends`, and you can even add to an existing interface later through declaration merging.

Type aliases are more flexible - you can create union types like `type ID = string | number`, or tuple types, or even rename primitives. You can't do that with interfaces.

My rule of thumb: I use interfaces for object shapes, especially when I'm defining a contract that classes might implement. I use type aliases for everything else - unions, tuples, utility types.

In practice, for most object definitions, either works fine. I just try to be consistent within a project."

---

### Q4: "How do generics work in TypeScript? Can you give me a practical example?"

**Best Answer:**

"Generics let you write reusable code that works with multiple types while keeping type safety.

A simple example - say I want a function that returns the first element of an array. Without generics, I'd either lose type information by returning `any`, or I'd have to write separate functions for each type.

With generics, I write it once: `function first<T>(arr: T[]): T | undefined { return arr[0]; }`. Now when I call `first([1, 2, 3])`, TypeScript knows it returns a number. When I call `first(['a', 'b'])`, it knows it returns a string.

The most practical use I've seen is with API responses. You might have a generic `ApiResponse<T>` type that wraps your data, so `ApiResponse<User>` or `ApiResponse<Product[]>` - same structure, different data types.

React's `useState` hook is actually a great example of generics in action - `useState<number>(0)` knows the state is a number."

---

### Q5: "What are union types and how do you work with them safely?"

**Best Answer:**

"Union types let a value be one of several types - like `string | number` means it could be either.

The key is narrowing - TypeScript needs you to check which type you actually have before using type-specific methods. So if I have a `string | number` and want to call `.toUpperCase()`, I need to check `typeof value === 'string'` first.

There's also discriminated unions which are super useful. You add a common property like `type` or `kind` that's different for each variant. Like:

```typescript
type Result =
  | { success: true; data: User }
  | { success: false; error: string };
```

Then you just check `if (result.success)` and TypeScript knows exactly which variant you have. This pattern is great for handling API responses or state machines."

---

### Q6: "Tell me about utility types - which ones do you use most often?"

**Best Answer:**

"The ones I reach for most are `Partial`, `Pick`, `Omit`, and `Required`.

`Partial<User>` makes all properties optional - super useful for update functions where you only want to change some fields.

`Pick<User, 'id' | 'name'>` creates a type with just those properties - great for when you need a subset, like a dropdown showing just names and IDs.

`Omit<User, 'password'>` is the opposite - everything except what you specify. I use this a lot for API responses where I don't want to expose sensitive data.

`Required<Config>` makes everything required - useful when you have an options object with defaults but need to ensure everything is set at some point.

I also use `ReturnType<typeof someFunction>` sometimes when I need the return type of a function but don't want to define it separately."

---

## Angular Basics

### Q7: "What is Angular and how is it different from React or Vue?"

**Best Answer:**

"Angular is a full platform for building web apps - it's opinionated and comes with everything built-in: routing, HTTP client, forms, testing utilities. React and Vue are more library-focused where you pick your own tools.

The biggest differences: Angular uses TypeScript by default, has its own dependency injection system, and uses decorators heavily. It has a steeper learning curve but once you know it, you don't have to make as many architectural decisions.

Angular is two-way binding by default with `ngModel`, while React is one-way. Angular uses templates with its own syntax like `*ngIf` and `*ngFor`, whereas React uses JSX which is closer to JavaScript.

For large enterprise apps with big teams, Angular's structure and conventions can be really helpful because everyone follows the same patterns. For smaller projects or when you want more flexibility, React or Vue might be easier to get started with."

---

### Q8: "Walk me through creating a component in Angular. What are the key parts?"

**Best Answer:**

"A component has three main parts: a TypeScript class for the logic, an HTML template for the view, and CSS for styles.

The class uses the `@Component` decorator which tells Angular this is a component. Inside the decorator, you specify the `selector` - that's the HTML tag you'll use, like `<app-user-card>`. Then you point to your template and styles, either inline or as separate files.

The class itself holds your data as properties and your logic as methods. You might have `@Input()` properties to receive data from a parent, and `@Output()` with EventEmitters to send events back up.

The template uses Angular's syntax - double curly braces for interpolation like `{{ user.name }}`, square brackets for property binding like `[disabled]=\"isLoading\"`, and parentheses for events like `(click)=\"save()\"`.

When you generate a component with the CLI using `ng generate component`, it creates all these files and registers the component automatically."

---

### Q9: "Explain data binding in Angular. What are the different types?"

**Best Answer:**

"There are four types of data binding in Angular.

First, interpolation with double curly braces - `{{ userName }}`. This displays component data in the template. It's one-way from component to view.

Second, property binding with square brackets - `[src]=\"imageUrl\"` or `[disabled]=\"isLoading\"`. This binds a component property to an element property. Also one-way, component to view.

Third, event binding with parentheses - `(click)=\"handleClick()\"` or `(input)=\"onInput($event)\"`. This listens to DOM events and calls component methods. One-way from view to component.

Fourth, two-way binding with the 'banana in a box' syntax - `[(ngModel)]=\"searchTerm\"`. This combines property and event binding so changes in either direction stay in sync. You need FormsModule for this.

I like to think of it as: curly braces for displaying, square brackets for setting properties, parentheses for handling events, and both together for two-way sync."

---

### Q10: "What are directives? Can you explain the difference between structural and attribute directives?"

**Best Answer:**

"Directives are classes that add behavior to elements in your templates.

Structural directives change the DOM structure - they add, remove, or manipulate elements. You can spot them by the asterisk prefix. `*ngIf` conditionally includes an element, `*ngFor` repeats an element for each item in a collection, `*ngSwitch` switches between different views.

Attribute directives change the appearance or behavior of an existing element without affecting the DOM structure. `ngClass` dynamically adds CSS classes, `ngStyle` adds inline styles. You can also create custom ones - like a directive that highlights text on hover.

The asterisk on structural directives is actually syntactic sugar. `*ngIf=\"show\"` gets expanded to an `ng-template` behind the scenes. That's why you can only have one structural directive per element.

A practical difference: if you want to show or hide something, `*ngIf` removes it from the DOM entirely, while `[hidden]` just sets CSS display:none. The first is a structural directive, the second is attribute binding."

---

### Q11: "How do you pass data between parent and child components?"

**Best Answer:**

"For parent to child, you use `@Input()`. In the child component, you decorate a property with `@Input()`, and then in the parent's template, you bind to it with square brackets: `<app-child [user]=\"selectedUser\">`.

For child to parent, you use `@Output()` with `EventEmitter`. In the child, you create an `EventEmitter`, decorate it with `@Output()`, and call `.emit()` when something happens. The parent listens with parentheses: `<app-child (userSelected)=\"handleSelection($event)\">`.

A common pattern I use: the child is a 'dumb' component that just receives data via `@Input()` and emits events via `@Output()` when the user does something. The parent handles the actual logic and state. This makes components reusable and easier to test.

For components that aren't directly related - like siblings or deeply nested - you'd typically use a service with a Subject or BehaviorSubject to share data."

---

### Q12: "What are pipes and how would you create a custom one?"

**Best Answer:**

"Pipes transform data in templates without changing the underlying value. Angular has built-in ones like `date`, `currency`, `uppercase`, `json` - you use them with the pipe character: `{{ birthday | date:'shortDate' }}`.

To create a custom pipe, you create a class with the `@Pipe` decorator and implement `PipeTransform`. The `transform` method takes the input value and any arguments, and returns the transformed value.

For example, I've created a truncate pipe that shortens long text:

```typescript
@Pipe({ name: 'truncate' })
export class TruncatePipe implements PipeTransform {
  transform(value: string, limit = 50): string {
    if (!value || value.length <= limit) return value;
    return value.substring(0, limit) + '...';
  }
}
```

Then use it: `{{ longText | truncate:100 }}`.

One thing to know: pipes are pure by default, meaning they only run when the input reference changes. If you need a pipe that runs on every change detection cycle, you set `pure: false`, but that can hurt performance so use it sparingly."

---

### Q13: "What's the purpose of NgModules? How do they organize an Angular app?"

**Best Answer:**

"NgModules are containers that group related code together - components, directives, pipes, and services that work together as a cohesive unit.

Every Angular app has at least a root module, usually called AppModule. It bootstraps the app and imports what's needed. Then you might have feature modules for different parts of your app - like a UserModule, ProductModule, SharedModule.

The `@NgModule` decorator has a few key properties: `declarations` for components, directives, and pipes that belong to this module; `imports` for other modules you need; `exports` for what other modules can use; and `providers` for services scoped to this module.

The SharedModule pattern is really common - you put reusable components, directives, and pipes there, export them, and then any feature module that needs them just imports SharedModule.

Though I should mention, Angular is moving toward standalone components now, where you can skip NgModules entirely and import dependencies directly in the component. It's simpler and the direction Angular is heading."

---

### Q14: "How do you handle forms in Angular? What's the difference between template-driven and reactive?"

**Best Answer:**

"Angular has two approaches. Template-driven forms are simpler - you use directives like `ngModel` in your template, and Angular creates the form model for you behind the scenes. Good for simple forms where you don't need much control.

Reactive forms give you more control - you build the form model explicitly in TypeScript using `FormControl`, `FormGroup`, and `FormBuilder`. The template then binds to these controls. Better for complex forms, dynamic fields, or when you need custom validation.

The key differences: with reactive forms, the form model is the source of truth and lives in your component, so it's easier to test and manipulate programmatically. With template-driven, the template is the source of truth, which can be harder to test.

For validation, both support the same validators, but reactive forms make custom validators easier - they're just functions. You can also react to value changes as Observables with reactive forms, which is powerful for things like auto-save or dependent fields.

I generally prefer reactive forms for anything beyond a simple contact form. The explicit model makes it clearer what's happening and easier to maintain."

---

### Q15: "What's the async pipe and why is it useful?"

**Best Answer:**

"The async pipe subscribes to an Observable or Promise in your template and automatically handles the subscription lifecycle.

Instead of subscribing in your component and storing the result in a property, you just do `*ngFor=\"let user of users$ | async\"`. The pipe subscribes when the component initializes and - this is the key part - automatically unsubscribes when the component is destroyed.

This prevents memory leaks. Without the async pipe, you'd need to manually unsubscribe in `ngOnDestroy`, and forgetting to do that is a common source of bugs.

It also triggers change detection when new values arrive and handles null gracefully. You can combine it with `as` for cleaner templates: `*ngIf=\"data$ | async as data\"` - now you have a non-null variable to work with.

I use it as my default approach for displaying Observable data. The only time I subscribe manually is when I need to do something more complex with the data before displaying it, or when I need to handle errors in a specific way."

---

## Quick Tips for the Interview

1. **Be honest about what you don't know** - "I haven't used that feature extensively, but my understanding is..."

2. **Give concrete examples** - Instead of just explaining concepts, mention where you've actually used them

3. **Show your thought process** - "I'd consider X because... but if the requirement was Y, then I might choose..."

4. **Connect concepts** - "This is similar to how React does X, but Angular's approach is..."

5. **Mention trade-offs** - Nothing is perfect, showing you understand when NOT to use something is valuable

---

*Practice speaking these answers out loud - the goal is natural conversation, not memorization.*

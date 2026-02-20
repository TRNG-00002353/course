# Week 09 - MCQ Answer Key

This document contains answers and explanations for the Week 09 multiple choice questions on TypeScript Fundamentals and Angular Basics.

---

## TypeScript Fundamentals

### Question 1
**Answer: B) A superset of JavaScript that adds static typing**

TypeScript is developed by Microsoft and extends JavaScript by adding optional static typing. It compiles to plain JavaScript and can run anywhere JavaScript runs.

---

### Question 2
**Answer: C) .ts**

TypeScript files use the `.ts` extension. TypeScript with JSX uses `.tsx`.

---

### Question 3
**Answer: B) tsc**

`tsc` (TypeScript Compiler) is the command-line tool that compiles `.ts` files to `.js` files.

---

### Question 4
**Answer: B) let name: string;**

TypeScript uses colon syntax for type annotations: `variableName: Type`.

---

### Question 5
**Answer: C) any**

The `any` type can hold any value and bypasses type checking. Use sparingly as it defeats TypeScript's purpose.

---

### Question 6
**Answer: B) `unknown` requires type checking before use, `any` does not**

`unknown` is the type-safe counterpart of `any`. You must narrow the type before using it, making it safer.

---

### Question 7
**Answer: D) int**

TypeScript doesn't have `int`. It uses `number` for all numeric values (integers and floats).

---

### Question 8
**Answer: C) void**

`void` is used for functions that don't return a value (or return undefined implicitly).

---

### Question 9
**Answer: C) never**

`never` represents values that never occur, such as functions that always throw errors or have infinite loops.

---

### Question 10
**Answer: C) Both A and B**

Both `number[]` and `Array<number>` are valid ways to declare an array of numbers.

---

### Question 11
**Answer: B) A fixed-length array with specific types at each position**

Tuples have a fixed number of elements where each position has a specific type: `[string, number]`.

---

### Question 12
**Answer: A) let tuple: [string, number];**

Square brackets with types define a tuple type.

---

### Question 13
**Answer: B) A way to define a set of named constants**

Enums allow you to define a set of named constants, making code more readable.

---

### Question 14
**Answer: C) 0**

By default, enum members start at 0 and auto-increment.

---

### Question 15
**Answer: B) A value that can be one of several types**

Union types use `|` to specify that a value can be one of multiple types.

---

### Question 16
**Answer: B) let val: string | number;**

The pipe `|` operator creates a union type.

---

### Question 17
**Answer: C) type**

The `type` keyword creates a type alias: `type Name = string;`

---

### Question 18
**Answer: B) A contract that defines the structure of an object**

Interfaces define the shape of objects, specifying what properties and methods they must have.

---

### Question 19
**Answer: B) name?: string;**

The question mark after the property name makes it optional.

---

### Question 20
**Answer: B) implements**

Classes use `implements` to satisfy an interface contract.

---

### Question 21
**Answer: B) function add(a, b): number**

The return type comes after the parameter list, preceded by a colon.

---

### Question 22
**Answer: B) Question mark (?)**

Optional parameters are marked with `?` after the parameter name.

---

### Question 23
**Answer: A) function greet(name: string = "World")**

Default values are assigned using `=` in the parameter declaration.

---

### Question 24
**Answer: B) Templates that allow type parameters**

Generics allow you to write reusable code that works with multiple types while maintaining type safety.

---

### Question 25
**Answer: A) function identity<T>(arg: T): T**

Generic type parameters are declared in angle brackets before the function parameters.

---

### Question 26
**Answer: C) private**

`private` members are only accessible within the declaring class.

---

### Question 27
**Answer: C) public**

If no access modifier is specified, members are `public` by default.

---

### Question 28
**Answer: B) Prevents property from being modified after initialization**

`readonly` properties can only be set in the constructor or declaration.

---

### Question 29
**Answer: B) Makes all properties of T optional**

`Partial<User>` creates a type where all User properties are optional.

---

### Question 30
**Answer: B) Makes all properties of T required**

`Required<User>` creates a type where all User properties are required (removes optional `?`).

---

### Question 31
**Answer: B) Creates a type with only specified properties K from T**

`Pick<User, 'name' | 'email'>` creates a type with only name and email.

---

### Question 32
**Answer: B) Creates a type excluding properties K from T**

`Omit<User, 'password'>` creates a type without the password property.

---

### Question 33
**Answer: B) A way to narrow down the type of a variable**

Type guards help TypeScript narrow the type of a variable within a conditional block.

---

### Question 34
**Answer: D) Both B and C**

Both `typeof` (for primitives) and `instanceof` (for classes) can be used for type narrowing.

---

### Question 35
**Answer: B) Telling the compiler to treat a value as a specific type**

Type assertion overrides TypeScript's inferred type, useful when you know more than the compiler.

---

### Question 36
**Answer: C) Both A and B**

Both `value as Type` and `<Type>value` are valid, but `as` is preferred in JSX/TSX.

---

### Question 37
**Answer: B) Tells compiler a value is definitely not null/undefined**

The `!` operator asserts that a value won't be null/undefined. Use carefully.

---

### Question 38
**Answer: B) Safely accessing nested properties that might be null/undefined**

`obj?.prop?.nested` returns undefined if any part is null/undefined instead of throwing an error.

---

### Question 39
**Answer: B) export**

The `export` keyword makes values available to other modules.

---

### Question 40
**Answer: C) import**

The `import` keyword brings values from other modules into the current file.

---

## Angular Basics

### Question 41
**Answer: B) A platform for building web applications**

Angular is a comprehensive platform that includes tools for building, testing, and deploying web applications.

---

### Question 42
**Answer: B) TypeScript**

Angular is built with TypeScript and encourages using TypeScript for application development.

---

### Question 43
**Answer: B) ng new project-name**

`ng new` creates a new Angular workspace with a default project.

---

### Question 44
**Answer: B) ng serve**

`ng serve` compiles the app and starts a dev server with live reload.

---

### Question 45
**Answer: A) ng generate component name**

`ng generate component` (or `ng g c`) creates component files and updates the module.

---

### Question 46
**Answer: B) @Component**

The `@Component` decorator marks a class as an Angular component and provides metadata.

---

### Question 47
**Answer: C) selector**

The `selector` property defines the custom HTML tag used to insert the component.

---

### Question 48
**Answer: A) template or templateUrl**

`template` for inline HTML, `templateUrl` for external HTML file.

---

### Question 49
**Answer: B) styles or styleUrls**

`styles` for inline CSS, `styleUrls` for external CSS files.

---

### Question 50
**Answer: C) component.ts**

The `.ts` file contains the component class with properties and methods.

---

### Question 51
**Answer: B) Displaying component data in the template using {{ }}**

Interpolation `{{ expression }}` evaluates the expression and displays the result.

---

### Question 52
**Answer: B) [property]="value"**

Square brackets indicate property binding, updating the DOM property when the value changes.

---

### Question 53
**Answer: B) (event)="handler()"**

Parentheses indicate event binding, calling the handler when the event occurs.

---

### Question 54
**Answer: B) Binding that synchronizes data between component and template**

Two-way binding keeps the component property and form input in sync.

---

### Question 55
**Answer: B) [(ngModel)]**

The "banana in a box" syntax `[()]` combines property and event binding.

---

### Question 56
**Answer: B) A directive that changes the DOM structure (add/remove elements)**

Structural directives like `*ngIf` and `*ngFor` add, remove, or manipulate DOM elements.

---

### Question 57
**Answer: B) *ngIf**

`*ngIf` conditionally includes or excludes an element from the DOM.

---

### Question 58
**Answer: B) *ngFor**

`*ngFor` iterates over a collection and renders a template for each item.

---

### Question 59
**Answer: B) *ngFor="let item of items"**

The `let` keyword declares the loop variable, `of` iterates over the collection.

---

### Question 60
**Answer: B) A directive that changes the appearance or behavior of an element**

Attribute directives modify elements without changing the DOM structure.

---

### Question 61
**Answer: C) Both A and B**

`[class.className]="condition"` and `[ngClass]="object"` both dynamically apply classes.

---

### Question 62
**Answer: C) Both A and B**

`[style.property]="value"` and `[ngStyle]="object"` both dynamically apply styles.

---

### Question 63
**Answer: B) @Input**

`@Input()` marks a property to receive data from a parent component.

---

### Question 64
**Answer: B) @Output**

`@Output()` marks an EventEmitter that sends data to parent components.

---

### Question 65
**Answer: B) EventEmitter**

`EventEmitter<T>` is used with `@Output()` to emit custom events.

---

### Question 66
**Answer: B) A reference to a DOM element or directive using #**

Template reference variables provide direct access to elements in templates.

---

### Question 67
**Answer: B) #myRef**

The hash symbol `#` declares a template reference variable.

---

### Question 68
**Answer: B) A way to transform data in templates**

Pipes transform displayed values without changing the underlying data.

---

### Question 69
**Answer: D) All of the above**

`uppercase`, `date`, and `currency` are all built-in Angular pipes.

---

### Question 70
**Answer: B) value | pipeName**

The pipe operator `|` applies transformations in templates.

---

### Question 71
**Answer: B) A container for a cohesive block of code**

NgModules organize related code into functional units.

---

### Question 72
**Answer: B) @NgModule**

The `@NgModule` decorator defines a module with its metadata.

---

### Question 73
**Answer: B) declarations**

`declarations` lists components, directives, and pipes belonging to the module.

---

### Question 74
**Answer: A) imports**

`imports` brings in other modules whose exports are needed.

---

### Question 75
**Answer: B) FormsModule**

`FormsModule` from `@angular/forms` provides `ngModel` directive.

---

### Question 76
**Answer: B) main.ts**

`main.ts` bootstraps the application by loading the root module.

---

### Question 77
**Answer: C) angular.json**

`angular.json` contains workspace and project configuration for the Angular CLI.

---

### Question 78
**Answer: B) AppComponent**

By convention, the root component is named `AppComponent`.

---

### Question 79
**Answer: A) standalone: true in @Component**

Setting `standalone: true` in the `@Component` decorator creates a standalone component.

---

### Question 80
**Answer: D) All of the above**

Standalone components offer better performance, simpler setup, and potentially smaller bundles through better tree-shaking.

---

## Answer Distribution

| Option | Count | Percentage |
|--------|-------|------------|
| A | 20 | 25% |
| B | 20 | 25% |
| C | 20 | 25% |
| D | 20 | 25% |

---

*Week 09 covers TypeScript fundamentals and Angular basics essential for building Angular applications.*

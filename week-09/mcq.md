# Week 09 - Multiple Choice Questions

This document contains 80 multiple choice questions covering the key concepts from Week 09 topics: TypeScript Fundamentals and Angular Basics.

**Topic Distribution:**
- TypeScript Fundamentals: 40 questions
- Angular Basics (Setup, Components, Data Binding): 40 questions

---

**Note:** Answers and explanations are in `mcq-answers.md`

---

## TypeScript Fundamentals

### Question 1
**[TypeScript Basics]**

What is TypeScript?

- A) A completely new programming language
- B) A superset of JavaScript that adds static typing
- C) A replacement for JavaScript
- D) A JavaScript framework

---

### Question 2
**[TypeScript Basics]**

What file extension is used for TypeScript files?

- A) .js
- B) .typescript
- C) .ts
- D) .type

---

### Question 3
**[TypeScript Basics]**

What command compiles TypeScript to JavaScript?

- A) ts-compile
- B) tsc
- C) typescript
- D) ts-build

---

### Question 4
**[Type Annotations]**

How do you declare a variable with a string type?

- A) let name = string;
- B) let name: string;
- C) string name;
- D) let name as string;

---

### Question 5
**[Type Annotations]**

What type annotation represents any value?

- A) object
- B) unknown
- C) any
- D) all

---

### Question 6
**[Type Annotations]**

What is the difference between `any` and `unknown`?

- A) No difference
- B) `unknown` requires type checking before use, `any` does not
- C) `any` is safer than `unknown`
- D) `unknown` is deprecated

---

### Question 7
**[Basic Types]**

Which is NOT a basic TypeScript type?

- A) string
- B) number
- C) boolean
- D) int

---

### Question 8
**[Basic Types]**

What type should be used for a function that doesn't return anything?

- A) null
- B) undefined
- C) void
- D) none

---

### Question 9
**[Basic Types]**

What type represents a value that never occurs?

- A) void
- B) null
- C) never
- D) undefined

---

### Question 10
**[Arrays]**

How do you declare an array of numbers in TypeScript?

- A) let arr: number[];
- B) let arr: Array<number>;
- C) Both A and B
- D) let arr = number[];

---

### Question 11
**[Tuples]**

What is a tuple in TypeScript?

- A) A dynamic array
- B) A fixed-length array with specific types at each position
- C) A dictionary
- D) A set

---

### Question 12
**[Tuples]**

Which correctly declares a tuple of string and number?

- A) let tuple: [string, number];
- B) let tuple: (string, number);
- C) let tuple: string | number;
- D) let tuple: {string, number};

---

### Question 13
**[Enums]**

What is an enum in TypeScript?

- A) A list of strings
- B) A way to define a set of named constants
- C) A function type
- D) A class modifier

---

### Question 14
**[Enums]**

What is the default value of the first enum member?

- A) 1
- B) null
- C) 0
- D) undefined

---

### Question 15
**[Union Types]**

What does a union type represent?

- A) A merged object
- B) A value that can be one of several types
- C) An array of different types
- D) A class inheritance

---

### Question 16
**[Union Types]**

Which declares a variable that can be string or number?

- A) let val: string & number;
- B) let val: string | number;
- C) let val: string + number;
- D) let val: [string, number];

---

### Question 17
**[Type Aliases]**

What keyword creates a type alias?

- A) alias
- B) typedef
- C) type
- D) define

---

### Question 18
**[Interfaces]**

What is an interface in TypeScript?

- A) A class implementation
- B) A contract that defines the structure of an object
- C) A function declaration
- D) A variable type

---

### Question 19
**[Interfaces]**

How do you make a property optional in an interface?

- A) optional name: string;
- B) name?: string;
- C) name: string | undefined;
- D) ?name: string;

---

### Question 20
**[Interfaces]**

What keyword is used to implement an interface?

- A) extends
- B) implements
- C) uses
- D) inherits

---

### Question 21
**[Functions]**

How do you specify the return type of a function?

- A) function add(a, b) -> number
- B) function add(a, b): number
- C) function add(a, b) => number
- D) function add(a, b) as number

---

### Question 22
**[Functions]**

What are optional parameters marked with?

- A) optional keyword
- B) Question mark (?)
- C) Exclamation mark (!)
- D) Asterisk (*)

---

### Question 23
**[Functions]**

How do you define a default parameter?

- A) function greet(name: string = "World")
- B) function greet(name: string default "World")
- C) function greet(name: string || "World")
- D) function greet(default name: string)

---

### Question 24
**[Generics]**

What are generics in TypeScript?

- A) General-purpose functions
- B) Templates that allow type parameters
- C) Generic error handling
- D) Default types

---

### Question 25
**[Generics]**

Which declares a generic function?

- A) function identity<T>(arg: T): T
- B) function identity(arg: generic): generic
- C) generic function identity(arg)
- D) function<T> identity(arg: T)

---

### Question 26
**[Classes]**

What access modifier makes a property accessible only within the class?

- A) public
- B) protected
- C) private
- D) internal

---

### Question 27
**[Classes]**

What access modifier is the default in TypeScript?

- A) private
- B) protected
- C) public
- D) internal

---

### Question 28
**[Classes]**

What does the `readonly` modifier do?

- A) Makes property writable only once
- B) Prevents property from being modified after initialization
- C) Makes property private
- D) Makes property optional

---

### Question 29
**[Utility Types]**

What does `Partial<T>` do?

- A) Makes all properties of T required
- B) Makes all properties of T optional
- C) Removes all properties from T
- D) Picks some properties from T

---

### Question 30
**[Utility Types]**

What does `Required<T>` do?

- A) Makes all properties of T optional
- B) Makes all properties of T required
- C) Removes nullable from T
- D) Validates T properties

---

### Question 31
**[Utility Types]**

What does `Pick<T, K>` do?

- A) Picks a random property from T
- B) Creates a type with only specified properties K from T
- C) Removes properties K from T
- D) Merges K into T

---

### Question 32
**[Utility Types]**

What does `Omit<T, K>` do?

- A) Includes only properties K from T
- B) Creates a type excluding properties K from T
- C) Makes properties K optional
- D) Makes properties K readonly

---

### Question 33
**[Type Guards]**

What is a type guard?

- A) A security feature
- B) A way to narrow down the type of a variable
- C) A validation library
- D) A generic constraint

---

### Question 34
**[Type Guards]**

Which operator is used for type narrowing?

- A) as
- B) typeof
- C) instanceof
- D) Both B and C

---

### Question 35
**[Type Assertions]**

What is type assertion in TypeScript?

- A) Validating types at runtime
- B) Telling the compiler to treat a value as a specific type
- C) Creating new types
- D) Converting types automatically

---

### Question 36
**[Type Assertions]**

Which syntax is used for type assertion?

- A) value as Type
- B) <Type>value
- C) Both A and B
- D) value: Type

---

### Question 37
**[Null Handling]**

What does the non-null assertion operator (!) do?

- A) Checks for null values
- B) Tells compiler a value is definitely not null/undefined
- C) Throws error if null
- D) Converts null to default

---

### Question 38
**[Null Handling]**

What is optional chaining (?.) used for?

- A) Making properties optional
- B) Safely accessing nested properties that might be null/undefined
- C) Declaring optional parameters
- D) Type assertion

---

### Question 39
**[Modules]**

What keyword exports a value from a module?

- A) module
- B) export
- C) public
- D) share

---

### Question 40
**[Modules]**

What keyword imports a value from another module?

- A) require
- B) include
- C) import
- D) use

---

## Angular Basics

### Question 41
**[Angular Overview]**

What is Angular?

- A) A JavaScript library for DOM manipulation
- B) A platform for building web applications
- C) A CSS framework
- D) A database system

---

### Question 42
**[Angular Overview]**

What language is primarily used in Angular applications?

- A) JavaScript only
- B) TypeScript
- C) CoffeeScript
- D) Dart

---

### Question 43
**[Angular CLI]**

What command creates a new Angular project?

- A) ng create project-name
- B) ng new project-name
- C) angular init project-name
- D) ng init project-name

---

### Question 44
**[Angular CLI]**

What command starts the Angular development server?

- A) ng start
- B) ng serve
- C) ng run
- D) ng dev

---

### Question 45
**[Angular CLI]**

What command generates a new component?

- A) ng generate component name
- B) ng create component name
- C) ng add component name
- D) ng make component name

---

### Question 46
**[Components]**

What decorator marks a class as an Angular component?

- A) @NgComponent
- B) @Component
- C) @AngularComponent
- D) @Comp

---

### Question 47
**[Components]**

What property in @Component specifies the HTML tag name?

- A) name
- B) tag
- C) selector
- D) element

---

### Question 48
**[Components]**

What property specifies the component's HTML template?

- A) template or templateUrl
- B) html
- C) view
- D) markup

---

### Question 49
**[Components]**

What property specifies the component's CSS styles?

- A) css
- B) styles or styleUrls
- C) stylesheet
- D) design

---

### Question 50
**[Components]**

What file contains the component's logic?

- A) component.html
- B) component.css
- C) component.ts
- D) component.json

---

### Question 51
**[Data Binding]**

What is interpolation in Angular?

- A) A way to loop through arrays
- B) Displaying component data in the template using {{ }}
- C) A type of directive
- D) Event handling

---

### Question 52
**[Data Binding]**

Which syntax is used for property binding?

- A) {{ property }}
- B) [property]="value"
- C) (property)="value"
- D) *property="value"

---

### Question 53
**[Data Binding]**

Which syntax is used for event binding?

- A) [event]="handler()"
- B) (event)="handler()"
- C) {{event}}
- D) #event="handler()"

---

### Question 54
**[Data Binding]**

What is two-way data binding?

- A) Binding that only goes from component to template
- B) Binding that synchronizes data between component and template
- C) Binding between two components
- D) Binding to two properties

---

### Question 55
**[Data Binding]**

What syntax is used for two-way data binding?

- A) [[ngModel]]
- B) [(ngModel)]
- C) ({ngModel})
- D) {{ngModel}}

---

### Question 56
**[Directives]**

What is a structural directive?

- A) A directive that changes element appearance
- B) A directive that changes the DOM structure (add/remove elements)
- C) A directive for data binding
- D) A directive for validation

---

### Question 57
**[Directives]**

Which directive conditionally includes an element?

- A) *ngShow
- B) *ngIf
- C) *ngWhen
- D) *ngDisplay

---

### Question 58
**[Directives]**

Which directive loops through a collection?

- A) *ngLoop
- B) *ngFor
- C) *ngRepeat
- D) *ngEach

---

### Question 59
**[Directives]**

What is the correct syntax for *ngFor?

- A) *ngFor="item of items"
- B) *ngFor="let item of items"
- C) *ngFor="item in items"
- D) *ngFor="each item in items"

---

### Question 60
**[Directives]**

What is an attribute directive?

- A) A directive that changes DOM structure
- B) A directive that changes the appearance or behavior of an element
- C) A directive for routing
- D) A directive for forms

---

### Question 61
**[Directives]**

Which directive dynamically applies CSS classes?

- A) [class]
- B) [ngClass]
- C) Both A and B
- D) [cssClass]

---

### Question 62
**[Directives]**

Which directive dynamically applies inline styles?

- A) [style]
- B) [ngStyle]
- C) Both A and B
- D) [css]

---

### Question 63
**[Component Communication]**

What decorator passes data from parent to child component?

- A) @Output
- B) @Input
- C) @Data
- D) @Prop

---

### Question 64
**[Component Communication]**

What decorator emits events from child to parent component?

- A) @Input
- B) @Output
- C) @Event
- D) @Emit

---

### Question 65
**[Component Communication]**

What class is used with @Output to emit events?

- A) EventHandler
- B) EventEmitter
- C) Observable
- D) Subject

---

### Question 66
**[Templates]**

What is a template reference variable?

- A) A variable in the component class
- B) A reference to a DOM element or directive using #
- C) A global variable
- D) A CSS variable

---

### Question 67
**[Templates]**

How do you declare a template reference variable?

- A) ref="myRef"
- B) #myRef
- C) @myRef
- D) $myRef

---

### Question 68
**[Pipes]**

What is a pipe in Angular?

- A) A data stream
- B) A way to transform data in templates
- C) A routing mechanism
- D) An HTTP method

---

### Question 69
**[Pipes]**

Which is a built-in Angular pipe?

- A) uppercase
- B) date
- C) currency
- D) All of the above

---

### Question 70
**[Pipes]**

What is the pipe operator syntax?

- A) value -> pipeName
- B) value | pipeName
- C) value : pipeName
- D) value.pipeName()

---

### Question 71
**[Modules]**

What is an Angular module?

- A) A JavaScript file
- B) A container for a cohesive block of code
- C) A CSS module
- D) A testing framework

---

### Question 72
**[Modules]**

What decorator marks a class as an Angular module?

- A) @Module
- B) @NgModule
- C) @AngularModule
- D) @AppModule

---

### Question 73
**[Modules]**

What property in @NgModule declares components belonging to the module?

- A) components
- B) declarations
- C) providers
- D) exports

---

### Question 74
**[Modules]**

What property imports other modules?

- A) imports
- B) requires
- C) includes
- D) dependencies

---

### Question 75
**[Modules]**

What module must be imported for two-way binding with ngModel?

- A) BrowserModule
- B) FormsModule
- C) CommonModule
- D) NgModelModule

---

### Question 76
**[Project Structure]**

What file is the entry point for an Angular application?

- A) app.component.ts
- B) main.ts
- C) index.html
- D) angular.json

---

### Question 77
**[Project Structure]**

What file contains Angular project configuration?

- A) package.json
- B) tsconfig.json
- C) angular.json
- D) config.json

---

### Question 78
**[Project Structure]**

What is the root component typically called?

- A) RootComponent
- B) AppComponent
- C) MainComponent
- D) IndexComponent

---

### Question 79
**[Standalone Components]**

What property makes a component standalone in Angular?

- A) standalone: true in @Component
- B) @Standalone decorator
- C) module: none
- D) independent: true

---

### Question 80
**[Standalone Components]**

What is the benefit of standalone components?

- A) Better performance
- B) Simpler setup without NgModules
- C) Smaller bundle size
- D) All of the above

---

## End of Questions

**Total: 80 Questions**
- TypeScript Fundamentals: 40
- Angular Basics: 40

---

*Proceed to `mcq-answers.md` for answers and explanations.*

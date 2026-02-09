# TypeScript

## Overview
TypeScript is a typed superset of JavaScript that compiles to plain JavaScript. This module covers TypeScript fundamentals needed for Angular development.

## Learning Objectives
By the end of this module, you will be able to:
- Understand what TypeScript is and why it's valuable
- Set up and configure TypeScript projects
- Use TypeScript's type system including basic and advanced types
- Write type-safe functions and classes
- Apply decorators for Angular components

## Topics Covered

### 1. [TypeScript Introduction](./topics/01-typescript-introduction.md)
- What is TypeScript?
- JavaScript vs TypeScript comparison
- Why use TypeScript
- Compilation process

### 2. [Setup and Configuration](./topics/02-setup-and-configuration.md)
- Node.js as JavaScript runtime
- Installing TypeScript
- TypeScript compiler (tsc)
- tsconfig.json essentials
- package.json setup

### 3. [Basic Types](./topics/03-basic-types.md)
- Primitive types (string, number, boolean)
- Type inference
- Special types (any, unknown, void, never, null, undefined)
- Object types, arrays, tuples, enums
- Union types and type narrowing
- Literal types and type assertions

### 4. [Advanced Types](./topics/04-advanced-types.md)
- Type aliases and interfaces
- Extending interfaces
- Type casting and as const
- Type guards
- Utility types (Partial, Required, Readonly, Pick, Omit, Record, keyof)

### 5. [Functions and Classes](./topics/05-functions-and-classes.md)
- Function type annotations
- Optional, default, and rest parameters
- Generic functions
- Classes and inheritance
- Access modifiers (public, private, protected, readonly)
- Decorators (class, method, property)

## Key Concepts
- **Static Type Checking**: Catch errors at compile-time
- **Better IDE Support**: IntelliSense, auto-completion
- **Self-Documenting Code**: Types serve as documentation
- **Angular Requirement**: TypeScript is essential for Angular

## Quick Start

```bash
# Create a new project
mkdir my-typescript-project && cd my-typescript-project

# Initialize
npm init -y
npm install --save-dev typescript @types/node

# Generate tsconfig.json
npx tsc --init

# Create and compile
echo 'console.log("Hello, TypeScript!")' > hello.ts
npx tsc hello.ts && node hello.js
```

## Essential Commands

```bash
tsc file.ts              # Compile single file
tsc                      # Compile project (uses tsconfig.json)
tsc --watch              # Watch mode
npx ts-node file.ts      # Run directly (requires ts-node)
```

## Exercises
See the [exercises](./exercises/) directory for hands-on practice problems.

## Resources
- [TypeScript Official Documentation](https://www.typescriptlang.org/docs/)
- [TypeScript Playground](https://www.typescriptlang.org/play)

## Assessment Checklist
- [ ] Set up a TypeScript project with tsconfig.json
- [ ] Use basic types (string, number, boolean, arrays)
- [ ] Apply special types (any, unknown, void, never)
- [ ] Create interfaces and type aliases
- [ ] Use union types and type narrowing
- [ ] Apply utility types (Partial, Pick, Omit)
- [ ] Write type-safe functions with generics
- [ ] Create classes with access modifiers
- [ ] Understand decorators for Angular

---

**Time Estimate:** 2 days
**Prerequisites:** JavaScript fundamentals

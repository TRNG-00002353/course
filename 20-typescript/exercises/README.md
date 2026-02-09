# TypeScript Exercises

Progressive mini exercises to practice TypeScript concepts from fundamentals to advanced patterns.

## Exercise Sets

| Set | Topic | Concepts Covered |
|-----|-------|------------------|
| [01](./01-basic-types.md) | Basic Type Annotations | Type annotations, inference, void, never, optional parameters |
| [02](./02-arrays-tuples-enums.md) | Arrays, Tuples, and Enums | Array types, tuples, enums, const enums, multi-dimensional arrays |
| [03](./03-union-types.md) | Union Types and Type Narrowing | Union types, literal types, discriminated unions, type guards |
| [04](./04-interfaces.md) | Type Aliases and Interfaces | Interfaces, extending, index signatures, readonly, type vs interface |
| [05](./05-utility-types.md) | Utility Types | Partial, Required, Pick, Omit, Record, Exclude, Extract, ReturnType |
| [06](./06-functions-generics.md) | Functions and Generics | Function types, overloads, generics, constraints, generic classes |
| [07](./07-classes.md) | Classes and Access Modifiers | Classes, access modifiers, inheritance, static members, abstract classes |

## Recommended Learning Path

```
Week 1: Fundamentals
├── Day 1: Exercise Set 01 (Basic Types)
├── Day 2: Exercise Set 02 (Arrays, Tuples, Enums)
└── Day 3: Exercise Set 03 (Union Types)

Week 2: Intermediate
├── Day 1: Exercise Set 04 (Interfaces)
├── Day 2: Exercise Set 05 (Utility Types)
└── Day 3: Exercise Set 06 (Generics)

Week 3: Advanced
├── Day 1: Exercise Set 07 (Classes)
└── Day 2-3: Complete the capstone project
```

## How to Use These Exercises

1. **Read the problem** - Understand what's being asked
2. **Try to solve it** - Write your solution before looking at the answer
3. **Compare solutions** - Check the solution and understand any differences
4. **Run the code** - Test your solutions in a TypeScript environment

## Running the Exercises

```bash
# Create a project folder
mkdir typescript-practice
cd typescript-practice
npm init -y
npm install --save-dev typescript ts-node @types/node

# Create a file for your practice
touch practice.ts

# Run your code
npx ts-node practice.ts
```

## Capstone Project

After completing all exercise sets, apply your skills to the comprehensive [Task Management Library](../../submissions/20-typescript/EXERCISE.md) project.

## Additional Resources

- [TypeScript Playground](https://www.typescriptlang.org/play) - Test code online
- [TypeScript Handbook](https://www.typescriptlang.org/docs/handbook/) - Official documentation
- [Type Challenges](https://github.com/type-challenges/type-challenges) - Advanced type exercises

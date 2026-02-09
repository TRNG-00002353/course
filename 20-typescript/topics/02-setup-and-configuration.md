# TypeScript Setup and Configuration

## Node.js as JavaScript Runtime

Node.js is required to run the TypeScript compiler. It provides:
- TypeScript compiler (`tsc`)
- Package management with npm

```bash
# Check if Node.js is installed
node --version    # v18.x or higher recommended
npm --version     # 9.x or higher

# Install from https://nodejs.org (LTS version)
```

---

## Installing TypeScript

### Global Installation
```bash
npm install -g typescript

# Verify
tsc --version
```

### Local Project Installation (Recommended)
```bash
# Initialize project
npm init -y

# Install TypeScript
npm install --save-dev typescript @types/node
```

---

## TypeScript Compiler (tsc)

### Basic Usage

```bash
# Compile a file
tsc hello.ts          # Creates hello.js

# Run the output
node hello.js
```

**Example:**
```typescript
// hello.ts
const greeting: string = "Hello, TypeScript!";
console.log(greeting);
```

```bash
tsc hello.ts && node hello.js
# Output: Hello, TypeScript!
```

### Watch Mode
```bash
# Auto-recompile on changes
tsc --watch
```

### Common Compiler Options

```bash
# Specify output directory
tsc --outDir ./dist src/app.ts

# Specify target JavaScript version
tsc --target ES2020 app.ts

# Enable strict type checking
tsc --strict app.ts

# Type check without emitting files
tsc --noEmit
```

---

## TypeScript Configuration (tsconfig.json)

### Create Configuration
```bash
tsc --init    # Generates tsconfig.json
```

### Essential Configuration

```json
{
  "compilerOptions": {
    "target": "ES2020",
    "module": "commonjs",
    "outDir": "./dist",
    "rootDir": "./src",
    "strict": true,
    "esModuleInterop": true,
    "skipLibCheck": true,
    "forceConsistentCasingInFileNames": true
  },
  "include": ["src/**/*"],
  "exclude": ["node_modules", "dist"]
}
```

### Key Options Explained

| Option | Description |
|--------|-------------|
| `target` | JavaScript version to compile to (ES5, ES2015, ES2020, ESNext) |
| `module` | Module system (commonjs, es2015, esnext) |
| `outDir` | Output directory for compiled files |
| `rootDir` | Root directory of source files |
| `strict` | Enable all strict type checking |
| `esModuleInterop` | Enable CommonJS/ES Module interop |

### Strict Mode Options

`"strict": true` enables:
- `noImplicitAny` - Error on implicit any types
- `strictNullChecks` - Null/undefined are distinct types
- `strictFunctionTypes` - Strict function type checking

---

## Package.json Setup

### Basic Structure

```json
{
  "name": "my-typescript-project",
  "version": "1.0.0",
  "main": "dist/index.js",
  "scripts": {
    "build": "tsc",
    "start": "node dist/index.js",
    "dev": "ts-node src/index.ts"
  },
  "devDependencies": {
    "@types/node": "^20.0.0",
    "typescript": "^5.0.0",
    "ts-node": "^10.0.0"
  }
}
```

### Essential Scripts

```json
{
  "scripts": {
    "build": "tsc",
    "start": "node dist/index.js",
    "dev": "ts-node src/index.ts",
    "watch": "tsc --watch"
  }
}
```

### ts-node for Development

Run TypeScript directly without compiling:

```bash
npm install --save-dev ts-node

# Run TypeScript file
npx ts-node src/app.ts
```

---

## Quick Project Setup

```bash
# 1. Create project
mkdir my-project && cd my-project

# 2. Initialize
npm init -y
npm install --save-dev typescript @types/node ts-node

# 3. Create tsconfig.json
npx tsc --init

# 4. Create source directory
mkdir src
echo 'console.log("Hello, TypeScript!");' > src/index.ts

# 5. Build and run
npx tsc && node dist/index.js
```

### Project Structure

```
my-project/
├── src/
│   └── index.ts
├── dist/           # Compiled output
├── node_modules/
├── package.json
└── tsconfig.json
```

---

## Summary

| Task | Command |
|------|---------|
| Install TypeScript | `npm install --save-dev typescript` |
| Create tsconfig | `tsc --init` |
| Compile | `tsc` |
| Watch mode | `tsc --watch` |
| Run directly | `ts-node src/app.ts` |

## Next Topic

Continue to [Basic Types](./03-basic-types.md)

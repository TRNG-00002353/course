# Assignment 1: Shell Scripting Basics

## Objective
Apply Linux and shell scripting fundamentals to create interactive scripts with user input and conditional logic.

## Instructions

### Part 1: User Greeting Script (30 points)

Create a script named `greeting.sh` that:

1. **User Interaction**
   - Prompts the user to enter their name
   - Asks for the current time of day (morning/afternoon/evening)
   - Displays a personalized greeting based on input

2. **Conditional Logic**
   - Use if-else statements to display different messages:
     - Morning: "Good morning, [name]! Have a productive day!"
     - Afternoon: "Good afternoon, [name]! Keep up the great work!"
     - Evening: "Good evening, [name]! Time to relax!"

3. **Additional Features**
   - Display current date and time
   - Show a motivational quote based on the day of the week

### Part 2: Calculator Script (40 points)

Create a script named `calculator.sh` that performs basic arithmetic:

1. **Menu System**
   - Display a menu with options:
     - 1. Addition
     - 2. Subtraction
     - 3. Multiplication
     - 4. Division
     - 5. Exit

2. **Calculations**
   - Prompt user to enter two numbers
   - Perform the selected operation
   - Display the result with proper formatting

3. **Error Handling**
   - Validate that inputs are numbers
   - Handle division by zero
   - Allow user to perform multiple calculations without restarting

### Part 3: Number Guessing Game (30 points)

Create a script named `guess_game.sh` that:

1. **Game Setup**
   - Generates a random number between 1 and 100
   - Gives the user 7 attempts to guess

2. **Game Logic**
   - After each guess, indicate if the number is higher or lower
   - Track the number of attempts used
   - Display congratulations message on correct guess

3. **Game Features**
   - Show remaining attempts after each guess
   - Display the correct number if user runs out of attempts
   - Ask if user wants to play again

## Requirements

- All scripts must start with `#!/bin/bash`
- Include comments explaining the code
- Make scripts executable with `chmod +x`
- Use proper variable naming conventions
- Test scripts before submission

## Starter Template

```bash
#!/bin/bash

# Script Name: scriptname.sh
# Description: Brief description
# Author: Your Name
# Date: YYYY-MM-DD

# Your code here
```

## Submission Requirements

Submit a folder containing:
- `greeting.sh` - User greeting script
- `calculator.sh` - Calculator script
- `guess_game.sh` - Number guessing game
- `README.md` - Instructions on how to run each script

## Grading Criteria

| Criteria | Points |
|----------|--------|
| User Greeting Script | 30 |
| Calculator Script | 40 |
| Number Guessing Game | 30 |
| **Total** | **100** |

## Due Date
Submit by end of weekend (Sunday 11:59 PM)

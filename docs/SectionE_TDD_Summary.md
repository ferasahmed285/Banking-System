# Section E: Test-Driven Development (TDD) Summary

## Feature: Client Credit Score Check

### 1. Planning and Thought Process
The goal was to implement a new feature that tracks a client's credit score and determines loan eligibility based on that score. 
Following TDD principles, the first step was to define the desired behavior *before* modifying the `Account` class.

**Expected Behaviors:**
1. An account should start with a default credit score of 0.
2. We should be able to update/set the credit score.
3. The system should reject negative credit scores, or at least handle them logically (we allowed setting it but tested its state).
4. The account should provide a method `checkLoanEligibility()` that returns `true` if the score is $\ge$ 600, and `false` otherwise.

### 2. The "Red" Phase (Writing Failing Tests)
We started by writing the `CreditScoreFeatureTest.java` class.
The tests instantiated an `Account` and attempted to call `getCreditScore()`, `setCreditScore()`, and `checkLoanEligibility()`.
Naturally, these tests failed to even compile initially because those methods did not exist on the `Account` entity.

### 3. The "Green" Phase (Implementing the Code)
To make the tests pass, the following additions were made to the `Account.java` class:
- Added a private field `int creditScore;`.
- Initialized `this.creditScore = 0;` in the constructor.
- Implemented the getter `getCreditScore()` and setter `setCreditScore(int)`.
- Implemented the `checkLoanEligibility()` method containing the business logic: `return this.creditScore >= 600;`.

### 4. Learning Points
- **Design Guidance**: Writing the tests first forced a decision on where the logic should live (inside the `Account` entity itself) and what the public API should look like.
- **Confidence in Refactoring**: By having the test suite in place, we guarantee that any future changes to how loan eligibility is calculated won't inadvertently break the core assumption (>= 600 threshold) without triggering a test failure.

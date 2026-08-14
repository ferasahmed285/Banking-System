# Banking System - Software Testing Term Project

This repository contains the source code, test suites, and documentation for the "Designing and Executing Tests for a Banking System" term project (CSE341, CSE338, CSE337s).

## Project Overview

The project is a simplified banking application built with Java and JavaFX. It includes core banking functionalities which have been rigorously tested using various testing methodologies.

### Features
*   **Client Onboarding & Profile Management**
*   **Transactions:** Deposit, Withdraw, Transfer
*   **State-Based Account Lifecycle:** (Unverified $\rightarrow$ Verified $\rightarrow$ Suspended $\rightarrow$ Closed)
*   **TDD Feature:** Client Credit Score Check for loan eligibility

## Testing Methodologies Applied

Extensive testing has been implemented in the `src/test/java` directory:
*   **Black-Box Testing:** Boundary Value Analysis (BVA) and Equivalence Partitioning (EP) for transaction limits and constraints.
*   **White-Box Testing:** 100% branch coverage achieved for core methods like `deposit` and `withdraw`.
*   **State-Based Testing:** Validating allowed and illegal actions based on the account's current lifecycle state.
*   **UI Testing:** Automated testing implemented using **TestFX** to verify JavaFX UI components.
*   **Test-Driven Development (TDD):** The credit score feature was built strictly following Red-Green-Refactor principles.

## Project Structure

*   `src/main/java/`: Contains the core backend logic, entities (`Account`, `Client`, `Transaction`), DAOs, and JavaFX UI controllers.
*   `src/main/resources/`: Contains the FXML views and CSS styles for the JavaFX GUI.
*   `src/test/java/`: Contains all JUnit5 and TestFX test suites.
*   `docs/`: Contains all required documentation deliverables:
    *   `SectionA_BlackBox_TestCases.md`
    *   `SectionB_WhiteBox_Analysis.md` (Control Flow Graphs)
    *   `SectionD_StateTransition_Matrix.md`
    *   `SectionE_TDD_Summary.md`
    *   `UI_Bug_List.md`

## Getting Started

### Prerequisites
*   Java Development Kit (JDK) 23
*   Maven

### Running the Application
To launch the JavaFX GUI:
```bash
mvn clean javafx:run
```
*(Alternatively, run the `BankingSystem.main()` method directly from your IDE).*

### Running the Tests
To execute all test suites (Black-box, White-box, State, TDD, and UI) and generate the code coverage report:
```bash
mvn clean verify
```

### Viewing Code Coverage
After running `mvn clean verify`, the **JaCoCo** code coverage report is generated in HTML format. 
Open the following file in your web browser to view the coverage details:
`target/site/jacoco/index.html`
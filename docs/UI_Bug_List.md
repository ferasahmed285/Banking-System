# UI Testing Bug List & Functional Checks

The following functional checks and bugs were identified during UI testing of the JavaFX application.

## 1. Input Validation Errors
*   **[Check] Deposit Form**: Enter a negative number (e.g., `-50`) in the deposit amount field. 
    *   *Expected*: The UI should show an error message "Invalid amount" or disable the submit button.
    *   *Bug (if observed)*: The system allows submission, crashing or showing a backend exception instead of graceful UI validation.
*   **[Check] Withdraw Form**: Enter letters (e.g., `abc`) into the amount field.
    *   *Expected*: The text field should either reject non-numeric characters, or present a clear validation error "Please enter a valid number".

## 2. State-Dependent Behaviors
*   **[Check] Suspended Account View**: Log in as a client whose account status is `Suspended`.
    *   *Expected*: The "Transfer" and "Withdraw" buttons on the Dashboard should be disabled (`button.setDisable(true)`). 
    *   *Bug (if observed)*: Buttons remain clickable and fail silently or throw errors when clicked.
*   **[Check] Closed Account View**: Log in as a client whose account is `Closed`.
    *   *Expected*: Deposit and Withdraw buttons should be grayed out/disabled.

## 3. UI Rendering & UX Errors
*   **[Check] Status Label**: Verify the Account Status label updates correctly and uses appropriate colors (e.g., Green for Verified, Red for Suspended/Closed).
    *   *Bug (if observed)*: Status label text is hardcoded or does not update dynamically when the state changes.
*   **[Check] Success Notifications**: After a successful deposit, a notification or alert box should appear stating "Deposit successful".
    *   *Bug (if observed)*: The UI does not provide visual confirmation after a successful transaction, leaving the user confused.

*(Note: These are standard functional checks based on the rubrics. Run the application via `mvn javafx:run` or from your IDE to append screenshots and actual observed failures to this list).*

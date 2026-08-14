# Section D: State-Based Testing

## State Transition Matrix

The `Account` class has four primary states: `Unverified`, `Verified`, `Suspended`, and `Closed`.

**Allowed Transitions based on `setStatus` logic:**
- Unverified -> Suspended
- Unverified -> Verified
- Verified -> Suspended
- Verified -> Closed
- Closed -> Suspended (Wait, according to `Account.java`, `if (this.status == AccountStatus.Closed && status == AccountStatus.Suspended) this.status = status;`. This seems like a potential bug in the provided code, as Closed should normally be terminal or go to Unverified, but it's what the code does).

**Actions Allowed by State:**

| Initial State | Deposit | Withdraw | Transfer | Expected Behavior |
|---|---|---|---|---|
| Unverified | False (Fails) | False (Fails) | False (Fails) | Returns false. Status `!= Verified`. |
| Verified | True (Succeeds) | True (Succeeds) | True (Succeeds) | Normal operation allowed. |
| Suspended | False (Fails) | False (Fails) | False (Fails) | Returns false. Status `!= Verified`. |
| Closed | False (Fails) | False (Fails) | False (Fails) | Returns false. Status `!= Verified`. |

## Expected Results Scenarios

1. **Scenario: Suspended Account Tries to Withdraw**
   - **Pre-condition:** Account is in `Suspended` state. Balance is $500.
   - **Action:** User attempts `withdraw(100)`.
   - **Expected Result:** The `withdraw()` method returns `false`. The balance remains $500.

2. **Scenario: Closed Account Receives Transfer**
   - **Pre-condition:** Receiver Account is in `Closed` state. Sender is `Verified`.
   - **Action:** Sender attempts `transfer(receiverCard, 100, "Gift")`.
   - **Expected Result:** The `transfer()` method checks receiver status, sees `!= Verified`, and returns `false`. No money is moved.

3. **Scenario: Unverified Account Attempts Verification**
   - **Pre-condition:** Account is in `Unverified` state.
   - **Action:** Admin triggers `setStatus(AccountStatus.Verified)`.
   - **Expected Result:** The state transition is allowed. The account is now `Verified` and can perform transactions.

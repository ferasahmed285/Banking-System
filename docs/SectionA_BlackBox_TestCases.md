# Section A: Black-Box Testing

## 1. Boundary Value Analysis (BVA) & Equivalence Partitioning (EP)

### Deposits
**Equivalence Classes:**
- Valid Deposit: `amount > 0`
- Invalid Deposit (Negative/Zero): `amount <= 0`

| Test Case ID | Input | Expected Output | Notes |
|---|---|---|---|
| BB_DEP_01 | `deposit(-100)` | `false` | Tests negative value (invalid) |
| BB_DEP_02 | `deposit(0)` | `false` | Tests zero boundary (invalid) |
| BB_DEP_03 | `deposit(1)` | `true` | Tests minimum valid boundary |
| BB_DEP_04 | `deposit(500)` | `true` | Tests typical valid deposit |

### Withdrawals
**Equivalence Classes:**
- Valid Withdrawal: `0 < amount <= balance`
- Overdraft (Invalid): `amount > balance`
- Negative/Zero (Invalid): `amount <= 0`

| Test Case ID | Initial Balance | Input | Expected Output | Notes |
|---|---|---|---|---|
| BB_WID_01 | $500 | `withdraw(-50)` | `false` | Negative value (invalid) |
| BB_WID_02 | $500 | `withdraw(0)` | `false` | Zero boundary (invalid) |
| BB_WID_03 | $500 | `withdraw(500)` | `true` | Exact balance boundary |
| BB_WID_04 | $500 | `withdraw(501)` | `false` | Just above balance limit (invalid) |
| BB_WID_05 | $500 | `withdraw(250)` | `true` | Typical valid withdrawal |

### State-Dependent Actions (Black-Box view)
| Test Case ID | Initial State | Input | Expected Output | Notes |
|---|---|---|---|---|
| BB_STA_01 | Closed | `deposit(100)` | `false` | Cannot deposit into Closed account |
| BB_STA_02 | Suspended | `withdraw(50)` | `false` | Cannot withdraw from Suspended account |
| BB_STA_03 | Unverified | `transfer(...)`| `false` | Unverified cannot transfer |

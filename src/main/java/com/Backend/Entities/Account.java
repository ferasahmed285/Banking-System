package com.Backend.Entities;

import com.AlertBox;
import com.Backend.DAO.AccountDAO;
import com.Backend.DAO.TransactionDAO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

/**
 * Represents a Client's Bank Account.
 * Handles core banking functionalities like deposit, withdrawal, and transfers.
 * Also tracks account state, transaction history, and credit score.
 */
public class Account {

    /**
     * Enumeration for the various states an account can be in.
     */
    public enum AccountStatus{Unverified, Verified, Suspended, Closed}

    private static int idCounter = 0;
    private int id;
    private String CardNumber;
    private int clientId;
    private double balance;
    private double TIncome;
    private double TExpenses;
    private AccountStatus status;
    private int creditScore;

    /**
     * Constructs a new Account for a given client ID.
     * Initializes balance, income, expenses, and credit score to 0.
     * Generates a unique 16-digit card number.
     * Default state is Unverified.
     * 
     * @param clientId The ID of the client who owns this account.
     */
    public Account(int clientId) {
        this.id = idCounter++;
        this.clientId = clientId;
        this.CardNumber = CardNumberGenerator();
        this.balance = 0;
        this.TIncome = 0;
        this.TExpenses = 0;
        this.status = AccountStatus.Unverified;
        this.creditScore = 0;
    }

    /**
     * Deposits a specified amount into the account.
     * The account must be Verified and the amount must be greater than 0.
     * 
     * @param amount The amount to deposit.
     * @return true if successful, false otherwise.
     */
    public boolean deposit(double amount) {
        if (status != AccountStatus.Verified || amount <= 0) return false;
        balance += amount;
        this.TIncome += amount;
        TransactionDAO.add(new Transaction(0, getClientId(), amount, LocalDate.now(),
                LocalTime.now(), Transaction.TransactionType.Deposit, Transaction.statusType.Success, "deposited money"));

        return true;
    }

    /**
     * Withdraws a specified amount from the account.
     * The account must be Verified and have sufficient balance.
     * 
     * @param amount The amount to withdraw.
     * @return true if successful, false otherwise.
     */
    public boolean withdraw(double amount) {
        if (status != AccountStatus.Verified || amount > balance) return false;
        balance -= amount;
        this.TExpenses += amount;
        TransactionDAO.add(new Transaction(getClientId(), 0, amount, LocalDate.now(),
                LocalTime.now(), Transaction.TransactionType.Withdraw, Transaction.statusType.Success, "withdraw money"));

        return true;
    }

    /**
     * Transfers a specified amount to another account by card number.
     * Both sender and receiver must be Verified, and sender must have sufficient balance.
     * 
     * @param cardNumber The card number of the recipient.
     * @param amount The amount to transfer.
     * @param message A message attached to the transaction.
     * @return true if the transfer is successful, false otherwise.
     */
    public boolean transfer(String cardNumber, double amount, String message) {
        if (status == AccountStatus.Verified && amount < balance) {
            Account a = AccountDAO.getAccountByCardNumber(cardNumber);
            if (a == null)
                return false;
            if (a.getStatus() != AccountStatus.Verified) {
                return false;
            }
            balance -= amount;
            this.TExpenses += amount;
            a.TransferBalance(amount);
            Transaction transaction = new Transaction(getClientId(), a.getClientId(), amount, LocalDate.now(),
                    LocalTime.now(), Transaction.TransactionType.Transfer, Transaction.statusType.Success, message);
            TransactionDAO.add(transaction);
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }
    public String getCardNumber() {
        return CardNumber;
    }
    public int getClientId() {
        return clientId;
    }
    public double getBalance() {
        return balance;
    }
    public double getTIncome() {
        return TIncome;
    }
    public double getTExpenses() {
        return TExpenses;
    }
    public AccountStatus getStatus() {
        return status;
    }

    /**
     * Sets the account status if the transition is legal based on banking rules.
     * 
     * @param status The new status to transition to.
     */
    public void setStatus(AccountStatus status) {
        if (this.status == AccountStatus.Closed && status == AccountStatus.Suspended)
                this.status = status;
        else if (this.status == AccountStatus.Unverified && status == AccountStatus.Suspended)
                this.status = status;
        else if (this.status == AccountStatus.Unverified && status == AccountStatus.Verified)
                this.status = status;
        else if (this.status == AccountStatus.Verified && status == AccountStatus.Closed)
                this.status = status;
        else if (this.status == AccountStatus.Verified && status == AccountStatus.Suspended)
                this.status = status;
    }

    /**
     * Generates a unique 16-digit card number format: XXXX XXXX XXXX XXXX
     */
    private String CardNumberGenerator() {
        Random random = new Random();
        String cardNumber;

        do {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append(random.nextInt(10));
                if ((i + 1) % 4 == 0 && i != 15) {
                    sb.append(" ");
                }
            }
            cardNumber = sb.toString();
        } while (AccountDAO.isCardNumberUsed(cardNumber));
        return cardNumber;
    }

    /**
     * Updates balance and tracks income/expenses without generating a new transaction log.
     * Typically used internally during transfers.
     */
    public void TransferBalance(double amount) {
        this.balance += amount;
        if (amount > 0) {
            this.TIncome += amount;
        } else {
            this.TExpenses += -amount;
        }
    }

    /**
     * @return The client's current credit score.
     */
    public int getCreditScore() {
        return creditScore;
    }

    /**
     * Updates the client's credit score.
     * @param creditScore The new score.
     */
    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    /**
     * TDD Feature: Checks if the client is eligible for a loan based on their credit score.
     * @return true if score is 600 or above, false otherwise.
     */
    public boolean checkLoanEligibility() {
        return this.creditScore >= 600;
    }
}
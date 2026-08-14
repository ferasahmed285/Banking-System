import com.Backend.DAO.*;
import com.Backend.Entities.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * White-Box Testing Suite for the Account class.
 * Focuses on branch coverage, decision paths, and internal state manipulation
 * within the deposit, withdraw, and transfer methods.
 */
public class AccountWhiteBoxTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1);
        account.setStatus(Account.AccountStatus.Verified);
        account.deposit(500); // seed balance
    }

    /**
     * Path 1 for deposit(): Tests branch where amount is less than or equal to 0.
     * Expectation: Returns false, no transaction occurs.
     */
    @Test
    void testDepositNegativeAmountBranch() {
        assertFalse(account.deposit(-50));
    }

    /**
     * Path 2 for deposit(): Tests boundary branch where amount is exactly 0.
     * Expectation: Returns false.
     */
    @Test
    void testDepositZeroAmount() {
        assertFalse(account.deposit(0));
    }

    /**
     * Path 3 for deposit(): Tests valid branch where status is Verified and amount > 0.
     * Expectation: Returns true, balance increases.
     */
    @Test
    void testDepositPositiveAmount() {
        assertTrue(account.deposit(200));
        assertEquals(700, account.getBalance());
    }

    /**
     * Path 1 for withdraw(): Tests valid branch where status is Verified and amount equals balance.
     * Expectation: Returns true, balance drops to 0.
     */
    @Test
    void testWithdrawEqualBalance() {
        Account acc = new Account(2);
        acc.setStatus(Account.AccountStatus.Verified);
        acc.deposit(300);
        assertTrue(acc.withdraw(300));
        assertEquals(0, acc.getBalance());
    }

    /**
     * Path 2 for withdraw(): Tests branch where amount exceeds balance.
     * Expectation: Returns false.
     */
    @Test
    void testWithdrawGreaterThanBalance() {
        assertFalse(account.withdraw(600));
    }

    /**
     * Path 3 for withdraw(): Tests branch where status is Suspended (not Verified).
     * Expectation: Returns false.
     */
    @Test
    void testWithdrawSuspendedAccount() {
        account.setStatus(Account.AccountStatus.Suspended);
        assertFalse(account.withdraw(100));
    }

    /**
     * Path 4 for withdraw(): Tests branch where status is Closed.
     * Expectation: Returns false.
     */
    @Test
    void testWithdrawClosedAccount() {
        account.setStatus(Account.AccountStatus.Closed);
        assertFalse(account.withdraw(100));
    }

    /**
     * Transfer Path 1: Successful transfer. Both Verified, sufficient balance.
     */
    @Test
    void testSuccessfulTransferBranch() {
        Account sender = new Account(3);
        Account receiver = new Account(4);

        sender.setStatus(Account.AccountStatus.Verified);
        receiver.setStatus(Account.AccountStatus.Verified);

        sender.deposit(1000);
        receiver.deposit(500);

        AccountDAO.add(sender);
        AccountDAO.add(receiver);

        boolean result = sender.transfer(receiver.getCardNumber(), 300, "Branch Success");
        assertTrue(result);
        assertEquals(700, sender.getBalance());
        assertEquals(800, receiver.getBalance());
    }

    /**
     * Transfer Path 2: Sender insufficient balance branch.
     */
    @Test
    void testTransferFailsWithInsufficientBalanceBranch() {
        Account sender = new Account(5);
        Account receiver = new Account(6);

        sender.setStatus(Account.AccountStatus.Verified);
        receiver.setStatus(Account.AccountStatus.Verified);

        sender.deposit(100);
        receiver.deposit(500);

        AccountDAO.add(sender);
        AccountDAO.add(receiver);

        boolean result = sender.transfer(receiver.getCardNumber(), 300, "Branch Insufficient");
        assertFalse(result);
        assertEquals(100, sender.getBalance());
        assertEquals(500, receiver.getBalance());
    }

    /**
     * Transfer Path 3: Receiver lookup fails (returns null).
     */
    @Test
    void testTransferFailsIfRecipientNotFoundBranch() {
        Account sender = new Account(7);
        sender.setStatus(Account.AccountStatus.Verified);
        sender.deposit(500);

        AccountDAO.add(sender); // only sender exists

        boolean result = sender.transfer("9999 9999 9999 9999", 100, "Branch No Recipient");
        assertFalse(result);
        assertEquals(500, sender.getBalance());
    }

    /**
     * Transfer Path 4: Receiver is Unverified branch.
     */
    @Test
    void testTransferToUnverifiedRecipientBranch() {
        Account sender = new Account(8);
        Account receiver = new Account(9);

        sender.setStatus(Account.AccountStatus.Verified);
        receiver.setStatus(Account.AccountStatus.Unverified);

        sender.deposit(500);
        receiver.deposit(300);

        AccountDAO.add(sender);
        AccountDAO.add(receiver);

        // Expectation: Receiver status is unverified, should hit "if (a.getStatus() != Verified)"
        boolean result = sender.transfer(receiver.getCardNumber(), 100, "Branch Unverified Recipient");

        assertFalse(result);
        assertEquals(500, sender.getBalance());
        assertEquals(0, receiver.getBalance());
    }

    /**
     * Transfer Path 5: Sender is Unverified branch.
     */
    @Test
    void testTransferFailsWhenSenderUnverifiedBranch() {
        Account sender = new Account(10);
        Account receiver = new Account(11);

        sender.setStatus(Account.AccountStatus.Unverified);
        receiver.setStatus(Account.AccountStatus.Verified);

        sender.deposit(1000);
        receiver.deposit(500);

        AccountDAO.add(sender);
        AccountDAO.add(receiver);

        // Expectation: Hits outer "if (status == Verified)" which fails
        boolean result = sender.transfer(receiver.getCardNumber(), 200, "Branch Unverified Sender");
        assertFalse(result);
        assertEquals(0, sender.getBalance());
        assertEquals(500, receiver.getBalance());
    }
}
import com.Backend.DAO.AccountDAO;
import com.Backend.Entities.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Black-Box Testing Suite for the Account class.
 * Tests are structured around Equivalence Partitioning (EP) and Boundary Value Analysis (BVA).
 */
public class AccountBlackBoxTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1);
        account.setStatus(Account.AccountStatus.Verified);
    }

    /**
     * BVA & EP: Tests an invalid equivalence class for deposit amount (amount <= 0).
     */
    @Test
    void testDepositNegativeAmount() {
        assertFalse(account.deposit(-100));
    }

    /**
     * BVA & EP: Tests a valid equivalence class for deposit amount (amount > 0).
     */
    @Test
    void testDepositValidAmount() {
        assertTrue(account.deposit(200));
        assertEquals(200, account.getBalance());
    }

    /**
     * State-based EP: Tests deposit action against an invalid state (Closed).
     */
    @Test
    void testDepositClosedAccount() {
        account.setStatus(Account.AccountStatus.Closed);
        assertFalse(account.deposit(100));
    }

    /**
     * BVA & EP: Tests a valid withdrawal amount (0 < amount <= balance).
     */
    @Test
    void testWithdrawValidAmount() {
        account.deposit(500);
        assertTrue(account.withdraw(200));
        assertEquals(300, account.getBalance());
    }

    /**
     * BVA & EP: Tests withdrawal overdraft boundary (amount > balance).
     */
    @Test
    void testWithdrawOverdraft() {
        account.deposit(100);
        assertFalse(account.withdraw(200));
    }

    /**
     * State-based EP: Tests withdrawal action against an invalid state (Suspended).
     */
    @Test
    void testWithdrawSuspendedAccount() {
        account.deposit(300);
        account.setStatus(Account.AccountStatus.Suspended);
        assertFalse(account.withdraw(100));
    }

    /**
     * EP: Tests a fully valid transfer scenario where all preconditions are met.
     */
    @Test
    void testSuccessfulTransfer() {
        Account sender = new Account(1);
        Account receiver = new Account(2);

        sender.setStatus(Account.AccountStatus.Verified);
        receiver.setStatus(Account.AccountStatus.Verified);

        sender.deposit(1000);
        receiver.deposit(500);

        // Register both accounts in DAO to allow lookup
        AccountDAO.add(sender);
        AccountDAO.add(receiver);

        boolean result = sender.transfer(receiver.getCardNumber(), 300, "BlackBox Transfer");
        assertTrue(result);
        assertEquals(700, sender.getBalance());   // deducted
        assertEquals(800, receiver.getBalance()); // credited
    }

    /**
     * EP: Tests transfer constraint where sender is not Verified.
     */
    @Test
    void testTransferFailsWhenSenderUnverified() {
        Account sender = new Account(1);
        Account receiver = new Account(2);

        sender.setStatus(Account.AccountStatus.Unverified);
        receiver.setStatus(Account.AccountStatus.Verified);

        sender.deposit(1000);
        receiver.deposit(500);

        AccountDAO.add(sender);
        AccountDAO.add(receiver);

        boolean result = sender.transfer(receiver.getCardNumber(), 200, "Fail Transfer");
        assertFalse(result);
        assertEquals(0, sender.getBalance()); // unchanged
        assertEquals(500, receiver.getBalance()); // unchanged
    }

    /**
     * EP: Tests transfer constraint where sender has insufficient funds.
     */
    @Test
    void testTransferFailsWithInsufficientBalance() {
        Account sender = new Account(1);
        Account receiver = new Account(2);

        sender.setStatus(Account.AccountStatus.Verified);
        receiver.setStatus(Account.AccountStatus.Verified);

        sender.deposit(100);
        receiver.deposit(500);

        AccountDAO.add(sender);
        AccountDAO.add(receiver);

        boolean result = sender.transfer(receiver.getCardNumber(), 300, "Insufficient Balance");
        assertFalse(result);
        assertEquals(100, sender.getBalance()); // unchanged
        assertEquals(500, receiver.getBalance()); // unchanged
    }

    /**
     * EP: Tests transfer constraint where recipient card number does not exist.
     */
    @Test
    void testTransferFailsIfRecipientNotFound() {
        Account sender = new Account(1);
        sender.setStatus(Account.AccountStatus.Verified);
        sender.deposit(500);

        AccountDAO.add(sender); // only sender is registered

        boolean result = sender.transfer("9999 9999 9999 9999", 100, "Invalid Recipient");
        assertFalse(result);
        assertEquals(500, sender.getBalance()); // unchanged
    }

}
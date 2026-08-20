import com.Backend.Entities.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountStateTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1);
    }
    @Test
    void testUnverifiedToVerifiedAllowed() {
        account.setStatus(Account.AccountStatus.Verified);
        assertEquals(Account.AccountStatus.Verified, account.getStatus());
    }

    @Test
    void testUnverifiedToSuspendedNotAllowed() {
        account.setStatus(Account.AccountStatus.Suspended);
        assertEquals(Account.AccountStatus.Unverified, account.getStatus());
    }

    @Test
    void testVerifiedToSuspendedAllowed() {
        account.setStatus(Account.AccountStatus.Verified);
        account.setStatus(Account.AccountStatus.Suspended);
        assertEquals(Account.AccountStatus.Suspended, account.getStatus());
    }

    @Test
    void testSuspendedToVerifiedAllowed() { // Appeal
        account.setStatus(Account.AccountStatus.Verified);
        account.setStatus(Account.AccountStatus.Suspended);
        account.setStatus(Account.AccountStatus.Verified);
        assertEquals(Account.AccountStatus.Verified, account.getStatus());
    }

    @Test
    void testSuspendedToClosedAllowed() { // Admin Action
        account.setStatus(Account.AccountStatus.Verified);
        account.setStatus(Account.AccountStatus.Suspended);
        account.setStatus(Account.AccountStatus.Closed);
        assertEquals(Account.AccountStatus.Closed, account.getStatus());
    }

    @Test
    void testClosedToVerifiedNotAllowed() {
        account.setStatus(Account.AccountStatus.Verified);
        account.setStatus(Account.AccountStatus.Closed);
        account.setStatus(Account.AccountStatus.Verified);
        assertEquals(Account.AccountStatus.Closed, account.getStatus());
    }
}
import com.Backend.Entities.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test-Driven Development (TDD) Suite for the Client Credit Score Check feature.
 * Validates the behavior of setting credit scores and evaluating loan eligibility.
 */
public class CreditScoreFeatureTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1); // Create account with client ID 1
    }

    /**
     * TDD Validation: Ensures new accounts default to a 0 credit score.
     */
    @Test
    void testCreditScoreInitiallyUnset() {
        assertEquals(0, account.getCreditScore());
    }

    /**
     * TDD Validation: Ensures credit score can be set and retrieved accurately.
     */
    @Test
    void testSetValidCreditScore() {
        account.setCreditScore(750);
        assertEquals(750, account.getCreditScore());
    }

    /**
     * TDD Validation: Ensures system handles/accepts negative scores (or rejects based on design).
     */
    @Test
    void testSetInvalidCreditScoreNegative() {
        account.setCreditScore(-20);
        assertTrue(account.getCreditScore() < 0);
    }

    /**
     * TDD Validation: Evaluates loan eligibility algorithm for a score BELOW the 600 threshold.
     */
    @Test
    void testCreditScoreAffectsLoanEligibilityLow() {
        account.setCreditScore(500);
        assertFalse(account.checkLoanEligibility());
    }

    /**
     * TDD Validation: Evaluates loan eligibility algorithm for a score ABOVE the 600 threshold.
     */
    @Test
    void testCreditScoreAffectsLoanEligibilityHigh() {
        account.setCreditScore(720);
        assertTrue(account.checkLoanEligibility());
    }
}

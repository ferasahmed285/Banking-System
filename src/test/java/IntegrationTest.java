import com.Backend.DAO.AccountDAO;
import com.Backend.DAO.ClientDAO;
import com.Backend.Database.Data;
import com.Backend.Entities.Account;
import com.Backend.Entities.Client;
import com.BankingSystem;
import com.controllers.Client.Forms.DepositMoney_FormController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Simulates user flow from GUI -> Controller -> Account methods.
 */
public class IntegrationTest {

    @BeforeAll
    public static void initJFX() {
        // Initialize JavaFX toolkit to allow FXMLLoader to run
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit already initialized
        }
    }

    @BeforeEach
    public void setup() {
        Data.clients.clear();
        Data.accounts.clear();
    }

    @Test
    public void testDepositIntegrationFlow() throws IOException, NoSuchFieldException, IllegalAccessException {
        // 1. Setup Backend State (Mocking a logged-in user)
        Client testClient = new Client("Integration Test", "test@banking.com", "0123456789", "Pass123!", LocalDate.of(1990, 1, 1));
        ClientDAO.add(testClient);
        Account account = AccountDAO.getAccountByClient(testClient);
        account.setStatus(Account.AccountStatus.Verified);
        account.deposit(5000); // Initial balance
        
        // Simulate BankingSystem context
        BankingSystem.client = testClient;

        // 2. Load the Controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Frontend/fxml/Client/Forms/DepositMoney_Form.fxml"));
        Parent root = loader.load();
        DepositMoney_FormController controller = loader.getController();

        // 3. Simulate GUI Input
        // Using reflection to set the TextField since it's private and injected by FXML
        Field amountField = DepositMoney_FormController.class.getDeclaredField("DMF_Amount");
        amountField.setAccessible(true);
        TextField dmfAmount = (TextField) amountField.get(controller);
        
        // User types "1500" into the deposit field
        dmfAmount.setText("1500");

        // 4. Simulate Button Click -> Controller Validation -> Account.deposit()
        // We pass null for ActionEvent since the controller doesn't strictly use it except for closing the stage
        com.AlertBox.testMode = true;
        try {
            controller.DepositMoney(null);
        } catch (NullPointerException e) {
            // Ignore the NPE from event.getSource().getScene().getWindow() because we aren't in a real Stage.
            // The important part is that account.deposit() was called before the stage closing logic!
        } finally {
            com.AlertBox.testMode = false;
        }

        // 5. Verify Backend State Changed
        assertEquals(6500.0, account.getBalance(), "The account balance should be updated by the controller via integration flow.");
    }
}

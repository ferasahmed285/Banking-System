import com.BankingSystem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.api.FxAssert;
import org.testfx.matcher.control.LabeledMatchers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Functional UI Testing using TestFX.
 * Validates that JavaFX components load correctly, input fields accept data,
 * and buttons interact as expected.
 */
public class UIFeatureTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        com.Backend.Database.Data.clients.clear();
        com.Backend.Database.Data.accounts.clear();
        com.Backend.Entities.Client testClient = new com.Backend.Entities.Client("Client Alpha", "alpha@gmail.com", "01010101110","Alpha123@", java.time.LocalDate.of(2000,5,12));
        com.Backend.DAO.ClientDAO.add(testClient);
        
        com.Backend.Entities.Account acc = com.Backend.DAO.AccountDAO.getAccountByClient(testClient);
        acc.setStatus(com.Backend.Entities.Account.AccountStatus.Verified);
        
        // Start directly at Dashboard
        BankingSystem.client = testClient;
        javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(BankingSystem.class.getResource("/Frontend/fxml/Client/Pages/Dashboard_Page.fxml"));
        javafx.scene.Scene scene = new javafx.scene.Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Functional UI Test: Proper rendering of status label
     */
    @Test
    public void testStatusLabelRendering() {
        sleep(1000);
        // Verify the status label is rendered correctly
        FxAssert.verifyThat("#DB_V", LabeledMatchers.hasText("Verified"));
    }

    /**
     * Functional UI Test: Buttons disabled based on state
     */
    @Test
    public void testButtonsDisabledBasedOnState() {
        sleep(1000);
        // Since the user is Verified, buttons should be enabled
        javafx.scene.control.Button depositBtn = lookup("Deposit").queryAs(javafx.scene.control.Button.class);
        assertFalse(depositBtn.isDisabled(), "Deposit button should be enabled for verified accounts");
    }
}

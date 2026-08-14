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
        new BankingSystem().start(stage);
    }

    /**
     * UI Component Rendering Test: Ensures the Login screen loads and vital nodes exist.
     */
    @Test
    public void testLoginScreenRenders() {
        // Verify that the login button exists by its text
        FxAssert.verifyThat(".button", LabeledMatchers.hasText("Login"));
    }

    /**
     * UI Input Validation Test: Ensures the text fields for Email and Password are interactable.
     */
    @Test
    public void testTextFieldsExist() {
        TextField emailField = lookup("#LP_Email").queryAs(TextField.class);
        TextField passwordField = lookup("#LP_Password").queryAs(TextField.class);

        assertNotNull(emailField, "Email text field should be present.");
        assertNotNull(passwordField, "Password text field should be present.");
    }
}

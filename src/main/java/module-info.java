module org.bankingsystemgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com to javafx.fxml;
    exports com.Backend.DAO;
    exports com.Backend.Entities;
    exports com.Backend.Database;
    opens com.Backend.Entities to javafx.base;
    opens com.controllers to javafx.fxml,javafx.base;
    exports com to javafx.controls,javafx.fxml,javafx.base,javafx.graphics;


    opens com.controllers.Client.Pages to javafx.fxml,javafx.base;
    opens com.controllers.Admin.Pages to javafx.fxml,javafx.base;
    opens com.controllers.Admin.Forms to javafx.fxml,javafx.base;
    opens com.controllers.Client.Cards to javafx.fxml,javafx.base; // Fix applied here
    opens com.controllers.Client.Forms to javafx.fxml,javafx.base;

}

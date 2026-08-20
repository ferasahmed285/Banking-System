package com;

import com.Backend.Entities.Account;
import com.Backend.Entities.Client;
import com.Backend.Entities.Transaction;
import com.controllers.Admin.Forms.AdminAccount_FormController;
import com.controllers.Admin.Forms.AdminTransaction_FormController;
import com.controllers.Client.Forms.Profile_FormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public static void SwitchToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/Login_Page.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void SwitchToSignup(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/Signup_Page.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void SwitchToForgotPassword(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/ForgotPassword_page.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void SwitchToProfile(ActionEvent event, Client client) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/Client/Forms/Profile_Form.fxml"));
        Parent root = loader.load();

        Profile_FormController controller = loader.getController();
        controller.setData(client);

        Stage newStage = new Stage();
        newStage.setTitle("Profile Form");

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(new Scene(root));
        newStage.showAndWait();
    }

    public static void SwitchToDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/Client/Pages/Dashboard_Page.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void SwitchToTransactions(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/Client/Pages/Transaction_Page.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void SwitchToSendMoney(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/Client/Forms/SendMoney_Form.fxml"));
        Parent root = loader.load();

        Stage newStage = new Stage();
        newStage.setTitle("Send Money Form");

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(new Scene(root));
        newStage.showAndWait();
    }
    public static void SwitchToDepositMoney(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/Client/Forms/DepositMoney_Form.fxml"));
        Parent root = loader.load();

        Stage newStage = new Stage();
        newStage.setTitle("Deposit Money Form");

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(new Scene(root));
        newStage.showAndWait();
    }
    public static void SwitchToWithdrawMoney(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/Client/Forms/WithdrawMoney_Form.fxml"));
        Parent root = loader.load();

        Stage newStage = new Stage();
        newStage.setTitle("Withdraw Money Form");

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(new Scene(root));
        newStage.showAndWait();
    }

    public static void SwitchToAdminCustomersPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/Admin/Pages/AdminAccount_Page.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void SwitchToAdminAccountsPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/Admin/Pages/AdminCustomers_Page.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void SwitchToAdminTransactionsPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/Admin/Pages/AdminTransaction_Page.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void SwitchToAdminAccountsForm(ActionEvent event, Account account) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/Admin/Forms/AdminAccount_Form.fxml"));
        Parent root = loader.load();

        AdminAccount_FormController controller = loader.getController();
        controller.setData(account);

        Stage newStage = new Stage();
        newStage.setTitle("AccountsForm");

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(new Scene(root));
        newStage.showAndWait();
    }
    public static void SwitchToAdminTransactionsForm(ActionEvent event, Transaction transaction) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/Frontend/fxml/Admin/Forms/AdminTransaction_Form.fxml"));
        Parent root = loader.load();

        AdminTransaction_FormController controller = loader.getController();
        controller.setData(transaction);

        Stage newStage = new Stage();
        newStage.setTitle("TransactionsForm");

        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(new Scene(root));
        newStage.showAndWait();
    }

}
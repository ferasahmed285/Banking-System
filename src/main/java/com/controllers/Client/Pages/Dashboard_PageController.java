package com.controllers.Client.Pages;

import com.AlertBox;
import com.Backend.DAO.AccountDAO;
import com.Backend.DAO.TransactionDAO;
import com.Backend.Entities.Account;
import com.Backend.Entities.Client;
import com.Backend.Entities.Transaction;
import com.BankingSystem;
import com.SceneController;
import com.controllers.Client.Cards.Notification_CardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Dashboard_PageController {

    @FXML
    private Label DB_V;
    @FXML
    private Label DB_Username;

    @FXML
    private Label DB_CardId;
    @FXML
    private Label DB_Money;
    @FXML

    private Label DB_Income;
    @FXML
    private Label DB_Expenses;

    @FXML
    private ScrollPane DB_NList;

    private Client client;
    private Account account;

    public void initialize() throws IOException {
        client = BankingSystem.client;
        DB_Username.setText(BankingSystem.client.getName());
        account = AccountDAO.getAccountByClient(client);
        DB_CardId.setText(account.getCardNumber());
        DB_Money.setText(String.valueOf(account.getBalance()));
        DB_Income.setText(String.valueOf(account.getTIncome()));
        DB_Expenses.setText(String.valueOf(account.getTExpenses()));
        DB_V.setText(account.getStatus().toString());
        populateNotifications();
    }
    private void populateNotifications() throws IOException {
        List<Transaction> transactions = TransactionDAO.getTransactionsforClient(client);

        if (transactions == null || transactions.isEmpty())
            return;

        GridPane notificationGrid = new GridPane();
        notificationGrid.setHgap(10);
        notificationGrid.setVgap(10);
        notificationGrid.setPadding(new javafx.geometry.Insets(10));

        Collections.reverse(transactions);

        int row = 0;
        for (Transaction transaction : transactions) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Frontend/fxml/Client/Cards/Notification_Card.fxml"));
            HBox notificationCard = loader.load();

            Notification_CardController controller = loader.getController();
            controller.setData(transaction);

            notificationGrid.add(notificationCard, 0, row);
            row++;
        }
        DB_NList.setContent(notificationGrid);
    }

    public void goToDashboard(ActionEvent event) throws IOException {
        SceneController.SwitchToDashboard(event);
    }
    public void goToTransactionPage(ActionEvent event) throws IOException {
        SceneController.SwitchToTransactions(event);
    }
    public void goToProfileForm(ActionEvent event) throws IOException {
        SceneController.SwitchToProfile(event,client);
    }
    public void goToLoginPage(ActionEvent event) throws IOException {
        BankingSystem.client = null;
        SceneController.SwitchToLogin(event);
    }

    public void goToSendMoneyForm(ActionEvent event) throws IOException {
        if (account.getStatus() != Account.AccountStatus.Verified) {
            AlertBox.alert("Account Not Verified", "Your account is not verified. You cannot send money.","close");
            return;
        }
        SceneController.SwitchToSendMoney(event);
        goToDashboard(event);
    }
    public void goToWithdrawForm(ActionEvent event) throws IOException {
        if (account.getStatus() != Account.AccountStatus.Verified) {
            AlertBox.alert("Account Not Verified", "Your account is not verified. You cannot withdraw money.","close");
            return;
        }
        SceneController.SwitchToWithdrawMoney(event);
        goToDashboard(event);
    }
    public void goToDepositForm(ActionEvent event) throws IOException {
        if (account.getStatus() != Account.AccountStatus.Verified) {
            AlertBox.alert("Account Not Verified", "Your account is not verified. You cannot deposit money.","close");
            return;
        }
        SceneController.SwitchToDepositMoney(event);
        goToDashboard(event);
    }

}
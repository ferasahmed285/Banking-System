package com.controllers.Client.Pages;

import com.Backend.DAO.TransactionDAO;
import com.Backend.Entities.Client;
import com.Backend.Entities.Transaction;
import com.BankingSystem;
import com.SceneController;
import com.controllers.Client.Cards.Transaction_CardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Transaction_PageController {

    @FXML
    private Label TP_Username;
    @FXML
    private ScrollPane TP_THistory;

    private Client client;

    public void initialize() throws IOException {
        client = BankingSystem.client;
        TP_Username.setText(BankingSystem.client.getName());
        populateTransactions();
    }

    private void populateTransactions() throws IOException {
        List<Transaction> transactions = TransactionDAO.getTransactionsforClient(client);

        if (transactions == null || transactions.isEmpty())
            return;

        GridPane TransactionsGrid = new GridPane();
        TransactionsGrid.setHgap(10);
        TransactionsGrid.setVgap(10);
        TransactionsGrid.setPadding(new javafx.geometry.Insets(10));

        Collections.reverse(transactions);

        int row = 0;
        for (Transaction transaction : transactions) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Frontend/fxml/Client/Cards/Transaction_Card.fxml"));
            AnchorPane notificationCard = loader.load();

            Transaction_CardController controller = loader.getController();
            controller.setData(transaction);

            TransactionsGrid.add(notificationCard, 0, row);
            row++;
        }
        TP_THistory.setContent(TransactionsGrid);
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

}
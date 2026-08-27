package com.controllers.Admin.Pages;

import com.AlertBox;
import com.Backend.Database.Data;
import com.Backend.Entities.Employee;
import com.Backend.Entities.Transaction;
import com.BankingSystem;
import com.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.sql.Time;


public class AdminTransaction_PageController {

    @FXML
    private Label C_Username;
    @FXML
    private TextField Search;
    @FXML
    private TableView<Transaction> HT;
    @FXML
    private TableColumn<Transaction, Double> TP_Amount;
    @FXML
    private TableColumn<Transaction, java.util.Date> TP_Date;
    @FXML
    private TableColumn<Transaction, String> TP_Message;
    @FXML
    private TableColumn<Transaction, Integer> TP_RID;
    @FXML
    private TableColumn<Transaction, Integer> TP_SID;
    @FXML
    private TableColumn<Transaction, Transaction.statusType> TP_Status;
    @FXML
    private TableColumn<Transaction, Integer> TP_TID;
    @FXML
    private TableColumn<Transaction, Time> TP_Time;
    @FXML
    private TableColumn<Transaction, Transaction.TransactionType> TP_Type;

    private final ObservableList<Transaction> transactionList = FXCollections.observableArrayList();
    private FilteredList<Transaction> filteredTransaction;

    private Employee employee = BankingSystem.employee;

    public void initialize() {
        if (employee != null) {
            C_Username.setText(employee.getName());
        }

        setupTables();
        setupSearchFilter();
        refreshTables();
    }

    private void setupTables() {

        TP_TID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TP_SID.setCellValueFactory(new PropertyValueFactory<>("senderId"));
        TP_RID.setCellValueFactory(new PropertyValueFactory<>("receiverId"));
        TP_Amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        TP_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        TP_Time.setCellValueFactory(new PropertyValueFactory<>("time"));
        TP_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        TP_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        TP_Message.setCellValueFactory(new PropertyValueFactory<>("message"));

        filteredTransaction = new FilteredList<>(transactionList, p -> true);

        SortedList<Transaction> sortedTransactions = new SortedList<>(filteredTransaction);
        sortedTransactions.comparatorProperty().bind(HT.comparatorProperty());

        HT.setItems(sortedTransactions);
        HT.setPlaceholder(new Label("No transactions found."));

    }
    private void refreshTables() {

        int selectedTransactionIndex = HT.getSelectionModel().getSelectedIndex();
        Transaction selectedItem = HT.getSelectionModel().getSelectedItem();

        transactionList.setAll(Data.transactions);

        filteredTransaction.setPredicate(filteredTransaction.getPredicate());

        if (selectedItem != null && HT.getItems().contains(selectedItem)) {
            HT.getSelectionModel().select(selectedItem);
        } else if (selectedTransactionIndex >= 0 && selectedTransactionIndex < HT.getItems().size()) {
            HT.getSelectionModel().select(selectedTransactionIndex);
        } else {
            HT.getSelectionModel().clearSelection();
        }
        HT.refresh();
    }

    private void setupSearchFilter() {
        Search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTransaction.setPredicate(transaction -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // Check all Transaction fields
                if (String.valueOf(transaction.getId()).contains(lowerCaseFilter)) return true;
                if (String.valueOf(transaction.getSenderId()).contains(lowerCaseFilter)) return true;
                if (String.valueOf(transaction.getReceiverId()).contains(lowerCaseFilter)) return true;
                if (String.valueOf(transaction.getAmount()).toLowerCase().contains(lowerCaseFilter)) return true;
                if (transaction.getDate() != null && transaction.getDate().toString().toLowerCase().contains(lowerCaseFilter)) return true;
                if (transaction.getTime() != null && transaction.getTime().toString().toLowerCase().contains(lowerCaseFilter)) return true;
                if (transaction.getType() != null && transaction.getType() .toString().toLowerCase().contains(lowerCaseFilter)) return true;
                if (transaction.getStatus() != null && transaction.getStatus().toString().toLowerCase().contains(lowerCaseFilter)) return true;
                if (transaction.getMessage() != null && transaction.getMessage().toLowerCase().contains(lowerCaseFilter)) return true;
                return false;
            });

            HT.getSelectionModel().clearSelection();
            HT.refresh();
        });
    }

    public void add(ActionEvent event) throws IOException {
        SceneController.SwitchToAdminTransactionsForm(event ,null);
        refreshTables();
    }

    public void edit(ActionEvent event) throws IOException {
        Transaction selectedTransaction = HT.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            SceneController.SwitchToAdminTransactionsForm(event ,selectedTransaction);
            refreshTables();
        } else {
            AlertBox.alert("Information", "Please select a transaction to edit.", "Close");
        }
    }

    public void goToAdminAccountsPage(ActionEvent event) throws IOException {
        com.SceneController.SwitchToAdminAccountsPage(event);
    }
    public void goToAdminCustomersPage(ActionEvent event) throws IOException {
        com.SceneController.SwitchToAdminCustomersPage(event);
    }
    public void goToAdminTransactionsPage(ActionEvent event) throws IOException {
        com.SceneController.SwitchToAdminTransactionsPage(event);
    }
    public void goToLoginPage(ActionEvent event) throws IOException {
        BankingSystem.client = null;
        BankingSystem.employee= null;
        com.SceneController.SwitchToLogin(event);
    }

}
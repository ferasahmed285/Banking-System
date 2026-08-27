package com.controllers.Admin.Pages;

import com.AlertBox;
import com.Backend.Database.Data;
import com.Backend.Entities.Account;
import com.Backend.Entities.Employee;
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


public class AdminAccount_PageController {

    @FXML
    private TableColumn<Account, Integer> AP_AID;
    @FXML
    private TableColumn<Account, Double> AP_Balance;
    @FXML
    private TableColumn<Account, Integer> AP_CID;
    @FXML
    private TableColumn<Account, String> AP_CN;
    @FXML
    private TableColumn<Account, Account.AccountStatus> AP_Status;
    @FXML
    private TableView<Account> BT;

    @FXML
    private Label C_Username;
    @FXML
    private TextField Search;

    private final ObservableList<Account> accountList = FXCollections.observableArrayList();
    private FilteredList<Account> filteredAccount;

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

        AP_AID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AP_CN.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
        AP_CID.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        AP_Balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        AP_Status.setCellValueFactory(new PropertyValueFactory<>("status"));

        filteredAccount = new FilteredList<>(accountList, p -> true);

        SortedList<Account> sortedAccounts = new SortedList<>(filteredAccount);
        sortedAccounts.comparatorProperty().bind(BT.comparatorProperty());

        BT.setItems(sortedAccounts);
        BT.setPlaceholder(new Label("No accounts found."));
    }

    private void refreshTables() {

        int selectedAccountIndex = BT.getSelectionModel().getSelectedIndex();
        Account selectedAccount = BT.getSelectionModel().getSelectedItem();

        accountList.setAll(Data.accounts);

        filteredAccount.setPredicate(filteredAccount.getPredicate());

        if (selectedAccount != null && BT.getItems().contains(selectedAccount)) {
            BT.getSelectionModel().select(selectedAccount);
        } else if (selectedAccountIndex >= 0 && selectedAccountIndex < BT.getItems().size()) {
            BT.getSelectionModel().select(selectedAccountIndex);
        } else {
            BT.getSelectionModel().clearSelection();
        }

        BT.refresh();

    }

    private void setupSearchFilter() {
        Search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAccount.setPredicate(account -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(account.getId()).contains(lowerCaseFilter)) return true;
                if (account.getCardNumber() != null && account.getCardNumber().toLowerCase().contains(lowerCaseFilter)) return true;
                if (String.valueOf(account.getClientId()).contains(lowerCaseFilter)) return true;
                if (String.valueOf(account.getBalance()).contains(lowerCaseFilter)) return true;
                if (String.valueOf(account.getTIncome()).contains(lowerCaseFilter)) return true;
                if (String.valueOf(account.getTExpenses()).contains(lowerCaseFilter)) return true;
                if (account.getStatus() != null && account.getStatus().name().toLowerCase().contains(lowerCaseFilter)) return true;

                return false;
            });

            BT.getSelectionModel().clearSelection();
            BT.refresh();
        });
    }

    public void edit(ActionEvent event) throws IOException {
        Account selectedAccounttime = BT.getSelectionModel().getSelectedItem();
        if (selectedAccounttime != null) {
            SceneController.SwitchToAdminAccountsForm(event, selectedAccounttime);
            refreshTables();
        } else {
            AlertBox.alert("Information", "Please select a account to edit.", "Close");
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

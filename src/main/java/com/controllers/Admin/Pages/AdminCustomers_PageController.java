package com.controllers.Admin.Pages;

import com.AlertBox;
import com.Backend.Database.Data;
import com.Backend.Entities.Employee;
import com.Backend.Entities.Client;
import com.Backend.Entities.Transaction;
import com.BankingSystem;
import com.SceneController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class AdminCustomers_PageController {



    @FXML
    private TableColumn<Client, Integer> CP_CID;
    @FXML
    private TableColumn<Client, Date> CP_DOB;
    @FXML
    private TableColumn<Client, String> CP_E;
    @FXML
    private TableColumn<Client, String> CP_Name;
    @FXML
    private TableColumn<Client, String> CP_PN;
    @FXML
    private TableColumn<Client, String> CP_Password;
    @FXML
    private TableView<Client> CT;

    @FXML
    private Label C_Username;
    @FXML
    private TextField Search;

    private ObservableList<Client> clientList = FXCollections.observableArrayList();
    private FilteredList<Client> filteredClient;

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

        CP_CID.setCellValueFactory(new PropertyValueFactory<>("id"));
        CP_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        CP_E.setCellValueFactory(new PropertyValueFactory<>("email"));
        CP_PN.setCellValueFactory(new PropertyValueFactory<>("phone"));
        CP_Password.setCellValueFactory(new PropertyValueFactory<>("password"));
        CP_DOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));

        filteredClient = new FilteredList<>(clientList, p -> true);

        SortedList<Client> sortedClients = new SortedList<>(filteredClient);
        sortedClients.comparatorProperty().bind(CT.comparatorProperty());

        CT.setItems(sortedClients);
        CT.setPlaceholder(new Label("No clients found."));

    }
    private void refreshTables() {

        int selectedClientIndex = CT.getSelectionModel().getSelectedIndex();
        Client selectedClient = CT.getSelectionModel().getSelectedItem();

        clientList.setAll(Data.clients);

        filteredClient.setPredicate(filteredClient.getPredicate());

        if (selectedClient != null && CT.getItems().contains(selectedClient)) {
            CT.getSelectionModel().select(selectedClient);
        } else if (selectedClientIndex >= 0 && selectedClientIndex < CT.getItems().size()) {
            CT.getSelectionModel().select(selectedClientIndex);
        } else {
            CT.getSelectionModel().clearSelection();
        }
        CT.refresh();
    }

    private void setupSearchFilter() {
        Search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredClient.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(client.getId()).contains(lowerCaseFilter)) return true;
                if (client.getName() != null && client.getName().toLowerCase().contains(lowerCaseFilter)) return true;
                if (client.getEmail() != null && client.getEmail().toLowerCase().contains(lowerCaseFilter)) return true;
                if (client.getPhone() != null && client.getPhone().toLowerCase().contains(lowerCaseFilter)) return true;
                if (client.getPassword() != null && client.getPassword().toLowerCase().contains(lowerCaseFilter)) return true;
                if (client.getDOB() != null && client.getDOB().toString().toLowerCase().contains(lowerCaseFilter)) return true;

                return false;
            });

            CT.getSelectionModel().clearSelection();
            CT.refresh();
        });
    }

    public void add(ActionEvent event) throws IOException {
        SceneController.SwitchToProfile(event, null);
        refreshTables();
    }

    public void edit(ActionEvent event) throws IOException {
        Client selectedClient = CT.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            SceneController.SwitchToProfile(event, selectedClient);
            refreshTables();
        } else {
            AlertBox.alert("Information", "Please select a showtime to edit.", "Close");
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
package com.controllers.Admin.Forms;


import com.Backend.DAO.AccountDAO;
import com.Backend.Entities.Account;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class AdminAccount_FormController {

    @FXML
    private Label AF_ID;
    @FXML
    private Label AF_CID;
    @FXML
    private Label AF_CN;
    @FXML
    private Label AF_Balance;

    @FXML
    private ChoiceBox<Account.AccountStatus> AF_Status;

    private Account account;

    public void setData(Account account) throws IllegalArgumentException {
        AF_Status.getItems().addAll(Account.AccountStatus.values());
        this.account = account;
        if (account != null) {
            AF_ID.setText(String.valueOf(account.getId()));
            AF_CID.setText(String.valueOf(account.getClientId()));
            AF_CN.setText(account.getCardNumber());
            AF_Balance.setText(String.valueOf(account.getBalance()));
            AF_Status.setValue(account.getStatus());
        }
    }

    public void updateData(ActionEvent event) {
        account.setStatus(Account.AccountStatus.valueOf(AF_Status.getSelectionModel().getSelectedItem().toString()));
        close(event);
    }

    public void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
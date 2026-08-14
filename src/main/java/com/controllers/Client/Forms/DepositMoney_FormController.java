package com.controllers.Client.Forms;

import com.Backend.DAO.AccountDAO;
import com.Backend.Entities.Account;
import com.Backend.Entities.Client;
import com.BankingSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DepositMoney_FormController {

    @FXML
    private TextField DMF_Amount;

    private Client client = BankingSystem.client;
    private Account account= AccountDAO.getAccountByClient(BankingSystem.client);

    public void DepositMoney(ActionEvent event) {
        this.account.deposit(Integer.parseInt(DMF_Amount.getText()));
        cancel(event);
    }

    public void cancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}

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

public class SendMoney_FormController {

    @FXML
    private TextField SMF_AccountNumber;
    @FXML
    private TextField SMF_Amount;
    @FXML
    private TextField SMF_Message;

    private Client client = BankingSystem.client;
    private Account account= AccountDAO.getAccountByClient(BankingSystem.client);

    public void DepositMoney(ActionEvent event) {
        try {
            int amount = Integer.parseInt(SMF_Amount.getText());
            if (this.account.transfer(SMF_AccountNumber.getText(), amount, SMF_Message.getText())) {
                com.AlertBox.alert("Success", "Transfer successful!", "Close");
                cancel(event);
            } else {
                com.AlertBox.alert("Transaction Failed", "Invalid account, insufficient funds, or account is not verified.", "Close");
            }
        } catch (NumberFormatException e) {
            com.AlertBox.alert("Invalid Input", "Please enter a valid numeric amount.", "Close");
        }
    }

    public void cancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}


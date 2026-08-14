package com.controllers.Admin.Forms;

import com.Backend.DAO.TransactionDAO;
import com.Backend.Entities.Transaction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AdminTransaction_FormController {

    @FXML
    private Label TF_ID;
    @FXML
    private TextField TF_RID;
    @FXML
    private TextField TF_SID;
    @FXML
    private Label TF_Time;
    @FXML
    private Label TF_Date;
    @FXML
    private TextField TF_Amount;
    @FXML
    private TextField TF_Message;

    @FXML
    private ChoiceBox<Transaction.statusType> TF_Status;
    @FXML
    private ChoiceBox<Transaction.TransactionType> TF_Type;

    private Transaction transaction;

    public void setData(Transaction transaction) throws IllegalArgumentException {
        TF_Status.getItems().addAll(Transaction.statusType.values());
        TF_Type.getItems().addAll(Transaction.TransactionType.values());

        this.transaction = transaction;
        if(transaction!=null) {
            TF_ID.setText(String.valueOf(transaction.getId()));
            if(transaction.getType() == Transaction.TransactionType.Deposit){
                TF_RID.setText("Bank");
                TF_SID.setText(String.valueOf(transaction.getSenderId()));
            }else if(transaction.getType() == Transaction.TransactionType.Withdraw){
                TF_RID.setText(String.valueOf(transaction.getReceiverId()));
                TF_SID.setText("Bank");
            }else if(transaction.getType() == Transaction.TransactionType.Transfer){
                TF_RID.setText(String.valueOf(transaction.getReceiverId()));
                TF_SID.setText(String.valueOf(transaction.getSenderId()));
            }
            TF_Time.setText(transaction.getTime().toString());
            TF_Amount.setText(String.valueOf(transaction.getAmount()));
            TF_Message.setText(String.valueOf(transaction.getMessage()));
            TF_Status.setValue(transaction.getStatus());
            TF_Type.setValue(transaction.getType());
            TF_Date.setText(transaction.getDate().toString());
        }else{
            TF_Time.setText(LocalTime.now().toString());
            TF_Date.setText(LocalDateTime.now().toString());
        }

    }

    public void updateData(ActionEvent event) {
        if (this.transaction == null)
            TransactionDAO.add(new Transaction(Integer.parseInt(TF_SID.getText()), Integer.parseInt(TF_RID.getText()),
                    Double.parseDouble(TF_Amount.getText()), LocalDate.now(), LocalTime.now(),
                    TF_Type.getValue(),TF_Status.getValue(),TF_Message.getText()));
        else
            transaction.setStatusType(Transaction.statusType.valueOf(TF_Status.getSelectionModel().getSelectedItem().toString()));
        close( event);
    }

    public void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
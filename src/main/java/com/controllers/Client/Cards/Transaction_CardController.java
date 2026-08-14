package com.controllers.Client.Cards;

import com.Backend.DAO.ClientDAO;
import com.Backend.Entities.Client;
import com.Backend.Entities.Transaction;
import com.BankingSystem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction_CardController {

    @FXML
    private Label BF_SPrice1;

    @FXML
    private Label TC_Date;
    @FXML
    private Label TC_Message;
    @FXML
    private Label TC_Money;
    @FXML
    private Label TC_Name;
    @FXML
    private Label TC_Time;
    @FXML
    private Label TC_Type;

    private Client client;
    private Transaction transaction;

    public void setData(Transaction Transactions) throws IllegalArgumentException {
        this.transaction = Transactions;
        this.client = BankingSystem.client;

        if (transaction.getSenderId() == client.getId()) {
            // I sent money → show receiver
            if (transaction.getType() == Transaction.TransactionType.Withdraw)
                TC_Name.setText("To: Bank");
            else{
                Client receiver = ClientDAO.getClientById(transaction.getReceiverId());
                if (receiver != null)
                    TC_Name.setText("To: " + receiver.getName());
                else
                    TC_Name.setText("To: Unknown");
            }
            TC_Type.setText("Sent");
        } else if (transaction.getReceiverId() == client.getId()) {
            // I received money → show sender
            if (transaction.getType() == Transaction.TransactionType.Deposit)
                TC_Name.setText("From: Bank");
            else{
                Client sender = ClientDAO.getClientById(transaction.getSenderId());
                if (sender != null)
                    TC_Name.setText("From: " + sender.getName());
                else
                    TC_Name.setText("From: Unknown");
            }
            TC_Type.setText("Received");
        }

        TC_Message.setText(transaction.getMessage());
        TC_Money.setText(String.format("%.2f", transaction.getAmount()));

        LocalDate date = transaction.getDate();
        TC_Date.setText(date.toString());

        // Time (12-hour format with AM/PM)
        LocalTime dateTime = transaction.getTime();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        String period = hour >= 12 ? "PM" : "AM";
        hour = (hour > 12) ? hour - 12 : (hour == 0 ? 12 : hour);
        String formattedTime = String.format("%d:%02d %s", hour, minute, period);
        TC_Time.setText(formattedTime);
    }




}

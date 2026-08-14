package com.controllers.Client.Cards;

import com.Backend.DAO.ClientDAO;
import com.Backend.Entities.Client;
import com.Backend.Entities.Transaction;
import com.BankingSystem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.time.LocalDate;
import java.time.LocalTime;

public class Notification_CardController {

    @FXML
    private Label NC_Name;
    @FXML
    private Label NC_Date;
    @FXML
    private Label NC_Time;
    @FXML
    private Label NC_Money;

    @FXML
    private ImageView NC_RArrow;
    @FXML
    private ImageView NC_SArrow;

    private Client client;
    private Transaction transaction;

    public void setData(Transaction Transactions) throws IllegalArgumentException {
        this.transaction = Transactions;
        this.client = BankingSystem.client;

        if (transaction.getSenderId() == client.getId()) {
            // I sent money → show receiver
            if (transaction.getType() == Transaction.TransactionType.Withdraw){
                NC_Name.setText("To: Bank");
                NC_SArrow.setVisible(true);
                NC_RArrow.setVisible(false);
            }
            else{
                Client receiver = ClientDAO.getClientById(transaction.getReceiverId());
                if (receiver != null)
                    NC_Name.setText("To: " + receiver.getName());
                else
                    NC_Name.setText("To: Unknown");
                NC_SArrow.setVisible(true);
                NC_RArrow.setVisible(false);
            }
        } else if (transaction.getReceiverId() == client.getId()) {
            // I received money → show sender
            if (transaction.getType() == Transaction.TransactionType.Deposit){
                NC_Name.setText("From: Bank");
                NC_SArrow.setVisible(false);
                NC_RArrow.setVisible(true);
            }
            else{
                Client sender = ClientDAO.getClientById(transaction.getSenderId());
                if (sender != null)
                    NC_Name.setText("From: " + sender.getName());
                else
                    NC_Name.setText("From: Unknown");
                NC_SArrow.setVisible(false);
                NC_RArrow.setVisible(true);
            }
        }

        NC_Money.setText(String.format("%.2f", transaction.getAmount()));

        LocalDate date = transaction.getDate();
        NC_Date.setText(date.toString());

        // Time (12-hour format with AM/PM)
        LocalTime dateTime = transaction.getTime();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        String period = hour >= 12 ? "PM" : "AM";
        hour = (hour > 12) ? hour - 12 : (hour == 0 ? 12 : hour);
        String formattedTime = String.format("%d:%02d %s", hour, minute, period);
        NC_Time.setText(formattedTime);
    }

}



//public void setData(Transaction Transactions) throws IllegalArgumentException {
//    this.transaction = Transactions;
//
//    NC_Name.setText((ClientDAO.getClientById(transaction.getSenderId()).getId());
//    NC_Money.setText(Transactions.getAmount());
//
//    LocalDate date = transaction.getDate().toLocalDate();
//    String time = String.valueOf(transaction.getTime());
//    int hour = Integer.parseInt(time.substring(0, 2));
//    String period = hour >= 12 ? "PM" : "AM";
//    hour = (hour > 12) ? hour - 12 : (hour == 0 ? 12 : hour);
//    String formattedTime = String.format("%d:%s %s", hour, time.substring(3, 5), period);
//
//    NC_Date.setText(String.valueOf(date));
//    NC_Name.setText(formattedTime);
//}
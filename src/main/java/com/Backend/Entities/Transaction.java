package com.Backend.Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {

    public enum TransactionType{Deposit, Withdraw , Transfer}
    public enum statusType{Success, Failed, canceled}

    private static int idCounter = 0;
    private int id;
    private int senderId;
    private int receiverId;
    private double amount;
    private LocalDate date;
    private LocalTime time;
    private TransactionType type;
    private statusType status;
    private String message;

    public Transaction(int senderId, int receiverId, double amount, LocalDate date, LocalTime time, TransactionType type, statusType status, String message) {
        this.id = idCounter++;
        if (type==TransactionType.Deposit) {
            this.receiverId = receiverId;

            this.senderId = 0;
        }else if (type==TransactionType.Withdraw) {
            this.receiverId = 0;
            this.senderId = senderId;
        } else if (type==TransactionType.Transfer) {
            this.receiverId = receiverId;
            this.senderId = senderId;
        }
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.type = type;
        this.status = status;
        this.message = message;
    }

    public int getId() {
        return id;
    }
    public int getSenderId() {
        return senderId;
    }
    public int getReceiverId() {
        return receiverId;
    }
    public double getAmount() {
        return amount;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public TransactionType getType() {
        return type;
    }
    public statusType getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }

    public void setStatusType(statusType status) {
        this.status = status;
    }
}
package com.Backend.DAO;

import com.Backend.Database.Data;
import static com.Backend.Database.Data.transactions;
import com.Backend.Entities.Client;
import com.Backend.Entities.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {


    public static List<Transaction> getTransactionsforClient(Client client) {
        ArrayList<Transaction> customerT = new ArrayList<>();
        for (Transaction t: transactions) {
            if (t.getSenderId() == client.getId() || t.getReceiverId() == client.getId()) {
                customerT.add(t);
            }
        }
        if (customerT.isEmpty()) return null;
        return customerT;
    }

    public static void add(Transaction customer) {
        transactions.add(customer);
    }

    public static List<Transaction> getAll() {
            return new ArrayList<>(transactions);
    }

}

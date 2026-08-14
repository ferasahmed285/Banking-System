package com.Backend.DAO;

import com.Backend.Database.Data;
import com.Backend.Entities.Account;
import com.Backend.Entities.Client;

import java.util.ArrayList;
import java.util.List;

import static com.Backend.Database.Data.*;

/**
 * Data Access Object (DAO) for Managing Accounts.
 * Handles simulated database operations like fetching, saving, and checking accounts in the in-memory store.
 */
public class AccountDAO {

    /**
     * Retrieves an account associated with a specific Client.
     * @param client The client entity to match against.
     * @return The Account object if found, otherwise null.
     */
    public static Account getAccountByClient(Client client) {
        for (Account a: accounts){
            if (a.getClientId() == client.getId())
                return a;
        }
        return null;
    }

    /**
     * Searches the data store for an account using its unique 16-digit card number.
     * @param cardNumber The card number string to search for.
     * @return The Account object if found, otherwise null.
     */
    public static Account getAccountByCardNumber(String cardNumber){
        for (Account a: accounts){
            if (a.getCardNumber().equals(cardNumber))
                return a;
        }
        return null;
    }

    /**
     * Helper method to verify if a randomly generated card number already exists in the system.
     * Crucial for maintaining card number uniqueness during account creation.
     * @param cardNumber The card number to check.
     * @return true if it exists, false if it is available.
     */
    public static boolean isCardNumberUsed(String cardNumber) {
        for (Account a: accounts){
            if (a.getCardNumber().equals(cardNumber))
                return true;
        }
        return false;
    }
    
    /**
     * Adds a newly instantiated Account to the data store.
     * @param account The account to save.
     */
    public static void add(Account account) {
        accounts.add(account);
    }

    /**
     * Fetches all accounts in the system.
     * Uses a shallow copy of the list to prevent direct manipulation of the internal data structure.
     * @return A List of all Accounts.
     */
    public static List<Account> getAll() {
        return new ArrayList<>(accounts);
    }

}
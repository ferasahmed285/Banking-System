package com.Backend.Database;

import com.Backend.Entities.*;
import com.Backend.DAO.*;
import java.time.LocalDate;
import java.util.List;

public class TestData {
    public static void test(){
        // ---------- Clients ----------
        ClientDAO.add(new Client("Client Alpha", "alpha@gmail.com", "01010101110","Alpha123@", LocalDate.of(2000,5,12)));
        ClientDAO.add(new Client("Client Beta", "beta@gmail.com", "01022223333","BetaPass@1", LocalDate.of(1998,3,22)));
        ClientDAO.add(new Client("Client Gamma", "gamma@gmail.com", "01033334444","Gamma@2024", LocalDate.of(2001,11,3)));
        ClientDAO.add(new Client("Client Delta", "delta@gmail.com", "01044445555","Delta#19", LocalDate.of(1995,7,17)));
        ClientDAO.add(new Client("Client Epsilon", "epsilon@gmail.com", "01055556666","Epsilon_77", LocalDate.of(1999,12,29)));
        ClientDAO.add(new Client("Client Zeta", "zeta@gmail.com", "01066667777","Zeta@88", LocalDate.of(1997,9,14)));
        ClientDAO.add(new Client("Client Eta", "eta@gmail.com", "01077778888","EtaPass1!", LocalDate.of(2002,2,5)));
        ClientDAO.add(new Client("Client Theta", "theta@gmail.com", "01088889999","Theta@55", LocalDate.of(1996,4,20)));
        ClientDAO.add(new Client("Client Iota", "iota@gmail.com", "01099990000","Iota_2000@", LocalDate.of(2000,8,30)));
        ClientDAO.add(new Client("Client Kappa", "kappa@gmail.com", "01100001111","Kappa#10", LocalDate.of(1992,6,15)));

        EmployeeDAO.add(new Employee("Admin One", "admin.one@gmail.com", "01211112222","Admin@123", LocalDate.of(1988,1,25), 5500));

        List<Account> allAccounts = AccountDAO.getAll();
        for (Account acc : allAccounts) {
            acc.setStatus(Account.AccountStatus.Verified);
            acc.deposit(10000);
        }

        Account clientA = AccountDAO.getAccountByClient(ClientDAO.getClientById(1));
        Account clientB = AccountDAO.getAccountByClient(ClientDAO.getClientById(2));
        assert clientA != null;
        assert clientB != null;
        clientA.transfer(clientB.getCardNumber(), 500, "Rent Payment");
        clientA.withdraw(300);
        clientA.deposit(1200);
        clientB.transfer(clientA.getCardNumber(), 700, "Gift Money");
        clientB.withdraw(400);
        clientB.deposit(2000);

    }

}

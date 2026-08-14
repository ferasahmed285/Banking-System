package com.Backend.DAO;

import com.Backend.Entities.Client;
import com.Backend.Database.Data;
import static com.Backend.Database.Data.clients;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientDAO {


    public static boolean verifyEmail(String email, String password) {
        for (Client client: clients){
            if ((Objects.equals(client.getEmail(), email))&&(Objects.equals(client.getPassword(), password)))
                return true;
        }
        return false;
    }

    public static Client getClientByEmail(String email) {
        for (Client client: clients){
            if (Objects.equals(client.getEmail(), email))
                return client;
        }
        return null;
    }

    public static Client getClientById(int Id) {
        for (Client client : clients) {
            if (client.getId() == Id)
                return client;
        }
        return null;
    }

    public static void add(Client client) {
        clients.add(client);
    }



}
package com.controllers;

import com.AlertBox;
import com.Backend.Entities.Client;
import com.Backend.DAO.ClientDAO;
import com.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.regex.Pattern;

public class Signup_PageController {

    @FXML
    private TextField SP_Name;
    @FXML
    private TextField SP_Email;
    @FXML
    private TextField SP_Phone;
    @FXML
    private TextField SP_Password;

    private boolean validateInput() {
        String name = SP_Name.getText().trim();
        String email = SP_Email.getText().trim();
        String password =  SP_Password.getText().trim();
        String Phone = SP_Phone .getText().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || Phone.isEmpty()) {
            AlertBox.alert("Validation Error", "All fields must be filled.", "Close");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z]+(?: [a-zA-Z]+)*$", name)) {
            AlertBox.alert("Validation Error", "Invalid username. Use letters only (no numbers), spaces allowed between words..", "Close");
            return false;
        }
        if (!Pattern.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.com$", email)) {
            AlertBox.alert("Validation Error", "Invalid email format.", "Close");
            return false;
        }
        if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", password)) {
            AlertBox.alert("Validation Error", "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be at least 8 characters long.", "Close");
            return false;
        }
        if (!Pattern.matches("^[0-9]{10}$", Phone)) {
            AlertBox.alert("Validation Error", "Invalid phone number format.", "Close");
            return false;
        }
        return true;
    }

    public void Signup(ActionEvent event) throws IOException {
        if (validateInput()) {
            String name = SP_Name.getText().trim();
            String email = SP_Email.getText().trim();
            String password =  SP_Password.getText().trim();
            String Phone = SP_Phone .getText().trim();
            Client client = new Client(name, email, Phone, password,null);
            ClientDAO.add(client);
            goToLoginPage(event);
        }
    }

    public void goToLoginPage(ActionEvent event) throws IOException {
        SceneController.SwitchToLogin(event);
    }

}
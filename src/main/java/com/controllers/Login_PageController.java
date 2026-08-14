package com.controllers;

import com.Backend.DAO.ClientDAO;
import com.AlertBox;
import com.Backend.DAO.EmployeeDAO;
import com.BankingSystem;
import com.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.IOException;

public class Login_PageController {

    @FXML
    private TextField LP_Email;
    @FXML
    private TextField LP_Password;

    public void handleLogin(ActionEvent event) throws IOException {
        String Email = LP_Email.getText().trim();
        String password = LP_Password.getText().trim();

        if (Email.isEmpty()) {
            AlertBox.alert("Error", "Email cannot be empty!", "Close");
            return;
        }
        if (password.isEmpty()) {
            AlertBox.alert( "Error", "Password cannot be empty!","Close");
            return;
        }
        if (ClientDAO.verifyEmail(Email, password)) {
                BankingSystem.client = ClientDAO.getClientByEmail(Email);
                System.out.println("Client Login successful");
                goToDashboardPage(event);

        }else if(EmployeeDAO.verifyEmail(Email, password)){
            BankingSystem.employee =EmployeeDAO.getEmployeeByEmail(Email);
                System.out.println("Admin Login successful");
                goToAdminPage(event);

        } else {
            AlertBox.alert("Login Failed","Invalid Email or password.","Close");
        }
    }

    public void goToSignUp(ActionEvent event) throws IOException {
        SceneController.SwitchToSignup(event);
    }
    public void goToForgotPassword(ActionEvent event) throws IOException {
        SceneController.SwitchToForgotPassword(event);
    }
    public void goToDashboardPage(ActionEvent event) throws IOException {
        SceneController.SwitchToDashboard(event);
    }
    public void goToAdminPage(ActionEvent event) throws IOException {
        SceneController.SwitchToAdminCustomersPage(event);
    }

}

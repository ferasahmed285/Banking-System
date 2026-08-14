package com.controllers.Client.Forms;

import com.Backend.DAO.ClientDAO;
import com.Backend.Entities.Client;
import com.BankingSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;

public class Profile_FormController {

    @FXML
    private TextField PF_Name;
    @FXML
    private TextField PF_Email;
    @FXML
    private TextField PF_Phone;
    @FXML
    private DatePicker PF_DOB;
    @FXML
    private TextField PF_Password;

    private Client client = BankingSystem.client;

    public void initialize( ) {
        if (client != null)
            setData(client);
    }

    public void setData(Client client) throws IllegalArgumentException {
        this.client = client;
        if(client != null) {
            PF_Name.setText(client.getName());
            PF_Phone.setText(client.getPhone());
            PF_Email.setText(client.getEmail());
            PF_Password.setText(client.getPassword());
            PF_DOB.setValue(client.getDOB());
        }

    }

    public void updateData(ActionEvent event) {
        if (client == null)
            ClientDAO.add(new Client(PF_Name.getText(), PF_Email.getText(),
                    PF_Phone.getText(),PF_Password.getText(),PF_DOB.getValue()));
        else
            client.update(PF_Name.getText(), PF_Email.getText(), PF_Phone.getText(), PF_Password.getText(), PF_DOB.getValue());
        close(event);
    }

    public void close(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}

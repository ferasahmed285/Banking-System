package com;

import com.Backend.Database.TestData;
import com.Backend.Entities.Client;
import com.Backend.Entities.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class BankingSystem extends Application {

    public static Client client;
    public static Employee employee;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankingSystem.class.getResource("/Frontend/fxml/Login_Page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Banking System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        TestData.test();
        launch();
    }
}
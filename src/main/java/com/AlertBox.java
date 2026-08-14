package com;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertBox {
    public static void alert(String title, String message, String buttonMessage) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UTILITY);
        window.setTitle(title);
        window.setResizable(false);

        Label label = new Label(message);
        label.setWrapText(true);
        label.setStyle("-fx-font-size: 16px; -fx-text-fill: #333;");
        label.setMaxWidth(400);

        Button button = new Button(buttonMessage);
        button.setStyle("-fx-background-color: #3f51b5; -fx-text-fill: white; -fx-font-size: 14px;");
        button.setOnAction(e -> window.close());

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 450, 200);
        window.setScene(scene);
        window.showAndWait();
    }
}

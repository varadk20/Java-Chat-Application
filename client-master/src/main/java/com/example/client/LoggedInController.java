package com.example.client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    @FXML
    private Button button_logout;

    @FXML
    private Label label_welcome;

    @FXML
    private Label label_thank;

    @FXML
    private Button button_server;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DBUtils.changeScene(event, "sample.fxml", "Log-in", null, null);
            }
        });

        button_server.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "Client.fxml", "client-window", null, null);
            }
        });
    }



    public void setUserInformation(String username){
        label_welcome.setText("Welcome "+ username +"!");
    }
}

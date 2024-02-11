package com.example.javalogin;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class Se implements Initializable {

    @FXML
    private Button button_logout1;
    @FXML
    private Button button_send;
    @FXML
    private TextField tf_message;
    @FXML
    private VBox vbox_messages;
    @FXML
    private ScrollPane sp_main;

    public Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Event handler for logout button
        button_logout1.setOnAction(event -> {
            try {
                if (server.serverSocket != null && !server.serverSocket.isClosed()) {
                    server.serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error connecting server");
            }
            DBUtils.changeScene(event, "sample.fxml", "logged-In", null, null);
        });

        try {
            server = new Server(new ServerSocket(1234));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating Server");
        }

        // Automatically scroll to the bottom when new messages are added
        vbox_messages.heightProperty().addListener((observable, oldValue, newValue) ->
                sp_main.setVvalue((Double) newValue));

        // Receive message from client
        server.receiveMessageFromClient(vbox_messages);

        // Send message button handler
        button_send.setOnAction(event -> {
            String messageToSend = tf_message.getText();
            if (!messageToSend.isEmpty()) {
                sendMessage(messageToSend);
            }
        });
    }

    // Method to send message
    private void sendMessage(String message) {
        if (server.socket.isClosed()) {
            System.out.println("Socket is closed. Unable to send message.");
            return;
        }

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-color: rgb(239,242,255);" +
                "-fx-background-color: rgb(15,25,242);" +
                "-fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        text.setFill(Color.color(0.934, 0.945, 0.996));

        hBox.getChildren().add(textFlow);
        vbox_messages.getChildren().add(hBox);

        server.sendMessageToClient(message);
        tf_message.clear(); // Clear the text-field
    }

    // Method to add a label
    public static void addLabel(String messageFromClient, VBox vbox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235);" + "-fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(() -> vbox.getChildren().add(hBox));
    }
}


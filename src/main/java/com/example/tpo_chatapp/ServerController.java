package com.example.tpo_chatapp;

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

public class ServerController implements Initializable {

    @FXML
    public Button button_send;
    @FXML
    public TextField tf_message;
    @FXML
    public ScrollPane sp_main;
    @FXML
    public VBox vbox_messages;
    private Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            server = new Server(new ServerSocket((4200)));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error creating server");
        }

        vbox_messages.heightProperty().addListener((observableValue, number, t1) -> sp_main.setVvalue((Double) t1));

        server.receiveMessageFromClient(vbox_messages);

        button_send.setOnAction(actionEvent -> {
            String messageToSend = tf_message.getText();
            if(!messageToSend.isEmpty()) {
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5));
                Text text = new Text(messageToSend);
                TextFlow textFlow = new TextFlow(text);

                textFlow.setStyle("-fx-color: rgb(239,242,255); " +
                        "-fx-background-color: rgb(60,152,2);" +
                        "-fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5));
                text.setFill(Color.color(0.934,0.945,0.966));

                hBox.getChildren().add(textFlow);
                vbox_messages.getChildren().add(hBox);

                server.sendMessageToClient(messageToSend);
                tf_message.clear();
            }
        });

    }
    public static void addLabel(String messageFromClient, VBox vbox) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-background-color: rgb(233, 233, 235);" +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5));
        hBox.getChildren().add(textFlow);

        Platform.runLater(() -> vbox.getChildren().add(hBox));

    }
}
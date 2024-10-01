package com.example.oopproj;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class StartPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic Tac Toe Game");

        Label player1Label = new Label("Player 1 Name:");
        Label player2Label = new Label("Player 2 Name:");
        player1Label.setStyle("-fx-font-size: 14px; -fx-text-fill: darkblue" );
        player2Label.setStyle("-fx-font-size: 14px; -fx-text-fill: darkblue");

        TextField player1TextField = new TextField();
        TextField player2TextField = new TextField();
        player1TextField.setStyle("-fx-font-size: 14px;");
        player2TextField.setStyle("-fx-font-size: 14px;");

        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        startButton.setOnAction(e -> {
            String player1Name = player1TextField.getText();
            String player2Name = player2TextField.getText();

            TicTacToe game = new TicTacToe(player1Name, player2Name);
            game.start(new Stage());
            primaryStage.close();
        });


        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setStyle("-fx-background-color: #FF0000; -fx-text-fill: white;");
        mainMenuButton.setOnAction(e -> {
            OpenPage openPage = new OpenPage();
            openPage.start(new Stage());
            primaryStage.close();
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(player1Label, 0, 0);
        gridPane.add(player1TextField, 1, 0);
        gridPane.add(player2Label, 0, 1);
        gridPane.add(player2TextField, 1, 1);
        gridPane.add(startButton, 0, 2, 2, 1);
        gridPane.add(mainMenuButton, 0, 3, 2, 1);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        borderPane.setStyle("-fx-background-color: #ADD8E6;");


        Scene scene = new Scene(borderPane, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


package com.example.oopproj;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class OpenPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic Tac Toe");

        Text titleText = new Text("Tic Tac Toe");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 36));

        Text modeText = new Text("Choose your game mode");
        modeText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        modeText.setFill(Color.ROYALBLUE);

        Button playWithPlayerButton = new Button("Player vs Player");
        Button playWithComputerButton = new Button("Player vs Computer");

        playWithPlayerButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: black;");
        playWithComputerButton.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;");

        playWithPlayerButton.setOnMouseEntered(e -> playWithPlayerButton.setStyle("-fx-background-color: darkblue; -fx-text-fill: white;"));
        playWithPlayerButton.setOnMouseExited(e -> playWithPlayerButton.setStyle("-fx-background-color: lightblue; -fx-text-fill: black;"));
        playWithComputerButton.setOnMouseEntered(e -> playWithComputerButton.setStyle("-fx-background-color: darkgreen; -fx-text-fill: white;"));
        playWithComputerButton.setOnMouseExited(e -> playWithComputerButton.setStyle("-fx-background-color: lightgreen; -fx-text-fill: black;"));
        playWithPlayerButton.setOnAction(e -> {
            StartPage startPage = new StartPage();
            startPage.start(primaryStage);
        });

        playWithComputerButton.setOnAction(e -> {
            PlayWithComputer game = new PlayWithComputer("Player");

            game.start(new Stage());
        });

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(titleText, modeText, playWithPlayerButton, playWithComputerButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vbox);
        borderPane.setPadding(new Insets(20));
        borderPane.setStyle("-fx-background-color: beige;");

        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
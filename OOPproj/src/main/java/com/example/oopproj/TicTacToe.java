package com.example.oopproj;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class TicTacToe extends Application {
    public String player1Name;
    public String player2Name;
    Label message;
    int player1Score = 0;
    int player2Score = 0;
    int player1Record = 0;
    int player2Record = 0;
    String pvpStatsFileName ="PVPstats.txt";

    public TicTacToe(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        message = new Label(this.player1Name + "'s turn");
        message.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        message.setTextFill(Color.YELLOW);
    }

    boolean oTurn = false;
    boolean xTurn = true;
    char xOro[][] = new char[3][3];

    Button b00 = new Button("");
    Button b01 = new Button("");
    Button b02 = new Button("");
    Button b10 = new Button("");
    Button b11 = new Button("");
    Button b12 = new Button("");
    Button b20 = new Button("");
    Button b21 = new Button("");
    Button b22 = new Button("");

    int highestScore = readHighestScore();
    @Override
    public void start(Stage primaryStage) {
        int highestScore = readHighestScore();

        //String highestScorer = (player1Record >= player2Record) ? player1Name : player2Name;
        String highestScoreMessage = "The highest score is " + highestScore;
        JOptionPane.showMessageDialog(null, highestScoreMessage);

        GridPane xyPane = new GridPane();
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #000000;");
        Button newGame = new Button("Reset");
        newGame.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        newGame.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        newGame.setOnAction(e -> resetGame());
        Button mainMenu = new Button("Main Menu");
        mainMenu.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        mainMenu.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        mainMenu.setOnAction(e -> primaryStage.close());
        Label scoreLabel = new Label("Scores: " + player1Name + ": " + player1Score + "  " + player2Name + ": " + player2Score);
        scoreLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        scoreLabel.setTextFill(Color.YELLOW);

        b00.setMinSize(125, 120);
        b01.setMinSize(125, 120);
        b02.setMinSize(125, 120);
        b10.setMinSize(125, 120);
        b11.setMinSize(125, 120);
        b12.setMinSize(125, 120);
        b20.setMinSize(125, 120);
        b21.setMinSize(125, 120);
        b22.setMinSize(125, 120);

        HBox buttonPane = new HBox(20);
        buttonPane.getChildren().addAll(newGame, mainMenu);

        VBox textAndButtonPane = new VBox(10);
        textAndButtonPane.getChildren().addAll(message, buttonPane);

        HBox topPane = new HBox(20);
        topPane.getChildren().addAll(message, newGame, mainMenu, scoreLabel);
        topPane.setSpacing(20);
        topPane.setAlignment(Pos.CENTER);
        message.setFont(Font.font(message.getFont().getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 20));

        HBox scorePane = new HBox();
        scorePane.getChildren().addAll(scoreLabel);
        scorePane.setAlignment(Pos.CENTER);
        scorePane.setSpacing(20);

        pane.setCenter(xyPane);
        pane.setTop(topPane);
        pane.setBottom(scorePane);
        pane.setPadding(new Insets(5, 5, 5, 5));

        scoreLabel.setText("Scores: " + player1Name + ": " + player1Score + "  " + player2Name + ": " + player2Score);

        Scene scene = new Scene(pane, 395, 455);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic Tac Toe ");
        primaryStage.show();

        xyPane.add(b00, 0, 0);
        xyPane.add(b01, 1, 0);
        xyPane.add(b02, 2, 0);
        xyPane.add(b10, 0, 1);
        xyPane.add(b11, 1, 1);
        xyPane.add(b12, 2, 1);
        xyPane.add(b20, 0, 2);
        xyPane.add(b21, 1, 2);
        xyPane.add(b22, 2, 2);
        xyPane.setVgap(5);
        xyPane.setHgap(5);

        pane.setCenter(xyPane);
        pane.setTop(topPane);
        pane.setPadding(new Insets(5, 5, 5, 5));

        newGame.setOnAction(event -> {

            b00.setText("");
            b01.setText("");
            b02.setText("");
            b10.setText("");
            b11.setText("");
            b12.setText("");
            b20.setText("");
            b21.setText("");
            b22.setText("");

            oTurn = false;
            xTurn = true;

            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    xOro[r][c] = '\0';
                }
            }
            b00.setDisable(false);
            b01.setDisable(false);
            b02.setDisable(false);
            b10.setDisable(false);
            b11.setDisable(false);
            b12.setDisable(false);
            b20.setDisable(false);
            b21.setDisable(false);
            b22.setDisable(false);

            message.setFont(Font.font(message.getFont().getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 20));
            message.setText(player1Name + "'s Turn");
            scoreLabel.setText("Scores: " + player1Name + ": " + player1Score + "  " + player2Name + ": " + player2Score);
        });


        mainMenu.setOnAction(event -> {
            OpenPage openPage = new OpenPage();
            openPage.start(primaryStage);
            resetGame();
        });


        b00.setOnAction(event -> {
            if (xTurn)
                b00.setText("X");
            else
                b00.setText("O");
            SetXO(0, 0);
            b00.setStyle("-fx-font-size:59");
            b00.setDisable(true);
        });
        b01.setOnAction(event -> {
            if (xTurn)
                b01.setText("X");
            else
                b01.setText("O");
            SetXO(0, 1);
            b01.setStyle("-fx-font-size:59");
            b01.setDisable(true);
        });
        b02.setOnAction(event -> {
            if (xTurn)
                b02.setText("X");
            else
                b02.setText("O");
            SetXO(0, 2);
            b02.setStyle("-fx-font-size:59");
            b02.setDisable(true);
        });
        b10.setOnAction(event -> {
            if (xTurn)
                b10.setText("X");
            else
                b10.setText("O");
            SetXO(1, 0);
            b10.setStyle("-fx-font-size:59");
            b10.setDisable(true);
        });
        b11.setOnAction(event -> {
            if (xTurn)
                b11.setText("X");
            else
                b11.setText("O");
            SetXO(1, 1);
            b11.setStyle("-fx-font-size:59");
            b11.setDisable(true);
        });
        b12.setOnAction(event -> {
            if (xTurn)
                b12.setText("X");
            else
                b12.setText("O");
            SetXO(1, 2);
            b12.setStyle("-fx-font-size:59");
            b12.setDisable(true);
        });
        b20.setOnAction(event -> {
            if (xTurn)
                b20.setText("X");
            else
                b20.setText("O");
            SetXO(2, 0);
            b20.setStyle("-fx-font-size:59");
            b20.setDisable(true);
        });
        b21.setOnAction(event -> {
            if (xTurn)
                b21.setText("X");
            else
                b21.setText("O");
            SetXO(2, 1);
            b21.setStyle("-fx-font-size:59");
            b21.setDisable(true);
        });
        b22.setOnAction(event -> {
            if (xTurn)
                b22.setText("X");
            else
                b22.setText("O");
            SetXO(2, 2);
            b22.setStyle("-fx-font-size:59");
            b22.setDisable(true);
        });
    }

    private int readHighestScore() {
        int highestScore = 0;
        try {
            File file = new File(pvpStatsFileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    int score = Integer.parseInt(parts[1]);
                    highestScore = Math.max(highestScore, score);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
        }
        return highestScore;
    }

    private void updatePlayerRecords() {
        try {
            File file = new File(pvpStatsFileName);
            FileWriter writer = new FileWriter(file, true);

            if (player1Score > player1Record) {
                player1Record = player1Score;
                writer.write(player1Name + ":" + player1Record + "\n");
            }

            if (player2Score > player2Record) {
                player2Record = player2Score;
                writer.write(player2Name + ":" + player2Record + "\n");
            }

            if (player1Score >= highestScore || player2Score >= highestScore) {
                String highestScorer = (player1Score >= player2Score) ? player1Name : player2Name;
                JOptionPane.showMessageDialog(null, "Congratulations! You broke the highest score  set by " +  " is: "+highestScore);
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    private void resetGame() {
        b00.setText("");
        b01.setText("");
        b02.setText("");
        b10.setText("");
        b11.setText("");
        b12.setText("");
        b20.setText("");
        b21.setText("");
        b22.setText("");

        oTurn = false;
        xTurn = true;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                xOro[r][c] = '\0';
            }
        }
        updatePlayerRecords();
    }

    public void SetXO(int r, int c) {
        Label scoreLabel = new Label();
        if (xTurn) {
            xOro[r][c] = 'X';

            message.setText(player2Name + "'s turn");

            oTurn = true;
            xTurn = false;
        } else if (oTurn) {
            xOro[r][c] = 'O';
            message.setText(player1Name + "'s turn");
            oTurn = false;
            xTurn = true;
        }

        if ((int) xOro[0][0] + (int) xOro[0][1] + (int) xOro[0][2] == 237 ||
                (int) xOro[1][0] + (int) xOro[1][1] + (int) xOro[1][2] == 237 ||
                (int) xOro[2][0] + (int) xOro[2][1] + (int) xOro[2][2] == 237 ||
                (int) xOro[0][0] + (int) xOro[1][1] + (int) xOro[2][2] == 237 ||
                (int) xOro[2][0] + (int) xOro[1][1] + (int) xOro[0][2] == 237 ||
                (int) xOro[0][0] + (int) xOro[1][0] + (int) xOro[2][0] == 237 ||
                (int) xOro[0][1] + (int) xOro[1][1] + (int) xOro[2][1] == 237 ||
                (int) xOro[0][2] + (int) xOro[1][2] + (int) xOro[2][2] == 237) {
            message.setText(player2Name + " Wins");
            player2Score++;
            scoreLabel.setText("Scores: " + player1Name + ": " + player1Score + "  " + player2Name + ": " + player2Score);
            disableButtons();

        } else if ((int) xOro[0][0] + (int) xOro[0][1] + (int) xOro[0][2] == 264 ||
                (int) xOro[1][0] + (int) xOro[1][1] + (int) xOro[1][2] == 264 ||
                (int) xOro[2][0] + (int) xOro[2][1] + (int) xOro[2][2] == 264 ||
                (int) xOro[0][0] + (int) xOro[1][1] + (int) xOro[2][2] == 264 ||
                (int) xOro[2][0] + (int) xOro[1][1] + (int) xOro[0][2] == 264 ||
                (int) xOro[0][0] + (int) xOro[1][0] + (int) xOro[2][0] == 264 ||
                (int) xOro[0][1] + (int) xOro[1][1] + (int) xOro[2][1] == 264 ||
                (int) xOro[0][2] + (int) xOro[1][2] + (int) xOro[2][2] == 264) {
            message.setText(player1Name + " Wins");
            player1Score++;
            scoreLabel.setText("Scores: " + player1Name + ": " + player1Score + "  " + player2Name + ": " + player2Score);
            disableButtons();
        } else if ((int) xOro[0][0] != 0 && (int) xOro[0][1] != 0 && (int) xOro[0][2] != 0 &&
                (int) xOro[1][0] != 0 && (int) xOro[1][1] != 0 && (int) xOro[1][2] != 0 &&
                (int) xOro[2][0] != 0 && (int) xOro[2][1] != 0 && (int) xOro[2][2] != 0) {
            message.setText("It's a Draw");
            message.setFont(Font.font(message.getFont().getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 17));
        }
    }

    private void disableButtons() {
        b00.setDisable(true);
        b01.setDisable(true);
        b02.setDisable(true);
        b10.setDisable(true);
        b11.setDisable(true);
        b12.setDisable(true);
        b20.setDisable(true);
        b21.setDisable(true);
        b22.setDisable(true);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
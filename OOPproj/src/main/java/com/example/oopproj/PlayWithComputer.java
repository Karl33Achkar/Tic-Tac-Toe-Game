package com.example.oopproj;

import javafx.application.Application;
import javafx.application.Platform;
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

import javax.swing.JOptionPane;
import java.io.*;
import java.util.*;

public class PlayWithComputer extends Application {
    public String playerName;
    Label message;
    int playerScore = 0;
    int computerScore = 0;
    Label playerScoreLabel;
    Label computerScoreLabel;
    int playerRecord = 0;
    int computerRecord = 0;
    String pvcStatsFileName = "PVCstats.txt";

    public PlayWithComputer(String playerName) {
        this.playerName = playerName;
        message = new Label(this.playerName + "'s turn");
    }

    boolean oTurn = false;
    boolean xTurn = true;
    char[][] xOro = new char[3][3];
    Button[][] buttons = new Button[3][3];

    @Override
    public void start(Stage primaryStage) {
        GridPane xyPane = new GridPane();
        HBox textPane = new HBox();
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #000000;");
        Button refresh = new Button("Reset");
        refresh.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        refresh.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        refresh.setOnAction(e -> resetGame());
        Button mainMenu = new Button("Main Menu");
        mainMenu.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        mainMenu.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        mainMenu.setOnAction(e -> primaryStage.close());

        playerScoreLabel = new Label("Player: " + playerScore);
        playerScoreLabel.setTextFill(Color.YELLOW);
        computerScoreLabel = new Label("Computer: " + computerScore);
        computerScoreLabel.setTextFill(Color.YELLOW);

        textPane.getChildren().add(message);
        textPane.getChildren().add(refresh);
        textPane.getChildren().add(mainMenu);
        textPane.setSpacing(20);
        textPane.setAlignment(Pos.CENTER);
        message.setFont(Font.font(message.getFont().getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 20));
        message.setTextFill(Color.YELLOW);

        int highestScore = readHighestScore();

        Label highestScoreLabel = new Label("Highest Score: " + highestScore);
        highestScoreLabel.setTextFill(Color.YELLOW);
        highestScoreLabel.setFont(Font.font(highestScoreLabel.getFont().getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 15));
        VBox topPane = new VBox(textPane, highestScoreLabel);
        topPane.setAlignment(Pos.CENTER);
        topPane.setSpacing(10);
        pane.setTop(topPane);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button("");
                buttons[i][j].setMinSize(125, 120);
                int finalI = i;
                int finalJ = j;
                buttons[i][j].setOnAction(event -> {
                    if (buttons[finalI][finalJ].getText().isEmpty()) {
                        buttons[finalI][finalJ].setText(xTurn ? "X" : "O");
                        SetXO(finalI, finalJ);
                        buttons[finalI][finalJ].setStyle("-fx-font-size:59");
                        buttons[finalI][finalJ].setDisable(true);
                        if (!checkGameEnd()) {

                            if (oTurn)
                                computerMove();
                        }
                    }
                });
                xyPane.add(buttons[i][j], j, i);
            }
        }

        xyPane.setVgap(5);
        xyPane.setHgap(5);

        HBox scorePane = new HBox(playerScoreLabel, computerScoreLabel);
        scorePane.setAlignment(Pos.CENTER);
        scorePane.setSpacing(20);

        VBox layout = new VBox(xyPane, scorePane);
        layout.setAlignment(Pos.CENTER);

        pane.setCenter(layout);
        pane.setPadding(new Insets(5, 5, 5, 5));

        Scene scene = new Scene(pane, 395, 480);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic Tac Toe ");
        primaryStage.show();


        refresh.setOnAction(event -> resetGame());

        mainMenu.setOnAction(event -> {
            OpenPage openPage = new OpenPage();
            openPage.start(primaryStage);
            primaryStage.close();
        });
    }

    private int readHighestScore() {
        int highestScore = 0;
        try {
            File file = new File(pvcStatsFileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(playerName)) {
                    int playerRecord = Integer.parseInt(parts[1]);
                    highestScore = Math.max(highestScore, playerRecord);
                } else if (parts.length == 2 && parts[0].equals("Computer")) {
                    int computerRecord = Integer.parseInt(parts[1]);
                    highestScore = Math.max(highestScore, computerRecord);
                }
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highestScore;
    }

    public void resetGame() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setText("");
                button.setDisable(false);
            }
        }

        oTurn = false;
        xTurn = true;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                xOro[r][c] = '\0';
            }
        }
        message.setFont(Font.font(message.getFont().getFamily(), FontWeight.BOLD, FontPosture.REGULAR, 20));
        message.setText(playerName + "'s turn");


        // Check if the player broke the highest score
        int highestScore = readHighestScore();
        if (playerScore > highestScore) {
            Platform.runLater(() -> {
                JOptionPane.showMessageDialog(null, "Congratulations! You broke the highest score: " + playerScore, "New Highest Score", JOptionPane.INFORMATION_MESSAGE);
            });
            updatePlayerRecord();
        }
    }

    private void updatePlayerRecord() {
        try {
            File file = new File(pvcStatsFileName);
            FileWriter writer = new FileWriter(file,true);

            if (playerScore > playerRecord) {
                playerRecord = playerScore;
            }

            if (computerScore > computerRecord) {
                computerRecord = computerScore;
            }

            writer.write(playerName + ":" + playerRecord + "\n");
            writer.write("Computer:" + computerRecord + "\n");
            writer.write("\n");


            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void computerMove() {
        Random random = new Random();
        int r, c;
        do {
            r = random.nextInt(3);
            c = random.nextInt(3);
        } while (!buttons[r][c].getText().isEmpty());

        buttons[r][c].setText(oTurn ? "O" : "X");
        SetXO(r, c);
        buttons[r][c].setStyle("-fx-font-size:59");
        buttons[r][c].setDisable(true);

        if (checkGameEnd()) {
            return;
        }

        oTurn = false;
        xTurn = true;
        message.setText(playerName + "'s turn");
    }

    public void SetXO(int r, int c) {
        if (xTurn) {
            xOro[r][c] = 'X';
            message.setText("Computer's turn");
            oTurn = true;
            xTurn = false;
        } else if (oTurn) {
            xOro[r][c] = 'O';
            message.setText(playerName + "'s turn");
            oTurn = false;
            xTurn = true;
        }
    }

    public boolean checkGameEnd() {
        // Check rows for a win
        for (int i = 0; i < 3; i++) {
            if (xOro[i][0] == xOro[i][1] && xOro[i][1] == xOro[i][2] && xOro[i][0] != '\0') {
                message.setText(xOro[i][0] == 'X' ? "Player Wins" : "Computer Wins");
                updateScores(xOro[i][0]);
                disableAllButtons();
                return true;
            }
        }

        // Check columns for a win
        for (int i = 0; i < 3; i++) {
            if (xOro[0][i] == xOro[1][i] && xOro[1][i] == xOro[2][i] && xOro[0][i] != '\0') {
                message.setText(xOro[0][i] == 'X' ? "Player Wins" : "Computer Wins");
                updateScores(xOro[0][i]);
                disableAllButtons();
                return true;
            }
        }

        // Check diagonals for a win
        if ((xOro[0][0] == xOro[1][1] && xOro[1][1] == xOro[2][2] && xOro[0][0] != '\0') ||
                (xOro[0][2] == xOro[1][1] && xOro[1][1] == xOro[2][0] && xOro[0][2] != '\0')) {
            message.setText(xOro[1][1] == 'X' ? "Player Wins" : "Computer Wins");
            updateScores(xOro[1][1]);
            disableAllButtons();
            return true;
        }

        // Check for a draw
        boolean isBoardFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (xOro[i][j] == '\0') {
                    isBoardFull = false;
                    break;
                }
            }
            if (!isBoardFull) {
                break;
            }
        }
        if (isBoardFull) {
            message.setText("It's a Draw");
            disableAllButtons();
            return true;
        }

        return false;
    }

    private void disableAllButtons() {
        for (Button[] row : buttons) {
            for (Button button : row) {
                button.setDisable(true);
            }
        }
    }

    private void updateScores(char winner) {
        if (winner == 'X') {
            playerScore++;
        } else if (winner == 'O') {
            computerScore++;
        }
        playerScoreLabel.setText("Player: " + playerScore);
        computerScoreLabel.setText("Computer: " + computerScore);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

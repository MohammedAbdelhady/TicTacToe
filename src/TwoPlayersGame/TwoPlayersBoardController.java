package TwoPlayersGame;

import TicTacToeGame.ListViewController;
import TicTacToeGame.TicTacToeGame;
import gamelogic.Tictactoe;
import helpers.DbManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.LoginFxmlController;
import networking.Client;

public class TwoPlayersBoardController implements Initializable {

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;

    @FXML
    private ImageView imageView5;

    @FXML
    private ImageView imageView6;

    @FXML
    private ImageView imageView7;

    @FXML
    private ImageView imageView8;

    @FXML
    private ImageView imageView9;

    @FXML
    private Button send;

    @FXML
    private TextField msgBox;

    @FXML
    private TextArea chatArea;

    @FXML
    private AnchorPane boardPane;

    private String username = "Khaled";
    private String opponent = "Ahmed";
    private Client client;
    private Tictactoe game;
    DbManager db;
    Thread th;
    private int playerMark = 0;

    public void handleImageViewClick(ImageView imageViewInAction, int row, int col) {

        imageViewInAction.setImage(new Image("res/" + game.getCurrentPlayerMark() + ".jpg"));
        imageViewInAction.setDisable(true);

        game.placeMark(row, col);

        if (game.checkForWin()) {

            if (playerMark == game.getCurrentPlayerMark()) {

                try {
                    System.out.println("Congratulations you have won the game !");

                    db.updateScore(username);

                    showWinningAlert();
                } catch (SQLException ex) {
                    Logger.getLogger(TwoPlayersBoardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

                System.out.println("Sorry you have lost the game !");

                showLosingAlert();
            }

        } else if (game.isBoardFull()) {
            System.out.println("Appears we have a draw!");

            showDrawAlert();
        }

        disableBoard(true);
        game.changePlayer();

    }

    @FXML
    void handleImageView1Click() {
        handleImageViewClick(imageView1, 0, 0);
        sendClickedPosition(0, 0);
    }

    @FXML
    void handleImageView2Click() {
        handleImageViewClick(imageView2, 0, 1);
        sendClickedPosition(0, 1);
    }

    @FXML
    void handleImageView3Click() {
        handleImageViewClick(imageView3, 0, 2);
        sendClickedPosition(0, 2);
    }

    @FXML
    void handleImageView4Click() {
        handleImageViewClick(imageView4, 1, 0);
        sendClickedPosition(1, 0);
    }

    @FXML
    void handleImageView5Click() {
        handleImageViewClick(imageView5, 1, 1);
        sendClickedPosition(1, 1);
    }

    @FXML
    void handleImageView6Click() {
        handleImageViewClick(imageView6, 1, 2);
        sendClickedPosition(1, 2);
    }

    @FXML
    void handleImageView7Click() {
        handleImageViewClick(imageView7, 2, 0);
        sendClickedPosition(2, 0);
    }

    @FXML
    void handleImageView8Click() {
        handleImageViewClick(imageView8, 2, 1);
        sendClickedPosition(2, 1);
    }

    @FXML
    void handleImageView9Click() {
        handleImageViewClick(imageView9, 2, 2);
        sendClickedPosition(2, 2);
    }

    @FXML
    void handleSendButtonClick() {

        String str = "chat" + "$" + opponent + "$" + msgBox.getText();
        client.display(str);
        client.send(str);
        chatArea.appendText("[Me] : " + msgBox.getText() + "\n");
        msgBox.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void sendClickedPosition(int r, int c) {

        String str = "board" + "$" + opponent + "$" + r + "$" + c;
        client.display(str);
        client.send(str);
    }

    public void syncBoard(int r, int c) {

        int index = (r * 3) + (c + 1);
        switch (index) {
            case 1:
                handleImageViewClick(imageView1, r, c);
                break;
            case 2:
                handleImageViewClick(imageView2, r, c);
                break;
            case 3:
                handleImageViewClick(imageView3, r, c);
                break;
            case 4:
                handleImageViewClick(imageView4, r, c);
                break;
            case 5:
                handleImageViewClick(imageView5, r, c);
                break;
            case 6:
                handleImageViewClick(imageView6, r, c);
                break;
            case 7:
                handleImageViewClick(imageView7, r, c);
                break;
            case 8:
                handleImageViewClick(imageView8, r, c);
                break;
            case 9:
                handleImageViewClick(imageView9, r, c);
                break;
            default:
                client.display("Invalid Index .. ");
                break;
        }
    }

    public void init(String opponent) {
        try {
            game = new Tictactoe();
            client = LoginFxmlController.getClient();
            this.opponent = opponent;
            this.username = client.getUsername();
            db = new DbManager();
            if (db.connect()) {

                System.out.println("username : " + username + " opponent : " + this.opponent);
             
                th = new Thread(new Runnable() {
                    
                    @Override
                    public void run() {

                        String msg;

                        while (true) {

                            msg = client.recieve();
                            client.display(msg);
                            String[] separatedMsg = msg.split("\\$");
                            if (separatedMsg[0].equalsIgnoreCase("chat")) {
                                client.display(msg);
                                chatArea.appendText(separatedMsg[1] + "\n");
                            } else if (separatedMsg[0].equalsIgnoreCase("board")) {
                                client.display(msg);
                                syncBoard(Integer.parseInt(separatedMsg[1]), Integer.parseInt(separatedMsg[2]));
                                disableBoard(false);
                            } else if (separatedMsg[0].equalsIgnoreCase("lost")) {
                                client.display(msg);
                                showLosingAlert();
                                break;
                            } else if (separatedMsg[0].equalsIgnoreCase("draw")) {
                                client.display(msg);
                                showDrawAlert();
                                break;
                            }

                        }
                        
                        th.stop();
                    }
                });
                     
              th.start();
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TwoPlayersBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disableBoard(boolean value) {

        boardPane.setDisable(value);
    }

    public int getPlayerMark() {
        return playerMark;
    }

    public void setPlayerMark(int playerMark) {
        this.playerMark = playerMark;
    }

    private void showDrawAlert() {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Drawing Notification");
                alert.setHeaderText("Draw !");
                alert.setContentText("Appears we have a draw!");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    
                    try {
                        db.updateStatus(1, username);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicTacToeGame/listview.fxml"));
                        Parent root = loader.load();

                        Scene scene = new Scene(root);
                        Stage stage = TicTacToeGame.getgStage();
                        stage.setScene(scene);
                        ListViewController.createNewClientThread();
                        stage.show();
                    } catch (IOException | SQLException ex) {
                        Logger.getLogger(TwoPlayersBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                }
            }
        });

    }

    private void showLosingAlert() {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Losing Notification");
                alert.setHeaderText("Losing");
                alert.setContentText("Sorry you have lost the game !");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        db.updateStatus(1, username);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicTacToeGame/listview.fxml"));
                        Parent root = loader.load();

                        Scene scene = new Scene(root);
                        Stage stage = TicTacToeGame.getgStage();
                        stage.setScene(scene);
                        ListViewController.createNewClientThread();
                        stage.show();
                    } catch (IOException | SQLException ex) {
                        Logger.getLogger(TwoPlayersBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                }
            }
        });
    }

    private void showWinningAlert() {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Winning Notification");
                alert.setHeaderText("Winner");
                alert.setContentText("Congratulations you have won the game !");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        db.updateStatus(1, username);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicTacToeGame/listview.fxml"));
                        Parent root = loader.load();

                        Scene scene = new Scene(root);
                        Stage stage = TicTacToeGame.getgStage();
                        stage.setScene(scene);
                        ListViewController.createNewClientThread();
                        stage.show();
                    } catch (IOException | SQLException ex) {
                        Logger.getLogger(TwoPlayersBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                }
            }
        });

    }

}

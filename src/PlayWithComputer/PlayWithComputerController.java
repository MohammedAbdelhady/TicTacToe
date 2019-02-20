package PlayWithComputer;

import TicTacToeGame.ListViewController;
import TicTacToeGame.TicTacToeGame;
import TwoPlayersGame.TwoPlayersBoardController;
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
import javafx.fxml. Initializable;
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

public class PlayWithComputerController implements Initializable {

    @FXML
    private AnchorPane boardPane;
        
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
    DbManager db;
    private Client client;
    private Tictactoe game;

    public void handleImageViewClick(ImageView imageViewInAction, int row, int col) {

        imageViewInAction.setImage(new Image("res/1.jpg"));
        imageViewInAction.setDisable(true); 
        
        game.placeMark(row, col);

        if (game.checkForWin()) {
            System.out.println("We have a winner! Congrats to " + game.getCurrentPlayerMark());
            System.out.println("1st image clicked");
            showWinningAlert();
            boardPane.setDisable(true);
            return;
        } else if (game.isBoardFull()) {
            System.out.println("Appears we have a draw!");
            showLosingAlert();
            return;
        }
        
        game.changePlayer();
        int x = game.playComputer();
        switch (x)
                {
            case 1: 
                imageViewInAction = imageView1;
                break;
            case 2: 
                imageViewInAction = imageView2;
                break;
            case 3: 
                imageViewInAction = imageView3;
                break;
            case 4: 
                imageViewInAction = imageView4;
                break;
            case 5: 
                imageViewInAction = imageView5;
                break;
            case 6: 
                imageViewInAction = imageView6;
                break;
            case 7: 
                imageViewInAction = imageView7;
                break;
            case 8: 
                imageViewInAction = imageView8;
                break;
            case 9: 
                imageViewInAction = imageView9;
                break;
        }
        imageViewInAction.setImage(new Image("res/0.jpg"));
        imageViewInAction.setDisable(true);  
        
        
       

        if (game.checkForWin()) {
            System.out.println("We have a winner! Congrats to " + game.getCurrentPlayerMark());
            System.out.println("1st image clicked");
            
            boardPane.setDisable(true);

            return;
        } else if (game.isBoardFull()) {
            System.out.println("Appears we have a draw!");
            return;
        }
        
        game.changePlayer();
    }

    @FXML
    void handleImageView1Click() {
        handleImageViewClick(imageView1, 0, 0);
    }

    @FXML
    void handleImageView2Click() {
        handleImageViewClick(imageView2, 0, 1);
    }

    @FXML
    void handleImageView3Click() {
        handleImageViewClick(imageView3, 0, 2);
    }

    @FXML
    void handleImageView4Click() {
        handleImageViewClick(imageView4, 1, 0);
    }

    @FXML
    void handleImageView5Click() {
        handleImageViewClick(imageView5, 1, 1);
    }

    @FXML
    void handleImageView6Click() {
        handleImageViewClick(imageView6, 1, 2);
    }

    @FXML
    void handleImageView7Click() {
        handleImageViewClick(imageView7, 2, 0);
    }

    @FXML
    void handleImageView8Click() {
        handleImageViewClick(imageView8, 2, 1);
    }

    @FXML
    void handleImageView9Click() {
        handleImageViewClick(imageView9, 2, 2);
    }

    @FXML
    void handleSendButtonClick() {
    }
    
    public PlayWithComputerController(){
        game = new Tictactoe();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }
    
    public void init(){
        try {
            game = new Tictactoe();
            db = new DbManager();
            db.connect();
            client = LoginFxmlController.getClient();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PlayWithComputerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
                        db.updateStatus(1, client.getUsername());
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
                        db.updateStatus(1, client.getUsername());
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

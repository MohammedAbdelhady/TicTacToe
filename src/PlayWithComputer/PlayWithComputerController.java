package PlayWithComputer;

import gamelogic.Tictactoe;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml. Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

    private Client client;
    private Tictactoe game;

    public void handleImageViewClick(ImageView imageViewInAction, int row, int col) {

        imageViewInAction.setImage(new Image("res/1.jpg"));
        imageViewInAction.setDisable(true); 
        
        game.placeMark(row, col);

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
        game = new Tictactoe();
        
   }

}

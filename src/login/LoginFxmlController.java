package login;

import TicTacToeGame.TicTacToeGame;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Player;
import helpers.DbManager;
import networking.Client;

public class LoginFxmlController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField Temail;
    @FXML
    private Label emaillabel;
    @FXML
    private Label passwordlabel;
    @FXML
    private Label found;
    @FXML
    private PasswordField Tpassword;

    Player player = new Player();
    DbManager db = new DbManager();
    private static Client client;

    @FXML
    private void login(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {

        String em = Temail.getText();
        String pass = Tpassword.getText();
        //System.out.println(em);
        if (em.equals("")) {
            emaillabel.setText("please enter an email");
        }
        if (pass.equals("")) {
            passwordlabel.setText("please enter an password");
        } else {
            boolean connect = db.connect();
            if (connect == true) {
                //found.setText("connected");
                emaillabel.setText("");
                passwordlabel.setText("");
                Player playerFounded;
                playerFounded = db.getPlayer(em);
                if (playerFounded == null) {
                    found.setText("This User Not Found Check Your Data or SignUp Null");
                    Temail.setText("");
                    Tpassword.setText("");
                } else if (playerFounded.getPassword().equals(pass)) {

                    client = new Client("127.0.0.1", 5005, playerFounded.getEmail());
                    client.start();
                    db.updateStatus(1, playerFounded.getEmail());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicTacToeGame/listview.fxml"));
                    Parent root = loader.load();

                    Scene scene = new Scene(root);
                    Stage stage = TicTacToeGame.getgStage();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    found.setText("This User Not Found Check Your Data or SignUp");
                    Temail.setText("");
                    Tpassword.setText("");
                }
            } else {
                emaillabel.setText("");
                passwordlabel.setText("");
                Temail.setText("");
                Tpassword.setText("");
                found.setText("please connect to database");
            }

        }
    }

    @FXML
    private void signUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/signup/SignUp.fxml"));
        Scene scene = new Scene(root);
        Stage stage = TicTacToeGame.getgStage();
        stage.setScene(scene);
        stage.show();

    }

    public static Client getClient() {
        return client;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}

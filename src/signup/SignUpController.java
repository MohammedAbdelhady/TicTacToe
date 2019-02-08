/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signup;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Player;
import helpers.DbManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.TicTacToeGame;

/**
 * FXML Controller class
 *
 * @author sarat
 */
public class SignUpController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField Temail;
    @FXML
    private PasswordField pass;
    @FXML
    private PasswordField cpass;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label password;
    @FXML
    private Label email;
    @FXML
    private Label cpassword;
    @FXML
    private Label found;

    Player player = new Player();
    DbManager db = new DbManager();

    @FXML
    private void signUp(ActionEvent event) throws ClassNotFoundException, SQLException {

        String firstName1 = firstname.getText();
        String lastName1 = lastname.getText();
        String email1 = Temail.getText();
        String password1 = pass.getText();
        String cpassword1 = cpass.getText();
        boolean connect = db.connect();
        if (firstName1.equals("")) {
            firstName.setText("please enter a first name");
        }
        if (lastName1.equals("")) {
            lastName.setText("please enter a last name");
        }
        if (email1.equals("")) {
            email.setText("please enter an email");
        }
        if (password1.equals("")) {
            password.setText("please enter a password");
        }
        if (cpassword1.equals("")) {
            cpassword.setText("please confirm your password");
        } else {
            if (password1.equals(cpassword1)) {

                if (connect == true) {

                    boolean add = db.addPlayer(firstName1, lastName1, email1, password1);
                    if (add == true) {
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/login/LoginFxml.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = TicTacToeGame.getgStage();
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        found.setText("player already exist");
                        firstname.setText("");
                        lastname.setText("");
                        Temail.setText("");
                        pass.setText("");
                        cpass.setText("");
                    }

                } else {
                    firstname.setText("");
                    lastname.setText("");
                    Temail.setText("");
                    pass.setText("");
                    cpass.setText("");

                    found.setText("please connect to database");
                }
            } else {
                firstName.setText("");
                lastName.setText("");
                email.setText("");
                password.setText("");
                cpassword.setText("please write same email");
                pass.setText("");
                cpass.setText("");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}

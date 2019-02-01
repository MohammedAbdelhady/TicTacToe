/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoegame;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Player;
import helpers.DbManager;

/**
 *
 * @author sarat
 */
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
    
    Player player=new Player();
    DbManager db=new DbManager();
    
    
    @FXML
    private void login(ActionEvent event) throws ClassNotFoundException, SQLException {
        
        String em=Temail.getText();
        String pass=Tpassword.getText();
        //System.out.println(em);
        if(em.equals("")){
            emaillabel.setText("please enter an email");
        }
        if(pass.equals("")){
            passwordlabel.setText("please enter an password");
        }
        else{
            boolean connect=db.connect();
            if(connect==true){
                //found.setText("connected");
                emaillabel.setText("");
                passwordlabel.setText("");
                Player playerFounded=new Player();
                playerFounded=db.getPlayer(em);
                if(playerFounded==null){
                    found.setText( "This User Not Found Check Your Data or SignUp");
                    Temail.setText("");
                    Tpassword.setText("");
                }

                else if(playerFounded.getPassword().equals(pass))
                {
                    emaillabel.setText("");
                    passwordlabel.setText("");
                    Temail.setText("");
                    Tpassword.setText("");
                    found.setText("Founded");
                }
                else{
                    found.setText( "This User Not Found Check Your Data or SignUp");
                    Temail.setText("");
                    Tpassword.setText("");  
                }
            }
            else{
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
      Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
      Scene scene = new Scene(root);
      Stage stage=new Stage();
      stage.setScene(scene);
      stage.show();
      
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

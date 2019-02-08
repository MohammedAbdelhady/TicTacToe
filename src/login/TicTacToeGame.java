package login;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TicTacToeGame extends Application {

  private static Stage gStage;
    @Override
    public void start(Stage stage) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoginFxml.fxml"));

            Scene scene = new Scene(root);

            stage.setScene(scene);
            gStage = stage;
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TicTacToeGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Stage getgStage() {
        return gStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}

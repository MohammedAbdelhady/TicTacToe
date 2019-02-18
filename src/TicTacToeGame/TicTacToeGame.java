
package TicTacToeGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TicTacToeGame extends Application {
    
    private static Stage gStage;
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/LoginFxml.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        gStage = stage;
        stage.show();
    }

    public static Stage getgStage() {
        return gStage;
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}

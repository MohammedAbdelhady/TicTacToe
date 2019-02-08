
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
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();
        
        FXMLDocumentController controller = loader.getController();
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setOnShowing(e -> controller.init());
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

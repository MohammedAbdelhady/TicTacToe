
package TicTacToeGame;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TicTacToeGame extends Application implements EventHandler<WindowEvent>{
    
    private static Stage gStage;
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/LoginFxml.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnHiding(this);
        gStage = stage;
        
        stage.show();
    }

    public static Stage getgStage() {
        return gStage;
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override  
  public void stop() throws Exception {  
  }  

    @Override  
  public void handle(WindowEvent event) {  
        FXMLDocumentController controller = new FXMLDocumentController();
        controller.stopStage();
  }  
}

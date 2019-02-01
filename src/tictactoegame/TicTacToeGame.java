package tictactoegame;

import java.util.Vector;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TicTacToeGame extends Application {
    
    private boolean turnX = true;
    private boolean isOver = false;
    private Vector<WinnerLine> winnerVect = new Vector<>();
    private BoardTile[][] board = new BoardTile[3][3];
    
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 1000, 600);
        
        //////////////////////////////////////////////////////////////////////////////////
        /// Building the tiles with StackPane order and distributing them on the root pane
        //////////////////////////////////////////////////////////////////////////////////
        // horizontally
        for (int y=0; y<3; y++){
            // vertically
            for (int x=0; x<3; x++){
                BoardTile eachTile = new BoardTile();
                eachTile.setTranslateX(x * 200);
                eachTile.setTranslateY(y * 200);
                
                root.getChildren().add(eachTile);
                
                // to save them as 2 Dimensional Array with fixed known positioning
                board[x][y] = eachTile;
            }
        }
        //////////////////////////////////////////////////////////////////////////////////
        
        
        ///////////////////////////////////////////////////////////////////////////////
        /// saving the 8 lines which we'll loop through them later to check the winner.
        ///////////////////////////////////////////////////////////////////////////////
        // horizontally
        for (int y=0; y<3; y++) {
            winnerVect.add(new WinnerLine(board[0][y], board[1][y], board[2][y]));
        }
        // vertically
        for (int x=0; x<3; x++) {
            winnerVect.add(new WinnerLine(board[x][0], board[x][1], board[x][2]));
        }
        // diagonal \
        winnerVect.add(new WinnerLine(board[0][0], board[1][1], board[2][2]));
        // diagonal /
        winnerVect.add(new WinnerLine(board[2][0], board[1][1], board[0][2]));
        ///////////////////////////////////////////////////////////////////////////////
        
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    //////////////////////////////////////////////////////////////////
    /// loop through the vector of possible lines to check the winner
    //////////////////////////////////////////////////////////////////
    private void isWinner(){
        for (WinnerLine line : winnerVect) {
            if (line.isComplete()) {
                isOver = true;
                break;
            }
        }
    }
    
    /////////////////
    // start new Game
    /////////////////
//    private void startNewGame() {
//        isOver = false;
//        turnX = true;
//        for (int y = 0; y < 3; y++) {
//            for (int x = 0; x < 3; x++) {
//                board[x][y].text.setText("");
//            }
//        }
//    }
    
    //////////////////////////////////////////////////
    /// for dealing with every tile as a separate pane
    //////////////////////////////////////////////////
    private class BoardTile extends StackPane {
        private Text text = new Text();
        
        public BoardTile(){
            Rectangle rect = new Rectangle(200, 200);
            rect.setFill(null);
            rect.setStroke(Color.BLACK);
            text.setFont(Font.font(72));
            getChildren().addAll(rect, text);
            
            setOnMouseClicked(event -> {
                if (isOver)
                    return;
                if ("".equals(getValue())) {
                    if (turnX) {
                        drawX();
                        turnX = false;
                    } else {
                        drawO();
                        turnX = true;
                    }
                    isWinner();
                }
            });
            
            setOnMouseMoved(event -> {
                setCursor(Cursor.HAND);
            });
        }
        
        private void drawX() {
            text.setText("X");
        }
        private void drawO() {
            text.setText("O");
        }
        
        public String getValue() {
            return text.getText();
        }
        
    }
    
    ////////////////////////////////////////////
    /// to check the equality of the three tiles
    ////////////////////////////////////////////
    private class WinnerLine {
        private BoardTile[] tiles;
        public WinnerLine(BoardTile... tiles) {
            this.tiles = tiles;
        }
        
        public boolean isComplete() {
            if (tiles[0].getValue().isEmpty())
                return false;
            
            return tiles[0].getValue().equals(tiles[1].getValue())
                    && tiles[0].getValue().equals(tiles[2].getValue());
        }
    }
    
//    @Override
//    public void start(Stage stage) {
//
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//
//            Scene scene = new Scene(root);
//
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException ex) {
//            Logger.getLogger(TicTacToeGame.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public static void main(String[] args) {
        launch(args);
    }

}

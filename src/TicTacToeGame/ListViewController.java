package TicTacToeGame;

import PlayWithComputer.PlayWithComputerController;
import TwoPlayersGame.TwoPlayersBoardController;
import networking.*;
import helpers.DbManager;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.util.Callback;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import login.LoginFxmlController;
import models.Player;

public class ListViewController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button playWithComputer;

    @FXML
    private Button challenge;

    @FXML
    private ListView listView;
    private static DbManager db;
    private static Client client;
    private static Thread th;
    private static String opponent;
    private ObservableList observableList = FXCollections.observableArrayList();

    public ListViewController() {

        db = new DbManager();
        try {
            if (db.connect()) {
                System.out.println("DB connected");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ListViewController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        observableList.setAll(db.getAllPlayers());

        this.createNewClientThread();

    }

    public void setListView() {

        listView.setItems(observableList);

        listView.setCellFactory(
                new Callback<ListView<Player>, javafx.scene.control.ListCell<Player>>() {
            @Override
            public ListCell<Player> call(ListView<Player> listView) {
                return new ListViewCell();
            }
        });

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                if (listView.getSelectionModel().getSelectedItem() != null) {

                    opponent = ((Player) listView.getSelectionModel().getSelectedItem()).getEmail();
                    System.out.println("clicked on " + ((Player) listView.getSelectionModel().getSelectedItem()).getEmail());

                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'viewlist.fxml'.";
        setListView();
    }

    @FXML
    public void handelPlayWithComputerButtonClicked(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PlayWithComputer/PlaywithComputer.fxml"));
        Parent root = loader.load();
        PlayWithComputerController controller = loader.getController();

        Scene scene = new Scene(root);
        Stage stage = TicTacToeGame.getgStage();
        stage.setScene(scene);
        stage.setOnShowing((WindowEvent e) -> controller.init());
        stage.show();
    }

    @FXML
    public void handelChallengeButtonClicked(ActionEvent event) {

        client.send("challenge$" + opponent);

    }

    private static void createNewClientThread() {
        client = LoginFxmlController.getClient();

        th = new Thread(new Runnable() {
            @Override
            public void run() {
                String msg;
                msg = client.recieve();
                
                String[] separatedMsg = msg.split("\\$");
                if (separatedMsg[0].equalsIgnoreCase("challenge")) {
                    Platform.runLater(new Runnable() {
                    
                        @Override
                        public void run() {
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Challenge Confirmation");
                            alert.setHeaderText(separatedMsg[1] + " " + separatedMsg[2]);
                            alert.setContentText("Are you ok with this?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                client.send("accepted$" + separatedMsg[1]);
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/TwoPlayersGame/TwoPlayersBoard.fxml"));
                                    Parent root = loader.load();
                                    TwoPlayersBoardController controller = loader.getController();
                                    Scene scene = new Scene(root);
                                    Stage stage = TicTacToeGame.getgStage();
                                    stage.setScene(scene);
                                    //stage.setOnShowing((e) -> controller.init(separatedMsg[1]));
                                    controller.init(separatedMsg[1]);
                                    stage.show();
                                }catch (IOException ex) {
                                    Logger.getLogger(ListViewController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                            }
                        }
                    });
                } else if (separatedMsg[0].equalsIgnoreCase("accepted")) {
                    try {
                        db.updateStatus(2, client.getUsername());
                        Platform.runLater(new Runnable() {
                            
                            @Override
                            public void run() {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/TwoPlayersGame/TwoPlayersBoard.fxml"));
                                    Parent root = loader.load();
                                    TwoPlayersBoardController controller = loader.getController();
                                    Scene scene = new Scene(root);
                                    Stage stage = TicTacToeGame.getgStage();
                                    controller.init(separatedMsg[1]);
                                    controller.setPlayerMark(1);
                                    stage.setScene(scene);
                                    stage.show();
                                }catch (IOException ex) {
                                    Logger.getLogger(ListViewController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    } catch (SQLException ex) {
                        Logger.getLogger(ListViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                th.stop();
            }
        });

        th.start();
    }
}

class ListViewCell extends ListCell<Player> {

    @Override
    public void updateItem(Player p, boolean empty) {
        super.updateItem(p, empty);
        if (p != null) {
            Data data = new Data();
            data.setInfo(p);
            setGraphic(data.getBox());
            if (p.getStatus() != 1) {
                this.setDisable(true);
            }
        }
    }
}

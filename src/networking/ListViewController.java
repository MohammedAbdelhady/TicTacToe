/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import helpers.DbManager;
import java.net.URL;
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
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import models.Player;

public class ListViewController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button start;
    
    @FXML
    private Button stop;

    @FXML
    private ListView listView;
    private DbManager db;
    Server server;
    Thread serverThread;
    private ObservableList observableList = FXCollections.observableArrayList();

    public ListViewController(){
       
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        assert listView != null : "fx:id=\"listView\" was not injected: check your FXML file 'viewlist.fxml'.";
        setListView();
    }

    @FXML
    public void handelStartServerButtonClicked(ActionEvent event){
    
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                
                server = new Server();
                server.start();
    
            }
        });
        
        serverThread.start();
    
    }
    
    @FXML
    public void handelStopServerButtonClicked(ActionEvent event){
       
        if(server != null){
            server.stop();
            serverThread.stop();
        }
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
            if(p.getStatus() != 1)
                this.setDisable(true);
        }
    }
}

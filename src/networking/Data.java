package networking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Player;

public class Data {

    @FXML
    private HBox hBox;
    @FXML
    private ImageView imageView;
    @FXML
    private Label name;
    @FXML
    private Label score;

    public Data() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listCellItem.fxml"));
        fxmlLoader.setController(this);
        try {
            hBox = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(Player p) {

        if (p.getStatus() == 1) {
            imageView.setImage(new Image("res/online.png"));
                    
        } else if (p.getStatus() == 0) {
            imageView.setImage(new Image("res/offline.png"));
        }
        name.setText(p.getfirstName() + " " + p.getlastName());
        score.setText("" + p.getScore());
    }

    public HBox getBox() {
        return hBox;
    }
}

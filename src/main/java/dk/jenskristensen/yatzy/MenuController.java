package dk.jenskristensen.yatzy;

import java.io.IOException;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    private void switchToGame() throws IOException {
        App.setRoot("gameScene");
    }
}

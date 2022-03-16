module dk.jenskristensen.yatzy {
    requires javafx.controls;
    requires javafx.fxml;

    opens dk.jenskristensen.yatzy to javafx.fxml;
    exports dk.jenskristensen.yatzy;
}

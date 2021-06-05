package chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextArea textField;
    @FXML
    private TextField messageField;
    @FXML
    private Button actionButton;

    @FXML
    public void sendMeesage(ActionEvent actionEvent) {
        textField.appendText(messageField.getText());
    }
}

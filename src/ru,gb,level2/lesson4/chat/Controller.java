package chat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class Controller {


    @FXML
    public ListView userList;
    @FXML
    private TextArea chatTextArea;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private Button sendMessageButton;

    @FXML
    public void sendMessage(ActionEvent actionEvent) {
        chatTextArea.appendText(messageTextArea.getText());
        chatTextArea.appendText(System.lineSeparator());
        messageTextArea.clear();
    }
}

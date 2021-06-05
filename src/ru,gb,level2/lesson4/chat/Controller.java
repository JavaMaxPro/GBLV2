package chat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
    public void sendMessage() {
        if(!userList.getSelectionModel().isEmpty()){
            String selUser = new String(String.valueOf(userList.getSelectionModel().getSelectedItems()));
            chatTextArea.appendText(selUser + " : ");
        }
        chatTextArea.appendText(messageTextArea.getText());
        chatTextArea.appendText(System.lineSeparator());
        messageTextArea.clear();
    }

   @FXML
    public void sendMessageAreaP(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            keyEvent.consume();
            if(keyEvent.isShiftDown()){
                messageTextArea.appendText(System.lineSeparator());
            }
            else {
                sendMessage();
            }
        }
    }

}

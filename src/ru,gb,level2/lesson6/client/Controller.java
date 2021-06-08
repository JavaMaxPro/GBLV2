package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class Controller {


    @FXML
    public ListView userList;
    @FXML
    private TextArea chatTextArea;
    @FXML
    private TextArea messageTextArea;
    @FXML
    private Button sendMessageButton;

    private NetworkClient networkClient;

    @FXML
    public void sendMessage() {
        String message = messageTextArea.getText();
        try {
            networkClient.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();

        }
        appendMessageToChat(message);
    }

    private void appendMessageToChat(String message) {
        if(!userList.getSelectionModel().isEmpty()){
            String selUser = new String(String.valueOf(userList.getSelectionModel().getSelectedItems()));
            chatTextArea.appendText(selUser + " : ");
        }
        chatTextArea.appendText(message);
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

    public void setNetworkClient(NetworkClient networkClient) {
        this.networkClient = networkClient;
    }
}

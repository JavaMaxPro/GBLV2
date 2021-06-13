package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ClientChat extends Application {


    public static final String NETWORK_ERROR_TITLE = "Сетевая ошибка";
    public static final String NETWORK_ERROR_CONNETCION_TYPE = "Невозможно установить сетевое соединение ";
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sample.fxml"));

        Parent root = fxmlLoader.load();
        this.primaryStage.setTitle("Chat");
        primaryStage.setScene(new Scene(root));
        setStageForSecondScreen(primaryStage);

        Controller controller = fxmlLoader.getController();
        controller.userList.getItems().addAll("user 1", "user2 ");
        primaryStage.show();

        Controller controller1 = fxmlLoader.getController();

        connectToServer(controller1);
    }

    private void connectToServer(Controller controller1) {
            NetworkClient networkClient = new NetworkClient();
            boolean result = networkClient.connect();
            if(!result){
                String errMsg ="Не удалось установить соединение с сервером!";
                showNetworkErrorDialog(NETWORK_ERROR_CONNETCION_TYPE,errMsg);
                return;
            }

            controller1.setNetworkClient(networkClient);
            controller1.setApplication(this);

            primaryStage.setOnCloseRequest(windowEvent -> networkClient.close());
    }

    private void showErrorDialog(String title, String type, String details) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(type);
        alert.setContentText(details);
        alert.showAndWait();
    }

    public void showNetworkErrorDialog(String type, String details) {
        showErrorDialog(NETWORK_ERROR_TITLE, type, details);
    }


    private void setStageForSecondScreen(Stage primaryStage) {
        Screen secondScreen = getSecondScreen();
        Rectangle2D bounds = secondScreen.getBounds();
        primaryStage.setX(bounds.getMinX() + (bounds.getWidth() - 300) / 2);
        primaryStage.setY(bounds.getMinY() + (bounds.getHeight() - 200) / 2);
    }

    private Screen getSecondScreen() {
        for (Screen screen : Screen.getScreens()) {
            if (!screen.equals(Screen.getPrimary())) {
                return screen;
            }
        }
        return Screen.getPrimary();
    }


    public static void main(String[] args) {

        launch(args);
    }
}

package org.uaspbo2;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class SceneController {

    public static final Deque<Scene> sceneStack = new ArrayDeque<>();
    private final Controller controller = new Controller();

    public void goTo(String fxmlPath, Event event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        sceneStack.push(stage.getScene());


        Parent root = FXMLLoader.load(getClass().getResource("/org/uaspbo2/" + fxmlPath));
        stage.setScene(new Scene(root));
        stage.show();
    }



    public void switchToSceneSetor(ActionEvent event) throws IOException {
        goTo("setor-page.fxml", event);
    }

    public void switchToSceneTarikTunai(ActionEvent event) throws IOException {
        goTo("tarik-tunai-page.fxml", event);
    }

    public void switchToSceneHistoryTransaksi(ActionEvent event) throws IOException {
        goTo("history-transaksi-page.fxml", event);
    }


}

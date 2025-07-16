package org.uaspbo2;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Controller {


//    public static final ServicesOperation SERVICES_OP;
//    static {                                       // static initializer
//        try {
//            SERVICES_OP = new ServicesOperation();
//        } catch (SQLException e) {
//            throw new ExceptionInInitializerError(e); // fail‑fast kalau koneksi gagal
//        }
//    }

    public void goBack(Event event) {
        if (SceneController.sceneStack.isEmpty()) {
            System.out.println("Tidak ada scene sebelumnya.");
            return;
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(SceneController.sceneStack.pop());
        stage.show();
    }

    public static void clearHistory() {
        SceneController.sceneStack.clear();
    }
}

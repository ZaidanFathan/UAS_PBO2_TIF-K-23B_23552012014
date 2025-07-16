package org.uaspbo2;


import javafx.event.ActionEvent;
import javafx.scene.control.*;
import org.uaspbo2.ServicesOperation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class registerController implements Initializable {
    ServicesOperation svo = ServicesOperation.getInstance();
    SceneController sceneController = new SceneController();
    Controller control = new Controller();
    @FXML
    private Button backButton;

    @FXML
    private ChoiceBox<String> tipeTabungan;
    private String[] type = {"Tabungan", "Giro"};

    @FXML
    public TextField username;
    public PasswordField password;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backButton.setOnMouseClicked(e-> {
            control.goBack(e);
        });

        tipeTabungan.getItems().addAll(type);
    }

    public void register(ActionEvent event) throws  IOException{
        String username = this.username.getText();
        String password = this.password.getText();
        String tipe_tabungan = tipeTabungan.getValue();

        if (svo.register(username, password, tipe_tabungan)){
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setTitle("Registrasi Berhasil");
            ok.setContentText("Pendaftaran berhasil! Silakan login menggunakan akun baru Anda.");
            ok.showAndWait();

            try {
                sceneController.goTo("login-page.fxml", event);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

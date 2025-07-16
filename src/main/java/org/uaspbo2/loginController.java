package org.uaspbo2;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {

    ServicesOperation svo = ServicesOperation.getInstance();
    @FXML
    private Hyperlink registerLink;
    public TextField username;
    public PasswordField password;
//    Controller control = new Controller();
    SceneController sceneController = new SceneController();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        registerLink.setOnMouseClicked(e -> {
            try{
                sceneController.goTo("registrasi.fxml", e);
            }catch (IOException error) {
                error.printStackTrace();
            }
        });
    }

    public void login(ActionEvent event)  throws IOException
    {
        String username = this.username.getText();
        String password = this.password.getText();


        if (svo.login(username,password)) {
            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setTitle("Login Berhasil");
            ok.setHeaderText(null);
            ok.setContentText("Selamat datang, " + username + "!");
            ok.showAndWait();                            // modal (blok sampai user klik OK)

            // lanjut pindah scene setelah dialog ditutup
            try {
                svo.setUsername(username);
                sceneController.goTo("Main.fxml", event);
            } catch (IOException e) {
                e.printStackTrace();                     // atau tampilkan dialog error lain
            }
        }else{
            Alert gagal = new Alert(Alert.AlertType.ERROR);
            gagal.setTitle("Login Gagal");
            gagal.setHeaderText(null);
            gagal.setContentText("Username atau password salah.");
            gagal.showAndWait();
        }
    }


}

package org.uaspbo2;


import javafx.event.Event;
import javafx.scene.control.*;
//import org.uaspbo2.ServicesOperation;
import javafx.fxml.FXML;




public class TarikTunaiController {
    ServicesOperation svo = ServicesOperation.getInstance();
    Controller control = new Controller();


    @FXML
    TextField tfNominal;

    @FXML
    public void initialize() {
        // Hanya izinkan angka
        tfNominal.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfNominal.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }



    public void goBack(Event event) {
        control.goBack(event);
    }

    public void tarikTunai() {
        int totalTF = Integer.parseInt(tfNominal.getText());

        svo.tarikTunai(totalTF);
    }
}

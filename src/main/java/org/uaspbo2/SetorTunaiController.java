package org.uaspbo2;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
//import org.uaspbo2.ServicesOperation;
import javafx.fxml.FXML;




public class SetorTunaiController {
    ServicesOperation svo = ServicesOperation.getInstance();
    Controller control = new Controller();

    @FXML
    TextField strNominal;

    @FXML
    public void initialize() {
        // Hanya izinkan angka
        strNominal.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                strNominal.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }


    public void setor() {
        String nominal = strNominal.getText();
            svo.setor(Integer.parseInt(nominal));
    }

    public void goBack(Event event) {
        control.goBack(event);
    }
}

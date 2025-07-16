package org.uaspbo2;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.*;
import org.uaspbo2.ServicesOperation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HistoryTransaksiController{
    ServicesOperation svo = ServicesOperation.getInstance();
    Controller control = new Controller();
    @FXML
    public void initialize() {
        // Hubungkan kolom ke index data dari database
        colTipe.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        colJumlah.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        colTanggal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));

        // Panggil data transaksi
        getTransaksi();
    }


    @FXML private TableView<ObservableList<String>> tableTransaksi;
    @FXML private TableColumn<ObservableList<String>, String> colTipe;
    @FXML private TableColumn<ObservableList<String>, String> colJumlah;
    @FXML private TableColumn<ObservableList<String>, String> colTanggal;


    public void getTransaksi() {
        svo.historyTransaksi(tableTransaksi);
    }

    public void goBack(Event event) {
        control.goBack(event);
    }

}

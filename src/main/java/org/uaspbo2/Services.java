package org.uaspbo2;

import javafx.scene.control.TableView;

public interface Services {
    void setor(int jumlahSetoran);
    void tarikTunai(int jumlahTarikTunai);
    boolean register(String username, String password, String jenisRekening);
    boolean login(String username, String password);
    void historyTransaksi(TableView tableView);
}

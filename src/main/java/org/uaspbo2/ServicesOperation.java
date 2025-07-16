package org.uaspbo2;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * Layanan akses database – pola Singleton.
 * Akses global via ServicesOperation.getInstance().
 */
public final class ServicesOperation implements Services {

    /*───────────────────────────  SINGLETON  ───────────────────────────*/
    /** ❶ Tahan koneksi hanya satu kali seumur aplikasi */
    private final Connection connection;
    private String userName;
    private int userId;

    /** ❷ Konstruktor privat → mencegah instansiasi di luar kelas */
    private ServicesOperation() throws SQLException {
        connection = databaseConnection.getConnection();
    }

    public void setUsername(String username) {
        this.userName = username;
    }


    private static class Holder {
        private static final ServicesOperation INSTANCE = createInstance();

        private static ServicesOperation createInstance() {
            try {
                return new ServicesOperation();
            } catch (SQLException e) {
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    /** ❹ Panggil ini di mana saja untuk mendapatkan satu‑satunya instance */
    public static ServicesOperation getInstance() {
        return Holder.INSTANCE;
    }
    /*────────────────────────  END SINGLETON  ─────────────────────────*/

    /*==========================  SERVICE API  ==========================*/



    @Override
    public boolean login(String username, String password) {
        // Contoh sederhana – ganti dengan hash & prepared statement!
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.userId = rs.getInt("id");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    @Override
    public void setor(int jumlahSetoran) {
        String q0 = "SELECT rekening.id AS rekening_id " + "FROM nasabah " + "JOIN rekening ON rekening.id = nasabah.rekening_id " + "WHERE nasabah.user_id = ?;";
        String q1 = "UPDATE rekening SET saldo = saldo + ? WHERE id = ?";
        String q2 = "INSERT INTO transaksi (rekening_id, tipe, jumlah, tanggal, user_id) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(q1);
             PreparedStatement trx = connection.prepareStatement(q2);
             PreparedStatement rkn = connection.prepareStatement(q0)
             ) {
            rkn.setInt(1,this.userId);

            try(ResultSet res = rkn.executeQuery()) {
                if (res.next()) {
                    stmt.setInt(1, jumlahSetoran);
                    stmt.setInt(2, res.getInt("rekening_id"));
                    stmt.executeUpdate();

                    trx.setInt(1, res.getInt("rekening_id"));
                    trx.setString(2, "setor");
                    trx.setInt(3, jumlahSetoran);
                    trx.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                    trx.setInt(5, this.userId);
                    trx.executeUpdate();

                    Alert ok = new Alert(Alert.AlertType.INFORMATION);
                    ok.setTitle("Berhasil mensetorkan tunai");
                    ok.setHeaderText(null);
                    ok.setContentText("Sebesar : " + jumlahSetoran);
                    ok.showAndWait();
                }else{
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText(null);
                    error.setContentText("Terjadi kesalahan pada saat proses setor tunai");
                    error.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tarikTunai(int jumlahTarikTunai) {
        String q0 = "SELECT rekening.id AS rekening_id, rekening.saldo AS saldo " + "FROM nasabah " + "JOIN rekening ON rekening.id = nasabah.rekening_id " + "WHERE nasabah.user_id = ?;";
        String q1 = "UPDATE rekening SET saldo = saldo - ? WHERE id = ?";
        String q2 = "INSERT INTO transaksi (rekening_id, tipe, jumlah, tanggal, user_id) VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(q1);
             PreparedStatement trx = connection.prepareStatement(q2);
             PreparedStatement rkn = connection.prepareStatement(q0)) {

            rkn.setInt(1, this.userId);
            try (ResultSet rs = rkn.executeQuery()){
                if (rs.next() && rs.getInt("saldo") > jumlahTarikTunai) {
                    stmt.setInt(1, jumlahTarikTunai);
                    stmt.setInt(2, rs.getInt("rekening_id"));
                    stmt.executeUpdate();

                    trx.setInt(1, rs.getInt("rekening_id"));
                    trx.setString(2, "tarik tunai");
                    trx.setInt(3, jumlahTarikTunai);
                    trx.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                    trx.setInt(5, this.userId);
                    trx.executeUpdate();

                    Alert ok = new Alert(Alert.AlertType.INFORMATION);
                    ok.setTitle("Berhasil menarik tunai");
                    ok.setHeaderText(null);
                    ok.setContentText("Anda menarik saldo sebesar, " + jumlahTarikTunai);
                    ok.showAndWait();
                }else{
                    Alert err = new Alert(Alert.AlertType.ERROR);
                    err.setTitle("Gagal meneraik tunai");
                    err.setHeaderText(null);
                    err.setContentText("Saldo anda tidak mencukupi untuk menarik tunai sebesar , " + jumlahTarikTunai);
                    err.showAndWait();
                }
            }
        } catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.WARNING);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Terjadi kesalahan pada saat menarik tunai");
            error.showAndWait();
            e.printStackTrace();
        }
    }

    @Override
    public void historyTransaksi(TableView tableView) {
        String query = "SELECT tipe, jumlah, tanggal FROM transaksi WHERE user_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, this.userId);
            ResultSet rs = statement.executeQuery();

            tableView.getItems().clear(); // Jangan clear kolom

            ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            tableView.setItems(data);

        } catch (SQLException err) {
            err.printStackTrace();
        }
    }



    @Override
    public boolean register(String username, String password, String jenisRekening) {
        String q1 = "INSERT INTO rekening (jenis, saldo) VALUES (?,?)";
        String q2 = "INSERT INTO nasabah (nama, rekening_id, user_id) VALUES (?,?,?)";
        String q3 = "INSERT INTO users (username, password) VALUES (?, ?)";
        String findDuplicate = "SELECT COUNT(*) FROM users WHERE username = ?";



        try (PreparedStatement stmt = connection.prepareStatement(q1, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmt2 = connection.prepareStatement(q2);
             PreparedStatement stmt3 = connection.prepareStatement(q3, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement stmt4 = connection.prepareStatement(findDuplicate);
             ) {

            /** Check duplikasi **/
            stmt4.setString(1, username);
            ResultSet dpct = stmt4.executeQuery();

            if (dpct.next() && dpct.getInt(1) > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Maaf, Username yang Anda pilih sudah digunakan.");
                alert.setHeaderText(null);
                alert.setContentText("Silakan coba username lain.");
                alert.showAndWait();
                return false;
            }else{
                stmt3.setString(1, username);
                stmt3.setString(2, password);
                stmt3.executeUpdate();
            }


            /** membuat rekening **/
            stmt.setString(1, jenisRekening);
            stmt.setInt(2, 0);
            stmt.executeUpdate();



            try (ResultSet keys = stmt.getGeneratedKeys();
            ResultSet id = stmt3.getGeneratedKeys()) {
                if (keys.next() && id.next()) {
                    int rekeningId = keys.getInt(1);
                    int userId = id.getInt(1);
                    stmt2.setString(1, username);
                    stmt2.setInt(2, rekeningId);
                    stmt2.setInt(3, userId);
                    stmt2.executeUpdate();
                    return true;
                }
                return false;
            }
        } catch (SQLIntegrityConstraintViolationException dup) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Maaf, Username yang Anda pilih sudah digunakan.");
            alert.setHeaderText(null);
            alert.setContentText("Silakan coba username lain.");
            alert.showAndWait();
            return false;
        }catch (SQLException e) {
            e.printStackTrace();
            Alert gagal = new Alert(Alert.AlertType.ERROR);
            gagal.setTitle("Register gagal");
            gagal.setHeaderText(null);
            gagal.setContentText("Terjadi kesalahan pada saat membuat akun");
            gagal.showAndWait();
            return  false;
        }
    }
}

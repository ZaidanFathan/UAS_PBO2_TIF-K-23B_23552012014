package org.uaspbo2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main extends Application {
//    public static void main(String[] args) {
//        try {
//            ServicesOperation servicesOperation = new ServicesOperation();
//            Scanner scanner = new Scanner(System.in);
//
//            while (true) {
//                System.out.println("\n Menu:");
//                System.out.println("1. Setor tunai:");
//                System.out.println("2. Tarik Tunai");
//                System.out.println("3. Daftar");
//                System.out.println("4. Hitung bunga");
//                System.out.println("5. Exit:");
//                System.out.print("Choose an option: ");
//                int choice = scanner.nextInt();
//                scanner.nextLine();
//
//                String rekeningId;
//                String amount;
//
//                switch (choice) {
//                    case 1:
//                        System.out.print("Masukkan nomor rekening : ");
//                        rekeningId = scanner.nextLine();
//                        System.out.print("Masukkan jumlah uang yang ingin disetorkan : ");
//                        amount = scanner.nextLine();
//                        servicesOperation.setor(Integer.parseInt(rekeningId), Integer.parseInt(amount));
//                        break;
//
//                    case 2:
//                        System.out.print("Masukkan nomor rekening : ");
//                        rekeningId = scanner.nextLine();
//                        System.out.print("Masukkan jumlah  yang ingin anda tarik : ");
//                        amount = scanner.nextLine();
//                        servicesOperation.tarikTunai(Integer.parseInt(rekeningId), Integer.parseInt(amount));
//                        break;
//
//                    case 3:
//                        System.out.print("Masukkan nama lengkap anda : ");
//                        String namaNasabah = scanner.nextLine();
//
//                        System.out.print("Pilih jenis tabungan : \n");
//                        System.out.print("1. Giro \n");
//                        System.out.print("2. Tabungan \n");
//                        System.out.print("Silahkan pilih jenis tabungan yang anda inginkan: ");
//                        String jenisTabungan = scanner.nextLine();
//
//                        servicesOperation.createNasabah(Integer.parseInt(jenisTabungan), namaNasabah);
//                        break;
//                    case 4:
//                        System.out.print("Silakan pilih jenis tabungan untuk melihat perhitungan bunga yang berlaku: \n");
//                        System.out.print("1. Giro \n");
//                        System.out.print("2. Tabungan \n");
//                        System.out.print("Pilihan anda : ");
//                        String ch = scanner.nextLine();
//                        if (Integer.parseInt(ch) == 1) {
//                            Giro giro = new Giro();
//                            System.out.print(giro.hitungBunga());
//                        }else if(Integer.parseInt(ch) == 2) {
//                            Tabungan tabungan = new Tabungan();
//                            System.out.print(tabungan.hitungBunga());
//                        }else{
//                            System.out.print("Jenis tabungan yang Anda pilih tidak tersedia.");
//                        }
//                        break;
//                    case 5:
//                        System.out.print("Exiting...");
//                        System.exit(0);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}



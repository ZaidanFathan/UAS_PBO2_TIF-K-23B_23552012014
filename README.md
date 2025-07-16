<h1>Final Proyek Pemrograman Berorientasi Obyek 2</h1>
<ul>
  <li>Mata Kuliah: Pemrograman Berorientasi Obyek 2</li>
  <li>Dosen Pengampu: <a href="https://github.com/Muhammad-Ikhwan-Fathulloh">Muhammad Ikhwan Fathulloh</a></li>
</ul>

## Profil
<ul>
  <li>Nama: Muhammad Zaidan Fathan Abdullah</li>
  <li>NIM: 23552012014</li>
  <li>Studi Kasus: Kasir Bank</li>
</ul>

## Judul Studi Kasus
<p>Kasir Bank</p>

## Penjelasan Studi Kasus
<p>Proyek Kasir Bank adalah sebuah aplikasi yang digunakan untuk membantu proses transaksi keuangan di sebuah bank secara efisien. Aplikasi ini bertindak sebagai sistem kasir yang melayani kebutuhan transaksi nasabah seperti penyetoran uang, penarikan, pembuatan akun, dan melihat bunga.</p>

## Penjelasan 4 Pilar OOP dalam Studi Kasus

### 1. Inheritance
<code>
  public class Tabungan extends Rekening{
    @Override
    public String hitungBunga() {
        return "Bunga sebesar 20%";
    }
}
</code>

### 2. Encapsulation
<code>
  private Connection connection;
</code>

### 3. Polymorphism
<code>
  public interface Services {
    void setor(int rekeningId, int jumlahSetoran);
    void tarikTunai(int rekeningId, int jumlahTarikTunai);
    void createNasabah(int jenisTabungan, String nama);
}
</code>
<code>
  public abstract String hitungBunga();
</code>

### 4. Abstract
<code>
  public abstract class Rekening {
  private int id;
  public abstract String hitungBunga();
}
</code>

## Demo Proyek
<ul>
<!--   <li>Github: <a href="https://github.com/ZaidanFathan/Kasir-Bank/">Github</a></li> -->
<!--   <li>Youtube: <a href="https://www.youtube.com/watch?v=bmrN65T-frI">Youtube</a></li>  -->
</ul>

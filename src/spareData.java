public class spareData {
  
    private String idBarang;
    private String idPelanggan;
    private String namaBarang;
    private Double hargaBarang;
    

    public spareData(String idBarang, String idPelanggan, String namaBarang, Double hargaBarang) {
        this.idBarang = idBarang;
        this.idPelanggan = idPelanggan;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
    }


    public String getIdBarang() {
        return idBarang;
    }


    public void setIdBarang(String idBarang) {
        this.idBarang = idBarang;
    }


    public String getIdPelanggan() {
        return idPelanggan;
    }


    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }


    public String getNamaBarang() {
        return namaBarang;
    }


    public void setNamaBrang(String namaBarang) {
        this.namaBarang = namaBarang;
    }


    public Double getHargaBarang() {
        return hargaBarang;
    }


    public void setHargaBarang(Double hargaBarang) {
        this.hargaBarang = hargaBarang;
    }


   
}

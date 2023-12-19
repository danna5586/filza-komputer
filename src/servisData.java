public class servisData {
    private String idServis;
    private String idPelanggan;
    private Double biaya;
    private String merk;
    private String sparepart;

    public servisData(String idServis, String idPelanggan, Double biaya, String merk, String sparepart) {
        this.idServis = idServis;
        this.idPelanggan = idPelanggan;
        this.biaya = biaya;
        this.merk = merk;
        this.sparepart = sparepart;
    }

    public String getIdServis() {
        return idServis;
    }

    public void setIdServis(String idServis) {
        this.idServis = idServis;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }


    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }


    public Double getBiaya() {
        return biaya;
    }


    public void setBiaya(Double biaya) {
        this.biaya = biaya;
    }


    public String getMerk() {
        return merk;
    }


    public void setMerk(String merk) {
        this.merk = merk;
    }


    public String getSparepart() {
        return sparepart;
    }


    public void setSparepart(String sparepart) {
        this.sparepart = sparepart;
    }



}

public class pelangganData {
    private String ID;
    private String Nama;
    private String noTelp;
    private String tanggal;
    private String Keperluan;

    public pelangganData(String ID, String Nama, String noTelp, String tanggal, String keperluan) {
        this.ID = ID;
        this.Nama = Nama;
        this.noTelp = noTelp;
        this.tanggal = tanggal;
        this.Keperluan = keperluan;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

        public void setKeperluan(String keperluan) {
        Keperluan = keperluan;
    }
            public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

 
    public String getNoTelp() {
        return noTelp;
    }



    public String getTanggal() {
        return tanggal;
    }

        public String getKeperluan() {
        return Keperluan;
    }


    public void settanggal(String tanggal_perekrutan) {
        tanggal = tanggal_perekrutan;
    }

}

public class ID_Pelanggan_DATA {
    private int CustomerID;
    private String NamaPelanggan;
    private String AlamatPelanggan;
    private int NoHpPelanggan;
    private String EmailPelanggan;
    public int getCustomerID() {
        return CustomerID;
    }
    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }
    public String getNamaPelanggan() {
        return NamaPelanggan;
    }
    public void setNamaPelanggan(String namaPelanggan) {
        NamaPelanggan = namaPelanggan;
    }
    public String getAlamatPelanggan() {
        return AlamatPelanggan;
    }
    public void setAlamatPelanggan(String alamatPelanggan) {
        AlamatPelanggan = alamatPelanggan;
    }
    public int getNoHpPelanggan() {
        return NoHpPelanggan;
    }
    public void setNoHpPelanggan(int noHpPelanggan) {
        NoHpPelanggan = noHpPelanggan;
    }
    public String getEmailPelanggan() {
        return EmailPelanggan;
    }
    public void setEmailPelanggan(String emailPelanggan) {
        EmailPelanggan = emailPelanggan;
    }

    public ID_Pelanggan_DATA(int CustomerID, String NamaPelanggan, String AlamatPelanggan, int NoHpPelanggan, String EmailPelanggan){
        this.CustomerID = CustomerID;
        this.NamaPelanggan = NamaPelanggan;
        this.AlamatPelanggan = AlamatPelanggan;
        this.NoHpPelanggan = NoHpPelanggan;
        this.EmailPelanggan = EmailPelanggan;
        
    }
}

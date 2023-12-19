public class PembayaranData {
    private int PaymentID;
    private int InvoiceID;
    private String Date;
    private double Amount;

    public int getPaymentID() {
        return PaymentID;
    }
    public void setPaymentID(int paymentID) {
        PaymentID = paymentID;
    }
    public int getInvoiceID() {
        return InvoiceID;
    }
    public void setInvoiceID(int invoiceID) {
        InvoiceID = invoiceID;
    }
    public String getDate() {
        return Date;
    }
    public void setDate(String date) {
        Date = date;
    }
    public double getAmount() {
        return Amount;
    }
    public void setAmount(double amount) {
        Amount = amount;
    }

    public PembayaranData(int PaymentID, int InvoiceID, String Date, double Amount) {
        this.PaymentID = PaymentID;
        this.InvoiceID = InvoiceID;
        this.Date = Date;
        this.Amount = Amount;
    }

}
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class Pelanggan implements Initializable { // implements initia

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getList();
        ID_Column.setCellValueFactory(new PropertyValueFactory<pelangganData, String>("ID"));
        Nama_Column.setCellValueFactory(new PropertyValueFactory<pelangganData, String>("Nama"));
        telp_Column.setCellValueFactory(new PropertyValueFactory<pelangganData, String>("noTelp"));
        tanggal_Column.setCellValueFactory(new PropertyValueFactory<pelangganData, String>("tanggal"));
        keperluan_Column.setCellValueFactory(new PropertyValueFactory<pelangganData, String>("keperluan"));
        Table.setItems(listTemp);

        Table.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                // Get the selected row data
                pelangganData selectedPelanggan = Table.getSelectionModel().getSelectedItem();

                if (selectedPelanggan != null) {
                    // Set the data to the input fields for editing
                    ID_Field.setText(selectedPelanggan.getID());
                    Nama_Field.setText(selectedPelanggan.getNama());
                    noTelp_Field.setText(selectedPelanggan.getNoTelp());
                    tanggal_Field.setText(selectedPelanggan.getTanggal());
                    //keperluan_Field.setText(selectedPelanggan.getKanggal());
                }
            }
        });
    }

    ObservableList<pelangganData> listTemp = FXCollections.observableArrayList(); // buat table view

    public void deleteRow(String ID) {
        // Informasi koneksi database
        String jdbcUrl = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false;";

        // Data yang ingin dihapus
        String id = ID;

        try {
            // Membuat koneksig
            Connection connection = DriverManager.getConnection(jdbcUrl);

            try {
                // Hapus data dari tabel ServisDetail
                String deleteServisDetailQuery = "DELETE FROM Pelanggan WHERE idPelanggan = ?;";
                try (PreparedStatement preparedStatementServisDetail = connection
                        .prepareStatement(deleteServisDetailQuery)) {
                    preparedStatementServisDetail.setString(1, id);
                    preparedStatementServisDetail.executeUpdate();
                }
                // Commit transaksi jika berhasil
                connection.commit();
                System.out.println("Data berhasil dihapus.");
            } catch (SQLException e) {
                // Rollback transaksi jika terjadi kesalahan
                showWarning("Warning", "Pastikan Semua Transaksi yang terhubung dihapus");
                connection.rollback();
                e.printStackTrace();
            } finally {
                // Kembalikan otomatis commit ke kondisi awal
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  @FXML
    private Button Add_Button;

    @FXML
    private Label Data_Karyawan;

    @FXML
    private Button Delete_Button;

    @FXML
    private Button Find_Button;

    @FXML
    private TableColumn<pelangganData, String> ID_Column;

    @FXML
    private TextField ID_Field;

    @FXML
    private Label ID_Text;

    @FXML
    private MenuItem Invoice;

    @FXML
    private MenuBar Menu_Bar;

    @FXML
    private MenuItem Menu_Customer;

    @FXML
    private MenuItem Menu_Employee;

    @FXML
    private MenuItem Menu_Employee1;

    @FXML
    private Menu Menu_File;

    @FXML
    private MenuItem Menu_Logout;

    @FXML
    private MenuItem Menu_Payment;

    @FXML
    private MenuItem Menu_Shipment;

    @FXML
    private TableColumn<pelangganData, String> Nama_Column;

    @FXML
    private TextField Nama_Field;

    @FXML
    private Label Nama_Text;

    @FXML
    private Label Nama_Text1;

    @FXML
    private Label Nama_Text111;

    @FXML
    private Button Refresh_Button1;

    @FXML
    private TextField Search_Field;

    @FXML
    private Label Search_Text;

    @FXML
    private SeparatorMenuItem Seperator_Menu;

    @FXML
    private TableView<pelangganData> Table;

    @FXML
    private Button Update_Button;

    @FXML
    private TableColumn<pelangganData, String> keperluan_Column;

    @FXML
    private TextField noTelp_Field;

    @FXML
    private CheckBox servCheck;

    @FXML
    private CheckBox spareCheck;

    @FXML
    private TableColumn<pelangganData, String> tanggal_Column;

    @FXML
    private TextField tanggal_Field;

    @FXML
    private TableColumn<pelangganData, String> telp_Column;

    @FXML
    void servChecked(ActionEvent event) {

    }

    @FXML
    void spareChecked(ActionEvent event) {

    }



    @FXML
    void Add_Btn(ActionEvent event) throws IOException, SQLException {

        String Servis = " ";

        if(servCheck.isSelected()&&spareCheck.isSelected()){
            Servis ="Servis & Sparepart";
        }else if(servCheck.isSelected()){
            Servis ="Servis";
        }else if(spareCheck.isSelected()){
            Servis = "Sparepart";
        }else{

        }

        String DB_URL = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false;";

        try (Connection connection = DriverManager.getConnection(DB_URL)) {

            // Buka koneksi
            System.out.println("Connecting to database...");

            // Persiapkan pernyataan SQL untuk insert
            String sql = "INSERT INTO Pelanggan (idPelanggan, namaPelanggan , noTelp, tanggalTransaksi, keperluan  ) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                String ID = ID_Field.getText();
                String Nama = Nama_Field.getText();
                String No_Telp = noTelp_Field.getText();
                String tanggal = tanggal_Field.getText();
                String Keperluan = Servis;

                // Set nilai parameter
                preparedStatement.setString(1, ID);
                preparedStatement.setString(2, Nama);
                preparedStatement.setString(3, No_Telp);
                preparedStatement.setDate(4, Date.valueOf("2023-01-01"));
                preparedStatement.setString(5, Keperluan);

                // Eksekusi pernyataan
                preparedStatement.executeUpdate();
                clearFields();

                System.out.println("Employee added successfully!");
                getList();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    @FXML
    void Delete_Btn(ActionEvent event) {
        int index = Table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        String IdToDelete = ID_Column.getCellData(index).toString();
        deleteRow(IdToDelete);
        getList();
    }

    @FXML
    void Refresh_Btn(ActionEvent event) {
        refreshTable();
        clearFields();
    }

    @FXML
    void Find_Btn(ActionEvent event) {
        String searchId = Search_Field.getText();
        if (searchId.isEmpty()) {
            showWarning("Warning", "Please enter an Employee ID for search.");
            return;
        }

        try {
            // Perform search operation
            searchPelangganByKeyword(searchId);
        } catch (SQLException e) {
            showErrorAlert("Error while searching for Employee: " + e.getMessage());
        }
    }

    @FXML
    void Logout_Btn(ActionEvent event) throws IOException {
        System.out.println("p");
        // root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        // stage = (Stage) Menu_Bar.getScene().getWindow();
        // scene = new Scene(root);
        // stage.setScene(scene);
        // stage.show();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Swtich_Customer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sparepart.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Switch_Payment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Pembayaran.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);  
        stage.show();
    }

    @FXML
    void Switch_Invoice(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Invoice.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);  
        stage.show();
    }

    @FXML
    void Switch_Sipment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("servis.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Update_Btn(ActionEvent event) {
        int index = Table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        String IdToUpdate = ID_Column.getCellData(index).toString();

        String Servis = "";

        if(servCheck.isSelected()&&spareCheck.isSelected()){
        Servis ="Servis & Sparepart";
        }else if(servCheck.isSelected()){
            Servis ="Servis";
        }else if(spareCheck.isSelected()){
            Servis = "Sparepart";
        }else{
            Servis=keperluan_Column.getCellData(index).toString();
        }

        pelangganData selectedPelanggan = Table.getSelectionModel().getSelectedItem();
        if (selectedPelanggan == null) {
            showWarning("Warning", "Please select a row for editing.");
            return;
        }
        selectedPelanggan.setID(ID_Field.getText());
        if(!Nama_Field.getText().equals("")){
            selectedPelanggan.setNama(Nama_Field.getText());
        }else{
            selectedPelanggan.setNama(Nama_Column.getCellData(index).toString());
        }if(!noTelp_Field.getText().equals("")){
            selectedPelanggan.setNoTelp(noTelp_Field.getText());
        }else{
            selectedPelanggan.setNoTelp(telp_Column.getCellData(index).toString());
        }if(!tanggal_Field.getText().equals("")){
            selectedPelanggan.settanggal(tanggal_Field.getText());
        }else{
            selectedPelanggan.settanggal(tanggal_Column.getCellData(index).toString());
        }
        selectedPelanggan.setKeperluan(Servis);

        updateEmployee(selectedPelanggan, IdToUpdate);
        System.out.println(selectedPelanggan.getID());
        clearFields();
        showSuccessAlert("Pelanggan data updated successfully!");
        refreshTable();
    }

    @FXML
    void Switch_Paket(ActionEvent event) throws IOException {
        System.out.println("p");
        root = FXMLLoader.load(getClass().getResource("Paket.fxml"));
        stage = (Stage) Menu_Bar.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void getList() {
        String url = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false;";
        listTemp.clear();
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Pelanggan";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Ambil data dari setiap kolom
                    String Id = resultSet.getString("idPelanggan");
                    String Nama = resultSet.getString("namaPelanggan");
                    String noTelp = resultSet.getString("noTelp");
                    String tanggal = resultSet.getString("tanggalTransaksi");
                    String keperluan = resultSet.getString("keperluan");

                    // Lakukan sesuatu dengan data, contoh: print ke konsol
                    listTemp.add(new pelangganData(Id, Nama, noTelp, tanggal, keperluan));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // FUNCTION RELATIVE

    private void searchPelangganByKeyword(String keyword) throws SQLException {
        String url = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false";
        listTemp.clear();
    
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Pelanggan WHERE " +
                         "idPelanggan LIKE ? OR namaPelanggan LIKE ? OR noTelp LIKE ? OR keperluan LIKE ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Ganti nilai keyword dengan kata kunci yang ingin Anda cari
                preparedStatement.setString(1, "%" + keyword + "%");
                preparedStatement.setString(2, "%" + keyword + "%");
                preparedStatement.setString(3, "%" + keyword + "%");
                preparedStatement.setString(4, "%" + keyword + "%");
    
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String idPelanggan = resultSet.getString("idPelanggan");
                        String namaPelanggan = resultSet.getString("namaPelanggan");
                        String noTelp = resultSet.getString("noTelp");
                        String tanggalTransaksi = resultSet.getString("tanggalTransaksi");
                        String keperluan = resultSet.getString("keperluan");
    
                        // Add the result to the list
                        listTemp.add(new pelangganData(idPelanggan, namaPelanggan, noTelp, tanggalTransaksi, keperluan));
                    }
    
                    // Update the table with the search result
                    Table.setItems(listTemp);
    
                    if (listTemp.isEmpty()) {
                        showAlert("Information", "Search Result", "No Pelanggan with keyword: " + keyword);
                    }
                }
            }
        }
    }

    private void updateEmployee(pelangganData Pelanggan, String IdToUpdate) {
        String jdbcUrl = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false;";

        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            // Persiapkan pernyataan SQL untuk update
            String sql = "UPDATE Pelanggan SET namaPelanggan = ?, noTelp = ?, tanggalTransaksi = ?, keperluan = ? WHERE idPelanggan = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Pelanggan.getNama());
                preparedStatement.setString(2, Pelanggan.getNoTelp());
                preparedStatement.setString(3, Pelanggan.getTanggal());
                preparedStatement.setString(4, Pelanggan.getKeperluan());
                preparedStatement.setString(5, IdToUpdate);

                // Eksekusi pernyataan
                int rowsAffected = preparedStatement.executeUpdate();

                // Commit the changes to the database
                if (rowsAffected > 0) {
                    System.out.println("Data berhasil diupdate.");
                } else {
                    System.out.println("Data tidak ditemukan untuk diupdate.");
                }
            }
        } catch (SQLException e) {
            // Handle the exception
            showErrorAlert("Error updating employee data in the database: " + e.getMessage());
        }
    }

    private void refreshTable() {
    getList(); 
    Table.setItems(listTemp); 
    }

    private void searchEmployeeById(String idPelanggan) throws SQLException {
    String url = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false;";
    listTemp.clear();

    try (Connection connection = DriverManager.getConnection(url)) {
        String sql = "SELECT * FROM Pelanggan WHERE idPelanggan = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, idPelanggan);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Ambil data dari setiap kolom
                    String Id = resultSet.getString("idPelanggan");
                    String Nama = resultSet.getString("namaPelanggan");
                    String noTelp = resultSet.getString("noTelp");
                    String tanggal = resultSet.getString("tanggalTransaksi");
                    String keperluan = resultSet.getString("keperluan");

                    // Lakukan sesuatu dengan data, contoh: print ke konsol
                    listTemp.add(new pelangganData(Id, Nama, noTelp, tanggal, keperluan));
                }
                // Update the table with the search result
                Table.setItems(listTemp);

                if (listTemp.isEmpty()) {
                    showAlert("Information", "Search Result", "No employee found with ID: " + idPelanggan);
                }
            }
        }
    }
}

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

     private void clearFields() {
        ID_Field.clear();
        Nama_Field.clear();
        noTelp_Field.clear();
        Search_Field.clear();
        tanggal_Field.clear();
        Search_Field.clear();
    }
    private void showWarning(String title, String content) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle(title);
      alert.setHeaderText(null);
      alert.setContentText(content);
      alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
       Alert alert = new Alert(Alert.AlertType.INFORMATION);
       alert.setTitle("Success");
       alert.setHeaderText(null);
       alert.setContentText(message);
       alert.showAndWait();
    }

    private void showErrorAlert(String message) {
       Alert alert = new Alert(Alert.AlertType.ERROR);
       alert.setTitle("Error");
       alert.setHeaderText(null);
       alert.setContentText(message);
       alert.showAndWait();
    }

}

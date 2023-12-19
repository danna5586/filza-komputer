import java.io.IOException;
import java.sql.Connection;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class Sparepart implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    String statuses;
    ObservableList<spareData> listTemp = FXCollections.observableArrayList(); // buat table view

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getList();
        Column_IDBarang.setCellValueFactory(new PropertyValueFactory<spareData, String>("idBarang"));
        Column_IDPelanggan.setCellValueFactory(new PropertyValueFactory<spareData, String>("idPelanggan"));
        Column_Nama.setCellValueFactory(new PropertyValueFactory<spareData, String>("namaBarang"));
        Column_Harga.setCellValueFactory(new PropertyValueFactory<spareData, Double>("hargaBarang"));

        Cust_Table.setItems(listTemp);

        Cust_Table.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                // Get the selected row data
                spareData Customer = Cust_Table.getSelectionModel().getSelectedItem();

                if (Customer != null) {
                    // Set the data to the input fields for editing
                    Field_IdBarang.setText(String.valueOf(Customer.getIdBarang()));
                    Field_IDPelanggan.setText(Customer.getIdPelanggan());
                    Field_NamaBarang.setText(Customer.getNamaBarang());
                    Field_Harga.setText(String.valueOf(Customer.getHargaBarang()));
                }
            }
        });
    }

    public void getList() {
        String url = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false";
        listTemp.clear();
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Sparepart";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Ambil data dari setiap kolom
                    String idBarang = resultSet.getString("idBarang");
                    String idPelanggan= resultSet.getString("idPelanggan");
                    String namaBarang = resultSet.getString("namaBarang");
                    Double hargaBarang = resultSet.getDouble("hargaBarang");

                    // Lakukan sesuatu dengan data, contoh: print ke konsol
                    listTemp.add(new spareData(idBarang, idPelanggan, namaBarang, hargaBarang));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private Button Add_Button;

    @FXML
    private TableColumn<spareData, Double> Column_Harga;

    @FXML
    private TableColumn<spareData, String> Column_IDBarang;

    @FXML
    private TableColumn<spareData, String> Column_IDPelanggan;

    @FXML
    private TableColumn<spareData, String> Column_Nama;

    @FXML
    private TableView<spareData> Cust_Table;

    @FXML
    private MenuItem Customer;

    @FXML
    private Button Delete_Button;

    @FXML
    private TextField Field_Harga;

    @FXML
    private TextField Field_IDPelanggan;

    @FXML
    private TextField Field_IdBarang;

    @FXML
    private TextField Field_NamaBarang;

    @FXML
    private TextField Field_Search;

    @FXML
    private Button Find_Button;

    @FXML
    private Label Label_Alamat;

    @FXML
    private Label Label_ID;

    @FXML
    private Label Label_Nama;

    @FXML
    private Label Label_NoHP;

    @FXML
    private Label Label_Search;

    @FXML
    private MenuItem Login;

    @FXML
    private MenuItem Paket;

    @FXML
    private MenuItem Pelanggan;

    @FXML
    private Button Refresh_Button;

    @FXML
    private MenuItem Shipment;

    @FXML
    private Button Update_Button;

    @FXML
    void Add_Btn(ActionEvent event) throws IOException, SQLException {
        System.out.println("p");

        String DB_URL = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false";

        try (Connection connection = DriverManager.getConnection(DB_URL)) {

            // Buka koneksi
            System.out.println("Connecting to database...");

            // Persiapkan pernyataan SQL untuk insert
            String sql = "INSERT INTO Sparepart (idBarang, idPelanggan, namaBarang, hargaBarang) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                String idBarang = Field_IdBarang.getText();
                String idPelanggan = Field_IDPelanggan.getText();
                String namaBarang = Field_NamaBarang.getText();
                System.out.println(Field_NamaBarang.getText());
                double hargaBarang = Double.parseDouble(Field_Harga.getText());
                
                // Set nilai parameter
                preparedStatement.setString(1, idBarang);
                preparedStatement.setString(2, idPelanggan);
                preparedStatement.setString(3, namaBarang);
                preparedStatement.setDouble(4, hargaBarang);

                // Eksekusi pernyataan
                preparedStatement.executeUpdate();
                clearFields();

                System.out.println("Sparepart added successfully!");
                getList();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    @FXML
    void Delete_Btn(ActionEvent event) {
        int index = Cust_Table.getSelectionModel().getSelectedIndex();
            if (index <= -1) {
                return;
            }
            String IdToDelete = Column_IDBarang.getCellData(index).toString();
            deleteRow(IdToDelete);
            getList();
    }

    @FXML
    void Find_Btn(ActionEvent event) {
        String searchId = Field_Search.getText();
        if (searchId.isEmpty()) {
            showWarning("Warning", "Please enter an part ID for search.");
            return;
        }

        try {
            // Perform search operation
            searchSparepartByKeyword(searchId);
            
        } catch (SQLException e) {
            showErrorAlert("Error while searching for Sparepart: " + e.getMessage());
        }

    }

    @FXML
    void Refresh_Btn(ActionEvent event) {
        refreshTable();
        clearFields();
    }

    @FXML
    void Update_Btn(ActionEvent event) {
        spareData selectedCustomer = Cust_Table.getSelectionModel().getSelectedItem();

        if (selectedCustomer == null) {
            showWarning("Warning", "Please select a row for editing.");
            return;
        }
        selectedCustomer.setIdBarang(Field_IdBarang.getText());
        selectedCustomer.setIdPelanggan(Field_IDPelanggan.getText());
        selectedCustomer.setNamaBrang(Field_NamaBarang.getText());
        selectedCustomer.setHargaBarang(Double.parseDouble(Field_Harga.getText()));

        updateCustomer(selectedCustomer);
        clearFields();
        showSuccessAlert("Employee data updated successfully!");
        refreshTable();
    }

      @FXML
    void Switch_Customer(ActionEvent event) {

    }

    @FXML
    void Switch_Paket(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Paket.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Switch_Employee(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pelanggan.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void Switch_Shipment(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("servis.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    
    }

    @FXML
    void Swtich_Login(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //FNC

    private void updateCustomer(spareData Spareparto) {
        String jdbcUrl = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false";

        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            // Persiapkan pernyataan SQL untuk update
            String sql = "UPDATE Sparepart SET idPelanggan = ?, namaBarang = ?, hargaBarang = ? WHERE idBarang = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Spareparto.getIdPelanggan());
                preparedStatement.setString(2, Spareparto.getNamaBarang());
                preparedStatement.setDouble(3, Spareparto.getHargaBarang());
                preparedStatement.setString(4, Spareparto.getIdBarang());
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
            showErrorAlert("Error updating Sparepart data in the database: " + e.getMessage());
        }
    }


    private void refreshTable() {
    getList(); 
    Cust_Table.setItems(listTemp); 
    }

    public void deleteRow(String ID) {
        // URL DB
        String jdbcUrl = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false";
        String id = ID;

        try {
            // Membuat koneksigehhhh
            Connection connection = DriverManager.getConnection(jdbcUrl);

            try {
                String deleteServisDetailQuery = "DELETE FROM Sparepart WHERE idBarang = ?;";
                try (PreparedStatement preparedStatementServisDetail = connection
                        .prepareStatement(deleteServisDetailQuery)) {
                    preparedStatementServisDetail.setString(1, id);
                    preparedStatementServisDetail.executeUpdate();
                }
                System.out.println("Data berhasil dihapus.");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void unconditionalSearch(){
        String url = "jdbc:sqlserver://your_server:your_port;databaseName=your_database;IntegratedSecurity=true;encrypt=false;";

        try (Connection connection = DriverManager.getConnection(url)) {
            // Menerima input kata kunci dari pengguna (misalnya, melalui scanner)
            String userInput = "your_input_keyword";

            // Query SQL dengan parameter ? untuk kata kunci
            String sql = "SELECT * FROM Pelanggan WHERE " +
                         "namaPelanggan LIKE ? OR noTelp LIKE ? OR keperluan LIKE ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set parameter values
                preparedStatement.setString(1, "%" + userInput + "%");
                preparedStatement.setString(2, "%" + userInput + "%");
                preparedStatement.setString(3, "%" + userInput + "%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Ambil data dari setiap kolom
                        String idPelanggan = resultSet.getString("idPelanggan");
                        String namaPelanggan = resultSet.getString("namaPelanggan");
                        String noTelp = resultSet.getString("noTelp");
                        String tanggalTransaksi = resultSet.getString("tanggalTransaksi");
                        String keperluan = resultSet.getString("keperluan");

                        // Lakukan sesuatu dengan data, contoh: print ke konsol
                        System.out.println("ID Pelanggan: " + idPelanggan);
                        System.out.println("Nama Pelanggan: " + namaPelanggan);
                        System.out.println("No Telp: " + noTelp);
                        System.out.println("Tanggal Transaksi: " + tanggalTransaksi);
                        System.out.println("Keperluan: " + keperluan);
                        System.out.println("---------------------------");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }

    private void searchSparepartByKeyword(String keyword) throws SQLException {
        String url = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false";
        listTemp.clear();
    
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Sparepart WHERE " +
                         "idBarang LIKE ? OR idPelanggan LIKE ? OR namaBarang LIKE ? OR hargaBarang LIKE ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Ganti nilai keyword dengan kata kunci yang ingin Anda cari
                String keywordLike = "%" + keyword + "%";
                for (int i = 1; i <= 4; i++) {
                    preparedStatement.setString(i, keywordLike);
                }
    
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String idBarang = resultSet.getString("idBarang");
                        String idPelanggan = resultSet.getString("idPelanggan");
                        String namaBarang = resultSet.getString("namaBarang");
                        Double hargaBarang = resultSet.getDouble("hargaBarang");
    
                        // Add the result to the list
                        listTemp.add(new spareData(idBarang, idPelanggan, namaBarang, hargaBarang));
                    }
    
                    // Update the table with the search result
                    Cust_Table.setItems(listTemp);
    
                    if (listTemp.isEmpty()) {
                        showAlert("Information", "Search Result", "No Sparepart with keyword: " + keyword);
                    }
                }
            }
        }
    }

        private void searchEmployeeById(String idBarang) throws SQLException {
        String url = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false";
        listTemp.clear();

        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Sparepart WHERE idBarang = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, idBarang);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String IdBarang = resultSet.getString("idBarang");
                        String IdPelanggan = resultSet.getString("idPelanggan");
                        String nama = resultSet.getString("namaBarang");
                        Double harga = resultSet.getDouble("hargaBarang");

                        // Add the result to the list
                        listTemp.add(new spareData(IdBarang, IdPelanggan, nama, harga));
                    }

                    // Update the table with the search result
                    Cust_Table.setItems(listTemp);

                    if (listTemp.isEmpty()) {
                        showAlert("Information", "Search Result", "No Sparepart with ID: " + idBarang);
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

    private void clearFields() {
        Field_IdBarang.clear();
        Field_IDPelanggan.clear();
        Field_NamaBarang.clear();
        Field_Search.clear();
        Field_Harga.clear();
        // Search_Field.clear();
    }

}

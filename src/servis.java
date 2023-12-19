import java.io.IOException;
import java.math.BigDecimal;
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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class servis implements Initializable {
    String statuses;
    ObservableList<servisData> listTemp = FXCollections.observableArrayList(); // buat table view

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {
        getList();
        idServis_Colum.setCellValueFactory(new PropertyValueFactory<servisData, String>("idServis"));
        idPelanggan_Collum.setCellValueFactory(new PropertyValueFactory<servisData, String>("idPelanggan"));
        Biaya_Colum.setCellValueFactory(new PropertyValueFactory<servisData, Double>("biaya"));
        merk_Colum.setCellValueFactory(new PropertyValueFactory<servisData, String>("merk"));
        sparepart_Column.setCellValueFactory(new PropertyValueFactory<servisData, String>("sparepart"));
        
        Data_Table.setItems(listTemp);
        
    }

    public void getList() {
        String url = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false";
        listTemp.clear();
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Servis";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Ambil data dari setiap kolom
                    String idServis = resultSet.getString("idServis");
                    String idPelanggan = resultSet.getString("idPelanggan");
                    Double biaya = resultSet.getDouble("biaya");
                    String merk = resultSet.getString("merk");
                    String sparepart = resultSet.getString("isIncludeSparepart");

                    // Lakukan sesuatu dengan data, contoh: print ke konsol
                    listTemp.add(new servisData(idServis, idPelanggan, biaya, merk, sparepart));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRow(String ID) {
        // Informasi koneksi database
        String jdbcUrl = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false";

        // Data yang ingin dihapus
        String id = ID;

        try {
            // Membuat koneksig
            Connection connection = DriverManager.getConnection(jdbcUrl);

            try {
                // Hapus data dari tabel ServisDetail
                String deleteServisDetailQuery = "DELETE FROM Servis WHERE idServis = ?;";
                try (PreparedStatement preparedStatementServisDetail = connection
                        .prepareStatement(deleteServisDetailQuery)) {
                    preparedStatementServisDetail.setString(1, id);
                    preparedStatementServisDetail.executeUpdate();
                }
                // connection.commit();
                System.out.println("Data berhasil dihapus.");
            } catch (SQLException e) {
                // connection.rollback();
                e.printStackTrace();
            } finally {

                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button Add_But;

    @FXML
    private TableColumn<servisData, Double> Biaya_Colum;

    @FXML
    private MenuItem Choose_Done;

    @FXML
    private MenuItem Choose_Shipment;

    @FXML
    private MenuButton Chooser;

    @FXML
    private MenuItem Customer;

    @FXML
    private TableView<servisData> Data_Table;

    @FXML
    private Button Delete_But;

    @FXML
    private MenuItem Employee;

    @FXML
    private Button Find_But;

    @FXML
    private MenuItem Login;

    @FXML
    private MenuItem Paket;

    @FXML
    private Button Refresh_Button;

    @FXML
    private TextField Search_F;

    @FXML
    private TextField biaya_F;

    @FXML
    private TableColumn<servisData, String> idPelanggan_Collum;

    @FXML
    private TextField idPelanggan_F;

    @FXML
    private TableColumn<servisData, String> idServis_Colum;

    @FXML
    private TextField idServis_F;

    @FXML
    private TableColumn<servisData, String> merk_Colum;

    @FXML
    private TextField merk_F;

    @FXML
    private TableColumn<servisData, String> sparepart_Column;

    @FXML
    private Button UpdateButton;


    @FXML
    void AddBtn(ActionEvent event) throws SQLException {
        System.out.println("p");

        String DB_URL = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false;";

        try (Connection connection = DriverManager.getConnection(DB_URL)) {

            // Buka koneksi
            System.out.println("Connecting to database...");

            // Persiapkan pernyataan SQL untuk insert
            String sql = "INSERT INTO Servis (idServis, idPelanggan, biaya, merk, isIncludeSparepart) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                String idServis= idServis_F.getText();
                String idPelanggan = idPelanggan_F.getText();
                double biaya = Double.parseDouble(biaya_F.getText());
                String merk = merk_F.getText();
                String sparepart = statuses;

                // Set nilai parameter
                preparedStatement.setString(1, idServis);
                preparedStatement.setString(2, idPelanggan);
                preparedStatement.setBigDecimal(3, BigDecimal.valueOf(biaya));
                preparedStatement.setString(4, merk);
                preparedStatement.setString(5, sparepart);
                // Eksekusi pernyataan
                preparedStatement.executeUpdate();

                System.out.println("Shipment added successfully!");
                clearFields();
                getList();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

      @FXML
    void Refresh_Btn(ActionEvent event) {
        refreshTable();
        clearFields();
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
    void DelBtn(ActionEvent event) {
        int index = Data_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        String IdToDelete = idServis_Colum.getCellData(index).toString();
        deleteRow(IdToDelete);
        getList();
    }

    @FXML
    void DoneC(ActionEvent event) {
        statuses = "Include";
        Chooser.setText("Include");
    }

    @FXML
    void FinBtn(ActionEvent event) {
        try {
            String id = Search_F.getText();
            searchServisByKeyword(id);

            Data_Table.setItems(listTemp);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid ID for searching.");
        }
    }

    @FXML
    void ShipC(ActionEvent event) {
        statuses = "Exclude";
        Chooser.setText("Exclude");
    }

    @FXML
    void Switch_Customer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sparepart.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

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
    void Swtich_Login(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();

        // Menggunakan MenuItem untuk mendapatkan Stage
        Stage stage = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    
    @FXML
    void UpdateClicked(ActionEvent event) {
        int index = Data_Table.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        String IdToUpdate = idServis_Colum.getCellData(index).toString();

        String sparepart = statuses;


        servisData selectedServis = Data_Table.getSelectionModel().getSelectedItem();
        if (selectedServis == null) {
            showWarning("Warning", "Please select a row for editing.");
            return;
        }
        selectedServis.setIdServis(idServis_Colum.getCellData(index).toString());
        if(!idPelanggan_F.getText().equals("")){
            selectedServis.setIdPelanggan(idPelanggan_F.getText());
        }else{
            selectedServis.setIdPelanggan(idPelanggan_Collum.getCellData(index).toString());
        }if(!biaya_F.getText().equals("")){
            selectedServis.setBiaya(Double.parseDouble(biaya_F.getText()));
        }else{
            selectedServis.setBiaya(Double.parseDouble(Biaya_Colum.getCellData(index).toString()));
        }if(!merk_F.getText().equals("")){
            selectedServis.setMerk(merk_F.getText());
        }else{
            selectedServis.setMerk(merk_Colum.getCellData(index).toString());
        }
        selectedServis.setSparepart(statuses);

        updateEmployee(selectedServis, IdToUpdate);
        System.out.println(selectedServis.getIdServis());
        clearFields();
        showSuccessAlert("Servis data updated successfully!");
        refreshTable();
    }



    // FNC

    

    private void updateEmployee(servisData Pelanggan, String IdToUpdate) {
        String jdbcUrl = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false;";

        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            // Persiapkan pernyataan SQL untuk update
            String sql = "UPDATE Servis SET idPelanggan = ?, biaya = ?, merk = ?, isIncludeSparepart = ? WHERE idServis = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Pelanggan.getIdPelanggan());
                preparedStatement.setDouble(2, Pelanggan.getBiaya());
                preparedStatement.setString(3, Pelanggan.getMerk());
                preparedStatement.setString(4, Pelanggan.getSparepart());
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
            showErrorAlert("Error updating Servis data in the database: " + e.getMessage());
        }
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

    private void showWarning(String title, String content) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle(title);
      alert.setHeaderText(null);
      alert.setContentText(content);
      alert.showAndWait();
    }

    private void clearFields() {
        idPelanggan_F.clear();
        idServis_F.clear();
        merk_F.clear();
        biaya_F.clear();
        Chooser.setText("Choose");
    }
    private void refreshTable() {
        getList(); // Mendapatkan data dari database
        Data_Table.setItems(listTemp); // Menetapkan data pada tabel
    }
    private void searchServisByKeyword(String keyword) {
        String url = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false";
        listTemp.clear();
    
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Servis WHERE " +
                         "idServis LIKE ? OR idPelanggan LIKE ? OR " +
                         "biaya LIKE ? OR merk LIKE ? OR isIncludeSparepart LIKE ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Ganti nilai keyword dengan kata kunci yang ingin Anda cari
                String keywordLike = "%" + keyword + "%";
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(i, keywordLike);
                }
    
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String idServis = resultSet.getString("idServis");
                        String idPelanggan = resultSet.getString("idPelanggan");
                        Double biaya = resultSet.getDouble("biaya");
                        String merk = resultSet.getString("merk");
                        String isIncludeSparepart = resultSet.getString("isIncludeSparepart");
    
                        // Add the result to the list
                        listTemp.add(new servisData(idServis, idPelanggan, biaya, merk, isIncludeSparepart));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void searchById(String id) {
        String url = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false";
        listTemp.clear();

        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM Servis WHERE idServis = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String idServis = resultSet.getString("idServis");
                        String idPelanggan = resultSet.getString("idPelanggan");
                        Double biaya = resultSet.getDouble("biaya");
                        String merk = resultSet.getString("merk");
                        String sparepart = resultSet.getString("isIncludeSparepart");

                        listTemp.add(new servisData(idServis, idPelanggan, biaya, merk, sparepart));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }


}

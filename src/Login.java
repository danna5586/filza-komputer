import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button Login_Button;

    @FXML
    private Label Login_Text;

    @FXML
    private PasswordField Pass_Field;

    @FXML
    private Label Password_Label;

    @FXML
    private TextField Username_Field;

    @FXML
    private Label Username_Label;

    @FXML
    void Act_Login(ActionEvent event) throws IOException {
        String Trycon = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false;";
        try (Connection conn = DriverManager.getConnection(Trycon)) {
            String sql = "SELECT * FROM Account WHERE Username = ? AND Password = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, Username_Field.getText());
                statement.setString(2, Pass_Field.getText());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // showAlert("Information", "Login Berhasil", "Selamat datang, " +
                        // Username_Field.getText() + "!");
                        showSuccessAlert("Login Berhasil!");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Pelanggan.fxml"));
                        root = loader.load();
                        // root = FXMLLoader.load(getClass().getResource("Karyawan.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                    } else {
                        // Login gagal, tampilkan pesan kesalahan atau lakukan tindakan lain
                        showAlert("Warning", "Login Gagal", "Nama atau password salah.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Kesalahan Database", "Terjadi kesalahan saat mengakses database.");
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
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

}
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);

            primaryStage.setTitle("Shipment_MS");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            String Trycon = "jdbc:sqlserver://LAPTOP-3I7OBSO1;databaseName=Filza;IntegratedSecurity=true;encrypt=false;";
            try {
                Connection connection = DriverManager.getConnection(Trycon);
                System.out.println("Connection Success");
            } finally {

            }
        } catch (SQLException e) {

            System.out.println("Error Connection");
            System.out.println(e);
            e.printStackTrace();
        }

        launch(args);
    }
}

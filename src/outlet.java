import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class outlet {

    @FXML
    private AnchorPane apoutlet;

    @FXML
    private Button btnadd;

    @FXML
    private Button btndelete;

    @FXML
    private Button btnfind;

    @FXML
    private Button btnupdate;

    @FXML
    private Label lblid;

    @FXML
    private Label lbljudul;

    @FXML
    private Label lblkontak;

    @FXML
    private Label lbllokasi;

    @FXML
    private Label lblnama;

    @FXML
    private Label lblsearch;

    @FXML
    private MenuBar mboutlet;

    @FXML
    private TableColumn<?, ?> tcid;

    @FXML
    private TableColumn<?, ?> tckontak;

    @FXML
    private TableColumn<?, ?> tclokasi;

    @FXML
    private TableColumn<?, ?> tcnama;

    @FXML
    private TextField tfid;

    @FXML
    private TextField tfkontak;

    @FXML
    private TextField tflokasi;

    @FXML
    private TextField tfnama;

    @FXML
    private TextField tfsearch;

    @FXML
    private TableView<?> tvoutlet;

}

package trabredesneurais;

import com.opencsv.CSVReader;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

public class TesteController implements Initializable {

    private CSVReader csvr;
    
    @FXML
    private TableView<List<String>> tvdados;
    @FXML
    private TableView<List<String>> tvconfusao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clkArq(ActionEvent event) {
    }

    @FXML
    private void clkTestar(ActionEvent event) {
        
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        
        a.setTitle("Treinar");
        a.setHeaderText("Atenção!");
        if(csvr == null) {
            
            a.setContentText("Arquivo Não Aberto");
            a.showAndWait();
            try { clkArq(null); } catch(Exception e) {}
        }
        else {
            
        }
    }
}
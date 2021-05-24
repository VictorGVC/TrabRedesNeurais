package trabredesneurais;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

public class TesteController implements Initializable {

    @FXML
    private TableView<?> tvdados;
    @FXML
    private TableView<?> tvconfusao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void clkArq(ActionEvent event) {
    }

    @FXML
    private void clkTreinar(ActionEvent event) {
    }
}
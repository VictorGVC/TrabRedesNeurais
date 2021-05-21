package trabredesneurais;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;

public class TreinamentoController implements Initializable {

    @FXML
    private ToggleGroup gay;
    @FXML
    private JFXTextField txentrada;
    @FXML
    private JFXTextField txsaida;
    @FXML
    private JFXTextField txoculta;
    @FXML
    private JFXTextField txiteracoes;
    @FXML
    private JFXTextField txerro;
    @FXML
    private JFXRadioButton rblin;
    @FXML
    private JFXRadioButton rblog;
    @FXML
    private JFXTextField txaprendizagem;
    @FXML
    private JFXRadioButton rbhiper;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }    

    @FXML
    private void clkArq(ActionEvent event) 
    {
        
    }

    @FXML
    private void clkTreinar(ActionEvent event) 
    {
        
    }
}

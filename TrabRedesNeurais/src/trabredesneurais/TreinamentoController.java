package trabredesneurais;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

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
    private void clkArq(ActionEvent event) throws FileNotFoundException, IOException, CsvValidationException 
    {
        FileChooser fc = new FileChooser();
        
        fc.setTitle("Abrir Arquivo CSV");
        Reader reader = Files.newBufferedReader(Paths.get(fc.showOpenDialog(null).toURI()));
        CSVReader csvr = new CSVReader(reader);

        String[] colstrings = csvr.readNext();

        while((colstrings = csvr.readNext()) != null)
        {
            for (String colstring : colstrings) 
            {
                System.out.print(colstring+" ");
            }
            System.out.println("");
        }  
            
    }

    @FXML
    private void clkTreinar(ActionEvent event) 
    {
        
    }
}

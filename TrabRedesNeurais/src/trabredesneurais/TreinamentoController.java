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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TableView<List<String>> tvcsv;
    
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

        List<String> colstrings = Arrays.asList(csvr.readNext());
        for (int j = 0; j < colstrings.size(); j++) 
        {
            final int i = j;
            TableColumn<List<String>,String> col = new TableColumn<>(colstrings.get(j).toUpperCase());
            col.setCellValueFactory((v) -> new SimpleStringProperty(v.getValue().get(i)));
            tvcsv.getColumns().add(col);
        }
        
        int entrada = colstrings.size()-1;
        txentrada.setText(""+entrada);
        
        List<String> classes = new ArrayList<>();
        String[] line;
        List<String> lc;
        while((line = csvr.readNext()) != null)
        {
            lc = Arrays.asList(line);
            if(!classes.contains(lc.get(lc.size()-1)))
                classes.add(lc.get(lc.size()-1));
            tvcsv.getItems().add(lc);
        }  
        
        int saida = classes.size();
        txsaida.setText(""+saida);
        txoculta.setText(""+((entrada+saida)/2));
    }

    @FXML
    private void clkTreinar(ActionEvent event) 
    {
        
    }
}

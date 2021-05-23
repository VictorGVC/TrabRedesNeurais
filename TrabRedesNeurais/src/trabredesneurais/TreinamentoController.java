package trabredesneurais;

import Models.Treino;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
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
import javafx.stage.FileChooser;

public class TreinamentoController implements Initializable {

    private CSVReader csvr;
    private List<Treino> t;
    double[] maior;
    double[] menor;
    
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
        csvr = new CSVReader(reader);

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
        maior = new double[colstrings.size()-2];
        menor = new double[colstrings.size()-2];
        
        for (int i = 0; i < maior.length; i++) 
        {
            maior[i] = Double.MIN_VALUE;
            menor[i] = Double.MAX_VALUE;
        }
        
        List<List<String>> l = new ArrayList<>();
        
        while((line = csvr.readNext()) != null)
        {
            lc = Arrays.asList(line);
            for (int i = 0; i < maior.length; i++)
            {
                if(Integer.parseInt(line[i]) > maior[i])
                    maior[i] = Double.parseDouble(line[i]);
                if(Integer.parseInt(line[i]) < menor[i])
                    menor[i] = Double.parseDouble(line[i]);
            }
            
            if(!classes.contains(lc.get(lc.size()-1)))
                classes.add(lc.get(lc.size()-1));
            tvcsv.getItems().add(lc);
            l.add(lc);
        }  
        
        geraCamadaEntrada(l);
        
        int saida = classes.size();
        txsaida.setText(""+saida);
        txoculta.setText(""+((entrada+saida)/2));
    }
    
    private void geraCamadaEntrada(List<List<String>> l)
    {
        Treino tr;
        double[] diferenca = new double[maior.length];
        t = new ArrayList<>();
        for (int i = 0; i < maior.length; i++) 
        {
            diferenca[i] = maior[i]-menor[i];
        }
        for (int j = 0; j < l.size(); j++) 
        {
            tr = new Treino();
            for (int k = 0; k < maior.length; k++) 
            {
                tr.getEntradas().add((Double.parseDouble(l.get(j).get(k))-menor[k])/diferenca[k]);
            }
            t.add(tr);
        }
        System.out.println("");
    }

    @FXML
    private void clkTreinar(ActionEvent event) 
    {
        int aux = Integer.parseInt(txaprendizagem.getText());
        if(aux<0||aux>1)
        {
            //ATENCAO ATENCAO, O CARRO DO OVO ESTÁ PASSANDO NA SUA RUA
            System.out.println("coloca erro que ta invalido aqui Mateus por favor");
        }
        if(csvr == null)
        {
            System.out.println("coloca aqui tbm que não foi colocado o arquivo de treinamento");
        }
        
    }
}

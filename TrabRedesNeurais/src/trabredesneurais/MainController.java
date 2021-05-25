package trabredesneurais;

import Models.Neuronio;
import Models.Treino;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import util.MaskFieldUtil;

public class MainController implements Initializable {
    
    private CSVReader csvr;
    private List<List<String>> l;
    private List<Treino> t;
    private double[] maior, menor,diferenca;
    private int[][] matrix;
    private double[][] ocultapeso, saidapeso;
    private List<double[][]> focutapeso, fsaidapeso;
    private List<String> classes;
    
    @FXML
    private Tab tbTreinamento;
    @FXML
    private Tab tbTeste;
    @FXML
    private JFXTextField txentrada;
    @FXML
    private JFXTextField txoculta;
    @FXML
    private JFXTextField txsaida;
    @FXML
    private JFXTextField txiteracoes;
    @FXML
    private JFXTextField txerro;
    @FXML
    private JFXRadioButton rblin;
    @FXML
    private ToggleGroup gay;
    @FXML
    private JFXRadioButton rblog;
    @FXML
    private JFXRadioButton rbhiper;
    @FXML
    private JFXTextField txaprendizagem;
    @FXML
    private TableView<List<String>> tvcsv;
    @FXML
    private TableView<List<String>> tvdados;
    @FXML
    private TableView<?> tvconfusao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        MaskFieldUtil.numericField(txentrada);
        MaskFieldUtil.numericField(txoculta);
        MaskFieldUtil.numericField(txsaida);
        MaskFieldUtil.numericField(txiteracoes);
        txaprendizagem.setText(""+0.9);
        txerro.setText(""+0);
        txiteracoes.setText(""+500);
        rblin.setSelected(true);
    }   
    
    public void maimen()
    {
        for (int i = 0; i < l.size(); i++)
        {
            for (int j = 0; j < maior.length; j++)
            {
                if(Integer.parseInt(l.get(i).get(j)) > maior[j])
                    maior[j] = Double.parseDouble(l.get(i).get(j));
                if(Integer.parseInt(l.get(i).get(j)) < menor[j])
                    menor[j] = Double.parseDouble(l.get(i).get(j));
            }
        }
        diferenca = new double[maior.length];
        for (int i = 0; i < maior.length; i++) 
            diferenca[i] = maior[i]-menor[i];
    }
    
    private void geraCamadaEntrada()
    {
        Treino tr;
        t = new ArrayList<>();
        for (int j = 0; j < l.size(); j++) 
        {
            tr = new Treino();
            for (int k = 0; k < maior.length; k++) 
                tr.getEntradas().add((Double.parseDouble(l.get(j).get(k))-menor[k])/diferenca[k]);
            t.add(tr);
        }
    } 
    
    private void geraMatrizDesejada()
    {
        int saida = Integer.parseInt(txsaida.getText());
        matrix = new int[saida][saida];
        
        for (int i = 0; i < saida; i++) 
            for (int j = 0; j < saida; j++) 
                if(rbhiper.isSelected())
                    if(i == j)
                        matrix[i][j] = 1;
                    else
                        matrix[i][j] = -1;
                else
                    if(i == j)
                        matrix[i][j] = 1;
                    else
                        matrix[i][j] = 0;
        
        System.out.println("");
    }

    @FXML
    private void clkArq(ActionEvent event) throws IOException, CsvException 
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
        
        classes = new ArrayList<>();
        List<String> lc;
        maior = new double[colstrings.size()-2];
        menor = new double[colstrings.size()-2];
        
        for (int i = 0; i < maior.length; i++) 
        {
            maior[i] = Double.MIN_VALUE;
            menor[i] = Double.MAX_VALUE;
        }
        
        l = new ArrayList<>();
        
        List<String[]> all = csvr.readAll();
        for (String[] line : all)
        {
            lc = Arrays.asList(line);
            
            tvcsv.getItems().add(lc);
            l.add(lc);
            if(!classes.contains(line[line.length-1]))
                classes.add(line[line.length-1]);
        }  
        lc = null;
        all = null;
        
        maimen();
        geraCamadaEntrada();
        
        int saida = classes.size();
        txsaida.setText(""+saida);
        txoculta.setText(""+((entrada+saida)/2));
    }

    @FXML
    private void clkTreinar(ActionEvent event) {
        
        focutapeso = new ArrayList<>();
        fsaidapeso = new ArrayList<>();
        boolean flag = true;
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        
        a.setTitle("Treinar");
        a.setHeaderText("Atenção!");
        
        if(txoculta.getText().equals("")) {
            
            a.setContentText("Quantidade de Camadas Ocultas é Necessaria");
            a.showAndWait();
            flag = false;
        }
        if(txaprendizagem.getText().equals("")) {
            
            a.setContentText("Taxa de Aprendizagem é Necessaria");
            a.showAndWait();
            flag = false;
        }
        if(csvr == null) {
            
            a.setContentText("Arquivo Não Aberto");
            a.showAndWait();
            try { clkArq(null); } catch(Exception e) {}
            flag = false;
        }
        if(flag) {
        
            double aprendizagem = Double.parseDouble(txaprendizagem.getText());
            int oculta = Integer.parseInt(txoculta.getText());
            int entrada = Integer.parseInt(txentrada.getText());
            int saida = Integer.parseInt(txsaida.getText());

            flag = true;
            if(aprendizagem < 0 || aprendizagem > 1)
            {
                //ATENCAO ATENCAO, O CARRO DO OVO ESTÁ PASSANDO NA SUA RUA
                //OLHA O CARRO DO DANONE EINNNNNN
                a.setContentText("Taxa Inválida! Insira um Valor Entre 0 e 1");
                a.showAndWait();
                flag = false;
            }
            if(flag)
            {
                geraMatrizDesejada();
                double erroatual = Double.MAX_VALUE;
                double[][] moculta = new double[oculta][entrada];
                double[][] msaida = new double[saida][oculta];

                for (Treino treino : t) 
                {
                    treino.getOculta().setCamada(entrada, oculta, saida);
                    for (int i = 0; i < saida; i++) 
                        treino.getSaidas().add(new Neuronio());
                    treino.getOculta().rPeso(entrada, saida);
                }

                for (int i = 0; i < saida; i++)
                {
                    focutapeso.add(moculta);
                    fsaidapeso.add(msaida);
                }
                
                int it = Integer.parseInt(txiteracoes.getText());
                double ermin = Double.parseDouble(txerro.getText());
                int saidad = 0;
                
                for (int i = 0; i < it && erroatual > ermin; i++) 
                {
                    erroatual = 0;
                    for (int k = 0; k < t.size(); k++) 
                    {
                        for (int j = 0; j < classes.size(); j++) 
                            if(classes.get(j).equals(tvcsv.getItems().get(k).get(entrada)))
                            {
                                saidad = j;
                                j = classes.size();
                            }

                        focutapeso.set(saidad,t.get(k).getOculta().getOcultapeso());
                        fsaidapeso.set(saidad,t.get(k).getOculta().getSaidapeso());

                        for (int j = 0; j < oculta; j++) 
                        {
                            t.get(k).getOculta().getNeuronio().get(j).calculaNet(j,t.get(k).getEntradas(),t.get(k).getOculta().getOcultapeso());
                            if(rblin.isSelected())
                                t.get(k).getOculta().getNeuronio().get(j).setLinear();
                            else if(rblog.isSelected())
                                t.get(k).getOculta().getNeuronio().get(j).SetLogistica();
                            else
                                t.get(k).getOculta().getNeuronio().get(j).setHiperbolica();
                        }
                        
                        List<Double> oc = new ArrayList<>();
                        for (int j = 0; j < oculta; j++) 
                            oc.add(t.get(k).getOculta().getNeuronio().get(j).getFnet());
                        
                        for (int j = 0; j < saida; j++) 
                        {
                            t.get(k).getSaidas().get(j).calculaNet(j, oc, t.get(k).getOculta().getSaidapeso());
                            
                            if(rblin.isSelected())
                                t.get(k).getSaidas().get(j).setLinear();
                            else if(rblog.isSelected())
                                t.get(k).getSaidas().get(j).SetLogistica();
                            else
                                t.get(k).getSaidas().get(j).setHiperbolica();
                            
                            t.get(k).getSaidas().get(j).calculaErroS(matrix[j][saidad]);
                        }
                        
                        List<Double> erroS = new ArrayList<>();
                        for (int j = 0; j < saida; j++) 
                            erroS.add(t.get(k).getSaidas().get(j).getErro());
                        
                        for (int j = 0; j < oculta; j++)
                            t.get(k).getOculta().getNeuronio().get(j).calculaErroOculta(j, erroS, t.get(k).getOculta().getOcultapeso());
                        
                        t.get(k).getOculta().corrigePesoS(aprendizagem, erroS);
                        t.get(k).getOculta().corrigePesoO(aprendizagem, erroS);
                        
                        t.get(k).calcularER();
                        
                        erroatual += t.get(k).getErro();
                        ocultapeso = t.get(k).getOculta().getOcultapeso();
                        saidapeso = t.get(k).getOculta().getSaidapeso();
                        moculta = ocultapeso;
                        msaida = saidapeso;
                    }
                    erroatual /= t.size();
                }
                //coloca uma mensagem aqui que apareça na tela q terminou o treino
                System.out.println("treinou");
            }
        }
    }

    @FXML
    private void clkTestar(ActionEvent event) {
        
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        
        int entrada = Integer.parseInt(txentrada.getText());
        int oculta = Integer.parseInt(txoculta.getText());
        int saida = Integer.parseInt(txsaida.getText());
        
        a.setTitle("Treinar");
        a.setHeaderText("Atenção!");
        if(csvr == null) 
        {
            a.setContentText("Arquivo Não Aberto");
            a.showAndWait();
            try { clkArq(null); } catch(Exception e) {}
        }
        else 
        {
            int[][] conf = new int[saida][saida];
            for (Treino tr : t) 
            {
                tr.getOculta().setCamada(entrada, oculta, saida);
                for (int i = 0; i < saida; i++) 
                    tr.getSaidas().add(new Neuronio());
            }
            
            int saidad = 0;
            
            for (int i = 0; i < t.size(); i++) 
            {
                if(tvdados.getItems().get(i).get(entrada).equals("CD"))
                    System.out.println("");
                for (int j = 0; j < classes.size(); j++) 
                    if(classes.get(j).equals(tvdados.getItems().get(i).get(entrada)))
                    {
                        saidad = j;
                        j = classes.size();
                    }
                
                t.get(i).getOculta().setOcultapeso(focutapeso.get(saidad));
                t.get(i).getOculta().setSaidapeso(fsaidapeso.get(saidad));
                
                for (int j = 0; j < oculta; j++)
                {
                    t.get(i).getOculta().getNeuronio().get(j).calculaNet(j, t.get(i).getEntradas(),t.get(i).getOculta().getOcultapeso());
                    if(rblin.isSelected())
                        t.get(i).getOculta().getNeuronio().get(j).setLinear();
                    else if(rblog.isSelected())
                        t.get(i).getOculta().getNeuronio().get(j).SetLogistica();
                    else
                        t.get(i).getOculta().getNeuronio().get(j).setHiperbolica();
                }
                
                List<Double> lfnet = new ArrayList<>();
                List<Double> loculta = new ArrayList<>();
                
                for (int j = 0; j < oculta; j++) 
                    loculta.add(t.get(i).getOculta().getNeuronio().get(j).getFnet());
                
                for (int j = 0; j < saida; j++) 
                {
                    t.get(i).getSaidas().get(j).calculaNet(j, loculta, t.get(i).getOculta().getSaidapeso());
                    
                    if(rblin.isSelected())
                        t.get(i).getSaidas().get(j).setLinear();
                    else if(rblog.isSelected())
                        t.get(i).getSaidas().get(j).SetLogistica();
                    else
                        t.get(i).getSaidas().get(j).setHiperbolica();
                    
                    lfnet.add(t.get(i).getSaidas().get(j).getFnet());
                }
                
                double maior = lfnet.get(0);
                int posm = 0;
                
                for (int j = 0; j < saida; j++) 
                {
                    if(lfnet.get(j) > maior)
                    {
                        maior = lfnet.get(j);
                        posm = j;
                    }
                }
                
                conf[posm][saidad]++;
            }
            
            System.out.println("");
        }
        
    }

    @FXML
    private void clkArqTeste(ActionEvent event) throws IOException, CsvValidationException, CsvException 
    {
        //COLOCA UM IF AQUI QUE PRECISA TER TREINADO ANTES PARA CONSEGUIR ESCOLHER O ARQUIVO DE TESTE
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
            tvdados.getColumns().add(col);
        }
        
        List<String> lc;
                
        l = new ArrayList<>();
        
        List<String[]> all = csvr.readAll();
        for (String[] line : all)
        {
            lc = Arrays.asList(line);
            
            tvdados.getItems().add(lc);
            l.add(lc);
        }  
        lc = null;
        all = null;
        
        geraCamadaEntrada();
    }
}
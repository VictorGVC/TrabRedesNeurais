package trabredesneurais;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MainController implements Initializable {
    
    @FXML
    private Tab tbTreinamento;
    @FXML
    private Tab tbTeste;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
}
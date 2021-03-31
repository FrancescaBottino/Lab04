package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button btnCercaIscrittiCorso;
    
    @FXML
    private Button btnCercaCorsi;
    
    @FXML
    private Button btnIscrivi;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextArea txtRisultato;
    
    @FXML
    private ComboBox<Corso> scegliCorsoBox;


    @FXML
    void doCercaCorsi(ActionEvent event) {
    	
    	btnIscrivi.setDisable(true);
    	btnCercaIscrittiCorso.setDisable(true);
    	
    	String matricola=txtMatricola.getText();
    	
    	Studente s=this.model.getInfoStudenteDataMatricola(Integer.parseInt(matricola));
    	
    	if(s==null) {
    		txtRisultato.setText("Lo studente non è presente nel database");
    	}
    	
    	String str="";	
    	
    	List<Corso> result=this.model.getCercaCorsi(matricola);
    	
    	if(result.size()==0) {
    		txtRisultato.setText("Lo studente con matricola: "+matricola+" non è iscritto a nessun corso");
    		return;
    	}
    	
    	for(Corso c: result) {
    	
    		str+=c.toString2();
    	}
    	
    	txtRisultato.setText(str);
    		

    }

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {
    	
    	//selezionando un corso mi compaiono tutti gli iscritti a quel corso
    	
    	btnIscrivi.setDisable(true);
    	btnCercaCorsi.setDisable(true);
    	txtMatricola.setDisable(true);
    	
    	Corso c=scegliCorsoBox.getValue();
    	
    	if(c.getNome()=="") {
    		txtRisultato.setText("Nessun corso è stato selezionato");
    		return;
    	}
    	
    	String str="";
    			
    	List<Studente> studenti=this.model.getStudentiIscrittiAlCorso(c);
    	
    	if(studenti.size()==0) {
    		txtRisultato.setText("Nessun studente iscritto a questo corso");
    		return;
    	}
    	
    	for(Studente s: studenti) {
    		str+=s.toString();
    	}
    
    	txtRisultato.setText(str);

    }

    @FXML
    void doCheck(ActionEvent event) {
    	
    	//data una matricola , schiacciando il bottone trovo nome e cognome
    	//completamento automatico
    	
    	int matricola=Integer.parseInt(txtMatricola.getText());
    	
    	Studente s= this.model.getInfoStudenteDataMatricola(matricola);
    	
    	if(s==null) {
    		txtRisultato.setText("Lo studente non è presente nel database");
    	}
    	else {
    		txtNome.setText(s.getNome());
    		txtCognome.setText(s.getCognome());
    	}

    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	
    	btnCercaCorsi.setDisable(true);
    	btnCercaIscrittiCorso.setDisable(true);
    	
    	Corso c=scegliCorsoBox.getValue();

    	if(c.getNome()=="") {
    		txtRisultato.setText("Nessun corso è stato selezionato");
    		return;
    	}

    	String matricola=txtMatricola.getText();
    	
        Studente s= this.model.getInfoStudenteDataMatricola(Integer.parseInt(matricola));
    	if(s==null) {
    		txtRisultato.setText("Lo studente non è presente nel database");
    	}
    	
    	boolean f=this.model.cercaStudenteIscrittoCorso(matricola, c);
    	
    	if(f==true) {
    		txtRisultato.setText("Lo studente è iscritto a questo corso");
    	}
    	else {
    		txtRisultato.setText("Lo studente non è iscritto a questo corso");
    	}

    }

    @FXML
    void doReset(ActionEvent event) {
    	
    	txtRisultato.clear();
    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	btnIscrivi.setDisable(false);
    	btnCercaCorsi.setDisable(false);
    	btnCercaIscrittiCorso.setDisable(false);
    	txtMatricola.setDisable(false);

    }


    @FXML
    void initialize() {
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model=model;
    	
    	this.scegliCorsoBox.getItems().addAll(this.model.getTuttiICorsi());
    	
    	
    }
}

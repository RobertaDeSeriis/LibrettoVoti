package it.polito.tdp.librettovoti;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.librettovoti.model.Libretto;
import it.polito.tdp.librettovoti.model.Voto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//il CONTROLLER decide come formattare le cose, si occupa di I/O e di come gestirli
public class FXMLController {
	//va bene se il controller conosce il modello non il contrario
	private Libretto model; 
	

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    //contiene oggetti integer, perchè i voti sono degli interi da 18/30
    @FXML
    private ComboBox<Integer> cmbPunti; //si ricorda l'oggetto voto, selezionandolo dalla tendina punteggio

    @FXML
    private TextField txtNome;

    @FXML
    private TextArea txtVoti;
    
    @FXML
    private Label txtStatus;

    @FXML
    void handleNuovoVoto(ActionEvent event) {
    	
    	//1.  ACQUISIZIONE E CONTROLLO DATI
    	String nome= txtNome.getText(); 
    	Integer punti= cmbPunti.getValue(); //estrai i punteggi dalla comboBox 
    	//meglio tenere l'oggetto e non int, per poterlo confrontare con null ed evitare le nullPointerException
    	
    	//controlli di validità
    	if (nome.equals("") || punti==null) {
    		//errore, non posso eseguire l'operazione
    		txtStatus.setText("ERRORE: occorre inserire nome e voto\n");
    		return; 
    		
    	}
    	
    	//2. ESECUZIONE DELL'OPERAZIONE (chiedere al model di farla)
    	boolean ok= model.add(new Voto(nome, punti)); 
    	
    	//3. VISUALIZZAZIONE/AGGIORNAMNETO DEL RISULTATO
    	if (ok) {
    	List<Voto> voti= model.getVoti(); 
    	txtVoti.clear();
    	txtVoti.appendText("Hai superato "+voti.size()+" esami\n");
    	
    	for(Voto v: voti) {
    		txtVoti.appendText(v.toString()+"\n");
    	}
    	//pulizia dei campi nome e punteggio una vota inserito correttamente il dato 
    	txtNome.clear();
    	cmbPunti.setValue(null); //value, indica il valore attualmente selezionato
    	txtStatus.setText("");
    	}
    	//ho fatto gestire al controller il modo di gestire i dati
    	
    	else {
    		txtStatus.setText("ERRORE: esame già esistente");
    	}
    	
    	//quando voglio modificare l'oggetto ho 2 metodi: 
    	//1) modifico l'oggetto
    	//2) creo una copia immutata (metodo funzionale) e apporto le modifiche del caso
    	
    }
    
    // ci ricorda qual è la classe model su cui lavorare
    public void setModel (Libretto model) {
    	this.model=model; 
    }

    //se hai altre operazioni da fare le aggiungi in initialize
    //es. popolamento della tendina con i voti da 18 a 30
    @FXML
    void initialize() {
        assert cmbPunti != null : "fx:id=\"cmbPunti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtVoti != null : "fx:id=\"txtVoti\" was not injected: check your FXML file 'Scene.fxml'.";
        
        //getItems, contiene la lista di oggetti che vengono mostrati quando si clicca sulla tendina
        cmbPunti.getItems().clear(); //pulire la lista
        
        //se non era Integer, bisognava fare il toString()
        for (int p=18; p<=30; p++) {
        	cmbPunti.getItems().add(p); 
        }
        
        
        List<Voto> voti= model.getVoti(); 
    	txtVoti.clear();
    	txtVoti.appendText("Hai superato "+voti.size()+" esami\n");
    	
    	for(Voto v: voti) {
    		txtVoti.appendText(v.toString()+"\n");
    	}
    }

}

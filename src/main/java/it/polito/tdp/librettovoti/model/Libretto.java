package it.polito.tdp.librettovoti.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.librettovoti.db.LiobrettoDAO;

//è il MODEL dell'applicazione
public class Libretto {
	
	public Libretto() {
	}
	
	//per evitare che un voto sia inserito 2 volte, se sbagli restituisce false
	public boolean add(Voto v) {
		LiobrettoDAO dao= new LiobrettoDAO(); 
		boolean result= dao.createVoto(v); 
		return result; 
	}
	
	//per ottenere una lista di oggetti da usare per ordinare i voti nella textArea
	public List<Voto> getVoti() {
		LiobrettoDAO dao= new LiobrettoDAO(); 
		return dao.readAllVoto(); 
	}
	
	//per stampare i voti nella console
	/*public String toString() {
		return this.voti.toString() ;
	}*/
}

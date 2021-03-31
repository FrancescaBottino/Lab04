package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	private CorsoDAO corsoDao;
	private StudenteDAO studenteDao;
	
	public Model() {
		corsoDao= new CorsoDAO();
		studenteDao= new StudenteDAO();
		
	}
	
	public List<Corso> getTuttiICorsi(){
		
		return corsoDao.getTuttiICorsi();
	}
	
	public Studente getInfoStudenteDataMatricola(int matricola){
		
		return studenteDao.getInfoStudenteDataMatricola(matricola);
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso){
		
		return corsoDao.getStudentiIscrittiAlCorso(corso);
		
	}
	
	public List<Corso> getCercaCorsi(String matricola){
		
		return corsoDao.getCercaCorsi(matricola);
	}
	
	public boolean cercaStudenteIscrittoCorso(String matricola, Corso c) {
		return corsoDao.cercaStudenteIscrittoCorso(matricola, c);
	}
	
}

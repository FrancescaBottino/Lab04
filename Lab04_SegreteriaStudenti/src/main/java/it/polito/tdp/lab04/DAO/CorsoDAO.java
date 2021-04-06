package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();
		corsi.add(new Corso("",0,"",0));

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				Corso c= new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);
				
				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
			}

			rs.close();
			st.close();
			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public void getCorso(Corso corso) {
		// TODO
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		String sql="select s.matricola, s.cognome, s.nome, s.cds "
				+ "from corso c, iscrizione i, studente s "
				+ "where c.codins=i.codins AND i.matricola= s.matricola AND c.nome=? ";
		
		List<Studente> result= new ArrayList<Studente>();
		
		String nomeCorso=corso.getNome();
		
		try {
			Connection conn= ConnectDB.getConnection();
			PreparedStatement st= conn.prepareStatement(sql);
			st.setString(1, nomeCorso);
			
			ResultSet rs= st.executeQuery();
			
			while(rs.next()) {
				
				Studente s= new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("cds"));
				result.add(s);
				
			}
			
			rs.close();
			st.close();
			conn.close();
			
			
		}catch(SQLException e) {
			
			throw new RuntimeException(e);
		}
		
		return result;
		
		
	}
	

	public List<Corso> getCercaCorsi(String matricola){
		
		//cercare se studente è iscritto al database
		//se si cercare i corsi a cui è iscritta la matricola
		
		
		String sql="select c.codins, c.crediti, c.nome, c.pd "
				+ "from corso c, iscrizione i "
				+ "where c.codins=i.codins AND i.matricola=? ";
		
		List<Corso> result=new ArrayList<Corso>();
		
		try {
			
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1, matricola);
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
			
				Corso c= new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result.add(c);
				
			}
			
			rs.close();
			st.close();
			conn.close();
			
		}catch(SQLException e) {
			
			throw new RuntimeException(e);
		}
		
		return result;
		
	}
	
	public boolean cercaStudenteIscrittoCorso(String matricola, Corso c) {
		
		boolean flag=false;
		
		String sql="select c.nome, s.matricola "
				+ "from corso c, iscrizione i, studente s "
				+ "where c.codins=i.codins AND i.matricola=s.matricola AND c.nome=? AND s.matricola=? ";
		
		
		try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1, c.getNome());
			st.setString(2, matricola);
			
			ResultSet rs=st.executeQuery();
			
			if(rs.next()) {
				
				flag=true;
				
			}
			
			rs.close();
			st.close();
			conn.close();
			
		}catch(SQLException e) {
			
			throw new RuntimeException(e);
		}
		
		return flag;
		
		
	}
	
	
	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		// TODO
		// ritorna true se l'iscrizione e' avvenuta con successo
		
		boolean flag=false;
		
        String sql= "INSERT INTO iscrizione "
				+ "VALUES (?, ?) ";
        
        try {
			Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			
			ResultSet rs=st.executeQuery();
			
			if(rs.next()) {
				
				flag=true;
				
			}
			
			rs.close();
			st.close();
			conn.close();
			
		}catch(SQLException e) {
			
			throw new RuntimeException(e);
		}
		
		return flag;
        
	}

}

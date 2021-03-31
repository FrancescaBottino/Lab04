package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	//data una matricola cerco nome e cognome
	
	public Studente getInfoStudenteDataMatricola(int matricola) {
		
		String sql="select * "
				+"from studente "
				+"where matricola=?";
		
		Studente result=null;
 		
        try {
			
			Connection conn=ConnectDB.getConnection(); //collegata con dbconnect
			PreparedStatement st=conn.prepareStatement(sql); //per inserire parametro ?
			st.setInt(1, matricola); //imposto primo parametro
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()) {
				Studente s= new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				result=s;
			}
			
		rs.close();
		st.close();
		conn.close();	
			
		}catch ( SQLException e) {
			
			throw new RuntimeException(e);
			
		}
		
		return result;
		
	}
				
		
	}
	

	



package dao;

import model.Sapato;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class SapatoDAO extends DAO {	
	public SapatoDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Sapato sapato) {
		boolean status = false;
		try {
			String sql = "INSERT INTO sapato (descricao, preco, quantidade) "
		               + "VALUES ('" + sapato.getDescricao() + "', "
		               + sapato.getPreco() + ", " + sapato.getQuantidade() + ");";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Sapato get(int id) {
		Sapato sapato = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM sapato WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 sapato = new Sapato(rs.getInt("id"), rs.getString("descricao"), (float)rs.getDouble("preco"), 
	                				   rs.getInt("quantidade"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return sapato;
	}
	
	
	public List<Sapato> get() {
		return get("");
	}

	
	public List<Sapato> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Sapato> getOrderByDescricao() {
		return get("descricao");		
	}
	
	
	public List<Sapato> getOrderByPreco() {
		return get("preco");		
	}
	
	
	private List<Sapato> get(String orderBy) {
		List<Sapato> sapatos = new ArrayList<Sapato>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM sapato" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Sapato p = new Sapato(rs.getInt("id"), rs.getString("descricao"), (float)rs.getDouble("preco"), 
	        			                rs.getInt("quantidade"));
	        			               
	            sapatos.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return sapatos;
	}
	
	
	public boolean update(Sapato sapato) {
		boolean status = false;
		try {  
			String sql = "UPDATE sapato SET descricao = '" + sapato.getDescricao() + "', "
					   + "preco = " + sapato.getPreco() + ", " 
					   + "quantidade = " + sapato.getQuantidade() + " WHERE id = " + sapato.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM sapato WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}
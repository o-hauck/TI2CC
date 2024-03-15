package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Sapato {
	private int id;
	private String descricao;
	private float preco;
	private int quantidade;
	
	public Sapato() {
		id = -1;
		descricao = "";
		preco = 0.00F;
		quantidade = 0;
	}

	public Sapato(int id, String descricao, float preco, int quantidade) {
		setId(id);
		setDescricao(descricao);
		setPreco(preco);
		setQuantidade(quantidade);
	}		
	
	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	

	


	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Sapato: " + descricao + "   Preço: R$" + preco + "   Quantidade.: " + quantidade ;
				
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((Sapato) obj).getID());
	}	
}
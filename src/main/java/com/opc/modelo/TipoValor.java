package com.opc.modelo;

import javax.persistence.*;
import java.util.*;	

@Entity
public class TipoValor {

	private int idTipoValor;
	private String descTipoValor;
	private String statTipoValor;
	
	public TipoValor() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public TipoValor(int idTipoValor, String descTipoValor, String statTipoValor) {
		super();
		this.idTipoValor = idTipoValor;
		this.descTipoValor = descTipoValor;
		this.statTipoValor = statTipoValor;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getIdTipoValor() {
		return idTipoValor;
	}


	public void setIdTipoValor(int idTipoValor) {
		this.idTipoValor = idTipoValor;
	}


	public String getDescTipoValor() {
		return descTipoValor;
	}


	public void setDescTipoValor(String descTipoValor) {
		this.descTipoValor = descTipoValor;
	}


	public String getStatTipoValor() {
		return statTipoValor;
	}


	public void setStatTipoValor(String statTipoValor) {
		this.statTipoValor = statTipoValor;
	}
	
	
	
	
	
	
	
	
	
}

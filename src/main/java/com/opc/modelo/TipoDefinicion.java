package com.opc.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class TipoDefinicion { 
	
	private int idTipoDefinicion;
	@NotNull
	private String descTipoDefinicion;
	@NotNull
	private String statTipoDefinicion;
	
		
	public TipoDefinicion(int idTipoDefinicion, String descTipoDefinicion, String statTipoDefinicion) {
		super();
		this.idTipoDefinicion = idTipoDefinicion;
		this.descTipoDefinicion = descTipoDefinicion;
		this.statTipoDefinicion = statTipoDefinicion;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getIdTipoDefinicion() {
		return idTipoDefinicion;
	}


	public void setIdTipoDefinicion(int idTipoDefinicion) {
		this.idTipoDefinicion = idTipoDefinicion;
	}


	public String getDescTipoDefinicion() {
		return descTipoDefinicion;
	}


	public void setDescTipoDefinicion(String descTipoDefinicion) {
		this.descTipoDefinicion = descTipoDefinicion;
	}


	public String getStatTipoDefinicion() {
		return statTipoDefinicion;
	}


	public void setStatTipoDefinicion(String statTipoDefinicion) {
		this.statTipoDefinicion = statTipoDefinicion;
	}
	
	
	
	

	
	

}

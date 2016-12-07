package com.opc.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Producto {
	
	private int idProducto;
	@NotNull
	private String descProducto;
	@NotNull
	private String statProducto;
	
	
	public Producto(){
		
	}
	
	public Producto(int idProducto, String descProducto, String statProducto) {
		super();
		this.idProducto = idProducto;
		this.descProducto = descProducto;
		this.statProducto = statProducto;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getIdProducto() {
		return idProducto;
	}


	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}


	public String getDescProducto() {
		return descProducto;
	}


	public void setDescProducto(String descProducto) {
		this.descProducto = descProducto;
	}


	public String getStatProducto() {
		return statProducto;
	}


	public void setStatProducto(String statProducto) {
		this.statProducto = statProducto;
	}


	
	
	

}

package com.opc.modelo;

import javax.persistence.*;
import java.util.*;


@Entity
public class UnidadMedida {
	
	private int idUnidad;
	private String descUnidadMedida;
	private String statUnidad;
	
	
	
	public UnidadMedida() {
		super();
		// TODO Auto-generated constructor stub
	}


	public UnidadMedida(int idUnidad, String descUnidadMedida, String statUnidad) {
		super();
		this.idUnidad = idUnidad;
		this.descUnidadMedida = descUnidadMedida;
		this.statUnidad = statUnidad;
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getIdUnidad() {
		return idUnidad;
	}


	public void setIdUnidad(int idUnidad) {
		this.idUnidad = idUnidad;
	}


	public String getdescUnidadMedida() {
		return descUnidadMedida;
	}


	public void setdescUnidadMedida(String descUnidadMedida) {
		this.descUnidadMedida = descUnidadMedida;
	}


	public String getStatUnidad() {
		return statUnidad;
	}


	public void setStatUnidad(String statUnidad) {
		this.statUnidad = statUnidad;
	}


	
	
	
	
}

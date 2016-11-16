package com.opc.modelo;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.*;

@Entity
@Table (name = "Marca")
public class Marca implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@Column(name="idMarca")
	private int idMarca;
	private String descripcion;
	

	//private Set<Dispositivo> dispositivo;
	
	public Marca() {
		
	}


	public Marca(int id){
		this.idMarca = id;
	}
	
	public Marca(String Descripcion){
		this.descripcion = Descripcion;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(int idMarca) {
		this.idMarca = idMarca;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	//@OneToMany(mappedBy ="marca", cascade = CascadeType.ALL)	
	//@JsonManagedReference
	
	/*
	public Set<Dispositivo> getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Set<Dispositivo> dispositivo) {
		this.dispositivo = dispositivo;
	}*/
	
	
}

package com.opc.modelo;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Sucursal implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	@Column(name="IdSucursal")
	private int idSucursal;
	private String nombre;
	private String direccion;
	private String telefono;
	private String Estado;
	private int turnos;
	private String configurado;
	


	public Sucursal() {
		
	}

	
	public Sucursal(int idSucursal)
	{
		this.idSucursal = idSucursal;
	}
	
	public Sucursal(String nombre, String direccion, String telefono, String estado){
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.Estado = estado;
	}
	
	//Getters y Setters

	public int getIdSucursal() {
		return idSucursal;
	}
	
	public void setIdSucursal(int idSucursal) {
		this.idSucursal = idSucursal;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDireccion() {
		return direccion;
	}
	
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getEstado() {
		return Estado;
	}
	
	public void setEstado(String estado) {
		Estado = estado;
	}
	
	
	public int getTurnos() {
		return turnos;
	}


	public void setTurnos(int turnos) {
		this.turnos = turnos;
	}


	public String getConfigurado() {
		return configurado;
	}


	public void setConfigurado(String configurado) {
		this.configurado = configurado;
	}
	
	


}

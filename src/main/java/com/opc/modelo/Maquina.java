package com.opc.modelo;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.*;
@Entity
public class Maquina implements Serializable {
	

	private int idMaquina;
	private String nombre;
	private String status;
	private Producto producto;
	//private Set<Dispositivo> dispositivo;
	
	public Maquina(){
		
	}
	
	public Maquina(String nombre){
		this.nombre = nombre;
	}
	
	public Maquina(String nombre, String status){
		this.nombre = nombre;
		this.status = status;
	}

	public Maquina(int idMaquina, String nombre, String status) {
		this.idMaquina = idMaquina;
		this.nombre = nombre;
		this.status = status;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="idMaquina")
	public int getIdMaquina() {
		return idMaquina;
	}

	public void setIdMaquina(int idMaquina) {
		this.idMaquina = idMaquina;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    @ManyToOne
    @JoinColumn(name = "id_Producto",referencedColumnName = "idProducto")		
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	//@OneToMany(mappedBy = "maquina", cascade = CascadeType.ALL)
	//@JsonManagedReference
/*	public Set<Dispositivo> getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Set<Dispositivo> dispositivo) {
		this.dispositivo = dispositivo;
	}
	*/
	
	
}

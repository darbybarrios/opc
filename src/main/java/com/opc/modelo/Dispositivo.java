package com.opc.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.*;			

@Entity
public class Dispositivo implements Serializable {
	

	private static final long serialVersionUID = 1L;

	private int idDispositivo;
	@NotNull
	private String descripcion;
	@NotNull
	private Date fechaIng;
	private Date fechaRetiro;
	@NotNull
	private String statDispositivo;
	@NotNull
	private Marca marca;
	@NotNull
	private Maquina maquina;
	@NotNull
	private Sucursal sucursal;
	@NotNull
	private String statReg;
	
	public Dispositivo(){
		
	}
	
	public Dispositivo(String descripcion, Date fechaIng, Date fechaRetiro, String statDispositivo, Marca marca, Maquina maquina, String statReg, Sucursal sucursal) {
		super();
		
		this.descripcion = descripcion;
		this.fechaIng = fechaIng;
		this.fechaRetiro = fechaRetiro;
		this.statDispositivo = statDispositivo;
		this.marca = marca;
		this.maquina = maquina;
		this.statReg = statReg;
		this.sucursal = sucursal;
	}

	public Dispositivo(int idDispositivo, String descripcion, String statDispositivo) {
        
		this.idDispositivo = idDispositivo;
		this.descripcion = descripcion;
		this.statDispositivo = statDispositivo;

	}	
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getIdDispositivo() {
		return idDispositivo;
	}


	public void setIdDispositivo(int idDispositivo) {
		this.idDispositivo = idDispositivo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Date getFechaIng() {
		return fechaIng;
	}


	public void setFechaIng(Date fechaIng) {
		this.fechaIng = fechaIng;
	}


	public Date getFechaRetiro() {
		return fechaRetiro;
	}


	public void setFechaRetiro(Date fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}


	public String getStatDispositivo() {
		return statDispositivo;
	}


	public void setStatDispositivo(String statDispositivo) {
		this.statDispositivo = statDispositivo;
	}


	@ManyToOne
	@JoinColumn(name = "id_marca", referencedColumnName = "idMarca")
	//@JsonBackReference
	public Marca getMarca() {
		return marca;
	}


	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	
	@ManyToOne
	@JoinColumn(name="id_Maquina",referencedColumnName = "idMaquina")
	//@JsonBackReference
	public Maquina getMaquina() {
		return maquina;
	}


	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}
	
	@ManyToOne
	@JoinColumn(name ="id_sucursal", referencedColumnName = "idSucursal")
	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public String getStatReg() {
		return statReg;
	}

	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}
	
	
	
	
	
    
}

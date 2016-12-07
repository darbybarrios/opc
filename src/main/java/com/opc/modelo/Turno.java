package com.opc.modelo;


import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Turno {
	private int idTurno;
	@NotNull
	private String descTurno;
	@NotNull
	//@Column(columnDefinition="time without time zone")
	private Calendar inicio;
	@NotNull
	//@Column(columnDefinition="time without time zone")
	private Calendar fin;
	@NotNull
	private String statTurno;
	@NotNull
	private Sucursal sucursal;
	private String tipoTurno;
	
	public Turno(){
		
	}
		
	public Turno(String descTurno,Calendar inicio, Calendar fin, String statTurno, Sucursal sucursal) {
		super();
		this.descTurno = descTurno;
		this.inicio = inicio;
		this.fin = fin;
		this.statTurno = statTurno;
		this.sucursal = sucursal;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getIdTurno() {
		return idTurno;
	}
	
	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}
	
	public String getDescTurno() {
		return descTurno;
	}
	
	public void setDescTurno(String descTurno) {
		this.descTurno = descTurno;
	}
	
	public Calendar getInicio() {
		return inicio;
	}
	
	public void setInicio(Calendar inicio) {
		this.inicio = inicio;
	}
	
	public Calendar getFin() {
		return fin;
	}
	
	public void setFin(Calendar fin) {
		this.fin = fin;
	}
	
	public String getStatTurno() {
		return statTurno;
	}
	
	public void setStatTurno(String statTurno) {
		this.statTurno = statTurno;
	}

	@ManyToOne
	@JoinColumn(name ="id_sucursal", referencedColumnName = "idSucursal")
	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public String getTipoTurno() {
		return tipoTurno;
	}

	public void setTipoTurno(String tipoTurno) {
		this.tipoTurno = tipoTurno;
	}	
	
	
	

}

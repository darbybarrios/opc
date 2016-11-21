package com.opc.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ResumenConectividad {
	private int idResumenConn;
	private Dispositivo dispositivo;
	private Tag tag;
	private int calidad;
	private String arrancado;
	private String parado;
	private Calendar fecha;
	
	public ResumenConectividad() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResumenConectividad(Dispositivo dispositivo, Tag tag, int calidad, String arrancado,
			String parado) {
		super();
		this.dispositivo = dispositivo;
		this.tag = tag;
		this.calidad = calidad;
		this.arrancado = arrancado;
		this.parado = parado;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)		
	public int getIdResumenConn() {
		return idResumenConn;
	}

	public void setIdResumenConn(int idResumenConn) {
		this.idResumenConn = idResumenConn;
	}
	
    @ManyToOne
    @JoinColumn(name = "id_Dispositivo",referencedColumnName = "idDispositivo")
	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Dispositivo dispo) {
		this.dispositivo = dispo;
	}

    @ManyToOne
    @JoinColumn(name = "id_Tag",referencedColumnName = "idTag")	
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public int getCalidad() {
		return calidad;
	}

	public void setCalidad(int calidad) {
		this.calidad = calidad;
	}

	public String getArrancado() {
		return arrancado;
	}

	public void setArrancado(String arrancado) {
		this.arrancado = arrancado;
	}

	public String getParado() {
		return parado;
	}

	public void setParado(String parado) {
		this.parado = parado;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
	
	
	
	
	
	

}

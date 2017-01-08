package com.opc.modelo;

import java.util.Calendar;

public class ProduccionFecha {
	String turno;	
	String version;	
	Integer cantidad;
	Calendar inicio;
	int velSet;
	int metaUnds;
	int totTiempo;
	int totUnds;
	double pr;
	
	
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Calendar getInicio() {
		return inicio;
	}
	public void setInicio(Calendar inicio) {
		this.inicio = inicio;
	}
	public int getVelSet() {
		return velSet;
	}
	public void setVelSet(int velSet) {
		this.velSet = velSet;
	}
	public int getMetaUnds() {
		return metaUnds;
	}
	public void setMetaUnds(int metaUnds) {
		this.metaUnds = metaUnds;
	}
	public int getTotTiempo() {
		return totTiempo;
	}
	public void setTotTiempo(int totTiempo) {
		this.totTiempo = totTiempo;
	}
	public int getTotUnds() {
		return totUnds;
	}
	public void setTotUnds(int totUnds) {
		this.totUnds = totUnds;
	}
	public double getPr() {
		return pr;
	}
	public void setPr(double pr) {
		this.pr = pr;
	}
	
	
}

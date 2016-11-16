package com.opc.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Sistema {
		
	private int idSistema;
	@NotNull
	private String descSistema;
	@NotNull
	private String statSistema;
	@NotNull
	private Area area;
	@NotNull
	private Maquina maquina;
	
	
	
	public Sistema() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Sistema(String descSistema, String statSistema, Area area) {
		super();
		this.descSistema = descSistema;
		this.statSistema = statSistema;
		this.area = area;
	}

	@Id
	public int getIdSistema() {
		return idSistema;
	}


	public void setIdSistema(int idSistema) {
		this.idSistema = idSistema;
	}


	public String getDescSistema() {
		return descSistema;
	}


	public void setDescSistema(String descSistema) {
		this.descSistema = descSistema;
	}


	public String getStatSistema() {
		return statSistema;
	}


	public void setStatSistema(String statSistema) {
		this.statSistema = statSistema;
	}

	@ManyToOne
	@JoinColumn(name="id_Area",referencedColumnName = "idArea")
	public Area getArea() {
		return area;
	}


	public void setArea(Area area) {
		this.area = area;
	}
	
	@ManyToOne
	@JoinColumn(name="id_Maquina",referencedColumnName = "idMaquina")
	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}	
	
	
	
	
	
	
	
	

}

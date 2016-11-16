package com.opc.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class SubSistema {
	
	private int idSubSistema;
	@NotNull
	private String descSubSistema;
	@NotNull
	private String statSubSistema;
	@NotNull
	private Area area;
	@NotNull
	private Sistema sistema;
	@NotNull
	private Maquina maquina;
	
	
	public SubSistema() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SubSistema(String descSubSistema, String statSubSistema, Sistema sistema) {
		super();
		this.descSubSistema = descSubSistema;
		this.statSubSistema = statSubSistema;
		this.sistema = sistema;
	}

	@Id
	public int getIdSubSistema() {
		return idSubSistema;
	}


	public void setIdSubSistema(int idSubSistema) {
		this.idSubSistema = idSubSistema;
	}


	public String getDescSubSistema() {
		return descSubSistema;
	}


	public void setDescSubSistema(String descSubSistema) {
		this.descSubSistema = descSubSistema;
	}


	public String getStatSubSistema() {
		return statSubSistema;
	}


	public void setStatSubSistema(String statSubSistema) {
		this.statSubSistema = statSubSistema;
	}

	@ManyToOne
	@JoinColumn(name="id_Sistema",referencedColumnName = "idSistema")
	public Sistema getSistema() {
		return sistema;
	}


	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
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

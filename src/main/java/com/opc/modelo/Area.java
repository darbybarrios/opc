package com.opc.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Area {
	private int idArea;
	@NotNull
	private String descArea;
	@NotNull
	private String statArea;
	@NotNull
	private Maquina maquina;
	
	public Area(){
		
	}
	
	public Area(String descArea, String statArea) {
		super();
		this.descArea = descArea;
		this.statArea = statArea;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	public int getIdArea() {
		return idArea;
	}
	
	public void setIdArea(int idArea) {
		this.idArea = idArea;
	}
	
	public String getDescArea() {
		return descArea;
	}
	
	public void setDescArea(String descArea) {
		this.descArea = descArea;
	}
	
	public String getStatArea() {
		return statArea;
	}
	
	public void setStatArea(String statArea) {
		this.statArea = statArea;
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

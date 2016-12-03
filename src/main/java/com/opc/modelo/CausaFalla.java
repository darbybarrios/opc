package com.opc.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;



@Entity
public class CausaFalla {
	
	private int idCausaFalla;
	private String descCausaFalla;
	private String statCausaFalla;
	@NotNull
	private Area area;
	@NotNull
	private Sistema sistema;
	@NotNull
	private SubSistema subSistema;
	private String tipoParada;
	private String restaTiempo;	
	@NotNull
	private Maquina maquina;
	
	
	public CausaFalla() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	public int getIdCausaFalla() {
		return idCausaFalla;
	}


	public void setIdCausaFalla(int idCausaFalla) {
		this.idCausaFalla = idCausaFalla;
	}


	public String getDescCausaFalla() {
		return descCausaFalla;
	}


	public void setDescCausaFalla(String descCausaFalla) {
		this.descCausaFalla = descCausaFalla;
	}


	public String getStatCausaFalla() {
		return statCausaFalla;
	}


	public void setStatCausaFalla(String statCausaFalla) {
		this.statCausaFalla = statCausaFalla;
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
	@JoinColumn(name="id_Sistema",referencedColumnName = "idSistema")
	public Sistema getSistema() {
		return sistema;
	}


	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	@ManyToOne
	@JoinColumn(name="id_SubSistema",referencedColumnName = "idSubSistema")
	public SubSistema getSubSistema() {
		return subSistema;
	}


	public void setSubSistema(SubSistema subSistema) {
		this.subSistema = subSistema;
	}


	public String getTipoParada() {
		return tipoParada;
	}

	public void setTipoParada(String tipoParada) {
		this.tipoParada = tipoParada;
	}

	public String getRestaTiempo() {
		return restaTiempo;
	}

	public void setRestaTiempo(String restaTiempo) {
		this.restaTiempo = restaTiempo;
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

package com.opc.modelo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;

import java.util.*;	

@Entity
public class Tag {
	
	private int idTag;
	@NotNull
	private String itemId;
	@NotNull
	private String descTag;
	@NotNull
	private Date fechaInicio;
	@NotNull
	private String statusTag;
	@NotNull
	private String statWeb;
	@NotNull
	private UnidadMedida unidadMedida;  //Ser refiere a si es MHZ, Unidades, etc.
	@NotNull
	private TipoValor tipoValor;  //, Que tipo de dato sera el Valor de esta TAG (Real, Entero, String, Boolean, Fecha)
	@NotNull
	private String statReg;
	private int escala; // mide el tope de valores de ciertas tags
	private int intervalo; // mide cada cuento tiempo se guardara en la base de datos
	private int valorBit;
	@NotNull
	private Dispositivo dispositivo;
	@NotNull
	private String tipoInformacion; //(1=Line Status , 2=Falla , 3=Produccion 4 = Velocidad 5 = Otro)
	
	
	public Tag() {
		// TODO Auto-generated constructor stub
	}



	
	
	public Tag(String itemId, String descTag, Date fechaInicio, String statusTag, String statWeb,
			UnidadMedida unidadMedida, TipoValor tipoValor, String statReg, int escala, int intervalo, int valorBit, 
			Dispositivo dispositivo,String tipoInformacion) {
		super();
		
		this.itemId = itemId;
		this.descTag = descTag;
		this.fechaInicio = fechaInicio;
		this.statusTag = statusTag;
		this.statWeb = statWeb;
		this.unidadMedida = unidadMedida;
		this.tipoValor = tipoValor;
		this.statReg = statReg;
		this.escala = escala;
		this.intervalo = intervalo;
		this.valorBit = valorBit;
		this.dispositivo = dispositivo;
		this.tipoInformacion = tipoInformacion;
	}





	public Tag(String itemId, String descTag) {
		super();

		this.itemId = itemId;
		this.descTag = descTag;

	}	
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getIdTag() {
		return idTag;
	}


	public void setIdTag(int idTag) {
		this.idTag = idTag;
	}


	public String getItemId() {
		return itemId;
	}


	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public String getdescTag() {
		return descTag;
	}


	public void setdescTag(String descTag) {
		this.descTag = descTag;
	}


	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public String getStatusTag() {
		return statusTag;
	}


	public void setStatusTag(String statusTag) {
		this.statusTag = statusTag;
	}


	public String getStatWeb() {
		return statWeb;
	}


	public void setStatWeb(String statWeb) {
		this.statWeb = statWeb;
	}

	@ManyToOne
	@JoinColumn(name="id_Unidad", referencedColumnName = "idUnidad")
	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}


	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	@ManyToOne
	@JoinColumn(name="id_TipoValor", referencedColumnName = "idTipoValor")
	public TipoValor getTipoValor() {
		return tipoValor;
	}


	public void setTipoValor(TipoValor tipoValor) {
		this.tipoValor = tipoValor;
	}


	public String getStatReg() {
		return statReg;
	}


	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}


	public int getEscala() {
		return escala;
	}


	public void setEscala(int escala) {
		this.escala = escala;
	}


	public int getIntervalo() {
		return intervalo;
	}


	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}


	public int getValorBit() {
		return valorBit;
	}


	public void setValorBit(int valorBit) {
		this.valorBit = valorBit;
	}

	
	
	@ManyToOne
	@JoinColumn(name="id_Dispositivo",referencedColumnName = "idDispositivo")
	public Dispositivo getDispositivo() {
		return dispositivo;
	}


	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public String getTipoInformacion() {
		return tipoInformacion;
	}

	public void setTipoInformacion(String tipoInformacion) {
		this.tipoInformacion = tipoInformacion;
	}

	
	
	
	
	
}

package com.opc.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class DetalleTag {
	
	private int idDetalleTag;
	@NotNull
	private String descDetalleTag;
	@NotNull
	private String tipoDetalle;
	@NotNull
	private Tag tag;
	@NotNull
	private String statDetalleTag;
	@NotNull
	private String valorDetTag;
	
	public DetalleTag(){
		
	}
	
	
	public DetalleTag(int idDetalleTag, String descDetalleTag, String tipoDetalle, Tag tag, String statDetalleTag, String valorDetTag) {
		super();
		this.idDetalleTag = idDetalleTag;
		this.descDetalleTag = descDetalleTag;
		this.tipoDetalle = tipoDetalle;
		this.tag = tag;
		this.statDetalleTag = statDetalleTag;
		this.valorDetTag = valorDetTag;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getIdDetalleTag() {
		return idDetalleTag;
	}


	public void setIdDetalleTag(int idDetalleTag) {
		this.idDetalleTag = idDetalleTag;
	}


	public String getDescDetalleTag() {
		return descDetalleTag;
	}


	public void setDescDetalleTag(String descDetalleTag) {
		this.descDetalleTag = descDetalleTag;
	}


	public String getTipoDetalle() {
		return tipoDetalle;
	}


	public void setTipoDetalle(String tipoDetalle) {
		this.tipoDetalle = tipoDetalle;
	}


	@ManyToOne
	@JoinColumn(name="id_Tag",referencedColumnName = "idTag")	
	public Tag getTag() {
		return tag;
	}


	public void setTag(Tag tag) {
		this.tag = tag;
	}


	public String getStatDetalleTag() {
		return statDetalleTag;
	}


	public void setStatDetalleTag(String statDetalleTag) {
		this.statDetalleTag = statDetalleTag;
	}


	public String getValorDetTag() {
		return valorDetTag;
	}


	public void setValorDetTag(String valorDetTag) {
		this.valorDetTag = valorDetTag;
	}
	
	
	

}

package com.opc.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProductoMaquina {
	private int idProductoMaquina;
	private Calendar fecCambio;
	private String statProducto;
	private Producto producto;
	private Maquina maquina;
	
	public ProductoMaquina() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductoMaquina(Calendar fecCambio, String statProducto, Producto producto, Maquina maquina) {
		super();
		this.fecCambio = fecCambio;
		this.statProducto = statProducto;
		this.producto = producto;
		this.maquina = maquina;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	public int getIdProductoMaquina() {
		return idProductoMaquina;
	}

	public void setIdProductoMaquina(int idProductoMaq) {
		this.idProductoMaquina = idProductoMaq;
	}

	public Calendar getFecCambio() {
		return fecCambio;
	}

	public void setFecCambio(Calendar fecCambio) {
		this.fecCambio = fecCambio;
	}

	public String getStatProducto() {
		return statProducto;
	}

	public void setStatProducto(String statProducto) {
		this.statProducto = statProducto;
	}

    @ManyToOne
    @JoinColumn(name = "id_Producto",referencedColumnName = "idProducto")	
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

    @ManyToOne
    @JoinColumn(name = "id_Maquina",referencedColumnName = "idMaquina")	
	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}
	
	
	
}

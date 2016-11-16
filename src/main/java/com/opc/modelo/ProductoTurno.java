package com.opc.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ProductoTurno {
	private int idProductoTurno;
	@NotNull
	private Calendar fechaInicio;
	@NotNull
	private Turno turno;
	@NotNull
	private Producto producto;
	
	public ProductoTurno(){
		
	}
	
	public ProductoTurno(int idProductoTurno, Calendar fechaInicio, Turno turno, Producto producto) {
		super();
		this.idProductoTurno = idProductoTurno;
		this.fechaInicio = fechaInicio;
		this.turno = turno;
		this.producto = producto;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getIdProductoTurno() {
		return idProductoTurno;
	}

	public void setIdProductoTurno(int idProductoTurno) {
		this.idProductoTurno = idProductoTurno;
	}

	public Calendar getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Calendar fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@ManyToOne
	@JoinColumn(name="id_Turno",referencedColumnName = "idTurno")	
	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	@ManyToOne
	@JoinColumn(name="id_Producto",referencedColumnName = "idProducto")	
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	
	
	

}

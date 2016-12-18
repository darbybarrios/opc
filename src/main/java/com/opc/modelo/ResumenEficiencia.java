package com.opc.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ResumenEficiencia {
	
	private int idResumenEficiencia;
	private Calendar fecRegistro;
	private int velocidad;
	private int cantUnidades;  //Cantidad Producida en el minuto
	private int cantAcum; //Cantidad Acumulada en ese minuto
	private float valorPr;
	private String statCalculo;
	private Turno turno;
	private ProductoMaquina productoMaquina;
	private Dispositivo dispositivo;
	private Calendar fechaJornada;
	private int velSeteada;
	
	public ResumenEficiencia() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResumenEficiencia(Calendar fecRegistro, Float valorPr, String statCalculo, Turno turno,
			ProductoMaquina productoMaquina) {
		super();
		this.fecRegistro = fecRegistro;
		this.valorPr = valorPr;
		this.statCalculo = statCalculo;
		this.turno = turno;
		this.productoMaquina = productoMaquina;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	public int getIdResumenEficiencia() {
		return idResumenEficiencia;
	}

	public void setIdResumenEficiencia(int idResumenEficiencia) {
		this.idResumenEficiencia = idResumenEficiencia;
	}

	
	
	
	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public int getCantUnidades() {
		return cantUnidades;
	}

	public void setCantUnidades(int cantUnidades) {
		this.cantUnidades = cantUnidades;
	}

	
	
	public int getCantAcum() {
		return cantAcum;
	}

	public void setCantAcum(int cantAcum) {
		this.cantAcum = cantAcum;
	}

	public Calendar getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(Calendar fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public Float getValorPr() {
		return valorPr;
	}

	public void setValorPr(Float valorPr) {
		this.valorPr = valorPr;
	}

	public String getStatCalculo() {
		return statCalculo;
	}

	public void setStatCalculo(String statCalculo) {
		this.statCalculo = statCalculo;
	}

    @ManyToOne
    @JoinColumn(name = "id_Turno",referencedColumnName = "idTurno")	
	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	
    @ManyToOne
    @JoinColumn(name = "id_ProductoMaquina",referencedColumnName = "idProductoMaquina")
	public ProductoMaquina getProductoMaquina() {
		return productoMaquina;
	}

	public void setProductoMaquina(ProductoMaquina productoMaquina) {
		this.productoMaquina = productoMaquina;
	}

    @ManyToOne
    @JoinColumn(name = "id_Dispositivo",referencedColumnName = "idDispositivo")	
	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public Calendar getFechaJornada() {
		return fechaJornada;
	}

	public void setFechaJornada(Calendar fechaJornada) {
		this.fechaJornada = fechaJornada;
	}

	public int getVelSeteada() {
		return velSeteada;
	}

	public void setVelSeteada(int velSeteada) {
		this.velSeteada = velSeteada;
	}
	
	
	
	

}
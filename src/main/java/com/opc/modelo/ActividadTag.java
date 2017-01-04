package com.opc.modelo;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.*;

@Entity
public class ActividadTag {
		private int idActividadTag;
		private Calendar fecha;
		private Date hora;
		private int calidad;
		private String valor;
		private Tag tag;
		private Date tiempoFunc;
		private Calendar duracion;
		private Area area;
		private Sistema sistema;
		private SubSistema subsistema;
		private CausaFalla causaFalla;
		private String statEditado;
		private Calendar fecEdicion;
		private DetalleTag detalleTag;
		private long tiempoFuncMs;
		private long duracionMs;
		private String comentarios;
		private String descFalla;
		private Calendar fechaJornada;
		private Turno turno;
		private long acumUnd;
		
		public ActividadTag() {
			super();
			// TODO Auto-generated constructor stub
		}


		
		public ActividadTag(int idActividadTag, Calendar fecha, Date hora, int calidad, String valor, Tag tag,
				            DetalleTag detalleTag) {
			super();
			this.idActividadTag = idActividadTag;
			this.fecha = fecha;
			this.hora = hora;
			this.calidad = calidad;
			this.valor = valor;
			this.tag = tag;
			this.detalleTag = detalleTag;
		}

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		public int getIdActividadTag() {
			return idActividadTag;
		}


		public void setIdActividadTag(int idActividadTag) {
			this.idActividadTag = idActividadTag;
		}


		public Calendar getFecha() {
			return fecha;
		}


		public void setFecha(Calendar fecha) {
			this.fecha = fecha;
		}


		public Date getHora() {
			return hora;
		}


		public void setHora(Date hora) {
			this.hora = hora;
		}


		public int getCalidad() {
			return calidad;
		}


		public void setCalidad(int calidad) {
			this.calidad = calidad;
		}
			
		
		public String getValor() {
			return valor;
		}


		public void setValor(String jiVariant) {
			this.valor = jiVariant;
		}

		
	    @ManyToOne
	    @JoinColumn(name = "id_Tag",referencedColumnName = "idTag")
		public Tag getTag() {
			return tag;
		}


		public void setTag(Tag tag) {
			this.tag = tag;
		}


		public Date getTiempoFunc() {
			return tiempoFunc;
		}


		public void setTiempoFunc(Date tiempoFunc) {
			this.tiempoFunc = tiempoFunc;
		}


		public Calendar getDuracion() {
			return duracion;
		}


		public void setDuracion(Calendar duracion) {
			this.duracion = duracion;
		}

	    @ManyToOne
	    @JoinColumn(name = "id_Area",referencedColumnName = "idArea")
		public Area getArea() {
			return area;
		}


		public void setArea(Area area) {
			this.area = area;
		}

	    @ManyToOne
	    @JoinColumn(name = "id_Sistema",referencedColumnName = "idSistema")
		public Sistema getSistema() {
			return sistema;
		}


		public void setSistema(Sistema sistema) {
			this.sistema = sistema;
		}

	    @ManyToOne
	    @JoinColumn(name = "id_SubSistema",referencedColumnName = "idSubSistema")
		public SubSistema getSubsistema() {
			return subsistema;
		}


		public void setSubsistema(SubSistema subsistema) {
			this.subsistema = subsistema;
		}


	    @ManyToOne
	    @JoinColumn(name = "id_CausaFalla",referencedColumnName = "idCausaFalla")	
		public CausaFalla getCausaFalla() {
			return causaFalla;
		}



		public void setCausaFalla(CausaFalla causaFalla) {
			this.causaFalla = causaFalla;
		}



		public String getStatEditado() {
			return statEditado;
		}


		public void setStatEditado(String statEditado) {
			this.statEditado = statEditado;
		}


		public Calendar getFecEdicion() {
			return fecEdicion;
		}


		public void setFecEdicion(Calendar fecEdicion) {
			this.fecEdicion = fecEdicion;
		}


	    @ManyToOne
	    @JoinColumn(name = "id_detalleTag",referencedColumnName = "idDetalleTag")
		public DetalleTag getDetalleTag() {
			return detalleTag;
		}



		public void setDetalleTag(DetalleTag detalleTag) {
			this.detalleTag = detalleTag;
		}



		public long getTiempoFuncMs() {
			return tiempoFuncMs;
		}



		public void setTiempoFuncMs(long tiempoFuncMs) {
			this.tiempoFuncMs = tiempoFuncMs;
		}



		public long getDuracionMs() {
			return duracionMs;
		}



		public void setDuracionMs(long duracionMs) {
			this.duracionMs = duracionMs;
		}



		public String getComentarios() {
			return comentarios;
		}



		public void setComentarios(String comentarios) {
			this.comentarios = comentarios;
		}



		public String getDescFalla() {
			return descFalla;
		}



		public void setDescFalla(String descFalla) {
			this.descFalla = descFalla;
		}



		public Calendar getFechaJornada() {
			return fechaJornada;
		}



		public void setFechaJornada(Calendar fechaJornada) {
			this.fechaJornada = fechaJornada;
		}


	    @ManyToOne
	    @JoinColumn(name = "id_Turno",referencedColumnName = "idTurno")	
		public Turno getTurno() {
			return turno;
		}


		public void setTurno(Turno turno) {
			this.turno = turno;
		}



		public long getAcumUnd() {
			return acumUnd;
		}



		public void setAcumUnd(long acumUnd) {
			this.acumUnd = acumUnd;
		}






}

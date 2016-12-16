package com.opc.controlador;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.modelo.ActividadTag;
import com.opc.modelo.Area;
import com.opc.modelo.CausaFalla;
import com.opc.modelo.Dispositivo;
import com.opc.modelo.Maquina;
import com.opc.modelo.ResumenConectividad;
import com.opc.modelo.Sistema;
import com.opc.modelo.SubSistema;
import com.opc.modelo.Tag;
import com.opc.modelo.Turno;
import com.opc.repositorio.RepositorioActividadTag;
import com.opc.repositorio.RepositorioArea;
import com.opc.repositorio.RepositorioCausaFalla;
import com.opc.repositorio.RepositorioDispositivo;
import com.opc.repositorio.RepositorioMaquina;
import com.opc.repositorio.RepositorioResumenConectividad;
import com.opc.repositorio.RepositorioSistema;
import com.opc.repositorio.RepositorioSubSistema;
import com.opc.repositorio.RepositorioTag;
import com.opc.repositorio.RepositorioTurno;

@Controller
public class ControladorActividadTag {
	
	@Autowired
	private RepositorioActividadTag daoActividad;
	@Autowired
	private RepositorioTag daoTag;
	@Autowired
	private RepositorioDispositivo daoDispositivo;
	@Autowired
	private RepositorioSistema daoSistema;	
	@Autowired
	private RepositorioSubSistema daoSubSistema;
	@Autowired
	private RepositorioArea daoArea;
	@Autowired
	private RepositorioMaquina daoMaquina;
	@Autowired
	private RepositorioTurno daoTurno;
	@Autowired
	private RepositorioCausaFalla daoCausaFalla;	
	@Autowired
	private RepositorioResumenConectividad daoResumenConn;		
	
	
	/*@RequestMapping("production/valor-actual-tag")
	@ResponseBody	
	public ActividadTag buscarValorActual(int idTag){
		ActividadTag actividad = daoActividad.findFirstByTagOrderByfechaAsc(idTag);
		return actividad;
	}*/

	
	@RequestMapping("buscarTag")
	@ResponseBody	
	public Tag buscarTag(int idDispositivo, String posTag){
		Dispositivo dispo = daoDispositivo.findOne(idDispositivo);
		Tag tag = daoTag.findByDispositivoAndStatWeb(dispo,posTag);
		return tag;
	}	
	
	@RequestMapping("Tablero_Tag1")
	@ResponseBody	
	public ActividadTag buscarValorTag1(int idDispositivo, String posTag){
		Dispositivo dispo = daoDispositivo.findOne(idDispositivo);
		Tag tag = daoTag.findByDispositivoAndStatWeb(dispo,posTag);
		ActividadTag actividad = daoActividad.findTopByTagOrderByFechaDesc(tag);
		return actividad;
	}	


	@RequestMapping("verificarArranque")
	@ResponseBody	
	public ResumenConectividad verificarArranque(int idDispositivo){
		Dispositivo dispo = daoDispositivo.findOne(idDispositivo);
		Tag tag = daoTag.findBytipoInformacionAndStatRegAndDispositivo("1", "0", dispo);
		ResumenConectividad rConn = daoResumenConn.findTopByTagOrderByFechaDesc(tag);
		
		return rConn;
	}	
	
	@RequestMapping("verificarParada")
	@ResponseBody	
	public ResumenConectividad verificarParada(int idDispositivo){
		Dispositivo dispo = daoDispositivo.findOne(idDispositivo);
		Tag tag = daoTag.findBytipoInformacionAndStatRegAndDispositivo("2", "0", dispo);
		ResumenConectividad rConn = daoResumenConn.findTopByTagOrderByFechaDesc(tag);
		
		return rConn;
	}	

	@RequestMapping("Fallas")
	@ResponseBody
	public List<ActividadTag> listarFallas(int idDispositivo){
		Dispositivo dispo = daoDispositivo.findOne(idDispositivo);
		Tag tag = daoTag.findBytipoInformacionAndStatRegAndDispositivo("2", "0", dispo);
		List<ActividadTag> fallas = daoActividad.findByTagOrderByFechaDesc(tag);
		return fallas;
	}

	@RequestMapping("Areas")
	@ResponseBody
	public List<Area> listarAreas(int idMaquina){
		Maquina maq = daoMaquina.findOne(idMaquina);
		List<Area> area = daoArea.findByStatAreaAndMaquina("0",maq);
		return area;
	}

	@RequestMapping("Sistemas")
	@ResponseBody
	public List<Sistema> listarSistemas(int parea, int idMaquina){
		Maquina maq = daoMaquina.findOne(idMaquina);
		Area area = daoArea.findOne(parea);
		List<Sistema> sis = daoSistema.findByStatSistemaAndAreaAndMaquina("0",area,maq);
		return sis;
	}

	@RequestMapping("SubSistemas")
	@ResponseBody
	public List<SubSistema> listarSubSistemas(int parea, int psistema, int idMaquina){
		Maquina maq = daoMaquina.findOne(idMaquina);
		Area area = daoArea.findOne(parea);
		Sistema sistema = daoSistema.findOne(psistema);
		List<SubSistema> subSis = daoSubSistema.findByStatSubSistemaAndAreaAndSistemaAndMaquina("0", area, sistema,maq);
		return subSis;
	}

	@RequestMapping("CausaFalla")
	@ResponseBody
	public List<CausaFalla> listarCausaFallas(int parea, int psistema, int psubSistema, int idMaquina){
		Maquina maq = daoMaquina.findOne(idMaquina);
		Area area = daoArea.findOne(parea);
		Sistema sistema = daoSistema.findOne(psistema);
		SubSistema sub = daoSubSistema.findOne(psubSistema);
		List<CausaFalla> subSis = daoCausaFalla.findByStatCausaFallaAndAreaAndSistemaAndSubSistemaAndMaquina("0", area, sistema, sub, maq);
		return subSis;
	}
	
	@RequestMapping("actParada")
	@ResponseBody	
	public int actualizarParada(int idActividad, int idArea, int idSistema,int idSubsistema,int idCausaFalla, String comen){
		ActividadTag actTag = daoActividad.findOne(idActividad);
		Area area = daoArea.findOne(idArea);
		Sistema sistema = daoSistema.findOne(idSistema);
		SubSistema subSistema = daoSubSistema.findOne(idSubsistema);
		CausaFalla causaFalla = daoCausaFalla.findOne(idCausaFalla);
		actTag.setArea(area);
		actTag.setSistema(sistema);
		actTag.setSubsistema(subSistema);
		actTag.setComentarios(comen);
		actTag.setStatEditado("1");
		actTag.setCausaFalla(causaFalla);
		daoActividad.save(actTag);
		return 1;
	}

	
	@RequestMapping("consultarParada")
	@ResponseBody	
	public ActividadTag consultarParada(int idParada){
		ActividadTag parada = daoActividad.findOne(idParada);
		return parada;
		
	}
	
	@RequestMapping("Fmecas-Periodo")
	@ResponseBody
	public List<ActividadTag> listarFallas(int idDispositivo, String inicio, String fin) throws ParseException{
	/*	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	    Date parsedDate = dateFormat.parse(yourString);
	    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());*/
		
		DateFormat formato;
		//System.out.println(inicio);
		formato = new SimpleDateFormat("yyyy-MM-dd");
		Date fecfin = formato.parse(fin);
		Date fecIni = formato.parse(inicio);
		
		Calendar cal = Calendar.getInstance();
		
		Calendar timestamFecFin = Calendar.getInstance();
		timestamFecFin.setTime(fecfin);
		Calendar timestamFecIni = Calendar.getInstance();
		timestamFecIni.setTime(fecIni);
		
		Date horaActual = new Date();
		System.out.println(horaActual);	
		
		//System.out.println(fecIni);
		Dispositivo dispo = daoDispositivo.findOne(idDispositivo);
		Tag tag = daoTag.findBytipoInformacionAndStatRegAndDispositivo("2", "0", dispo);
		System.out.println("Inicio : " + fecIni);
		System.out.println("Fin : " + fecfin);
		//List<ActividadTag> fallas = daoActividad.findByTagAndFechaBetweenOrderByFechaDesc(tag, timestamFecIni,timestamFecFin);
		List<ActividadTag> fallas = daoActividad.findByTagAndFechaLessThanEqualAndFechaGreaterThanEqualOrderByFechaDesc(tag, timestamFecFin, timestamFecIni);
		//List<ActividadTag> fallas = daoActividad.findByTagAndFechaLessThanOrderByFechaDesc(tag, fecfin)
		return fallas;
	}	
	
	@RequestMapping("Pr_Actual")
	@ResponseBody		
	public float eficienciaActual(int idDispositivo, int idTurno){
		
		Dispositivo dispo = daoDispositivo.findOne(idDispositivo);
		Tag unidades = daoTag.findBytipoInformacionAndStatRegAndDispositivo("4", "0", dispo);
		Tag velocidad = daoTag.findBytipoInformacionAndStatRegAndDispositivo("5", "0", dispo);
		ActividadTag undTotales = daoActividad.findTopByTagOrderByFechaDesc(unidades);
		ActividadTag velActual = daoActividad.findTopByTagOrderByFechaDesc(velocidad);
		Turno turno = daoTurno.findOne(idTurno);
		float pr = 0.0f;
		
		if (undTotales!= null){
		
			long tAgendado = (turno.getFin().getTimeInMillis() - turno.getInicio().getTimeInMillis())/60000;
			
			int und = Integer.parseInt(undTotales.getValor());
			int vel = Integer.parseInt(velActual.getValor());
			
			pr = ((und)/((tAgendado)*vel));
		
		}
		
		
		return pr;
		
	}

	
	@RequestMapping("graficosTipoParadasMp")
	@ResponseBody		
	public List<Object[]> graficoTipoParadasMp(String filtro, String grupo, int idSucursal, int idDispositivo,int idTurno, String inicio, String fin){
		// Grupo = Turno, Dia, Mes
		// Filtro = Sucursal, Maquina
		List<Object[]> resulParadas = null;
		
		if (grupo.equals("Dia")){
			
			if (filtro.equals("Sucursal")){
				
			}else if (filtro.equals("Dispositivo")){
				
				//List<Object[]> resulParadas = daoActividad.findParadasByDispositivoGroupTipoAndFecha(idDispositivo);
				
			}
			
		}else if (grupo.equals("Mes")) {
		}else if (grupo.equals("Turno")) {
			if (filtro.equals("Sucursal")){
				
			}else if (filtro.equals("Dispositivo")){
				
				resulParadas = daoActividad.findParadasByDispositivoAndTurno(idDispositivo,idTurno);
				//List<Object[]> resulParadas = daoActividad.findParadasByDispositivoGroupTipoAndFecha(idDispositivo);
				
			}
			
		}
		
		
		return resulParadas;
	}
	
	@RequestMapping("graficosCausaFallaParadasMp")
	@ResponseBody		
	public List<Object[]> graficoCausaFallasMp(String filtro, String grupo, int idSucursal, int idDispositivo,int idTurno, String inicio, String fin){
		// Grupo = Turno, Dia, Mes
		// Filtro = Sucursal, Maquina
		List<Object[]> resulParadas = null;
		
		if (grupo.equals("Dia")){
			
			if (filtro.equals("Sucursal")){
				
			}else if (filtro.equals("Dispositivo")){
				
				//List<Object[]> resulParadas = daoActividad.findParadasByDispositivoGroupTipoAndFecha(idDispositivo);
				
			}
			
		}else if (grupo.equals("Mes")) {
		}else if (grupo.equals("Turno")) {
			if (filtro.equals("Sucursal")){
				
			}else if (filtro.equals("Dispositivo")){
				
				resulParadas = daoActividad.findParadasByDispositivoAndTurnoByCausaFalla(idDispositivo, idTurno);
				//List<Object[]> resulParadas = daoActividad.findParadasByDispositivoGroupTipoAndFecha(idDispositivo);
				
			}
			
		}
		
		
		return resulParadas;
	}	

}

package com.opc.controlador;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Array;
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
import com.opc.modelo.Dispositivo;
import com.opc.modelo.Tag;
import com.opc.modelo.Turno;
import com.opc.repositorio.RepositorioActividadTag;
import com.opc.repositorio.RepositorioArea;
import com.opc.repositorio.RepositorioCausaFalla;
import com.opc.repositorio.RepositorioDispositivo;
import com.opc.repositorio.RepositorioMaquina;
import com.opc.repositorio.RepositorioResumenConectividad;
import com.opc.repositorio.RepositorioResumenEficiencia;
import com.opc.repositorio.RepositorioSistema;
import com.opc.repositorio.RepositorioSubSistema;
import com.opc.repositorio.RepositorioTag;
import com.opc.repositorio.RepositorioTurno;

@Controller
public class ControladorEficiencia {
	
	@Autowired
	private RepositorioResumenEficiencia daoEficiencia;
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
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	@RequestMapping("graficoEficienciaMaquinaDia")
	@ResponseBody		
	public List<Object[]> graficoEficienciaMaquina(int idDispo){
		List<Object[]> resulMaq = daoEficiencia.findByIdDispo(idDispo);
		
		return resulMaq;
			
	}
	
	@RequestMapping("EficienciaActual")
	@ResponseBody		
	public double eficienciaActual(int idDispositivo, int idTurno) throws ParseException{
		
		//double pr = 0.0;
		List<Object[]> valoresActuales = daoEficiencia.findByIdTurnoAndIdDispo(idTurno, idDispositivo);
		DateFormat dateF = new SimpleDateFormat("HH:mm");
		DateFormat dateD = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
				
		Turno turno = daoTurno.findOne(idTurno);
		Date horaActual = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(horaActual);
		long tActual = cal.getTimeInMillis();
		//long tInicio = turno.getInicio().getTimeInMillis();
		
		String iniStr = dateF.format(turno.getInicio().getTime());
		String fecStr = dateD.format(horaActual);
		String fecha = fecStr + " " + iniStr+":00";
		
		Date inicio = dateC.parse(fecha);
		Calendar inicioC = Calendar.getInstance();
		inicioC.setTime(inicio);
		long tInicio = inicioC.getTimeInMillis();
		long tAgendado = (tActual - tInicio)/60000;

		
		double pr = 0.0;
		if (valoresActuales != null){
			
			
			BigInteger und = (BigInteger) valoresActuales.get(0)[2];		
			BigDecimal vel = (BigDecimal) valoresActuales.get(0)[3];
			
			int undInt = und.intValue();
			double velDou = vel.doubleValue();
			
			pr = ((undInt)/((tAgendado)*velDou));
			
		}
		
		return (round(pr,2));
		
	}	
	

}

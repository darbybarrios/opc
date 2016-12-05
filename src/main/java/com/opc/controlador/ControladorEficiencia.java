package com.opc.controlador;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.modelo.ActividadTag;
import com.opc.modelo.Dispositivo;
import com.opc.modelo.ResumenEficiencia;
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
	
	public long tiempoTurno(Turno turno) throws ParseException{
		
		long horaInicio = turno.getInicio().getTimeInMillis();
		long horaFinal = turno.getFin().getTimeInMillis();
		long tiempoTot = 0;
		
		if (turno.getTipoTurno().equals("D")){

			tiempoTot = (horaFinal - horaInicio)/60000;
		}else{
			DateFormat dateC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date corte1 = dateC.parse("1970-01-01 24:00:00");
			Date corte2 = dateC.parse("1970-01-01 00:00:00");
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(corte1);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(corte2);
			long corteMl1 = cal1.getTimeInMillis();
			long corteMl2 = cal2.getTimeInMillis();
			long parteUno = corteMl1 - horaInicio;
			long parteDos = horaFinal - corteMl2;
			tiempoTot = (parteUno+parteDos)/60000;
			
		}
		return tiempoTot;
	}
	
	@RequestMapping("graficoEficienciaMaquinaDia")
	@ResponseBody		
	public List<Object[]> graficoEficienciaMaquina(int idDispo){
		List<Object[]> resulMaq = daoEficiencia.findByIdDispo(idDispo);
		List<Object[]> resulPr = new ArrayList<Object[]>();  

        for (int i = 0; i < resulMaq.size(); i++) {
        	double pr = 0.0;
        	Object[] aux = new Object[5];
          	aux[0] = resulMaq.get(i)[0];
          	aux[1] = resulMaq.get(i)[1];
          	aux[2] = resulMaq.get(i)[2];
          	aux[3] = resulMaq.get(i)[3];
 
			BigInteger und = (BigInteger) aux[2];		
			BigDecimal vel = (BigDecimal) aux[3];
			int undInt = und.intValue();
			double velDou = vel.doubleValue();          	
			pr = ((undInt)/((1440)*velDou));  //1440 Min tiene el dia
			aux[4] = round(pr*100,2);
        	
        	resulPr.add(i, aux);
        	          
      
        }
		return resulPr;
			
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

	@RequestMapping("EficienciaTurnoDiaMaq")
	@ResponseBody	
	public List<Object[]> eficienciaTurnoDiaMaq(int idDispo) throws ParseException{
		List<Object[]> resulMaq = daoEficiencia.findPrTurnoDiaByIdDispo(idDispo);
		List<Object[]> resulPr = new ArrayList<Object[]>();  

		DateFormat dateF = new SimpleDateFormat("HH:mm");
		DateFormat dateD = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dateC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
					
		
		
        for (int i = 0; i < resulMaq.size(); i++) {
        	double pr = 0.0;
        	Object[] aux = new Object[7];
          	aux[0] = resulMaq.get(i)[0];
          	aux[1] = resulMaq.get(i)[1];
          	aux[2] = resulMaq.get(i)[2];
          	aux[3] = resulMaq.get(i)[3];
          	aux[4] = resulMaq.get(i)[4];
          	aux[5] = resulMaq.get(i)[5];
          	
    		Turno turno = daoTurno.findOne((Integer) aux[1]);

    		//long tInicio = inicioC.getTimeInMillis(); Leer desde la Tabla Turno en Min
    		long tAgendado = tiempoTurno(turno);          	
 
			BigInteger und = (BigInteger) aux[4];		
			BigDecimal vel = (BigDecimal) aux[5];
			int undInt = und.intValue();
			double velDou = vel.doubleValue();          	
			pr = ((undInt)/((tAgendado)*velDou));  //1440 Min tiene el dia
			aux[6] = round(pr*100,2);
        	
        	resulPr.add(i, aux);
        	          
      
        }
		return resulPr;	
	}

	@RequestMapping("velocidadActual")
	@ResponseBody	
	public ResumenEficiencia velocidadActual(int idDispositivo){
		Dispositivo dispo = daoDispositivo.findOne(idDispositivo);
		ResumenEficiencia res = daoEficiencia.findTopByDispositivoOrderByFecRegistroDesc(dispo);
		return res;
		
	}
	

}

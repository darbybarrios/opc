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
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.modelo.ActividadTag;
import com.opc.modelo.Dispositivo;
import com.opc.modelo.Maquina;
import com.opc.modelo.ProduccionFecha;
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
	
	public Turno turno_actual(RepositorioTurno daoTurno) throws ParseException
	{
		DateFormat dateF = new SimpleDateFormat("HH:mm");
		String horaStr = dateF.format(new Date());
		boolean enc = false;
		Turno turnoActual = null;
		List<Turno> turno = new ArrayList<Turno>();

		List<Turno> turnos = (List<Turno>) daoTurno.findAll();
		Iterator<Turno> vectorTurno = turnos.iterator();
		
		while (vectorTurno.hasNext()){
			Turno tur = vectorTurno.next();
			String ini = dateF.format(tur.getInicio().getTime());
			String fin = dateF.format(tur.getFin().getTime());
			
			Date horaActual = dateF.parse(horaStr);
			Date horaIni = dateF.parse(ini);
			Date horaFin = dateF.parse(fin);
			
			if ((horaActual.compareTo(horaIni) >= 0) && (horaActual.compareTo(horaFin) <= 0)) {
				enc = true;
				turnoActual = tur;
				break;
			}
			
		}

		@SuppressWarnings("unchecked")
		List<Turno> turnosR = (List<Turno>) daoTurno.findAll();
		Iterator<Turno> vectorTurnoR = turnosR.iterator();		
		if (!enc){
			
			while (vectorTurnoR.hasNext()){
				Turno tur = vectorTurnoR.next();
				String ini = dateF.format(tur.getInicio().getTime());
				String fin = dateF.format(tur.getFin().getTime());
				
				Date horaActual = dateF.parse(horaStr);
				Date horaIni = dateF.parse(ini);
				Date horaFin = dateF.parse(fin);

				if (horaIni.compareTo(horaFin) > 0){
					enc = true;
					turnoActual = tur;
					break;
				}
				
				/*
				if (((horaActual.compareTo(horaIni) < 0) && (horaActual.compareTo(horaFin) < 0)) || 
				((horaActual.compareTo(horaIni) > 0) && (horaActual.compareTo(horaFin) > 0))) {
					enc = true;
					turnoActual = tur;
					break;
				} */
				
			}			
			
		}
		
		
		return turnoActual;
		
	}
	
	public long tiempoParadas(String tipo, int idDispositivo, int idTurno, Date inicio, Date fin, int mes){
		
		long resul = 0;
		DateFormat dateC = new SimpleDateFormat("yyyy-MM-dd");
		String inicioStr = dateC.format(inicio);
		
		if (tipo.equals("Turno")){
		}else if (tipo.equals("TurnoGeneral")){	
			resul = daoEficiencia.findTiempoRestadoByTurnoGeneral(inicioStr, idTurno);
		}else if (tipo.equals("Dia")){
			resul = daoEficiencia.findTiempoRestadoByDia(idDispositivo, inicioStr);
		}else if (tipo.equals("MesGeneral")){			
			resul = daoEficiencia.findTiempoRestadoByMesGeneral(mes);
		}else if (tipo.equals("DiaGeneral")){
			resul = daoEficiencia.findTiempoRestadoByDiaGeneral(inicioStr);
		}else if (tipo.equals("Rango")){
			
		}
		return resul;
	}
	
	public int cantidadLineas(String fecha){
		int tot = daoEficiencia.findcantidadLineas(fecha);
		return tot;
	}
	
	public Object[] calculoEfic_General(String fec, List<Object[]> listaDiaDispo, String tipoResta, long tagenda){
		Object[] aux = new Object[5];
		double pr = 0.0;
		double sumPr = 0.0;
		double prDispo = 0.0;
		int contDispo = 0;
		
		for (int j = 0; j < listaDiaDispo.size(); j++){  //Filtro los Dispositivos por Dia
			
			BigInteger und = (BigInteger) listaDiaDispo.get(j)[0];		
			BigDecimal vel = (BigDecimal) listaDiaDispo.get(j)[1];
			int undInt = und.intValue();
			Date fecha = (Date) listaDiaDispo.get(j)[3];
			int idDispo = (int) listaDiaDispo.get(j)[4];
			long tRest = (tiempoParadas(tipoResta,idDispo,0,fecha,new Date(),0))/60000;
			double velDou = vel.doubleValue();   
			
			if (velDou > 0){
				if (tipoResta.equals("Dia")){
					prDispo = ((undInt)/((tagenda - tRest)*velDou));  //1440 Min tiene el dia
				}else if (tipoResta.equals("TurnoGeneral")){
					
					prDispo = ((undInt)/((tagenda - tRest)*velDou));  //1440 Min tiene el dia
				}
			}
			
			sumPr = sumPr + prDispo;
			contDispo = contDispo + 1;
			
			if ((undInt == 0) && (tRest == 0 )){ //Verifico si la Maquia no estuvo para las 24H
				contDispo = contDispo - 1;
			}
			
		}
		
		if (contDispo > 0){
			pr = sumPr/contDispo;
		}
		
		pr = pr*100;
		if (pr > 100){
			pr = 100;
		}
		aux[0] = 0;
		aux[1] = 0;
		aux[2] = 0;
		aux[3] = fec;
		aux[4] = round(pr,2);
		
		return aux; 
		
	}
	
	//Eficiencias Maquinas Generales (Planta)
	public List<Object[]> eficienciaMaquinas(String tipo, String mes) throws ParseException{
		List<Object[]> listaDispo = daoEficiencia.findDispositivosEficiencia();
		DateFormat dateC = new SimpleDateFormat("yyyy-MM-dd");
		double pr = 0.0;
	
		
		List<Object[]> resulPr = new ArrayList<Object[]>();  
		ControladorTurnos ct = new ControladorTurnos();
		Turno turno = turno_actual(daoTurno);
		
		if (tipo.equals("Lista")){
			for (int i = 0; i < listaDispo.size(); i++){
				Object[] aux = new Object[5];
				aux[3] = listaDispo.get(i);
				Date fecD = (Date) aux[3];
				//String fecStr = (String) aux[3];
				String fec = dateC.format(fecD);
				List<Object[]> listaDiaDispo = daoEficiencia.findEficGenByDispoDia(fec);
	     		Object[] resul = calculoEfic_General(fec, listaDiaDispo,"Dia",1440);
				resulPr.add(i, resul);
			}
		}else if ((tipo.equals("Individual")) || (tipo.equals("IndividualActual"))) {
				

		    	Calendar fecha = Calendar.getInstance();
		    	fecha.setTime(new Date());
		    	Calendar fjornada = ct.determinarFechaJornada(fecha, turno);
				fjornada.add(Calendar.DAY_OF_MONTH, -1);
				DateFormat dateformatC = new SimpleDateFormat("yyyy-MM-dd");
				String jornadaStr = dateformatC.format(fjornada.getTime());	
				List<Object[]> listaDiaDispo = daoEficiencia.findEficGenByDispoDia(jornadaStr);
	     		Object[] resul = calculoEfic_General(jornadaStr, listaDiaDispo,"Dia",1440);
				resulPr.add(0, resul);				
		}else if (tipo.equals("Turno")) {
			    
			    List<Object[]> listasTurno = daoEficiencia.findUltimoTurno();
			    int sw = 0;
			    if (listasTurno.size() > 0){
				    int idTurno = (int) listasTurno.get(0)[4];
				    Date fturno = (Date) listasTurno.get(0)[3];
				    Turno utlturno = daoTurno.findOne(idTurno);
				    Turno turnoActual = turno_actual(daoTurno);
			    	Calendar fecha = Calendar.getInstance();
			    	fecha.setTime(new Date());
			    	Calendar fjornada = ct.determinarFechaJornada(fecha, utlturno);
					//fjornada.add(Calendar.DAY_OF_MONTH, -1);
					DateFormat dateformatC = new SimpleDateFormat("yyyy-MM-dd");
					String jornadaStr = dateformatC.format(fjornada.getTime());	
					String fturnoStr = dateformatC.format(fturno);
					
					if ((jornadaStr.equals(fturnoStr)) && (idTurno == turnoActual.getIdTurno())){
						sw = 1;
						
							if (listasTurno.size() > 1){
								idTurno = (int) listasTurno.get(1)[4];
								fturno = (Date) listasTurno.get(1)[3];
								fturnoStr = dateformatC.format(fturno);
							}else{
								idTurno = 0;
	      					}
						
					}
				    long tAgendado = tiempoTurno(utlturno);
	                List<Object[]> listaDiaDispo = daoEficiencia.findEficGenByDispoDiaTurno(idTurno, fturnoStr);
		     		Object[] resul = calculoEfic_General(fturnoStr, listaDiaDispo,"TurnoGeneral",tAgendado);
					resulPr.add(0, resul);					 
				
			    }
		}else if (tipo.equals("TurnoActual")) {
		    List<Object[]> listasTurno = daoEficiencia.findUltimoTurno();
		    int sw = 0;
		    if (listasTurno.size() > 0){
			    int idTurno = (int) listasTurno.get(0)[4];
			    Date fturno = (Date) listasTurno.get(0)[3];
			    Turno utlturno = daoTurno.findOne(idTurno);
			    Turno turnoActual = turno_actual(daoTurno);
		    	Calendar fecha = Calendar.getInstance();
		    	fecha.setTime(new Date());
		    	Calendar fjornada = ct.determinarFechaJornada(fecha, utlturno);
				//fjornada.add(Calendar.DAY_OF_MONTH, -1);
				DateFormat dateformatC = new SimpleDateFormat("yyyy-MM-dd");
				String jornadaStr = dateformatC.format(fjornada.getTime());	
				String fturnoStr = dateformatC.format(fturno);
				
				if ((jornadaStr.equals(fturnoStr)) && (idTurno == turnoActual.getIdTurno())){
				    long tAgendado = tiempoTurno(utlturno);
	                List<Object[]> listaDiaDispo = daoEficiencia.findEficGenByDispoDiaTurno(idTurno, fturnoStr);
		     		Object[] resul = calculoEfic_General(fturnoStr, listaDiaDispo,"TurnoGeneral",tAgendado);
					resulPr.add(0, resul);	
					
				}
		    }
		}
		
		return resulPr;
	}
	
	
	@RequestMapping("velocidadSeteada")
	@ResponseBody	
	public int velocidadSeteada(int idDispositivo){
		Dispositivo dispo = daoDispositivo.findOne(idDispositivo);
		Maquina maq = daoMaquina.findOne(dispo.getMaquina().getIdMaquina());
		int velSet = maq.getVelocidad();
		return velSet;
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
	public List<Object[]> graficoEficienciaMaquina(int idDispo, String tipo,int idTurno) throws ParseException{
        
		List<Object[]> resulMaq = null;
		long tRest = 0;
		double velDou = 0.0;
		Turno turno = daoTurno.findOne(idTurno);
		ControladorTurnos ct = new ControladorTurnos();
    	Calendar fechaA = Calendar.getInstance();
    	fechaA.setTime(new Date());
    	Calendar fjornada = ct.determinarFechaJornada(fechaA, turno);
		DateFormat dateC = new SimpleDateFormat("yyyy-MM-dd");
		String jornadaStr = dateC.format(fjornada.getTime());
		
		if (tipo.equals("Dia")){
			resulMaq = daoEficiencia.findByIdDispo(idDispo);
		}else if (tipo.equals("Hora")){
			resulMaq = daoEficiencia.findByIdDispoAndFecha(idDispo, jornadaStr);
		}
		
		List<Object[]> resulPr = new ArrayList<Object[]>();  
		

        for (int i = 0; i < resulMaq.size(); i++) {
        	double pr = 0.0;
        	Object[] aux = new Object[5];
          	aux[0] = resulMaq.get(i)[0];
          	aux[1] = resulMaq.get(i)[1];
          	aux[2] = resulMaq.get(i)[2];
          	aux[3] = resulMaq.get(i)[3];
          	//aux[4] = resulMaq.get(i)[4];
 
			BigInteger und = (BigInteger) aux[2];		
			//int vel = (int) aux[3];
			int undInt = und.intValue();
			int velSet = velocidadSeteada(idDispo); //Velocidad Seteada Directo en la Maquina
			Date fecha = (Date) aux[1];
			
			if (tipo.equals("Dia")){
				tRest = (tiempoParadas("Dia",idDispo,0,fecha,new Date(),0))/60000;
				velDou = (double) velSet;    
				if (velDou > 0){
					pr = ((undInt)/((1440 - tRest)*velDou));  //1440 Min tiene el dia
				}
			else if (tipo.equals("Hora")){
				//tRest = (tiempoParadas("Dia",idDispo,0,fecha,new Date(),0))/60000;
				velDou = (double) velSet;     
				if (velDou > 0){
					pr = ((undInt)/((60 - tRest)*velDou));  //60 Min tiene la Hora
				}	
				String hr = (String) aux[4];
				aux[1] = hr;
			}
		}
			
			
			
			pr = pr*100;
			if (pr > 100){
				pr = 100.0;
			}
			
     		aux[4] = round(pr,2);
        	
        	resulPr.add(i, aux);
        	          
      
        }
		return resulPr;
			
	}
	
	/*
	
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
			int vel = (int) aux[3];
			int undInt = und.intValue();
			int velSet = velocidadSeteada(idDispo); //Velocidad Seteada Directo en la Maquina
			Date fecha = (Date) aux[1];
			
			long tRest = (tiempoParadas("Dia",idDispo,0,fecha,new Date(),0))/60000;
		
			double velDou = (double) vel;     
			if (velDou > 0){
				pr = ((undInt)/((1440 - tRest)*velDou));  //1440 Min tiene el dia
			}
			
			pr = pr*100;
			if (pr > 100){
				pr = 100.0;
			}
			
     		aux[4] = round(pr,2);
        	
        	resulPr.add(i, aux);
        	          
      
        }
		return resulPr;
			
	}
	
	*/
	
	
	
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
		int velSet = velocidadSeteada(idDispositivo); //Velocidad Seteada Directo en la Maquina
		
		double pr = 0.0;

		if (valoresActuales != null){
			
			
			BigInteger und = (BigInteger) valoresActuales.get(0)[2];		
			//BigDecimal vel = (BigDecimal) valoresActuales.get(0)[3];
			int vel = (int) valoresActuales.get(0)[3];
			
			
			
			int undInt = und.intValue();
			//double velDou = vel.doubleValue();
			double velDou = vel;
			
			if (velDou > 0){
				pr = ((undInt)/((tAgendado)*velDou));
			}
			
			pr = pr*100;
			if (pr > 100){
				pr = 100.0;
			}		
			
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
		
		int velSet = velocidadSeteada(idDispo);			
		
		
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
			int vel = (int) aux[5];
			int undInt = und.intValue();
			
			
			
			double velDou = (double) vel;         
			if (velDou > 0){
				pr = ((undInt)/((tAgendado)*velDou));  //1440 Min tiene el dia
			}
			
			pr = pr*100;
			if (pr > 100){
				pr = 100.0;
			}
			/*
			
			if (velSet > 0){
				pr = ((undInt)/((tAgendado)*velSet));  //1440 Min tiene el dia
			}*/
			aux[6] = round(pr,2);
        	
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

	//Mes Anterior
	@RequestMapping("eficienciaGeneral")
	@ResponseBody		
	public double eficienciaGeneral(){
		double resul = 0.0;
		long tRest = 0;
		double pr = 0.0;
		List<Object[]> resulMes = daoEficiencia.eficicienciaGeneralMensual();
		if (resulMes.size() < 2){
			resul = 0.0;
		}else{

			
        	Object[] aux = new Object[5];
          	aux[0] = resulMes.get(1)[0];
          	aux[1] = resulMes.get(1)[1];//unidades
          	aux[2] = resulMes.get(1)[2];//velocidad
          	aux[3] = resulMes.get(1)[3];
          	aux[4] = resulMes.get(1)[4];//pr
 
			BigInteger und = (BigInteger) aux[0];		
			BigDecimal vel = (BigDecimal) aux[1];
			int undInt = und.intValue();
			//int velSet = velocidadSeteada(idDispo); //Velocidad Seteada Directo en la Maquina
			
    		int mes = (int) aux[2];			
    		tRest = (tiempoParadas("MesGeneral",0,0,new Date(),new Date(),mes))/60000;
    		double velDou = vel.doubleValue();     
			if (velDou > 0){
				pr = ((undInt)/((43200 - tRest)*velDou));  //43200 Min tiene el Mes
			}
	
			if (pr*100 > 100){
				pr = 100;
			}			
				
				
			}
		
		
		return pr;
	}

	@RequestMapping("eficienciaGeneralDiaAnterior")
	@ResponseBody	
	public double eficienciaGeneralDia() throws ParseException{
		double resul = 0.0;;
		int ind = 0;
	
		List<Object[]> resulDia = eficienciaMaquinas("Individual","");
		
		if (resulDia.size() > 0){
			double pr = (double) resulDia.get(ind)[4];
			//double resDl = pr.doubleValue();	
			resul = pr;
		}
		return round(resul,2);
		
		

	}
	
	@RequestMapping("eficienciaGeneralTurnoAnterior")
	@ResponseBody	
	public double eficienciaGeneralTurno() throws ParseException{
		double resul = 0.0;
		int ind = 0;
	
		List<Object[]> resulDia = eficienciaMaquinas("Turno","");
		
		if (resulDia.size() > 0){
			double pr = (double) resulDia.get(ind)[4];
			//double resDl = pr.doubleValue();	
			if (pr > 100){
				pr = 100;
			}
			resul = pr;
		}
		return round(resul,2);
		
	}	
	
	//Se tomara como Eficiencia Actual del Dia
	@RequestMapping("eficienciaGeneralTurnoActual")
	@ResponseBody	
	public double eficienciaGeneralTurnoActual() throws ParseException{
		double resul = 0.0;
		int ind = 0;
	
		List<Object[]> resulDia = eficienciaMaquinas("TurnoActual","");
		
		if (resulDia.size() > 0){
			double pr = (double) resulDia.get(ind)[4];
			//double resDl = pr.doubleValue();	
			if (pr > 100){
				pr = 100;
			}
			resul = pr;
		}
		return round(resul,2);
		
		/*double resul = 0.0;
		List<Object[]> resulDia = daoEficiencia.findEficienciaGeneralTurnoAnterior();
		//BigInteger turno = 100000000;
		//BigInteger turno = (BigInteger) resulDia.get(1)[1];
		BigInteger totUnd =  (BigInteger) resulDia.get(0)[2];
		BigDecimal vel = (BigDecimal) resulDia.get(0)[3];
		//double pr = (double) resulDia.get(1)[3];
		Date fecha = (Date) resulDia.get(0)[5];
		double velDou = vel.doubleValue();
		//int iTurno = turno.intValue();
		Turno turno = daoTurno.findOne((Integer) resulDia.get(0)[1]);
		long tAgendado = tiempoTurno(turno); 		
		//int iTurno = Integer.parseInt(turno);
		
		long tRest = (tiempoParadas("TurnoGeneral",0,1,fecha,new Date(),0))/60000;
		int iTotUnd = totUnd.intValue();
		
		resul = (iTotUnd/((tAgendado - tRest)*velDou))*100;
		
		
		if (resul > 100){
			resul = 100;
		}		
		
		return round(resul,2);*/
	}	
	
	
	
	@RequestMapping("graficoEficienciaGeneral")
	@ResponseBody		
	public List<Object[]> graficoEficienciaGeneral(String tipoGr) throws ParseException{
		List<Object[]> resulMaq = null;
		long tRest = 0;
		List<Object[]> resulPr = new ArrayList<Object[]>();
		if (tipoGr.equals("Dia")){
			 //resulMaq = daoEficiencia.findEficienciaGeneralGrafico();
			resulMaq = eficienciaMaquinas("Lista","");
			resulPr = resulMaq;
			
		}else if (tipoGr.equals("Mes")){
			 resulMaq = daoEficiencia.eficicienciaGeneralMensual();
		}
		
       for (int i = 0; i < resulMaq.size(); i++) {
        	double pr = 0.0;
        	Object[] aux = new Object[5];
          	aux[0] = resulMaq.get(i)[0];
          	aux[1] = resulMaq.get(i)[1];//unidades
          	aux[2] = resulMaq.get(i)[2];//velocidad
          	aux[3] = resulMaq.get(i)[3];
          	//aux[4] = resulMaq.get(i)[4];//pr
 

			//int velSet = velocidadSeteada(idDispo); //Velocidad Seteada Directo en la Maquina
			

			if (tipoGr.equals("Dia")){
			/*	BigInteger und = (BigInteger) aux[0];		
				BigDecimal vel = (BigDecimal) aux[1];
				int undInt = und.intValue();				
				Date fecha = (Date) aux[3];
				DateFormat dateC = new SimpleDateFormat("yyyy-MM-dd");
				String fechaStr = dateC.format(fecha);
				int tLin = cantidadLineas(fechaStr);
				tRest = (tiempoParadas("DiaGeneral",0,0,fecha,new Date(),0))/60000;
				double velDou = vel.doubleValue();     
				if ((velDou > 0) && (tLin > 0)){
					
					pr = ((undInt)/(((1440 * tLin) - tRest)*velDou));  //1440 Min tiene el dia
				}				*/
			}else if (tipoGr.equals("Mes")){
				BigInteger und = (BigInteger) aux[0];		
				BigDecimal vel = (BigDecimal) aux[1];
				int undInt = und.intValue();				
				double mesD = (double) aux[2];
				int mes = (int) mesD;
				tRest = (tiempoParadas("MesGeneral",0,0,new Date(),new Date(),mes))/60000;
				double velDou = vel.doubleValue();   
				if (velDou > 0){
					
					pr = ((undInt)/((43200 - tRest)*velDou));  //43200 Min tiene el Mesjyuiiio0'p9p´´popñ´+p{+opoopño 
				}				

			
				pr = pr*100;

				if (pr > 100){
					pr = 100;
				}
				
				aux[4] = round(pr*100,2);
	        	
	        	resulPr.add(i, aux);
			
			}			
			
   
      
        }
		return resulPr;
			
	}	

	@RequestMapping("topProduccion")
	@ResponseBody		
	public List<Object[]> topProduccion(){

		List<Object[]> resulMaq = daoEficiencia.topMaquinas();
		List<Object[]> resulPr = new ArrayList<Object[]>();  
		

        for (int i = 0; i < resulMaq.size(); i++) {
        	double pr = 0.0;
        	Object[] aux = new Object[7];
          	aux[0] = resulMaq.get(i)[0];//IdDispo
          	aux[1] = resulMaq.get(i)[1];//TotUnd
          	aux[2] = resulMaq.get(i)[2];//Velocidad
          	aux[3] = resulMaq.get(i)[3];//PR
           	aux[5] = resulMaq.get(i)[5];//idMaquina
           	aux[6] = resulMaq.get(i)[6];//Nombre
 
         	resulPr.add(i, aux);
        	          
      
        }
		return resulPr;
			
	}	

	@RequestMapping("produccionMensual")
	@ResponseBody	
	public List<Object[]> produccionMensual(){
		List<Object[]> resulProd = daoEficiencia.produccionMensual();
		return resulProd;
	}
	
	@RequestMapping("produccionAnual")
	@ResponseBody	
	public long produccionAnual(){
		long resulProd = daoEficiencia.produccionAnual();
		return resulProd;
	}	
	
	@RequestMapping("produccionFecha")
	@ResponseBody	
	public List<ProduccionFecha> produccionFecha(int idDispositivo, String fecha_ini, String fecha_fin){
		List<Object[]> resulProd = daoEficiencia.produccionFecha(idDispositivo, fecha_ini, fecha_fin);
		
		List<ProduccionFecha> listaProduccion = new ArrayList<ProduccionFecha>();
		
		for (int i = 0; i < resulProd.size(); i++) {
			BigInteger cantidad;
			ProduccionFecha produccion = new ProduccionFecha();
			cantidad = (BigInteger) resulProd.get(i)[2];
			produccion.setTurno((String)resulProd.get(i)[0]);
			produccion.setVersion((String)resulProd.get(i)[1]);
			produccion.setCantidad(cantidad.intValue());
			listaProduccion.add(produccion);
		}		
		
		return listaProduccion;
	}
	
	
	

	

}

package com.opc.controlador;


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

import com.opc.modelo.Sucursal;
import com.opc.modelo.Turno;
import com.opc.repositorio.RepositorioSucursal;
import com.opc.repositorio.RepositorioTurno;

@Controller
public class ControladorTurnos {
	
	@Autowired
	public RepositorioTurno daoTurno;

	@Autowired
	private RepositorioSucursal daoSucursal;
	
	
	
	public ControladorTurnos() {
		super();
		// TODO Auto-generated constructor stub
	}

	@RequestMapping("listar-turnos")
	@ResponseBody
	public List<Turno> listar_turnos(){
		List<Turno> turnos = (List<Turno>) daoTurno.findAll();
		return turnos;
	}

	@RequestMapping("nuevo-turno")
	@ResponseBody	
	public void nuevo_turno(String desc, String inicio, String fin, int idSucursal, String tipoTurno, int secuencia) throws ParseException{
		DateFormat formato = new SimpleDateFormat("HH:mm:ss");
		Date horaIni = formato.parse(inicio);
		Date horaFin = formato.parse(fin);
				
		Calendar hIni = Calendar.getInstance();
		hIni.setTime(horaIni);
		
		Calendar hFin = Calendar.getInstance();
		hFin.setTime(horaFin);		
		

		Sucursal sucursal = daoSucursal.findOne(idSucursal);
		Turno turn = new Turno(desc,hIni,hFin,"0",sucursal,tipoTurno,secuencia);
		daoTurno.save(turn);
		
	}
	
	@RequestMapping("modificar-turno")
	@ResponseBody	
	public void modificar_turno(int idTurno, String desc, String inicio, String fin, int idSucursal, String tipoTurno, int secuencia) throws ParseException{
		DateFormat formato = new SimpleDateFormat("HH:mm:ss");
		Date horaIni = formato.parse(inicio);
		Date horaFin = formato.parse(fin);
				
		Calendar hIni = Calendar.getInstance();
		hIni.setTime(horaIni);
		
		Calendar hFin = Calendar.getInstance();
		hFin.setTime(horaFin);		
		

		Sucursal sucursal = daoSucursal.findOne(idSucursal);
		Turno turno = new Turno();
		turno.setIdTurno(idTurno);
		turno.setDescTurno(desc);
		turno.setInicio(hIni);
		turno.setFin(hFin);
		turno.setSucursal(sucursal);
		turno.setStatTurno("0");
		turno.setTipoTurno(tipoTurno);
		turno.setSecuencia(secuencia);
		daoTurno.save(turno);
		
	}

	@RequestMapping("fecha-jornada-actual")
	@ResponseBody	
	public Calendar determinarFechaJornada(Calendar fecha, Turno turno) throws ParseException{
		Calendar fechaInicio = null;
		DateFormat dateF = new SimpleDateFormat("HH:mm");
		String horaCorte = "23:59";
		Date horaC = dateF.parse(horaCorte);
		String ini = dateF.format(fecha.getTime());
		Date horaActual = dateF.parse(ini);
		
		if (turno.getTipoTurno().equals("D")){
				fechaInicio = fecha;					
		}else{
			if ((horaActual.compareTo(horaC)) > 0) {
				fechaInicio = fecha;
				fechaInicio.add(Calendar.DAY_OF_MONTH, -1);
				
			}else{
				
				fechaInicio = fecha;
			}
		}
		
		return fechaInicio;
	}

	
	
	@RequestMapping("turno-actual")
	@ResponseBody		
	public Turno turno_actual() throws ParseException
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
	
	
	
	
	

}

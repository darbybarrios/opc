package com.opc;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.AccessBase;
import org.openscada.opc.lib.da.AddFailedException;
import org.openscada.opc.lib.da.DataCallback;
import org.openscada.opc.lib.da.DuplicateGroupException;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.SyncAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.opc.controlador.ControladorTurnos;
import com.opc.modelo.ActividadTag;
import com.opc.modelo.DetalleTag;
import com.opc.modelo.Dispositivo;
import com.opc.modelo.ProductoMaquina;
import com.opc.modelo.ResumenConectividad;
import com.opc.modelo.ResumenEficiencia;
import com.opc.modelo.Tag;
import com.opc.modelo.TipoValor;
import com.opc.modelo.Turno;
import com.opc.modelo.accesarDispositivo;
import com.opc.repositorio.RepositorioActividadTag;
import com.opc.repositorio.RepositorioDetalleTag;
import com.opc.repositorio.RepositorioDispositivo;
import com.opc.repositorio.RepositorioProducto;
import com.opc.repositorio.RepositorioProductoMaquina;
import com.opc.repositorio.RepositorioResumenConectividad;
import com.opc.repositorio.RepositorioResumenEficiencia;
import com.opc.repositorio.RepositorioTag;
import com.opc.repositorio.RepositorioTurno;



@SpringBootApplication
@EnableAutoConfiguration
public class OpcApplication {
	private static final Logger log = LoggerFactory.getLogger(OpcApplication.class);
	boolean parado;
	boolean arrancado;
	boolean manual = false;

	public static void main(String[] args) {
		SpringApplication.run(OpcApplication.class, args);
	}
	

	
	public long tiempoFucionamiento(Calendar fechaParo,Dispositivo dispo, RepositorioTag daoTag, RepositorioActividadTag daoActividadTag) throws ParseException{
		//RepositorioTag daoTag = null;
		//RepositorioActividadTag daoActividadTag = null;
		long func = 0;
		Tag tag = daoTag.findBytipoInformacionAndStatRegAndDispositivo("1", "0", dispo);
		ActividadTag act = daoActividadTag.findTopByTagAndValorOrderByFechaDesc(tag,"1"); //Valor = 1 , Ultimo Arranque
		
		if (act != null){
			//System.out.println("arranque : " + fechaParo.toString());
			//System.out.println("paro : " + act.getFecha().toString());
			func = fechaParo.getTimeInMillis() - act.getFecha().getTimeInMillis();
			
	
			
			final Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(func);
				
			DateFormat formato = new SimpleDateFormat("hh:mm:ss");
			String dateFormatted = formato.format(cal.getTime());
			Date tiempo = formato.parse(dateFormatted); 
			//System.out.println("Milisegundos : " + func);
		}
		return func;
	}
	
	public long duracion(Calendar fechaArranque,Dispositivo dispo, RepositorioTag daoTag, RepositorioActividadTag daoActividadTag){
		long func = 0;
		Tag tag = daoTag.findBytipoInformacionAndStatRegAndDispositivo("2", "0", dispo);
		
		if (tag != null){
		
			ActividadTag act = daoActividadTag.findTopByTagOrderByFechaDesc(tag);
			
			if (act != null){
			
				func = fechaArranque.getTimeInMillis() - act.getFecha().getTimeInMillis();
				final Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(func);
				act.setDuracionMs(func);
				daoActividadTag.save(act);
			
			}
		
		}
		
		return 0;
	}

	
	public boolean hay_arranque(RepositorioTag daoTag, Dispositivo dispo){
		boolean resul;
		Tag tag = daoTag.findBytipoInformacionAndStatRegAndDispositivo("1", "0", dispo);
		if (tag != null){
			resul = true;
		}
			else{
			resul = false;
		
		}
		return resul;
	}
	
	public String motivo_falla(RepositorioDispositivo daoDispositivo, RepositorioTag daoTag, 
			Dispositivo dispo, Server server, Group group) throws JIException, AddFailedException, IllegalArgumentException, UnknownHostException, NotConnectedException, DuplicateGroupException{
		List<Tag> tags = daoTag.findBytipoInformacionAndStatRegAndDispositivoOrderByItemId("3", "0", dispo);
		Iterator<Tag> vectorTag = tags.iterator();
		//final Group group = server.addGroup ( "fallas" );
		String valor;
		int valorInt;
		String error = null;
		Boolean enc = false;
		TipoValor tipoValor;
    	String descTipo;
    	String itemId = null;
	
		
		while (vectorTag.hasNext()){
			
			valor = null;
			Tag tag = vectorTag.next();
	    	tipoValor = tag.getTipoValor();
			descTipo = tipoValor.getDescTipoValor();
			//System.out.println("Tipo Valor :" + descTipo);
			itemId = tag.getItemId();
			
			if (Objects.equals(descTipo,"Binario")){
				itemId = itemId + "/" + tag.getValorBit();
				//System.out.println(itemId);
			}	
			
			final Item item = group.addItem ( itemId );
			
			final ItemState itemState = item.read ( true );
			valor = ":" +itemState.getValue();
			valor = valor.replaceAll("\\[", "");
            valor = valor.replaceAll("\\]", "");
            valor = valor.substring(1, valor.length());
    		//System.out.println("TIPO FALLA");
    		valorInt = Integer.parseInt(valor);		
    		
    		if (valorInt > 0){
    			error = tag.getdescTag();
    			//error = tag.getItemId();
    			enc = true;
    			break;
    		}
			
   		
		}
		
		if (!enc){
			error = "Paro Manual";
		}		
		
    	return error;
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
			
			if ((horaActual.compareTo(horaIni) > 0) && (horaActual.compareTo(horaFin) < 0)) {
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
	
	public Calendar determinarFechaJornada(Calendar fecha, Turno turno) throws ParseException{
		Calendar fechaInicio = null;
		DateFormat dateF = new SimpleDateFormat("HH:mm");
		String horaCorte = "00:00";
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
	
	
	
	public void guardarConectividad(Dispositivo dispo, Tag tag, String arrancado, String parado,
			                        int calidad, Calendar fecha,RepositorioResumenConectividad daoResumenConn){
		ResumenConectividad resumenConn = new ResumenConectividad();
		resumenConn.setDispositivo(dispo);
		resumenConn.setTag(tag);
		resumenConn.setArrancado(arrancado);
		resumenConn.setParado(parado);
		resumenConn.setFecha(fecha);
		resumenConn.setCalidad(calidad);
		daoResumenConn.save(resumenConn);
	}
	
    public void guardarEficiencia(Dispositivo dispo, RepositorioActividadTag daoActividad, RepositorioProducto daoProducto,
                                  RepositorioProductoMaquina daoProductoMaq, RepositorioTurno daoTurno, RepositorioTag daoTag,
                                  RepositorioResumenEficiencia daoEficiencia) throws ParseException{
    	
    	//ControladorTurnos contTur = new ControladorTurnos().turno_actual();
    	//ControladorTurnos cturno = new ControladorTurnos();
    	Turno turno = turno_actual(daoTurno);
    	
    	DateFormat seg = new SimpleDateFormat("ss");
    	DateFormat min = new SimpleDateFormat("mm");
    	Calendar fecha = Calendar.getInstance();
    	fecha.setTime(new Date());
		String horaStr = seg.format(new Date());
		String minStr = min.format(new Date());
		int cantAnterior = 0;
		int cantActual = 0;
		int cantAcum = 0;
        String minActual = "99";
       // Calendar f_Jornada = cturno.determinarFechaJornada(fecha, turno);
        
		if (horaStr.equals("00")){
		
			
			Tag unidades = daoTag.findBytipoInformacionAndStatRegAndDispositivo("4", "0", dispo);
			Tag velocidad = daoTag.findBytipoInformacionAndStatRegAndDispositivo("5", "0", dispo);
			ActividadTag undAct = daoActividad.findTopByTagOrderByFechaDesc(unidades);
			ActividadTag velActual = daoActividad.findTopByTagOrderByFechaDesc(velocidad);
			ProductoMaquina prodMaq = daoProductoMaq.findByMaquinaAndStatProducto(dispo.getMaquina(),"0");
			
			ResumenEficiencia efic = daoEficiencia.findTopByProductoMaquinaOrderByFecRegistroDesc(prodMaq);
			
			if (efic != null){
				cantAnterior = efic.getCantAcum();
			}else{
				cantAnterior = 0;
			}
			
			if (undAct != null){
				cantActual = Integer.parseInt(undAct.getValor());
			}else
			{
				cantActual = 0;
			}
			
			int totund = (cantActual - cantAnterior);
			
			if (cantActual < cantAnterior){
				totund = cantActual;
			}
			
			ResumenEficiencia eficAct = new ResumenEficiencia();
			eficAct.setFecRegistro(fecha);
			eficAct.setCantUnidades(totund);
			eficAct.setCantAcum(cantActual);
			eficAct.setProductoMaquina(prodMaq);
			eficAct.setStatCalculo("0");
			eficAct.setVelocidad(totund);
			
			/*if (velActual == null){
				eficAct.setVelocidad(0);
			}else{
				//eficAct.setVelocidad(Integer.parseInt(velActual.getValor()));
				eficAct.setVelocidad(totund);
			}	*/		
			 
			eficAct.setTurno(turno);
			eficAct.setValorPr(00.0f);
			eficAct.setDispositivo(dispo);
			//eficAct.setFechaJornada(f_Jornada);
			daoEficiencia.save(eficAct);
		
		}
				
		
    }

	@Bean
	public CommandLineRunner demo(RepositorioTag daoTag,RepositorioDispositivo daoDispositivo, 
			RepositorioDispositivo repositorioDispositivo,RepositorioActividadTag daoActividadTag,
			RepositorioDetalleTag daoDetalleTag, RepositorioProducto daoProducto,RepositorioProductoMaquina daoProductoMaq, 
			RepositorioTurno daoTurno, RepositorioResumenEficiencia daoEficiencia, RepositorioResumenConectividad daoResumenConn){
		return (args) -> {
			
			List<Dispositivo> listaDispo = daoDispositivo.findByStatReg("0");
			Iterator<Dispositivo> vectorDis = listaDispo.iterator();
			accesarDispositivo acceso = accesarDispositivo.getInstancia();
			

			Server server = acceso.conectar();
			final Group group = server.addGroup ( "fallas" );
			
			
			//acceso.cargarPlcs();
			//acceso.cargarTags("Todos");
			
			
			//accesarDispositivo acceso = new accesarDispositivo();
			
		/*	acceso.cargarPlcs();
			List<Dispositivo> dis = acceso.mostrarTodos();			
			
			if (dis != null){
			System.out.println("SI HAY CONEXION");
			acceso.desconectar();	*/
			while (vectorDis.hasNext()){
				
				//Server server = acceso.conectar();
			
				Dispositivo dispo = vectorDis.next();
						
				
				List<Tag> listaTag = daoTag.findByDispositivoAndStatRegAndStatusTag(dispo,"0","0");
				Iterator<Tag> vectorTag = listaTag.iterator();
				//System.out.println(dispo.getDescripcion());
				//------------ Se empieza a Recorrer las Tags por Dispositivo ------------------------
				
				while (vectorTag.hasNext()){
							
					//String itemId = "[Antares]N7:30";
			        // create a new server
			        //final Server server= new Server(ci, Executors.newSingleThreadScheduledExecutor());
         
			        try {

						final Tag tag = vectorTag.next();
						//System.out.println(tag.getdescTag());
	                	TipoValor tipoValor;
	                	String descTipo;
	                	tipoValor = tag.getTipoValor();
						descTipo = tipoValor.getDescTipoValor();

						String itemId = tag.getItemId();
						//System.out.println("Tipo Valor :" + descTipo);
						
                		if (Objects.equals(descTipo,"Binario")){
                			itemId = itemId + "/" + tag.getValorBit();
                			//System.out.println(itemId);
                		}
                		
						int intervalo = tag.getIntervalo();
						final AccessBase access = new SyncAccess(server, intervalo);

			              			            
			            access.addItem(itemId, new DataCallback() {
				        	int valorInt;
				        	int valorAnt = -1;
				        	boolean guardar = true;
				        	long tiempoFunc;
				        	boolean conexion = true;
				        	boolean arranco = false;
				        	String minAnt = "99";
				        	
				        	
				        	
				        	
				        	
			                public void changed(Item item, ItemState state) {
			                	String valor;
			                	String tipoInfo;
			                	String falla = null;
			                	valor = ":"+state.getValue();
			                	
			                			                	
			                	
			                	//System.out.println(state);
			                	
			                	if (state.getQuality() == 0){ //Si es Cero No hay acceso al PLC
			                		//access.unbind();
									System.out.println("Problemas al Conectar a : " + dispo.getDescripcion());
									if (guardar){
										guardarConectividad(dispo,tag,null,null,state.getQuality(),state.getTimestamp(),daoResumenConn);
									}
									
									guardar = false;
									conexion = false;
									
									
			                	}else{
			                		conexion = true;
			                	}
			                	
			                	if (conexion){
			                		
			                		
				                	if (state.getValue() != null){
					                	
					                	tipoInfo = tag.getTipoInformacion();
					                  	valor = valor.replaceAll("\\[", "");
						                valor = valor.replaceAll("\\]", "");
						                valor = valor.substring(1, valor.length());
				                		//System.out.println("TIPO FALLA");
				                		valorInt = Integer.parseInt(valor);
						                
						                if (Objects.equals(tag.getTipoInformacion(),"1")){
					                		
						                	if (valorInt > 0){
					                			//parado = false;
					                			arrancado = true;
					                			manual = false;
					                			if (valorInt != valorAnt){
					                				long m = duracion(state.getTimestamp(),dispo,daoTag,daoActividadTag);
					                			}
					                		}else{
					                			//parado = true;
					                			arrancado = false;				                			
					                		}			
						                	
						                	
					                		if (valorInt != valorAnt){
					                				guardar = true;
					                				valorAnt = valorInt;
					                				guardarConectividad(dispo,tag,valor,null,state.getQuality(),state.getTimestamp(),daoResumenConn);
					                		}else{
					                			guardar = false;
					                		}
						                	
						                }

					                	//System.out.println("Valor Ant : " + valorAnt + " Item : " + tag.getItemId());
					                	//System.out.println("Valor Int : " + valorInt);
					                	//System.out.println("Arrancado :" + arrancado);
					                	//System.out.println("Parado :" + parado);
						                
					                	if (Objects.equals(tag.getTipoInformacion(),"2")){
					                		

					                		if (valorInt >= 0){
					                			parado = true;
					                			try {
					                				tiempoFunc = tiempoFucionamiento(state.getTimestamp(),dispo,daoTag,daoActividadTag);
													//System.out.println("Funcionamiento : " + tiempoFunc);
												} catch (ParseException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
					                			//arrancado = false;
					                		}else{
					                			parado = false;
					                			//arrancado = true;				                			
					                		}  
					                		
					                		if (valorInt != valorAnt){
					                			guardarConectividad(dispo,tag,null,valor,state.getQuality(),state.getTimestamp(),daoResumenConn);
					                			if (valorInt == 0) {
					                				if  (arrancado){
					                					guardar = false; 
					                				}else
					                				{
					                					guardar = false; //Paro Manual por el Operador
					                					
					                					if ((!manual) || (valorAnt ==0)){ //Aqui hay un Paro Manual
					                						guardar = true;
					                						manual = true;
					                					}
					                					
					                					if (valorAnt > 0){  //Aqui hay un RESET
					                						guardar = false;
					                					}
					                					valorAnt = valorInt;
					                				}
					                			}else{
						                				guardar = true;  //Paro por Falla
						                				valorAnt = valorInt;					                					
                                         			}
					                		}else{
					                				guardar = false;
					                		}
					                		
					                		if ((valorInt == 0) && (!arrancado) && (!manual)){
					                			guardar = true;
					                			manual = true;
					                		}
					                		
						              
					               /* 		if ((valorInt > 0) && (guardar)){
							                	
			                				    try {
													falla = motivo_falla(daoDispositivo, daoTag, dispo, server,group);
												} catch (IllegalArgumentException | UnknownHostException
														| JIException | AddFailedException
														| NotConnectedException | DuplicateGroupException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}	
		                				    
						                	} */
						                						                		
				                		

					                	} 
					                	
					                	if (Objects.equals(tag.getTipoInformacion(),"4")){
					                	
						                	try {
						                		
						                		DateFormat mm = new SimpleDateFormat("mm");
						                		String minStr = mm.format(new Date());
						                		if (!minStr.equals(minAnt)){
						                			guardarEficiencia(dispo,daoActividadTag,daoProducto,daoProductoMaq,daoTurno,daoTag,daoEficiencia);
						                		    minAnt = minStr;
						                		}
						                	} catch (ParseException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
					                	}
					                	
					                	
					                	
					                	if ((!Objects.equals(tag.getTipoInformacion(),"1")) && (!Objects.equals(tag.getTipoInformacion(),"2"))){
					                		if (valorInt != valorAnt){
					                			guardar = true;
					                			valorAnt = valorInt;
					                		}
					                			else{
					                				guardar = false;
					                			}
					                	}
					                	


					                	
					                    
					                	
					                					                	
					                	if (guardar){

					                		if (tag.getTipoInformacion().equals("2")){
					                			
						                		
								                	
				                				    try {
														falla = motivo_falla(daoDispositivo, daoTag, dispo, server,group);
													} catch (IllegalArgumentException | UnknownHostException
															| JIException | AddFailedException
															| NotConnectedException | DuplicateGroupException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}	
			                				    
							                		
						                		
					                		}
				                		    

											
											
					                		DetalleTag detalle = daoDetalleTag.findByTagAndValorDetTag(tag, valor);
						                    ActividadTag evento = new ActividadTag();
						                    evento.setFecha(state.getTimestamp());
						                    evento.setCalidad(state.getQuality());
						                    evento.setValor(valor);
						                    //System.out.println(detalle);
						                    //evento.setValor(valor.substring(1, valor.length()));
						                    evento.setTag(tag);
						                    evento.setDetalleTag(detalle);
						                    //evento.setTiempoFunc(tiempoFunc);
						                    evento.setStatEditado("0");
						                    evento.setTiempoFuncMs(tiempoFunc);
						                    evento.setDescFalla(falla);
						                    
						                   // ControladorTurnos cTurnos = new ControladorTurnos();
					                		Turno tJornada = null;
					                		Calendar fJornada = null;
					                		
											try {
												tJornada = turno_actual(daoTurno);
											} catch (ParseException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
					                		try {
					                			Calendar fecReg = state.getTimestamp();
												fJornada = determinarFechaJornada(fecReg, tJornada);
											} catch (ParseException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} 
						                    
						                    evento.setFechaJornada(fJornada);
						                    
						                    daoActividadTag.save(evento);
					                	}
				                	}			                		
			                	}
			                	

			                }
			            });
			            // start reading
			            access.bind();
			            // wait a little bit
			            //Thread.sleep(10 * 1000);
			            
			           
			            // stop reading
			            //access.unbind();
			        } catch (final JIException e) {
			            System.out.println("LOG ERROR : " + server.getErrorMessage(e.getErrorCode()));
			        }					
				}
				
			}  //-------Fin Recorrer Dispositivoo ------------------------------------
			
			//}	
	
		
		};
	} 
	
	
} 
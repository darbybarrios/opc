package com.opc.controlador;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.AccessBase;
import org.openscada.opc.lib.da.AddFailedException;
import org.openscada.opc.lib.da.DuplicateGroupException;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.SyncAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.modelo.Dispositivo;
import com.opc.modelo.Maquina;
import com.opc.modelo.Marca;
import com.opc.modelo.Sucursal;
import com.opc.repositorio.RepositorioDispositivo;
import com.opc.repositorio.RepositorioMaquina;
import com.opc.repositorio.RepositorioMarca;
import com.opc.repositorio.RepositorioSucursal;

import com.opc.modelo.Tag;
import com.opc.modelo.accesarDispositivo;
import com.opc.repositorio.RepositorioTag;

@Controller
public class ControladorDispositivo {
	
	
	@Autowired
	private RepositorioDispositivo daoDispositivo;
	
	@Autowired
	private RepositorioMarca daoMarca;
	
	@Autowired 
	private RepositorioMaquina daoMaquina;
	
	@Autowired
	private RepositorioSucursal daoSucursal;

	@Autowired
	private RepositorioTag daoTag;	
	
	@RequestMapping("verificar-conexion")
	@ResponseBody	
	public String verificar_conexion(int id){
		return "1";
	} 


/*	
	@RequestMapping("verificar-conexion")
	@ResponseBody	
	public String verificar_conexion(int id) throws IllegalArgumentException, JIException, AlreadyConnectedException, IOException, NotConnectedException, DuplicateGroupException, AddFailedException{
		String status = null;
		try{
			
			Dispositivo dispo =  daoDispositivo.findOne(id);
			//accesarDispositivo acceso = new accesarDispositivo();
			accesarDispositivo acceso = accesarDispositivo.getInstancia();
			Tag tag = daoTag.findBytipoInformacionAndStatRegAndDispositivo("1", "0", dispo);
			//Server server = acceso.conectar();
						
			Server server = acceso.sesion();
					
			final Group group = server.addGroup ( "test" );
			final Item item = group.addItem ( tag.getItemId() );
			final ItemState itemState = item.read ( true );
			
			server.removeGroup(group, true);
			//final AccessBase access = new SyncAccess(server, 500);
			
			if (itemState.getQuality() == 0){
				status = "0";
			}
			else
			{
				status = "1";
				
			}
			
			//server.disconnect();
			
		}
		catch(NotConnectedException excepcion)
		{
			status = "2"; //No hay Conexion al Servidor
			System.out.println("Ya esta Conectado");
		}
		catch(AlreadyConnectedException excepcion)
		{
			status = "2"; //No hay Conexion al Servidor
			System.out.println("Already!!..Ya esta Conectado");
		}		
	
		
		return status;
	}	
	
	*/
	
	@RequestMapping(value = "listar-dispositivos")
	@ResponseBody
	public List<Dispositivo> mostrarTodas(){
		List<Dispositivo> listadispo = daoDispositivo.findByStatReg("0");
	    Iterator<Dispositivo> vector = listadispo.iterator();
	    //System.out.println("Lista " + listadispo);
	    
	    
		while (vector.hasNext()){
			Dispositivo d = (Dispositivo) vector.next();
			//Marca marca = daoMarca.findByIdMarca(d.get)
			//System.out.println("Nombre : " + d.getMarca().getDescripcion());
		}	
		return listadispo;
		
	}
	
	@RequestMapping(value = "crear-dispositivo")
	@ResponseBody
	public String crear(String descripcion,int idMarca, int idMaquina, int idSucursal){
		String nombreDispositivo;
		try{
			Marca marca = daoMarca.findByIdMarca(idMarca);
			Maquina maquina = daoMaquina.findByIdMaquina(idMaquina);
			Sucursal sucursal = daoSucursal.findOne(idSucursal);
			Dispositivo dispositivo = new Dispositivo(descripcion,new Date(),null,"0",marca,maquina,"0",sucursal);
			daoDispositivo.save(dispositivo);
			nombreDispositivo = dispositivo.getDescripcion();
		}
		catch (Exception ex){
			return "Error al Crear el Dispositivo : " + ex.toString();
		}
		return "Dispositivo Creado Exitosamente : " + nombreDispositivo;
	}
	
	@RequestMapping(value = "actualizar-dispositivo")
	@ResponseBody	
	public String actualizar(int id,int idMarca, int idMaquina,String statDis, String statReg){
		try{
			Dispositivo dispositivo =  daoDispositivo.findOne(id);
			Marca marca = daoMarca.findByIdMarca(idMarca);
			Maquina maquina = daoMaquina.findByIdMaquina(idMaquina);
			dispositivo.setMarca(marca);
			dispositivo.setMaquina(maquina);
			dispositivo.setStatDispositivo(statDis);
			dispositivo.setStatReg(statReg);
			daoDispositivo.save(dispositivo);
						
		}
		catch (Exception ex){
			return "Error al Actualizar Dispositivo : " + ex.toString();
		}
		return "Dispositivo Actualizado Correctamente";
	}
	
	@RequestMapping(value = "eliminar-dispositivo")
	@ResponseBody	
	public String eliminar(int id, String statReg){
		try{
			Dispositivo dispositivo =  daoDispositivo.findOne(id);
			dispositivo.setStatReg(statReg);
			daoDispositivo.save(dispositivo);
						
		}
		catch (Exception ex){
			return "Error al Eliminar Dispositivo : " + ex.toString();
		}
		return "Dispositivo Eliminado Correctamente";
	}	

	@RequestMapping(value = "buscarPorMaquina")
	@ResponseBody
	public Dispositivo buscarPorMaquina(int idMaquina){
		Maquina maq = daoMaquina.findOne(idMaquina);
		Dispositivo dispo = daoDispositivo.findByMaquina(maq);
		return dispo;
	}
}

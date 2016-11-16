package com.opc.controlador;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.modelo.Dispositivo;
import com.opc.modelo.accesarDispositivo;

@Controller
public class ControladorAccesoDispositivos {

	@RequestMapping("listar-dispositivos-server")
	@ResponseBody
	public List<Dispositivo> listar() throws IllegalArgumentException, JIException, AlreadyConnectedException, IOException{
		List<Dispositivo> dis = null;
		try{
		accesarDispositivo acceso = accesarDispositivo.getInstancia();
		//acceso.cargarPlcs();
		List<Dispositivo> dispo = acceso.mostrarTodos();
		dis = dispo;
		
		}
		catch(AlreadyConnectedException error){
			System.out.println("ErrorDispo" + error.getMessage());
		}
		
		return dis;
	}

}

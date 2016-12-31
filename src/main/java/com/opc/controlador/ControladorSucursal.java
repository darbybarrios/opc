package com.opc.controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opc.modelo.Sucursal;
import com.opc.repositorio.RepositorioSucursal;

@Controller
public class ControladorSucursal {
	
	@Autowired
	private RepositorioSucursal repositorioSucursal;
	
	
	@RequestMapping("listar-sucursales")
	@ResponseBody
	public List<Sucursal> listarTodas(){
		List<Sucursal> suc = repositorioSucursal.findAll();
		return suc;
	}

	@RequestMapping("buscar-sucursal")
	@ResponseBody	
	public Sucursal bucarUna(int idSuc){
			Sucursal suc = repositorioSucursal.findOne(idSuc);
			return suc;
	}
	
	@RequestMapping("nueva-sucursal")
	@ResponseBody	
	public void nuevo (String estado, String direccion, String nombre, String telefono, int turnos,String config) {
		Sucursal sucursal = new Sucursal();
		sucursal.setEstado(estado);
		sucursal.setDireccion(direccion);
		sucursal.setNombre(nombre);
		sucursal.setTelefono(telefono);
		sucursal.setTurnos(turnos);
		sucursal.setConfigurado(config);
		repositorioSucursal.save(sucursal);
			
	}
	
	@RequestMapping("editar-sucursal")
	@ResponseBody	
	public void editar (int idSuc, String estado, String direccion, String nombre, String telefono, int turnos,String config) {
		Sucursal sucursal = new Sucursal();
		sucursal.setIdSucursal(idSuc);
		sucursal.setEstado(estado);
		sucursal.setDireccion(direccion);
		sucursal.setNombre(nombre);
		sucursal.setTelefono(telefono);
		sucursal.setTurnos(turnos);
		sucursal.setConfigurado(config);
		repositorioSucursal.save(sucursal);
			
	}

	
	

}

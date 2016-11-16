package com.opc.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.modelo.Sucursal;
import com.opc.repositorio.RepositorioSucursal;

@Controller
public class ControladorSucursal {
	
	@Autowired
	private RepositorioSucursal repositorioSucursal;
	
	
	@RequestMapping(value = "listar-sucursales", method = RequestMethod.GET)
	@ResponseBody
	public List<Sucursal> listarTodas(){
		List<Sucursal> suc = repositorioSucursal.findAll();
		return suc;
	}

	@RequestMapping(value = "buscar-sucursal", method = RequestMethod.GET)
	@ResponseBody	
	public Sucursal bucarUna(int idSuc){
			Sucursal suc = repositorioSucursal.findOne(idSuc);
			return suc;
	}

}

package com.opc.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.modelo.Maquina;
import com.opc.repositorio.RepositorioMaquina;

@Controller
public class ControladorMaquina {
	@Autowired
	private RepositorioMaquina repositorioMaquina;
	
	@RequestMapping(value = "listar-maquinas")
	@ResponseBody
	public List<Maquina> listar(){
		
		List<Maquina> lista = (List<Maquina>) repositorioMaquina.findAll();
		return lista;
	}

}

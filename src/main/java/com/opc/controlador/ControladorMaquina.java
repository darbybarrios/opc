package com.opc.controlador;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.modelo.Maquina;
import com.opc.modelo.Marca;
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
	
	@RequestMapping("nueva-maquina")
	@ResponseBody	
	public String nuevo_maquina(String nombre) throws ParseException{		
		Maquina maquina = new Maquina();
		maquina.setNombre(nombre);
		maquina.setStatus("0");
		repositorioMaquina.save(maquina);
		return "Maquina";
	}

}

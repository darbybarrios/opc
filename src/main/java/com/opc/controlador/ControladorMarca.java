package com.opc.controlador;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.modelo.Marca;
import com.opc.modelo.Producto;
import com.opc.repositorio.RepositorioMarca;


@Controller
public class ControladorMarca {
	
	@Autowired
	private RepositorioMarca repositorioMarca;
	
	@RequestMapping(value = "listar-marcas")
	@ResponseBody
	public List<Marca> mostrar_todas(){
		List<Marca> lista = (List<Marca>) repositorioMarca.findAll();
		return lista;
	}
	
	@RequestMapping("nueva-marca")
	@ResponseBody	
	public void nueva_marca(String desc) throws ParseException{		
		Marca marcas = new Marca();
		marcas.setDescripcion(desc);
		repositorioMarca.save(marcas);
	}
	
	@RequestMapping("consultar-marca")
	@ResponseBody	
	public Marca consultar_marca(int idMarca) throws ParseException{		
		Marca marca = repositorioMarca.findOne(idMarca);
		return marca;
		
	}
	
	@RequestMapping("editar-marca")
	@ResponseBody	
	public void editar_marca(int idMarca, String desc) throws ParseException{		
		Marca marca = new Marca();
		marca.setIdMarca(idMarca);
		marca.setDescripcion(desc);
		repositorioMarca.save(marca);
		
	}
	
	

}

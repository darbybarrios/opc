package com.opc.controlador;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.modelo.Maquina;
import com.opc.modelo.Marca;
import com.opc.modelo.Producto;
import com.opc.modelo.ProductoMaquina;
import com.opc.repositorio.RepositorioMaquina;
import com.opc.repositorio.RepositorioProducto;
import com.opc.repositorio.RepositorioProductoMaquina;

@Controller
public class ControladorMaquina {
	@Autowired
	private RepositorioMaquina repositorioMaquina;
	@Autowired
	private RepositorioProducto repositorioProducto;
	@Autowired
	private RepositorioProductoMaquina repositorioProductoMaq;	
	
	
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
	
	@RequestMapping("consultar-maquina")
	@ResponseBody	
	public Maquina consultar_maquina(int idMaquina) throws ParseException{		
		Maquina maquina = repositorioMaquina.findOne(idMaquina);
		return maquina;
			
	}	
	
	@RequestMapping("actualizar-ProductoMaquina")
	@ResponseBody	
	public int actualizarProductoMaquina(String nombre, int idMaquina, int idProducto) throws ParseException{		
		Maquina maquina = repositorioMaquina.findOne(idMaquina);
		Producto producto = repositorioProducto.findOne(idProducto);
		ProductoMaquina prodMaq = new ProductoMaquina();
		
		maquina.setNombre(nombre);
		repositorioMaquina.save(maquina);
				
		prodMaq.setMaquina(maquina);
		prodMaq.setProducto(producto);
		prodMaq.setStatProducto("0");
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(new Date());
		prodMaq.setFecCambio(fecha);
		repositorioProductoMaq.save(prodMaq);
		
		return 1;
			
	}	

}

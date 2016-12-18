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
import com.opc.repositorio.RepositorioProductos;

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
	public int nuevo_maquina(String nombre, int idProducto, int velocidad, int tiempoHambre) throws ParseException{	
		Producto producto = repositorioProducto.findOne(idProducto);
		Maquina maquina = new Maquina();
		maquina.setNombre(nombre);
		maquina.setStatus("0");
		maquina.setProducto(producto);
		maquina.setVelocidad(velocidad);
		maquina.setTiempoHambre(tiempoHambre);
	
		repositorioMaquina.save(maquina);
		
		ProductoMaquina prodMaq = new ProductoMaquina();
		prodMaq.setMaquina(maquina);
		prodMaq.setProducto(producto);
		prodMaq.setStatProducto("0");
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(new Date());
		prodMaq.setFecCambio(fecha);
		repositorioProductoMaq.save(prodMaq);
		
		
		return 1;
	}
	
	@RequestMapping("consultar-maquina")
	@ResponseBody	
	public Maquina consultar_maquina(int idMaquina) throws ParseException{		
		Maquina maquina = repositorioMaquina.findOne(idMaquina);
		return maquina;
			
	}	
	
	@RequestMapping("actualizar-ProductoMaquina")
	@ResponseBody			
	public int actualizarProductoMaquina(String nombre, int idMaquina, int idProducto, int velocidad, int tiempoHambre) throws ParseException{		
		Maquina maquina = repositorioMaquina.findOne(idMaquina);
		Producto producto = repositorioProducto.findOne(idProducto);
		boolean enc = false;
		
		ProductoMaquina prodMaq1 = repositorioProductoMaq.findByMaquinaAndStatProducto(maquina, "0");
		if (prodMaq1 != null){
			
			if (!prodMaq1.getProducto().equals(producto)){
				prodMaq1.setStatProducto("1");
				repositorioProductoMaq.save(prodMaq1);
				enc = true;
			}

		}
		
				
		maquina.setNombre(nombre);
		maquina.setProducto(producto);
		maquina.setVelocidad(velocidad);
		maquina.setTiempoHambre(tiempoHambre);
		repositorioMaquina.save(maquina);
		
		if (enc) {
		
			ProductoMaquina prodMaq = new ProductoMaquina();				
			prodMaq.setMaquina(maquina);
			prodMaq.setProducto(producto);
			prodMaq.setStatProducto("0");
			Calendar fecha = Calendar.getInstance();
			fecha.setTime(new Date());
			prodMaq.setFecCambio(fecha);
			repositorioProductoMaq.save(prodMaq);
		
		}
		return 1;
			
	}
	
	@RequestMapping("eliminar-ProductoMaquina")
	@ResponseBody	
	public void eliminar(int idMaquina) throws ParseException{		
		Maquina maquina = repositorioMaquina.findOne(idMaquina);
		maquina.setStatus("1");
		repositorioMaquina.save(maquina);
		
		ProductoMaquina prodMaq1 = repositorioProductoMaq.findByMaquina(maquina);
		
		if (prodMaq1 != null){
			prodMaq1.setStatProducto("1");
			repositorioProductoMaq.save(prodMaq1);
		}
		
			
	}	

}

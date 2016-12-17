package com.opc.controlador;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.modelo.Dispositivo;
import com.opc.modelo.Maquina;
import com.opc.modelo.Producto;
import com.opc.modelo.Sucursal;
import com.opc.modelo.Turno;
import com.opc.repositorio.RepositorioProductos;
import com.opc.repositorio.RepositorioSucursal;
import com.opc.repositorio.RepositorioTurno;

@Controller
public class ControladorProductos {
	
	
	
	@Autowired
	private RepositorioProductos daoProductos;

	
	
	
	
	@RequestMapping("listar-productos")
	@ResponseBody
	public List<Producto> listar_productos(){
		List<Object[]> lista;
		List<Producto> productos = (List<Producto>) daoProductos.findAll();
		
		return productos;
	}

	@RequestMapping("nuevo-producto")
	@ResponseBody	
	public void nuevo_producto(String desc) throws ParseException{		
		Producto producto = new Producto();
		producto.setDescProducto(desc);
		producto.setStatProducto("0");
		daoProductos.save(producto);
		
	}
	
	@RequestMapping("eliminar-producto")
	@ResponseBody	
	public void eliminar(int idProducto, String statProducto){
		try{
			
			Producto producto =  daoProductos.findOne(idProducto);
			producto.setStatProducto(statProducto);
			daoProductos.save(producto);
						
		}
		catch (Exception ex){
			 ex.toString();
		}
		
	}
	
	@RequestMapping("editar-producto")
	@ResponseBody	
	public void editar(int idProducto, String descProducto, String statProducto){
		try{
			
			Producto producto =  new Producto();
			producto.setIdProducto(idProducto);
			producto.setDescProducto(descProducto);
			producto.setStatProducto(statProducto);
			daoProductos.save(producto);
						
		}
		catch (Exception ex){
			 ex.toString();
		}
		
	}
	
	@RequestMapping("consultar-producto")
	@ResponseBody	
	public Producto consultar_maquina(int idProducto) throws ParseException{		
		Producto producto = daoProductos.findOne(idProducto);
		return producto;
			
	}

	
	
	
	
	
	
	
	

}

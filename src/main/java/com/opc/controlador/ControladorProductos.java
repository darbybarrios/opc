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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.modelo.Dispositivo;
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
	public String nuevo_producto(String desc) throws ParseException{		
		Producto producto = new Producto();
		producto.setDescProducto(desc);
		producto.setStatProducto("0");
		daoProductos.save(producto);
		return "Producto";
	}
	
	@RequestMapping("eliminar-producto")
	@ResponseBody	
	public String eliminar(int id, String statProducto){
		try{
			Producto producto =  daoProductos.findOne(id);
			producto.setStatProducto(statProducto);
			daoProductos.save(producto);
						
		}
		catch (Exception ex){
			return "Error al Eliminar Producto : " + ex.toString();
		}
		return "Producto Eliminado Correctamente";
	}

	
	
	
	
	
	
	
	

}

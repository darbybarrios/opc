package com.opc.repositorio;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import com.opc.modelo.Producto;

@Transactional
public interface RepositorioProductos extends CrudRepository<Producto, Integer> {
	
	List<Producto> findAll();

}


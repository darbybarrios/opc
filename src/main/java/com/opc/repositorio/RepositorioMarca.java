package com.opc.repositorio;

import com.opc.modelo.Marca;
import java.util.*;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface RepositorioMarca extends CrudRepository<Marca, Integer> {
	
	List<Marca> findByDescripcion(String descripcion);
	Marca findByIdMarca(int IdMarca);

}
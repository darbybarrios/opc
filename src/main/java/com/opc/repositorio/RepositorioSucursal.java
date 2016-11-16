package com.opc.repositorio;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import com.opc.modelo.Sucursal;

@Transactional
public interface RepositorioSucursal extends CrudRepository<Sucursal, Integer> {
	
	List<Sucursal> findAll();

}

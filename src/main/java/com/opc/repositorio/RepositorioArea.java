package com.opc.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.opc.modelo.Area;
import com.opc.modelo.Maquina;

@Transactional
public interface RepositorioArea extends CrudRepository<Area, Integer> {
	
	List<Area> findByStatAreaAndMaquina(String statArea, Maquina maquina);

}

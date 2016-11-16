package com.opc.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.opc.modelo.Area;
import com.opc.modelo.Maquina;
import com.opc.modelo.Sistema;

@Transactional
public interface RepositorioSistema extends CrudRepository<Sistema, Integer> {
	
	List<Sistema> findByStatSistemaAndAreaAndMaquina(String statReg, Area area, Maquina maquina);

}

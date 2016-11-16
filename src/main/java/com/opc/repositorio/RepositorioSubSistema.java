package com.opc.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.opc.modelo.Area;
import com.opc.modelo.Maquina;
import com.opc.modelo.Sistema;
import com.opc.modelo.SubSistema;

public interface RepositorioSubSistema extends CrudRepository<SubSistema, Integer> {
	
	List<SubSistema> findByStatSubSistemaAndAreaAndSistemaAndMaquina(String statReg, Area area, Sistema sistema, Maquina maq);
}

package com.opc.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.opc.modelo.Area;
import com.opc.modelo.CausaFalla;
import com.opc.modelo.Maquina;
import com.opc.modelo.Sistema;
import com.opc.modelo.SubSistema;

public interface RepositorioCausaFalla extends CrudRepository<CausaFalla, Integer> {
	List<CausaFalla> findByStatCausaFallaAndAreaAndSistemaAndSubSistemaAndMaquina(String statReg, Area area, Sistema sistema,SubSistema subSistema, Maquina maq);
}


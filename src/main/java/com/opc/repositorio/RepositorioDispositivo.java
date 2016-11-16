package com.opc.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.opc.modelo.Dispositivo;
import com.opc.modelo.Maquina;

@Transactional
//@RepositoryRestResource
public interface RepositorioDispositivo extends CrudRepository<Dispositivo, Integer> {
	
	List<Dispositivo> findAll();
	List<Dispositivo> findByStatReg(String sta);
	Dispositivo findByMaquina(Maquina maquina);

}

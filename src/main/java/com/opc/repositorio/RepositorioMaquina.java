package com.opc.repositorio;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;


import com.opc.modelo.Maquina;


@Transactional
public interface RepositorioMaquina extends CrudRepository<Maquina, Integer> {
	Maquina findByIdMaquina(int IdMaquina);
	
}

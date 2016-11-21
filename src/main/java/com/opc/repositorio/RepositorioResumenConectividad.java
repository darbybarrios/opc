package com.opc.repositorio;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;


import com.opc.modelo.Dispositivo;
import com.opc.modelo.ResumenConectividad;
import com.opc.modelo.Tag;

@Transactional
public interface RepositorioResumenConectividad extends CrudRepository<ResumenConectividad, Integer> {
	
	ResumenConectividad findTopByDispositivoOrderByFechaDesc(Dispositivo dispo);
	ResumenConectividad findTopByTagOrderByFechaDesc(Tag tag);

}

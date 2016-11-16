package com.opc.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.opc.modelo.Dispositivo;
import com.opc.modelo.Tag;

@Transactional
public interface RepositorioTag extends CrudRepository<Tag, Integer> {

	List<Tag> findByDispositivo(Dispositivo dispositivo);
	List<Tag> findByDispositivoAndStatReg(Dispositivo dispositivo, String statReg);
	Tag findByDispositivoAndStatWeb(Dispositivo dispositivo, String statWeb);
	Tag findBytipoInformacionAndStatRegAndDispositivo(String tipoInf, String statReg, Dispositivo dispositivo);
	List<Tag> findBytipoInformacionAndStatRegAndDispositivoOrderByItemId(String tipoInf, String statReg, Dispositivo dispositivo);
	List<Tag> findByDispositivoAndStatRegAndStatusTag(Dispositivo dispositivo, String statReg, String statTag);
	
}

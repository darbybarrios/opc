package com.opc.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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
	
	@Query(value="Select tag.id_tag, tag.desc_tag " +
				"From " +
				"tag Inner Join " +
				"dispositivo " +
				"On tag.id_dispositivo = dispositivo.id_dispositivo Inner Join " +
				"maquina " +
				"On dispositivo.id_maquina = maquina.id_maquina " +
				"Where " +
				"maquina.id_maquina = :id_maquina " +
				"Order By " +
				"tag.id_tag", nativeQuery=true)
	List<Object[]> BuscarTagxMaquina(@Param("id_maquina") int idMaquina);
	
}

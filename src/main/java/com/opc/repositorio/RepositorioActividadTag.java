package com.opc.repositorio;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.opc.modelo.ActividadTag;
import com.opc.modelo.Tag;

public interface RepositorioActividadTag extends CrudRepository<ActividadTag, Integer> {
	
	ActividadTag findTopByTagOrderByFechaDesc(Tag tag);	
	List<ActividadTag> findByTagOrderByFechaDesc(Tag tag);
	ActividadTag findTopByTagAndValorOrderByFechaDesc(Tag tag,String valor);
	ActividadTag findByIdActividadTag(ActividadTag actividadTag);
	
	List<ActividadTag> findByTagAndFechaLessThanEqualAndFechaGreaterThanEqualOrderByFechaDesc(Tag tag,Calendar fin, Calendar inicio);
	
	List<ActividadTag> findByTagAndFechaBetweenOrderByFechaDesc(Tag tag,Calendar fecIni, Calendar fecfin);
	
	List<ActividadTag> findByTagAndFechaLessThanOrderByFechaDesc(Tag tag,Calendar fecfin);
	
	
	
}

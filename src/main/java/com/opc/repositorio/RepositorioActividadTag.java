package com.opc.repositorio;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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
	

 	
	@Query(value="Select count(*) As total, causa_falla.tipo_parada, date(fecha) From " +
				 "actividad_tag Inner Join causa_falla On actividad_tag.id_causa_falla = causa_falla.id_causa_falla " +
				 "Inner Join tag On actividad_tag.id_tag = tag.id_tag Where " +
				 "tag.tipo_informacion = '2'	And tag.id_dispositivo = :idDispo " +   
				 "Group By causa_falla.tipo_parada, date(fecha) " +
				 "Order By date(fecha) desc  ",nativeQuery=true)
	List<Object[]> findParadasByDispositivoGroupTipoAndFecha(@Param("idDispo") int idDispo);
	
	
	
}

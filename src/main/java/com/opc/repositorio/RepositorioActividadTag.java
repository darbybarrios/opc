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
	

 	//Paradas_ByDispositivoAndTruno   -- FlySpeed --
	@Query(value="Select ((Sum(actividad_tag.duracion_ms)*100)/(Select Sum(a.duracion_ms) " +
				 "From  actividad_tag a Inner Join tag t  On a.id_tag = t.id_tag " +
				 "Where a.id_turno = actividad_tag.id_turno And t.id_dispositivo =  tag.id_dispositivo And a.id_causa_falla is not null " +
				 "Group By a.id_turno, t.id_dispositivo )) as Porc, causa_falla.tipo_parada, " +
				 "actividad_tag.id_turno,  tag.id_dispositivo " +
                 "From actividad_tag Inner Join causa_falla On actividad_tag.id_causa_falla = causa_falla.id_causa_falla Inner Join " +
                 "tag On actividad_tag.id_tag = tag.id_tag Where actividad_tag.id_turno = :idTurno " +
                 " And tag.id_dispositivo = :idDispo Group By  " +
                 "causa_falla.tipo_parada, actividad_tag.id_turno, tag.id_dispositivo",nativeQuery=true)
	List<Object[]> findParadasByDispositivoAndTurno(@Param("idDispo") int idDispo, @Param("idTurno") int idTurno);
	
	
	
}

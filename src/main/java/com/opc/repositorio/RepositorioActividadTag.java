package com.opc.repositorio;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.opc.modelo.ActividadTag;
import com.opc.modelo.Tag;
import com.opc.modelo.Turno;

public interface RepositorioActividadTag extends CrudRepository<ActividadTag, Integer> {
	
	ActividadTag findTopByTagOrderByFechaDesc(Tag tag);	
	List<ActividadTag> findByTagOrderByFechaDesc(Tag tag);
	ActividadTag findTopByTagAndValorOrderByFechaDesc(Tag tag,String valor);
	ActividadTag findByIdActividadTag(ActividadTag actividadTag);
	
	List<ActividadTag> findByTagAndFechaLessThanEqualAndFechaGreaterThanEqualOrderByFechaDesc(Tag tag,Calendar fin, Calendar inicio);
	
	List<ActividadTag> findByTagAndFechaBetweenOrderByFechaDesc(Tag tag,Calendar fecIni, Calendar fecfin);
	
	List<ActividadTag> findByTagAndFechaLessThanOrderByFechaDesc(Tag tag,Calendar fecfin);
	
	
	

 	//Paradas_ByDispositivoAndTruno   -- FlySpeed --
	@Query(value="Select round(((Sum(actividad_tag.duracion_ms)*100)/(Select Sum(a.duracion_ms) " +
				 "From  actividad_tag a Inner Join tag t  On a.id_tag = t.id_tag " +
				 "Where a.id_turno = actividad_tag.id_turno And t.id_dispositivo =  tag.id_dispositivo And a.id_causa_falla is not null " +
				 "Group By a.id_turno, t.id_dispositivo )),2) as Porc, causa_falla.tipo_parada, " +
				 "actividad_tag.id_turno,  tag.id_dispositivo " +
                 "From actividad_tag Inner Join causa_falla On actividad_tag.id_causa_falla = causa_falla.id_causa_falla Inner Join " +
                 "tag On actividad_tag.id_tag = tag.id_tag Where actividad_tag.id_turno = :idTurno " +
                 " And tag.id_dispositivo = :idDispo Group By  " +
                 "causa_falla.tipo_parada, actividad_tag.id_turno, tag.id_dispositivo",nativeQuery=true)
	List<Object[]> findParadasByDispositivoAndTurno(@Param("idDispo") int idDispo, @Param("idTurno") int idTurno);
	

	@Query(value="Select round(((Sum(actividad_tag.duracion_ms)*100)/(Select Sum(a.duracion_ms) " +
			 "From  actividad_tag a Inner Join tag t  On a.id_tag = t.id_tag " +
			 "Where a.id_turno = actividad_tag.id_turno And t.id_dispositivo =  tag.id_dispositivo And a.id_causa_falla is not null " +
			 "Group By a.id_turno, t.id_dispositivo )),2) as Porc, causa_falla.desc_causa_falla, " +
			 "actividad_tag.id_turno,  tag.id_dispositivo " +
            "From actividad_tag Inner Join causa_falla On actividad_tag.id_causa_falla = causa_falla.id_causa_falla Inner Join " +
            "tag On actividad_tag.id_tag = tag.id_tag Where actividad_tag.id_turno = :idTurno " +
            " And tag.id_dispositivo = :idDispo Group By  " +
            "causa_falla.desc_causa_falla, actividad_tag.id_turno, tag.id_dispositivo",nativeQuery=true)	
	List<Object[]> findParadasByDispositivoAndTurnoByCausaFalla(@Param("idDispo") int idDispo, @Param("idTurno") int idTurno);
	
	
	//Paradas_ByTipoSemanal   -- FlySpeed --
	@Query(value="Select round(((Sum(actividad_tag.duracion_ms) * 100) / (Select Sum(a.duracion_ms) " +
           "From actividad_tag a Inner Join tag t On a.id_tag = t.id_tag  Where a.id_turno = actividad_tag.id_turno And " +
           "t.id_dispositivo = tag.id_dispositivo And  date(a.fecha) = actividad_tag.fecha And " +
           "a.id_causa_falla Is Not Null Group By a.id_turno, t.id_dispositivo)),2) As Porc, causa_falla.tipo_parada, " +
  	       "actividad_tag.id_turno, tag.id_dispositivo, case extract(dow from date(actividad_tag.fecha)) " +
  	       "when 1 then 'Lunes' when 2 then 'Martes' when 3 then 'Miercoles' when 4 then 'Jueves' " +
		   "when 5 then 'Viernes' when 6 then 'Sabado' else 'Domingo' end " +
           "From (Select date(a.fecha) fecha,a.duracion_ms,a.id_tag,a.id_turno,a.id_causa_falla from actividad_tag a) actividad_tag Inner Join " +
           "causa_falla On actividad_tag.id_causa_falla = causa_falla.id_causa_falla Inner Join tag On actividad_tag.id_tag = tag.id_tag " +
           "Where tag.id_dispositivo = :idDispo And actividad_tag.id_turno = :idTurno Group By causa_falla.tipo_parada, actividad_tag.id_turno, tag.id_dispositivo,actividad_tag.fecha Order By causa_falla.tipo_parada",nativeQuery=true)
	List<Object[]> findParadasByTipoSemanal(@Param("idDispo") int idDispo, @Param("idTurno") int idTurno);
	
	//Paradas_ByTipoMensual
	@Query(value="Select round(((Sum(actividad_tag.duracion_ms) * 100) / (Select Sum(a.duracion_ms) " +
                 "From actividad_tag a where a.id_causa_falla Is Not Null And to_char(a.fecha_jornada,'YYYY') = to_char(now(),'YYYY') )),2) As Porc, causa_falla.tipo_parada, " + 
  	             "actividad_tag.fecha, Case actividad_tag.fecha When 1 Then 'Ene' When 2 Then 'Feb' When 3 Then 'Mar' When 4 Then 'Abr' When 5 Then 'May' When 6 Then 'Jun' " +
                 "When 7 Then 'Jul' When 8 Then 'Ago' When 9 Then 'Sep' When 10 Then 'Oct' When 11 Then 'Nov' When 12 Then 'Dic' Else 'DEC' End From (Select date_part('month',a.fecha_jornada) fecha,a.duracion_ms,a.id_tag,a.id_turno,a.id_causa_falla from actividad_tag a " +
                 "where to_char(a.fecha_jornada,'YYYY') = to_char(now(),'YYYY')) actividad_tag Inner Join " +
                 "causa_falla On actividad_tag.id_causa_falla = causa_falla.id_causa_falla Inner Join tag On actividad_tag.id_tag = tag.id_tag " +
                 "Group By causa_falla.tipo_parada, actividad_tag.fecha Order By causa_falla.tipo_parada",nativeQuery=true) 
	List<Object[]> findParadasByTipoMensual();
	
    @Query(value="Select coalesce(sum(actividad_tag.duracion_ms),0) totMin From actividad_tag Where " +   
    			 "to_char(actividad_tag.fecha_jornada,'YYYY') = to_char(now(),'YYYY') " +
	             "and actividad_tag.id_causa_falla is not null",nativeQuery=true)
    long totalParosNoPlan();
    
    @Query(value="Select coalesce(count(*),0) From actividad_tag a " +
                 "Where a.id_tag = :idTag and Date(a.fecha_jornada) = Date(:fecha) and a.valor != '0'",nativeQuery=true)
    long countByTagAndFechaJornada(@Param("idTag") int idTag, @Param("fecha") String fecha);

    @Query(value="Select coalesce(count(*),0) From actividad_tag a " +
            "Where a.id_tag = :idTag and Date(a.fecha_jornada) = Date(:fecha) And a.id_turno = :idTurno and a.valor != '0'",nativeQuery=true)    
	long countByTagAndTurnoAndFechaJornada(@Param("idTag") int idTag,@Param("idTurno") int idTurno, @Param("fecha") String fecha);	

    @Query(value="Select coalesce(sum(a.acum_und),0) From actividad_tag a Where a.id_tag = :idTag and " +
		     "Date(a.fecha_jornada) = Date(:fecha) and a.id_turno = :idTurno",nativeQuery=true)
    long findTopByTagAndTurnoAndFechaJornadaOrderByFecha(@Param("idTag") int idTag,@Param("idTurno") int idTurno, @Param("fecha") String fecha);

    @Query(value="Select coalesce(sum(a.acum_und),0) From actividad_tag a Where a.id_tag = :idTag and " +
		     "Date(a.fecha_jornada) = Date(:fecha)",nativeQuery=true)
    long findTopByTagAndFechaJornadaOrderByFecha(@Param("idTag") int idTag, @Param("fecha") String fecha);
    
    @Query(value="Select valor From actividad_tag a Where a.id_tag = :idTag and " +
		     "Date(a.fecha_jornada) = Date(:fecha) and a.id_turno = :idTurno Order by a.fecha Desc limit 1",nativeQuery=true)
    String findTopByTagAndTurnoAndFechaJornadaOrderByFechaDesc(@Param("idTag") int idTag,@Param("idTurno") int idTurno, @Param("fecha") String fecha);
 
    
	
}

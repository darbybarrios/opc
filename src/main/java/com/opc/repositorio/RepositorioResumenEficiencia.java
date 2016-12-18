package com.opc.repositorio;

import java.sql.Array;
import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.opc.modelo.Dispositivo;
import com.opc.modelo.ProductoMaquina;
import com.opc.modelo.ResumenEficiencia;

public interface RepositorioResumenEficiencia extends CrudRepository< ResumenEficiencia, Integer> {

	ResumenEficiencia findTopByProductoMaquinaOrderByFecRegistroDesc(ProductoMaquina prod);
	ResumenEficiencia findTopByDispositivoOrderByFecRegistroDesc(Dispositivo dispo);
	
	@Query(value= "Select " +
			  "r.id_dispositivo, " +
			  "date(r.fec_Registro), " +
			  "sum(r.cant_Unidades) as Total, " +
			  "max(r.vel_seteada) as Velocidad " +
			"From " +
			  "Resumen_Eficiencia r " +
			"Where r.id_dispositivo = :idDispo " +
			"Group By " +
			  "r.id_dispositivo,date(r.fec_Registro) " +
			  "Order by date(r.fec_Registro) ",nativeQuery = true)
	List<Object[]> findByIdDispo(@Param("idDispo") int idDispo);  //Eficiencia por Maquina Diaria
	
	@Query(value="Select " +
					"resumen_eficiencia.id_dispositivo,  resumen_eficiencia.id_turno, " +
					"Sum(resumen_eficiencia.cant_unidades) As Total,  max(resumen_eficiencia.vel_seteada) As Velocidad " +
				 "From resumen_eficiencia " +
				 "Where (date(fec_registro) = current_date) And resumen_eficiencia.id_turno = :idTurno And resumen_eficiencia.id_dispositivo = :idDispo  " + 
				 "Group By " +
				 	"resumen_eficiencia.id_dispositivo, resumen_eficiencia.id_turno",nativeQuery = true)
	List<Object[]> findByIdTurnoAndIdDispo(@Param("idTurno") int idTurno, @Param("idDispo") int idDispo); //Eficiencia por Turno y Maquina Actual Tiempo Real

	
	@Query(value="Select " +
				 "resumen_eficiencia.id_dispositivo,  resumen_eficiencia.id_turno, turno.desc_turno, date(fec_registro), " +
				 "sum(resumen_eficiencia.cant_unidades) As Total,  max(resumen_eficiencia.vel_seteada) As Velocidad " +
				 "From resumen_eficiencia Inner Join turno On resumen_eficiencia.id_turno = turno.id_turno " +
			     "where resumen_eficiencia.id_dispositivo = :idDispo " +
			     "Group By " +
			     "resumen_eficiencia.id_dispositivo, resumen_eficiencia.id_turno, turno.desc_turno, date(fec_registro) " +
			     "Order By date(fec_registro) desc " +	
			     "limit 4",nativeQuery = true)	
	List<Object[]> findPrTurnoDiaByIdDispo(@Param("idDispo") int idDispo);
	
	/*
	long findTiempoRestadoByDia(@Param("idDispo") int idDispo, @Param("Fecha") Calendar fecha);
	
	*/
	@Query(value="Select case when Sum(actividad_tag.duracion_ms) is null then 0 else Sum(actividad_tag.duracion_ms) end " +
                 "From actividad_tag Inner Join tag On actividad_tag.id_tag = tag.id_tag Inner Join causa_falla " +
                 "On actividad_tag.id_causa_falla = causa_falla.id_causa_falla Where actividad_tag.duracion_ms > 0 and actividad_tag.id_causa_falla is not null " +
                 "And causa_falla.resta_tiempo = 'si' And tag.id_dispositivo = :idDispo And date(actividad_tag.fecha_jornada) = date(:Fecha)",nativeQuery=true)
	long findTiempoRestadoByDia(@Param("idDispo") int idDispo, @Param("Fecha") String fecha);
	
	
}

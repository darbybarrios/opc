package com.opc.repositorio;

import java.sql.Array;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.opc.modelo.ProductoMaquina;
import com.opc.modelo.ResumenEficiencia;

public interface RepositorioResumenEficiencia extends CrudRepository< ResumenEficiencia, Integer> {

	ResumenEficiencia findTopByProductoMaquinaOrderByFecRegistroDesc(ProductoMaquina prod);
	
	@Query(value= "Select " +
			  "r.id_dispositivo, " +
			  "date(r.fec_Registro), " +
			  "sum(r.cant_Unidades) as Total, " +
			  "avg(r.velocidad) as Velocidad " +
			"From " +
			  "Resumen_Eficiencia r " +
			"Where r.id_dispositivo = :idDispo " +
			"Group By " +
			  "r.id_dispositivo,date(r.fec_Registro) " +
			  "Order by date(r.fec_Registro) ",nativeQuery = true)
	List<Object[]> findByIdDispo(@Param("idDispo") int idDispo);
	
	@Query(value="Select " +
					"resumen_eficiencia.id_dispositivo,  resumen_eficiencia.id_turno, " +
					"Sum(resumen_eficiencia.cant_unidades) As Total,  round(Avg(resumen_eficiencia.velocidad),2) As Velocidad " +
				 "From resumen_eficiencia " +
				 "Where (date(fec_registro) = current_date) And resumen_eficiencia.id_turno = :idTurno And resumen_eficiencia.id_dispositivo = :idDispo  " + 
				 "Group By " +
				 	"resumen_eficiencia.id_dispositivo, resumen_eficiencia.id_turno",nativeQuery = true)
	List<Object[]> findByIdTurnoAndIdDispo(@Param("idTurno") int idTurno, @Param("idDispo") int idDispo);

}

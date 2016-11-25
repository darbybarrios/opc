package com.opc.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.opc.modelo.ProductoMaquina;
import com.opc.modelo.ResumenEficiencia;

public interface RepositorioResumenEficiencia extends CrudRepository< ResumenEficiencia, Integer> {
	
	/*
	@Query("Select " +
			  "r.turno, " +
			  "r.dispositivo, " +
			  "day(r.fecRegistro) || '/' || month(r.fecRegistro) || '/' || year(r.fecRegistro), " +
			  "sum(r.cantUnidades) as Total, " +
			  "avg(r.velocidad) as Velocidad " +
			"From " +
			  "ResumenEficiencia r " +
			"Group By " +
			  "r.turno, r.dispositivo,day(r.fecRegistro) || '/' || month(r.fecRegistro) || '/' || year(r.fecRegistro) " +
			"Order by day(r.fecRegistro) || '/' || month(r.fecRegistro) || '/' || year(r.fecRegistro) ")
	List<Object[]> findByIdDispo(@Param("idDispo") int idDispo);	
	*/
	ResumenEficiencia findTopByProductoMaquinaOrderByFecRegistroDesc(ProductoMaquina prod);
	
	@Query(value= "Select " +
			  "r.id_turno, " +
			  "r.id_dispositivo, " +
			  "date(r.fec_Registro), " +
			  "sum(r.cant_Unidades) as Total, " +
			  "avg(r.velocidad) as Velocidad " +
			"From " +
			  "Resumen_Eficiencia r " +
			"Where r.id_dispositivo = :idDispo " +
			"Group By " +
			  "r.id_turno, r.id_dispositivo,date(r.fec_Registro) " +
			"Order by date(r.fec_Registro) ",nativeQuery = true)
	List<Object[]> findByIdDispo(@Param("idDispo") int idDispo);

}

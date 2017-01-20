package com.opc.repositorio;

import java.sql.Array;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.opc.modelo.Dispositivo;
import com.opc.modelo.ProduccionFecha;
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
	

	@Query(value="Select case when Sum(actividad_tag.duracion_ms) is null then 0 else Sum(actividad_tag.duracion_ms) end " +
            "From actividad_tag Inner Join tag On actividad_tag.id_tag = tag.id_tag Inner Join causa_falla " +
            "On actividad_tag.id_causa_falla = causa_falla.id_causa_falla Where actividad_tag.duracion_ms > 0 and actividad_tag.id_causa_falla is not null " +
            "And causa_falla.resta_tiempo = 'si' And date(actividad_tag.fecha_jornada) = date(:Fecha)",nativeQuery=true)
	long findTiempoRestadoByDiaGeneral(@Param("Fecha") String fecha);	
	
	//Pr_General en Flyspeed
	
	@Query(value="Select round(avg(a.pr),2) from (Select r .id_dispositivo, Sum(r.cant_unidades), avg(r.vel_seteada),((Sum(r.cant_unidades)/(1440*avg(r.vel_seteada)))*100) Pr,date(r.fecha_jornada) " +
                 "From resumen_eficiencia r Where r.fecha_jornada is not null and to_char(fecha_jornada,'YYYY') = to_char(now(),'YYYY') " +
                 "Group By r.id_dispositivo, date(r.fecha_jornada)) a",nativeQuery = true)
	double findEficienciaGeneral();
	
	//Pr_general_DiaAnterior en FlySpeed
	@Query(value="Select r.id_dispositivo, Sum(r.cant_unidades), round(avg(r.vel_seteada),2),((Sum(r.cant_unidades)/(1440*avg(r.vel_seteada)))*100) Pr,date(r.fecha_jornada) " +
                 "From resumen_eficiencia r Where r.fecha_jornada is not null Group By r.id_dispositivo, date(r.fecha_jornada) " +
                 "Order by date(r.fecha_jornada) desc limit 2",nativeQuery = true)
	List<Object[]> findEficienciaGeneralDiaAnterior();

	//Pr_general_TurnoAnterior en FlySpeed
	@Query(value="Select r.id_dispositivo,r.id_turno, Sum(r.cant_unidades), round(avg(r.vel_seteada),2),((Sum(r.cant_unidades)/(1440*avg(r.vel_seteada)))*100) Pr,date(r.fecha_jornada) " +
                 "From resumen_eficiencia r Where r.fecha_jornada is not null Group By r.id_dispositivo, r.id_turno, date(r.fecha_jornada) " +
                 "Order by date(r.fecha_jornada) desc limit 2",nativeQuery = true)
	List<Object[]> findEficienciaGeneralTurnoAnterior();	
	
	//restarTiempoPorTurno
	@Query(value="Select case when Sum(actividad_tag.duracion_ms) is null then 0 else Sum(actividad_tag.duracion_ms) end " +
            	 "From actividad_tag Inner Join tag On actividad_tag.id_tag = tag.id_tag Inner Join causa_falla " +
                 "On actividad_tag.id_causa_falla = causa_falla.id_causa_falla Where actividad_tag.duracion_ms > 0 and actividad_tag.id_causa_falla is not null " +
                 "And causa_falla.resta_tiempo = 'si' And date(actividad_tag.fecha_jornada) = date(:Fecha) And actividad_tag.id_turno = :idTurno",nativeQuery=true)
	long findTiempoRestadoByTurnoGeneral(@Param("Fecha") String fecha, @Param("idTurno") int idTurno );	
	
	//Pr_Grafico_por_maquina
	@Query(value="Select r .id_dispositivo, Sum(r.cant_unidades), avg(r.vel_seteada),((Sum(r.cant_unidades)/(1440*avg(r.vel_seteada)))*100) Pr,date(r.fecha_jornada) " +
                 "From resumen_eficiencia r Where r.fecha_jornada is not null and to_char(fecha_jornada,'YYYY') = to_char(now(),'YYYY')  " +
                 "Group By r.id_dispositivo, date(r.fecha_jornada) Order by date(r.fecha_jornada)",nativeQuery=true)
	List<Object[]> findEficienciaGeneralGraficoMaq();
	
	//Pr_GeneralGrafico
	@Query(value="Select Sum(r.cant_unidades), avg(r.vel_seteada),((Sum(r.cant_unidades)/(1440*avg(r.vel_seteada)))*100) Pr,date(r.fecha_jornada) " +
                 "From resumen_eficiencia r Where r.fecha_jornada is not null and to_char(fecha_jornada,'YYYY') = to_char(now(),'YYYY')  " +
                 "Group By date(r.fecha_jornada) Order by date(r.fecha_jornada)",nativeQuery=true)
	List<Object[]> findEficienciaGeneralGrafico();	
	
	
	@Query(value="Select r.id_dispositivo, Sum(r.cant_unidades) TotalUnd, Avg(r.vel_seteada), ((Sum(r.cant_unidades) / (1440 * Avg(r.vel_seteada))) * 100) As Pr, " +
                 "to_char(r.fecha_jornada,'YYYY'), maquina.id_maquina, maquina.nombre From resumen_eficiencia r Inner Join " +
                 "dispositivo  On r.id_dispositivo = dispositivo.id_dispositivo Inner Join maquina On dispositivo.id_maquina = maquina.id_maquina " +
                 "Where r.fecha_jornada Is Not Null And To_Char(r.fecha_jornada, 'YYYY') = To_Char(Now(), 'YYYY') Group By r.id_dispositivo, to_char(r.fecha_jornada,'YYYY'), " +
                 "maquina.id_maquina, maquina.nombre Order By TotalUnd desc",nativeQuery=true)
	List<Object[]> topMaquinas();
	
	
	@Query(value="Select Sum_cant_unidades, Case(t.Mes) When '01' then 'Ene' When '02' then 'Feb' When '03' then 'Mar' When '04' then 'Abr' " +
				"When '05' then 'May' When '06' then 'Jun' When '07' then 'Jul' When '08' then 'Ago' When '09' then 'Sep' " +
				"When '10' then 'Oct' When '11' then 'Nov' When '12' then 'Dic' else 'Dic' end From " +
				"(Select Sum(r.cant_unidades) Sum_cant_unidades, To_Char(r.fecha_jornada,'MM') Mes " +
                 "From resumen_eficiencia r Where To_Char(r.fecha_jornada,'MM') = to_char(now(),'MM') And To_Char(r.fecha_jornada,'YYYY') = to_char(now(),'YYYY')  Group By " +
                 "To_Char(r.fecha_jornada,'MM')) t", nativeQuery=true)
	List<Object[]> produccionMensual();
	
	//Pr_General_Mensual
	@Query(value="Select t.totUnd, t.vel, t.mes, Case t.Mes When 1 Then 'Ene' When 2 Then 'Feb' When 3 Then 'Mar' " +
	                 "When 4 Then 'Abr' When 5 Then 'May' When 6 Then 'Jun' When 7 Then 'Jul' When 8 Then 'Ago' When '9' Then 'Sep' " +
	                 "When '10' Then 'Oct' When '11' Then 'Nov' Else 'Dic' End From (Select " +
	                 "Sum(r.cant_unidades) As totUnd, Avg(r.vel_seteada) As vel, Date_Part('month', r.fecha_jornada) As Mes " +
	                 "From resumen_eficiencia r Where r.fecha_jornada Is Not Null And to_char(r.fecha_jornada,'YYYY') = to_char(now(),'YYYY') " +
	                 "Group By Date_Part('month', r.fecha_jornada) Order By Mes Desc) t",nativeQuery=true)
	List<Object[]> eficicienciaGeneralMensual();

	@Query(value="Select case when Sum(actividad_tag.duracion_ms) is null then 0 else Sum(actividad_tag.duracion_ms) end " +
	            	 "From actividad_tag Inner Join tag On actividad_tag.id_tag = tag.id_tag Inner Join causa_falla " +
	                 "On actividad_tag.id_causa_falla = causa_falla.id_causa_falla Where actividad_tag.duracion_ms > 0 and actividad_tag.id_causa_falla is not null " +
	                 "And causa_falla.resta_tiempo = 'si' And date_part('month',actividad_tag.fecha_jornada) = :Mes",nativeQuery=true)
	long findTiempoRestadoByMesGeneral(@Param("Mes") int mes);	
		
	@Query(value="Select Sum(r.cant_unidades) Sum_cant_unidades " +
	             "From resumen_eficiencia r Where To_Char(r.fecha_jornada,'YYYY') = to_char(now(),'YYYY') ", nativeQuery=true)
	long produccionAnual();	
	
	@Query(value="Select turno.desc_turno, producto.desc_producto, Sum(resumen_eficiencia.cant_unidades) As cant_acum " +
				"From resumen_eficiencia " + 
				"Inner Join turno On resumen_eficiencia.id_turno = turno.id_turno " + 
				"Inner Join producto_maquina On resumen_eficiencia.id_producto_maquina = producto_maquina.id_producto_maquina " + 
				"Inner Join producto On producto_maquina.id_producto = producto.id_producto " +
				"Where " +
				"resumen_eficiencia.id_dispositivo = :id_dispositivo and " +
				"resumen_eficiencia.fecha_jornada between to_date(:fecha_ini,'yyyy/MM/dd') and to_date(:fecha_fin,'yyyy/MM/dd') " +
				"Group By " +
				"turno.desc_turno, producto.desc_producto", nativeQuery=true)
	List<Object[]> produccionFecha(@Param("id_dispositivo") int idDispositivo, @Param("fecha_ini") String fecha_ini, @Param("fecha_fin") String fecha_fin );
	
	@Query(value="Select coalesce(Count(*),0) From (Select Sum(r.cant_unidades), avg(r.vel_seteada),Sum(r.cant_unidades), " +
				 "date(r.fecha_jornada),id_dispositivo " +
				 "From resumen_eficiencia r Where r.fecha_jornada is not null and to_char(fecha_jornada,'YYYY') = to_char(now(),'YYYY') " +
				 "And to_char(r.fecha_jornada,'YYYY/MM/DD') = :fecha " +
				 "Group By date(r.fecha_jornada), id_dispositivo " +
				 "Having Sum(r.cant_unidades) > 0) Q1",nativeQuery=true)
	int findcantidadLineas(@Param("fecha") String fecha);
	
	@Query(value="Select * From (Select distinct date(fecha_jornada) " +
                 "From resumen_eficiencia r Where r.fecha_jornada is not null and to_char(fecha_jornada,'YYYY') = to_char(now(),'YYYY') " +
                 "Order by date(fecha_jornada) " +
                 "limit 15) t1",nativeQuery=true)
	List<Object[]> findDispositivosEficiencia();
	
	@Query(value="Select Sum(r.cant_unidades), avg(r.vel_seteada),((Sum(r.cant_unidades)/(1440*avg(r.vel_seteada)))*100) Pr,date(r.fecha_jornada), id_dispositivo " +
				 "From resumen_eficiencia r Where to_date(to_char(r.fecha_jornada,'yyyy/MM/dd'),'yyyy/MM/dd') = to_date(:fecha,'yyyy/MM/dd') And r.fecha_jornada is not null and to_char(fecha_jornada,'YYYY') = to_char(now(),'YYYY') " +
				 "Group By date(r.fecha_jornada), id_dispositivo Order by date(r.fecha_jornada)",nativeQuery=true)
	List<Object[]> findEficGenByDispoDia(@Param("fecha") String fecha);
	
	
	@Query(value="Select Sum(r.cant_unidades), avg(r.vel_seteada),((Sum(r.cant_unidades)/(1440*avg(r.vel_seteada)))*100) Pr, " +
                 "date(r.fecha_jornada),id_turno From resumen_eficiencia r " +
		         "Where r.fecha_jornada is not null and to_char(fecha_jornada,'YYYY') = to_char(now(),'YYYY') " +
		         "Group By date(r.fecha_jornada), id_turno Order by date(r.fecha_jornada) desc " +
                 "limit 2",nativeQuery=true)
	List<Object[]> findUltimoTurno();
	
	@Query(value="Select Sum(r.cant_unidades), avg(r.vel_seteada),((Sum(r.cant_unidades)/(1440*avg(r.vel_seteada)))*100) Pr, " +
                 "date(r.fecha_jornada),id_dispositivo,id_turno From resumen_eficiencia r  " +
		         "Where to_date(to_char(r.fecha_jornada,'yyyy/MM/dd'),'yyyy/MM/dd') = to_date(:fecha,'yyyy/MM/dd') " +
		         "And id_turno = :id_turno " +
		         "And r.fecha_jornada is not null and to_char(fecha_jornada,'YYYY') = to_char(now(),'YYYY') " +
		         "Group By date(r.fecha_jornada), id_dispositivo, id_turno Order by date(r.fecha_jornada)",nativeQuery=true)
	List<Object[]> findEficGenByDispoDiaTurno(@Param("id_turno") int idTurno, @Param("fecha") String fecha);
	
	
	@Query(value = "Select resumen_eficiencia.id_dispositivo,  date(fec_registro),sum(resumen_eficiencia.cant_unidades) As Total,  Avg(resumen_eficiencia.velocidad) As Velocidad,to_char(fec_registro,'hh') " +
	               "From resumen_eficiencia " +
	               "where resumen_eficiencia.id_dispositivo = :idDispo and to_date(to_char(fecha_jornada,'yyyy/MM/dd'),'yyyy/MM/dd') = to_date(:fecha,'yyyy/MM/dd') " +
	               "Group By resumen_eficiencia.id_dispositivo, date(fec_registro), to_char(fec_registro,'hh') " +
	               "Order By date(fec_registro) desc",nativeQuery=true)
	List<Object[]> findByIdDispoAndFecha(@Param("idDispo") int idDispo, @Param("fecha") String fecha);  //Eficiencia por Maquina Diaria
	
	
	
		
	
	
}

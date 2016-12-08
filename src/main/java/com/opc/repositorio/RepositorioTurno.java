package com.opc.repositorio;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.opc.modelo.Sucursal;
import com.opc.modelo.Turno;

@Transactional
public interface RepositorioTurno extends CrudRepository<Turno, Integer> {
	
	Turno findByInicioLessThanEqualAndFinGreaterThanEqual(Date horaActual, Date hora2);
	Turno findByStatTurnoOrderByFinDesc(String stat);
	Turno findTopOrderBySecuencia(Sucursal sucursal);
	Turno findTopOrderBySecuenciaDesc(Sucursal sucursal);
	
	

}


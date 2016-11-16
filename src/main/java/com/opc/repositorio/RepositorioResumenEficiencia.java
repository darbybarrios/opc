package com.opc.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.opc.modelo.ProductoMaquina;
import com.opc.modelo.ResumenEficiencia;

public interface RepositorioResumenEficiencia extends CrudRepository< ResumenEficiencia, Integer> {
	
	ResumenEficiencia findTopByProductoMaquinaOrderByFecRegistroDesc(ProductoMaquina prod);

}

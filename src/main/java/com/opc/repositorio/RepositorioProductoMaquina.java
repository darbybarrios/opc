package com.opc.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.opc.modelo.Maquina;
import com.opc.modelo.ProductoMaquina;

public interface RepositorioProductoMaquina extends CrudRepository <ProductoMaquina, Integer> {
	
	ProductoMaquina findByMaquinaAndStatProducto(Maquina maq, String stat);
	ProductoMaquina findByMaquina(Maquina maq);
	

}

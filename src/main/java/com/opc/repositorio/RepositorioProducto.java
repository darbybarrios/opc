package com.opc.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.opc.modelo.Producto;

public interface RepositorioProducto extends CrudRepository<Producto, Integer> {

}

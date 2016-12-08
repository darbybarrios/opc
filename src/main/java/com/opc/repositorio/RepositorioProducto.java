package com.opc.repositorio;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.opc.modelo.Producto;
@Transactional
public interface RepositorioProducto extends CrudRepository<Producto, Integer> {

}

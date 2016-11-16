package com.opc.repositorio;

import org.springframework.data.repository.CrudRepository;

import com.opc.modelo.DetalleTag;
import com.opc.modelo.Tag;

public interface RepositorioDetalleTag extends CrudRepository<DetalleTag, Integer> {
	
	DetalleTag findByTagAndValorDetTag(Tag tag, String valor);

}

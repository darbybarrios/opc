package com.opc.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.opc.modelo.DetalleTag;
import com.opc.modelo.Tag;

public interface RepositorioDetalleTag extends CrudRepository<DetalleTag, Integer> {
	
	DetalleTag findByTagAndValorDetTag(Tag tag, String valor);
	List<DetalleTag> findByTag(Tag tag);
	DetalleTag findByIdDetalleTag(int idDetalleTag);
	

}

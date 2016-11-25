package com.opc.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.repositorio.RepositorioActividadTag;
import com.opc.repositorio.RepositorioResumenEficiencia;

@Controller
public class ControladorEficiencia {
	
	@Autowired
	private RepositorioResumenEficiencia daoEficiencia;
	
	@RequestMapping("graficoEficienciaMaquinaDia")
	@ResponseBody		
	public List<Object[]> graficoEficienciaMaquina(int idDispo){
		List<Object[]> resulMaq = daoEficiencia.findByIdDispo(idDispo);
		
		return resulMaq;
		
	}
	

}

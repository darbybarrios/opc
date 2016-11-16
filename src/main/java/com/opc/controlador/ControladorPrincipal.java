package com.opc.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ControladorPrincipal {
	
	  @RequestMapping(value = "/")
	  public String index() {
	    return "principal.html";
	  
	}

}

package com.opc.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executors;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Server;

public class Servidor {
	private Server server;
	String dominio;
	public Servidor(){
		
	}
	public Server conectarServidor()throws IllegalArgumentException, JIException, AlreadyConnectedException, IOException{
        final ConnectionInformation ci = new ConnectionInformation();
        
        File file = new File("C:\\antares/dispositivo.properties");
        Properties prop = new Properties();
        InputStream is = null;
        OutputStream os = null;
       
            if (file.exists()) {
                is = new FileInputStream(file);
                prop.load(is);
            }
            
            
       ci.setHost(prop.getProperty("host"));
	        //ci.setDomain("");
	   ci.setUser(prop.getProperty("user"));
	   ci.setPassword(prop.getProperty("password"));
	   ci.setProgId(prop.getProperty("progid"));
	   ci.setClsid(prop.getProperty("clsid"));
	   
	   dominio = prop.getProperty("domain");
	   
	   
	   
	   if (Objects.equals(dominio,"y")) {
		   ci.setDomain(prop.getProperty("domainid"));		   				   
	   }
        
        
   /*     
        ci.setHost("localhost");
        //ci.setDomain("");
        ci.setUser("Maria");
        ci.setPassword("v17638296");
        //ci.setProgId("Matrikon.OPC.Simulation.1");
        //ci.setClsid("F8582CF2-88FB-11D0-B850-00C0F0104305"); // if ProgId is not working, try it using the Clsid instead
        
        // Conexion para RsLinx
        
        ci.setProgId("RSLinx OPC Server");
        ci.setClsid("A05BB6D6-2F8A-11D1-9BB0-080009D01446");    */
        

        // create a new server
	  
	   
        return new Server(ci, Executors.newSingleThreadScheduledExecutor());
	
	
	
	
	
	}
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
	
}

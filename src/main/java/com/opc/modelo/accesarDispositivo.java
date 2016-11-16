package com.opc.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executors;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.browser.Branch;
import org.openscada.opc.lib.da.browser.Leaf;



public class accesarDispositivo {
		private List<Dispositivo> plcs = new ArrayList<Dispositivo>();
		private List<Tag> tags = new ArrayList<Tag>();
		Server server;
		String dominio;
		private static accesarDispositivo instancia;
		
		



		private accesarDispositivo() throws IllegalArgumentException, JIException, AlreadyConnectedException, IOException{
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
	        server = new Server(ci, Executors.newSingleThreadScheduledExecutor());
	        
	       // cargarPlcs();
	        
			
		}

		
		public static accesarDispositivo getInstancia() throws IllegalArgumentException, JIException, AlreadyConnectedException, IOException {
			if (instancia == null){
				instancia = new accesarDispositivo();
			}
			return instancia;
		}		
		
	    public void plc ( final Branch branch, final int level ) 
	    { 
	        final StringBuilder sb = new StringBuilder (); 
	        //Item item;
	        
	        for ( int i = 0; i < level; i++ ) 
	        { 
	            sb.append ( "  " ); 
	        } 
	        final String indent = sb.toString (); 
	 
	        for ( final Branch subBranch : branch.getBranches () ) 
	        { 
	            //System.out.println ( indent + "Nodo: " + subBranch.getName () + "ID : " + subBranch.hashCode() ); 
	        	
	        	plcs.add(new Dispositivo(subBranch.hashCode(),subBranch.getName(),"0"));
	            //dumpTree ( grupo,subBranch, level + 1 ); 
	        } 
	    } 

	    
	    public void tag ( final Branch branch, final int level, String nombrePlc) 
	    { 
	        final StringBuilder sb = new StringBuilder (); 
	        //Item item;
	        
	        for ( int i = 0; i < level; i++ ) 
	        { 
	            sb.append ( "  " ); 
	        } 
	        final String indent = sb.toString (); 
	        
	       // if (branch.getName() == nombrePlc){
	 
		      /*  for ( final Leaf leaf : branch.getLeaves () ) 
		        { 
		            System.out.println ( indent + "Item: " + leaf.getName () + " [" + leaf.getItemId () + "]" ); 
		            tags.add(new Tag(leaf.getItemId (), leaf.getName ()));
		            //Item item = grupo.addItem(leaf.getItemId());
		            //dumpItemState ( item,item.read(true)  ); 
		        } */
		        for ( final Leaf leaf : branch.getLeaves()) 
		        { 
		           // System.out.println ( indent + "Item: " + leaf.getName () + " [" + leaf.getItemId () + "]" ); 
		            tags.add(new Tag(leaf.getItemId (), leaf.getName ()));
		            //Item item = grupo.addItem(leaf.getItemId());
		            //dumpItemState ( item,item.read(true)  ); 
		        } 
		        
		        for ( final Branch subBranch : branch.getBranches () ) 
		        { 
		            //System.out.println ( indent + "Nodo: " + subBranch.getName () + "ID : " + subBranch.hashCode() ); 
		            //tags.add(new Tag(subBranch.getName(), subBranch.getName()));
		        	//plcs.add(new Dispositivo(subBranch.hashCode(),subBranch.getName(),"0"));
		            //dumpTree ( grupo,subBranch, level + 1 ); 
		           // if (subBranch.getName () == nombrePlc){
		            
		               
		            	tag ( subBranch, level + 1, nombrePlc ); //Agregar el Nodo Name como parametro
		           // }
		        }
	       // }
	    }	    
		
		public void cargarPlcs() throws IllegalArgumentException, UnknownHostException, JIException, AlreadyConnectedException{
			//server.connect();
			plc(server.getTreeBrowser().browse(),0);

		
		}
		
		public void cargarTags(String nombrePlc) throws IllegalArgumentException, UnknownHostException, JIException, AlreadyConnectedException{
			//server.connect();

			tag(server.getTreeBrowser().browse(),0,nombrePlc);
		
			
		}
		
		
		
		public List<Dispositivo> mostrarTodos(){
			return plcs;
		}
		
		public List<Tag> mostrarTags(){
			return tags;
		}
		
		public Server conectar() throws IllegalArgumentException, UnknownHostException, JIException, AlreadyConnectedException{
			
			server.connect();
			return server;
		}
		
		public void desconectar() throws IllegalArgumentException, UnknownHostException, JIException, AlreadyConnectedException{
			
			server.disconnect();
			
		}	
		
		public Server sesion(){
			return server;
		}
		
		

}

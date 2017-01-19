package com.opc.controlador;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.AddFailedException;
import org.openscada.opc.lib.da.DuplicateGroupException;
import org.openscada.opc.lib.da.Group;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.da.UnknownGroupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.opc.modelo.ActividadTag;
import com.opc.modelo.DetalleTag;
import com.opc.modelo.Dispositivo;
import com.opc.modelo.ProduccionFecha;
import com.opc.modelo.Tag;
import com.opc.modelo.TipoValor;
import com.opc.modelo.UnidadMedida;
import com.opc.modelo.accesarDispositivo;
import com.opc.repositorio.RepositorioDetalleTag;
import com.opc.repositorio.RepositorioDispositivo;
import com.opc.repositorio.RepositorioTag;
import com.opc.repositorio.RepositorioTipoValor;
import com.opc.repositorio.RepositorioUnidadMedida;

@Controller
public class ControladorTag {
	@Autowired
	private RepositorioTag daoTag;
	@Autowired
	private RepositorioDispositivo daoDispo;
	@Autowired
	private RepositorioUnidadMedida daoUnidad;
	@Autowired
	private RepositorioTipoValor daoTipoValor;
	@Autowired
	private RepositorioDispositivo daoDispositivo;
	@Autowired
	private RepositorioDetalleTag daoDetalleTag;
	
	@RequestMapping("listar-tags-dispo")
	@ResponseBody	
	public List<Tag> listar_tag_dispo(int idDispositivo) throws IllegalArgumentException, JIException, AlreadyConnectedException, IOException{
		Dispositivo dispo = daoDispo.findOne(idDispositivo);
		String nombDisp;
		accesarDispositivo acceso = accesarDispositivo.getInstancia();
		int longDis = dispo.getDescripcion().length();
		//acceso.cargarTags(dispo.getDescripcion());
		List<Tag> tags = acceso.mostrarTags();		
	
		List<Tag> tagDef = new ArrayList<Tag>();
		
		Iterator<Tag> vectorTag = tags.iterator();
		
		while (vectorTag.hasNext()){
			Tag tag = vectorTag.next();
			
			if (tag.getItemId().substring(0, 1).equals("[")){
				nombDisp = tag.getItemId().substring(1, longDis+1);
					if (nombDisp.equals(dispo.getDescripcion()) ){
						tagDef.add(new Tag(tag.getItemId(),tag.getdescTag()));
					}
			}	
				else{
					tagDef.add(new Tag(tag.getItemId(),tag.getdescTag()));
			}
					
		}  
		
		//acceso.desconectar();
		return tagDef;
	}
	
	
	@RequestMapping("listar-tags")
	@ResponseBody
	public List<Tag> listarTags(int idDispositivo){
		Dispositivo dispo2 = daoDispo.findOne(idDispositivo);
		List<Tag> unidad = (List<Tag>) daoTag.findByDispositivoAndStatReg(dispo2, "0");
		
		return unidad;
		
	}
	
	@RequestMapping("listar-tags-valores")
	@ResponseBody
	public List<ActividadTag> listarTagsValores(int idDispositivo) throws IllegalArgumentException, JIException, AlreadyConnectedException, IOException, NotConnectedException, DuplicateGroupException, AddFailedException, UnknownGroupException{
		Dispositivo dispo2 = daoDispo.findOne(idDispositivo);
		List<Tag> unidad = (List<Tag>) daoTag.findByDispositivoAndStatReg(dispo2, "0");
		accesarDispositivo acceso = accesarDispositivo.getInstancia();
		List<ActividadTag> listaTags = new ArrayList<ActividadTag>();
		String valor;
		int valorInt;
		Group group = null;
		
		
		if (unidad!=null){
		
		Server server = acceso.sesion();
	    
		if (group == null){
			group = server.findGroup("Tags");
		}
		
		for (int i=0; i < unidad.size(); i++){
			ActividadTag atag = new ActividadTag();
			Tag tag = new Tag();
			
			String itemId = unidad.get(i).getItemId();
			TipoValor tipoValor = unidad.get(i).getTipoValor();
			String descTipo = tipoValor.getDescTipoValor();
			
    		if (descTipo.equals("Binario")){
    			itemId = itemId + "/" + unidad.get(i).getValorBit();
    			//System.out.println(itemId);
    		}	
    		
			tag.setItemId(itemId);
			tag.setdescTag(unidad.get(i).getdescTag());
			atag.setTag(tag);
			
		
			final Item item = group.addItem ( itemId );
			final ItemState itemState = item.read ( true );
			valor = ":" +itemState.getValue();
			valor = valor.replaceAll("\\[", "");
            valor = valor.replaceAll("\\]", "");
            valor = valor.substring(1, valor.length());
    		//System.out.println("TIPO FALLA");
    		valorInt = Integer.parseInt(valor);	
    		
    		if ( unidad.get(i).getEscala() == 1){ //El Valor es Inverso en los Binarios
    			if (valorInt == 0){
    				valorInt = 1;
    				valor = "1";
    			}else if (valorInt == 1){
    				valorInt = 0;
    				valor = "0";
    			}
    		}
    		
    		atag.setCalidad(itemState.getQuality());
    		atag.setFecha(itemState.getTimestamp());
    		atag.setValor(valor);
    		listaTags.add(atag);
		}
		
		
		
		}
		return listaTags;
		
	}	
	
	
	
	
	@RequestMapping("listar-unidades")
	@ResponseBody
	public List<UnidadMedida> listarUnidades(){
		List<UnidadMedida> unidad = (List<UnidadMedida>) daoUnidad.findAll();
		return unidad;
		
	}
	
	@RequestMapping("listar-valores")
	@ResponseBody	
	public List<TipoValor> listarValores(){
		List<TipoValor> valores = (List<TipoValor>) daoTipoValor.findAll();
		return valores;
	}
	
	@RequestMapping("crear-tag")
	@ResponseBody	
	public int crear_tag(String itemId, String descTag, String statWeb,int idUnidadMedida, int idTipoValor, 
			int escala, int intervalo, int valorBit, int idDispositivo, String tipoInformacion){
		String nombre;
		try{
			TipoValor tipoValor = daoTipoValor.findOne(idTipoValor);
			UnidadMedida unidadMedida = daoUnidad.findOne(idUnidadMedida);
			Dispositivo dispositivo = daoDispositivo.findOne(idDispositivo); 
			String status;
			/*System.out.println(itemId);
			System.out.println(descTag);
			System.out.println(statWeb);
			System.out.println(idUnidadMedida);
			System.out.println(idTipoValor);
			System.out.println(escala);
			System.out.println(intervalo);
			System.out.println(valorBit);*/
			if (tipoInformacion == "3"){
				status = "1";
			}else{
				status = "0";
			}
			
			Tag tag = new Tag(itemId,descTag,new Date(),status,statWeb,unidadMedida,tipoValor,"0",escala,intervalo,valorBit,dispositivo,tipoInformacion);
			daoTag.save(tag);
			//System.out.println(tag.getItemId());
				
		}
		catch (Exception ex){
			return 0;
		}		
		
		return 1;
		
	}
	
	@RequestMapping(value = "eliminar-tag")
	@ResponseBody
	public String eliminarTag(int idTag, String stat){
		try{
			Tag tag = daoTag.findOne(idTag);
			tag.setStatReg(stat);
			daoTag.save(tag);
		}
		catch (Exception ex){
			return "Error al Eliminar Tag : " + ex.toString();
		}
		
		return "Tag eliminada Correctamente";
	}

	@RequestMapping(value = "valorDetTag")
	@ResponseBody	
	public String buscarValorDetalle(int idTag, String valor){
		Tag tag = daoTag.findOne(idTag);
		DetalleTag det =daoDetalleTag.findByTagAndValorDetTag(tag, valor);
		return det.getDescDetalleTag();
	}
	
	@RequestMapping(value = "detalle-tag")
	@ResponseBody	
	public List<DetalleTag> detalletag(int idTag){
		Tag tag = new Tag();
		tag.setIdTag(idTag);
		
		List<DetalleTag> listaDetalleTag = daoDetalleTag.findByTag(tag);
		
		return listaDetalleTag;
	}
	
	@RequestMapping(value = "listar-tag")
	@ResponseBody	
	public List<Tag> ListarTag(int idMaquina){
		List<Object[]> tag =daoTag.BuscarTagxMaquina(idMaquina);
		List<Tag> listaTag = new ArrayList<Tag>();
		for (int i = 0; i < tag.size(); i++) {
			Tag Tag = new Tag();
			Tag.setIdTag((Integer)tag.get(i)[0]);
			Tag.setdescTag((String)tag.get(i)[1]);
			listaTag.add(Tag);
		}
		return listaTag;
	}
	
	@RequestMapping(value = "insertar-detalle")
	@ResponseBody	
	public void InsertarDetalle(int idTag, String valor, String descripcion){
		Tag tag = new Tag();
		tag.setIdTag(idTag);
		DetalleTag detalle = new DetalleTag();
		detalle.setDescDetalleTag(descripcion);
		detalle.setTag(tag);
		detalle.setTipoDetalle("F");
		detalle.setStatDetalleTag("0");
		detalle.setValorDetTag(valor);
		daoDetalleTag.save(detalle);
	
		
	}
	
	@RequestMapping(value = "buscar-detalle")
	@ResponseBody	
	public DetalleTag BuscarDetalle(int idDetalleTag){
		DetalleTag detalle = daoDetalleTag.findByIdDetalleTag(idDetalleTag);
			
		return detalle;
		
	}
	
	@RequestMapping(value = "editar-detalle")
	@ResponseBody	
	public void EditarDetalle(int idDetalleTag, String valor, String descripcion){
		
		DetalleTag detalle = daoDetalleTag.findOne(idDetalleTag);
		detalle.setValorDetTag(valor);
		detalle.setDescDetalleTag(descripcion);
		daoDetalleTag.save(detalle);
	
		
	}
	@RequestMapping(value = "eliminar-detalle")
	@ResponseBody	
	public void EliminarDetalle(int idDetalleTag){
		
		DetalleTag detalle = daoDetalleTag.findOne(idDetalleTag);
		detalle.setStatDetalleTag("1");
		daoDetalleTag.save(detalle);
	
		
	}
	
	
	
}

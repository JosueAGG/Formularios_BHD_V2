package com.alldatum.ebx.bhd.form.gdpr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationHome;
import com.onwbp.adaptation.AdaptationName;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.Request;
import com.onwbp.adaptation.RequestResult;
import com.onwbp.adaptation.XPathFilter;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.instance.BranchKey;
import com.orchestranetworks.instance.Repository;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.Constraint;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.ConstraintEnumeration;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaTypeName;
import com.orchestranetworks.schema.TableRefFilter;
import com.orchestranetworks.schema.TableRefFilterContext;
import com.orchestranetworks.schema.dynamic.BeanDefinition;
import com.orchestranetworks.schema.dynamic.BeanElement;
import com.orchestranetworks.schema.dynamic.BeanFacetTableRef;
import com.orchestranetworks.service.ServiceKey;
import com.orchestranetworks.ui.base.JsFunctionCall;
import com.orchestranetworks.ui.form.widget.UIComboBox;
import com.orchestranetworks.ui.form.widget.UIRadioButtonGroup;
import com.orchestranetworks.ui.selection.RecordEntitySelection;
import com.orchestranetworks.ui.selection.TableEntitySelection;
import com.orchestranetworks.userservice.ObjectKey;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.UserServiceDisplayConfigurator;
import com.orchestranetworks.userservice.UserServiceEventContext;
import com.orchestranetworks.userservice.UserServiceEventOutcome;
import com.orchestranetworks.userservice.UserServiceObjectContextBuilder;
import com.orchestranetworks.userservice.UserServicePaneContext;
import com.orchestranetworks.userservice.UserServicePaneWriter;
import com.orchestranetworks.userservice.UserServiceProcessEventOutcomeContext;
import com.orchestranetworks.userservice.UserServiceSetupDisplayContext;
import com.orchestranetworks.userservice.UserServiceSetupObjectContext;
import com.orchestranetworks.userservice.UserServiceValidateContext;

public class Pruebadecampos<T extends TableEntitySelection> implements UserService<T> {
	
	private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");
    
	//1.- Se crean  objeto y path  nuevos (CampoDependiente)
	private static final ObjectKey RESPUESTAS_KEY=ObjectKey.forName("respuestas");
	private static final Path caminoRiesgos= Path.parse("riesgos");
    private static final Path caminoRespuestas= Path.parse("respuestaRiesgosNew");
	private static  Adaptation dataset;
    private static  ValueContext vc;
	private   UserServiceSetupObjectContext<T> ContextoUserService;
	private static Repository repositorio;
	
	@Override
	public UserServiceEventOutcome processEventOutcome(UserServiceProcessEventOutcomeContext<T> aContext,
			UserServiceEventOutcome anOutcome) {

		return anOutcome;
	}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<T> aContext, UserServiceDisplayConfigurator aConfigurator) {
		if(aContext.isInitialDisplay())
			vc = aContext.getValueContext(RESPUESTAS_KEY);
		aConfigurator.setContent(this::pane);
		aConfigurator.setDefaultButtons(this::save);

		
	}
	
	private UserServiceEventOutcome save(UserServiceEventContext aContext) {
		
		aContext.save(OBJECT_KEY,RESPUESTAS_KEY); //Se agrego el segundo argumento 20-03-2024
		
		return null;
	}
	
	private void pane(UserServicePaneContext aPaneContext, UserServicePaneWriter aWriter) {
		
		aWriter.setCurrentObject(RESPUESTAS_KEY);
		
		//Seccion de creación 22-03-24
		UIComboBox riesgosBox = aWriter.newComboBox(caminoRiesgos);
		try {
		riesgosBox.setActionOnAfterValueChanged(JsFunctionCall.on("resetBanner"));
		}catch(Exception e) {e.getStackTrace();}
		UIComboBox resultadosBox= aWriter.newComboBox(caminoRespuestas);
		
		
		aWriter.setCurrentObject(OBJECT_KEY);
		
		aWriter.add_cr("<h3>Tratamiento</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Identificador"));
		aWriter.addFormRow(Path.parse("NombreT"));
		aWriter.addFormRow(Path.parse("Fecha_de_creacion_del_tratamiento"));
		aWriter.endExpandCollapseBlock();
		//----------------------------------------
		aWriter.add_cr("<h3>Responsable</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Nombre_contacto"));
		aWriter.addFormRow(Path.parse("Rol2"));
		aWriter.addFormRow(Path.parse("CargoR"));
		aWriter.endExpandCollapseBlock();	
		//----------------------------------------
		aWriter.add_cr("<h3>Corresponsable</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Nombre_Co"));
		aWriter.addFormRow(Path.parse("Rol"));
		aWriter.addFormRow(Path.parse("CargoCR"));
		aWriter.endExpandCollapseBlock();
		//----------------------------------------
		aWriter.add_cr("<h3>Analista</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Nombreanalista"));
		aWriter.addFormRow(Path.parse("RolAnalista"));
		aWriter.addFormRow(Path.parse("CargoAn"));
		aWriter.endExpandCollapseBlock();
		//----------------------------------------
		aWriter.add_cr("<h3>Procesos operativos</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Proceso_operativo"));
		aWriter.endExpandCollapseBlock();
		//----------------------------------------
		aWriter.add_cr("<h3>Finalidad del tratamiento</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Finalidad_del_tratamiento"));
		aWriter.addFormRow(Path.parse("Fines_secundarios"));
		aWriter.addFormRow(Path.parse("Fines_posteriores"));
		aWriter.endExpandCollapseBlock();
		//-----------------------------------------
		aWriter.add_cr("<h3>Alcance y ámbito del tratamiento</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos personales"), true);
		aWriter.addFormRow(Path.parse("CategorIa"));
		aWriter.addFormRow(Path.parse("Frecuencia_de_recoleccion"));
		aWriter.addFormRow(Path.parse("Ciclo_de_vida_de_los_datos"));
		
		startComplexGroup(aPaneContext, aWriter, Path.parse("Actualizacion"), "block_actualizacion");
		aWriter.addFormRow(Path.parse("Mactualizacion"));
		aWriter.addFormRow(Path.parse("Periodicidad_de_actualizacion"));
		endComplexGroup(aWriter);
		
		aWriter.addFormRow(Path.parse("Titulares_afectados"));
		aWriter.addFormRow(Path.parse("Volumen"));
		aWriter.endExpandCollapseBlock();
		
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Extensión geográfica"), true);
		aWriter.addFormRow(Path.parse("Extension_geografica"));
		aWriter.endExpandCollapseBlock();
		
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Duración"), true);
		aWriter.addFormRow(Path.parse("Duracion_del_tratamiento_en_el_tiempo"));
		aWriter.endExpandCollapseBlock();
		
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Plazos de conservación después de concluir la relación con el titular"), true);
		aWriter.addFormRow(Path.parse("Plazos_de_conservacion_titular"));
		aWriter.endExpandCollapseBlock();
		//---------------------------------------
		aWriter.add_cr("<h3>Naturaleza</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Implementación"), true);
		aWriter.addFormRow(Path.parse("Operaciones_ejecuta"));
		
		startComplexGroup(aPaneContext, aWriter, Path.parse("Casos_de_uso"), "block_casos_uso");
		aWriter.addFormRow(Path.parse("Senales_caso_de_uso"));
		startComplexGroup(aPaneContext, aWriter, Path.parse("VinculoeleSN"), "block_vinculoelesn");
		aWriter.addFormRow(Path.parse("Vinculo2"));
		startComplexGroup(aPaneContext, aWriter, Path.parse("adjuntadoc"), "block_adjuntadoc");
		aWriter.addFormRow(Path.parse("doc"));
		endComplexGroup(aWriter);
		endComplexGroup(aWriter);
		endComplexGroup(aWriter);
		
		aWriter.addFormRow(Path.parse("Inventario_de_activos"));
		aWriter.endExpandCollapseBlock();
		
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Levantamiento y/o generación de datos"), true);
		
		startComplexGroup(aPaneContext, aWriter, Path.parse("Externo2"), "block_externo");
		aWriter.addFormRow(Path.parse("origendatos"));
		endComplexGroup(aWriter);
		
		startComplexGroup(aPaneContext, aWriter, Path.parse("Internos2"), "block_interno");
		aWriter.addFormRow(Path.parse("origendatosinternos"));
		endComplexGroup(aWriter);
		
		aWriter.addFormRow(Path.parse("Categorias_de_datos_inferidos_o_generados"));
		aWriter.endExpandCollapseBlock();
		
		
		
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Acceso a los datos"), true);
		
		startComplexGroup(aPaneContext, aWriter, Path.parse("Acceso_datos_2"), "block_acceso_datos");
		startComplexGroup(aPaneContext, aWriter, Path.parse("Internos3"), "block_internos");
		aWriter.addFormRow(Path.parse("./Internos/Cargo"));
		aWriter.addFormRow(Path.parse("./Internos/Operacionejecuta"));
		endComplexGroup(aWriter);
		startComplexGroup(aPaneContext, aWriter, Path.parse("Esternos3"), "block_esternos");
		aWriter.addFormRow(Path.parse("./Externos/NombreExt"));
		aWriter.addFormRow(Path.parse("./Externos/Rol4"));
		
		endComplexGroup(aWriter);
		endComplexGroup(aWriter);
		
		aWriter.addFormRow(Path.parse("Nivel_de_acceso"));
		aWriter.addFormRow(Path.parse("Permisos"));
		
		startComplexGroup(aPaneContext, aWriter, Path.parse("Interfaces_con_otros_tratamientos"), "block_interfaces_tratamientos");
		aWriter.addFormRow(Path.parse("BusquedaT"));
		endComplexGroup(aWriter);
		
		
		startComplexGroup(aPaneContext, aWriter, Path.parse("Comunicacion_de_datos_Transferencias"), "block_comunicacion_datos");
		
			startComplexGroup(aPaneContext, aWriter, Path.parse("./Cesion/CesionDecicion"), "block_cesion_decision");
			aWriter.addFormRow(Path.parse("./Cesion/DestinatarioCesion"));
			aWriter.addFormRow(Path.parse("./Cesion/FinalidadesCesion"));
			aWriter.addFormRow(Path.parse("./Cesion/Causaslegitimacioncesion"));
			endComplexGroup(aWriter);
			
			startComplexGroup(aPaneContext, aWriter, Path.parse("./Comunicacionencargado/Comunicacionencargadodecicion"), "block_comunicacion_encargado");
				aWriter.addFormRow(Path.parse("./Comunicacionencargado/Destinatariocomunicacionconencargado"));
				aWriter.addFormRow(Path.parse("./Comunicacionencargado/OperacionqueejecutaComuencargados"));
				aWriter.addFormRow(Path.parse("./Cesion/Causaslegitimacioncesion"));
				
				startComplexGroup(aPaneContext, aWriter, Path.parse("./Comunicacionencargado/ContratoCconEncargado"), "block_contrato_encargado");
				aWriter.addFormRow(Path.parse("./Comunicacionencargado/NombreContratoCConEncarg"));
				aWriter.addFormRow(Path.parse("./Comunicacionencargado/FechaFormalizacionCconEncarg"));
				
				startComplexGroup(aPaneContext, aWriter, Path.parse("./Comunicacionencargado/VigenteCconEncarg"), "block_vigente_encargado");
				aWriter.addFormRow(Path.parse("./Comunicacionencargado/AdjuntarContratoCconEncarg"));
				endComplexGroup(aWriter);
		
				endComplexGroup(aWriter);
			
			endComplexGroup(aWriter);
			
			startComplexGroup(aPaneContext, aWriter, Path.parse("./Comunicacionconsubencargado/Comunicacionsubencargadodecicion"), "block_comunicacion_subencargado");
				aWriter.addFormRow(Path.parse("./Comunicacionconsubencargado/DestinatarioCconSuben"));
				aWriter.addFormRow(Path.parse("./Comunicacionconsubencargado/Operacionesqueejecutansubencargado"));
				
				startComplexGroup(aPaneContext, aWriter, Path.parse("./Comunicacionconsubencargado/Contratosubencargado"), "block_contrato_subencargado");
				aWriter.addFormRow(Path.parse("./Comunicacionconsubencargado/nombrecontratosubencargado"));
				aWriter.addFormRow(Path.parse("./Comunicacionconsubencargado/Fechaformalizacionsubencargado"));
				aWriter.addFormRow(Path.parse("./Comunicacionconsubencargado/Periododevigenciasubencargado"));
				
				startComplexGroup(aPaneContext, aWriter, Path.parse("./Comunicacionconsubencargado/Vigentesubencargado"), "block_vigente_subencargado");
				aWriter.addFormRow(Path.parse("./Comunicacionconsubencargado/Adjuntarcontratosubencargado"));
				endComplexGroup(aWriter);
		
				endComplexGroup(aWriter);
			
			endComplexGroup(aWriter);
			
			startComplexGroup(aPaneContext, aWriter, Path.parse("./Trasferencia/Trasferenciadecicion"), "block_transferencia");
				aWriter.addFormRow(Path.parse("./Trasferencia/Destinatariotrasferencia"));
				aWriter.addFormRow(Path.parse("./Trasferencia/Finalidadestrasferencia"));
				aWriter.addFormRow(Path.parse("./Trasferencia/Causaslegitimaciontrasferencia"));
				aWriter.addFormRow(Path.parse("./Trasferencia/Paisdestinotrasferencia"));
				
				startComplexGroup(aPaneContext, aWriter, Path.parse("./Trasferencia/Contratotrasferencia"), "block_contrato_transferencia");
				aWriter.addFormRow(Path.parse("./Trasferencia/Nombrecontratotrasferencia"));
				aWriter.addFormRow(Path.parse("./Trasferencia/Fechaformalizaciontrasferencia"));
				aWriter.addFormRow(Path.parse("./Trasferencia/Periododevigenciatrasferencia"));
				
				startComplexGroup(aPaneContext, aWriter, Path.parse("./Trasferencia/Vigenciatransferencia"), "block_vigente_transferencia");
				aWriter.addFormRow(Path.parse("./Trasferencia/Adjuntarcontratotrasferencia"));
				endComplexGroup(aWriter);
		
				endComplexGroup(aWriter);
			
			endComplexGroup(aWriter);

		endComplexGroup(aWriter);
		
		aWriter.endExpandCollapseBlock();
		//---------------------------------------
		aWriter.add_cr("<h3>Conclusión juicio de idoneidad, proporcionalidad y necesidad</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Conclusion_juicio_de_idoneidad_proporcionalidad_necesidad"));
		aWriter.endExpandCollapseBlock();
		
		//---------------------------------------
		aWriter.add_cr("<h3>Riesgos</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		
		aWriter.setCurrentObject(RESPUESTAS_KEY);
		//aWriter.addFormRow(Path.parse("Riesgo"));
		aWriter.addFormRow(riesgosBox);
		aWriter.addFormRow(Path.parse("prueba"));
		aWriter.setCurrentObject(OBJECT_KEY);
		
		aWriter.endExpandCollapseBlock();
		
		//---------------------------------------
		aWriter.add_cr("<h3>Debilidades</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Debilidades"));
		aWriter.endExpandCollapseBlock();
		
		//---------------------------------------
		aWriter.add_cr("<h3>Medidas y garantías implementadas</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startComplexGroup(aPaneContext, aWriter, Path.parse("Por_defecto_y_desde_el_diseno"), "block_por_defecto_y_desde_el_diseno");
		aWriter.addFormRow(Path.parse("defectomenu"));
		endComplexGroup(aWriter);
		
		aWriter.addFormRow(Path.parse("Como_respuesta_riesgos_del_tratamiento"));
		// ---- Test
		aWriter.setCurrentObject(RESPUESTAS_KEY); // Agregado 20-03-2024
		
		aWriter.addFormRow(resultadosBox);//Cambiado
		
		aWriter.setCurrentObject(OBJECT_KEY);
		//Fin de intento de agregar campo nuevo 20-03-2024
		
		startComplexGroup(aPaneContext, aWriter, Path.parse("Asociadas_a_la_transferencia_de_datos"), "block_asociadas_a_la_transferencia_de_datos");
		aWriter.addFormRow(Path.parse("asociacionmenu"));
		endComplexGroup(aWriter);
//Duda
		startComplexGroup(aPaneContext, aWriter, Path.parse("Asociadas_al_procesamiento_de_parte_de_terceros"), "block_asociadas_al_procesamiento_de_parte_de_terceros");
		startComplexGroup(aPaneContext, aWriter, Path.parse("Cargo"), "block_Cargo");
		
		
		aWriter.addFormRow(Path.parse("./Encargado/Descripcion"));
		aWriter.addFormRow(Path.parse("./Subencargado/Descripcion"));
		endComplexGroup(aWriter);
		endComplexGroup(aWriter);		
		
		aWriter.endExpandCollapseBlock();
		
		
		//---------------------------------------
		aWriter.add_cr("<h3>Posibles efectos colaterales, no deseados derivados del tratamiento</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startComplexGroup(aPaneContext, aWriter, Path.parse("Posibles_efectos_colateralesDecicion"), "block_posibles_efectos_colateralesDecicion");
		aWriter.addFormRow(Path.parse("Posibles_efectos_colaterales"));
		endComplexGroup(aWriter);	
		aWriter.endExpandCollapseBlock();	
		
		//---------------------------------------
		aWriter.add_cr("<h3>Antecedentes (incidentes) conocidos</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startComplexGroup(aPaneContext, aWriter, Path.parse("AntecedentesDecicion"), "block_antecedentesDecicion");
		aWriter.addFormRow(Path.parse("Antecedentes_incidentes_conocidos"));
		endComplexGroup(aWriter);	
		startComplexGroup(aPaneContext, aWriter, Path.parse("VincularFgestiondeviolacion"), "block_vincularFgestiondeviolacion");
		aWriter.addFormRow(Path.parse("IdentificadorFGV"));
		endComplexGroup(aWriter);
		aWriter.endExpandCollapseBlock();	
		
		//---------------------------------------
		aWriter.add_cr("<h3>Amenazas conocidas</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startComplexGroup(aPaneContext, aWriter, Path.parse("Amenazasdecicion"), "block_amenazasdecicion");
		aWriter.addFormRow(Path.parse("Tipodevulneracion"));
		endComplexGroup(aWriter);	
		
		aWriter.endExpandCollapseBlock();		
		
		//---------------------------------------
///Duda2
		/*	aWriter.add_cr("<h3>Revisión metodológica</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		DropList(aPaneContext, aWriter, Path.parse("Revision_metodologica"), "block2_revision_metodologica");
		aWriter.addFormRow(Path.parse("ConcideracionesA"));
		aWriter.addFormRow(Path.parse("Menu_aceptable"));
		aWriter.addFormRow(Path.parse("NombreDRevisorA"));
		aWriter.addFormRow(Path.parse("RoloCargoFAB"));
		enddroplis(aWriter);	
		DropList(aPaneContext, aWriter, Path.parse("Revision_metodologica"), "block3_revision_metodologica");
		aWriter.addFormRow(Path.parse("ConcideracionIna"));
		aWriter.addFormRow(Path.parse("MenuIna"));
		aWriter.addFormRow(Path.parse("Nombrerevisorina"));
		aWriter.addFormRow(Path.parse("RolocargoinaFAB"));
		enddroplis(aWriter);	
		aWriter.endExpandCollapseBlock();		
*/
		
		
	    //Metodo para escribir un valor en un campo en concreto.
		//aWriter.addJS_cr("ebx_form_setValue('" + aWriter.getPrefixedPath(Path.parse("NombreT")).format() + "', '" +  "EXITOSO" + "')");
		//aWriter.addJS_cr("ebx_form_setValue('" + aWriter.getPrefixedPath(Path.parse("Como_respuesta_riesgos_del_tratamiento")).format() + "', '" +  "[]" + "')");
		
		//---------------------------------------
		aWriter.setCurrentObject(RESPUESTAS_KEY);
        
    		aWriter.addJS_cr("function resetBanner(){");
	        aWriter.addJS_cr("var idBanner = ebx_form_getValue('" + aWriter.getPrefixedPath(caminoRiesgos).format() + "');");//Obtiene el valor del camporiesgo
	        aWriter.addJS_cr("var cadena=JSON.stringify(idBanner);");
	        aWriter.addJS_cr("var pedazo=JSON.parse(cadena);");
	        aWriter.addJS_cr("var result=pedazo.key;");
	   	    aWriter.addJS_cr("var valorBanner = null;");//Variable valorDErESPUESTA es null
	        aWriter.addJS_cr("if(idBanner != null){");//si el valor del campo riesgo es diferente a null
	        aWriter.addJS_cr("var valorBanner = {");//entonces el valor de la variable valorDeRespuesta es---
			aWriter.addJS_cr("key: result,");
			aWriter.addJS_cr("label: '',");
			aWriter.addJS_cr("previewURL: undefined");
			aWriter.addJS_cr("};");
			aWriter.addJS_cr("}");
	        aWriter.addJS_cr("ebx_form_setValue('" + aWriter.getPrefixedPath(caminoRespuestas).format() + "', valorBanner);");
	        //aWriter.addJS_cr("ebx_form_setValue('" + aWriter.getPrefixedPath(caminoRiesgos).format() + "', null);");
	        aWriter.addJS_cr("}");

		
		
		
		aWriter.setCurrentObject(OBJECT_KEY);
		
		
		
        aWriter.addJS_cr("function displayBlock(buttonValue, blockId){");
        aWriter.addJS_cr("if (buttonValue == 'true'){");
        aWriter.addJS_cr("document.getElementById(blockId).style.display = 'block';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else {");
        aWriter.addJS_cr("document.getElementById(blockId).style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");
		
 
        
	}
	

	private void startComplexGroup(UserServicePaneContext aPaneContext,
            UserServicePaneWriter aWriter, Path path, String  blockId) {
		
		UIRadioButtonGroup button = aWriter.newRadioButtonGroup(path);
		button.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlock", blockId));
		aWriter.addFormRow(button);
		Boolean value = (Boolean) aPaneContext.getValueContext(OBJECT_KEY, path).getValue();
		String display;
		if(value != null && value) 
			display = "display:block";
		else 
			display = "display:none";
		aWriter.add("<div ");
		aWriter.addSafeAttribute("id", blockId);
		aWriter.addSafeAttribute("style", display);
		aWriter.add_cr(">");
	}
	
	private void endComplexGroup(UserServicePaneWriter aWriter) {
		
		aWriter.add_cr("</div>");
		
	}
	/*private void DropList(UserServicePaneContext aPaneContext,
            UserServicePaneWriter aWriter, Path path, String  dropId) {
		
		UIDropDownList button = aWriter.newDropDownList(path);
		button.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlock2", dropId));
		aWriter.addFormRow(button);
		String value = (String) aPaneContext.getValueContext(OBJECT_KEY, path).getValue();
		String display;
		if (value != null && !value.isEmpty()) 
			display = "display:block2";
		 else if (value != null && !value.isEmpty()) 
			display = "display:block3";
		 else 
			 display = "display:none";
		aWriter.add("<div ");
		aWriter.addSafeAttribute("id", dropId);
		aWriter.addSafeAttribute("style", display);
		aWriter.add_cr(">");
	}
	
	private void enddroplis(UserServicePaneWriter aWriter) {
		
		aWriter.add_cr("</div>");
		
	}*/
	@SuppressWarnings("unchecked")
	@Override
	public void setupObjectContext(UserServiceSetupObjectContext<T> aContext, UserServiceObjectContextBuilder aBuilder) {
	
		if(aContext.isInitialDisplay()) {
			
			repositorio=aContext.getRepository();
			
			dataset=aContext.getEntitySelection().getDataset();
			BeanDefinition def = aBuilder.createBeanDefinition();
			aBuilder.registerBean(RESPUESTAS_KEY, def);
			
			BeanElement riesgosElement= def.createElement(caminoRiesgos, SchemaTypeName.XS_STRING);
			riesgosElement.addFacetConstraint((Class<? extends Constraint<?>>) EnumerationConstraint.class);
			riesgosElement.setMinOccurs(1);
			riesgosElement.setMinOccursErrorMessage("Obligatorio");
			riesgosElement.setLabel("Riesgo");
			
			
			
			
			BeanElement elemento;
			elemento= def.createElement(caminoRespuestas, SchemaTypeName.XS_STRING);
			elemento.setLabel("Respuesta al riesgo");
			BeanFacetTableRef respuestasFacet =elemento.addFacetTableRef(dataset.getTable(Path.parse("/root/Catalogos/Matrices/Catalogo_de_Riesgos_de_Objetivos_de_Riesgo_conforme_objetivos/Riesgos_conforme_objetivos")));
			respuestasFacet.setDisplayPattern("${./Controles_genericos}");
			respuestasFacet.setFilter(CustomFilter.class);
			elemento.setMinOccurs(1);
			elemento.setMinOccursErrorMessage("OBLIGATORIO");
			TableEntitySelection selection = aContext.getEntitySelection();
			
			BeanElement prueba;
			prueba= def.createElement(Path.parse("prueba"), SchemaTypeName.XS_STRING);
			prueba.setLabel("prueba");
			
			
			
			if(selection instanceof RecordEntitySelection) {
				
				Adaptation record = ((RecordEntitySelection) selection).getRecord();
				//-- Modificar el record y context procedure le damos el valor de el nuevo campo
				//Idea crea un Bean aparte y a ese bean aparte  luego le metes el valor con un procedure usando el adaptation que obteines en la linea anterior a esta
				//1.- Se crea un bean definition para agregar nuevo campo manipulable droplist Agregado 20-03-2024
				
				//---
				
				
				if(aContext.getServiceKey().equals(ServiceKey.DUPLICATE))
					{
					  aBuilder.registerNewDuplicatedRecord(OBJECT_KEY, record);
					
					}
				else {
					aBuilder.registerRecordOrDataSet(OBJECT_KEY, record);
				}
				
			} else {
				
				aBuilder.registerNewRecord(OBJECT_KEY, selection.getTable());
				
			}
			
		}
		
	}

	@Override
	public void validate(UserServiceValidateContext<T> aContext) {

		
	}

        
	
	
	
    //Necesario para rellenar el comboBox de riesgos
	public static class EnumerationConstraint<T> implements ConstraintEnumeration<T> {

		@Override
		public void checkOccurrence(T arg0, ValueContextForValidation arg1) throws InvalidSchemaException {
			
		}

		@Override
		public void setup(ConstraintContext arg0) {
			
		}

		@Override
		public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {
			return null;
		}

		@Override
		public String displayOccurrence(T aValue, ValueContext aContext, Locale aLocale) throws InvalidSchemaException {
			//Checar esto convierte el ID a Nombre
			return  obtenerRiesgo((String) aValue);
			
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<T> getValues(ValueContext aContext) throws InvalidSchemaException {//Se cambio Riesgo por ID

			Query<Tuple> query = dataset.createQuery("SELECT  MIN(ID) AS ID, Riesgo  FROM \"/root/Catalogos/Matrices/Catalogo_de_Riesgos_de_Objetivos_de_Riesgo_conforme_objetivos/Riesgos_conforme_objetivos\" GROUP BY Riesgo"); 
			
			List<T> valuesList = new ArrayList<T>();
			
			try(QueryResult<Tuple> result=  query.getResult()){
				
				Iterator<Tuple> it = result.iterator();
				
				while(it.hasNext()) {
					
					valuesList.add((T) it.next().get(0, String.class));
				}
				
			}
			
			return valuesList;
		}
		
	}
	
	//3.- Clase para personalizar el filtrado
	public static class CustomFilter implements TableRefFilter {

		@Override
		public boolean accept(Adaptation anAdaptation, ValueContext aContext) {
			
			String idObtenido = (String) vc.getValue(caminoRiesgos);//OBTENER VALOR DEL ID DEL RIESGO
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\t\t\t¡¡¡¡¡¡¡¡¡ ******  idObtenido ******** !!!!!!!!!!!!!!!!!"+idObtenido+"\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			
			String banVal=obtenerRiesgo(idObtenido);
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\t\t\t¡¡¡¡¡¡¡¡¡ ******  banVal ******** !!!!!!!!!!!!!!!!!"+banVal+"\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			
			//String banVal="Abuso, falsificación o denegación de privilegios de acuerdo con los perfiles de usuarios";
			String tablaVal = anAdaptation.getString(Path.parse("/Riesgo"));
			
			if(banVal == null || tablaVal == null) { //Si el valor del campo riesgo y el valor de la tabla es null regresa falso
				
				return false;
				
			} else if(banVal.equals(tablaVal)) { //si el valor del campo riesgo coincide con el riesgo de la tabla entonces regresa truw
				
				return true;
				
			} else { //Si no pasa nada de eso regresa falso
				
				return false;
				
			}

		}

		@Override
		public void setup(TableRefFilterContext aContext) {
			
		}

		@Override
		public String toUserDocumentation(Locale aLocale, ValueContext aContext) throws InvalidSchemaException {

			return null;
		}
		
	}
	
	
	
	
	
	//25-03-2024
	public static String obtenerRiesgo(String idRiesgo)
	{
		
		AdaptationHome dataspace= getRepositorio().lookupHome(BranchKey.forBranchName("BHD"));
		AdaptationName llaveAdaptation =AdaptationName.forName("BHD");
		Adaptation dataset = dataspace.findAdaptationOrNull(llaveAdaptation);
		AdaptationTable tabla =dataset.getTable(Path.parse("/root/Catalogos/Matrices/Catalogo_de_Riesgos_de_Objetivos_de_Riesgo_conforme_objetivos/Riesgos_conforme_objetivos"));
		XPathFilter  filtro = XPathFilter.newFilter("./ID=$param1");
		String resultado="";
		Request request =tabla.createRequest();
		request.setXPathFilter(filtro);
		request.setXPathParameter("param1", idRiesgo);
		
		try (RequestResult resultadoRequest=request.execute())
		{
			for(Adaptation record; (record=resultadoRequest.nextAdaptation())!=null;)
            {
				 resultado=(String) record.get(Path.parse("/Riesgo"));
				
            }  

		}
		  
        return resultado;
		
		
	}
	
	public static String obtenerID(String Riesgo)
	{
		AdaptationHome dataspace= getRepositorio().lookupHome(BranchKey.forBranchName("BHD"));
		AdaptationName llaveAdaptation =AdaptationName.forName("BHD");
		Adaptation dataset = dataspace.findAdaptationOrNull(llaveAdaptation);
		AdaptationTable tabla =dataset.getTable(Path.parse("/root/Catalogos/Matrices/Catalogo_de_Riesgos_de_Objetivos_de_Riesgo_conforme_objetivos/Riesgos_conforme_objetivos"));
		XPathFilter  filtro = XPathFilter.newFilter("./Riesgo=$param1");
		String resultado="";
		Request request =tabla.createRequest();
		request.setXPathFilter(filtro);
		request.setXPathParameter("param1", Riesgo);
		
		try (RequestResult resultadoRequest=request.execute())
		{
			for(Adaptation record; (record=resultadoRequest.nextAdaptation())!=null;)
            {
				 resultado=(String) record.get(Path.parse("/Riesgo"));
				
            }  

		}
		  
        return resultado;
	}
	
	
	public void setRepositorio(Repository unRepositorio)
	{
		repositorio=unRepositorio;
	}
	
	public static Repository getRepositorio()
	{
		return repositorio;
	}
	
}
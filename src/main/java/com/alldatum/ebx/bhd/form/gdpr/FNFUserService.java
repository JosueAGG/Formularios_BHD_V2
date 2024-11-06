package com.alldatum.ebx.bhd.form.gdpr;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.alldatum.ebx.bhd.util.AdaptationUtil;
import com.alldatum.ebx.bhd.widget.UILinkableListWidget;
import com.alldatum.ebx.bhd.widget.UILinkableListWidgetFactory;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.interactions.InteractionHelper.ParametersMap;
import com.orchestranetworks.interactions.SessionInteraction;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
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
import com.orchestranetworks.ui.UIButtonSpecSubmit;
import com.orchestranetworks.ui.base.JsFunctionCall;
import com.orchestranetworks.ui.form.widget.UIComboBox;
import com.orchestranetworks.ui.form.widget.UIDropDownList;
import com.orchestranetworks.ui.form.widget.UIRadioButtonGroup;
import com.orchestranetworks.ui.selection.RecordEntitySelection;
import com.orchestranetworks.ui.selection.TableEntitySelection;
import com.orchestranetworks.userservice.ObjectKey;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.userservice.UserServiceAjaxContext;
import com.orchestranetworks.userservice.UserServiceAjaxResponse;
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
import com.orchestranetworks.userservice.UserServiceWriter;

public class FNFUserService <T extends TableEntitySelection> implements UserService<T> {

	private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");
	private static final ObjectKey RESPUESTAS_KEY = ObjectKey.forName("respuestas");	  
	private static final Path Riesgo = Path.parse("Medidas_garantias_privacidad_seguridad_adoptadas_funcion_riesgos");	  
	private static final Path Rriesgo = Path.parse("Controles");
	private static final Path RESPUESTA_RIESGO_FOCUS = Path.parse("resriesgofocus");
	//heredar datos
	private static final Path NOMBRE_TRATAMIENTO_PATH = Path.parse("NombreT");
	private static final Path ID_FAB_PATH = Path.parse("IdentificadorFAB");
	private static final Path PROCESOS_OPERATIVOS_PATH = Path.parse("Procesos_operativos_o_comerciales");
	private static final Path CODIGO_PROCESO_PATH = Path.parse("Codigo_proceso");
	
	
	//Datos de revisor metodológico
	//Aceptable
	private static final Path MENU_ACEPTABLE_PATH = Path.parse("MAceptable");
	private static final Path CONDICION_ACEPTABLE_PATH = Path.parse("CondisidAcep");
	private static final Path NOMBRE_REVISORA_PATH = Path.parse("NombreDRevisorAFNF");
	private static final Path ROL_CARGOA_PATH = Path.parse("RolCargoFAF");
	
	//Inaceptable
	private static final Path MENU_INACEPTABLE_PATH = Path.parse("Meniinacep");
	private static final Path CONDICION_INACEPTABLE_PATH = Path.parse("Concideaina");
	private static final Path NOMBRE_REVISORI_PATH = Path.parse("NombrerevisorinacFNF");
	private static final Path ROL_CARGOI_PATH = Path.parse("RolCargoinaFNF");

	
	private String idFABPrefix;
	private String procesosPrefix;
	private String codigoPrefix;

	private String MenuAPrefix;
	private String CondicionAPrefix;
	private String NombreAPrefix;
	private String RolCAPrefix;
	
	private String MenuIPrefix;
	private String CondicionIPrefix;
	private String NombreIPrefix;
	private String RolCIPrefix;
	
	private static Adaptation dataset;


	@Override
	public UserServiceEventOutcome processEventOutcome(UserServiceProcessEventOutcomeContext<T> aContext,
			UserServiceEventOutcome anOutcome) {
		
		return anOutcome;
	}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<T> aContext, UserServiceDisplayConfigurator aConfigurator) {
		
	aConfigurator.setContent(this::pane);
		
		UIButtonSpecSubmit saveButton = aConfigurator.newSaveButton(this::save);
		saveButton.setId("saveButtonId");
		
		aConfigurator.setLeftButtons(saveButton, aConfigurator.newCloseButton());
		
	}
	
	@SuppressWarnings("unchecked")
	private UserServiceEventOutcome save(UserServiceEventContext aContext) {
		List<String> riesgoList = (List<String>) aContext.getValueContext(RESPUESTAS_KEY, Riesgo).getValue();
		List<String> rriesgoList = (List<String>) aContext.getValueContext(RESPUESTAS_KEY, Rriesgo).getValue();
		aContext.getValueContext(OBJECT_KEY, Riesgo).setNewValue(riesgoList);	
		aContext.getValueContext(OBJECT_KEY, Rriesgo).setNewValue(rriesgoList);
		
		if(!aContext.save(OBJECT_KEY).hasFailed()) {
			
			if(aContext.getSession().isInWorkflowInteraction(false)) {
				
				ServiceKey sk = aContext.getServiceKey();
				if(sk.equals(ServiceKey.CREATE) || sk.equals(ServiceKey.DUPLICATE)) {
					
					Adaptation record = AdaptationUtil.getRecordForValueContext(aContext.getValueContext(OBJECT_KEY));
					String recordString = record.toXPathExpression();
					
					SessionInteraction si = aContext.getSession().getInteraction(false);
					ParametersMap internalMap = new ParametersMap();			
					internalMap.setVariableString("created", recordString);
					si.complete(internalMap);		
					
				}		
			} 
		}
		return null;
	}
	
	private void pane(UserServicePaneContext aPaneContext, UserServicePaneWriter aWriter) {
		
		aWriter.setCurrentObject(RESPUESTAS_KEY);
		String rriesgofocusPrefix = aWriter.getPrefixedPath(RESPUESTA_RIESGO_FOCUS).format();

		UILinkableListWidget riesgosBox = (UILinkableListWidget) aWriter.newCustomWidget(Riesgo, new UILinkableListWidgetFactory());
		riesgosBox.setId("riesgosWidget");

		UILinkableListWidget resultadosBox = (UILinkableListWidget) aWriter.newCustomWidget(Rriesgo, new UILinkableListWidgetFactory());
		resultadosBox.setId("resultadosWidget");
		resultadosBox.setPathForSelectedNode(Path.parse("../resriesgofocus"));
		
		riesgosBox.linkWidget(resultadosBox);
		
		aWriter.add("<div ");
		aWriter.addSafeAttribute("style", "display:none;");
		aWriter.add_cr(">");
		aWriter.addFormRow(RESPUESTA_RIESGO_FOCUS);
		aWriter.add_cr("</div>");
		
		aWriter.setCurrentObject(OBJECT_KEY);
		
		
		MenuAPrefix = aWriter.getPrefixedPath(MENU_ACEPTABLE_PATH).format();
		CondicionAPrefix = aWriter.getPrefixedPath(CONDICION_ACEPTABLE_PATH).format();
		NombreAPrefix = aWriter.getPrefixedPath(NOMBRE_REVISORA_PATH).format();
		RolCAPrefix = aWriter.getPrefixedPath(ROL_CARGOA_PATH).format();
		 
		MenuIPrefix= aWriter.getPrefixedPath(MENU_INACEPTABLE_PATH).format();
		CondicionIPrefix = aWriter.getPrefixedPath(CONDICION_INACEPTABLE_PATH).format();
		NombreIPrefix = aWriter.getPrefixedPath(NOMBRE_REVISORI_PATH).format();
		RolCIPrefix = aWriter.getPrefixedPath(ROL_CARGOI_PATH).format();
		
		//Hereda datos
		idFABPrefix = aWriter.getPrefixedPath(ID_FAB_PATH).format();
		procesosPrefix = aWriter.getPrefixedPath(PROCESOS_OPERATIVOS_PATH).format();
		codigoPrefix = aWriter.getPrefixedPath(CODIGO_PROCESO_PATH).format();	
		
		aWriter.add_cr("<h3>Datos básicos del tratamiento nivel fases</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Identificador"));
		aWriter.addFormRow(Path.parse("Fecha_de_creacion_del_tratamiento"));
		UIComboBox inhTipImp = aWriter.newComboBox(NOMBRE_TRATAMIENTO_PATH);
		inhTipImp.setActionOnAfterValueChanged(JsFunctionCall.on("updateInheritedFields"));	
		aWriter.addFormRow(inhTipImp);	
		aWriter.addFormRow(ID_FAB_PATH);
		aWriter.addFormRow(PROCESOS_OPERATIVOS_PATH);
		aWriter.addFormRow(CODIGO_PROCESO_PATH);
		aWriter.endExpandCollapseBlock();		
		//----------------------------------------
		aWriter.add_cr("<h3>Tipo de la fase del tratamiento</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Tipo_fase_tratamiento"));
		aWriter.addFormRow(Path.parse("Descripcion_fase_tratamiento"));		
		aWriter.addFormRow(Path.parse("Fase_anterior_tratamiento"));
		aWriter.addFormRow(Path.parse("Fase_posterior_tratamiento"));		
		aWriter.endExpandCollapseBlock();	
		//----------------------------------------
		aWriter.add_cr("<h3>Características relevantes de implementación</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Caracteristicas_relevantes_implementacion"));
		aWriter.endExpandCollapseBlock();
		//----------------------------------------
		aWriter.add_cr("<h3>Datos tratados</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Datos_tratados"));
		aWriter.endExpandCollapseBlock();
		//----------------------------------------
		aWriter.add_cr("<h3>Origen de los datos - Datos inferidos o generados</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("OrigendelosdatosInternos"), "block_origendelosdatosInternos");
		aWriter.addFormRow(Path.parse("origendatosinternosq"));
		endBlock(aWriter);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("OriegndelosdatosExternos"), "block_oriegndelosdatosExternos");
		aWriter.addFormRow(Path.parse("Origendatosexternos"));
		endBlock(aWriter);	
		aWriter.addFormRow(Path.parse("Datos_inferidos_o_generados"));	
		aWriter.endExpandCollapseBlock();
		//----------------------------------------	
		
		aWriter.add_cr("<h3>Destino de los datos</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("DestinodelosdatosInterno"), "block_destinodelosdatosInterno");				
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Interior_Banco/Procesos_operativos_comerciales"), "block_interior_banco_procesos_operativos_comerciales");
			aWriter.addFormRow(Path.parse("./Interior_Banco/Catalogodeprocesosoperativosocomerciales"));
			endBlock(aWriter);
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Interior_Banco/Contratacion__producto_servicio"), "block_interior_banco_Contratacion__producto_servicio");
			aWriter.addFormRow(Path.parse("./Interior_Banco/CatalogoproductosserviciosBanco"));
			endBlock(aWriter);			
		endBlock(aWriter);	
		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("DestinodelosdatosExterior"), "block_destinodelosdatosExterior");
			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Paratratamientodeencargadosdecicion"), "block_exterior_banco_paratratamientodeencargadosdecicion");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_encargados/Servicio_contratado_encargado"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_encargados/Destinatario_encargado"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_encargados/Operaciones_que_ejecuta_encargado"));
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Para_tratamiento_encargados/Contrato_encargado"), "block_exterior_banco_Para_tratamiento_encargados_contrato_encargado");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_encargados/Nombre_contrato_encargado"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_encargados/Fecha_de_formalizacion_encargado"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_encargados/Periodo_vigencia_encargado"));
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Para_tratamiento_encargados/Vigente_encargado"), "block_exterior_banco_para_tratamiento_encargados_vigente_encargado");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_encargados/Adjuntar_contrato_encargado"));
			endBlock(aWriter);
			endBlock(aWriter);
			endBlock(aWriter);
			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Paratratamientodesubencargadosdecision"), "block_exterior_banco_paratratamientodesubencargadosdecision");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Servicio_contratado_subencargado"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Destinatario_subencargado"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Operaciones_que_ejecuta_subencargado"));
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Contrato_subencargado"), "block_exterior_banco_para_tratamiento_subencargados_contrato_subencargado");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Nombre_contrato_subencargado"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Fecha_de_formalizacion_subencargado"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Periodo_vigencia_subencargado"));
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Vigente_subencargado"), "block_exterior_banco_para_tratamiento_subencargados_vigente_subencargado");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Adjuntar_contrato_subencargado"));
			endBlock(aWriter);
			endBlock(aWriter);
			endBlock(aWriter);
			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Paratransferiratercerosdecisin"), "block_exterior_banco_paratransferiratercerosdecisin");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_transferir_terceros/Destinatario_trasferencia_terceros"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_transferir_terceros/Pais_trasferencia_terceros"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_transferir_terceros/Finalidades_de_la_transferencia_trasferencia_terceros"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_transferir_terceros/Causa_de_legitimacion_trasferencia_terceros"));
			endBlock(aWriter);
			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Paracederdatospersonalesdecision"), "block_exterior_banco_paracederdatospersonalesdecision");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_ceder_datos_personales/Destinatario_ceder_datos_personales"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_ceder_datos_personales/Causa_de_legitimacion_cer_datos"));
			endBlock(aWriter);
				
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
			
		
		//-----------------------------------------
		aWriter.add_cr("<h3>Intervinientes externos</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Nombre_persona_fisica_o_juridica"));
		aWriter.addFormRow(Path.parse("Rol"));
		aWriter.addFormRow(Path.parse("Funciones"));
		aWriter.endExpandCollapseBlock();
		
		//-----------------------------------------
		aWriter.add_cr("<h3>Incidentes</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Incidentes conocidos de fases similares ya implementadas"), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Incidentes-conocidos"), "block_incidentes_conocidos");		
		aWriter.addFormRow(Path.parse("Incidentes_conocidos"));
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Amenazas conocidas"), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Amenazas_conocidas"), "block_amenazas_conocidas");		
		aWriter.addFormRow(Path.parse("Tipo_de_amenaza"));
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Medidas y garantías de privacidad y seguridad por defecto"), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Medidas_garantias_privacidad_seguridad_defecto"), "block_medidas_garantias_privacidad_seguridad_defecto");		
		aWriter.addFormRow(Path.parse("./Campo_Medidas_garantias_privacidad/Campo_Medidas_garantias_privacidad"));
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Medidas y garantías de privacidad y seguridad adoptadas en función de los riesgos"), true);	
		aWriter.setCurrentObject(RESPUESTAS_KEY);
		aWriter.addFormRow(riesgosBox);
		
		aWriter.setCurrentObject(OBJECT_KEY);
		//aWriter.addFormRow(Path.parse("Medidas_garantias_privacidad_seguridad_adoptadas_funcion_riesgos"));
		aWriter.setCurrentObject(RESPUESTAS_KEY);
		aWriter.addFormRow(resultadosBox);		
		aWriter.setCurrentObject(OBJECT_KEY);
		//aWriter.addFormRow(Path.parse("Controles"));
		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Vinculacion_ficha_gestion_incidentes_privacidadB"), "block_Vinculacion_ficha_gestion_incidentes_privacidadB");		
		aWriter.addFormRow(Path.parse("Vinculacion_ficha_gestion_incidentes_privacidad"));		
		endBlock(aWriter);	
		aWriter.endExpandCollapseBlock();


		
		//---------------------------------------	
		aWriter.add_cr("<h3>Revisión metodológica</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		
		Path revPath = Path.parse("Revicion_Metodologica");
		UIDropDownList revDropDown = aWriter.newDropDownList(revPath);
		revDropDown.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision"));
		aWriter.addFormRow(revDropDown);
		String value = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath).getValue();
		
		startBlock(aWriter, "block_revision_aceptable", "aceptable".equalsIgnoreCase(value));	
		aWriter.addFormRow(MENU_ACEPTABLE_PATH);
		aWriter.addFormRow(CONDICION_ACEPTABLE_PATH);
		aWriter.addFormRow(NOMBRE_REVISORA_PATH);
		aWriter.addFormRow(ROL_CARGOA_PATH);
		endBlock(aWriter);
		
		startBlock(aWriter, "block_revision_inaceptable", "inaceptable".equalsIgnoreCase(value));
		aWriter.addFormRow(MENU_INACEPTABLE_PATH);
		aWriter.addFormRow(CONDICION_INACEPTABLE_PATH);
		aWriter.addFormRow(NOMBRE_REVISORI_PATH);
		aWriter.addFormRow(ROL_CARGOI_PATH);
		endBlock(aWriter);
		
		aWriter.endExpandCollapseBlock();	

		
		//---------------------------------------
		
		
		
		
		//Funcion que utiliza el campo riesgo y como respuesta al riesgo
		aWriter.setCurrentObject(RESPUESTAS_KEY);
		aWriter.addJS_cr("function resetBanner(){");
        aWriter.addJS_cr("var idBanner = ebx_form_getValue('" + aWriter.getPrefixedPath(Riesgo).format() + "');");
        aWriter.addJS_cr("var valorBanner = null;");
        aWriter.addJS_cr("if(idBanner != null){");
        aWriter.addJS_cr("var valorBanner = {");
		aWriter.addJS_cr("key: idBanner,");
		aWriter.addJS_cr("label: '',");
		aWriter.addJS_cr("previewURL: undefined");
		aWriter.addJS_cr("};");
		aWriter.addJS_cr("}");
        aWriter.addJS_cr("ebx_form_setValue('" + aWriter.getPrefixedPath(Rriesgo).format() + "', valorBanner);");       
        aWriter.addJS_cr("}");	
	    aWriter.setCurrentObject(OBJECT_KEY);
		
	  //Funcion que ocupan los campo boolean para ocultar y mostrar 
        aWriter.addJS_cr("function displayBlock(buttonValue, blockId){");
        aWriter.addJS_cr("if (buttonValue == 'true'){");
        aWriter.addJS_cr("document.getElementById(blockId).style.display = 'block';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else {");
        aWriter.addJS_cr("document.getElementById(blockId).style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");
		
        // Funcion que ocupan los campos String que tiene valores definidos y dependindo la selecion muestra los campos (Revisión metodológica)

		aWriter.addJS_cr("function displayBlockRevision(buttonValue) {");
		aWriter.addJS_cr("  const blockAceptable = document.getElementById('block_revision_aceptable');");
		aWriter.addJS_cr("  const blockInaceptable = document.getElementById('block_revision_inaceptable');");
		aWriter.addJS_cr("  if (buttonValue === 'Aceptable') {");
		aWriter.addJS_cr("    resetValuesListR('" + MenuIPrefix + "');");
		aWriter.addJS_cr("    resetValuesListR('" + CondicionIPrefix + "');");
		aWriter.addJS_cr("    resetFieldValueR('" + NombreIPrefix + "');");
		aWriter.addJS_cr("    resetFieldValueR('" + RolCIPrefix + "');");
		aWriter.addJS_cr("    blockAceptable.style.display = 'block';");
		aWriter.addJS_cr("    blockInaceptable.style.display = 'none';");
		aWriter.addJS_cr("  } else if (buttonValue === 'Inaceptable') {");
		aWriter.addJS_cr("    resetValuesListR('" + NombreAPrefix + "');");
		aWriter.addJS_cr("    resetValuesListR('" + CondicionIPrefix + "');");
		aWriter.addJS_cr("    resetFieldValueR('" + NombreIPrefix + "');");
		aWriter.addJS_cr("    resetFieldValueR('" + RolCIPrefix + "');");
		aWriter.addJS_cr("    blockAceptable.style.display = 'none';");
		aWriter.addJS_cr("    blockInaceptable.style.display = 'block';");
		aWriter.addJS_cr("  } else {");
		aWriter.addJS_cr("    resetValuesListR('" + MenuAPrefix + "');");
		aWriter.addJS_cr("    resetValuesListR('" + CondicionAPrefix + "');");
		aWriter.addJS_cr("    resetFieldValueR('" + NombreAPrefix + "');");
		aWriter.addJS_cr("    resetFieldValueR('" + RolCAPrefix + "');");
		aWriter.addJS_cr("    blockAceptable.style.display = 'none';");
		aWriter.addJS_cr("    blockInaceptable.style.display = 'none';");
		aWriter.addJS_cr("  }");
		aWriter.addJS_cr("}");

		aWriter.addJS_cr("function resetValuesListR(path) {");
		aWriter.addJS_cr("  let i = 0;");
		aWriter.addJS_cr("  while (ebx_form_isNodeDefined(path + '[' + i + ']')) {");
		aWriter.addJS_cr("    ebx_form_setValue(path + '[' + i + ']', null);");
		aWriter.addJS_cr("    i++;");
		aWriter.addJS_cr("  }");
		aWriter.addJS_cr("}");

		aWriter.addJS_cr("function resetFieldValueR(path) {");
		aWriter.addJS_cr("  ebx_form_setValue(path, null);");
		aWriter.addJS_cr("}");
        
        aWriter.addJS_cr("document.getElementById('saveButtonId').addEventListener('mouseover', function(){");   
        aWriter.addJS_cr("ebx_form_setValue('" + rriesgofocusPrefix + "', null);"); 
        aWriter.addJS_cr("});");
        
		//Funcion para heredar datos
        aWriter.addJS_cr("AjaxHandler = function (){");
        aWriter.addJS_cr("this.handleAjaxResponseSuccess = function(responseContent){");
        aWriter.addJS_cr("};");
        aWriter.addJS_cr("this.handleAjaxResponseFailed = function(responseContent){");
        aWriter.addJS_cr("};");
        aWriter.addJS_cr("};");
        
        aWriter.addJS_cr("AjaxHandler.prototype = new EBX_AJAXResponseHandler();");

        aWriter.addJS_cr("function callAjaxComponent(value){");
        aWriter.addJS_cr("var handler = new AjaxHandler();");
        aWriter.addJS_cr("var requestParameters = '&fieldValue=' + value;");
        aWriter.addJS_cr("var request = '" + aWriter.getURLForAjaxRequest(this::ajaxCallback) + "' + requestParameters;"); 
        aWriter.addJS_cr("handler.sendRequest(request);");
        aWriter.addJS_cr("}");

        aWriter.addJS_cr("function updateInheritedFields(value){");
        aWriter.addJS_cr("if(value == null){");
        aWriter.addJS_cr("callAjaxComponent('');");
        aWriter.addJS_cr("}else{");
        aWriter.addJS_cr("callAjaxComponent(value.key);");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");
        
	}
	

	private void startBlock(UserServicePaneWriter aWriter, String blockId, Boolean isDisplayed) {
		
		String display = isDisplayed ? "display:block" : "display:none";
		
		aWriter.add("<div ");
		aWriter.addSafeAttribute("id", blockId);
		aWriter.addSafeAttribute("style", display);
		aWriter.add_cr(">");
		
	}

	private void startBooleanBlock(UserServicePaneContext aPaneContext,UserServicePaneWriter aWriter, Path path, String  blockId) {
		
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
	
	private void endBlock(UserServicePaneWriter aWriter) {
		
		aWriter.add_cr("</div>");
		
	}


	@Override
	public void setupObjectContext(UserServiceSetupObjectContext<T> aContext, UserServiceObjectContextBuilder aBuilder) {
		if(aContext.isInitialDisplay()) {
			
			dataset = aContext.getEntitySelection().getDataset();
			BeanDefinition def = aBuilder.createBeanDefinition();
			aBuilder.registerBean(RESPUESTAS_KEY, def);			
			
			BeanElement riesgosElement= def.createElement(Riesgo, SchemaTypeName.XS_STRING);
			riesgosElement.addFacetConstraint(EnumerationConstraint.class);
			riesgosElement.setMinOccurs(0);
			riesgosElement.setMinOccursErrorMessage("");
			riesgosElement.setLabel("Riesgo");
			riesgosElement.setMaxOccursAsUnbounded();		
			
			BeanElement resriesgo= def.createElement(Rriesgo, SchemaTypeName.XS_STRING);
			resriesgo.setLabel("Respuesta al riesgo");			
			BeanFacetTableRef respuestasFacet =resriesgo.addFacetTableRef(dataset.getTable(Path.parse("/root/Catalogos/Matrices/Catalogo_de_Riesgos_de_Objetivos_de_Riesgo_conforme_objetivos/Riesgos_conforme_objetivos")));
			respuestasFacet.setDisplayPattern("${./Controles_genericos}");
			respuestasFacet.setFilter(CustomFilter.class);
			resriesgo.setMinOccurs(0);
			resriesgo.setMinOccursErrorMessage("");
			resriesgo.setMaxOccursAsUnbounded();			
			
			def.createElement(RESPUESTA_RIESGO_FOCUS, SchemaTypeName.XS_INTEGER);	
			
			TableEntitySelection selection = aContext.getEntitySelection();			
			
			if(selection instanceof RecordEntitySelection) {	
				
				Adaptation record = ((RecordEntitySelection) selection).getRecord();	
				
				riesgosElement.setDefaultValue(record.getValueWithoutResolution(Riesgo));
				resriesgo.setDefaultValue(record.getValueWithoutResolution(Rriesgo));	
				
				if(aContext.getServiceKey().equals(ServiceKey.DUPLICATE))
					
					aBuilder.registerNewDuplicatedRecord(OBJECT_KEY, record);
				else
					aBuilder.registerRecordOrDataSet(OBJECT_KEY, record);				
			} else {				
				aBuilder.registerNewRecord(OBJECT_KEY, selection.getTable());				
			}			
		}
		
	}
	
    private void ajaxCallback(UserServiceAjaxContext anAjaxContext, UserServiceAjaxResponse anAjaxResponse){
    	
    	Adaptation dataset = anAjaxContext.getValueContext(OBJECT_KEY).getAdaptationInstance();
    	AdaptationTable table = dataset.getTable(Path.parse("/root/Formularios/FABAN"));
    	
    	String identificador = null;
    	String proceso = null;
    	String codigo = null;
    	
    	String value = anAjaxContext.getParameter("fieldValue");
    	System.out.println(value);
    	
    	if(value != null && !value.isBlank()) {

	    	Adaptation record = table.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(value));
	    	
	    	identificador = record.getString(Path.parse("./Identificador"));
	    	proceso = record.getString(Path.parse("./Proceso_operativo"));
	    	codigo = record.getString(Path.parse("./Codigo_proceso"));
    	
    	}
    	
    	UserServiceWriter aWriter = anAjaxResponse.getWriter();
    	
    	identificador = identificador == null ? "null" : "'" + identificador + "'";
    	codigo = codigo == null ? "null" : "'" + codigo + "'";
    	
    	if(proceso == null) {
    		
    		aWriter.addJS_cr("proceso = null;");
    		
    	} else {
    	
			aWriter.addJS_cr("proceso = {");
			aWriter.addJS_cr("key: '" + proceso + "',");
			aWriter.addJS_cr("label: '',");
			aWriter.addJS_cr("previewURL: undefined");
			aWriter.addJS_cr("};");
		
    	}
		
		aWriter.addJS_cr("ebx_form_setValue('" + idFABPrefix + "', " +  identificador + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + codigoPrefix + "', " +  codigo + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + procesosPrefix + "', proceso)");

    	
    	
    }

	@Override
	public void validate(UserServiceValidateContext<T> arg0) {
		// TODO Auto-generated method stub
		
	}
	public static class EnumerationConstraint implements ConstraintEnumeration<String> {

		@Override
		public void checkOccurrence(String arg0, ValueContextForValidation arg1) throws InvalidSchemaException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setup(ConstraintContext arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String displayOccurrence(String arg0, ValueContext aContext, Locale arg2) throws InvalidSchemaException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getValues(ValueContext aContext) throws InvalidSchemaException {
			
			
			Query<Tuple> query = dataset.createQuery("SELECT DISTINCT Riesgo FROM \"/root/Catalogos/Matrices/Catalogo_de_Riesgos_de_Objetivos_de_Riesgo_conforme_objetivos/Riesgos_conforme_objetivos\""); 
			
			List<String> valuesList = new ArrayList<>();
			
			try(QueryResult<Tuple> result=  query.getResult()){
				
				Iterator<Tuple> it = result.iterator();
				
				while(it.hasNext()) {
					
					valuesList.add(it.next().get(0, String.class));
						
				}
				
			}
			
			return valuesList;
		}
		
	}
	
	public static class CustomFilter implements TableRefFilter {

		@SuppressWarnings("unchecked")
		@Override
		public boolean accept(Adaptation anAdaptation, ValueContext aContext) {

			Integer index = (Integer) aContext.getValue(Path.parse("../resriesgofocus"));
			List<String> riesgoList = (List<String>) aContext.getValue(Path.parse("../Medidas_garantias_privacidad_seguridad_adoptadas_funcion_riesgos"));
			String riesgo = null;
				
			if(index == null)
				return true;
			
			if(index < riesgoList.size()) riesgo = riesgoList.get(index);
			
			String tablaVal = anAdaptation.getString(Path.parse("./Riesgo"));			
			if(riesgo == null || tablaVal == null) { 
				
				return false;
				
			} else if(riesgo.equals(tablaVal)) { 
				
				return true;
				
			} else { 
				
				return false;
				
			}
		}

		@Override
		public void setup(TableRefFilterContext aContext) {
			// TODO Auto-generated method stub

			
		}

		@Override
		public String toUserDocumentation(Locale aContext, ValueContext arg1) throws InvalidSchemaException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}

package com.alldatum.ebx.bhd.form.gdpr;


import java.util.ArrayList;
import java.util.List;

import com.alldatum.ebx.bhd.util.AdaptationUtil;
import com.alldatum.ebx.bhd.widget.UIDynamicListWidget;
import com.alldatum.ebx.bhd.widget.UIDynamicListWidgetFactory;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.interactions.InteractionHelper.ParametersMap;
import com.orchestranetworks.instance.ValueContextForInputValidation;
import com.orchestranetworks.interactions.SessionInteraction;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaNode;
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

public class FAnalisis_ImpactoDPIAUserServices<T extends TableEntitySelection> implements UserService<T> {
	
	private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");	
	private static final Path EVA_IMPAC_PATH = Path.parse("Evaluacion_Impacto_DPIA");
	 
	//heredar datos
	private static final Path NOMBRE_TRATAMIENTO_PATH = Path.parse("Datos_basico_tratamiento/Nombre");
	private static final Path ID_FAB_PATH = Path.parse("Datos_basico_tratamiento/IdentificadorFABOA");
	private static final Path RESPONSABLE_PATH =Path.parse("Datos_basico_tratamiento/Responsable");
	private static final Path ROLR_PATH = Path.parse("Datos_basico_tratamiento/Rol_o_CargoR");
	private static final Path CORRESPONSABLE_PATH = Path.parse("Datos_basico_tratamiento/Correspondable");
	private static final Path ROLC_PATH = Path.parse("Datos_basico_tratamiento/Rol_cargocor");
	private static final Path PROCESOS_OPERATIVOS_PATH = Path.parse("Datos_basico_tratamiento/Procesos_operativos");
	
	//Datos de revisor metodológico
		//Aceptable
	
	private static final Path MENU_ACEPTABLE_PATH = Path.parse("./Revision_metodologica/MenuACepAI");
	private static final Path CONDICION_ACEPTABLE_PATH = Path.parse("./Revision_metodologica/ConsiadAI");
	private static final Path NOMBRE_REVISORA_PATH = Path.parse("./Revision_metodologica/NombrereviorPAIDPIA");
	private static final Path ROL_CARGOA_PATH = Path.parse("./Revision_metodologica/RolcargoPAI");
	

	//iInaceptable
	private static final Path MENU_INACEPTABLE_PATH = Path.parse("./Revision_metodologica/MeniinaAI");
	private static final Path CONDICION_INACEPTABLE_PATH = Path.parse("./Revision_metodologica/ConciinaAI");
	private static final Path NOMBRE_REVISORI_PATH = Path.parse("./Revision_metodologica/NombrerevisorPAIina");
	private static final Path ROL_CARGOI_PATH = Path.parse("./Revision_metodologica/RolocargoPAIna");
	
	
	private static final Path PRIMERO_PATH = Path.parse("Evaluacion_Impacto_DPIA/ObjetivosAIP");
	private static final Path SEGUNDO_PATH = Path.parse("Evaluacion_Impacto_DPIA/RiesgoARE");
	private static final Path TERCERO_PATH = Path.parse("Evaluacion_Impacto_DPIA/Control_instalado_DPIA");
	private static final Path PRIMERO_AUX_PATH = Path.parse("Auxiliar");
	private static final Path SEGUNDO_AUX_PATH = Path.parse("Auxiliar2");
	private static final Path TERCERO_AUX_PATH = Path.parse("Auxiliar3");
	
	private static final String RESPONSABLE_ID = "responsableList";
	private static final String ROL_RESP_ID = "rolResponsableList";
	private static final String CORRESPONSABLE_ID = "corresponsableList";
	private static final String ROL_CORRESP_ID = "rolCorresponsableList";

	private String idFABPrefix;
	private String responsablePrefix;
	private String rolrPrefix;
	private String corresponsablePrefix;
	private String rolcPrefix;
	private String procesosPrefix;
		
	
	//prefix aceptable
    String MAceptablePrefix;
    String CAceptablePrefix;
    String NRvisorAPrefix;
    String RCRevisorAPrefix;
  //prefic inaceptable
    String MInaceptablePrefix;
    String CInaceptablePrefix;
    String NRvisorIPrefix ;
    String RCRevisorIPrefix;
	
	
	
	
	
	@Override
	public UserServiceEventOutcome processEventOutcome(UserServiceProcessEventOutcomeContext<T> aContext,
			UserServiceEventOutcome anOutcome) {

		return anOutcome;
	}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<T> aContext, UserServiceDisplayConfigurator aConfigurator) {

		if(aContext.isInitialDisplay()) {
			if(aContext.getServiceKey().equals(ServiceKey.CREATE)){
				
				List<String> list1 = new ArrayList<>();
				List<String> list2 = new ArrayList<>();
				List<String> list3 = new ArrayList<>();
				for(int i = 0; i < 7; i++) {
					list1.add("-1");
					list2.add("-1");
					list3.add("-1");
				}
				aContext.getValueContext(OBJECT_KEY, PRIMERO_AUX_PATH).setNewValue(list1);
				aContext.getValueContext(OBJECT_KEY, SEGUNDO_AUX_PATH).setNewValue(list2);
				aContext.getValueContext(OBJECT_KEY, TERCERO_AUX_PATH).setNewValue(list3);
				
			}	
		}	
		
		aConfigurator.setContent(this::pane);
		
		UIButtonSpecSubmit saveButton = aConfigurator.newSaveButton(this::save);
		saveButton.setId("saveButtonId");
		
		aConfigurator.setLeftButtons(saveButton, aConfigurator.newCloseButton());
		
	}
	
	@SuppressWarnings("unchecked")
	private UserServiceEventOutcome save(UserServiceEventContext aContext) {

		
		ValueContextForInputValidation vc = aContext.getValueContext(OBJECT_KEY);
		SchemaNode node1 = vc.getNode(PRIMERO_PATH);
		SchemaNode node2 = vc.getNode(SEGUNDO_PATH);
		SchemaNode node3 = vc.getNode(TERCERO_PATH);
		List<Object> values = (List<Object>) vc.getValue(EVA_IMPAC_PATH);
		List<String> list1 = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		List<String> list3 = new ArrayList<>();
		for(Object value: values) {
	///////////////////////////////////////////////////////////////		
			String ref1 = (String) node1.executeRead(value);
			if(ref1 == null || ref1.isBlank())
				list1.add("-1");
			else
				list1.add(ref1);
	//////////////////////////////////////////////////////////////////		
			String ref2 = (String) node2.executeRead(value);
			if(ref2 == null || ref2.isBlank())
				list2.add("-1");
			else
				list2.add(ref2);
	//////////////////////////////////////////////////////////////////////		
			String ref3 = (String) node3.executeRead(value);
			if(ref3 == null || ref3.isBlank())
				list3.add("-1");
			else
				list3.add(ref3);
		
		}
		
		for(int i = 0; i < 7; i++) {
			list1.add("-1");
			list2.add("-1");
			list3.add("-1");
		}
		aContext.getValueContext(OBJECT_KEY, PRIMERO_AUX_PATH).setNewValue(list1);
		aContext.getValueContext(OBJECT_KEY, SEGUNDO_AUX_PATH).setNewValue(list2);
		aContext.getValueContext(OBJECT_KEY, TERCERO_AUX_PATH).setNewValue(list3);
		
		
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
		
		
		aWriter.setCurrentObject(OBJECT_KEY);
	    // Prefijos para Aceptable
		   MAceptablePrefix = aWriter.getPrefixedPath(MENU_ACEPTABLE_PATH).format();		    
		   CAceptablePrefix = aWriter.getPrefixedPath(CONDICION_ACEPTABLE_PATH).format();		    
		   NRvisorAPrefix = aWriter.getPrefixedPath(NOMBRE_REVISORA_PATH).format();		    
		   RCRevisorAPrefix = aWriter.getPrefixedPath(ROL_CARGOA_PATH).format();
		   

		    // Prefijos para Inaceptable
		  MInaceptablePrefix = aWriter.getPrefixedPath(MENU_INACEPTABLE_PATH).format();
		  CInaceptablePrefix = aWriter.getPrefixedPath(CONDICION_INACEPTABLE_PATH).format();
		  NRvisorIPrefix = aWriter.getPrefixedPath(NOMBRE_REVISORI_PATH).format();
		  RCRevisorIPrefix = aWriter.getPrefixedPath(ROL_CARGOI_PATH).format();
		
	// pathInherente
		String prefixProbabilidadInherente = "/Probabilidad_inhe";
		String prefixTipoDeDatos = "/Tipo_de_datos";
		String prefixCantidadDeDatos = "/Cantidad_de_datos";
		String prefixCategoriaDeTitulares = "/Categoria_de_titulare";
		String prefixCantidadDeTitulares = "/Cantidad_de_titulares";
		String prefixTipoDeImpacto = "/Tipo_de_impacto";
		// SalidaInherente
		String prefixExposicionInherenteTipoDeDato = "/Exposicion_inerente_tipo_de_dato";
		String prefixExposicionInherenteCantidadDatos = "/Exposicion_inherente_cantidad_de_datos";
		String prefixExposicionInherenteCategoriaTitulares = "/Exposicion_inherente_categoria_de_titulares";
		String prefixExposicionInherenteCantidadTitulares = "/Exposicion_inherente_cantidad_de_los_titulares";
		String prefixExposicionInherenteTipoDeImpacto = "/Exposicion_inherente_tipo_de_impacto";
		String prefixExposicionInherente = "/ExpoIneherenteResult"; 
		String prefixClasificacion = "/Clasificacion2";

		// pathResidual
		String prefixProbabilidadResidual = "/Probabilidad_residual";
		String prefixTipoDatoResidual = "/Tipo_de_dato_R";
		String prefixCantidadDeDatosResidual = "/Cantidad_de_datos_R";
		String prefixCategoriaDeLosTitularesResidual = "/Categoria_de_los_titulares_R";
		String prefixCantidadDeLosTitularesResidual = "/Cantidad_de_los_titulares_r";
		String prefixTipoDeImpactoResidual = "/Tipo_de_impacto_R";
		// SalidaRecidul
		String prefixExpoResidualTipoDeDato = "/Exposicion_recidual_tipo_de_dato";
		String prefixExpoResidualCantidadDatos = "/Exposicion_recidual_cantidad_de_datos";
		String prefixExpoResidualCategoriaTitulares = "/Exposicion_recidual_categoria_de_los_titulares";
		String prefixExpoResidualCantidadTitulares = "/Exposicion_recidual_cantidad_de_los_titulares";
		String prefixExpoResidualTipoImpacto = "/Exposicion_recidual_tipo_de_impacto";
		String prefixExpoResidual = "/ExposicionResidualResultado";
		String prefixClasificacionResidual = "/Clasificacion5";
	
		
		String evaImpac = "Evaluacion_Impacto_DPIA";
		Path evaImpacPath = Path.parse(evaImpac);
		String evaImpacPrefix = aWriter.getPrefixedPath(evaImpacPath).format();
		String divId = "accion_control";
		String primAuxPrefix = aWriter.getPrefixedPath(PRIMERO_AUX_PATH).format();
		String segundoAuxPrefix = aWriter.getPrefixedPath(SEGUNDO_AUX_PATH).format();
		String terceroAuxPrefix = aWriter.getPrefixedPath(TERCERO_AUX_PATH).format();
	
		idFABPrefix = aWriter.getPrefixedPath(ID_FAB_PATH).format();
		responsablePrefix = aWriter.getPrefixedPath(RESPONSABLE_PATH).format();
		rolrPrefix = aWriter.getPrefixedPath(ROLR_PATH).format();
		corresponsablePrefix = aWriter.getPrefixedPath(CORRESPONSABLE_PATH).format();
		rolcPrefix = aWriter.getPrefixedPath(ROLC_PATH).format();
		procesosPrefix = aWriter.getPrefixedPath(PROCESOS_OPERATIVOS_PATH).format();	

		aWriter.add_cr("<h3>Datos básicos del tratamiento</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Datos_basico_tratamiento/Identificador"));
		UIComboBox inhTipImp = aWriter.newComboBox(NOMBRE_TRATAMIENTO_PATH);
		inhTipImp.setActionOnAfterValueChanged(JsFunctionCall.on("updateInheritedFields"));	
		aWriter.addFormRow(inhTipImp);	
		aWriter.addFormRow(ID_FAB_PATH);
		aWriter.addFormRow(Path.parse("Datos_basico_tratamiento/Fecha_creacion"));
		
		UIDynamicListWidget respWidget = aWriter.newCustomWidget(RESPONSABLE_PATH, new UIDynamicListWidgetFactory());
		respWidget.setListId(RESPONSABLE_ID);
		aWriter.addFormRow(respWidget);
		UIDynamicListWidget rolRespWidget = aWriter.newCustomWidget(ROLR_PATH, new UIDynamicListWidgetFactory());
		rolRespWidget.setListId(ROL_RESP_ID);
		aWriter.addFormRow(rolRespWidget);
		UIDynamicListWidget correspWidget = aWriter.newCustomWidget(CORRESPONSABLE_PATH, new UIDynamicListWidgetFactory());
		correspWidget.setListId(CORRESPONSABLE_ID);
		aWriter.addFormRow(correspWidget);
		UIDynamicListWidget rolCorrespWidget = aWriter.newCustomWidget(ROLC_PATH, new UIDynamicListWidgetFactory());
		rolCorrespWidget.setListId(ROL_CORRESP_ID);
		aWriter.addFormRow(rolCorrespWidget);
		aWriter.addFormRow(PROCESOS_OPERATIVOS_PATH);
		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Datos_basico_tratamiento/Ficha_analisis_fases"), "block_ficha_analisis_fases");
		aWriter.addFormRow(Path.parse("Datos_basico_tratamiento/Campos_ficha_fase/Nombre"));
		endBlock(aWriter);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Datos_basico_tratamiento/Ficha_ciclo_vida"), "block_Ficha_ciclo_vida");
		aWriter.addFormRow(Path.parse("Datos_basico_tratamiento/Campos_Ficha_ciclo_vida/Nombre"));
		endBlock(aWriter);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Datos_basico_tratamiento/IdentificadorFAR"), "block_analisis_riesgo");
		aWriter.addFormRow(Path.parse("Datos_basico_tratamiento/Campos_ficha_analisis_de_riesgo/Identificador_FAR"));
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
	
		//Campo ocultro
		aWriter.add("<div ");
		aWriter.addSafeAttribute("style", "display:none;");
		aWriter.add_cr(">");
		aWriter.addFormRow(PRIMERO_AUX_PATH);
		aWriter.addFormRow(SEGUNDO_AUX_PATH);
		aWriter.addFormRow(TERCERO_AUX_PATH);
		aWriter.add_cr("</div>");
		//----------------------------------------
		aWriter.add_cr("<h3>Evaluación de Impacto DPIA</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);			
		aWriter.addFormRow(EVA_IMPAC_PATH);	
		aWriter.endExpandCollapseBlock();
			
		
		
		//----------------------------------------
		
		aWriter.add_cr("<h3>Revisión metodológica</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		
		//Preparación
		Path revPath = Path.parse("Revision_metodologica/ReviMetoAI");
		UIDropDownList revDropDown = aWriter.newDropDownList(revPath);
		revDropDown.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision"));
		aWriter.addFormRow(revDropDown);
		String value = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath).getValue();

		//Configuración dependiendo de lo seleccionado
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
		
		//-----------------------------------------------------
		  aWriter.addJS_cr("function saveSelectedValue(value, index){");
	        aWriter.addJS_cr("if(value == null){");
	         aWriter.addJS_cr("value = '-1';");
	        aWriter.addJS_cr("}");
	        aWriter.addJS_cr("ebx_form_setValue('" + primAuxPrefix + "[' + index + ']', value)");
	        aWriter.addJS_cr("}");
	        
	        
	        aWriter.addJS_cr("function saveSelectedValue2(value, index){");
	        aWriter.addJS_cr("if(value != null){");
	        aWriter.addJS_cr("value = value.key;");
	        aWriter.addJS_cr("}else{");
	        aWriter.addJS_cr("value = '-1';");
	        aWriter.addJS_cr("}");
	        aWriter.addJS_cr("ebx_form_setValue('" + segundoAuxPrefix + "[' + index + ']', value)");
	        aWriter.addJS_cr("}");
	        
	        aWriter.addJS_cr("function saveSelectedValue3(value, index){");
	        aWriter.addJS_cr("if(value != null){");
	        aWriter.addJS_cr("value = value.key;");
	        aWriter.addJS_cr("}else{");
	        aWriter.addJS_cr("value = '-1';");
	        aWriter.addJS_cr("}");
	        aWriter.addJS_cr("ebx_form_setValue('" + terceroAuxPrefix + "[' + index + ']', value)");
	        aWriter.addJS_cr("}");
	        
	        
	        
		// pathInherente

		aWriter.addJS_cr("function updateAllFieldsInherent(probValue, parentIndex){");
		aWriter.addJS_cr("    const tipoDatosPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixTipoDeDatos + "';");
		aWriter.addJS_cr("    const cantidadDatosPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixCantidadDeDatos + "';");
		aWriter.addJS_cr("    const categoriaTitularesPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixCategoriaDeTitulares + "';");
		aWriter.addJS_cr("    const cantidadTitularesPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixCantidadDeTitulares + "';");
		aWriter.addJS_cr("    const tipoImpactoPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixTipoDeImpacto + "';");
		aWriter.addJS_cr("    const expInhTipoDatoPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixExposicionInherenteTipoDeDato + "';");
		aWriter.addJS_cr("    const expInhCantidadDatosPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixExposicionInherenteCantidadDatos + "';");
		aWriter.addJS_cr("    const expInhCategoriaTitularesPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixExposicionInherenteCategoriaTitulares + "';");
		aWriter.addJS_cr("    const expInhCantidadTitularesPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixExposicionInherenteCantidadTitulares + "';");
		aWriter.addJS_cr("    const expInhTipoImpactoPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixExposicionInherenteTipoDeImpacto + "';");
		aWriter.addJS_cr("    const expInhPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixExposicionInherente + "';");
		aWriter.addJS_cr("    const clasificacionPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixClasificacion + "';");
		aWriter.addJS_cr("    const divId = '" + divId + "' + '_' + parentIndex;");

		aWriter.addJS_cr("    const a = updateFieldProbability(probValue, tipoDatosPath, expInhTipoDatoPath);");
		aWriter.addJS_cr("    const b = updateFieldProbability(probValue, cantidadDatosPath, expInhCantidadDatosPath);");
		aWriter.addJS_cr("    const c = updateFieldProbability(probValue, categoriaTitularesPath, expInhCategoriaTitularesPath);");
		aWriter.addJS_cr("    const d = updateFieldProbability(probValue, cantidadTitularesPath, expInhCantidadTitularesPath);");
		aWriter.addJS_cr("    const e = updateFieldProbability(probValue, tipoImpactoPath, expInhTipoImpactoPath);");
		aWriter.addJS_cr("    const highest = updateHighest(expInhPath, a, b, c, d, e);");
		aWriter.addJS_cr("    const classif = updateClassification(highest, clasificacionPath);");   
		aWriter.addJS_cr("displayDiv(classif, divId);");
		aWriter.addJS_cr("}");
	

		// Función para actualizar los campos afectados inherentes
		aWriter.addJS_cr("function updateAffectedFieldsInherent(originValue, targetPathIndex){");
		aWriter.addJS_cr("const [targetPath, index] = targetPathIndex.split(',');");
		aWriter.addJS_cr("const composedPath = '" + evaImpacPrefix + "[' + index + ']' + targetPath;");
		aWriter.addJS_cr("const probInherentePath = '" + evaImpacPrefix + "[' + index + ']" + prefixProbabilidadInherente + "';");
		aWriter.addJS_cr("const expInhTipoDatoPath = '" + evaImpacPrefix + "[' + index + ']" + prefixExposicionInherenteTipoDeDato + "';");
		aWriter.addJS_cr("const expInhCantidadDatosPath = '" + evaImpacPrefix + "[' + index + ']" + prefixExposicionInherenteCantidadDatos + "';");
		aWriter.addJS_cr("const expInhCategoriaTitularesPath = '" + evaImpacPrefix + "[' + index + ']" + prefixExposicionInherenteCategoriaTitulares + "';");
		aWriter.addJS_cr("const expInhCantidadTitularesPath = '" + evaImpacPrefix + "[' + index + ']" + prefixExposicionInherenteCantidadTitulares + "';");
		aWriter.addJS_cr("const expInhTipoImpactoPath = '" + evaImpacPrefix + "[' + index + ']" + prefixExposicionInherenteTipoDeImpacto + "';");
		aWriter.addJS_cr("const expInhPath = '" + evaImpacPrefix + "[' + index + ']" + prefixExposicionInherente + "';");
		aWriter.addJS_cr("const clasificacionPath = '" + evaImpacPrefix + "[' + index + ']" + prefixClasificacion + "';");
		aWriter.addJS_cr("const divId = '" + divId + "' + '_' + index;");
		aWriter.addJS_cr("updateField(originValue, composedPath, probInherentePath);");
		aWriter.addJS_cr("const a = ebx_form_getValue(expInhTipoDatoPath);");
		aWriter.addJS_cr("const b = ebx_form_getValue(expInhCantidadDatosPath);");
		aWriter.addJS_cr("const c = ebx_form_getValue(expInhCategoriaTitularesPath);");
		aWriter.addJS_cr("const d = ebx_form_getValue(expInhCantidadTitularesPath);");
		aWriter.addJS_cr("const e = ebx_form_getValue(expInhTipoImpactoPath);");
		aWriter.addJS_cr("const highest = updateHighest(expInhPath, a, b, c, d, e);");
		aWriter.addJS_cr("const classif = updateClassification(highest, clasificacionPath);");
		aWriter.addJS_cr("displayDiv(classif, divId);");
		aWriter.addJS_cr("}");

		
		
		// Specific Javascript Residual
		aWriter.addJS_cr("function updateAllFieldsResidual(probValue, parentIndex){");
		aWriter.addJS_cr("const resTipoDatoPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixTipoDatoResidual + "';");
		aWriter.addJS_cr("const resCantidadDatosPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixCantidadDeDatosResidual + "';");
		aWriter.addJS_cr("const resCategoriaTitularesPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixCategoriaDeLosTitularesResidual + "';");
		aWriter.addJS_cr("const resCantidadTitularesPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixCantidadDeLosTitularesResidual + "';");
		aWriter.addJS_cr("const resTipoImpactoPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixTipoDeImpactoResidual + "';");
		aWriter.addJS_cr("const expResTipoDatoPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixExpoResidualTipoDeDato + "';");
		aWriter.addJS_cr("const expResCantidadDatosPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixExpoResidualCantidadDatos + "';");
		aWriter.addJS_cr("const expResCategoriaTitularesPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixExpoResidualCategoriaTitulares + "';");
		aWriter.addJS_cr("const expResCantidadTitularesPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixExpoResidualCantidadTitulares + "';");
		aWriter.addJS_cr("const expResTipoImpactoPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixExpoResidualTipoImpacto + "';");
		aWriter.addJS_cr("const expResPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixExpoResidual + "';");
		aWriter.addJS_cr("const clasifResPath = '" + evaImpacPrefix + "[' + parentIndex + ']" + prefixClasificacionResidual + "';");
		
		aWriter.addJS_cr("const a = updateFieldProbability(probValue, resTipoDatoPath, expResTipoDatoPath);");
		aWriter.addJS_cr("const b = updateFieldProbability(probValue, resCantidadDatosPath, expResCantidadDatosPath);");
		aWriter.addJS_cr("const c = updateFieldProbability(probValue, resCategoriaTitularesPath, expResCategoriaTitularesPath);");
		aWriter.addJS_cr("const d = updateFieldProbability(probValue, resCantidadTitularesPath, expResCantidadTitularesPath);");
		aWriter.addJS_cr("const e = updateFieldProbability(probValue, resTipoImpactoPath, expResTipoImpactoPath);");
		aWriter.addJS_cr("const highest = updateHighest(expResPath, a, b, c, d, e);");
		aWriter.addJS_cr("const classif = updateClassification(highest, clasifResPath);");
			aWriter.addJS_cr("}");

		// Función para actualizar los campos afectados residuales
		aWriter.addJS_cr("function updateAffectedFieldsResidual(originValue, targetPathIndex){");
		aWriter.addJS_cr("const [targetPath, index] = targetPathIndex.split(',');");
		aWriter.addJS_cr("const composedPath = '" + evaImpacPrefix + "[' + index + ']' + targetPath;");
		aWriter.addJS_cr("const probResidualPath = '" + evaImpacPrefix + "[' + index + ']" + prefixProbabilidadResidual + "';");
		aWriter.addJS_cr("const expResTipoDatoPath = '" + evaImpacPrefix + "[' + index + ']" + prefixExpoResidualTipoDeDato + "';");
		aWriter.addJS_cr("const expResCantidadDatosPath = '" + evaImpacPrefix + "[' + index + ']" + prefixExpoResidualCantidadDatos + "';");
		aWriter.addJS_cr("const expResCategoriaTitularesPath = '" + evaImpacPrefix + "[' + index + ']" + prefixExpoResidualCategoriaTitulares + "';");
		aWriter.addJS_cr("const expResCantidadTitularesPath = '" + evaImpacPrefix + "[' + index + ']" + prefixExpoResidualCantidadTitulares + "';");
		aWriter.addJS_cr("const expResTipoImpactoPath = '" + evaImpacPrefix + "[' + index + ']" + prefixExpoResidualTipoImpacto + "';");
		aWriter.addJS_cr("const expResPath = '" + evaImpacPrefix + "[' + index + ']" + prefixExpoResidual + "';");
		aWriter.addJS_cr("const clasifResPath = '" + evaImpacPrefix + "[' + index + ']" + prefixClasificacionResidual + "';");
		aWriter.addJS_cr("updateField(originValue, composedPath, probResidualPath);");
		aWriter.addJS_cr("const a = ebx_form_getValue(expResTipoDatoPath);");
		aWriter.addJS_cr("const b = ebx_form_getValue(expResCantidadDatosPath);");
		aWriter.addJS_cr("const c = ebx_form_getValue(expResCategoriaTitularesPath);");
		aWriter.addJS_cr("const d = ebx_form_getValue(expResCantidadTitularesPath);");
		aWriter.addJS_cr("const e = ebx_form_getValue(expResTipoImpactoPath);");
		aWriter.addJS_cr("const highest = updateHighest(expResPath, a, b, c, d, e);");
		aWriter.addJS_cr("const classif = updateClassification(highest, clasifResPath);");	
		aWriter.addJS_cr("}");
		
		//funcion mustra y oculta campo 
        aWriter.addJS_cr("function displayDiv(value, divId){");
        aWriter.addJS_cr("const divElement = document.getElementById(divId);");
        aWriter.addJS_cr("if(value == 'ATENCIÓN' || value == 'INACEPTABLE'){");
        aWriter.addJS_cr("divElement.style.display = 'contents';");
        aWriter.addJS_cr("} else {");
        aWriter.addJS_cr("divElement.style.display = 'none';");
        aWriter.addJS_cr("}");

        aWriter.addJS_cr("}");
        
        aWriter.addJS_cr("function updateFieldProbability(probValue, originPath, targetPath){");
        aWriter.addJS_cr("const originValue = ebx_form_getValue(originPath);");		        
        aWriter.addJS_cr("const targetValue = (probValue * 4 + originValue * 6) / 10;");		
        aWriter.addJS_cr("ebx_form_setValue(targetPath, targetValue);");
        aWriter.addJS_cr("return targetValue;");
        aWriter.addJS_cr("}");
        
        aWriter.addJS_cr("function updateField(originValue, targetPath, probPath){");
        aWriter.addJS_cr("const probValue = ebx_form_getValue(probPath);");
        aWriter.addJS_cr("const targetValue = (probValue * 4 + originValue * 6) / 10;");
        aWriter.addJS_cr("ebx_form_setValue(targetPath, targetValue);");
        aWriter.addJS_cr("return targetValue;");
        aWriter.addJS_cr("}");
        
        aWriter.addJS_cr("function updateHighest(targetPath, ...args){");
        aWriter.addJS_cr("const targetValue = Math.max(...args);");
        aWriter.addJS_cr("ebx_form_setValue(targetPath, targetValue);");
        aWriter.addJS_cr("return targetValue;");
        aWriter.addJS_cr("}");
		
        aWriter.addJS_cr("function updateClassification(originValue, targetPath){");
        aWriter.addJS_cr("let targetValue;");
        aWriter.addJS_cr("let color;");
        aWriter.addJS_cr("if(originValue <= 2.5){");
        aWriter.addJS_cr("targetValue = 'ACEPTABLE';");
        aWriter.addJS_cr("color = '#008000';");
        aWriter.addJS_cr("} else if(originValue <= 3.5){");
        aWriter.addJS_cr("targetValue = 'ATENCIÓN';");
        aWriter.addJS_cr("color = '#FFFF00';");
        aWriter.addJS_cr("} else {");
        aWriter.addJS_cr("targetValue = 'INACEPTABLE';");
        aWriter.addJS_cr("color = '#FF0000';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("ebx_form_setValue(targetPath, targetValue);");
        aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode(targetPath).getEditor().getInput().style.backgroundColor = color;");
        aWriter.addJS_cr("return targetValue;");
        aWriter.addJS_cr("}");
		
		
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
		aWriter.addJS_cr("    resetValuesList('" + MInaceptablePrefix + "');");
		aWriter.addJS_cr("    resetValuesList('" + CInaceptablePrefix + "');");
		aWriter.addJS_cr("    resetFieldValue('" + NRvisorIPrefix + "');");
		aWriter.addJS_cr("    resetFieldValue('" + RCRevisorIPrefix + "');");
		aWriter.addJS_cr("    blockAceptable.style.display = 'block';");
		aWriter.addJS_cr("    blockInaceptable.style.display = 'none';");
		aWriter.addJS_cr("  } else if (buttonValue === 'Inaceptable') {");
		aWriter.addJS_cr("    resetValuesList('" + MAceptablePrefix + "');");
		aWriter.addJS_cr("    resetValuesList('" + CAceptablePrefix + "');");
		aWriter.addJS_cr("    resetFieldValue('" + NRvisorAPrefix + "');");
		aWriter.addJS_cr("    resetFieldValue('" + RCRevisorAPrefix + "');");
		aWriter.addJS_cr("    blockAceptable.style.display = 'none';");
		aWriter.addJS_cr("    blockInaceptable.style.display = 'block';");
		aWriter.addJS_cr("  } else {");
		aWriter.addJS_cr("    resetValuesList('" + MAceptablePrefix + "');");
		aWriter.addJS_cr("    resetValuesList('" + CAceptablePrefix + "');");
		aWriter.addJS_cr("    resetFieldValue('" + NRvisorAPrefix + "');");
		aWriter.addJS_cr("    resetFieldValue('" + RCRevisorAPrefix + "');");
		aWriter.addJS_cr("    blockAceptable.style.display = 'none';");
		aWriter.addJS_cr("    blockInaceptable.style.display = 'none';");
		aWriter.addJS_cr("  }");
		aWriter.addJS_cr("}");
		
		
		aWriter.addJS_cr("function resetValuesList(path) {");
		aWriter.addJS_cr("  let i = 0;");
		aWriter.addJS_cr("  while (ebx_form_isNodeDefined(path + '[' + i + ']')) {");
		aWriter.addJS_cr("    ebx_form_setValue(path + '[' + i + ']', null);");
		aWriter.addJS_cr("    i++;");
		aWriter.addJS_cr("  }");
		aWriter.addJS_cr("}");

		aWriter.addJS_cr("function resetFieldValue(path) {");
		aWriter.addJS_cr("  ebx_form_setValue(path, null);");
		aWriter.addJS_cr("}");

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
        
        //Funciones de lista
        aWriter.addJS_cr("function resetList(path, listId){");
        aWriter.addJS_cr("const elementList = document.getElementById(listId).children;");
        aWriter.addJS_cr("for(let i = 0; i < elementList.length; i++){");
        aWriter.addJS_cr("elementList.item(i).style.display = 'none';");
        aWriter.addJS_cr("ebx_form_setValue(path + '[' + i + ']' , null);");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");
        
        aWriter.addJS_cr("function setList(path, listId, valuesArray){");
        aWriter.addJS_cr("const elementList = document.getElementById(listId).children;");
        aWriter.addJS_cr("valuesArray.forEach(function(element, index){");
        aWriter.addJS_cr("ebx_form_setValue(path + '[' + index + ']' , element);");
        aWriter.addJS_cr("elementList.item(index).style.display = '';");
        aWriter.addJS_cr("});");
        aWriter.addJS_cr("}");
        
        //Funciones de widget
        aWriter.addJS_cr("let style = document.createElement('style');");
        aWriter.addJS_cr("style.innerHTML = `");
        aWriter.addJS_cr(".simpleEnvelope {");
        aWriter.addJS_cr("display: contents;");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("`;");
        aWriter.addJS_cr("document.head.appendChild(style);");
        
        aWriter.addJS_cr("function wrapElement(path, divId, display){");
		aWriter.addJS_cr("console.log('WRAPPING IN DIV ' + divId);");
        aWriter.addJS_cr("const node = EBX_FormNodeIndex.getFormNode(path);");
        aWriter.addJS_cr("const field = node.getDecoratorElement().closest('.ebx_Field');");
        aWriter.addJS_cr("let div = document.getElementById(divId);");
        aWriter.addJS_cr("if(!div){");
        aWriter.addJS_cr("div = document.createElement('div');");
        aWriter.addJS_cr("div.id = divId;");
        aWriter.addJS_cr("div.style.display = display;");
        aWriter.addJS_cr("if(field.tagName == 'TR'){");
        aWriter.addJS_cr("const outer = document.createElement('tr');");
        aWriter.addJS_cr("outer.classList.add('ebx_Field');");
        aWriter.addJS_cr("outer.classList.add('simpleEnvelope');");
        aWriter.addJS_cr("field.parentNode.insertBefore(outer, field);");
        aWriter.addJS_cr("outer.appendChild(div);");
        aWriter.addJS_cr("} else {");
        aWriter.addJS_cr("field.parentNode.insertBefore(div, field);");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("div.appendChild(field);");
        aWriter.addJS_cr("}");
        
        
	}
	

	private void startBlock(UserServicePaneWriter aWriter, String blockId, Boolean isDisplayed) {
		
		String display = isDisplayed ? "display:block" : "display:none";
		
		aWriter.add("<div ");
		aWriter.addSafeAttribute("id", blockId);
		aWriter.addSafeAttribute("style", display);
		aWriter.add_cr(">");
		
	}

	private void startBooleanBlock(UserServicePaneContext aPaneContext,
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
	
	private void endBlock(UserServicePaneWriter aWriter) {
		
		aWriter.add_cr("</div>");
		
	}


	@Override
	public void setupObjectContext(UserServiceSetupObjectContext<T> aContext, UserServiceObjectContextBuilder aBuilder) {
	
		if(aContext.isInitialDisplay()) {
			
			TableEntitySelection selection = aContext.getEntitySelection();
			
			if(selection instanceof RecordEntitySelection) {
				
				Adaptation record = ((RecordEntitySelection) selection).getRecord();
				
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
	    List<String> responsables = null;
	    List<String> rolesR = null;
	    List<String> corresponsables = null;
	    List<String> rolesC = null;
	    
	    String value = anAjaxContext.getParameter("fieldValue");

	    if(value != null && !value.isBlank()) {

	        Adaptation record = table.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(value));
	        
	        identificador = record.getString(Path.parse("./Identificador"));
	        proceso = record.getString(Path.parse("./Proceso_operativo"));
	        responsables = record.getList(Path.parse("./Nombre_contacto"));
	        rolesR = record.getList(Path.parse("./CargoR"));
	        corresponsables = record.getList(Path.parse("./Nombre_Co"));
	        rolesC = record.getList(Path.parse("./CargoCR"));
	    
	    }
	    
	    UserServiceWriter aWriter = anAjaxResponse.getWriter();
	    
	    //Identificador
	    identificador = identificador == null ? "null" : "'" + identificador + "'";
	    aWriter.addJS_cr("ebx_form_setValue('" + idFABPrefix + "', " +  identificador + ")");
	    
	    
	    //Proceso
	    
	    if(proceso == null) {
	        
	        aWriter.addJS_cr("proceso = null;");
	        
	    } else {
	    
	        aWriter.addJS_cr("proceso = {");
	        aWriter.addJS_cr("key: '" + proceso + "',");
	        aWriter.addJS_cr("label: '',");
	        aWriter.addJS_cr("previewURL: undefined");
	        aWriter.addJS_cr("};");
	    
	        
	    }
	    
	    aWriter.addJS_cr("ebx_form_setValue('" + procesosPrefix + "', proceso)");
	    
	    //Responsable
	    
    	aWriter.addJS_cr("responsableArray = [];");
    	aWriter.addJS_cr("resetList('" + responsablePrefix + "', '" + RESPONSABLE_ID + "');");
    	
    	if(responsables != null) {
	    	for(String responsable : responsables) {
	    		
				aWriter.addJS_cr("responsable = {");
				aWriter.addJS_cr("key: '" + responsable + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("responsableArray.push(responsable);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + responsablePrefix + "', '" + RESPONSABLE_ID + "', responsableArray);");	
	    	
    	}
    	
	    //Roles responsable
	    
    	aWriter.addJS_cr("rolesRespArray = [];");
    	aWriter.addJS_cr("resetList('" + rolrPrefix + "', '" + ROL_RESP_ID + "');");
    	
    	if(rolesR != null) {
	    	for(String rolR : rolesR) {
	    		
				aWriter.addJS_cr("rolResp = {");
				aWriter.addJS_cr("key: '" + rolR + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("rolesRespArray.push(rolResp);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + rolrPrefix + "', '" + ROL_RESP_ID + "', rolesRespArray);");	
	    	
    	}
    	
	    //Corresponsable
	    
    	aWriter.addJS_cr("corresponsableArray = [];");
    	aWriter.addJS_cr("resetList('" + corresponsablePrefix + "', '" + CORRESPONSABLE_ID + "');");
    	
    	if(corresponsables != null) {
	    	for(String corresponsable : corresponsables) {
	    		
				aWriter.addJS_cr("corresponsable = {");
				aWriter.addJS_cr("key: '" + corresponsable + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("corresponsableArray.push(corresponsable);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + corresponsablePrefix + "', '" + CORRESPONSABLE_ID + "', corresponsableArray);");	
	    	
    	}
    	
	    //Roles corresponsable
	    
    	aWriter.addJS_cr("rolesCorrespArray = [];");
    	aWriter.addJS_cr("resetList('" + rolcPrefix + "', '" + ROL_CORRESP_ID + "');");
    	
    	if(rolesC != null) {
	    	for(String rolC : rolesC) {
	    		
				aWriter.addJS_cr("rolCorresp = {");
				aWriter.addJS_cr("key: '" + rolC + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("rolesCorrespArray.push(rolCorresp);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + rolcPrefix + "', '" + ROL_CORRESP_ID + "', rolesCorrespArray);");	
	    	
    	}


	}


	@Override
	public void validate(UserServiceValidateContext<T> aContext) {

	}
	
}
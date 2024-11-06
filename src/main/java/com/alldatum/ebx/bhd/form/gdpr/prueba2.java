package com.alldatum.ebx.bhd.form.gdpr;

import java.util.List;

import com.alldatum.ebx.bhd.util.AdaptationUtil;
import com.alldatum.ebx.bhd.widget.UIDynamicListWidget;
import com.alldatum.ebx.bhd.widget.UIDynamicListWidgetFactory;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.interactions.InteractionHelper.ParametersMap;
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

public class prueba2<T extends TableEntitySelection> implements UserService<T> {
	
	private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");	
	//private static final Path EVA_RIES_PATH = Path.parse("Evaluacion_Riesgo");
	//heredar datos
	private static final Path NOMBRE_TRATAMIENTO_PATH = Path.parse("Datos_basico_tratamiento/Nombre");
	private static final Path ID_FAB_PATH = Path.parse("Datos_basico_tratamiento/IdentificadorFABOA");
	private static final Path RESPONSABLE_PATH =Path.parse("Datos_basico_tratamiento/Responsable");
	private static final Path ROLR_PATH = Path.parse("Datos_basico_tratamiento/Rol_o_CargoR");
	private static final Path CORRESPONSABLE_PATH = Path.parse("Datos_basico_tratamiento/Correspondable");
	private static final Path ROLC_PATH = Path.parse("Datos_basico_tratamiento/Rol_cargocor");
	
	//Path de la carpeta Evaluacion_Riesgo
	private static final Path EVALUACION_IMPACTO_PATH = Path.parse("Evaluacion_herencia");
	
	 
	 
	 private static final String RESPONSABLE_ID = "responsableList";
	private static final String ROL_RESP_ID = "rolResponsableList";
	private static final String CORRESPONSABLE_ID = "corresponsableList";
	private static final String ROL_CORRESP_ID = "rolCorresponsableList";
	//List de la carpeta Evaluacion_Riesgo
	private static final String EVALUACIONR_ID = "evaluacionrisgoList";

	private String idFABPrefix;
	private String responsablePrefix;
	private String rolrPrefix;
	private String corresponsablePrefix;
	private String rolcPrefix;
	//Prefix carpeta Evaluacion_Riesgo
	private String evarPrefix;
	private List<Object> evaluacion = null;
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
	
	private UserServiceEventOutcome save(UserServiceEventContext aContext) {
	aContext.getValueContext(OBJECT_KEY, EVALUACION_IMPACTO_PATH).setNewValue(evaluacion);
		
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
	
		
		String evaImpac = "Evaluacion_herencia";
		Path evaImpacPath = Path.parse(evaImpac);
		String evaImpacPrefix = aWriter.getPrefixedPath(evaImpacPath).format();
		String divId = "accion_control";
		
	
		
		idFABPrefix = aWriter.getPrefixedPath(ID_FAB_PATH).format();
		responsablePrefix = aWriter.getPrefixedPath(RESPONSABLE_PATH).format();
		rolrPrefix = aWriter.getPrefixedPath(ROLR_PATH).format();
		corresponsablePrefix = aWriter.getPrefixedPath(CORRESPONSABLE_PATH).format();
		rolcPrefix = aWriter.getPrefixedPath(ROLC_PATH).format();
	//	procesosPrefix = aWriter.getPrefixedPath(PROCESOS_OPERATIVOS_PATH).format();	
		evarPrefix = aWriter.getPrefixedPath(EVALUACION_IMPACTO_PATH).format();

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
		//aWriter.addFormRow(PROCESOS_OPERATIVOS_PATH);
		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Datos_basico_tratamiento/Ficha_analisis_fases"), "block_ficha_analisis_fases");
		aWriter.addFormRow(Path.parse("Datos_basico_tratamiento/Campos_ficha_fase/Nombre"));
		endBlock(aWriter);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Datos_basico_tratamiento/Ficha_ciclo_vida"), "block_Ficha_ciclo_vida");
		aWriter.addFormRow(Path.parse("Datos_basico_tratamiento/Campos_Ficha_ciclo_vida/Nombre"));
		endBlock(aWriter);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Datos_basico_tratamiento/IdentificadorFAR"), "block_Ficha_riesgo");
		aWriter.addFormRow(Path.parse("Datos_basico_tratamiento/Campos_ficha_analisis_de_riesgo/Identificador_FAR"));
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		//----------------------------------------
	
		//----------------------------------------
		aWriter.add_cr("<h3>Evaluación de riesgo</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		
		UIDynamicListWidget evaRWidget = aWriter.newCustomWidget(EVALUACION_IMPACTO_PATH, new UIDynamicListWidgetFactory());
		evaRWidget.setListId(EVALUACIONR_ID);
		aWriter.addFormRow(evaRWidget);
		aWriter.endExpandCollapseBlock();
			
		//----------------------------------------
		
		aWriter.add_cr("<h3>Revisión metodológica</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		Path revPath = Path.parse("Revision_metodologica/ReviMetoAI");
		UIDropDownList revDropDown = aWriter.newDropDownList(revPath);
		revDropDown.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision"));
		aWriter.addFormRow(revDropDown);
		String value2 = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath).getValue();		
		startBlock(aWriter, "block_revision_aceptable", "aceptable".equalsIgnoreCase(value2));	
		aWriter.addFormRow(Path.parse("Revision_metodologica/MenuACepAI"));
		aWriter.addFormRow(Path.parse("Revision_metodologica/ConsiadAI"));
		aWriter.addFormRow(Path.parse("Revision_metodologica/NombrereviorPAIDPIA"));
		aWriter.addFormRow(Path.parse("Revision_metodologica/RolcargoPAI"));
		endBlock(aWriter);		
		startBlock(aWriter, "block_revision_inaceptable", "inaceptable".equalsIgnoreCase(value2));
		aWriter.addFormRow(Path.parse("Revision_metodologica/MeniinaAI"));
		aWriter.addFormRow(Path.parse("Revision_metodologica/ConciinaAI"));
		aWriter.addFormRow(Path.parse("Revision_metodologica/NombrerevisorPAIina"));
		aWriter.addFormRow(Path.parse("Revision_metodologica/RolocargoPAIna"));
		endBlock(aWriter);		
		aWriter.endExpandCollapseBlock();		
		
		//-----------------------------------------------------
		
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
        aWriter.addJS_cr("function displayBlockRevision(buttonValue){");
        aWriter.addJS_cr("const blockAceptable = document.getElementById('block_revision_aceptable');");
        aWriter.addJS_cr("const blockInaceptable = document.getElementById('block_revision_inaceptable');");
        aWriter.addJS_cr("if (buttonValue == 'Aceptable'){");
        aWriter.addJS_cr("blockAceptable.style.display = 'block';");
        aWriter.addJS_cr("blockInaceptable.style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else if(buttonValue == 'Inaceptable'){");
        aWriter.addJS_cr("blockAceptable.style.display = 'none';");
        aWriter.addJS_cr("blockInaceptable.style.display = 'block';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else {");
        aWriter.addJS_cr("blockAceptable.style.display = 'none';");
        aWriter.addJS_cr("blockInaceptable.style.display = 'none';");
        aWriter.addJS_cr("}");
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
        
   
        ////
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
        
        
        //Funcion de lista para los campos de la carpeta Evaluacion_Riesgo, resetComplexList y setComplexList
        
        aWriter.addJS_cr("function resetComplexList(pathBase, pathArray, listId) {");
        aWriter.addJS_cr("const elementList = document.getElementById(listId).children;");
        aWriter.addJS_cr("for (let i = 0; i < elementList.length; i++) {");
        aWriter.addJS_cr("elementList.item(i).style.display = 'none';");
        aWriter.addJS_cr("pathArray.forEach(function(path){");
        aWriter.addJS_cr("ebx_form_setValue(pathBase + '[' + i + ']/' + path, null);");
        aWriter.addJS_cr("});");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");

        aWriter.addJS_cr("function setComplexList(pathBase, pathArray, listId, valuesArray) {");
        aWriter.addJS_cr("const elementList = document.getElementById(listId).children;");
        aWriter.addJS_cr("valuesArray.forEach(function(element, index) {");
        aWriter.addJS_cr("pathArray.forEach(function(path){");
        aWriter.addJS_cr("ebx_form_setValue(pathBase + '[' + index + ']/' + path, element[path]);");
        aWriter.addJS_cr("});");
        aWriter.addJS_cr("elementList.item(index).style.display = '';");
        aWriter.addJS_cr("});");
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
				evaluacion = record.getList(EVALUACION_IMPACTO_PATH);
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
	    AdaptationTable table = dataset.getTable(Path.parse("/root/Formularios/Plantilla_Analisis_Impacto_DPIA"));

	    String identificador = null;
	    List<String> responsables = null;
	    List<String> rolesR = null;
	    List<String> corresponsables = null;
	    List<String> rolesC = null;
	    //List de la carpeta /Evaluacion_Riesgo
	   // List<Object> evaluacion = null;
	    //nodo
	    SchemaNode node = null;
	    
	    String value = anAjaxContext.getParameter("fieldValue");

	    if(value != null && !value.isBlank()) {

	        Adaptation record = table.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(value));
	        
	        identificador = record.getString(Path.parse("./Datos_basico_tratamiento/IdentificadorFABOA"));
	        responsables = record.getList(Path.parse("./Datos_basico_tratamiento/Responsable"));
	        rolesR = record.getList(Path.parse("./Datos_basico_tratamiento/Rol_o_CargoR"));
	        corresponsables = record.getList(Path.parse("./Datos_basico_tratamiento/Correspondable"));
	        rolesC = record.getList(Path.parse("./Datos_basico_tratamiento/Rol_cargocor"));
	        //Path de la carpeta /Evaluacion_Riesgo
	        evaluacion = record.getList(Path.parse("./Evaluacion_Impacto_DPIA"));
	        //Path del nodo 
	        node = record.getSchemaNode().getNode(Path.parse("./Evaluacion_Impacto_DPIA"));
	        
	        
	    }
	    
	    UserServiceWriter aWriter = anAjaxResponse.getWriter();
	    
	    //Identificador
	    identificador = identificador == null ? "null" : "'" + identificador + "'";
	    aWriter.addJS_cr("ebx_form_setValue('" + idFABPrefix + "', " +  identificador + ")");
	    
	    
	    //Campos de Evaluacion Riesgo
	    /*String objePath = "Objetivo";
	    String riesPath = "RiesgoNAIP";
	    String fechIdenPath = "Fecha_iden";
	    String probInhePath = "Probabilidad_inhe";
	    String impaInhePath = "Impacto_inhe";
	    /*String expoInhePath = "Expo_inherente";
	    String clasInhePath = "Clasificacion2";*
	    String contPath = "Control_instalado";
	    String probresiPath = "Probabilidad_residual";
	    String  impacresiPath = "Impacto_residual";
	    /*String exporesiPath = "Exposicion_residual_R";
	    String clasresiPath = "Clasificacion5";*
	    String controlsuPath = "Accion_Control_sugerido";
	    String responPath = "Responsable";	
	    String estadoPath = "Estado";
	    String fechimplePaht = "Fecha_Estimada_Implementacion2";
	    String kri1Path = "KRI1_por_objetivo";
	    String kri2Paht = "KRI2_por_objetivo";*/
	    
	    
        // Campos de Analsisi de Impacto
        String objePath = "ObjetivosAIP";
        String riesPath = "RiesgoARE";
        String fechIdenPath = "Fecha_iden";
        String probInhePath = "Probabilidad_inhe";
        String tiposDatosPath = "Tipo_de_datos";
        String cantidadDatosPath = "Cantidad_de_datos";
        String categoriaTitularesPath = "Categoria_de_titulare";
        String tipoImpactoPath = "Tipo_de_impacto";
        String expoInherTDatoPath = "Exposicion_inerente_tipo_de_dato";
        String expoInheCantDPath = "Exposicion_inherente_cantidad_de_datos";
        String expoInheCantTituDPath = "Exposicion_inherente_categoria_de_titulares";
        String expoInheCantTituD2Path = "Exposicion_inherente_cantidad_de_los_titulares";
        String expoInheTpImpaDPath = "Exposicion_inherente_tipo_de_impacto";
        String expoInheResultpaDPath = "ExpoIneherenteResult";
       // String expoInheRPath = "Exposicion_InherenteR";
        //String claspruebaPath = "clasprueba";
        String clasificacion2aPath = "Clasificacion2";
        String controlInstaPath = "Control_instalado_DPIA";
        String probabilidadRPath = "Probabilidad_residual";
        String tipoDatoRPath = "Tipo_de_dato_R";
        String cantidadDatoRPath = "Cantidad_de_datos_R";
        String categoriaTitularesRPath = "Categoria_de_los_titulares_R";
        String cantidadRitularesRPath = "Cantidad_de_los_titulares_r";
        String tipoImpactoRPath = "Tipo_de_impacto_R";
        String expoResidualTpDatoPath = "Exposicion_recidual_tipo_de_dato";
        String expoResidualTpDatoCanPath = "Exposicion_recidual_cantidad_de_datos";
        String expoRecCatTitularesPath = "Exposicion_recidual_categoria_de_los_titulares";
        String expoRecCanTitularesPath = "Exposicion_recidual_cantidad_de_los_titulares";
        String expoRecTpImpactoPath = "Exposicion_recidual_tipo_de_impacto";
        String expoResResultadoPath = "ExposicionResidualResultado";
       // String expoResRPath = "Exposicion_residual_R";
      //String clasifiPath = "Clasificacion_RF";
        String clasificacion5Path = "Clasificacion5";
        String accControlSPath = "Accion_Control_sugerido";
        String responsablePath = "Responsable";
        String estadoPath = "Estado";
        String fechaEstimadaImpPath = "Fecha_Estimada_Implementacion2";
        String kri1ImpPath = "KRI1_por_objetivo";
        String kri2Path = "KRI2_por_objetivo";
	    
	    //Creacion de Array de Evaluacion_Riesgo
    	aWriter.addJS_cr("evalRiesgoArray = [];");   	
    	aWriter.addJS_cr("evalPathArray = [];");
        aWriter.addJS_cr("evalPathArray.push('" + objePath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + riesPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + fechIdenPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + probInhePath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + tiposDatosPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + cantidadDatosPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + categoriaTitularesPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + tipoImpactoPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + expoInherTDatoPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + expoInheCantDPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + expoInheCantTituDPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + expoInheCantTituD2Path + "');");
        aWriter.addJS_cr("evalPathArray.push('" + expoInheTpImpaDPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + expoInheResultpaDPath + "');");
       // aWriter.addJS_cr("evalPathArray.push('" + expoInheRPath + "');");
       // aWriter.addJS_cr("evalPathArray.push('" + claspruebaPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + clasificacion2aPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + controlInstaPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + probabilidadRPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + tipoDatoRPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + cantidadDatoRPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + categoriaTitularesRPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + cantidadRitularesRPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + tipoImpactoRPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + expoResidualTpDatoPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + expoResidualTpDatoCanPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + expoRecCatTitularesPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + expoRecCanTitularesPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + expoRecTpImpactoPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + expoResResultadoPath + "');");
       // aWriter.addJS_cr("evalPathArray.push('" + expoResRPath + "');");
       // aWriter.addJS_cr("evalPathArray.push('" + clasifiPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + clasificacion5Path + "');");
        aWriter.addJS_cr("evalPathArray.push('" + accControlSPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + responsablePath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + estadoPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + fechaEstimadaImpPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + kri1ImpPath + "');");
        aWriter.addJS_cr("evalPathArray.push('" + kri2Path + "');");
   
    	aWriter.addJS_cr("resetComplexList('" + evarPrefix + "', evalPathArray, '" + EVALUACIONR_ID + "');");
    	
        if (evaluacion != null) {
            for (Object eval : evaluacion) {

                    String objetivo = String.valueOf(node.getNode(Path.parse(objePath)).executeRead(eval));
                    if (objetivo.isBlank() || objetivo.equals("null")) {
                            objetivo = "null";
                            System.out.println("Valor del riesgo null");
                    } else {
                            objetivo = "{key: '" + objetivo + "', label: '', previewURL: undefined}";
                            System.out.println("Sin datos");
                    }

                    String riesgo = String.valueOf(node.getNode(Path.parse(riesPath)).executeRead(eval));
                    if (riesgo.isBlank() || riesgo.equals("null")) {
                            riesgo = "null";
                            System.out.println("Valor del riesgo null");
                    } else {
                            riesgo = "{key: '" + riesgo + "', label: '', previewURL: undefined}";
                            System.out.println("Sin datos");
                    }
                    String fecha = String.valueOf(node.getNode(Path.parse(fechIdenPath)).executeRead(eval));
                    if (fecha.isBlank() || fecha.equals("null"))
                            fecha = "null";
                    else
                            fecha = "new Date('" + fecha + "')";

                    String probInhe = String
                                    .valueOf(node.getNode(Path.parse(probInhePath)).executeRead(eval));
                    if (probInhe.isBlank() || probInhe.equals("null"))
                            probInhe = "null";
                    else
                            probInhe = "'" + probInhe + "'";

                    String tiposDatos = String
                                    .valueOf(node.getNode(Path.parse(tiposDatosPath)).executeRead(eval));
                    if (tiposDatos.isBlank() || tiposDatos.equals("null"))
                            tiposDatos = "null";
                    else
                            tiposDatos = "'" + tiposDatos + "'";

                    String cantDatos = String
                                    .valueOf(node.getNode(Path.parse(cantidadDatosPath)).executeRead(eval));
                    if (cantDatos.isBlank() || cantDatos.equals("null"))
                            cantDatos = "null";
                    else
                            cantDatos = "'" + cantDatos + "'";

                    String catTitulares = String
                                    .valueOf(node.getNode(Path.parse(categoriaTitularesPath))
                                                    .executeRead(eval));
                    if (catTitulares.isBlank() || catTitulares.equals("null"))
                            catTitulares = "null";
                    else
                            catTitulares = "'" + catTitulares + "'";

                    String tpImpac = String
                                    .valueOf(node.getNode(Path.parse(tipoImpactoPath)).executeRead(eval));
                    if (tpImpac.isBlank() || tpImpac.equals("null"))
                            tpImpac = "null";
                    else
                            tpImpac = "'" + tpImpac + "'";

                    String expoInhe = String
                                    .valueOf(node.getNode(Path.parse(expoInherTDatoPath))
                                                    .executeRead(eval));
                    if (expoInhe.isBlank() || expoInhe.equals("null"))
                            expoInhe = "null";
                    else
                            expoInhe = "'" + expoInhe + "'";

                    String expoInheCant = String
                                    .valueOf(node.getNode(Path.parse(expoInheCantDPath))
                                                    .executeRead(eval));
                    if (expoInheCant.isBlank() || expoInheCant.equals("null"))
                            expoInheCant = "null";
                    else
                            expoInheCant = "'" + expoInheCant + "'";

                    String expoInheCantTitular = String
                                    .valueOf(node.getNode(Path.parse(expoInheCantTituDPath))
                                                    .executeRead(eval));
                    if (expoInheCantTitular.isBlank() || expoInheCantTitular.equals("null"))
                            expoInheCantTitular = "null";
                    else
                            expoInheCantTitular = "'" + expoInheCantTitular + "'";

                    String expoInheCantTitular2 = String
                                    .valueOf(node.getNode(Path.parse(expoInheCantTituD2Path))
                                                    .executeRead(eval));
                    if (expoInheCantTitular2.isBlank() || expoInheCantTitular2.equals("null"))
                            expoInheCantTitular2 = "null";
                    else
                            expoInheCantTitular2 = "'" + expoInheCantTitular2 + "'";

                    String expoInheTpImpac = String
                                    .valueOf(node.getNode(Path.parse(expoInheTpImpaDPath))
                                                    .executeRead(eval));
                    if (expoInheTpImpac.isBlank() || expoInheTpImpac.equals("null"))
                            expoInheTpImpac = "null";
                    else
                            expoInheTpImpac = "'" + expoInheTpImpac + "'";

                    String expoInheResult = String
                                    .valueOf(node.getNode(Path.parse(expoInheResultpaDPath))
                                                    .executeRead(eval));
                    if (expoInheResult.isBlank() || expoInheResult.equals("null"))
                            expoInheResult = "null";
                    else
                            expoInheResult = "'" + expoInheResult + "'";

                  /*  String expoInheR = String
                                    .valueOf(node.getNode(Path.parse(expoInheRPath))
                                                    .executeRead(eval));
                    if (expoInheR.isBlank() || expoInheR.equals("null"))
                            expoInheR = "null";
                    else
                            expoInheR = "'" + expoInheR + "'";

                    String classPrueba = String
                                    .valueOf(node.getNode(Path.parse(claspruebaPath))
                                                    .executeRead(eval));
                    if (classPrueba.isBlank() || classPrueba.equals("null"))
                            classPrueba = "null";
                    else
                            classPrueba = "'" + classPrueba + "'";*/

                    String clasificacion2 = String
                                    .valueOf(node.getNode(Path.parse(clasificacion2aPath))
                                                    .executeRead(eval));
                    if (clasificacion2.isBlank() || clasificacion2.equals("null"))
                            clasificacion2 = "null";
                    else
                            clasificacion2 = "'" + clasificacion2 + "'";

                    String controlInsa = String
                                    .valueOf(node.getNode(Path.parse(controlInstaPath)).executeRead(eval));
                    if (controlInsa.isBlank() || controlInsa.equals("null")) {
                            controlInsa = "null";
                            System.out.println("Valor del riesgo null");
                    } else {
                            controlInsa = "{key: '" + controlInsa + "', label: '', previewURL: undefined}";
                            System.out.println("Sin datos");
                    }

                    String probabilidadR = String
                                    .valueOf(node.getNode(Path.parse(probabilidadRPath))
                                                    .executeRead(eval));
                    if (probabilidadR.isBlank() || probabilidadR.equals("null"))
                            probabilidadR = "null";
                    else
                            probabilidadR = "'" + probabilidadR + "'";

                    String tipoDatoR = String
                                    .valueOf(node.getNode(Path.parse(tipoDatoRPath))
                                                    .executeRead(eval));
                    if (tipoDatoR.isBlank() || tipoDatoR.equals("null"))
                            tipoDatoR = "null";
                    else
                            tipoDatoR = "'" + tipoDatoR + "'";

                    String cantidadDatoR = String
                                    .valueOf(node.getNode(Path.parse(cantidadDatoRPath))
                                                    .executeRead(eval));
                    if (cantidadDatoR.isBlank() || cantidadDatoR.equals("null"))
                            cantidadDatoR = "null";
                    else
                            cantidadDatoR = "'" + cantidadDatoR + "'";

                    String catTitularesR = String
                                    .valueOf(node.getNode(Path.parse(categoriaTitularesRPath))
                                                    .executeRead(eval));
                    if (catTitularesR.isBlank() || catTitularesR.equals("null"))
                            catTitularesR = "null";
                    else
                            catTitularesR = "'" + catTitularesR + "'";

                    String cantidadTituR = String
                                    .valueOf(node.getNode(Path.parse(cantidadRitularesRPath))
                                                    .executeRead(eval));
                    if (cantidadTituR.isBlank() || cantidadTituR.equals("null"))
                            cantidadTituR = "null";
                    else
                            cantidadTituR = "'" + cantidadTituR + "'";

                    String tipoImpactoR = String
                                    .valueOf(node.getNode(Path.parse(tipoImpactoRPath))
                                                    .executeRead(eval));
                    if (tipoImpactoR.isBlank() || tipoImpactoR.equals("null"))
                            tipoImpactoR = "null";
                    else
                            tipoImpactoR = "'" + tipoImpactoR + "'";

                    String expoResidualR = String
                                    .valueOf(node.getNode(Path.parse(expoResidualTpDatoPath))
                                                    .executeRead(eval));
                    if (expoResidualR.isBlank() || expoResidualR.equals("null"))
                            expoResidualR = "null";
                    else
                            expoResidualR = "'" + expoResidualR + "'";

                    String expoRTpDato = String
                                    .valueOf(node.getNode(Path.parse(expoResidualTpDatoCanPath))
                                                    .executeRead(eval));
                    if (expoRTpDato.isBlank() || expoRTpDato.equals("null"))
                            expoRTpDato = "null";
                    else
                            expoRTpDato = "'" + expoRTpDato + "'";

                    String expoRecCantTitulares = String
                                    .valueOf(node.getNode(Path.parse(expoRecCatTitularesPath))
                                                    .executeRead(eval));
                    if (expoRecCantTitulares.isBlank() || expoRecCantTitulares.equals("null"))
                            expoRecCantTitulares = "null";
                    else
                            expoRecCantTitulares = "'" + expoRecCantTitulares + "'";

                    String expoRecCanTitulares = String
                                    .valueOf(node.getNode(Path.parse(expoRecCanTitularesPath))
                                                    .executeRead(eval));
                    if (expoRecCanTitulares.isBlank() || expoRecCanTitulares.equals("null"))
                            expoRecCanTitulares = "null";
                    else
                            expoRecCanTitulares = "'" + expoRecCanTitulares + "'";

                    String expoRecTpImpacto = String
                                    .valueOf(node.getNode(Path.parse(expoRecTpImpactoPath))
                                                    .executeRead(eval));
                    if (expoRecTpImpacto.isBlank() || expoRecTpImpacto.equals("null"))
                            expoRecTpImpacto = "null";
                    else
                            expoRecTpImpacto = "'" + expoRecTpImpacto + "'";

                    String expoResResultado = String
                                    .valueOf(node.getNode(Path.parse(expoResResultadoPath))
                                                    .executeRead(eval));
                    if (expoResResultado.isBlank() || expoResResultado.equals("null"))
                            expoResResultado = "null";
                    else
                            expoResResultado = "'" + expoResResultado + "'";

                   /* String expoRes = String
                                    .valueOf(node.getNode(Path.parse(expoResRPath))
                                                    .executeRead(eval));
                    if (expoRes.isBlank() || expoRes.equals("null"))
                            expoRes = "null";
                    else
                            expoRes = "'" + expoRes + "'";

                    String clasifi = String
                                    .valueOf(node.getNode(Path.parse(clasifiPath))
                                                    .executeRead(eval));

                    if (clasifi.isBlank() || clasifi.equals("null"))
                            clasifi = "null";
                    else
                            clasifi = "'" + clasifi + "'";*/

                    String clasificacion5 = String
                                    .valueOf(node.getNode(Path.parse(clasificacion5Path))
                                                    .executeRead(eval));
                    if (clasificacion5.isBlank() || clasificacion5.equals("null"))
                            clasificacion5 = "null";
                    else
                            clasificacion5 = "'" + clasificacion5 + "'";

                    String accControl = String
                                    .valueOf(node.getNode(Path.parse(accControlSPath)).executeRead(eval));
                    if (accControl.isBlank() || accControl.equals("null")) {
                            accControl = "null";
                            System.out.println("Valor del riesgo null");
                    } else {
                            accControl = "{key: '" + accControl + "', label: '', previewURL: undefined}";
                            System.out.println("Sin datos");
                    }

                    String responsable = String
                                    .valueOf(node.getNode(Path.parse(responsablePath)).executeRead(eval));
                    if (responsable.isBlank() || responsable.equals("null")) {
                            responsable = "null";
                            System.out.println("Valor del riesgo null");
                    } else {
                            responsable = "{key: '" + responsable + "', label: '', previewURL: undefined}";
                            System.out.println("Sin datos");
                    }

                    String estado = String.valueOf(node.getNode(Path.parse(estadoPath)).executeRead(eval));
                    if (estado.isBlank() || estado.equals("null")) {
                            estado = "null";
                            System.out.println("Valor del riesgo null");
                    } else {
                            estado = "{key: '" + estado + "', label: '', previewURL: undefined}";
                            System.out.println("Sin datos");
                    }

                    String fechaEstimada = String
                                    .valueOf(node.getNode(Path.parse(fechaEstimadaImpPath))
                                                    .executeRead(eval));
                    if (fechaEstimada.isBlank() || fechaEstimada.equals("null"))
                            fechaEstimada = "null";
                    else
                            fechaEstimada = "'" + fechaEstimada + "'";

                    String kri1Imp = String
                                    .valueOf(node.getNode(Path.parse(kri1ImpPath)).executeRead(eval));
                    if (kri1Imp.isBlank() || kri1Imp.equals("null")) {
                            kri1Imp = "null";
                            System.out.println("Valor del riesgo null");
                    } else {
                            kri1Imp = "{key: '" + kri1Imp + "', label: '', previewURL: undefined}";
                            System.out.println("Sin datos");
                    }

                    String kri2 = String.valueOf(node.getNode(Path.parse(kri2Path)).executeRead(eval));
                    if (kri2.isBlank() || kri2.equals("null")) {
                            kri2 = "null";
                            System.out.println("Valor del riesgo null");
                    } else {
                            kri2 = "{key: '" + kri2 + "', label: '', previewURL: undefined}";
                            System.out.println("Sin datos");
                    }

                    aWriter.addJS_cr("evalRiesgo = {");
                     aWriter.addJS_cr(objePath + ": " + objetivo + ",");
                    aWriter.addJS_cr(riesPath + ": " + riesgo + ",");
                    aWriter.addJS_cr(fechIdenPath + ": " + fecha + ",");
                    aWriter.addJS_cr(probInhePath + ": " + probInhe + ",");

                    aWriter.addJS_cr(tiposDatosPath + ": " + tiposDatos + ",");
                    aWriter.addJS_cr(cantidadDatosPath + ": " + cantDatos + ",");
                    aWriter.addJS_cr(categoriaTitularesPath + ": " + catTitulares + ",");
                    aWriter.addJS_cr(tipoImpactoPath + ": " + tpImpac + ",");
                    aWriter.addJS_cr(expoInherTDatoPath + ": " + expoInhe + ",");

                    aWriter.addJS_cr(expoInheCantDPath + ": " + expoInheCant + ",");
                    aWriter.addJS_cr(expoInheCantTituDPath + ": " + expoInheCantTitular + ",");
                    aWriter.addJS_cr(expoInheCantTituD2Path + ": " + expoInheCantTitular2 + ",");
                    aWriter.addJS_cr(expoInheTpImpaDPath + ": " + expoInheTpImpac + ",");
                    aWriter.addJS_cr(expoInheResultpaDPath + ": " + expoInheResult + ",");

                   // aWriter.addJS_cr(expoInheRPath + ": " + expoInheR + ",");
                    //aWriter.addJS_cr(claspruebaPath + ": " + classPrueba + ",");
                    aWriter.addJS_cr(clasificacion2aPath + ": " + clasificacion2 + ",");
                    aWriter.addJS_cr(controlInstaPath + ": " + controlInsa + ",");
                    aWriter.addJS_cr(probabilidadRPath + ": " + probabilidadR + ",");

                    aWriter.addJS_cr(tipoDatoRPath + ": " + tipoDatoR + ",");
                    aWriter.addJS_cr(cantidadDatoRPath + ": " + cantidadDatoR + ",");
                    aWriter.addJS_cr(categoriaTitularesRPath + ": " + catTitularesR + ",");
                    aWriter.addJS_cr(cantidadRitularesRPath + ": " + cantidadTituR + ",");
                    aWriter.addJS_cr(tipoImpactoRPath + ": " + tipoImpactoR + ",");

                    aWriter.addJS_cr(expoResidualTpDatoPath + ": " + expoResidualR + ",");
                    aWriter.addJS_cr(expoResidualTpDatoCanPath + ": " + expoRTpDato + ",");
                    aWriter.addJS_cr(expoRecCatTitularesPath + ": " + expoRecCantTitulares + ",");
                    aWriter.addJS_cr(expoRecCanTitularesPath + ": " + expoRecCanTitulares + ",");
                    aWriter.addJS_cr(expoRecTpImpactoPath + ": " + expoRecTpImpacto + ",");

                    aWriter.addJS_cr(expoResResultadoPath + ": " + expoResResultado + ",");
                    //aWriter.addJS_cr(expoResRPath + ": " + expoRes + ",");
                    //aWriter.addJS_cr(clasifiPath + ": " + clasifi + ",");
                    aWriter.addJS_cr(clasificacion5Path + ": " + clasificacion5 + ",");
                    aWriter.addJS_cr(accControlSPath + ": " + accControl + ",");

                    aWriter.addJS_cr(responsablePath + ": " + responsable + ",");
                    aWriter.addJS_cr(estadoPath + ": " + estado + ",");
                    aWriter.addJS_cr(fechaEstimadaImpPath + ": " + fechaEstimada + ",");
                    aWriter.addJS_cr(kri1ImpPath + ": " + kri1Imp + ",");
                    aWriter.addJS_cr(kri2Path + ": " + kri2 + ",");

                    aWriter.addJS_cr("};");

                    aWriter.addJS_cr("evalRiesgoArray.push(evalRiesgo);");

            }

            aWriter.addJS_cr("setComplexList('" + evarPrefix + "', evalPathArray, '"+ EVALUACIONR_ID + "', evalRiesgoArray);");

    }
    	
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
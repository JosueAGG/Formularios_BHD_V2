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

public class Pruebadecampos<T extends TableEntitySelection> implements UserService<T> {
	
	private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");	
	//private static final Path EVA_RIES_PATH = Path.parse("Evaluacion_Riesgo");
	//heredar datos
	private static final Path NOMBRE_TRATAMIENTO_PATH = Path.parse("Datos_basico_tratamiento/Nombre");
	private static final Path ID_FAB_PATH = Path.parse("Datos_basico_tratamiento/Identificador_analisis_basico");
	private static final Path RESPONSABLE_PATH =Path.parse("Datos_basico_tratamiento/Responsable");
	private static final Path ROLR_PATH = Path.parse("Datos_basico_tratamiento/Rol_o_Cargo");
	private static final Path CORRESPONSABLE_PATH = Path.parse("Datos_basico_tratamiento/Nombre_c");
	private static final Path ROLC_PATH = Path.parse("Datos_basico_tratamiento/Rol_o_cargo_corres");
	
	//Path de la carpeta Evaluacion_Riesgo
	private static final Path EVALUACION_RIESGO_PATH = Path.parse("Evaluacion_Riesgo");
	
	 
	 
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
	aContext.getValueContext(OBJECT_KEY, EVALUACION_RIESGO_PATH).setNewValue(evaluacion);
		
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
		/*String evaRies = "Evaluacion_Riesgo";
		String inhProb = "/Probabilidad_inhe";
		String inhImpa = "/Impacto_inhe";
		String inhExpo = "/Expo_inherente";
		String inhClas = "/Clasificacion2";
		
		String resProb = "/Probabilidad_residual";
		String resImpa = "/Impacto_residual";
		String resExpo = "/Exposicion_residual_R";
		String resClas = "/Clasificacion5";
		
		Path evaRiesPath = Path.parse(evaRies);
		String evaRiesPrefix = aWriter.getPrefixedPath(evaRiesPath).format();
		String divId = "accion_control";*/
		
	
		
		idFABPrefix = aWriter.getPrefixedPath(ID_FAB_PATH).format();
		responsablePrefix = aWriter.getPrefixedPath(RESPONSABLE_PATH).format();
		rolrPrefix = aWriter.getPrefixedPath(ROLR_PATH).format();
		corresponsablePrefix = aWriter.getPrefixedPath(CORRESPONSABLE_PATH).format();
		rolcPrefix = aWriter.getPrefixedPath(ROLC_PATH).format();
	//	procesosPrefix = aWriter.getPrefixedPath(PROCESOS_OPERATIVOS_PATH).format();	
		evarPrefix = aWriter.getPrefixedPath(EVALUACION_RIESGO_PATH).format();

		aWriter.add_cr("<h3>Datos básicos del tratamiento</h3>");
		
		
		
		
		
		
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		UIComboBox inhTipImp = aWriter.newComboBox(NOMBRE_TRATAMIENTO_PATH);
		inhTipImp.setActionOnAfterValueChanged(JsFunctionCall.on("updateInheritedFields"));	
		aWriter.addFormRow(inhTipImp);	
		aWriter.addFormRow(ID_FAB_PATH);
		aWriter.addFormRow(Path.parse("Datos_basico_tratamiento/Fecha_creacion_tratamiento"));
		
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
		
		aWriter.endExpandCollapseBlock();
		//----------------------------------------
	
		//----------------------------------------
		aWriter.add_cr("<h3>Evaluación de riesgo</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		
		UIDynamicListWidget evaRWidget = aWriter.newCustomWidget(EVALUACION_RIESGO_PATH, new UIDynamicListWidgetFactory());
		evaRWidget.setListId(EVALUACIONR_ID);
		aWriter.addFormRow(evaRWidget);
		aWriter.endExpandCollapseBlock();
			
		//----------------------------------------
		
		aWriter.add_cr("<h3>Revisión metodológica</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		Path revPath = Path.parse("Revision_metodologica/RevAR");
		UIDropDownList revDropDown = aWriter.newDropDownList(revPath);
		revDropDown.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision"));
		aWriter.addFormRow(revDropDown);
		String value2 = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath).getValue();		
		startBlock(aWriter, "block_revision_aceptable", "aceptable".equalsIgnoreCase(value2));	
		aWriter.addFormRow(Path.parse("Revision_metodologica/MenuARACEP"));
		aWriter.addFormRow(Path.parse("Revision_metodologica/ConsideARA"));
		aWriter.addFormRow(Path.parse("Revision_metodologica/NombreRevisorAR"));
		aWriter.addFormRow(Path.parse("Revision_metodologica/RolcargoAR"));
		endBlock(aWriter);		
		startBlock(aWriter, "block_revision_inaceptable", "inaceptable".equalsIgnoreCase(value2));
		aWriter.addFormRow(Path.parse("Revision_metodologica/MenuInaAR"));
		aWriter.addFormRow(Path.parse("Revision_metodologica/ConARINA"));
		aWriter.addFormRow(Path.parse("Revision_metodologica/NombrerevisorARina"));
		aWriter.addFormRow(Path.parse("Revision_metodologica/RolocargoARina"));
		endBlock(aWriter);		
		aWriter.endExpandCollapseBlock();		
		
		//-----------------------------------------------------
		
	

       
        

		
		//Funcion que ocupan los campo boolean para ocultar y mostrar 
      /*  aWriter.addJS_cr("function displayBlock(buttonValue, blockId){");
        aWriter.addJS_cr("if (buttonValue == 'true'){");
        aWriter.addJS_cr("document.getElementById(blockId).style.display = 'block';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else {");
        aWriter.addJS_cr("document.getElementById(blockId).style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");*/
		
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
        
        
        
        
		//Specific Javascript Inherente 		
	/*	aWriter.addJS_cr("function updateExposicionInherenteProbabilidad(probValue, parentIndex){");
		aWriter.addJS_cr("const inhImpPath = '" + evaRiesPrefix + "[' + parentIndex + ']"  + inhImpa + "';");
		aWriter.addJS_cr("const inhExpPath = '" + evaRiesPrefix + "[' + parentIndex + ']"  + inhExpo + "';");
		aWriter.addJS_cr("const inhClaPath = '" + evaRiesPrefix + "[' + parentIndex + ']"  + inhClas + "';");
		aWriter.addJS_cr("const divId = '" + divId + "' + '_' + parentIndex;");
        aWriter.addJS_cr("const expInh = updateFieldProbability(probValue, inhImpPath, inhExpPath);");
        aWriter.addJS_cr("const classif = updateClassification(expInh, inhClaPath);");
        aWriter.addJS_cr("displayDiv(classif, divId);");
        aWriter.addJS_cr("}");
        
        aWriter.addJS_cr("function updateAffectedFieldsInherent(originValue, parentIndex){");
		aWriter.addJS_cr("const inhProPath = '" + evaRiesPrefix + "[' + parentIndex + ']"  + inhProb + "';");
		aWriter.addJS_cr("const inhExpPath = '" + evaRiesPrefix + "[' + parentIndex + ']"  + inhExpo + "';");
		aWriter.addJS_cr("const inhClaPath = '" + evaRiesPrefix + "[' + parentIndex + ']"  + inhClas + "';");
		aWriter.addJS_cr("const divId = '" + divId + "' + '_' + parentIndex;");
        aWriter.addJS_cr("const expInh = updateField(originValue, inhExpPath, inhProPath);");
        aWriter.addJS_cr("const classif = updateClassification(expInh, inhClaPath);");
        aWriter.addJS_cr("displayDiv(classif, divId);");
        aWriter.addJS_cr("}");
        
      //Specific Javascript Residual		

		aWriter.addJS_cr("function updateExposicionResidualProbabilidad(probValue, parentIndex){");
		aWriter.addJS_cr("const resImpPath = '" + evaRiesPrefix + "[' + parentIndex + ']"  + resImpa + "';");
		aWriter.addJS_cr("const resExpPath = '" + evaRiesPrefix + "[' + parentIndex + ']"  + resExpo + "';");
		aWriter.addJS_cr("const resClaPath = '" + evaRiesPrefix + "[' + parentIndex + ']"  + resClas + "';");
        aWriter.addJS_cr("const expresi = updateFieldProbability(probValue, resImpPath, resClaPath);");
        aWriter.addJS_cr("updateClassification(expresi, resClaPath);");
        aWriter.addJS_cr("}");
      
        aWriter.addJS_cr("function updateAffectedFieldsResidual(originValue, parentIndex){");
		aWriter.addJS_cr("const resProPath = '" + evaRiesPrefix + "[' + parentIndex + ']"  + resProb + "';");
		aWriter.addJS_cr("const resExpPath = '" + evaRiesPrefix + "[' + parentIndex + ']"  + resExpo + "';");
		aWriter.addJS_cr("const resClaPath = '" + evaRiesPrefix + "[' + parentIndex + ']"  + resClas + "';");
        aWriter.addJS_cr("const expresi = updateField(originValue, resExpPath, resProPath);");
        aWriter.addJS_cr("updateClassification(expresi, resClaPath);");
        aWriter.addJS_cr("}");		
        
        //General 

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
        aWriter.addJS_cr("const probValue = ebx_form_getValue(probPath);");r
        aWriter.addJS_cr("const targetValue = (probValue * 4 + originValue * 6) / 10;");
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
        aWriter.addJS_cr("}");	*/

        
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
				evaluacion = record.getList(EVALUACION_RIESGO_PATH);
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
	    AdaptationTable table = dataset.getTable(Path.parse("/root/Formularios_nuevos/FormularioR2"));

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
	        
	        identificador = record.getString(Path.parse("./Datos_basico_tratamiento/Identificador_analisis_basico"));
	        responsables = record.getList(Path.parse("./Datos_basico_tratamiento/Responsable"));
	        rolesR = record.getList(Path.parse("./Datos_basico_tratamiento/Rol_o_Cargo"));
	        corresponsables = record.getList(Path.parse("./Datos_basico_tratamiento/Nombre_c"));
	        rolesC = record.getList(Path.parse("./Datos_basico_tratamiento/Rol_o_cargo_corres"));
	        //Path de la carpeta /Evaluacion_Riesgo
	        evaluacion = record.getList(Path.parse("./Evaluacion_Riesgo"));
	        //Path del nodo 
	        node = record.getSchemaNode().getNode(Path.parse("./Evaluacion_Riesgo"));
	        
	        
	    }
	    
	    UserServiceWriter aWriter = anAjaxResponse.getWriter();
	    
	    //Identificador
	    identificador = identificador == null ? "null" : "'" + identificador + "'";
	    aWriter.addJS_cr("ebx_form_setValue('" + idFABPrefix + "', " +  identificador + ")");
	    
	    
	    //Campos de Evaluacion Riesgo
	    String objePath = "Objetivo";
	    String riesPath = "RiesgoNAIP";
	    String fechIdenPath = "Fecha_iden";
	    String probInhePath = "Probabilidad_inhe";
	    String impaInhePath = "Impacto_inhe";
	    String expoInhePath = "Expo_inherente";
	    String clasInhePath = "Clasificacion2";
	    String contPath = "Control_instalado";
	    String probresiPath = "Probabilidad_residual";
	    String  impacresiPath = "Impacto_residual";
	    String exporesiPath = "Exposicion_residual_R";
	    String clasresiPath = "Clasificacion5";
	    String controlsuPath = "Accion_Control_sugerido";
	    String responPath = "Responsable";	
	    String estadoPath = "Estado";
	    String fechimplePaht = "Fecha_Estimada_Implementacion2";
	    String kri1Path = "KRI1_por_objetivo";
	    String kri2Paht = "KRI2_por_objetivo";
	    
	    
	    //Creacion de Array de Evaluacion_Riesgo
    	aWriter.addJS_cr("evalRiesgoArray = [];");   	
    	aWriter.addJS_cr("evalPathArray = [];");
    	aWriter.addJS_cr("evalPathArray.push('" + objePath + "');");
    	aWriter.addJS_cr("evalPathArray.push('" + riesPath + "');");
    	
    	aWriter.addJS_cr("evalPathArray.push('" + fechIdenPath + "');");
    	aWriter.addJS_cr("evalPathArray.push('" + probInhePath + "');");
    	aWriter.addJS_cr("evalPathArray.push('" + impaInhePath + "');");
    	aWriter.addJS_cr("evalPathArray.push('" + expoInhePath + "');");
    	aWriter.addJS_cr("evalPathArray.push('" + clasInhePath + "');");
    	aWriter.addJS_cr("evalPathArray.push('" + contPath + "');");    	
    	aWriter.addJS_cr("evalPathArray.push('" + probresiPath + "');");
    	aWriter.addJS_cr("evalPathArray.push('" + impacresiPath + "');");
    	aWriter.addJS_cr("evalPathArray.push('" + exporesiPath + "');");
    	aWriter.addJS_cr("evalPathArray.push('" + clasresiPath + "');");
    	aWriter.addJS_cr("evalPathArray.push('" + controlsuPath + "');");
    	aWriter.addJS_cr("evalPathArray.push('" + responPath + "');");
    	aWriter.addJS_cr("evalPathArray.push('" + estadoPath + "');");    	
    	aWriter.addJS_cr("evalPathArray.push('" + fechimplePaht + "');");
    	aWriter.addJS_cr("evalPathArray.push('" + kri1Path + "');");    	
    	aWriter.addJS_cr("evalPathArray.push('" + kri2Paht + "');");
   
    	aWriter.addJS_cr("resetComplexList('" + evarPrefix + "', evalPathArray, '" + EVALUACIONR_ID + "');");
    	
    	if(evaluacion != null) {
	    	for(Object eval : evaluacion) {
	    		
	    		String objetivo = String.valueOf(node.getNode(Path.parse(objePath)).executeRead(eval));	    		
	    		if(objetivo.isBlank() || objetivo.equals("null"))
	    			objetivo = "null";
	    		else
	    			objetivo = "{key: '" + objetivo + "', label: '', previewURL: undefined}";
	    		
	    		String riesgo = String.valueOf(node.getNode(Path.parse(riesPath)).executeRead(eval));
	    		if(riesgo.isBlank() || riesgo.equals("null"))
	    			riesgo = "null";
	    		else
	    			riesgo = "{key: '" + riesgo + "', label: '', previewURL: undefined}";
	    		
	    		String fecha = String.valueOf(node.getNode(Path.parse(fechIdenPath)).executeRead(eval));
	    		if(fecha.isBlank() || fecha.equals("null"))
	    			fecha = "null";
	    		else
	    			fecha = "new Date('" + fecha + "')";
	    		
	    		String probInhe = String.valueOf(node.getNode(Path.parse(probInhePath)).executeRead(eval));
	    		if(probInhe.isBlank() || probInhe.equals("null"))
	    			probInhe = "null";
	    		else
	    			probInhe = "'" + probInhe + "'";
	    		
	    		String impaInhe = String.valueOf(node.getNode(Path.parse(impaInhePath)).executeRead(eval));
	    		if(impaInhe.isBlank() || impaInhe.equals("null"))
	    			impaInhe = "null";
	    		else
	    			impaInhe = "'" + impaInhe + "'";
	    		
	    		String expoInhe = String.valueOf(node.getNode(Path.parse(expoInhePath)).executeRead(eval));
	    		if(expoInhe.isBlank() || expoInhe.equals("null"))
	    			expoInhe = "null";
	    		else
	    			expoInhe = "'" + expoInhe + "'";
	    		
	    		String clasInhe = String.valueOf(node.getNode(Path.parse(clasInhePath)).executeRead(eval));
	    		if(clasInhe.isBlank() || clasInhe.equals("null"))
	    			clasInhe = "null";
	    		else
	    			clasInhe = "'" + clasInhe + "'";
	    			    		
	    		String control = String.valueOf(node.getNode(Path.parse(contPath)).executeRead(eval));	    		
	    		if(control.isBlank() || control.equals("null"))
	    			control = "null";
	    		else
	    			control = "{key: '" + control + "', label: '', previewURL: undefined}";
	    		
	    		
	    		String probResi = String.valueOf(node.getNode(Path.parse(probresiPath)).executeRead(eval));
	    		if(probResi.isBlank() || probResi.equals("null"))
	    			probResi = "null";
	    		else
	    			probResi = "'" + probResi + "'";
	    		
	    		String impaResi = String.valueOf(node.getNode(Path.parse(impacresiPath)).executeRead(eval));
	    		if(impaResi.isBlank() || impaResi.equals("null"))
	    			impaResi = "null";
	    		else
	    			impaResi = "'" + impaResi + "'";
	    		
	    		String expoResi = String.valueOf(node.getNode(Path.parse(exporesiPath)).executeRead(eval));
	    		if(expoResi.isBlank() || expoResi.equals("null"))
	    			expoResi = "null";
	    		else
	    			expoResi = "'" + expoResi + "'";
	    		
	    		String clasResi = String.valueOf(node.getNode(Path.parse(clasresiPath)).executeRead(eval));
	    		if(clasResi.isBlank() || clasResi.equals("null"))
	    			clasResi = "null";
	    		else
	    			clasResi = "'" + clasResi + "'";
	    		
	    		String accioncon = String.valueOf(node.getNode(Path.parse(controlsuPath)).executeRead(eval));
	    		if(accioncon.isBlank() || accioncon.equals("null"))
	    			accioncon = "null";
	    		else
	    			accioncon = "{key: '" + accioncon + "', label: '', previewURL: undefined}";
	    		
	    		String respon = String.valueOf(node.getNode(Path.parse(responPath)).executeRead(eval));
	    		if(respon.isBlank() || respon.equals("null"))
	    			respon = "null";
	    		else
	    			respon = "{key: '" + respon + "', label: '', previewURL: undefined}";
	    		
	    		String estad = String.valueOf(node.getNode(Path.parse(estadoPath)).executeRead(eval));
	    		if(estad.isBlank() || estad.equals("null"))
	    			estad = "null";
	    		else
	    			estad = "{key: '" + estad + "', label: '', previewURL: undefined}";
	    		
	    		String fechastimada = String.valueOf(node.getNode(Path.parse(fechimplePaht)).executeRead(eval));
	    		if(fechastimada.isBlank() || fechastimada.equals("null"))
	    			fechastimada = "null";
	    		else
	    			fechastimada = "new Date('" + fechastimada + "')";
	    		
	    		String KRI1 = String.valueOf(node.getNode(Path.parse(kri1Path)).executeRead(eval));
	    		if(KRI1.isBlank() || KRI1.equals("null"))
	    			KRI1 = "null";
	    		else
	    			KRI1 = "{key: '" + KRI1 + "', label: '', previewURL: undefined}";
	    		
	    		String KRI2 = String.valueOf(node.getNode(Path.parse(kri2Paht)).executeRead(eval));
	    		if(KRI2.isBlank() || KRI2.equals("null"))
	    			KRI2 = "null";
	    		else
	    			KRI2 = "{key: '" + KRI2 + "', label: '', previewURL: undefined}";

	    		
				aWriter.addJS_cr("evalRiesgo = {");
				aWriter.addJS_cr(objePath + ": " + objetivo + ",");
				aWriter.addJS_cr(riesPath + ": " + riesgo + ",");
				aWriter.addJS_cr(fechIdenPath + ": " + fecha + ",");
				aWriter.addJS_cr(probInhePath + ": " + probInhe + ",");
				aWriter.addJS_cr(impaInhePath + ": " + impaInhe + ",");
				aWriter.addJS_cr(expoInhePath + ": " + expoInhe + ",");
				aWriter.addJS_cr(clasInhePath + ": " + clasInhe + ",");
				aWriter.addJS_cr(contPath + ": " + control + ",");				
				aWriter.addJS_cr(probresiPath + ": " + probResi + ",");
				aWriter.addJS_cr(impacresiPath + ": " + impaResi + ",");
				aWriter.addJS_cr(exporesiPath + ": " + expoResi + ",");
				aWriter.addJS_cr(clasresiPath + ": " + clasResi + ",");					
				aWriter.addJS_cr(controlsuPath + ": " + accioncon + ",");				
				aWriter.addJS_cr(responPath + ": " + respon + ",");
				aWriter.addJS_cr(estadoPath + ": " + estad + ",");
				aWriter.addJS_cr(fechimplePaht + ": " + fechastimada + ",");				
				aWriter.addJS_cr(kri1Path + ": " + KRI1 + ",");
				aWriter.addJS_cr(kri2Paht + ": " + KRI2);		
				
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("evalRiesgoArray.push(evalRiesgo);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setComplexList('" + evarPrefix + "', evalPathArray, '" + EVALUACIONR_ID + "', evalRiesgoArray);");	
	    	
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
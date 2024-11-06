package com.alldatum.ebx.bhd.form.gdpr;



import com.alldatum.ebx.bhd.util.AdaptationUtil;
import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.interactions.InteractionHelper.ParametersMap;
import com.orchestranetworks.interactions.SessionInteraction;
import com.orchestranetworks.schema.Path;

import com.orchestranetworks.service.ServiceKey;
import com.orchestranetworks.ui.base.JsFunctionCall;
import com.orchestranetworks.ui.form.widget.UIDropDownList;
import com.orchestranetworks.ui.form.widget.UITextBox;
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

public class EvaluacionRiesgoUserService<T extends TableEntitySelection> implements UserService<T> {
	
	private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");	
	
	@Override
	public UserServiceEventOutcome processEventOutcome(UserServiceProcessEventOutcomeContext<T> aContext,
			UserServiceEventOutcome anOutcome) {

		return anOutcome;
	}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<T> aContext, UserServiceDisplayConfigurator aConfigurator) {
		
		
		aConfigurator.setContent(this::pane);
		aConfigurator.setDefaultButtons(this::save);

		
	}
	
	private UserServiceEventOutcome save(UserServiceEventContext aContext) {

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
		//Path Inherente
		Path ProbainhePath = Path.parse("Probabilidad_inhe");//seleciona numero
		Path  ImpacinhePath= Path.parse("Impacto_inhe");
		Path ExpinheRPath = Path.parse("Expo_inherente");//muestra resultado
		Path ClassinhePath = Path.parse("Clasificacion2");//color
		//Path residual
		Path ProbaresiPath = Path.parse("Probabilidad_residual");//seleciona numero
		Path  ImpacresiPath= Path.parse("Impacto_residual");
		Path ExpresiRPath = Path.parse("Exposicion_residual_R");//muestra resultado
		Path ClassresiPath = Path.parse("Clasificacion5");//color
		Path conGenPath = Path.parse("Controles_genericos");//Path controlesgenericos
		
		aWriter.add_cr("<h3>Evaluacion de Riesgo</h3>");	
		aWriter.addFormRow(Path.parse("Numero_riesgo"));
		aWriter.addFormRow(Path.parse("RiesgoNAIP"));
		aWriter.addFormRow(Path.parse("Fecha_iden"));
		
		//----------------------------------------
				
		//Prefixed Path Inherente
		String inhProPrefix = aWriter.getPrefixedPath(ProbainhePath).format();
		String inhImpcPrefix = aWriter.getPrefixedPath(ImpacinhePath).format();		
		String inhExpPrefix = aWriter.getPrefixedPath(ExpinheRPath).format();
		String inhClaPrefix = aWriter.getPrefixedPath(ClassinhePath).format();
		
		//Prefixed Path residual
		String ProbresiPrefix = aWriter.getPrefixedPath(ProbaresiPath).format();
		String ImpcresiPrefix = aWriter.getPrefixedPath(ImpacresiPath).format();		
		String ExporesiPrefix = aWriter.getPrefixedPath(ExpresiRPath).format();
		String ClassresiPrefix = aWriter.getPrefixedPath(ClassresiPath).format();
		
		//Widgets Inherente
		UIDropDownList inhPro = aWriter.newDropDownList(ProbainhePath);
		inhPro.setActionOnAfterValueChanged(JsFunctionCall.on("updateExposicionInherenteProbabilidad"));
		aWriter.addFormRow(inhPro);
		
		UIDropDownList inhTipDat = aWriter.newDropDownList(ImpacinhePath);
		inhTipDat.setActionOnAfterValueChanged(JsFunctionCall.on("updateAffectedFieldsInherent",inhExpPrefix));	
		aWriter.addFormRow(inhTipDat);	
		aWriter.addFormRow(ExpinheRPath);
		
		UITextBox inhCla = aWriter.newTextBox(ClassinhePath);
		String inhClaValue = (String) aPaneContext.getValueContext(OBJECT_KEY, ClassinhePath).getValue();
		String inhClaColor = "#FFFFFF";
		String conGenDivId = "conGenDiv";
		String conGenDisplay = "display:none";
		if(inhClaValue != null) {
			if(inhClaValue.equals("ACEPTABLE")) {
				inhClaColor = "#008000";
			}else if(inhClaValue.equals("ATENCIÓN")) {
				inhClaColor = "#FFFF00";
				conGenDisplay = "display:block";
			}else if(inhClaValue.equals("INACEPTABLE")) {
				inhClaColor = "#FF0000";
				conGenDisplay = "display:block";
			}
		}
		
		inhCla.setBackgroundColor(inhClaColor);
		inhCla.setActionOnAfterValueChanged(JsFunctionCall.on("displayDiv", conGenDivId));
		aWriter.addFormRow(inhCla);	
		
		aWriter.add("<div ");
		aWriter.addSafeAttribute("id", conGenDivId);
		aWriter.addSafeAttribute("style", conGenDisplay);
		aWriter.add_cr(">");
		aWriter.addFormRow(conGenPath);
		aWriter.add_cr("</div>");

		
		
		
		
		
		
		//Widgets Residual
		UIDropDownList resiPro = aWriter.newDropDownList(ProbaresiPath);
		resiPro.setActionOnAfterValueChanged(JsFunctionCall.on("updateExposicionResidualProbabilidad"));
		aWriter.addFormRow(resiPro);
		
		
		UIDropDownList resiImp = aWriter.newDropDownList(ImpacresiPath);
		resiImp.setActionOnAfterValueChanged(JsFunctionCall.on("updateAffectedFieldsResidual",ExporesiPrefix));	
		aWriter.addFormRow(resiImp);
		
	
		
		//aWriter.addFormRow(inhExpTipDatPath);
		aWriter.addFormRow(ExpresiRPath);
		
		UITextBox resiCla = aWriter.newTextBox(ClassresiPath);
		String resiClaValue = (String) aPaneContext.getValueContext(OBJECT_KEY, ClassresiPath).getValue();
		String resiClaColor = "ACEPTABLE".equals(resiClaValue) ? "#008000" : 
			"ATENCIÓN".equals(resiClaValue) ? "#FFFF00" :
				"INACEPTABLE".equals(resiClaValue) ? "#FF0000" : "#FFFFFF";
		resiCla.setBackgroundColor(resiClaColor);
		//inhCla.setEditorDisabled(true);
		
		aWriter.addFormRow(resiCla);
		
		
		//ultimos widgets
		
		aWriter.addFormRow(Path.parse("Accion_Control_sugerido_obje"));
		aWriter.addFormRow(Path.parse("Responsable"));
		aWriter.addFormRow(Path.parse("Estado"));
		aWriter.addFormRow(Path.parse("Fecha_Estimada_Implementacion2"));
		aWriter.addFormRow(Path.parse("KRI1_por_objetivo"));
		aWriter.addFormRow(Path.parse("KRI2_por_riesgo"));
		
		
		
		
		
		
		
		
		
		//-----------------------------------------------------
		//Specific Javascript Inherente
				aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + inhProPrefix + "').getEditor().getInput().readOnly = true;");
				aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + inhImpcPrefix + "').getEditor().getInput().readOnly = true;");				
				aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + inhExpPrefix + "').getEditor().getInput().readOnly = true;");
				aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + inhClaPrefix + "').getEditor().getInput().readOnly = true;");
				
				aWriter.addJS_cr("function updateExposicionInherenteProbabilidad(probValue){");
				aWriter.addJS_cr("  console.log('probValue:', probValue);");
		        aWriter.addJS_cr("const expInh = updateFieldProbability(probValue, '" + inhImpcPrefix + "', '" + inhExpPrefix + "');");
		        aWriter.addJS_cr("  console.log('expInh:', expInh);");
		        aWriter.addJS_cr("updateClassification(expInh, '" + inhClaPrefix + "');");
		        aWriter.addJS_cr("}");
		        
		        aWriter.addJS_cr("function updateAffectedFieldsInherent(originValue, targetPath){");
		        aWriter.addJS_cr("console.log('originValue:', originValue);");
		        aWriter.addJS_cr("console.log('targetPath:', targetPath);");
		        aWriter.addJS_cr("const expInh = updateField(originValue, '" + inhExpPrefix + "', '" + inhProPrefix + "');");
		        aWriter.addJS_cr("console.log('expInh:', expInh);"); // Imprimir expInh
		        aWriter.addJS_cr("updateClassification(expInh, '" + inhClaPrefix + "');");
		        aWriter.addJS_cr("console.log('expInh:', expInh);");
		        aWriter.addJS_cr("console.log('inhClaPrefix:', '" + inhClaPrefix + "');");
		        aWriter.addJS_cr("}");
		        
		      //Specific Javascript Inherente		
				aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + ProbresiPrefix + "').getEditor().getInput().readOnly = true;");
				aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + ImpcresiPrefix + "').getEditor().getInput().readOnly = true;");				
				aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + ExporesiPrefix + "').getEditor().getInput().readOnly = true;");
				aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + ClassresiPrefix + "').getEditor().getInput().readOnly = true;");
				
				aWriter.addJS_cr("function updateExposicionResidualProbabilidad(probValue){");
		        aWriter.addJS_cr("const expresi = updateFieldProbability(probValue, '" + ImpcresiPrefix + "', '" + ExporesiPrefix + "');");
		        aWriter.addJS_cr("updateClassification(expresi, '" + ClassresiPrefix + "');");
		        aWriter.addJS_cr("}");
		        
		        aWriter.addJS_cr("function updateAffectedFieldsResidual(originValue, targetPath){");
		        aWriter.addJS_cr("const expresi = updateField(originValue, '" + ExporesiPrefix + "', '" + ProbresiPrefix + "');");
		        aWriter.addJS_cr("updateClassification(expresi, '" + ClassresiPrefix + "');");
		        aWriter.addJS_cr("}");
        
		        
		        
		        
		        //General 

		        aWriter.addJS_cr("function displayDiv(value, divId){");
		        aWriter.addJS_cr("const divElement = document.getElementById(divId);");
		        aWriter.addJS_cr("if(value == 'ATENCIÓN' || value == 'INACEPTABLE'){");
		        aWriter.addJS_cr("divElement.style.display = 'block';");
		        aWriter.addJS_cr("} else {");
		        aWriter.addJS_cr("divElement.style.display = 'none';");
		        aWriter.addJS_cr("}");

		        aWriter.addJS_cr("}");
		        
		        aWriter.addJS_cr("function updateFieldProbability(probValue, originPath, targetPath){");
		        aWriter.addJS_cr("  console.log('Valor de probValueF2:', probValue);");
		        aWriter.addJS_cr("  console.log('Valor de originPath:', originPath);");
		        aWriter.addJS_cr("  console.log('Valor de targetPath:', targetPath);");
		        aWriter.addJS_cr("const originValue = ebx_form_getValue(originPath);");		        
		        aWriter.addJS_cr("const targetValue = (probValue * 4 + originValue * 6) / 10;");		
		      //  aWriter.addJS_cr("const roundedValue = targetValue.toFixed(1);"); // Redondear a 1 decimal
		        aWriter.addJS_cr("ebx_form_setValue(targetPath, targetValue);");
		        aWriter.addJS_cr("return targetValue;");
		        aWriter.addJS_cr("}");
		        
		        aWriter.addJS_cr("function updateField(originValue, targetPath, probPath){");
		        aWriter.addJS_cr("const probValue = ebx_form_getValue(probPath);");
		        aWriter.addJS_cr("const targetValue = (probValue * 4 + originValue * 6) / 10;");
		        //aWriter.addJS_cr("const roundedValue = targetValue.toFixed(1);"); // Redondear a 1 decimal
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
		        aWriter.addJS_cr("}");
              
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

	@Override
	public void validate(UserServiceValidateContext<T> aContext) {

		
	}

}
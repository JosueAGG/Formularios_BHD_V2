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


public class EARUserService <T extends TableEntitySelection> implements UserService<T>
{
	private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");


	@Override
	public UserServiceEventOutcome processEventOutcome(UserServiceProcessEventOutcomeContext<T> aContext,
			UserServiceEventOutcome anOutcome) {
		
		return anOutcome;
	}

	@Override
	public void setupDisplay(UserServiceSetupDisplayContext<T> anOutcome, UserServiceDisplayConfigurator aConfigurator) {
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
	
	
     private void pane(UserServicePaneContext aPaneContext, UserServicePaneWriter aWriter) 
     {
		 
		aWriter.setCurrentObject(OBJECT_KEY);
		
		//Paths normales
		Path pathNumeroRiesgo= Path.parse("Numero_riesgo");
		Path pathRiesgo= Path.parse("RiesgoARE");
		Path pathFechaIdentifiacion = Path.parse("Fecha_iden");
		
		aWriter.addFormRow(pathNumeroRiesgo);
		aWriter.addFormRow(pathRiesgo);
		aWriter.addFormRow(pathFechaIdentifiacion);
		
		// Paths de la parte dinamica
		//De entrada
		Path pathProbabilidadInherente = Path.parse("Probabilidad_inhe");
		Path pathTipoDeDatos = Path.parse("Tipo_de_datos");
		Path pathCantidadDeDatos=Path.parse("Cantidad_de_datos");
		Path pathCategoriaDeTitulares=Path.parse("Categoria_de_titulare");
		Path pathCantitdadDeTitulares= Path.parse("Cantidad_de_titulares");
		Path pathTipoDeImpacto= Path.parse("Tipo_de_impacto");
		//De salida 
		Path pathExposisionInherenteTipoDeDato =Path.parse("Exposicion_inerente_tipo_de_dato");
		Path pathExposisionInherenteCantidadDatos= Path.parse("Exposicion_inherente_cantidad_de_datos");
		Path pathExposisionInherenteCategoriaTitulares= Path.parse("Exposicion_inherente_categoria_de_titulares");
		Path pathExposisionInherenteCantidadTitulares= Path.parse("Exposicion_inherente_cantidad_de_los_titulares");
		Path pathExposicionInherenteTipoDeImpacto=Path.parse("Exposicion_inherente_tipo_de_impacto");
		Path pathExposisionInherente = Path.parse("ExpoIneherenteResult");//OJO cambiar segun el modelo a usar
		
		Path pathClasificacion = Path.parse("Clasificacion2");
		
		//Prefix de los paths de la parte dinamica
		
		//De entrada
				String prefixProbabilidadInherente = aWriter.getPrefixedPath(pathProbabilidadInherente).format();
				String prefixTipoDeDatos = aWriter.getPrefixedPath(pathTipoDeDatos).format();
				String prefixCantidadDeDatos=aWriter.getPrefixedPath(pathCantidadDeDatos).format();
				String prefixCategoriaDeTitulares=aWriter.getPrefixedPath(pathCategoriaDeTitulares).format();
				String prefixCantitdadDeTitulares= aWriter.getPrefixedPath(pathCantitdadDeTitulares).format();;
				String prefixTipoDeImpacto= aWriter.getPrefixedPath(pathTipoDeImpacto).format();
		//De salida 
				String prefixExposisionInherenteTipoDeDato = aWriter.getPrefixedPath(pathExposisionInherenteTipoDeDato).format();
				String prefixExposisionInherenteCantidadDatos = aWriter.getPrefixedPath(pathExposisionInherenteCantidadDatos).format();
				String prefixExposisionInherenteCategoriaTitulares = aWriter.getPrefixedPath(pathExposisionInherenteCategoriaTitulares).format();
				String prefixExposisionInherenteCantidadTitulares = aWriter.getPrefixedPath(pathExposisionInherenteCantidadTitulares).format();
				String prefixExposicionInherenteTipoDeImpacto = aWriter.getPrefixedPath(pathExposicionInherenteTipoDeImpacto).format();
				String prefixExposisionInherente = aWriter.getPrefixedPath(pathExposisionInherente).format();
				
				String prefixClasificacion = aWriter.getPrefixedPath(pathClasificacion).format();
		
		//Widgets 
				UIDropDownList inhPro = aWriter.newDropDownList(pathProbabilidadInherente);
				inhPro.setActionOnAfterValueChanged(JsFunctionCall.on("updateAllFieldsInherent"));
				aWriter.addFormRow(inhPro);
		
				UIDropDownList inhTipDat = aWriter.newDropDownList(pathTipoDeDatos);
				inhTipDat.setActionOnAfterValueChanged(JsFunctionCall.on("updateAffectedFieldsInherent", prefixExposisionInherenteTipoDeDato));	
				aWriter.addFormRow(inhTipDat);
				
				UIDropDownList inhCanDat = aWriter.newDropDownList(pathCantidadDeDatos);
				inhCanDat.setActionOnAfterValueChanged(JsFunctionCall.on("updateAffectedFieldsInherent",prefixExposisionInherenteCantidadDatos));	
				aWriter.addFormRow(inhCanDat);
     
				UIDropDownList inhCatTit = aWriter.newDropDownList(pathCategoriaDeTitulares);
				inhCatTit.setActionOnAfterValueChanged(JsFunctionCall.on("updateAffectedFieldsInherent", prefixExposisionInherenteCategoriaTitulares));	
				aWriter.addFormRow(inhCatTit);
                  
				UIDropDownList inhCanTit = aWriter.newDropDownList(pathCantitdadDeTitulares);
				inhCanTit.setActionOnAfterValueChanged(JsFunctionCall.on("updateAffectedFieldsInherent", prefixExposisionInherenteCantidadTitulares));	
				aWriter.addFormRow(inhCanTit);
				
				UIDropDownList inhTipImp = aWriter.newDropDownList(pathTipoDeImpacto);
				inhTipImp.setActionOnAfterValueChanged(JsFunctionCall.on("updateAffectedFieldsInherent", prefixExposisionInherenteTipoDeDato));	
				aWriter.addFormRow(inhTipImp);
				
                aWriter.addFormRow(pathExposisionInherenteTipoDeDato);
                aWriter.addFormRow(pathExposisionInherenteCantidadDatos);
                aWriter.addFormRow(pathExposisionInherenteCategoriaTitulares);
                aWriter.addFormRow(pathExposisionInherenteCantidadTitulares);
                aWriter.addFormRow(pathExposicionInherenteTipoDeImpacto);
                aWriter.addFormRow(pathExposisionInherente);
                
            	UITextBox inhCla = aWriter.newTextBox(pathClasificacion);
        		String inhClaValue = (String) aPaneContext.getValueContext(OBJECT_KEY, pathClasificacion).getValue();
        		String inhClaColor = "ACEPTABLE".equals(inhClaValue) ? "#008000" : 
        			"ATENCIÓN".equals(inhClaValue) ? "#FFFF00" :
        				"INACEPTABLE".equals(inhClaValue) ? "#FF0000" : "#FFFFFF";
        		inhCla.setBackgroundColor(inhClaColor);
        		aWriter.addFormRow(inhCla);
     
               
        		//Paths del probabilidad residual
        		//Entrada 
        		Path pathProbabilidadResidual = Path.parse("Probabilidad_residual");
        		Path pathTipoDatoResidual = Path.parse("Tipo_de_dato_R");
        		Path pathCantidadDeDatosResidual= Path.parse("Cantidad_de_datos_R");
        		Path pathCategoríaDeLosTitularesResidual=Path.parse("Categoria_de_los_titulares_R");
        		Path pathCantidadDeLosTitularesResidual=Path.parse("Cantidad_de_los_titulares_r");
        		Path pathTipoDeImpactoResidual=Path.parse("Tipo_de_impacto_R");
        		//Salida
        		Path pathExpoResidualTipoDeDato=Path.parse("Exposicion_recidual_tipo_de_dato");
        		Path pathExpoResidualCantidadDatos= Path.parse("Exposicion_recidual_cantidad_de_datos");
        		Path pathExpoResidualCategoriaTitulares=Path.parse("Exposicion_recidual_categoria_de_los_titulares");
        		Path pathExpoResidualCantidadTitulares= Path.parse("Exposicion_recidual_cantidad_de_los_titulares");
        	    Path pathExpoResidualTipoImpacto = Path.parse("Exposicion_recidual_tipo_de_impacto");
        		
        		Path pathExpoResidual= Path.parse("ExposicionResidualResultado");//OJO CAMBIAR SEGUN MODELO A USAR
        		Path pathClasificacionResidual = Path.parse("Clasificacion5");
        		
        		//Prefix de los campos del residual
        		//-Entrada
        		String prefixProbabilidadResidual = aWriter.getPrefixedPath(pathProbabilidadResidual).format();
        		String prefixTipoDatoResidual = aWriter.getPrefixedPath(pathTipoDatoResidual).format();
        		String prefixCantidadDeDatosResidual = aWriter.getPrefixedPath(pathCantidadDeDatosResidual).format();
        		String prefixCategoríaDeLosTitularesResidual = aWriter.getPrefixedPath(pathCategoríaDeLosTitularesResidual).format();
        		String prefixCantidadDeLosTitularesResidual = aWriter.getPrefixedPath(pathCantidadDeLosTitularesResidual).format();
        		String prefixTipoDeImpactoResidual = aWriter.getPrefixedPath(pathTipoDeImpactoResidual).format();
        		//-Salida
        		String prefixExpoResidualTipoDeDato = aWriter.getPrefixedPath(pathExpoResidualTipoDeDato).format();
        		String prefixExpoResidualCantidadDatos = aWriter.getPrefixedPath(pathExpoResidualCantidadDatos).format();
        		String prefixExpoResidualCategoriaTitulares = aWriter.getPrefixedPath(pathExpoResidualCategoriaTitulares).format();
        		String prefixExpoResidualCantidadTitulares = aWriter.getPrefixedPath(pathExpoResidualCantidadTitulares).format();
        		String prefixExpoResidualTipoImpacto = aWriter.getPrefixedPath(pathExpoResidualTipoImpacto).format();
        		
        		String prefixExpoResidual = aWriter.getPrefixedPath(pathExpoResidual).format();
        		String prefixClasificacionResidual= aWriter.getPrefixedPath(pathClasificacionResidual).format();
        		
        		//Widgets
        		UIDropDownList resiPro = aWriter.newDropDownList(pathProbabilidadResidual);
        		resiPro.setActionOnAfterValueChanged(JsFunctionCall.on("updateAllFieldsResidual"));
        		aWriter.addFormRow(resiPro);
        		
        		UIDropDownList resiTipDat = aWriter.newDropDownList(pathTipoDatoResidual);
        		resiTipDat.setActionOnAfterValueChanged(JsFunctionCall.on("updateAffectedFieldsResidual", prefixExpoResidualTipoDeDato));	
        		aWriter.addFormRow(resiTipDat);
        		
        		UIDropDownList resiCanDat = aWriter.newDropDownList(pathCantidadDeDatosResidual);
        		resiCanDat.setActionOnAfterValueChanged(JsFunctionCall.on("updateAffectedFieldsResidual", prefixExpoResidualCantidadDatos));	
        		aWriter.addFormRow(resiCanDat);
        		
        		UIDropDownList resiCatTit = aWriter.newDropDownList(pathCategoríaDeLosTitularesResidual);
        		resiCatTit.setActionOnAfterValueChanged(JsFunctionCall.on("updateAffectedFieldsResidual", prefixExpoResidualCategoriaTitulares));	
        		aWriter.addFormRow(resiCatTit);
        		
        		UIDropDownList resiCanTit = aWriter.newDropDownList(pathCantidadDeLosTitularesResidual);
        		resiCanTit.setActionOnAfterValueChanged(JsFunctionCall.on("updateAffectedFieldsResidual", prefixExpoResidualCantidadTitulares));	
        		aWriter.addFormRow(resiCanTit);
        		
        		UIDropDownList resiTipImp = aWriter.newDropDownList(pathTipoDeImpactoResidual);
        		resiTipImp.setActionOnAfterValueChanged(JsFunctionCall.on("updateAffectedFieldsResidual", prefixExpoResidualTipoImpacto));	
        		aWriter.addFormRow(resiTipImp);
        		
        		aWriter.addFormRow(pathExpoResidualTipoDeDato);
        		aWriter.addFormRow(pathExpoResidualCantidadDatos);
        		aWriter.addFormRow(pathExpoResidualCategoriaTitulares);
        		aWriter.addFormRow(pathExpoResidualCantidadTitulares);
        		aWriter.addFormRow(pathExpoResidualTipoImpacto);
        		aWriter.addFormRow(pathExpoResidual);
        		
        		UITextBox resCla = aWriter.newTextBox(pathClasificacionResidual);
        		String resClaValue = (String) aPaneContext.getValueContext(OBJECT_KEY, pathClasificacionResidual).getValue();
        		String resClaColor = "ACEPTABLE".equals(resClaValue) ? "#008000" : 
        			"ATENCIÓN".equals(resClaValue) ? "#FFFF00" :
        				"INACEPTABLE".equals(resClaValue) ? "#FF0000" : "#FFFFFF";
        		inhCla.setBackgroundColor(resClaColor);
        		aWriter.addFormRow(resCla);
        		
        		//Ultimos campos comunes
        		
        		aWriter.addFormRow(Path.parse("Accion_Control_sugerido_obje"));
        		aWriter.addFormRow(Path.parse("Responsable"));
        		aWriter.addFormRow(Path.parse("Estado"));
        		aWriter.addFormRow(Path.parse("Fecha_Estimada_Implementacion2"));
        		aWriter.addFormRow(Path.parse("KRI1_por_objetivo"));
        		aWriter.addFormRow(Path.parse("KRI2_por_riesgo"));
        		
        		
        		
        		
        		//Specific Javascript
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixExposisionInherenteTipoDeDato + "').getEditor().getInput().readOnly = true;");
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixExposisionInherenteCantidadDatos + "').getEditor().getInput().readOnly = true;");
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixExposisionInherenteCategoriaTitulares + "').getEditor().getInput().readOnly = true;");
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixExposisionInherenteCantidadTitulares + "').getEditor().getInput().readOnly = true;");
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixExposicionInherenteTipoDeImpacto + "').getEditor().getInput().readOnly = true;");
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixExposisionInherente + "').getEditor().getInput().readOnly = true;");
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixClasificacion + "').getEditor().getInput().readOnly = true;");
              
        		aWriter.addJS_cr("function updateAllFieldsInherent(probValue){");
                aWriter.addJS_cr("const a = updateFieldProbability(probValue, '" + prefixTipoDeDatos + "', '" + prefixExposisionInherenteTipoDeDato + "');");
                aWriter.addJS_cr("const b = updateFieldProbability(probValue, '" + prefixCantidadDeDatos + "', '" + prefixExposisionInherenteCantidadDatos+ "');");
                aWriter.addJS_cr("const c = updateFieldProbability(probValue, '" + prefixCategoriaDeTitulares + "', '" +prefixExposisionInherenteCategoriaTitulares + "');");
                aWriter.addJS_cr("const d = updateFieldProbability(probValue, '" + prefixCantitdadDeTitulares + "', '" + prefixExposisionInherenteCantidadTitulares + "');");
                aWriter.addJS_cr("const e = updateFieldProbability(probValue, '" +prefixTipoDeImpacto + "', '" + prefixExposicionInherenteTipoDeImpacto + "');");
                aWriter.addJS_cr("const highest = updateHighest('" + prefixExposisionInherente + "', a, b, c, d, e);");
                aWriter.addJS_cr("updateClassification(highest, '" + prefixClasificacion + "');");
                aWriter.addJS_cr("}");
      
                aWriter.addJS_cr("function updateAffectedFieldsInherent(originValue, targetPath){");
                aWriter.addJS_cr("updateField(originValue, targetPath, '" + prefixProbabilidadInherente + "');");
                aWriter.addJS_cr("const a = ebx_form_getValue('" + prefixExposisionInherenteTipoDeDato + "');");
                aWriter.addJS_cr("const b = ebx_form_getValue('" + prefixExposisionInherenteCantidadDatos + "');");
                aWriter.addJS_cr("const c = ebx_form_getValue('" + prefixExposisionInherenteCategoriaTitulares + "');");
                aWriter.addJS_cr("const d = ebx_form_getValue('" + prefixExposisionInherenteCantidadTitulares + "');");
                aWriter.addJS_cr("const e = ebx_form_getValue('" + prefixExposicionInherenteTipoDeImpacto+ "');");
                aWriter.addJS_cr("const highest = updateHighest('" + prefixExposisionInherente + "', a, b, c, d, e);");
                aWriter.addJS_cr("updateClassification(highest, '" + prefixClasificacion + "');");
                aWriter.addJS_cr("}");
     
                
                
                
                //--------- METODOS PARA LA PARTE RESIDUAL ---------
                
              //Specific Javascript Residual
                
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixExpoResidualTipoDeDato + "').getEditor().getInput().readOnly = true;");
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixExpoResidualCantidadDatos + "').getEditor().getInput().readOnly = true;");
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixExpoResidualCategoriaTitulares + "').getEditor().getInput().readOnly = true;");
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixExpoResidualCantidadTitulares + "').getEditor().getInput().readOnly = true;");
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixExpoResidualTipoImpacto + "').getEditor().getInput().readOnly = true;");
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixExpoResidual + "').getEditor().getInput().readOnly = true;");
        		aWriter.addJS_cr("EBX_FormNodeIndex.getFormNode('" + prefixClasificacionResidual + "').getEditor().getInput().readOnly = true;");
              
        		aWriter.addJS_cr("function updateAllFieldsResidual(probValue){");
                aWriter.addJS_cr("const a = updateFieldProbability(probValue, '" + prefixTipoDatoResidual + "', '" + prefixExpoResidualTipoDeDato + "');");
                aWriter.addJS_cr("const b = updateFieldProbability(probValue, '" + prefixCantidadDeDatosResidual + "', '" + prefixExpoResidualCantidadDatos+ "');");
                aWriter.addJS_cr("const c = updateFieldProbability(probValue, '" + prefixCategoríaDeLosTitularesResidual + "', '" +prefixExpoResidualCategoriaTitulares + "');");
                aWriter.addJS_cr("const d = updateFieldProbability(probValue, '" + prefixCantidadDeLosTitularesResidual + "', '" + prefixExpoResidualCantidadTitulares + "');");
                aWriter.addJS_cr("const e = updateFieldProbability(probValue, '" +prefixTipoDeImpactoResidual + "', '" + prefixExpoResidualTipoImpacto + "');");
                aWriter.addJS_cr("const highest = updateHighest('" + prefixExpoResidual + "', a, b, c, d, e);");
                aWriter.addJS_cr("updateClassification(highest, '" + prefixClasificacionResidual + "');");
                aWriter.addJS_cr("}");
      
                aWriter.addJS_cr("function updateAffectedFieldsResidual(originValue, targetPath){");
                aWriter.addJS_cr("updateField(originValue, targetPath, '" + prefixProbabilidadResidual + "');");
                aWriter.addJS_cr("const a = ebx_form_getValue('" + prefixExpoResidualTipoDeDato + "');");
                aWriter.addJS_cr("const b = ebx_form_getValue('" + prefixExpoResidualCantidadDatos + "');");
                aWriter.addJS_cr("const c = ebx_form_getValue('" + prefixExpoResidualCategoriaTitulares + "');");
                aWriter.addJS_cr("const d = ebx_form_getValue('" + prefixExpoResidualCantidadTitulares + "');");
                aWriter.addJS_cr("const e = ebx_form_getValue('" + prefixExpoResidualTipoImpacto+ "');");
                aWriter.addJS_cr("const highest = updateHighest('" + prefixExpoResidual + "', a, b, c, d, e);");
                aWriter.addJS_cr("updateClassification(highest, '" + prefixClasificacionResidual + "');");
                aWriter.addJS_cr("}");
                
                
                
              //----------------------------------------------------------------------------------------------
        		
                //General Javascript para ambos inherente y residual
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
     public void validate(UserServiceValidateContext<T> arg0) 
     {
	      // TODO Auto-generated method stub
	
     }	
	
}

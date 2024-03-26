package com.alldatum.ebx.bhd.form.gdpr;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


import com.onwbp.adaptation.Adaptation;
import com.onwbp.base.text.UserMessage;
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
import com.orchestranetworks.ui.form.widget.UIDropDownList;
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

public class FNFUserService <T extends TableEntitySelection> implements UserService<T> {

	private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");
	private static final ObjectKey RESPUESTAS_KEY = ObjectKey.forName("respuestas");	  
	private static final Path Riesgo = Path.parse("Medidas_garantias_privacidad_seguridad_adoptadas_funcion_riesgos");	  
	private static final Path Rriesgo = Path.parse("Controles");
	private static ValueContext vc;
	private static Adaptation dataset;


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
		
		aContext.save(OBJECT_KEY,RESPUESTAS_KEY);
		return null;
	}
	
	private void pane(UserServicePaneContext aPaneContext, UserServicePaneWriter aWriter) {
		
		aWriter.setCurrentObject(RESPUESTAS_KEY);		
		UIComboBox riesgosBox = aWriter.newComboBox(Riesgo);
		try {
		riesgosBox.setActionOnAfterValueChanged(JsFunctionCall.on("resetBanner"));
		}catch(Exception e) {e.getStackTrace();}
		UIComboBox resultadosBox= aWriter.newComboBox(Rriesgo);
		
		aWriter.setCurrentObject(OBJECT_KEY);
		
		aWriter.add_cr("<h3>Datos básicos del tratamiento nivel fases</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Identificador"));
		aWriter.addFormRow(Path.parse("Fecha_de_creacion_del_tratamiento"));
		aWriter.addFormRow(Path.parse("NombreT"));		
		aWriter.addFormRow(Path.parse("IdentificadorFAB"));
		aWriter.addFormRow(Path.parse("Procesos_operativos_o_comerciales"));
		aWriter.addFormRow(Path.parse("Codigo_proceso"));
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
		aWriter.addFormRow(Path.parse("MAceptable"));
		aWriter.addFormRow(Path.parse("CondisidAcep"));
		aWriter.addFormRow(Path.parse("NombreDRevisorAFNF"));
		aWriter.addFormRow(Path.parse("RolCargoFAF"));
		endBlock(aWriter);
		
		startBlock(aWriter, "block_revision_inaceptable", "inaceptable".equalsIgnoreCase(value));
		aWriter.addFormRow(Path.parse("Meniinacep"));
		aWriter.addFormRow(Path.parse("Concideaina"));
		aWriter.addFormRow(Path.parse("NombrerevisorinacFNF"));
		aWriter.addFormRow(Path.parse("RolCargoinaFNF"));
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


	@SuppressWarnings("unchecked")
	@Override
	public void setupObjectContext(UserServiceSetupObjectContext<T> aContext, UserServiceObjectContextBuilder aBuilder) {
		if(aContext.isInitialDisplay()) {
			dataset=aContext.getEntitySelection().getDataset();
			BeanDefinition def = aBuilder.createBeanDefinition();
			aBuilder.registerBean(RESPUESTAS_KEY, def);
			
			BeanElement riesgosElement= def.createElement(Riesgo, SchemaTypeName.XS_STRING);
			//BeanFacetTableRef predcate =riesgosElement.addFacetTableRef(dataset.getTable(Path.parse("/root/Catalogos/Matrices/Catalogo_de_Riesgos_de_Objetivos_de_Riesgo_conforme_objetivos/Riesgos_conforme_objetivos")));
			//predcate.setDisplayPattern("${./ID} - ${./Riesgo}");
			riesgosElement.addFacetConstraint((Class<? extends Constraint<?>>) EnumerationConstraint.class);
			riesgosElement.setMinOccurs(1);
			riesgosElement.setMinOccursErrorMessage("Obligatorio");
			riesgosElement.setLabel("Riesgo");
			
			
			
			BeanElement resriesgo= def.createElement(Rriesgo, SchemaTypeName.XS_STRING);
			//elemento= def.createElement(Rriesgo, SchemaTypeName.XS_STRING);
			resriesgo.setLabel("Respuesta al riesgo");
			BeanFacetTableRef respuestasFacet =resriesgo.addFacetTableRef(dataset.getTable(Path.parse("/root/Catalogos/Matrices/Catalogo_de_Riesgos_de_Objetivos_de_Riesgo_conforme_objetivos/Riesgos_conforme_objetivos")));
			respuestasFacet.setDisplayPattern("${./ID} - ${./Controles_genericos}");
			respuestasFacet.setFilter(CustomFilter.class);
			resriesgo.setMinOccurs(1);
			resriesgo.setMinOccursErrorMessage("OBLIGATORIO");
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
	public void validate(UserServiceValidateContext<T> arg0) {
		// TODO Auto-generated method stub
		
	}
	public static class EnumerationConstraint<T> implements ConstraintEnumeration<T> {

		@Override
		public void checkOccurrence(T arg0, ValueContextForValidation arg1) throws InvalidSchemaException {
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
		public String displayOccurrence(T arg0, ValueContext arg1, Locale arg2) throws InvalidSchemaException {
			// TODO Auto-generated method stub
			return null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<T> getValues(ValueContext aContext) throws InvalidSchemaException {
		Query<Tuple> query = dataset.createQuery("SELECT DISTINCT Riesgo FROM \"/root/Catalogos/Matrices/Catalogo_de_Riesgos_de_Objetivos_de_Riesgo_conforme_objetivos/Riesgos_conforme_objetivos\""); 
			
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
	
	public static class CustomFilter implements TableRefFilter {

		@Override
		public boolean accept(Adaptation anAdaptation, ValueContext aContext) {
			String banVal = (String) vc.getValue(Riesgo);
			String tablaVal = anAdaptation.getString(Path.parse("./Riesgo"));			
			if(banVal == null || tablaVal == null) { 
				
				return false;
				
			} else if(banVal.equals(tablaVal)) { 
				
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

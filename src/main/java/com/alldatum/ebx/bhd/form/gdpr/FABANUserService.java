package com.alldatum.ebx.bhd.form.gdpr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import com.alldatum.ebx.bhd.widget.UILinkableListWidgetFactory;
import com.alldatum.ebx.bhd.util.AdaptationUtil;
import com.alldatum.ebx.bhd.widget.UILinkableListWidget;
import com.onwbp.adaptation.Adaptation;
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

public class FABANUserService<T extends TableEntitySelection> implements UserService<T> {	
	
	private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");
	private static final ObjectKey RESPUESTAS_KEY = ObjectKey.forName("respuestas");	  
	private static final Path Riesgo = Path.parse("Riesgo");	  
	private static final Path Rriesgo = Path.parse("Como_respuesta_riesgos_del_tratamiento");
	private static final Path RESPUESTA_RIESGO_FOCUS = Path.parse("resriesgofocus");
	
	//Datos de revisor metodológico
		//Aceptable
	private static final Path MENU_ACEPTABLE_PATH = Path.parse("./Menu_aceptable");
	private static final Path CONDICION_ACEPTABLE_PATH = Path.parse("./ConcideracionesA");
	private static final Path NOMBRE_REVISORA_PATH = Path.parse("./NombreDRevisorA");
	private static final Path ROL_CARGOA_PATH = Path.parse("./RoloCargoFAB");
	

	//iInaceptable
	private static final Path MENU_INACEPTABLE_PATH = Path.parse("./MenuIna");
	private static final Path CONDICION_INACEPTABLE_PATH = Path.parse("./ConcideracionIna");
	private static final Path NOMBRE_REVISORI_PATH = Path.parse("./Nombrerevisorina");
	private static final Path ROL_CARGOI_PATH = Path.parse("./RolocargoinaFAB");

	private static Adaptation dataset;
	
	//prefic aceptable
    String MAceptablePrefix;
    String CAceptablePrefix;
    String NRvisorAPrefix;
    String RCRevisorAPrefix;

    String MInaceptablePrefix;
    String CInaceptablePrefix;
    String NRvisorIPrefix ;
    String RCRevisorIPrefix;
	
	@Override
	public UserServiceEventOutcome processEventOutcome(UserServiceProcessEventOutcomeContext<T> aContext,UserServiceEventOutcome anOutcome) {
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
		//aWriter.addFormRow(Path.parse("Rol2"));
		aWriter.addFormRow(Path.parse("CargoR"));
		aWriter.endExpandCollapseBlock();	
		//----------------------------------------
		aWriter.add_cr("<h3>Corresponsable</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Nombre_Co"));
		//aWriter.addFormRow(Path.parse("Rol"));
		aWriter.addFormRow(Path.parse("CargoCR"));
		aWriter.endExpandCollapseBlock();
		//----------------------------------------
		aWriter.add_cr("<h3>Analista</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Nombreanalista"));
		//aWriter.addFormRow(Path.parse("RolAnalista"));
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
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Actualizacion"), "block_actualizacion");
		aWriter.addFormRow(Path.parse("Mactualizacion"));
		aWriter.addFormRow(Path.parse("Periodicidad_de_actualizacion"));
		endBlock(aWriter);		
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
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Casos_de_uso"), "block_casos_uso");
		aWriter.addFormRow(Path.parse("Senales_caso_de_uso"));
		startBooleanBlock(aPaneContext, aWriter, Path.parse("VinculoeleSN"), "block_vinculoelesn");
		aWriter.addFormRow(Path.parse("Vinculo2"));
		startBooleanBlock(aPaneContext, aWriter, Path.parse("adjuntadoc"), "block_adjuntadoc");
		aWriter.addFormRow(Path.parse("doc"));
		endBlock(aWriter);
		endBlock(aWriter);
		endBlock(aWriter);		
		aWriter.addFormRow(Path.parse("Inventario_de_activos"));
		aWriter.endExpandCollapseBlock();		
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Levantamiento y/o generación de datos"), true);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Externo2"), "block_externo");
		aWriter.addFormRow(Path.parse("origendatos"));
		endBlock(aWriter);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Internos2"), "block_interno");
		aWriter.addFormRow(Path.parse("origendatosinternos"));
		endBlock(aWriter);		
		aWriter.addFormRow(Path.parse("Categorias_de_datos_inferidos_o_generados"));
		aWriter.endExpandCollapseBlock();				
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Acceso a los datos"), true);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Acceso_datos_2"), "block_acceso_datos");
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Internos3"), "block_internos");
		aWriter.addFormRow(Path.parse("./Internos/NombreCE"));
		aWriter.addFormRow(Path.parse("./Internos/Cargo"));
		aWriter.addFormRow(Path.parse("./Internos/Operacionejecuta"));
		aWriter.addFormRow(Path.parse("Nivel_de_acceso"));
		endBlock(aWriter);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Esternos3"), "block_esternos");
		aWriter.addFormRow(Path.parse("./Externos/NombreExt"));
		aWriter.addFormRow(Path.parse("./Externos/Rol4"));	
		aWriter.addFormRow(Path.parse("Permisos"));		
		endBlock(aWriter);
		endBlock(aWriter);		
		//aWriter.addFormRow(Path.parse("Nivel_de_acceso"));
		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Interfaces_con_otros_tratamientos"), "block_interfaces_tratamientos");
		aWriter.addFormRow(Path.parse("BusquedaT"));
		endBlock(aWriter);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Comunicacion_de_datos_Transferencias"), "block_comunicacion_datos");		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Cesion/CesionDecicion"), "block_cesion_decision");
			aWriter.addFormRow(Path.parse("./Cesion/DestinatarioCesion"));
			aWriter.addFormRow(Path.parse("./Cesion/FinalidadesCesion"));
			aWriter.addFormRow(Path.parse("./Cesion/Causaslegitimacioncesion"));
			endBlock(aWriter);			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Comunicacionencargado/Comunicacionencargadodecicion"), "block_comunicacion_encargado");
				aWriter.addFormRow(Path.parse("./Comunicacionencargado/Destinatariocomunicacionconencargado"));
				aWriter.addFormRow(Path.parse("./Comunicacionencargado/OperacionqueejecutaComuencargados"));						
				startBooleanBlock(aPaneContext, aWriter, Path.parse("./Comunicacionencargado/ContratoCconEncargado"), "block_contrato_encargado");
				aWriter.addFormRow(Path.parse("./Comunicacionencargado/NombreContratoCConEncarg"));
				aWriter.addFormRow(Path.parse("./Comunicacionencargado/FechaFormalizacionCconEncarg"));				
				startBooleanBlock(aPaneContext, aWriter, Path.parse("./Comunicacionencargado/VigenteCconEncarg"), "block_vigente_encargado");
				aWriter.addFormRow(Path.parse("./Comunicacionencargado/AdjuntarContratoCconEncarg"));
				endBlock(aWriter);		
				endBlock(aWriter);			
			endBlock(aWriter);			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Comunicacionconsubencargado/Comunicacionsubencargadodecicion"), "block_comunicacion_subencargado");
				aWriter.addFormRow(Path.parse("./Comunicacionconsubencargado/DestinatarioCconSuben"));
				aWriter.addFormRow(Path.parse("./Comunicacionconsubencargado/Operacionesqueejecutansubencargado"));				
				startBooleanBlock(aPaneContext, aWriter, Path.parse("./Comunicacionconsubencargado/Contratosubencargado"), "block_contrato_subencargado");
				aWriter.addFormRow(Path.parse("./Comunicacionconsubencargado/nombrecontratosubencargado"));
				aWriter.addFormRow(Path.parse("./Comunicacionconsubencargado/Fechaformalizacionsubencargado"));
				aWriter.addFormRow(Path.parse("./Comunicacionconsubencargado/Periododevigenciasubencargado"));				
				startBooleanBlock(aPaneContext, aWriter, Path.parse("./Comunicacionconsubencargado/Vigentesubencargado"), "block_vigente_subencargado");
				aWriter.addFormRow(Path.parse("./Comunicacionconsubencargado/Adjuntarcontratosubencargado"));
				endBlock(aWriter);		
				endBlock(aWriter);			
			endBlock(aWriter);			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Trasferencia/Trasferenciadecicion"), "block_transferencia");
				aWriter.addFormRow(Path.parse("./Trasferencia/Destinatariotrasferencia"));
				aWriter.addFormRow(Path.parse("./Trasferencia/Finalidadestrasferencia"));
				aWriter.addFormRow(Path.parse("./Trasferencia/Causaslegitimaciontrasferencia"));
				aWriter.addFormRow(Path.parse("./Trasferencia/Paisdestinotrasferencia"));				
				startBooleanBlock(aPaneContext, aWriter, Path.parse("./Trasferencia/Contratotrasferencia"), "block_contrato_transferencia");
				aWriter.addFormRow(Path.parse("./Trasferencia/Nombrecontratotrasferencia"));
				aWriter.addFormRow(Path.parse("./Trasferencia/Fechaformalizaciontrasferencia"));
				aWriter.addFormRow(Path.parse("./Trasferencia/Periododevigenciatrasferencia"));				
				startBooleanBlock(aPaneContext, aWriter, Path.parse("./Trasferencia/Vigenciatransferencia"), "block_vigente_transferencia");
				aWriter.addFormRow(Path.parse("./Trasferencia/Adjuntarcontratotrasferencia"));
				endBlock(aWriter);		
				endBlock(aWriter);			
			endBlock(aWriter);
		endBlock(aWriter);		
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
		aWriter.addFormRow(riesgosBox);
		//aWriter.addFormRow(Path.parse("Riesgo"));
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
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Por_defecto_y_desde_el_diseno"), "block_por_defecto_y_desde_el_diseno");
		aWriter.addFormRow(Path.parse("defectomenu"));
		endBlock(aWriter);
		
		aWriter.setCurrentObject(RESPUESTAS_KEY);
		aWriter.addFormRow(resultadosBox);		
		aWriter.setCurrentObject(OBJECT_KEY);
		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Asociadas_a_la_transferencia_de_datos"), "block_asociadas_a_la_transferencia_de_datos");
		aWriter.addFormRow(Path.parse("asociacionmenu"));
		endBlock(aWriter);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Asociadas_al_procesamiento_de_parte_de_terceros"), "block_asociadas_al_procesamiento_de_parte_de_terceros");		
		Path carPath = Path.parse("Cargo");
		UIDropDownList carDropDown = aWriter.newDropDownList(carPath);
		carDropDown.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockCargo"));
		aWriter.addFormRow(carDropDown);
		String value = (String) aPaneContext.getValueContext(OBJECT_KEY, carPath).getValue();		
		startBlock(aWriter, "block_cargo_encargado", "encargado".equalsIgnoreCase(value));	
		aWriter.addFormRow(Path.parse("./Encargado/Descripcion"));
		endBlock(aWriter);		
		startBlock(aWriter, "block_cargo_subencargado", "subencargado".equalsIgnoreCase(value));
		aWriter.addFormRow(Path.parse("./Subencargado/Descripcion"));
		endBlock(aWriter);		
		aWriter.endExpandCollapseBlock();		
		//---------------------------------------
		aWriter.add_cr("<h3>Posibles efectos colaterales, no deseados derivados del tratamiento</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Posibles_efectos_colateralesDecicion"), "block_posibles_efectos_colateralesDecicion");
		aWriter.addFormRow(Path.parse("Posibles_efectos_colaterales"));
		endBlock(aWriter);	
		aWriter.endExpandCollapseBlock();			
		//---------------------------------------
		aWriter.add_cr("<h3>Antecedentes (incidentes) conocidos</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("AntecedentesDecicion"), "block_antecedentesDecicion");
		aWriter.addFormRow(Path.parse("Antecedentes_incidentes_conocidos"));
		endBlock(aWriter);	
		startBooleanBlock(aPaneContext, aWriter, Path.parse("VincularFgestiondeviolacion"), "block_vincularFgestiondeviolacion");
		aWriter.addFormRow(Path.parse("IdentificadorFGV"));
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();		
		//---------------------------------------
		aWriter.add_cr("<h3>Amenazas conocidas</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Amenazasdecicion"), "block_amenazasdecicion");
		aWriter.addFormRow(Path.parse("Tipodevulneracion"));
		endBlock(aWriter);	
		
		aWriter.endExpandCollapseBlock();	
		//---------------------------------------
		aWriter.add_cr("<h3>Revisión metodológica</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		Path revPath = Path.parse("Revision_metodologica");
		UIDropDownList revDropDown = aWriter.newDropDownList(revPath);
		revDropDown.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision"));
		aWriter.addFormRow(revDropDown);
		String value2 = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath).getValue();		
		startBlock(aWriter, "block_revision_aceptable", "aceptable".equalsIgnoreCase(value2));	
		aWriter.addFormRow(MENU_ACEPTABLE_PATH);
		aWriter.addFormRow(CONDICION_ACEPTABLE_PATH);
		aWriter.addFormRow(NOMBRE_REVISORA_PATH);
		aWriter.addFormRow(ROL_CARGOA_PATH);
		endBlock(aWriter);		
		startBlock(aWriter, "block_revision_inaceptable", "inaceptable".equalsIgnoreCase(value2));
		aWriter.addFormRow(MENU_INACEPTABLE_PATH);
		aWriter.addFormRow( CONDICION_INACEPTABLE_PATH);
		aWriter.addFormRow(NOMBRE_REVISORI_PATH);
		aWriter.addFormRow(ROL_CARGOI_PATH);
		endBlock(aWriter);		
		aWriter.endExpandCollapseBlock();		
		//-----------------------------------------------------
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
    // Funcion que ocupan los campos String que tiene valores definidos y dependindo la selecion muestra los campos (RevisiÃ³n metodolÃ³gica)
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

 // Funcion que ocupan los campos String que tiene valores definidos y dependindo la selecion muestra los campos (Cargo)
    aWriter.addJS_cr("function displayBlockCargo(buttonValue){");
    aWriter.addJS_cr("const blockAceptable = document.getElementById('block_cargo_encargado');");
    aWriter.addJS_cr("const blockInaceptable = document.getElementById('block_cargo_subencargado');");
    aWriter.addJS_cr("if (buttonValue == 'Encargado'){");
    aWriter.addJS_cr("blockAceptable.style.display = 'block';");
    aWriter.addJS_cr("blockInaceptable.style.display = 'none';");
    aWriter.addJS_cr("}");
    aWriter.addJS_cr("else if(buttonValue == 'Subencargado'){");
    aWriter.addJS_cr("blockAceptable.style.display = 'none';");
    aWriter.addJS_cr("blockInaceptable.style.display = 'block';");
    aWriter.addJS_cr("}");
    aWriter.addJS_cr("else {");
    aWriter.addJS_cr("blockAceptable.style.display = 'none';");
    aWriter.addJS_cr("blockInaceptable.style.display = 'none';");
    aWriter.addJS_cr("}");
    aWriter.addJS_cr("}");   
    
    aWriter.addJS_cr("document.getElementById('saveButtonId').addEventListener('mouseover', function(){");   
    aWriter.addJS_cr("ebx_form_setValue('" + rriesgofocusPrefix + "', null);"); 
    aWriter.addJS_cr("});");	
    
    
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
			
			dataset = aContext.getEntitySelection().getDataset();
			BeanDefinition def = aBuilder.createBeanDefinition();
			aBuilder.registerBean(RESPUESTAS_KEY, def);			
			
			BeanElement riesgosElement= def.createElement(Riesgo, SchemaTypeName.XS_STRING);
			riesgosElement.addFacetConstraint(EnumerationConstraint.class);;
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
	@Override
	public void validate(UserServiceValidateContext<T> aContext) {		
	}
	public static class EnumerationConstraint implements ConstraintEnumeration<String> {
		@Override
		public void checkOccurrence(String arg0, ValueContextForValidation arg1) throws InvalidSchemaException {			
		}
		@Override
		public void setup(ConstraintContext arg0) {			
		}
		@Override
		public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {
			return null;
		}
		@Override
		public String displayOccurrence(String arg0, ValueContext aContext, Locale arg2) throws InvalidSchemaException {
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
			List<String> riesgoList = (List<String>) aContext.getValue(Path.parse("../Riesgo"));
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
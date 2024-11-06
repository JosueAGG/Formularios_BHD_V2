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

public class FNCVDUserService <T extends TableEntitySelection> implements UserService<T> {

	private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");
	private static final ObjectKey RESPUESTAS_KEY = ObjectKey.forName("respuestas");	  
	private static final Path Riesgo = Path.parse("Medidas_garantias_privacidad_seguridad_adoptadas_funcion_riesgos");	  
	private static final Path Rriesgo = Path.parse("Controles_F");
	private static final Path RESPUESTA_RIESGO_FOCUS = Path.parse("resriesgofocus");

	
	//heredar datos
	private static final Path NOMBRE_TRATAMIENTO_PATH = Path.parse("NombreT");
	private static final Path ID_FAB_PATH = Path.parse("identificadorFANB");
	private static final Path PROCESOS_OPERATIVOS_PATH = Path.parse("Procesos_operativos_o_comerciales");
	
	
	
	//Datos de revisor metodológico
	//Aceptable
	private static final Path MENU_ACEPTABLE_PATH = Path.parse("./Menuacep2");
	private static final Path CONDICION_ACEPTABLE_PATH = Path.parse("./Consiadi");
	private static final Path NOMBRE_REVISORA_PATH = Path.parse("./NombrerevisorFNCV");
	private static final Path ROL_CARGOA_PATH = Path.parse("./RolcargoFNCV");
	

	//iInaceptable
	private static final Path MENU_INACEPTABLE_PATH = Path.parse("./Menuinacep2");
	private static final Path CONDICION_INACEPTABLE_PATH = Path.parse("./ConcideinaFCV");
	private static final Path NOMBRE_REVISORI_PATH = Path.parse("./NombrerevFCVDINA");
	private static final Path ROL_CARGOI_PATH = Path.parse("./RolocargoFNCVina");
	
	
	
	private String idFABPrefix;
	private String procesosPrefix;
	//prefic aceptable
    String MAceptablePrefix;
    String CAceptablePrefix;
    String NRvisorAPrefix;
    String RCRevisorAPrefix;

    String MInaceptablePrefix;
    String CInaceptablePrefix;
    String NRvisorIPrefix ;
    String RCRevisorIPrefix;
	
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
	
	
	idFABPrefix = aWriter.getPrefixedPath(ID_FAB_PATH).format();
	procesosPrefix = aWriter.getPrefixedPath(PROCESOS_OPERATIVOS_PATH).format();
	
	
	
	
	
	
		
		aWriter.add_cr("<h3>Datos básicos del tratamiento de nivel ciclo de vida de datos</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Identificador"));
		UIComboBox inhTipImp = aWriter.newComboBox(NOMBRE_TRATAMIENTO_PATH);
		inhTipImp.setActionOnAfterValueChanged(JsFunctionCall.on("updateInheritedFields"));	
		aWriter.addFormRow(inhTipImp);	
		aWriter.addFormRow(ID_FAB_PATH);		
		aWriter.addFormRow(Path.parse("Fecha_creacion"));
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Ficha_analisis_fase"), "block_ficha_analisis_fase");
		aWriter.addFormRow(Path.parse("ID_Ficha_Analisis_de_Fases_del_Tratamiento"));
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		//----------------------------------------
		aWriter.add_cr("<h3>Categoría o dato personal que corresponda al ciclo de vida</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Categoria_dato_personal_corresponda_al_ciclo_de_vida"), "block_categoria_dato_personal_corresponda_al_ciclo_de_vida");
			
			aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos de identificación y contacto"), true);		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_de_Datos_identificacion_contacto/Datos_de_identificacion_contacto_Decicion"), "block_datos_de_identificacion_contacto_Decicion");
			aWriter.addFormRow(Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_de_Datos_identificacion_contacto/Catalogo_de_Datos_de_identificacion_contacto"));
			endBlock(aWriter);
			aWriter.endExpandCollapseBlock();
			
			aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos sobre características físicas"), true);		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_sobre_caracteristicas_fisicas/Datos_sobre_caracteristicas_fisicas_decicion"), "block_datos_sobre_caracteristicas_fisicas_decicion");
			aWriter.addFormRow(Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_sobre_caracteristicas_fisicas/C_Datos_sobre_caracteristicas_fisicas"));
			endBlock(aWriter);
			aWriter.endExpandCollapseBlock();
			
			aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos laborales"), true);		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_laborales/Datos_laborales_condicion"), "block_datos_laborales_condicion");
			aWriter.addFormRow(Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_laborales/C_Datos_laborales"));
			endBlock(aWriter);
			aWriter.endExpandCollapseBlock();
			
			aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos académicos"), true);		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_academicos/Datos_academicos_decicion"), "block_datos_academicos_decicion");
			aWriter.addFormRow(Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_academicos/Catalogo_Datos_academicos"));
			endBlock(aWriter);
			aWriter.endExpandCollapseBlock();
			
			
			aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos migratorios"), true);		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_migratorios/Datos_migratorios_condicion"), "block_datos_migratorios_condicion");
			aWriter.addFormRow(Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_migratorios/Datos_migratorios"));
			endBlock(aWriter);
			aWriter.endExpandCollapseBlock();
			
			aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos patrimoniales y financieros"), true);		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_patrimoniales_financieros/Datos_patrimoniales_financieros_decicion"), "block_datos_patrimoniales_financieros_decicion");
			aWriter.addFormRow(Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_patrimoniales_financieros/Datos_patrimoniales_financieros"));
			endBlock(aWriter);
			aWriter.endExpandCollapseBlock();
			
			
			aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos personales sobre pasatiempos, entretenimiento y diversión"), true);		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_de_pasatiempos_entretenimiento_diversion/Pasatiempos_entretenimiento_decision"), "block_pasatiempos_entretenimiento_decision");
			aWriter.addFormRow(Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_de_pasatiempos_entretenimiento_diversion/Datos_personales_sobre_pasatiempos_entretenimiento_diversion"));
			endBlock(aWriter);
			aWriter.endExpandCollapseBlock();
			
			aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos legales"), true);		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_legales/Datos_legales_decision"), "block_datos_legales_decision");
			aWriter.addFormRow(Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_legales/Datos_legales"));
			endBlock(aWriter);
			aWriter.endExpandCollapseBlock();
			
			
			aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos personales relacionados con aspectos personales"), true);		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_aspectos_personales/Aspectos_personales_decicion"), "block_aspectos_personales_decicion");
			aWriter.addFormRow(Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_aspectos_personales/aspectos_personales"));
			endBlock(aWriter);
			aWriter.endExpandCollapseBlock();
			
			aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos relacionados con las preferencias de consumo, hábitos, gustos, necesidades"), true);		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_preferencias_consumo/preferencias_consumo_decision"), "block_preferencias_consumo_decision");
			aWriter.addFormRow(Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_preferencias_consumo/c_datos_relacionados2"));
			endBlock(aWriter);
			aWriter.endExpandCollapseBlock();
			
			aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos personales relacionados con el rendimiento laboral"), true);		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_rendimiento_laboral/Rendimiento_laboral_decicion"), "block_rendimiento_laboral_decicion");
			aWriter.addFormRow(Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_rendimiento_laboral/Rendimiento_laboral"));
			endBlock(aWriter);
			aWriter.endExpandCollapseBlock();
			
			aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos de localización"), true);		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_localizacion/Datos_de_localizacion_decision"), "block_datos_de_localizacion_decision");
			aWriter.addFormRow(Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_Datos_localizacion/Datos_de_localizacion"));
			endBlock(aWriter);
			aWriter.endExpandCollapseBlock();
			
			aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos especiales o especialmente protegidos"), true);		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_especiales_especialmente_protejidos/Especiales_especialmente_protejidos_decision"), "block_especiales_especialmente_protejidos_decision");
			aWriter.addFormRow(Path.parse("./Categoria_dato_personal_que_corresponda_al_ciclo_de_vida/Campos_especiales_especialmente_protejidos/Especiales_especialmente"));
			endBlock(aWriter);
			aWriter.endExpandCollapseBlock();
		
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();	
		//----------------------------------------
		aWriter.add_cr("<h3>Procesos operativos o procesos comerciales vinculados al tratamiento</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(PROCESOS_OPERATIVOS_PATH);
		aWriter.endExpandCollapseBlock();
		//----------------------------------------
		aWriter.add_cr("<h3>Nombre de la fase del ciclo de vida</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("NombreF"));
		aWriter.endExpandCollapseBlock();
		//----------------------------------------
		aWriter.add_cr("<h3>Descripción de la fase del ciclo de vida</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Descripcion_de_la_fase_del_ciclo_de_vida"));
		aWriter.endExpandCollapseBlock();
		//----------------------------------------
		aWriter.add_cr("<h3>Fases del ciclo de vida de la categoría o dato personal</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Fase_anterior_del_ciclo_de_vida"));
		aWriter.addFormRow(Path.parse("Fase_posterior_del_ciclo_de_vida"));
		aWriter.endExpandCollapseBlock();
		//-----------------------------------------
		aWriter.add_cr("<h3>Características relevantes de implementación</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Caracteristicas_relevantes_de_implementacion"));
		aWriter.endExpandCollapseBlock();
		//-----------------------------------------
		aWriter.add_cr("<h3>Datos tratados</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Datos_tratados"));
		aWriter.endExpandCollapseBlock();
		//---------------------------------------
			
		aWriter.add_cr("<h3>Origen de los datos</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Origen_datos_Internos"), "block_origen_datos_Internos");
		aWriter.addFormRow(Path.parse("origendatosinternosm"));
		endBlock(aWriter);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Oriegn_datos_Externos"), "block_oriegn_datos_Externos");
		aWriter.addFormRow(Path.parse("Origen_datos_externos"));
		endBlock(aWriter);		
		aWriter.endExpandCollapseBlock();		
		//---------------------------------------
				
		aWriter.add_cr("<h3>Datos inferidos o generados</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Datos_inferidos_o_generados"));
		aWriter.endExpandCollapseBlock();
		//-----------------------------------------
		
		aWriter.add_cr("<h3>Destino de los datos</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Destino_datos_Interno"), "block_destino_datos_Interno");
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Interior_Banco/Procesos_operativos_comercialesF"), "block_interior_Banco_procesos_operativos_comercialesF");
			aWriter.addFormRow(Path.parse("./Interior_Banco/Catalogo_procesosoperativosocomerciales"));
			endBlock(aWriter);
			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Interior_Banco/Contratacion_producto_servicio"), "block_interior_contratacion_producto_servicio");
			aWriter.addFormRow(Path.parse("./Interior_Banco/Catalogo_productos_servicios_Banco"));
			endBlock(aWriter);
		endBlock(aWriter);		
		
		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Destino_datos_Exterior"), "block_destino_datos_Exterior");
		
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Para_tratamiento_encargadosdecicion"), "block_exterior_banco_Para_tratamiento_encargadosdecicion");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Paratratamiento_encargados/Servicio_contratado-encargado"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Paratratamiento_encargados/Destinatario-encargado"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Paratratamiento_encargados/Operaciones_ejecuta_encargado"));
			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Paratratamiento_encargados/Contrato-encargado"), "block_contrato_encargado");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Paratratamiento_encargados/Nombre_contrato-encargado"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Paratratamiento_encargados/Fecha_formalizacion_encargado"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Paratratamiento_encargados/Periodo_vigenciaencargado"));			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Paratratamiento_encargados/Vigente_encargado"), "block_vigente_encargado");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Paratratamiento_encargados/Adjuntar_contrato-encargado"));
			endBlock(aWriter);
			endBlock(aWriter);
			endBlock(aWriter);
			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Para_tratamiento_subencargados_decicion"), "block_subencargados_decicion");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Servicio_contratado_subencargado_F"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Destinatario_subencargado_F"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Operaciones_que_ejecuta_subencargado_F"));
			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Contrato_subencargado_F"), "block_contrato_subencargado_F");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Nombre_contrato_subencargado_F"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Fecha_de_formalizacion_subencargado_F"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Periodo_vigencia_subencargado_f"));			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Vigente_subencargado_F"), "block_vigente_subencargado_F");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Adjuntar_contrato_subencargado_F"));
			endBlock(aWriter);
			endBlock(aWriter);
			endBlock(aWriter);
			
			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Para_transferir_terceros_decision"), "block_para_transferir_terceros_decision");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_transferir-terceros/Destinatario_trasferencia_terceros_F"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_transferir-terceros/Pais_trasferencia_terceros_F"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_transferir-terceros/Finalidades_de_la_transferencia_trasferencia_terceros_F"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_transferir-terceros/Causa_de_legitimacion_trasferencia_terceros_F"));
			endBlock(aWriter);
			
			
			startBooleanBlock(aPaneContext, aWriter, Path.parse("./Exterior_banco/Para_ceder_datos_personales_desicion"), "block_para_ceder_datos_personales_desicion");
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_ceder_datos-personales/Destinatario_ceder_datos_personalesF"));
			aWriter.addFormRow(Path.parse("./Exterior_banco/Para_ceder_datos-personales/Causa_de_legitimacion_cer_datos_F"));
			endBlock(aWriter);
			
			
		endBlock(aWriter);
		
		
		
		aWriter.endExpandCollapseBlock();		
		//---------------------------------------
		
		
		aWriter.add_cr("<h3>Intervinientes externos</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Nombre_persona_fisica_o_juridica"));
		aWriter.addFormRow(Path.parse("Rol"));
		aWriter.addFormRow(Path.parse("Funciones"));
		aWriter.endExpandCollapseBlock();
		//-----------------------------------------
		
		aWriter.add_cr("<h3>Incidentes conocidos de fases similares ya implementadas</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Incidentes_decicion"), "block_incidentes_decicion");
		aWriter.addFormRow(Path.parse("Incidentes_conocidos"));
		endBlock(aWriter);		
		aWriter.endExpandCollapseBlock();		
		//---------------------------------------
		
		
		aWriter.add_cr("<h3>Amenazas conocidas</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Amenazas_conocidas_F"), "block_amenazas_conocidas_F");
		aWriter.addFormRow(Path.parse("Tipo_de_amenaza_F"));
		endBlock(aWriter);		
		aWriter.endExpandCollapseBlock();		
		//---------------------------------------
		
		
		aWriter.add_cr("<h3>Medidas y garantías de privacidad y seguridad por defecto</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Medidas_garantias_privacidad_seguridad_defecto"), "block_medidas_garantias_privacidad_seguridad_defecto");
		aWriter.addFormRow(Path.parse("./Campo_Medidas_garantias_privacidad/Campo_Medidas_garantias_privacidad"));
		endBlock(aWriter);		
		aWriter.endExpandCollapseBlock();		
		//---------------------------------------
		
		aWriter.add_cr("<h3>Medidas y garantías de privacidad y seguridad adoptadas en función de los riesgos</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		aWriter.setCurrentObject(RESPUESTAS_KEY);
		aWriter.addFormRow(riesgosBox);
		//aWriter.addFormRow(Path.parse("Medidas_garantias_privacidad_seguridad_adoptadas_funcion_riesgos"));
		aWriter.setCurrentObject(OBJECT_KEY);
		aWriter.setCurrentObject(RESPUESTAS_KEY);
		//aWriter.addFormRow(Path.parse("Controles_F"));
		aWriter.addFormRow(resultadosBox);		
		aWriter.setCurrentObject(OBJECT_KEY);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Posibilidad_vincular"), "block_posibilidad_vincular");
		aWriter.addFormRow(Path.parse("vincular_analisis_riesgo"));
		endBlock(aWriter);		
		aWriter.endExpandCollapseBlock();		
		//---------------------------------------	
			
		aWriter.add_cr("<h3>Revisión metodológica</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		Path revPath = Path.parse("RevicionMeto");
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
		aWriter.addFormRow( CONDICION_INACEPTABLE_PATH);
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
    	
    	
    	String value = anAjaxContext.getParameter("fieldValue");
    	System.out.println(value);
    	
    	if(value != null && !value.isBlank()) {

	    	Adaptation record = table.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(value));
	    	
	    	identificador = record.getString(Path.parse("./Identificador"));
	    	proceso = record.getString(Path.parse("./Proceso_operativo"));
	    	
    	
    	}
    	
    	UserServiceWriter aWriter = anAjaxResponse.getWriter();
    	
    	identificador = identificador == null ? "null" : "'" + identificador + "'";
    	
    	
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
		aWriter.addJS_cr("ebx_form_setValue('" + procesosPrefix + "', proceso)");

    	
    	
    }
		
	@Override
	public void validate(UserServiceValidateContext<T> arg0) {
		
		
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

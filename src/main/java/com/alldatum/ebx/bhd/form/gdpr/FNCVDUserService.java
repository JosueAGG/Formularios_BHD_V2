package com.alldatum.ebx.bhd.form.gdpr;

import java.util.Arrays;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.ServiceKey;
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

public class FNCVDUserService <T extends TableEntitySelection> implements UserService<T> {

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
		
		aContext.save(OBJECT_KEY);
		return null;
	}
	
private void pane(UserServicePaneContext aPaneContext, UserServicePaneWriter aWriter) {
		
		aWriter.setCurrentObject(OBJECT_KEY);
		
		aWriter.add_cr("<h3>Datos básicos del tratamiento de nivel ciclo de vida de datos</h3>");	
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Identificador"));
		aWriter.addFormRow(Path.parse("NombreT"));
		aWriter.addFormRow(Path.parse("identificadorFANB"));
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
		aWriter.addFormRow(Path.parse("Procesos_operativos_o_comerciales"));
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
		aWriter.addFormRow(Path.parse("Medidas_garantias_privacidad_seguridad_adoptadas_funcion_riesgos"));
		aWriter.addFormRow(Path.parse("Controles_F"));
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Posibilidad_vincular"), "block_posibilidad_vincular");
		aWriter.addFormRow(Path.parse("vincular_analisis_riesgo"));
		endBlock(aWriter);		
		aWriter.endExpandCollapseBlock();		
		//---------------------------------------	
		//---------------------------------------
		
		

		
		
		aWriter.add_cr("<h3>Revisión metodológica</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		
		Path revPath = Path.parse("RevicionMeto");
		UIDropDownList revDropDown = aWriter.newDropDownList(revPath);
		revDropDown.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision"));
		aWriter.addFormRow(revDropDown);
		String value = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath).getValue();
		
		startBlock(aWriter, "block_revision_aceptable", "aceptable".equalsIgnoreCase(value));	
		aWriter.addFormRow(Path.parse("Menuacep2"));
		aWriter.addFormRow(Path.parse("Consiadi"));
		aWriter.addFormRow(Path.parse("NombrerevisorFNCV"));
		aWriter.addFormRow(Path.parse("RolcargoFNCV"));
		endBlock(aWriter);
		
		startBlock(aWriter, "block_revision_inaceptable", "inaceptable".equalsIgnoreCase(value));
		aWriter.addFormRow(Path.parse("Menuinacep2"));
		aWriter.addFormRow(Path.parse("ConcideinaFCV"));
		aWriter.addFormRow(Path.parse("NombrerevFCVDINA"));
		aWriter.addFormRow(Path.parse("RolocargoFNCVina"));
		endBlock(aWriter);
		
		aWriter.endExpandCollapseBlock();
		//---------------------------------------
		 aWriter.addJS_cr("function displayBlock(buttonValue, blockId){");
	     aWriter.addJS_cr("if (buttonValue == 'true'){");
	     aWriter.addJS_cr("document.getElementById(blockId).style.display = 'block';");
	     aWriter.addJS_cr("}");
	     aWriter.addJS_cr("else {");
	     aWriter.addJS_cr("document.getElementById(blockId).style.display = 'none';");
	     aWriter.addJS_cr("}");
	     aWriter.addJS_cr("}");
		
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

	@Override
	public void validate(UserServiceValidateContext<T> arg0) {
		
		
	}

}

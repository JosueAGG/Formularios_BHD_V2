package com.alldatum.ebx.bhd.form.gdpr;

import com.alldatum.ebx.bhd.util.AdaptationUtil;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.interactions.InteractionHelper;
import com.orchestranetworks.interactions.SessionInteraction;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.PathAccessException;
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


public class RVSUserService<T extends TableEntitySelection> implements UserService<T> {
	private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");
	
	
	
	
	//Datos de revisor metodológico
	//Aceptable
	private static final Path MENU_ACEPTABLE_PATH = Path.parse("./RMOPDP/RMOPDP_Aceptable");
	private static final Path CONDICION_ACEPTABLE_PATH = Path.parse("./RMOPDP/Consideraciones_Adicionales_Aceptable");
	private static final Path NOMBRE_REVISORA_PATH = Path.parse("./RMOPDP/NombrerevisorA");
	private static final Path ROL_CARGOA_PATH = Path.parse("./RMOPDP/CargoRaRV");
	

	//iInaceptable
	private static final Path MENU_INACEPTABLE_PATH = Path.parse("./RMOPDP/RMOPDP_Inaceptable");
	private static final Path CONDICION_INACEPTABLE_PATH = Path.parse("./RMOPDP/Consideraciones_Adicionales_Inaceptable");
	private static final Path NOMBRE_REVISORI_PATH = Path.parse("./RMOPDP/NombrerevisorinaRV");
	private static final Path ROL_CARGOI_PATH = Path.parse("./RMOPDP/cargoinarv");
	
	
	//prefic aceptable
	    String MAceptablePrefix;
	    String CAceptablePrefix;
	    String NRvisorAPrefix;
	    String RCRevisorAPrefix;

	    String MInaceptablePrefix;
	    String CInaceptablePrefix;
	    String NRvisorIPrefix ;
	    String RCRevisorIPrefix;
	    
	public UserServiceEventOutcome processEventOutcome(UserServiceProcessEventOutcomeContext<T> aContext,
			UserServiceEventOutcome anOutcome) {
		return anOutcome;
	}

	public void setupDisplay(UserServiceSetupDisplayContext<T> aContext, UserServiceDisplayConfigurator aConfigurator) {
		aConfigurator.setContent(this::pane);
		aConfigurator.setDefaultButtons(this::save);
	}

	private UserServiceEventOutcome save(UserServiceEventContext aContext) {
		if (!aContext.save(new ObjectKey[]{OBJECT_KEY}).hasFailed()
				&& aContext.getSession().isInWorkflowInteraction(false)) {
			ServiceKey sk = aContext.getServiceKey();
			if (sk.equals(ServiceKey.CREATE) || sk.equals(ServiceKey.DUPLICATE)) {
				Adaptation record = AdaptationUtil.getRecordForValueContext(aContext.getValueContext(OBJECT_KEY));
				String recordString = record.toXPathExpression();
				SessionInteraction si = aContext.getSession().getInteraction(false);
				InteractionHelper.ParametersMap internalMap = new InteractionHelper.ParametersMap();
				internalMap.setVariableString("created", recordString);
				si.complete(internalMap);
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
	

		
		
		
		
		aWriter.add_cr("<h3>Datos básicos del registro de violación de seguridad</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Identificador"));
		aWriter.addFormRow(Path.parse("Fecha_creacion"));
		aWriter.addFormRow(Path.parse("Proceso"));
		aWriter.addFormRow(Path.parse("Tipo_violacion_seguridad"));
		aWriter.addFormRow(Path.parse("CTitulares"));
		aWriter.addFormRow(Path.parse("Ntitulares"));
		aWriter.endExpandCollapseBlock();
		
		aWriter.add_cr("<h3>Datos personales presuntamente comprometidos</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.add_cr("<h3>Datos de identificacion y contacto</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		this.startBooleanBlock(aPaneContext, aWriter, Path.parse("./Datos_identificacion_contacto/Datosiden_decision"),"block_datos_identificacion_contacto");
		aWriter.addFormRow(Path.parse("./Datos_identificacion_contacto/Catalo_identificacion_contacto"));
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		aWriter.add_cr("<h3>Datos sobre caracteristicas fisicas</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		this.startBooleanBlock(aPaneContext, aWriter, Path.parse("./Datos_Caracteristicas_Fisicas/Datos_CF_Decision"),"block_datos_caracteristicas_fisicas");
		aWriter.addFormRow(Path.parse("./Datos_Caracteristicas_Fisicas/Catalogo_Caracteristicas_Fisicas"));
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		aWriter.add_cr("<h3>Datos laborales</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		this.startBooleanBlock(aPaneContext, aWriter, Path.parse("./Datos_Laborales/Datos_Laborales_Decision"),"block_datos_laborales");
		aWriter.addFormRow(Path.parse("./Datos_Laborales/Catalogo_Datos_Laborales"));
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		aWriter.add_cr("<h3>Datos academicos</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		this.startBooleanBlock(aPaneContext, aWriter, Path.parse("./Datos_Academicos/Datos_Academicos_Decision"),"block_datos_academicos");
		aWriter.addFormRow(Path.parse("./Datos_Academicos/Catalogo_Datos_Academicos"));
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		aWriter.add_cr("<h3>Datos migratorios</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		this.startBooleanBlock(aPaneContext, aWriter, Path.parse("./Datos_Migratorios/Datos_Migratorios_Decision"),"block_datos_migratorios");
		aWriter.addFormRow(Path.parse("./Datos_Migratorios/Catalogo_Datos_Migratorios"));
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		aWriter.add_cr("<h3>Datos patrimoniales y financieros</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		this.startBooleanBlock(aPaneContext, aWriter,
				Path.parse("./Datos_Patrimoniales_Financieros/Datos_PatriFinan_Decision"),"block_datos_patrimoniales_financieros");
		aWriter.addFormRow(Path.parse("./Datos_Patrimoniales_Financieros/Catalogo_Datos_PatriFinan"));
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		aWriter.add_cr("<h3>Datos personales sobre pasatiempos, entretenimiento y diversión</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		this.startBooleanBlock(aPaneContext, aWriter, Path.parse("./Datos_PSPED/Datos_PPED_Decision"), "block_dpedc");
		aWriter.addFormRow(Path.parse("./Datos_PSPED/Catalogo_DPPED"));
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		aWriter.add_cr("<h3>Datos legales</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		this.startBooleanBlock(aPaneContext, aWriter, Path.parse("./Datos_Legales/Datos_Legales_Decision"),"block_dles");
		aWriter.addFormRow(Path.parse("./Datos_Legales/Catalogo_Datos_Legales"));
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		aWriter.add_cr("<h3>Datos personales relacionados con aspectos personales</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		this.startBooleanBlock(aPaneContext, aWriter, Path.parse("./Datos_PRAP/Datos_PRAP_Decision"), "block_dpraps");
		aWriter.addFormRow(Path.parse("./Datos_PRAP/Catalogo_DPRAP"));
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		aWriter.add_cr("<h3>Datos relacionados con las preferencias de consumo, hábitos, gustos, necesidades</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		this.startBooleanBlock(aPaneContext, aWriter, Path.parse("./Datos_RPCHGN/Datos_RPCHGN_Decision"),
				"block_drpchgns");
		aWriter.addFormRow(Path.parse("./Datos_RPCHGN/Catalogo_DRPCHGN"));
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		aWriter.add_cr("<h3>Datos personales relacionados con el rendimiento laboral</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		this.startBooleanBlock(aPaneContext, aWriter, Path.parse("./Datos_PRRL/Datos_PRRL_Decision"), "block_drls");
		aWriter.addFormRow(Path.parse("./Datos_PRRL/Catalogo_DPRRL"));
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		aWriter.add_cr("<h3>Datos de localización</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		this.startBooleanBlock(aPaneContext, aWriter, Path.parse("./Datos_Localizacion/Datos_Localizacion_Decision"),
				"block_dlocalizacion");
		aWriter.addFormRow(Path.parse("./Datos_Localizacion/Catalogo_Datos_Localizacion"));
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		aWriter.add_cr("<h3>Datos especiales o especialmente protegidos</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		this.startBooleanBlock(aPaneContext, aWriter, Path.parse("./Datos_Especiales/Datos_Especiales_Decision"),
				"block_dEspeciales");
		aWriter.addFormRow(Path.parse("./Datos_Especiales/Catalogo_Datos_Especiales"));
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		aWriter.endExpandCollapseBlock();
		
		aWriter.add_cr("<h3>Registro de Incidentes de Seguridad</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("DescripcionV"));
		aWriter.addFormRow(Path.parse("SistemaCom"));
		aWriter.addFormRow(Path.parse("Consecuencias"));
		aWriter.addFormRow(Path.parse("AccionesC"));
		aWriter.addFormRow(Path.parse("RecomendacionesT"));
		aWriter.addFormRow(Path.parse("MedidasP"));
		aWriter.addFormRow(Path.parse("Holayfecha"));
		aWriter.addFormRow(Path.parse("NombrePersonasdesignadas"));
		aWriter.addFormRow(Path.parse("Informacion_adicional"));
		aWriter.addFormRow(Path.parse("NombreperonaInforma"));
		aWriter.addFormRow(Path.parse("NombreperonaregistraV"));
		aWriter.endExpandCollapseBlock();
		
		aWriter.add_cr("<h3>Revisión metodológica de la Oficina de Protección de Datos Personales</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		Path revPath = Path.parse("./RMOPDP/RMOPDP_Decision");
		UIDropDownList revDropDown = aWriter.newDropDownList(revPath);
		revDropDown.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision"));
		aWriter.addFormRow(revDropDown);
		String value2 = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath).getValue();
		this.startBlock(aWriter, "block_revision_aceptable", "aceptable".equalsIgnoreCase(value2));
		aWriter.addFormRow(MENU_ACEPTABLE_PATH);
		aWriter.addFormRow(CONDICION_ACEPTABLE_PATH);
		aWriter.addFormRow(NOMBRE_REVISORA_PATH);
		aWriter.addFormRow(ROL_CARGOA_PATH);
		this.endBlock(aWriter);
		this.startBlock(aWriter, "block_revision_inaceptable", "inaceptable".equalsIgnoreCase(value2));
		aWriter.addFormRow(MENU_INACEPTABLE_PATH);
		aWriter.addFormRow( CONDICION_INACEPTABLE_PATH);
		aWriter.addFormRow(NOMBRE_REVISORI_PATH);
		aWriter.addFormRow(ROL_CARGOI_PATH);
		this.endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		

		
		aWriter.setCurrentObject(OBJECT_KEY);
		aWriter.addJS_cr("function displayBlock(buttonValue, blockId){");
		aWriter.addJS_cr("if (buttonValue == 'true'){");
		aWriter.addJS_cr("document.getElementById(blockId).style.display = 'block';");
		aWriter.addJS_cr("}");
		aWriter.addJS_cr("else {");
		aWriter.addJS_cr("document.getElementById(blockId).style.display = 'none';");
		aWriter.addJS_cr("}");
		aWriter.addJS_cr("}");
		
		
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
	}
	



	private void startBlock(UserServicePaneWriter aWriter, String blockId, Boolean isDisplayed) {
		String display = isDisplayed ? "display:block" : "display:none";
		aWriter.add("<div ");
		aWriter.addSafeAttribute("id", blockId);
		aWriter.addSafeAttribute("style", display);
		aWriter.add_cr(">");
	}

	private void startBooleanBlock(UserServicePaneContext aPaneContext, UserServicePaneWriter aWriter, Path path,
			String blockId) {
		UIRadioButtonGroup button = aWriter.newRadioButtonGroup(path);
		button.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlock", blockId));
		aWriter.addFormRow(button);
		Boolean value = (Boolean) aPaneContext.getValueContext(OBJECT_KEY, path).getValue();
		String display;
		if (value != null && value) {
			display = "display:block";
		} else {
			display = "display:none";
		}

		aWriter.add("<div ");
		aWriter.addSafeAttribute("id", blockId);
		aWriter.addSafeAttribute("style", display);
		aWriter.add_cr(">");
	}

	private void endBlock(UserServicePaneWriter aWriter) {
		aWriter.add_cr("</div>");
	}

	public void setupObjectContext(UserServiceSetupObjectContext<T> aContext,
			UserServiceObjectContextBuilder aBuilder) {
		if (aContext.isInitialDisplay()) {
			TableEntitySelection selection = (TableEntitySelection) aContext.getEntitySelection();
			if (selection instanceof RecordEntitySelection) {
				Adaptation record = ((RecordEntitySelection) selection).getRecord();
				if (aContext.getServiceKey().equals(ServiceKey.DUPLICATE)) {
					aBuilder.registerNewDuplicatedRecord(OBJECT_KEY, record);
				} else {
					aBuilder.registerRecordOrDataSet(OBJECT_KEY, record);
				}
			} else {
				aBuilder.registerNewRecord(OBJECT_KEY, selection.getTable());
			}
		}

	}
	
	


	public void validate(UserServiceValidateContext<T> aContext) {
	}
}
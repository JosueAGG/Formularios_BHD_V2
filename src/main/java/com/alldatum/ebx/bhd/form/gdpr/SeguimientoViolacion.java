
package com.alldatum.ebx.bhd.form.gdpr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alldatum.ebx.bhd.util.AdaptationUtil;
import com.alldatum.ebx.bhd.widget.UIDynamicListWidget;
import com.alldatum.ebx.bhd.widget.UIDynamicListWidgetFactory;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.interactions.InteractionHelper;
import com.orchestranetworks.interactions.SessionInteraction;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.ServiceKey;
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

public class SeguimientoViolacion<T extends TableEntitySelection> implements UserService<T> {
	
	private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");

	private static final Path NOMBRE_NOTIF =  Path.parse("ID_nombre_notificacion");
	private static final Path RESPONSABLE_PATH = Path.parse("Responsable");
	private static final Path MEDIDAS_REMEDIACION_PATH = Path.parse("MedidasRem_AC");
	private static final Path TIPOV_PATH = Path.parse("TipoV2");
	private static final Path CATEGORIATA_PATH = Path.parse("CategoriaTA");
	private static final Path DATOS_IDENTIFICACION_PATH = Path.parse("DatosComprometidos/Decision_Identificacion_Contacto_Seguimiento");
	private static final Path DATOS_FISICOS_PATH = Path.parse("DatosComprometidos/Decision_Caracteristicas_Fisicas_Seguimiento");
	private static final Path DATOS_LABORALES_PATH = Path.parse("DatosComprometidos/Decision_Laborales_Seguimiento");
	private static final Path DATOS_ACADEMICOS_PATH = Path.parse("DatosComprometidos/Decision_Academicos_Seguimiento");
	private static final Path DATOS_MIGRATORIOS_PATH = Path.parse("DatosComprometidos/Decision_Migratorios_Seguimiento");
	private static final Path DATOS_PATRIMONIALES_PATH = Path.parse("DatosComprometidos/Decision_Patrimoniales_Seguimiento");
	private static final Path DATOS_PASATIEMPOS_PATH = Path.parse("DatosComprometidos/Decision_PED_Seguimiento");
	private static final Path DATOS_LEGALES_PATH = Path.parse("DatosComprometidos/Decision_Legales_Seguimiento");
	private static final Path DATOS_ASPECTOS_PATH = Path.parse("DatosComprometidos/Decision_PRAP_Seguimiento");
	private static final Path DATOS_PREFERENCIAS_PATH = Path.parse("DatosComprometidos/Decision_RPCHGN_Seguimiento");
	private static final Path DATOS_RENDIMIENTO_PATH = Path.parse("DatosComprometidos/Decision_Rendimiento_Laboral_Seguimiento");
	private static final Path DATOS_LOCALIZACION_PATH = Path.parse("DatosComprometidos/Decision_Localizacion_Seguimiento");
	private static final Path DATOS_ESPECIALES_PATH = Path.parse("DatosComprometidos/Decision_Especiales_Seguimiento");

	private static final Path DATOS_IDENTIFICACION_PATH2 = Path.parse("DatosComprometidos/Datos_Identificacion_Contacto_Seguimiento");
	private static final Path DATOS_FISICOS_PATH2 = Path.parse("DatosComprometidos/Datos_Caracteristicas_Fisicas_Seguimiento");
	private static final Path DATOS_LABORALES_PATH2 = Path.parse("DatosComprometidos/Datos_Laborales_Seguimiento");
	private static final Path DATOS_ACADEMICOS_PATH2 = Path.parse("DatosComprometidos/Datos_Academicos_Seguimiento");
	private static final Path DATOS_MIGRATORIOS_PATH2 = Path.parse("DatosComprometidos/Datos_Migratorios_Seguimiento");
	private static final Path DATOS_PATRIMONIALES_PATH2 = Path.parse("DatosComprometidos/Datos_PatFin_Seguimiento");
	private static final Path DATOS_PASATIEMPOS_PATH2 = Path.parse("DatosComprometidos/Datos_PPED_Seguimiento");
	private static final Path DATOS_LEGALES_PATH2 = Path.parse("DatosComprometidos/Datos_Legales_Seguimiento");
	private static final Path DATOS_ASPECTOS_PATH2 = Path.parse("DatosComprometidos/Datos_PRAP_Seguimiento");
	private static final Path DATOS_PREFERENCIAS_PATH2 = Path.parse("DatosComprometidos/Datos_RPCHGN_Seguimiento");
	private static final Path DATOS_RENDIMIENTO_PATH2 = Path.parse("DatosComprometidos/Datos_PRRL_Seguimiento");
	private static final Path DATOS_LOCALIZACION_PATH2 = Path.parse("DatosComprometidos/Datos_Localizacion_Seguimiento");
	private static final Path DATOS_ESPECIALES_PATH2 = Path.parse("DatosComprometidos/Datos_Especiales_Seguimiento");

	private static final String MEDIDAS_REMEDIACION_ID = "medremList";
	private static final String TIPOV_ID = "tipoList";
	private static final String CATEGORIATA_ID = "categoriataList";
	private static final String DATOS_IDENTIFICACION_ID = "datosidentificacionList";
	private static final String DATOS_FISICOS_ID = "datosfisicosList";
	private static final String DATOS_LABORALES_ID = "datoslaboralesList";
	private static final String DATOS_ACADEMICOS_ID = "datosacademicosList";
	private static final String DATOS_MIGRATORIOS_ID = "datosmigratorisList";
	private static final String DATOS_PATRIMONIALES_ID = "datospatrimonialesList";
	private static final String DATOS_PASATIEMPOS_ID = "datospasatiemposList";
	private static final String DATOS_LEGALES_ID = "datoslegalesList";
	private static final String DATOS_ASPECTOS_ID = "datosaspectosList";
	private static final String DATOS_PREFERENCIAS_ID = "datospreferenciasList";
	private static final String DATOS_RENDIMIENTO_ID = "datosrendimientoList";
	private static final String DATOS_LOCALIZACION_ID = "datoslocalizacionList";
	private static final String DATOS_ESPECIALES_ID = "datosespecialesList";
	
	//Datos de revisor metodológico
	//Aceptable
	private static final Path MENU_ACEPTABLE_PATH = Path.parse("./RecomendacionesOPD");
	private static final Path CONDICION_ACEPTABLE_PATH = Path.parse("./ConsideracionesAceptable");
	private static final Path NOMBRE_REVISORA_PATH = Path.parse("./NombreRAS");
	private static final Path ROL_CARGOA_PATH = Path.parse("./CargoSeg");
	

	//iInaceptable
	private static final Path MENU_INACEPTABLE_PATH = Path.parse("./NrecomendacionesDPO");
	private static final Path CONDICION_INACEPTABLE_PATH = Path.parse("./ConsideracionesInaceptable");
	private static final Path NOMBRE_REVISORI_PATH = Path.parse("./NomrIna");
	private static final Path ROL_CARGOI_PATH = Path.parse("./CargoSegm");
	

	private String responsablePrefix;
	private String medremPrefix;
	private String tipovPrefix;
	private String categoriataPrefix;
	private String datosidentificacionPrefix;
	private String datosfisicosPrefix;
	private String datoslaboralesPrefix;
	private String datosacademicosPrefix;
	private String datosmigratoriosPrefix;
	private String datospatrimonialesPrefix;
	private String datospasatiemposPrefix;
	private String datoslegalesPrefix;
	private String datosaspectosPrefix;
	private String datospreferenciasPrefix;
	private String datosrendimientoPrefix;
	private String datoslocalizacionPrefix;
	private String datosespecialesPrefix;
	private String datosidentificacionPrefix2;
	private String datosfisicosPrefix2;
	private String datoslaboralesPrefix2;
	private String datosacademicosPrefix2;
	private String datosmigratoriosPrefix2;
	private String datospatrimonialesPrefix2;
	private String datospasatiemposPrefix2;
	private String datoslegalesPrefix2;
	private String datosaspectosPrefix2;
	private String datospreferenciasPrefix2;
	private String datosrendimientoPrefix2;
	private String datoslocalizacionPrefix2;
	private String datosespecialesPrefix2;
	
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

		//Titulo de resumen ejecutivo 

		responsablePrefix=aWriter.getPrefixedPath(RESPONSABLE_PATH).format();
		medremPrefix = aWriter.getPrefixedPath(MEDIDAS_REMEDIACION_PATH).format();
		tipovPrefix = aWriter.getPrefixedPath(TIPOV_PATH).format();
		categoriataPrefix = aWriter.getPrefixedPath(CATEGORIATA_PATH).format();
		datosidentificacionPrefix = aWriter.getPrefixedPath(DATOS_IDENTIFICACION_PATH).format();
		datosfisicosPrefix = aWriter.getPrefixedPath(DATOS_FISICOS_PATH).format();
		datoslaboralesPrefix = aWriter.getPrefixedPath(DATOS_LABORALES_PATH).format();
		datosacademicosPrefix = aWriter.getPrefixedPath(DATOS_ACADEMICOS_PATH).format();
		datosmigratoriosPrefix = aWriter.getPrefixedPath(DATOS_MIGRATORIOS_PATH).format();
		datospatrimonialesPrefix = aWriter.getPrefixedPath(DATOS_PATRIMONIALES_PATH).format();
		datospasatiemposPrefix = aWriter.getPrefixedPath(DATOS_PASATIEMPOS_PATH).format();
		datoslegalesPrefix = aWriter.getPrefixedPath(DATOS_LEGALES_PATH).format();
		datosaspectosPrefix = aWriter.getPrefixedPath(DATOS_ASPECTOS_PATH).format();
		datospreferenciasPrefix = aWriter.getPrefixedPath(DATOS_PREFERENCIAS_PATH).format();
		datosrendimientoPrefix = aWriter.getPrefixedPath(DATOS_RENDIMIENTO_PATH).format();
		datoslocalizacionPrefix = aWriter.getPrefixedPath(DATOS_LOCALIZACION_PATH).format();
		datosespecialesPrefix = aWriter.getPrefixedPath(DATOS_ESPECIALES_PATH).format();
		datosidentificacionPrefix2 = aWriter.getPrefixedPath(DATOS_IDENTIFICACION_PATH2).format();
		datosfisicosPrefix2 = aWriter.getPrefixedPath(DATOS_FISICOS_PATH2).format();
		datoslaboralesPrefix2 = aWriter.getPrefixedPath(DATOS_LABORALES_PATH2).format();
		datosacademicosPrefix2 = aWriter.getPrefixedPath(DATOS_ACADEMICOS_PATH2).format();
		datosmigratoriosPrefix2 = aWriter.getPrefixedPath(DATOS_MIGRATORIOS_PATH2).format();
		datospatrimonialesPrefix2 = aWriter.getPrefixedPath(DATOS_PATRIMONIALES_PATH2).format();
		datospasatiemposPrefix2 = aWriter.getPrefixedPath(DATOS_PASATIEMPOS_PATH2).format();
		datoslegalesPrefix2 = aWriter.getPrefixedPath(DATOS_LEGALES_PATH2).format();
		datosaspectosPrefix2 = aWriter.getPrefixedPath(DATOS_ASPECTOS_PATH2).format();
		datospreferenciasPrefix2 = aWriter.getPrefixedPath(DATOS_PREFERENCIAS_PATH2).format();
		datosrendimientoPrefix2 = aWriter.getPrefixedPath(DATOS_RENDIMIENTO_PATH2).format();
		datoslocalizacionPrefix2 = aWriter.getPrefixedPath(DATOS_LOCALIZACION_PATH2).format();
		datosespecialesPrefix2 = aWriter.getPrefixedPath(DATOS_ESPECIALES_PATH2).format();
		
		
		aWriter.add_cr("<h3>Datos basicos del Seguimiento de violacion de seguridad</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Identificador"));
		aWriter.addFormRow(Path.parse("Fecha_creacion"));
		UIComboBox inhTipImp = aWriter.newComboBox(NOMBRE_NOTIF);
		inhTipImp.setActionOnAfterValueChanged(JsFunctionCall.on("updateInheritedFields"));
		aWriter.addFormRow(inhTipImp);
		aWriter.addFormRow(RESPONSABLE_PATH);
		aWriter.addFormRow(Path.parse("Causaraiz"));
		UIDynamicListWidget medremWidget = aWriter.newCustomWidget(MEDIDAS_REMEDIACION_PATH, new UIDynamicListWidgetFactory());
		medremWidget.setListId(MEDIDAS_REMEDIACION_ID);
		aWriter.addFormRow(medremWidget);
		aWriter.addFormRow(Path.parse("Medidasreme"));

		startBooleanBlock(aPaneContext, aWriter, Path.parse("NotificacionAC"), "block_NotificacionAC");
		aWriter.addFormRow(Path.parse("CamposN/Fecha"));
		aWriter.addFormRow(Path.parse("CamposN/Consideracion"));
		aWriter.addFormRow(Path.parse("CamposN/AdjuntarA"));
		endBlock(aWriter);

		startBooleanBlock(aPaneContext, aWriter, Path.parse("NotificacionTi"), "block_NotificacionTi");
		aWriter.addFormRow(Path.parse("NotificacionT/FechaNot"));
		aWriter.addFormRow(Path.parse("NotificacionT/ConsideracionControl"));
		aWriter.addFormRow(Path.parse("NotificacionT/AdjuntarB"));
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		
		aWriter.add_cr("<h3>Impacto Final</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		UIDynamicListWidget tipoWidget = aWriter.newCustomWidget(TIPOV_PATH,new UIDynamicListWidgetFactory());
		tipoWidget.setListId(TIPOV_ID);
		aWriter.addFormRow(tipoWidget);
		UIDynamicListWidget categoriaWidget = aWriter.newCustomWidget(CATEGORIATA_PATH,new UIDynamicListWidgetFactory());
		categoriaWidget.setListId(CATEGORIATA_ID);
		aWriter.addFormRow(categoriaWidget);
		aWriter.addFormRow(Path.parse("NtitularesAfec"));

		
		aWriter.add_cr("<h3>Datos de identificacion y contacto</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, DATOS_IDENTIFICACION_PATH, "block_DIC");
		UIDynamicListWidget identificacionWidget = aWriter.newCustomWidget(DATOS_IDENTIFICACION_PATH2, new UIDynamicListWidgetFactory());
		identificacionWidget.setListId(DATOS_IDENTIFICACION_ID);
		aWriter.addFormRow(identificacionWidget);
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		
		aWriter.add_cr("<h3>Datos sobre caracteristicas fisicas</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, DATOS_FISICOS_PATH, "block_DCFS");
		UIDynamicListWidget fisicosWidget = aWriter.newCustomWidget(DATOS_FISICOS_PATH2, new UIDynamicListWidgetFactory());
		fisicosWidget.setListId(DATOS_FISICOS_ID);
		aWriter.addFormRow(fisicosWidget);
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>Datos laborales</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, DATOS_LABORALES_PATH, "block_DLS");
		UIDynamicListWidget laboralesWidget = aWriter.newCustomWidget(DATOS_LABORALES_PATH2, new UIDynamicListWidgetFactory());
		laboralesWidget.setListId(DATOS_LABORALES_ID);
		aWriter.addFormRow(laboralesWidget);
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>Datos academicos</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, DATOS_ACADEMICOS_PATH, "block_DAS");
		UIDynamicListWidget academicosWidget = aWriter.newCustomWidget(DATOS_ACADEMICOS_PATH2, new UIDynamicListWidgetFactory());
		academicosWidget.setListId(DATOS_ACADEMICOS_ID);
		aWriter.addFormRow(academicosWidget);
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>Datos migratorios</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, DATOS_MIGRATORIOS_PATH ,"block_DMS");
		UIDynamicListWidget migratoriosWidget = aWriter.newCustomWidget(DATOS_MIGRATORIOS_PATH2, new UIDynamicListWidgetFactory());
		migratoriosWidget.setListId(DATOS_MIGRATORIOS_ID);
		aWriter.addFormRow(migratoriosWidget);
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>Datos patrimoniales y seguimiento</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, DATOS_PATRIMONIALES_PATH, "block_DPS");
		UIDynamicListWidget patrimonialesWidget = aWriter.newCustomWidget(DATOS_PATRIMONIALES_PATH2, new UIDynamicListWidgetFactory());
		patrimonialesWidget.setListId(DATOS_PATRIMONIALES_ID);
		aWriter.addFormRow(patrimonialesWidget);
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>Datos personales sobre pasatiempos, entretenimiento y diversi n</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, DATOS_PASATIEMPOS_PATH, "block_DPEDS");
		UIDynamicListWidget pasatiemposWidget = aWriter.newCustomWidget(DATOS_PASATIEMPOS_PATH2, new UIDynamicListWidgetFactory());
		pasatiemposWidget.setListId(DATOS_PASATIEMPOS_ID);
		aWriter.addFormRow(pasatiemposWidget);
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>Datos legales</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, DATOS_LEGALES_PATH, "block_DLeS");
		UIDynamicListWidget legalesWidget = aWriter.newCustomWidget(DATOS_LEGALES_PATH2, new UIDynamicListWidgetFactory());
		legalesWidget.setListId(DATOS_LEGALES_ID);
		aWriter.addFormRow(legalesWidget);
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>Datos personales relacionados con aspectos personales</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, DATOS_ASPECTOS_PATH, "block_DPRAPS");
		UIDynamicListWidget aspectosWidget = aWriter.newCustomWidget(DATOS_ASPECTOS_PATH2, new UIDynamicListWidgetFactory());
		aspectosWidget.setListId(DATOS_ASPECTOS_ID);
		aWriter.addFormRow(aspectosWidget);
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>Datos relacionados con las preferencias de consumo, h bitos, gustos, necesidades</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, DATOS_PREFERENCIAS_PATH, "block_DRPCHGNS");
		UIDynamicListWidget preferenciasWidget = aWriter.newCustomWidget(DATOS_PREFERENCIAS_PATH2, new UIDynamicListWidgetFactory());
		preferenciasWidget.setListId(DATOS_PREFERENCIAS_ID);
		aWriter.addFormRow(preferenciasWidget);
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>Datos personales relacionados con el rendimiento laboral</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, DATOS_RENDIMIENTO_PATH, "block_DRLS");
		UIDynamicListWidget rendimientoWidget = aWriter.newCustomWidget(DATOS_RENDIMIENTO_PATH2, new UIDynamicListWidgetFactory());
		rendimientoWidget.setListId(DATOS_RENDIMIENTO_ID);
		aWriter.addFormRow(rendimientoWidget);
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>Datos de localización</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, DATOS_LOCALIZACION_PATH, "block_DLoS");
		UIDynamicListWidget localizacionWidget = aWriter.newCustomWidget(DATOS_LOCALIZACION_PATH2, new UIDynamicListWidgetFactory());
		localizacionWidget.setListId(DATOS_LOCALIZACION_ID);
		aWriter.addFormRow(localizacionWidget);
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>Datos especiales o especialmente protegidos</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, DATOS_ESPECIALES_PATH, "block_DES");
		UIDynamicListWidget especialesWidget = aWriter.newCustomWidget(DATOS_ESPECIALES_PATH2, new UIDynamicListWidgetFactory());
		especialesWidget.setListId(DATOS_ESPECIALES_ID);
		aWriter.addFormRow(especialesWidget);
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		
		aWriter.addFormRow(Path.parse("Duracion"));
		aWriter.addFormRow(Path.parse("Fecha_inicio"));
		aWriter.addFormRow(Path.parse("Fecha_Resolucion"));
		
		aWriter.endExpandCollapseBlock();
		
		aWriter.add_cr("<h3>Recomendaciones de la oficina de proteccion de datos personales</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		Path revPath = Path.parse("./Revicion_Metodologica_seg");
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
		aWriter.endExpandCollapseBlock();

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
        

		
		////////////////////////////////////////////////////


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
		
		//para los que tienen llave foranea
		aWriter.addJS_cr("function setList(path, listId, valuesArray){");
		aWriter.addJS_cr("const elementList = document.getElementById(listId).children;");
		aWriter.addJS_cr("valuesArray.forEach(function(element, index){");
		aWriter.addJS_cr("ebx_form_setValue(path + '[' + index + ']' , element);");
		aWriter.addJS_cr("elementList.item(index).style.display = '';");
		aWriter.addJS_cr("});");
		aWriter.addJS_cr("}");
		
		//para los que no tienen llave foranea
		aWriter.addJS_cr("function setList2(path, listId, valuesArray){");
		aWriter.addJS_cr("const elementList = document.getElementById(listId).children;");
		aWriter.addJS_cr("valuesArray.forEach(function(element, index){");
		aWriter.addJS_cr("ebx_form_setValue(path + '[' + index + ']' , element.key);");
		aWriter.addJS_cr("elementList.item(index).style.display = '';");
		aWriter.addJS_cr("});");
		aWriter.addJS_cr("}");
		///////////////////////////////////////////////////
        
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
	
	private void startBlock(UserServicePaneWriter aWriter, String blockId, Boolean isDisplayed) {
		String display = isDisplayed ? "display:block" : "display:none";
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
    	AdaptationTable table = dataset.getTable(Path.parse("/root/Gestion_brechas_privacidad/Notificacion_violacion_seguridad"));
    	
    	//String identificador = null;
    	String responsable = null;
    	List<String> medrems = null;
		List<String> tipos = null;
		List<String> categorias = null;
		List<String> identificacionContactoL = null;
		List<String> datosfisicosL = null;
		List<String> datoslaboralesL = null;
		List<String> datosacademicosL = null;
		List<String> datosmigratoriosL = null;
		List<String> datospatrimonialesL = null;
		List<String> datospasatiemposL = null;
		List<String> datoslegalesL = null;
		List<String> datosaspectosL = null;
		List<String> datospreferenciasL = null;
		List<String> datosrendimientoL = null;
		List<String> datoslocalizacionL = null;
		List<String> datosespecialesL = null;
		boolean identificacionContacto = false;
		boolean datosfisicos = false;
		boolean datoslaborales = false;
		boolean datosacademicos = false;
		boolean datosmigratorios = false;
		boolean datospatrimoniales = false;
		boolean datospasatiempos = false;
		boolean datoslegales = false;
		boolean datosaspectos = false;
		boolean datospreferencias = false;
		boolean datosrendimiento = false;
		boolean datoslocalizacion = false;
		boolean datosespeciales = false;
    	//String procesoOperativo = null;

    	String value = anAjaxContext.getParameter("fieldValue");
    	System.out.println(value);
    	
    	if(value != null && !value.isBlank()) {

	    	Adaptation record = table.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(value));
	    	
	    	responsable = record.getString(Path.parse("./NombrePersonasdesignadas"));
	    	medrems = record.getList(Path.parse("./AccionesC"));
			tipos = record.getList(Path.parse("./Tipo_violacion_seguridad"));
			categorias = record.getList(Path.parse("./CTitulares"));
			identificacionContacto = record.get_boolean(Path.parse("./Datos_identificacion_contacto/Datosiden_decision"));
			datosfisicos = record.get_boolean(Path.parse("./Datos_Caracteristicas_Fisicas/Datos_CF_Decision"));
			datoslaborales = record.get_boolean(Path.parse("./Datos_Laborales/Datos_Laborales_Decision"));
			datosacademicos = record.get_boolean(Path.parse("./Datos_Academicos/Datos_Academicos_Decision"));
			datosmigratorios = record.get_boolean(Path.parse("./Datos_Migratorios/Datos_Migratorios_Decision"));
			datospatrimoniales = record.get_boolean(Path.parse("./Datos_Patrimoniales_Financieros/Datos_PatriFinan_Decision"));
			datospasatiempos = record.get_boolean(Path.parse("./Datos_PSPED/Datos_PPED_Decision"));
			datoslegales = record.get_boolean(Path.parse("./Datos_Legales/Datos_Legales_Decision"));
			datosaspectos = record.get_boolean(Path.parse("./Datos_PRAP/Datos_PRAP_Decision"));
			datospreferencias = record.get_boolean(Path.parse("./Datos_RPCHGN/Datos_RPCHGN_Decision"));
			datosrendimiento = record.get_boolean(Path.parse("./Datos_PRRL/Datos_PRRL_Decision"));
			datoslocalizacion = record.get_boolean(Path.parse("./Datos_Localizacion/Datos_Localizacion_Decision"));
			datosespeciales = record.get_boolean(Path.parse("./Datos_Especiales/Datos_Especiales_Decision"));

			identificacionContactoL = record.getList(Path.parse("./Datos_identificacion_contacto/Catalo_identificacion_contacto"));
			datosfisicosL = record.getList(Path.parse("./Datos_Caracteristicas_Fisicas/Catalogo_Caracteristicas_Fisicas"));
			datoslaboralesL = record.getList(Path.parse("./Datos_Laborales/Catalogo_Datos_Laborales"));
			datosacademicosL = record.getList(Path.parse("./Datos_Academicos/Catalogo_Datos_Academicos"));
			datosmigratoriosL = record.getList(Path.parse("./Datos_Migratorios/Catalogo_Datos_Migratorios"));
			datospatrimonialesL = record.getList(Path.parse("./Datos_Patrimoniales_Financieros/Catalogo_Datos_PatriFinan"));
			datospasatiemposL = record.getList(Path.parse("./Datos_PSPED/Catalogo_DPPED"));
			datoslegalesL = record.getList(Path.parse("./Datos_Legales/Catalogo_Datos_Legales"));
			datosaspectosL = record.getList(Path.parse("./Datos_PRAP/Catalogo_DPRAP"));
			datospreferenciasL = record.getList(Path.parse("./Datos_RPCHGN/Catalogo_DRPCHGN"));
			datosrendimientoL = record.getList(Path.parse("./Datos_PRRL/Catalogo_DPRRL"));
			datoslocalizacionL = record.getList(Path.parse("./Datos_Localizacion/Catalogo_Datos_Localizacion"));
			datosespecialesL = record.getList(Path.parse("./Datos_Especiales/Catalogo_Datos_Especiales"));
    	}
    	
    	UserServiceWriter aWriter = anAjaxResponse.getWriter();

		if(responsable == null) {
    		
    		aWriter.addJS_cr("responsable = null;");
    		
    	} else {
    	
			aWriter.addJS_cr("responsable = {");
			aWriter.addJS_cr("key: '" + responsable + "',");
			aWriter.addJS_cr("label: '',");
			aWriter.addJS_cr("previewURL: undefined");
			aWriter.addJS_cr("};");
		
    	}
		//MEDIDAS DE REMEDIACION
	    
    	aWriter.addJS_cr("medremArray = [];");
    	aWriter.addJS_cr("resetList('" + medremPrefix + "', '" + MEDIDAS_REMEDIACION_ID + "');");
    	
    	if(medrems != null) {
	    	for(String medrem : medrems) {
	    		
				aWriter.addJS_cr("medrem = {");
				aWriter.addJS_cr("key: '" + medrem + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("medremArray.push(medrem);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + medremPrefix + "', '" + MEDIDAS_REMEDIACION_ID + "', medremArray);");	
	    	
    	}

		//Tipos de Violacion
	    
    	aWriter.addJS_cr("tiposVArray = [];");
    	aWriter.addJS_cr("resetList('" + tipovPrefix + "', '" + TIPOV_ID + "');");
    	
    	if(tipos != null) {
	    	for(String tipov : tipos) {
	    		
				aWriter.addJS_cr("tipov = {");
				aWriter.addJS_cr("key: '" + tipov + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("tiposVArray.push(tipov);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + tipovPrefix + "', '" + TIPOV_ID + "', tiposVArray);");	
	    	
    	}
		
//Categorias Titulares afectados
	    
    	aWriter.addJS_cr("categoriasArray = [];");
    	aWriter.addJS_cr("resetList('" + categoriataPrefix + "', '" + CATEGORIATA_ID + "');");
    	
    	if(categorias != null) {
	    	for(String categoria : categorias) {
	    		
				aWriter.addJS_cr("categoria = {");
				aWriter.addJS_cr("key: '" + categoria + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("categoriasArray.push(categoria);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + categoriataPrefix + "', '" + CATEGORIATA_ID + "', categoriasArray);");	
	    	
    	}

//Booleano Datos de indentificacion y contacto

		//DATOS IDENTIFICACION
	    
    	aWriter.addJS_cr("datosidentificacionArray = [];");
    	aWriter.addJS_cr("resetList('" + datosidentificacionPrefix2 + "', '" + DATOS_IDENTIFICACION_ID + "');");
    	
    	if(identificacionContactoL != null) {
	    	for(String identificacionContactoS : identificacionContactoL) {
	    		
				aWriter.addJS_cr("identificacionContactoS = {");
				aWriter.addJS_cr("key: '" + identificacionContactoS + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("datosidentificacionArray.push(identificacionContactoS);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + datosidentificacionPrefix2 + "', '" + DATOS_IDENTIFICACION_ID + "', datosidentificacionArray);");	
	    	
    	}
		
		//Datos fisicos
	    
    	aWriter.addJS_cr("datosfisicosArray = [];");
    	aWriter.addJS_cr("resetList('" + datosfisicosPrefix2 + "', '" + DATOS_FISICOS_ID + "');");
    	
    	if(datosfisicosL != null) {
	    	for(String datosfisicosS : datosfisicosL) {
	    		
				aWriter.addJS_cr("datosfisicosS = {");
				aWriter.addJS_cr("key: '" + datosfisicosS + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("datosfisicosArray.push(datosfisicosS);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + datosfisicosPrefix2 + "', '" + DATOS_FISICOS_ID + "', datosfisicosArray);");	
	    	
    	}
		
		//Datos Laborales
	    
    	aWriter.addJS_cr("datoslaboralesArray = [];");
    	aWriter.addJS_cr("resetList('" + datoslaboralesPrefix2 + "', '" + DATOS_LABORALES_ID + "');");
    	
    	if(datoslaboralesL != null) {
	    	for(String datoslaboralesS : datoslaboralesL) {
	    		
				aWriter.addJS_cr("datoslaboralesS = {");
				aWriter.addJS_cr("key: '" + datoslaboralesS + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("datoslaboralesArray.push(datoslaboralesS);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + datoslaboralesPrefix2 + "', '" + DATOS_LABORALES_ID + "', datoslaboralesArray);");	
	    	
    	}
		
		//Datos Academicos
	    
    	aWriter.addJS_cr("datosacademicosArray = [];");
    	aWriter.addJS_cr("resetList('" + datosacademicosPrefix2 + "', '" + DATOS_ACADEMICOS_ID + "');");
    	
    	if(datosacademicosL != null) {
	    	for(String datosacademicosS : datosacademicosL) {
	    		
				aWriter.addJS_cr("datosacademicosS = {");
				aWriter.addJS_cr("key: '" + datosacademicosS + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("datosacademicosArray.push(datosacademicosS);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + datosacademicosPrefix2 + "', '" + DATOS_ACADEMICOS_ID + "', datosacademicosArray);");	
	    	
    	}
		
		//Datos Migratorios
	    
    	aWriter.addJS_cr("datosmigratoriosArray = [];");
    	aWriter.addJS_cr("resetList('" + datosmigratoriosPrefix2 + "', '" + DATOS_MIGRATORIOS_ID + "');");
    	
    	if(datosmigratoriosL != null) {
	    	for(String datosmigratoriosS : datosmigratoriosL) {
	    		
				aWriter.addJS_cr("datosmigratoriosS = {");
				aWriter.addJS_cr("key: '" + datosmigratoriosS + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("datosmigratoriosArray.push(datosmigratoriosS);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + datosmigratoriosPrefix2 + "', '" + DATOS_MIGRATORIOS_ID + "', datosmigratoriosArray);");	
	    	
    	}
		
		//Datos Patrimoniales
	    
    	aWriter.addJS_cr("datospatrimonialesArray = [];");
    	aWriter.addJS_cr("resetList('" + datospatrimonialesPrefix2 + "', '" + DATOS_PATRIMONIALES_ID + "');");
    	
    	if(datospatrimonialesL != null) {
	    	for(String datospatrimonialesS : datospatrimonialesL) {
	    		
				aWriter.addJS_cr("datospatrimonialesS = {");
				aWriter.addJS_cr("key: '" + datospatrimonialesS + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("datospatrimonialesArray.push(datospatrimonialesS);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + datospatrimonialesPrefix2 + "', '" + DATOS_PATRIMONIALES_ID + "', datospatrimonialesArray);");	
	    	
    	}
		
		//Datos Pasatiempos
	    
    	aWriter.addJS_cr("datospasatiemposArray = [];");
    	aWriter.addJS_cr("resetList('" + datospasatiemposPrefix2 + "', '" + DATOS_PASATIEMPOS_ID + "');");
    	
    	if(datospasatiemposL != null) {
	    	for(String datospasatiemposS : datospasatiemposL) {
	    		
				aWriter.addJS_cr("datospasatiemposS = {");
				aWriter.addJS_cr("key: '" + datospasatiemposS + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("datospasatiemposArray.push(datospasatiemposS);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + datospasatiemposPrefix2 + "', '" + DATOS_PASATIEMPOS_ID + "', datospasatiemposArray);");	
	    	
    	}
		
		//Datos Legales
	    
    	aWriter.addJS_cr("datoslegalesArray = [];");
    	aWriter.addJS_cr("resetList('" + datoslegalesPrefix2 + "', '" + DATOS_LEGALES_ID + "');");
    	
    	if(datoslegalesL != null) {
	    	for(String datoslegalesS : datoslegalesL) {
	    		
				aWriter.addJS_cr("datoslegalesS = {");
				aWriter.addJS_cr("key: '" + datoslegalesS + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("datoslegalesArray.push(datoslegalesS);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + datoslegalesPrefix2 + "', '" + DATOS_LEGALES_ID + "', datoslegalesArray);");	
	    	
    	}
		
		//Datos Aspectos
	    
    	aWriter.addJS_cr("datosaspectosArray = [];");
    	aWriter.addJS_cr("resetList('" + datosaspectosPrefix2 + "', '" + DATOS_ASPECTOS_ID + "');");
    	
    	if(datosaspectosL != null) {
	    	for(String datosaspectosS : datosaspectosL) {
	    		
				aWriter.addJS_cr("datosaspectosS = {");
				aWriter.addJS_cr("key: '" + datosaspectosS + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("datosaspectosArray.push(datosaspectosS);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + datosaspectosPrefix2 + "', '" + DATOS_ASPECTOS_ID + "', datosaspectosArray);");	
	    	
    	}
		
		//Datos Preferencias
	    
    	aWriter.addJS_cr("datospreferenciasArray = [];");
    	aWriter.addJS_cr("resetList('" + datospreferenciasPrefix2 + "', '" + DATOS_PREFERENCIAS_ID + "');");
    	
    	if(datospreferenciasL != null) {
	    	for(String datospreferenciasS : datospreferenciasL) {
	    		
				aWriter.addJS_cr("datospreferenciasS = {");
				aWriter.addJS_cr("key: '" + datospreferenciasS + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("datospreferenciasArray.push(datospreferenciasS);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + datospreferenciasPrefix2 + "', '" + DATOS_PREFERENCIAS_ID + "', datospreferenciasArray);");	
	    	
    	}
		
		//Datos Rendimiento
	    
    	aWriter.addJS_cr("datosrendimientoArray = [];");
    	aWriter.addJS_cr("resetList('" + datosrendimientoPrefix2 + "', '" + DATOS_RENDIMIENTO_ID + "');");
    	
    	if(datosrendimientoL != null) {
	    	for(String datosrendimientoS : datosrendimientoL) {
	    		
				aWriter.addJS_cr("datosrendimientoS = {");
				aWriter.addJS_cr("key: '" + datosrendimientoS + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("datosrendimientoArray.push(datosrendimientoS);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + datosrendimientoPrefix2 + "', '" + DATOS_RENDIMIENTO_ID + "', datosrendimientoArray);");	
	    	
    	}
		
		//Datos Localizacion
	    
    	aWriter.addJS_cr("datoslocalizacionArray = [];");
    	aWriter.addJS_cr("resetList('" + datoslocalizacionPrefix2 + "', '" + DATOS_LOCALIZACION_ID + "');");
    	
    	if(datoslocalizacionL != null) {
	    	for(String datoslocalizacionS : datoslocalizacionL) {
	    		
				aWriter.addJS_cr("datoslocalizacionS = {");
				aWriter.addJS_cr("key: '" + datoslocalizacionS + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("datoslocalizacionArray.push(datoslocalizacionS);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + datoslocalizacionPrefix2 + "', '" + DATOS_LOCALIZACION_ID + "', datoslocalizacionArray);");	
	    	
    	}
	
		//Datos Especiales
	    
    	aWriter.addJS_cr("datosespecialesnArray = [];");
    	aWriter.addJS_cr("resetList('" + datosespecialesPrefix2 + "', '" + DATOS_ESPECIALES_ID + "');");
    	
    	if(datosespecialesL != null) {
	    	for(String datosespecialesS : datosespecialesL) {
	    		
				aWriter.addJS_cr("datosespecialesS = {");
				aWriter.addJS_cr("key: '" + datosespecialesS + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("datosespecialesnArray.push(datosespecialesS);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + datosespecialesPrefix2 + "', '" + DATOS_ESPECIALES_ID + "', datosespecialesnArray);");	
	    	
    	}
		

		aWriter.addJS_cr("ebx_form_setValue('" + responsablePrefix + "', responsable)");
		aWriter.addJS_cr("ebx_form_setValue('" + datosidentificacionPrefix + "', " +  identificacionContacto + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + datosfisicosPrefix + "', " +  datosfisicos + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + datoslaboralesPrefix + "', " +  datoslaborales + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + datosacademicosPrefix + "', " +  datosacademicos + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + datosmigratoriosPrefix + "', " +  datosmigratorios + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + datospatrimonialesPrefix + "', " +  datospatrimoniales + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + datospasatiemposPrefix + "', " +  datospasatiempos + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + datoslegalesPrefix + "', " +  datoslegales + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + datosaspectosPrefix + "', " +  datosaspectos + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + datospreferenciasPrefix + "', " +  datospreferencias + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + datosrendimientoPrefix + "', " +  datosrendimiento + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + datoslocalizacionPrefix + "', " +  datoslocalizacion + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + datosespecialesPrefix + "', " +  datosespeciales + ")");

    	
    	
    }
	@Override
	public void validate(UserServiceValidateContext<T> aContext) {

		
	}

}
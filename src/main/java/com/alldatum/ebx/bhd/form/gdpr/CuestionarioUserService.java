package com.alldatum.ebx.bhd.form.gdpr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alldatum.ebx.bhd.util.AdaptationUtil;
import com.alldatum.ebx.bhd.widget.UIDynamicListWidget;
import com.alldatum.ebx.bhd.widget.UIDynamicListWidgetFactory;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.interactions.InteractionHelper.ParametersMap;
import com.orchestranetworks.interactions.SessionInteraction;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.service.ServiceKey;
import com.orchestranetworks.ui.base.JsFunctionCall;
import com.onwbp.adaptation.AdaptationTable;
import com.orchestranetworks.ui.form.widget.UIDropDownList;
import com.orchestranetworks.ui.form.widget.UIRadioButtonGroup;
import com.orchestranetworks.ui.selection.RecordEntitySelection;
import com.orchestranetworks.ui.selection.TableEntitySelection;
import com.orchestranetworks.userservice.ObjectKey;
import com.orchestranetworks.userservice.UserService;
import com.orchestranetworks.ui.form.widget.UIComboBox;
import com.orchestranetworks.userservice.UserServiceDisplayConfigurator;
import com.orchestranetworks.userservice.UserServiceEventContext;
import com.orchestranetworks.userservice.UserServiceEventOutcome;
import com.orchestranetworks.userservice.UserServiceObjectContextBuilder;
import com.onwbp.adaptation.PrimaryKey;
import com.orchestranetworks.userservice.UserServicePaneContext;
import com.orchestranetworks.userservice.UserServiceWriter;
import com.orchestranetworks.userservice.UserServicePaneWriter;
import com.orchestranetworks.userservice.UserServiceProcessEventOutcomeContext;
import com.orchestranetworks.userservice.UserServiceAjaxContext;
import com.orchestranetworks.userservice.UserServiceAjaxResponse;
import com.orchestranetworks.userservice.UserServiceSetupDisplayContext;
import com.orchestranetworks.userservice.UserServiceSetupObjectContext;
import com.orchestranetworks.userservice.UserServiceValidateContext;

public class CuestionarioUserService<T extends TableEntitySelection> implements UserService<T> {
	
	private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");

	//private static final Path  = Path.parse("");
	private static final Path NOMBRE_FABAN = Path.parse("Nombre");
	private static final Path NOMBRE_FNF = Path.parse("Campospregunta9/Cesiones_FAF_C/IdentificadorNFAF_C");
	private static final Path NOMBRE_FNCV = Path.parse("Campospregunta9/Cesiones_FACV_C/Identificador_CFACV_C");
	private static final Path ID_FAB_PATH = Path.parse("IdentificadorFAB");
	private static final Path RESPONSABLE_PATH = Path.parse("ResponsableC");
	private static final Path CORRESPONSABLE_PATH = Path.parse("CorresponsableC");
	private static final Path PROCESOS_OPERATIVOS_PATH = Path.parse("ProcesooperativosC");
	private static final Path JUICIO_PATH = Path.parse("JucioAB");
	private static final Path DESTINATARIO_FABAN_PATH = Path.parse("Campospregunta9/Cesion_FAB_C/Destinatario_FAB_C");
	private static final Path FINALIDADES_FABAN_PATH = Path.parse("Campospregunta9/Cesion_FAB_C/Finalidades_FAB_C");
	private static final Path CAUSAS_LEG_FABAN_PATH = Path.parse("Campospregunta9/Cesion_FAB_C/CausasLFAB_C");
	private static final Path DESTINATARIO_FNF_PATH = Path.parse("Campospregunta9/Cesiones_FAF_C/DestinatarioFAF_C");
	private static final Path FINALIDADES_FNF_PATH = Path.parse("Campospregunta9/Cesiones_FAF_C/Finalidades_FAF_C");
	private static final Path CAUSAS_LEG_FNF_PATH = Path.parse("Campospregunta9/Cesiones_FAF_C/CausasLFAF_C");
	private static final Path DESTINATARIO_FNCV_PATH = Path.parse("Campospregunta9/Cesiones_FACV_C/Destinatario_CFACV_C");
	private static final Path FINALIDADES_FNCV_PATH = Path.parse("Campospregunta9/Cesiones_FACV_C/Finalidades_CFACV_C");
	private static final Path CAUSAS_LEG_FNCV_PATH = Path.parse("Campospregunta9/Cesiones_FACV_C/CausasLegitimacion_CFACV_C");
	private static final Path DESTINATARIO_EFAB_PATH = Path.parse("Campospregunta9/Encargados_FAB_C/Destinatario_EFAB_C");
	private static final Path OPERACION_EFAB_PATH = Path.parse("Campospregunta9/Encargados_FAB_C/OperacionEjecuta_EFAB_C");
	private static final Path NOMBRECONTRATO_EFAB_PATH = Path.parse("Campospregunta9/Encargados_FAB_C/NombreContrato_EFAB_C");
	private static final Path FECHAFORM_EFAB_PATH = Path.parse("Campospregunta9/Encargados_FAB_C/FechaFormalizacion_EFAB_C");
	private static final Path PERIODO_EFAB_PATH = Path.parse("Campospregunta9/Encargados_FAB_C/PeriodoVigencia_EFAB_C");
	private static final Path ESTATUSVIGENCIA_EFAB_PATH = Path.parse("Campospregunta9/Encargados_FAB_C/EstatusVigencia_EFAB_C");
	private static final Path DESTINATARIO_EFNF_PATH = Path.parse("Campospregunta9/Encargados_FAF_C/Destinatario_FAF_C");
	private static final Path OPERACION_EFNF_PATH = Path.parse("Campospregunta9/Encargados_FAF_C/OperacioneEjecuta_EFAF_C");
	private static final Path NOMBRECONTRATO_EFNF_PATH = Path.parse("Campospregunta9/Encargados_FAF_C/NombreContrato_EFAF_C");
	private static final Path PERIODOVIGENCIA_EFNF_PATH = Path.parse("Campospregunta9/Encargados_FAF_C/PeriodoVigencia_EFAF_C");
	private static final Path ESTATUSVIGENCIA_EFNF_PATH = Path.parse("Campospregunta9/Encargados_FAF_C/EstatusVigencia_EFAF_C");
	private static final Path DESTINATARIO_EFACV_PATH = Path.parse("Campospregunta9/Encargados_FACV_C/Destinatario_EFACV_C");
	private static final Path OPERACION_EFACV_PATH = Path.parse("Campospregunta9/Encargados_FACV_C/OperacionEjecuta_EFACV_C");
	private static final Path NOMBRECONTRATO_EFACV_PATH = Path.parse("Campospregunta9/Encargados_FACV_C/NombreContrato_EFACV_C");
	private static final Path PERIODO_EFACV_PATH = Path.parse("Campospregunta9/Encargados_FACV_C/PeriodoVigencia_EFACV_C");
	private static final Path ESTATUSVIGENCIA_EFACV_PATH = Path.parse("Campospregunta9/Encargados_FACV_C/EstatusVigencia_EFACV_C");
	private static final Path DESTINATARIO_SFAB_PATH = Path.parse("Campospregunta9/SubEncargados_FAB_C/Destinatario_SEFAB_C");
	private static final Path OPERACIONES_SFAB_PATH = Path.parse("Campospregunta9/SubEncargados_FAB_C/OperacionEjecuta_SEFAB_C");
	private static final Path NOMBRECONTRATO_SFAB_PATH = Path.parse("Campospregunta9/SubEncargados_FAB_C/NombreContrato_SEFAB_C");
	private static final Path PERIODO_SFAB_PATH = Path.parse("Campospregunta9/SubEncargados_FAB_C/PeriodoVigencia_SEFAB_C");
	private static final Path ESTATUSVIGENCIA_SFAB_PATH = Path.parse("Campospregunta9/SubEncargados_FAB_C/EstatusVigencia_SEFAB_C");
	private static final Path DESTINATARIO_SFNF_PATH = Path.parse("Campospregunta9/SubEncargados_FAF_C/Destinatario_SEFAF_C");
	private static final Path OPERACIONES_SFNF_PATH = Path.parse("Campospregunta9/SubEncargados_FAF_C/OperacionEjecuta_SEFAF_C");
	private static final Path NOMBRECONTRATO_SFNF_PATH = Path.parse("Campospregunta9/SubEncargados_FAF_C/NombreContrato_SEFAF_C");
	private static final Path PERIODO_SFNF_PATH = Path.parse("Campospregunta9/SubEncargados_FAF_C/PeriodoVigencia_SEFAF_C");
	private static final Path ESTATUSVIGENCIA_SFNF_PATH = Path.parse("Campospregunta9/SubEncargados_FAF_C/EstatusVigencia_SEFAF_C");
	private static final Path DESTINATARIO_SFNCV_PATH = Path.parse("Campospregunta9/SubEncargados_FACV_C/Destinatario_SEFACV_C");
	private static final Path OPERACIONES_SFNCV_PATH = Path.parse("Campospregunta9/SubEncargados_FACV_C/OperacionEjectua_SEFACV_C");
	private static final Path NOMBRECONTRATO_SFNCV_PATH = Path.parse("Campospregunta9/SubEncargados_FACV_C/NombreContrato_SEFACV_C");
	private static final Path PERIODO_SFNCV_PATH = Path.parse("Campospregunta9/SubEncargados_FACV_C/PeriodoVigencia_SEFACV_C");
	private static final Path ESTATUSVIGENCIA_SFNCV_PATH = Path.parse("Campospregunta9/SubEncargados_FACV_C/EstatusVigencia_SEFACV_C");
	private static final Path DESTINATARIO_TFAB_PATH = Path.parse("CamposPregunta19/Transferencias_FAB_C/Destinatario_TFAB_C");
	private static final Path PAIS_TFAB_PATH = Path.parse("CamposPregunta19/Transferencias_FAB_C/Pais_TFAB_C");
	private static final Path FINALIDADES_TFAB_PATH = Path.parse("CamposPregunta19/Transferencias_FAB_C/Finalidades_TFAB_C");
	private static final Path CAUSALEG_TFAB_PATH = Path.parse("CamposPregunta19/Transferencias_FAB_C/CausaLegitimacion_TFAB_C");
	private static final Path DESTINATARIO_TFNF_PATH = Path.parse("CamposPregunta19/Transferencias_FAF_C/Destinatario_TFAF_C");
	private static final Path PAIS_TFNF_PATH = Path.parse("CamposPregunta19/Transferencias_FAF_C/Pais_TFAF_C");
	private static final Path FINALIDADES_TFNF_PATH = Path.parse("CamposPregunta19/Transferencias_FAF_C/Finalidades_TFAF_C");
	private static final Path CAUSALEG_TFNF_PATH = Path.parse("CamposPregunta19/Transferencias_FAF_C/CausasLegitimacion_TFAF_C");
	private static final Path DESTINATARIO_TFNCV_PATH = Path.parse("CamposPregunta19/Transferencias_FACV_C/Destinatario_TFACV_C");
	private static final Path PAIS_TFNCV_PATH = Path.parse("CamposPregunta19/Transferencias_FACV_C/Pais_TFACV_C");
	private static final Path FINALIDADES_TFNCV_PATH = Path.parse("CamposPregunta19/Transferencias_FACV_C/Finalidades_TFACV_C");
	private static final Path CAUSALEG_TFNCV_PATH = Path.parse("CamposPregunta19/Transferencias_FACV_C/CausasLegitimacion_TFACV_C");



	//private static final String  = "";
	private static final String RESPONSABLE_ID = "responsableList";
	private static final String CORRESPONSABLE_ID = "corresponsableList";
	private static final String JUICIO_ID = "juicioList";
	private static final String DESTINATARIO_FABAN_ID = "destinatarioList";
	private static final String FINALIDADES_FABAN_ID = "finalidadesList";
	private static final String CAUSAS_LEG_FABAN_ID = "causaslegList";
	private static final String DESTINATARIO_FNF_ID = "destintariofnfList";
	private static final String CAUSAS_LEG_FNF_ID = "causaslegfnfList";
	private static final String FINALIDADES_FNF_ID = "finalidadesfnfList";
	private static final String DESTINATARIO_FNCV_ID = "destinatariofncvList";
	private static final String FINALIDADES_FNCV_ID = "finalidadesfncvList";
	private static final String CAUSAS_LEG_FNCV_ID = "causaslegfncvList";
	private static final String DESTINATARIO_EFAB_ID = "destinatarioefabList";
	private static final String OPERACION_EFAB_ID = "operacionefabList";
	private static final String NOMBRECONTRATO_EFAB_ID = "nombrecontratoList";
	private static final String DESTINATARIO_EFNF_ID = "destinatarioefnfList";
	private static final String OPERACION_EFNF_ID = "operacionefnfList";
	private static final String NOMBRECONTRATO_EFNF_ID = "nombrecontratoefnfList";
	private static final String PERIODOVIGENCIA_EFNF_ID = "periodovigenciaefnfList";
	private static final String DESTINATARIO_EFACV_ID = "destinatarioefacvList";
	private static final String OPERACION_EFACV_ID = "operacionefacvList";
	private static final String NOMBRECONTRATO_EFACV_ID = "nombrecontratoefacvList";
	private static final String DESTINATARIO_SFAB_ID = "destinatariosfabList";
	private static final String OPERACION_SFAB_ID = "operacionsfabList";
	private static final String NOMBRECONTRATO_SFAB_ID = "nombrecontratosfabList";
	private static final String DESTINATARIO_SFNF_ID = "destinatariosfnfList";
	private static final String OPERACION_SFNF_ID = "operacionsfnfList";
	private static final String NOMBRECONTRATO_SFNF_ID = "nombrecontratosfnfList";
	private static final String DESTINATARIO_SFNCV_ID = "destinatariosfncvList";
	private static final String OPERACIONES_SFNCV_ID = "operacionessfncv";
	private static final String NOMBRECONTRATO_SFNCV_ID = "nombrecontratosfncvList";
	private static final String DESTINATARIO_TFAB_ID = "destinatariotfabList";
	private static final String PAIS_TFAB_ID = "paistfabList";
	private static final String FINALIDADES_TFAB_ID = "finalidadestfabList";
	private static final String CAUSALEG_TFAB_ID = "causaslegtfabList";
	private static final String DESTINATARIO_TFNF_ID = "destinatariotfnfList";
	private static final String PAIS_TFNF_ID = "paistfnfList";
	private static final String FINALIDADES_TFNF_ID = "finalidadestfnfList";
	private static final String CAUSALEG_TFNF_ID = "causaslegtfnfList";
	private static final String DESTINATARIO_TFNCV_ID = "destinatariotfncvList";
	private static final String PAIS_TFNCV_ID = "paistfncvList";
	private static final String FINALIDADES_TFNCV_ID = "finalidadestfncvList";
	private static final String CAUSALEG_TFNCV_ID = "causalegtfncvList";

	
	private static final Path MENU_ACEPTABLE_PATH = Path.parse("./Pregunta_21");
	private static final Path CONDICION_ACEPTABLE_PATH = Path.parse("./Justificacion_Preg21");
	private static final Path NOMBRE_REVISORA_PATH = Path.parse("./NombreRevisorA");
	private static final Path ROL_CARGOA_PATH = Path.parse("./rolocargoCA");
	

	//iInaceptable
	private static final Path MENU_INACEPTABLE_PATH = Path.parse("./InaceptableC");
	private static final Path CONDICION_INACEPTABLE_PATH = Path.parse("./Justificacioninaceptable");
	private static final Path NOMBRE_REVISORI_PATH = Path.parse("./NombrerevisoIna");
	private static final Path ROL_CARGOI_PATH = Path.parse("./rolocargoCINA");
	
	
	
	
	private String idFABPrefix;
	private String idFNFPrefix;
	private String idFNCVDPrefix;
	private String responsablePrefix;
	private String corresponsablePrefix;
	private String procesosPrefix;
	private String juicioPrefix;
	private String destinatariofabanPrefix;
	private String finalidadesfabanPrefix;
	private String causaslegfabanPrefix;
	private String destinatariofnfPrefix;
	private String finalidadesfnfPrefix;
	private String causaslegfnfPrefix;
	private String destinatariofncvPrefix;
	private String finalidadesfncvPrefix;
	private String causaslegfncvPrefix;
	private String destinatarioefabPrefix;
	private String operacionesefabPrefix;
	private String nombreconrtatoefabPrefix;
	private String fechaformefabPrefix;
	private String periodovigenciaPrefix;
	private String estatusvigenciaefabPrefix;
	private String destinatarioefnfPerfix;
	private String operacionefnfPerfix;
	private String nombrecontratoefnfPerfix;
	private String periodovigenciaefnfPerfix;
	private String estatusvigenciaefnfPerfix;
	private String destinatarioefacvPerfix;
	private String operacionefacvPerfix;
	private String nombrecontratoefacvPerfix;
	private String periodovigenciaefacvPerfix;
	private String estatusvigenciaefacvPerfix;
	private String destinatariosfabPrefix;
	private String operacionesfabPrefix;
	private String nombrecontratosfabPrefix;
	private String periodovigenciasfabPrefix;
	private String estatusvigenciasfabPrefix;
	private String destinatariosfnfPrefix;
	private String operacionesfnfPrefix;
	private String nombrecontratosfnfPrefix;
	private String periodovigenciasfnfPrefix;
	private String estatusvigenciasfnfPrefix;
	private String destinatariosfncvPrefix;
	private String operacionesfncvPrefix;
	private String nombrecontratosfncvPrefix;
	private String periodovigenciasfncvPrefix;
	private String estatusvigenciasfncvPrefix;
	private String destinatariotfabPrefix;
	private String paistfabPrefix;
	private String finalidadestfabPrefix;
	private String causalegtfabPrefix;
	private String destinatariostfnfPrefix;
	private String paistfnfPrefix;
	private String finalidadestfnfPrefix;
	private String causaslegtfnfPrefix;
	private String destinatariostfncvPrefix;
	private String paistfncvPrefix;
	private String finalidadestfncvPrefix;
	private String causalegtfncvPrefix;
	
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

		idFABPrefix = aWriter.getPrefixedPath(ID_FAB_PATH).format();
		responsablePrefix = aWriter.getPrefixedPath(RESPONSABLE_PATH).format();
		corresponsablePrefix = aWriter.getPrefixedPath(CORRESPONSABLE_PATH).format();
		procesosPrefix = aWriter.getPrefixedPath(PROCESOS_OPERATIVOS_PATH).format();
		juicioPrefix = aWriter.getPrefixedPath(JUICIO_PATH).format();
		destinatariofabanPrefix = aWriter.getPrefixedPath(DESTINATARIO_FABAN_PATH).format();
		finalidadesfabanPrefix = aWriter.getPrefixedPath(FINALIDADES_FABAN_PATH).format();
		causaslegfabanPrefix = aWriter.getPrefixedPath(CAUSAS_LEG_FABAN_PATH).format();
		destinatariofnfPrefix = aWriter.getPrefixedPath(DESTINATARIO_FNF_PATH).format();
		finalidadesfnfPrefix = aWriter.getPrefixedPath(FINALIDADES_FNF_PATH).format();
		causaslegfnfPrefix = aWriter.getPrefixedPath(CAUSAS_LEG_FNF_PATH).format();
		destinatariofncvPrefix =aWriter.getPrefixedPath(DESTINATARIO_FNCV_PATH).format();
		finalidadesfncvPrefix = aWriter.getPrefixedPath(FINALIDADES_FNCV_PATH).format();
		causaslegfncvPrefix = aWriter.getPrefixedPath(CAUSAS_LEG_FNCV_PATH).format();
		destinatarioefabPrefix = aWriter.getPrefixedPath(DESTINATARIO_EFAB_PATH).format();
		operacionesefabPrefix = aWriter.getPrefixedPath(OPERACION_EFAB_PATH).format();
		nombreconrtatoefabPrefix = aWriter.getPrefixedPath(NOMBRECONTRATO_EFAB_PATH).format();
		fechaformefabPrefix = aWriter.getPrefixedPath(FECHAFORM_EFAB_PATH).format();
		periodovigenciaPrefix = aWriter.getPrefixedPath(PERIODO_EFAB_PATH).format();
		estatusvigenciaefabPrefix = aWriter.getPrefixedPath(ESTATUSVIGENCIA_EFAB_PATH).format();
		destinatarioefnfPerfix = aWriter.getPrefixedPath(DESTINATARIO_EFNF_PATH).format();
		operacionefnfPerfix = aWriter.getPrefixedPath(OPERACION_EFNF_PATH).format();
		nombrecontratoefnfPerfix = aWriter.getPrefixedPath(NOMBRECONTRATO_EFNF_PATH).format();
		periodovigenciaefnfPerfix = aWriter.getPrefixedPath(PERIODOVIGENCIA_EFNF_PATH).format();
		estatusvigenciaefnfPerfix = aWriter.getPrefixedPath(ESTATUSVIGENCIA_EFNF_PATH).format();
		destinatarioefacvPerfix = aWriter.getPrefixedPath(DESTINATARIO_EFACV_PATH).format();
		operacionefacvPerfix = aWriter.getPrefixedPath(OPERACION_EFACV_PATH).format();
		nombrecontratoefacvPerfix = aWriter.getPrefixedPath(NOMBRECONTRATO_EFACV_PATH).format();
		periodovigenciaefacvPerfix = aWriter.getPrefixedPath(PERIODO_EFACV_PATH).format();
		estatusvigenciaefacvPerfix = aWriter.getPrefixedPath(ESTATUSVIGENCIA_EFACV_PATH).format();
		destinatariosfabPrefix = aWriter.getPrefixedPath(DESTINATARIO_SFAB_PATH).format();
		operacionesfabPrefix = aWriter.getPrefixedPath(OPERACIONES_SFAB_PATH).format();
		nombrecontratosfabPrefix = aWriter.getPrefixedPath(NOMBRECONTRATO_SFAB_PATH).format();
		periodovigenciasfabPrefix = aWriter.getPrefixedPath(PERIODO_SFAB_PATH).format();
		estatusvigenciasfabPrefix = aWriter.getPrefixedPath(ESTATUSVIGENCIA_SFAB_PATH).format();
		destinatariosfnfPrefix = aWriter.getPrefixedPath(DESTINATARIO_SFNF_PATH).format();
		operacionesfnfPrefix = aWriter.getPrefixedPath(OPERACIONES_SFNF_PATH).format();
		nombrecontratosfnfPrefix = aWriter.getPrefixedPath(NOMBRECONTRATO_SFNF_PATH).format();
		periodovigenciasfnfPrefix = aWriter.getPrefixedPath(PERIODO_SFNF_PATH).format();
		estatusvigenciasfnfPrefix = aWriter.getPrefixedPath(ESTATUSVIGENCIA_SFNF_PATH).format();
		destinatariosfncvPrefix = aWriter.getPrefixedPath(DESTINATARIO_SFNCV_PATH).format();
		operacionesfncvPrefix = aWriter.getPrefixedPath(OPERACIONES_SFNCV_PATH).format();
		nombrecontratosfncvPrefix = aWriter.getPrefixedPath(NOMBRECONTRATO_SFNCV_PATH).format();
		periodovigenciasfncvPrefix = aWriter.getPrefixedPath(PERIODO_SFNCV_PATH).format();
		estatusvigenciasfncvPrefix = aWriter.getPrefixedPath(ESTATUSVIGENCIA_SFNCV_PATH).format();
		destinatariotfabPrefix = aWriter.getPrefixedPath(DESTINATARIO_TFAB_PATH).format();
		paistfabPrefix = aWriter.getPrefixedPath(PAIS_TFAB_PATH).format();
		finalidadestfabPrefix = aWriter.getPrefixedPath(FINALIDADES_TFAB_PATH).format();
		causalegtfabPrefix = aWriter.getPrefixedPath(CAUSALEG_TFAB_PATH).format();
		destinatariostfnfPrefix = aWriter.getPrefixedPath(DESTINATARIO_TFNF_PATH).format();
		paistfnfPrefix = aWriter.getPrefixedPath(PAIS_TFNF_PATH).format();
		finalidadestfnfPrefix = aWriter.getPrefixedPath(FINALIDADES_TFNF_PATH).format();
		causaslegtfnfPrefix = aWriter.getPrefixedPath(CAUSALEG_TFNF_PATH).format();
		destinatariostfncvPrefix = aWriter.getPrefixedPath(DESTINATARIO_TFNCV_PATH).format();
		paistfncvPrefix = aWriter.getPrefixedPath(PAIS_TFNCV_PATH).format();
		finalidadestfncvPrefix = aWriter.getPrefixedPath(FINALIDADES_TFNCV_PATH).format();
		causalegtfncvPrefix = aWriter.getPrefixedPath(CAUSALEG_TFNCV_PATH).format();


		aWriter.add_cr("<h3>Datos básicos del cuestionario para evaluación de impacto</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Fecha_creacion"));
		aWriter.addFormRow(Path.parse("Identificador"));
		UIComboBox inhTipImp = aWriter.newComboBox(NOMBRE_FABAN);
		inhTipImp.setActionOnAfterValueChanged(JsFunctionCall.on("updateInheritedFields"));
		aWriter.addFormRow(ID_FAB_PATH);
		aWriter.addFormRow(inhTipImp);
		UIDynamicListWidget respWidget = aWriter.newCustomWidget(RESPONSABLE_PATH, new UIDynamicListWidgetFactory());
		respWidget.setListId(RESPONSABLE_ID);
		aWriter.addFormRow(respWidget);
		UIDynamicListWidget correspWidget = aWriter.newCustomWidget(CORRESPONSABLE_PATH, new UIDynamicListWidgetFactory());
		correspWidget.setListId(CORRESPONSABLE_ID);
		aWriter.addFormRow(correspWidget);
		aWriter.addFormRow(PROCESOS_OPERATIVOS_PATH);
		aWriter.addFormRow(Path.parse("IdentificadorAR"));
		aWriter.endExpandCollapseBlock();	
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

		aWriter.addJS_cr("function callAjaxComponent2(value){");
        aWriter.addJS_cr("var handler = new AjaxHandler();");
        aWriter.addJS_cr("var requestParameters = '&fieldValue=' + value;");
        aWriter.addJS_cr("var request = '" + aWriter.getURLForAjaxRequest(this::ajaxCallback2) + "' + requestParameters;"); 
        aWriter.addJS_cr("handler.sendRequest(request);");
        aWriter.addJS_cr("}");

		aWriter.addJS_cr("function updateInheritedFields2(value){");
        aWriter.addJS_cr("if(value == null){");
        aWriter.addJS_cr("callAjaxComponent2('');");
        aWriter.addJS_cr("}else{");
        aWriter.addJS_cr("callAjaxComponent2(value.key);");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");

		aWriter.addJS_cr("function callAjaxComponent3(value){");
        aWriter.addJS_cr("var handler = new AjaxHandler();");
        aWriter.addJS_cr("var requestParameters = '&fieldValue=' + value;");
        aWriter.addJS_cr("var request = '" + aWriter.getURLForAjaxRequest(this::ajaxCallback3) + "' + requestParameters;"); 
        aWriter.addJS_cr("handler.sendRequest(request);");
        aWriter.addJS_cr("}");

		aWriter.addJS_cr("function updateInheritedFields3(value){");
        aWriter.addJS_cr("if(value == null){");
        aWriter.addJS_cr("callAjaxComponent3('');");
        aWriter.addJS_cr("}else{");
        aWriter.addJS_cr("callAjaxComponent3(value.key);");
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


		aWriter.add_cr("<h3>1.- ¿Se ha descrito el fin o fines del tratamiento?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Pregunta1"), "block_Pregunta1");
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();


		aWriter.add_cr("<h3>2.- ¿Existe una identificación clara del Responsable del tratamiento?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Pregunta2"), "block_Pregunta2");
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();
		
		aWriter.add_cr("<h3>3.- ¿Se ha realizado un análisis de las bases jurídicas del tratamiento con relación a cada uno de sus fines, incluyendo fines secundarios o posteriores?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Pregunta3"), "block_Pregunta3");
		UIDynamicListWidget juicioWidget = aWriter.newCustomWidget(JUICIO_PATH, new UIDynamicListWidgetFactory());
		juicioWidget.setListId(JUICIO_ID);
		aWriter.addFormRow(juicioWidget);
		aWriter.addFormRow(Path.parse("Base_juridica_C"));
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>4.- ¿Si la licitud del tratamiento se basa en el consentimiento del titular, se han analizado las condiciones que determinan su gestión según el MGPPDP y las políticas específicas correspondientes?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Pregunta_4"));
		aWriter.endExpandCollapseBlock();


		aWriter.add_cr("<h3>5.- ¿Si la licitud del tratamiento se basa en el interés legítimo, se ha llevado a cabo la ponderación de derechos, en particular, cuando se trata de menores o personas en riesgo de exclusión social u otras circunstancias que pudieran suponer discriminación para los interesados? </h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Pregunta_5"));
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>6.- ¿Si la licitud del tratamiento se basa en que este es necesario para el cumplimiento de una misión realizada en interés público o en el ejercicio de poderes públicos conferidos al responsable del tratamiento, se detallará la norma habilitante?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		Path revPath6 = Path.parse("Pregunta_6");
		UIDropDownList revDropDown6 = aWriter.newDropDownList(revPath6);
		revDropDown6.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision6"));
		aWriter.addFormRow(revDropDown6);
		String value6 = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath6).getValue();		
		startBlock(aWriter, "block_revision_si6", "Si".equalsIgnoreCase(value6));	
		aWriter.addFormRow(Path.parse("LRND_C"));
		aWriter.addFormRow(Path.parse("textopregunta6"));
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>7.- ¿Si la licitud del tratamiento se basa en que el tratamiento es necesario para el cumplimiento de una obligación legal aplicable al Responsable del tratamiento, se detallará la norma habilitante?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		Path revPath7 = Path.parse("Pregunta_7");
		UIDropDownList revDropDown7 = aWriter.newDropDownList(revPath7);
		revDropDown7.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision7"));
		aWriter.addFormRow(revDropDown7);
		String value7 = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath7).getValue();		
		startBlock(aWriter, "block_revision_si7", "Si".equalsIgnoreCase(value7));	
		aWriter.addFormRow(Path.parse("LRND_C_P7"));
		aWriter.addFormRow(Path.parse("Texto2"));
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>8.- ¿Con carácter previo a la determinación de la legitimación del tratamiento, en caso de tratarse de categorías especiales de datos, se deberá determinar la causa (para esto puede ser que la habilitación se base en el consentimiento del titular y, en tal caso, se analizarán las condiciones para su gestión)?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		aWriter.addFormRow(Path.parse("Pregunta8"));
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>9.- ¿Si existen corresponsables implicados o terceros intervinientes en el tratamiento, existe un acuerdo o acto jurídico donde se establecen sus responsabilidades?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Pregunta9"), "block_Pregunta9");
		aWriter.add_cr("<h4>Cesiones</h4>");

		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Cesiones Ficha de Analisis básico "), true);		
		UIDynamicListWidget destinatariofabanWidget = aWriter.newCustomWidget(DESTINATARIO_FABAN_PATH, new UIDynamicListWidgetFactory());
		destinatariofabanWidget.setListId(DESTINATARIO_FABAN_ID);
		aWriter.addFormRow(destinatariofabanWidget);
		UIDynamicListWidget finalidadesWidget = aWriter.newCustomWidget(FINALIDADES_FABAN_PATH, new UIDynamicListWidgetFactory());
		finalidadesWidget.setListId(FINALIDADES_FABAN_ID);
		aWriter.addFormRow(finalidadesWidget);
		UIDynamicListWidget causaslegWidget = aWriter.newCustomWidget(CAUSAS_LEG_FABAN_PATH, new UIDynamicListWidgetFactory());
		causaslegWidget.setListId(CAUSAS_LEG_FABAN_ID);
		aWriter.addFormRow(causaslegWidget);
		aWriter.endExpandCollapseBlock();

		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Cesiones Ficha de Analisis de Fase "), true);
		UIComboBox inhTipImp2 = aWriter.newComboBox(NOMBRE_FNF);
		inhTipImp2.setActionOnAfterValueChanged(JsFunctionCall.on("updateInheritedFields2"));
		aWriter.addFormRow(inhTipImp2);
		UIDynamicListWidget destinatariofnfWidget = aWriter.newCustomWidget(DESTINATARIO_FNF_PATH, new UIDynamicListWidgetFactory());
		destinatariofnfWidget.setListId(DESTINATARIO_FNF_ID);
		aWriter.addFormRow(destinatariofnfWidget);
		UIDynamicListWidget finalidadesfnfWidget = aWriter.newCustomWidget(FINALIDADES_FNF_PATH, new UIDynamicListWidgetFactory());
		finalidadesfnfWidget.setListId(FINALIDADES_FNF_ID);
		aWriter.addFormRow(finalidadesfnfWidget);
		UIDynamicListWidget causaslegfnfWidget = aWriter.newCustomWidget(CAUSAS_LEG_FNF_PATH, new UIDynamicListWidgetFactory());
		causaslegfnfWidget.setListId(CAUSAS_LEG_FNF_ID);
		aWriter.addFormRow(causaslegfnfWidget);
		aWriter.endExpandCollapseBlock();

		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Cesiones Ficha de Analisis Ciclo de Vida"), true);		
		//aWriter.addFormRow(Path.parse("Campospregunta9/Cesiones_FACV_C/Identificador_CFACV_C"));
		UIComboBox inhTipImp3 = aWriter.newComboBox(NOMBRE_FNCV);
		inhTipImp3.setActionOnAfterValueChanged(JsFunctionCall.on("updateInheritedFields3"));
		aWriter.addFormRow(inhTipImp3);
		UIDynamicListWidget destinatariofncvWidget = aWriter.newCustomWidget(DESTINATARIO_FNCV_PATH, new UIDynamicListWidgetFactory());
		destinatariofncvWidget.setListId(DESTINATARIO_FNCV_ID);
		aWriter.addFormRow(destinatariofncvWidget);
		UIDynamicListWidget finalidadesfncvWidget = aWriter.newCustomWidget(FINALIDADES_FNCV_PATH, new UIDynamicListWidgetFactory());
		finalidadesfncvWidget.setListId(FINALIDADES_FNCV_ID);
		aWriter.addFormRow(finalidadesfncvWidget);
		UIDynamicListWidget causaslegfncvWidget = aWriter.newCustomWidget(CAUSAS_LEG_FNCV_PATH, new UIDynamicListWidgetFactory());
		causaslegfncvWidget.setListId(CAUSAS_LEG_FNCV_ID);
		aWriter.addFormRow(causaslegfncvWidget);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h4>Encargados</h4>");

		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Encargados Ficha de Analisis básico "), true);	
		UIDynamicListWidget destinatarioefabWidget = aWriter.newCustomWidget(DESTINATARIO_EFAB_PATH, new UIDynamicListWidgetFactory());
		destinatarioefabWidget.setListId(DESTINATARIO_EFAB_ID);
		aWriter.addFormRow(destinatarioefabWidget);
		UIDynamicListWidget operacionefabWidget = aWriter.newCustomWidget(OPERACION_EFAB_PATH, new UIDynamicListWidgetFactory());
		operacionefabWidget.setListId(OPERACION_EFAB_ID);
		aWriter.addFormRow(operacionefabWidget);
		UIDynamicListWidget nombrecontratofabWidget = aWriter.newCustomWidget(NOMBRECONTRATO_EFAB_PATH, new UIDynamicListWidgetFactory());
		nombrecontratofabWidget.setListId(NOMBRECONTRATO_EFAB_ID);
		aWriter.addFormRow(nombrecontratofabWidget);
		
		
		
		aWriter.addFormRow(Path.parse("Campospregunta9/Encargados_FAB_C/FechaFormalizacion_EFAB_C"));
		aWriter.addFormRow(PERIODO_EFAB_PATH);
		aWriter.addFormRow(ESTATUSVIGENCIA_EFAB_PATH);
		aWriter.endExpandCollapseBlock();

		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Encargados Ficha de Analisis de Fase "), true);		
		UIDynamicListWidget destinatarioefnfWidget = aWriter.newCustomWidget(DESTINATARIO_EFNF_PATH, new UIDynamicListWidgetFactory());
		destinatarioefnfWidget.setListId(DESTINATARIO_EFNF_ID);
		aWriter.addFormRow(destinatarioefnfWidget);
		UIDynamicListWidget operacionefnfWidget = aWriter.newCustomWidget(OPERACION_EFNF_PATH, new UIDynamicListWidgetFactory());
		operacionefnfWidget.setListId(OPERACION_EFNF_ID);
		aWriter.addFormRow(operacionefnfWidget);
		UIDynamicListWidget nombrecontratofnfWidget = aWriter.newCustomWidget(NOMBRECONTRATO_EFNF_PATH, new UIDynamicListWidgetFactory());
		nombrecontratofnfWidget.setListId(NOMBRECONTRATO_EFNF_ID);
		aWriter.addFormRow(nombrecontratofnfWidget);
		aWriter.addFormRow(Path.parse("Campospregunta9/Encargados_FAF_C/FechaFormalizacion_EFAF_C"));
		aWriter.addFormRow(PERIODOVIGENCIA_EFNF_PATH);
		aWriter.addFormRow(ESTATUSVIGENCIA_EFNF_PATH);
		aWriter.endExpandCollapseBlock();

		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Encargados Ficha de Analisis Ciclo de Vida"), true);		
		UIDynamicListWidget destinatarioefacvWidget = aWriter.newCustomWidget(DESTINATARIO_EFACV_PATH, new UIDynamicListWidgetFactory());
		destinatarioefacvWidget.setListId(DESTINATARIO_EFACV_ID);
		aWriter.addFormRow(destinatarioefacvWidget);
		UIDynamicListWidget operacionefacvWidget = aWriter.newCustomWidget(OPERACION_EFACV_PATH, new UIDynamicListWidgetFactory());
		operacionefacvWidget.setListId(OPERACION_EFACV_ID);
		aWriter.addFormRow(operacionefacvWidget);
		UIDynamicListWidget nombrecontratofacvWidget = aWriter.newCustomWidget(NOMBRECONTRATO_EFACV_PATH, new UIDynamicListWidgetFactory());
		nombrecontratofacvWidget.setListId(NOMBRECONTRATO_EFACV_ID);
		aWriter.addFormRow(nombrecontratofacvWidget);
		aWriter.addFormRow(Path.parse("Campospregunta9/Encargados_FACV_C/FechaFormalizacion_EFACV_C"));
		aWriter.addFormRow(PERIODO_EFACV_PATH);
		aWriter.addFormRow(ESTATUSVIGENCIA_EFACV_PATH);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h4>Sub encargados</h4>");

		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Sub Encargados Ficha de Analisis básico "), true);		
		UIDynamicListWidget destinatariosfabWidget = aWriter.newCustomWidget(DESTINATARIO_SFAB_PATH, new UIDynamicListWidgetFactory());
		destinatariosfabWidget.setListId(DESTINATARIO_SFAB_ID);
		aWriter.addFormRow(destinatariosfabWidget);
		UIDynamicListWidget operacionessfabWidget = aWriter.newCustomWidget(OPERACIONES_SFAB_PATH, new UIDynamicListWidgetFactory());
		operacionessfabWidget.setListId(OPERACION_SFAB_ID);
		aWriter.addFormRow(operacionessfabWidget);
		UIDynamicListWidget nombrecontratosfabWidget = aWriter.newCustomWidget(NOMBRECONTRATO_SFAB_PATH, new UIDynamicListWidgetFactory());
		nombrecontratosfabWidget.setListId(NOMBRECONTRATO_SFAB_ID);
		aWriter.addFormRow(nombrecontratosfabWidget);
		aWriter.addFormRow(Path.parse("Campospregunta9/SubEncargados_FAB_C/FechaFormalizacion_SEFAB_C"));
		aWriter.addFormRow(PERIODO_SFAB_PATH);
		aWriter.addFormRow(ESTATUSVIGENCIA_SFAB_PATH);
		aWriter.endExpandCollapseBlock();

		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Sub Encargados Ficha de Analisis de Fase "), true);		
		UIDynamicListWidget destinatariosfnfWidget = aWriter.newCustomWidget(DESTINATARIO_SFNF_PATH, new UIDynamicListWidgetFactory());
		destinatariosfnfWidget.setListId(DESTINATARIO_SFNF_ID);
		aWriter.addFormRow(destinatariosfnfWidget);
		UIDynamicListWidget operacionessfnfWidget = aWriter.newCustomWidget(OPERACIONES_SFNF_PATH, new UIDynamicListWidgetFactory());
		operacionessfnfWidget.setListId(OPERACION_SFNF_ID);
		aWriter.addFormRow(operacionessfnfWidget);
		UIDynamicListWidget nombrecontratosfnfWidget = aWriter.newCustomWidget(NOMBRECONTRATO_SFNF_PATH, new UIDynamicListWidgetFactory());
		nombrecontratosfnfWidget.setListId(NOMBRECONTRATO_SFNF_ID);
		aWriter.addFormRow(nombrecontratosfnfWidget);
		
		aWriter.addFormRow(Path.parse("Campospregunta9/SubEncargados_FAF_C/FechaFormalizacion_SEFAF_C"));
		aWriter.addFormRow(PERIODO_SFNF_PATH);
		aWriter.addFormRow(ESTATUSVIGENCIA_SFNF_PATH);
		aWriter.endExpandCollapseBlock();

		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Sub Encargados Ficha de Analisis Ciclo de Vida"), true);		
		//aWriter.addFormRow(Path.parse("Campospregunta9/SubEncargados_FACV_C/Destinatario_SEFACV_C"));
		UIDynamicListWidget destinatariosfncvWidget = aWriter.newCustomWidget(DESTINATARIO_SFNCV_PATH, new UIDynamicListWidgetFactory());
		destinatariosfncvWidget.setListId(DESTINATARIO_SFNCV_ID);
		aWriter.addFormRow(destinatariosfncvWidget);

		//aWriter.addFormRow(Path.parse("Campospregunta9/SubEncargados_FACV_C/OperacionEjectua_SEFACV_C"));
		UIDynamicListWidget operacionessfncvWidget = aWriter.newCustomWidget(OPERACIONES_SFNCV_PATH, new UIDynamicListWidgetFactory());
		operacionessfncvWidget.setListId(OPERACIONES_SFNCV_ID);
		aWriter.addFormRow(operacionessfncvWidget);

		//aWriter.addFormRow(Path.parse("Campospregunta9/SubEncargados_FACV_C/NombreContrato_SEFACV_C"));
		UIDynamicListWidget nombrecontratosfncvWidget = aWriter.newCustomWidget(NOMBRECONTRATO_SFNCV_PATH, new UIDynamicListWidgetFactory());
		nombrecontratosfncvWidget.setListId(NOMBRECONTRATO_SFNCV_ID);
		aWriter.addFormRow(nombrecontratosfncvWidget);

		aWriter.addFormRow(Path.parse("Campospregunta9/SubEncargados_FACV_C/FechaFormalizacion_SEFACV_C"));
		aWriter.addFormRow(PERIODO_SFNCV_PATH);
		aWriter.addFormRow(ESTATUSVIGENCIA_SFNCV_PATH);
		aWriter.endExpandCollapseBlock();
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>10.- ¿Existen garantías jurídicas adecuadas para garantizar la consulta al Responsable por parte de los encargados antes de abordar la contratación de sub encargados o terceros intervinientes en el tratamiento?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		Path revPath10 = Path.parse("Pregunta_10");
		UIDropDownList revDropDown10 = aWriter.newDropDownList(revPath10);
		revDropDown10.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision10"));
		aWriter.addFormRow(revDropDown10);
		String value10 = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath10).getValue();		
		startBlock(aWriter, "block_revision_si10", "Si".equalsIgnoreCase(value10));	
		aWriter.addFormRow(Path.parse("texto10"));
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>11.- ¿Se han establecido medidas que permitan al responsable garantizar y demostrar el cumplimiento de las previsiones del MGPPDP? </h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("pregunta11"), "block_pregunta11");
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>12.- ¿El vínculo jurídico establecido entre Responsables, Encargados y Sub encargados especifica y define las medidas y garantías de responsabilidad proactiva que ha de implementar el encargado y los mecanismos de monitorización?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		Path revPath12 = Path.parse("Pregunta_12");
		UIDropDownList revDropDown12 = aWriter.newDropDownList(revPath12);
		revDropDown12.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision12"));
		aWriter.addFormRow(revDropDown12);
		String value12 = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath12).getValue();		
		startBlock(aWriter, "block_revision_si12", "Si".equalsIgnoreCase(value12));	

		Path revPath12_2 = Path.parse("Catalogo12");
		UIDropDownList revDropDown12_2 = aWriter.newDropDownList(revPath12_2);
		revDropDown12_2.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision12_2"));
		aWriter.addFormRow(revDropDown12_2);
		String value12_2 = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath12_2).getValue();		
		startBlock(aWriter, "block_revision_si12_2", "Garantias previstas en otros mecanismos".equalsIgnoreCase(value12_2));	
		aWriter.addFormRow(Path.parse("C_Preg12_OtrosMecanismos"));
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock(); 

		aWriter.add_cr("<h3>13.- ¿Se cumple con las obligaciones de información a los interesados (titulares), si corresponde?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		aWriter.addFormRow(Path.parse("Pregunta_13"));
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>14.- ¿Están implementados los procedimientos para garantizar los derechos de los interesados: acceso, rectificación, supresión, limitación del tratamiento, portabilidad, oposición y los que corresponden a las posibles decisiones individuales automatizadas?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("pregunta14"), "block_pregunta14");
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>15.- ¿Se han identificado los procesos, productos y servicios asociados al tratamiento y los casos en los que se puede ofrecer a los interesados el derecho a la portabilidad?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("pregunta15"), "block_pregunta15");
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>16.- ¿Los datos utilizados son adecuados, pertinentes y limitados a lo necesario para abordar los fines identificados del tratamiento?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("pregunta16"), "block_pregunta16");
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>17.- ¿Se establecen plazos de limitación de las operaciones de tratamiento con relación a los datos?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("pregunta17"), "block_pregunta17");
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>18.- ¿Se ha establecido la caducidad del tratamiento?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("Pregunta18"), "block_Pregunta18");
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>19.- ¿En caso de transferencias internacionales, está documentado el cumplimiento de las garantías necesarias?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		startBooleanBlock(aPaneContext, aWriter, Path.parse("pregunta19"), "block_pregunta19");

		aWriter.add_cr("<h4>Transferencias</h4>");

		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Transferencias - Ficha Análisis Básico"), true);		
		UIDynamicListWidget destinatariotfabWidget = aWriter.newCustomWidget(DESTINATARIO_TFAB_PATH, new UIDynamicListWidgetFactory());
		destinatariotfabWidget.setListId(DESTINATARIO_TFAB_ID);
		aWriter.addFormRow(destinatariotfabWidget);
		UIDynamicListWidget paistfabWidget = aWriter.newCustomWidget(PAIS_TFAB_PATH, new UIDynamicListWidgetFactory());
		paistfabWidget.setListId(PAIS_TFAB_ID);
		aWriter.addFormRow(paistfabWidget);
		UIDynamicListWidget finalidadestfabWidget = aWriter.newCustomWidget(FINALIDADES_TFAB_PATH, new UIDynamicListWidgetFactory());
		finalidadestfabWidget.setListId(FINALIDADES_TFAB_ID);
		aWriter.addFormRow(finalidadestfabWidget);
		UIDynamicListWidget causaslegtfabWidget = aWriter.newCustomWidget(CAUSALEG_TFAB_PATH, new UIDynamicListWidgetFactory());
		causaslegtfabWidget.setListId(CAUSALEG_TFAB_ID);
		aWriter.addFormRow(causaslegtfabWidget);
		aWriter.endExpandCollapseBlock();

		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Transferencias - Ficha Análisis de Fase"), true);	
		
		UIDynamicListWidget destinatariostfnfWidget = aWriter.newCustomWidget(DESTINATARIO_TFNF_PATH, new UIDynamicListWidgetFactory());
		destinatariostfnfWidget.setListId(DESTINATARIO_TFNF_ID);
		aWriter.addFormRow(destinatariostfnfWidget);
		
		UIDynamicListWidget paistfnfWidget = aWriter.newCustomWidget(PAIS_TFNF_PATH, new UIDynamicListWidgetFactory());
		paistfnfWidget.setListId(PAIS_TFNF_ID);
		aWriter.addFormRow(paistfnfWidget);

		UIDynamicListWidget finalidadestfnfWidget = aWriter.newCustomWidget(FINALIDADES_TFNF_PATH, new UIDynamicListWidgetFactory());
		finalidadestfnfWidget.setListId(FINALIDADES_TFNF_ID);
		aWriter.addFormRow(finalidadestfnfWidget);

		UIDynamicListWidget causaslegtfnfWidget = aWriter.newCustomWidget(CAUSALEG_TFNF_PATH, new UIDynamicListWidgetFactory());
		causaslegtfnfWidget.setListId(CAUSALEG_TFNF_ID);
		aWriter.addFormRow(causaslegtfnfWidget);

		aWriter.endExpandCollapseBlock();

		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Transferencias - Ficha Análisis Ciclo de Vida"), true);		
		
		UIDynamicListWidget destinatariostfncvWidget = aWriter.newCustomWidget(DESTINATARIO_TFNCV_PATH, new UIDynamicListWidgetFactory());
		destinatariostfncvWidget.setListId(DESTINATARIO_TFNCV_ID);
		aWriter.addFormRow(destinatariostfncvWidget);
		
		UIDynamicListWidget paistfncvWidget = aWriter.newCustomWidget(PAIS_TFNCV_PATH, new UIDynamicListWidgetFactory());
		paistfncvWidget.setListId(PAIS_TFNCV_ID);
		aWriter.addFormRow(paistfncvWidget);

		UIDynamicListWidget finalidadestfncvWidget = aWriter.newCustomWidget(FINALIDADES_TFNCV_PATH, new UIDynamicListWidgetFactory());
		finalidadestfncvWidget.setListId(FINALIDADES_TFNCV_ID);
		aWriter.addFormRow(finalidadestfncvWidget);

		UIDynamicListWidget causaslegtfncvWidget = aWriter.newCustomWidget(CAUSALEG_TFNCV_PATH, new UIDynamicListWidgetFactory());
		causaslegtfncvWidget.setListId(CAUSALEG_TFNCV_ID);
		aWriter.addFormRow(causaslegtfncvWidget);

		aWriter.endExpandCollapseBlock();
		endBlock(aWriter);
		aWriter.endExpandCollapseBlock();

		aWriter.add_cr("<h3>20.- ¿Se gestiona el cumplimiento de los códigos de conducta aprobados a los que el Responsable se ha adherido?</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);		
		aWriter.addFormRow(Path.parse("Pregunta_20"));
		aWriter.endExpandCollapseBlock();

		
		//---------------------------------------
		aWriter.add_cr("<h3>Revisión metodológica</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo("Determinación sobre la necesidad de elaborar una evaluación de impacto en la protección de datos personales al tratamiento de datos personales que se describe"), true);	
		//aWriter.add_cr("<h2>DeterminaciÃ³n sobre la necesidad de elaborar una evaluaciÃ³n de impacto en la protecciÃ³n de datos personales al tratamiento de datos personales que se describe</h2>");
		Path revPath = Path.parse("Revision_metodologicaC");
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
		
		
		
		
		
		
		
		
		
		
		
		
			
		
		

		

        aWriter.addJS_cr("function displayBlock(buttonValue, blockId){");
        aWriter.addJS_cr("if (buttonValue == 'true'){");
        aWriter.addJS_cr("document.getElementById(blockId).style.display = 'block';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else {");
        aWriter.addJS_cr("document.getElementById(blockId).style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");
		
        
        aWriter.addJS_cr("function displayBlockRevision6(buttonValue){");
        aWriter.addJS_cr("const blockSi = document.getElementById('block_revision_si6');");
        aWriter.addJS_cr("if (buttonValue == 'Si'){");
        aWriter.addJS_cr("blockSi.style.display = 'block';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else if(buttonValue == 'No'){");
        aWriter.addJS_cr("blockSi.style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else {");
        aWriter.addJS_cr("blockSi.style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");
		
		aWriter.addJS_cr("function displayBlockRevision7(buttonValue){");
        aWriter.addJS_cr("const blockSi = document.getElementById('block_revision_si7');");
        aWriter.addJS_cr("if (buttonValue == 'Si'){");
        aWriter.addJS_cr("blockSi.style.display = 'block';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else if(buttonValue == 'No'){");
        aWriter.addJS_cr("blockSi.style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else {");
        aWriter.addJS_cr("blockSi.style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");
		
		aWriter.addJS_cr("function displayBlockRevision10(buttonValue){");
        aWriter.addJS_cr("const blockSi = document.getElementById('block_revision_si10');");
        aWriter.addJS_cr("if (buttonValue == 'Si'){");
        aWriter.addJS_cr("blockSi.style.display = 'block';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else if(buttonValue == 'No'){");
        aWriter.addJS_cr("blockSi.style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else {");
        aWriter.addJS_cr("blockSi.style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");
		
		aWriter.addJS_cr("function displayBlockRevision12(buttonValue){");
        aWriter.addJS_cr("const blockSi = document.getElementById('block_revision_si12');");
        aWriter.addJS_cr("if (buttonValue == 'Si'){");
        aWriter.addJS_cr("blockSi.style.display = 'block';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else if(buttonValue == 'No'){");
        aWriter.addJS_cr("blockSi.style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else {");
        aWriter.addJS_cr("blockSi.style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");

		aWriter.addJS_cr("function displayBlockRevision12_2(buttonValue){");
        aWriter.addJS_cr("const blockSi = document.getElementById('block_revision_si12_2');");
        aWriter.addJS_cr("if (buttonValue == 'Garantias previstas en otros mecanismos'){");
        aWriter.addJS_cr("blockSi.style.display = 'block';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else if(buttonValue == 'No'){");
        aWriter.addJS_cr("blockSi.style.display = 'none';");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("else {");
        aWriter.addJS_cr("blockSi.style.display = 'none';");
        aWriter.addJS_cr("}");
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
        
        
        // Funcion que ocupan los campos String que tiene valores definidos y dependindo la selecion muestra los campos (RevisiÃ³n metodolÃ³gica)
		
		aWriter.addJS_cr("function displayBlockRevision(buttonValue) {");
		aWriter.addJS_cr("  const blockAceptable = document.getElementById('block_revision_aceptable');");
		aWriter.addJS_cr("  const blockInaceptable = document.getElementById('block_revision_inaceptable');");
		aWriter.addJS_cr("  if (buttonValue === 'Aceptable') {");
		aWriter.addJS_cr("    resetFieldValue('" + MInaceptablePrefix + "');");
		aWriter.addJS_cr("    resetFieldValue('" + CInaceptablePrefix + "');");
		aWriter.addJS_cr("    resetFieldValue('" + NRvisorIPrefix + "');");
		aWriter.addJS_cr("    resetFieldValue('" + RCRevisorIPrefix + "');");
		aWriter.addJS_cr("    blockAceptable.style.display = 'block';");
		aWriter.addJS_cr("    blockInaceptable.style.display = 'none';");
		aWriter.addJS_cr("  } else if (buttonValue === 'Inaceptable') {");
		aWriter.addJS_cr("    resetFieldValue('" + MAceptablePrefix + "');");
		aWriter.addJS_cr("    resetFieldValue('" + CAceptablePrefix + "');");
		aWriter.addJS_cr("    resetFieldValue('" + NRvisorAPrefix + "');");
		aWriter.addJS_cr("    resetFieldValue('" + RCRevisorAPrefix + "');");
		aWriter.addJS_cr("    blockAceptable.style.display = 'none';");
		aWriter.addJS_cr("    blockInaceptable.style.display = 'block';");
		aWriter.addJS_cr("  } else {");
		aWriter.addJS_cr("    resetFieldValue('" + MAceptablePrefix + "');");
		aWriter.addJS_cr("    resetFieldValue('" + CAceptablePrefix + "');");
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
	   private void ajaxCallback(UserServiceAjaxContext anAjaxContext, UserServiceAjaxResponse anAjaxResponse){
    	
    	Adaptation dataset = anAjaxContext.getValueContext(OBJECT_KEY).getAdaptationInstance();
    	AdaptationTable table = dataset.getTable(Path.parse("/root/Formularios/FABAN"));
		
    	
    	String identificador = null;
    	List<String> responsables = null;
    	List<String> corresponsables = null;
    	String procesoOperativo = null;
		List<String> juicios = null;
		List<String> destinatariosfaban = null;
		List<String> finalidadesfaban = null;
		List<String> causaslegs = null;
		List<String> destinatariosefaban = null;
		List<String> operacionesefab = null;
		List<String> nombrescontratosefab = null;
		String periodovigenciaefab = null;
		boolean estatusvigenciaefab = false;
		List<String> destinatariossfab = null;
		List<String> operacionessfab = null;
		List<String> nombrescontratossfab = null;
		String periodovigenciasfab = null;
		boolean estatusvigenciasfab = false;
		List<String> destinatariostfab = null;
		List<String> paisestfab = null;
		List<String> finalidadestfab = null;
		List<String> causaslegstfab = null;
		

    	String value = anAjaxContext.getParameter("fieldValue");
		
    	System.out.println(value);
    	
    	if(value != null && !value.isBlank()) {

	    	Adaptation record = table.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(value));
	    	
	    	identificador = record.getString(Path.parse("./Identificador"));
	    	responsables = record.getList(Path.parse("./Nombre_contacto"));
	    	corresponsables = record.getList(Path.parse("./Nombre_Co"));
			procesoOperativo = record.getString(Path.parse("./Proceso_operativo"));
			juicios = record.getList(Path.parse("./Conclusion_juicio_de_idoneidad_proporcionalidad_necesidad"));
			destinatariosfaban = record.getList(Path.parse("./Cesion/DestinatarioCesion"));
			finalidadesfaban = record.getList(Path.parse("./Cesion/FinalidadesCesion"));
			causaslegs = record.getList(Path.parse("./Cesion/Causaslegitimacioncesion"));
			destinatariosefaban = record.getList(Path.parse("./Comunicacionencargado/Destinatariocomunicacionconencargado"));
			operacionesefab = record.getList(Path.parse("./Comunicacionencargado/OperacionqueejecutaComuencargados"));
			nombrescontratosefab = record.getList(Path.parse("./Comunicacionencargado/NombreContratoCConEncarg"));
			periodovigenciaefab = record.getString(Path.parse("./Comunicacionencargado/PeriodovigenciaCconEncarg"));
			estatusvigenciaefab = record.get_boolean(Path.parse("./Comunicacionencargado/VigenteCconEncarg"));
			destinatariossfab = record.getList(Path.parse("./Comunicacionconsubencargado/DestinatarioCconSuben"));
			operacionessfab = record.getList(Path.parse("./Comunicacionconsubencargado/Operacionesqueejecutansubencargado"));
			nombrescontratossfab = record.getList(Path.parse("./Comunicacionconsubencargado/nombrecontratosubencargado"));
			periodovigenciasfab = record.getString(Path.parse("./Comunicacionconsubencargado/Periododevigenciasubencargado"));
			estatusvigenciasfab = record.get_boolean(Path.parse("./Comunicacionconsubencargado/Vigentesubencargado"));
			destinatariostfab = record.getList(Path.parse("./Trasferencia/Destinatariotrasferencia"));
			paisestfab = record.getList(Path.parse("./Trasferencia/Paisdestinotrasferencia"));
			finalidadestfab = record.getList(Path.parse("./Trasferencia/Finalidadestrasferencia"));
			causaslegstfab = record.getList(Path.parse("./Trasferencia/Causaslegitimaciontrasferencia"));
    	}
		
		
    	
    	UserServiceWriter aWriter = anAjaxResponse.getWriter();
    	
    	identificador = identificador == null ? "null" : "'" + identificador + "'";
    	
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

		//Juicio
	    
    	aWriter.addJS_cr("juicioArray = [];");
    	aWriter.addJS_cr("resetList('" + juicioPrefix + "', '" + JUICIO_ID + "');");
    	
    	if(juicios != null) {
	    	for(String juicio : juicios) {
	    		
				aWriter.addJS_cr("juicio = {");
				aWriter.addJS_cr("key: '" + juicio + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("juicioArray.push(juicio);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + juicioPrefix + "', '" + JUICIO_ID + "', juicioArray);");	
	    	
    	}
		//DESTINATARIO FABAN
	    
    	aWriter.addJS_cr("destinatariofabanArray = [];");
    	aWriter.addJS_cr("resetList('" + destinatariofabanPrefix + "', '" + DESTINATARIO_FABAN_ID + "');");
    	
    	if(destinatariosfaban != null) {
	    	for(String destinatariofaban : destinatariosfaban) {
	    		
				aWriter.addJS_cr("destinatariofaban = {");
				aWriter.addJS_cr("key: '" + destinatariofaban + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("destinatariofabanArray.push(destinatariofaban);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + destinatariofabanPrefix + "', '" + DESTINATARIO_FABAN_ID + "', destinatariofabanArray);");	
	    	
    	}
		//FINALIDADES FABAN
	    
    	aWriter.addJS_cr("finalidadesArray = [];");
    	aWriter.addJS_cr("resetList('" + finalidadesfabanPrefix + "', '" + FINALIDADES_FABAN_ID + "');");
    	
    	if(finalidadesfaban != null) {
	    	for(String finalidadfaban : finalidadesfaban) {
	    		
				aWriter.addJS_cr("finalidadfaban = {");
				aWriter.addJS_cr("key: '" + finalidadfaban + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("finalidadesArray.push(finalidadfaban);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + finalidadesfabanPrefix + "', '" + FINALIDADES_FABAN_ID + "', finalidadesArray);");	
	    	
    	}
		//CAUSAS LEGITIMACION FABAN
	    
    	aWriter.addJS_cr("causaslegArray = [];");
    	aWriter.addJS_cr("resetList('" + causaslegfabanPrefix + "', '" + CAUSAS_LEG_FABAN_ID + "');");
    	
    	if(causaslegs != null) {
	    	for(String causasleg : causaslegs) {
	    		
				aWriter.addJS_cr("causasleg = {");
				aWriter.addJS_cr("key: '" + causasleg + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("causaslegArray.push(causasleg);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + causaslegfabanPrefix + "', '" + CAUSAS_LEG_FABAN_ID + "', causaslegArray);");	
	    	
    	}
		//Destinatario EFAB
	    
    	aWriter.addJS_cr("destinatarioefabArray = [];");
    	aWriter.addJS_cr("resetList('" + destinatarioefabPrefix + "', '" + DESTINATARIO_EFAB_ID + "');");
    	
    	if(destinatariosefaban != null) {
	    	for(String destinatarioefab : destinatariosefaban) {
	    		
				aWriter.addJS_cr("destinatarioefab = {");
				aWriter.addJS_cr("key: '" + destinatarioefab + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("destinatarioefabArray.push(destinatarioefab);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + destinatarioefabPrefix + "', '" + DESTINATARIO_EFAB_ID + "', destinatarioefabArray);");	
	    	
    	}
		//Operacion EFAB
	    
    	aWriter.addJS_cr("operacionefabArray = [];");
    	aWriter.addJS_cr("resetList('" + operacionesefabPrefix + "', '" + OPERACION_EFAB_ID + "');");
    	
    	if(operacionesefab != null) {
	    	for(String operacionefab : operacionesefab) {
	    		
				aWriter.addJS_cr("operacionefab = {");
				aWriter.addJS_cr("key: '" + operacionefab + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("operacionefabArray.push(operacionefab);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + operacionesefabPrefix + "', '" + OPERACION_EFAB_ID + "', operacionefabArray);");	
	    	
    	}
		//Nombre contrato EFAB
	    
    	aWriter.addJS_cr("nombconefabArray = [];");
    	aWriter.addJS_cr("resetList('" + nombreconrtatoefabPrefix + "', '" + NOMBRECONTRATO_EFAB_ID + "');");
    	
    	if(nombrescontratosefab != null) {
	    	for(String nombrecontratoefaban : nombrescontratosefab) {
	    		
				aWriter.addJS_cr("nombrecontratoefaban = {");
				aWriter.addJS_cr("key: '" + nombrecontratoefaban + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("nombconefabArray.push(nombrecontratoefaban);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + nombreconrtatoefabPrefix + "', '" + NOMBRECONTRATO_EFAB_ID + "', nombconefabArray);");	
	    	
    	}

		//DESTINATARIO SFAB
	    
    	aWriter.addJS_cr("destinatariosfabArray = [];");
    	aWriter.addJS_cr("resetList('" + destinatariosfabPrefix + "', '" + DESTINATARIO_SFAB_ID + "');");
    	
    	if(destinatariossfab != null) {
	    	for(String destinatariossfaban : destinatariossfab) {
	    		
				aWriter.addJS_cr("destinatariossfaban = {");
				aWriter.addJS_cr("key: '" + destinatariossfaban + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("destinatariosfabArray.push(destinatariossfaban);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + destinatariosfabPrefix + "', '" + DESTINATARIO_SFAB_ID + "', destinatariosfabArray);");	
	    	
    	}

		//OPERACIONES SFAB
	    
    	aWriter.addJS_cr("operacionessfabArray = [];");
    	aWriter.addJS_cr("resetList('" + operacionesfabPrefix + "', '" + OPERACION_SFAB_ID + "');");
    	
    	if(operacionessfab != null) {
	    	for(String operacionsfaban : operacionessfab) {
	    		
				aWriter.addJS_cr("operacionsfaban = {");
				aWriter.addJS_cr("key: '" + operacionsfaban + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("operacionessfabArray.push(operacionsfaban);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + operacionesfabPrefix + "', '" + OPERACION_SFAB_ID + "', operacionessfabArray);");	
	    	
    	}

		//Nombre contrato SFAB
	    
    	aWriter.addJS_cr("nombconsfabArray = [];");
    	aWriter.addJS_cr("resetList('" + nombrecontratosfabPrefix + "', '" + NOMBRECONTRATO_SFAB_ID + "');");
    	
    	if(nombrescontratossfab != null) {
	    	for(String nombrecontratosfaban : nombrescontratossfab) {
	    		
				aWriter.addJS_cr("nombrecontratosfaban = {");
				aWriter.addJS_cr("key: '" + nombrecontratosfaban + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("nombconsfabArray.push(nombrecontratosfaban);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + nombrecontratosfabPrefix + "', '" + NOMBRECONTRATO_SFAB_ID + "', nombconsfabArray);");	
	    	
    	}

		//destinatario TFAB
	    
    	aWriter.addJS_cr("destinatariotfabArray = [];");
    	aWriter.addJS_cr("resetList('" + destinatariotfabPrefix + "', '" + DESTINATARIO_TFAB_ID + "');");
    	
    	if(destinatariostfab != null) {
	    	for(String destinatariotfab : destinatariostfab) {
	    		
				aWriter.addJS_cr("destinatariotfab = {");
				aWriter.addJS_cr("key: '" + destinatariotfab + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("destinatariotfabArray.push(destinatariotfab);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + destinatariotfabPrefix + "', '" + DESTINATARIO_TFAB_ID + "', destinatariotfabArray);");	
	    	
    	}

		//pais TFAB
	    
    	aWriter.addJS_cr("paistfabArray = [];");
    	aWriter.addJS_cr("resetList('" + paistfabPrefix + "', '" + PAIS_TFAB_ID + "');");
    	
    	if(paisestfab != null) {
	    	for(String paistafb : paisestfab) {
	    		
				aWriter.addJS_cr("paistafb = {");
				aWriter.addJS_cr("key: '" + paistafb + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("paistfabArray.push(paistafb);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + paistfabPrefix + "', '" + PAIS_TFAB_ID + "', paistfabArray);");	
	    	
    	}

		//finalidades TFAB
	    
    	aWriter.addJS_cr("finalidadestfabArray = [];");
    	aWriter.addJS_cr("resetList('" + finalidadestfabPrefix + "', '" + FINALIDADES_TFAB_ID + "');");
    	
    	if(finalidadestfab != null) {
	    	for(String finalidadtfab : finalidadestfab) {
	    		
				aWriter.addJS_cr("finalidadtfab = {");
				aWriter.addJS_cr("key: '" + finalidadtfab + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("finalidadestfabArray.push(finalidadtfab);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + finalidadestfabPrefix + "', '" + FINALIDADES_TFAB_ID + "', finalidadestfabArray);");	
	    	
    	}

		//causas legitimas TFAB
	    
    	aWriter.addJS_cr("causaslegtfabArray = [];");
    	aWriter.addJS_cr("resetList('" + causalegtfabPrefix + "', '" + CAUSALEG_TFAB_ID + "');");
    	
    	if(causaslegstfab != null) {
	    	for(String causalegtfab : causaslegstfab) {
	    		
				aWriter.addJS_cr("causalegtfab = {");
				aWriter.addJS_cr("key: '" + causalegtfab + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("causaslegtfabArray.push(causalegtfab);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + causalegtfabPrefix + "', '" + CAUSALEG_TFAB_ID + "', causaslegtfabArray);");	
	    	
    	}
		/////////////////////////////////////////

    	if(procesoOperativo == null) {
    		
    		aWriter.addJS_cr("procesoOperativo = null;");
    		
    	} else {
    	
			aWriter.addJS_cr("procesoOperativo = {");
			aWriter.addJS_cr("key: '" + procesoOperativo + "',");
			aWriter.addJS_cr("label: '',");
			aWriter.addJS_cr("previewURL: undefined");
			aWriter.addJS_cr("};");
		
    	}
//periodovigenciaefab
		if(periodovigenciaefab == null) {
					
			aWriter.addJS_cr("periodovigenciaefab = null;");
			
		} else {

			aWriter.addJS_cr("periodovigenciaefab = {");
			aWriter.addJS_cr("key: '" + periodovigenciaefab + "',");
			aWriter.addJS_cr("label: '',");
			aWriter.addJS_cr("previewURL: undefined");
			aWriter.addJS_cr("};");

		}

		//periodovigenciasfab
		if(periodovigenciasfab == null) {
					
			aWriter.addJS_cr("periodovigenciasfab = null;");
			
		} else {

			aWriter.addJS_cr("periodovigenciasfab = {");
			aWriter.addJS_cr("key: '" + periodovigenciasfab + "',");
			aWriter.addJS_cr("label: '',");
			aWriter.addJS_cr("previewURL: undefined");
			aWriter.addJS_cr("};");

		}

		aWriter.addJS_cr("ebx_form_setValue('" + idFABPrefix + "', " +  identificador + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + procesosPrefix + "', procesoOperativo)");
		aWriter.addJS_cr("ebx_form_setValue('" + estatusvigenciaefabPrefix + "', " +  estatusvigenciaefab + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + periodovigenciaPrefix + "', periodovigenciaefab)");
    	aWriter.addJS_cr("ebx_form_setValue('" + periodovigenciasfabPrefix + "', periodovigenciasfab)");
		aWriter.addJS_cr("ebx_form_setValue('" + estatusvigenciasfabPrefix + "', " +  estatusvigenciasfab + ")");
    	
    }
	   
	   
	private void ajaxCallback2(UserServiceAjaxContext anAjaxContext, UserServiceAjaxResponse anAjaxResponse){
		Adaptation dataset = anAjaxContext.getValueContext(OBJECT_KEY).getAdaptationInstance();
		AdaptationTable table2 = dataset.getTable(Path.parse("/root/Formularios/FNF"));
		
		List<String> destinatariosfnf = null;
		List<String> finalidadesfnf = null;
		List<String> causaslegsfnf = null;
		List<String> destinatariosefnf = null;
		List<String> operacionesefnf = null;
		List<String> nombrecontratosefnf = null;
		String preiodosvigenciaefnf = null;
		boolean estatusvigenciaefnf = false;
		List<String> destinatariossfnf = null;
		List<String> operacionessfnf = null;
		List<String> nombrecontratossfnf = null;
		String preiodosvigenciasfnf = null;
		boolean estatusvigenciasfnf = false;
		List<String> destinatariostfnf = null;
		List<String> paisestfnf = null;
		List<String> finalidadestfnf = null;
		List<String> causaslegstfnf = null;

		String value2 = anAjaxContext.getParameter("fieldValue");


		if(value2 != null && !value2.isBlank()) {


			Adaptation record2 = table2.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(value2));
	    
			destinatariosfnf = record2.getList(Path.parse("./Exterior_banco/Para_transferir_terceros/Destinatario_trasferencia_terceros"));
			finalidadesfnf = record2.getList(Path.parse("./Exterior_banco/Para_transferir_terceros/Finalidades_de_la_transferencia_trasferencia_terceros"));
			causaslegsfnf = record2.getList(Path.parse("./Exterior_banco/Para_transferir_terceros/Causa_de_legitimacion_trasferencia_terceros"));
			destinatariosefnf = record2.getList(Path.parse("./Exterior_banco/Para_tratamiento_encargados/Destinatario_encargado"));
			operacionesefnf = record2.getList(Path.parse("./Exterior_banco/Para_tratamiento_encargados/Operaciones_que_ejecuta_encargado"));
			nombrecontratosefnf = record2.getList(Path.parse("./Exterior_banco/Para_tratamiento_encargados/Nombre_contrato_encargado"));
			preiodosvigenciaefnf = record2.getString(Path.parse("./Exterior_banco/Para_tratamiento_encargados/Periodo_vigencia_encargado"));
			estatusvigenciaefnf = record2.get_boolean(Path.parse("./Exterior_banco/Para_tratamiento_encargados/Vigente_encargado"));
			destinatariossfnf = record2.getList(Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Destinatario_subencargado"));
			operacionessfnf = record2.getList(Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Operaciones_que_ejecuta_subencargado"));
			nombrecontratossfnf = record2.getList(Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Nombre_contrato_subencargado"));
			preiodosvigenciasfnf = record2.getString(Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Periodo_vigencia_subencargado"));
			estatusvigenciasfnf = record2.get_boolean(Path.parse("./Exterior_banco/Para_tratamiento_subencargados/Vigente_subencargado"));
			destinatariostfnf = record2.getList(Path.parse("./Exterior_banco/Para_transferir_terceros/Destinatario_trasferencia_terceros"));
			paisestfnf = record2.getList(Path.parse("./Exterior_banco/Para_transferir_terceros/Pais_trasferencia_terceros"));
			finalidadestfnf = record2.getList(Path.parse("./Exterior_banco/Para_transferir_terceros/Finalidades_de_la_transferencia_trasferencia_terceros"));
			causaslegstfnf = record2.getList(Path.parse("./Exterior_banco/Para_transferir_terceros/Causa_de_legitimacion_trasferencia_terceros"));
    	}
		UserServiceWriter aWriter = anAjaxResponse.getWriter();
		//DESTINATARIO FNF
	    
    	aWriter.addJS_cr("destinatariofnfArray = [];");
    	aWriter.addJS_cr("resetList('" + destinatariofnfPrefix + "', '" + DESTINATARIO_FNF_ID + "');");
    	
    	if(destinatariosfnf != null) {
	    	for(String destinatariofnf : destinatariosfnf) {
	    		
				aWriter.addJS_cr("destinatariofnf = {");
				aWriter.addJS_cr("key: '" + destinatariofnf + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("destinatariofnfArray.push(destinatariofnf);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + destinatariofnfPrefix + "', '" + DESTINATARIO_FNF_ID + "', destinatariofnfArray);");	
	    	
    	}
		//FINALIDADES FNF
	    
    	aWriter.addJS_cr("finalidadesfnfArray = [];");
    	aWriter.addJS_cr("resetList('" + finalidadesfnfPrefix + "', '" + FINALIDADES_FNF_ID + "');");
    	
    	if(finalidadesfnf != null) {
	    	for(String finalidadfnf : finalidadesfnf) {
	    		
				aWriter.addJS_cr("finalidadfnf = {");
				aWriter.addJS_cr("key: '" + finalidadfnf + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("finalidadesfnfArray.push(finalidadfnf);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + finalidadesfnfPrefix + "', '" + FINALIDADES_FNF_ID + "', finalidadesfnfArray);");	
	    	
    	}
		//CAUSAS LEGITIMACION FNF
	    
    	aWriter.addJS_cr("causaslegfnfArray = [];");
    	aWriter.addJS_cr("resetList('" + causaslegfnfPrefix + "', '" + CAUSAS_LEG_FNF_ID + "');");
    	
    	if(causaslegsfnf != null) {
	    	for(String causaslegfnf : causaslegsfnf) {
	    		
				aWriter.addJS_cr("causaslegfnf = {");
				aWriter.addJS_cr("key: '" + causaslegfnf + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("causaslegfnfArray.push(causaslegfnf);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + causaslegfnfPrefix + "', '" + CAUSAS_LEG_FNF_ID + "', causaslegfnfArray);");	
	    	
    	}
		//DESTINATARIO EFNF
	    
    	aWriter.addJS_cr("destinatarioefnfArray = [];");
    	aWriter.addJS_cr("resetList('" + destinatarioefnfPerfix + "', '" + DESTINATARIO_EFNF_ID + "');");
    	
    	if(destinatariosefnf != null) {
	    	for(String destinatarioefnf : destinatariosefnf) {
	    		
				aWriter.addJS_cr("destinatarioefnf = {");
				aWriter.addJS_cr("key: '" + destinatarioefnf + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("destinatarioefnfArray.push(destinatarioefnf);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + destinatarioefnfPerfix + "', '" + DESTINATARIO_EFNF_ID + "', destinatarioefnfArray);");	
	    	
    	}
		//OPERACIONES EFNF
	    
    	aWriter.addJS_cr("operacionefnfArray = [];");
    	aWriter.addJS_cr("resetList('" + operacionefnfPerfix + "', '" + OPERACION_EFNF_ID + "');");
    	
    	if(operacionesefnf != null) {
	    	for(String operacionefnf : operacionesefnf) {
	    		
				aWriter.addJS_cr("operacionefnf = {");
				aWriter.addJS_cr("key: '" + operacionefnf + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("operacionefnfArray.push(operacionefnf);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + operacionefnfPerfix + "', '" + OPERACION_EFNF_ID + "', operacionefnfArray);");	
	    	
    	}
		// NOMBRE CONTRATO EFNF
	    
    	aWriter.addJS_cr("nombreconefnfArray = [];");
    	aWriter.addJS_cr("resetList('" + nombrecontratoefnfPerfix + "', '" + NOMBRECONTRATO_EFNF_ID + "');");
    	
    	if(nombrecontratosefnf != null) {
	    	for(String nombrecontratoefnf : nombrecontratosefnf) {
	    		
				aWriter.addJS_cr("nombrecontratoefnf = {");
				aWriter.addJS_cr("key: '" + nombrecontratoefnf + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("nombreconefnfArray.push(nombrecontratoefnf);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + nombrecontratoefnfPerfix + "', '" + NOMBRECONTRATO_EFNF_ID + "', nombreconefnfArray);");	
	    	
    	}

		// DESTINATARIO SFNF
	    
    	aWriter.addJS_cr("destinatariosfnfArray = [];");
    	aWriter.addJS_cr("resetList('" + destinatariosfnfPrefix + "', '" + DESTINATARIO_SFNF_ID + "');");
    	
    	if(destinatariossfnf != null) {
	    	for(String destinatariosfnf2 : destinatariossfnf) {
	    		
				aWriter.addJS_cr("destinatariosfnf2 = {");
				aWriter.addJS_cr("key: '" + destinatariosfnf2 + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("destinatariosfnfArray.push(destinatariosfnf2);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + destinatariosfnfPrefix + "', '" + DESTINATARIO_SFNF_ID + "', destinatariosfnfArray);");	
	    	
    	}

		// OPERACIONES SFNF
	    
    	aWriter.addJS_cr("operacionessfnfArray = [];");
    	aWriter.addJS_cr("resetList('" + operacionesfnfPrefix + "', '" + OPERACION_SFNF_ID + "');");
    	
    	if(operacionessfnf != null) {
	    	for(String operacionsfnf : operacionessfnf) {
	    		
				aWriter.addJS_cr("operacionsfnf = {");
				aWriter.addJS_cr("key: '" + operacionsfnf + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("operacionessfnfArray.push(operacionsfnf);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + operacionesfnfPrefix + "', '" + OPERACION_SFNF_ID + "', operacionessfnfArray);");	
	    	
    	}

		// NOMBRE CONTRATO SFNF
	    
    	aWriter.addJS_cr("nombreconsfnfArray = [];");
    	aWriter.addJS_cr("resetList('" + nombrecontratosfnfPrefix + "', '" + NOMBRECONTRATO_SFNF_ID + "');");
    	
    	if(nombrecontratossfnf != null) {
	    	for(String nombrecontratosfnf : nombrecontratossfnf) {
	    		
				aWriter.addJS_cr("nombrecontratosfnf = {");
				aWriter.addJS_cr("key: '" + nombrecontratosfnf + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("nombreconsfnfArray.push(nombrecontratosfnf);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + nombrecontratosfnfPrefix + "', '" + NOMBRECONTRATO_SFNF_ID + "', nombreconsfnfArray);");	
	    	
    	}

		// Destinatario TFNF
	    
    	aWriter.addJS_cr("destinatariostfnfArray = [];");
    	aWriter.addJS_cr("resetList('" + destinatariostfnfPrefix + "', '" + DESTINATARIO_TFNF_ID + "');");
    	
    	if(destinatariostfnf != null) {
	    	for(String destinatariotfnf : destinatariostfnf) {
	    		
				aWriter.addJS_cr("destinatariotfnf = {");
				aWriter.addJS_cr("key: '" + destinatariotfnf + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("destinatariostfnfArray.push(destinatariotfnf);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + destinatariostfnfPrefix + "', '" + DESTINATARIO_TFNF_ID + "', destinatariostfnfArray);");	
	    	
    	}

		// pais TFNF
	    
    	aWriter.addJS_cr("paisestfnfArray = [];");
    	aWriter.addJS_cr("resetList('" + paistfnfPrefix + "', '" + PAIS_TFNF_ID + "');");
    	
    	if(paisestfnf != null) {
	    	for(String paistfnf : paisestfnf) {
	    		
				aWriter.addJS_cr("paistfnf = {");
				aWriter.addJS_cr("key: '" + paistfnf + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("paisestfnfArray.push(paistfnf);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + paistfnfPrefix + "', '" + PAIS_TFNF_ID + "', paisestfnfArray);");	
	    	
    	}

		// finalidades TFNF
	    
    	aWriter.addJS_cr("finalidadestfnfArray = [];");
    	aWriter.addJS_cr("resetList('" + finalidadestfnfPrefix + "', '" + FINALIDADES_TFNF_ID + "');");
    	
    	if(finalidadestfnf != null) {
	    	for(String finalidadtfnf : finalidadestfnf) {
	    		
				aWriter.addJS_cr("finalidadtfnf = {");
				aWriter.addJS_cr("key: '" + finalidadtfnf + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("finalidadestfnfArray.push(finalidadtfnf);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + finalidadestfnfPrefix + "', '" + FINALIDADES_TFNF_ID + "', finalidadestfnfArray);");	
	    	
    	}

		// causas legitimacion TFNF
	    
    	aWriter.addJS_cr("causaslegtfnfArray = [];");
    	aWriter.addJS_cr("resetList('" + causaslegtfnfPrefix + "', '" + CAUSALEG_TFNF_ID + "');");
    	
    	if(causaslegstfnf != null) {
	    	for(String causalegtfnf : causaslegstfnf) {
	    		
				aWriter.addJS_cr("causalegtfnf = {");
				aWriter.addJS_cr("key: '" + causalegtfnf + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("causaslegtfnfArray.push(causalegtfnf);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + causaslegtfnfPrefix + "', '" + CAUSALEG_TFNF_ID + "', causaslegtfnfArray);");	
	    	
    	}

		//periodovigenciaefnf
		if(preiodosvigenciaefnf == null) {
					
			aWriter.addJS_cr("preiodosvigenciaefnf = null;");
			
		} else {

			aWriter.addJS_cr("preiodosvigenciaefnf = {");
			aWriter.addJS_cr("key: '" + preiodosvigenciaefnf + "',");
			aWriter.addJS_cr("label: '',");
			aWriter.addJS_cr("previewURL: undefined");
			aWriter.addJS_cr("};");

		}

		//periodovigenciasfnf
		if(preiodosvigenciasfnf == null) {
					
			aWriter.addJS_cr("preiodosvigenciasfnf = null;");
			
		} else {

			aWriter.addJS_cr("preiodosvigenciasfnf = {");
			aWriter.addJS_cr("key: '" + preiodosvigenciasfnf + "',");
			aWriter.addJS_cr("label: '',");
			aWriter.addJS_cr("previewURL: undefined");
			aWriter.addJS_cr("};");

		}
		aWriter.addJS_cr("ebx_form_setValue('" + preiodosvigenciaefnf + "', preiodosvigenciaefnf)");
		aWriter.addJS_cr("ebx_form_setValue('" + estatusvigenciaefnfPerfix + "', " +  estatusvigenciaefnf + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + preiodosvigenciasfnf + "', preiodosvigenciasfnf)");
		aWriter.addJS_cr("ebx_form_setValue('" + estatusvigenciasfnfPrefix + "', " +  estatusvigenciasfnf + ")");


	}
	private void ajaxCallback3(UserServiceAjaxContext anAjaxContext, UserServiceAjaxResponse anAjaxResponse){
		Adaptation dataset = anAjaxContext.getValueContext(OBJECT_KEY).getAdaptationInstance();
		AdaptationTable table3 = dataset.getTable(Path.parse("/root/Formularios/FNCVD"));
		List<String> destinatariosfncv = null;
		List<String> finalidadesfncv = null;
		List<String> causaslegsfncv = null;
		List<String> destinatariosefacv = null;
		List<String> operacionesefacv = null;
		List<String> nombrecontratosefacv = null;
		String periodovigenciaefacv = null;
		boolean estatusvigenciaefacv = false;
		List<String> destinatariossfncv = null;
		List<String> operacionessfncv = null;
		List<String> nombrecontratossfncv = null;
		String periodovigenciasfncv = null;
		boolean estatusvigenciasfncv = false;
		List<String> destinatariostfncv = null;
		List<String> paisestfncv = null;
		List<String> finalidadestfncv = null;
		List<String> causaslegstfncv= null; 

		String value3 = anAjaxContext.getParameter("fieldValue");
		if(value3 != null && !value3.isBlank()) {


			Adaptation record3 = table3.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(value3));
	    
			destinatariosfncv = record3.getList(Path.parse("./Exterior_banco/Para_transferir-terceros/Destinatario_trasferencia_terceros_F"));
			finalidadesfncv = record3.getList(Path.parse("./Exterior_banco/Para_transferir-terceros/Finalidades_de_la_transferencia_trasferencia_terceros_F"));
			causaslegsfncv = record3.getList(Path.parse("./Exterior_banco/Para_transferir-terceros/Causa_de_legitimacion_trasferencia_terceros_F"));
			destinatariosefacv = record3.getList(Path.parse("./Exterior_banco/Paratratamiento_encargados/Destinatario-encargado"));
			operacionesefacv = record3.getList(Path.parse("./Exterior_banco/Paratratamiento_encargados/Operaciones_ejecuta_encargado"));
			nombrecontratosefacv = record3.getList(Path.parse("./Exterior_banco/Paratratamiento_encargados/Nombre_contrato-encargado"));
			periodovigenciaefacv = record3.getString(Path.parse("./Exterior_banco/Paratratamiento_encargados/Periodo_vigenciaencargado"));
			estatusvigenciaefacv = record3.get_boolean(Path.parse("./Exterior_banco/Paratratamiento_encargados/Vigente_encargado"));
			destinatariossfncv = record3.getList(Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Destinatario_subencargado_F"));
			operacionessfncv = record3.getList(Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Operaciones_que_ejecuta_subencargado_F"));
			nombrecontratossfncv = record3.getList(Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Nombre_contrato_subencargado_F"));
			periodovigenciasfncv = record3.getString(Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Periodo_vigencia_subencargado_f"));
			estatusvigenciasfncv = record3.get_boolean(Path.parse("./Exterior_banco/Para_tratamiento-subencargados/Vigente_subencargado_F"));
			destinatariostfncv = record3.getList(Path.parse("./Exterior_banco/Para_transferir-terceros/Destinatario_trasferencia_terceros_F"));
			paisestfncv = record3.getList(Path.parse("./Exterior_banco/Para_transferir-terceros/Pais_trasferencia_terceros_F"));
			finalidadestfncv = record3.getList(Path.parse("./Exterior_banco/Para_transferir-terceros/Finalidades_de_la_transferencia_trasferencia_terceros_F"));
			causaslegstfncv = record3.getList(Path.parse("./Exterior_banco/Para_transferir-terceros/Causa_de_legitimacion_trasferencia_terceros_F"));
    	}
		UserServiceWriter aWriter = anAjaxResponse.getWriter();
		//DESTINATARIO FNCV
	    
    	aWriter.addJS_cr("destinatariofncvArray = [];");
    	aWriter.addJS_cr("resetList('" + destinatariofncvPrefix + "', '" + DESTINATARIO_FNCV_ID + "');");
    	
    	if(destinatariosfncv != null) {
	    	for(String destinatariofncv : destinatariosfncv) {
	    		
				aWriter.addJS_cr("destinatariofncv = {");
				aWriter.addJS_cr("key: '" + destinatariofncv + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("destinatariofncvArray.push(destinatariofncv);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + destinatariofncvPrefix + "', '" + DESTINATARIO_FNCV_ID + "', destinatariofncvArray);");	
	    	
    	}
		//FINALIDADES FNCV
	    
    	aWriter.addJS_cr("finalidadesfncvArray = [];");
    	aWriter.addJS_cr("resetList('" + finalidadesfncvPrefix + "', '" + FINALIDADES_FNCV_ID + "');");
    	
    	if(finalidadesfncv != null) {
	    	for(String finalidadfncv : finalidadesfncv) {
	    		
				aWriter.addJS_cr("finalidadfncv = {");
				aWriter.addJS_cr("key: '" + finalidadfncv + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("finalidadesfncvArray.push(finalidadfncv);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + finalidadesfncvPrefix + "', '" + FINALIDADES_FNCV_ID + "', finalidadesfncvArray);");	
	    	
    	}
		//CAUSAS LEGITIMACION FNCV
	    
    	aWriter.addJS_cr("causaslegfncvArray = [];");
    	aWriter.addJS_cr("resetList('" + causaslegfncvPrefix + "', '" + CAUSAS_LEG_FNCV_ID + "');");
    	
    	if(causaslegsfncv != null) {
	    	for(String causaslegfncv : causaslegsfncv) {
	    		
				aWriter.addJS_cr("causaslegfncv = {");
				aWriter.addJS_cr("key: '" + causaslegfncv + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("causaslegfncvArray.push(causaslegfncv);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList('" + causaslegfncvPrefix + "', '" + CAUSAS_LEG_FNCV_ID + "', causaslegfncvArray);");	
	    	
    	}

		//DESTINATARIO EFNCV
	    
    	aWriter.addJS_cr("destinatarioefncvArray = [];");
    	aWriter.addJS_cr("resetList('" + destinatarioefacvPerfix + "', '" + DESTINATARIO_EFACV_ID + "');");
    	
    	if(destinatariosefacv != null) {
	    	for(String destinatarioefacv : destinatariosefacv) {
	    		
				aWriter.addJS_cr("destinatarioefacv = {");
				aWriter.addJS_cr("key: '" + destinatarioefacv + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("destinatarioefncvArray.push(destinatarioefacv);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + destinatarioefacvPerfix + "', '" + DESTINATARIO_EFACV_ID + "', destinatarioefncvArray);");	
	    	
    	}
		//OPERACIONES EFNCV
	    
    	aWriter.addJS_cr("operacionesfacvArray = [];");
    	aWriter.addJS_cr("resetList('" + operacionefacvPerfix + "', '" + OPERACION_EFACV_ID + "');");
    	
    	if(operacionesefacv != null) {
	    	for(String operacionefacv : operacionesefacv) {
	    		
				aWriter.addJS_cr("operacionefacv = {");
				aWriter.addJS_cr("key: '" + operacionefacv + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("operacionesfacvArray.push(operacionefacv);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + operacionefacvPerfix + "', '" + OPERACION_EFACV_ID + "', operacionesfacvArray);");	
	    	
    	}
		//NOMBRE CONTRATO EFNCV
	    
    	aWriter.addJS_cr("nombrecontratofncvArray = [];");
    	aWriter.addJS_cr("resetList('" + nombrecontratoefacvPerfix + "', '" + NOMBRECONTRATO_EFACV_ID + "');");
    	
    	if(nombrecontratosefacv != null) {
	    	for(String nombrecontratoefacv : nombrecontratosefacv) {
	    		
				aWriter.addJS_cr("nombrecontratoefacv = {");
				aWriter.addJS_cr("key: '" + nombrecontratoefacv + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("nombrecontratofncvArray.push(nombrecontratoefacv);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + nombrecontratoefacvPerfix + "', '" + NOMBRECONTRATO_EFACV_ID + "', nombrecontratofncvArray);");	
	    	
    	}

		//DESTINATARIO SFNCV
	    
    	aWriter.addJS_cr("destinatariosfncvArray = [];");
    	aWriter.addJS_cr("resetList('" + destinatariosfncvPrefix + "', '" + DESTINATARIO_SFNCV_ID + "');");
    	
    	if(destinatariosfncv != null) {
	    	for(String destinatariosfncv1 : destinatariosfncv) {
	    		
				aWriter.addJS_cr("destinatariosfncv1 = {");
				aWriter.addJS_cr("key: '" + destinatariosfncv1 + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("destinatariosfncvArray.push(destinatariosfncv1);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + destinatariosfncvPrefix + "', '" + DESTINATARIO_SFNCV_ID + "', destinatariosfncvArray);");	
	    	
    	}

		//OPERACIONES SFNCV
	    
    	aWriter.addJS_cr("operacionessfncvArray = [];");
    	aWriter.addJS_cr("resetList('" + operacionesfncvPrefix + "', '" + OPERACIONES_SFNCV_ID + "');");
    	
    	if(nombrecontratossfncv != null) {
	    	for(String nombrecontratosfncv : nombrecontratossfncv) {
	    		
				aWriter.addJS_cr("nombrecontratosfncv = {");
				aWriter.addJS_cr("key: '" + nombrecontratosfncv + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("operacionessfncvArray.push(nombrecontratosfncv);");
	    		
	    	}
	    	
	    	aWriter.addJS_cr("setList2('" + operacionesfncvPrefix + "', '" + OPERACIONES_SFNCV_ID + "', operacionessfncvArray);");	
	    	
    	}

		//NOMBRE CONTRATO SFNCV
	    
    	aWriter.addJS_cr("nombrecontratosfncvArray = [];");
    	aWriter.addJS_cr("resetList('" + nombrecontratosfncvPrefix + "', '" + NOMBRECONTRATO_SFNCV_ID + "');");
    	
    	if(nombrecontratossfncv != null) {
	    	for(String nombrecontratosfncv : nombrecontratossfncv) {
	    		
				aWriter.addJS_cr("nombrecontratosfncv = {");
				aWriter.addJS_cr("key: '" + nombrecontratosfncv + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("nombrecontratosfncvArray.push(nombrecontratosfncv);");
	    		
	    	}

	    	aWriter.addJS_cr("setList2('" + nombrecontratosfncvPrefix + "', '" + NOMBRECONTRATO_SFNCV_ID + "', nombrecontratosfncvArray);");	
	    	
    	}

		//DESTINATARIO TFNCV
	    
    	aWriter.addJS_cr("destinatariotfncvArray = [];");
    	aWriter.addJS_cr("resetList('" + destinatariostfncvPrefix + "', '" + DESTINATARIO_TFNCV_ID + "');");
    	
    	if(destinatariostfncv != null) {
	    	for(String destinatariotfnc : destinatariostfncv) {
	    		
				aWriter.addJS_cr("destinatariotfnc = {");
				aWriter.addJS_cr("key: '" + destinatariotfnc + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("destinatariotfncvArray.push(destinatariotfnc);");
	    		
	    	}

	    	aWriter.addJS_cr("setList2('" + destinatariostfncvPrefix + "', '" + DESTINATARIO_TFNCV_ID + "', destinatariotfncvArray);");	
	    	
    	}

		//PAIS TFNCV
				
		aWriter.addJS_cr("paistfncvArray = [];");
		aWriter.addJS_cr("resetList('" + paistfncvPrefix + "', '" + PAIS_TFNCV_ID + "');");

		if(paisestfncv != null) {
			for(String paistfnc : paisestfncv) {
				
				aWriter.addJS_cr("paistfnc = {");
				aWriter.addJS_cr("key: '" + paistfnc + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("paistfncvArray.push(paistfnc);");
				
			}

			aWriter.addJS_cr("setList('" + paistfncvPrefix + "', '" + PAIS_TFNCV_ID + "', paistfncvArray);");	
			
		}

		//DESTINATARIO TFNCV
	    
    	aWriter.addJS_cr("finalidadestfncvArray = [];");
    	aWriter.addJS_cr("resetList('" + finalidadestfncvPrefix + "', '" + FINALIDADES_TFNCV_ID + "');");
    	
    	if(finalidadestfncv != null) {
	    	for(String finalidadtfnc : finalidadestfncv) {
	    		
				aWriter.addJS_cr("finalidadtfnc = {");
				aWriter.addJS_cr("key: '" + finalidadtfnc + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("finalidadestfncvArray.push(finalidadtfnc);");
	    		
	    	}

	    	aWriter.addJS_cr("setList2('" + finalidadestfncvPrefix + "', '" + FINALIDADES_TFNCV_ID + "', finalidadestfncvArray);");	
	    	
    	}

		//CAUSAS LEGITIMACION TFNCV
	    
    	aWriter.addJS_cr("causaslegtfncvArray = [];");
    	aWriter.addJS_cr("resetList('" + causalegtfncvPrefix + "', '" + CAUSALEG_TFNCV_ID + "');");
    	
    	if(causaslegstfncv != null) {
	    	for(String causalegtfnc : causaslegstfncv) {
	    		
				aWriter.addJS_cr("causalegtfnc = {");
				aWriter.addJS_cr("key: '" + causalegtfnc + "',");
				aWriter.addJS_cr("label: '',");
				aWriter.addJS_cr("previewURL: undefined");
				aWriter.addJS_cr("};");
				
				aWriter.addJS_cr("causaslegtfncvArray.push(causalegtfnc);");
	    		
	    	}

	    	aWriter.addJS_cr("setList('" + causalegtfncvPrefix + "', '" + CAUSALEG_TFNCV_ID + "', causaslegtfncvArray);");	
	    	
    	}

		//periodovigenciasfncv
		if(periodovigenciasfncv == null) {
					
			aWriter.addJS_cr("periodovigenciasfncv = null;");
			
		} else {

			aWriter.addJS_cr("periodovigenciasfncv = {");
			aWriter.addJS_cr("key: '" + periodovigenciasfncv + "',");
			aWriter.addJS_cr("label: '',");
			aWriter.addJS_cr("previewURL: undefined");
			aWriter.addJS_cr("};");

		}

		//periodovigenciaefacv
		if(periodovigenciaefacv == null) {
					
			aWriter.addJS_cr("periodovigenciaefacv = null;");
			
		} else {

			aWriter.addJS_cr("periodovigenciaefacv = {");
			aWriter.addJS_cr("key: '" + periodovigenciaefacv + "',");
			aWriter.addJS_cr("label: '',");
			aWriter.addJS_cr("previewURL: undefined");
			aWriter.addJS_cr("};");

		}

		aWriter.addJS_cr("ebx_form_setValue('" + estatusvigenciaefacvPerfix + "', " +  estatusvigenciaefacv + ")");
		aWriter.addJS_cr("ebx_form_setValue('" + periodovigenciasfncvPrefix + "', periodovigenciasfncv)");
		aWriter.addJS_cr("ebx_form_setValue('" + periodovigenciaefnfPerfix + "', periodovigenciaefacv)");
		aWriter.addJS_cr("ebx_form_setValue('" + estatusvigenciasfncvPrefix + "', " +  estatusvigenciasfncv + ")");
	}
	
	@Override
	public void validate(UserServiceValidateContext<T> aContext) {

		
	}

}


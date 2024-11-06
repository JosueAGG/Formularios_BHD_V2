package com.alldatum.ebx.bhd.form.gdpr;


import java.util.List;
import java.util.Date;
import com.alldatum.ebx.bhd.util.AdaptationUtil;
import com.alldatum.ebx.bhd.widget.UIDynamicListWidget;
import com.alldatum.ebx.bhd.widget.UIDynamicListWidgetFactory;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.PrimaryKey;
import com.onwbp.base.text.UserMessage;
import com.onwbp.org.apache.calcite.avatica.Meta.Pat;
import com.onwbp.org.apache.cxf.jaxrs.ext.PATCH;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.interactions.SessionInteraction;
import com.orchestranetworks.interactions.InteractionHelper.ParametersMap;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaNode;
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

public class FEDIUserService<T extends TableEntitySelection> implements UserService<T> {

        private static final ObjectKey OBJECT_KEY = ObjectKey.forName("record");
       // private static final ObjectKey RESPUESTAS_KEY = ObjectKey.forName("respuestas");

        // Crear los datos para los registros con herencia
        private static final Path NOMBRE_TRATAMIENTO_PATH = Path.parse("Nombre_tratamiento");
        private static final Path NOMBRE_ANALISIS_IMPACTO_PATH = Path.parse("Heredar_datos/ID_Nombre_analisis_impacto");
        private static final Path DESTINATARIOE_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/EncargadoAB/DestinatarioE");
        private static final Path SERVICIOCENCARGADO_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/EncargadoAB/ServicionC");

        private static final Path IDENTIFICADOR_AR_INFORMACION_PATH = Path.parse("Informacion/IdentificadorAR");

        // Heredar de la ficha de analisis basico
        private static final Path PERIODO_VIGENCIAE_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/EncargadoAB/Periodo_vigenciaE");
        private static final Path OPERACIONES_EJECUTA_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/EncargadoAB/Operaciones_ejecuta");
        private static final Path NOMBRE_CONTRATO_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/EncargadoAB/Nombre_contrato");
        private static final Path FECHA_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/EncargadoAB/Fecha_formalizacion_E");
        
        private static final Path ESTATUS_VIGENCIAE_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/EncargadoAB/Estatus_vigenciaE");

        //
        private static final Path DESTINATARIO_SUN_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/Sub_encargadoAB/Destinatario-sun");
        private static final Path OPERACIONES_EJECSUB_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/Sub_encargadoAB/Operacione_ejecutaSub");
        private static final Path NOMBRE_CON_SUB_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/Sub_encargadoAB/Nombre_contrato_sub");
        private static final Path FECHA_FORM_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/Sub_encargadoAB/Fecha_formalizacion-sun");
        private static final Path PERIODO_VIG_SUB_PATCH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/Sub_encargadoAB/PeriodovigenciaSub");
        private static final Path ESTATUS_V_SUB_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/Sub_encargadoAB/EstastusV-sub");

        private static final Path BASE_JURIDICA_HERED_PATH = Path.parse("Base_juridica/JuicioHereda");
        private static final Path ID_NOMB_FAB_PATH = Path.parse("Informacion/idnombrefab");

        private static final Path OPERACIONES_EJECUTA_DPIA_PATH = Path.parse("Extenciones_limites_DPIA/Operaciones_ejecutaAB");
        private static final Path RESPONSABLE_FAB_PATH = Path.parse("DatosBasicos/ResponsableFAB");
        private static final Path CORRESPONSABLE_FAB_PATH = Path.parse("DatosBasicos/CorresponsableFAB");
        private static final Path FUNCIONES_DOS_PATH = Path.parse("DatosBasicos/Funciones2");
        private static final Path DESTINATARIO_I_PATH = Path.parse("DatosBasicos/Destinatarios_I");
        private static final Path FINALIDAD_C_PATH = Path.parse("DatosBasicos/FinalidadC");
        private static final Path DESTINATARIO_DOS_I_PATH = Path.parse("DatosBasicos/Destinatario2_I");
        private static final Path OPERACIONES_DOS_I_PATH = Path.parse("DatosBasicos/Operaciones2_I");
        private static final Path CAUSAS_DE_LEGITIMACIONCP_PATH = Path.parse("Cesionesprevistasm/Causas_de_legitimacionCP");
        private static final Path DESTINATARIO_TRES_I_PATH = Path.parse("DatosBasicos/Destinatario3_I");

        //
        private static final Path DESTINATARIO_CP_PATH = Path.parse("Cesionesprevistasm/DestinatarioCP");
        private static final Path FINALIDADES_CP_PATH = Path.parse("Cesionesprevistasm/FinalidadesCP");
        private static final Path OPEEJE4_PATH = Path.parse("Aspectos_Metodologicos_DPIA/ExtencionesLDPIA/OPEEJE4");
        private static final Path HEREDAR_JUICIO_PATH = Path.parse("Analisis_de_Bases_Juridicas/Heredar_juicio");
        private static final Path FIN_PRINCIPAL_UNO_PATH = Path.parse("Descripcion_tratamiento/Finprincipal1");
        private static final Path FIN_SECUNDARIO_PATH = Path.parse("Descripcion_tratamiento/Finsecundario2");
        private static final Path FINES_POSTERIORES_PATH = Path.parse("Descripcion_tratamiento/Finesposteriores");
        private static final Path ROLES_PATH = Path.parse("DatosBasicos/Roles");
        private static final Path OPEREACIONES_SUBI_PATH = Path.parse("DatosBasicos/operacionessub_I");
        private static final Path DESTINATARIO_CUATRO_PATH = Path.parse("DatosBasicos/Destinatarios4_I");
        private static final Path FANLIDAD_TI_PATH = Path.parse("DatosBasicos/Finalidad_IT");

        //Cuestionarioa
        
    	private static final Path NOMBRE_CUESTIONARIODPIA_PATH = Path.parse("Analisis_obligacion/idCuestionariodpia");
    	private static final Path PROCEDENCIADPIA_PATH = Path.parse("Analisis_obligacion/ProdeDPIA");
    	private static final Path JUSTIFICACIONDPIA_PATH = Path.parse("Analisis_obligacion/JustifricaP");
        
        
        
        
        
        private static final Path PRINCIPALES_RIESGOS_A_PATH = Path.parse("Principales_riesgos_identificadosPrincipales_riesgos_identificados");
        private static final Path ANALISIS_RIESGO_PATH = Path.parse("Analisis_riesgo");
        private static final Path EVRIESGO_R_ANALISIS_PATH = Path.parse("EVriesgoRAnalsisImopac");
        private static final Path PG_RIESGO_ANALISIS_PATH = Path.parse("PGriesgo-AnalisisR");
        private static final Path PGR_ANALISIS_IMPACDPIA_PATH = Path.parse("PGR-Analsisi_impactoDPIA");
        
        
        
        
        
        
        private static final Path RESPONSABLE_HEREDA_PATH = Path.parse("Heredar_datos/Responsable");
        private static final Path CORRESPONSABLE_O_PATH = Path.parse("Heredar_datos/Corresponsables");
        private static final Path ENCARGADO_DECICION_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/EncargadoAB/Encargado_decicion");
        private static final Path SUB_ENCARGADO_DE_PATH = Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/Sub_encargadoAB/Sub_encargado_decicion");
        private static final Path ENCARGADO_DE_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/EncargadoAB/Encargado_decicion");
        private static final Path SERVCONTRATAFO_PATH = Path.parse("Heredar_datos/Herencia-datosFF/EncargadoAB/ServicionC");
        private static final Path DESTINATARIO_E_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/EncargadoAB/DestinatarioE");
        private static final Path OPERACIONEA_EJECUTA_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/EncargadoAB/Operaciones_ejecuta");
        private static final Path NOM_CONTRATA_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/EncargadoAB/Nombre_contrato");
        private static final Path FECHA_FORM_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/EncargadoAB/Fecha_formalizacion_E");
        private static final Path PERIODO_VIG_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/EncargadoAB/Periodo_vigenciaE");
        private static final Path ESTATUSVIG_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/EncargadoAB/Estatus_vigenciaE");
        private static final Path SUB_ENCAR_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/Sub_encargadoAB/Sub_encargado_decicion");
        private static final Path SERVIC_CONTRATADO_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/Sub_encargadoAB/Servicio_contratadoSub");

        private static final Path DESTINATARIO_SUN_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/Sub_encargadoAB/Destinatario-sun");
        private static final Path OPER_EJEC_SUB_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/Sub_encargadoAB/Destinatario-sun");
        private static final Path NOM_CON_SUB_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/Sub_encargadoAB/Nombre_contrato_sub");
        
        private static final Path FECH_FOR_SUM_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/Sub_encargadoAB/Fecha_formalizacion-sun");
       
        private static final Path PERIODO_SUB_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/Sub_encargadoAB/PeriodovigenciaSub");
        private static final Path ESTATUS_V_DOS_PATH = Path.parse("Heredar_datos/Herencia-datosFF/Sub_encargadoAB/EstastusV-sub");

        private static final Path ENCARGADO_TRES_O_PATH = Path.parse("Heredar_datos/Hereda_FCVD/EncargadoAB/Encargado_decicion");

        private static final Path CONTRATADO_TRES_PATH = Path.parse("Heredar_datos/Hereda_FCVD/EncargadoAB/ServicionC");

        private static final Path DESTINATARIO_E_TRES_PATH = Path.parse("Heredar_datos/Hereda_FCVD/EncargadoAB/DestinatarioE");
        //
        private static final Path OPERACIONES_TRES_PATH = Path.parse("Heredar_datos/Hereda_FCVD/EncargadoAB/Operaciones_ejecuta");
        private static final Path NOM_TRES_PATH = Path.parse("Heredar_datos/Hereda_FCVD/EncargadoAB/Nombre_contrato");
        private static final Path PERIODO_TRES_PATH = Path.parse("Heredar_datos/Hereda_FCVD/EncargadoAB/Periodo_vigenciaE");
        private static final Path ESTATUS_TRES_PATH = Path.parse("Heredar_datos/Hereda_FCVD/EncargadoAB/Estatus_vigenciaE");
        private static final Path FECHA_TRES_PATH = Path.parse("Heredar_datos/Hereda_FCVD/EncargadoAB/Fecha_formalizacion_E");

        //

        private static final Path SUB_TRES_D_PATH = Path
                        .parse("Heredar_datos/Hereda_FCVD/Sub_encargadoAB/Sub_encargado_decicion");
        private static final Path SERVI_CON_CUATRO_PATH = Path
                        .parse("Heredar_datos/Hereda_FCVD/Sub_encargadoAB/Servicio_contratadoSub");
        private static final Path DESTINATARIO_CUATROA_PATH = Path
                        .parse("Heredar_datos/Hereda_FCVD/Sub_encargadoAB/Destinatario-sun");
        private static final Path OPER_EJECUA_PATH = Path
                        .parse("Heredar_datos/Hereda_FCVD/Sub_encargadoAB/Operacione_ejecutaSub");
        private static final Path NOM_CON_CATRO_PATH = Path
                        .parse("Heredar_datos/Hereda_FCVD/Sub_encargadoAB/Nombre_contrato_sub");
        private static final Path FECHA_CUATRO_PATH = Path
                        .parse("Heredar_datos/Hereda_FCVD/Sub_encargadoAB/Fecha_formalizacion-sun");
        private static final Path PERIODO_VIG_CUATRO_PATH = Path
                        .parse("Heredar_datos/Hereda_FCVD/Sub_encargadoAB/PeriodovigenciaSub");
        private static final Path ESTATUSV_PATH = Path
                        .parse("Heredar_datos/Hereda_FCVD/Sub_encargadoAB/EstastusV-sub");

        //
        private static final Path DESTINATARIO_CP_PATH2 = Path
                        .parse("Cesiones_previasAF/DestinatarioCP");

        private static final Path FINALIDADES_CP_PATH2 = Path
                        .parse("Cesiones_previasAF/FinalidadesCP");

        private static final Path CAUSA_CP_PATH = Path
                        .parse("Cesiones_previasAF/Causas_de_legitimacionCP");

        private static final Path DESTINATARIO_5_PATH = Path
                        .parse("Cesiones_previasCV/DestinatarioCP");

        private static final Path FINALIDADES_CP_A_PATH = Path
                        .parse("Cesiones_previasCV/FinalidadesCP");

        private static final Path CAUSE_LEG_2_PATH = Path
                        .parse("Cesiones_previasCV/Causas_de_legitimacionCP");
        private static final Path DESCRIPCION_FT_PATH = Path
                        .parse("Extenciones_limites_DPIA/Descripcion_FT");

        private static final Path DESCRIPCIONFC_PATH = Path
                        .parse("Extenciones_limites_DPIA/DescripcionFC");

        private static final Path NOM_FICHA_ANALISIS_PATH = Path
                        .parse("DatosBasicos/Nombre_ficha_de_analisis_de_impacto");
        private static final Path DESTINATARIO_F5_PATH = Path.parse("DatosBasicos/Destinatario_5IF");

        private static final Path CAUSAL_L_PATH = Path.parse("DatosBasicos/CausasL_I");
        private static final Path DESTINATARIO_ITT_PATH = Path.parse("DatosBasicos/Destinatarios_ITT");
        private static final Path FINALIDAD_ITT_PATH = Path.parse("DatosBasicos/Finalidad_ITT");
        private static final Path ROLES6_PATH = Path.parse("DatosBasicos/Roles6I");
        private static final Path OPERACIONES_N_PATH = Path.parse("DatosBasicos/operacioneejeIN");
        private static final Path DESTINATARIOFCI_PATH = Path.parse("DatosBasicos/DestinatarioFCI");
        private static final Path CAUSAS_LEGITIMAS_CDVI_PATH = Path.parse("DatosBasicos/CausaslegitimasCVDI");

        private static final Path DESTINATARIO_CVDITT_PATH = Path
                        .parse("DatosBasicos/DestinatarioCVDITT");
        private static final Path FINALIDADES_CVDT_PATH = Path
                        .parse("DatosBasicos/Finalidades_CVDTT_I");
        private static final Path ROLES_CVD_12_PATH = Path
                        .parse("DatosBasicos/RolesCVD_I2");

        private static final Path OPERAEJIE_PATH = Path
                        .parse("DatosBasicos/OperaEJIEXFCVD");

        private static final Path DESFASE4_PATH = Path
                        .parse("Aspectos_Metodologicos_DPIA/ExtencionesLDPIA/DesFase4");
        private static final Path DESFASE5_PATH = Path
                        .parse("Aspectos_Metodologicos_DPIA/ExtencionesLDPIA/DescripcionFAse5");
        private static final Path ID_NOM_FAB_PATH = Path.parse("Referencias2/idnombrefab");
        
        
        
    	//Datos de revisor metodológico
    	//Aceptable
    	
    	private static final Path CONCLUCION_ACEPTABLE_PATH = Path.parse("JustificacionAceptable");  	

    	//iInaceptable
    	
    	private static final Path CONCLUCION_INACEPTABLE_PATH = Path.parse("Justificacionina");
        
        
        
        
    	//Datos de revisor metodológico
    	//Aceptable
    	private static final Path MENU_ACEPTABLE_PATH = Path.parse("./Memorando_delegado/AcepM");
    	private static final Path CONDICION_ACEPTABLE_PATH = Path.parse("./Memorando_delegado/Consideraciones_adicionales_A");  	

    	//iInaceptable
    	private static final Path MENU_INACEPTABLE_PATH = Path.parse("./Memorando_delegado/Menuinaceptable");
    	private static final Path CONDICION_INACEPTABLE_PATH = Path.parse("./Memorando_delegado/Consideraciones_adicionales_I");
    	
        
        
        
        
        //
        private static final String DESTINATARIO_E = "destinatarioEList";
        private static final String SERVICIOE_ID = "servicioceList";
        private static final String OPERACIONES_EJECUTA = "operacionesEjecutaList";
        private static final String NOMBRE_CONTRATO = "nombreContratoList";
        private static final String DESTINATARIO_SUN = "destinatarioSunList";
        private static final String OPERACIONES_EJECSUB = "operacionesESubList";
        private static final String NOM_CONTRATO_SUB = "nombreConSubList";

        private static final String BASE_JURIDICA = "baseJuridicaLista";
        private static final String OPERACIONES_EJECUTA_DPIA = "operEjecDPIAList";
        private static final String RESPONSABLE_FAB = "responsableFabList";
        private static final String CORRESPONSABLE_FAB = "corresponcableFabList";
        private static final String FUNCIONES_DOS_FAB = "funcionesDosList";
        private static final String DESTINATARIO_I = "destinatarioIList";
        private static final String FINALIDAD_C = "finalidadCList";
        private static final String DESTINATARIO_DOS_I = "destinatarioDosIList";
        private static final String OPERACIONES_DOS_I = "operacionesDosIList";
        private static final String DESTINATARIO_CP = "destinatarioCPList";
        private static final String FINALIDAD_CP = "finalidadCPList";
        private static final String OPEEJE4 = "opeeje4List";
        private static final String HEREDAR_JUICIO = "heredaJuicioList";
        private static final String FIN_PRINCIPAL = "finPrincipalList";
        private static final String FIN_SECUNDARIO = "finSecundarioList";
        private static final String FINES_POSTERIORES = "finesPosterioresList";
        private static final String CAUSA_DE_LEGITIMACION_CP = "causaLegiCP_List";
        private static final String ROLES_LIST = "rolesList";
        private static final String DESTINATARIO_TRES_I = "destinatario3List";
        private static final String OPERACIONES_SUB_I = "operacionesSubIList";
        private static final String DESTINATARIO_CUATRO = "destinatarioCuatroList";
        private static final String FINALIDAD_IT = "finalidadITList";
        private static final String PRINCIPALES_RIESGOS = "principalesRList";
        private static final String ANALISIS_RIESGOS = "analisisRiesgoList";
        private static final String EVANALISIS_RIESGO_IMP = "analisisRiesgoImpacList";
        private static final String PG_RIESGO_ANALISIS_R = "pgAnalisisRList";
        private static final String PGR_ANALISIS_IMPACDIP = "pgrAnalisisList";
        private static final String RESPONSABLE_HEREDA = "responsableList";
        private static final String CORRESPONSABLE_HEREDA = "corresponsableHerreda";
        private static final String SERVICIO_CONTRATADO = "servConList";
        private static final String DESTINATARIO_DOS = "destinatariolist";
        private static final String OPERACIONES_EJECUTA_DOS = "operEjecDosLis";
        private static final String NOM_CONT_DOS = "nombDosList";
        private static final String FECHA_FORM_DOS = "fechaForList";
        private static final String PERIODO_VIG_DOS = "periodoVigDos";
        private static final String ESTATUSVIG = "estatusVigDos";
        private static final String SERV_CON_DOS = "servConDos";
        private static final String DEST_SUN_DOS = "destDosList";
        private static final String OPER_EJEC_SUB_DOS = "operEjecDosSub";
        private static final String NOM_DOS_CON = "nomDosConList";
        private static final String PERIODO_VIDOS = "peVigDosAAAList";
        private static final String ESTATUSS_V_DOS = "estatusdosAVList";
        private static final String ENCARGADO_TRESA = "encargadoTresList";
        private static final String CONTRATADO_TRES = "conTresList";
        private static final String DESTINATARIO__3 = "destTres";
        //
        private static final String OPERACIONES_TRES = "operTres";
        private static final String nomConTraTres = "nomContraTresLiA";
        private static final String SERVICOCUATRO = "serCuatroList";
        private static final String DEST_SUN = "destCuatro";
        private static final String OPER_CUATRO = "operEjecCuatro";
        private static final String NOM_CON_CUA = "nomContratoCuatro";
        private static final String DESTINATARIO_CP_2 = "destaLis";
        private static final String FINALIDADES_ACP = "finaLidasAAAC";
        private static final String CAUSA_CP = "causaCPList";
        private static final String DESTINATARIOCP = "DESCP";
        private static final String FINALIDADCP = "finalidadesCPList";
        private static final String CAUSA_LEG = "finalidadesCPList";
        private static final String DESCRIPTIONFT = "descirpcioListFT";
        private static final String DESCFC = "descirpcioListFC";
        private static final String NOM_FICHA_A = "nomFichaAnList";
        private static final String DESTINATARIO_5IF = "destinatarioF5";
        private static final String CAUSAL_I = "causalI";
        private static final String DESTINATARIO_ITT = "destITTList";
        private static final String FINALIDAD_ITT = "finalidadTTList";
        private static final String ROLES_6 = "rolesAAAList";
        private static final String OPERACIONES_EJEC = "operacionesEjecList";
        private static final String DESTINATARIO_FCI = "destinatarioFCIL";
        private static final String CAUSAS_LEGITIMAS = "causasLegitimas";
        private static final String DESTINATARIO_CDVDITT = "destinatarioCDVD";
        private static final String FINALIDADES_CDVDITT = "FinalidadesCDVD";
        private static final String ROLES12 = "roles12";
        private static final String OPERAJIE = "operajieList";
        private static final String DESFASE4 = "destFase4List";
        private static final String DESFASE5 = "destFase5List";

        // Prefix de los campos
       
        private String pDestinatarioE;
        private String ServicioCEprefix;
        private String pOperacionesEjecuta;
        private String pPeriodoVigenciaE;
        private String pEstatusVigenciaE;
        private String pNombreContrato;
        private String pDestinatarioSun;
        private String pOperacionesEjecutaS;
        private String pFechaFormali;
        private String pPeriodoVigSub;
        private String pEstatuVSub;
        private String pNomConSub;
        private String pBaseJuri;
        private String pOperEjecDPIA;
        private String pIdNomFab;
        private String pResponsableFab;
        private String pCorresponsableFAB;
        private String pFuncionesDos;
        private String pDestinatarioI;
        private String pFinalidadC;
        private String pDestinatarioDosI;
        private String pOperacionesDosI;
        private String pDestinatarioCP;
        private String pFinalidadCP;
        private String pOpeeje4;
        private String pHeredaJuicio;
        private String pFinPrincipal;
        private String pFinSecundario;
        private String pFinesPosteriores;
        private String pCausaLeitimacionCP;
        private String pRoles;
        private String pDestinatarioTres;
        private String pOperacionesSubI;
        private String pDestinatario4;
        private String pFinalidadIT;
        private String pPrincipalesRiesgos;
        private String pAnalisisRiesgo;
        private String pEvRiesgoImpc;
        private String pPGAnalisisRiesgo;
        private String pPGRAnalisisDPIA;
        private String pHeredaResponsable;
        private String pCorresponsable;
        private String pEncargadoDecicion;
        private String pSubEncagado;
        private String pEncargadoDos;
        private String pServContratado;
        private String pDestinatarioDos;
        private String pOperEjecDos;
        private String pNombreConDos;
        private String pFechaDos;
        private String pVigDos;
        private String pEstatusDos;
        private String pSubDos;
        private String pServDos;
        private String pDestDos;
        private String pOperEjecSubDos;
        private String pNomDosContra;
        private String pFechaDosA;
        private String pVigSubDos;
        private String pStatusVDos;
        private String pEncargadoTres;
        private String pContraTres;
        private String pDestinatarioETres;
        private String pOperacionesTres;
        private String pNomConTres;
        private String pFechaTres;
        private String pPeriodoTresVig;
        private String pEstatusT;
        private String pSubCuatro;
        private String pServCuatroList;
        private String pDestCuatro;
        private String pOperEjecutaCuatro;
        private String pNombCCuatro;
        private String pFechaFprm;
        private String pPeriodoVCuatro;
        private String pEstatusCuatro;
        private String pDestinatarioCP2;
        private String pFinalidadCuatroA;
        private String pCausaCP;
        private String pDestinatarioCP3;
        private String pfinalidadesCP;
        private String pCausaLeg;
        private String pDescripcionFT;
        private String pDescripcionFC;
        private String pNomFichaAnalisis;
        private String pDestinatario5F;
        private String pCausaLI;
        private String pDestinatarioTT;
        private String pFinalidad_ITT;
        private String pRoles6;
        private String pOperEjecuta;
        private String pDestinatarioFCI;
        private String pCausasLegitimas;
        private String pDestinatarioCVDT;
        private String pFinalidadesCVDT;
        private String pRoles12;
        private String pOperaEJIE;
        private String pDestFase4;
        private String pDestFase5;
        private String pIdNombreFAB;
        private String pfecha_P;
        //Cuestionario
		private String procedenciaPrefix;
		private String justificacionPrefix;
        
		
		//prefic aceptable
	    String MAceptablePrefix;
	    String CAceptablePrefix;


	    String MInaceptablePrefix;
	    String CInaceptablePrefix;

	    String ConaceptablePrefix;
	    String CoInaceptablePrefix;
        
        //
        private static ValueContext vc;
        private static Adaptation dataset;
        List<Object> evaluacion = null;
        List<Object> evaluacion2 = null;
        List<Object> evaluacion3 = null;
        
        List<Object> evaluacionR = null;
        List<Object> evaluacion2R = null;
        List<Object> evaluacion3R = null;

        @Override
        public UserServiceEventOutcome processEventOutcome(UserServiceProcessEventOutcomeContext<T> aContext,
                        UserServiceEventOutcome anOutcome) {

                return anOutcome;
        }

        @Override
        public void setupDisplay(UserServiceSetupDisplayContext<T> anOutcome,
                        UserServiceDisplayConfigurator aConfigurator) {
                vc = anOutcome.getValueContext(OBJECT_KEY);

                aConfigurator.setContent(this::pane);
                aConfigurator.setDefaultButtons(this::save);

        }

        private UserServiceEventOutcome save(UserServiceEventContext aContext) {
                // Campos heredados Listas
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
  
     		   

     		    // Prefijos para Inaceptable
     		  MInaceptablePrefix = aWriter.getPrefixedPath(MENU_INACEPTABLE_PATH).format();
     		  CInaceptablePrefix = aWriter.getPrefixedPath(CONDICION_INACEPTABLE_PATH).format();
    //Conclucion
     		 ConaceptablePrefix = aWriter.getPrefixedPath(CONCLUCION_ACEPTABLE_PATH).format();
     		CoInaceptablePrefix = aWriter.getPrefixedPath(CONCLUCION_INACEPTABLE_PATH).format();
                
                // Construyendo los prefix de herencia
                pDestinatarioE = aWriter.getPrefixedPath(DESTINATARIOE_PATH).format();
                ServicioCEprefix = aWriter.getPrefixedPath(SERVICIOCENCARGADO_PATH).format();
                pOperacionesEjecuta = aWriter.getPrefixedPath(OPERACIONES_EJECUTA_PATH).format();
                pNombreContrato = aWriter.getPrefixedPath(NOMBRE_CONTRATO_PATH).format();
                pfecha_P = aWriter.getPrefixedPath(FECHA_PATH).format();
                pPeriodoVigenciaE = aWriter.getPrefixedPath(PERIODO_VIGENCIAE_PATH).format();
                pEstatusVigenciaE = aWriter.getPrefixedPath(ESTATUS_VIGENCIAE_PATH).format();
                pDestinatarioSun = aWriter.getPrefixedPath(DESTINATARIO_SUN_PATH).format();
                pOperacionesEjecutaS = aWriter.getPrefixedPath(OPERACIONES_EJECSUB_PATH).format();
                pNomConSub = aWriter.getPrefixedPath(NOMBRE_CON_SUB_PATH).format();
                pFechaFormali = aWriter.getPrefixedPath(FECHA_FORM_PATH).format();
                pPeriodoVigSub = aWriter.getPrefixedPath(PERIODO_VIG_SUB_PATCH).format();
                pEstatuVSub = aWriter.getPrefixedPath(ESTATUS_V_SUB_PATH).format();
                pBaseJuri = aWriter.getPrefixedPath(BASE_JURIDICA_HERED_PATH).format();
                pCorresponsableFAB = aWriter.getPrefixedPath(CORRESPONSABLE_FAB_PATH).format();
                pIdNomFab = aWriter.getPrefixedPath(ID_NOMB_FAB_PATH).format();
                pOperEjecDPIA = aWriter.getPrefixedPath(OPERACIONES_EJECUTA_DPIA_PATH).format();
                pResponsableFab = aWriter.getPrefixedPath(RESPONSABLE_FAB_PATH).format();
                pFuncionesDos = aWriter.getPrefixedPath(FUNCIONES_DOS_PATH).format();
                pDestinatarioI = aWriter.getPrefixedPath(DESTINATARIO_I_PATH).format();
                pDestinatarioDosI = aWriter.getPrefixedPath(DESTINATARIO_DOS_I_PATH).format();
                pFinalidadC = aWriter.getPrefixedPath(FINALIDAD_C_PATH).format();
                pOperacionesDosI = aWriter.getPrefixedPath(OPERACIONES_DOS_I_PATH).format();
                pDestinatarioCP = aWriter.getPrefixedPath(DESTINATARIO_CP_PATH).format();
                pFinalidadCP = aWriter.getPrefixedPath(FINALIDADES_CP_PATH).format();
                pOpeeje4 = aWriter.getPrefixedPath(OPEEJE4_PATH).format();
                pHeredaJuicio = aWriter.getPrefixedPath(HEREDAR_JUICIO_PATH).format();
                pFinPrincipal = aWriter.getPrefixedPath(FIN_PRINCIPAL_UNO_PATH).format();
                pFinSecundario = aWriter.getPrefixedPath(FIN_SECUNDARIO_PATH).format();
                pFinesPosteriores = aWriter.getPrefixedPath(FINES_POSTERIORES_PATH).format();
                pCausaLeitimacionCP = aWriter.getPrefixedPath(CAUSAS_DE_LEGITIMACIONCP_PATH).format();
                pRoles = aWriter.getPrefixedPath(ROLES_PATH).format();
                pDestinatarioTres = aWriter.getPrefixedPath(DESTINATARIO_TRES_I_PATH).format();
                pOperacionesSubI = aWriter.getPrefixedPath(OPEREACIONES_SUBI_PATH).format();
                pDestinatario4 = aWriter.getPrefixedPath(DESTINATARIO_CUATRO_PATH).format();
                pFinalidadIT = aWriter.getPrefixedPath(FANLIDAD_TI_PATH).format();
                pPrincipalesRiesgos = aWriter.getPrefixedPath(PRINCIPALES_RIESGOS_A_PATH).format();
                pAnalisisRiesgo = aWriter.getPrefixedPath(ANALISIS_RIESGO_PATH).format();
                pEvRiesgoImpc = aWriter.getPrefixedPath(EVRIESGO_R_ANALISIS_PATH).format();
                pPGAnalisisRiesgo = aWriter.getPrefixedPath(PG_RIESGO_ANALISIS_PATH).format();
                pPGRAnalisisDPIA = aWriter.getPrefixedPath(PGR_ANALISIS_IMPACDPIA_PATH).format();
                pHeredaResponsable = aWriter.getPrefixedPath(RESPONSABLE_HEREDA_PATH).format();
                pCorresponsable = aWriter.getPrefixedPath(CORRESPONSABLE_O_PATH).format();
                pEncargadoDecicion = aWriter.getPrefixedPath(ENCARGADO_DECICION_PATH).format();
                pSubEncagado = aWriter.getPrefixedPath(SUB_ENCARGADO_DE_PATH).format();
                pEncargadoDos = aWriter.getPrefixedPath(ENCARGADO_DE_DOS_PATH).format();
                pServContratado = aWriter.getPrefixedPath(SERVCONTRATAFO_PATH).format();
                pDestinatarioDos = aWriter.getPrefixedPath(DESTINATARIO_E_DOS_PATH).format();
                pOperEjecDos = aWriter.getPrefixedPath(OPERACIONEA_EJECUTA_DOS_PATH).format();
                pNombreConDos = aWriter.getPrefixedPath(NOM_CONTRATA_DOS_PATH).format();
                pFechaDos = aWriter.getPrefixedPath(FECHA_FORM_DOS_PATH).format();
                pVigDos = aWriter.getPrefixedPath(PERIODO_VIG_DOS_PATH).format();
                pEstatusDos = aWriter.getPrefixedPath(ESTATUSVIG_DOS_PATH).format();
                pSubDos = aWriter.getPrefixedPath(SUB_ENCAR_DOS_PATH).format();
                pServDos = aWriter.getPrefixedPath(SERVIC_CONTRATADO_DOS_PATH).format();
                pDestDos = aWriter.getPrefixedPath(DESTINATARIO_SUN_DOS_PATH).format();
                pOperEjecSubDos = aWriter.getPrefixedPath(OPER_EJEC_SUB_DOS_PATH).format();
                pNomDosContra = aWriter.getPrefixedPath(NOM_CON_SUB_DOS_PATH).format();
                pFechaDosA = aWriter.getPrefixedPath(FECH_FOR_SUM_DOS_PATH).format();
                pVigSubDos = aWriter.getPrefixedPath(PERIODO_SUB_DOS_PATH).format();
                pStatusVDos = aWriter.getPrefixedPath(ESTATUS_V_DOS_PATH).format();
                pEncargadoTres = aWriter.getPrefixedPath(ENCARGADO_TRES_O_PATH).format();
                pContraTres = aWriter.getPrefixedPath(CONTRATADO_TRES_PATH).format();
                pDestinatarioETres = aWriter.getPrefixedPath(DESTINATARIO_E_TRES_PATH).format();
                pOperacionesTres = aWriter.getPrefixedPath(OPERACIONES_TRES_PATH).format();
                pNomConTres = aWriter.getPrefixedPath(NOM_TRES_PATH).format();
                
                pFechaTres = aWriter.getPrefixedPath(FECHA_TRES_PATH).format();
                
                pPeriodoTresVig = aWriter.getPrefixedPath(PERIODO_TRES_PATH).format();
                pEstatusT = aWriter.getPrefixedPath(ESTATUS_TRES_PATH).format();
                pSubCuatro = aWriter.getPrefixedPath(SUB_TRES_D_PATH).format();
                pServCuatroList = aWriter.getPrefixedPath(SERVI_CON_CUATRO_PATH).format();
                pDestCuatro = aWriter.getPrefixedPath(DESTINATARIO_CUATROA_PATH).format();
                pOperEjecutaCuatro = aWriter.getPrefixedPath(OPER_EJECUA_PATH).format();
                pNombCCuatro = aWriter.getPrefixedPath(NOM_CON_CATRO_PATH).format();
                pFechaFprm = aWriter.getPrefixedPath(FECHA_CUATRO_PATH).format();
                pPeriodoVCuatro = aWriter.getPrefixedPath(PERIODO_VIG_CUATRO_PATH).format();
                pEstatusCuatro = aWriter.getPrefixedPath(ESTATUSV_PATH).format();
                pDestinatarioCP2 = aWriter.getPrefixedPath(DESTINATARIO_CP_PATH2).format();
                pFinalidadCuatroA = aWriter.getPrefixedPath(FINALIDADES_CP_PATH2).format();
                pCausaCP = aWriter.getPrefixedPath(CAUSA_CP_PATH).format();
                pDestinatarioCP3 = aWriter.getPrefixedPath(DESTINATARIO_5_PATH).format();
                pfinalidadesCP = aWriter.getPrefixedPath(FINALIDADES_CP_A_PATH).format();
                pCausaLeg = aWriter.getPrefixedPath(CAUSE_LEG_2_PATH).format();
                pDescripcionFT = aWriter.getPrefixedPath(DESCRIPCION_FT_PATH).format();
                pDescripcionFC = aWriter.getPrefixedPath(DESCRIPCIONFC_PATH).format();
                pNomFichaAnalisis = aWriter.getPrefixedPath(NOM_FICHA_ANALISIS_PATH).format();
                pDestinatario5F = aWriter.getPrefixedPath(DESTINATARIO_F5_PATH).format();
                pCausaLI = aWriter.getPrefixedPath(CAUSAL_L_PATH).format();
                pDestinatarioTT = aWriter.getPrefixedPath(DESTINATARIO_ITT_PATH).format();
                pFinalidad_ITT = aWriter.getPrefixedPath(FINALIDAD_ITT_PATH).format();
                pRoles6 = aWriter.getPrefixedPath(ROLES6_PATH).format();
                pOperEjecuta = aWriter.getPrefixedPath(OPERACIONES_N_PATH).format();
                pDestinatarioFCI = aWriter.getPrefixedPath(DESTINATARIOFCI_PATH).format();
                pCausasLegitimas = aWriter.getPrefixedPath(CAUSAS_LEGITIMAS_CDVI_PATH).format();
                pDestinatarioCVDT = aWriter.getPrefixedPath(DESTINATARIO_CVDITT_PATH).format();
                pFinalidadesCVDT = aWriter.getPrefixedPath(FINALIDADES_CVDT_PATH).format();
                pRoles12 = aWriter.getPrefixedPath(ROLES_CVD_12_PATH).format();
                pOperaEJIE = aWriter.getPrefixedPath(OPERAEJIE_PATH).format();
                pDestFase4 = aWriter.getPrefixedPath(DESFASE4_PATH).format();
                pDestFase5 = aWriter.getPrefixedPath(DESFASE5_PATH).format();
                pIdNombreFAB = aWriter.getPrefixedPath(ID_NOM_FAB_PATH).format();
                
                //Cuestionario
            	procedenciaPrefix = aWriter.getPrefixedPath(PROCEDENCIADPIA_PATH).format();
        		justificacionPrefix = aWriter.getPrefixedPath(JUSTIFICACIONDPIA_PATH).format();
                
                
                // se definen los widgets
                UIDynamicListWidget destinataroEWid = aWriter.newCustomWidget(DESTINATARIOE_PATH,new UIDynamicListWidgetFactory());
                destinataroEWid.setListId(DESTINATARIO_E);
                
                UIDynamicListWidget ServicioCEWid = aWriter.newCustomWidget(SERVICIOCENCARGADO_PATH,new UIDynamicListWidgetFactory());
                ServicioCEWid.setListId(SERVICIOE_ID);

                UIDynamicListWidget operacionesEjecutaWid = aWriter.newCustomWidget(OPERACIONES_EJECUTA_PATH,new UIDynamicListWidgetFactory());
                operacionesEjecutaWid.setListId(OPERACIONES_EJECUTA);

                UIDynamicListWidget nombreContratojecutaWid = aWriter.newCustomWidget(NOMBRE_CONTRATO_PATH,new UIDynamicListWidgetFactory());
                nombreContratojecutaWid.setListId(NOMBRE_CONTRATO);

                UIDynamicListWidget destinatarioSunaWid = aWriter.newCustomWidget(DESTINATARIO_SUN_PATH,new UIDynamicListWidgetFactory());
                destinatarioSunaWid.setListId(DESTINATARIO_SUN);

                UIDynamicListWidget operacionesEjeSubaWid = aWriter.newCustomWidget(OPERACIONES_EJECSUB_PATH,new UIDynamicListWidgetFactory());
                operacionesEjeSubaWid.setListId(OPERACIONES_EJECSUB);

                UIDynamicListWidget nomConSubaWid = aWriter.newCustomWidget(NOMBRE_CON_SUB_PATH,new UIDynamicListWidgetFactory());
                nomConSubaWid.setListId(NOM_CONTRATO_SUB);

                UIDynamicListWidget baseJuridiaWid = aWriter.newCustomWidget(BASE_JURIDICA_HERED_PATH,
                                new UIDynamicListWidgetFactory());
                baseJuridiaWid.setListId(BASE_JURIDICA);

                UIDynamicListWidget operEjecDPIAaWid = aWriter.newCustomWidget(OPERACIONES_EJECUTA_DPIA_PATH,
                                new UIDynamicListWidgetFactory());
                operEjecDPIAaWid.setListId(OPERACIONES_EJECUTA_DPIA);

                UIDynamicListWidget responsableFabaWid = aWriter.newCustomWidget(RESPONSABLE_FAB_PATH,
                                new UIDynamicListWidgetFactory());
                responsableFabaWid.setListId(RESPONSABLE_FAB);

                UIDynamicListWidget corresponsableWid = aWriter.newCustomWidget(CORRESPONSABLE_FAB_PATH,
                                new UIDynamicListWidgetFactory());
                corresponsableWid.setListId(CORRESPONSABLE_FAB);

                UIDynamicListWidget funcioneDosaWid = aWriter.newCustomWidget(FUNCIONES_DOS_PATH,
                                new UIDynamicListWidgetFactory());
                funcioneDosaWid.setListId(FUNCIONES_DOS_FAB);

                UIDynamicListWidget destinatarioIaWid = aWriter.newCustomWidget(DESTINATARIO_I_PATH,
                                new UIDynamicListWidgetFactory());
                destinatarioIaWid.setListId(DESTINATARIO_I);

                UIDynamicListWidget fianlidadCaWid = aWriter.newCustomWidget(FINALIDAD_C_PATH,
                                new UIDynamicListWidgetFactory());
                fianlidadCaWid.setListId(FINALIDAD_C);

                UIDynamicListWidget destinatarioDoIaWid = aWriter.newCustomWidget(DESTINATARIO_DOS_I_PATH,
                                new UIDynamicListWidgetFactory());
                destinatarioDoIaWid.setListId(DESTINATARIO_DOS_I);

                UIDynamicListWidget operacionesDosIaWid = aWriter.newCustomWidget(OPERACIONES_DOS_I_PATH,
                                new UIDynamicListWidgetFactory());
                operacionesDosIaWid.setListId(OPERACIONES_DOS_I);

                UIDynamicListWidget destinatarioCPaWid = aWriter.newCustomWidget(DESTINATARIO_CP_PATH,
                                new UIDynamicListWidgetFactory());
                destinatarioCPaWid.setListId(DESTINATARIO_CP);

                UIDynamicListWidget finalidadCPCPaWid = aWriter.newCustomWidget(FINALIDADES_CP_PATH,
                                new UIDynamicListWidgetFactory());
                finalidadCPCPaWid.setListId(FINALIDAD_CP);

                UIDynamicListWidget opeejeaWid = aWriter.newCustomWidget(OPEEJE4_PATH,
                                new UIDynamicListWidgetFactory());
                opeejeaWid.setListId(OPEEJE4);

                UIDynamicListWidget heredaJuicioaWid = aWriter.newCustomWidget(HEREDAR_JUICIO_PATH,
                                new UIDynamicListWidgetFactory());
                heredaJuicioaWid.setListId(HEREDAR_JUICIO);

                UIDynamicListWidget finPrincipalaWid = aWriter.newCustomWidget(FIN_PRINCIPAL_UNO_PATH,
                                new UIDynamicListWidgetFactory());
                finPrincipalaWid.setListId(FIN_PRINCIPAL);

                UIDynamicListWidget finSecundarioaWid = aWriter.newCustomWidget(FIN_SECUNDARIO_PATH,
                                new UIDynamicListWidgetFactory());
                finSecundarioaWid.setListId(FIN_SECUNDARIO);

                UIDynamicListWidget finesPosterioresWid = aWriter.newCustomWidget(FINES_POSTERIORES_PATH,
                                new UIDynamicListWidgetFactory());
                finesPosterioresWid.setListId(FINES_POSTERIORES);

                UIDynamicListWidget causaLegitimacionCPWid = aWriter.newCustomWidget(CAUSAS_DE_LEGITIMACIONCP_PATH,
                                new UIDynamicListWidgetFactory());
                causaLegitimacionCPWid.setListId(CAUSA_DE_LEGITIMACION_CP);

                UIDynamicListWidget rolesWid = aWriter.newCustomWidget(ROLES_PATH,
                                new UIDynamicListWidgetFactory());
                rolesWid.setListId(ROLES_LIST);

                UIDynamicListWidget destinatarioTresWid = aWriter.newCustomWidget(DESTINATARIO_TRES_I_PATH,
                                new UIDynamicListWidgetFactory());
                destinatarioTresWid.setListId(DESTINATARIO_TRES_I);

                UIDynamicListWidget operacionesSubWid = aWriter.newCustomWidget(OPEREACIONES_SUBI_PATH,
                                new UIDynamicListWidgetFactory());
                operacionesSubWid.setListId(OPERACIONES_SUB_I);

                UIDynamicListWidget destinatarioCuatroWid = aWriter.newCustomWidget(DESTINATARIO_CUATRO_PATH,
                                new UIDynamicListWidgetFactory());
                destinatarioCuatroWid.setListId(DESTINATARIO_CUATRO);

                UIDynamicListWidget finalidadITWid = aWriter.newCustomWidget(FANLIDAD_TI_PATH,
                                new UIDynamicListWidgetFactory());
                finalidadITWid.setListId(FINALIDAD_IT);

                UIDynamicListWidget principalesRaWid = aWriter.newCustomWidget(PRINCIPALES_RIESGOS_A_PATH,
                                new UIDynamicListWidgetFactory());
                principalesRaWid.setListId(PRINCIPALES_RIESGOS);

                UIDynamicListWidget analisisRiesgioaWid = aWriter.newCustomWidget(ANALISIS_RIESGO_PATH,
                                new UIDynamicListWidgetFactory());
                analisisRiesgioaWid.setListId(ANALISIS_RIESGOS);

                UIDynamicListWidget eVRiegoaWid = aWriter.newCustomWidget(EVRIESGO_R_ANALISIS_PATH,
                                new UIDynamicListWidgetFactory());
                eVRiegoaWid.setListId(EVANALISIS_RIESGO_IMP);

                UIDynamicListWidget pgAnalisisaWid = aWriter.newCustomWidget(PG_RIESGO_ANALISIS_PATH,
                                new UIDynamicListWidgetFactory());
                pgAnalisisaWid.setListId(PG_RIESGO_ANALISIS_R);

                UIDynamicListWidget pgrAnalisisDPIaWid = aWriter.newCustomWidget(PGR_ANALISIS_IMPACDPIA_PATH,
                                new UIDynamicListWidgetFactory());
                pgrAnalisisDPIaWid.setListId(PGR_ANALISIS_IMPACDIP);

                UIDynamicListWidget heredaResponsableaWid = aWriter.newCustomWidget(RESPONSABLE_HEREDA_PATH,
                                new UIDynamicListWidgetFactory());
                heredaResponsableaWid.setListId(RESPONSABLE_HEREDA);

                UIDynamicListWidget corresponsableHaWid = aWriter.newCustomWidget(CORRESPONSABLE_O_PATH,
                                new UIDynamicListWidgetFactory());
                corresponsableHaWid.setListId(CORRESPONSABLE_HEREDA);

                UIDynamicListWidget serviConaWid = aWriter.newCustomWidget(SERVCONTRATAFO_PATH,
                                new UIDynamicListWidgetFactory());
                serviConaWid.setListId(SERVICIO_CONTRATADO);

                UIDynamicListWidget DestinatarioDosaWid = aWriter.newCustomWidget(DESTINATARIO_E_DOS_PATH,
                                new UIDynamicListWidgetFactory());
                DestinatarioDosaWid.setListId(DESTINATARIO_DOS);

                UIDynamicListWidget OperEjecaWid = aWriter.newCustomWidget(OPERACIONEA_EJECUTA_DOS_PATH,
                                new UIDynamicListWidgetFactory());
                OperEjecaWid.setListId(OPERACIONES_EJECUTA_DOS);

                UIDynamicListWidget nomContDosaWid = aWriter.newCustomWidget(NOM_CONTRATA_DOS_PATH,
                                new UIDynamicListWidgetFactory());
                nomContDosaWid.setListId(NOM_CONT_DOS);

               

                UIDynamicListWidget causaLegWid = aWriter.newCustomWidget(CAUSE_LEG_2_PATH,
                                new UIDynamicListWidgetFactory());
                causaLegWid.setListId(CAUSA_LEG);

   

                UIDynamicListWidget servConDosaWid = aWriter.newCustomWidget(SERVIC_CONTRATADO_DOS_PATH,
                                new UIDynamicListWidgetFactory());
                servConDosaWid.setListId(SERV_CON_DOS);

                UIDynamicListWidget destDosaWid = aWriter.newCustomWidget(DESTINATARIO_SUN_DOS_PATH,
                                new UIDynamicListWidgetFactory());
                destDosaWid.setListId(DEST_SUN_DOS);

                UIDynamicListWidget operSubEjecDosaWid = aWriter.newCustomWidget(OPER_EJEC_SUB_DOS_PATH,
                                new UIDynamicListWidgetFactory());
                operSubEjecDosaWid.setListId(OPER_EJEC_SUB_DOS);

                UIDynamicListWidget nomConDosaWid = aWriter.newCustomWidget(NOM_CON_SUB_DOS_PATH,
                                new UIDynamicListWidgetFactory());
                nomConDosaWid.setListId(NOM_DOS_CON);

                UIDynamicListWidget contratadoTresaWid = aWriter.newCustomWidget(CONTRATADO_TRES_PATH,
                                new UIDynamicListWidgetFactory());
                contratadoTresaWid.setListId(CONTRATADO_TRES);

                UIDynamicListWidget destinatarioTresAWid = aWriter.newCustomWidget(DESTINATARIO_E_TRES_PATH,
                                new UIDynamicListWidgetFactory());
                destinatarioTresAWid.setListId(DESTINATARIO__3);

                UIDynamicListWidget operEjecutaTresAWid = aWriter.newCustomWidget(OPERACIONES_TRES_PATH,
                                new UIDynamicListWidgetFactory());
                operEjecutaTresAWid.setListId(OPERACIONES_TRES);

                UIDynamicListWidget nomConTresAWid = aWriter.newCustomWidget(NOM_TRES_PATH,
                                new UIDynamicListWidgetFactory());
                nomConTresAWid.setListId(nomConTraTres);

                UIDynamicListWidget SCuatroAWid = aWriter.newCustomWidget(SERVI_CON_CUATRO_PATH,
                                new UIDynamicListWidgetFactory());
                SCuatroAWid.setListId(SERVICOCUATRO);

                UIDynamicListWidget DCuatroAWid = aWriter.newCustomWidget(DESTINATARIO_CUATROA_PATH,
                                new UIDynamicListWidgetFactory());
                DCuatroAWid.setListId(DEST_SUN);

                UIDynamicListWidget operEjecutaAWid = aWriter.newCustomWidget(OPER_EJECUA_PATH,
                                new UIDynamicListWidgetFactory());
                operEjecutaAWid.setListId(OPER_CUATRO);

                UIDynamicListWidget nomConCuatroWid = aWriter.newCustomWidget(NOM_CON_CATRO_PATH,
                                new UIDynamicListWidgetFactory());
                nomConCuatroWid.setListId(NOM_CON_CUA);

                UIDynamicListWidget destCuatroWid = aWriter.newCustomWidget(DESTINATARIO_CP_PATH2,
                                new UIDynamicListWidgetFactory());
                destCuatroWid.setListId(DESTINATARIO_CP_2);

                UIDynamicListWidget finalidadCWid = aWriter.newCustomWidget(FINALIDADES_CP_PATH2,
                                new UIDynamicListWidgetFactory());
                finalidadCWid.setListId(FINALIDADES_ACP);

                UIDynamicListWidget causaCPWid = aWriter.newCustomWidget(CAUSA_CP_PATH,
                                new UIDynamicListWidgetFactory());
                causaCPWid.setListId(CAUSA_CP);

                UIDynamicListWidget DestCP5Wid = aWriter.newCustomWidget(DESTINATARIO_5_PATH,
                                new UIDynamicListWidgetFactory());
                DestCP5Wid.setListId(DESTINATARIOCP);

                UIDynamicListWidget finalidadCPWid = aWriter.newCustomWidget(FINALIDADES_CP_A_PATH,
                                new UIDynamicListWidgetFactory());
                finalidadCPWid.setListId(FINALIDADCP);

                UIDynamicListWidget descripcionFTWid = aWriter.newCustomWidget(DESCRIPCION_FT_PATH,
                                new UIDynamicListWidgetFactory());
                descripcionFTWid.setListId(DESCRIPTIONFT);

                UIDynamicListWidget descripcionFCWid = aWriter.newCustomWidget(DESCRIPCIONFC_PATH,
                                new UIDynamicListWidgetFactory());
                descripcionFCWid.setListId(DESCFC);

                UIDynamicListWidget destinatario5FIWid = aWriter.newCustomWidget(DESTINATARIO_F5_PATH,
                                new UIDynamicListWidgetFactory());
                destinatario5FIWid.setListId(DESTINATARIO_5IF);

                UIDynamicListWidget causaLIWid = aWriter.newCustomWidget(CAUSAL_L_PATH,
                                new UIDynamicListWidgetFactory());
                causaLIWid.setListId(CAUSAL_I);

                UIDynamicListWidget destinatarioITTWid = aWriter.newCustomWidget(DESTINATARIO_ITT_PATH,
                                new UIDynamicListWidgetFactory());
                destinatarioITTWid.setListId(DESTINATARIO_ITT);

                UIDynamicListWidget finalidadITTWid = aWriter.newCustomWidget(FINALIDAD_ITT_PATH,
                                new UIDynamicListWidgetFactory());
                finalidadITTWid.setListId(FINALIDAD_ITT);

                UIDynamicListWidget roles6Wid = aWriter.newCustomWidget(ROLES6_PATH,
                                new UIDynamicListWidgetFactory());
                roles6Wid.setListId(ROLES_6);

                UIDynamicListWidget operacionesJelNWid = aWriter.newCustomWidget(OPERACIONES_N_PATH,
                                new UIDynamicListWidgetFactory());
                roles6Wid.setListId(OPERACIONES_EJEC);

                UIDynamicListWidget destinatarioFCIWid = aWriter.newCustomWidget(DESTINATARIOFCI_PATH,
                                new UIDynamicListWidgetFactory());
                destinatarioFCIWid.setListId(DESTINATARIO_FCI);

                UIDynamicListWidget causasLegitimasWid = aWriter.newCustomWidget(CAUSAS_LEGITIMAS_CDVI_PATH,
                                new UIDynamicListWidgetFactory());
                causasLegitimasWid.setListId(CAUSAS_LEGITIMAS);

                UIDynamicListWidget destinatarioCDVTWid = aWriter.newCustomWidget(DESTINATARIO_CVDITT_PATH,
                                new UIDynamicListWidgetFactory());
                destinatarioCDVTWid.setListId(DESTINATARIO_CDVDITT);

                UIDynamicListWidget finalidadCDVTWid = aWriter.newCustomWidget(FINALIDADES_CVDT_PATH,
                                new UIDynamicListWidgetFactory());
                finalidadCDVTWid.setListId(FINALIDADES_CDVDITT);

                UIDynamicListWidget Roles12Wid = aWriter.newCustomWidget(ROLES_CVD_12_PATH,
                                new UIDynamicListWidgetFactory());
                Roles12Wid.setListId(ROLES12);

                UIDynamicListWidget operaijeWid = aWriter.newCustomWidget(OPERAEJIE_PATH,
                                new UIDynamicListWidgetFactory());
                operaijeWid.setListId(OPERAJIE);

                UIDynamicListWidget FASE4Wid = aWriter.newCustomWidget(DESFASE4_PATH,
                                new UIDynamicListWidgetFactory());
                FASE4Wid.setListId(DESFASE4);

                UIDynamicListWidget fase5Wid = aWriter.newCustomWidget(DESFASE5_PATH,
                                new UIDynamicListWidgetFactory());
                fase5Wid.setListId(DESFASE5);

           

                // Datos básicos del tratamiento
                aWriter.add_cr("<h3>Resumen ejecutivo</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos b&aacute;sicos del tratamiento:"), true);
                aWriter.add_cr("<h3>Datos b&aacute;sicos del tratamiento:</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
                aWriter.addFormRow(Path.parse("Identificador"));
                aWriter.addFormRow(Path.parse("Resumen"));
                aWriter.addFormRow(Path.parse("vigente_B"));
                aWriter.addFormRow(Path.parse("Fecha_creacion"));

                
                UIComboBox cbxNombreT = aWriter.newComboBox(NOMBRE_TRATAMIENTO_PATH);
                cbxNombreT.setActionOnAfterValueChanged(JsFunctionCall.on("updateInheritedFields"));
                aWriter.addFormRow(cbxNombreT);
                

                UIComboBox cbxIdAnalisisImpacto = aWriter.newComboBox(NOMBRE_ANALISIS_IMPACTO_PATH);
                cbxIdAnalisisImpacto.setActionOnAfterValueChanged(JsFunctionCall.on("updateInheritedFieldsDos"));
                aWriter.addFormRow(cbxIdAnalisisImpacto);
                
                UIComboBox cbxIdentificadorARInfo = aWriter.newComboBox(IDENTIFICADOR_AR_INFORMACION_PATH);
                cbxIdentificadorARInfo.setActionOnAfterValueChanged(JsFunctionCall.on("updateInheritedFieldsTres"));
                aWriter.addFormRow(heredaResponsableaWid);
                aWriter.addFormRow(corresponsableHaWid);

                
              //  aWriter.add("<div style='margin-left: 34em;'>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Encargados ficha de análisis básico"), true);               
                startComplexGroup(aPaneContext, aWriter, ENCARGADO_DECICION_PATH,"block_encargado_decicion");
               // aWriter.addFormRow(ServicioCEWid);
                aWriter.addFormRow(destinataroEWid);
                aWriter.addFormRow(operacionesEjecutaWid);
                aWriter.addFormRow(nombreContratojecutaWid);
                aWriter.addFormRow(FECHA_PATH);
                aWriter.addFormRow(PERIODO_VIGENCIAE_PATH);
                aWriter.addFormRow(ESTATUS_VIGENCIAE_PATH);
                endComplexGroup(aWriter);
                aWriter.endExpandCollapseBlock();

                
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Sub encargados ficha de análisis básico"),true);
                startComplexGroup(aPaneContext, aWriter, SUB_ENCARGADO_DE_PATH,"block_sub_encargado_decicion");

               // aWriter.addFormRow(Path.parse("Heredar_datos/heredar_de_la_ficha_de_analisis_basico/Sub_encargadoAB/Servicio_contratadoSub"));

                aWriter.addFormRow(destinatarioSunaWid);
                aWriter.addFormRow(operacionesEjeSubaWid);
                aWriter.addFormRow(nomConSubaWid);
                aWriter.addFormRow(FECHA_FORM_PATH);               
                aWriter.addFormRow(PERIODO_VIG_SUB_PATCH);
                aWriter.addFormRow(ESTATUS_V_SUB_PATH);
                endComplexGroup(aWriter);
                aWriter.endExpandCollapseBlock();

                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Encargado ficha nivel fases"), true);

                startComplexGroup(aPaneContext, aWriter, ENCARGADO_DE_DOS_PATH,"block_sub_encargado_decicionFF");

                aWriter.addFormRow(serviConaWid);
                aWriter.addFormRow(DestinatarioDosaWid);
                aWriter.addFormRow(OperEjecaWid);

                aWriter.addFormRow(nomContDosaWid);

                aWriter.addFormRow(FECHA_FORM_DOS_PATH);
                aWriter.addFormRow(PERIODO_VIG_DOS_PATH);
                aWriter.addFormRow(ESTATUSVIG_DOS_PATH);
                endComplexGroup(aWriter);
                aWriter.endExpandCollapseBlock();
                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Sub encargado ficha nivel fases"), true);

                startComplexGroup(aPaneContext, aWriter, SUB_ENCAR_DOS_PATH,"block_sub_encargado_decicioFFnn");
                aWriter.addFormRow(servConDosaWid);
                aWriter.addFormRow(destDosaWid);
                aWriter.addFormRow(operSubEjecDosaWid);
                aWriter.addFormRow(nomConDosaWid);
                
               
                
                aWriter.addFormRow(FECH_FOR_SUM_DOS_PATH);
                aWriter.addFormRow(PERIODO_SUB_DOS_PATH);
                aWriter.addFormRow(ESTATUS_V_DOS_PATH);
                endComplexGroup(aWriter);
                aWriter.endExpandCollapseBlock();

                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Encargado ficha ciclo de vida del dato"),true);

                startComplexGroup(aPaneContext, aWriter, ENCARGADO_TRES_O_PATH,"block_encargado_decicionFCVD2");

                aWriter.addFormRow(contratadoTresaWid);
                aWriter.addFormRow(destinatarioTresAWid);

                aWriter.addFormRow(operEjecutaTresAWid);

                aWriter.addFormRow(nomConTresAWid);
                
                
                
                
                
                
                
                
                aWriter.addFormRow(FECHA_TRES_PATH);

                
                
                
                
                
                
                
                
                aWriter.addFormRow(PERIODO_TRES_PATH);

                aWriter.addFormRow(ESTATUS_TRES_PATH);

                endComplexGroup(aWriter);
                aWriter.endExpandCollapseBlock();
                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Sub encargado ficha ciclo de vida del dato"),true);
                startComplexGroup(aPaneContext, aWriter, SUB_TRES_D_PATH,"block_sub_encargado_decicionFCVD");

                aWriter.addFormRow(SCuatroAWid);
                aWriter.addFormRow(DCuatroAWid);
                aWriter.addFormRow(operEjecutaAWid);
                aWriter.addFormRow(nomConCuatroWid);
                
                
                
                            
                aWriter.addFormRow(FECHA_CUATRO_PATH);
                
                
                
                
                
                aWriter.addFormRow(PERIODO_VIG_CUATRO_PATH);
                aWriter.addFormRow(ESTATUSV_PATH);
                endComplexGroup(aWriter);
                aWriter.endExpandCollapseBlock();

               // aWriter.add("</div>");

                aWriter.endExpandCollapseBlock();
                
                // Datos básicos del tratamiento -Base Juridica
                aWriter.add_cr("<h3>Base Juridica</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
                // aWriter.addFormRow(Path.parse("Base_juridica/JuicioHereda"));
                aWriter.addFormRow(baseJuridiaWid);
                aWriter.addFormRow(Path.parse("Base_juridica/Base_juridica_menu"));
                aWriter.endExpandCollapseBlock();

                // Datos básicos del tratamiento -Por más información ver Ficha
                aWriter.add_cr("<h3>Por más información ver Ficha</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
                // aWriter.addFormRow(Path.parse("Informacion/idnombrefab"));
                aWriter.addFormRow(ID_NOMB_FAB_PATH);
                aWriter.addFormRow(Path.parse("Informacion/idnombrefaf"));
                aWriter.addFormRow(Path.parse("Informacion/idnombrefacd"));
                // aWriter.addFormRow(Path.parse("Informacion/IdentificadorAR"));
                aWriter.addFormRow(cbxIdentificadorARInfo);
                aWriter.addFormRow(Path.parse("Informacion/identificadorC"));
                aWriter.addFormRow(Path.parse("Informacion/IdentificadorAIP"));
                aWriter.endExpandCollapseBlock();

                // Datos básicos del tratamiento -Cesiones previstas
                aWriter.add_cr("<h3>Cesiones previstas</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
                startComplexGroup(aPaneContext, aWriter, Path.parse("Cesiones_previstas"), "block_cesiones_previstas");
                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Cesiones previstas ficha de análisis básico"),true);
                aWriter.addFormRow(destinatarioCPaWid);
                aWriter.addFormRow(finalidadCPCPaWid);
                aWriter.addFormRow(causaLegitimacionCPWid);
                aWriter.endExpandCollapseBlock();
               
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Cesiones previstas ficha de análisis de fase"),true);
                aWriter.addFormRow(DESTINATARIO_CP_PATH2);
                aWriter.addFormRow(finalidadCWid);
                aWriter.addFormRow(causaCPWid);
                aWriter.endExpandCollapseBlock();
               
                
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Cesiones previstas ficha de ciclo de vida del dato"), true);
                aWriter.addFormRow(DestCP5Wid);
                aWriter.addFormRow(finalidadCPWid);
                aWriter.addFormRow(causaLegWid);
                aWriter.endExpandCollapseBlock();
                endComplexGroup(aWriter);
               
                aWriter.endExpandCollapseBlock();
                // Datos básicos del tratamiento -Extensión y límites del DPIA
                aWriter.add_cr("<h3>Extensión y límites del DPIA</h3>");
             
                aWriter.addFormRow(operEjecDPIAaWid);
                aWriter.addFormRow(descripcionFTWid);
                aWriter.addFormRow(descripcionFCWid);
                // Datos básicos del tratamiento - Principales riesgos identificados y sus
                // correspondientes medidas de mitigación
                aWriter.add_cr("<h3>Principales riesgos identificados y sus correspondientes medidas de mitigación</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
                aWriter.addFormRow(principalesRaWid);
                aWriter.endExpandCollapseBlock();
                // Datos básicos del tratamiento
                // tratamiento
                aWriter.add_cr("<h3>Conclusión respecto al riesgo residual del tratamiento</h3>");
              
                aWriter.addFormRow(Path.parse("Concluoargu"));
                aWriter.endExpandCollapseBlock();
                ///

                // Datos básicos
                aWriter.add_cr("<h3>Datos básicos</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Datos básicos"), true);
                aWriter.add_cr("<h3>Nombre del tratamiento</h3>");
                aWriter.addFormRow(NOM_FICHA_ANALISIS_PATH);
                aWriter.add_cr("<h3>Identificación del Responsable</h3>");

                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Responsable y corresponsable ficha de análisis básico"),true);
                aWriter.addFormRow(responsableFabaWid);
                
                aWriter.addFormRow(corresponsableWid);
                aWriter.endExpandCollapseBlock();
                //
                //
                aWriter.startExpandCollapseBlock(
                                UserMessage.createInfo("Roles y funciones ficha de análisis básico"), true);
                aWriter.addFormRow(rolesWid);               
                aWriter.addFormRow(funcioneDosaWid);
                aWriter.endExpandCollapseBlock();
                //
                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Destinatarios y finalidades de cesiones de datos personales realizadas ficha de análisis básico"),true);
                aWriter.addFormRow(destinatarioIaWid);
                aWriter.addFormRow(fianlidadCaWid);
                aWriter.endExpandCollapseBlock();
                //

                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Destinatarios y operaciones que ejecuta de comunicaciones con encargados realizadas ficha de análisis básico"),true);
                aWriter.addFormRow(destinatarioDoIaWid);
                aWriter.addFormRow(operacionesDosIaWid);
                aWriter.endExpandCollapseBlock();
                //

                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Destinatarios y operaciones que ejecuta de comunicaciones con sub encargados realizadas ficha de análisis básico"),true);
                aWriter.addFormRow(destinatarioTresWid);              
                aWriter.addFormRow(operacionesSubWid);                
                aWriter.endExpandCollapseBlock();
                //

                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Destinatarios y finalidades de transferencias de datos personales realizadas ficha de análisis básico"), true);
                aWriter.addFormRow(destinatarioCuatroWid);               
                aWriter.addFormRow(finalidadITWid);               
                aWriter.endExpandCollapseBlock();
                //
                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Destinatarios y causa de legitimación de cesiones de datos personales realizadas ficha de análisis de fases"),true);
                aWriter.addFormRow(destinatario5FIWid);
                aWriter.addFormRow(causaLIWid);
                aWriter.endExpandCollapseBlock();
                //
                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Destinatarios y causa de legitimación de cesiones de datos personales realizadas ficha de análisis de fases"),true);
                aWriter.addFormRow(destinatarioITTWid);
                aWriter.addFormRow(finalidadITTWid);
                aWriter.endExpandCollapseBlock();
                //
                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Roles y operaciones que ejecuta ficha de análisis de fases ficha de análisis de fases"),true);
                aWriter.addFormRow(roles6Wid);
                aWriter.addFormRow(operacionesJelNWid);
                aWriter.endExpandCollapseBlock();
                //
                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Destinatarios y causa de legitimación de cesiones de datos personales realizadas ficha ciclo de vida del dato"),true);
                aWriter.addFormRow(destinatarioFCIWid);
                aWriter.addFormRow(causasLegitimasWid);
                aWriter.endExpandCollapseBlock();
                //
                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Destinatarios y finalidades de transferencias de datos personales realizadas ficha ciclo de vida del dato"),true);
                aWriter.addFormRow(destinatarioCDVTWid);
                aWriter.addFormRow(finalidadCDVTWid);
                aWriter.endExpandCollapseBlock();
                //
                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Roles y operaciones que ejecuta ficha ciclo de vida del dato"),true);
                aWriter.addFormRow(Roles12Wid);
                aWriter.addFormRow(operaijeWid);
                aWriter.endExpandCollapseBlock();
                //
                aWriter.add_cr("<h3>Fecha prevista de inicio del tratamiento</h3>");
                aWriter.addFormRow(Path.parse("DatosBasicos/Fecha_prevista_inicio_tratamiento"));
                startComplexGroup(aPaneContext, aWriter,Path.parse("DatosBasicos/Justificacion_objetivamente_situacion"),"block_sub_Justificacion_objetivamente_situacion");
                aWriter.addFormRow(Path.parse("DatosBasicos/Justificacion"));
                endComplexGroup(aWriter);
/////////
                /*a qui es
                
                Si 
                no
                no aplica
                
                */
                aWriter.add_cr("<h3>Fecha de fin del tratamiento o condiciones de caducidad</h3>");                
                Path FechaPath = Path.parse("DatosBasicos/FechapreveFin");
        		UIDropDownList fechaDropDown = aWriter.newDropDownList(FechaPath);
        		fechaDropDown.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockCargo"));
        		aWriter.addFormRow(fechaDropDown);
        		String value1 = (String) aPaneContext.getValueContext(OBJECT_KEY, FechaPath).getValue();		
        		startBlock(aWriter, "block_si", "si".equalsIgnoreCase(value1));	
        		aWriter.addFormRow(Path.parse("DatosBasicos/FechaFin"));
        		endBlock(aWriter);		
        		
                
        		 // Funcion que ocupan los campos String que tiene valores definidos y dependindo la selecion muestra los campos (Cargo)
        	    aWriter.addJS_cr("function displayBlockCargo(buttonValue){");
        	    aWriter.addJS_cr("const blockAceptable = document.getElementById('block_si');");        	 
        	    aWriter.addJS_cr("if (buttonValue == 'Si'){");
        	    aWriter.addJS_cr("blockAceptable.style.display = 'block';");        	
        	    aWriter.addJS_cr("}");       
        	    aWriter.addJS_cr("else {");
        	    aWriter.addJS_cr("blockAceptable.style.display = 'none';");        	
        	    aWriter.addJS_cr("}");
        	    aWriter.addJS_cr("}");
                /*
                        
                
                
                
                
                */
                
                aWriter.endExpandCollapseBlock();
                //
                // Aspectos metodológicos DPIA
                aWriter.add_cr("<h3>Aspectos metodológicos DPIA</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Aspectos metodológicos DPIA"), true);
                aWriter.addFormRow(Path.parse("Aspectos_Metodologicos_DPIA/Persona"));
                aWriter.addFormRow(Path.parse("Aspectos_Metodologicos_DPIA/RolDPIA"));
                aWriter.addFormRow(Path.parse("Aspectos_Metodologicos_DPIA/Tareas"));
                aWriter.addFormRow(Path.parse("Aspectos_Metodologicos_DPIA/Responsabilidades"));

                startComplexGroup(aPaneContext, aWriter, Path.parse("Aspectos_Metodologicos_DPIA/opinion_I"),"block_sub_opinion_I");
                aWriter.addFormRow(Path.parse("Aspectos_Metodologicos_DPIA/Describeproceso"));
                endComplexGroup(aWriter);

                aWriter.addFormRow(Path.parse("Aspectos_Metodologicos_DPIA/Guia_herramientasMN"));
               // aWriter.add("<div style='margin-left: 21em;'>");
                aWriter.add_cr("<h3>Extensión y límites del DPIA: Identificar que ha quedado fuera de la evaluación</h3>");
                //aWriter.add("</div>");
                // aWriter.addFormRow(Path.parse("Aspectos_Metodologicos_DPIA/ExtencionesLDPIA/OPEEJE4"));
                aWriter.addFormRow(opeejeaWid);
                aWriter.addFormRow(FASE4Wid);
                aWriter.addFormRow(fase5Wid);
                aWriter.addFormRow(Path.parse("Aspectos_Metodologicos_DPIA/Fecharealizacioninforme"));
                aWriter.endExpandCollapseBlock();
                ///

                // Análisis de bases jurídicas del tratamiento y cumplimiento normativo
                aWriter.add_cr("<h3>Análisis de bases jurídicas del tratamiento y cumplimiento normativo</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Análisis de bases jurídicas del tratamiento y cumplimiento normativo"),true);
                aWriter.addFormRow(heredaJuicioaWid);
                // aWriter.addFormRow(Path.parse("Analisis_de_Bases_Juridicas/Heredar_juicio"));
                aWriter.addFormRow(Path.parse("Analisis_de_Bases_Juridicas/Basejuridica6"));
                aWriter.endExpandCollapseBlock();
                // Descripción del tratamiento
                aWriter.add_cr("<h3>Descripción del tratamiento</h3>");
                aWriter.startExpandCollapseBlock(
                                UserMessage.createInfo("Descripción del tratamiento"), true);
                aWriter.addFormRow(finPrincipalaWid);
                // aWriter.addFormRow(Path.parse("Descripcion_tratamiento/Finprincipal1"));
                aWriter.addFormRow(finSecundarioaWid);
                // aWriter.addFormRow(Path.parse("Descripcion_tratamiento/Finsecundario2"));
                aWriter.addFormRow(finesPosterioresWid);
                // aWriter.addFormRow(Path.parse("Descripcion_tratamiento/Finesposteriores"));
                aWriter.addFormRow(Path.parse("Descripcion_tratamiento/id-nombre3"));
                aWriter.addFormRow(Path.parse("Descripcion_tratamiento/idFase"));
                aWriter.addFormRow(Path.parse("Descripcion_tratamiento/idCiclo"));
                aWriter.endExpandCollapseBlock();
                //

                // Análisis de la obligación y necesidad de realizar un DPIA
                aWriter.add_cr("<h3>Análisis de la obligación y necesidad de realizar un DPIA</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Análisis de la obligación y necesidad de realizar un DPIA"),true);
                
                UIComboBox nomcues = aWriter.newComboBox(NOMBRE_CUESTIONARIODPIA_PATH);
        		nomcues.setActionOnAfterValueChanged(JsFunctionCall.on("updateInheritedFieldsdpia"));	
        		aWriter.addFormRow(nomcues);	
        		aWriter.addFormRow(PROCEDENCIADPIA_PATH);
        		aWriter.addFormRow(JUSTIFICACIONDPIA_PATH);	
        		
        		startBooleanBlock(aPaneContext, aWriter, Path.parse("Analisis_obligacion/Consideraciones_adicionales3"), "block_cinsiadicionales");
        		aWriter.addFormRow(Path.parse("Analisis_obligacion/Conside6"));
        		endBlock(aWriter);
                
                aWriter.endExpandCollapseBlock();
                //

          
        		
        		
        	
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                // Identificación y análisis de los riesgos de privacidad
                aWriter.add_cr("<h3>Identificación y análisis de los riesgos de privacidad</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Identificación y análisis de los riesgos de privacidad"),true);
                aWriter.addFormRow(Path.parse("Identificacionyanalisisderiesgo/ObjetivosP"));
                // aWriter.addFormRow(Path.parse("IdAR"));
                aWriter.addFormRow(analisisRiesgioaWid);
                aWriter.addFormRow(eVRiegoaWid);
                // aWriter.addFormRow(Path.parse("Evaluacion_del_riesgo_residual"));
                // aWriter.addFormRow(Path.parse("Riesgo10"));
                // aWriter.addFormRow(Path.parse("Expore10"));
                // aWriter.addFormRow(Path.parse("Class10"));

                aWriter.endExpandCollapseBlock();
                //

                // Controles para migrar el riesgo
                aWriter.add_cr("<h3>Controles para migrar el riesgo</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Controles para migrar el riesgo"), true);
                aWriter.addFormRow(Path.parse("Medidas_consepto_tratamiento"));
                aWriter.addFormRow(Path.parse("Medidasgobernanza"));
                aWriter.addFormRow(Path.parse("Medidas_proteccion_datos_desde_diseno"));
                aWriter.addFormRow(Path.parse("Medidas_seguridad"));
                // Controles para migrar el riesgo -Plan de acciona de la gestión de riesgo
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Plan de acciona de la gestión de riesgo"), true);
                aWriter.addFormRow(pgAnalisisaWid);
                aWriter.addFormRow(pgrAnalisisDPIaWid);
                // aWriter.addFormRow(Path.parse("Plan_gestion_riesgos/EstatusI"));
                // aWriter.addFormRow(Path.parse("Plan_gestion_riesgos/AccionC2"));
                // aWriter.addFormRow(Path.parse("Plan_gestion_riesgos/Responsable9"));
                // aWriter.addFormRow(Path.parse("Plan_gestion_riesgos/Estado9"));
                aWriter.endExpandCollapseBlock();
                //
                // Controles para migrar el riesgo -Plan de acciona de la gestión de riesgo
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Reevaluación del riesgo"), true);
                aWriter.addFormRow(Path.parse("Reevaluacionderiesgo/RevisiondeanalisR"));
                aWriter.addFormRow(Path.parse("Reevaluacionderiesgo/CondicionRE"));
                aWriter.endExpandCollapseBlock();
                //
                aWriter.endExpandCollapseBlock();
                //

                // Conclusiones y recomendaciones
                aWriter.add_cr("<h3>Conclusiones y recomendaciones</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Conclusiones y recomendaciones"), true);		
        		Path revPath = Path.parse("Conclusiones_recomendaciones");
        		UIDropDownList revDropDown = aWriter.newDropDownList(revPath);
        		revDropDown.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision"));
        		aWriter.addFormRow(revDropDown);
        		String value2 = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath).getValue();		
        		startBlock(aWriter, "block_revision_aceptable", "aceptable".equalsIgnoreCase(value2));	
        		aWriter.addFormRow(CONCLUCION_ACEPTABLE_PATH);
        		endBlock(aWriter);		
        		startBlock(aWriter, "block_revision_inaceptable", "inaceptable".equalsIgnoreCase(value2));
        		aWriter.addFormRow(CONCLUCION_INACEPTABLE_PATH);
        		endBlock(aWriter);		
        		aWriter.endExpandCollapseBlock();       
             
                //

                // Memorando del delegado de protección de datos
                aWriter.add_cr("<h3>Memorando del delegado de protección de datos</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Visión DPO"), true);
               	Path revdpoPath = Path.parse("Memorando_delegado/Vision_DPO");
        		UIDropDownList revdpoDropDown = aWriter.newDropDownList(revdpoPath);
        		revdpoDropDown.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockDPO"));
        		aWriter.addFormRow(revdpoDropDown);
        		String value = (String) aPaneContext.getValueContext(OBJECT_KEY, revdpoPath).getValue();		
        		startBlock(aWriter, "block_dpo_aceptable", "aceptable".equalsIgnoreCase(value));	
        		aWriter.addFormRow(MENU_ACEPTABLE_PATH);
        		aWriter.addFormRow(CONDICION_ACEPTABLE_PATH);
        		endBlock(aWriter);		
        		startBlock(aWriter, "block_dpo_inaceptable", "inaceptable".equalsIgnoreCase(value));
        		aWriter.addFormRow(MENU_INACEPTABLE_PATH);
        		aWriter.addFormRow( CONDICION_INACEPTABLE_PATH);
        		endBlock(aWriter);		
        		aWriter.endExpandCollapseBlock();	 
        		
       		
                //
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Conclusiones y recomendaciones del DPO al responsable"), true);
                aWriter.addFormRow(Path.parse("Memorando_delegado/Concluciones_DPO_responsable"));
                aWriter.endExpandCollapseBlock();
                //

                /// Referencias
                aWriter.add_cr("<h3>Referencias</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Referencias"), true);
                aWriter.addFormRow(ID_NOM_FAB_PATH);
                aWriter.addFormRow(Path.parse("Referencias2/idnombrefaf"));
                aWriter.addFormRow(Path.parse("Referencias2/idnombrefacd"));
                aWriter.addFormRow(Path.parse("Referencias2/IdentificadorAR"));
                aWriter.addFormRow(Path.parse("Referencias2/identificadorC"));
                aWriter.addFormRow(Path.parse("Referencias2/IdentificadorAIP"));
                aWriter.endExpandCollapseBlock();
                ///

                // Anexos
                aWriter.add_cr("<h3>Anexos</h3>");
                aWriter.startExpandCollapseBlock(UserMessage.createInfo("Anexos"), true);
                aWriter.addFormRow(Path.parse("Anexos/Fechaescrita"));
                aWriter.addFormRow(Path.parse("Anexos/Firma"));
                aWriter.addFormRow(Path.parse("Anexos/Responsable9"));
                aWriter.addFormRow(Path.parse("Anexos/Cargo"));
                aWriter.addFormRow(Path.parse("Anexos/Correo"));
                aWriter.addFormRow(Path.parse("Anexos/Telefono"));
                aWriter.addFormRow(Path.parse("Anexos/CorresponsableA"));
                aWriter.addFormRow(Path.parse("Anexos/cargoCor"));
                aWriter.addFormRow(Path.parse("Anexos/telcor"));
                aWriter.addFormRow(Path.parse("Anexos/DPO"));
                aWriter.addFormRow(Path.parse("Anexos/correoDPO"));
                aWriter.addFormRow(Path.parse("Anexos/telDPO"));
        		startBooleanBlock(aPaneContext, aWriter, Path.parse("Anexos/EspecialistaD"), "block_datosespecialista");
        		aWriter.addFormRow(Path.parse("Anexos/Nombre_E"));
        		aWriter.addFormRow(Path.parse("Anexos/Rol-E"));
        		aWriter.addFormRow(Path.parse("Anexos/CorreoE"));
        		aWriter.addFormRow(Path.parse("Anexos/TelefonoE"));
        		endBlock(aWriter);
                aWriter.endExpandCollapseBlock();
                //

                //
                // aWriter.endExpandCollapseBlock();

                
                
                
                
			      //Funcion para heredar datos Cuestionario
	        aWriter.addJS_cr("AjaxHandlerdpia = function (){");
	        aWriter.addJS_cr("this.handleAjaxResponseSuccess = function(responseContent){");
	        aWriter.addJS_cr("};");
	        aWriter.addJS_cr("this.handleAjaxResponseFailed = function(responseContent){");
	        aWriter.addJS_cr("};");
	        aWriter.addJS_cr("};");
	        
	        aWriter.addJS_cr("AjaxHandlerdpia.prototype = new EBX_AJAXResponseHandler();");

	        aWriter.addJS_cr("function callAjaxComponentdpia(value){");
	        aWriter.addJS_cr("var handler = new AjaxHandlerdpia();");
	        aWriter.addJS_cr("var requestParameters = '&fieldValue=' + value;");
	        aWriter.addJS_cr("var request = '" + aWriter.getURLForAjaxRequest(this::ajaxCallbackdpia) + "' + requestParameters;"); 
	        aWriter.addJS_cr("handler.sendRequest(request);");
	        aWriter.addJS_cr("}");

	        aWriter.addJS_cr("function updateInheritedFieldsdpia(value){");
	        aWriter.addJS_cr("if(value == null){");
	        aWriter.addJS_cr("callAjaxComponentdpia('');");
	        aWriter.addJS_cr("}else{");
	        aWriter.addJS_cr("callAjaxComponentdpia(value.key);");
	        aWriter.addJS_cr("}");
	        aWriter.addJS_cr("}");
                
                
                
                
                
                
                
                
                
                
                
                
                // Codigo Js
                aWriter.addJS_cr("function displayBlock(buttonValue, blockId){");
                aWriter.addJS_cr("if (buttonValue == 'true'){");
                aWriter.addJS_cr("document.getElementById(blockId).style.display = 'block';");
                aWriter.addJS_cr("}");
                aWriter.addJS_cr("else {");
                aWriter.addJS_cr("document.getElementById(blockId).style.display = 'none';");
                aWriter.addJS_cr("}");
                aWriter.addJS_cr("}");

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
                aWriter.addJS_cr(
                                "var request = '" + aWriter.getURLForAjaxRequest(this::ajaxCallback)
                                                + "' + requestParameters;");
                aWriter.addJS_cr("handler.sendRequest(request);");
                aWriter.addJS_cr("}");

                aWriter.addJS_cr("function updateInheritedFields(value){");
                aWriter.addJS_cr("if(value == null){");
                aWriter.addJS_cr("callAjaxComponent('');");
                aWriter.addJS_cr("}else{");
                aWriter.addJS_cr("callAjaxComponent(value.key);");
                aWriter.addJS_cr("}");
                aWriter.addJS_cr("}");

                // Codigo JS dos

                aWriter.addJS_cr("AjaxHandlerDos = function (){");
                aWriter.addJS_cr("this.handleAjaxResponseSuccess = function(responseContent){");
                aWriter.addJS_cr("};");
                aWriter.addJS_cr("this.handleAjaxResponseFailed = function(responseContent){");
                aWriter.addJS_cr("};");
                aWriter.addJS_cr("};");

                aWriter.addJS_cr("AjaxHandlerDos.prototype = new EBX_AJAXResponseHandler();");

                aWriter.addJS_cr("function callAjaxComponentDos(value){");
                aWriter.addJS_cr("var handler = new AjaxHandlerDos();");
                aWriter.addJS_cr("var requestParameters = '&fieldValue=' + value;");
                aWriter.addJS_cr("var request = '" + aWriter.getURLForAjaxRequest(this::ajaxCallbackDos)+ "' + requestParameters;");
                aWriter.addJS_cr("handler.sendRequest(request);");
                aWriter.addJS_cr("}");

                aWriter.addJS_cr("function updateInheritedFieldsDos(value){");
                aWriter.addJS_cr("if(value == null){");
                aWriter.addJS_cr("callAjaxComponentDos('');");
                aWriter.addJS_cr("}else{");
                aWriter.addJS_cr("callAjaxComponentDos(value.key);");
                aWriter.addJS_cr("}");
                aWriter.addJS_cr("}");

                //
                
                
            	
       		 // Funcion que ocupan los campos String que tiene valores definidos y dependindo la selecion muestra los campos (Cargo)
                /*
       	    aWriter.addJS_cr("function displayBlockDPO(buttonValue){");
       	    aWriter.addJS_cr("const blockAceptable = document.getElementById('block_dpo_aceptable');");
       	    aWriter.addJS_cr("const blockInaceptable = document.getElementById('block_dpo_inaceptable');");
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
       	    aWriter.addJS_cr("}");*/ 
                
                
    		aWriter.addJS_cr("function displayBlockDPO(buttonValue) {");
    		aWriter.addJS_cr("  const blockAceptable = document.getElementById('block_dpo_aceptable');");
    		aWriter.addJS_cr("  const blockInaceptable = document.getElementById('block_dpo_inaceptable');");
    		aWriter.addJS_cr("  if (buttonValue === 'Aceptable') {");
    		aWriter.addJS_cr("    resetValuesList('" + MInaceptablePrefix + "');");
    		aWriter.addJS_cr("    resetValuesList('" + CInaceptablePrefix + "');");
    		aWriter.addJS_cr("    blockAceptable.style.display = 'block';");
    		aWriter.addJS_cr("    blockInaceptable.style.display = 'none';");
    		aWriter.addJS_cr("  } else if (buttonValue === 'Inaceptable') {");
    		aWriter.addJS_cr("    resetValuesList('" + MAceptablePrefix + "');");
    		aWriter.addJS_cr("    resetValuesList('" + CAceptablePrefix + "');");
    		aWriter.addJS_cr("    blockAceptable.style.display = 'none';");
    		aWriter.addJS_cr("    blockInaceptable.style.display = 'block';");
    		aWriter.addJS_cr("  } else {");
    		aWriter.addJS_cr("    resetValuesList('" + MAceptablePrefix + "');");
    		aWriter.addJS_cr("    resetValuesList('" + CAceptablePrefix + "');");
    		aWriter.addJS_cr("    blockAceptable.style.display = 'none';");
    		aWriter.addJS_cr("    blockInaceptable.style.display = 'none';");
    		aWriter.addJS_cr("  }");
    		aWriter.addJS_cr("}");
                
                // Funcion que ocupan los campos String que tiene valores definidos y dependindo la selecion muestra los campos (Revisión metodológica)
    		aWriter.addJS_cr("function displayBlockRevision(buttonValue) {");
    		aWriter.addJS_cr("  const blockAceptable = document.getElementById('block_revision_aceptable');");
    		aWriter.addJS_cr("  const blockInaceptable = document.getElementById('block_revision_inaceptable');");
    		aWriter.addJS_cr("  if (buttonValue === 'Aceptable') {");
    		aWriter.addJS_cr("    resetValuesList('" + ConaceptablePrefix + "');");
    		aWriter.addJS_cr("    blockAceptable.style.display = 'block';");
    		aWriter.addJS_cr("    blockInaceptable.style.display = 'none';");
    		aWriter.addJS_cr("  } else if (buttonValue === 'Inaceptable') {");
    		aWriter.addJS_cr("    resetValuesList('" + CoInaceptablePrefix + "');");
    		aWriter.addJS_cr("    blockAceptable.style.display = 'none';");
    		aWriter.addJS_cr("    blockInaceptable.style.display = 'block';");
    		aWriter.addJS_cr("  } else {");
    		aWriter.addJS_cr("    resetValuesList('" + CoInaceptablePrefix + "');");
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
                // Codigo JS tres

                aWriter.addJS_cr("AjaxHandlerTres = function (){");
                aWriter.addJS_cr("this.handleAjaxResponseSuccess = function(responseContent){");
                aWriter.addJS_cr("};");
                aWriter.addJS_cr("this.handleAjaxResponseFailed = function(responseContent){");
                aWriter.addJS_cr("};");
                aWriter.addJS_cr("};");

                aWriter.addJS_cr("AjaxHandlerTres.prototype = new EBX_AJAXResponseHandler();");

                aWriter.addJS_cr("function callAjaxComponentTres(value){");
                aWriter.addJS_cr("var handler = new AjaxHandlerTres();");
                aWriter.addJS_cr("var requestParameters = '&fieldValue=' + value;");
                aWriter.addJS_cr(
                                "var request = '" + aWriter.getURLForAjaxRequest(this::ajaxCallbackTres)
                                                + "' + requestParameters;");
                aWriter.addJS_cr("handler.sendRequest(request);");
                aWriter.addJS_cr("}");

                aWriter.addJS_cr("function updateInheritedFieldsTres(value){");
                aWriter.addJS_cr("if(value == null){");
                aWriter.addJS_cr("callAjaxComponentTres('');");
                aWriter.addJS_cr("}else{");
                aWriter.addJS_cr("callAjaxComponentTres(value.key);");
                aWriter.addJS_cr("}");
                aWriter.addJS_cr("}");

                //

                // Funciones de lista
                aWriter.addJS_cr("function resetList(path, listId){");
                aWriter.addJS_cr("const elementList = document.getElementById(listId).children;");
                aWriter.addJS_cr("for(let i = 0; i < elementList.length; i++){");
                aWriter.addJS_cr("elementList.item(i).style.display = 'none';");
                aWriter.addJS_cr("ebx_form_setValue(path + '[' + i + ']' , null);");
                aWriter.addJS_cr("}");
                aWriter.addJS_cr("}");

                aWriter.addJS_cr("function setList(path, listId, valuesArray){");
                aWriter.addJS_cr("console.log(listId);");
                aWriter.addJS_cr("const elementList = document.getElementById(listId).children;");
                aWriter.addJS_cr("valuesArray.forEach(function(element, index){");
                aWriter.addJS_cr("console.log(element);");
                aWriter.addJS_cr("ebx_form_setValue(path + '[' + index + ']' , element);");
                aWriter.addJS_cr("elementList.item(index).style.display = '';");
                aWriter.addJS_cr("});");
                aWriter.addJS_cr("}");

                // Funcion de lista para los campos de la carpeta Evaluacion_Riesgo,
                // resetComplexList y setComplexList

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
                
              //para los que no tienen llave foranea
        		aWriter.addJS_cr("function setList2(path, listId, valuesArray){");
        		aWriter.addJS_cr("const elementList = document.getElementById(listId).children;");
        		aWriter.addJS_cr("valuesArray.forEach(function(element, index){");
        		aWriter.addJS_cr("ebx_form_setValue(path + '[' + index + ']' , element.key);");
        		aWriter.addJS_cr("elementList.item(index).style.display = '';");
        		aWriter.addJS_cr("});");
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

        private void startComplexGroup(UserServicePaneContext aPaneContext,
                        UserServicePaneWriter aWriter, Path path, String blockId) {

                UIRadioButtonGroup button = aWriter.newRadioButtonGroup(path);
                button.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlock", blockId));
                aWriter.addFormRow(button);
                Boolean value = (Boolean) aPaneContext.getValueContext(OBJECT_KEY, path).getValue();
                String display;
                if (value != null && value)
                        display = "display:block";
                else
                        display = "display:none";
                aWriter.add("<div ");
                aWriter.addSafeAttribute("id", blockId);
                aWriter.addSafeAttribute("style", display);
                aWriter.add_cr(">");
        }

        private void endComplexGroup(UserServicePaneWriter aWriter) {

                aWriter.add_cr("</div>");

        }

        @Override
        public void setupObjectContext(UserServiceSetupObjectContext<T> aContext,
                        UserServiceObjectContextBuilder aBuilder) {
                if (aContext.isInitialDisplay()) {

                        TableEntitySelection selection = aContext.getEntitySelection();

                        if (selection instanceof RecordEntitySelection) {

                                Adaptation record = ((RecordEntitySelection) selection).getRecord();

                                if (aContext.getServiceKey().equals(ServiceKey.DUPLICATE))
                                        aBuilder.registerNewDuplicatedRecord(OBJECT_KEY, record);
                                else
                                        aBuilder.registerRecordOrDataSet(OBJECT_KEY, record);

                        } else {

                                aBuilder.registerNewRecord(OBJECT_KEY, selection.getTable());

                        }

                }

        }

		private void ajaxCallbackdpia(UserServiceAjaxContext anAjaxContext, UserServiceAjaxResponse anAjaxResponse){
	    	
		    Adaptation dataset = anAjaxContext.getValueContext(OBJECT_KEY).getAdaptationInstance();
		    AdaptationTable table = dataset.getTable(Path.parse("/root/Cuestionario/CuestionarioDPIA"));

		    String proce = null;
		    String justi = null;
		   
		    
		    String value = anAjaxContext.getParameter("fieldValue");

		    if(value != null && !value.isBlank()) {

		        Adaptation record = table.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(value));
		        
		        proce = record.getString(Path.parse("./Pregunta_21"));
		        justi = record.getString(Path.parse("./Justificacion_Preg21"));

		    
		    }
		    
		    UserServiceWriter aWriter = anAjaxResponse.getWriter();
		    
		    //justi
		    justi = justi == null ? "null" : "'" + justi + "'";
		
		    
		    
		    //justi
		    
		    if(proce == null) {
		        aWriter.addJS_cr("justi = null;");
		        
		    } else {
		    
		        aWriter.addJS_cr("proce = {");
		        aWriter.addJS_cr("key: '" + proce + "',");
		        aWriter.addJS_cr("label: '',");
		        aWriter.addJS_cr("previewURL: undefined");
		        aWriter.addJS_cr("};");
		    
		        
		    }
		    aWriter.addJS_cr("ebx_form_setValue('" + justificacionPrefix + "', " +  justi + ")");
		    aWriter.addJS_cr("ebx_form_setValue('" + procedenciaPrefix + "', proce)");
		    
		   
		}

        private void ajaxCallback(UserServiceAjaxContext anAjaxContext, UserServiceAjaxResponse anAjaxResponse) {

                Adaptation datasetA = anAjaxContext.getValueContext(OBJECT_KEY).getAdaptationInstance();
                AdaptationTable table = datasetA
                                .getTable(Path.parse("/root/Formularios/FABAN"));

                List<String> destinatariosE = null;
                List<String> operacionesEjecuta = null;
                List<String> nombresContrato = null;
                List<String> destinatariosSun = null;
                List<String> opersEjecutaSub = null;
                List<String> nombsConSub = null;
                List<String> operEjecDPI = null;
                List<String> basesJuridica = null;
                List<String> responsableFab = null;
                List<String> corresponsableFab = null;
                List<String> funcionesdos = null;
                List<String> destiantarioI = null;
                List<String> finalidadC = null;
                List<String> destinatarioDosI = null;
                List<String> operacionesDosI = null;
                List<String> destinatariosCP = null;
                List<String> finalidadCP = null;
                List<String> opeejea4 = null;
                List<String> heredaJuicio = null;
                List<String> finprincipal1 = null;
                List<String> finSecundario = null;
                List<String> finesPosteriores = null;
                List<String> causaLegitimacionCP = null;
                List<String> roles = null;
                List<String> destinatarioTres = null;
                List<String> operacionesSubI = null;
                List<String> destinatariosCuatro = null;
                List<String> finalidadIT = null;

                String periodosVigenciaE = null;
                String statusVigenciaE = null;
                // String baseJuridicaHereda = null;
                String fechaFE = null;
                String fechaFormali = null;
                String periodoVigenciaSub = null;
                String idNombreFab = null;

                boolean isEncargadoD = false;
                boolean isEstatusVigencia = false;
                boolean isSubEncargado = false;
                boolean isSubElemento = false;
                String value = anAjaxContext.getParameter("fieldValue");
                System.out.println(value);

                if (value != null) {

                        Adaptation record = table.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(value));
                        destinatariosE = record.getList( Path.parse("./Comunicacionencargado/Destinatariocomunicacionconencargado"));
                        
                        operacionesEjecuta = record.getList(
                                        Path.parse("./Comunicacionencargado/OperacionqueejecutaComuencargados"));
                        nombresContrato = record
                                        .getList(Path.parse("./Comunicacionencargado/NombreContratoCConEncarg"));
                        fechaFE = String.valueOf(record.getDate(Path.parse("./Comunicacionencargado/FechaFormalizacionCconEncarg")));
                       
                        
                        periodosVigenciaE = record.getString(Path.parse("./Comunicacionencargado/PeriodovigenciaCconEncarg"));
                       
                        isEstatusVigencia = record
                                        .get_boolean(Path.parse("./Comunicacionencargado/VigenteCconEncarg"));
                        destinatariosSun = record
                                        .getList(Path.parse("./Comunicacionconsubencargado/DestinatarioCconSuben"));
                        opersEjecutaSub = record.getList(
                                        Path.parse("./Comunicacionconsubencargado/Operacionesqueejecutansubencargado"));
                        nombsConSub = record.getList(
                                        Path.parse("./Comunicacionconsubencargado/nombrecontratosubencargado"));
                        fechaFormali = String.valueOf(record.getDate(Path.parse("./Comunicacionconsubencargado/Fechaformalizacionsubencargado")));
                        
                        periodoVigenciaSub = record.getString(Path.parse("./Comunicacionconsubencargado/Periododevigenciasubencargado"));
                       
                        basesJuridica = record.getList(Path.parse("./Conclusion_juicio_de_idoneidad_proporcionalidad_necesidad"));
                        operEjecDPI = record.getList(Path.parse("./Operaciones_ejecuta"));
                        responsableFab = record.getList(Path.parse("./Internos/NombreCE"));
                        corresponsableFab = record.getList(Path.parse("./Nombre_Co"));
                        funcionesdos = record.getList(Path.parse("./Internos/Operacionejecuta"));
//aqui
                        
                        destiantarioI = record.getList(Path.parse("./Cesion/DestinatarioCesion"));
                        finalidadC = record.getList(Path.parse("./Cesion/FinalidadesCesion"));
                        destinatarioDosI = record.getList(
                                        Path.parse("./Comunicacionencargado/Destinatariocomunicacionconencargado"));
                        operacionesDosI = record.getList(
                                        Path.parse("./Comunicacionencargado/OperacionqueejecutaComuencargados"));
                        destinatariosCP = record.getList(Path.parse("./Cesion/DestinatarioCesion"));
                        finalidadCP = record.getList(Path.parse("./Cesion/FinalidadesCesion"));
                        opeejea4 = record.getList(Path.parse("./Operaciones_ejecuta"));
                        heredaJuicio = record.getList(
                                        Path.parse("./Conclusion_juicio_de_idoneidad_proporcionalidad_necesidad"));
                        finprincipal1 = record.getList(Path.parse("./Finalidad_del_tratamiento"));
                        finSecundario = record.getList(Path.parse("./Fines_secundarios"));
                        finesPosteriores = record.getList(Path.parse("./Fines_posteriores"));
                        causaLegitimacionCP = record.getList(Path.parse("./Cesion/Causaslegitimacioncesion"));
                        roles = record.getList(Path.parse("./Internos/Cargo"));
                        destinatarioTres = record
                                        .getList(Path.parse("./Comunicacionconsubencargado/DestinatarioCconSuben"));
                        operacionesSubI = record.getList(
                                        Path.parse("./Comunicacionconsubencargado/Operacionesqueejecutansubencargado"));
                        destinatariosCuatro = record.getList(Path.parse("./Trasferencia/Destinatariotrasferencia"));
                        finalidadIT = record.getList(Path.parse("./Trasferencia/Finalidadestrasferencia"));
                        // .toString();
                        // periodoVigenciaE = record
                        // .getString(Path.parse("./Comunicacionencargado/PeriodovigenciaCconEncarg"));
                        // baseJuridicaHereda = record.getString(
                        // Path.parse("./Conclusion_juicio_de_idoneidad_proporcionalidad_necesidad"));
                        idNombreFab = record.getString(Path.parse("./Identificador"));
                        isEncargadoD = record.get_boolean(
                                        Path.parse("./Comunicacionencargado/Comunicacionencargadodecicion"));
                        isSubEncargado = record.get_boolean(
                                        Path.parse("./Comunicacionconsubencargado/Comunicacionsubencargadodecicion"));
                        isSubElemento = record
                                        .get_boolean(Path.parse("./Comunicacionconsubencargado/Vigentesubencargado"));
                        System.out.println(idNombreFab);
                }

                UserServiceWriter aWriter = anAjaxResponse.getWriter();

                idNombreFab = idNombreFab == null ? "null" : "'" + idNombreFab + "'";
                periodosVigenciaE = periodosVigenciaE == null ? "null" : "'" + periodosVigenciaE + "'";
                // statusVigenciaE = statusVigenciaE == null ? "null" : "'" + statusVigenciaE +
                // "'";
                fechaFE = fechaFE == null ? "null" : "'" + fechaFE + "'";
                fechaFormali = fechaFormali == null ? "null" : "'" + fechaFormali + "'";
                periodoVigenciaSub = periodoVigenciaSub == null ? "null" : "'" + periodoVigenciaSub + "'";
                System.out.println(idNombreFab);

                // DestinatarioE

                aWriter.addJS_cr("destinatarioEArray = [];");
                aWriter.addJS_cr("resetList('" + pDestinatarioE + "', '" + DESTINATARIO_E + "');");
                if (destinatariosE != null) {

                        for (String destinatarioE : destinatariosE) {

                                aWriter.addJS_cr("destinatarioE = {");
                                aWriter.addJS_cr("key: '" + destinatarioE + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destinatarioEArray.push(destinatarioE);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatarioE + "', '" + DESTINATARIO_E
                                        + "', destinatarioEArray);");

                }

                aWriter.addJS_cr("operacionesEjecutaArray = [];");
                aWriter.addJS_cr("resetList('" + pOperacionesEjecuta + "', '" + OPERACIONES_EJECUTA + "');");
                if (operacionesEjecuta != null) {

                        for (String operacionEjecuta : operacionesEjecuta) {

                                aWriter.addJS_cr("operacionEjecuta = {");
                                aWriter.addJS_cr("key: '" + operacionEjecuta + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("operacionesEjecutaArray.push(operacionEjecuta);");

                        }
                        aWriter.addJS_cr("setList2('" + pOperacionesEjecuta + "', '" + OPERACIONES_EJECUTA
                                        + "', operacionesEjecutaArray);");

                }

                aWriter.addJS_cr("nombresContratojecutaArray = [];");
                aWriter.addJS_cr("resetList('" + pNombreContrato + "', '" + NOMBRE_CONTRATO + "');");
                if (nombresContrato != null) {

                        for (String nombreContrato : nombresContrato) {

                                aWriter.addJS_cr("nombreContrato = {");
                                aWriter.addJS_cr("key: '" + nombreContrato + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("nombresContratojecutaArray.push(nombreContrato);");

                        }
                        aWriter.addJS_cr("setList2('" + pNombreContrato + "', '" + NOMBRE_CONTRATO
                                        + "', nombresContratojecutaArray);");

                }

                aWriter.addJS_cr("destinatarioSunArray = [];");
                aWriter.addJS_cr("resetList('" + pDestinatarioSun + "', '" + DESTINATARIO_SUN + "');");
                if (destinatariosSun != null) {

                        for (String destinatarioSun : destinatariosSun) {

                                aWriter.addJS_cr("destinatarioSun = {");
                                aWriter.addJS_cr("key: '" + destinatarioSun + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destinatarioSunArray.push(destinatarioSun);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatarioSun + "', '" + DESTINATARIO_SUN
                                        + "', destinatarioSunArray);");

                }

                aWriter.addJS_cr("opEjecutaSubArray = [];");
                aWriter.addJS_cr("resetList('" + pOperacionesEjecutaS + "', '" + OPERACIONES_EJECSUB + "');");
                if (opersEjecutaSub != null) {

                        for (String operEjecutaSub : opersEjecutaSub) {

                                aWriter.addJS_cr("operEjecutaSub = {");
                                aWriter.addJS_cr("key: '" + operEjecutaSub + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("opEjecutaSubArray.push(operEjecutaSub);");

                        }
                        aWriter.addJS_cr("setList2('" + pOperacionesEjecutaS + "', '" + OPERACIONES_EJECSUB
                                        + "', opEjecutaSubArray);");

                }

                aWriter.addJS_cr("nomConSubArray = [];");
                aWriter.addJS_cr("resetList('" + pNomConSub + "', '" + NOM_CONTRATO_SUB + "');");
                if (nombsConSub != null) {

                        for (String nomConSub : nombsConSub) {

                                aWriter.addJS_cr("nomConSub = {");
                                aWriter.addJS_cr("key: '" + nomConSub + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("nomConSubArray.push(nomConSub);");

                        }
                        aWriter.addJS_cr("setList2('" + pNomConSub + "', '" + NOM_CONTRATO_SUB
                                        + "', nomConSubArray);");

                }

                aWriter.addJS_cr("baseJuridiArray = [];");
                aWriter.addJS_cr("resetList('" + pBaseJuri + "', '" + BASE_JURIDICA + "');");
                if (basesJuridica != null) {

                        for (String baseJuri : basesJuridica) {

                                aWriter.addJS_cr("baseJuri = {");
                                aWriter.addJS_cr("key: '" + baseJuri + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("baseJuridiArray.push(baseJuri);");

                        }
                        aWriter.addJS_cr("setList2('" + pBaseJuri + "', '" + BASE_JURIDICA+ "', baseJuridiArray);");

                }

                aWriter.addJS_cr("oPerejecutaDPIAArray = [];");
                aWriter.addJS_cr("resetList('" + pOperEjecDPIA + "', '" + OPERACIONES_EJECUTA_DPIA + "');");
                if (operEjecDPI != null) {

                        for (String oPerejecdpia : operEjecDPI) {

                                aWriter.addJS_cr("oPerejecdpia = {");
                                aWriter.addJS_cr("key: '" + oPerejecdpia + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("oPerejecutaDPIAArray.push(oPerejecdpia);");

                        }
                        aWriter.addJS_cr("setList2('" + pOperEjecDPIA + "', '" + OPERACIONES_EJECUTA_DPIA
                                        + "', oPerejecutaDPIAArray);");

                }

                aWriter.addJS_cr("responsableFabArray = [];");
                aWriter.addJS_cr("resetList('" + pResponsableFab + "', '" + RESPONSABLE_FAB + "');");
                if (responsableFab != null) {

                        for (String responFAB : responsableFab) {

                                aWriter.addJS_cr("responFAB = {");
                                aWriter.addJS_cr("key: '" + responFAB + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("responsableFabArray.push(responFAB);");

                        }
                        aWriter.addJS_cr("setList('" + pResponsableFab + "', '" + RESPONSABLE_FAB
                                        + "', responsableFabArray);");

                }

                aWriter.addJS_cr("coresponsableFabArray = [];");
                aWriter.addJS_cr("resetList('" + pCorresponsableFAB + "', '" + CORRESPONSABLE_FAB + "');");
                if (corresponsableFab != null) {

                        for (String coresponFAB : corresponsableFab) {

                                aWriter.addJS_cr("coresponFAB = {");
                                aWriter.addJS_cr("key: '" + coresponFAB + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("coresponsableFabArray.push(coresponFAB);");

                        }
                        aWriter.addJS_cr("setList('" + pCorresponsableFAB + "', '" + CORRESPONSABLE_FAB
                                        + "', coresponsableFabArray);");

                }

                aWriter.addJS_cr("funcionesDosArray = [];");
                aWriter.addJS_cr("resetList('" + pFuncionesDos + "', '" + FUNCIONES_DOS_FAB + "');");
                if (funcionesdos != null) {

                        for (String funcionDos : funcionesdos) {

                                aWriter.addJS_cr("funcionDos = {");
                                aWriter.addJS_cr("key: '" + funcionDos + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("funcionesDosArray.push(funcionDos);");

                        }
                        aWriter.addJS_cr("setList2('" + pFuncionesDos + "', '" + FUNCIONES_DOS_FAB
                                        + "', funcionesDosArray);");

                }

                aWriter.addJS_cr("destinatarioIArray = [];");
                aWriter.addJS_cr("resetList('" + pDestinatarioI + "', '" + DESTINATARIO_I + "');");
                if (destiantarioI != null) {

                        for (String destI : destiantarioI) {

                                aWriter.addJS_cr("destI = {");
                                aWriter.addJS_cr("key: '" + destI + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destinatarioIArray.push(destI);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatarioI + "', '" + DESTINATARIO_I
                                        + "', destinatarioIArray);");

                }

                aWriter.addJS_cr("finalidadCArray = [];");
                aWriter.addJS_cr("resetList('" + pFinalidadC + "', '" + FINALIDAD_C + "');");
                if (finalidadC != null) {

                        for (String finalidadCa : finalidadC) {

                                aWriter.addJS_cr("finalidadCa = {");
                                aWriter.addJS_cr("key: '" + finalidadCa + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("finalidadCArray.push(finalidadCa);");

                        }
                        aWriter.addJS_cr("setList2('" + pFinalidadC + "', '" + FINALIDAD_C
                                        + "', finalidadCArray);");

                }

                aWriter.addJS_cr("destinatarioDosIArray = [];");
                aWriter.addJS_cr("resetList('" + pDestinatarioDosI + "', '" + DESTINATARIO_DOS_I + "');");
                if (destinatarioDosI != null) {

                        for (String destDosI : destinatarioDosI) {

                                aWriter.addJS_cr("destDosI = {");
                                aWriter.addJS_cr("key: '" + destDosI + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destinatarioDosIArray.push(destDosI);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatarioDosI + "', '" + DESTINATARIO_DOS_I
                                        + "', destinatarioDosIArray);");

                }

                aWriter.addJS_cr("operacionesoDosIArray = [];");
                aWriter.addJS_cr("resetList('" + pOperacionesDosI + "', '" + OPERACIONES_DOS_I + "');");
                if (operacionesDosI != null) {

                        for (String operacionDosI : operacionesDosI) {

                                aWriter.addJS_cr("operacionDosI = {");
                                aWriter.addJS_cr("key: '" + operacionDosI + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("operacionesoDosIArray.push(operacionDosI);");

                        }
                        aWriter.addJS_cr("setList2('" + pOperacionesDosI + "', '" + OPERACIONES_DOS_I
                                        + "', operacionesoDosIArray);");

                }

                aWriter.addJS_cr("destinatarioCPArray = [];");
                aWriter.addJS_cr("resetList('" + pDestinatarioCP + "', '" + DESTINATARIO_CP + "');");
                if (destinatariosCP != null) {

                        for (String destinatarioCP : destinatariosCP) {

                                aWriter.addJS_cr("destinatarioCP = {");
                                aWriter.addJS_cr("key: '" + destinatarioCP + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destinatarioCPArray.push(destinatarioCP);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatarioCP + "', '" + DESTINATARIO_CP
                                        + "', destinatarioCPArray);");

                }

                aWriter.addJS_cr("finalidadCPArray = [];");
                aWriter.addJS_cr("resetList('" + pFinalidadCP + "', '" + FINALIDAD_CP + "');");
                if (finalidadCP != null) {

                        for (String finalidaCP : finalidadCP) {

                                aWriter.addJS_cr("finalidaCP = {");
                                aWriter.addJS_cr("key: '" + finalidaCP + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("finalidadCPArray.push(finalidaCP);");

                        }
                        aWriter.addJS_cr("setList2('" + pFinalidadCP + "', '" + FINALIDAD_CP
                                        + "', finalidadCPArray);");

                }

                aWriter.addJS_cr("opeeje4Array = [];");
                aWriter.addJS_cr("resetList('" + pOpeeje4 + "', '" + OPEEJE4 + "');");
                if (opeejea4 != null) {

                        for (String opeejea : opeejea4) {

                                aWriter.addJS_cr("opeejea = {");
                                aWriter.addJS_cr("key: '" + opeejea + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewurl: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("opeeje4Array.push(opeejea);");

                        }
                        aWriter.addJS_cr("setList2('" + pOpeeje4 + "', '" + OPEEJE4
                                        + "', opeeje4Array);");

                }

                aWriter.addJS_cr("HeredaJuiciorray = [];");
                aWriter.addJS_cr("resetList('" + pHeredaJuicio + "', '" + HEREDAR_JUICIO + "');");
                if (heredaJuicio != null) {

                        for (String heredaJ : heredaJuicio) {

                                aWriter.addJS_cr("heredaJ = {");
                                aWriter.addJS_cr("key: '" + heredaJ + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("HeredaJuiciorray.push(heredaJ);");

                        }
                        aWriter.addJS_cr("setList2('" + pHeredaJuicio + "', '" + HEREDAR_JUICIO
                                        + "', HeredaJuiciorray);");

                }

                aWriter.addJS_cr("finalidadPrinArray = [];");
                aWriter.addJS_cr("resetList('" + pFinPrincipal + "', '" + FIN_PRINCIPAL + "');");
                if (finprincipal1 != null) {

                        for (String finalidadPrin : finprincipal1) {

                                aWriter.addJS_cr("finalidadPrin = {");
                                aWriter.addJS_cr("key: '" + finalidadPrin + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("finalidadPrinArray.push(finalidadPrin);");

                        }
                        aWriter.addJS_cr("setList2('" + pFinPrincipal + "', '" + FIN_PRINCIPAL
                                        + "', finalidadPrinArray);");

                }

                aWriter.addJS_cr("finSecundarioArray = [];");
                aWriter.addJS_cr("resetList('" + pFinSecundario + "', '" + FIN_SECUNDARIO + "');");
                if (finSecundario != null) {

                        for (String finSecund : finSecundario) {

                                aWriter.addJS_cr("finSecund = {");
                                aWriter.addJS_cr("key: '" + finSecund + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("finSecundarioArray.push(finSecund);");

                        }
                        aWriter.addJS_cr("setList2('" + pFinSecundario + "', '" + FIN_SECUNDARIO
                                        + "', finSecundarioArray);");

                }

                aWriter.addJS_cr("finalesPosterioresArray = [];");
                aWriter.addJS_cr("resetList('" + pFinesPosteriores + "', '" + FINES_POSTERIORES + "');");
                if (finesPosteriores != null) {

                        for (String finPosterior : finesPosteriores) {

                                aWriter.addJS_cr("finPosterior = {");
                                aWriter.addJS_cr("key: '" + finPosterior + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("finalesPosterioresArray.push(finPosterior);");

                        }
                        aWriter.addJS_cr("setList2('" + pFinesPosteriores + "', '" + FINES_POSTERIORES
                                        + "', finalesPosterioresArray);");

                }

                aWriter.addJS_cr("causaLegitimacionArray = [];");
                aWriter.addJS_cr("resetList('" + pCausaLeitimacionCP + "', '" + CAUSA_DE_LEGITIMACION_CP + "');");
                if (causaLegitimacionCP != null) {

                        for (String causaLeg : causaLegitimacionCP) {

                                aWriter.addJS_cr("causaLeg = {");
                                aWriter.addJS_cr("key: '" + causaLeg + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("causaLegitimacionArray.push(causaLeg);");

                        }
                        aWriter.addJS_cr("setList2('" + pCausaLeitimacionCP + "', '" + CAUSA_DE_LEGITIMACION_CP
                                        + "', causaLegitimacionArray);");

                }

                aWriter.addJS_cr("rolesArray = [];");
                aWriter.addJS_cr("resetList('" + pRoles + "', '" + ROLES_LIST + "');");
                if (roles != null) {

                        for (String rol : roles) {

                                aWriter.addJS_cr("rol = {");
                                aWriter.addJS_cr("key: '" + rol + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("rolesArray.push(rol);");

                        }
                        aWriter.addJS_cr("setList('" + pRoles + "', '" + ROLES_LIST
                                        + "', rolesArray);");

                }

                aWriter.addJS_cr("destinatarioTresArray = [];");
                aWriter.addJS_cr("resetList('" + pDestinatarioTres + "', '" + DESTINATARIO_TRES_I + "');");
                if (destinatarioTres != null) {

                        for (String destiTres : destinatarioTres) {

                                aWriter.addJS_cr("destiTres = {");
                                aWriter.addJS_cr("key: '" + destiTres + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destinatarioTresArray.push(destiTres);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatarioTres + "', '" + DESTINATARIO_TRES_I
                                        + "', destinatarioTresArray);");

                }

                aWriter.addJS_cr("operacionesSubIArray = [];");
                aWriter.addJS_cr("resetList('" + pOperacionesSubI + "', '" + OPERACIONES_SUB_I + "');");
                if (operacionesSubI != null) {

                        for (String operacionesSubi : operacionesSubI) {

                                aWriter.addJS_cr("operacionesSubi = {");
                                aWriter.addJS_cr("key: '" + operacionesSubi + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("operacionesSubIArray.push(operacionesSubi);");

                        }
                        aWriter.addJS_cr("setList2('" + pOperacionesSubI + "', '" + OPERACIONES_SUB_I
                                        + "', operacionesSubIArray);");

                }

                aWriter.addJS_cr("destinatarioCuatroArray = [];");
                aWriter.addJS_cr("resetList('" + pDestinatario4 + "', '" + DESTINATARIO_CUATRO + "');");
                if (destinatariosCuatro != null) {

                        for (String destCuatro : destinatariosCuatro) {

                                aWriter.addJS_cr("destCuatro = {");
                                aWriter.addJS_cr("key: '" + destCuatro + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destinatarioCuatroArray.push(destCuatro);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatario4 + "', '" + DESTINATARIO_CUATRO
                                        + "', destinatarioCuatroArray);");

                }

                aWriter.addJS_cr("finalidadTIArray = [];");
                aWriter.addJS_cr("resetList('" + pFinalidadIT + "', '" + FINALIDAD_IT + "');");
                if (finalidadIT != null) {

                        for (String finIt : finalidadIT) {

                                aWriter.addJS_cr("finIt = {");
                                aWriter.addJS_cr("key: '" + finIt + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("finalidadTIArray.push(finIt);");

                        }
                        aWriter.addJS_cr("setList2('" + pFinalidadIT + "', '" + FINALIDAD_IT
                                        + "', finalidadTIArray);");

                }

                aWriter.addJS_cr("ebx_form_setValue('" + pIdNomFab + "', " + idNombreFab + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pfecha_P + "', " + fechaFE + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pPeriodoVigenciaE + "', " + periodosVigenciaE + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pEstatusVigenciaE + "', " + isEstatusVigencia + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pFechaFormali + "', " + fechaFormali + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pPeriodoVigSub + "', " + periodoVigenciaSub + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pEncargadoDecicion + "', " + isEncargadoD + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pSubEncagado + "', " + isSubEncargado + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pEstatuVSub + "', " + isSubElemento + ")");

        }

        private void ajaxCallbackDos(UserServiceAjaxContext anAjaxContext, UserServiceAjaxResponse anAjaxResponse) {

                Adaptation datasetA = anAjaxContext.getValueContext(OBJECT_KEY).getAdaptationInstance();
                AdaptationTable table = datasetA
                                .getTable(Path.parse("/root/Formularios/Plantilla_Analisis_Impacto_DPIA"));
                // IdentificadorAIP
                /// root/Formularios/Evaluacion_impact
                /// root/Cuestionario/CuestionarioDPIA
                // idCuestionariodpia

                String value = anAjaxContext.getParameter("fieldValue");
                System.out.println(value);

                List<String> principalesRiesgos = null;
                List<String> eVRiesgoList = null;
                List<String> pgrAnalisisImpacDPI = null;
                List<String> heredaResponsableL = null;
                List<String> corresponsableH = null;
                List<String> servicioContratado = null;
                List<String> destinatarioEDos = null;
                List<String> operacionesEjecutaDos = null;
                List<String> nomContDos = null;
                String fechaDos = null;
                String periodoVigD = null;
               // String pEstaVgDos = null;
                List<String> servConDos = null;
                List<String> destDosA = null;
                List<String> operEjecSubDos = null;
                List<String> nomContratoSub = null;
                String perioVigDos = null;
                boolean estatusVSubDos = false;
                List<String> contratadoTres = null;
                List<String> destinatarioTres = null;
                List<String> operEjecutaTres = null;
                List<String> nomConTres = null;
                List<String> serContratadoCu = null;
                List<String> DSunCuatro = null;
                List<String> operCuatroEjec = null;
                List<String> nomConCuatro = null;
                
                String fechaFormaCuatro = null;
               
                String periodoVigCuatro = null;
                boolean estatusVCuatro = false;
                List<String> destinatarioCP = null;
                List<String> finalidadCuatroCP = null;
                List<String> causaCP = null;
                String nombreFichaA = null;
                List<String> causaLI = null;

                List<String> destCP5 = null;
                List<String> finalidadCP = null;
                List<String> causaLeg = null;
                List<String> descripcionFT = null;
                List<String> descripcionFC = null;
                List<String> destinatario5FI = null;
                List<String> finaliadITT = null;

                List<String> destinatarioITT = null;
                List<String> roles6 = null;
                List<String> operacionesEjN = null;
                List<String> destinatarioFCI = null;
                List<String> causasLegitimas = null;
                List<String> destinatarioCVDITT = null;

                List<String> finalidadesVDITT = null;
                List<String> roles12 = null;
                List<String> operaiJe = null;

                List<String> desFase4 = null;
                List<String> desFase5 = null;

                String fechaFormTres = null;
                String periodoT = null;
                boolean estatusT = false;
                String fechaForm = null;
                boolean isEncargadoDos = false;
                boolean isSubEncargadoDos = false;
                boolean isEncargadoTres = false;
                boolean isSubCuatro = false;
                boolean pEstaVgDos = false;
                String idNombreFab = null;

                // List de la carpeta /Evaluacion_Riesgo

                // nodo
                SchemaNode node = null;
                SchemaNode node2 = null;
                SchemaNode node3 = null;
                if (value != null) {

                        Adaptation record = table.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(value));

                        principalesRiesgos = record.getList(
                                        Path.parse("./Evaluacion_Impacto_DPIA"));
                        eVRiesgoList = record.getList(Path.parse("./Evaluacion_Impacto_DPIA"));
                        pgrAnalisisImpacDPI = record.getList(Path.parse("./Evaluacion_Impacto_DPIA"));
                        heredaResponsableL = record.getList(Path.parse("./Datos_basico_tratamiento/Responsable"));
                        System.out.println("El valor del responsable  " + heredaResponsableL.size());
                        corresponsableH = record.getList(Path.parse("./Datos_basico_tratamiento/Correspondable"));
                        System.out.println("El valor del responsable  " + corresponsableH.size());
                        isEncargadoDos = record.get_boolean(Path.parse("./Valores/ValoresFAF/EncargadoFNF/EncargadoDeciiconFAF"));
                        servicioContratado = record.getList(Path.parse("./Valores/ValoresFAF/EncargadoFNF/ServicionCFAF"));

                        destinatarioEDos = record.getList(Path.parse("./Valores/ValoresFAF/EncargadoFNF/DestinatarioEFNF"));
                        operacionesEjecutaDos = record.getList(Path.parse("./Valores/ValoresFAF/EncargadoFNF/OperacionesEFAF"));
                        nomContDos = record.getList(Path.parse("./Valores/ValoresFAF/EncargadoFNF/NombreCEFAF"));
                        fechaDos = String.valueOf(record.getDate(Path.parse("./Valores/ValoresFAF/EncargadoFNF/FechaFEFAF")));
                        
                        periodoVigD = record.getString(Path.parse("./Valores/ValoresFAF/EncargadoFNF/PeriodoVEFAF"));
                        pEstaVgDos = record.get_boolean(Path.parse("./Valores/ValoresFAF/EncargadoFNF/EstatusVEFAF"));
                        isSubEncargadoDos = record.get_boolean(Path.parse("./Valores/ValoresFAF/SubENFAF/SubDeFAF"));
                        servConDos = record.getList(Path.parse("./Valores/ValoresFAF/SubENFAF/ServicioCSFAF"));
                        destDosA = record.getList(Path.parse("./Valores/ValoresFAF/SubENFAF/DestinatarioSubFAF"));
                        
                        operEjecSubDos = record.getList(Path.parse("./Valores/ValoresFAF/SubENFAF/OperacionesESubFAF"));
                        
                        nomContratoSub = record.getList(Path.parse("./Valores/ValoresFAF/SubENFAF/NombreCSUBFAF"));
                        
                        
                        fechaForm = String.valueOf(record.getDate(Path.parse("./Valores/ValoresFAF/SubENFAF/FechaFSubFAF")));
                       // fechaFormali = String.valueOf(record.getDate(Path.parse("./Comunicacionconsubencargado/Fechaformalizacionsubencargado")));
                        
                        
                        perioVigDos = record.getString(Path.parse("./Valores/ValoresFAF/SubENFAF/PeriodoVSubFAF"));

                        estatusVSubDos = record.get_boolean(Path.parse("./Valores/ValoresFAF/SubENFAF/EstatusVSubFAF"));
                        isEncargadoTres = record
                                        .get_boolean(Path.parse("./Valores/ValoresFCVD/EncargadoFCVD/EncargadoDFCVD"));
                        contratadoTres = record
                                        .getList(Path.parse("./Valores/ValoresFCVD/EncargadoFCVD/ServicioECFCVD"));
                        destinatarioTres = record
                                        .getList(Path.parse("./Valores/ValoresFCVD/EncargadoFCVD/DestinatarioEFCVD"));
                        operEjecutaTres = record
                                        .getList(Path.parse("./Valores/ValoresFCVD/EncargadoFCVD/OperacionesEEFCVD"));
                        nomConTres = record
                                        .getList(Path.parse("./Valores/ValoresFCVD/EncargadoFCVD/NombreCEFCVD"));

                        fechaFormTres = String.valueOf(record.getDate(Path.parse("./Valores/ValoresFAF/SubENFAF/FechaFSubFAF")));

                        periodoT = record.getString(Path.parse("./Valores/ValoresFCVD/EncargadoFCVD/PeriodoVEFCVD"));
                        estatusT = record.get_boolean(Path.parse("./Valores/ValoresFCVD/EncargadoFCVD/EstatusCEFCVD"));
                        isSubCuatro = record.get_boolean(Path.parse( "./Valores/ValoresFCVD/Subencargado/SubencargadoDesFCVD"));
                        serContratadoCu = record
                                        .getList(Path.parse("./Valores/ValoresFCVD/Subencargado/ServicioCSUBFCVD"));

                        DSunCuatro = record
                                        .getList(Path.parse("./Valores/ValoresFCVD/Subencargado/DestinatarioSUBFCVD"));
                        operCuatroEjec = record
                                        .getList(Path.parse("./Valores/ValoresFCVD/Subencargado/OperaSUBFCVD"));
                        nomConCuatro = record
                                        .getList(Path.parse("./Valores/ValoresFCVD/Subencargado/NombreCFCVD"));

                        fechaFormaCuatro = String.valueOf(record.getDate(Path.parse("./Valores/ValoresFCVD/Subencargado/FechaFFCVD")));

                        periodoVigCuatro = record
                                        .getString(Path.parse("./Valores/ValoresFCVD/Subencargado/PeriodoVSUBFCVD"));

                        estatusVCuatro = record
                                        .get_boolean(Path.parse("./Valores/ValoresFCVD/Subencargado/EstatusVSUBFCVD"));

                        destinatarioCP = record
                                        .getList(Path.parse("./Valores/ValoresFAF/CesionesFAF/DestinatariosCFAF"));
                        finalidadCuatroCP = record
                                        .getList(Path.parse("./Valores/ValoresFAF/CesionesFAF/FinalidadesCFAF"));

                        causaCP = record.getList(Path.parse("./Valores/ValoresFAF/CesionesFAF/CausasLCFAF"));
                        destCP5 = record.getList(Path.parse("./Valores/ValoresFCVD/CesionesFCVD/DestinatarioceFCVD"));

                        finalidadCP = record
                                        .getList(Path.parse("./Valores/ValoresFCVD/CesionesFCVD/FinalidadesCEFCVD"));
                        causaLeg = record
                                        .getList(Path.parse("./Valores/ValoresFCVD/CesionesFCVD/CausasLCFCVD"));

                        descripcionFT = record
                                        .getList(Path.parse("./Valores/ValoresFAF/DescripciondeFAF/DesFAF"));

                        descripcionFC = record
                                        .getList(Path.parse("./Valores/ValoresFCVD/DescripcionFCVD/DesFCVD"));
                        nombreFichaA = record
                                        .getString(Path.parse("./Datos_basico_tratamiento/Nombre"));

                        destinatario5FI = record
                                        .getList(Path.parse(
                                                        "./Valores/ValoresFAF/ParacederdatospersonalesFAF/DestinatariosPCDPFAF"));

                        causaLI = record.getList(Path.parse(
                                        "./Valores/ValoresFAF/ParacederdatospersonalesFAF/CausasLPCDFAF"));

                        destinatarioITT = record.getList(Path.parse(
                                        "./Valores/ValoresFAF/CesionesFAF/DestinatariosCFAF"));

                        finaliadITT = record.getList(Path.parse(
                                        "./Valores/ValoresFAF/CesionesFAF/FinalidadesCFAF"));
                        roles6 = record.getList(Path.parse(
                                        "./Valores/ValoresFAF/IntervinientesEXFAF/RolesIEFAF"));

                        operacionesEjN = record.getList(Path.parse(
                                        "./Valores/ValoresFAF/IntervinientesEXFAF/opeejeIEFAF"));
                        destinatarioFCI = record.getList(Path.parse(
                                        "./Valores/ValoresFCVD/ParacederdatospFCVD/DestinatarioCDPFCVD"));
                        causasLegitimas = record.getList(Path.parse(
                                        "./Valores/ValoresFCVD/ParacederdatospFCVD/CausasLCDPFCVD"));

                        destinatarioCVDITT = record.getList(Path.parse(
                                        "./Valores/ValoresFCVD/CesionesFCVD/DestinatarioceFCVD"));
                        finalidadesVDITT = record.getList(Path.parse(
                                        "./Valores/ValoresFCVD/CesionesFCVD/FinalidadesCEFCVD"));
                        roles12 = record.getList(Path.parse(
                                        "./Valores/ValoresFCVD/IntervFCVD/RolesFCVD"));
                        operaiJe = record.getList(Path.parse("./Valores/ValoresFCVD/IntervFCVD/OperaEFCVD"));
                        desFase4 = record.getList(Path.parse("./Valores/ValoresFAF/DescripciondeFAF/DesFAF"));

                        desFase5 = record.getList(Path.parse("./Valores/ValoresFCVD/DescripcionFCVD/DesFCVD"));

                        idNombreFab = record.getString(Path.parse("./Valores/IdentificadorFichas/IdentificadorFAB"));

                        // Path de la carpeta /Evaluacion_Riesgo
                        evaluacion = record.getList(Path
                                        .parse("./Evaluacion_Impacto_DPIA"));

                        evaluacion2 = record.getList(Path
                                        .parse("./Evaluacion_Impacto_DPIA"));
                        evaluacion3 = record.getList(Path
                                        .parse("./Evaluacion_Impacto_DPIA"));
                        // Path del nodo
                        node = record.getSchemaNode().getNode(Path
                                        .parse("./Evaluacion_Impacto_DPIA"));
                        node2 = record.getSchemaNode().getNode(Path
                                        .parse("./Evaluacion_Impacto_DPIA"));

                        node3 = record.getSchemaNode().getNode(Path
                                        .parse("./Evaluacion_Impacto_DPIA"));
                }
                UserServiceWriter aWriter = anAjaxResponse.getWriter();
                fechaDos= fechaDos == null ? "null" : "'" + fechaDos + "'";
                fechaForm = fechaForm == null ? "null" : "'" + fechaForm + "'";
                periodoVigD = periodoVigD == null ? "null" : "'" + periodoVigD + "'";
               // pEstaVgDos = pEstaVgDos == null ? "null" : "'" + pEstaVgDos + "'";
                perioVigDos = perioVigDos == null ? "null" : "'" + perioVigDos + "'";
              //  estatusVSubDos = estatusVSubDos == null ? "null" : "'" + estatusVSubDos + "'";
                fechaFormTres = fechaFormTres == null ? "null" : "'" + fechaFormTres + "'";
                periodoT = periodoT == null ? "null" : "'" + periodoT + "'";
               // estatusT = estatusT == null ? "null" : "'" + estatusT + "'";
                fechaFormaCuatro = fechaFormaCuatro == null ? "null" : "'" + fechaFormaCuatro + "'";
                periodoVigCuatro = periodoVigCuatro == null ? "null" : "'" + periodoVigCuatro + "'";
               // estatusVCuatro = estatusVCuatro == null ? "null" : "'" + estatusVCuatro + "'";
                nombreFichaA = nombreFichaA == null ? "null" : "'" + nombreFichaA + "'";
                idNombreFab = idNombreFab == null ? "null" : "'" + idNombreFab + "'";

 
                llenaCamposR(node, aWriter, evaluacion, pPrincipalesRiesgos, PRINCIPALES_RIESGOS);
                llenaCamposR(node2, aWriter, evaluacion2, pEvRiesgoImpc, EVANALISIS_RIESGO_IMP);
                llenaCamposR(node3, aWriter, evaluacion3, pPGRAnalisisDPIA, PGR_ANALISIS_IMPACDIP);



                aWriter.addJS_cr("heredaResponsableArray = [];");
                aWriter.addJS_cr("resetList('" + pHeredaResponsable + "', '" + RESPONSABLE_HEREDA + "');");
                if (heredaResponsableL != null) {

                        for (String heredaResponsable : heredaResponsableL) {

                                aWriter.addJS_cr("heredaResponsable = {");
                                aWriter.addJS_cr("key: '" + heredaResponsable + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("heredaResponsableArray.push(heredaResponsable);");

                        }
                        aWriter.addJS_cr("setList('" + pHeredaResponsable + "', '" + RESPONSABLE_HEREDA
                                        + "', heredaResponsableArray);");

                }

                aWriter.addJS_cr("corresponsableHArray = [];");
                aWriter.addJS_cr("resetList('" + pCorresponsable + "', '" + CORRESPONSABLE_HEREDA + "');");
                if (corresponsableH != null) {

                        for (String corresponsable : corresponsableH) {

                                aWriter.addJS_cr("corresponsable = {");
                                aWriter.addJS_cr("key: '" + corresponsable + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("corresponsableHArray.push(corresponsable);");

                        }
                        aWriter.addJS_cr("setList('" + pCorresponsable + "', '" + CORRESPONSABLE_HEREDA
                                        + "', corresponsableHArray);");

                }

                aWriter.addJS_cr("servicioContratadoArray = [];");
                aWriter.addJS_cr("resetList('" + pServContratado + "', '" + SERVICIO_CONTRATADO + "');");
                if (servicioContratado != null) {

                        for (String serContratado : servicioContratado) {

                                aWriter.addJS_cr("serContratado = {");
                                aWriter.addJS_cr("key: '" + serContratado + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("servicioContratadoArray.push(serContratado);");

                        }
                        aWriter.addJS_cr("setList2('" + pServContratado + "', '" + SERVICIO_CONTRATADO
                                        + "', servicioContratadoArray);");

                }

                aWriter.addJS_cr("destinatarioDosArray = [];");
                aWriter.addJS_cr("resetList('" + pDestinatarioDos + "', '" + DESTINATARIO_DOS + "');");
                if (destinatarioEDos != null) {

                        for (String destDos : destinatarioEDos) {

                                aWriter.addJS_cr("destDos = {");
                                aWriter.addJS_cr("key: '" + destDos + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destinatarioDosArray.push(destDos);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatarioDos + "', '" + DESTINATARIO_DOS
                                        + "', destinatarioDosArray);");

                }

                aWriter.addJS_cr("operacionesJecutaArray = [];");
                aWriter.addJS_cr("resetList('" + pOperEjecDos + "', '" + OPERACIONES_EJECUTA_DOS + "');");
                if (operacionesEjecutaDos != null) {

                        for (String ejectDos : operacionesEjecutaDos) {

                                aWriter.addJS_cr("ejectDos = {");
                                aWriter.addJS_cr("key: '" + ejectDos + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("operacionesJecutaArray.push(ejectDos);");

                        }
                        aWriter.addJS_cr("setList2('" + pOperEjecDos + "', '" + OPERACIONES_EJECUTA_DOS
                                        + "', operacionesJecutaArray);");

                }

                aWriter.addJS_cr("nomConDosAArray = [];");
                aWriter.addJS_cr("resetList('" + pNombreConDos + "', '" + NOM_CONT_DOS + "');");
                if (nomContDos != null) {

                        for (String nomContDosA : nomContDos) {

                                aWriter.addJS_cr("nomContDosA = {");
                                aWriter.addJS_cr("key: '" + nomContDosA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("nomConDosAArray.push(nomContDosA);");

                        }
                        aWriter.addJS_cr("setList2('" + pNombreConDos + "', '" + NOM_CONT_DOS
                                        + "', nomConDosAArray);");

                }
                
                
                
                
               /* aWriter.addJS_cr("fechaFrommArray = [];");
                aWriter.addJS_cr("resetList('" + pFechaDosA + "', '" + FECHA_ID + "');");
                if (fechaForm != null) {

                        for (String fechF : fechaForm) {

                                aWriter.addJS_cr("fechDos = {");
                                aWriter.addJS_cr("key: '" + fechF + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("fechaFrommArray.push(fechDos);");

                        }
                        aWriter.addJS_cr("setList('" + pFechaDosA + "', '"  + FECHA_ID
                                        + "', fechaFrommArray);");

                }
                
                
                aWriter.addJS_cr("fechatfArray = [];");
           aWriter.addJS_cr("resetList('" + pFechaTres + "', '" + FECHAT_ID + "');");
           if (fechaFormTres != null) {

                   for (String fechFT : fechaFormTres) {

                           aWriter.addJS_cr("fechDos = {");
                           aWriter.addJS_cr("key: '" + fechFT + "',");
                           aWriter.addJS_cr("label: '',");
                           aWriter.addJS_cr("previewURL: undefined");
                           aWriter.addJS_cr("};");

                           aWriter.addJS_cr("fechatfArray.push(fechDos);");

                   }
                   aWriter.addJS_cr("setList('" + pFechaTres + "', '" + FECHAT_ID
                                   + "', fechatfArray);");

           }
                
           aWriter.addJS_cr("fechacuatArray = [];");
           aWriter.addJS_cr("resetList('" + pFechaFprm + "', '" + FECHAC_ID + "');");
           if (fechaFormaCuatro != null) {

                   for (String fechct : fechaFormaCuatro) {

                           aWriter.addJS_cr("fechDos = {");
                           aWriter.addJS_cr("key: '" + fechct + "',");
                           aWriter.addJS_cr("label: '',");
                           aWriter.addJS_cr("previewURL: undefined");
                           aWriter.addJS_cr("};");

                           aWriter.addJS_cr("fechacuatArray.push(fechDos);");

                   }
                   aWriter.addJS_cr("setList('" + pFechaFprm + "', '" + FECHAC_ID
                                   + "', fechacuatArray);");

           }
                

                aWriter.addJS_cr("fechaDosArray = [];");
                aWriter.addJS_cr("resetList('" + pFechaDos + "', '" + FECHA_FORM_DOS + "');");
                if (fechaDos != null) {

                        for (String fechDos : fechaDos) {

                                aWriter.addJS_cr("fechDos = {");
                                aWriter.addJS_cr("key: '" + fechDos + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("fechaDosArray.push(fechDos);");

                        }
                        aWriter.addJS_cr("setList('" + pFechaDos + "', '" + FECHA_FORM_DOS
                                        + "', fechaDosArray);");

                }*/

                aWriter.addJS_cr("servConDosAArray = [];");
                aWriter.addJS_cr("resetList('" + pServDos + "', '" + SERV_CON_DOS + "');");
                if (servConDos != null) {

                        for (String servDos : servConDos) {

                                aWriter.addJS_cr("servDos = {");
                                aWriter.addJS_cr("key: '" + servDos + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("servConDosAArray.push(servDos);");

                        }
                        aWriter.addJS_cr("setList2('" + pServDos + "', '" + SERV_CON_DOS
                                        + "', servConDosAArray);");

                }

                aWriter.addJS_cr("destDosAAArray = [];");
                aWriter.addJS_cr("resetList('" + pDestDos + "', '" + DEST_SUN_DOS + "');");
                if (destDosA != null) {

                        for (String destDosAA : destDosA) {

                                aWriter.addJS_cr("destDosAA = {");
                                aWriter.addJS_cr("key: '" + destDosAA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destDosAAArray.push(destDosAA);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestDos + "', '" + DEST_SUN_DOS
                                        + "', destDosAAArray);");

                }

                aWriter.addJS_cr("operEjectSubDosAArray = [];");
                aWriter.addJS_cr("resetList('" + pOperEjecSubDos + "', '" + OPER_EJEC_SUB_DOS + "');");
                if (operEjecSubDos != null) {

                        for (String operEjecASub : operEjecSubDos) {

                                aWriter.addJS_cr("operEjecASub = {");
                                aWriter.addJS_cr("key: '" + operEjecASub + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("operEjectSubDosAArray.push(operEjecASub);");

                        }
                        aWriter.addJS_cr("setList2('" + pOperEjecSubDos + "', '" + OPER_EJEC_SUB_DOS
                                        + "', operEjectSubDosAArray);");

                }

                aWriter.addJS_cr("nomConDosAArray = [];");
                aWriter.addJS_cr("resetList('" + pNomDosContra + "', '" + NOM_DOS_CON + "');");
                if (nomContratoSub != null) {

                        for (String nomCon : nomContratoSub) {

                                aWriter.addJS_cr("nomCon = {");
                                aWriter.addJS_cr("key: '" + nomCon + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("nomConDosAArray.push(nomCon);");

                        }
                        aWriter.addJS_cr("setList2('" + pNomDosContra + "', '" + NOM_DOS_CON
                                        + "', nomConDosAArray);");

                }

                aWriter.addJS_cr("serContraTresArray = [];");
                aWriter.addJS_cr("resetList('" + pContraTres + "', '" + CONTRATADO_TRES + "');");
                if (contratadoTres != null) {

                        for (String nomConTresAAA : contratadoTres) {

                                aWriter.addJS_cr("nomConTresAAA = {");
                                aWriter.addJS_cr("key: '" + nomConTresAAA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("serContraTresArray.push(nomConTresAAA);");

                        }
                        aWriter.addJS_cr("setList2('" + pContraTres + "', '" + CONTRATADO_TRES
                                        + "', serContraTresArray);");

                }

                aWriter.addJS_cr("destTresArray = [];");
                aWriter.addJS_cr("resetList('" + pDestinatarioETres + "', '" + DESTINATARIO__3 + "');");
                if (destinatarioTres != null) {

                        for (String destinatarioTresA : destinatarioTres) {

                                aWriter.addJS_cr("destinatarioTresA = {");
                                aWriter.addJS_cr("key: '" + destinatarioTresA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destTresArray.push(destinatarioTresA);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatarioETres + "', '" + DESTINATARIO__3
                                        + "', destTresArray);");

                }

                aWriter.addJS_cr("operEjecArray = [];");
                aWriter.addJS_cr("resetList('" + pOperacionesTres + "', '" + OPERACIONES_TRES + "');");
                if (operEjecutaTres != null) {

                        for (String operEjecutaTresA : operEjecutaTres) {

                                aWriter.addJS_cr("operEjecutaTresA = {");
                                aWriter.addJS_cr("key: '" + operEjecutaTresA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("operEjecArray.push(operEjecutaTresA);");

                        }
                        aWriter.addJS_cr("setList2('" + pOperacionesTres + "', '" + OPERACIONES_TRES
                                        + "', operEjecArray);");

                }

                aWriter.addJS_cr("nomConTraArray = [];");
                aWriter.addJS_cr("resetList('" + pNomConTres + "', '" + nomConTraTres + "');");
                if (nomConTres != null) {

                        for (String nomConTresB : nomConTres) {

                                aWriter.addJS_cr("nomConTresB = {");
                                aWriter.addJS_cr("key: '" + nomConTresB + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("nomConTraArray.push(nomConTresB);");

                        }
                        aWriter.addJS_cr("setList2('" + pNomConTres + "', '" + nomConTraTres
                                        + "', nomConTraArray);");

                }

                aWriter.addJS_cr("serConCuatroaAArray = [];");
                aWriter.addJS_cr("resetList('" + pServCuatroList + "', '" + SERVICOCUATRO + "');");
                if (serContratadoCu != null) {

                        for (String servicoConCA : serContratadoCu) {

                                aWriter.addJS_cr("servicoConCA = {");
                                aWriter.addJS_cr("key: '" + servicoConCA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("serConCuatroaAArray.push(servicoConCA);");

                        }
                        aWriter.addJS_cr("setList2('" + pServCuatroList + "', '" + SERVICOCUATRO
                                        + "', serConCuatroaAArray);");

                }

                aWriter.addJS_cr("desCatroArrayAArray = [];");
                aWriter.addJS_cr("resetList('" + pDestCuatro + "', '" + DEST_SUN + "');");
                if (DSunCuatro != null) {

                        for (String DSunCuatroA : DSunCuatro) {

                                aWriter.addJS_cr("DSunCuatroA = {");
                                aWriter.addJS_cr("key: '" + DSunCuatroA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("desCatroArrayAArray.push(DSunCuatroA);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestCuatro + "', '" + DEST_SUN
                                        + "', desCatroArrayAArray);");

                }

                aWriter.addJS_cr("operEjecCuatroAAArray = [];");
                aWriter.addJS_cr("resetList('" + pOperEjecutaCuatro + "', '" + OPER_CUATRO + "');");
                if (operCuatroEjec != null) {

                        for (String operCuatroEjecA : operCuatroEjec) {

                                aWriter.addJS_cr("operCuatroEjecA = {");
                                aWriter.addJS_cr("key: '" + operCuatroEjecA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("operEjecCuatroAAArray.push(operCuatroEjecA);");

                        }
                        aWriter.addJS_cr("setList2('" + pOperEjecutaCuatro + "', '" + OPER_CUATRO
                                        + "', operEjecCuatroAAArray);");

                }

                aWriter.addJS_cr("nomConAAArray = [];");
                aWriter.addJS_cr("resetList('" + pNombCCuatro + "', '" + NOM_CON_CUA + "');");
                if (nomConCuatro != null) {

                        for (String nomConCuatroAA : nomConCuatro) {

                                aWriter.addJS_cr("nomConCuatroAA = {");
                                aWriter.addJS_cr("key: '" + nomConCuatroAA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("nomConAAArray.push(nomConCuatroAA);");

                        }
                        aWriter.addJS_cr("setList2('" + pNombCCuatro + "', '" + NOM_CON_CUA
                                        + "', nomConAAArray);");

                }

                // aWriter.addJS_cr("destinatarioCpAAArray = [];");
                // aWriter.addJS_cr("resetList('" + pDestinatarioCP2 + "', '" +
                // DESTINATARIO_CP_2 + "');");
                // if (destinatarioCP != null) {

                // for (String destinatarioCPA : destinatarioCP) {

                // aWriter.addJS_cr("destinatarioCPA = {");
                // aWriter.addJS_cr("key: '" + destinatarioCPA + "',");
                // aWriter.addJS_cr("label: '',");
                // aWriter.addJS_cr("previewURL: undefined");
                // aWriter.addJS_cr("};");

                // aWriter.addJS_cr("destinatarioCpAAArray.push(destinatarioCPA);");

                // }
                // aWriter.addJS_cr("setList('" + pDestinatarioCP2 + "', '" + DESTINATARIO_CP_2
                // + "', destinatarioCpAAArray);");

                // }

                aWriter.addJS_cr("finalidadesCPAAArray = [];");
                aWriter.addJS_cr("resetList('" + pFinalidadCuatroA + "', '" + FINALIDADES_ACP + "');");
                if (finalidadCuatroCP != null) {

                        for (String finalidadCuatroCPA : finalidadCuatroCP) {

                                aWriter.addJS_cr("finalidadCuatroCPA = {");
                                aWriter.addJS_cr("key: '" + finalidadCuatroCPA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("finalidadesCPAAArray.push(finalidadCuatroCPA);");

                        }
                        aWriter.addJS_cr("setList2('" + pFinalidadCuatroA + "', '" + FINALIDADES_ACP
                                        + "', finalidadesCPAAArray);");

                }

                aWriter.addJS_cr("causaCPArray = [];");
                aWriter.addJS_cr("resetList('" + pCausaCP + "', '" + CAUSA_CP + "');");
                if (causaCP != null) {

                        for (String causaCPA : causaCP) {

                                aWriter.addJS_cr("causaCPA = {");
                                aWriter.addJS_cr("key: '" + causaCPA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("causaCPArray.push(causaCPA);");

                        }
                        aWriter.addJS_cr("setList2('" + pCausaCP + "', '" + CAUSA_CP
                                        + "', causaCPArray);");

                }

                aWriter.addJS_cr("desCP5Array = [];");
                aWriter.addJS_cr("resetList('" + pDestinatarioCP3 + "', '" + DESTINATARIOCP + "');");
                if (destCP5 != null) {

                        for (String destCP5AA : destCP5) {

                                aWriter.addJS_cr("destCP5AA = {");
                                aWriter.addJS_cr("key: '" + destCP5AA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("desCP5Array.push(causaCPA);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatarioCP3 + "', '" + DESTINATARIOCP
                                        + "', desCP5Array);");

                }

                aWriter.addJS_cr("finalidadCPArray = [];");
                aWriter.addJS_cr("resetList('" + pfinalidadesCP + "', '" + FINALIDADCP + "');");
                if (finalidadCP != null) {

                        for (String finalidadCPA : finalidadCP) {

                                aWriter.addJS_cr("finalidadCPA = {");
                                aWriter.addJS_cr("key: '" + finalidadCPA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("finalidadCPArray.push(finalidadCPA);");

                        }
                        aWriter.addJS_cr("setList2('" + pfinalidadesCP + "', '" + FINALIDADCP
                                        + "', finalidadCPArray);");

                }

                aWriter.addJS_cr("causaLegAArray = [];");
                aWriter.addJS_cr("resetList('" + pCausaLeg + "', '" + CAUSA_LEG + "');");
                if (causaLeg != null) {

                        for (String causaLegA : causaLeg) {

                                aWriter.addJS_cr("causaLegA = {");
                                aWriter.addJS_cr("key: '" + causaLegA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("causaLegAArray.push(causaLegA);");

                        }
                        aWriter.addJS_cr("setList2('" + pCausaLeg + "', '" + CAUSA_LEG
                                        + "', causaLegAArray);");

                }

                aWriter.addJS_cr("DescripcionFTArray = [];");
                aWriter.addJS_cr("resetList('" + pDescripcionFT + "', '" + DESCRIPTIONFT + "');");
                if (descripcionFT != null) {

                        for (String descripcionFTA : descripcionFT) {

                                aWriter.addJS_cr("descripcionFTA = {");
                                aWriter.addJS_cr("key: '" + descripcionFTA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("DescripcionFTArray.push(descripcionFTA);");

                        }
                        aWriter.addJS_cr("setList2('" + pDescripcionFT + "', '" + DESCRIPTIONFT
                                        + "', DescripcionFTArray);");

                }

                aWriter.addJS_cr("descripcionFcArray = [];");
                aWriter.addJS_cr("resetList('" + pDescripcionFC + "', '" + DESCFC + "');");
                if (descripcionFC != null) {

                        for (String descripcionFCA : descripcionFC) {

                                aWriter.addJS_cr("descripcionFCA = {");
                                aWriter.addJS_cr("key: '" + descripcionFCA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("descripcionFcArray.push(descripcionFCA);");

                        }
                        aWriter.addJS_cr("setList2('" + pDescripcionFC + "', '" + DESCFC
                                        + "', descripcionFcArray);");

                }

                aWriter.addJS_cr("destinatario5FIArray = [];");
                aWriter.addJS_cr("resetList('" + pDestinatario5F + "', '" + DESTINATARIO_5IF + "');");
                if (destinatario5FI != null) {

                        for (String destinatario5FIA : destinatario5FI) {

                                aWriter.addJS_cr("destinatario5FIA = {");
                                aWriter.addJS_cr("key: '" + destinatario5FIA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destinatario5FIArray.push(destinatario5FIA);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatario5F + "', '" + DESTINATARIO_5IF
                                        + "', destinatario5FIArray);");

                }

                aWriter.addJS_cr("causaLIArray = [];");
                aWriter.addJS_cr("resetList('" + pCausaLI + "', '" + CAUSAL_I + "');");
                if (causaLI != null) {

                        for (String causaLIA : causaLI) {

                                aWriter.addJS_cr("causaLIA = {");
                                aWriter.addJS_cr("key: '" + causaLIA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("causaLIArray.push(causaLIA);");

                        }
                        aWriter.addJS_cr("setList2('" + pCausaLI + "', '" + CAUSAL_I
                                        + "', causaLIArray);");

                }

                aWriter.addJS_cr("destinatarioIttArray = [];");
                aWriter.addJS_cr("resetList('" + pDestinatarioTT + "', '" + DESTINATARIO_ITT + "');");
                if (destinatarioITT != null) {

                        for (String destinatarioITTA : destinatarioITT) {

                                aWriter.addJS_cr("destinatarioITTA = {");
                                aWriter.addJS_cr("key: '" + destinatarioITTA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destinatarioIttArray.push(destinatarioITTA);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatarioTT + "', '" + DESTINATARIO_ITT
                                        + "', destinatarioIttArray);");

                }

                aWriter.addJS_cr("finalidadITTArray = [];");
                aWriter.addJS_cr("resetList('" + pFinalidad_ITT + "', '" + FINALIDAD_ITT + "');");
                if (finaliadITT != null) {

                        for (String finaliadITTA : finaliadITT) {

                                aWriter.addJS_cr("finaliadITTA = {");
                                aWriter.addJS_cr("key: '" + finaliadITTA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("finalidadITTArray.push(finaliadITTA);");

                        }
                        aWriter.addJS_cr("setList2('" + pFinalidad_ITT + "', '" + FINALIDAD_ITT
                                        + "', finalidadITTArray);");

                }

                // aWriter.addJS_cr("roles6Array = [];");
                // aWriter.addJS_cr("resetList('" + pRoles6 + "', '" + ROLES_6 + "');");
                // if (roles6 != null) {

                // for (String roles6A : roles6) {

                // aWriter.addJS_cr("roles6A = {");
                // aWriter.addJS_cr("key: '" + roles6A + "',");
                // aWriter.addJS_cr("label: '',");
                // aWriter.addJS_cr("previewURL: undefined");
                // aWriter.addJS_cr("};");

                // aWriter.addJS_cr("roles6Array.push(roles6A);");

                // }
                // aWriter.addJS_cr("setList('" + pRoles6 + "', '" + ROLES_6
                // + "', roles6Array);");

                // }

                aWriter.addJS_cr("operEjecutaLNArray = [];");
                aWriter.addJS_cr("resetList('" + pOperEjecuta + "', '" + OPERACIONES_EJEC + "');");
                if (operacionesEjN != null) {

                        for (String operacionesEjNA : operacionesEjN) {

                                aWriter.addJS_cr("operacionesEjNA = {");
                                aWriter.addJS_cr("key: '" + operacionesEjNA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("operEjecutaLNArray.push(operacionesEjNA);");

                        }
                        aWriter.addJS_cr("setList2('" + pOperEjecuta + "', '" + OPERACIONES_EJEC
                                        + "', operEjecutaLNArray);");

                }

                aWriter.addJS_cr("destinatarioFCIAArray = [];");
                aWriter.addJS_cr("resetList('" + pDestinatarioFCI + "', '" + DESTINATARIO_FCI + "');");
                if (destinatarioFCI != null) {

                        for (String destinatarioFCIA : destinatarioFCI) {

                                aWriter.addJS_cr("destinatarioFCIA = {");
                                aWriter.addJS_cr("key: '" + destinatarioFCIA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destinatarioFCIAArray.push(destinatarioFCIA);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatarioFCI + "', '" + DESTINATARIO_FCI
                                        + "', destinatarioFCIAArray);");

                }

                aWriter.addJS_cr("causasLegArray = [];");
                aWriter.addJS_cr("resetList('" + pCausasLegitimas + "', '" + CAUSAS_LEGITIMAS + "');");
                if (causasLegitimas != null) {

                        for (String causasLegitimasA : causasLegitimas) {

                                aWriter.addJS_cr("causasLegitimasA = {");
                                aWriter.addJS_cr("key: '" + causasLegitimasA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("causasLegArray.push(causasLegitimasA);");

                        }
                        aWriter.addJS_cr("setList2('" + pCausasLegitimas + "', '" + CAUSAS_LEGITIMAS
                                        + "', causasLegArray);");

                }

                aWriter.addJS_cr("destinatarioCVDTTIArray = [];");
                aWriter.addJS_cr("resetList('" + pDestinatarioCVDT + "', '" + DESTINATARIO_CDVDITT + "');");
                if (destinatarioCVDITT != null) {

                        for (String destinatarioCVDITTA : destinatarioCVDITT) {

                                aWriter.addJS_cr("destinatarioCVDITTA = {");
                                aWriter.addJS_cr("key: '" + destinatarioCVDITTA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("destinatarioCVDTTIArray.push(destinatarioCVDITTA);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestinatarioCVDT + "', '" + DESTINATARIO_CDVDITT
                                        + "', destinatarioCVDTTIArray);");

                }

                aWriter.addJS_cr("finalidadesCVDTTIArray = [];");
                aWriter.addJS_cr("resetList('" + pFinalidadesCVDT + "', '" + FINALIDADES_CDVDITT + "');");
                if (finalidadesVDITT != null) {

                        for (String finalidadesVDITTA : finalidadesVDITT) {

                                aWriter.addJS_cr("finalidadesVDITTA = {");
                                aWriter.addJS_cr("key: '" + finalidadesVDITTA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("finalidadesCVDTTIArray.push(finalidadesVDITTA);");

                        }
                        aWriter.addJS_cr("setList2('" + pFinalidadesCVDT + "', '" + FINALIDADES_CDVDITT
                                        + "', finalidadesCVDTTIArray);");

                }

                aWriter.addJS_cr("roles12Array = [];");
                aWriter.addJS_cr("resetList('" + pRoles12 + "', '" + ROLES12 + "');");
                if (roles12 != null) {

                        for (String roles12A : roles12) {

                                aWriter.addJS_cr("roles12A = {");
                                aWriter.addJS_cr("key: '" + roles12A + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("roles12Array.push(roles12A);");

                        }
                        aWriter.addJS_cr("setList2('" + pRoles12 + "', '" + ROLES12
                                        + "', roles12Array);");

                }

                aWriter.addJS_cr("operaijeArray = [];");
                aWriter.addJS_cr("resetList('" + pOperaEJIE + "', '" + OPERAJIE + "');");
                if (operaiJe != null) {

                        for (String operaiJeA : operaiJe) {

                                aWriter.addJS_cr("operaiJeA = {");
                                aWriter.addJS_cr("key: '" + operaiJeA + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("operaijeArray.push(operaiJeA);");

                        }
                        aWriter.addJS_cr("setList2('" + pOperaEJIE + "', '" + OPERAJIE
                                        + "', operaijeArray);");

                }

                aWriter.addJS_cr("desFase4Array = [];");
                aWriter.addJS_cr("resetList('" + pDestFase4 + "', '" + DESFASE4 + "');");
                if (desFase4 != null) {

                        for (String desFase4A : desFase4) {

                                aWriter.addJS_cr("desFase4A = {");
                                aWriter.addJS_cr("key: '" + desFase4A + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("desFase4Array.push(desFase4A);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestFase4 + "', '" + DESFASE4
                                        + "', desFase4Array);");

                }

                aWriter.addJS_cr("desFase5Array = [];");
                aWriter.addJS_cr("resetList('" + pDestFase5 + "', '" + DESFASE5 + "');");
                if (desFase5 != null) {

                        for (String desFase5A : desFase5) {

                                aWriter.addJS_cr("desFase5A = {");
                                aWriter.addJS_cr("key: '" + desFase5A + "',");
                                aWriter.addJS_cr("label: '',");
                                aWriter.addJS_cr("previewURL: undefined");
                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr("desFase5Array.push(desFase5A);");

                        }
                        aWriter.addJS_cr("setList2('" + pDestFase5 + "', '" + DESFASE5
                                        + "', desFase5Array);");

                }

                // aWriter.addJS_cr("perioSubAArray = [];");
                // aWriter.addJS_cr("resetList('" + pVigSubDos + "', '" + PERIODO_VIDOS +
                // "');");
                // if (perioVigDos != null) {

                // for (String perioVigDosA : perioVigDos) {

                // aWriter.addJS_cr("perioVigDosA = {");
                // aWriter.addJS_cr("key: '" + perioVigDosA + "',");
                // aWriter.addJS_cr("label: '',");
                // aWriter.addJS_cr("previewURL: undefined");
                // aWriter.addJS_cr("};");

                // aWriter.addJS_cr("perioSubAArray.push(perioVigDosA);");

                // }
                // aWriter.addJS_cr("setList('" + pVigSubDos + "', '" + PERIODO_VIDOS
                // + "', perioSubAArray);");

                // }

                // aWriter.addJS_cr("estatusAVArray = [];");
                // aWriter.addJS_cr("resetList('" + pStatusVDos + "', '" + ESTATUSS_V_DOS +
                // "');");
                // if (estatusVSubDos != null) {

                // for (String estatusDos : estatusVSubDos) {

                // aWriter.addJS_cr("estatusDos = {");
                // aWriter.addJS_cr("key: '" + estatusDos + "',");
                // aWriter.addJS_cr("label: '',");
                // aWriter.addJS_cr("previewURL: undefined");
                // aWriter.addJS_cr("};");

                // aWriter.addJS_cr("estatusAVArray.push(estatusDos);");

                // }
                // aWriter.addJS_cr("setList('" + pStatusVDos + "', '" + ESTATUSS_V_DOS
                // + "', estatusAVArray);");

                // }
               // aWriter.addJS_cr("ebx_form_setValue('" + pEncargadoDos + "', " + pEstaVgDos + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pFechaDos + "', " + fechaDos + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pEncargadoDos + "', " + isEncargadoDos + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pSubDos + "', " + isSubEncargadoDos + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pFechaDosA + "', " + fechaForm + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pVigDos + "', " + periodoVigD + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pEstatusDos + "', " + pEstaVgDos + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pVigSubDos + "', " + perioVigDos + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pStatusVDos + "', " + estatusVSubDos + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pEncargadoTres + "', " + isEncargadoTres + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pFechaTres + "', " + fechaFormTres + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pPeriodoTresVig + "', " + periodoT + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pEstatusT + "', " + estatusT + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pSubCuatro + "', " + isSubCuatro + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pFechaFprm + "', " + fechaFormaCuatro + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pPeriodoVCuatro + "', " + periodoVigCuatro + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pEstatusCuatro + "', " + estatusVCuatro + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pIdNombreFAB + "', " + idNombreFab + ")");
                aWriter.addJS_cr("ebx_form_setValue('" + pNomFichaAnalisis + "', " + nombreFichaA + ")");

                aWriter.addJS_cr("console.log('Hola mundo')");

        }

        /**
         * Metodo que es utilizado para realizar el mapeo de los registro
         * de la carpeta de Evaluacion_Impacto_DPIA
         * 
         * 
         * @param SchemaNode        node
         * @param UserServiceWriter aWriter
         * @param List<Object>      evaluacion
         * @param String            prefix
         * @param String            idList
         * @return
         * @throws
         */
        private void llenaCamposR(SchemaNode node, UserServiceWriter aWriter, List<Object> evaluacion, String prefix,
                        String idList) {

            // Campos de Analsisi de Impacto
            String objePath = "ObjetivosAIP";
            String riesPath = "RiesgoARE";
            String fechIdenPath = "Fecha_iden";
            String probInhePath = "Probabilidad_inhe";
            String tiposDatosPath = "Tipo_de_datos";
            String cantidadDatosPath = "Cantidad_de_datos";
            String categoriaTitularesPath = "Categoria_de_titulare";
            String cantidadtitularesPath = "Cantidad_de_titulares";
            
            String tipoImpactoPath = "Tipo_de_impacto";
            String expoInherTDatoPath = "Exposicion_inerente_tipo_de_dato";
            String expoInheCantDPath = "Exposicion_inherente_cantidad_de_datos";
            String expoInheCantTituDPath = "Exposicion_inherente_categoria_de_titulares";
            String expoInheCantTituD2Path = "Exposicion_inherente_cantidad_de_los_titulares";
            String expoInheTpImpaDPath = "Exposicion_inherente_tipo_de_impacto";
            String expoInheResultpaDPath = "ExpoIneherenteResult";
            String clasificacion2aPath = "Clasificacion2";
            String controlInstaPath = "Control_instalado_DPIA";
            String probabilidadRPath = "Probabilidad_residual";
            String tipoDatoRPath = "Tipo_de_dato_R";
            String cantidadDatoRPath = "Cantidad_de_datos_R";
            String categoriaTitularesRPath = "Categoria_de_los_titulares_R";
            String cantidadRitularesRPath = "Cantidad_de_los_titulares_r";
            String tipoImpactoRPath = "Tipo_de_impacto_R";
            String expoResidualTpDatoPath = "Exposicion_recidual_tipo_de_dato";
            String expoResidualTpDatoCanPath = "Exposicion_recidual_cantidad_de_datos";
            String expoRecCatTitularesPath = "Exposicion_recidual_categoria_de_los_titulares";
            String expoRecCanTitularesPath = "Exposicion_recidual_cantidad_de_los_titulares";
            String expoRecTpImpactoPath = "Exposicion_recidual_tipo_de_impacto";
            String expoResResultadoPath = "ExposicionResidualResultado";
            String clasificacion5Path = "Clasificacion5";
            String accControlSPath = "Accion_Control_sugerido";
            String responsablePath = "Responsable";
            String estadoPath = "Estado";
            String fechaEstimadaImpPath = "Fecha_Estimada_Implementacion2";
            String kri1ImpPath = "KRI1_por_objetivo";
            String kri2Path = "KRI2_por_objetivo";

                // Creacion de Array de Evaluacion_Riesgo
                final String pathArray = idList.concat("PathArray");
                aWriter.addJS_cr(idList.concat("Array") + " = [];");
                aWriter.addJS_cr(pathArray + "= [];");
                aWriter.addJS_cr(pathArray + ".push('" + objePath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + riesPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + fechIdenPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + probInhePath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + tiposDatosPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + cantidadDatosPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + categoriaTitularesPath + "');");
                
                aWriter.addJS_cr(pathArray + ".push('" + cantidadtitularesPath + "');");
                
                aWriter.addJS_cr(pathArray + ".push('" + tipoImpactoPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + expoInherTDatoPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + expoInheCantDPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + expoInheCantTituDPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + expoInheCantTituD2Path + "');");
                aWriter.addJS_cr(pathArray + ".push('" + expoInheTpImpaDPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + expoInheResultpaDPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + clasificacion2aPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + controlInstaPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + probabilidadRPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + tipoDatoRPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + cantidadDatoRPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + categoriaTitularesRPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + cantidadRitularesRPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + tipoImpactoRPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + expoResidualTpDatoPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + expoResidualTpDatoCanPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + expoRecCatTitularesPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + expoRecCanTitularesPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + expoRecTpImpactoPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + expoResResultadoPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + clasificacion5Path + "');");
                aWriter.addJS_cr(pathArray + ".push('" + accControlSPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + responsablePath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + estadoPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + fechaEstimadaImpPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + kri1ImpPath + "');");
                aWriter.addJS_cr(pathArray + ".push('" + kri2Path + "');");

                aWriter.addJS_cr("resetComplexList('" + prefix + "'," + pathArray + ", '"
                                + idList + "');");

                if (evaluacion != null) {
                        for (Object eval : evaluacion) {

                                String objetivo = String.valueOf(node.getNode(Path.parse(objePath)).executeRead(eval));
                                if (objetivo.isBlank() || objetivo.equals("null")) {
                                        objetivo = "null";
                                        System.out.println("Valor del riesgo null");
                                } else {
                                        objetivo = "{key: '" + objetivo + "', label: '', previewURL: undefined}";
                                        System.out.println("Sin datos");
                                }

                                String riesgo = String.valueOf(node.getNode(Path.parse(riesPath)).executeRead(eval));
                                if (riesgo.isBlank() || riesgo.equals("null")) {
                                        riesgo = "null";
                                        System.out.println("Valor del riesgo null");
                                } else {
                                        riesgo = "{key: '" + riesgo + "', label: '', previewURL: undefined}";
                                        System.out.println("Sin datos");
                                }
                                String fecha = String.valueOf(node.getNode(Path.parse(fechIdenPath)).executeRead(eval));
                                if (fecha.isBlank() || fecha.equals("null"))
                                        fecha = "null";
                                else
                                        fecha = "new Date('" + fecha + "')";

                                String probInhe = String
                                                .valueOf(node.getNode(Path.parse(probInhePath)).executeRead(eval));
                                if (probInhe.isBlank() || probInhe.equals("null"))
                                        probInhe = "null";
                                else
                                        probInhe = "'" + probInhe + "'";

                                String tiposDatos = String
                                                .valueOf(node.getNode(Path.parse(tiposDatosPath)).executeRead(eval));
                                if (tiposDatos.isBlank() || tiposDatos.equals("null"))
                                        tiposDatos = "null";
                                else
                                        tiposDatos = "'" + tiposDatos + "'";

                                String cantDatos = String
                                                .valueOf(node.getNode(Path.parse(cantidadDatosPath)).executeRead(eval));
                                if (cantDatos.isBlank() || cantDatos.equals("null"))
                                        cantDatos = "null";
                                else
                                        cantDatos = "'" + cantDatos + "'";

                              
                                String catTitulares = String
                                                .valueOf(node.getNode(Path.parse(categoriaTitularesPath))
                                                                .executeRead(eval));
                                if (catTitulares.isBlank() || catTitulares.equals("null"))
                                        catTitulares = "null";
                                else
                                        catTitulares = "'" + catTitulares + "'";

                               
                                String canTitulares = String
                                        .valueOf(node.getNode(Path.parse(cantidadtitularesPath))
                                                        .executeRead(eval));
                        if (canTitulares.isBlank() || canTitulares.equals("null"))
                        	canTitulares = "null";
                        else
                        	canTitulares = "'" + canTitulares + "'";
                                
                                
                                
                                
                                
                                String tpImpac = String
                                                .valueOf(node.getNode(Path.parse(tipoImpactoPath)).executeRead(eval));
                                if (tpImpac.isBlank() || tpImpac.equals("null"))
                                        tpImpac = "null";
                                else
                                        tpImpac = "'" + tpImpac + "'";

                                String expoInhe = String
                                                .valueOf(node.getNode(Path.parse(expoInherTDatoPath))
                                                                .executeRead(eval));
                                if (expoInhe.isBlank() || expoInhe.equals("null"))
                                        expoInhe = "null";
                                else
                                        expoInhe = "'" + expoInhe + "'";

                                String expoInheCant = String
                                                .valueOf(node.getNode(Path.parse(expoInheCantDPath))
                                                                .executeRead(eval));
                                if (expoInheCant.isBlank() || expoInheCant.equals("null"))
                                        expoInheCant = "null";
                                else
                                        expoInheCant = "'" + expoInheCant + "'";

                                String expoInheCantTitular = String
                                                .valueOf(node.getNode(Path.parse(expoInheCantTituDPath))
                                                                .executeRead(eval));
                                if (expoInheCantTitular.isBlank() || expoInheCantTitular.equals("null"))
                                        expoInheCantTitular = "null";
                                else
                                        expoInheCantTitular = "'" + expoInheCantTitular + "'";

                                String expoInheCantTitular2 = String
                                                .valueOf(node.getNode(Path.parse(expoInheCantTituD2Path))
                                                                .executeRead(eval));
                                if (expoInheCantTitular2.isBlank() || expoInheCantTitular2.equals("null"))
                                        expoInheCantTitular2 = "null";
                                else
                                        expoInheCantTitular2 = "'" + expoInheCantTitular2 + "'";

                                String expoInheTpImpac = String
                                                .valueOf(node.getNode(Path.parse(expoInheTpImpaDPath))
                                                                .executeRead(eval));
                                if (expoInheTpImpac.isBlank() || expoInheTpImpac.equals("null"))
                                        expoInheTpImpac = "null";
                                else
                                        expoInheTpImpac = "'" + expoInheTpImpac + "'";

                                String expoInheResult = String
                                                .valueOf(node.getNode(Path.parse(expoInheResultpaDPath))
                                                                .executeRead(eval));
                                if (expoInheResult.isBlank() || expoInheResult.equals("null"))
                                        expoInheResult = "null";
                                else
                                        expoInheResult = "'" + expoInheResult + "'";


                                String clasificacion2 = String
                                                .valueOf(node.getNode(Path.parse(clasificacion2aPath))
                                                                .executeRead(eval));
                                if (clasificacion2.isBlank() || clasificacion2.equals("null"))
                                        clasificacion2 = "null";
                                else
                                        clasificacion2 = "'" + clasificacion2 + "'";

                                String controlInsa = String
                                                .valueOf(node.getNode(Path.parse(controlInstaPath)).executeRead(eval));
                                if (controlInsa.isBlank() || controlInsa.equals("null")) {
                                        controlInsa = "null";
                                        System.out.println("Valor del riesgo null");
                                } else {
                                        controlInsa = "{key: '" + controlInsa + "', label: '', previewURL: undefined}";
                                        System.out.println("Sin datos");
                                }

                                String probabilidadR = String
                                                .valueOf(node.getNode(Path.parse(probabilidadRPath))
                                                                .executeRead(eval));
                                if (probabilidadR.isBlank() || probabilidadR.equals("null"))
                                        probabilidadR = "null";
                                else
                                        probabilidadR = "'" + probabilidadR + "'";

                                String tipoDatoR = String
                                                .valueOf(node.getNode(Path.parse(tipoDatoRPath))
                                                                .executeRead(eval));
                                if (tipoDatoR.isBlank() || tipoDatoR.equals("null"))
                                        tipoDatoR = "null";
                                else
                                        tipoDatoR = "'" + tipoDatoR + "'";

                                String cantidadDatoR = String
                                                .valueOf(node.getNode(Path.parse(cantidadDatoRPath))
                                                                .executeRead(eval));
                                if (cantidadDatoR.isBlank() || cantidadDatoR.equals("null"))
                                        cantidadDatoR = "null";
                                else
                                        cantidadDatoR = "'" + cantidadDatoR + "'";

                                String catTitularesR = String
                                                .valueOf(node.getNode(Path.parse(categoriaTitularesRPath))
                                                                .executeRead(eval));
                                if (catTitularesR.isBlank() || catTitularesR.equals("null"))
                                        catTitularesR = "null";
                                else
                                        catTitularesR = "'" + catTitularesR + "'";

                                String cantidadTituR = String
                                                .valueOf(node.getNode(Path.parse(cantidadRitularesRPath))
                                                                .executeRead(eval));
                                if (cantidadTituR.isBlank() || cantidadTituR.equals("null"))
                                        cantidadTituR = "null";
                                else
                                        cantidadTituR = "'" + cantidadTituR + "'";

                                String tipoImpactoR = String
                                                .valueOf(node.getNode(Path.parse(tipoImpactoRPath))
                                                                .executeRead(eval));
                                if (tipoImpactoR.isBlank() || tipoImpactoR.equals("null"))
                                        tipoImpactoR = "null";
                                else
                                        tipoImpactoR = "'" + tipoImpactoR + "'";

                                String expoResidualR = String
                                                .valueOf(node.getNode(Path.parse(expoResidualTpDatoPath))
                                                                .executeRead(eval));
                                if (expoResidualR.isBlank() || expoResidualR.equals("null"))
                                        expoResidualR = "null";
                                else
                                        expoResidualR = "'" + expoResidualR + "'";

                                String expoRTpDato = String
                                                .valueOf(node.getNode(Path.parse(expoResidualTpDatoCanPath))
                                                                .executeRead(eval));
                                if (expoRTpDato.isBlank() || expoRTpDato.equals("null"))
                                        expoRTpDato = "null";
                                else
                                        expoRTpDato = "'" + expoRTpDato + "'";

                                String expoRecCantTitulares = String
                                                .valueOf(node.getNode(Path.parse(expoRecCatTitularesPath))
                                                                .executeRead(eval));
                                if (expoRecCantTitulares.isBlank() || expoRecCantTitulares.equals("null"))
                                        expoRecCantTitulares = "null";
                                else
                                        expoRecCantTitulares = "'" + expoRecCantTitulares + "'";

                                String expoRecCanTitulares = String
                                                .valueOf(node.getNode(Path.parse(expoRecCanTitularesPath))
                                                                .executeRead(eval));
                                if (expoRecCanTitulares.isBlank() || expoRecCanTitulares.equals("null"))
                                        expoRecCanTitulares = "null";
                                else
                                        expoRecCanTitulares = "'" + expoRecCanTitulares + "'";

                                String expoRecTpImpacto = String
                                                .valueOf(node.getNode(Path.parse(expoRecTpImpactoPath))
                                                                .executeRead(eval));
                                if (expoRecTpImpacto.isBlank() || expoRecTpImpacto.equals("null"))
                                        expoRecTpImpacto = "null";
                                else
                                        expoRecTpImpacto = "'" + expoRecTpImpacto + "'";

                                String expoResResultado = String
                                                .valueOf(node.getNode(Path.parse(expoResResultadoPath))
                                                                .executeRead(eval));
                                if (expoResResultado.isBlank() || expoResResultado.equals("null"))
                                        expoResResultado = "null";
                                else
                                        expoResResultado = "'" + expoResResultado + "'";

           

                                String clasificacion5 = String
                                                .valueOf(node.getNode(Path.parse(clasificacion5Path))
                                                                .executeRead(eval));
                                if (clasificacion5.isBlank() || clasificacion5.equals("null"))
                                        clasificacion5 = "null";
                                else
                                        clasificacion5 = "'" + clasificacion5 + "'";

                                String accControl = String
                                                .valueOf(node.getNode(Path.parse(accControlSPath)).executeRead(eval));
                                if (accControl.isBlank() || accControl.equals("null")) {
                                        accControl = "null";
                                        System.out.println("Valor del riesgo null");
                                } else {
                                        accControl = "{key: '" + accControl + "', label: '', previewURL: undefined}";
                                        System.out.println("Sin datos");
                                }

                                String responsable = String
                                                .valueOf(node.getNode(Path.parse(responsablePath)).executeRead(eval));
                                if (responsable.isBlank() || responsable.equals("null")) {
                                        responsable = "null";
                                        System.out.println("Valor del riesgo null");
                                } else {
                                        responsable = "{key: '" + responsable + "', label: '', previewURL: undefined}";
                                        System.out.println("Sin datos");
                                }

                                String estado = String.valueOf(node.getNode(Path.parse(estadoPath)).executeRead(eval));
                                if (estado.isBlank() || estado.equals("null")) {
                                        estado = "null";
                                        System.out.println("Valor del riesgo null");
                                } else {
                                        estado = "{key: '" + estado + "', label: '', previewURL: undefined}";
                                        System.out.println("Sin datos");
                                }

                                String fechaEstimada = String
                                                .valueOf(node.getNode(Path.parse(fechaEstimadaImpPath))
                                                                .executeRead(eval));
                                if (fechaEstimada.isBlank() || fechaEstimada.equals("null"))
                                        fechaEstimada = "null";
                                else
                                        fechaEstimada = "'" + fechaEstimada + "'";

                                String kri1Imp = String
                                                .valueOf(node.getNode(Path.parse(kri1ImpPath)).executeRead(eval));
                                if (kri1Imp.isBlank() || kri1Imp.equals("null")) {
                                        kri1Imp = "null";
                                        System.out.println("Valor del riesgo null");
                                } else {
                                        kri1Imp = "{key: '" + kri1Imp + "', label: '', previewURL: undefined}";
                                        System.out.println("Sin datos");
                                }

                                String kri2 = String.valueOf(node.getNode(Path.parse(kri2Path)).executeRead(eval));
                                if (kri2.isBlank() || kri2.equals("null")) {
                                        kri2 = "null";
                                        System.out.println("Valor del riesgo null");
                                } else {
                                        kri2 = "{key: '" + kri2 + "', label: '', previewURL: undefined}";
                                        System.out.println("Sin datos");
                                }

                                aWriter.addJS_cr(idList.concat("Obj") + " = {");
                                aWriter.addJS_cr(objePath + ": " + objetivo + ",");
                                aWriter.addJS_cr(riesPath + ": " + riesgo + ",");
                                aWriter.addJS_cr(fechIdenPath + ": " + fecha + ",");
                                aWriter.addJS_cr(probInhePath + ": " + probInhe + ",");

                                aWriter.addJS_cr(tiposDatosPath + ": " + tiposDatos + ",");
                                aWriter.addJS_cr(cantidadDatosPath + ": " + cantDatos + ",");
                                aWriter.addJS_cr(categoriaTitularesPath + ": " + catTitulares + ",");
                                aWriter.addJS_cr(cantidadtitularesPath + ": " + canTitulares + ",");
                                
                                
                                aWriter.addJS_cr(tipoImpactoPath + ": " + tpImpac + ",");
                                aWriter.addJS_cr(expoInherTDatoPath + ": " + expoInhe + ",");

                                aWriter.addJS_cr(expoInheCantDPath + ": " + expoInheCant + ",");
                                aWriter.addJS_cr(expoInheCantTituDPath + ": " + expoInheCantTitular + ",");
                                aWriter.addJS_cr(expoInheCantTituD2Path + ": " + expoInheCantTitular2 + ",");
                                aWriter.addJS_cr(expoInheTpImpaDPath + ": " + expoInheTpImpac + ",");
                                aWriter.addJS_cr(expoInheResultpaDPath + ": " + expoInheResult + ",");

                
                                aWriter.addJS_cr(clasificacion2aPath + ": " + clasificacion2 + ",");
                                aWriter.addJS_cr(controlInstaPath + ": " + controlInsa + ",");
                                aWriter.addJS_cr(probabilidadRPath + ": " + probabilidadR + ",");

                                aWriter.addJS_cr(tipoDatoRPath + ": " + tipoDatoR + ",");
                                aWriter.addJS_cr(cantidadDatoRPath + ": " + cantidadDatoR + ",");
                                aWriter.addJS_cr(categoriaTitularesRPath + ": " + catTitularesR + ",");
                                aWriter.addJS_cr(cantidadRitularesRPath + ": " + cantidadTituR + ",");
                                aWriter.addJS_cr(tipoImpactoRPath + ": " + tipoImpactoR + ",");

                                aWriter.addJS_cr(expoResidualTpDatoPath + ": " + expoResidualR + ",");
                                aWriter.addJS_cr(expoResidualTpDatoCanPath + ": " + expoRTpDato + ",");
                                aWriter.addJS_cr(expoRecCatTitularesPath + ": " + expoRecCantTitulares + ",");
                                aWriter.addJS_cr(expoRecCanTitularesPath + ": " + expoRecCanTitulares + ",");
                                aWriter.addJS_cr(expoRecTpImpactoPath + ": " + expoRecTpImpacto + ",");

                                aWriter.addJS_cr(expoResResultadoPath + ": " + expoResResultado + ",");
                      
                                aWriter.addJS_cr(clasificacion5Path + ": " + clasificacion5 + ",");
                                aWriter.addJS_cr(accControlSPath + ": " + accControl + ",");

                                aWriter.addJS_cr(responsablePath + ": " + responsable + ",");
                                aWriter.addJS_cr(estadoPath + ": " + estado + ",");
                                aWriter.addJS_cr(fechaEstimadaImpPath + ": " + fechaEstimada + ",");
                                aWriter.addJS_cr(kri1ImpPath + ": " + kri1Imp + ",");
                                aWriter.addJS_cr(kri2Path + ": " + kri2 + ",");

                                aWriter.addJS_cr("};");

                                aWriter.addJS_cr(idList.concat("Array") + ".push(" + idList.concat("Obj") + ");");

                        }

                        aWriter.addJS_cr("setComplexList('" + prefix + "'," + pathArray + ", '" + idList + "'," + idList.concat("Array") + ");");

                } else {
                        System.out.println("--------------------> Evaluacion nulll");
                }

        }

        private void ajaxCallbackTres(UserServiceAjaxContext anAjaxContext, UserServiceAjaxResponse anAjaxResponse) {

                Adaptation datasetA = anAjaxContext.getValueContext(OBJECT_KEY).getAdaptationInstance();
                AdaptationTable table = datasetA
                                .getTable(Path.parse("/root/Formularios/Plantilla_analisis_riesgo"));
                // IdentificadorAIP
                /// root/Formularios/Evaluacion_impact
                /// root/Cuestionario/CuestionarioDPIA
                // idCuestionariodpia

                String value = anAjaxContext.getParameter("fieldValue");
                System.out.println(value);

                List<String> analisisDeRiesgo = null;
                List<String> pgRiesgoAnalisis = null;

                // List de la carpeta /Evaluacion_Riesgo
               
                // nodo
                SchemaNode node = null;
                SchemaNode node2 = null;
                SchemaNode node3 = null;
                if (value != null) {

                        Adaptation record = table.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(value));

                        analisisDeRiesgo = record.getList(
                                        Path.parse("./Evaluacion_Riesgo"));
                        pgRiesgoAnalisis = record.getList(Path.parse("./Evaluacion_Riesgo"));

                        // Path de la carpeta /Evaluacion_Riesgo
                        evaluacionR = record.getList(Path.parse("./Evaluacion_Riesgo"));
                        evaluacion2R = record.getList(Path.parse("./Evaluacion_Riesgo"));
                        evaluacion3R = record.getList(Path.parse("./Evaluacion_Riesgo"));
                        // Path del nodo
                        node = record.getSchemaNode().getNode(Path.parse("./Evaluacion_Riesgo"));
                        node2 = record.getSchemaNode().getNode(Path.parse("./Evaluacion_Riesgo"));
                        node3 = record.getSchemaNode().getNode(Path.parse("./Evaluacion_Riesgo"));
                }

                UserServiceWriter aWriter = anAjaxResponse.getWriter();

                // Obtiene el mapeo de las carpetas
                llenaCamposDos(node, evaluacionR, aWriter, pAnalisisRiesgo, ANALISIS_RIESGOS);
                llenaCamposDos(node2, evaluacion2R, aWriter, pPGAnalisisRiesgo, PG_RIESGO_ANALISIS_R);

                aWriter.addJS_cr("console.log('Hola mundo')");

        }

        /**
         * Metodo que es utilizado realizar el mapeo de los registro
         * de la carpeta de Evaluacion_Riesgo
         * 
         * 
         * @param SchemaNode        node
         * @param List<Object>      evaluacion
         * @param UserServiceWriter aWriter
         * @param String            prefix
         * @param String            idList
         * @return
         * @throws
         */
        private void llenaCamposDos(SchemaNode node, List<Object> evaluacion, UserServiceWriter aWriter,
                        String prefix, String idList) {

                // Campos de Evaluacion Riesgo
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

    	    
    	    
                // Creacion de Array de Evaluacion_Riesgo
    	    	final String evalPathArray = idList.concat("evalPathArray");
    	    	aWriter.addJS_cr(idList.concat("valueArray") + " = [];");
    	    	aWriter.addJS_cr(evalPathArray + "= [];");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + objePath + "');");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + riesPath + "');");    	    	
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + fechIdenPath + "');");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + probInhePath + "');");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + impaInhePath + "');");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + expoInhePath + "');");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + clasInhePath + "');");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + contPath + "');");    	
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + probresiPath + "');");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + impacresiPath + "');");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + exporesiPath + "');");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + clasresiPath + "');");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + controlsuPath + "');");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + responPath + "');");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + estadoPath + "');");    	
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + fechimplePaht + "');");
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + kri1Path + "');");    	
    	    	aWriter.addJS_cr(evalPathArray + ".push('" + kri2Paht + "');");

                aWriter.addJS_cr("resetComplexList('" + prefix + "'," + evalPathArray + ", '" + idList + "');");

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


                              
                                aWriter.addJS_cr(idList.concat("Obj") + " = {");
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

                                aWriter.addJS_cr(idList.concat("valueArray") + ".push(" + idList.concat("Obj") + ");");

                        }

                        aWriter.addJS_cr("setComplexList('" + prefix + "'," + evalPathArray + ", '" + idList+ "'," + idList.concat("valueArray") + ");");

                }else {
                    System.out.println("--------------------> Evaluacion Riesgo");
            }

        }

        @Override
        public void validate(UserServiceValidateContext<T> arg0) {
                // TODO Auto-generated method stub

        }
}

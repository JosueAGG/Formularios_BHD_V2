package com.alldatum.ebx.bhd.form.gdpr;

import com.alldatum.ebx.bhd.util.AdaptationUtil;
import com.onwbp.adaptation.Adaptation;
import com.onwbp.base.text.UserMessage;
import com.orchestranetworks.interactions.InteractionHelper.ParametersMap;
import com.orchestranetworks.interactions.SessionInteraction;
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


public class FPAIDUserService <T extends TableEntitySelection> implements UserService<T> 
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
	
	
     private void pane(UserServicePaneContext aPaneContext, UserServicePaneWriter aWriter) {
		 
		aWriter.setCurrentObject(OBJECT_KEY);
		
		aWriter.add_cr("<h3>Datos básicos del tratamiento </h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
        aWriter.addFormRow(Path.parse("Identificador"));
        aWriter.addFormRow(Path.parse("Nombre"));
        aWriter.addFormRow(Path.parse("Fecha_creacion"));
        aWriter.addFormRow(Path.parse("Responsable"));
        aWriter.addFormRow(Path.parse("Correspondable"));
        aWriter.addFormRow(Path.parse("Procesos_operativos"));
        
        
        startBooleanBlock(aPaneContext, aWriter, Path.parse("Ficha_analisis_fases"), "block_fichaAnalisisFases");
        aWriter.addFormRow(Path.parse("Campos_ficha_fase/Nombre"));
        endBlock(aWriter);
        
        
        startBooleanBlock(aPaneContext, aWriter, Path.parse("Ficha_ciclo_vida"), "block_fichaCicloDeVida");
        aWriter.addFormRow(Path.parse("Campos_Ficha_ciclo_vida/Nombre"));
        endBlock(aWriter);
           
        
        startBooleanBlock(aPaneContext, aWriter, Path.parse("IdentificadorFAR"), "block_identificadorFAR");
        aWriter.addFormRow(Path.parse("Campos_ficha_analisis_de_riesgo/Identificador_FAR"));
        endBlock(aWriter);
        
        
		aWriter.endExpandCollapseBlock();
		
		//-------------------------------------------------------
		
		aWriter.add_cr("<h3>Revisión metodológica</h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		
		//Preparación
		Path revPath = Path.parse("ReviMetoAI");
		UIDropDownList revDropDown = aWriter.newDropDownList(revPath);
		revDropDown.setActionOnAfterValueChanged(JsFunctionCall.on("displayBlockRevision"));
		aWriter.addFormRow(revDropDown);
		String value = (String) aPaneContext.getValueContext(OBJECT_KEY, revPath).getValue();

		//Configuración dependiendo de lo seleccionado
		startBlock(aWriter, "block_revision_aceptable", "aceptable".equalsIgnoreCase(value));	
		aWriter.addFormRow(Path.parse("MenuACepAI"));
		aWriter.addFormRow(Path.parse("ConsiadAI"));
		aWriter.addFormRow(Path.parse("NombrereviorPAIDPIA"));
		aWriter.addFormRow(Path.parse("RolcargoPAI"));
		endBlock(aWriter);

		startBlock(aWriter, "block_revision_inaceptable", "inaceptable".equalsIgnoreCase(value));
		aWriter.addFormRow(Path.parse("MeniinaAI"));
		aWriter.addFormRow(Path.parse("ConciinaAI"));
		aWriter.addFormRow(Path.parse("NombrerevisorPAIina"));
		aWriter.addFormRow(Path.parse("RolocargoPAIna"));
		endBlock(aWriter);
		
		
		
		
		//aWriter.addFormRow(Path.parse("ReviMetoAI"));
		
		
		aWriter.endExpandCollapseBlock();
		
		//-------------------------------------------------------
		
		aWriter.add_cr("<h3>Evaluación </h3>");
		aWriter.startExpandCollapseBlock(UserMessage.createInfo(""), true);
		aWriter.addFormRow(Path.parse("Evaluacion_analisis_de_impacto"));
		aWriter.endExpandCollapseBlock();
		
		
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
	
    
      
      
      
      
     //Metodos útilizados  para los campos booleanos
      
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

      
      
	
}

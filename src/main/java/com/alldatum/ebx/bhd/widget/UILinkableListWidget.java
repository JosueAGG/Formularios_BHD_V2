package com.alldatum.ebx.bhd.widget;

import com.orchestranetworks.schema.Path;
import com.orchestranetworks.ui.form.widget.UIListCustomWidget;
import com.orchestranetworks.ui.form.widget.WidgetDisplayContext;
import com.orchestranetworks.ui.form.widget.WidgetFactoryContext;
import com.orchestranetworks.ui.form.widget.WidgetWriterForList;

public class UILinkableListWidget extends UIListCustomWidget {
	
	private String id;
	private String linkedId;
	private Path pathForSelectedNode;

	public UILinkableListWidget(WidgetFactoryContext aContext) {
		super(aContext);

	}

	@Override
	public void write(WidgetWriterForList aWriter, WidgetDisplayContext aContext) {
		
		if(id == null || id.isBlank()) {
			
			aWriter.addList(Path.SELF, new UICustomWidgetFactory(aWriter));
			
		} else {
			
			aWriter.add("<div ");
			aWriter.addSafeAttribute("id", id);
			aWriter.add_cr(">");
			
			aWriter.addList(Path.SELF, new UICustomWidgetFactory(aWriter));
			
			aWriter.add_cr("</div>");
			
			if(linkedId != null && !linkedId.isBlank()) {
				
			
				aWriter.addJS_cr("function linkButtons(thisId, linkedId){");
				aWriter.addJS_cr("const thisWidgetDiv = document.getElementById(thisId);");
				aWriter.addJS_cr("const linkedWidgetDiv = document.getElementById(linkedId);");
				aWriter.addJS_cr("const thisTrashcanArray = thisWidgetDiv.querySelectorAll('.ebx_Trashcan');");
				aWriter.addJS_cr("const linkedTrashcanArray = linkedWidgetDiv.querySelectorAll('.ebx_Trashcan');");
				aWriter.addJS_cr("if(thisTrashcanArray.length == linkedTrashcanArray.length){");
				aWriter.addJS_cr("thisTrashcanArray.forEach(function(element, index){");
				aWriter.addJS_cr("const linkedElement = linkedTrashcanArray[index];");
				aWriter.addJS_cr("EBX_ButtonUtils.setButtonDisabled(linkedElement, true);");
				aWriter.addJS_cr("element.onclick = function(){");
				aWriter.addJS_cr("EBX_ButtonUtils.toggleButtonListener(this, event);");
				aWriter.addJS_cr("EBX_ButtonUtils.setButtonDisabled(linkedElement, false);");
				aWriter.addJS_cr("linkedElement.click();");
				aWriter.addJS_cr("EBX_ButtonUtils.setButtonDisabled(linkedElement, true);");
				aWriter.addJS_cr("};");
				aWriter.addJS_cr("});");
				aWriter.addJS_cr("const thisAddButton = thisWidgetDiv.querySelector('.ebx_AddOccurrence');");
				aWriter.addJS_cr("const linkedAddButton = linkedWidgetDiv.querySelector('.ebx_AddOccurrence');");
				aWriter.addJS_cr("EBX_ButtonUtils.setButtonDisabled(linkedAddButton, true);");
				aWriter.addJS_cr("thisAddButton.onclick = function(){");
				aWriter.addJS_cr("EBX_ButtonUtils.jsButtonListener(this, event);");
				aWriter.addJS_cr("EBX_ButtonUtils.setButtonDisabled(linkedAddButton, false);");
				aWriter.addJS_cr("linkedAddButton.click();");
				aWriter.addJS_cr("EBX_ButtonUtils.setButtonDisabled(linkedAddButton, true);");
				aWriter.addJS_cr("};");
				aWriter.addJS_cr("} else {");
				aWriter.addJS_cr("console.log('Widgets are not the same length');");
				aWriter.addJS_cr("}");
				aWriter.addJS_cr("}");
		
				aWriter.addJS_cr("linkButtons('" + id + "', '" + linkedId +"');");
			
			}	
			
			if(pathForSelectedNode != null) {
				
				aWriter.addJS_cr("function setListenerForSelectedNode(thisId, path){");
				aWriter.addJS_cr("const thisWidgetDiv = document.getElementById(thisId);");
				aWriter.addJS_cr("const thisInputArray = thisWidgetDiv.querySelectorAll('.ebx_ISS_Container');");
				aWriter.addJS_cr("thisInputArray.forEach(function(element, index){");
				aWriter.addJS_cr("element.addEventListener('mouseover', function(){");
				aWriter.addJS_cr("ebx_form_setValue(path, index);");
				aWriter.addJS_cr("});");
				aWriter.addJS_cr("});");
				aWriter.addJS_cr("}");
				
				aWriter.addJS_cr("setListenerForSelectedNode('" + id + "', '" + aWriter.getPrefixedPath(pathForSelectedNode).format() +"');");
				
				
			}
			
		}
		
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setPathForSelectedNode(Path pathForSelectedNode) {
		this.pathForSelectedNode = pathForSelectedNode;
	}
	

	public void linkWidget(UILinkableListWidget widget){
			
		this.linkedId = widget.getId();
		
	}
}

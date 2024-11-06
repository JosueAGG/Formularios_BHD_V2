package com.alldatum.ebx.bhd.widget;

import java.util.List;

import com.orchestranetworks.ui.form.widget.UIListCustomWidget;
import com.orchestranetworks.ui.form.widget.WidgetDisplayContext;
import com.orchestranetworks.ui.form.widget.WidgetFactoryContext;
import com.orchestranetworks.ui.form.widget.WidgetWriterForList;

public class UIDynamicListWidget extends UIListCustomWidget{
	
	private String listId;

	public UIDynamicListWidget(WidgetFactoryContext aContext) {
		super(aContext);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(WidgetWriterForList aWriter, WidgetDisplayContext aContext) {
		
		List<String> valueList = (List<String>) aContext.getValueContext().getValue();
		
		int size = valueList == null ? 0 : valueList.size();
		
		aWriter.add("<ol ");
		if(listId != null && !listId.isBlank()) 
			aWriter.addSafeAttribute("id", listId);
		aWriter.addSafeAttribute("class", "ebx_OccurrenceList");
		aWriter.add_cr(">");
		
		for(int i = 0; i < size; i++ ) {
			
			aWriter.add_cr("<li>");
			aWriter.addWidget(aWriter.getPathForListOccurrence(i, true));
			aWriter.add_cr("</li>");
			
		}

		for(int i = size; i < size + 15; i++) {
			
			aWriter.add("<li ");
			aWriter.addSafeAttribute("style", "display:none;");
			aWriter.add_cr(">");
			aWriter.addWidget(aWriter.getPathForListOccurrence(i, true));
			aWriter.add_cr("</li>");
			
		}	
		
		aWriter.add_cr("</ol>");
	}
	

	public String getListId() {
		return listId;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

}

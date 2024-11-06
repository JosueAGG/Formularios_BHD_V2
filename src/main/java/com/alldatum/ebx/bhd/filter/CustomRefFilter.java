package com.alldatum.ebx.bhd.filter;

import java.util.Locale;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.base.validation.ValidationContext;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.schema.InvalidSchemaException;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.Step;
import com.orchestranetworks.schema.TableRefFilter;
import com.orchestranetworks.schema.TableRefFilterContext;

public class CustomRefFilter implements TableRefFilter {
	
	private String filterField;
	private String foreignFilterField;
	private String optionalAuxListField;
	
	@Override
	public boolean accept(Adaptation anAdaptation, ValueContext aContext) {

		Integer index = null;
		String selected;
		String tableVal = String.valueOf(anAdaptation.get(Path.parse(foreignFilterField)));

		if(optionalAuxListField != null && !optionalAuxListField.isBlank()) {
			
			if(aContext instanceof ValidationContext) {
		
				ValidationContext vc = (ValidationContext) aContext;
				Step parentPath = vc.getCurrentPath().getPathWithoutLastStep().getLastStep();
				System.out.println(parentPath.format());
				if(parentPath.isIndexed())
					index = parentPath.getIndex();
			}
			
		}
		
		if(index == null)
			selected = String.valueOf(aContext.getValue(Path.parse(filterField)));	
		else
			selected = String.valueOf(aContext.getValue(Path.parse(optionalAuxListField).addIndex(index)));	

		if(selected == null || tableVal == null) { 
			
			return false;
			
		} else if(selected.equals(tableVal)) { 
			
			return true;
			
		} else { 
			
			return false;
			
		}
	}

	@Override
	public void setup(TableRefFilterContext aContext) {
		
	}

	@Override
	public String toUserDocumentation(Locale aContext, ValueContext arg1) throws InvalidSchemaException {
		return null;
	}

	public String getFilterField() {
		return filterField;
	}

	public void setFilterField(String filterField) {
		this.filterField = filterField;
	}

	public String getForeignFilterField() {
		return foreignFilterField;
	}

	public void setForeignFilterField(String foreignFilterField) {
		this.foreignFilterField = foreignFilterField;
	}

	public String getOptionalAuxListField() {
		return optionalAuxListField;
	}

	public void setOptionalAuxListField(String optionalAuxListField) {
		this.optionalAuxListField = optionalAuxListField;
	}
	
}

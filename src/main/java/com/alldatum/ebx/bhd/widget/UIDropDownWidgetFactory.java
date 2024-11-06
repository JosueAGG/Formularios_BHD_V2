package com.alldatum.ebx.bhd.widget;

import com.orchestranetworks.ui.form.widget.UIWidgetFactory;
import com.orchestranetworks.ui.form.widget.WidgetFactoryContext;
import com.orchestranetworks.ui.form.widget.WidgetFactorySetupContext;

public class UIDropDownWidgetFactory implements UIWidgetFactory<UIDropDownWidget> {
	
	private String methodAfterValueChange = null;
	private String optionalArgument = null;
	private Boolean passParentIndexInArgument = false;
	private String divId = null;
	private Boolean isHiddenByDefault = false;
	private String changeValues = null;
	private String controlField = null;
	

	@Override
	public UIDropDownWidget newInstance(WidgetFactoryContext aContext) {
		
		UIDropDownWidget widget = new UIDropDownWidget(aContext);

		if(methodAfterValueChange != null && !methodAfterValueChange.isBlank())
			widget.setActionOnAfterValueChanged(methodAfterValueChange, optionalArgument, passParentIndexInArgument);
		
		if(divId != null && !divId.isBlank())
			widget.setDivBehavior(divId, isHiddenByDefault, controlField, changeValues);

		return widget;
		
	}

	@Override
	public void setup(WidgetFactorySetupContext aContext) {

		if(aContext.getSchemaNode().getFacetEnumeration() == null) 
			aContext.addError("This node isn't an enumeration.");

	}

	public String getMethodAfterValueChange() {
		return methodAfterValueChange;
	}

	public void setMethodAfterValueChange(String methodAfterValueChange) {
		this.methodAfterValueChange = methodAfterValueChange;
	}

	public String getOptionalArgument() {
		return optionalArgument;
	}

	public void setOptionalArgument(String optionalArgument) {
		this.optionalArgument = optionalArgument;
	}

	public Boolean getPassParentIndexInArgument() {
		return passParentIndexInArgument;
	}

	public void setPassParentIndexInArgument(Boolean passParentIndexInArgument) {
		this.passParentIndexInArgument = passParentIndexInArgument;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public Boolean getIsHiddenByDefault() {
		return isHiddenByDefault;
	}

	public void setIsHiddenByDefault(Boolean isHiddenByDefault) {
		this.isHiddenByDefault = isHiddenByDefault;
	}

	public String getChangeValues() {
		return changeValues;
	}

	public void setChangeValues(String changeValues) {
		this.changeValues = changeValues;
	}

	public String getControlField() {
		return controlField;
	}

	public void setControlField(String controlField) {
		this.controlField = controlField;
	}
	



}

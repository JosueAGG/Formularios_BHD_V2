package com.alldatum.ebx.bhd.widget;

import com.orchestranetworks.ui.form.widget.UIWidgetFactory;
import com.orchestranetworks.ui.form.widget.WidgetFactoryContext;
import com.orchestranetworks.ui.form.widget.WidgetFactorySetupContext;

public class UIDynamicListWidgetFactory implements UIWidgetFactory<UIDynamicListWidget>{

	@Override
	public UIDynamicListWidget newInstance(WidgetFactoryContext aContext) {
		
		return new UIDynamicListWidget(aContext);
	}

	@Override
	public void setup(WidgetFactorySetupContext aContext) {

		
	}

}
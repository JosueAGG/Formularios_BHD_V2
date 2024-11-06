package com.alldatum.ebx.bhd.widget;

import com.orchestranetworks.ui.form.widget.UIWidget;
import com.orchestranetworks.ui.form.widget.UIWidgetFactory;
import com.orchestranetworks.ui.form.widget.WidgetFactoryContext;
import com.orchestranetworks.ui.form.widget.WidgetFactorySetupContext;

public class UILinkableListWidgetFactory implements UIWidgetFactory<UIWidget>{

	@Override
	public UIWidget newInstance(WidgetFactoryContext aContext) {
		
		return new UILinkableListWidget(aContext);
	}

	@Override
	public void setup(WidgetFactorySetupContext aContext) {

		
	}

}

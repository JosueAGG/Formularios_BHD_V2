package com.alldatum.ebx.bhd.widget;

import com.orchestranetworks.schema.Path;
import com.orchestranetworks.ui.form.UIFormWriter;
import com.orchestranetworks.ui.form.widget.UIWidget;
import com.orchestranetworks.ui.form.widget.UIWidgetFactory;
import com.orchestranetworks.ui.form.widget.WidgetFactoryContext;
import com.orchestranetworks.ui.form.widget.WidgetFactorySetupContext;

public class UICustomWidgetFactory implements UIWidgetFactory<UIWidget> {
	
	private UIFormWriter formWriter;

	@Override
	public UIWidget newInstance(WidgetFactoryContext aContext) {

		return  formWriter.newBestMatching(Path.SELF);
		
		
	}

	@Override
	public void setup(WidgetFactorySetupContext aContext) {

		
	}
	
	public  UICustomWidgetFactory(UIFormWriter formWriter) {
		this.formWriter = formWriter;
	}
	
	
	

}

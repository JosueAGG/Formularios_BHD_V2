package com.alldatum.ebx.bhd.widget;

import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.Step;
import com.orchestranetworks.schema.info.SchemaFacetEnumeration;
import com.orchestranetworks.ui.UIJavaScriptWriter;
import com.orchestranetworks.ui.base.JsFunctionCall;
import com.orchestranetworks.ui.form.widget.UIAtomicWithEnumeration;
import com.orchestranetworks.ui.form.widget.UISimpleCustomWidget;
import com.orchestranetworks.ui.form.widget.WidgetDisplayContext;
import com.orchestranetworks.ui.form.widget.WidgetFactoryContext;
import com.orchestranetworks.ui.form.widget.WidgetWriter;

public class UIDropDownWidget extends UISimpleCustomWidget{
    
    private String methodAfterValueChange = null;
    private String optionalArgument = null;
    private Boolean passParentIndex = false;
    private String divId = null;
    private Boolean isHiddenByDefault = false;
    private String changeValues = null;
    private String controlField = null;

    UIDropDownWidget(WidgetFactoryContext aContext) {
        super(aContext);
    }

    @Override
    public void write(WidgetWriter aWriter, WidgetDisplayContext aContext) {
        
        Step parentPath = aWriter.getPrefixedPath(Path.PARENT).getLastStep();
        Integer index = null;
        if(parentPath.isIndexed()) {
            index = parentPath.getIndex();
        }
        
        JsFunctionCall actionOnAfterValueChanged = null;
        if(methodAfterValueChange != null && !methodAfterValueChange.isBlank()) {
            if(optionalArgument != null && !optionalArgument.isBlank()) {
                if(passParentIndex) {
                    String argument = optionalArgument + "," + String.valueOf(index);
                    actionOnAfterValueChanged = JsFunctionCall.on(methodAfterValueChange, argument);
                } else {
                    actionOnAfterValueChanged = JsFunctionCall.on(methodAfterValueChange, optionalArgument);
                }
            } else {
                if(passParentIndex) {
                    actionOnAfterValueChanged = JsFunctionCall.on(methodAfterValueChange, String.valueOf(index));
                } else {
                    actionOnAfterValueChanged = JsFunctionCall.on(methodAfterValueChange);
                }
            }
        }
        
        UIAtomicWithEnumeration widget;
        SchemaFacetEnumeration facet = aContext.getNode().getFacetEnumeration();
        if(facet.isEnumerationTableRef())
            widget = aWriter.newComboBox(Path.SELF);
        else
            widget = aWriter.newDropDownList(Path.SELF);
        if(actionOnAfterValueChanged != null)
            widget.setActionOnAfterValueChanged(actionOnAfterValueChanged);
        aWriter.addWidget(widget);
        
        if(divId != null && !divId.isBlank()) {
            String selfPath = aWriter.getPrefixedPath(Path.SELF).format();
            String display = isHiddenByDefault ? "none" : "contents";
            divId = index == null ? divId : divId + "_" + String.valueOf(index);
            if(controlField != null && !controlField.isBlank()) {
                String fieldValue = String.valueOf(aContext.getValueContext().getValue(Path.parse(controlField)));
                
                // Agregado: Log para depuración del valor del campo de control
                aWriter.addJS_cr("console.log('Valor del campo de control: " + fieldValue + "');");
                
                if(fieldValue.equals("null"))
                    fieldValue = "";
                if(changeValues == null)
                    changeValues = "";
                String[] valueArray = changeValues.split(",", -1);
                for(String value: valueArray){
                    // Agregado: Log para depuración de la comparación de valores
                    aWriter.addJS_cr("console.log('Comparando valor: " + fieldValue + " con: " + value + "');");
                    
                    if (fieldValue.equalsIgnoreCase(value)) {
                        display = isHiddenByDefault ? "contents" : "none";
                        break;
                    }
                }
            }
            aWriter.addJS_cr("wrapElement('" + selfPath + "','" + divId + "','" + display + "');");
        }

    }

    public void setActionOnAfterValueChanged(String methodAfterValueChange, String optionalArgument, Boolean passParentIndex) {
        this.methodAfterValueChange = methodAfterValueChange;
        this.optionalArgument = optionalArgument;
        if(passParentIndex !=null)
            this.passParentIndex = passParentIndex;
    }
    
    public void setDivBehavior(String divId, Boolean isHiddenByDefault, String controlField, String changeValues) {
        this.divId = divId;
        if(isHiddenByDefault != null)
            this.isHiddenByDefault = isHiddenByDefault;
        this.controlField = controlField;
        this.changeValues = changeValues;
    }

    public String getDivId() {
        return divId;
    }

    public void setDivId(String divId) {
        this.divId = divId;
    }
    
    public static void writeWidgetJavascript(UIJavaScriptWriter aWriter) {
        
        aWriter.addJS_cr("let style = document.createElement('style');");
        aWriter.addJS_cr("style.innerHTML = `");
        aWriter.addJS_cr(".simpleEnvelope {");
        aWriter.addJS_cr("display: contents;");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("`;");
        aWriter.addJS_cr("document.head.appendChild(style);");
        
        aWriter.addJS_cr("function wrapElement(path, divId, display){");
        aWriter.addJS_cr("const node = EBX_FormNodeIndex.getFormNode(path);");
        aWriter.addJS_cr("const field = node.getDecoratorElement().closest('.ebx_Field');");
        aWriter.addJS_cr("let div = document.getElementById(divId);");
        aWriter.addJS_cr("if(!div){");
        aWriter.addJS_cr("div = document.createElement('div');");
        aWriter.addJS_cr("div.id = divId;");
        aWriter.addJS_cr("div.style.display = display;");
        aWriter.addJS_cr("if(field.tagName == 'TR'){");
        aWriter.addJS_cr("const outer = document.createElement('tr');");
        aWriter.addJS_cr("outer.classList.add('ebx_Field');");
        aWriter.addJS_cr("outer.classList.add('simpleEnvelope');");
        aWriter.addJS_cr("field.parentNode.insertBefore(outer, field);");
        aWriter.addJS_cr("outer.appendChild(div);");
        aWriter.addJS_cr("} else {");
        aWriter.addJS_cr("field.parentNode.insertBefore(div, field);");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("}");
        aWriter.addJS_cr("div.appendChild(field);");
        aWriter.addJS_cr("}");
        
    }
    
}

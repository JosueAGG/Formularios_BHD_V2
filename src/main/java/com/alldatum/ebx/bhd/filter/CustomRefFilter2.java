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

public class CustomRefFilter2 implements TableRefFilter {
    
    private String filterField;
    private String foreignFilterField;
    private String optionalAuxListField;
    
    @Override
    public boolean accept(Adaptation anAdaptation, ValueContext aContext) {

        Integer index = null;
        String selected;
        String tableVal = String.valueOf(anAdaptation.get(Path.parse(foreignFilterField)));

        if (optionalAuxListField != null && !optionalAuxListField.isBlank()) {
            
            if (aContext instanceof ValidationContext) {
                ValidationContext vc = (ValidationContext) aContext;
                Step parentPath = vc.getCurrentPath().getPathWithoutLastStep().getLastStep();
                System.out.println("Ruta del padre: " + parentPath.format());  // Impresi�n en espa�ol: mostrando la ruta del padre
                if (parentPath.isIndexed()) {
                    index = parentPath.getIndex();
                    System.out.println("�ndice recuperado: " + index); // Impresi�n del �ndice recuperado
                }
            }
        }
        
        if (index == null) {
            selected = String.valueOf(aContext.getValue(Path.parse(filterField)));
        } else {
            selected = String.valueOf(aContext.getValue(Path.parse(optionalAuxListField).addIndex(index)));
        }

        System.out.println("Valor seleccionado: " + selected); // Impresi�n del valor seleccionado
        System.out.println("Valor de la tabla: " + tableVal);  // Impresi�n del valor de la tabla

        if (selected == null || tableVal == null) { 
            System.out.println("El valor seleccionado o el valor de la tabla es nulo");  // Impresi�n en espa�ol: verificando si los valores son nulos
            return false;
        } else if (selected.equalsIgnoreCase(tableVal)) { 
            System.out.println("El valor seleccionado coincide con el valor de la tabla (sin importar may�sculas/min�sculas)");  // Impresi�n en espa�ol: verificando si los valores coinciden
            return true;
        } else { 
            System.out.println("El valor seleccionado no coincide con el valor de la tabla");  // Impresi�n en espa�ol: verificando si los valores no coinciden
            return false;
        }
        
        
        
        
    }

    @Override
    public void setup(TableRefFilterContext aContext) {
        // Este m�todo se utiliza para configurar el filtro
    }

    @Override
    public String toUserDocumentation(Locale aContext, ValueContext arg1) throws InvalidSchemaException {
        // Aqu� podr�as agregar documentaci�n para el usuario, si es necesario
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

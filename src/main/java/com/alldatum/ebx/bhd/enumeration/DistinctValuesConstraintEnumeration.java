package com.alldatum.ebx.bhd.enumeration;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.onwbp.adaptation.Adaptation;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.instance.ValueContextForValidation;
import com.orchestranetworks.query.Query;
import com.orchestranetworks.query.QueryResult;
import com.orchestranetworks.query.Tuple;
import com.orchestranetworks.schema.ConstraintContext;
import com.orchestranetworks.schema.ConstraintEnumeration;
import com.orchestranetworks.schema.InvalidSchemaException;

public class DistinctValuesConstraintEnumeration implements ConstraintEnumeration<String> {

    String field;
    String tablePath;

    @Override
    public void checkOccurrence(String arg0, ValueContextForValidation arg1) throws InvalidSchemaException {

    }

    @Override
    public void setup(ConstraintContext arg0) {

    }

    @Override
    public String toUserDocumentation(Locale arg0, ValueContext arg1) throws InvalidSchemaException {
        return null;
    }

    @Override
    public String displayOccurrence(String value, ValueContext aContext, Locale arg2) throws InvalidSchemaException {
        return value;
    }

    @Override
    public List<String> getValues(ValueContext aContext) throws InvalidSchemaException {

        Adaptation dataset = aContext.getAdaptationInstance();
        Query<Tuple> query = dataset.createQuery("SELECT DISTINCT \"" + field + "\" FROM \"" + tablePath + "\"");

        List<String> valuesList = new ArrayList<>();

        try (QueryResult<Tuple> result = query.getResult()) {

            Iterator<Tuple> it = result.iterator();

            while (it.hasNext()) {
                String value = it.next().get(0, String.class);
                // Recortar los espacios y normalizar el valor
                String normalizedValue = normalizeString(value.trim());
                
                // Verificar si la lista ya contiene el valor normalizado
                if (!containsIgnoreCaseAndAccent(valuesList, normalizedValue)) {
                    valuesList.add(value.trim());  // Añadir el valor original pero sin espacios
                }
            }

        }

        return valuesList;
    }

    /**
     * Método para normalizar cadenas, eliminando acentos y convirtiendo a minúsculas
     */
    private String normalizeString(String input) {
        if (input == null) {
            return null;
        }
        // Convertir a minúsculas y eliminar espacios en blanco
        String lowerCaseString = input.toLowerCase(Locale.ROOT).trim();
        // Eliminar acentos y caracteres diacríticos
        String normalizedString = Normalizer.normalize(lowerCaseString, Normalizer.Form.NFD);
        return normalizedString.replaceAll("\\p{M}", "");
    }

    /**
     * Verifica si la lista contiene un valor ignorando mayúsculas, minúsculas y acentos
     */
    private boolean containsIgnoreCaseAndAccent(List<String> list, String value) {
        for (String item : list) {
            if (normalizeString(item).equals(value)) {
                return true;
            }
        }
        return false;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTablePath() {
        return tablePath;
    }

    public void setTablePath(String tablePath) {
        this.tablePath = tablePath;
    }
}

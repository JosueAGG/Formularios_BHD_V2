package prueba;

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
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.TableRefFilter;
import com.orchestranetworks.schema.TableRefFilterContext;

public class UnifiedFilterAndEnumeration implements TableRefFilter, ConstraintEnumeration<String> {
    
    private String filterField;
    private String foreignFilterField;
    private String field;
    private String tablePath;

    // Implementation of TableRefFilter methods
    @Override
    public boolean accept(Adaptation anAdaptation, ValueContext aContext) {
        String selected = String.valueOf(aContext.getValue(Path.parse(filterField)));    
        String tableVal = String.valueOf(anAdaptation.get(Path.parse(foreignFilterField)));    
        
        if(selected == null || tableVal == null) { 
            return false;
        } else {
            return selected.equals(tableVal);
        }
    }

    @Override
    public void setup(TableRefFilterContext aContext) {
        // No setup needed
    }

    @Override
    public String toUserDocumentation(Locale aContext, ValueContext arg1) throws InvalidSchemaException {
        return null;
    }

    // Implementation of ConstraintEnumeration methods
    @Override
    public void checkOccurrence(String arg0, ValueContextForValidation arg1) throws InvalidSchemaException {
        // No occurrence check needed
    }

    @Override
    public void setup(ConstraintContext arg0) {
        // No setup needed
    }

    @Override
    public String displayOccurrence(String arg0, ValueContext aContext, Locale arg2) throws InvalidSchemaException {
        return null;
    }

    @Override
    public List<String> getValues(ValueContext aContext) throws InvalidSchemaException {
        Adaptation dataset = aContext.getAdaptationInstance();
        Query<Tuple> query = dataset.createQuery("SELECT DISTINCT \"" + field + "\" FROM \"" + tablePath + "\"");

        List<String> valuesList = new ArrayList<>();
        try (QueryResult<Tuple> result = query.getResult()) {
            Iterator<Tuple> it = result.iterator();
            while(it.hasNext()) {
                valuesList.add(it.next().get(0, String.class));
            }
        }
        return valuesList;
    }

    // Getters and setters
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

package com.alldatum.ebx.bhd.enumeration;

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
		
		try(QueryResult<Tuple> result=  query.getResult()){
			
			Iterator<Tuple> it = result.iterator();
			
			while(it.hasNext()) {
				
				valuesList.add(it.next().get(0, String.class));
					
			}
			
		}
		
		return valuesList;
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

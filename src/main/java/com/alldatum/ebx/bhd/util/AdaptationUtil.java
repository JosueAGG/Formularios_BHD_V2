package com.alldatum.ebx.bhd.util;

import java.util.ArrayList;
import java.util.List;

import com.onwbp.adaptation.Adaptation;
import com.onwbp.adaptation.AdaptationTable;
import com.onwbp.adaptation.RequestResult;
import com.orchestranetworks.instance.ValueContext;
import com.orchestranetworks.schema.Path;
import com.orchestranetworks.schema.SchemaNode;

public class AdaptationUtil {
	
	public static AdaptationTable getFKTable(final Adaptation record, final Path fkPath)
	{
		if (record == null)
		{
			return null;
		}

		return getFKTable(record.createValueContext(), fkPath);
	}
	
	public static AdaptationTable getFKTable(final ValueContext valueContext, final Path fkPath)
	{
		if (valueContext == null)
		{
			return null;
		}

		SchemaNode fkNode = valueContext.getNode().getNode(fkPath);
		return fkNode.getFacetOnTableReference().getTable(valueContext);
	}
	
	public static Adaptation getRecordForValueContext(ValueContext valueContext)
	{
		AdaptationTable table = valueContext.getAdaptationTable();
		if (table == null)
		{
			return null;
		}
		return table.lookupAdaptationByPrimaryKey(valueContext);
	}
	
	public static List<Adaptation> getReferrersInTable(Adaptation record, Path tablePath, Path fkPath){
		
		List<Adaptation> recordList = new ArrayList<>();
		
		AdaptationTable table = record.getTable(tablePath);
		
		String request = fkPath.format() + "='" + record.getOccurrencePrimaryKey().format() + "'";
		RequestResult rr = table.createRequestResult(request);
		
		for(int i = 0; i < rr.getSize(); i++) {
			
			recordList.add(rr.nextAdaptation());
		}
		
		return recordList;
		
	}
	
	public static List<Adaptation> getReferrersInTable(ValueContext context, Path tablePath, Path fkPath){
		
		return getReferrersInTable(getRecordForValueContext(context), tablePath, fkPath);
	}
	
	public static Adaptation getFirstReferrerInTable(Adaptation record, Path tablePath, Path fkPath) {
		
		AdaptationTable table = record.getTable(tablePath);
		
		String request = fkPath.format() + "='" + record.getOccurrencePrimaryKey().format() + "'";

		return table.lookupFirstRecordMatchingPredicate(request);
	}
	
	public static Adaptation getFirstReferrerInTable(ValueContext vc, Path tablePath, Path fkPath) {
		
		return getFirstReferrerInTable(getRecordForValueContext(vc), tablePath, fkPath);
	}
	
	public static Adaptation followFK(final Adaptation record, final Path fkPath)
	{
		if (record == null)
		{
			return null;
		}

		SchemaNode fkNode = record.getSchemaNode().getNode(fkPath);
		return fkNode.getFacetOnTableReference().getLinkedRecord(record);
	}
	
	public static Adaptation followFK(final ValueContext valueContext, final Path fkPath)
	{
		SchemaNode fkNode = valueContext.getNode().getNode(fkPath);
		return fkNode.getFacetOnTableReference().getLinkedRecord(valueContext);
	}
	
	public static Object followFK(
			final Adaptation record,
			final Path fkPath,
			final Path fkAttributePath)
		{
			final Adaptation target = followFK(record, fkPath);
			if (target == null)
			{
				return null;
			}
			return target.get(fkAttributePath);
		}
	
	public static Object followFK(
			final ValueContext valueContext,
			final Path fkPath,
			final Path fkAttributePath)
		{
			final Adaptation target = followFK(valueContext, fkPath);
			if (target == null)
			{
				return null;
			}
			return target.get(fkAttributePath);
		}
	
	public static List<Adaptation> getLinkedRecords(
			SchemaNode fkNode,
			Adaptation record,
			ValueContext vc)
		{
			if (vc == null && record != null)
				vc = record.createValueContext();
			
			return fkNode.getFacetOnTableReference().getLinkedRecords(vc);

			/*List<String> keys = (List<String>) vc.getValue(fkNode);
			SchemaFacetTableRef tableRef = fkNode.getFacetOnTableReference();
			AdaptationTable refTable = tableRef.getTable(vc);
			List<Adaptation> result = new ArrayList<>();
			for (String key : keys)
			{
				Adaptation ref = refTable.lookupAdaptationByPrimaryKey(PrimaryKey.parseString(key));
				if (ref != null)
					result.add(ref);
			}
			return result;*/
		}
	
	public static List<Adaptation> followFKList(final Adaptation record, final Path fkPath)
	{
		if (record == null)
		{
			return new ArrayList<>();
		}

		SchemaNode fkNode = record.getSchemaNode().getNode(fkPath);
		return getLinkedRecords(fkNode, record, null);
	}
	
	public static List<Adaptation> followFKList(final ValueContext valueContext, final Path fkPath)
	{
		SchemaNode fkNode = valueContext.getNode().getNode(fkPath);
		return getLinkedRecords(fkNode, null, valueContext);
	}
	
	private static List<?> getValuesForFKList(
			List<Adaptation> targetList,
			Path fkAttributePath,
			boolean includeNulls)
		{
			final List<Object> valueList = new ArrayList<>();
			for (Adaptation targetRecord : targetList)
			{
				Object value = targetRecord.get(fkAttributePath);
				if (includeNulls || value != null)
				{
					valueList.add(value);
				}
			}
			return valueList;
		}
	
	public static List<?> followFKList(
			final Adaptation record,
			final Path fkPath,
			final Path fkAttributePath,
			final boolean includeNulls)
		{
			final List<Adaptation> targetList = followFKList(record, fkPath);
			return getValuesForFKList(targetList, fkAttributePath, includeNulls);
		}
	
	public static List<?> followFKList(
			final ValueContext valueContext,
			final Path fkPath,
			final Path fkAttributePath,
			final boolean includeNulls)
		{
			final List<Adaptation> targetList = followFKList(valueContext, fkPath);
			return getValuesForFKList(targetList, fkAttributePath, includeNulls);
		}

}
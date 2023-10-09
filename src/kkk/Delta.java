package kkk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

public class Delta {
	static String queryString =  "SELECT ?s ?p ?o \r\n"
			+ "WHERE {\r\n"
			+ "  ?s ?p ?o .\r\n"
			+ "}\r\n"
			+ "";
	
	static String queryString2 =  "SELECT DISTINCT ?s  \r\n"
			+ "WHERE {\r\n"
			+ "  ?s ?p ?o .\r\n"
			+ "}\r\n"
			+ "";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedHashMap<String, LinkedHashMap<String, ArrayList<Object>>> hashMap1 = new LinkedHashMap<>();
		LinkedHashMap<String, LinkedHashMap<String, ArrayList<Object>>> hashMap2 = new LinkedHashMap<>();

		Model model1 = ModelFactory.createDefaultModel();
		model1.read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019march.ttl");

		Model model2 = ModelFactory.createDefaultModel();
		model2.read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019august.ttl");

		fetch(model1, hashMap1);
		fetch(model2, hashMap2);
		compare(hashMap1, hashMap2);
	}

	private static void compare(LinkedHashMap<String, LinkedHashMap<String, ArrayList<Object>>> hashMap1, LinkedHashMap<String, LinkedHashMap<String, ArrayList<Object>>> hashMap2) {
		// TODO Auto-generated method stub
		
		System.out.println(hashMap1.size());
		System.out.println(hashMap2.size());
		
		// iterating all subject from file 1
		for (Entry<String, LinkedHashMap<String, ArrayList<Object>>> entry : hashMap1.entrySet()) {
			String key = entry.getKey();
			LinkedHashMap<String, ArrayList<Object>> valueMap1 = entry.getValue();
			
			LinkedHashMap<String, ArrayList<Object>> valueMap2 = new LinkedHashMap<String, ArrayList<Object>>();
			
			if (hashMap2.containsKey(key)) {
				 valueMap2 = hashMap2.get(key);
			}
			
			for (Entry<String, ArrayList<Object>> entry2 : valueMap1.entrySet()) {
				String valueKey = entry2.getKey();
				ArrayList<Object> objectList1 = entry2.getValue();
				
				ArrayList<Object> objectList2 = new ArrayList<Object>();
				if (valueMap2.containsKey(valueKey)) {
					objectList2 = valueMap2.get(valueKey);
				}
				
				if (!objectList1.toString().equals(objectList2.toString())) {
					//System.out.println("Changes detected for: " + key + ", Prop: " + valueKey);
				}
			}
		}
	}

	private static void fetch(Model model1, LinkedHashMap<String, LinkedHashMap<String, ArrayList<Object>>> hashMap1) {
		// TODO Auto-generated method stub
		QueryExecution queryExecution = QueryExecutionFactory.create(queryString, model1);

		org.apache.jena.query.ResultSet results = queryExecution.execSelect();
		
//		ResultSetFormatter.out(ResultSetFactory.copyResults(results));
		
		LinkedHashMap<String, ArrayList<Object>> valueMap = new LinkedHashMap<>();
		
		String currentSubject = "";
		
		while (results.hasNext()) {
			QuerySolution solution = results.nextSolution();
			String subject = solution.getResource("s").toString();
			String predicate = solution.getResource("p").toString();
			
			// System.out.println(currentSubject.isEmpty());
			
			if (!currentSubject.equals(subject)) {
				if (!currentSubject.isEmpty()) {
					hashMap1.put(currentSubject, valueMap);
					valueMap = new LinkedHashMap<>();
					//System.out.println("dd"+subject);  
				}
				
				currentSubject = subject;
			}

			RDFNode objectNode = solution.get("o");

			
			//System.out.println("dd"+hashMap1.containsKey(subject));
			if (hashMap1.containsKey(subject)) {
				valueMap = hashMap1.get(subject);
				System.out.println("dd"+valueMap);
			}
			
			
			ArrayList<Object> objectList = new ArrayList<Object>();
			
			if (valueMap.containsKey(predicate)) {
				objectList = valueMap.get(predicate);
			}
			
			objectList.add(objectNode);
			
			valueMap.put(predicate, objectList);
		}
		
		hashMap1.put(currentSubject, valueMap);
		valueMap = new LinkedHashMap<>();
		
		
	}
}

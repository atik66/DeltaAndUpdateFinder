package kkk;

import java.util.ArrayList;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

public class Model2 {

	

	static ArrayList<Triples> triples1= new ArrayList<>();
	static String queryString =  "SELECT ?s ?p ?o \r\n"
			+ "WHERE {\r\n"
			+ "  ?s ?p ?o .\r\n"
			+ "}\r\n"
			+ "";
	
	public static void main(String[] args) {
		
	
		Model m1=ModelFactory.createDefaultModel();
		m1.read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019august.ttl");
		

		
		
		
		try {
			
			 
			
			// Create a SPARQL query execution object
			QueryExecution queryExecution = QueryExecutionFactory.create(queryString, m1);

			// Execute the query and obtain the result set
			org.apache.jena.query.ResultSet results = queryExecution.execSelect();
			 while (results.hasNext()) {
			        QuerySolution solution = results.nextSolution();
			       org.apache.jena.rdf.model.Resource subject = solution.getResource("s");
			       org.apache.jena.rdf.model.Resource predicate = solution.getResource("p");
//			       org.apache.jena.rdf.model.Resource object = solution.getResource("o");
			       
			       
			       RDFNode objectNode = solution.get("o");

	                if (objectNode.isResource()) {
	                    // Handle resource (subject)
	                    org.apache.jena.rdf.model.Resource object = objectNode.asResource();
//	                    System.out.println("Subject: " + subject.toString());
//	                    System.out.println("Object (Resource): " + object.toString());
	                    triples1.add(new Triples(subject.toString(),predicate.toString(),object.toString()));
	                } else {
	                    // Handle literal (object)
	                    String object = objectNode.toString();
	                    triples1.add(new Triples(subject.toString(),predicate.toString(),object));
//	                    System.out.println("Subject: " + subject.toString());
//	                    System.out.println("Object (Literal): " + object);
	                }
			       
			       
			    
			      
			       
//			        System.out.println("Subject: " + subject.toString());
//			        System.out.println("predicate: " + predicate.toString());
//			        System.out.println("object: " + object.toString());
			    }
//                        	       
	         
		
	         	
			ResultSetFormatter.out((org.apache.jena.query.ResultSet) results);
		} catch (QueryParseException qpe) {
			System.err.println("SPARQL query syntax error: " + qpe.getMessage());
		}
		
		print2();
	}
	
	private static void print2() {
		// TODO Auto-generated method stub
		for(Triples t:triples1) {
			System.out.println("sub :"+t.getSubject());
			System.out.println("pre :"+t.getPredicate());
			System.out.println("obj :"+t.getObject());
		}
		
		
		System.out.print(triples1.size());
		
	}
		
}

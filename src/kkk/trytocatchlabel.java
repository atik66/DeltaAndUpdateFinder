package kkk;


import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

public class trytocatchlabel {

	 static String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
     		+ "SELECT ?s ?p ?o ?sLabel  ?oLabel\n"
     		+"WHERE {\n" +
     		"  ?s ?p ?o.\n" +
     		
     		"    ?s rdfs:label ?sLabel.\n" +
     		 
     		 "   ?o rdfs:label ?oLabel.\n" +
     		 
     		 "}";

	
	
    public static void main(String[] args) {
        // Define your SPARQL query with label retrieval
       
        		

        Model m=ModelFactory.createDefaultModel();
		m.read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019march.ttl");
        
        Model m1=ModelFactory.createDefaultModel();
		m1.read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019august.ttl");
		
		
		
		//fetch1(m);
		fetch2(m1);
		
      
    }

	private static void fetch2(Model m1) {
	       Query query = QueryFactory.create(queryString);

	        // Create a Jena QueryExecution object using your dataset or model
	        // Replace 'model' with your own dataset or model
	        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, m1)) {
	            ResultSet resultSet = queryExecution.execSelect();

	            // Iterate through the results and process them
	            while (resultSet.hasNext()) {
	                QuerySolution solution = resultSet.nextSolution();

	                // Retrieve values by variable name including labels
	                RDFNode sLabel = solution.get("sLabel");
	                
	                RDFNode oLabel = solution.get("oLabel");

	                // Do something with the values, which include labels
	                System.out.println("Subject Label: " + sLabel);
	             
	                System.out.println("Object Label: " + oLabel);
	            }
	        } catch (QueryParseException qpe) {
	            System.err.println("SPARQL query syntax error: " + qpe.getMessage());
	        }
		
	}

	private static void fetch1(Model m) {

		
        Query query = QueryFactory.create(queryString);

        // Create a Jena QueryExecution object using your dataset or model
        // Replace 'model' with your own dataset or model
        try (QueryExecution queryExecution = QueryExecutionFactory.create(query, m)) {
            ResultSet resultSet = queryExecution.execSelect();

            // Iterate through the results and process them
            while (resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();

                // Retrieve values by variable name including labels
                RDFNode sLabel = solution.get("sLabel");
                
                RDFNode oLabel = solution.get("oLabel");

                // Do something with the values, which include labels
                System.out.println("Subject Label: " + sLabel);
             
                System.out.println("Object Label: " + oLabel);
            }
        } catch (QueryParseException qpe) {
            System.err.println("SPARQL query syntax error: " + qpe.getMessage());
        }
		
	}
}

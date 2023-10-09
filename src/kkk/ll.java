package kkk;






import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import com.github.andrewoma.dexx.collection.List;


public class ll {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
          System.out.print("dd");
          Model [] m= new Model[6];
          m[1] =  ModelFactory.createDefaultModel();
          m[2] =  ModelFactory.createDefaultModel();
          m[3] =  ModelFactory.createDefaultModel();
          m[4] =  ModelFactory.createDefaultModel();
          m[5] =  ModelFactory.createDefaultModel();
          m[1].read( "C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\2020january.ttl" );
          m[2].read( "C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\2020april.ttl" );
          m[3].read( "C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\2020december.ttl" );
          m[4].read( "C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\2021april.ttl" );
          m[5].read( "C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\2021december.ttl" );
          
          
          
         
          Model model = m[2].difference(m[1]);
         
         //m2.write(System.out);
         
         String queryString = "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\r\n"
         		+ "PREFIX wd: <http://www.wikidata.org/entity/>\r\n"
         		+ "\r\n"
         		+ "SELECT  ?l ?k \r\n"
         		+ "WHERE {\r\n"
         		+ "  wd:Q81068910 wdt:P1120 ?l.\r\n"
         		+ "  wd:Q81068910 wdt:P1603 ?k.\r\n"
         		+ "\r\n"
         		+ "\r\n"
         		+ "}";
//try {
//         // Create a SPARQL query execution object
//         QueryExecution queryExecution = QueryExecutionFactory.create(queryString, m[1]);
//
//         // Execute the query and obtain the result set
//                org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();
//
//             // Process and print the query results
//             ResultSetFormatter.out((org.apache.jena.query.ResultSet) resultSet);
//         }
//         catch (QueryParseException qpe) {
//             System.err.println("SPARQL query syntax error: " + qpe.getMessage());
//        
//         }
//
//
//      
//try {
//    // Create a SPARQL query execution object
//    QueryExecution queryExecution = QueryExecutionFactory.create(queryString, m[2]);
//
//    // Execute the query and obtain the result set
//           org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();
//
//        // Process and print the query results
//        ResultSetFormatter.out((org.apache.jena.query.ResultSet) resultSet);
//    }
//    catch (QueryParseException qpe) {
//        System.err.println("SPARQL query syntax error: " + qpe.getMessage());
//    }


     

model.write(System.out);


try {
    // Create a SPARQL query execution object
    QueryExecution queryExecution = QueryExecutionFactory.create(queryString, model);

    // Execute the query and obtain the result set
           org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();

        // Process and print the query results
        ResultSetFormatter.out((org.apache.jena.query.ResultSet) resultSet);
    }
    catch (QueryParseException qpe) {
        System.err.println("SPARQL query syntax error: " + qpe.getMessage());
    }




            
//          
//          StmtIterator iter = model.listStatements();
//          while (iter.hasNext()) {
//              Statement stmt = (Statement) iter.nextStatement();
//
//              // Get the subject, predicate, and object of the statement
//              String subject = ((org.apache.jena.rdf.model.Statement) stmt).getSubject().toString();
//              String predicate = ((org.apache.jena.rdf.model.Statement) stmt).getPredicate().toString();
//              String object = ((org.apache.jena.rdf.model.Statement) stmt).getObject().toString();
//
//              // Print the triple in subject-predicate-object format
//              System.out.println("Subject: " + subject);
//              System.out.println("Predicate: " + predicate);
//              System.out.println("Object: " + object);
//              System.out.println();
//          }
          
          
              
	}

}

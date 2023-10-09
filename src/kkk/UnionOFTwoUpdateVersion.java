package kkk;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class UnionOFTwoUpdateVersion {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model model1 = ModelFactory.createDefaultModel();
		model1.read("E:\\hourlyIC\\Update\\000002.nt");

		Model model2 = ModelFactory.createDefaultModel();
		model2.read("E:\\hourlyIC\\Update\\000003.nt");
		
		Model model3=ModelFactory.createDefaultModel();
		
		model3=model1.add(model2);
		
		//Methods.print(model3);
		
		
		String dm="PREFIX dbpedia: <http://dbpedia.org/resource/>\r\n"
				+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\r\n"
				+ "PREFIX example: <http://example.org/property/>\r\n"
				+ "PREFIX kk:<http://dbpedia.org/ontology/>\r\n"
				+ "\r\n"
				+ "SELECT *\r\n"
				+ "WHERE {\r\n"
				+ "  dbpedia:2015_ATP_Challenger_Tour  kk:found ?value .\r\n"
				+"?value <http://www.w3.org/2002/07/owl/sameAs> ?tt.\r\n"
				+"?tt <http://example.org/property/fromdate> ?fromdate.\r\n"
				+"?tt <http://example.org/property/todate> ?todate.\r\n"
				+ "?value <http://dbpedia.org/ontology/wikiPageEditLink> ?changeValue.\r\n"
				
				
				+ "}\r\n"
				+ "";
				
		String dm1="PREFIX example: <http://example.org/property/>\r\n"
				+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\r\n"
				+"PREFIX About: <http://dbpedia.org/resource/>\r\n"
				+ ""
				+ "\r\n"
				+ "SELECT ?instance ?p ?fromDate ?toDate ?wikiPageEditLink\r\n"
				+ "WHERE {\r\n"
				+ "  ?instance ?p ?o.\r\n"    
				+ "  ?instance <http://www.w3.org/2002/07/owl/sameAs> ?o .\r\n"
				+ "  ?instance example:fromdate ?fromDate .\r\n"
				+ "  ?instance example:todate ?toDate .\r\n"
				+ "  ?instance ?k ?wikiPageEditLink .\r\n"
				+ "}\r\n"
				+ "";
		
		QueryExecution queryExecution4 = QueryExecutionFactory.create(dm1, model3);
		// Execute the query and obtain the result set
		ResultSet resultSet4 = queryExecution4.execSelect();
		

		
		Methods.print(resultSet4);
		
		
	}

}

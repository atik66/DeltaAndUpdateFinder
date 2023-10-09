package kkk;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.jena.dboe.sys.Sys;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.RDFWriter;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.FileManager;

import com.fasterxml.jackson.databind.util.TypeKey;



public class UpdateFinder {

     static HashMap<String,String> hasornot = new HashMap<>();
     static List<String> subjectversion = new ArrayList<>(); 
     static List<String> subjectdelta = new ArrayList<>();
     static String fromdate;
	 String todate;
     
	
	
	static String dm="SELECT ?literal\r\n"
    		+ "WHERE {\r\n"
    		+ "  <http://dbpedia.org/resource/2015_AFL_season> <http://dbpedia.org/ontology/Modified> ?literal .\r\n"
    		+ "}";
	
	static String dm1="SELECT DISTINCT ?subject\r\n"
			+ "WHERE {\r\n"
			+ "  ?subject ?predicate ?object .\r\n"
			+ "}\r\n"
			+ "";
	
	
	
	
	 public static void main(String[] args) {
		
		
		//declare time property from date and todate
		String predicateURI = "http://example.org/property/fromdate";
		String predicateURI1 = "http://example.org/property/todate"	;	
		
		  
		  String filePath = "E:\\7th semister\\Thesis\\covid19\\dbchange.nt";// delta
		  
		  String filePath1 = "E:\\7th semister\\Thesis\\covid19\\00002.nt"; // version 
		  
		
	       
	        Model model = ModelFactory.createDefaultModel();
	        Model model1 = ModelFactory.createDefaultModel();
	      
	        FileManager.get().readModel(model, filePath);
	        FileManager.get().readModel(model1, filePath1);
	       
	        
	        Property predicate = model1.createProperty(predicateURI); 
            Property predicate1 = model1.createProperty(predicateURI1);
	   
	        
	        QueryExecution queryExecution = QueryExecutionFactory.create(dm, model1);

            // Execute the query and obtain the result set
            ResultSet resultSet = queryExecution.execSelect();
            
            while (resultSet.hasNext()) {
		        QuerySolution solution = resultSet.nextSolution();
		        RDFNode object = solution.get("literal");
		        fromdate=object.toString();
    	       
            }
          
            
            QueryExecution queryExecution1 = QueryExecutionFactory.create(dm1, model1);

            // Execute the query and obtain the result set
            ResultSet resultSet1 = queryExecution1.execSelect();
            
            while (resultSet1.hasNext()) {
		        QuerySolution solution = resultSet1.nextSolution();
		        Resource subject = solution.getResource("subject");
		        subjectversion.add(subject.toString());
    	       
		       
            }
            
            
            
            QueryExecution queryExecution2 = QueryExecutionFactory.create(dm1, model);

            // Execute the query and obtain the result set
            ResultSet resultSet2 = queryExecution2.execSelect();
            
            while (resultSet2.hasNext()) {
		        QuerySolution solution = resultSet2.nextSolution();
		        Resource subject = solution.getResource("subject");
		        subjectdelta.add(subject.toString());
		       // System.out.print(subject);
            }
            
            
            
            for(String subd: subjectdelta) {
            	
            	//System.out.print(subd);
            	boolean k=subjectversion.contains(subd);
            	
            	
            	if(k) {
            		System.out.println(subd);
            		String e=subd;
            		String dm3="PREFIX ex: <http://example.org/>\r\n"
            				+ "\r\n"
            				+ "SELECT (COUNT(*) AS ?rowCount) ?object\r\n"
            				+ "WHERE {\r\n"
            				+ "<"+e+">  <http://dbpedia.org/ontology/Modified>  ?object .\r\n"
            				+ "}\r\n"
            				+ "";
            		
            		 QueryExecution queryExecution3 = QueryExecutionFactory.create(dm1, model1);

                     // Execute the query and obtain the result set
                     ResultSet resultSet3 = queryExecution3.execSelect();
                     
                        resultSet3.hasNext();
         		        QuerySolution solution = resultSet3.nextSolution();
         		        Resource subject = solution.getResource("rowcount");
         		       RDFNode object = solution.get("object");
         		        System.out.println(subject);
         		        if(subject==null) {
         		        	Property ss = model1.createProperty(subd); 
         		        	model1.addLiteral(ss, predicate, fromdate);
         		        	model1.addLiteral(ss, predicate1,"Infinite");
         		        }
             	        
         		        else {
         		        	Property ss = model1.createProperty(subd); 
         		        	model1.addLiteral(ss, predicate, object);
         		        	model1.addLiteral(ss, predicate1,fromdate);
         		        }
         		        
                     
            		
            		
            		
            	}
            	
            	
            	
            }
           
            
            
            Methods.print(model1);
	        
	        
//	        
//	        Property predicate = model1.createProperty(predicateURI); 
//            Property predicate1 = model1.createProperty(predicateURI1);
//
//	        // Iterate over delta and put unique subject to hasornot map
//	        
//	        StmtIterator stmtIterator = model1.listStatements();
//	        while (stmtIterator.hasNext()) {
//	            Statement stmt = stmtIterator.nextStatement();
//	            Resource subject = stmt.getSubject();
//	            Resource pred = stmt.getPredicate();
//                 // System.out.println(subject);
//                 // model1.addLiteral(subject, predicate,"sjjj");
//	            // Check if the subject is already in the hasornot map
//	            if (!hasornot.containsKey(subject.toString())) {
//	                // Mark the subject as processed
//	                hasornot.put(subject.toString(), "11");
//	                
//	            }
//	        }
//	        
//	    //    Methods.print(model1);
//	        
////	        for (String name: hasornot.keySet()) {
////	            String key = name.toString();
////	            String value = hasornot.get(name).toString();
////	            System.out.println(key + " " + value);
////	        }
//
//	        
//	       
//	        // iterate over the version 
//	        StmtIterator stmtIterator1 = model1.listStatements();
//	        while (stmtIterator1.hasNext()) {
//	        	int flag=1;
//	            Statement stmt1 = stmtIterator1.nextStatement();
//	            Resource subject1 = stmt1.getSubject();
//
//	            // Check if the changeset subject exist in version or not 
//	            if (hasornot.get(subject1.toString())=="11") {
//	            	hasornot.put(subject1.toString(), "22"); // if present then we enter into only one time in this segment otherwise not
//	            	
//           	 
//	            	//property create for the version
////	            	 Property predicate = model1.createProperty(predicateURI); 
////	                 Property predicate1 = model1.createProperty(predicateURI1);
//          
//                 //this inner loop check predicate and literal of the associate subject that present in both delta and version 
//                 StmtIterator stmtIterator4 = model1.listStatements();
//                 while (stmtIterator4.hasNext()) {
//     	            Statement stmt4 = stmtIterator4.nextStatement();
//     	            Resource subject5 = stmt4.getSubject(); 
//     	            Resource pred = stmt4.getPredicate();
//     	            RDFNode obj = stmt4.getObject();
//     	     
//     	            //check the associate subject has a  todate predicate or not
//     	           if(subject5.toString().equals(subject1.toString())&& pred.toString().equals("http://example.org/property/todate")) {
//     	        	   System.out.println(pred);
//     	        	   flag=0;
//     	        	   
//     	        	  statementsToRemove.add(stmt4);  //remove todate
//     	        	  Statement newStatement = model1.createStatement(subject1, predicate, obj); //add from date equal todate
//                      triplesToAdd.add(newStatement);
//                      
//     	           }
//     	           
//     	           else if(subject5.toString().equals(subject1.toString())&& pred.toString().equals("http://example.org/property/fromdate")) {
//     	        	  System.out.println(pred);
//    	        	   flag=0;
//    	        	   
//    	        	  statementsToRemove.add(stmt4);  //remove fromdate
//    	        	  Statement newStatement = model1.createStatement(subject1, predicate1, "currentcurent"); //add to date equal current
//                     triplesToAdd.add(newStatement);
//     	           }
//     	           
//                 }
//     	          
//               //first occurrence  
//               if(flag==1) {
//            	   Date date = Calendar.getInstance().getTime();  
//            	   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
//            	   String strDate = dateFormat.format(date);  
//            	   Statement newStatement = model1.createStatement(subject1, predicate,"Infinite");
//                   Statement newStatement1 = model1.createStatement(subject1, predicate1, strDate);
//                   triplesToAdd.add(newStatement);
//                   triplesToAdd.add(newStatement1);
//               }
//     	            
//                 
//	           
//	        }
//
//	        }
//	  
//	        //update the past (from to) property
//	        for (Statement stmtToRemove : statementsToRemove) {
//	            model1.remove(stmtToRemove);
//	        }
//	        
//	        for (Statement tripleToAdd : triplesToAdd) {
//	            model1.add(tripleToAdd);
//	        }
//	        
//	        //print triples
//	        StmtIterator stmtIterator2 = model1.listStatements();
//       while (stmtIterator2.hasNext()) {
//	            Statement stmt1 = stmtIterator2.nextStatement();
//	            Resource subject1 = stmt1.getSubject();
//	            Resource predicate3 = stmt1.getPredicate();
//	            RDFNode ob=stmt1.getObject();
//	            System.out.println(subject1.toString()+" "+predicate3+" "+ob);
//	        }
//	        
//       
//       
       
       
	      
	        }
	
}

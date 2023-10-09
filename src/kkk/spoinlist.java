package kkk;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import kkk.Triples;

import javax.annotation.Resource;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

public class spoinlist {

	static ArrayList<Triples> triples= new ArrayList<>();
	static ArrayList<Triples> triples1= new ArrayList<>();
	static ArrayList<Triples> differenceList= new ArrayList<>();
	static String queryString =  "SELECT ?s ?p ?o \r\n"
			+ "WHERE {\r\n"
			+ "  ?s ?p ?o .\r\n"
			+ "}\r\n"
			+ "";
	
	public static void main(String[] args) {
		
		
	
		
		Model m=ModelFactory.createDefaultModel();
		m.read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019march.ttl");
		
		Model m1=ModelFactory.createDefaultModel();
		m1.read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019august.ttl");
		
		
		
		
		fetch1(m);
	
		fetch2(m1);
		
	findDifference();
		
//		WriteObjectToFile(triples1);
	
		
		print2();
		
//		new java.util.Timer().schedule( 
//		        new java.util.TimerTask() {
//		            @Override
//		            public void run() {
//		            	System.out.print(difference());
//		            }
//		        }, 
//		        5000 
//		);
//		//
	
		
	}

	

	
	private static void findDifference() {
	    for (Triples t : triples1) {
	        boolean found = false;
	        for (Triples t1 : triples) {
	            if (t.equals(t1)) {
	                found = true;
	                break;
	            }
	        }
	        if (!found) {
	            differenceList.add(t); // Add the difference to the new list
	        }
	    }
	}
	
	
	
	

	


//	private static int difference() {
//		    int values = 0;
//		    int f=1;
//		    if(triples != null && triples1 != null) {
//		        
//		        for(Triples t:triples1) {
//		        	System.out.println("ttt :"+t);
//		        	for(Triples k:triples) {
//		        		System.out.println("kkkk :"+k);
////		        		if(compare(t,k)) {
////		        			values++;
////		        			break;
////		        		}
//		        		
//		        		
//		        	}
//		        	
//		        	
//		        	
//		        	
//		        	
//		        	
//		        }
//		        
//		        
//		    }
//		    return values;
//		    
//		}
		







//
//	private static boolean compare(Object t,Object k) {
//		// TODO Auto-generated method stub
//		
//		if(((Triples) t).getSubject().equals(((Triples) k).getSubject())&& ((Triples) t).getPredicate().equals(((Triples) k).getPredicate()) && ((Triples) t).getObject().equals(((Triples) k).getObject())) {
//			
//			return false;
//		}
//		return true;
//		
////		if(t.equals(k)) {
////			return false;
////		}
////		return true;
//		
//	}







	private static void fetch2(Model m1) {
		// TODO Auto-generated method stub
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
			       
			      // System.out.println(subject);
			       
			       RDFNode objectNode = solution.get("o");
			       
			       
			       triples.add(new Triples(subject.toString(),predicate.toString(),objectNode));

//	                if (objectNode.isResource()) {
//	                    // Handle resource (subject)
//	                    org.apache.jena.rdf.model.Resource object = objectNode.asResource();
////	                    System.out.println("Subject: " + subject.toString());
////	                    System.out.println("Object (Resource): " + object.toString());
//	                    triples1.add(new Triples(subject.toString(),predicate.toString(),object.toString()));
//	                } else {
//	                    // Handle literal (object)
//	                    String object = objectNode.toString();
//	                    triples1.add(new Triples(subject.toString(),predicate.toString(),object));
////	                    System.out.println("Subject: " + subject.toString());
////	                    System.out.println("Object (Literal): " + object);
//	                }
//			       
			       
			    
			      
			       
//			        System.out.println("Subject: " + subject.toString());
//			        System.out.println("predicate: " + predicate.toString());
//			        System.out.println("object: " + object.toString());
			    }
//                        	       
	         
		
	         	
			ResultSetFormatter.out((org.apache.jena.query.ResultSet) results);
		} catch (QueryParseException qpe) {
			System.err.println("SPARQL query syntax error: " + qpe.getMessage());
		}
	}



     private static void fetch1(Model m) {
		
         try {
			
			 
			
			// Create a SPARQL query execution object
			QueryExecution queryExecution = QueryExecutionFactory.create(queryString, m);

			// Execute the query and obtain the result set
			org.apache.jena.query.ResultSet results = queryExecution.execSelect();
			 while (results.hasNext()) {
			        QuerySolution solution = results.nextSolution();
			       org.apache.jena.rdf.model.Resource subject = solution.getResource("s");
			       org.apache.jena.rdf.model.Resource predicate = solution.getResource("p");
//			       org.apache.jena.rdf.model.Resource object = solution.getResource("o");
			       
			       
			       
			       RDFNode objectNode = solution.get("o");
			       
			       triples.add(new Triples(subject.toString(),predicate.toString(),objectNode));

//	                if (objectNode.isResource()) {
//	                    // Handle resource (subject)
//	                    org.apache.jena.rdf.model.Resource object = objectNode.asResource();
////	                    System.out.println("Subject: " + subject.toString());
////	                    System.out.println("Object (Resource): " + object.toString());
//	                    triples.add(new Triples(subject.toString(),predicate.toString(),object.toString()));
//	                } else {
//	                    // Handle literal (object)
//	                    String object = objectNode.toString();
//	                    triples.add(new Triples(subject.toString(),predicate.toString(),object));
////	                    System.out.println("Subject: " + subject.toString());
////	                    System.out.println("Object (Literal): " + object);
//	                }
			       
			       
			    
			      
			       
//			        System.out.println("Subject: " + subject.toString());
//			        System.out.println("predicate: " + predicate.toString());
//			        System.out.println("object: " + object.toString());
			    }
//                        	       
	         
		
	         	
			ResultSetFormatter.out((org.apache.jena.query.ResultSet) results);
		} catch (QueryParseException qpe) {
			System.err.println("SPARQL query syntax error: " + qpe.getMessage());
		}
		
		
	}
     
     
     
//     public static void WriteObjectToFile(ArrayList<Triples> t ) {
//    	 
//    	 try{
//    		    FileOutputStream writeData = new FileOutputStream("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\k.txt");
//                ObjectOutputStream writeStream = new ObjectOutputStream(writeData);
//
//                writeStream.writeObject(t);
//                writeStream.flush();
//                writeStream.close();
//
//    		}catch (IOException e) {
//    		    e.printStackTrace();
//    		}
//     }



	private static void print1() {
		// TODO Auto-generated method stub
		
			
			 for(Triples t:differenceList) {
				
		
				System.out.println("sub :"+t.getSubject());
				System.out.println("pre :"+t.getPredicate());
				System.out.println("obj :"+t.getObject());
		      }
			System.out.println(differenceList.size());
		
//////		
//////			// TODO Auto-generated catch block
//////		
      		}
////	    
	
	
	
//	
//	
	
	private static void print2() {
		// TODO Auto-generated method stub
		
	
		for(Triples t:triples) {
			System.out.println("sub :"+t.getSubject());
			System.out.println("pre :"+t.getPredicate());
			System.out.println("obj :"+t.getObject());
		}
		System.out.println(triples.size());
	}
	
	
	
	
	
	
	
	
}

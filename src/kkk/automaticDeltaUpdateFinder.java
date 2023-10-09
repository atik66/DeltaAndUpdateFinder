package kkk;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.table.DefaultTableModel;

import org.apache.jena.n3.N3JenaWriter;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelCon;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.tdb2.TDB2Factory;
import org.apache.jena.util.FileManager;

public class automaticDeltaUpdateFinder {



	
		private static Dataset dataset;
	static  String basePath = "E:\\hourlyIC\\Mydataset\\new\\";
	  static	String tdbPath = basePath + "datasets";

//		static String basePath = "E:\\hourlyIC\\Mydatset\\"; // dataset path E:\\hourlyIC\\version\\new\\
//		static String tdbPath = basePath + "datasets";

		 // delta save in this path

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
	       int ii;
          for( ii=1;ii<5;ii++)	{	
        	    // dataset path E:\\hourlyIC\\version\\new\\
      		 
        	   String outputFile = "E:\\hourlyIC\\changeset\\";
        	  String change="";
        	  Model model1 = ModelFactory.createDefaultModel();
			//Scanner sc = new Scanner(System.in);
			//String str = sc.nextLine();
        	 int jj=ii+1;
			String first = Integer.toString(ii);
			//String str1 = sc.nextLine();
			String second =Integer.toString(jj);
			 change = first + "-" + second + "change.nt";
			outputFile += change;
			int k = first.length();
			String c = "";
			if (k < 7) {
				for (int i = 0; i < 6 - k; i++) {
					c += '0';
				}
			}

			String first1 = c + first;

			int l = second.length();
			String d = "";
			if (k < 7) {
				for (int i = 0; i < 6 - l; i++) {
					d += '0';
				}
			}

			String second1 = d + second;

			System.out.println("Input :"+ first1 + " " + second1);

			String dmQuery = "PREFIX version: <http://www.wikidata.org/entity/version/>\n"
					+ "PREFIX wd: <http://www.wikidata.org/entity/>\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
					+ "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" + "SELECT * WHERE {\n" + "		GRAPH version:"
					+ first + "{\n" + "		?s ?p ?o .\n" + "	} .\n" + "	FILTER (NOT EXISTS {\n" + "		GRAPH version:"
					+ second + "{\n" + "		?s ?p ?o .\n" + "		}\n" + "	})\n" + "} LIMIT 100 \n";

			String filePathOne = basePath + first1 + ".nt";
			String filePathTwo = basePath + second1 + ".nt";

			initDataset();

			Model firstModel = Methods.readModelFromPath(filePathOne);
			// Methods.print("kklllk"+firstModel);
			writeNamedModelToDataset("http://www.wikidata.org/entity/version/" + first, firstModel, dataset);

			Model secondModel = Methods.readModelFromPath(filePathTwo);
			// Methods.print("kkkk"+secondModel);
			writeNamedModelToDataset("http://www.wikidata.org/entity/version/" + second, secondModel, dataset);
			
			long startTime = System.currentTimeMillis();
			findDelta(dmQuery, outputFile);
			long endTime = System.currentTimeMillis();
	        long elapsedTime = endTime - startTime;
	        System.out.println("delta need time : "+elapsedTime);
	        
	        long startTime1 = System.currentTimeMillis();
			UpdateFinder(filePathOne, filePathTwo, outputFile, first1, second1);
			rename(filePathOne,first1);
			rename(filePathOne,second1);
			long endTime1 = System.currentTimeMillis();
	        long elapsedTime1 = endTime1 - startTime1;
	        System.out.println("Update Type 2 Need time : "+elapsedTime1);
			
	        long startTime2 = System.currentTimeMillis();
			UpdateTypeThree(filePathOne, filePathTwo, outputFile, first1, second1);
			long endTime2 = System.currentTimeMillis();
	        long elapsedTime2 = endTime2 - startTime2;
	        System.out.println("Update Type 3 Need time : "+elapsedTime2);
			System.out.println("done");
		}

		}	
		
		
		private static void UpdateTypeThree(String filePathOne, String filePathTwo, String outputFile, String first,
				String second) {
			// TODO Auto-generated method stub
			
			String updatefile = "E:\\hourlyIC\\Update3\\"; // updatetype2 save in this path version wise
			String updatefile2 = updatefile + second + ".nt";
			
			List<Statement> subjectListold = new ArrayList<>();
			List<Statement> subjectListexpire = new ArrayList<>();
			String dm = "SELECT  ?subject ?predicate ?object\r\n" + "WHERE {\r\n" + "  ?subject ?predicate ?object .\r\n" + "}\r\n"
					+ "";

			Model model = ModelFactory.createDefaultModel();
			Model model2 = ModelFactory.createDefaultModel();
			FileManager.get().readModel(model, outputFile);// delta
			FileManager.get().readModel(model2, filePathTwo);
			QueryExecution queryExecution1 = QueryExecutionFactory.create(dm, model);

			ResultSet resultSet1 = queryExecution1.execSelect();

			while (resultSet1.hasNext()) {
				QuerySolution solution = resultSet1.nextSolution();
				Resource subject = solution.getResource("subject");
				Resource predicate = solution.getResource("predicate");
				RDFNode object = solution.get("object");
				//System.out.println(subject);
				String sparqlQueryString = String.format(
			            "ASK WHERE { <%s> ?p ?o }",
			            subject.toString()
			        );
				try (QueryExecution qexec = QueryExecutionFactory.create(sparqlQueryString, model2)) {
		            // Execute the query and get the result (true or false)
		            boolean subjectExists = qexec.execAsk();
		            
		            // Check the result
		            if (subjectExists) {
		            String	newpredicate=predicate.toString()+"/oldvalue"+first;
		            Property  predicateresouce = ResourceFactory.createProperty(newpredicate);
		            Statement newStmt = ResourceFactory.createStatement(subject, predicateresouce, object);
		               subjectListold.add(newStmt);
		            } else {
		            	String	newpredicate=predicate.toString()+"/expire"+first;
		 	            Property  predicateresouce = ResourceFactory.createProperty(newpredicate);
		 	            Statement newStmt = ResourceFactory.createStatement(subject, predicateresouce, object);
		 	           subjectListexpire.add(newStmt);
		            }
		        }
				

			}
			
			for( Statement x:subjectListold) {
				model2.add(x);
			}
			
			for( Statement x:subjectListexpire) {
				model2.add(x);
			}
			
			
			try {
				FileOutputStream outputStream = new FileOutputStream(updatefile2);
				model2.write(outputStream, "NTRIPLES");
				System.out.println("RDF Update type 3 data saved to " + updatefile2);
				// You can specify other RDF formats here
				outputStream.close();

				//System.out.println("RDF data saved to ");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
		}




		private static void rename(String filePathOne,String first) {
			
			ArrayList<Statement> removestatement=new ArrayList<>();
			
			String dm1 = "SELECT DISTINCT ?subject\r\n" + "WHERE {\r\n" + "  ?subject ?predicate ?object .\r\n" + "}\r\n"
					+ "";
			Model model = ModelFactory.createDefaultModel();
			Model modelchangeset = ModelFactory.createDefaultModel();
			FileManager.get().readModel(model,filePathOne);
			String filepathtwo="E:\\hourlyIC\\Update\\"+first+".nt";
			FileManager.get().readModel(modelchangeset,filepathtwo);
			//Methods.print(modelchangeset);
			QueryExecution queryExecution = QueryExecutionFactory.create(dm1, model);

			// Execute the query and obtain the result set
			ResultSet resultSet = queryExecution.execSelect();
			
			while (resultSet.hasNext()) {
				QuerySolution solution = resultSet.nextSolution();
				String subject = solution.getResource("subject").toString();
				String newsubject=subject.toString()+first;
				Resource newsubject1=modelchangeset.createResource(newsubject);
				StmtIterator iter =modelchangeset.listStatements();
				 while (iter.hasNext()) {
			            Statement stmt = iter.nextStatement();
			           // System.out.println(stmt);

			            // Check if the subject of the statement matches the old subject
			            if (stmt.getSubject().toString().equals(subject)) {
			                // Create a new statement with the new subject
			            	
			            	//System.out.println(stmt);
			            	 removestatement.add(stmt);
			                Statement newStmt = ResourceFactory.createStatement(newsubject1, stmt.getPredicate(), stmt.getObject());
	                       
			                // Add the new statement to the modified model
			                modelchangeset.add(newStmt);
			            } else {
			                // Add the original statement to the modified model
			            	modelchangeset.add(stmt);
			            }
			        }
				 //Methods.print("ddddd"+modelchangeset);
			

			}
			
			
			for(Statement x: removestatement) {
				modelchangeset.remove(x);
			}
			
			

			
			//Methods.print(resultSet);
		}
		
		
		
		
		//update type 2

		private static void UpdateFinder(String filePathOne, String filePathTwo, String outputFile, String first,
				String second) {

			int klll = 0;
			RDFNode obje1 = null;
			String updatefile = "E:\\hourlyIC\\Update\\"; // updatetype2 save in this path version wise
			String updatefile1 = updatefile + first + ".nt";
			String updatefile2 = updatefile + second + ".nt";
			HashMap<String, String> hasornot = new HashMap<>();
			List<String> subjectversion = new ArrayList<>();
			List<String> subjectdelta = new ArrayList<>();
			String fromdate = null;
			String todate = null;
			String predicateURI = "http://example.org/property/fromdate";
			String predicateURI1 = "http://example.org/property/todate";
			String predicateURI3="http://www.w3.org/2002/07/owl/sameAs";

			String dm = "SELECT ?literal\r\n" + "WHERE {\r\n"
					+ "  <http://dbpedia.org/resource/2015_version> <http://dbpedia.org/ontology/Modified> ?literal .\r\n"
					+ "}";

			String dm1 = "SELECT DISTINCT ?subject\r\n" + "WHERE {\r\n" + "  ?subject ?predicate ?object .\r\n" + "}\r\n"
					+ "";

			Model model = ModelFactory.createDefaultModel();
			Model model1 = ModelFactory.createDefaultModel();
			Model model2 = ModelFactory.createDefaultModel();

			FileManager.get().readModel(model, outputFile);// delta
			FileManager.get().readModel(model1, filePathOne);// version
			FileManager.get().readModel(model2, filePathTwo);// version

			Property predicate = model1.createProperty(predicateURI);
			Property predicate1 = model1.createProperty(predicateURI1);
	         Property predicate3 = model1.createProperty(predicateURI3);

			QueryExecution queryExecution = QueryExecutionFactory.create(dm, model1);

			// Execute the query and obtain the result set
			ResultSet resultSet = queryExecution.execSelect();
	        //Methods.print(resultSet);
			while (resultSet.hasNext()) {
				QuerySolution solution = resultSet.nextSolution();
				RDFNode object = solution.get("literal");
				fromdate = object.toString();
				///System.out.println(fromdate);
			}

			QueryExecution queryExecution4 = QueryExecutionFactory.create(dm, model2);

			// Execute the query and obtain the result set
			ResultSet resultSet4 = queryExecution4.execSelect();

			while (resultSet4.hasNext()) {
				QuerySolution solution = resultSet4.nextSolution();
				RDFNode object = solution.get("literal");
				todate = object.toString();
				//System.out.println(todate);
			}

			QueryExecution queryExecution1 = QueryExecutionFactory.create(dm1, model2);

			ResultSet resultSet1 = queryExecution1.execSelect();

			while (resultSet1.hasNext()) {
				QuerySolution solution = resultSet1.nextSolution();
				Resource subject = solution.getResource("subject");
				//System.out.print(subject);
				subjectversion.add(subject.toString());

			}

			QueryExecution queryExecution2 = QueryExecutionFactory.create(dm1, model);

			// Execute the query and obtain the result set
			ResultSet resultSet2 = queryExecution2.execSelect();

			while (resultSet2.hasNext()) {
				QuerySolution solution = resultSet2.nextSolution();
				Resource subject = solution.getResource("subject");
				subjectdelta.add(subject.toString());
				//System.out.print(subject);
			}

			for (String subd : subjectdelta) {
				String e = subd;
				//System.out.println(e);
				String dm3 = "PREFIX ex: <http://example.org/>\r\n" + "\r\n" + "SELECT ?object \r\n" + "WHERE {\r\n" + "<"
						+ e + ">  <http://example.org/property/todate>  ?object .\r\n" + "}\r\n" + "";

				QueryExecution queryExecution3 = QueryExecutionFactory.create(dm3, model1);

				// Execute the query and obtain the result set
				ResultSet resultSet3 = queryExecution3.execSelect();

				if (resultSet3.hasNext()) {
					QuerySolution solution = resultSet3.nextSolution();
					RDFNode object = solution.get("object");
					obje1 = object;
					//System.out.println(obje1);
				} else {
					klll = 1;
				}

//				fromdate += '.';
//				todate += '.';

				if (klll == 1) {
					//System.out.println(klll);
	                String k=subd+first;
	               
					Property ss = model1.createProperty(k);
					Property st = model1.createProperty(subd);
					//1System.out.println(fromdate);
					model1.addLiteral(ss, predicate, fromdate);
					model1.addLiteral(ss, predicate1, todate);
					model1.addLiteral(ss, predicate3, st);
				}

				else {

					Property ss = model1.createProperty(subd);
					model1.remove(ss, predicate1, obje1);
					model1.addLiteral(ss, predicate1, todate);

				}

			}

			try {
				FileOutputStream outputStream = new FileOutputStream(updatefile1);
				model1.write(outputStream, "NTRIPLES");
				// You can specify other RDF formats here
				outputStream.close();

				System.out.println("RDF data saved to " + updatefile1);
			} catch (IOException e) {
				e.printStackTrace();
			}

			for (String subd : subjectdelta) {

				// System.out.print(subd);
				boolean k = subjectversion.contains(subd);

				if (k) {
					// System.out.println(subd);
					String e = subd;
					//System.out.println(e);

//					fromdate += '.';
//					todate += '.';
					 String kk=subd+second;
					
					Property ss = model1.createProperty(kk);
					Property st = model1.createProperty(subd);
					model2.addLiteral(ss, predicate, todate);
					model2.addLiteral(ss, predicate1, "infinity");
					model2.addLiteral(ss, predicate3, st);

				}

			}

			try {

				FileOutputStream outputStream1 = new FileOutputStream(updatefile2);
				model2.write(outputStream1, "NTRIPLES");
				outputStream1.close();

				System.out.println("RDF data saved to " + updatefile2);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		private static void findDelta(String dmQuery, String outputFile) {

			Model model5 = ModelFactory.createDefaultModel();
			// TODO Auto-generated method stub
			boolean isInTransaction = dataset.isInTransaction();
			if (!isInTransaction) {
				dataset.begin(ReadWrite.READ);
			}

			try {
				// Create a QueryExecution object
				QueryExecution queryExecution = QueryExecutionFactory.create(dmQuery, dataset);

				ResultSet resultSet = queryExecution.execSelect();
//	            Methods.print(resultSet);

				while (resultSet.hasNext()) {
					QuerySolution solution = resultSet.nextSolution();
					Resource subj = solution.getResource("s");
					Resource pred = solution.getResource("p");
					RDFNode obj = solution.get("o");

					//System.out.println(subj + " " + pred);
					String ob = obj.toString() + ".";
					Property predicate1 = model5.createProperty(pred.toString());
					Statement statement = model5.createStatement(subj, predicate1, obj);
					model5.add(statement);

				}

				try {
					FileOutputStream outputStream = new FileOutputStream(outputFile);
					model5.write(outputStream, "NTRIPLES"); // You can specify other RDF formats here
					outputStream.close();
					System.out.println("RDF data saved to " + outputFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				// Close the output stream

				// System.out.println("Query results saved to " + outputFile);

				// Close the query execution
				queryExecution.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				// End the transaction and release resources
				dataset.end();
				dataset.close();
			}
		}

		private static void initDataset() {
			// TODO Auto-generated method stub
			String prefixFilePath = basePath + "rdf_archive.nt";
			dataset = TDB2Factory.connectDataset(tdbPath);

			Model prefixModel = Methods.readModelFromPath(prefixFilePath);
			writeModelToDataset(prefixModel, dataset);
		}

		private static void writeModelToDataset(Model model, Dataset dataset) {
			// TODO Auto-generated method stub
			dataset.begin(ReadWrite.WRITE);

			try {
				// Get the default model from the dataset
				Model datasetModel = dataset.getDefaultModel();

				// Add the data from the model to the dataset model
				datasetModel.add(model);

				// Commit the transaction
				dataset.commit();
			} catch (Exception e) {
				// Abort the transaction in case of an error
				dataset.abort();
				e.printStackTrace();
			} finally {
				// End the transaction and release resources
				dataset.end();
				dataset.close();
			}
		}

		private static void writeNamedModelToDataset(String version, Model model, Dataset dataset) {
			// TODO Auto-generated method stub
			dataset.begin(ReadWrite.WRITE);

			try {
				// Add the data from the model to the dataset model
				dataset.addNamedModel(version, model);

				// Commit the transaction
				dataset.commit();
			} catch (Exception e) {
				// Abort the transaction in case of an error
				dataset.abort();
				e.printStackTrace();
			} finally {
				// End the transaction and release resources
				dataset.end();
				dataset.close();
			}
		}

	}


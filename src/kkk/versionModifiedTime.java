package kkk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDFSyntax;

public class versionModifiedTime {
	public static void main(String[] args) {
        // Specify the file path to your NT format RDF file
       // String filePath = "E:\\hourlyIC\\alldata.IC\\000001.nt";
        
      ArrayList<String> Add=new ArrayList<>();
        String FolderPath = "E:\\hourlyIC\\version\\new\\";

        // Specify the target folder path where you want to save the modified files
        String sourceFolderPath = "E:\\hourlyIC\\version\\alldata.IC\\";
        
        String subject ="http://dbpedia.org/resource/2015_version" ;
        String predicate ="http://dbpedia.org/ontology/Modified";
       
        
        
        // Get a list of all files in the source folder
        File sourceFolder = new File(sourceFolderPath);
        File[] files = sourceFolder.listFiles();

        if (files != null) {
        	
            for (File file : files) {
            	String sentenceToAdd="";
                if (file.isFile()) {
                    try {
                        // Read the file content
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        StringBuilder content = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                        	 if (line.startsWith("#")) {
                                 // Extract and print the comment
                                 String comment = line.substring(1);
                                 // Remove the '#' character
                                 sentenceToAdd = comment.substring(2, comment.length());
                                // Add.add(sentenceToAdd);
                                // sentenceToAdd+="\""+substring1+"\".";
                                // System.out.println("Comment: "+sentenceToAdd );
                                  break;
                             }
                        	
                        	 
                           
                        }
                        reader.close();
                        String source = "E:\\hourlyIC\\version\\alldata.IC\\";
                        File source1 = new File(source);
                        String modelfile=source+file.getName();
                        Model model = ModelFactory.createDefaultModel();
                		FileManager.get().readModel(model, modelfile);
                	    Property sub = model.createProperty(subject);
                		Property pre = model.createProperty(predicate);
                		model.addLiteral(sub, pre, sentenceToAdd);
                		//Methods.print(model);
                		
                		try {
                			FileOutputStream outputStream = new FileOutputStream(FolderPath+file.getName());
                			model.write(outputStream, "NTRIPLES");
                			// You can specify other RDF formats here
                			outputStream.close();

                			System.out.println("done");
                		} catch (IOException e) {
                			e.printStackTrace();
                		}
                		
                       
                        
                        // Add the sentence to the content
                        //content.append(sentenceToAdd).append("\n");

                        // Create the target file path
//                        String targetFilePath = targetFolderPath + File.separator + file.getName();
//
//                        // Write the modified content to the target file
//                        BufferedWriter writer = new BufferedWriter(new FileWriter(targetFilePath));
//                        writer.write(content.toString());
//                        writer.close();

                        System.out.println("ok\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        
        
        }
        else {
            System.out.println("NO");
        }
        
        
         
        
   
        
        
        
        
        
        
	}}
        
    
	
	



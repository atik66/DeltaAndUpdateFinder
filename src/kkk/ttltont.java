package kkk;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.FileManager;

public class ttltont {

	public static void main(String[] args) {
	//	 Model model = ModelFactory.createDefaultModel();
//	        model.read("E:\\hourlyIC\\queryresults.rdf","RDF"); // Replace "your-data.ttl" with your Turtle file path
//
		  String tsvDataFile = "E:\\hourly\\query_results.tsv"; // Replace with the actual path to your TSV data file

	        // Create a Jena model
	        Model model = ModelFactory.createDefaultModel();

	        // Read RDF data in TSV format into the model
	    

	        // Define the output N-Triples file path
	        String ntOutputFile = "output_data.nt"; // Replace with the desired output file path

	        // Write the model to N-Triples format
	        try {
	            model.write(new FileOutputStream(ntOutputFile), "N-TRIPLE");
	            System.out.println("Conversion to N-Triples completed. Data saved to " + ntOutputFile);
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }

	        
	        // Define the output file path for the N-Triples data
	        //String outputFilePath = "output-data.nt"; // Replace with your desired output file path

//	       Resource m =model.getResource("http://dbpedia.org/ontology/wikiPageModified");
//	       
//	        
//	        
//	        // Use RDFDataMgr to write the model in N-Triples format
//	        RDFDataMgr.write(System.out, model, Lang.NTRIPLES); // Use DRDFTurtle to specify Turtle input
//	                // Set a base URI if needed
	                

	}

}

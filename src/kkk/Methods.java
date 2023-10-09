package kkk;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

import net.miginfocom.swing.MigLayout;

public class Methods {


    public static Model readModelFromPath(String filePath) {
        // TODO Auto-generated method stub
        Model model = ModelFactory.createDefaultModel();
        try {
            model.read(filePath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        return model;
    }

    
    
    
    public static String mergeAllTempFiles(int numOfFiles, String resultFile) {
        // TODO Auto-generated method stub
        createNewFile(resultFile);

        for (int i = 1; i <= numOfFiles; i++) {
            String filePath = Variables.TEMP_DIR + i + ".ttl";

            try {
//				System.out.println(filePath);

                Model model = readModelFromPath(filePath);
                if (model != null) {
                    String string = modelToString(model, getFileExtension(resultFile));
                    appendToFile(string, resultFile);

                    File file = new File(filePath);
                    boolean status = file.delete();

                    if (status) {
                        print("File deleted.");
                    }
                }
                // System.out.println(filePath + " deleted");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("Error in merging file. Check file: " + filePath);
            }
        }
        return "Success.\nFile Saved: " + resultFile;
    }


    public static void print(Object object) {
      // TODO Auto-generated method stub    
    	if (object instanceof LinkedHashMap) {
           for (Map.Entry<Object, Object> map : ((LinkedHashMap<Object, Object>) object).entrySet()) {
                Object key = map.getKey();
                Object value = map.getValue();

                print(key);
                print(value);
            }
        } else if (object instanceof String) {
            System.out.println(object);
        } else if (object instanceof ResultSet) {
            ResultSetFormatter.out(ResultSetFactory.copyResults((ResultSet) object));
        } else if (object instanceof Model) {
            ((Model) object).write(System.out, "TTL");
        } else if (object instanceof Boolean) {

        } else if (object instanceof ArrayList) {
            for (int i = 0; i < ((ArrayList<String>) object).size(); i++) {
                String string = ((ArrayList<String>) object).get(i);
               System.out.println(string);
            }
       }
    }
    
     
    
    public static void createNewFile(String fileName) {
        // TODO Auto-generated method stub
        File file = new File(fileName);
        try {
            boolean s1 = file.delete();
            boolean s2 = file.createNewFile();

            if (s1)
                print("File deleted");

            if (s2)
                print("File created");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    
    public static String getFileExtension(String filePath) {
        // TODO Auto-generated method stub
        String[] parts = filePath.split("\\.");
        if (parts.length == 2) {
            return parts[1];
        } else {
            return "";
        }
    }
    
    
    public static String modelToString(Model model, String extension) {
        // TODO Auto-generated method stub
        StringWriter out = new StringWriter();
        model.write(out, extension.toUpperCase());
        return out.toString();
    }
    
    
    
    public static void appendToFile(String string, String csvTarget) {
        // TODO Auto-generated method stub
        try {
            FileWriter fileWriter = new FileWriter(csvTarget, true);
            fileWriter.write(string);
            fileWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    
    
    
}

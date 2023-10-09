package kkk;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.jena.base.Sys;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryParseException;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import java.awt.Window.Type;
import net.miginfocom.swing.MigLayout;


public class MainFrame extends JFrame {
	int v1=0, v2=0,i=0,j=0,b=0;
	private JPanel contentPane;
	private JTable table;
	  private List<JTable> tables;
	    private List<List<Object>> tableData;
	DefaultTableModel modell,modell1;
	static Model[] m = new Model[9];
	int integerValue;
    String death="0";
    String cases="0";
    String recovery="0";
    String country="0";
//    String[] names = new String[1000];
//    String[] names1 = new String[1000];
//    String[] names2 = new String[1000];
    private List<String> morseList = new ArrayList<>();
    private Vector<String> vlohoiseList = new Vector<>();
    private List<String> akranthoList = new ArrayList<>();
    private JTable table_1;
    private JTable table_2;
    private JTable table_3;
    
	String queryString =  "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\r\n"
			+ "PREFIX wd: <http://www.wikidata.org/entity/>\r\n"
			
   +"PREFIX wikibase: <http://wikiba.se/ontology#>\r\n"
   +"PREFIX bd: <http://www.bigdata.com/rdf#>\r\n" 
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "SELECT  ?l   \r\n"
			+ "			 WHERE {\r\n"
			+ "			    wd:Q84263196 wdt:P1120 ?l.\r\n"
		
			
			+ "\r\n"
			
			+ "}";
	
	String queryString1 =  "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\r\n"
			+ "PREFIX wd: <http://www.wikidata.org/entity/>\r\n"
			
   +"PREFIX wikibase: <http://wikiba.se/ontology#>\r\n"
   +"PREFIX bd: <http://www.bigdata.com/rdf#>\r\n" 
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "SELECT   ?k  \r\n"
			+ "			 WHERE {\r\n"
			
			+ "			    wd:Q84263196 wdt:P1603 ?k. \r\n"
			
			
			+ "\r\n"
			
			+ "}";
	
	
	String queryString2 =  "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\r\n"
			+ "PREFIX wd: <http://www.wikidata.org/entity/>\r\n"
			
   +"PREFIX wikibase: <http://wikiba.se/ontology#>\r\n"
   +"PREFIX bd: <http://www.bigdata.com/rdf#>\r\n" 
			+ "\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "SELECT  ?M  \r\n"
			+ "			 WHERE {\r\n"
			
			+ "			   wd:Q84263196 wdt:P8010 ?M. \r\n"
			
			
			+ "\r\n"
			
			+ "}";
	private JTable table_4;
	private JTable table_5;
	
    

	public static void main(String[] args) {
	    
		
		m[1] = ModelFactory.createDefaultModel();
		m[2] = ModelFactory.createDefaultModel();
		m[3] = ModelFactory.createDefaultModel();
		m[4] = ModelFactory.createDefaultModel();
		m[5] = ModelFactory.createDefaultModel();
		m[6] = ModelFactory.createDefaultModel();
		m[7] = ModelFactory.createDefaultModel();
		m[1].read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019march.ttl");
		m[2].read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019april.ttl");
		m[3].read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019may.ttl");
		m[4].read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019june.ttl");
		m[5].read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019july.ttl");
		m[6].read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019august.ttl");
		m[7].read("C:\\Users\\HP\\eclipse-workspace\\kkk\\Data\\202019september.ttl");
		
		
		

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		
		
		setFont(new Font("Dialog", Font.BOLD, 25));
		setTitle("Welcome to Rdf Archive");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 400);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
						
						
						JLabel lblNewLabel = new JLabel("Old");
						lblNewLabel.setBounds(78, 42, 34, 25);
						lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
						contentPane.add(lblNewLabel);
				
						JLabel lblNewLabel_1 = new JLabel("New");
						lblNewLabel_1.setBounds(201, 42, 45, 25);
						lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
						contentPane.add(lblNewLabel_1);
		
				JSpinner spinner = new JSpinner();
				spinner.setBounds(68, 78, 72, 30);
				contentPane.add(spinner);
				spinner.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						int selectedValue1 = (int) spinner.getValue();
						v1 = selectedValue1;
						//System.out.print(v1);

					}
				});
		
				JSpinner spinner1 = new JSpinner();
				spinner1.setBounds(181, 78, 87, 30);
				contentPane.add(spinner1);
				spinner1.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						int selectedValue2 = (int) spinner1.getValue();
						v2 = selectedValue2;
						//System.out.print(selectedValue2);

					}
				});
		
				JButton btnNewButton = new JButton("Run");
				btnNewButton.setBounds(127, 140, 72, 23);
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						
						 vlohoiseList.clear();
						 akranthoList.clear();
						 morseList.clear();

						 System.out.print(v1+" "+v2);

						 if(v2>0&&v1>0) {
						 
						   Model  model = m[v2].difference(m[v1]);
							 
							
							 
							

							try {
								
								 
								
								// Create a SPARQL query execution object
								QueryExecution queryExecution = QueryExecutionFactory.create(queryString, model);

								// Execute the query and obtain the result set
								org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();
						         while (resultSet.hasNext()) {
						                // Get the next result
				                org.apache.jena.query.QuerySolution solution = resultSet.nextSolution();
				                
				                 String lValue = solution.getLiteral("l").getString();
				                 addtoarray(lValue);
				                 //morse.append(lValue);
				                // System.out.println(lValue); 
				                  
				              	       
						         }
							
						         	
								ResultSetFormatter.out((org.apache.jena.query.ResultSet) resultSet);
							} catch (QueryParseException qpe) {
								System.err.println("SPARQL query syntax error: " + qpe.getMessage());
							}
							
							
							
							
							try {
								
							
								
								
								// Create a SPARQL query execution object
								QueryExecution queryExecution = QueryExecutionFactory.create(queryString1, model);

								// Execute the query and obtain the result set
								org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();
								
								 while (resultSet.hasNext()) {
						                // Get the next result
				                org.apache.jena.query.QuerySolution solution = resultSet.nextSolution();
				                
	
				                 String KValue = solution.getLiteral("k").getString();
				                 addtoarray1(KValue);
				               		       
						         }
								 
//					            
							
								ResultSetFormatter.out((org.apache.jena.query.ResultSet) resultSet);
							} catch (QueryParseException qpe) {
								System.err.println("SPARQL query syntax error: " + qpe.getMessage());
							}
							
							
							
							
							
							try {
								
								
							
								// Create a SPARQL query execution object
								QueryExecution queryExecution = QueryExecutionFactory.create(queryString2, model);

								// Execute the query and obtain the result set
								org.apache.jena.query.ResultSet resultSet = queryExecution.execSelect();
								
								 while (resultSet.hasNext()) {
						                // Get the next result
				                org.apache.jena.query.QuerySolution solution = resultSet.nextSolution();
				                
	
				                 String MValue = solution.getLiteral("M").getString();
				                 addtoarray2(MValue);
						         }

                    
//					            }
							
								ResultSetFormatter.out((org.apache.jena.query.ResultSet) resultSet);
							} catch (QueryParseException qpe) {
								System.err.println("SPARQL query syntax error: " + qpe.getMessage());
							}
							
							
							
					
							
							
							tableedit();
								
							
							
						 }
					}

				});
				contentPane.add(btnNewButton);
				
				
				
	}	
					
					private void tableedit() {
						 
					   
						   JFrame newFrame = new JFrame();
					        newFrame.setTitle("New Frame");
					        newFrame.setSize(400, 300);

					        // Add components to the new JFrame
					        JPanel newContentPane = new JPanel();
					        newContentPane.setLayout(new FlowLayout());
					        newFrame.setContentPane(newContentPane);

					        DefaultTableModel model = new DefaultTableModel();

					        model.addColumn("version");
					        model.addColumn("Cases");
					        model.addColumn("Rocover");
					        model.addColumn("Dead");
					       
					        
					        Object[] row=new Object[4];
					        row[0]=v2;
					        
					        for(i=0;i<morseList.size();i++) {
					        	row[3]=morseList.get(i);
					        	model.addRow(row);
					        }
					        
					        for(i=0;i<vlohoiseList.size();i++) {
					        	row[2]=vlohoiseList.get(i);
					        	model.addRow(row);
					        }
                           
					       
					        for(i=0;i<akranthoList.size();i++) {
					        	row[1]=akranthoList.get(i);
					        	model.addRow(row);
					        }
					        
					        
					        

					        JTable table = new JTable(model);
					        

					        // Create a JScrollPane to hold the table (for scrolling)
					        JScrollPane scrollPane = new JScrollPane(table);
					        
					        // Add the JScrollPane to the JFrame
					        newFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

					        // Set JFrame visibility to true
					       

					        // Set the new JFrame's visibility to true
					        newFrame.setVisible(true);
						
		                  System.out.print(morseList);
		                  System.out.print(vlohoiseList);
		                  System.out.print(akranthoList);
						
					}

					
					private void addtoarray(String l) {
						// TODO Auto-generated method stub
						      
						      morseList.add(l);
							//  names[i]=l;
//							  System.out.print("dddd"+ morse);
							//  i++;
		                  
					}

					
					private void addtoarray1(String l) {
						// TODO Auto-generated method stub
						 akranthoList.add(l);
					           
							  //names1[j]=l;
							 // System.out.print("dddd"+vlohoiseList);
							//  j++;
		                  
					}
					
					
					private void addtoarray2(String l) {
						// TODO Auto-generated method stub
						vlohoiseList.add(l);
						    
						     
							//  names2[b]=l;
							 // System.out.print("dddd"+names2[b]);
							//  b++;
		                  
					}
}

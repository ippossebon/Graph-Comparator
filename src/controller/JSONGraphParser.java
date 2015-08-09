package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import model.Graph;
import model.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class JSONGraphParser {
	private ArrayList<Graph> graphs;
	private ArrayList<Person> people;
	
	public JSONGraphParser(){
		this.graphs = new ArrayList<Graph>();
		this.people = new ArrayList<Person>();
	}
	
	public void execute(){
		BufferedReader br = null;
		String sCurrentLine;
    	try {
   				br = new BufferedReader(new FileReader("/Users/Isadora/Documents/RUG/Research Project/Data/data/data.json"));
    			
   				// Read each line of the file as a JSON object.
    			while ((sCurrentLine = br.readLine()) != null) {
    				JSONObject obj = new JSONObject(sCurrentLine);
    				Iterator<String> keys_iterator = obj.keys();
    				
    				while (keys_iterator.hasNext()){
    					String first_key = (String)keys_iterator.next();
    					JSONArray o = obj.getJSONArray(first_key);
        				JSONObject directed_graph_object = o.getJSONObject(0);
        					
        				Person person = new Person(first_key);
        				
        				// Gets all links and nodes of the directed graph.
        				Graph directed_graph = new Graph();
        				try {
        					
							JSONArray links_array = directed_graph_object.getJSONArray("links");
		
							for (int i = 0; i < links_array.length(); i++){
								JSONObject link = (JSONObject) links_array.get(i);
								directed_graph.addNewLink(link.getInt("source"), link.getInt("target"));
							}
							this.graphs.add(directed_graph);
							person.setDirected_graph(directed_graph);
							
							JSONArray nodes_array = directed_graph_object.getJSONArray("nodes");
							
							for (int i = 0; i < nodes_array.length(); i++){
								JSONObject node = (JSONObject) nodes_array.get(i);
								directed_graph.addNewNode(node.getInt("index"), node.getString("name"), node.getString("type"));
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							System.out.println("ATENÇAO: Item sem links.");
						}
        				person.setDirected_graph(directed_graph);
        				
        				/* Gets all links and nodes of the undirected graph. */
        				JSONObject undirected_graph_object = o.getJSONObject(1);
        				Graph undirected_graph = new Graph();
        				try {
							JSONArray links_array = directed_graph_object.getJSONArray("links");
		
							for (int i = 0; i < links_array.length(); i++){
								JSONObject link = (JSONObject) links_array.get(i);
								undirected_graph.addNewLink(link.getInt("source"), link.getInt("target"));
							}
							this.graphs.add(undirected_graph);
							person.setUndirected_graph(undirected_graph);
							
							JSONArray nodes_array = undirected_graph_object.getJSONArray("nodes");
							
							for (int i = 0; i < nodes_array.length(); i++){
								JSONObject node = (JSONObject) nodes_array.get(i);
								undirected_graph.addNewNode(node.getInt("index"), node.getString("name"), node.getString("type"));
							}
							person.setUndirected_graph(undirected_graph);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							System.out.println("ATENÇAO: Item sem links.");
						}
        				
        				people.add(person);
    				} 
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		} catch (Exception e1){
    			e1.printStackTrace();
    		}
    		finally {
    			try {
    				if (br != null)
    					br.close();
    			} catch (IOException ex) {
    				ex.printStackTrace();
    			}
    		}
	}
	
	public void printGraphsInfo(ArrayList<Graph> graphs){
		for (Graph g : graphs){
			g.printInfo();
		}
	}
	
	public ArrayList<Graph> getGraphs(){
		return this.graphs;
	}
	
	public ArrayList<Person> getPeople(){
		return this.people;
	}
}

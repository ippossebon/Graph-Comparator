package controller;

import java.util.ArrayList;

import model.Graph;

public class Comparator {
	private static final int comparison_factor = 1;

	public Comparator(){
		
	}
	
	/* For each node in g1: 
	 * 		- create 'connections' that contains g1 connections;
	 * 		- get correspondent node in g2, if one exists;
	 * 			- store its connections in 'connections2'.
	 * 		- compare 'connections' and 'connections2': for each similar connection: +1.
	 * Returns the degree of similiarity between two graphs. */
	public double myCompare(Graph g1, Graph g2){
	double degree = 0;
	String g1_nodes[][] = g1.getNodes();
		
		for (int i = 0; i < g1.getNumb_nodes(); i++){
			ArrayList<String> connections_names = new ArrayList<String>();
			connections_names = g1.getNodeConnectionsNames(i);
			
			// Gets similar node in g2, if one exists.
			int similar_node_index = existsInGraph(g1_nodes[i][0], g2);
			if (similar_node_index != -1){
				ArrayList<String> connections_names2 = new ArrayList<String>();
				connections_names2 = g2.getNodeConnectionsNames(similar_node_index);
		
				// Evaluate similarity between its connections.
				degree = myEvaluate(connections_names, connections_names2)/ ((g1.getNumb_links() + g2.getNumb_links())/2);
			}
		}
		return degree;
	}
	
	/* First step: compare nodes.
	 * Second step: compare only graphs that have at least n similar nodes. (n is indicated by 'comparison_factor') */
	public double twoStepsCompare(Graph g1, Graph g2){
		ArrayList<String> similar_nodes = new ArrayList<String>();
		similar_nodes = getSimilarNodes(g1, g2);
		
		if (similar_nodes.size() < comparison_factor){
			return 0;
		}
		return myCompare(g1, g2);
	}
	
	/* Method from "Web graph similarity for anomaly detection", Vertex/edge overlap (VEO).
	 * sim(G, G') = 2 * ((number of common vertices + number of commomn edges)/ 
	 * 						nr vertices of g1 + nr vertices of g2 + nr edges of g1 + nr edges of g2) */
	public double graphDistance(Graph g1, Graph g2){
		
		double similarity_degree = 2 * ((this.getNumberOfCommonNodes(g1, g2)) + this.getNumberOfCommonEdges(g1, g2)/
				(g1.getNumb_nodes() + g2.getNumb_nodes() + g1.getNumb_links() + g2.getNumb_links()));
		return similarity_degree/12;
	}
	
	private ArrayList<String> getSimilarNodes(Graph g1, Graph g2){
		ArrayList<String> similar_nodes = new ArrayList<String>();
		String g1_nodes[][] = g1.getNodes();
		String g2_nodes[][] = g2.getNodes();
		
		for (int i = 0; i < g1.getNumb_nodes(); i++){
			String name1 = g1_nodes[i][0];
			
			for (int j = 0; j <g2.getNumb_nodes(); j++){
				String name2 = g2_nodes[j][0];
				
				if (name1.equals(name2)){
					similar_nodes.add(name1);
				}
			}
		}
		return similar_nodes;
	}
	
	private double myEvaluate(ArrayList<String> nodes1, ArrayList<String> nodes2){
		double degree = 0;
		
		for (String node : nodes1){
			for (String node2 : nodes2){
				if (node.equals(node2)){
					degree++;
				}
			}
		}
		return degree;
	}
	
	/* Checks whether a node exists in a graph. Return the index of this node in the graph or -1, in case this one does not exist. */
	private int existsInGraph(String name, Graph g){
		for (int i = 0; i < g.getNumb_nodes(); i++){
			String nodes[][] = g.getNodes();
			
			// If the node exists in the graph, return its index
			if (nodes[i][0].equals(name)){
				return i;
			}
		}
		return -1;
	}
	
	private int getNumberOfCommonNodes(Graph g1, Graph g2){
		int numb_common_nodes = 0;
		
		for (int i = 0; i < g1.getNumb_nodes(); i++){
			for (int j = 0; j < g2.getNumb_nodes(); j++){
				if (g1.getNameFromIndex(i).equals(g2.getNameFromIndex(j))){
					numb_common_nodes++;
				}
			}
		}
		return numb_common_nodes;
	}
	
	private int countCommonStrings(ArrayList<String> array_list1, ArrayList<String> array_list2){
		int counter = 0;
		
		for (String s : array_list1){
			for (String t : array_list2){
				if (s.equals(t)){
					counter++;
				}
			}
		}
		return counter;
	}
	
	private int getNumberOfCommonEdges(Graph g1, Graph g2){
		int numb_common_edges = 0;
		ArrayList<String> connections_names = new ArrayList<String>();
		ArrayList<String> connections_names2 = new ArrayList<String>();
		
		for (int i = 0; i < g1.getNumb_nodes(); i++){
			connections_names.clear();
			connections_names = g1.getNodeConnectionsNames(i);
			for (int j = 0; j < g2.getNumb_nodes(); j++){
				connections_names2.clear();
				connections_names2 = g2.getNodeConnectionsNames(j);
				numb_common_edges += this.countCommonStrings(connections_names, connections_names2);
			}
		}	
		return numb_common_edges;
	}
}

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
	public int myCompare(Graph g1, Graph g2){
	int degree = 0;
	String g1_nodes[][] = g1.getNodes();
		
		for (int i = 0; i < g1.getNumb_nodes(); i++){
			ArrayList<Integer> connections = g1.getNodeConnections(i);
			ArrayList<String> connections_names = new ArrayList<String>();
			
			if (connections == null){
				return 0;
			}
			// Get all connections names.
			for (Integer c : connections){
				connections_names.add(g1.getNameFromIndex(c));
			}
			
			// Gets similar node in g2, if one exists.
			int similar_node_index = existsInGraph(g1_nodes[i][0], g2);
			if (similar_node_index != -1){
				ArrayList<Integer> connections2 = g2.getNodeConnections(similar_node_index);
				ArrayList<String> connections_names2 = new ArrayList<String>();
				
				if (connections2 == null){
					return 0;
				}
				
				for (Integer c : connections2){
					connections_names2.add(g2.getNameFromIndex(c));
				}
				// Evaluate similarity between its connections.
				degree = evaluate(connections_names, connections_names2);
			}
		}
		return degree;
	}
	
	/* First step: compare nodes.
	 * Second step: compare only graphs that have at least n similar nodes. (n is indicated by 'comparison_factor') */
	public int twoStepsCompare(Graph g1, Graph g2){
		ArrayList<String> similar_nodes = new ArrayList<String>();
		similar_nodes = getSimilarNodes(g1, g2);
		
		if (similar_nodes.size() < comparison_factor){
			return 0;
		}
		return myCompare(g1, g2);
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
	
	private int evaluate(ArrayList<String> nodes1, ArrayList<String> nodes2){
		int degree = 0;
		
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
}

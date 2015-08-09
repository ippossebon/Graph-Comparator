package controller;

import java.util.ArrayList;

import model.Graph;

public class Comparator {

	public Comparator(){
		
	}
	
	/* Returns the degree of similiarity between two graphs. */
	public int compare(Graph g1, Graph g2){
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
		System.out.println("Similarity degree: " + degree);
		return degree;
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

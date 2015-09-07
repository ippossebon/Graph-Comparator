package controller;

import java.util.ArrayList;
import java.util.Map;

import model.Graph;

public class LevenshteinComparator extends Comparator{

	private Map<Integer, String> m1;
	private Map<Integer, String> m2;
	private int counter;
	
	public LevenshteinComparator(){
		this.counter = 0;
	}
	
	public void preprocess(ArrayList<Graph> graphs){
		int id;
		String code;
		for (Graph g : graphs){
			id = this.counter;
			code = this.getMinimumDFSCode(g).toString();
			this.m1.put(id, code);
			counter++;
		}
		// Continue
		
	}

	public Map<Integer, String> getNode_labels() {
		return this.m1;
	}

	public void setNode_labels(Map<Integer, String> node_labels) {
		this.m1 = node_labels;
	}

	public Map<Integer, String> getEdge_labels() {
		return this.m2;
	}

	public void setEdge_labels(Map<Integer, String> edge_labels) {
		this.m2 = edge_labels;
	}
	
	private char[] getMinimumDFSCode(Graph g){
		int[] dfs;
		char[] dfs_code;
		ArrayList<String> all_codes = new ArrayList<String>();
		
		// Apply DFS for each of the vertices as root.
		for (int i = 0; i < g.getNumb_nodes(); i++){
			dfs = getDFSPath(i, g);
			dfs_code = convertDFSCode(dfs, g);
			all_codes.add(dfs_code.toString());
		}
		
		dfs_code = getMin(all_codes);
		return dfs_code;
	}
	
	private char[] getMin(ArrayList<String> codes){
		
		
		return null;
	}
	
	private char[] convertDFSCode(int[] path, Graph g){
		char[] code = new char[g.getNumb_nodes() + g.getNumbEdges() + 1];
		
		// Code..
		
		return code;
	}
	
	private int[] getDFSPath(int root, Graph g){
		int[] path = new int[g.getNumb_nodes()];
		int[] visited_nodes = new int[g.getNumb_nodes()];
		
		this.dfs(root, visited_nodes, path, g.getAdjacency_matrix());
		
		return path;
	}
	
	
	public void testDFS(){
		//int visited_nodes[] = new int[this.numb_nodes];
		
		//for (int i = 0; i < this.numb_nodes; i++){
			//System.out.println(this.getNameFromIndex(i));
			//this.dfs(i, visited_nodes);
		//}
		
	}
	
	private void dfs(int node, int visited_nodes[], int[] path, int[][] adjacency_matrix){
		visited_nodes[node] = 1;
		int i = 0;
		for (i = 0; i < path.length; i++){
			if(adjacency_matrix[node][i] == 1 && (visited_nodes[i] == 0)){
				dfs(i, visited_nodes, path, adjacency_matrix);
			}
		}
	}
}

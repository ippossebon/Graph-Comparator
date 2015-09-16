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
			//dfs = getDFSPath(i, g);
			//dfs_code = convertDFSCode(dfs, g);
			//all_codes.add(dfs_code.toString());
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
	
	private ArrayList<Integer> getDFSPath(int root, Graph g){
		int[] fringe = new int[g.getNumb_nodes()];
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		//this.dfs(root, fringe, path, g.getAdjacency_matrix());
		
		return path;
	}
	
	
	public void testDFS(Graph g){
		ArrayList<Integer> fringe = new ArrayList<Integer>();
		ArrayList<Integer> path = new ArrayList<Integer>();
		this.dfs(4, fringe, path, g.getAdjacency_matrix());
		
		
		
		// Print results
		for (Integer node : path){
			System.out.println(node);
		}
	}
	
	private void dfs(int node, ArrayList<Integer> fringe, ArrayList<Integer> path, int[][] adjacency_matrix){
		fringe.add(node);
		path.add(node);
		int i = 0;
		
		if (fringe.isEmpty()){
			return;
		}
		
		for (i = 0; i < adjacency_matrix.length; i++){
			if(adjacency_matrix[node][i] == 1 && (! fringe.contains(i))){
				dfs(i, fringe, path, adjacency_matrix);
			}
			else if (i == adjacency_matrix.length && adjacency_matrix[node][i] == 0){
				// Se o node em questao nao tem filhos, voltamos para o ultimo da fringe, removendo o atual.
				fringe.remove(node);
				dfs(fringe.get(fringe.size() -1), fringe, path, adjacency_matrix);
			}
			
		}
	}
}

package networkscomparison;

import java.util.ArrayList;
import java.util.Map;

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
	public double simpleComparison(Graph g1, Graph g2){
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
				degree = simpleEvaluate(connections_names, connections_names2)/ ((g1.getNumbEdges() + g2.getNumbEdges())/2);
			}
		}
		return degree;
	}
	
	/* First step: compare nodes.
	 * Second step: compare only graphs that have at least n similar nodes. (n is indicated by 'comparison_factor') */
	public double twoStepsComparison(Graph g1, Graph g2){
		ArrayList<String> similar_nodes = new ArrayList<String>();
		similar_nodes = getSimilarNodes(g1, g2);
		
		if (similar_nodes.size() < comparison_factor){
			return 0;
		}
		return simpleComparison(g1, g2);
	}
	
	/* Method from "Web graph similarity for anomaly detection", Vertex/edge overlap (VEO).
	 * sim(G, G') = 2 * ((number of common vertices + number of commomn edges)/ 
	 * 						nr vertices of g1 + nr vertices of g2 + nr edges of g1 + nr edges of g2) */
	public double graphDistance(Graph g1, Graph g2){
		
		double similarity_degree = 2 * ((this.getNumberOfCommonNodes(g1, g2)) + this.getNumberOfCommonEdges(g1, g2)/
				(g1.getNumb_nodes() + g2.getNumb_nodes() + g1.getNumbEdges() + g2.getNumbEdges()));
		return similarity_degree/12;
	}
	
	/* Get the similar nodes' names from two given graphs. */
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
	
	/* For each similar node from two graphs, one point. This value will later be normalized in order to be in the range [0,1]. */
	private double simpleEvaluate(ArrayList<String> nodes1, ArrayList<String> nodes2){
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
	
	/* Returns the number os common strings in two given arrays.*/
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
	
	
	/* Returns the number of common edges in two given graphs. */
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
	
	/* Sort two related arrays (relation = same index). */
	private void sortTwoRelatedArrays(double[] scores, int[] vertices)
    {
        for(int i=0; i<scores.length; i++)
        {
            for(int j=i + 1; j<scores.length; j++)
            {
                if(scores[i] > scores[j])
                {
                    double temp = scores[i];
                    scores[i] = scores[j];
                    scores[j] = temp;
                    
                    int aux = vertices[i];
                    vertices[i] = vertices[j];
                    vertices[j] = aux;
                }
            }

        }
    }

	/* Returns an array with all vertices' indices, given the number of vertices.*/
	private int[] createVerticesArray(int numb_vertices){
		int[] array = new int[numb_vertices];
		for (int i = 0; i < numb_vertices; i++){
			array[i] = i;
		}
		return array;
	}
	
	private void concatenateDoubleArrays(double[] a, double[] b, ArrayList<Double> all){
		for (int i = 0; i < a.length; i++){
			all.add(a[i]);
		}
		for (int i = 0; i < b.length; i++){
			all.add(b[i]);
		}
	}
	
	private void concatenateIntArrays(int[] a, int[] b, ArrayList<Integer> all){
		for (int i = 0; i < a.length; i++){
			all.add(a[i]);
		}
		
		for (int i = 0; i < b.length; i++){
			all.add(b[i]);
		}
	}
	
	/* Two graphs are similar if the ranking of their vertices are similar.*/
	public double vertexRankingComparison(Graph g1, Graph g2){
		
		if (g1.getNumb_nodes() == 0 || g1.getNumbEdges() == 0 || g2.getNumb_nodes() == 0 || g2.getNumbEdges() == 0){
			return 0;
		}
		
		// Calculate the score for each vertex of the graph.
		double[] g1_scores = new double[g1.getNumb_nodes()];
		double[] g2_scores = new double[g2.getNumb_nodes()];
		int[] g1_vertices = createVerticesArray(g1.getNumb_nodes());
		int[] g2_vertices = createVerticesArray(g2.getNumb_nodes());
		
		g1_scores = g1.calculatePageRank().clone();
		g2_scores = g2.calculatePageRank().clone();
		
		sortTwoRelatedArrays(g1_scores, g1_vertices);
		sortTwoRelatedArrays(g2_scores, g2_vertices);
		
		ArrayList<Double> scores = new ArrayList<Double>();
		concatenateDoubleArrays(g1_scores, g2_scores, scores);
		ArrayList<Integer> vertices = new ArrayList<Integer>();
		concatenateIntArrays(g1_vertices, g2_vertices, vertices);
		
		return calculateSimilarityDegreeForVertexRanking(scores, vertices, g1, g2);
	}
	
	/* Given the vertex number(id), returns its index on the array of vertices. */
	private int getIndexFromVertex(int vertex, ArrayList<Integer> vertices){
		for (int i = 0; i < vertices.size(); i++){
			if (vertices.get(i)== vertex){
				return i;
			}
		}
		return -1;
	}
	
	private double calculateSimilarityDegreeForVertexRanking(ArrayList<Double> scores, ArrayList<Integer> vertices, Graph g1, Graph g2){
		double degree = 0;
		double sum = 0;
		double score = 0;
		double ranking_factor = 0;
		double normalization_factor = 64; // -63.81625 is the maximum value (approx.)
		int vertex = -1;
		int index_in_array = -1;
		
		for (int i = vertices.size() - 1; i >= 0; i = i - 2){
			try{
				vertex = vertices.get(i);
			}
			catch(IndexOutOfBoundsException e){ // For the case that we have only one vertex in the array.
				i++;
				vertex = vertices.get(i);
			}
				
			// If the current vertex exists in both graphs, we consider the average of its score in both graphs.
			String vertex_name = g1.getNameFromIndex(vertex);
			int result = existsInGraph(vertex_name, g1);
			score = 0;
			ranking_factor = 0;
				
			if (result != -1){
				// The vertex exists in both graphs. Therefore, its score would be the average between both scores.
				index_in_array = getIndexFromVertex(result, vertices);
				score = (double)(scores.get(i) + scores.get(index_in_array))/2;
				ranking_factor = Math.pow(i - index_in_array, 2);
			}
			else{
				// The vertex exists only in one graph. Therefore, its rank will be = numb_nodes + 1.
				score = scores.get(i);
				ranking_factor = Math.pow((i - g1.getNumb_nodes() + 1), 2);
			}
			sum += score * ranking_factor;
		
			try{
				scores.remove(i);
				scores.remove(index_in_array);
				vertices.remove(i);
				vertices.remove(index_in_array);
			}
			catch(IndexOutOfBoundsException e){
				System.out.println("No more items to remove. (Vertex ranking algorithm)");
			}
			
		}
		sum = (double)2 * sum;
		sum = sum + 5;
		degree = (double) 1 - (sum/normalization_factor);
		return degree;
	}
	
	/* Returns the vertices' index shared by G1 and G2. */
	private void getSharedVerticesIndex(Graph g1, Graph g2, ArrayList<Integer> g1_similar_nodes, ArrayList<Integer> g2_similar_nodes){
		ArrayList<String> similar_nodes_names = this.getSimilarNodes(g1, g2);		
		String[][] g1_nodes = g1.getNodes();
		String[][] g2_nodes = g2.getNodes();
				
		for (int i = 0; i < g1.getNumb_nodes(); i++){
			if (similar_nodes_names.contains(g1_nodes[i][0])){
				g1_similar_nodes.add(i);
			}
		}	
		for (int j = 0; j < g2.getNumb_nodes(); j++){
			if (similar_nodes_names.contains(g2_nodes[j][0])){
				g2_similar_nodes.add(j);
			}
		}
	}
	
	/* Used for quality vectors. */
	private void computeAverageScoresForVectors(double[] v1, double[] v2, double[] result){
		for (int i= 0; i < v1.length; i++){
			result[i] = (double) (v1[i] + v2[i])/2;
		}
	}
	
	/* “Two graphs are similar if their node/edge weight vectors are close” */
	public double vectorSimilarity(Graph g1, Graph g2){
		ArrayList<Integer> g1_shared_nodes = new ArrayList<Integer>();
		ArrayList<Integer> g2_shared_nodes = new ArrayList<Integer>();
		double degree = 0;
		double[] qualities = getQualityVector(g1, g2, g1_shared_nodes, g2_shared_nodes);
		int[][] edges = getAllSetOfEdges(g1, g2);
		double sum = 0;
		double x = 0;
		double y = 0;
		
		// There are no nodes that these two graphs share.
		if (qualities.length == 0){
			return 0;
		}
		for (int i = 0; i < edges.length; i++){
			x = Math.abs(lambda(edges[i][0], edges[i][1], g1, qualities, g1_shared_nodes));
			y = lambda(edges[i][0], edges[i][1], g2, qualities, g2_shared_nodes);
			sum +=  (x - y)/ (Math.max(x, y));
		}
		
		degree = (double) (1 - sum)/edges.length;
		degree = (double) degree/1.08; // Normalizes de value
		if (degree < 0){
			return 0;
		}
		
		return degree;
	}
	
	private double lambda(int source, int target, Graph g, double[] qualities, ArrayList<Integer> g_shared_nodes){
		int index = 0;
		double numb_source_outlinks = g.getNumberNodeOutlinks(source);
		
		if (numb_source_outlinks == 0){
			return 0;
		}
		for (int i = 0; i < g_shared_nodes.size(); i++){
			if (g_shared_nodes.get(i).equals(source)){
				index = i;
			}
		}
		// Multiplies per 1 because in this database of graphs, only single edges exist.
		double value = (double)(qualities[index] * 1)/numb_source_outlinks;
			
		return value;
	}
	
	private int[][] getAllSetOfEdges(Graph g1, Graph g2){
		int[][] edges = new int[g1.getNumbEdges() + g2.getNumbEdges()][2];
		int[][] edges1 = g1.getEdges();
		int[][] edges2 = g2.getEdges();
		int i;
		
		for (i = 0; i < g1.getNumbEdges(); i++){
			edges[i][0] = edges1[i][0];
			edges[i][1] = edges[i][1];
		}
		for (int j = 0; j < g2.getNumbEdges(); j++){
			edges[j+i][0] = edges2[j][0];
			edges[j+i][1] = edges2[j][1];
		}
		return edges;
	}
	
	private double[] getQualityVector(Graph g1, Graph g2, ArrayList<Integer> g1_shared_nodes, ArrayList<Integer> g2_shared_nodes){
		
		// g1_shared_nodes and g2_shared_nodes contain the indices of the vertex shared, respectively.
		getSharedVerticesIndex(g1, g2, g1_shared_nodes, g2_shared_nodes);
		
		double[] g1_nodes_quality = new double[g1_shared_nodes.size()];
		double [] g2_nodes_quality = new double[g2_shared_nodes.size()];
		
		double[] g1_pagerank = g1.calculatePageRank();
		double[] g2_pagerank = g2.calculatePageRank();
		
		// Gets the quality for each shared vertex.
		for (int i = 0; i < g1_shared_nodes.size(); i++){
			g1_nodes_quality[i] = g1_pagerank[g1_shared_nodes.get(i)];
		}
		for (int j = 0; j < g2_shared_nodes.size(); j++){
			g2_nodes_quality[j] = g2_pagerank[g2_shared_nodes.get(j)];
		}
		
		double[] nodes_quality = new double[g1_shared_nodes.size()];
		computeAverageScoresForVectors(g1_nodes_quality, g2_nodes_quality, nodes_quality);
		
		return nodes_quality;
	}
}

package controller;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Graph;
import model.Network;
import networkscomparison.Comparator;
import view.ApplicationFrame;
import clustering.ClusteringController;
import dataprocessing.JSONGraphParser;

public class Main {

	public static void main(String args[]){
		JSONGraphParser parser = new JSONGraphParser();
		ApplicationFrame frame;
		initialize(parser);
		
		ArrayList<String> graphs1 = new ArrayList<String>();
		ArrayList<String> graphs2 = new ArrayList<String>();
		
		Comparator comparator = new Comparator();
		JTable directed_networks_table = new JTable();
		JTable undirected_networks_table = new JTable();
		DefaultTableModel dft_directed = new DefaultTableModel();
		DefaultTableModel dft_undirected = new DefaultTableModel();
		
		/***** Directed networks ******/
		double degree = 0;
		ArrayList<Double> simple_comparison_results = new ArrayList<Double>();
		ArrayList<Double> two_steps_comparison_results = new ArrayList<Double>();
		ArrayList<Double> graph_distance_results = new ArrayList<Double>();
		ArrayList<Double> vertex_ranking_results = new ArrayList<Double>();
		ArrayList<Double> vector_similarity_results = new ArrayList<Double>();
		
		for (int i = 0; i < (int)parser.getPeople().size()-1; i++){
			graphs1.add(parser.getPeople().get(i).getName());
			graphs2.add(parser.getPeople().get(i+1).getName());
			
			degree = comparator.simpleComparison(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i+1).getDirected_graph());
			simple_comparison_results.add(degree);
			
			degree = comparator.twoStepsComparison(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i+1).getDirected_graph());
			two_steps_comparison_results.add(degree);
			
			degree = comparator.graphDistance(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i+1).getDirected_graph());
			graph_distance_results.add(degree);
			
			try{
				degree = comparator.vertexRankingComparison(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i).getDirected_graph());
			}
			catch(IndexOutOfBoundsException e){
				System.out.println(i);
			}
			vertex_ranking_results.add(degree);
			
			degree = comparator.vectorSimilarity(parser.getPeople().get(i).getDirected_graph(), parser.getPeople().get(i+1).getDirected_graph());
			vector_similarity_results.add(degree);
		}	
		dft_directed.addColumn("Graph 1 ID", graphs1.toArray());
		dft_directed.addColumn("Graph 2 ID", graphs2.toArray());
		dft_directed.addColumn("Simple comparison", simple_comparison_results.toArray());
		dft_directed.addColumn("Two steps comparison", two_steps_comparison_results.toArray());
		dft_directed.addColumn("Graph distance", graph_distance_results.toArray());
		dft_directed.addColumn("Vertex ranking", vertex_ranking_results.toArray());
		dft_directed.addColumn("Vector similarity", vector_similarity_results.toArray());
		directed_networks_table.setModel(dft_directed);
		
		/***** Undirected networks ******/
		graphs1.clear();
		graphs2.clear();
		ArrayList<Double> simple_comparison_results2 = new ArrayList<Double>();
		ArrayList<Double> two_steps_comparison_results2 = new ArrayList<Double>();
		ArrayList<Double> graph_distance_results2 = new ArrayList<Double>();
		ArrayList<Double> vertex_ranking_results2 = new ArrayList<Double>();
		ArrayList<Double> vector_similarity_results2 = new ArrayList<Double>();
		
		for (int i = 0; i < (int)parser.getPeople().size()-1; i++){
			graphs1.add(parser.getPeople().get(i).getName());
			graphs2.add(parser.getPeople().get(i+1).getName());
			
			degree = comparator.simpleComparison(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i).getUndirected_graph());
			simple_comparison_results2.add(degree);
			
			degree = comparator.twoStepsComparison(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			two_steps_comparison_results2.add(degree);
			
			degree = comparator.graphDistance(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			graph_distance_results2.add(degree);
			
			try{
				degree = comparator.vertexRankingComparison(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			}
			catch(IndexOutOfBoundsException e){
				System.out.println(i);
			}
			vertex_ranking_results2.add(degree);
			
			degree = comparator.vectorSimilarity(parser.getPeople().get(i).getUndirected_graph(), parser.getPeople().get(i+1).getUndirected_graph());
			vector_similarity_results2.add(degree);
		}	
		dft_undirected.addColumn("Graph 1 ID", graphs1.toArray());
		dft_undirected.addColumn("Graph 2 ID", graphs2.toArray());
		dft_undirected.addColumn("Simple comparison", simple_comparison_results2.toArray());
		dft_undirected.addColumn("Two steps comparison", two_steps_comparison_results2.toArray());
		dft_undirected.addColumn("Graph distance", graph_distance_results2.toArray());
		dft_undirected.addColumn("Vertex ranking", vertex_ranking_results2.toArray());
		dft_undirected.addColumn("Vector similarity", vector_similarity_results2.toArray());
		
		undirected_networks_table.setModel(dft_undirected);
		
		/* Big networks */
		parser.executeBigNetworks();
		JTable big_networks_table = new JTable();
		DefaultTableModel dft = new DefaultTableModel();
		
		for (Graph g : parser.getNetworks()){
			g.createAdjacencyMatrixDirectedGraph();
		}
		
		ArrayList<String> nets1 = new ArrayList<String>();
		ArrayList<String> nets2 = new ArrayList<String>();
		ArrayList<Double> results_simple_compare = new ArrayList<Double>();
		ArrayList<Double> results_two_steps = new ArrayList<Double>();
		ArrayList<Double> results_graph_distance = new ArrayList<Double>();
		ArrayList<Double> results_vertex_ranking = new ArrayList<Double>();
		ArrayList<Double> results_vector_similarity = new ArrayList<Double>();
		
		for (int i = 0; i < (int)parser.getNetworks().size() -1; i++){
			nets1.add(parser.getNetworks().get(i).getId());
			nets2.add(parser.getNetworks().get(i+1).getId());
			
			degree = comparator.simpleComparison(parser.getNetworks().get(i), parser.getNetworks().get(i+1));
			results_simple_compare.add(degree);
			
			degree = comparator.twoStepsComparison(parser.getNetworks().get(i), parser.getNetworks().get(i+1));
			results_two_steps.add(degree);
			
			degree = comparator.graphDistance(parser.getNetworks().get(i), parser.getNetworks().get(i+1));
			results_graph_distance.add(degree);
			
			degree = comparator.vertexRankingComparison(parser.getNetworks().get(i), parser.getNetworks().get(i+1));
			results_vertex_ranking.add(degree);
			
			degree = comparator.vectorSimilarity(parser.getNetworks().get(i), parser.getNetworks().get(i+1));
			results_vector_similarity.add(degree);
		}	
		dft.addColumn("Network 1", nets1.toArray());
		dft.addColumn("Network 2", nets2.toArray());
		dft.addColumn("Simple comparison", results_simple_compare.toArray());
		dft.addColumn("Two steps comparison", results_two_steps.toArray());
		dft.addColumn("Graph distance", results_graph_distance.toArray());
		dft.addColumn("Vertex ranking", results_vertex_ranking.toArray());
		dft.addColumn("Vector similarity", results_vector_similarity.toArray());
		big_networks_table.setModel(dft);
		
		//frame = new ApplicationFrame(directed_networks_table, undirected_networks_table, big_networks_table);
		//frame.setVisible(true);
		
		/* Clustering */
		
		ArrayList<Graph> all_networks = createNetworksArray(parser);
		if (all_networks.isEmpty()){
			System.out.println("Error: array that contains all networks is empty.");
		}
		else{
			double[][] similarity_matrix_all_networks = createSimilarityMatrix(comparator, all_networks);
			double r = 0.15; // Value considering the vector similarity
			ClusteringController clustering_controller = new ClusteringController(similarity_matrix_all_networks, r, all_networks.size());
			System.out.println("Clustering for all networks in database.");
			clustering_controller.run();
			
			double[][] similarity_matrix_big_networks = createSimilarityMatrixBigNetworks(comparator, parser.getNetworks());
			ClusteringController cc2 = new ClusteringController(similarity_matrix_big_networks, r, parser.getNetworks().size());
			System.out.println("Clustering for all big networks.");
			cc2.run();
		}
		
		
	}
	
	private static void initialize(JSONGraphParser parser){
		parser.execute();
		
		for (int i = 0; i < parser.getPeople().size(); i++){
			parser.getPeople().get(i).getDirected_graph().createAdjacencyMatrixDirectedGraph();
		}
		
		for (int j = 0; j < parser.getPeople().size(); j++){
			parser.getPeople().get(j).getUndirected_graph().createAdjacencyMatrixUndirectedGraph();
		}
	}
	
	private static ArrayList<Graph> createNetworksArray(JSONGraphParser parser){
		ArrayList<Graph> networks = new ArrayList<Graph>();
		
		for (int i = 0; i < (int)parser.getPeople().size(); i++){
			networks.add(parser.getPeople().get(i).getDirected_graph());
			networks.add(parser.getPeople().get(i).getUndirected_graph());
		}
		for (int i = 0; i < (int)parser.getNetworks().size(); i++){
			networks.add(parser.getNetworks().get(i));
		}
		
		return networks;
	}
	
	private static double[][] createSimilarityMatrix(Comparator comparator, ArrayList<Graph> networks){
		double[][] similarity_matrix = new double[networks.size()][networks.size()];
		
		for (int i = 0; i < networks.size(); i++){
			for (int j = 0; j < networks.size(); j++){
				similarity_matrix[i][j] = comparator.vectorSimilarity(networks.get(i), networks.get(j));
			}
		}
		
		return similarity_matrix;
	}
	
	private static double[][] createSimilarityMatrixBigNetworks(Comparator comparator, ArrayList<Network> networks){
		double[][] similarity_matrix = new double[networks.size()][networks.size()];
		
		for (int i = 0; i < networks.size(); i++){
			for (int j = 0; j < networks.size(); j++){
				similarity_matrix[i][j] = comparator.vectorSimilarity(networks.get(i), networks.get(j));
			}
		}
		
		return similarity_matrix;
	}
}

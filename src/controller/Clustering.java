package controller;

import java.util.ArrayList;

import model.Graph;

public class Clustering {
	private double[][] similarity_matrix;
	private double r = 0; // Threshold
	private int[][] correlation_matrix;
	private double[][] modularity_matrix;
	private int network_number_edges = 0;
	private ArrayList<Graph> networks;

	public Clustering(ArrayList<Graph> networks, double[][] similarity_matrix, double r, int network_edges){
		this.setNetworks(networks);
		this.setSimilarity_matrix(similarity_matrix);
		this.setR(r);
		this.setCorrelation_matrix(new int[similarity_matrix.length][similarity_matrix.length]);
		this.setModularity_matrix(new double[similarity_matrix.length][similarity_matrix.length]);
		this.setNetwork_number_edges(network_edges);
	}
	
	public void run(){
		createCorrelationMatrix();
		
	}
	
	private void createCorrelationMatrix(){	
		for (int i = 0; i < this.correlation_matrix.length; i++){
			for (int j = 0; j < this.correlation_matrix.length; j++){
				if (this.similarity_matrix[i][j] >= r){
					this.correlation_matrix[i][j] = 1;
				}
				else{
					this.correlation_matrix[i][j] = 0;
				}
			}
		}
	}
	
	private void createModularityMatrix(){
		double i_degree = 0;
		double j_degree = 0;
		double total_numb_edges = 0;
		
		for (int i = 0; i < this.modularity_matrix.length; i++){
			for (int j = 0; j < this.modularity_matrix.length; j++){
				this.modularity_matrix[i][j] = this.correlation_matrix[i][j] - ((i_degree * j_degree)/2*this.network_number_edges);
			}
		}
	}
	
	private void computeEigenvector(){
		
	}
	
	private void computeEigenvalue(){
		
	}
	
	private void divideInTwo(){
		
	}

	public double[][] getSimilarity_matrix() {
		return similarity_matrix;
	}

	public void setSimilarity_matrix(double[][] similarity_matrix) {
		this.similarity_matrix = similarity_matrix;
	}

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public int[][] getCorrelation_matrix() {
		return correlation_matrix;
	}

	public void setCorrelation_matrix(int[][] correlation_matrix) {
		this.correlation_matrix = correlation_matrix;
	}
	
	
	private void printMatrix(int[][] m){
		for (int i = 0; i < m.length; i++){
			for (int j = 0; j < m.length; j++){
				System.out.print(m[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	public double[][] getModularity_matrix() {
		return modularity_matrix;
	}

	public void setModularity_matrix(double[][] modularity_matrix) {
		this.modularity_matrix = modularity_matrix;
	}

	public int getNetwork_number_edges() {
		return network_number_edges;
	}

	public void setNetwork_number_edges(int network_number_edges) {
		this.network_number_edges = network_number_edges;
	}

	public ArrayList<Graph> getNetworks() {
		return networks;
	}

	public void setNetworks(ArrayList<Graph> networks) {
		this.networks = networks;
	}
}

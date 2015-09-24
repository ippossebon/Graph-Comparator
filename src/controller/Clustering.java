package controller;

import java.util.ArrayList;
import java.util.List;

import model.Graph;

import org.jblas.ComplexDoubleMatrix;
import org.jblas.DoubleMatrix;
import org.jblas.Eigen;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

public class Clustering {
	private double[][] similarity_matrix;
	private double r = 0; // Threshold
	private int[][] correlation_matrix = {{0, 1, 0, 0, 0}, {1, 0, 0, 0, 0}, {0, 0, 0, 1, 1}, {0, 0, 1, 0, 1}, {0, 0, 1, 1, 0}}; // Used instead of using an adjacency matrix. Matrix nxn where n(i,j) is the similary degree calculated for i and j.
	private double[][] modularity_matrix;
	private int network_number_edges = 8; //0
	private ArrayList<Graph> networks;

	public Clustering(ArrayList<Graph> networks, double[][] similarity_matrix, double r){
		this.setNetworks(networks);
		this.setSimilarity_matrix(similarity_matrix);
		this.setR(r);
		//this.setCorrelation_matrix(new int[similarity_matrix.length][similarity_matrix.length]);
		this.setModularity_matrix(new double[5][5]);
	}
	
	public void run(){
		//createCorrelationMatrix();
		createModularityMatrix();
		eigenvalueDecomposition();
		
	
	}
	
	public void eigenvalueDecomposition(){
		Matrix matrix = new Matrix(this.modularity_matrix);
		EigenvalueDecomposition eigen = matrix.eig();
		Matrix eigenvectors = eigen.getV();
		
		double[] real_part = eigen.getRealEigenvalues();
		double[] imag_part = eigen.getImagEigenvalues();
		
		double max = -500;
		for (int i = 0; i < real_part.length; i++){
			if (real_part[i] > max){
				max = real_part[i];
			}
		}		
	}
	
	/* - Two graphs are considered to be similar if their similarity degree is > r. 
	 * - Select a correlation as being an edge if the correlation value is > r. */
	private void createCorrelationMatrix(){	
		for (int i = 0; i < this.correlation_matrix.length; i++){
			for (int j = 0; j < this.correlation_matrix.length; j++){
				if (this.similarity_matrix[i][j] >= r){
					this.correlation_matrix[i][j] = 1;
					this.network_number_edges++; 
				}
				else{
					this.correlation_matrix[i][j] = 0;
				}
			}
		}
	}
	
	private void createModularityMatrix(){
		for (int i = 0; i < this.modularity_matrix.length; i++){
			for (int j = 0; j < this.modularity_matrix.length; j++){
				this.modularity_matrix[i][j] = this.correlation_matrix[i][j] - ((double)(calculateDegree(i) * calculateDegree(j))/this.network_number_edges);
			}
		}
	}
	
	/* Returns the degree of a given node. That is, the number of graphs to each this one is considered to be similar.*/
	private int calculateDegree(int index){
		int count = 0;
		for (int i = 0; i < this.correlation_matrix.length; i++){
			if (this.correlation_matrix[index][i] >= r){
				count++;
			}
		}
		return count;
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
	
	
	private void printDoubleMatrix(double[][] m){
		for (int i = 0; i < m.length; i++){
			for (int j = 0; j < m.length; j++){
				System.out.print(m[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	private void printMatrix(double[][] m){
		for (int i = 0; i < m.length; i++){
			for (int j = 0; j < m.length; j++){
				System.out.print(m[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
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

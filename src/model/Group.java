package model;

import java.util.ArrayList;
import java.util.List;

import utilities.GroupOperations;
import utilities.MatrixOperations;
import clustering.ClusteringController;

public class Group{
	private int[][] correlation_matrix;
	private double[][] generalized_modularity_matrix;	
	private ArrayList<Integer> indices;
	private double[] s;
	private int numb_edges;
	private Group parent;
	private ArrayList<Group> children;
	
	public Group(int[][] correlation_matrix, double[][] generalized_modularity_matrix, ArrayList<Integer> indices, double[] s, int numb_edges){
		this.correlation_matrix = correlation_matrix;
		this.generalized_modularity_matrix = generalized_modularity_matrix;
		this.indices = indices;
		this.s = s;
		this.setNumb_edges(numb_edges);
		this.children = new ArrayList<Group>();
	}
	
	public void applyClusteringAlgorithm(){
		boolean done = true;
		double[] leading_eigenvector = MatrixOperations.getLeadingEigenvector(this.generalized_modularity_matrix);
		
		ArrayList<Integer> s1 = new ArrayList<Integer>();
		ArrayList<Integer> s2 = new ArrayList<Integer>();
		ArrayList<Integer> indices_group1 = new ArrayList<Integer>();
		ArrayList<Integer> indices_group2 = new ArrayList<Integer>();
		GroupOperations.divideInTwo(this.s, leading_eigenvector, this.indices, s1, s2, indices_group1, indices_group2);
	
		/* Avoids having empty groups and groups that are equal to the current one. */
		if (!indices_group1.isEmpty() && isNewGroup(indices_group1)){
			IntegerHolder numb_edges1 = new IntegerHolder(0);
			int[][] correlation_matrix_group1 = GroupOperations.calculateGroupCorrelationMatrix(indices_group1, numb_edges1, ClusteringController.similarity_matrix, ClusteringController.r);
			double deltaQ1 = GroupOperations.calculateModularityContribution(indices_group1, ClusteringController.getCorrelation_matrix(), numb_edges1.getValue(), ClusteringController.getModularity_matrix());
			double[][] mm1 = GroupOperations.calculateGeneralizedModularityMatrix(indices_group1, correlation_matrix_group1, ClusteringController.getModularity_matrix());
			double[] leading_eigenvector1 = MatrixOperations.getLeadingEigenvector(mm1);
			
			if (deltaQ1 > 0 && MatrixOperations.containsPositiveValue(leading_eigenvector1)){
				double[] sv = MatrixOperations.convertArrayListToVector(s1);
				Group g = new Group(correlation_matrix_group1, mm1, indices_group1, sv, numb_edges1.getValue());	
				this.addChild(g);
				ClusteringController.updateS(indices_group1);
				g.applyClusteringAlgorithm();
				done = false;
			}
		}
		if (! indices_group2.isEmpty() && isNewGroup(indices_group2)){
			IntegerHolder numb_edges2 = new IntegerHolder(0);
			int[][] correlation_matrix_group2 = GroupOperations.calculateGroupCorrelationMatrix(indices_group2, numb_edges2, ClusteringController.similarity_matrix, ClusteringController.r);
			double deltaQ2 = GroupOperations.calculateModularityContribution(indices_group2, ClusteringController.getCorrelation_matrix(), numb_edges2.getValue(), ClusteringController.getModularity_matrix());
			double[][] mm2 = GroupOperations.calculateGeneralizedModularityMatrix(indices_group2, correlation_matrix_group2, ClusteringController.getModularity_matrix());
			double[] leading_eigenvector2 = MatrixOperations.getLeadingEigenvector(mm2);
			
			if (deltaQ2 > 0 && MatrixOperations.containsPositiveValue(leading_eigenvector2)){
				double[] sv = MatrixOperations.convertArrayListToVector(s2);
				Group g = new Group(correlation_matrix_group2, mm2, indices_group2, sv, numb_edges2.getValue());	
				this.addChild(g);
				ClusteringController.updateS(indices_group2);
				g.applyClusteringAlgorithm();
				done = false;
			}
		}
		if (done){
			MatrixOperations.printVector(ClusteringController.s);
		}
	}
	
	private boolean isNewGroup(ArrayList<Integer> ind){
		for (Integer i : this.indices){
			if (! ind.contains(i)){
				return true;
			}
		}
		return false;
	}

	public int[][] getCorrelation_matrix() {
		return correlation_matrix;
	}

	public void setCorrelation_matrix(int[][] correlation_matrix) {
		this.correlation_matrix = correlation_matrix;
	}

	public double[][] getGeneralized_modularity_matrix() {
		return generalized_modularity_matrix;
	}

	public void setGeneralized_modularity_matrix(double[][] modularity_matrix) {
		this.generalized_modularity_matrix = modularity_matrix;
	}

	public void addChild(Group c){
		this.children.add(c);
	}
	
	public ArrayList<Group> getChildren(){
		return this.children;
	}

	public ArrayList<Integer> getIndices() {
		return indices;
	}

	public void setIndices(ArrayList<Integer> indices) {
		this.indices = indices;
	}

	public double[] getS() {
		return s;
	}

	public void setS(double[] s) {
		this.s = s;
	}
	
	
	
	
	private double[][] transposeVector(double[] v){
		double[][] t = new double[v.length][1];
		
		for (int i = 0; i < v.length; i++){
			t[i][0] = v[i];
		}
		
		return t;
	}
	
	private double[][] convertVectorToMatrix(double[] s){
		double[][] s_matrix = new double[1][s.length];
		for (int i = 0; i < s.length; i++){
			s_matrix[0][i] = s[i];
		}
		return s_matrix;
	}
	
	private double convertMatrixToDouble(double[][] a){
		if (a.length == 1){
			return a[0][0];
		}
		return -9999999;
	}

	
	// First matrix is mxn, second is pxq, result is mxq
	private double[][] matrixMultiplication(double[][] first, int m, int n, double[][] second, int p, int q){
	    double[][] result = new double[m][q];
	    double sum = 0;
		
	    for (int i = 0 ; i < m ; i++ )
        {
           for (int j = 0 ; j < q ; j++ )
           {   
              for (int k = 0 ; k < p ; k++ )
              {
                 sum = sum + first[i][k]*second[k][j];
              }

              result[i][j] = sum;
              sum = 0;
           }
        }
	    return result;
	}
	
	public void calculateCorrelationMatrix(double[][] similarity_matrix, double r){
		this.correlation_matrix = new int[indices.size()][indices.size()];
		for (int i = 0; i < this.indices.size(); i++){
			for (int j = 0; j < this.indices.size(); j++){
				if(similarity_matrix[this.indices.get(i)][this.indices.get(j)] >= r){
					this.correlation_matrix[i][j] = 1;
					this.setNumb_edges(this.getNumb_edges() + 1);
				}
				else{
					this.correlation_matrix[i][j] = 0;
				}
			}
		}
	}
	
	public void calculateGeneralizedModularityMatrix(double[][] modularity_matrix){
		double sum_group_values = calculateSumOfGroupValues(this.indices, modularity_matrix);
		
		for (int i = 0; i < modularity_matrix.length; i++){
			for (int j = 0; j < modularity_matrix.length; j++){
				this.generalized_modularity_matrix[i][j] = (double)modularity_matrix[i][j] - (double)kroneckerDelta(i,j) * sum_group_values;
			}
		}
	}
	
	private int kroneckerDelta(int i, int j){
		if (i == j){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	// Calculates the sum of the modularity value of each of the elements in the group
	private double calculateSumOfGroupValues(List<Integer> indices, double[][] modularity_matrix){
		double sum = 0;
		for (int i = 0; i < modularity_matrix.length; i++){
			for (int j = 0; j < indices.size(); j++){
				sum += modularity_matrix[i][indices.get(j)];
			}
		}
		return sum;
	}

	public int getNumb_edges() {
		return numb_edges;
	}

	public void setNumb_edges(int numb_edges) {
		this.numb_edges = numb_edges;
	}

	public Group getParent() {
		return parent;
	}

	public void setParent(Group parent) {
		this.parent = parent;
	}
	
}

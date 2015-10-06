package utilities;

import java.util.ArrayList;
import java.util.List;

import model.IntegerHolder;

public final class GroupOperations {

	public static int[][] calculateGroupCorrelationMatrix(List<Integer> indices, IntegerHolder numb_edges, double[][] similarity_matrix, double r){
		int[][] correlation = new int[indices.size()][indices.size()];
		for (int i = 0; i < indices.size(); i++){
			for (int j = 0; j < indices.size(); j++){
				if(similarity_matrix[indices.get(i)][indices.get(j)] >= r){
					correlation[i][j] = 1;
					numb_edges.increment();
				}
				else{
					correlation[i][j] = 0;
				}
			}
		}
		return correlation;
	}
	
	public static double[][] calculateGeneralizedModularityMatrix(List<Integer>indices, int[][] correlation_matrix, double[][] modularity_matrix){
		double[][] modularity = new double[modularity_matrix.length][modularity_matrix.length];
		double sum_group_values = calculateSumOfGroupValues(indices, modularity_matrix);
		
		for (int i = 0; i < modularity_matrix.length; i++){
			for (int j = 0; j < modularity_matrix.length; j++){
				modularity[i][j] = (double)modularity_matrix[i][j] - (double)kroneckerDelta(i,j) * sum_group_values;
			}
		}
		return modularity;
	}
	
	public static int kroneckerDelta(int i, int j){
		if (i == j){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	// Calculates the sum of the modularity value of each of the elements in the group
	public static double calculateSumOfGroupValues(List<Integer> indices, double[][] modularity_matrix){
		double sum = 0;
		for (int i = 0; i < modularity_matrix.length; i++){
			for (int j = 0; j < indices.size(); j++){
				sum += modularity_matrix[i][indices.get(j)];
			}
		}
		return sum;
	}
	
	// Calculates correlation matrix and generalized modularity matrix for the group
	public static double calculateModularityContribution(ArrayList<Integer> indices, double[] s, int[][] original_correlation_matrix, int numb_edges, double[][] modularity_matrix){
		double[][] generalized_modularity_matrix = calculateGeneralizedModularityMatrix(indices, original_correlation_matrix, modularity_matrix);
		double[][] sT = MatrixOperations.transposeVector(s);
		double[][] s_matrix = MatrixOperations.convertVectorToMatrix(s);
				
		double[][] s_modularity = MatrixOperations.matrixMultiplication(s_matrix,1, s.length, generalized_modularity_matrix, generalized_modularity_matrix.length, generalized_modularity_matrix.length);
		double[][] sTs = MatrixOperations.matrixMultiplication(s_modularity, 1, s_modularity.length, sT, sT.length, 1);
		double sTs_value = MatrixOperations.convertMatrixToDouble(sTs);
		double Q  = 0;
		if (sTs_value == -9999999){
			System.out.println("Error: st * s value calculated incorrectly");
			Q = -1;
		}
			
		Q = (double)1/4*numb_edges * (double)sTs_value;
			
		return Q;
		}
	
	
	public static void separateIndicesGroups(double[] v, List<Integer> indices1, List<Integer> indices2){
		for (int i = 0; i < v.length; i++){
			if (v[i] >= 0){
				indices1.add(i);
			}
			else{
				indices2.add(i);
			}
		}
	}
	
	/* Divide the vector v in two vectors, s1 and s2, accordingly to the sings of the leading eigenvector. 
	 * indices_group ArrayList, at the end of the execution, will contain the indices that belong to the correspondent group.*/
	public static void divideInTwo(double[] s, double[] leading_eigenvector, ArrayList<Integer> current_indices, ArrayList<Integer> s1, ArrayList<Integer> s2, ArrayList<Integer> indices_group1, ArrayList<Integer> indices_group2){
		for (int i = 0; i < current_indices.size(); i++){
			if (leading_eigenvector[i] >= 0){
				s[i] = 1;
				s1.add(1);
				indices_group1.add(current_indices.get(i));
			}
			else
			{
				s[i] = -1;
				s2.add(-1);
				indices_group2.add(current_indices.get(i));
			}
		}
		
		/*for (Integer i : current_indices){
			if (leading_eigenvector[i] >= 0){
				s[i] = 1;
				s1.add(1);
				indices_group1.add(i);
			}
			else{
				s[i] = -1;
				s2.add(-1);
				indices_group2.add(i);
			}
		}
		*/
	}
}

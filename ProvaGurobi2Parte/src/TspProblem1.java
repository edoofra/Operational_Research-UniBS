/* Copyright 2020, Gurobi Optimization, LLC */

import gurobi.*;
import gurobi.GRB.DoubleAttr;

public class TspProblem1 {

	private GRBVar[][] vars;
	private static int[][] tspMatrix;
	// private static int[][] pathMatrix;
	private static GRBEnv env;
	private static GRBModel model;
	private int problemType;
	private static int n;
	private static double finalSolution;
	private static StringBuilder tourString = new StringBuilder();

	public TspProblem1(int type) {
		this.problemType = type;
	}

	// Given an integer-feasible solution 'sol', return the smallest
	// sub-tour (as a list of node indices).

	protected int[] findSubtour(double[][] sol) {
		
		int n = sol.length;
		boolean[] seen = new boolean[n];
		int[] tour = new int[n];
		int bestInd, bestLen;
		int i, node, len, start;

		for (i = 0; i < n; i++)
			seen[i] = false;

		start = 0;
		bestLen = n + 1;
		bestInd = -1;
		node = 0;
		while (start < n) {
			for (node = 0; node < n; node++)
				if (!seen[node])
					break;
			if (node == n)
				break;
			for (len = 0; len < n; len++) {
				tour[start + len] = node;
				seen[node] = true;
				for (i = 0; i < n; i++) {
					if (sol[node][i] > 0.5 && !seen[i]) {
						node = i;
						break;
					}
				}
				if (i == n) {
					len++;
					if (len < bestLen) {
						bestLen = len;
						bestInd = start;
					}
					start += len;
					break;
				}
			}
		}

		int result[] = new int[bestLen];
		for (i = 0; i < bestLen; i++)
			result[i] = tour[bestInd + i];
		return result;
	}

	public void addMTZConstr() throws GRBException {

		GRBLinExpr expr = new GRBLinExpr();
		GRBVar u[] = new GRBVar[n];

		// aggiunta variabili
		for (int i = 1; i < n; i++) {
			u[i] = model.addVar(2, n, 0, GRB.INTEGER, "u" + i);
		}

		// Primo vincolo u0 = 1
		u[0] = model.addVar(1, 1, 0, GRB.INTEGER, "u0");
//		expr.addTerm(1, u[0]);
//		model.addConstr(expr, GRB.EQUAL, 1, "mtz1");

		// secondo set di vincoli 2 <= ui <= n
		for (int i = 1; i < n; i++) {
			expr = new GRBLinExpr();
			expr.addTerm(1, u[i]);
			model.addConstr(expr, GRB.GREATER_EQUAL, 2, "mtz-" + i + 1);
			model.addConstr(expr, GRB.LESS_EQUAL, n, "mtz" + i + 1);
		}
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < n; j++) {
				if (i == j)
					continue;
				expr = new GRBLinExpr();
				expr.addTerm(1, u[i]);
				expr.addTerm(-1, u[j]);
				expr.addTerm(n - 1, vars[i][j]);
				model.addConstr(expr, GRB.LESS_EQUAL, n - 2, "mtz*" + (i - 1) * n + j);
			}
		}

	}
	
	public String getTour() {
		return tourString.toString();
	}
	
	public double getFinalSolution() {
		return finalSolution;
	}

	public void resolve() {
		// LEGGO DAL FILE LE INFORMAZIONI SUI NODI
		FileUtil.parsing();
		tspMatrix = FileUtil.returnTsp();
		// pathMatrix=FileUtil.returnX();
		int lenght = tspMatrix.length;

		try {
			env = new GRBEnv();
			model = new GRBModel(env);

			// Must set LazyConstraints parameter when using lazy constraints

			model.set(GRB.IntParam.LazyConstraints, 1);

			// Create variables

			GRBVar[][] vars = new GRBVar[lenght][lenght];

			for (int i = 0; i < lenght; i++)
				for (int j = 0; j < lenght; j++) {
					vars[i][j] = model.addVar(0.0, 1.0, tspMatrix[i][j], GRB.BINARY,
							"x" + String.valueOf(i) + "_" + String.valueOf(j));
				}

			// Degree-2 constraints

			for (int i = 0; i < lenght; i++) {
				GRBLinExpr expr = new GRBLinExpr();
				for (int j = 0; j < lenght; j++)
					expr.addTerm(1.0, vars[i][j]);
				model.addConstr(expr, GRB.EQUAL, 1, "deg1_" + String.valueOf(i));
			}

			for (int i = 0; i < lenght; i++) {
				GRBLinExpr expr = new GRBLinExpr();
				for (int j = 0; j < lenght; j++)
					expr.addTerm(1.0, vars[j][i]);
				model.addConstr(expr, GRB.EQUAL, 1, "deg2_" + String.valueOf(i));
			}

			// Forbid edge from node back to itself

			for (int i = 0; i < lenght; i++)
				vars[i][i].set(GRB.DoubleAttr.UB, 0.0);
			
			if(problemType == 2) addMTZConstr();

			model.optimize();

			if (model.get(GRB.IntAttr.SolCount) > 0) {
				int[] tour = findSubtour(model.get(GRB.DoubleAttr.X, vars));
				assert tour.length == lenght;

				for (int i = 0; i < tour.length; i++)
					tourString.append(String.valueOf(tour[i]) + " ");
			}
			
			finalSolution = model.get(DoubleAttr.ObjVal);

			// Dispose of model and environment
			model.dispose();
			env.dispose();

		} catch (GRBException e) {
			System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
			e.printStackTrace();
		}
	}
}

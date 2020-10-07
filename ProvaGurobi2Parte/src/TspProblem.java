import gurobi.*;
import gurobi.GRB.DoubleAttr;

/**
 * class for solving the tsp problem
 * @author E.Fratus C.Bonomini
 */
public class TspProblem {
	private GRBVar[][] variablesMatrix;
	private int[][] tspMatrix;
	private int[][] pathMatrix;
	private GRBEnv env;
	private GRBModel model;
	private int n;
	private int problemType;

	public TspProblem(int _problemType) {

		this.problemType = _problemType;
		//read from file
		FileUtil.parsing();
		tspMatrix = FileUtil.returnTsp();
		pathMatrix = FileUtil.returnX();
		n = pathMatrix.length;
	}
	/**
	 * copyright gurobi
	 * Subtour elimination callback. Whenever a feasible solution is found,
	 * find the subtour that contains node 0, and add a subtour elimination
	 * constraint if the tour doesn't visit every node.
	 * Given an integer-feasible solution 'sol', return the smallest
	 * sub-tour (as a list of node indices).
	*/
	public String findSubtour() throws GRBException {
		int n = this.n;
		boolean[] seen = new boolean[n];
		int[] tour = new int[n];
		int bestind, bestlen;
		int i, node, len, start;

		for (i = 0; i < n; i++)
			seen[i] = false;

		start = 0;
		bestlen = n + 1;
		bestind = -1;
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
					if (variablesMatrix[node][i].get(DoubleAttr.X) > 0.5 && !seen[i]) {
						node = i;
						break;
					}
				}
				if (i == n) {
					len++;
					if (len < bestlen) {
						bestlen = len;
						bestind = start;
					}
					start += len;
					break;
				}
			}
		}

		int result[] = new int[bestlen];
		for (i = 0; i < bestlen; i++)
			result[i] = tour[bestind + i];
		//modified to return string
		StringBuilder tourString = new StringBuilder();
		for (int k = 0; k < result.length; k++) {
			tourString.append(k + " ");
		}
		return tourString.toString();
	}

	/**
	 * initializes the problem 
	 * @throws GRBException
	 */
	public void initialize() throws GRBException {

		env = new GRBEnv();
		model = new GRBModel(env);
		model.set(GRB.IntParam.LazyConstraints, 1);

		// Create variables
		variablesMatrix = new GRBVar[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				variablesMatrix[i][j] = model.addVar(0.0, 1.0, tspMatrix[i][j], GRB.BINARY,
						"x" + String.valueOf(i) + "_" + String.valueOf(j));
			}

		// assignment constraints
		for (int i = 0; i < n; i++) {
			GRBLinExpr expr = new GRBLinExpr();
			for (int j = 0; j < n; j++)
				expr.addTerm(1.0, variablesMatrix[i][j]);
			model.addConstr(expr, GRB.EQUAL, 1, "deg1_" + String.valueOf(i));
		}
		for (int i = 0; i < n; i++) {
			GRBLinExpr expr = new GRBLinExpr();
			for (int j = 0; j < n; j++)
				expr.addTerm(1.0, variablesMatrix[j][i]);
			model.addConstr(expr, GRB.EQUAL, 1, "deg2_" + String.valueOf(i));
		}

		for (int i = 0; i < n; i++)
			variablesMatrix[i][i].set(GRB.DoubleAttr.UB, 0.0);
		//choice for question II or question III
		if (problemType == 1)
			addMTZ(variablesMatrix);
	}

	/**
	 * add MTZ constr for question III
	 * @param vars variables of the model
	 * @throws GRBException
	 */
	public void addMTZ(GRBVar vars[][]) throws GRBException {
		
		GRBLinExpr expr = new GRBLinExpr();
		GRBVar u[] = new GRBVar[n];

		//adding variables
		for (int i = 1; i < n; i++) {
			u[i] = model.addVar(2, n, 0, GRB.INTEGER, "u" + i);
		}
		u[0] = model.addVar(1, 1, 0, GRB.INTEGER, "u0");
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

	/**
	 * solve the problem
	 * @return value of the objective function
	 * @throws GRBException
	 */
	public double solve() throws GRBException {
		initialize();
		model.optimize();
		return model.get(DoubleAttr.ObjVal);
	}

	/**
	 * dispose the model and environment
	 * @throws GRBException
	 */
	public void dispose() throws GRBException {
		model.dispose();
		env.dispose();
	}

}

import gurobi.*;
import gurobi.GRB.*;

/**
 * @author E.Fratus, C.Bonomini
 * class for solving the MMKP problem
 * (multidimensional multiple-choice knapsack problem)
 */
public class MmkpProblem {   

    private static final int TIME = 120;
    private static String FILEPATH = "mmkp.lp";
    private static GRBEnv env;
    private static GRBModel model;
    private static GRBModel relaxed;
    private double finalValue;
    private double GAP = 0.0;

    /**
     * creation of the model, preprocessing and resolution
     * @throws GRBException
     */
    public void resolveMMKP() throws GRBException {

           env = new GRBEnv();
           setEnvParam();
           //create model from the file
           model = new GRBModel(env, FILEPATH);
           fixing();
           model.presolve();
           model.optimize(); 
           finalValue = model.get(DoubleAttr.ObjVal);
    }
    
    /**
     * preprocessing method with variable fixing
     * @throws GRBException
     */
    private void fixing() throws GRBException {
        //create the relaxed model with PL problem
    	relaxed =  model.relax();
    	relaxed.optimize();
        //if the cj of a variable is lower than the GAP
        //sets the value of the variable to 0
    	GAP =30188 - relaxed.get(GRB.DoubleAttr.ObjVal);
    	for(GRBVar x : relaxed.getVars()) {
    		if(x.get(DoubleAttr.X)==0) {
    			if(x.get(GRB.DoubleAttr.RC)<GAP)
        			model.getVarByName(x.get(StringAttr.VarName)).set(DoubleAttr.UB, 0);
    		}	
    	}
    }

    /**
     * set the environment parameters
     * @throws GRBException
     */
    private  void setEnvParam() throws GRBException {

    	env.set(DoubleParam.TimeLimit,TIME);
        env.set(IntParam.Presolve, 1);
        env.set(IntParam.Cuts, 2);
        env.set(GRB.IntParam.MIPFocus, 1);
        env.set(GRB.DoubleParam.Heuristics, 0.7);
    }

    /**
     * 
     * @return value of objective function
     */
    public double getFinalValue() {
        return finalValue;
    }
    
    /**
     * dispose model and environment
     * @throws GRBException
     */
    public void dispose() throws GRBException {
		model.dispose();
		env.dispose();
	}
}

import gurobi.*;
import gurobi.GRB.*;

/**
 * classe per la soluzione del problema MMKP
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
     * metodo per la creazione del modello, il suo preprocessing e la sua risoluzione
     * @throws GRBException
     */
    public void resolveMMKP() throws GRBException {

           env = new GRBEnv();
           setEnvParam();
           model = new GRBModel(env, FILEPATH);
           fixing();
           model.presolve();
           model.optimize(); 
           finalValue = model.get(DoubleAttr.ObjVal);
           model.dispose();
           env.dispose();  
    }
    
    private void fixing() throws GRBException {
    	relaxed =  model.relax();
    	relaxed.optimize();
    	//cercato
    	GAP =30188 - relaxed.get(GRB.DoubleAttr.ObjVal);
    	for(GRBVar x : relaxed.getVars()) {
    		if(x.get(DoubleAttr.X)==0) {
    			//controllo se costo ridotto è minore del gap
    			if(x.get(GRB.DoubleAttr.RC)<GAP)
        			model.getVarByName(x.get(StringAttr.VarName)).set(DoubleAttr.UB, 0);
    		}	
    	}
    }

    /**
     * metodo per il settaggio dei parametri dell'environment
     * @throws GRBException
     */
    private  void setEnvParam() throws GRBException {

    	env.set(DoubleParam.TimeLimit,TIME);
        env.set(IntParam.Presolve, 1);
        env.set(IntParam.Cuts, 2);
        env.set(GRB.IntParam.MIPFocus, 1);
        env.set(GRB.DoubleParam.Heuristics, 0.7);
    }

    public double getFinalValue() {
        return finalValue;
    }
}

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
    private double finalValue;

    /**
     * metodo per la creazione del modello, il suo preprocessing e la sua risoluzione
     * @throws GRBException
     */
    public void resolveMMKP() throws GRBException {

           env = new GRBEnv();
           setEnvParam();
           model = new GRBModel(env, FILEPATH);
           model.presolve();
           model.optimize(); 
           finalValue = model.get(DoubleAttr.ObjVal);
           model.dispose();
           env.dispose();  
    }

    /**
     * metodo per il settaggio dei parametri dell'environment
     * @throws GRBException
     */
    private  void setEnvParam() throws GRBException {

    	env.set(DoubleParam.TimeLimit,TIME);
        env.set(IntParam.Presolve, 2);
        env.set(IntParam.Cuts, 1);
        env.set(GRB.IntParam.MIPFocus, 1);
        env.set(GRB.DoubleParam.Heuristics, 1);
    }

    public double getFinalValue() {
        return finalValue;
    }
}

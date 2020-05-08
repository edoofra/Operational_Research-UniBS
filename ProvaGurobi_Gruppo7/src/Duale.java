import gurobi.*;
import gurobi.GRB.DoubleAttr;
import gurobi.GRB.IntAttr;

/**
 * @author E.Fratus
 * @author C.Bonomini
 * 
 * classe per l'estrazione dei dati relativi al duale
 */
public class Duale {
	GRBModel model;	
	
	public Duale(GRBModel model) {
		this.model = model;
	}
	
	public double soluzioneOttima() throws GRBException {
		double soluzioneDuale = 0.0;
		int stato = model.get(IntAttr.Status);
		if(stato==2) soluzioneDuale = model.get(DoubleAttr.ObjVal);
		return soluzioneDuale;
	}
	
	public void prezziOmbra(StringBuilder listaPi) throws GRBException {
		for(GRBConstr constr : model.getConstrs()) {
			double pi = constr.get(GRB.DoubleAttr.Pi);
			listaPi.append("<"+pi+"> ");	
		}
	}
	
	public double lowerBound(int n) throws GRBException {
		GRBVar [] listDualVariables = model.getVars();
		return listDualVariables[n].get(GRB.DoubleAttr.SAObjLow);  	
	}
	
	public double upperBound(int n) throws GRBException {
		GRBVar [] listDualVariables = model.getVars();
		return listDualVariables[n].get(GRB.DoubleAttr.SAObjUp);
	    	
	}
	

}

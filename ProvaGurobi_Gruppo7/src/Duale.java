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
	/**
	 * metodo per il calcolo del valore ottimo del duale
	 * @return valore ottimo duale
	 * @throws GRBException
	 */
	public double soluzioneOttima() throws GRBException {
		double soluzioneDuale = 0.0;
		int stato = model.get(IntAttr.Status);
		if(stato==2) soluzioneDuale = model.get(DoubleAttr.ObjVal);
		return soluzioneDuale;
	}
	
	/**
	 * calcolo soluzione ottima duale, ovvero vettore dei prezzi ombra
	 * @param listaPi StringBuilder contenente i prezzi ombra
	 * @throws GRBException
	 */
	public void prezziOmbra(StringBuilder listaPi) throws GRBException {
		for(GRBConstr constr : model.getConstrs()) {
			double pi = constr.get(GRB.DoubleAttr.Pi);
			listaPi.append("<"+pi+"> ");	
		}
	}
	
	/**
	 * calcolo lower bound sensitivit� per una variabile n
	 * @param n numero variabile nella lista
	 * @return valore lowerBound
	 * @throws GRBException
	 */
	public double lowerBound(int n) throws GRBException {
		GRBVar [] listDualVariables = model.getVars();
		return listDualVariables[n].get(GRB.DoubleAttr.SAObjLow);  	
	}
	
	/**
	 * calcolo upper bound sensitivit� per una variabile n
	 * @param n numero variabile nella lista
	 * @return valore upperBound
	 * @throws GRBException
	 */
	public double upperBound(int n) throws GRBException {
		GRBVar [] listDualVariables = model.getVars();
		return listDualVariables[n].get(GRB.DoubleAttr.SAObjUp);
	    	
	}
	

}

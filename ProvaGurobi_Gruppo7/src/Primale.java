import gurobi.*;
import gurobi.GRB.DoubleAttr;
import gurobi.GRB.IntAttr;

/**
 * @author E.Fratus
 * @author C. Bonomini
 * 
 * classe per l'estrazione dei dati relativi al problema primale
 */
public class Primale {
	private GRBModel model;
	/**
	 * costruttore
	 * @param model contenente il problema di programmazione lineare 
	 */
	public Primale(GRBModel model) {
		this.model = model;
	}
	
	/**
	 * metodo per la determinazione delle variabili in base e di quelle fuori base
	 * @param variabiliBase StringBulder contenente 1 se l rispettiva variabile è in base, 0 altrimenti
	 * @throws GRBException
	 */
	public void controlloBase(StringBuilder variabiliBase) throws GRBException {
		
		for(GRBVar var : model.getVars()) {
			double valore = var.get(IntAttr.VBasis);
			if(valore == 0) variabiliBase.append("<"+1+"> ");
			else variabiliBase.append("<"+0+"> ");
		}	
		
//		for(GRBConstr constr : model.getConstrs()) {
//			double slack = constr.get(GRB.DoubleAttr.Slack);
//			if(slack != 0) variabiliBase.append("<"+1+"> ");
//			else variabiliBase.append("<"+0+"> ");
//		}		
	}
	
	/**
	 * metodo per la determinazione della soluzione ottima
	 * @param listaOttimo  StringBuilder in cui salvare i valori dei coefficenti delle variabili nella sol. ottima
	 * @throws GRBException
	 */
	public void soluzioneOttima(StringBuilder listaOttimo) throws GRBException {
		
		for(GRBVar var : model.getVars()) {
			double valore = var.get(DoubleAttr.X);
			listaOttimo.append(String.format("<%.4f> ", valore));
		}
		
//		for(GRBConstr constr : model.getConstrs()) {
//			double slack = constr.get(GRB.DoubleAttr.Slack);
//			listaOttimo.append(String.format("<%.4f> ", slack));	
//		}		
	}
	
	/**
	 * metodo per la determinazione dei coefficenti di costo ridotto
	 * @param listaCosti StringBuilder contenente i coefficenti di costo ridotto
	 * @throws GRBException
	 */
	public void controlloCosti(StringBuilder listaCosti) throws GRBException {
		
		for(GRBVar var : model.getVars()) {
			double costo = var.get(DoubleAttr.RC);
			listaCosti.append("<"+costo+"> ");
		}		
	}
	/**
	 * metodo per controllare se la soluzione ottima trovata è multipla
	 * @return boolean che indica true se è multipla
	 * @throws GRBException
	 */
	public boolean controlloSoluzioneMultipla() throws GRBException {
		GRBVar [] listaVar = model.getVars();
		
		for(int i = 0; i<listaVar.length; i++) {
			double valore = listaVar[i].get(DoubleAttr.X);
			double costo = listaVar[i].get(DoubleAttr.RC);
			if(valore == 0 && costo == 0) return true;			
		}		
		return false;
	}
	/**
	 * metodo per controllare se la soluzione ottima trovata è degenere
	 * @return boolean true se è degenere
	 * @throws GRBException
	 */
	public boolean controlloSoluzioneDegenere() throws GRBException {
		GRBVar [] listaVar = model.getVars();
		GRBConstr [] listaVincoli = model.getConstrs();
		
		for(int i = 0; i<listaVar.length; i++) {
			double valore = listaVar[i].get(DoubleAttr.X);
			int base = listaVar[i].get(IntAttr.VBasis);
			if(valore == 0 && base == 0) return true;			
		}
		
//		for(int i= 0; i<listaVincoli.length; i++) {
//			double valore = listaVincoli[i].get(DoubleAttr.Slack);
//			if(valore <= 0) return true;
//		}
    	return false;
	}
	/**
	 * metodo per trovar il valore della funzione obiettivo all'ottimo
	 * @return valore f.o.
	 * @throws GRBException
	 */
	public double valoreFunzione() throws GRBException {
		return  model.get(DoubleAttr.ObjVal);
	}
}

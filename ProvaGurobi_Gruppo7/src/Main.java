import java.io.BufferedWriter;
import Jama.Matrix;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import gurobi.*;
import gurobi.GRB.DoubleAttr;
import gurobi.GRB.IntAttr;
import gurobi.GRB.IntParam;


public class Main {

	public static void main(String[] args) {
		
		try {
			
			//istanziamento dell'environment e creazione del file di log
			GRBEnv env = new GRBEnv("ProvaGurobi_Gruppo7.log");
			
			//modifica parametri environment 
			env.set(IntParam.Presolve, 0);
			env.set(IntParam.Method, 0);
			env.set(GRB.DoubleParam.TimeLimit, 600);
			env.set(IntParam.PoolSolutions, 2);
			env.set(IntParam.PoolSearchMode, 2);

			//creazione modello contenente il problema da risolvere 
			//creazione delle variabili del problema
			GRBModel model = new GRBModel(env);
			GRBVar x1 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "x1");
			GRBVar x2 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "x2");
			GRBVar x3 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "x3");
			GRBVar x4 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "x4");
			GRBVar x5 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "x5");
			GRBVar x6 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "x6");
			GRBVar x7 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "x7");
			GRBVar x8 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "x8");
			GRBVar x9 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "x9");
			GRBVar x10 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "x10");
			GRBVar s1 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "s1");
			GRBVar s2 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "s2");
			GRBVar s3 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "s3");
			GRBVar s4 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "s4");
			GRBVar s5 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "s5");
			GRBVar s6 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "s6");
			GRBVar s7 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "s7");
			GRBVar s8 = model.addVar(0, GRB.INFINITY, 0, GRB.CONTINUOUS, "s8");
			
			
			//costruzione funzione obiettivo
			GRBLinExpr expr = new GRBLinExpr();
			expr.addTerm(1.0, x1);
			expr.addTerm(6.0, x2);
			expr.addTerm(6.0, x3);
			expr.addTerm(7.0, x4);
			expr.addTerm(-6.0, x5);
			expr.addTerm(-4.0, x6);
			expr.addTerm(4.0, x7);
			expr.addTerm(-8.0, x8);
			expr.addTerm(8.0, x9);
			expr.addTerm(-9.0, x10);
			model.setObjective(expr, GRB.MINIMIZE);
			
			//costruzione vincolo 1 e aggiunta al modello
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x1);
			expr.addTerm(1.0, x4);
			expr.addTerm(2.0, x6);
			expr.addTerm(7.0, x7);
			expr.addTerm(2.0, x9);
			expr.addTerm(1.0, s1);
			model.addConstr(expr, GRB.EQUAL, 6.0,"v1");
			
			//costruzione vincolo 2 e aggiunta al modello
			expr = new GRBLinExpr();
			expr.addTerm(-6.0, x7);
			expr.addTerm(2.0, x8);
			expr.addTerm(5.0, x10);
			expr.addTerm(1.0, s2);
			model.addConstr(expr, GRB.EQUAL, 1.0,"v2");
			
			//costruzione vincolo 3 e aggiunta al modello
			expr = new GRBLinExpr();
			expr.addTerm(-3.0, x1);
			expr.addTerm(1.0, x6);
			expr.addTerm(-6.0, x7);
			expr.addTerm(-7.0, x9);
			expr.addTerm(-1.0, s3);
			model.addConstr(expr, GRB.EQUAL, -3.0,"v3");
			
			//costruzione vincolo 4 e aggiunta al modello
			expr = new GRBLinExpr();
			expr.addTerm(1.0, x1);
			expr.addTerm(7.0, x8);
			expr.addTerm(-4.0, x9);
			expr.addTerm(-3.0, x10);
			expr.addTerm(1.0, s4);
			model.addConstr(expr, GRB.EQUAL, 10.0,"v4");
			
			//costruzione vincolo 5 e aggiunta al modello
			expr = new GRBLinExpr();
			expr.addTerm(-5.0, x7);
			expr.addTerm(-5.0, x10);
			expr.addTerm(-1.0, s5);
			model.addConstr(expr, GRB.EQUAL, -6.0,"v5");
			
			//costruzione vincolo 6 e aggiunta al modello
			expr = new GRBLinExpr();
			expr.addTerm(3.0, x7);
			expr.addTerm(-13.0, x8);
			expr.addTerm(1.0, x9);
			expr.addTerm(-7.0, x10);
			expr.addTerm(1.0, s6);
			model.addConstr(expr, GRB.EQUAL, 5.0,"v6");
			
			//costruzione vincolo 7 e aggiunta al modello
			expr = new GRBLinExpr();
			expr.addTerm(-6.0, x5);
			expr.addTerm(4.0, x7);
			expr.addTerm(-4.0, x8);
			expr.addTerm(-7.0, x9);
			expr.addTerm(1.0, s7);
			model.addConstr(expr, GRB.EQUAL, 3.0,"v7");
			
			//costruzione vincolo 8 e aggiunta al modello
			expr = new GRBLinExpr();
			expr.addTerm(-4.0, x1);
			expr.addTerm(-5.0, x5);
			expr.addTerm(-5.0, x7);
			expr.addTerm(-1.0, s8);
		    model.addConstr(expr, GRB.EQUAL, -10.0,"v8");
		
			//avvio ottimizzazione modello
			model.optimize();		
			
			/***********************************************************************************************************/
		    
			//QUESITO I
			//problema primale
			
			StringBuilder listaOttimo = new StringBuilder();
			StringBuilder variabiliBase = new StringBuilder();
			StringBuilder listaCosti = new StringBuilder();
			
			//creo oggetto contenentr il problema primale
			Primale primale = new Primale(model);
			double objValue = primale.valoreFunzione();
			primale.soluzioneOttima(listaOttimo);
			primale.controlloBase(variabiliBase);
			primale.controlloCosti(listaCosti);
			String soluzioneMultipla = (primale.controlloSoluzioneMultipla()) ? "si" : "no";
			String soluzioneDegenere = (primale.controlloSoluzioneDegenere()) ? "si" : "no";
			
			/***********************************************************************************************************/
			
			//QUESITO II
			//problema duale e sensitività
			
			StringBuilder listaPi = new StringBuilder();
			Duale duale = new Duale(model);
			double soluzioneDuale = duale.soluzioneOttima();
			duale.prezziOmbra(listaPi);
			double lowerBound = duale.lowerBound(2);
			double upperBound = duale.upperBound(2);
			
			
			/***********************************************************************************************************/
			//QUESITO III
			
			double [][] array = {{0,7,0,0,0,0,0,0}, {0,-6,2,0,0,0,0,0}, {0,-6,0,-1,0,0,0,0}, {0,0,7,0,1,0,0,0},
								{0,-5,0,0,0,-1,0,0}, {0,3,3,0,0,0,1,0}, {-6,4,-4,0,0,0,0,1}, {-5,-5,0,0,0,0,0,0}};
			
			Matrix B = new Matrix(array);
			double [][] terminiNoti = {{6},{1},{-3},{10},{-6},{5},{3},{-10}};
			Matrix term = new Matrix(terminiNoti);
//			double[][] cbt = {{-6},{4},{-8},{0},{0},{0},{0},{0}};
//			Matrix CBT = new Matrix(cbt);
			Matrix newSolution = B.inverse().times(term);			
			
			double [][] arrayNewSolution = newSolution.getArray();
			int [] base = {0,0,0,0,1,0,1,1,0,0,0,0,1,1,1,1,1,0};
			StringBuilder strNuovoOttimo = new StringBuilder();
			int j = 0;
			for(int i=0; i<base.length; i++) {
				if(base[i] == 1) strNuovoOttimo.append(String.format("< %.4f < ", arrayNewSolution[j++][0]));
				else  strNuovoOttimo.append(String.format("< %.4f < ", 0.0));
			}
			
			

			/***********************************************************************************************************/
			
			//creazione file con le risposte
			File fileText = new File("RisultatiGruppo7.txt");
			try {
				
				if(!fileText.exists())fileText.createNewFile();
				BufferedWriter out = new BufferedWriter(new FileWriter(fileText));
				out.write("GRUPPO <7> \n" + "Componenti: <E. Fratus> <C. Bonomini> \n\n");
				out.write("QUESITO I:\n");
				out.write("valore funzione obiettivo = <" + objValue + ">\n");
				out.write("soluzione di base ottima: " + listaOttimo.toString() + "\n");
				out.write("variabili in base: " + variabiliBase.toString() + "\n");
				out.write("coefficenti di costo ridotto: " + listaCosti+ "\n");
				out.write("soluzione ottima multipla: " + soluzioneMultipla +  "\n");
				out.write("soluzione ottima degenere: " + soluzioneDegenere + "\n");
				out.write("motivazione: \n\n");
				
				out.write("QUESITO II: \n");
				out.write("valore funzione obiettivo duale: " + soluzioneDuale + "\n");
				out.write("soluzione di base ottima duale: "+listaPi+"\n");
				out.write("sensitività: " + lowerBound + " <= DELTA <= " + upperBound + "\n\n");
				
				out.write("QUESITO III: \n");
				out.write("soluzione ottima alternativa: " + strNuovoOttimo);
				
				
				
				out.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//libero risorse associate a model ed environment
			model.dispose();
			env.dispose();			
			
		} catch (GRBException e) {
			e.printStackTrace();
		}
	}
}

/*TODO
 * -----------------------------------------aggiungere slack alla soluzione ottima 
 * -----------------------------------------algoritmo soluzione multipla
 * .........................................algoritmo soluzione degenere
 * -----------------------------------------soluzione ottima del duale
 *------------------------------------------sistemare metodi e classi (primale, duale, main e utility)
 * aggiungere slack alla soluzione del duale
 * 
 */

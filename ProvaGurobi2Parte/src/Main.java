import gurobi.GRBException;

public class Main {

	public static void main(String[] args) {

		StringBuilder risposte = new StringBuilder();
		/********************************************************************* */
		//quesito 1
		//creo un'istanza per la soluzione del problema MMKP
		MmkpProblem mmkp = new MmkpProblem();
		try {

			mmkp.resolveMMKP();
			risposte.append("QUESITO I \n\n funzione obiettivo = < "
			 + mmkp.getFinalValue() + ">\n\n");

		} catch (GRBException e) {
			e.printStackTrace();
		}
		/********************************************************************* */

		//quesito 2
		risposte.append("QUESITO II \n\n");
		TspProblem1 tspProblem = new TspProblem1(1);
		tspProblem.resolve();
		risposte.append("funzione obiettivo senza SEC, CC o MTZ = < " + 
				tspProblem.getFinalSolution() + " >\n");
		risposte.append("sottociclo individuato = [ " + 
				tspProblem.getTour()+" ]\n\n");

		/********************************************************************* */
		
		//quesito 3
		risposte.append("QUESITO III \n\n");
		TspProblem1 tspProblem2 = new TspProblem1(2);
		tspProblem2.resolve();
		risposte.append("funzione obiettivo con MTZ = < " + 
				tspProblem2.getFinalSolution() + " >\n");
		risposte.append("ciclo ottimo = [ " + 
				tspProblem2.getTour()+" ]\n\n");


		FileUtil.writeOnFile(risposte.toString());
	}

}

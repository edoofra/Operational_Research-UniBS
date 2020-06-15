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
			risposte.append("QUESITO 1 \n funzione obiettivo = < "
			 + mmkp.getFinalValue() + ">\n");

		} catch (GRBException e) {
			e.printStackTrace();
		}
		/********************************************************************* */

		FileUtil.writeOnFile(risposte.toString());
	}

}

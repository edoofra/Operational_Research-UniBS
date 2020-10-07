import gurobi.GRBException;

public class Main {

	public static void main(String[] args) {

		//contains the answers to the questions
		StringBuilder answers = new StringBuilder();
		try {
			/********************************************************************* */

			// QUESTION I
			MmkpProblem mmkp = new MmkpProblem();
			mmkp.resolveMMKP();
			answers.append("QUESITO I \n\n funzione obiettivo = < "
					+ mmkp.getFinalValue() + ">\n\n");
			mmkp.dispose();

			/********************************************************************* */

			// QUESTION II
			answers.append("QUESITO II \n\n");
			TspProblem tspProblem = new TspProblem(0);
			answers.append("funzione obiettivo senza SEC, CC o MTZ = < " 
					+ tspProblem.solve() + " >\n");
			answers.append("sottociclo individuato = [ "
					+ tspProblem.findSubtour() + " ]\n\n");
			tspProblem.dispose();

			/********************************************************************* */

			// QUESTION III
			answers.append("QUESITO III \n\n");
			TspProblem tspProblem2 = new TspProblem(1);
			answers.append("funzione obiettivo con MTZ = < " 
					+ tspProblem2.solve() + " >\n");
			answers.append("ciclo ottimo = [ "
					+ tspProblem2.findSubtour() + " ]\n\n");
			tspProblem2.dispose();

		} catch (GRBException ex) {
			ex.printStackTrace();
		}
		//write on file
		FileUtil.writeOnFile(answers.toString());
	}

}

import java.io.*;
import java.util.Scanner;

/**
 * @author E.Fratus, C.Bonomini
 * class for file management *
 */
public class FileUtil {
	
	private static int[][] tsp = new int[1][1];
	private static int[][] x = new int[1][1];
	
	public static void parsing() {				
		//parsing from the file
		try {
			
			File myFile = new File("tsp.txt");
			Scanner reader = new Scanner(myFile);
			while (reader.hasNextLine()) {
				//divides the string when it finds a space
				String read[] = reader.nextLine().split(" ");
				if (read.length == 2) {
					Integer matrixDimension = Integer.parseInt(read[1]);
					tsp = new int[matrixDimension][matrixDimension];
					x = new int[matrixDimension][matrixDimension];
					//initializes the 2 matrices
					for (int i = 0; i < Integer.parseInt(read[1]); i++) {
						for (int j = 0; j < Integer.parseInt(read[1]); j++) {
							tsp[i][j] = 0;
							x[i][j] = 0;
						}
					}
					
				} else {
					int i = Integer.parseInt(read[0]);
					int j = Integer.parseInt(read[1]);
					int len = Integer.parseInt(read[2]);
					// because of the symmetry
					tsp[i][j] = len; 
					tsp[j][i]= len;
					x[i][j] = 1;
					x[j][i] = 1;
					

				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}		
	}

	public static int[][] returnTsp(){
		return tsp;
	}
	
	public static int[][] returnX(){
		return x;
	}


	/**
	 * writes the answers on file
	 * @param toWrite StringBuilder with the answers
	 */
	public static void writeOnFile(String toWrite){
		
		File fileText = new File("Risposte-Gruppo7.txt");
		try {

			if(!fileText.exists())fileText.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(fileText));
			out.write("GRUPPO <7> \n" + "Componenti: <E. Fratus> <C. Bonomini> \n\n");
			out.write(toWrite);
			out.flush();
			out.close();

		} catch(Exception ex){
			ex.printStackTrace();;
		}
	}
}




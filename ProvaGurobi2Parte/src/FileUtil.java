import java.io.*;
import java.util.Scanner;

public class FileUtil {

	private static int[][] tsp = new int[1][1];
	private static int[][] x = new int[1][1];

	public static void parsing() {				
		//tsp ha tutti i costi, x ha se esiste un percorso o no 
		// parsing
		try {
			//nel file tsp sono indicati i  collegamenti tra i 48 nodi e il relativo costo
			//la prima cifra indica il nodo di partenza, la seconda quello di destinazione e l'ultima il costo
			File myFile = new File("tsp.txt");
			Scanner reader = new Scanner(myFile);
			while (reader.hasNextLine()) {
				//split mi divide una stringa in un array di stringhe quando trova il carattere passato
				//divide nodo di partenza, di destinazione e costo
				String data[] = reader.nextLine().split(" ");
				if (data.length == 2) {
					//parseInt mi trasforma il carattere contenuto nella substringa in un intero
					tsp = new int[Integer.parseInt(data[1])][Integer.parseInt(data[1])];
					x = new int[Integer.parseInt(data[1])][Integer.parseInt(data[1])];
					for (int i = 0; i < Integer.parseInt(data[1]); i++) {
						for (int j = 0; j < Integer.parseInt(data[1]); j++) {
							tsp[i][j] = 0;
							x[i][j] = 0;
						}
					}

				} else {
					int i = Integer.parseInt(data[0]);
					int j = Integer.parseInt(data[1]);
					int len = Integer.parseInt(data[2]);
					tsp[i][j] = len; //per simm tsp j i = len
					tsp[j][i]= len;
					x[j][i]=1;
					x[i][j] = 1;

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




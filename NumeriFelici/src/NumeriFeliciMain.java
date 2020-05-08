import java.util.HashMap;

public class NumeriFeliciMain {

	public static boolean isPrimo(int n, HashMap<Integer, Boolean> lista) {
		
		boolean clone = lista.containsKey(n);
		if(clone) return false;
		else {
			lista.put(n,clone);
			if(n == 1) return true;
			n = quadratoCifre(n);
			return(isPrimo(n, lista));
		}
	}
	
	public static int quadratoCifre(int n) {
		
		int i = 10, cifra, quadrato = 0;
		while(n != 0) {
			cifra = n % i;
			quadrato += cifra*cifra;
			n = n / i;			
		}
		return quadrato;
	}
	
	public static HashMap<Integer, Boolean> listaFelici(int n){
		HashMap<Integer, Boolean> lista = new HashMap<Integer, Boolean>();
		for(int i=n; i>0; i--) {
			lista.put(i, (isPrimo(i, new HashMap<Integer, Boolean>())));
		}
		return lista;
	}
	
	public static void main(String[] args) {
		int n = 57;
		System.out.println("felice? " + isPrimo(n, new HashMap<Integer, Boolean>()));
		System.out.println(listaFelici(n));
		

	}

}

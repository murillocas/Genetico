package genetico;

public class Genetico {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int x = 0 ; x<10 ;x++) {
			System.err.print("Popula��o  " + x+ ": ");
			Genes genes = new Genes(100,50,0.1,0.25);
		}
		

	}

}

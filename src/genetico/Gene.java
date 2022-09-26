package genetico;

public class Gene {
	double genoma[];
	
	double fitness;
	double F_objetivo;
	double probabilidade;
	public double getProbabilidade() {
		return probabilidade;
	}
	public double getF_objetivo() {
		return F_objetivo;
	}
	public void setF_objetivo(double f_objetivo) {
		F_objetivo = f_objetivo;
	}
	public void setProbabilidade(double probabilidade) {
		this.probabilidade = probabilidade;
	}
	Gene(double genoma[]){
		this.genoma = genoma;
	}
	public double[] getGenoma() {
		return genoma;
	}
	public void setGenoma(double[] genoma) {
		this.genoma = genoma;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public void funcao_Objetivo() {
		double aux=0;
		//Avaliação  Evaluation 1*a + 2*b + 3*c + 4*d
		for(int x=0;x<genoma.length;x++) {
			aux += genoma[x] * (x+1);
		}
		if(aux-30 < 0) {
			F_objetivo = (aux-30) * -1;
		}else {
			F_objetivo = aux-30;
		}
		
	}
	public double calc_fitness() {
		/* 1/(1+f_obj)
		 * cromossomo mais apto
		 * a melhor aptidao do cromossomo*/
		fitness = 1 /(F_objetivo+1);
		return fitness;
	}
	public void calc_Probabilidade(double total) {
		//probabilidade do cromossomo 
		probabilidade = fitness / total;
	}
	public void mutar(int posi,double valor) {
		genoma[posi] = valor;
	}
	public double mostrarResultado() {
		double aux=0;
		//Avaliação  Evaluation 1*a + 2*b + 3*c + 4*d
		for(int x=0;x<genoma.length;x++) {
			System.out.print(genoma[x] + "__");
			aux += genoma[x] * (x+1);
		}
		return aux;
	}
	

}

package genetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genes {
	double a ;
	ArrayList<Gene> lista_gene = new ArrayList<>();
	int tamanho_populacao;
	int geracoes;
	double taxa_mutacao;
	double taxa_crusamento;
	
	Random rand = new Random();
	
	Genes(int tamnaho_populacao,int geracoes,double taxa_mutacao,double taxa_crusamento){
		this.tamanho_populacao = tamnaho_populacao;
		this.geracoes = geracoes;
		this.taxa_crusamento = taxa_crusamento;
		this.taxa_mutacao = taxa_mutacao;
		iniciar_Genes();
		controlar_Geracoes();
	}
	
public void iniciar_Genes() {
		//System.out.println("iniciar genes ");

		for(int x=0;x < tamanho_populacao ; x++) {
			double[] genoma = new double[4];
			genoma[0] = rand.nextInt(30);
			genoma[1] = rand.nextInt(30);
			genoma[2] = rand.nextInt(30);
			genoma[3] = rand.nextInt(30);
			Gene gene = new Gene(genoma);
			lista_gene.add(gene);
		}
		
	}
	public void controlar_Geracoes() {
		for(int x=0 ;x< geracoes ;x++) {
			//System.out.println("geração " + x);
			
			funcao_objetivo();
			if(melhor_Individuo() == 0) {
				
				break;
			}
			ordenarPorProbabilidade();
			calcular_probablidade_acumulativa();
			selecionar_genes();
			cross_over();
			mutacao();
			
		}
		
	}
public void funcao_objetivo() {
	//System.out.println("funcao objetivo" );

		double total =0;
		// calcula a função objetiva (1*a + 2*b + 3*c + 4*d)
		// calcula o fitness 1/(1+f_obj) 
		for(int x=0 ;x< lista_gene.size();x++) {
			lista_gene.get(x).funcao_Objetivo();
			total += lista_gene.get(x).calc_fitness(); 
		}
		// calcula a probabilidade de cada cromossomo (fitness / total_aptidao)
		for(int x=0 ;x< lista_gene.size();x++) {
			lista_gene.get(x).calc_Probabilidade(total);
		}
		
}

	public void ordenarPorProbabilidade() {
		System.out.println("ordenar" );
		// selection sort ordenando pela probabilidade
		double aux;
		int posi;
		for(int i=0 ; i<lista_gene.size() ; i++){
            aux = lista_gene.get(i).getProbabilidade();
            posi = i ;
            for(int j=i+1 ; j < lista_gene.size() ; j++){
                if(aux > lista_gene.get(j).getProbabilidade()){
                    aux = lista_gene.get(j).getProbabilidade();
                    posi = j;
                }
            }
            if(i != posi){
            		Collections.swap(lista_gene, posi, i);                   
            }
    }
	}
	public void calcular_probablidade_acumulativa() {
		System.out.println("probabilidade acumulativa" );
		//probabilidade do atual + o anterior
		for(int x=1 ;x< lista_gene.size();x++) {
			double aux;
			aux = lista_gene.get(x-1).probabilidade;
			aux += lista_gene.get(x).probabilidade;
			lista_gene.get(x).setProbabilidade(aux);
			
		}
	}
	public void selecionar_genes() {
		//System.out.println("selecionar genes" );
		/*
		 * selecionar os cromossomos para a proxima geração
		 */		
		ArrayList<Gene> new_Genes = new ArrayList<>();
		double aux;
		for(int x=0 ;x< tamanho_populacao;x++) {
			 aux = rand.nextDouble();
			 for(int y=0 ;y< tamanho_populacao;y++) {
				 if(aux < lista_gene.get(y).getProbabilidade() ){
					 new_Genes.add(lista_gene.get(y));
					 break;
				 }
			 }
		}
		
		for(int x=0;x< tamanho_populacao;x++) {
			lista_gene.get(x).setGenoma(new_Genes.get(x).getGenoma());
			lista_gene.get(x).setFitness(new_Genes.get(x).getFitness());
			lista_gene.get(x).setProbabilidade(new_Genes.get(x).getProbabilidade());
			lista_gene.get(x).setF_objetivo(new_Genes.get(x).getF_objetivo());


		}
		
	}
	
	public void cross_over() {
		//System.out.println("cross over" );
		ArrayList<Integer> lista_CrossOver = new ArrayList<>();
		ArrayList<Integer> ponto_CrossOver = new ArrayList<>();

		//seleção dos pais
		for(int x=0;x<lista_gene.size();x++) {
			double aux = rand.nextDouble();
			if(aux < taxa_mutacao) {
				lista_CrossOver.add(x);
			}
		}
		//ponto de crusamneto entre e 1 e (comprimento do cromossomo – 1).
		
		for(int x=0;x<lista_CrossOver.size();x++) {
			ponto_CrossOver.add(rand.nextInt(3));
		}
		for(int x=0;x<lista_CrossOver.size()-1;x++) {
			double[] aux1 = lista_gene.get(x).getGenoma();
			double[] aux2 = lista_gene.get(x+1).getGenoma();
			double[] newGene = new double[4];
			int y=0;
			for(y=0;y< ponto_CrossOver.get(x);y++) {
				//System.out.println("valor y : " +y +" valor x :" +x);
				newGene[y] = aux1[y];
			}
			for(;y< 4;y++) {
				///System.out.println("segubndo valor y : " +y  +"valor x : " + x);
				newGene[y] = aux2[y];
			}
			lista_gene.get(x).setGenoma(newGene);
		}
		
	}
	public void mutacao(){
		//System.out.println("mutacao" );
		int total_gen = 4 * lista_gene.size();
		int pros_mutacao = rand.nextInt(total_gen-1)+1;
		int gene_alteracao;
		int localalteracao;
		int numero_Mutacoes = (int) (taxa_mutacao * total_gen);
	
		for(int x=0;x<numero_Mutacoes;x++) {
			gene_alteracao = rand.nextInt(total_gen);
			localalteracao = (int)gene_alteracao/4;
			
			lista_gene.get(localalteracao).mutar(gene_alteracao%4, rand.nextInt(30));
			
			
		}
	}
	
	public double melhor_Individuo() {
		double melhor_Objetivo = Double.MAX_VALUE;
		int posi_melhor =0;
		for(int x=0 ; x< tamanho_populacao;x++) {
			if(melhor_Objetivo > lista_gene.get(x).getF_objetivo()) {
				melhor_Objetivo = lista_gene.get(x).getF_objetivo();
				posi_melhor = x;
			}
			
		}
		System.out.println("o melhor valor e " + lista_gene.get(posi_melhor).mostrarResultado());
		System.out.println("o melhor valor objetivo e " + melhor_Objetivo + "\n na posição : " + posi_melhor);
		return  melhor_Objetivo;
	}
}



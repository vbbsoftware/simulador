package ad.simulador;

import umontreal.iro.lecuyer.probdist.StudentDist;

public class Estatistica {

	//classe destinada a calcular:
	// * media amostral
	// * variancia amostral
	// * desvio padrao
	// * intervalo de confianca
	
	// essa classe foi criada com base no
	// capitulo 16 da apostila.
	
	// usamos aqui o pacote de distribuicoes
	// probabilisticas da universidade de
	// montreal para obter os percentis
	// da t-Student.
	
	private int n; // numero de elementos na amostra
	
	private double somatorioX_i; // soma acumulada dos elementos X_i
	private double somatorioX_iAoQuadrado; // soma acumulada dos quadrados elementos X_i

	public Estatistica() {
		// inicializa um estimador,
		// com valores iniciais zerados.
		n = 0;
		somatorioX_i = 0;
		somatorioX_iAoQuadrado = 0;
	}

	public double calculaMediaAmostral() {
		// retorna a media amostral, que
		// eh simplesmente a soma dos elementos X_i
		// coletados sobre o numero n de elementos.
		return somatorioX_i / n;
	}

	public double calculaVarianciaAmostral() {
		// retorna a variancia amostral, que
		// eh a soma dos quadrados dos elementos X_i
		// coletados sobre o numero n de elementos menos 1.
		if (n == 0 || n == 1) return 0; // precisamos de mais de 1 elemento para ter variancia.
		else {
			// cria uma variavel para armazenar
			// o quadrado do somatorio de X_i,
			// usado na formula de variancia amostral.
			// Note que o quadrado do somatorio eh
			// diferente do somatorio dos quadrados.
			double quadradoDoSomatorioX_i;
			quadradoDoSomatorioX_i = Math.pow(somatorioX_i, 2);
			
			// formula da variancia:
			return (somatorioX_iAoQuadrado / (n - 1)) - (quadradoDoSomatorioX_i / ((n - 1) * n));
		} 
	}

	public double calculaIntervaloDeConfianca(double confianca) {
		// essa funcao calcula a distancia do intervalo,
		// a ser somado e subtraido da media,
		// com o nivel de confianca sendo
		// passado como parametro.
		
		double a; //usado para representar o alfa.
		a = 1 - confianca; // auto-explicativo.
		
		// Verifica qual eh o percentil
		// da t-Student com 1-a/2 de confianca
		// e n-1 graus de liberdade.
		// Chamamos esse percentil de t:
		double t = StudentDist.inverseF(n - 1, 1 - a/2);

		// Definicao de intervalo de confianca:
		// IC = mediaAmostral +- t_(1-a/2; n-1) * desvioPadraoAmostral / sqrt(n);
		// onde desvioPadraoAmostral = sqrt(varianciaAmostral / n);
		
		// Aplicacao desse resultado:
		return t * Math.sqrt(calculaVarianciaAmostral() / n);
	}
	
	public double calculaLimiteSuperiorIntervaloDeConfianca(double confianca) {
		// o nome dessa funcao eh auto-explicativo;
		return calculaMediaAmostral() + calculaIntervaloDeConfianca(confianca);
	}

	public double calculaLimiteInferiorIntervaloDeConfianca(double confianca) {
		// o nome dessa funcao eh auto-explicativo;
		return calculaMediaAmostral() - calculaIntervaloDeConfianca(confianca);
	}
	
	public void imprimeIntervaloDeConfianca(double confianca){
		// funcao apenas para informar no console o resultado
		// dos intervalos de confianca:
		
		// Upper (Limite Superior)
		System.out.println("U: " + calculaLimiteInferiorIntervaloDeConfianca(confianca));
		
		// Lower (Limite Inferior)
		System.out.println("L: " + calculaLimiteSuperiorIntervaloDeConfianca(confianca));
	}
	
	public void insereElemento(double valor) {
		// essa funcao insere um elemento i.i.d
		// dentro da amostra que estamos que estamos
		// analisando, ou seja, ela
		// acumula o valor do elemento nos
		// somatorios e atualiza o numero de
		// amostras.
		somatorioX_i += valor;
		somatorioX_iAoQuadrado += Math.pow(valor,2);
		n = n+1;
	}

	public void coletarAmostra(double tempo){};
	

}
package ad.simulador;

public class PoliticaRED extends Roteador {

	// Essa classe eh responsavel pela politica
	// RED do roteador. Implementamos o algoritmo
	// baseado na especificacao do enunciado do 
	// trabalho.
	
	
	// os nomes das variaveis
	// estao iguais a descricao
	// do algoritmo RED no 
	// enunciado sempre que possivel.
	private double avg;
	private int minth;
	private int maxth;
	private int count;
	private double max_p;
	private double w_q;

	// parametros da simulacao:
	private int mss;
	private double cs;
	
	// Variaveis e instancias
	// de objetos adicionais para
	// o funcionamento dessa classe:
	private GeradorDeNumerosAleatorios geradorDeNumerosAleatorios;
	private Estatistica estatisticas;
	private double inicioPeriodoOcioso;
	private double fimPeriodoOcioso;
	private long pacotesPeriodoOcioso;

	public PoliticaRED() {
		// cria as estruturas para inicializar
		// o RED.
		
		// Precisamos de um gerador de
		// numeros aleatorios.
		geradorDeNumerosAleatorios = new GeradorDeNumerosAleatorios();
		
		// Valor das Variaveis (dados no enunciado)
		mss = 1500;
		cs = 10000000;

		// Valores recomendados no enunciado:
		minth = 5;
		maxth = 15;
		max_p = (double) 1 / 50;
		w_q = 0.002;

		// Inicializacao:
		avg = 0;
		count = 0;
		pacotesPeriodoOcioso = 0;

		// Estimando o periodo ocioso:
		estatisticas = new Estatistica();
		inicioPeriodoOcioso = 0;
	}

	private void atualizarAvg() {
		if (getNumeroPacotes() > 0) {
			avg = (1 - w_q) * avg + w_q * getNumeroPacotes();
		} else {
			avg = Math.pow(1 - w_q, pacotesPeriodoOcioso) * avg;
		}
	}
	
	private void atualizarPacotesPeriodoOcioso(double tempoAtualSimulado) {
		if (getNumeroPacotes() == 0) {
			fimPeriodoOcioso = tempoAtualSimulado;
			estatisticas.coletarAmostra(fimPeriodoOcioso - inicioPeriodoOcioso);

			double vazao = mss / cs;
			pacotesPeriodoOcioso = Math.round(estatisticas.calculaMediaAmostral() / vazao);
		}
	}
	
	public SelectiveACK enviarProximoPacote(double tempoAtualSimulado) {
		SelectiveACK sack = super.enviarProximoPacote(tempoAtualSimulado);
		if (getNumeroPacotes() == 0) {
			// se nao ha pacotes para enviar, inicia
			// o periodo ocioso.
			inicioPeriodoOcioso = tempoAtualSimulado;
		}
		return sack;
	}
	
	@Override
	public boolean recebeuPacoteComSucesso(Pacote p, double tempo) {
		atualizarAvg();
		atualizarPacotesPeriodoOcioso(tempo);

		if (buffer.size() == getTamanhoBuffer()) {
			count = 0;
			return false;
		}

		// Algoritmo RED de acordo com o enunciado:
		if (avg < minth) {
			// tem espaco, coloca no buffer
			buffer.add(p);
			count = count + 1;
			return true;
		} else if (avg > maxth) {
			// buffer cheio, nao consegue incluir
			count = 0;
			return false;
		} else {
			// descarte previo aleatorio:
			if (geradorDeNumerosAleatorios.nextDouble() < getP_a()) {
				//descarta:
				count = 0;
				return false;
			} else {
				//nao descarta:
				buffer.add(p);
				count = count + 1;
				return true;
			}
		}
	}

	public int getMaxth() {
		return maxth;
	}

	public int getMinth() {
		return minth;
	}

	public double getAvg() {
		return avg;
	}

	private double getP_a() {

		return getP_b() / (1 - count * getP_b());
	}

	private double getP_b() {
		return max_p * (avg - minth) / (maxth - minth);
	}

	public int getMss() {
		return mss;
	}

	public void setMss(int mss) {
		this.mss = mss;
	}

	public double getTaxaEnlaceDeSaida() {
		return cs;
	}

	public void setTaxaEnlaceDeSaida(double taxaEnlaceDeSaida) {
		this.cs = taxaEnlaceDeSaida;
	}

}
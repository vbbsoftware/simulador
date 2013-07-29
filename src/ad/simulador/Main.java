package ad.simulador;



public class Main {
	// Esta classe possui a funcao
	// main deste programa,
	// isto eh, ela eh a classe
	// que inicializa o programa
	// e pede os valores dos parametros
	
	@SuppressWarnings("unused")
	public static void main(String args[]){
		// instancia um console para capturar o que foi digitado
		// no teclado pelo usuario.
		Console console = new Console();
		
		// instancia as variaveis:
		long cg; // taxa do enlace de saida do gargado
		long cs; // taxa do enlace de saida dos servidores
		long tp1; // tempo de propagacao do grupo 1
		long tp2; // tempo de propagacao do grupo 2
		long g1; // numero de estacoes do grupo 1
		long g2; // numero de estacoes do grupo 2
		long tamanhoMedioRajadas;
		long intervaloEntreRajadas;
		long tamanhoBuffer;
		int politicaFila;
		
		//le os parametros de entrada
		
		
		System.out.println("Informe Cg (taxa do enlace de saida do gargalo em bps):");		
		cg = console.readLong();
		System.out.println("Informe Cs (taxa do enlace de saída dos servidores em bps):");
		cs = console.readLong();
		System.out.println("Informe TP1 (atraso de propagação para o grupo 1):");
		tp1 = console.readLong();
		System.out.println("Informe TP2 (atraso de propagação para o grupo 2):");
		tp2 = console.readLong();
		System.out.println("Informe o numero de estacoes do grupo 1:");
		g1 = console.readLong();
		System.out.println("Informe o numero de estacoes do grupo 2:");
		g2 = console.readLong();
		System.out.println("Informe o tamanho medio das rajadas do trafego de fundo:");
		tamanhoMedioRajadas = console.readLong();
		System.out.println("Informe o intervalo entre rajadas do trafego de fundo em ms:");
		intervaloEntreRajadas = console.readLong();
		System.out.println("Informe o tamanho do buffer, em numero de pacotes:");
		tamanhoBuffer = console.readLong();
		
		System.out.println("Deseja usar (1) FIFO ou (2) FIFO RED esta simulacao?");
		System.out.println("Digite 1 para FIFO ou 2 para RED:");
		politicaFila = console.readInt();
		
				
	}
	
}

package ad.simulador;

public class RecebimentoNoRoteadorDeTrafegoDeFundo extends Evento{

	public RecebimentoNoRoteadorDeTrafegoDeFundo(double tempo) {
		super(tempo);
		// TODO Auto-generated constructor stub
	}
	// Esta classe eh um subtipo de evento e por
	// isso extende a classe Evento.
	
	// Ela representa o momento em que um trafego
	// de fundo chega ao roteador.
	
	// Note que nao eh necessario recriar os
	// metodos para obter o tempo e comparar
	// com outro evento visto que isso ja esta
	// implementado na classe pai desta classe.
	
}

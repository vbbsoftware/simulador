package ad.simulador;

public class Evento implements Comparable<Evento> {
	// Esta classe representa
	// um evento que esta na lista
	// de eventos do programa e 
	// que sera tratado quando
	// chegar a sua vez.
	
	// Os subtipos de eventos estao
	// em outras classes que funcionam
	// como extensao desta aqui.
	
	// A informacao de maior importancia
	// sobre um evento eh o tempo em
	// que ele ocorrer, que esta na
	// variavel tempo.
	private final double tempo;
	
	public double informaTempo() {
		// Esta funcao informa o tempo
		// em que ocorreu esta instancia
		// do objeto Evento.
		return tempo;
	}
	
	@Override
	public int compareTo(Evento arg0) {
		// Precisamos ter a opcao de comparar 
		// se um evento ocorre antes ou depois
		// de outro evento da lista.
		if (this.informaTempo() < arg0.informaTempo()) {
			return -1; // valores negativos indicam que eh menor
		} else if (this.informaTempo() > arg0.informaTempo()) {
			return 1; // valores positivos indicam que eh maior
		}
		// se nao for nenhum nem outro,
		// eh igual (valor zero):
		return 0;	
	}
	public Evento(double tempo) {
		// Cria o Evento com o tempo passado
		// como parametro.
		this.tempo = tempo;
	}
}
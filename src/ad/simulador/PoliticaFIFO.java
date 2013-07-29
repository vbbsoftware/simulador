package ad.simulador;

public class PoliticaFIFO extends Roteador {

	// Essa classe representa a fila com politica
	// FIFO.
	
	@Override
	public boolean recebeuPacoteComSucesso(Pacote pacote, double tempo) {
		// Essa abstracao do recebimento de pacote
		// apenas ve se tem espaco no buffer para
		// recebe-lo. Se houver, retorna true,
		// senao, retorna false.
		if (buffer.size() < getTamanhoBuffer()) {
			buffer.add(pacote);
			return true;
		}
		return false;
	}

}
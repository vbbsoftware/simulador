package ad.simulador;

public class Pacote {
	// Esta classe eh uma abstracao dos Pacotes
	// a serem enviados na rede.
	
	private long numeroDeSequenciaDoPrimeiroByte;
	private long numeroDeSequenciaDoUltimoByte;

	// destinatario num valor de 0...n.
	private int destinatario;

	public Pacote() {
		this.numeroDeSequenciaDoPrimeiroByte = 0;
		this.numeroDeSequenciaDoUltimoByte = 0;
		// quando um pacote nao tiver
		// destinatario definido,
		// o representaremos
		// com valor menor do que zero,
		// pois valores maiores do que
		// zero representam os pares
		// de servidores/estacoes.
		this.destinatario = -1;
	}

	
	public void setDestino(int destino) {
		this.destinatario = destino;
	}

	public int getDestinatario() {
		return this.destinatario;

	}

	public long getByteInicial() {
		return numeroDeSequenciaDoPrimeiroByte;
	}

	public void defineBytes(long byteInicial, long byteFinal){
		this.numeroDeSequenciaDoPrimeiroByte = byteInicial;
		this.numeroDeSequenciaDoUltimoByte = byteFinal;
	}

	public long getByteFinal() {
		return numeroDeSequenciaDoUltimoByte;
	}

	public boolean isTrafegoDeFundo() {
		if (destinatario >= 0) return false;
		else return true;
	}

	public long getTamanho() {
		return numeroDeSequenciaDoUltimoByte - numeroDeSequenciaDoPrimeiroByte + 1;
	}
	
}

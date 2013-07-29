package ad.simulador;

import java.util.ArrayList;

public abstract class Roteador {
	// Abstracao do roteador,
	// com buffer e envio de Acks.
	// Extensoes dessa classe
	// implementam as politicas
	// RED e FIFO.
	
	private int tamanhoBuffer;
	protected ArrayList<Pacote> buffer;
	private Receptor[] receptores; // lista de RxTCP conectados ao roteador

	public Roteador() {
		tamanhoBuffer = 0;
		buffer = new ArrayList<Pacote>();
	}
	
	// nessa abstracao, nao precisamos
	// nos preocupar com a politica que sera
	// usada. Apenas nas extensoes dessa classe
	// que esses metodos serao tratados com mais
	// detalhes.
	public abstract boolean recebeuPacoteComSucesso(Pacote p, double tempo);

	public boolean receberPacote(Pacote p) {
		return recebeuPacoteComSucesso(p, 0);
	}

	public SelectiveACK enviarProximoPacote(double tempo) {
		Pacote p = getProximoPacoteAEnviar();
		SelectiveACK sack = null;
		if (p.getDestinatario() >= 0) {
			// Determina o destinatario
			sack = receptores[p.getDestinatario()].receberPacote(p);
		}
		buffer.remove(p);
		return sack;
	}

	public void enviarProximoPacote() {
		enviarProximoPacote(0);
	}

	public Pacote getProximoPacoteAEnviar() {
		return buffer.get(0);
	}

	public int getNumeroPacotes() {
		return buffer.size();
	}

	public int getTamanhoBuffer() {
		return tamanhoBuffer;
	}

	public void setTamanhoBuffer(int tamanhoBuffer) {
		this.tamanhoBuffer = tamanhoBuffer;
	}

	public Receptor[] getReceptores() {
		return receptores;
	}

	public void setReceptores(Receptor[] receptores) {
		this.receptores = receptores;
	}
}
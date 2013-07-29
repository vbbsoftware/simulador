package ad.simulador;

public class SelectiveACK {

	// Essa classe eh responsavel pelo funcionamento
	// do Selective ACK.
	
	// Os metodos dessa classe servem para informar
	// qual eh o valor do ultimo ACK recebido, que
	// representa o valor do proximo pacote esperado;
	// alem de guardar um buffer dos pacotes
	// que foram recebidos fora de ordem.
	
	private long[][] vetorItensRecebidosComSucesso;
	private long itemEsperadoNoMomento;
	
	private int destinoAck; //para onde o ACK deve ser enviado

	// construtor informa o destino do Ack
	// e qual eh o numero da sequencia
	// esperada atualmente.
	
	// temos duas possibilidades de construtor:
	// esta abaixo, onde ja recebemos pacotes
	// com sucesso (e estao em buffer)...
	public SelectiveACK(int destinoAck, long itemEsperadoNoMomento, long[][] vetorItensRecebidosComSucesso) {
		this.itemEsperadoNoMomento = itemEsperadoNoMomento;
		this.vetorItensRecebidosComSucesso = vetorItensRecebidosComSucesso;
		this.destinoAck = destinoAck;
	}

	// e esta, onde o buffer esta vazio.
	public SelectiveACK(int destinoAck, long itemEsperadoNoMomento) {
		this.itemEsperadoNoMomento = itemEsperadoNoMomento;
		this.vetorItensRecebidosComSucesso = null; // nulo pois esta vazio
		this.destinoAck = destinoAck;
	}

	public long informaUltimoAck() {
		// informa o valor do ultimo ACK recebido.
		return itemEsperadoNoMomento;
	}

	public long[][] obterVetorItensRecebidosComSucesso() {
		// nome auto-explicativo;
		return vetorItensRecebidosComSucesso;
	}

	public boolean equals(Object arg0) {
		if (arg0 instanceof SelectiveACK) {

			SelectiveACK osack = (SelectiveACK) arg0;

			if (osack.informaDestinoAck() != this.informaDestinoAck()) {
				return false;
			}

			// Se o próximo byte esperado for diferente, então os objetos são
			// diferentes.
			if (osack.informaUltimoAck() != this.informaUltimoAck()) {
				return false;
			}

			// Toma NullPointer!
			if (osack.obterVetorItensRecebidosComSucesso() == null
					|| this.vetorItensRecebidosComSucesso == null) {

				if (osack.obterVetorItensRecebidosComSucesso() == null
						&& this.vetorItensRecebidosComSucesso == null) {
					return true;
				}

				/*
				 * Se eles tem o mesmo destino, e o mesmo próximo byte esperado,
				 * e um deles tem um vetor de sequência de tamanho 0 e o outro é
				 * nulo, então eles também são iguais.
				 */
				if ((osack.obterVetorItensRecebidosComSucesso() == null && this.vetorItensRecebidosComSucesso.length == 0)
						|| (this.vetorItensRecebidosComSucesso == null && osack
								.obterVetorItensRecebidosComSucesso().length == 0)) {
					return true;
				} else {
					return false;
				}
			}

			/*
			 * Se o vetor de sequência tiver tamanho diferente, então os objetos
			 * são diferentes.
			 */
			if (vetorItensRecebidosComSucesso.length != osack
					.obterVetorItensRecebidosComSucesso().length) {
				return false;
			}

			// Agora eu sei que o próximo byte esperado e o tamanho do vetor de
			// sequência são compatíveis. Resta testar o conteúdo linha a linha.
			for (int i = 0; i < vetorItensRecebidosComSucesso.length; i++) {

				// Se o tamanho da linha for diferente, então os objetos são
				// diferentes.
				if (vetorItensRecebidosComSucesso[i].length != osack
						.obterVetorItensRecebidosComSucesso()[i].length) {
					return false;
				}

				for (int j = 0; j < vetorItensRecebidosComSucesso[i].length; j++) {

					// Se o conteúdo dos vetores forem incompatíveis, então são
					// objetos diferentes.
					if (vetorItensRecebidosComSucesso[i][j] != osack
							.obterVetorItensRecebidosComSucesso()[i][j]) {
						return false;
					}
				}

			}
			// Depois de todos esses testes, se não retornou false, então os
			// objetos são iguais.
			return true;
		}

		// Se não são instâncias de SACK, não há conversa!
		return false;
	}
	
	public int informaDestinoAck() {
		return destinoAck;
	}

	public void setDestinoAck(int destinoAck) {
		this.destinoAck = destinoAck;
	}
}

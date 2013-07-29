package ad.simulador;

import java.util.ArrayList;

	public class Receptor {
		// Essa classe representa um
		// Receptor dentro de nossa rede.
		
		// A parte mais complexa dessa classe
		// eh gerenciar o envio de ACKs.
		
		
		// Valor do ultimo ACK
		private long byteAguardado;

		// Sequencias fora de ordem recebidas
		private ArrayList<long[]> recebidoEmBuffer;

		public Receptor() {
			byteAguardado = 0;
			recebidoEmBuffer = new ArrayList<long[]>();
		}

		public SelectiveACK receberPacote(Pacote p) {
			// recebe o pacote e retorna
			// um ACK referente ao mesmo.
			if (p.getByteInicial() < this.byteAguardado) {			
				return new SelectiveACK(p.getDestinatario(), this.byteAguardado, this.getArrayDeSequenciaParaMatriz());
			}
			else if(p.getByteInicial() == this.byteAguardado){
				// sequencia na ordem, OK
				this.atualizaBuffer(p);
				long[] sequencia = this.recebidoEmBuffer.get(0);

				if(sequencia.length >= 2){
					this.byteAguardado = sequencia[1];
				}
				// remove o que esta no buffer
				// pois pacote foi tratado. 
				this.recebidoEmBuffer.remove(0);
				
				// ack do proximo:
				SelectiveACK proximo = new SelectiveACK(p.getDestinatario(), this.byteAguardado, this.getArrayDeSequenciaParaMatriz());
				return proximo;
			}
			else
			{
				this.atualizaBuffer(p);
				SelectiveACK proximo = new SelectiveACK(p.getDestinatario(), this.byteAguardado, this.getArrayDeSequenciaParaMatriz());
				return proximo;
			}
		}

		private long[][] getArrayDeSequenciaParaMatriz(){

			if(this.recebidoEmBuffer.size() == 0 || this.recebidoEmBuffer == null){
				return null;
			}

			long[][] sequenciasSack = new long[this.recebidoEmBuffer.size()][2];

			for(int i = 0; i<sequenciasSack.length; i++){
				sequenciasSack[i][0] = this.recebidoEmBuffer.get(i)[0];
				sequenciasSack[i][1] = this.recebidoEmBuffer.get(i)[1];
			}

			return sequenciasSack;
		}

		private void atualizaBuffer(Pacote p) {
			// Esta funcao tem como objetivo
			// verificar se ja ha alguma sequencia
			// completa que pode ser removida
			// do buffer, dando espaco
			// para novos elementos.
			long limites[] = new long[2];

			limites[0] = p.getByteInicial();
			limites[1] = p.getByteFinal() + 1;

			if (this.recebidoEmBuffer.size() == 0) {
				this.recebidoEmBuffer.add(limites);
				return;
			} else {
				if (this.recebidoEmBuffer.get(0)[0] > p.getByteFinal()) {
					if (this.recebidoEmBuffer.get(0)[0] == p.getByteFinal() + 1) {
						this.recebidoEmBuffer.get(0)[0] = p.getByteInicial();
						return;
					} else {
						this.recebidoEmBuffer.add(0, limites);
						return;
					}
				}
				for (int i = this.recebidoEmBuffer.size() - 1; i >= 0; i--) {
					if (this.pacoteContidoNaSequencia(p, i)) {
						return;
					}
					if (this.recebidoEmBuffer.get(i)[1] <= p.getByteInicial()) {
						this.insereNaSequencia(p, i);
						return;
					}
				}
			}

		}

		private void insereNaSequencia(Pacote p, int i) {
			if (this.recebidoEmBuffer.get(i)[1] == p.getByteInicial()) {
				this.recebidoEmBuffer.get(i)[1] = p.getByteFinal() + 1;

				if (i < this.recebidoEmBuffer.size() - 1) {
					if (this.recebidoEmBuffer.get(i)[1] == this.recebidoEmBuffer.get(i + 1)[0]) {
						this.recebidoEmBuffer.get(i)[1] = this.recebidoEmBuffer.get(i + 1)[1];
						this.recebidoEmBuffer.remove(i + 1);
					}
				}
			} else {
				if (i < this.recebidoEmBuffer.size() - 1
						&& this.recebidoEmBuffer.get(i + 1)[0] == p.getByteFinal() + 1) {
					this.recebidoEmBuffer.get(i + 1)[0] = p.getByteInicial();
				} else {
					long[] novaSequencia = new long[2];
					novaSequencia[0] = p.getByteInicial();
					novaSequencia[1] = p.getByteFinal() + 1;
					this.recebidoEmBuffer.add(i + 1, novaSequencia);
				}
			}
		}

		private boolean pacoteContidoNaSequencia(Pacote p, int sequencia) {
			return this.recebidoEmBuffer.get(sequencia)[0] <= p.getByteInicial()
					&& this.recebidoEmBuffer.get(sequencia)[1] > p.getByteFinal();
		}
	}


package ad.simulador;

public class GeradorDeNumerosAleatorios extends java.util.Random{
	// essa classe gera numeros aleatorios
	// distribuidos de maneira geometrica
	// ou exponencial.
	
	// Nos baseamos na classe Random do java,
	// que gera numeros aleatorios, e adaptamos
	// para que eles sejam distribuidos
	// das maneiras que nos interessam.
	
	// Note que a funcao "log" do java retorna
	// diretamente o ln.
	
	private static final long serialVersionUID = 1L; // frescura do Eclipse

	public GeradorDeNumerosAleatorios(long semente) {
		// informa ao java qual a semente
		// usada para gerar numeros aleatorios.
		// Obs: Recurso opcional, se nao passar
		// esse parametro, o Java escolhe
		// sozinho.
		super(semente);
	}
	
	public GeradorDeNumerosAleatorios() {
		// construtor padrao.
		super();
	}
	
	
	public double geraNumeroDistribuidoGeometricamente(double p) {
		// o titulo dessa funcao eh auto-explicativo.
		
		// u_0 sera um numero aleatorio distribuido uniformemente
		// entre [0;1].
		double u_0;
		u_0 = nextDouble();
		
		double numeroGerado;
		
		// de acordo com a apostila, cap 11,
		// a inversa da geometrica eh dada por:		
		numeroGerado = Math.log(1 - u_0) / Math.log(1 - p);

		// Como esse valor eh double e estamos
		// interessados numa distribuicao discreta,
		// temos que arredondar o valor obtido,
		// usando a funcao Math.ceil.

		return Math.ceil(numeroGerado);
	}
	
	public double geraNumeroDistribuidoExponencialmente(double lambda) {
		// o titulo dessa funcao eh auto-explicativo.
		
		// u_0 sera um numero aleatorio distribuido uniformemente
		// entre [0;1].
		double u;
		u = nextDouble(); // nœmero uniforme entre (0, 1)
			
		// de acordo com o cap 11 da apostila, para exponencial,
		// a inversa eh:
		return -(Math.log(1 - u) / lambda);
	}
}

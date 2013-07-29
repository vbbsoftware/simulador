package ad.simulador;


// Importa as bibliotecas do Java
// necessarias para fazer operacoes
// de E/S
import java.io.InputStream;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;


public class Console {
	// Classe que representa os dispositivos padrões de entrada (teclado) e saída
	// (janela onde o programa está sendo executado) de dados de um computador.
	
	// Essa classe eh necessaria pois o Java
	// nao possui um comando simples para ler
	// como o printf do C.

    /**
     * A stream de entrada padrão
     */
    private static InputStream in = System.in; // Esta linha foi alterada para evitar a sobrecarga
                                               // do sistema com várias Streams de Entrada

    /**
     * A stream de saída padrão
     */
    private static PrintStream out = System.out; // Esta linha foi alterada para evitar a sobrecarga
                                                 // do sistema com várias Streams de Entrada

    /**
     * A stream de erros padrão
     */
    private static PrintStream err = System.err; // Esta linha foi alterada para evitar a sobrecarga
                                                 // do sistema com várias Streams de Entrada

    /**
     * A stream de entrada que converte os bytes em chars
     */
    private static InputStreamReader isr = new InputStreamReader(Console.in); // Esta linha foi alterada para evitar a sobrecarga
                                                                              // do sistema com várias Streams de Entrada

    /**
     * O buffer que armazena os dados vindos da stream de entrada
     */
    private static BufferedReader br = new BufferedReader(Console.isr); // Esta linha foi alterada para evitar a sobrecarga
                                                                        // do sistema com várias Streams de Entrada

    /**
     * O construtor default da Classe Console
     */
    public Console() {}

    /*
     * Os métodos de leitura foram modificados para synchronized para criar
     * regiões de exclusão mútua que evitam que um método de leitura se
     * superponha a outro ( chamado ou não no mesmo objeto que executa aquele
     * comando ) causando erros durante a leitura no Buffer da Classe Console.
     */

    /* 
     * Método que espera que o usuário digite uma String e
     * aperte "Enter", devolvendo a String digitada como resultado
     *
     * @return    A String digitada
     */
    public synchronized String readString() {
        String ret = "";

        try {

            while (!Console.br.ready()) {} // esta linha de código foi acrescentada para dar mais segurança
                                           // contra erros de Estrada/Saída durante a leitura de uma stream
            ret = Console.br.readLine();
        } catch (java.io.IOException e) {
            Console.err.println("Ocorreu um erro de Entrada/Saida !!!");
            e.printStackTrace();
        }

        if (ret == null) { // este bloco foi acrescentado para evitar
            ret = "";      // que seja retornada uma String nula
        }

        return ret;
    }

    /**
     * Método que espera que o usuário digite um boolean e
     * aperte "Enter", devolvendo true se ele for digitado
     * e false se qualquer outra coisa seja digitada
     *
     * @return    Um valor booleano
     */
    public synchronized boolean readBoolean() {
        boolean ret = false;
        String truthstr = "false";
        truthstr = this.readString();
        Boolean truthbln = Boolean.FALSE;
        truthbln = Boolean.valueOf(truthstr);
        ret = truthbln.booleanValue();
        return ret;
    }

    /**
     * Método que espera que o usuário digite um caracter e
     * aperte "Enter", devolvendo o caracter digitado. Caso
     * o usuário digite mais de um caracter, o primeiro será
     * lido e os demais serão descartados do sistema
     *
     * @return    O caracter digitado
     */
    public synchronized char readChar() {  // este método foi alterado para ficar mais robusto
        char ret = (char) 0x0000;          // a erros de entrada e saída e mais eficiente
        String line = this.readString();

        if (line.length() > 0) {  // este bloco foi alterado para evitar
            ret = line.charAt(0); // erros quando a String lida for vazia
        } else {
            ret = '\n';
        }

        return ret;
    }

    /**
     * Método que espera que o usuário digite uma String e aperte "Enter",
     * devolvendo um array com os caracteres digitados como resultado
     *
     * @return    A String digitada como array de caracteres
     */
    public synchronized char[] readCharArray() {
        char[] ret = { (char) 0x0000 };
        String string = this.readString();
        ret = string.toCharArray();
        return ret;
    }

    /**
     * Método que espera que o usuário digite um byte e
     * aperte "Enter", devolvendo o byte digitado,
     * independentemente de ter sido digitado como:<br>
     *
     * decimal;<br>
     * octal: começando com um 0 (zero)<br>
     * hexadecimal: começando com um 0x ou um #<br>
     *
     * @return    O byte digitado
     */
    public synchronized byte readByte() {
        byte ret = 0;
        String bytestr = this.readString();
        bytestr = bytestr.trim(); // esta linha foi acrescentada para que não ocorram problemas na
                                  // leitura de um byte que tem espaços ou tabulações em torno de si
        try {
            Byte byteobj = Byte.decode(bytestr);
            ret = byteobj.byteValue();
        } catch (java.lang.NumberFormatException e) {
            Console.err.println("O valor digitado naum "
                             + "corresponde a um byte valido !!!");
            Console.err.println("O menor byte valido eh: " + Byte.MIN_VALUE);
            Console.err.println("O maior byte valido eh: " + Byte.MAX_VALUE);
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Método que espera que o usuário digite um short e
     * aperte "Enter", devolvendo o short digitado,
     * independentemente de ter sido digitado como:<br>
     *
     * decimal;<br>
     * octal: começando com um 0 (zero)<br>
     * hexadecimal: começando com um 0x ou um #<br>
     *
     * @return    O short digitado
     */
    public synchronized short readShort() {
        short ret = 0;
        String shortstr = this.readString();
        shortstr = shortstr.trim(); // esta linha foi acrescentada para que não ocorram problemas na
                                    // leitura de um short que tem espaços ou tabulações em torno de si
        try {
            Short shortobj = Short.decode(shortstr);
            ret = shortobj.shortValue();
        } catch (java.lang.NumberFormatException e) {
            Console.err.println("O valor digitado naum "
                             + "corresponde a um short valido !!!");
            Console.err.println("O menor short valido eh: " + Short.MIN_VALUE);
            Console.err.println("O maior short valido eh: " + Short.MAX_VALUE);
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Método que espera que o usuário digite um int e
     * aperte "Enter", devolvendo o int digitado,
     * independentemente de ter sido digitado como:<br>
     *
     * decimal;<br>
     * octal: começando com um 0 (zero)<br>
     * hexadecimal: começando com um 0x ou um #<br>
     *
     * @return    O int digitado
     */
    public synchronized int readInt() {
        int ret = 0;
        String intstr = this.readString();
        intstr = intstr.trim(); // esta linha foi acrescentada para que não ocorram problemas na
                                // leitura de um int que tem espaços ou tabulações em torno de si
        try {
            Integer intobj = Integer.decode(intstr);
            ret = intobj.intValue();
        } catch (java.lang.NumberFormatException e) {
            Console.err.println("O valor digitado naum "
                             + "corresponde a um int valido !!!");
            Console.err.println("O menor int valido eh: " + Integer.MIN_VALUE);
            Console.err.println("O maior int valido eh: " + Integer.MAX_VALUE);
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Método que espera que o usuário digite um long e
     * aperte "Enter", devolvendo o long digitado,
     * independentemente de ter sido digitado como:<br>
     *
     * decimal;<br>
     * octal: começando com um 0 (zero)<br>
     * hexadecimal: começando com um 0x ou um #<br>
     *
     * @return    O long digitado
     */
    public synchronized long readLong() {
        long ret = 0;
        String longstr = this.readString();
        longstr = longstr.trim(); // esta linha foi acrescentada para que não ocorram problemas na
                                  // leitura de um long que tem espaços ou tabulações em torno de si
        try { // este bloco foi alterado para que possa rodar em compiladores mais antigos (JDK 1.1.x)
            int radix = 10;
            String longstrLC = longstr.toLowerCase();

            if (longstrLC.startsWith("0x") || longstrLC.startsWith("-0x")) {
                radix = 16;
            } else if (longstrLC.startsWith("0") || longstrLC.startsWith("-0")) {
                radix = 8;
            }

            ret = Long.parseLong(longstr, radix);
        } catch (java.lang.NumberFormatException e) {
            Console.err.println("O valor digitado naum "
                             + "corresponde a um long valido !!!");
            Console.err.println("O menor long valido eh: " + Long.MIN_VALUE);
            Console.err.println("O maior long valido eh: " + Long.MAX_VALUE);
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Método que espera que o usuário digite um float
     * e aperte "Enter", devolvendo o float digitado
     *
     * @return    O byte digitado
     */
    public synchronized float readFloat() {
        float ret = 0.0f;
        String floatstr = this.readString();
        floatstr = floatstr.trim(); // esta linha foi acrescentada para que não ocorram problemas na
                                    // leitura de um float que tem espaços ou tabulações em torno de si
        try {
            Float floatobj = Float.valueOf(floatstr);
            ret = floatobj.floatValue();
        } catch (java.lang.NumberFormatException e) {
            Console.err.println("O valor digitado naum "
                             + "corresponde a um float valido !!!");
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Método que espera que o usuário digite um double
     * e aperte "Enter", devolvendo o double digitado
     *
     * @return    O byte digitado
     */
    public synchronized double readDouble() {
        double ret = 0.0;
        String doublestr = this.readString();
        doublestr = doublestr.trim(); // esta linha foi acrescentada para que não ocorram problemas na
                                      // leitura de um double que tem espaços ou tabulações em torno de si
        try {
            Double doubleobj = Double.valueOf(doublestr);
            ret = doubleobj.doubleValue();
        } catch (java.lang.NumberFormatException e) {
            Console.err.println("O valor digitado naum "
                             + "corresponde a um double valido !!!");
            e.printStackTrace();
        }

        return ret;
    }

   // A partir daqui, funcoes para
    // imprimir no console!
    
    public void println() {
        Console.out.println();
    }

    public void print(Object obj) {

        if (obj != null) {
            Console.out.print(obj);
        } else {
            Console.err.println("Voce acaba de inserir uma referencia"
                             + " nula no metodo print(Object obj)"
                             + " da classe Console !!!");
        }

    }

    public void println(Object obj) {

        if (obj != null) {
            Console.out.println(obj);
        } else {
            Console.err.println("Voce acaba de inserir uma referencia"
                             + " nula no metodo println(Object obj)"
                             + " da classe Console !!!");
        }

    }

    public void print(String str) {

        if (str != null) {
            Console.out.print(str);
        } else {
            Console.err.println("Voce acaba de inserir uma referencia"
                             + " nula no metodo print(String str)"
                             + " da classe Console !!!");
        }

    }

    public void println(String str) {

        if (str != null) {
            Console.out.println(str);
        } else {
            Console.err.println("Voce acaba de inserir uma referencia"
                             + " nula no metodo println(String str)"
                             + " da classe Console !!!");
        }

    }

    public void print(boolean bln) {
        Console.out.print(bln);
    }

    public void println(boolean bln) {
        Console.out.println(bln);
    }

    public void print(char chr) {
        Console.out.print(chr);
    }

    public void println(char chr) {
        Console.out.println(chr);
    }

    public void print(char[] chrs) {
        Console.out.print(chrs);
    }

    public void println(char[] chrs) {
        Console.out.println(chrs);
    }

    public void print(byte bits) {
        Console.out.print(bits);
    }

    public void println(byte bits) {
        Console.out.println(bits);
    }

    public void print(byte bits, int base) {
        String byteWithRadix = Integer.toString( (int) bits, base);
        Console.out.print(byteWithRadix);
    }

    public void println(byte bits, int base) {
        String byteWithRadix = Integer.toString( (int) bits, base);
        Console.out.println(byteWithRadix);
    }

    public void print(short sht) {
        Console.out.print(sht);
    }

    public void println(short sht) {
        Console.out.println(sht);
    }

    public void print(short sht, int base) {
        String shortWithRadix = Integer.toString( (int) sht, base);
        Console.out.print(shortWithRadix);
    }

    public void println(short sht, int base) {
        String shortWithRadix = Integer.toString( (int) sht, base);
        Console.out.println(shortWithRadix);
    }

    public void print(int integer) {
        Console.out.print(integer);
    }

    public void println(int integer) {
        Console.out.println(integer);
    }

    public void print(int integer, int base) {
        String intWithRadix = Integer.toString(integer, base);
        Console.out.print(intWithRadix);
    }

    public void println(int integer, int base) {
        String intWithRadix = Integer.toString( (int) integer, base);
        Console.out.println(intWithRadix);
    }

    public void print(long lng) {
        Console.out.print(lng);
    }

    public void println(long lng) {
        Console.out.println(lng);
    }

    public void print(long lng, int base) {
        String longWithRadix = Long.toString(lng, base);
        Console.out.print(longWithRadix);
    }

    public void println(long lng, int base) {
        String longWithRadix = Long.toString(lng, base);
        Console.out.println(longWithRadix);
    }

    public void print(float flt) {
        Console.out.print(flt);
    }

    public void println(float flt) {
        Console.out.println(flt);
    }

    public void print(double dbl) {
        Console.out.print(dbl);
    }

    public void println(double dbl) {
        Console.out.println(dbl);
    }


}



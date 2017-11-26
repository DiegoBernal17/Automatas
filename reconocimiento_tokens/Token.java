package reconocimiento_tokens;

public class Token {
    private int num_consecutivo;
    private String tipo;
    private String lexema;
    private int num_linea;

    public Token(int num_consecutivo, String tipo, String lexema, int num_linea) {
        this.num_consecutivo = num_consecutivo;
        this.tipo = tipo;
        this.lexema = lexema;
        this.num_linea = num_linea;
    }

    public boolean tipoDeDato() {
        return (lexema.equals("entero") || lexema.equals("decimal") || lexema.equals("caracter"));
    }

    public int getNum() {
        return num_consecutivo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public int getNum_linea() {
        return num_linea;
    }

    public String toString() {
        return num_consecutivo+"      "+tipo+"          "+lexema+"              "+num_linea+"\n";
    }
}

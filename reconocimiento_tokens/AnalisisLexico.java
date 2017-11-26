package reconocimiento_tokens;

import manejoarchivos.Archivos;

public class AnalisisLexico {
    private String cadena_original;
    private String cadena_auxiliar;
    private char caracter_actual;
    private char caracter_siguiente;
    private int num_consecutivo;
    private String tipo;
    private String lexema;
    private int num_linea;
    private int id_num, entero_num, real_num;
    private ContainerTokens tokens;

    private final String[] palaras_reservadas = {"cadena", "caracter", "decimal", "entero", "entonces", "entrada", "fin",
            "hacer", "hasta", "inicio", "mientras", "programa", "repetir", "salida", "si", "sino", "variables"};

    private boolean esLetra() {
        return Character.isLetter(caracter_actual);
    }

    private boolean esNumeroEntero() {
        return Character.isDigit(caracter_actual);
    }

    /**
     * IDENTIFICADORES ( 100 )
     * Inicia con # y al menos dos letra y puede contener más letras o números, cualquier cantidad y en cualquier orden
     */
    private boolean esIdentificador() {
        if(caracter_actual == '#') {
            String ide_name = "";
            num_consecutivo = 100+(++id_num);
            tipo = "Identificador";
            int numeroDeLetras=0;
            avanzar();
            while(this.esLetra() || this.esNumeroEntero()) {
                ide_name += caracter_actual;
                numeroDeLetras++;
                if(Character.isLetter(caracter_siguiente) || Character.isDigit(caracter_siguiente))
                    this.avanzar();
                else
                    break;
            }
            lexema = "#"+ide_name;
            if (numeroDeLetras >= 2) {
                tokens.push(num_consecutivo, tipo, lexema, num_linea);
                return true;
            }
            agregarError();
            return true;
        }
        return false;
    }

    /**
     * OPERADORES ARITMETICOS ( 200 )
     * - ( Resta ) + ( Suma ) * (multiplicación) / ( división )
     */
    private boolean esOperadorAritmetico() {
        if(caracter_actual == '+') {
            num_consecutivo = 201;
            tipo = "Operador Aritmético";
            lexema = "+";
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        } else if(caracter_actual == '-') {
            num_consecutivo = 202;
            tipo = "Operador Aritmético";
            lexema = "-";
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        } else if(caracter_actual == '*') {
            num_consecutivo = 203;
            tipo = "Operador Aritmético";
            lexema = "*";
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        } else if(caracter_actual == '/' && caracter_siguiente != '*') {
            num_consecutivo = 204;
            tipo = "Operador Aritmético";
            lexema = "/";
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        }
        return false;
    }

    /**
     * OPERADORES RELACIONALES ( 300 )
     * < (menor que) > ( mayor que ) <= (menor igual) >= (mayor igual) == (comparación)
     */
    private boolean esOperadorRelacional() {
         if(caracter_actual == '<' && caracter_siguiente == '=') {
            num_consecutivo = 303;
            tipo = "Operador Relacional";
            lexema = "<=";
            this.avanzar();
            this.avanzar();
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
        return true;
        } else if(caracter_actual == '>' && caracter_siguiente == '=') {
             num_consecutivo = 304;
             tipo = "Operador Relacional";
             lexema = ">=";
             this.avanzar();
             this.avanzar();
             tokens.push(num_consecutivo, tipo, lexema, num_linea);
             return true;
         } else if (caracter_actual == '<') {
            num_consecutivo = 301;
            tipo = "Operador Relacional";
            lexema = "<";
            this.avanzar();
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        } else if(caracter_actual == '>') {
            num_consecutivo = 302;
            tipo = "Operador Relacional";
            lexema = ">";
            this.avanzar();
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        } else if(caracter_actual == '=' && caracter_siguiente == '=') {
            num_consecutivo = 305;
            tipo = "Operador Relacional";
            lexema = "==";
            this.avanzar();
            this.avanzar();
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        }
        return false;
    }

    /**
     * OPERADORES LOGICOS ( 400 )
     * && ( AND ) || ( OR ) ! ( NOT )
     */
    private boolean esOperadorLogico() {
        if(caracter_actual == '&' && caracter_siguiente == '&') {
            num_consecutivo = 401;
            tipo = "Operador Logico";
            lexema = "&&";
            this.avanzar();
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        } else if(caracter_actual == '|' && caracter_siguiente == '|') {
            num_consecutivo = 402;
            tipo = "Operador Logico";
            lexema = "||";
            this.avanzar();
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        } else if(caracter_actual == '!') {
            num_consecutivo = 403;
            tipo = "Operador Logico";
            lexema = "!";
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        }
        return false;
    }

    /**
     * PALABRAS RESERVADAS ( 500 )
     * Programa, inicio, fin, entrada, salida, entero, decimal, caracter, cadena, si, sino, entonces, mientras, hacer, repetir, hasta, variables
     */
    private boolean esPalabraReservada() {
        String palabra = "";
        while(this.esLetra()) {
            palabra += caracter_actual;
            if(Character.isLetter(caracter_siguiente))
                this.avanzar();
            else
                break;
        }
        for(int i=0; i<palaras_reservadas.length; i++) {
            if(palaras_reservadas[i].equals(palabra)) {
                num_consecutivo = 501+i;
                tipo = "Palabra reservada";
                lexema = palabra;
                tokens.push(num_consecutivo, tipo, lexema, num_linea);
                return true;
            } else if(i == palaras_reservadas.length-1 && this.esLetra()) {
                lexema = palabra;
                agregarError();
                return true;
            }
        }
        return false;
    }

    /**
     * CARACTERES ESPECIALES ( 600 )
     * )  ;  ,  =
     */
    private boolean esCaracter_especial() {
        if(caracter_actual == '(') {
            num_consecutivo = 601;
            tipo = "Caracter especial";
            lexema = "(";
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        } else if(caracter_actual == ')') {
            num_consecutivo = 602;
            tipo = "Caracter especial";
            lexema = ")";
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        } else if(caracter_actual == ';') {
            num_consecutivo = 603;
            tipo = "Caracter especial";
            lexema = ";";
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        } else if(caracter_actual == ',') {
            num_consecutivo = 604;
            tipo = "Caracter especial";
            lexema = ",";
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        } else if (caracter_actual == '=') {
            num_consecutivo = 605;
            tipo = "Caracter especial";
            lexema = "=";
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        }
        return false;
    }

    /**
     * COMENTARIOS ( 700 )
     * Inician y terminan con /(asterisco) (asterisco)/ y puede contener cualquier otro carácter o estar vacío separado
     * al menos de un espacio /(asterisco) hola (asterisco)/ , /(asterisco) (asterisco)/
     */
    private boolean esComentario() {
        if(caracter_actual == '/' && caracter_siguiente == '*') {
            String comentario = "";
            num_consecutivo = 701;
            tipo = "Comentario";
            this.avanzar();
            this.avanzar();
            while (continua()) {
                if(caracter_actual == '*' && caracter_siguiente == '/') {
                    avanzar();
                    break;
                }
                comentario += caracter_actual;
                avanzar();
            }
            lexema = "/*"+comentario+"*/";
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        }
        return false;
    }

    /**
     * NUMEROS ENTEROS ( 800 )
     * Cualquier cantidad entera puede ser positiva o negativa
     */
    private boolean esNumero() {
        if(Character.isDigit(caracter_actual)) {
            String numero = "";
            while (Character.isDigit(caracter_actual)) {
                numero += caracter_actual;
                if(Character.isDigit(caracter_siguiente))
                    avanzar();
                else
                    break;
            }

            if(caracter_siguiente == '.') {
                avanzar();
                return esNumeroReal(numero);
            }
            num_consecutivo = 800+(++entero_num);
            tipo = "Numero entero";
            lexema = numero;
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        }
        return false;
    }

    /**
     * NUMEROS REALES ( 900 )
     * Cualquier cantidad con punto decimal puede ser positivo o negativo, no maneja notación
     * científica ni exponencial, siempre debe manejar una parte entera y una parte decimal
     */
    private boolean esNumeroReal(String numero) {
        if(caracter_actual == '.') {
            numero += ".";
            avanzar();
            while (continua()) {
                if(!Character.isDigit(caracter_actual))
                    break;
                numero += caracter_actual;
                avanzar();
            }
            num_consecutivo = 900+(++real_num);
            tipo = "Numero real";
            lexema = numero;
            tokens.push(num_consecutivo, tipo, lexema, num_linea);
            return true;
        }
        return false;
    }

    private void agregarError() {
        if(lexema != null)
            tokens.pushError(lexema, num_linea);
    }

    private void avanzar() {
        if(continua()) {
            caracter_actual = cadena_auxiliar.charAt(0);
            if (cadena_auxiliar.length() > 1)
                caracter_siguiente = cadena_auxiliar.charAt(1);
            else
                caracter_siguiente = ' ';
            cadena_auxiliar = cadena_auxiliar.substring(1, cadena_auxiliar.length());
        }
        if(caracter_actual == 10)
            num_linea++;
    }

    private boolean continua() {
        return cadena_auxiliar.length() > 0;
    }

    private void buscarCategoria() {
        while(continua()) {
            if(esIdentificador()) {
            } else if(esOperadorAritmetico()) {
            } else if(esOperadorRelacional()) {
            } else if(esOperadorLogico()) {
            } else if(esPalabraReservada()) {
            } else if(esCaracter_especial()) {
            } else if(esComentario()) {
            } else if(esNumero()) {
            } else if(caracter_actual <= 32) {
            } else {
                lexema = caracter_actual+"";
                agregarError();
            }
            avanzar();
        }
        guardarTablas();
    }

    public AnalisisLexico(String archivoLeer) {
        cadena_original = Archivos.leerArchivo(archivoLeer);
        tokens = new ContainerTokens();
        num_linea=1;
        cadena_auxiliar = cadena_original;
        if(continua())
            buscarCategoria();
        else
            System.out.println("El archivo se encuentra vacio");
    }

    public AnalisisLexico(String cadena, boolean leerCadena) {
        cadena_original = cadena;
        cadena_auxiliar = cadena_original;
        tokens = new ContainerTokens();
        num_linea=1;
        if(continua())
            buscarCategoria();
        else
            System.out.println("La cadena está vacia.");
    }

    public void guardarTablas() {
        Archivos.guardarArchivo("tabla", tokens.toStringTokens());
        Archivos.guardarArchivo("tabla_errores", tokens.toStringErrores());
    }

    public void imprimirTablas() {
        System.out.println(tokens.toStringTokens());
        System.out.println(tokens.toStringErrores());
    }

    public ContainerTokens getTokens() {
        return tokens;
    }
    public String getTipo() {
        return tipo;
    }
    public String getLexema() {
        return lexema;
    }
    public int getnumConsecutivo() {
        return num_consecutivo;
    }
}

package analisis_sintactico;

import javax.swing.*;
import manejoarchivos.Archivos;
import reconocimiento_tokens.*;

import java.util.Arrays;

public class Simulador {
    private String cadena_original;
    private String cadena_auxiliar;
    private char caracter_actual;
    private AnalisisLexico lexico;
    private ContainerTokens tokens;
    private int lineaActual;
    private Token token_actual;

    public static void main(String[] args) {
        new Simulador();
    }

    public Simulador() {
        int option;
        String nombre="";
        do {
            option = Integer.parseInt(JOptionPane.showInputDialog(null,"1) Leer archivo\n2) Salir"));
            if(option==1){
                try {
                    nombre = JOptionPane.showInputDialog(null, "Nombre del archivo");
                    if (nombre.equals(""))
                        nombre = "default";
                    cadena_original = Archivos.leerArchivo(nombre);
                    if (!cadena_original.isEmpty()) {
                        cadena_auxiliar = cadena_original;
                        lexico = new AnalisisLexico(cadena_original, true);
                        tokens = lexico.getTokens();
                        inicio();
                    } else
                        JOptionPane.showMessageDialog(null, "El archivo seleccionado está vacio.");
                } catch (NullPointerException e) {
                    System.out.println("Error: "+e.getMessage());
                    System.exit(0);
                }
            }
        } while(option != 2);
    }

    private void inicio() {
        // Validación de la primer palabra reservada: programa.
        validarToken("programa", "Palabra reservada");
        // Debe contener un espacio
        validarEspacio();
        // Validación del id que correspode a la P.R programa
        validarToken("", "Identificador");
        // Validación de caracter especial punto y coma (;)
        validarToken(";", "Caracter especial");
        // Ir a la validacion de variables
        variables();
        // Se va a buscar metodos validos
        sentencias();
        // Si llega aquí terminó correctamente
        System.out.println("Completado.");
    }

    private void variables() {
        // Palabra reservada variables para iniciar
        validarToken("variables", "Palabra reservada");
        // Un espacio
        validarEspacio();
        Token token_siguiente = tokens.viewNextToken();
        while(token_siguiente.tipoDeDato()) {
            // Validar el tipos de datos: caracter, entero, decimal
            validarTokens(new String[]{"entero", "decimal", "caracter"}, "Palabra reservada");
            // Debe contener un espacio
            validarEspacio();
            // Validación del id que correspode a la P.R del tipo de dato anterior validado
            validarToken("", "Identificador");
            // Quitar caracteres innecesarios (como espacios)
            quitarEspacios();
            while (caracter_actual == ',') {
                validarToken(",", "Caracter especial");
                quitarEspacios();
                // Validación del id que correspode a la P.R del tipo de dato anterior validado
                validarToken("", "Identificador");
                quitarEspacios();

                // Mientras el caracter actual no sea punto y coma (;) continua
                if (caracter_actual == ';') {
                    validarToken(";", "Caracter especial");
                    break;
                }
            }
            token_siguiente = tokens.viewNextToken();
        }
    }
    // El siguiente token que toma es el comentario así que agarra como token == comentario y se salta todo
    private void sentencias() {
        validarToken("inicio", "Palabra reservada");
        quitarComentarios();
        Token next_token = tokens.viewNextToken();
        boolean continuar = true;
        while(continuar) {
            switch (next_token.getLexema()) {
                case "entrada":
                    entradas();
                    break;
                case "salida":
                    salidas();
                    break;
                case "si":
                    si();
                    break;
                case "repetir":
                    repetir();
                default:
                    continuar=false;
            }
            if(next_token.getTipo().equals("Identificador")) {
                continuar = true;
                asigancion();
            }
            next_token = tokens.viewNextToken();
            if(next_token.getTipo().equals("Comentario")) {
                quitarComentarios();
                next_token = tokens.viewNextToken();
            }
        }
        validarToken("fin", "Palabra reservada");
        System.out.println();
    }

    private void entradas() {
        validarToken("entrada", "Palabra reservada");
        validarToken("(", "Caracter especial");
        validarToken("", "Identificador");
        validarToken(")", "Caracter especial");
        validarToken(";", "Caracter especial");
    }

    private void salidas() {
        validarToken("salida", "Palabra reservada");
        validarToken("(", "Caracter especial");
        validarToken("", "Identificador");
        validarToken(")", "Caracter especial");
        validarToken(";", "Caracter especial");
    }

    private void asigancion() {
        validarToken("", "Identificador");
        validarToken("=", "Caracter especial");
        operaciones();
        validarToken(";", "Caracter especial");
    }

    private void operaciones() {
        validarTokens("", new String[]{"Identificador","Numero entero","Numero real"});
        quitarEspacios();
        Token next_token = tokens.viewNextToken();
        while(next_token.getTipo().equals("Operador Aritmético")) {
            validarToken("", "Operador Aritmético");
            validarTokens("", new String[]{"Identificador", "Numero entero", "Numero real"});
            next_token = tokens.viewNextToken();
        }
    }

    private void si() {
        validarToken("si", "Palabra reservada");
        validarLogicaYRelacional();
        validarToken("entonces", "Palabra reservada");
        sentencias();
        Token next_token = tokens.viewNextToken();
        if(next_token.getLexema().equals("sino")) {
            validarToken("sino", "Palabra reservada");
            sentencias();
        }
    }

    private void repetir() {
        validarToken("repetir", "Palabra reservada");
        sentencias();
        validarToken("hasta", "Palabra reservada");
        validarLogicaYRelacional();
        validarToken(";", "Caracter especial");
    }

    private void validarLogicaYRelacional() {
        validarToken("(", "Caracter especial");
        validarTokens("", new String[]{"Identificador","Numero entero","Numero real"});
        validarToken("", "Operador Relacional");
        validarTokens("", new String[]{"Identificador","Numero entero","Numero real"});
        validarToken(")", "Caracter especial");
    }

    private void validarToken(String comparar, String tipoToken) {
        String[] comp = {comparar};
        String[] tipo = {tipoToken};
        validarTokens(comp, tipo);
    }

    private void validarTokens(String[] comparar, String tipoToken) {
        String[] tipos = new String[comparar.length];
        for(int i=0; i<tipos.length; i++) {
            tipos[i] = tipoToken;
        }
        validarTokens(comparar, tipos);
    }

    private void validarTokens(String comparar, String[] tipoToken) {
        String[] compara = new String[tipoToken.length];
        for(int i=0; i<compara.length; i++) {
            compara[i] = comparar;
        }
        validarTokens(compara, tipoToken);
    }

    private void validarTokens(String[] comparar, String[] tipoToken) {
        String cadena="";
        // Quitar los tokens de comentarios
        quitarComentarios();
        token_actual = tokens.nextToken();
        lineaActual = token_actual.getNum_linea();
        quitarEspacios();
        while(!cadena.equals(token_actual.getLexema())) {
            cadena += caracter_actual;
            if(cadena_auxiliar.length()<=0)
                error();
            else
                avanzar();
        }
        noCoincidencia(comparar, tipoToken);
        System.out.print(cadena);
    }

    // Si encuentra alguna coincidencia con el arreglo de "comparar" termina en ese instante (return).
    // Si no encuentra ninguna coincidencia con el arreglo de "comparar" y el lexema del token manda error
    //
    private void noCoincidencia(String[] comparar, String[] tipoToken) {
        if(comparar.length != tipoToken.length) {
            error();
        }
        noCoincidenciaTipoDato(tipoToken);
        for(String i: comparar) {
            if (i.equals("") || i.equals(token_actual.getLexema())) {
                return;
            }
        }
        error();
    }

    private void noCoincidenciaTipoDato(String[] tipoToken) {
        for(String i: tipoToken) {
            if (i.equals(token_actual.getTipo()))
                return;
        }
        error();
    }

    private void validarEspacio() {
        if(caracter_actual != 32) {
            error();
        }
    }

    private void quitarEspacios() {
        while(caracter_actual <= 32) {
            System.out.print(caracter_actual);
            avanzar();
        }
    }

    private void quitarComentarios() {
        token_actual = tokens.nextToken();
        while(token_actual.getTipo().equals("Comentario")) {
            String cadena = "";
            quitarEspacios();
            while(!cadena.equals(token_actual.getLexema())) {
                cadena += caracter_actual;
                if(cadena_auxiliar.length()<=1)
                    error();
                else
                    avanzar();
            }

            System.out.println(cadena);
            token_actual = tokens.nextToken();
        }
        token_actual = tokens.previousToken();
    }

    private void avanzar() {
        if(!esFinal()) {
            caracter_actual = cadena_auxiliar.charAt(0);
            cadena_auxiliar = cadena_auxiliar.substring(1, cadena_auxiliar.length());

            if(caracter_actual < 31) {
                System.out.print(caracter_actual);
                avanzar();
            }
        }
    }

    private void error() {
        System.out.println("Error en la linea " + lineaActual);
        System.exit(0);
    }

    private boolean esFinal() {
        return cadena_auxiliar.length() <= 0;
    }
}
package analisis_sintactico;

import java.util.Scanner;

public class DescensoRecursivo {
    private String entrada;
    private String cadena, cadena_auxiliar;
    private char apuntador;

    /*
     * S -> cAd
     * A -> ab | a
     */
    public static void main(String[] args) {
        new DescensoRecursivo();
    }

    public DescensoRecursivo() {
        Scanner sc = new Scanner(System.in);
        entrada = sc.next();
        cadena = entrada;
        S();
    }

    public void S() {
        avanzar();
        if (apuntador == 'c') {
            avanzar();
            int valor = A();
            avanzar();
            System.out.println("camino: "+ valor);
            if (valor != 2 && apuntador == 'd') {
                System.out.println(apuntador);
                return;
            }
        }
        System.out.println("Caden NO valida.");
    }

    public int A() {
        if (apuntador == 'a') {
            avanzar();
            if(apuntador == 'b') {
                return 0;
            }
            retroceder();
            return 1;
        }
        return 2;
    }

    public void avanzar() {
        if(cadena.length() > 0) {
            cadena_auxiliar = cadena;
            apuntador = cadena.charAt(0);
            cadena = cadena.substring(1, cadena.length());
        }
    }

    public void retroceder() {
        if(cadena.length() != entrada.length()) {
            apuntador = cadena_auxiliar.charAt(0);
            cadena = cadena_auxiliar;
        }
    }
}
